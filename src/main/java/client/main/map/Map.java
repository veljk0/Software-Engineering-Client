package client.main.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import client.main.enums.FortState;
import client.main.enums.PlayerPositionState;
import client.main.enums.Terrain;

/**
 * Map
 *
 * Using MapNode & Coordinates
 * @author Veljko Radunovic 01528243
 */

public class Map {
	private HashMap<Coordinate, MapNode> nodes;
	private boolean horizontalFullMap = false;

	public Map() {
		this.nodes = new HashMap<Coordinate, MapNode>();
	}
	
	
	/**
	 * This method is used to print all fields in vertical or horizontal layout.
	 * Used to print both HalfMap and FullMap nodes.
	 *
	 *  @author Veljko Radunovic 01528243
	 */
	public void printMap() {
		Set<Coordinate> set = nodes.keySet();
		List<Coordinate> list = new ArrayList<>();
		System.out.println("MAP SIZE: " + set.size());
		for (Coordinate c : set) {
			if(!horizontalFullMap && c.getX() > 7) horizontalFullMap = true;
			list.add(c);
		}

		Collections.sort(list, (c1, c2) -> c1.getX() - c2.getX());
		Collections.sort(list, (c1, c2) -> c1.getY() - c2.getY());

		
		for (Coordinate c : list) {
			String helper = "G";
			if(nodes.get(c).getFieldType().equals(Terrain.WATER)) helper = "W";
			if(nodes.get(c).getFieldType().equals(Terrain.MOUNTAIN)) helper = "M";
			if(nodes.get(c).getPlayerPositionState().equals(PlayerPositionState.MyPlayerPosition))
				helper = "+"; 
			if(nodes.get(c).getPlayerPositionState().equals(PlayerPositionState.BothPlayerPosition))
				helper = "*"; 
			if(nodes.get(c).getPlayerPositionState().equals(PlayerPositionState.EnemyPlayerPosition))
				helper = "&"; 
			if(nodes.get(c).getFortState().equals(FortState.MyFortPresent))
				helper = "#";
			if(nodes.get(c).getFortState().equals(FortState.EnemyFortPresent))
				helper = "$";
			
			
			System.out.print(helper + " " + nodes.get(c).getCoordinate().toString() + "  ");
			
			
			
			if ((horizontalFullMap && c.getX() == 15) || (!horizontalFullMap && c.getX() == 7))
				System.out.println();
		}

	}

	
	/**
	 * 
	 * Getters & Setters
	 * 
	*/
	public HashMap<Coordinate, MapNode> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<Coordinate, MapNode> nodes) {
		this.nodes.clear();
		this.nodes = nodes;
	}


	public boolean isFullMap() {
		return nodes.size() == 64;
	}

}
