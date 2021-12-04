package client.main;

import client.main.controllers.MapController;
import client.main.controllers.NetworkController;
import client.main.map.Map;

public class MainClient {

	public static void main(String[] args) {

		/* TODO args */
		String serverBaseUrl = "http://swe1.wst.univie.ac.at:18235";
		String gameId = "UPNap";

		NetworkController networkController = new NetworkController(gameId, serverBaseUrl);
		MapController mapController = MapController.getMapControllerInstance();
		Map map = mapController.generateMap();
		map.printMap();

		try {
			networkController.registerPlayer();
			networkController.sendHalfMap(mapController.generateMap());
			networkController.startPlaying();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
