package com.wyverngame.anvil.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ArrayBlockingQueue;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.ActionTypes;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.builder.ActionEntryBuilder;
import com.wyverngame.anvil.api.server.event.RequestActionsEvent;
import com.wyverngame.anvil.api.server.event.ServerTickEvent;
import com.wyverngame.anvil.api.server.event.action.ItemActionEvent;

@PluginMetadata(name = "Claim Vote Plugin", version = "1.0.0", author = "Jonneh")
public final class ClaimVotePlugin extends ServerPlugin {
	private final String CLAIM_KEY = "CLAIM";
	private final String API_KEY = "<insert API key here>";

	@Override
	public void init() throws Exception {
		final ArrayBlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(1024);

		ActionEntry claimActionEntry = registerActionEntry(
			new ActionEntryBuilder(CLAIM_KEY, "Claim vote reward")
			.setDescription("claiming")
			.addTypes(
				ActionTypes.ACTION_TYPE_QUICK,
				ActionTypes.ACTION_TYPE_NEVER_USE_ACTIVE_ITEM
			)
		);

		on(RequestActionsEvent.class, (ctx, evt) -> {
			if (!evt.getTargetType().isItem()) return;

			try {
				Item item = Items.getItem(evt.getTarget());
				if (item.getTemplateId() != ItemList.villageToken) return;

				ctx.cancel();
				evt.sendActions(claimActionEntry);
			} catch (NoSuchItemException ex) {
				/* ignore */
			}
		});

		on(ItemActionEvent.class, (ctx, evt) -> {
			Item item = evt.getFirstItem();
			if (item.getTemplateId() != ItemList.villageToken) return;
			if (evt.getActionId() != claimActionEntry.getNumber()) return;

			ctx.cancel();

			new Thread(() -> {
				try {
					HttpsURLConnection connection = (HttpsURLConnection) new URL(
						"https://wurm-unlimited.com/api/?action=post&object=votes&element=claim&key=" + API_KEY +
							"&steamid=" + evt.getPerformer().SteamId
					).openConnection();
					connection.setSSLSocketFactory(createTrustAllSocketFactory());

					int responseCode = connection.getResponseCode();
					if (responseCode != 200) throw new IOException("Invalid response code.");

					InputStream in = connection.getInputStream();

					ByteArrayOutputStream stringBuffer = new ByteArrayOutputStream();
					byte[] buffer = new byte[256];
					int len = 0;
					while (len != -1) {
						stringBuffer.write(buffer, 0, len);
						len = in.read(buffer);
					}

					String result = new String(stringBuffer.toByteArray(), StandardCharsets.UTF_8);
					if (result.equals("1")) {
						tasks.add(() -> {
							try {
								evt.getPerformer().addMoney(MonetaryConstants.COIN_COPPER * 10);
								evt.getPerformer().getCommunicator().sendNormalServerMessage("Thanks for voting for Wyvern! 10 copper coins have been added to your bank.");
							} catch (IOException ex) {
								tasks.add(() -> evt.getPerformer().getCommunicator().sendAlertServerMessage("Failed to add reward. Please submit a support ticket."));
								ex.printStackTrace();
							}
						});
					} else {
						tasks.add(() -> evt.getPerformer().getCommunicator().sendAlertServerMessage("It appears you haven't voted recently with this steam account or have already claimed your reward for today. You can vote for Wyvern at http://wurm-unlimited.com/server/53/vote/"));
					}

					in.close();
				} catch (IOException ex) {
					tasks.add(() -> evt.getPerformer().getCommunicator().sendAlertServerMessage("Unable to check vote at this time. Please try again later."));
					ex.printStackTrace();
				}
			}).start();
		});

		on(ServerTickEvent.class, (ctx, evt) -> {
			while (!tasks.isEmpty()) {
				tasks.poll().run();
			}
		});
	}

	/*
	 * We should really add the ISRG root to the JDK's trust store. But Wurm distributes the JDK so any steam updates
	 * would overwrite this. So for now let's just go with the hacky solution of disabling trust checks :(
	 */
	private SSLSocketFactory createTrustAllSocketFactory() throws IOException {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			TrustManager[] trustManagers = {
				new X509TrustManager() {
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) {
						/* empty */
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType) {
						/* empty */
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				}
			};
			ctx.init(null, trustManagers, null);
			return ctx.getSocketFactory();
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
	}
}
