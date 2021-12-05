package client.main;

import client.main.controllers.MapController;
import client.main.controllers.NetworkController;
import client.main.worker.Worker;

public class MainClient {

	public static void main(String[] args) {

		/* TODO args */
		String serverBaseUrl = "http://swe1.wst.univie.ac.at:18235";
		String gameId = "ZuYvP";

		NetworkController networkController = new NetworkController(gameId, serverBaseUrl);
		MapController mapController = MapController.getMapControllerInstance();

		try {
			networkController.registerPlayer();

			Thread statusWorker = new Thread(new Worker(networkController));
			statusWorker.start();

			networkController.sendHalfMap(mapController.generateMap());
			networkController.startPlaying();

		} catch (Exception e) {
			System.exit(0);
		}
	}
}
