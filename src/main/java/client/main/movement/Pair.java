package client.main.movement;

import client.main.enums.Move;

public class Pair {
	
	private Move move; 
	private int moveNumber; 
	
	public Pair(Move move, int number) {
		this.move = move; 
		this.moveNumber = number;
	}
	
	public Move getMove() {
		return move;
	}
	
	public int getMoveNumber() {
		return moveNumber;
	}
	
	public void decreaseMoveNumber() {
		this.moveNumber--;
	}

}
