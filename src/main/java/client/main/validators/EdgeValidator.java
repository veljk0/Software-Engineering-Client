package client.main.validators;

import client.main.enums.Terrain;
import client.main.map.Coordinate;
import client.main.map.Map;

public class EdgeValidator implements IMapValidator {

	private final static int maxVertical = 1;
	private final static int maxHorizontal = 3;

	@Override
	public boolean validateMap(Map map) {
		int upperHorizontalWaterCounter = 0;
		int lowerHorizontalWaterCounter = 0;
		int leftVerticalWaterCounter = 0;
		int rightVerticalWaterCounter = 0;

		for (Coordinate c : map.getNodes().keySet()) {
			if (map.getNodes().get(c).getCoordinate().getY() == 0
					&& map.getNodes().get(c).getFieldType().equals(Terrain.WATER))
				++upperHorizontalWaterCounter;
			if (map.getNodes().get(c).getCoordinate().getY() == 3
					&& map.getNodes().get(c).getFieldType().equals(Terrain.WATER))
				++lowerHorizontalWaterCounter;
			if (map.getNodes().get(c).getCoordinate().getX() == 0
					&& map.getNodes().get(c).getFieldType().equals(Terrain.WATER))
				++leftVerticalWaterCounter;
			if (map.getNodes().get(c).getCoordinate().getX() == 7
					&& map.getNodes().get(c).getFieldType().equals(Terrain.WATER))
				++rightVerticalWaterCounter;
		}

		if (upperHorizontalWaterCounter > maxHorizontal || lowerHorizontalWaterCounter > maxHorizontal
				|| leftVerticalWaterCounter > maxVertical || rightVerticalWaterCounter > maxVertical)
			return false;

		return true;
	}

}
