package client.main.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.main.enums.FortState;
import client.main.enums.Terrain;

/**
 * MapGenerator
 *
 *Tasks:
 *1. Generate terrains
 *2. Assign terrains to nodes
 *3. Generate/Set map 
 * @author Veljko Radunovic 01528243
 */

public class MapGenerator {
	
	/**
	 * defining class logger
	 */
	static Logger logger = LoggerFactory.getLogger(MapGenerator.class);

	public MapGenerator() {}
	
	
	
	/**
	 * generating Map
	 *
	 *Tasks: 
	 *1. Assign terrains to nodes
	 *2. Set castle to one grass node
	 *3. Set generated nodes to map 
	 * @param Map map
	 * @author Veljko Radunovic 01528243
	 */
	public void generateMap(Map map) {
		HashMap<Coordinate, MapNode> tmp = new HashMap<>();
		List<Terrain> terrains = new ArrayList<>();
		terrains = generateTerrains();
		logger.info("[generateMap] terranins have been generated & shuffled");
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 4; ++j) {
				MapNode node = new MapNode(new Coordinate(i, j), terrains.get(terrains.size() - 1),
						FortState.NoOrUnknownFortState);
				tmp.put(node.getCoordinate(), node);
				terrains.remove(terrains.size() - 1);
			}
		}
		
		for (Coordinate c : tmp.keySet()) {
			if (tmp.get(c).getFieldType().equals(Terrain.GRASS)) {
				tmp.get(c).setFortState(FortState.MyFortPresent);
				logger.info("[generateMap] setting castle position" );
				break;
			}
		}

		map.setNodes(tmp);
		logger.info("[generateMap] setting generated nodes on the map");
		
	}
	
	
	/**
	 * Terrains generator
	 *
	 *Task: Generate terrains & shuffle terrains
	 *Generating 4 water, 3 mountain and 25 grass terrains. 
	 * @author Veljko Radunovic 01528243
	 */
	private List<Terrain> generateTerrains() {
		logger.info("[generateTerrains] generating terrains");
		List<Terrain> tmp = new ArrayList<>();
		int waterCounter = 0;
		int mountainCounter = 0;
		int grassCounter = 0;

		while (waterCounter < 4) {
			tmp.add(Terrain.WATER);
			++waterCounter;
		}

		while (mountainCounter < 3) {
			tmp.add(Terrain.MOUNTAIN);
			++mountainCounter;
		}

		while (grassCounter < 25) {
			tmp.add(Terrain.GRASS);
			++grassCounter;
		}
		logger.info("[generateTerrains] shuffling terrains");
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);

		return tmp;
	}
}
