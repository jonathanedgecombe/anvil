package com.wurmonline.client.comm;

import java.util.List;
import java.util.Map;

import com.wurmonline.client.WurmClientBase;
import com.wurmonline.client.comm.ServerConnectionListenerClass;
import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.cell.CreatureCellRenderable;
import com.wurmonline.client.renderer.gui.CreationListItem;
import com.wurmonline.client.renderer.gui.CreationFrame.ItemType;
import com.wurmonline.client.renderer.structures.StructureData;
import com.wurmonline.shared.constants.PlayerAction;
import com.wurmonline.shared.constants.TicketGroup;
import com.wurmonline.shared.constants.BridgeConstants.BridgeMaterial;
import com.wurmonline.shared.constants.BridgeConstants.BridgeState;
import com.wurmonline.shared.constants.BridgeConstants.BridgeType;
import com.wurmonline.shared.constants.StructureConstants.FloorMaterial;
import com.wurmonline.shared.constants.StructureConstants.FloorState;
import com.wurmonline.shared.constants.StructureConstants.FloorType;
import com.wurmonline.shared.util.MulticolorLineSegment;
import com.wyverngame.anvil.api.Anvil;
import com.wyverngame.anvil.api.event.StaminaUpdateEvent;

public final class ServerConnectionListenerProxy extends ServerConnectionListenerClass {
	public ServerConnectionListenerProxy(WurmClientBase client, World world) {
		super(client, world);
	}

	@Override
	public void loginResult(String message, String model, float x, float y, float h, float yRot, int layer, long wurmTimeSeconds, long serverTimeMillis, byte commandType, byte kingdomId, int counter, byte bloodKingdom, long bridgeId, float groundOffset) {
		super.loginResult(message, model, x, y, h, yRot, layer, wurmTimeSeconds, serverTimeMillis, commandType, kingdomId, counter, bloodKingdom, bridgeId, groundOffset);
	}

	@Override
	void serverTime(long wurmTimeSeconds, long serverTimeMillis) {
		super.serverTime(wurmTimeSeconds, serverTimeMillis);
	}

	@Override
	public void textMessage(String title, float r, float g, float b, String message) {
		super.textMessage(title, r, g, b, message);
	}

	@Override
	void textMessage(String title, List<MulticolorLineSegment> segments) {
		super.textMessage(title, segments);
	}

	@Override
	void teleport(boolean local, float x, float y, float h, float yRot, int layer, int count, boolean disembark, byte commandType, int teleportCounter) {
		super.teleport(local, x, y, h, yRot, layer, count, disembark, commandType, teleportCounter);
	}

	@Override
	void setSpeedModifier(float speed) {
		super.setSpeedModifier(speed);
	}

	@Override
	public void addTheCreature(long id, String modelName, String lName, byte materialId, float x, float y, float h, float rot, byte layer, boolean floating, byte type, boolean solid, int soundSourceID, byte kingdomId, byte bloodKingdom, byte rarity, long bridgeId) {
		super.addTheCreature(id, modelName, lName, materialId, x, y, h, rot, layer, floating, type, solid, soundSourceID, kingdomId, bloodKingdom, rarity, bridgeId);
	}

	@Override
	void moveCreature(long id, byte x, byte y, short h, byte rot) {
		super.moveCreature(id, x, y, h, rot);
	}

	@Override
	void deleteCreature(long id) {
		super.deleteCreature(id);
	}

	@Override
	void playDeadThenReplaceWithCorpse(long creatureId, long corpseId, String modelName, String lName, byte materialId,
			float x, float y, float h, float rot, byte layer, String description, short iconId, float size) {
		super.playDeadThenReplaceWithCorpse(creatureId, corpseId, modelName, lName, materialId, x, y, h, rot, layer,
				description, iconId, size);
	}

	@Override
	void startMoving() {
		super.startMoving();
	}

	@Override
	void tileSomeStripFar(short width, short height, short[][] tileData, short xStart, short yStart, byte[][] types) {
		super.tileSomeStripFar(width, height, tileData, xStart, yStart, types);
	}

	@Override
	public void tileSomeStrip(short width, short height, int[][] tileData, short xStart, short yStart) {
		super.tileSomeStrip(width, height, tileData, xStart, yStart);
	}

