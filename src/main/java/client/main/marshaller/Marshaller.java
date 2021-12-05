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
import client.main.map.Coordinate;
import client.main.map.Map;
import client.main.map.MapNode;

public class Marshaller {

	/**
	 * Marshaller --- Making data readable for CLIENT - Methods
	 * 
	 * @author Veljko Radunovic 01528243
	 */

	public void marshallDataToClientData(ClientData clientData, GameState serverGameState) {

		if (!serverGameState.getGameStateId().equals(clientData.getGameStateId())) {
			clientData.setGameStateId(serverGameState.getGameStateId());
			convertPlayerGameStateToClient(clientData, serverGameState);
			convertMapToClient(clientData, serverGameState);

		} else {
			// throw error
		}

	}

	// ------------- Convert PlayerGameState to Client ------------- //
	public void convertPlayerGameStateToClient(ClientData clientData, GameState serverGameState) {		
		Set<PlayerState> playerStates = serverGameState.getPlayers();
		for (PlayerState p : playerStates)
			if (p.getUniquePlayerID().equals(clientData.getPlayerID()))
				switch (p.getState()) {
				case MustWait:
					clientData.setGameState(PlayerGameState.MustWait);
					break;
				case MustAct:
					clientData.setGameState(PlayerGameState.MustAct);
					break;
				case Lost:
					clientData.setGameState(PlayerGameState.Lost);
					break;
				case Won:
					clientData.setGameState(PlayerGameState.Won);
					break;
				default:
					break; // TODO BACI ERROR
				}
	}

	// ------------- Convert Map to Client ------------- //
	public void convertMapToClient(ClientData clientData, GameState serverGameState) {

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

	// ------------- Convert Coordinates to Client ------------- //
	public Coordinate convertCoordinatesToClient(FullMapNode fullMapNode) {
		return new Coordinate(fullMapNode.getX(), fullMapNode.getY());
	}

	// ------------- Convert Terrain to Client ------------- //
	public Terrain convertTerrainToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getTerrain()) {
		case Water:
			return Terrain.WATER;
		case Grass:
			return Terrain.GRASS;
		case Mountain:
			return Terrain.MOUNTAIN;
		default:
			break; // TODO BACI ERROR
		}
		return null;
	}

	// ------------- Convert FortState to Client ------------- //
	private FortState convertFortStateToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getFortState()) {
		case EnemyFortPresent:
			return FortState.EnemyFortPresent;
		case MyFortPresent:
			return FortState.MyFortPresent;
		case NoOrUnknownFortState:
			return FortState.NoOrUnknownFortState;
		default:
			break; // TODO BACI ERROR
		}
		return null;
	}

	// ------------- Convert PlayerPosition to Client ------------- //
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
			break; // TODO BACI ERROR
		}
		return null;
	}

	// ------------- Convert TreasureState to Client ------------- //
	private TreasureState convertTreasureStateToClient(FullMapNode fullMapNode) {
		switch (fullMapNode.getTreasureState()) {
		case MyTreasureIsPresent:
			return TreasureState.MyTreasureIsPresent;
		case NoOrUnknownTreasureState:
			return TreasureState.NoOrUnknownTreasureState;
		default:
			break; // TODO BACI ERROR
		}
		return null;
	}

	/**
	 * Marshaller --- Making data readable for SERVER - Methods
	 * 
	 * @author Veljko Radunovic 01528243
	 */

	// ------------------------CONVERT MAP TO SERVER---------------------------//

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
				break;
			}

			if (map.getNodes().get(c).getFortState().equals(FortState.MyFortPresent))
				halfMapNode = new HalfMapNode(c.getX(), c.getY(), true, terrain);
			else
				halfMapNode = new HalfMapNode(c.getX(), c.getY(), false, terrain);

			halfMapNodes.add(halfMapNode);
		}
		return halfMapNodes;
	}

	// ------------------------CONVERT MOVE TO SERVER---------------------------//
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
			break;
		}

		// TODO throw error
		return null;
	}

}