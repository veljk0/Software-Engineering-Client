package client.main.controllers;

import client.main.map.Map;
import client.main.validators.CastleValidator;
import client.main.validators.EdgeValidator;
import client.main.validators.IMapValidator;
import client.main.validators.IslandValidator;
import client.main.validators.SizeValidator;

public class ValidatorController implements IMapValidator {

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

	/* TODO Exception handling */
	@Override
	public boolean validateMap(Map map) {
		if (!sizeValidator.validateMap(map)) {
			System.err.println("SIZE ERROR");
			return false;
		}
		if (!castleValidator.validateMap(map)) {
			System.err.println("CASTLE ERROR");
			return false;
		}
		if (!edgeValidator.validateMap(map)) {
			System.err.println("EDGE ERROR");
			return false;
		}
		if (!islandValidator.validateMap(map)) {
			System.err.println("ISLAND ERROR");
			return false;
		}

		System.out.println("Map is valid!");
		return true;

	}

}
