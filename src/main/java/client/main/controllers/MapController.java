package client.main.controllers;

import client.main.map.Map;
import client.main.map.MapGenerator;

public class MapController {

	private static MapController controller = null;
	private Map map;
	private MapGenerator mapGenerator;
	private ValidatorController validatorController;

	private MapController() {
		map = new Map();
		mapGenerator = new MapGenerator();
		validatorController = new ValidatorController();
	}

	public static MapController getMapControllerInstance() {
		if (controller == null) {
			controller = new MapController();
		}
		return controller;
	}

	public Map generateMap() {
		do {
			mapGenerator.generateMap(map);
		} while (!validatorController.validateMap(map));
		return this.map;
	}

	public Map getMap() {
		return map;
	}
}
