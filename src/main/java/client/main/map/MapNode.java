package client.main.map;

import client.main.enums.FortState;
import client.main.enums.PlayerPositionState;
import client.main.enums.Terrain;
import client.main.enums.TreasureState;

/**
 * MapNode
 * Objects of this class represent map fields in this game. 
 * The field has its coordinates, its type of terrain, the presence of the castle, the presence of gold and the position of the players.
 * It overrides the equals method for easier field comaprison.
 * @author Veljko Radunovic 01528243
 */

public class MapNode {
	private Coordinate coordinate;
	private Terrain terrain;
	private FortState fortState;
	private TreasureState treasureState;
	private PlayerPositionState playerPositionState;

	public MapNode() {
	}

	public MapNode(Coordinate coordinate, Terrain fieldType, FortState fortState) {
		this.coordinate = coordinate;
		this.terrain = fieldType;
		this.fortState = fortState;
		playerPositionState = PlayerPositionState.NoPlayerPresent;
	}

	/**
	 * 
	 * Getters & Setters
	 * 
	*/
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Terrain getFieldType() {
		return terrain;
	}

	public void setFieldType(Terrain fieldType) {
		this.terrain = fieldType;
	}

	public FortState getFortState() {
		return fortState;
	}

	public void setFortState(FortState fortState) {
		this.fortState = fortState;
	}

	public TreasureState getTreasureState() {
		return treasureState;
	}

	public void setTreasureState(TreasureState treasureState) {
		this.treasureState = treasureState;
	}

	public PlayerPositionState getPlayerPositionState() {
		return playerPositionState;
	}

	public void setPlayerPositionState(PlayerPositionState playerPositionState) {
		this.playerPositionState = playerPositionState;
	}

	@Override
	public String toString() {
		return "X = " + this.coordinate.getY() + ", " + "Y = " + this.coordinate.getX();
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof MapNode)) {
			return false;
		}

		MapNode c = (MapNode) o;
		return this.coordinate.equals(c.getCoordinate());
	}

}
