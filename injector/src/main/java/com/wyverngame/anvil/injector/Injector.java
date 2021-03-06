package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.wyverngame.anvil.api.client.event.AddComplexEffectEvent;
import com.wyverngame.anvil.api.client.event.AddCreatureEvent;
import com.wyverngame.anvil.api.client.event.AddEffectEvent;
import com.wyverngame.anvil.api.client.event.AddFightMoveEvent;
import com.wyverngame.anvil.api.client.event.AddGroundItemEvent;
import com.wyverngame.anvil.api.client.event.AddKingdomEvent;
import com.wyverngame.anvil.api.client.event.AddMapAnnotationEvent;
import com.wyverngame.anvil.api.client.event.AddPlonkEvent;
import com.wyverngame.anvil.api.client.event.AttachControllerEvent;
import com.wyverngame.anvil.api.client.event.AttachCreatureEvent;
import com.wyverngame.anvil.api.client.event.AttachEffectEvent;
import com.wyverngame.anvil.api.client.event.AvailableActionsEvent;
import com.wyverngame.anvil.api.client.event.AvailableSelectionBarActionsEvent;
import com.wyverngame.anvil.api.client.event.ClearMapAnnotationsEvent;
import com.wyverngame.anvil.api.client.event.ClientTickEvent;
import com.wyverngame.anvil.api.client.event.CompleteAchievementEvent;
import com.wyverngame.anvil.api.client.event.DetachEffectEvent;
import com.wyverngame.anvil.api.client.event.HudInitEvent;
import com.wyverngame.anvil.api.client.event.HudRenderEvent;
import com.wyverngame.anvil.api.client.event.KillCreatureEvent;
import com.wyverngame.anvil.api.client.event.ListAchievementEvent;
import com.wyverngame.anvil.api.client.event.ListPersonalGoalEvent;
import com.wyverngame.anvil.api.client.event.LoginEvent;
import com.wyverngame.anvil.api.client.event.MessageEvent;
import com.wyverngame.anvil.api.client.event.MoveCreatureEvent;
import com.wyverngame.anvil.api.client.event.OpenPortalMapEvent;
import com.wyverngame.anvil.api.client.event.PlayAnimationEvent;
import com.wyverngame.anvil.api.client.event.PlayAnimationWithItemEvent;
import com.wyverngame.anvil.api.client.event.PlayMusicEvent;
import com.wyverngame.anvil.api.client.event.PlaySoundEvent;
import com.wyverngame.anvil.api.client.event.ReconnectEvent;
import com.wyverngame.anvil.api.client.event.RemoveCorpseEvent;
import com.wyverngame.anvil.api.client.event.RemoveCreatureEvent;
import com.wyverngame.anvil.api.client.event.RemoveEffectEvent;
import com.wyverngame.anvil.api.client.event.RemoveGroundItemEvent;
import com.wyverngame.anvil.api.client.event.RemoveMapAnnotationEvent;
import com.wyverngame.anvil.api.client.event.RenameGroundItemEvent;
import com.wyverngame.anvil.api.client.event.ResetMovementEvent;
import com.wyverngame.anvil.api.client.event.SegmentedMessageEvent;
import com.wyverngame.anvil.api.client.event.SpeedModifierUpdateEvent;
import com.wyverngame.anvil.api.client.event.StartMovingEvent;
import com.wyverngame.anvil.api.client.event.StopSoundEngineEvent;
import com.wyverngame.anvil.api.client.event.TimeEvent;
import com.wyverngame.anvil.api.client.event.ToggleArcheryModeEvent;
import com.wyverngame.anvil.api.client.event.ToggleEvent;
import com.wyverngame.anvil.api.client.event.ToggleFeatureEvent;
import com.wyverngame.anvil.api.client.event.TradeChangedEvent;
import com.wyverngame.anvil.api.client.event.TradeCloseEvent;
import com.wyverngame.anvil.api.client.event.TradeOpenEvent;
import com.wyverngame.anvil.api.client.event.TradeStatusEvent;
import com.wyverngame.anvil.api.client.event.UpdateBridgeIdEvent;
import com.wyverngame.anvil.api.client.event.UpdateCreatureAttitudeEvent;
import com.wyverngame.anvil.api.client.event.UpdateCreatureLayerEvent;
import com.wyverngame.anvil.api.client.event.UpdateCurrentServerInformationEvent;
import com.wyverngame.anvil.api.client.event.UpdateEquipmentEvent;
import com.wyverngame.anvil.api.client.event.UpdateFaceEvent;
import com.wyverngame.anvil.api.client.event.UpdateGroundItemEvent;
import com.wyverngame.anvil.api.client.event.UpdateGroundItemModelEvent;
import com.wyverngame.anvil.api.client.event.UpdateGroundItemState;
import com.wyverngame.anvil.api.client.event.UpdateGroundOffsetEvent;
import com.wyverngame.anvil.api.client.event.UpdateHasTargetCreatureEvent;
import com.wyverngame.anvil.api.client.event.UpdateHealthTargetCreatureEvent;
import com.wyverngame.anvil.api.client.event.UpdateNeighbourServersEvent;
import com.wyverngame.anvil.api.client.event.UpdatePaintEvent;
import com.wyverngame.anvil.api.client.event.UpdatePersonalGoalEvent;
import com.wyverngame.anvil.api.client.event.UpdateRotationEvent;
import com.wyverngame.anvil.api.client.event.UpdateSelectedItemForKeepingEvent;
import com.wyverngame.anvil.api.client.event.UpdateSelectionBarActionsEvent;
import com.wyverngame.anvil.api.client.event.UpdateSizeEvent;
import com.wyverngame.anvil.api.client.event.UpdateStanceEvent;
import com.wyverngame.anvil.api.client.event.UpdateWeatherEvent;
import com.wyverngame.anvil.api.client.event.UpdateWindImpactEvent;
import com.wyverngame.anvil.api.client.event.UseItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowActionResultEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowAddCategoryListEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowAddGroundItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowAddItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowAddItemToListEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowClearItemListEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowItemTreeSentEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowRemoveGroundItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowReplaceGroundItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowRequestStructureListEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowSortItemListEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowUpdateCategoryEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowUpdateGroundItemEvent;
import com.wyverngame.anvil.api.client.event.creationwindow.CreationWindowUpdateUnfinishedEvent;
import com.wyverngame.anvil.api.client.event.player.JoinGroupEvent;
import com.wyverngame.anvil.api.client.event.player.LeaveGroupEvent;
import com.wyverngame.anvil.api.client.event.player.TeleportEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateActionEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateClimbingEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateDeadEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateHungerEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateKingdomEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateMountSpeedEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateStaminaEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateStatusEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateStunnedEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateTargetCreatureEvent;
import com.wyverngame.anvil.api.client.event.player.UpdateThirstEvent;
import com.wyverngame.anvil.api.client.event.render.WorldRenderEvent;
import com.wyverngame.anvil.api.client.event.structure.AddBridgeEvent;
import com.wyverngame.anvil.api.client.event.structure.AddFenceEvent;
import com.wyverngame.anvil.api.client.event.structure.AddHouseEvent;
import com.wyverngame.anvil.api.client.event.structure.FenceOpenEvent;
import com.wyverngame.anvil.api.client.event.structure.MarkStructureEvent;
import com.wyverngame.anvil.api.client.event.structure.RemoveFenceEvent;
import com.wyverngame.anvil.api.client.event.structure.RemoveStructureEvent;
import com.wyverngame.anvil.api.client.event.structure.UpdateStructureDamageEvent;
import com.wyverngame.anvil.api.client.event.structure.UpdateStructurePartDamageEvent;
import com.wyverngame.anvil.api.client.event.structure.WallOpenEvent;
import com.wyverngame.anvil.api.client.event.structure.WallPassableEvent;
import com.wyverngame.anvil.api.client.event.terrain.TerrainCaveUpdateEvent;
import com.wyverngame.anvil.api.client.event.terrain.TerrainFarUpdateEvent;
import com.wyverngame.anvil.api.client.event.terrain.TerrainNearUpdateEvent;
import com.wyverngame.anvil.api.server.event.*;
import com.wyverngame.anvil.api.server.event.action.QueueActionEvent;
import com.wyverngame.anvil.api.server.event.combat.GetMinimumBowRangeEvent;
import com.wyverngame.anvil.api.server.event.combat.GetRangeDifficultyEvent;
import com.wyverngame.anvil.injector.trans.MethodHookTransformer;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.client.WurmClientBaseTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionEntryPriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionEntryTypePriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionFaithfulPriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionTimeTransformer;
import com.wyverngame.anvil.injector.trans.server.AddDugItemTransformer;
import com.wyverngame.anvil.injector.trans.server.CaWindowTransformer;
import com.wyverngame.anvil.injector.trans.server.ChangeKingdomTransformer;
import com.wyverngame.anvil.injector.trans.server.ChaosTransformer;
import com.wyverngame.anvil.injector.trans.server.ChatTransformer;
import com.wyverngame.anvil.injector.trans.server.CreateCreatureTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsCropPollTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsTilePollTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsTransformer;
import com.wyverngame.anvil.injector.trans.server.GetBehavioursTransformer;
import com.wyverngame.anvil.injector.trans.server.InvulnerableTraderTransformer;
import com.wyverngame.anvil.injector.trans.server.LootTransformer;
import com.wyverngame.anvil.injector.trans.server.MaxGuardsTransformer;
import com.wyverngame.anvil.injector.trans.server.PortalTransformer;
import com.wyverngame.anvil.injector.trans.server.ReimburseMethodTransformer;
import com.wyverngame.anvil.injector.trans.server.ServerTransformer;
import com.wyverngame.anvil.injector.trans.server.SkillGainTransformer;
import com.wyverngame.anvil.injector.trans.server.SowActionTimeTransformer;
import com.wyverngame.anvil.injector.trans.server.SpyPreventionTransformer;
import com.wyverngame.anvil.injector.trans.server.TicketAddTransformer;
import com.wyverngame.anvil.injector.trans.server.TreasureChestTransformer;
import com.wyverngame.anvil.injector.trans.server.VeinCapTransformer;
import com.wyverngame.anvil.injector.trans.server.XmasAfterCalendarTransformer;
import com.wyverngame.anvil.injector.trans.server.XmasBeforeCalendarTransformer;
import com.wyverngame.anvil.injector.trans.server.XmasCalendarTransformer;
import com.wyverngame.anvil.injector.trans.server.XmasPresentTransformer;
import com.wyverngame.anvil.injector.util.EmptyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Injector {
	private static final Logger logger = LoggerFactory.getLogger(Injector.class.getName());
	private static final URL[] EMPTY_URL_ARRAY = new URL[0];

	public static void main(String[] args) throws IOException {
		Injector.create().run();
	}

	public static Injector create() throws IOException {
		logger.info("Reading jars...");

		Module common = Module.read(Paths.get("common-public.jar"));
		Module client = Module.read(Paths.get("client-public.jar"));
		Module server = Module.read(Paths.get("server-public.jar"));
		return new Injector(common, client, server);
	}

	private final ImmutableList<Transformer> commonTransformers = ImmutableList.of();
	private final ImmutableList<Transformer> clientTransformers = ImmutableList.of(
		new WurmClientBaseTransformer(),
		new MethodHookTransformer(
			"com/wurmonline/client/WurmClientBase",
			"runGameLoop",
			"()V",
			ClientTickEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/renderer/gui/HeadsUpDisplay",
			"init",
			"(II)V",
			HudInitEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/renderer/gui/HeadsUpDisplay",
			"render",
			"(FZII)V",
			HudRenderEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"loginResult",
			"(Ljava/lang/String;Ljava/lang/String;FFFFIJJBBIBJF)V",
			LoginEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"serverTime",
			"(JJ)V",
			TimeEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"textMessage",
			"(Ljava/lang/String;FFFLjava/lang/String;B)V",
			MessageEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"textMessage",
			"(Ljava/lang/String;Ljava/util/List;)V",
			SegmentedMessageEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"teleport",
			"(ZFFFFIIZBI)V",
			TeleportEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setSpeedModifier",
			"(F)V",
			SpeedModifierUpdateEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addTheCreature",
			"(JLjava/lang/String;Ljava/lang/String;BFFFFBZBZIBBBJ)V",
			AddCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"moveCreature",
			"(JBBFB)V",
			MoveCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"deleteCreature",
			"(J)V",
			RemoveCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playDeadThenReplaceWithCorpse",
			"(JJLjava/lang/String;Ljava/lang/String;BFFFFBLjava/lang/String;SF)V",
			KillCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"startMoving",
			"()V",
			StartMovingEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStripFar",
			"(SS[[SSS[[B)V",
			TerrainFarUpdateEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStrip",
			"(SS[[I[[SSS)V",
			TerrainNearUpdateEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStripCave",
			"(SSS[[IS[[S)V",
			TerrainCaveUpdateEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAvailableActions",
			"(BLjava/util/List;Ljava/lang/String;)V",
			AvailableActionsEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAvailableSelectionBarActions",
			"(BLjava/util/List;)V",
			AvailableSelectionBarActionsEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updateSelectionbarActions",
			"(J)V",
			UpdateSelectionBarActionsEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setSelectedItemForKeeping",
			"(J)V",
			UpdateSelectedItemForKeepingEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addItem",
			"(JLjava/lang/String;Ljava/lang/String;BFFFFBLjava/lang/String;SFJB)V",
			AddGroundItemEvent.class,
			false),
		// TODO addProjectile event
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeItem",
			"(J)V",
			RemoveGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeFromCorpsesToAddLaterListWithId",
			"(J)V",
			RemoveCorpseEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setActionString",
			"(JLjava/lang/String;S)V",
			UpdateActionEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setUpdatePlayerKingdom",
			"(B)V",
			UpdateKingdomEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setTargetCreature",
			"(J)V",
			UpdateTargetCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setHasTarget",
			"(JZ)V",
			UpdateHasTargetCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureDamage",
			"(JF)V",
			UpdateHealthTargetCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setItemState",
			"(JS)V",
			UpdateGroundItemState.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"resetMovement",
			"()V",
			ResetMovementEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"stopSoundEngine",
			"()V",
			StopSoundEngineEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addEffect",
			"(JSFFFI)V",
			AddEffectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addComplexEffect",
			"(JJSFFFIFFIBB)V",
			AddComplexEffectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeEffect",
			"(J)V",
			RemoveEffectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStamina",
			"(FF)V",
			UpdateStaminaEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setHunger",
			"(FB)V",
			UpdateHungerEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setThirst",
			"(F)V",
			UpdateThirstEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addHouse",
			"(JLjava/lang/String;SSB)V",
			AddHouseEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addBridge",
			"(JLjava/lang/String;SS)V",
			AddBridgeEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeStructure",
			"(J)V",
			RemoveStructureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"buildMark",
			"(JSSB)V",
			MarkStructureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWallOpen",
			"(JIIIBZB)V",
			WallOpenEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWallPassable",
			"(JIIIBZB)V",
			WallPassableEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"rename",
			"(IJLjava/lang/String;)V",
			RenameGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openTradeWindow",
			"(Ljava/lang/String;Z)V",
			TradeOpenEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"closeTradeWindow",
			"()V",
			TradeCloseEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setTradeAgree",
			"(Z)V",
			TradeStatusEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tradeChanged",
			"(I)V",
			TradeChangedEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"renameItem",
			"(JLjava/lang/String;Ljava/lang/String;BLjava/lang/String;SB)V",
			UpdateGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"changeModelName",
			"(JLjava/lang/String;)V",
			UpdateGroundItemModelEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addFence",
			"(IIIBBFFFFBLjava/lang/String;)V",
			AddFenceEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeFence",
			"(IIIBB)V",
			RemoveFenceEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openWall",
			"(IIIBZZB)V",
			FenceOpenEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openFence",
			"(IIIBZZZB)V",
			FenceOpenEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playSound",
			"(Ljava/lang/String;FFFFFFZ)V",
			PlaySoundEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playMusic",
			"(Ljava/lang/String;FFFFFF)V",
			PlayMusicEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStatusString",
			"(Ljava/lang/String;)V",
			UpdateStatusEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"joinGroup",
			"(Ljava/lang/String;Ljava/lang/String;J)V",
			JoinGroupEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"partGroup",
			"(Ljava/lang/String;Ljava/lang/String;)V",
			LeaveGroupEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureAttitude",
			"(JI)V",
			UpdateCreatureAttitudeEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToAchievementList",
			"(ILjava/lang/String;Ljava/lang/String;BJI)V",
			ListAchievementEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAchievement",
			"(ZZILjava/lang/String;Ljava/lang/String;BJI)V",
			CompleteAchievementEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToPersonalGoalList",
			"(ILjava/lang/String;Ljava/lang/String;BZ)V",
			ListPersonalGoalEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updatePersonalGoalList",
			"(IZ)V",
			UpdatePersonalGoalEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToKingdomList",
			"(BLjava/lang/String;Ljava/lang/String;)V",
			AddKingdomEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWeather",
			"(FFFFFF)V",
			UpdateWeatherEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"reconnect",
			"()V",
			ReconnectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setClimbing",
			"(Z)V",
			UpdateClimbingEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStunned",
			"(Z)V",
			UpdateStunnedEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setToggle",
			"(II)V",
			ToggleEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"toggleArcheryMode",
			"(I)V",
			ToggleArcheryModeEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDead",
			"(Z)V",
			UpdateDeadEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureLayer",
			"(JI)V",
			UpdateCreatureLayerEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"toggleClientFeature",
			"(II)V",
			ToggleFeatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"attachEffect",
			"(JBBBBB)V",
			AttachEffectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"unattachEffect",
			"(JB)V",
			DetachEffectEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setEquipment",
			"(JILjava/lang/String;B)V",
			UpdateEquipmentEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDamageState",
			"(JB)V",
			UpdateStructureDamageEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDamageState",
			"(JJB)V",
			UpdateStructurePartDamageEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"useItem",
			"(JLjava/lang/String;B)V",
			UseItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"repaint",
			"(JFFFFI)V",
			UpdatePaintEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"resize",
			"(JFFF)V",
			UpdateSizeEvent.class,
			false),
		new MethodHookTransformer(
		"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setBridgeId",
			"(JJ)V",
			UpdateBridgeIdEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"attachCreature",
			"(JJFFFB)V",
			AttachCreatureEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setVehicleController",
			"(JJFFFFFFFB)V",
			AttachControllerEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playAnimation",
			"(JLjava/lang/String;ZZ)V",
			PlayAnimationEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playAnimationWithTargetItem",
			"(JLjava/lang/String;ZZJ)V",
			PlayAnimationWithItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStance",
			"(JB)V",
			UpdateStanceEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setGroundOffset",
			"(IZ)V",
			UpdateGroundOffsetEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWindImpact",
			"(B)V",
			UpdateWindImpactEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setMountSpeed",
			"(B)V",
			UpdateMountSpeedEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setNewFace",
			"(JJ)V",
			UpdateFaceEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openPortalMap",
			"()V",
			OpenPortalMapEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCurrentServerInformation",
			"(ZILjava/lang/String;)V",
			UpdateCurrentServerInformationEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addMapAnnotations",
			"(JBLjava/lang/String;IILjava/lang/String;B)V",
			AddMapAnnotationEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeMapAnnotations",
			"(JBLjava/lang/String;)V",
			RemoveMapAnnotationEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"clearMapAnnotationsByType",
			"(B)V",
			ClearMapAnnotationsEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setIsUnfinishedViewForCreationWindow",
			"(Z)V",
			CreationWindowUpdateUnfinishedEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"clearItemListsForCreationWindow",
			"()V",
			CreationWindowClearItemListEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCurrentCategoryForCreationWindow",
			"(Ljava/lang/String;)V",
			CreationWindowUpdateCategoryEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationGroundItem",
			"(Ljava/lang/String;JFFFSLcom/wurmonline/client/renderer/gui/CreationFrame$ItemType;)V",
			CreationWindowAddGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"replaceCreationGroundItem",
			"(JLjava/lang/String;JFFFSLcom/wurmonline/client/renderer/gui/CreationFrame$ItemType;)V",
			CreationWindowReplaceGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setActionResult",
			"(Z)V",
			CreationWindowActionResultEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updateCreationGroundItem",
			"(JFFF)V",
			CreationWindowUpdateGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeCreationGroundItem",
			"(J)V",
			CreationWindowRemoveGroundItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationCategoryLists",
			"(Ljava/util/Map;S)V",
			CreationWindowAddCategoryListEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addItemToCreationList",
			"(Lcom/wurmonline/client/renderer/gui/CreationListItem;)V",
			CreationWindowAddItemToListEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationItem",
			"(Ljava/lang/String;SSS)V",
			CreationWindowAddItemEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"creationItemTreeSent",
			"()V",
			CreationWindowItemTreeSentEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"sortCreationItemList",
			"()V",
			CreationWindowSortItemListEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"requestStructureCreationList",
			"(J)V",
			CreationWindowRequestStructureListEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addPlonk",
			"(S)V",
			AddPlonkEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setRotation",
			"(JF)V",
			UpdateRotationEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setAvailableServer",
			"(BZ)V",
			UpdateNeighbourServersEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addFightMove",
			"(SLjava/lang/String;)V",
			AddFightMoveEvent.class,
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/renderer/WorldRender",
			"render",
			"(II)V",
			WorldRenderEvent.class)
	);

	private final ImmutableList<Transformer> serverTransformers = ImmutableList.of(
		new ServerTransformer(),
		new ChaosTransformer(),
		new LootTransformer(),
		new SkillGainTransformer(),
		new ActionEntryPriestRestrictionTransformer(),
		new ActionEntryTypePriestRestrictionTransformer(),
		new ActionFaithfulPriestRestrictionTransformer(),
		new FarmingWeedsTransformer(),
		new FarmingWeedsTilePollTransformer(),
		new FarmingWeedsCropPollTransformer(),
		new PortalTransformer(),
		//new SteamAuthDuplicateTransformer(),
		new CreateCreatureTransformer(),
		new ChatTransformer(),
		new ChangeKingdomTransformer(),
		new ActionTimeTransformer("getSlowActionTime", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/skills/Skill;Lcom/wurmonline/server/items/Item;D)I", 250D),
		new ActionTimeTransformer("getPickActionTime", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/skills/Skill;Lcom/wurmonline/server/items/Item;D)I", 300D, 200D),
		new ActionTimeTransformer("getImproveActionTime", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;)I", 50D),
		new ActionTimeTransformer("getRepairActionTime", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/skills/Skill;D)I", 300D),
		new ActionTimeTransformer("getDestroyActionTime", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/skills/Skill;Lcom/wurmonline/server/items/Item;D)I", 300D, 50D),
		new SowActionTimeTransformer(),
		new SpyPreventionTransformer(),
		new InvulnerableTraderTransformer(),
		new MaxGuardsTransformer("<clinit>", "()V"),
		new MaxGuardsTransformer("getCostForGuards", "(I)J"),
		new CaWindowTransformer(),
		new XmasCalendarTransformer(),
		new XmasBeforeCalendarTransformer(),
		new XmasAfterCalendarTransformer(),
		new XmasPresentTransformer(),
		new ReimburseMethodTransformer(),
		new TicketAddTransformer(),
		new TreasureChestTransformer(),
		//new SecureLoginTransformer(),
		new GetBehavioursTransformer(),
		new VeinCapTransformer(),
		new AddDugItemTransformer(),
		new MethodHookTransformer(
			"com/wurmonline/server/behaviours/BehaviourDispatcher",
			"requestActions",
			"(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/creatures/Communicator;BJJ)V",
			RequestActionsEvent.class, false),
		new MethodHookTransformer(
			"com/wurmonline/server/behaviours/MethodsItems",
			"take",
			"(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;)Lcom/wurmonline/server/behaviours/TakeResultEnum;",
			CheckTakeItemEvent.class, false),
		new MethodHookTransformer(
			"com/wurmonline/server/players/Player",
			"setDead",
			"(Z)V",
			SetDeadEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/players/Player",
			"createSomeItems",
			"(FZ)V",
			CreateStarterItemsEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/players/Player",
			"sendSpawnQuestion",
			"()V",
			SendSpawnQuestionEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/creatures/Creature",
			"poll",
			"()Z",
			CreaturePollEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/Server",
			"run",
			"()V",
			ServerTickEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/structures/Structure",
			"getLimitFor",
			"(IIZZ)I",
			GetStructureSkillRequirementEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/items/DbItem",
			"setTemplateId",
			"(I)V",
			SetItemTemplateEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/combat/Archery",
			"getMinimumRangeForBow",
			"(I)I",
			GetMinimumBowRangeEvent.class, false),
		new MethodHookTransformer(
			"com/wurmonline/server/combat/Archery",
			"getRangeDifficulty",
			"(Lcom/wurmonline/server/creatures/Creature;IFF)D",
			GetRangeDifficultyEvent.class, false),
		new MethodHookTransformer(
			"com/wurmonline/server/behaviours/ActionStack",
			"addAction",
			"(Lcom/wurmonline/server/behaviours/Action;)V",
			QueueActionEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/items/Item",
			"putItemInfrontof",
			"(Lcom/wurmonline/server/creatures/Creature;F)V",
			PutItemInfrontOfEvent.class
		),
		new MethodHookTransformer(
			"com/wurmonline/server/questions/KarmaQuestion",
			"answer",
			"(Ljava/util/Properties;)V",
			KarmaQuestionAnswerEvent.class),
		new MethodHookTransformer(
			"com/wurmonline/server/players/Player",
			"initialisePlayer",
			"(Lcom/wurmonline/server/players/PlayerInfo;)V",
			InitializePlayerEvent.class
		),
		new MethodHookTransformer(
			"com/wurmonline/server/LoginHandler",
			"loadPlayer",
			"(Lcom/wurmonline/server/players/Player;I)I",
			LoadPlayerEvent.class)
	);
	private final Module common, client, server;

	private Injector(Module common, Module client, Module server) {
		this.common = common;
		this.client = client;
		this.server = server;
	}

	public void run() throws IOException {
		logger.info("Transforming classes...");

		/* transform common.jar */
		Application commonApplication = new Application(common);

		for (Transformer transformer : commonTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(commonApplication);
		}

		/* transform client.jar */
		Application clientApplication = new Application(common, client);

		for (Transformer transformer : clientTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(clientApplication);
		}

		/* transform server.jar */
		Application serverApplication = new Application(common, server);

		for (Transformer transformer : serverTransformers) {
			logger.info("Running transformer: {}...", transformer.getClass().getName());
			transformer.transform(serverApplication);
		}

		/* find libraries required to compute stack frames */
		ClassLoader commonClassLoader = new EmptyClassLoader();
		ClassLoader clientClassLoader = createClassLoader(Paths.get("wurm/client/WurmLauncher/lib"));
		ClassLoader serverClassLoader = createClassLoader(Paths.get("wurm/server/lib"));

		/* write patched jars */
		logger.info("Writing jars...");

		common.write(commonApplication, commonClassLoader, Paths.get("common-patched.jar"));
		client.write(clientApplication, clientClassLoader, Paths.get("client-patched.jar"));
		server.write(serverApplication, serverClassLoader, Paths.get("server-patched.jar"));
	}

	private ClassLoader createClassLoader(Path path) throws IOException {
		List<URL> jars = new ArrayList<>();

		for (Path jar : Files.newDirectoryStream(path, "*.jar")) {
			jars.add(jar.toUri().toURL());
		}

		return new URLClassLoader(jars.toArray(EMPTY_URL_ARRAY));
	}
}
