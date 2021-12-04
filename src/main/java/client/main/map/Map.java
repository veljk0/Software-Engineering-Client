package client.main.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Map {
	private HashMap<Coordinate, MapNode> nodes;

	public Map() {
		this.nodes = new HashMap<Coordinate, MapNode>();
	}

	public void printMap() {
		Set<Coordinate> set = nodes.keySet();
		List<Coordinate> list = new ArrayList<>();
		for (Coordinate c : set)
			list.add(c);

		Collections.sort(list, (c1, c2) -> c1.getX() - c2.getX());
		Collections.sort(list, (c1, c2) -> c1.getY() - c2.getY());

		for (Coordinate c : list) {
			System.out.print("|" + nodes.get(c).toString() + '|');
			if (c.getX() == 7)
				System.out.println();
		}

	}

	// -------------------- getters & setters --------------//
	public HashMap<Coordinate, MapNode> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<Coordinate, MapNode> nodes) {
		this.nodes.clear();
		this.nodes = nodes;
	}

}