	@Override
	void tileSomeStripCave(short xStart, short yStart, short width, int[][] tiles, short height) {
		super.tileSomeStripCave(xStart, yStart, width, tiles, height);
	}

	@Override
	void addAvailableActions(byte requestId, List<PlayerAction> actionList, String helpTopic) {
		super.addAvailableActions(requestId, actionList, helpTopic);
	}

	@Override
	void addItem(long id, String modelName, String lName, byte materialId, float x, float y, float h, float rot,
			byte layer, String description, short iconId, float scale, long bridgeId, byte rarity) {
		super.addItem(id, modelName, lName, materialId, x, y, h, rot, layer, description, iconId, scale, bridgeId, rarity);
	}

	@Override
	void addProjectile(long id, byte type, String modelName, String lName, byte materialId, float startX, float startY,
			float startH, float rot, byte layer, float endX, float endY, float endH, long sourceId, long targetId,
			float secondsInAir, float realSecondsInAir) {
		super.addProjectile(id, type, modelName, lName, materialId, startX, startY, startH, rot, layer, endX, endY, endH,
				sourceId, targetId, secondsInAir, realSecondsInAir);
	}

	@Override
	void removeItem(long id) {
		super.removeItem(id);
	}

	@Override
	void setActionString(long creatureID, String actionString, short timeleft) {
		super.setActionString(creatureID, actionString, timeleft);
	}

	@Override
	void setUpdatePlayerKingdom(byte kingdomId) {
		super.setUpdatePlayerKingdom(kingdomId);
	}

	@Override
	void setTargetCreature(long creatureID) {
		super.setTargetCreature(creatureID);
	}

	@Override
	public void setHasTarget(long creatureID, boolean hasTarget) {
		super.setHasTarget(creatureID, hasTarget);
	}

	@Override
	void setCreatureDamage(long creatureID, float damage) {
		super.setCreatureDamage(creatureID, damage);
	}

	@Override
	void setItemState(long itemId, short newStateId) {
		super.setItemState(itemId, newStateId);
	}

	@Override
	void addEffect(long id, short type, float x, float y, float h, int layer) {
		super.addEffect(id, type, x, y, h, layer);
	}

	@Override
	void addComplexEffect(long id, long target, short type, float x, float y, float h, int layer, float radiusMeters,
			float lengthMeters, int direction, byte kingdom, byte entityId) {
		super.addComplexEffect(id, target, type, x, y, h, layer, radiusMeters, lengthMeters, direction, kingdom, entityId);
	}

	@Override
	void removeEffect(long id) {
		super.removeEffect(id);
	}

	@Override
	void setStamina(float stamina, float damage) {
		Anvil.handleEvent(new StaminaUpdateEvent(stamina, damage));
		super.setStamina(stamina, damage);
	}

	@Override
	void setHunger(float hunger, byte nutritionLevel) {
		super.setHunger(hunger, nutritionLevel);
	}

	@Override
	void setThirst(float thirst) {
		super.setThirst(thirst);
	}

	@Override
	void setWeight(float weight) {
		super.setWeight(weight);
	}

	@Override
	public void addHouse(long id, String name, short xCenter, short yCenter, byte layer) {
		super.addHouse(id, name, xCenter, yCenter, layer);
	}

	@Override
	public void addBridge(long id, String name, short xCenter, short yCenter) {
		super.addBridge(id, name, xCenter, yCenter);
	}

	@Override
	void removeHouse(long id) {
		super.removeHouse(id);
	}

	@Override
	void removeStructure(long id) {
		super.removeStructure(id);
	}

	@Override
	void buildMark(long id, short x, short y) {
		super.buildMark(id, x, y);
	}

	@Override
	public void addWall(long houseId, int x, int y, int heightOffset, byte dir, byte type, String material, float r,
			float g, float b, float a, byte layer, String doorName, boolean overrideReverse) {
		super.addWall(houseId, x, y, heightOffset, dir, type, material, r, g, b, a, layer, doorName, overrideReverse);
	}

	@Override
	void setWallOpen(long houseId, int x, int y, int heightOffset, byte dir, boolean open, byte layer) {
		super.setWallOpen(houseId, x, y, heightOffset, dir, open, layer);
	}

	@Override
	public void setWallPassable(long houseId, int x, int y, int heightOffset, byte dir, boolean passable, byte layer) {
		super.setWallPassable(houseId, x, y, heightOffset, dir, passable, layer);
	}

