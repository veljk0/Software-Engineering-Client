package client.main.validators;

import client.main.enums.Terrain;
import client.main.map.Coordinate;
import client.main.map.Map;

public class SizeValidator implements IMapValidator {

	private final static int minWater = 4;
	private final static int minMountains = 3;
	private final static int minGrass = 15;

	@Override
	public boolean validateMap(Map map) {
		int grassCnt = 0;
		int waterCnt = 0;
		int mountainCnt = 0;
		if (map.getNodes().size() != 32)
			return false;

		for (Coordinate c : map.getNodes().keySet()) {
			if (map.getNodes().get(c).getFieldType().equals(Terrain.GRASS))
				++grassCnt;
			if (map.getNodes().get(c).getFieldType().equals(Terrain.WATER))
				++waterCnt;
			if (map.getNodes().get(c).getFieldType().equals(Terrain.MOUNTAIN))
				++mountainCnt;
		}

		if (grassCnt < minGrass || waterCnt < minWater || mountainCnt < minMountains)
			return false;

		return true;
	}

}
