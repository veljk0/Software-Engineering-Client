package client.main.movement;

import java.util.LinkedList;

import client.main.enums.Move;
import client.main.map.MapNode;

public class Node {

	/**
	 * Represents the field that the Player wants to go to
	 */
	private MapNode field; 
	
	/**
	 * Represents the number of turns needed to reach the targeted field
	 */
	private int cost; 
	
	/**
	 * List consisting of enum CPlayerMove moves needed to reach the targeted field
	 */
	private LinkedList<Move> moveList;
	
	public Node(MapNode field, int cost) {
		this.field = field; 
		this.cost = cost; 
		this.moveList = new LinkedList<Move>(); 
	}
	
	public Node(MapNode field, int cost, LinkedList<Move> list) {
		this.field = field; 
		this.moveList = list;
		setCost(cost);
	}
	
	public MapNode getField() {
		return field;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void addMove(Move move) {
		this.moveList.add(move); 
	}
	
	public void updateMoveList(LinkedList<Move> list) {
		this.moveList = new LinkedList<Move>();
		this.moveList = list; 
	}
	
	public Move getMove() {
		return moveList.pollFirst();
	}
	
	public LinkedList<Move> getMoveList() {
		return moveList;
	}
	
	
}