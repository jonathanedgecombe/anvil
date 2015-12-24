package com.wyverngame.anvil.api.id;

public enum Type {
	PLAYER(0),
	CREATURE(1),
	ITEM(2),
	TILE(3),
	STRUCTURE(4),
	WALL(5),
	TEMP_ITEM(6),
	FENCE(7),
	WOUND(8),
	CREATURE_SKILL(9),
	PLAYER_SKILL(10),
	TEMPLATE_SKILL(11),
	TILE_BORDER(12),
	BANK(13),
	PLANET(14),
	SPELL(15),
	PLAN(16),
	CAVE_TILE(17),
	SKILL_ID(18),
	BODY_ID(19),
	COIN_ID(20),
	WC_COMMAND(21),
	MISSION(22),
	FLOOR(23),
	ILLUSION(24),
	SUPPORT_TICKET(25),
	POI_ID(26),
	TILE_CORNER(27),
	BRIDGE(28),
	REDEEM_ID(29),
	MENU(30);

	private static final Type[] TYPES = new Type[256];

	static {
		for (Type type : values()) {
			TYPES[type.getId()] = type;
		}
	}

	private final int id;

	Type(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
