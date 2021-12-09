package client.main.movement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import client.main.map.MapNode;

public class RouteNode {
	
	private MapNode mapNode; 
	private int distance; 
	private LinkedList<Pair> moveList;
	private List<MapNode> neighbours; 
	
	public RouteNode(MapNode node, int maxDistance) {
		this.mapNode = node; 
		this.distance = maxDistance;
		this.moveList = new LinkedList<>();
		this.neighbours = new ArrayList<MapNode>(); 
	}
	
	public MapNode getMapNode() {
		return mapNode;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public LinkedList<Pair> getMoveList() {
		return moveList;
	}
	
	public Pair getNextMove() {
		return moveList.poll();
	}
	
	
	public void setNeighbours(List<MapNode> neighbours) {
		this.neighbours = neighbours;
	}
	/*
	// suvisno ???
	public void addNeighbor(MapNode neighbour) {
		neighbours.add(neighbour);
	}
	*/
	public List<MapNode> getNeighbours() {
		return neighbours;
	}
	
	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof RouteNode)) {
			return false;
		}

		RouteNode c = (RouteNode) o;
		return this.mapNode.equals(c.getMapNode());
	}


}
