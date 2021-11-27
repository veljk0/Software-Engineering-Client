package client.main.validators;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import client.main.enums.Terrain;
import client.main.map.Coordinate;
import client.main.map.Map;
import client.main.map.MapNode;

public class IslandValidator implements IMapValidator {

	private LinkedList<MapNode> askNodes;
	private Set<MapNode> checkedNodes;

	public IslandValidator() {
		askNodes = new LinkedList<>();
		checkedNodes = new HashSet<>();
	}

	@Override
	public boolean validateMap(Map map) {

		findFirst(map);
		while (!askNodes.isEmpty()) {
			MapNode tmp = askNodes.poll();
			checkedNodes.add(tmp);
			// find valid neighbours
			findNeighbours(tmp, map);

		}
		System.out.println("BAUBAU: " + checkedNodes.size());
		return checkedNodes.size() == 28;

	}

	private void findFirst(Map map) {
		for (Coordinate c : map.getNodes().keySet()) {
			if (!map.getNodes().get(c).getFieldType().equals(Terrain.WATER)) {
				this.askNodes.add(map.getNodes().get(c));
				break;
			}
		}

	}

	private void findNeighbours(MapNode mapNode, Map map) {
		Coordinate up = new Coordinate(mapNode.getCoordinate().getX(), mapNode.getCoordinate().getY() - 1);
		Coordinate left = new Coordinate(mapNode.getCoordinate().getX() - 1, mapNode.getCoordinate().getY());
		Coordinate down = new Coordinate(mapNode.getCoordinate().getX(), mapNode.getCoordinate().getY() + 1);
		Coordinate right = new Coordinate(mapNode.getCoordinate().getX() + 1, mapNode.getCoordinate().getY());

		for (Coordinate c : map.getNodes().keySet()) {
			if ((c.equals(up) || c.equals(left) || c.equals(down) || c.equals(right))
					&& !map.getNodes().get(c).getFieldType().equals(Terrain.WATER)
					&& !checkedNodes.contains(map.getNodes().get(c)))
				askNodes.add(map.getNodes().get(c));
		}
	}
}
