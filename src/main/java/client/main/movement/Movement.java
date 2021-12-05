package client.main.movement;

import java.util.ArrayList;
import java.util.List;

import client.main.enums.FortState;
import client.main.enums.Move;
import client.main.map.Coordinate;
import client.main.map.Map;
import client.main.map.MapNode;

public class Movement {

	private Map map;
	private MapType mapType;
	private List<MapNode> myMapFields;
	private List<MapNode> visitedFields;
	private boolean defaultPosition;

	Move move = Move.Up;
	
	public Movement() {
		this.mapType = mapType.VERTICAL;
		this.myMapFields = new ArrayList<>();
		this.setVisitedFields(new ArrayList<>());
		this.defaultPosition = false;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
		findMyHalfMap();
		// defineMapType();

	}

//	public void defineMapType() {
//		for (Coordinate c : this.map.getNodes().keySet()) {
//			if (this.map.getNodes().get(c).getCoordinate().getX() > 7) {
//				this.mapType = MapType.HORIZONTAL;
//				break;
//			}
//		}
//
//	}

	private void findMyHalfMap() {

		for (Coordinate c : this.map.getNodes().keySet()) {
			MapNode tmp = map.getNodes().get(c);
			if (tmp.getFortState().equals(FortState.MyFortPresent) && tmp.getCoordinate().getX() <= 7
					&& tmp.getCoordinate().getY() <= 3) {
				defaultPosition = true;
				break;
			}
		}

		for (Coordinate c : this.map.getNodes().keySet()) {
			MapNode tmp = map.getNodes().get(c);
			if (defaultPosition && tmp.getCoordinate().getX() <= 7 && tmp.getCoordinate().getY() <= 3) {
				myMapFields.add(tmp);
			}
			else if(!defaultPosition && (tmp.getCoordinate().getX() > 7 || tmp.getCoordinate().getY() > 3))
				myMapFields.add(tmp);
		}

	}

	public Move calculateNextMove() {
		/*
		if(move == move.Down) move = Move.Up;
		else move = Move.Down;
		
		
		return move;
		
		*/
		
		
		// find my position(first turn myburg)
		
		// Make dijkstra nodes 
		
		// Calculate cost and route to every node not visited 
		
		//find lowest cost
		
		// Follow route
		
		return null; 
	}

	public List<MapNode> getVisitedFields() {
		return visitedFields;
	}

	public void setVisitedFields(List<MapNode> visitedFields) {
		this.visitedFields = visitedFields;
	}

}
