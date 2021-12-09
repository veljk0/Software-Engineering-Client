package client.main.marshaller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import MessagesBase.EMove;
import MessagesBase.ETerrain;
import MessagesBase.HalfMapNode;
import MessagesGameState.FullMapNode;
import MessagesGameState.GameState;
import MessagesGameState.PlayerState;
import client.main.data.ClientData;
import client.main.enums.FortState;
import client.main.enums.Move;
import client.main.enums.PlayerGameState;
import client.main.enums.PlayerPositionState;
import client.main.enums.Terrain;
import client.main.enums.TreasureState;
import client.main.exceptions.MarshallerException;
import client.main.map.Coordinate;
import client.main.map.Map;
import client.main.map.MapNode;

public class Marshaller {


	/**
	 * Marshaller --- Making data readable in both directions for the CLIENT and for the SERVER
	 * Tasks:
	 * 1. Convert PlayerGameState to Client
	 * 2. Convert Map to Client
	 * 		2.1 Convert Coordinates to Client
	 * 		2.2 Convert Terrain to Client
	 * 		2.3 Convert FortState to Client
	 * 		2.4 Convert PlayerPosition to Client
	 * 		2.5 Convert TreasureState to Client
	 * 3. Convert map to Server
	 * 4. Convert move to Server
	 * @author Veljko Radunovic 01528243
	 */

	
	/**
	 * 
	 * @param ClientData clientData
	 * @param GameState serverGameState
	 */
	public void marshallDataToClientData(ClientData clientData, GameState serverGameState) {

		if (!serverGameState.getGameStateId().equals(clientData.getGameStateId())) {
			clientData.setGameStateId(serverGameState.getGameStateId());
			convertPlayerGameStateToClient(clientData, serverGameState);
			convertMapToClient(clientData, serverGameState);
		} 
		
		//else throw new MarshallerException("Marshaller > converting data from server to ClientData");
	}

	/** 
	 * TASK 1
	 * @param ClientData clientData
	 * @param GameState serverGameState
	 */
	public void convertPlayerGameStateToClient(ClientData clientData, GameState serverGameState) {		
		Set<PlayerState> playerStates = serverGameState.getPlayers();
		for (PlayerState p : playerStates)
			if (p.getUniquePlayerID().equals(clientData.getPlayerID())) {
				
				if(p.hasCollectedTreasure()) 
					ClientData.getClientDataInstance().setTresureFound(true);
				
		
				switch (p.getState()) {
				case MustWait:
					clientData.setGameState(PlayerGameState.MustWait);
					break;
				case MustAct:
					clientData.setGameState(PlayerGameState.MustAct);
					break;
				case Lost:
					System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
					clientData.setGameState(PlayerGameState.Lost);
					//System.exit(1);
					break;
				case Won:
					System.out.println("####################\n"
							  		 + "#####    WON   #####\n"
							         + "####################");
					clientData.setGameState(PlayerGameState.Won);
					break;
				default:
					throw new MarshallerException("Marshaller PlayerGameState error");
				}
			}
	}

	/** TASK 2
	 * 
	 * @param ClientData clientData
	 * @param GameState serverGameState
	 */
	public void convertMapToClient(ClientData clientData, GameState serverGameState) {

		if(serverGameState.getMap().get().getMapNodes().size() == 64) {
			Collection<FullMapNode> fullMapNodes = serverGameState.getMap().get().getMapNodes();
			HashMap<Coordinate, MapNode> myMapNodes = new HashMap<Coordinate, MapNode>();
	
			for (FullMapNode fullMapNode : fullMapNodes) {
				MapNode myMapNode = new MapNode();
				myMapNode.setCoordinate(convertCoordinatesToClient(fullMapNode));
				myMapNode.setFieldType(convertTerrainToClient(fullMapNode));
				myMapNode.setFortState(convertFortStateToClient(fullMapNode));
				myMapNode.setPlayerPositionState(convertPlayerPositionStateToClient(fullMapNode));
				myMapNode.setTreasureState(convertTreasureStateToClient(fullMapNode));
				myMapNodes.put(myMapNode.getCoordinate(), myMapNode);
			}
	
			clientData.getFullmap().setNodes(myMapNodes);
			clientData.getFullmap().printMap();

		}
	}