	@Override
	void rename(int type, long id, String name) {
		super.rename(type, id, name);
	}

	@Override
	void openTradeWindow(String opponent, boolean playerInitiated) {
		super.openTradeWindow(opponent, playerInitiated);
	}

	@Override
	void closeTradeWindow() {
		super.closeTradeWindow();
	}

	@Override
	void setTradeAgree(boolean isAgree) {
		super.setTradeAgree(isAgree);
	}

	@Override
	void tradeChanged(int changeId) {
		super.tradeChanged(changeId);
	}

	@Override
	void renameItem(long id, String name, String modelName, byte materialId, String lDesc, short iconId, byte rarity) {
		super.renameItem(id, name, modelName, materialId, lDesc, iconId, rarity);
	}

	@Override
	void changeModelName(long id, String modelName) {
		super.changeModelName(id, modelName);
	}

	@Override
	void addFence(int x, int y, int heightOffset, byte dir, byte type, float r, float g, float b, float a, byte layer,
			String name) {
		super.addFence(x, y, heightOffset, dir, type, r, g, b, a, layer, name);
	}

	@Override
	void removeFence(int x, int y, int heightOffset, byte dir, byte layer) {
		super.removeFence(x, y, heightOffset, dir, layer);
	}

	@Override
	void openWall(int x, int y, int heightOffset, byte dir, boolean open, boolean mayPass, byte layer) {
		super.openWall(x, y, heightOffset, dir, open, mayPass, layer);
	}

	@Override
	void openFence(int x, int y, int heightOffset, byte dir, boolean open, boolean changePassable, boolean mayPass,
			byte layer) {
		super.openFence(x, y, heightOffset, dir, open, changePassable, mayPass, layer);
	}

	@Override
	void playSound(String soundName, float x, float y, float h, float pitch, float volume, float prio,
			boolean isPersonal) {
		super.playSound(soundName, x, y, h, pitch, volume, prio, isPersonal);
	}

	@Override
	void playMusic(String soundName, float x, float y, float h, float pitch, float volume, float prio) {
		super.playMusic(soundName, x, y, h, pitch, volume, prio);
	}

	@Override
	void setStatusString(String status) {
		super.setStatusString(status);
	}

	@Override
	void joinGroup(String group, String name, long id) {
		super.joinGroup(group, name, id);
	}

	@Override
	void partGroup(String group, String name) {
		super.partGroup(group, name);
	}

	@Override
	void setCreatureAttitude(long id, int attitude) {
		super.setCreatureAttitude(id, attitude);
	}

	@Override
	void setWeather(float cloudiness, float fog, float rain, float windRot, float windPower, float temp) {
		super.setWeather(cloudiness, fog, rain, windRot, windPower, temp);
	}

	@Override
	public void reconnect() {
		super.reconnect();
	}

	@Override
	void setClimbing(boolean climbing) {
		super.setClimbing(climbing);
	}

	@Override
	void setStunned(boolean stunned) {
		super.setStunned(stunned);
	}

	@Override
	void setToggle(int toggle, int value) {
		super.setToggle(toggle, value);
	}

	@Override
	void toggleArcheryMode(int value) {
		super.toggleArcheryMode(value);
	}

	@Override
	void setDead(boolean isDead) {
		super.setDead(isDead);
	}

	@Override
	void setCreatureLayer(long id, int layer) {
		super.setCreatureLayer(id, layer);
	}

	@Override
	void toggleClientFeature(int type, int value) {
		super.toggleClientFeature(type, value);
	}

	@Override
	public void attachEffect(long wurmId, byte effectType, byte data0, byte data1, byte data2, byte data3) {
		super.attachEffect(wurmId, effectType, data0, data1, data2, data3);
	}

	@Override
	public void unattachEffect(long wurmId, byte effectType) {
		super.unattachEffect(wurmId, effectType);
	}

	@Override
	void setEquipment(long wurmId, int slot, String model, byte rarity) {
		super.setEquipment(wurmId, slot, model, rarity);
	}

	@Override
	void setDamageState(long wurmId, byte damage) {
		super.setDamageState(wurmId, damage);
	}

	@Override
	void setDamageState(long houseId, long wallId, byte damage) {
		super.setDamageState(houseId, wallId, damage);
	}

