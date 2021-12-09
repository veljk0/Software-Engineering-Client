package client.main.data;

import java.util.Objects;
import client.main.enums.PlayerGameState;
import client.main.map.Map;
import client.main.map.MapNode;


public class ClientData {
	
	private static ClientData data = null; 
	
	private String gameStateId;
	private String playerID;
	private Map fullmap;
	private PlayerGameState gameState;
	private MapNode currentNodePosition;
	
	private boolean treasureSpoted; 
	private boolean treasureFound; 
	private boolean enemyFortresFound; 

	private ClientData() {
		fullmap = new Map();
		gameState = PlayerGameState.MustWait;
		treasureSpoted = false; 
		treasureFound = false; 
		enemyFortresFound = false; 
	}
	
	/*
	public ClientData(String playerID, Map fullmap, PlayerGameState gameState) {
		this.playerID = playerID;
		this.fullmap = fullmap;
		this.gameState = gameState;
	}
	*/
	
	public static ClientData getClientDataInstance() {
		if(data == null)
			data = new ClientData();
		
		return data; 
	}

	

	// ---------------- Getters & Setters -------------------- //
	
	public boolean isTreasureFound() {
		return treasureFound;
	}
	
	public synchronized void setCurrentNodePosition(MapNode currentNodePosition) {
		this.currentNodePosition = currentNodePosition;
	}
	
	public MapNode getCurrentNodePosition() {
		return currentNodePosition;
	}

	public String getPlayerID() {
		return playerID;
	}

	public synchronized void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public Map getFullmap() {
		return fullmap;
	}

	public synchronized void setFullmap(Map fullmap) {
		this.fullmap = fullmap;
	}

	public PlayerGameState getGameState() {
		return gameState;
	}

	public synchronized void setGameState(PlayerGameState gameState) {
		this.gameState = gameState;
	}

	public String getGameStateId() {
		return gameStateId;
	}

	public synchronized void setGameStateId(String gameStateId) {
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

	public void setTresureFound(boolean b) {
		this.treasureFound = b; 
		
	}

	public void setEnemyFortresFound(boolean b) {
		this.enemyFortresFound = b; 
		
	}
	
	public void setTreasureSpoted(boolean treasureSpoted) {
		this.treasureSpoted = treasureSpoted;
	}

}
