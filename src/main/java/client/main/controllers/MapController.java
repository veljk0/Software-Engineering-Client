package client.main.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.main.map.Map;
import client.main.map.MapGenerator;

/**
 * MapController
 * Singleton pattern
 * Tasks: Generating Map & Validating Map
 * Using Map, MapGenerator & ValidatorController instances to process tasks
 * @author Veljko Radunovic 01528243
 */

public class MapController {

	/**
	 * defining class logger
	 */
	static Logger logger = LoggerFactory.getLogger(MapController.class);

	private static MapController controller = null;
	private Map map;
	private MapGenerator mapGenerator;
	private ValidatorController validatorController;

	private MapController() {
		map = new Map();
		mapGenerator = new MapGenerator();
		validatorController = new ValidatorController();
	}
	
	/**
	 * Singleton get MapController instance
	 */
	public static MapController getMapControllerInstance() {
		if (controller == null) {
			controller = new MapController();
			logger.info("Generating MapController Singletong instance");
		}
		return controller;
	}

	
	/**
	 * Generating map until get the valid one. 
	 * Generating map through -> MapGenerator object
	 * Validating map through -> ValidatorController controller
	 */
	public Map generateMap() {
		do {
			mapGenerator.generateMap(map);
			logger.info("Send request for generating HalfMap to MapGenerator");
			map.printMap();
		} while (!validatorController.validateMap(map));
		
		logger.info("HalfMap has been generated and it is valid");
		return this.map;
	}

	
	
	/**
	 * 
	 * Getters & Setters
	 * 
	*/
	public static MapController getController() {
		logger.info("Getting MapController");
		return controller;
	}

	public static void setController(MapController controller) {
		logger.info("Setting MapController");
		MapController.controller = controller;
	}

	public Map getMap() {
		logger.info("Getting Map");
		return map;
	}

	public void setMap(Map map) {
		logger.info("Setting Map");
		this.map = map;
	}

	public MapGenerator getMapGenerator() {
		logger.info("Getting MapGenerator");
		return mapGenerator;
	}

	public void setMapGenerator(MapGenerator mapGenerator) {
		logger.info("Setting MapGenerator");
		this.mapGenerator = mapGenerator;
	}

	public ValidatorController getValidatorController() {
		logger.info("Getting ValidatorController");
		return validatorController;
	}

	public void setValidatorController(ValidatorController validatorController) {
		logger.info("Setting ValidatorController");
		this.validatorController = validatorController;
	}
}
