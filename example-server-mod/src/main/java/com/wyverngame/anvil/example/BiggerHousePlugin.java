package com.wyverngame.anvil.example;

import com.wurmonline.server.Servers;
import com.wurmonline.server.structures.Structure;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import com.wyverngame.anvil.api.PluginMetadata;
import com.wyverngame.anvil.api.server.ServerPlugin;
import com.wyverngame.anvil.api.server.event.GetStructureSkillRequirementEvent;

import java.util.Set;

@PluginMetadata(name = "Bigger House Plugin", version = "1.0.0", author = "Jonathan")
public class BiggerHousePlugin extends ServerPlugin {
	@Override
	public void init() throws Exception {
		on(GetStructureSkillRequirementEvent.class, (ctx, evt) -> {
			int x = evt.getX();
			int y = evt.getY();
			boolean adding = evt.isAdding();
			Structure structure = evt.getStructure();

			VolaTile newTile = Zones.getOrCreateTile(x, y, evt.isOnSurface());
			int points = structure.getLimit();

			if (structure.contains(x, y) && adding) {
				ctx.cancel(Math.max(points / 2, 5));
			} else {
				byte newTilePoints = 5;
				Set neighbours;
				int newTilePoints1;
				if (adding) {
					neighbours = Structure.createNeighbourStructureTiles(structure, newTile);
					newTilePoints1 = newTilePoints - neighbours.size();
					points -= neighbours.size();
					ctx.cancel(Math.max((points + newTilePoints1) / 2, 5));
				} else if (structure.contains(x, y)) {
					neighbours = Structure.createNeighbourStructureTiles(structure, newTile);
					newTilePoints1 = newTilePoints - neighbours.size();
					points += neighbours.size();
					ctx.cancel(Math.max((points - newTilePoints1) / 2, 5));
				} else {
					ctx.cancel(Math.max(points, 5));
				}
			}
		});
	}
}
