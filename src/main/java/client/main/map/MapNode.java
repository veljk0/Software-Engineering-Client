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

	public MapNode(Coordinate coordinate, Terrain fieldType, FortState fortState) {
		this.coordinate = coordinate;
		this.fieldType = fieldType;
		this.fortState = fortState;
		playerPositionState = PlayerPositionState.NoPlayerPresent;
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
		return "X=" + this.coordinate.getY() + ";" + "Y=" + this.coordinate.getX();
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
