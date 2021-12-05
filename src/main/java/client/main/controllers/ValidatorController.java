package client.main.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.main.map.Map;
import client.main.validators.CastleValidator;
import client.main.validators.EdgeValidator;
import client.main.validators.IMapValidator;
import client.main.validators.IslandValidator;
import client.main.validators.SizeValidator;

/**
 * ValidatorController
 * 
 * This is a controller that uses instances that extend IMapValidator. 
 * Each in its own way validates certain cases. More details in the validators. 
 * The controller serves to run all validators and to determine if the map is correct. 
 * 
 * Tasks: 
 * 1. Validate size of the map
 * 2. Validate my castle position
 * 3. Validate the borders/edges
 * 4. Validate the existence of the island
 * 
 * Using EdgeValidator, CastleValidator, IslandValidator & SizeValidator instances to process tasks
 * @author Veljko Radunovic 01528243
 */
public class ValidatorController implements IMapValidator {
	
	/**
	 * defining class logger
	 */
	static Logger logger = LoggerFactory.getLogger(ValidatorController.class);


	private EdgeValidator edgeValidator;
	private CastleValidator castleValidator;
	private IslandValidator islandValidator;
	private SizeValidator sizeValidator;

	public ValidatorController() {
		this.edgeValidator = new EdgeValidator();
		this.castleValidator = new CastleValidator();
		this.islandValidator = new IslandValidator();
		this.sizeValidator = new SizeValidator();
	}

	@Override
	public boolean validateMap(Map map) {
		if (!sizeValidator.validateMap(map)) {
			logger.error("[validateMap] size not valid");
			return false;
		}
		if (!castleValidator.validateMap(map)) {
			logger.error("[validateMap] castle not valid");
			return false;
		}
		if (!edgeValidator.validateMap(map)) {
			logger.error("[validateMap] edges not valid");
			return false;
		}
		if (!islandValidator.validateMap(map)) {
			logger.error("[validateMap] island not valid");
			return false;
		}
		
		logger.info("Map is valid!");
		return true;

	}

}
