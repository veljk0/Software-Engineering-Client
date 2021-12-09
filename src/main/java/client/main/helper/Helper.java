package client.main.helper;

import client.main.enums.Move;
import client.main.enums.Terrain;
import client.main.map.MapNode;

public class Helper {
	
	private static final int waterCost = 10000; 
	private static final int grassCost = 1; 
	private static final int mountainCost = 2; 

	
	public static int getCost(MapNode node) {
		if(node.getFieldType().equals(Terrain.GRASS)) return grassCost; 
		if(node.getFieldType().equals(Terrain.MOUNTAIN)) return mountainCost; 
		
		return waterCost;
	}


	public static Move moveDirection(MapNode field, MapNode neighbour) {
		
			if(field.getCoordinate().getX() == neighbour.getCoordinate().getX() && field.getCoordinate().getY() < neighbour.getCoordinate().getY()) return Move.Down; 
			if(field.getCoordinate().getX() == neighbour.getCoordinate().getX() && field.getCoordinate().getY() > neighbour.getCoordinate().getY()) return Move.Up; 
			if(field.getCoordinate().getX() < neighbour.getCoordinate().getX() && field.getCoordinate().getY() == neighbour.getCoordinate().getY()) return Move.Right; 
			return Move.Left;
		
	}
}
