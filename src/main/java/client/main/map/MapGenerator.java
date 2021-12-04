package client.main.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import client.main.enums.FortState;
import client.main.enums.Terrain;

public class MapGenerator {

	public MapGenerator() {
	}

	public void generateMap(Map map) {
		HashMap<Coordinate, MapNode> tmp = new HashMap<>();
		List<Terrain> terrains = new ArrayList<>();
		terrains = generateTerrains();
		System.out.println("===== Map Generator =====");
		System.out.println(">> terranins have been generated & shuffled <<");
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 4; ++j) {
				MapNode node = new MapNode(new Coordinate(i, j), terrains.get(terrains.size() - 1),
						FortState.NoOrUnknownFortState);
				tmp.put(node.getCoordinate(), node);
				terrains.remove(terrains.size() - 1);
			}
		}

		System.out.println("===== Map Generator =====");
		System.out.println(">> setting castle position <<");

		for (Coordinate c : tmp.keySet()) {
			if (tmp.get(c).getFieldType().equals(Terrain.GRASS)) {
				tmp.get(c).setFortState(FortState.MyFortPresent);
				break;
			}
		}

		System.out.println("===== Map Generator =====");
		System.out.println(">> setting generated nodes on map <<");
		map.setNodes(tmp);
	}

	private List<Terrain> generateTerrains() {
		System.out.println("===== Map Generator =====");
		System.out.println(">> generating terrains <<");
		List<Terrain> tmp = new ArrayList<>();
		int cnt1 = 0;
		int cnt2 = 0;
		int cnt3 = 0;

		while (cnt1 < 4) {
			tmp.add(Terrain.WATER);
			++cnt1;
		}

		while (cnt2 < 3) {
			tmp.add(Terrain.MOUNTAIN);
			++cnt2;
		}

		while (cnt3 < 25) {
			tmp.add(Terrain.GRASS);
			++cnt3;
		}

		System.out.println("===== Map Generator =====");
		System.out.println(">> shuffling terrains <<");
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);

		return tmp;

	}

}
