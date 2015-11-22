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
import com.google.common.collect.ImmutableMap;
import com.wyverngame.anvil.injector.trans.Transformer;
import com.wyverngame.anvil.injector.trans.client.MethodHookTransformer;
import com.wyverngame.anvil.injector.trans.client.WurmClientBaseTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionEntryPriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionEntryTypePriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ActionFaithfulPriestRestrictionTransformer;
import com.wyverngame.anvil.injector.trans.server.ChangeKingdomTransformer;
import com.wyverngame.anvil.injector.trans.server.ChaosNotAllowedTransformer;
import com.wyverngame.anvil.injector.trans.server.ChaosTransformer;
import com.wyverngame.anvil.injector.trans.server.ChatTransformer;
import com.wyverngame.anvil.injector.trans.server.CreateCreatureTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsCropPollTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsTilePollTransformer;
import com.wyverngame.anvil.injector.trans.server.FarmingWeedsTransformer;
import com.wyverngame.anvil.injector.trans.server.FreedomAltarTransformer;
import com.wyverngame.anvil.injector.trans.server.PortalTransformer;
import com.wyverngame.anvil.injector.trans.server.ServerTransformer;
import com.wyverngame.anvil.injector.trans.server.SkillGainTransformer;
import com.wyverngame.anvil.injector.trans.server.SteamAuthCallbackTransformer;
import com.wyverngame.anvil.injector.trans.server.SteamAuthDuplicateTransformer;
import com.wyverngame.anvil.injector.trans.server.SteamAuthTransformer;
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

		Module common = Module.read(Paths.get("wurm/server/common.jar"));
		Module client = Module.read(Paths.get("wurm/client/WurmLauncher/client.jar"));
		Module server = Module.read(Paths.get("wurm/server/server.jar"));
		return new Injector(common, client, server);
	}

	private final ImmutableList<Transformer> commonTransformers = ImmutableList.of();
	private final ImmutableList<Transformer> clientTransformers = ImmutableList.of(
		new WurmClientBaseTransformer(),
		new MethodHookTransformer(
			"com/wurmonline/client/WurmClientBase",
			"runGameLoop()V",
			"com/wyverngame/anvil/api/client/event/TickEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"loginResult(Ljava/lang/String;Ljava/lang/String;FFFFIJJBBIBJF)V",
			"com/wyverngame/anvil/api/client/event/LoginEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"serverTime(JJ)V",
			"com/wyverngame/anvil/api/client/event/TimeEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"textMessage(Ljava/lang/String;FFFLjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/MessageEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"textMessage(Ljava/lang/String;Ljava/util/List;)V",
			"com/wyverngame/anvil/api/client/event/SegmentedMessageEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"teleport(ZFFFFIIZBI)V",
			"com/wyverngame/anvil/api/client/event/TeleportEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setSpeedModifier(F)V",
			"com/wyverngame/anvil/api/client/event/SpeedModifierUpdateEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addTheCreature(JLjava/lang/String;Ljava/lang/String;BFFFFBZBZIBBBJ)V",
			"com/wyverngame/anvil/api/client/event/AddCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"moveCreature(JBBSB)V",
			"com/wyverngame/anvil/api/client/event/MoveCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"deleteCreature(J)V",
			"com/wyverngame/anvil/api/client/event/RemoveCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playDeadThenReplaceWithCorpse(JJLjava/lang/String;Ljava/lang/String;BFFFFBLjava/lang/String;SF)V",
			"com/wyverngame/anvil/api/client/event/KillCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"startMoving()V",
			"com/wyverngame/anvil/api/client/event/StartMovingEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStripFar(SS[[SSS[[B)V",
			"com/wyverngame/anvil/api/client/event/TerrainFarUpdateEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStrip(SS[[ISS)V",
			"com/wyverngame/anvil/api/client/event/TerrainNearUpdateEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tileSomeStripCave(SSS[[IS)V",
			"com/wyverngame/anvil/api/client/event/TerrainCaveUpdateEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAvailableActions(BLjava/util/List;Ljava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/AvailableActionsEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAvailableSelectionBarActions(BLjava/util/List;)V",
			"com/wyverngame/anvil/api/client/event/AvailableSelectionBarActionsEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updateSelectionbarActions(J)V",
			"com/wyverngame/anvil/api/client/event/UpdateSelectionBarActionsEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setSelectedItemForKeeping(J)V",
			"com/wyverngame/anvil/api/client/event/UpdateSelectedItemForKeepingEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addItem(JLjava/lang/String;Ljava/lang/String;BFFFFBLjava/lang/String;SFJB)V",
			"com/wyverngame/anvil/api/client/event/AddGroundItemEvent",
			false),
		// TODO addProjectile event
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeItem(J)V",
			"com/wyverngame/anvil/api/client/event/RemoveGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeFromCorpsesToAddLaterListWithId(J)V",
			"com/wyverngame/anvil/api/client/event/RemoveCorpseEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setActionString(JLjava/lang/String;S)V",
			"com/wyverngame/anvil/api/client/event/UpdateActionEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setUpdatePlayerKingdom(B)V",
			"com/wyverngame/anvil/api/client/event/UpdateKingdomEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setTargetCreature(J)V",
			"com/wyverngame/anvil/api/client/event/UpdateTargetCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setHasTarget(JZ)V",
			"com/wyverngame/anvil/api/client/event/UpdateHasTargetCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureDamage(JF)V",
			"com/wyverngame/anvil/api/client/event/UpdateHealthTargetCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setItemState(JS)V",
			"com/wyverngame/anvil/api/client/event/UpdateGroundItemState",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"resetMovement()V",
			"com/wyverngame/anvil/api/client/event/ResetMovementEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"stopSoundEngine()V",
			"com/wyverngame/anvil/api/client/event/StopSoundEngineEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addEffect(JSFFFI)V",
			"com/wyverngame/anvil/api/client/event/AddEffectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addComplexEffect(JJSFFFIFFIBB)V",
			"com/wyverngame/anvil/api/client/event/AddComplexEffectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeEffect(J)V",
			"com/wyverngame/anvil/api/client/event/RemoveEffectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStamina(FF)V",
			"com/wyverngame/anvil/api/client/event/UpdateStaminaEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setHunger(FB)V",
			"com/wyverngame/anvil/api/client/event/UpdateHungerEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setThirst(F)V",
			"com/wyverngame/anvil/api/client/event/UpdateThirstEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addHouse(JLjava/lang/String;SSB)V",
			"com/wyverngame/anvil/api/client/event/AddHouseEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addBridge(JLjava/lang/String;SS)V",
			"com/wyverngame/anvil/api/client/event/AddBridgeEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeStructure(J)V",
			"com/wyverngame/anvil/api/client/event/RemoveStructureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"buildMark(JSS)V",
			"com/wyverngame/anvil/api/client/event/MarkStructureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWallOpen(JIIIBZB)V",
			"com/wyverngame/anvil/api/client/event/WallOpenEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWallPassable(JIIIBZB)V",
			"com/wyverngame/anvil/api/client/event/WallPassableEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"rename(IJLjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/RenameGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openTradeWindow(Ljava/lang/String;Z)V",
			"com/wyverngame/anvil/api/client/event/TradeOpenEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"closeTradeWindow()V",
			"com/wyverngame/anvil/api/client/event/TradeCloseEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setTradeAgree(Z)V",
			"com/wyverngame/anvil/api/client/event/TradeStatusEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"tradeChanged(I)V",
			"com/wyverngame/anvil/api/client/event/TradeChangedEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"renameItem(JLjava/lang/String;Ljava/lang/String;BLjava/lang/String;SB)V",
			"com/wyverngame/anvil/api/client/event/UpdateGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"changeModelName(JLjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/UpdateGroundItemModelEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addFence(IIIBBFFFFBLjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/AddFenceEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeFence(IIIBB)V",
			"com/wyverngame/anvil/api/client/event/RemoveFenceEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openWall(IIIBZZB)V",
			"com/wyverngame/anvil/api/client/event/FenceOpenEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openFence(IIIBZZZB)V",
			"com/wyverngame/anvil/api/client/event/FenceOpenEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playSound(Ljava/lang/String;FFFFFFZ)V",
			"com/wyverngame/anvil/api/client/event/PlaySoundEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playMusic(Ljava/lang/String;FFFFFF)V",
			"com/wyverngame/anvil/api/client/event/PlayMusicEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStatusString(Ljava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/UpdateStatusEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"joinGroup(Ljava/lang/String;Ljava/lang/String;J)V",
			"com/wyverngame/anvil/api/client/event/JoinGroupEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"partGroup(Ljava/lang/String;Ljava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/LeaveGroupEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureAttitude(JI)V",
			"com/wyverngame/anvil/api/client/event/UpdateCreatureAttitudeEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToAchievementList(ILjava/lang/String;Ljava/lang/String;BJI)V",
			"com/wyverngame/anvil/api/client/event/ListAchievementEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addAchievement(ZZILjava/lang/String;Ljava/lang/String;BJI)V",
			"com/wyverngame/anvil/api/client/event/CompleteAchievementEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToPersonalGoalList(ILjava/lang/String;Ljava/lang/String;BZ)V",
			"com/wyverngame/anvil/api/client/event/ListPersonalGoalEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updatePersonalGoalList(IZ)V",
			"com/wyverngame/anvil/api/client/event/UpdatePersonalGoalEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addToKingdomList(BLjava/lang/String;Ljava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/AddKingdomEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWeather(FFFFFF)V",
			"com/wyverngame/anvil/api/client/event/UpdateWeatherEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"reconnect()V",
			"com/wyverngame/anvil/api/client/event/ReconnectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setClimbing(Z)V",
			"com/wyverngame/anvil/api/client/event/UpdateClimbingEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStunned(Z)V",
			"com/wyverngame/anvil/api/client/event/UpdateStunnedEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setToggle(II)V",
			"com/wyverngame/anvil/api/client/event/ToggleEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"toggleArcheryMode(I)V",
			"com/wyverngame/anvil/api/client/event/ToggleArcheryModeEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDead(Z)V",
			"com/wyverngame/anvil/api/client/event/UpdateDeadEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCreatureLayer(JI)V",
			"com/wyverngame/anvil/api/client/event/UpdateCreatureLayerEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"toggleClientFeature(II)V",
			"com/wyverngame/anvil/api/client/event/ToggleFeatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"attachEffect(JBBBBB)V",
			"com/wyverngame/anvil/api/client/event/AttachEffectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"unattachEffect(JB)V",
			"com/wyverngame/anvil/api/client/event/DetachEffectEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setEquipment(JILjava/lang/String;B)V",
			"com/wyverngame/anvil/api/client/event/UpdateEquipmentEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDamageState(JB)V",
			"com/wyverngame/anvil/api/client/event/UpdateStructureDamageEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setDamageState(JJB)V",
			"com/wyverngame/anvil/api/client/event/UpdateStructurePartDamageEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"useItem(JLjava/lang/String;B)V",
			"com/wyverngame/anvil/api/client/event/UseItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"repaint(JFFFFI)V",
			"com/wyverngame/anvil/api/client/event/UpdatePaintEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"resize(JFFF)V",
			"com/wyverngame/anvil/api/client/event/UpdateSizeEvent",
			false),
		new MethodHookTransformer(
		"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setBridgeId(JJ)V",
			"com/wyverngame/anvil/api/client/event/UpdateBridgeIdEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"attachCreature(JJFFFB)V",
			"com/wyverngame/anvil/api/client/event/AttachCreatureEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setVehicleController(JJFFFFFFFB)V",
			"com/wyverngame/anvil/api/client/event/AttachControllerEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playAnimation(JLjava/lang/String;ZZ)V",
			"com/wyverngame/anvil/api/client/event/PlayAnimationEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"playAnimationWithTargetItem(JLjava/lang/String;ZZJ)V",
			"com/wyverngame/anvil/api/client/event/PlayAnimationWithItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setStance(JB)V",
			"com/wyverngame/anvil/api/client/event/UpdateStanceEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setGroundOffset(IZ)V",
			"com/wyverngame/anvil/api/client/event/UpdateGroundOffsetEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setWindImpact(B)V",
			"com/wyverngame/anvil/api/client/event/UpdateWindImpactEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setMountSpeed(B)V",
			"com/wyverngame/anvil/api/client/event/UpdateMountSpeedEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setNewFace(JJ)V",
			"com/wyverngame/anvil/api/client/event/UpdateFaceEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"openPortalMap()V",
			"com/wyverngame/anvil/api/client/event/OpenPortalMapEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCurrentServerInformation(ZILjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/UpdateCurrentServerInformationEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addMapAnnotations(JBLjava/lang/String;IILjava/lang/String;B)V",
			"com/wyverngame/anvil/api/client/event/AddMapAnnotationEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeMapAnnotations(JBLjava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/RemoveMapAnnotationEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"clearMapAnnotationsByType(B)V",
			"com/wyverngame/anvil/api/client/event/ClearMapAnnotationsEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setIsUnfinishedViewForCreationWindow(Z)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowUpdateUnfinishedEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"clearItemListsForCreationWindow()V",
			"com/wyverngame/anvil/api/client/event/CreationWindowClearItemListEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setCurrentCategoryForCreationWindow(Ljava/lang/String;)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowUpdateCategoryEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationGroundItem(Ljava/lang/String;JFFFSLcom/wurmonline/client/renderer/gui/CreationFrame$ItemType;)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowAddGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"replaceCreationGroundItem(JLjava/lang/String;JFFFSLcom/wurmonline/client/renderer/gui/CreationFrame$ItemType;)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowReplaceGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"setActionResult(Z)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowActionResultEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"updateCreationGroundItem(JFFF)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowUpdateGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"removeCreationGroundItem(J)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowRemoveGroundItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationCategoryLists(Ljava/util/Map;S)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowAddCategoryListEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addItemToCreationList(Lcom/wurmonline/client/renderer/gui/CreationListItem;)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowAddItemToListEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addCreationItem(Ljava/lang/String;SSS)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowAddItemEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"creationItemTreeSent()V",
			"com/wyverngame/anvil/api/client/event/CreationWindowItemTreeSentEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"sortCreationItemList()V",
			"com/wyverngame/anvil/api/client/event/CreationWindowSortItemListEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"requestStructureCreationList(J)V",
			"com/wyverngame/anvil/api/client/event/CreationWindowRequestStructureListEvent",
			false),
		new MethodHookTransformer(
			"com/wurmonline/client/comm/ServerConnectionListenerClass",
			"addPlonk(S)V",
			"com/wyverngame/anvil/api/client/event/AddPlonkEvent",
			false)
	);

	private final ImmutableList<Transformer> serverTransformers = ImmutableList.of(
		new ServerTransformer(),
		new FreedomAltarTransformer(),
		new ChaosTransformer(),
		new SkillGainTransformer(),
		new ActionEntryPriestRestrictionTransformer(),
		new ActionEntryTypePriestRestrictionTransformer(),
		new ActionFaithfulPriestRestrictionTransformer(),
		new FarmingWeedsTransformer(),
		new FarmingWeedsTilePollTransformer(),
		new FarmingWeedsCropPollTransformer(),
		new PortalTransformer(),
		new SteamAuthTransformer(),
		new SteamAuthCallbackTransformer(),
		new SteamAuthDuplicateTransformer(),
		new CreateCreatureTransformer(),
		new ChatTransformer(),
		new ChangeKingdomTransformer(),
		new ChaosNotAllowedTransformer()
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