	@Override
	void useItem(long wurmId, String model, byte rarity) {
		super.useItem(wurmId, model, rarity);
	}

	@Override
	void repaint(long id, float r, float g, float b, float a, int paintType) {
		super.repaint(id, r, g, b, a, paintType);
	}

	@Override
	void resize(long id, float xScale, float hScale, float yScale) {
		super.resize(id, xScale, hScale, yScale);
	}

	@Override
	public void setBridgeId(long wurmId, long bridgeId) {
		super.setBridgeId(wurmId, bridgeId);
	}

	@Override
	public void attachCreature(long passenger, long carrier, float xOffset, float yOffset, float hOffset,
			byte placeId) {
		super.attachCreature(passenger, carrier, xOffset, yOffset, hOffset, placeId);
	}

	@Override
	void setVehicleController(long controller, long carrier, float xOffset, float yOffset, float hOffset,
			float maxDepth, float maxHeight, float maxHeightDiff, float vehicleRotation, byte placeId) {
		super.setVehicleController(controller, carrier, xOffset, yOffset, hOffset, maxDepth, maxHeight, maxHeightDiff,
				vehicleRotation, placeId);
	}

	@Override
	public void playAnimation(long id, String animationName, boolean looping, boolean freeze) {
		super.playAnimation(id, animationName, looping, freeze);
	}

	@Override
	void playAnimationWithTargetItem(long id, String animationName, boolean looping, boolean freeze,
			long targetItemId) {
		super.playAnimationWithTargetItem(id, animationName, looping, freeze, targetItemId);
	}

	@Override
	public void setStance(long creature, byte stance) {
		super.setStance(creature, stance);
	}

	@Override
	void setWindImpact(byte windImpact) {
		super.setWindImpact(windImpact);
	}

	@Override
	void setMountSpeed(byte mountSpeed) {
		super.setMountSpeed(mountSpeed);
	}

	@Override
	public void setNewFace(long target, long newFace) {
		super.setNewFace(target, newFace);
	}

	@Override
	void openPortalMap() {
		super.openPortalMap();
	}

	@Override
	void setCurrentServerInformation(boolean isEpic, int cluster, String serverName) {
		super.setCurrentServerInformation(isEpic, cluster, serverName);
	}

	@Override
	void setIsUnfinishedViewForCreationWindow(boolean isUnfinishedView) {
		super.setIsUnfinishedViewForCreationWindow(isUnfinishedView);
	}

	@Override
	void clearItemListsForCreationWindow() {
		super.clearItemListsForCreationWindow();
	}

	@Override
	void setCurrentCategoryForCreationWindow(String categoryName) {
		super.setCurrentCategoryForCreationWindow(categoryName);
	}

	@Override
	void addCreationGroundItem(String name, long id, float quality, float damage, float weight, short iconId,
			ItemType itemType) {
		super.addCreationGroundItem(name, id, quality, damage, weight, iconId, itemType);
	}

	@Override
	void replaceCreationGroundItem(long replaceWithId, String name, long id, float quality, float damage, float weight,
			short iconId, ItemType itemType) {
		super.replaceCreationGroundItem(replaceWithId, name, id, quality, damage, weight, iconId, itemType);
	}

	@Override
	void setActionResult(boolean isFail) {
		super.setActionResult(isFail);
	}

	@Override
	void updateCreationGroundItem(long id, float ql, float weight, float damage) {
		super.updateCreationGroundItem(id, ql, weight, damage);
	}

	@Override
	void removeCreationGroundItem(long id) {
		super.removeCreationGroundItem(id);
	}

	@Override
	void addCreationCategoryLists(Map<Short, CreationListItem> categoryList, short numberofItems) {
		super.addCreationCategoryLists(categoryList, numberofItems);
	}

	@Override
	void addItemToCreationList(CreationListItem item) {
		super.addItemToCreationList(item);
	}

	@Override
	void addCreationItem(String itemName, short procent, short actionId, short iconId) {
		super.addCreationItem(itemName, procent, actionId, iconId);
	}

	@Override
	void creationItemTreeSent() {
		super.creationItemTreeSent();
	}

	@Override
	void sortCreationItemList() {
		super.sortCreationItemList();
	}

	@Override
	void requestStructureCreationList(long fenceId) {
		super.requestStructureCreationList(fenceId);
	}

