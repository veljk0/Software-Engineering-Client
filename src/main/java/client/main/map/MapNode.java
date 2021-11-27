package client.main.map;

import client.main.enums.FortState;
import client.main.enums.PlayerPositionState;
import client.main.enums.Terrain;
import client.main.enums.TreasureState;

public class MapNode {
	private Coordinate coordinate;
	private Terrain fieldType;
	private FortState fortState;
	private TreasureState treasureState;
	private PlayerPositionState playerPositionState;

	public MapNode() {
	}

	public MapNode(Coordinate coordinate, Terrain fieldType) {
		this.coordinate = coordinate;
		this.fieldType = fieldType;
	}

	// ------------------------- Getters & Setters
	// ------------------------------------//
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Terrain getFieldType() {
		return fieldType;
	}

	public void setFieldType(Terrain fieldType) {
		this.fieldType = fieldType;
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
		return this.coordinate.getY() + " " + this.coordinate.getX() + " " + this.fieldType + "|    ";
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
