package client.main;

import client.main.controllers.MapController;
import client.main.controllers.NetworkController;


public class MainClient {

	public static void main(String[] args) {
		
//		Coordinate c = new Coordinate(0, 0);
//		Coordinate b = new Coordinate(0, 0);
//
//		System.out.println(b.equals(c));
//		
//		MapNode node = new MapNode(c, null, null);
//		MapNode node1 = new MapNode(b, null, null);
//		
//		System.out.println(node.equals(node1));
		
		/* TODO args */
		String serverBaseUrl = args[1];
		String gameId = args[2];

		NetworkController networkController = new NetworkController(gameId, serverBaseUrl);
		MapController mapController = MapController.getMapControllerInstance();

		try {
			networkController.registerPlayer();

			networkController.sendHalfMap(mapController.generateMap());
			networkController.startPlaying();

		} catch (Exception e) {
			System.exit(0);
		}
	}
}