	@Override
	void addPlonk(short id) {
		super.addPlonk(id);
	}

	@Override
	void addLookingForVillageItem(int id, String villageName, String description, String creator, boolean isOnline) {
		super.addLookingForVillageItem(id, villageName, description, creator, isOnline);
	}

	@Override
	void setRotation(long itemId, float rotation) {
		super.setRotation(itemId, rotation);
	}

	@Override
	void setAvailableServer(byte direction, boolean avail) {
		super.setAvailableServer(direction, avail);
	}

	@Override
	void addFightMove(short move, String moveName) {
		super.addFightMove(move, moveName);
	}

	@Override
	void toggleShield(boolean on) {
		super.toggleShield(on);
	}

	@Override
	void setFightStyle(byte style) {
		super.setFightStyle(style);
	}

	@Override
	void addSpellEffect(long id, String lName, byte type, byte influence, int duration, float power) {
		super.addSpellEffect(id, lName, type, influence, duration, power);
	}

	@Override
	void addStatusEffect(long id, int typeId, int duration) {
		super.addStatusEffect(id, typeId, duration);
	}

	@Override
	void removeStatusEffect(long id) {
		super.removeStatusEffect(id);
	}

	@Override
	void addTileEffect(int tilex, int tiley, int layer, byte type, boolean loop, int floorLevel, int heightOffset) {
		super.addTileEffect(tilex, tiley, layer, type, loop, floorLevel, heightOffset);
	}

	@Override
	void removeTileEffect(int tilex, int tiley, int layer) {
		super.removeTileEffect(tilex, tiley, layer);
	}

	@Override
	void removeSpellEffect(long id) {
		super.removeSpellEffect(id);
	}

	@Override
	void setSkill(long id, long parentId, String lName, float value, float maxValue, byte affinities) {
		super.setSkill(id, parentId, lName, value, maxValue, affinities);
	}

	@Override
	void updateSkill(long id, float newValue, byte affinities) {
		super.updateSkill(id, newValue, affinities);
	}

	@Override
	void openInventoryWindow(long inventoryWindowId, String title) {
		super.openInventoryWindow(inventoryWindowId, title);
	}

	@Override
	void openInventoryContainerWindow(long containerId) {
		super.openInventoryContainerWindow(containerId);
	}

	@Override
	void openaManageRecruitmentAdWindow(String inputString) {
		super.openaManageRecruitmentAdWindow(inputString);
	}

	@Override
	void openWindofOfType(byte windowType) {
		super.openWindofOfType(windowType);
	}

	@Override
	void closeInventoryWindow(long inventoryWindowId) {
		super.closeInventoryWindow(inventoryWindowId);
	}

	@Override
	void addFakeInventoryItem(long inventoryWindowId, long parentId) {
		super.addFakeInventoryItem(inventoryWindowId, parentId);
	}

	@Override
	void removeFakeInventoryItem(long inventoryWindowId, long parentId) {
		super.removeFakeInventoryItem(inventoryWindowId, parentId);
	}

	@Override
	void addInventoryItem(long inventoryWindowId, long id, long parentId, short iconId, String baseName,
			String customName, byte materialId, float quality, float damage, float weight, float r, float g, float b,
			int price, short impIconId, short typeBits, byte temperature, byte rarity, byte auxData) {
		super.addInventoryItem(inventoryWindowId, id, parentId, iconId, baseName, customName, materialId, quality, damage,
				weight, r, g, b, price, impIconId, typeBits, temperature, rarity, auxData);
	}

	@Override
	void removeInventoryItem(long inventoryWindowId, long id) {
		super.removeInventoryItem(inventoryWindowId, id);
	}

	@Override
	void updateInventoryItem(long inventoryWindowId, long id, long parentId, String baseName, String customName,
			float quality, float damage, float weight, float r, float g, float b, int price, short impIconId,
			byte temperature, byte rarity) {
		super.updateInventoryItem(inventoryWindowId, id, parentId, baseName, customName, quality, damage, weight, r, g, b,
				price, impIconId, temperature, rarity);
	}

	@Override
	void updateInventoryItemData(long inventoryWindowId, long id, float ql, float damage, float weight, short impIcon) {
		super.updateInventoryItemData(inventoryWindowId, id, ql, damage, weight, impIcon);
	}