	/** TASK 2.1
	 * 
	 * @param FullMapNode fullMapNode
	 * @return Coordinate
	 */
	public Coordinate convertCoordinatesToClient(FullMapNode fullMapNode) {
		return new Coordinate(fullMapNode.getX(), fullMapNode.getY());
	}

	/** TASK 2.2
	 * 
	 * @param FullMapNode fullMapNode
	 * @return Terrain
	 */
	public Terrain convertTerrainToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getTerrain()) {
		case Water:
			return Terrain.WATER;
		case Grass:
			return Terrain.GRASS;
		case Mountain:
			return Terrain.MOUNTAIN;
		default:
			throw new MarshallerException("Marshaller Terrain error");
		}
	}

	/** TASK 2.3
	 * 
	 * @param FullMapNode fullMapNode
	 * @return FortState
	 */
	private FortState convertFortStateToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getFortState()) {
		case EnemyFortPresent:
			ClientData.getClientDataInstance().setEnemyFortresFound(true);
			return FortState.EnemyFortPresent;
		case MyFortPresent:
			return FortState.MyFortPresent;
		case NoOrUnknownFortState:
			return FortState.NoOrUnknownFortState;
		default:
			throw new MarshallerException("Marshaller FortState error");
		}
	
	}

	/** TASK 2.4
	 * 
	 * @param FullMapNode fullMapNode
	 * @return PlayerPositionState
	 */
	private PlayerPositionState convertPlayerPositionStateToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getPlayerPositionState()) {
		case BothPlayerPosition:
			return PlayerPositionState.BothPlayerPosition;
		case EnemyPlayerPosition:
			return PlayerPositionState.EnemyPlayerPosition;
		case MyPlayerPosition:
			
			return PlayerPositionState.MyPlayerPosition;
		case NoPlayerPresent:
			return PlayerPositionState.NoPlayerPresent;
		default:
			throw new MarshallerException("Marshaller PlayerPositionState error");
		}
	}

	/** TASK 2.5
	 * 
	 * @param FullMapNode fullMapNode
	 * @return TreasureState
	 */
	private TreasureState convertTreasureStateToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getTreasureState()) {
		case MyTreasureIsPresent:
			ClientData.getClientDataInstance().setTreasureSpoted(true);
			return TreasureState.MyTreasureIsPresent;
		case NoOrUnknownTreasureState:
			return TreasureState.NoOrUnknownTreasureState;
		default:
			throw new MarshallerException("Marshaller TreasureState error");
		}
	}

	/**
	 * Marshaller --- Making data readable for SERVER - Methods
	 * 
	 * @author Veljko Radunovic 01528243
	 */

	
	/** TAKS 3
	 * 
	 * @param Map map
	 * @return Collection<HalfMapNode>
	 */
	public Collection<HalfMapNode> convertMapToServer(Map map) {
		HalfMapNode halfMapNode;
		Collection<HalfMapNode> halfMapNodes = new ArrayList<HalfMapNode>();
		ETerrain terrain = null;

		for (Coordinate c : map.getNodes().keySet()) {
			switch (map.getNodes().get(c).getFieldType()) {
			case GRASS:
				terrain = ETerrain.Grass;
				break;
			case MOUNTAIN:
				terrain = ETerrain.Mountain;
				break;
			case WATER:
				terrain = ETerrain.Water;
				break;
			default:
				throw new MarshallerException("Marshaller Map to Server error");
			}

			if (map.getNodes().get(c).getFortState().equals(FortState.MyFortPresent))
				halfMapNode = new HalfMapNode(c.getX(), c.getY(), true, terrain);
			else
				halfMapNode = new HalfMapNode(c.getX(), c.getY(), false, terrain);

			halfMapNodes.add(halfMapNode);
		}
		return halfMapNodes;
	}

	/** TASK 4
	 * 
	 * @param Move move
	 * @return EMove
	 */
	public EMove convertMoveToServer(Move move) {
		switch (move) {
		case Down:
			return EMove.Down;
		case Up:
			return EMove.Up;
		case Right:
			return EMove.Right;
		case Left:
			return EMove.Left;
		default:
			throw new MarshallerException("Marshaller Move error");
		}
	}
}