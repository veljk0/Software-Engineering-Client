package client.main.movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

import client.main.data.ClientData;
import client.main.enums.FortState;
import client.main.enums.Move;
import client.main.enums.PlayerPositionState;
import client.main.enums.Terrain;
import client.main.helper.Helper;
import client.main.map.Coordinate;
import client.main.map.Map;
import client.main.map.MapNode;



class SortGrass implements Comparator<MapNode>{
	@Override
	public int compare(MapNode o1, MapNode o2) {
		return Helper.getCost(o1) - Helper.getCost(o2);
	}
}

public class Movement {

	private Map map;
	private MapType mapType;
	private List<MapNode> myMapFields;
	private List<MapNode> notVisitedGrassField; 
	private Set<MapNode> visitedFields;
	private boolean defaultPosition;
	private static ClientData clientData; 
	private Node nextNode = null;
	private HashMap<MapNode, Node> potentionalMoveList; 


	Move move = Move.Up;
	private boolean searchingForEnemyCastel;
	
	public Movement() {
		this.mapType = mapType.VERTICAL;
		this.myMapFields = new ArrayList<>();
		this.visitedFields = new HashSet<>();
		this.defaultPosition = false;
		this.clientData = ClientData.getClientDataInstance();
		this.notVisitedGrassField = new ArrayList<MapNode>();
		this.map = clientData.getFullmap();
		this.potentionalMoveList = new HashMap<MapNode, Node>();
		this.searchingForEnemyCastel = false; 
	}

	public void prepareField() {
		findMyHalfMap();
		findNotVisitedFields();
		findMyPositionField();	
	}
	
	
	private void findMyPositionField() {
		
		for (Coordinate c : this.map.getNodes().keySet()) {
			MapNode tmp = map.getNodes().get(c);
			if (tmp.getPlayerPositionState().equals(PlayerPositionState.MyPlayerPosition)
					|| tmp.getPlayerPositionState().equals(PlayerPositionState.BothPlayerPosition)){
				
				clientData.setCurrentNodePosition(tmp);
				visitedFields.add(tmp);
				break;
			}
		}
			
		
		
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
		
		if(clientData.isTreasureFound() && !searchingForEnemyCastel) {
			searchingForEnemyCastel = true; 
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			findNotVisitedFields();
			nextNode = null;
		}
		
		if(nextNode == null || nextNode.getMoveList().isEmpty()) {
			
			findMyPositionField();
			nextNode = new Node(clientData.getCurrentNodePosition(), 10000, null);
			
			potentionalMoveList.clear();
			findNextMode(clientData.getCurrentNodePosition(), new LinkedList<Move>(),  10000);
			
			nextNode = getNodeMinCost();			
			notVisitedGrassField.remove(nextNode.getField());

			for(MapNode node: visitedFields) {
				System.out.print(node.getCoordinate() + " ");
			}
		}
				
		return nextNode.getMove();
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
		
	}
	
private void findNextMode(MapNode field, LinkedList<Move> list, int cost) {
		
		if(potentionalMoveList.containsKey(field) && potentionalMoveList.get(field).getCost() > cost) {
			potentionalMoveList.get(field).setCost(cost);
			potentionalMoveList.get(field).updateMoveList(list);
		}
		else if(potentionalMoveList.containsKey(field) && potentionalMoveList.get(field).getCost() < cost) return; 
		else potentionalMoveList.put(field, new Node(field, cost, list)); 	
		
		
		
		List<MapNode> neighbors = new ArrayList<MapNode>();
		neighbors = findNeighbours(field);		
		Collections.sort(neighbors, new SortGrass()); 
		
		for(MapNode neighbour: neighbors) {
			if(!neighbour.getFieldType().equals(Terrain.WATER)) {
				int newCost = Helper.getCost(field) + Helper.getCost(neighbour); 
				if(!list.isEmpty()) newCost += cost;
	
				LinkedList<Move> list2 = new LinkedList<Move>(); 
				list2.addAll(list);
					
				for(int i = 0; i < Helper.getCost(field) + Helper.getCost(neighbour); i++)
					list2.add(Helper.moveDirection(field, neighbour));
				
				findNextMode(neighbour, list2, newCost);
			}
		}	
	}

	private Node getNodeMinCost() {
		nextNode = new Node(null, 10000);
	
		for(MapNode field: potentionalMoveList.keySet()) {
			if(isNodeVisited(field) 
					&& potentionalMoveList.get(field).getCost() < nextNode.getCost()) {
				nextNode = potentionalMoveList.get(field);
			}
			
	}
		
		System.out.println("########   Min COST    ##### ");
		
		System.out.println(nextNode.getCost());
		System.out.println(nextNode.getField().toString());
		
		for(Move move: nextNode.getMoveList())
		System.out.print(move + " ");
	
	
        System.out.println("\n\n");
	
	return nextNode;
}
	
	private boolean isNodeVisited(MapNode field) {
		for(MapNode f: notVisitedGrassField)
			if(f.getCoordinate().getX() == field.getCoordinate().getX() && f.getCoordinate().getY() == field.getCoordinate().getY()) return true; 
		
		return false;
	}


	private void findNotVisitedFields() {
		
		if(clientData.isTreasureFound()) notVisitedGrassField.clear();
		
		for (Coordinate c : this.map.getNodes().keySet()) {
			MapNode tmp = map.getNodes().get(c);
			if(!clientData.isTreasureFound() && myMapFields.contains(tmp) 
					&& tmp.getFieldType().equals(Terrain.GRASS)) notVisitedGrassField.add(tmp);
			else if(clientData.isTreasureFound() && !myMapFields.contains(tmp) 
					&& tmp.getFieldType().equals(Terrain.GRASS)) notVisitedGrassField.add(tmp);
		}
			
	}
	
	
private List<MapNode> findNeighbours(MapNode node) {
		
		List<MapNode> neighbour = new ArrayList<>();
		
		Coordinate up = new Coordinate(node.getCoordinate().getX(), node.getCoordinate().getY() - 1);
		Coordinate left = new Coordinate(node.getCoordinate().getX() - 1, node.getCoordinate().getY());
		Coordinate down = new Coordinate(node.getCoordinate().getX(), node.getCoordinate().getY() + 1);
		Coordinate right = new Coordinate(node.getCoordinate().getX() + 1, node.getCoordinate().getY());

		for (Coordinate c : clientData.getFullmap().getNodes().keySet()) {
			if ((c.equals(up) || c.equals(left) || c.equals(down) || c.equals(right))
					&& !clientData.getFullmap().getNodes().get(c).getFieldType().equals(Terrain.WATER)) 
				neighbour.add(clientData.getFullmap().getNodes().get(c));
		}
		
		return neighbour;
	}

	


	
}