	@Override
	void updateInventoryItemParent(long inventoryWindowId, long id, long parentId) {
		super.updateInventoryItemParent(inventoryWindowId, id, parentId);
	}

	@Override
	void updateInventoryItemName(long inventoryWindowId, long id, String name) {
		super.updateInventoryItemName(inventoryWindowId, id, name);
	}

	@Override
	void updateInventoryItemCustomName(long inventoryWindowId, long id, String customName) {
		super.updateInventoryItemCustomName(inventoryWindowId, id, customName);
	}

	@Override
	void updateInventoryItemColor(long inventoryWindowId, long id, float r, float g, float b) {
		super.updateInventoryItemColor(inventoryWindowId, id, r, g, b);
	}

	@Override
	void updateInventoryItemTemperature(long inventoryWindowId, long id, byte temps) {
		super.updateInventoryItemTemperature(inventoryWindowId, id, temps);
	}

	@Override
	void updateInventoryItemType(long inventoryWindowId, long id, short type, String desc) {
		super.updateInventoryItemType(inventoryWindowId, id, type, desc);
	}

	@Override
	void showBMLForm(short id, String title, int width, int height, boolean closeable, boolean resizeable, float r,
			float g, float b, String bml) {
		super.showBMLForm(id, title, width, height, closeable, resizeable, r, g, b, bml);
	}

	@Override
	void setFightMoveOptions(byte[] fightOptions, short tenthsOfSeconds) {
		super.setFightMoveOptions(fightOptions, tenthsOfSeconds);
	}

	@Override
	void setFightPosition(float distance, float footing, byte playerStance) {
		super.setFightPosition(distance, footing, playerStance);
	}

	@Override
	void addMissionState(long wurmid, String name, String creator, String description, long startDate, long endDate,
			long expireDate, float state, boolean restartable) {
		super.addMissionState(wurmid, name, creator, description, startDate, endDate, expireDate, state, restartable);
	}

	@Override
	void removeMissionState(long wurmid) {
		super.removeMissionState(wurmid);
	}

	@Override
	public void addTemplateItem(int itemId, String modelName) {
		super.addTemplateItem(itemId, modelName);
	}

	@Override
	public void addClothing(long wurmId, int itemId, byte bodyPart, boolean hasColour, float red, float green,
			float blue, byte material, byte rarity) {
		super.addClothing(wurmId, itemId, bodyPart, hasColour, red, green, blue, material, rarity);
	}

	@Override
	public void removeClothing(long wurmId, byte bodyPart) {
		super.removeClothing(wurmId, bodyPart);
	}

	@Override
	void addMountItem(long wurmId, int itemId, byte material) {
		super.addMountItem(wurmId, itemId, material);
	}

	@Override
	void removeMountItem(long wurmId, int itemId) {
		super.removeMountItem(wurmId, itemId);
	}

	@Override
	void addMountSaddle(long wurmId) {
		super.addMountSaddle(wurmId);
	}

	@Override
	void removeMountSaddle(long wurmId) {
		super.removeMountSaddle(wurmId);
	}

	@Override
	void addBarding(long wurmId, byte material) {
		super.addBarding(wurmId, material);
	}

	@Override
	void removeBarding(long wurmId) {
		super.removeBarding(wurmId);
	}

	@Override
	void focusLevelChanged(byte fl, long targetId, String msg) {
		super.focusLevelChanged(fl, targetId, msg);
	}

	@Override
	public void addRoof(long houseId, int x, int y, short heightOffset, FloorType roofType, FloorMaterial roofMaterial,
			FloorState roofState, byte layer, float dir, byte specialRoofId) {
		super.addRoof(houseId, x, y, heightOffset, roofType, roofMaterial, roofState, layer, dir, specialRoofId);
	}

	@Override
	void removeRoof(long houseId, int x, int y, short heightOffset, byte layer) {
		super.removeRoof(houseId, x, y, heightOffset, layer);
	}

	@Override
	public void addFloor(long houseId, int x, int y, short heightOffset, FloorType floorType,
			FloorMaterial floorMaterial, FloorState floorState, byte layer, byte dir) {
		super.addFloor(houseId, x, y, heightOffset, floorType, floorMaterial, floorState, layer, dir);
	}

	@Override
	void removeFloor(long houseId, int x, int y, short heightOffset, byte layer) {
		super.removeFloor(houseId, x, y, heightOffset, layer);
	}

