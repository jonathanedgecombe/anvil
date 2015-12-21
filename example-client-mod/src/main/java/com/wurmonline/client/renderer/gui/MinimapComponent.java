package com.wurmonline.client.renderer.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.wurmonline.client.game.TerrainDataBuffer;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.PickData;
import com.wurmonline.client.settings.SavePosManager;
import com.wurmonline.client.settings.WindowPosition;
import com.wurmonline.mesh.Tiles;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class MinimapComponent extends StaticComponent implements WindowSerializer {
	private static final int[] COLOURS = new int[256];
	private static final int SIZE = 208;
	private static final int BORDER_SIZE = 4;

	static {
		for (Tiles.Tile tile : Tiles.Tile.values()) {
			COLOURS[tile.getId() & 0xFF] = tile.getColor().getRGB();
		}
	}

	private final World world;
	private final HeadsUpDisplay hud;
	private final DragController dragger;
	private final BufferedImage border;

	private boolean first = true;
	private int textureId = 0;
	private int update = 0;
	private float alpha = 1;

	public MinimapComponent(World world, HeadsUpDisplay hud) throws IOException, IllegalAccessException, NoSuchFieldException {
		super("Minimap", 0, 0, SIZE, SIZE);
		this.world = world;
		this.hud = hud;
		this.dragger = new DragController(this);

		hud.mainMenu.registerComponent("Minimap", this);
		hud.mainMenu.setAvailable(this, true);
		hud.hudSettings.registerComponent("Minimap", this);
		hud.hudSettings.setAvailable(this, true);

		Field field = HeadsUpDisplay.class.getDeclaredField("savePosManager");
		field.setAccessible(true);
		((SavePosManager) field.get(hud)).registerAndRefresh(this, "minimap");

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("minimap_border.png");
		border = ImageIO.read(in);
		in.close();
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	protected void renderComponent(float alpha) {
		if (first) {
			textureId = GL11.glGenTextures();
			first = false;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, this.alpha);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		if ((update++ % 5) == 0) {
			TerrainDataBuffer buffer = world.getNearTerrainBuffer();
			int playerX = world.getPlayerCurrentTileX();
			int playerY = world.getPlayerCurrentTileY();
			float playerRotation = (float) (world.getPlayerRotX() * Math.PI / 180);

			byte[] pixels = new byte[SIZE * SIZE * 3];

			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					if (x < BORDER_SIZE || y < BORDER_SIZE || x >= SIZE - BORDER_SIZE || y >= SIZE - BORDER_SIZE) {
						int rgb = border.getRGB(x, y);
						pixels[(((y * SIZE) + x) * 3) + 0] = (byte) ((rgb >> 16) & 0xFF);
						pixels[(((y * SIZE) + x) * 3) + 1] = (byte) ((rgb >> 8) & 0xFF);
						pixels[(((y * SIZE) + x) * 3) + 2] = (byte) ((rgb >> 0) & 0xFF);
					} else {
						byte c = ((x / 4) + (y / 4)) % 2 == 0 ? (byte) 0x88 : (byte) 0xBB;
						pixels[(((y * SIZE) + x) * 3) + 0] = c;
						pixels[(((y * SIZE) + x) * 3) + 1] = c;
						pixels[(((y * SIZE) + x) * 3) + 2] = c;
					}
				}
			}

			for (int dx = -142; dx < 142; dx++) {
				for (int dy = -142; dy < 142; dy++) {
					int tx = (int) ((Math.cos(playerRotation) * dx) - (Math.sin(playerRotation) * dy));
					int ty = (int) ((Math.sin(playerRotation) * dx) + (Math.cos(playerRotation) * dy));

					int tileType = buffer.getRawType(playerX + tx, playerY + ty) & 0xFF;
					int colour = COLOURS[tileType];

					int r = (colour >>> 16) & 0xFF;
					int g = (colour >>> 8) & 0xFF;
					int b = colour & 0xFF;

					float tileHeight = buffer.getHeight(playerX + tx, playerY + ty) / 4;
					//float tileHeightN = buffer.getHeight(playerX + tx + 1, playerY + ty + 1) / 4;

					/*float delta = tileHeightN - tileHeight;
					float factor = (float) (1f + Math.tanh(delta / 4f));

					r = (int) (factor * r);
					g = (int) (factor * g);
					b = (int) (factor * b);*/

					if (tileHeight < 0) {
						r = (r / 5) + 41;
						g = (r / 5) + 51;
						b = (r / 5) + 102;
					}

					if (r < 0) r = 0;
					if (r > 255) r = 255;
					if (g < 0) g = 0;
					if (g > 255) g = 255;
					if (b < 0) b = 0;
					if (b > 255) b = 255;

					for (int qx = 0; qx < 2; qx++) {
						for (int qy = 0; qy < 2; qy++) {
							int ix = dx + qx + (SIZE / 2);
							int iy = dy + qy + (SIZE / 2);

							if (ix < BORDER_SIZE || iy < BORDER_SIZE || ix >= SIZE - BORDER_SIZE || iy >= SIZE - BORDER_SIZE) {
								continue;
							}

							pixels[(((iy * SIZE) + ix) * 3) + 0] = (byte) r;
							pixels[(((iy * SIZE) + ix) * 3) + 1] = (byte) g;
							pixels[(((iy * SIZE) + ix) * 3) + 2] = (byte) b;
						}
					}
				}
			}

			dot(SIZE / 2, SIZE / 2, pixels, (byte) 255, (byte) 255, (byte) 255);

			ByteBuffer b = BufferUtils.createByteBuffer(SIZE * SIZE * 3);
			b.put(pixels);
			b.flip();

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, SIZE, SIZE, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, b);
		}

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(this.x, this.y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(this.x, this.y + SIZE);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(this.x + SIZE, this.y + SIZE);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(this.x + SIZE, this.y);
		GL11.glEnd();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);
	}

	@Override
	protected void leftPressed(int xMouse, int yMouse, int clickCount) {
		this.dragger.leftPressed(xMouse, yMouse, clickCount);
	}

	@Override
	protected void mouseDragged(int xMouse, int yMouse) {
		this.dragger.mouseDragged(xMouse, yMouse);
	}

	@Override
	protected void leftReleased(int xMouse, int yMouse) {
		this.dragger.leftReleased(xMouse, yMouse);
	}

	@Override
	public void pick(PickData pickData, int xMouse, int yMouse) {
		pickData.addText("Minimap");
	}

	@Override
	protected void rightPressed(int xMouse, int yMouse, int clickCount) {
		final MinimapComponent minimap = this;

		WurmPopup popup = new WurmPopup("minimapMenu", "Minimap Options", xMouse, yMouse);
		popup.addSeparator();

		this.dragger.addContextMenuEntry(popup);

		WurmPopup group = new WurmPopup("opacitySubmenu");
		group.addButton(group.new WPopupLiveButton("100%", null) {
			@Override
			protected void handleLeftClick() {
				minimap.setAlpha(1);
			}
		});
		group.addButton(group.new WPopupLiveButton("75%", null) {
			@Override
			protected void handleLeftClick() {
				minimap.setAlpha(0.75f);
			}
		});
		group.addButton(group.new WPopupLiveButton("50%", null) {
			@Override
			protected void handleLeftClick() {
				minimap.setAlpha(0.5f);
			}
		});
		group.addButton(group.new WPopupLiveButton("25%", null) {
			@Override
			protected void handleLeftClick() {
				minimap.setAlpha(0.25f);
			}
		});
		popup.addButton(popup.new WPopupDeadButton("Opacity", group));

		hud.showPopupComponent(popup);
	}

	@Override
	public void restorePositionHints(WindowPosition windowPosition) {
		this.setPosition(windowPosition.x, windowPosition.y);
	}

	@Override
	public WindowPosition createPositionHints() {
		return new WindowPosition(this.x, this.y);
	}

	public static void dot(int x, int y, byte[] pixels, byte r, byte g, byte b) {
		for (int dx = 0; dx < 4; dx++) {
			for (int dy = 0; dy < 4; dy++) {
				if ((dx == 0 && dy == 0) || (dx == 0 && dy == 3) || (dx == 3 && dy == 0) || (dx == 3 && dy == 3)) {
					continue;
				}

				int px = x + dx - 2;
				int py = y + dy - 2;

				if (px < BORDER_SIZE || py < BORDER_SIZE || px >= SIZE - BORDER_SIZE || py >= SIZE - BORDER_SIZE) {
					continue;
				}

				pixels[(((py * SIZE) + px) * 3) + 0] = r;
				pixels[(((py * SIZE) + px) * 3) + 1] = g;
				pixels[(((py * SIZE) + px) * 3) + 2] = b;
			}
		}
	}
}
