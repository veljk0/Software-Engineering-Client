package client.main.data;

import java.util.Objects;

import client.main.enums.PlayerGameState;
import client.main.map.Map;

public class ClientData {
	private String gameStateId;
	private String playerID;
	private Map fullmap;
	private PlayerGameState gameState;

	public ClientData() {
		fullmap = new Map();
	}

	public ClientData(String playerID, Map fullmap, PlayerGameState gameState) {
		this.playerID = playerID;
		this.fullmap = fullmap;
		this.gameState = gameState;
	}

	// ---------------- Getters & Setters -------------------- //

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public Map getFullmap() {
		return fullmap;
	}

	public void setFullmap(Map fullmap) {
		this.fullmap = fullmap;
	}

	public PlayerGameState getGameState() {
		return gameState;
	}

	public void setGameState(PlayerGameState gameState) {
		this.gameState = gameState;
	}

	public String getGameStateId() {
		return gameStateId;
	}

	public void setGameStateId(String gameStateId) {
		this.gameStateId = gameStateId;
	}

	@Override
	public String toString() {
		return "GameState [playerID=" + playerID + ", fullmap=" + fullmap + ", gameState=" + gameState + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullmap, gameState, playerID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientData other = (ClientData) obj;
		return Objects.equals(fullmap, other.fullmap) && gameState == other.gameState
				&& Objects.equals(playerID, other.playerID);
	}

}