	@Override
	public void addBridgePart(long structureId, int x, int y, short heightOffset, BridgeType bridgeType,
			BridgeMaterial bridgeMaterial, BridgeState bridgeState, byte dir, byte slope) {
		super.addBridgePart(structureId, x, y, heightOffset, bridgeType, bridgeMaterial, bridgeState, dir, slope);
	}

	@Override
	void removeBridgePart(long structureId, int x, int y, short heightOffset) {
		super.removeBridgePart(structureId, x, y, heightOffset);
	}

	@Override
	void updatePlayerTitles(String normalTitle, String medTitle) {
		super.updatePlayerTitles(normalTitle, medTitle);
	}

	@Override
	void updateSleepBonusInfo(byte status, int secondsLeft) {
		super.updateSleepBonusInfo(status, secondsLeft);
	}

	@Override
	void updateTargetStatus(long targetId, byte type, float status) {
		super.updateTargetStatus(targetId, type, status);
	}

	@Override
	void openFaceCustomization(long oldFaceId, long mirrorItemId) {
		super.openFaceCustomization(oldFaceId, mirrorItemId);
	}

	@Override
	void drumrollStarted() {
		super.drumrollStarted();
	}

	@Override
	void clearFriendsList() {
		super.clearFriendsList();
	}

	@Override
	void updateFriendList(String playername, int playerstatus, long lastseen, String playerserver) {
		super.updateFriendList(playername, playerstatus, lastseen, playerserver);
	}

	@Override
	void clearSupportTickets() {
		super.clearSupportTickets();
	}

	@Override
	void updateSupportTicket(long ticketNo, TicketGroup ticketGroup, String description, String message,
			byte colourCode) {
		super.updateSupportTicket(ticketNo, ticketGroup, description, message, colourCode);
	}

	@Override
	void updateSupportTicketData(long ticketNo, int actionNo, String message, String description) {
		super.updateSupportTicketData(ticketNo, actionNo, message, description);
	}

	@Override
	void removeSupportTicket(long ticketNo) {
		super.removeSupportTicket(ticketNo);
	}

	@Override
	public void closePlanWindow() {
		super.closePlanWindow();
	}

	@Override
	public void openPlanWindow(String planName, byte planDirection, byte planLength, byte planWidth, int startX,
			int startY, int startH, int endX, int endY, int endH, byte planMaterial, String planCustom) {
		super.openPlanWindow(planName, planDirection, planLength, planWidth, startX, startY, startH, endX, endY, endH,
				planMaterial, planCustom);
	}

	@Override
	void setupPermissionsManagement(int qId, String objectType, String objectName, String ownerName, boolean canGoBack,
			boolean isOwner, boolean canChangeOwner, boolean isManaged, boolean managedEnabled, String mayManageText,
			String mayManageHover, String warningText, String messageOnTick, String questionOnTick,
			String messageUnTick, String questionUnTick, String allowAlliesText, String allowCitizensText,
			String allowKingdomText, String allowEveryoneText, String[] header1, String[] header2, String[] hover,
			String[] trusted, long[] tIds, String[] friends, long[] fIds, String mySettlement, String[] citizens,
			long[] cIds, String[] permitted, long[] pIds, boolean[][] allowed) {
		super.setupPermissionsManagement(qId, objectType, objectName, ownerName, canGoBack, isOwner, canChangeOwner, isManaged,
				managedEnabled, mayManageText, mayManageHover, warningText, messageOnTick, questionOnTick, messageUnTick,
				questionUnTick, allowAlliesText, allowCitizensText, allowKingdomText, allowEveryoneText, header1, header2,
				hover, trusted, tIds, friends, fIds, mySettlement, citizens, cIds, permitted, pIds, allowed);
	}

	@Override
	void permissionsManuallyAddReply(String name, long id) {
		super.permissionsManuallyAddReply(name, id);
	}

	@Override
	void permissionsApplyChangeFail(int qId, String errorMessage) {
		super.permissionsApplyChangeFail(qId, errorMessage);
	}

	@Override
	void permissionsHide() {
		super.permissionsHide();
	}

	@Override
	public Map<Long, StructureData> getStructures() {
		return super.getStructures();
	}

	@Override
	public Map<Long, CreatureCellRenderable> getCreatures() {
		return super.getCreatures();
	}
}
