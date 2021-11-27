package client.main.validators;

import client.main.enums.FortState;
import client.main.enums.Terrain;
import client.main.map.Coordinate;
import client.main.map.Map;

public class CastleValidator implements IMapValidator {

	@Override
	public boolean validateMap(Map map) {
		int castleCounter = 0;

		for (Coordinate c : map.getNodes().keySet()) {
			if (map.getNodes().get(c).getFieldType().equals(Terrain.GRASS)
					&& map.getNodes().get(c).getFortState().equals(FortState.MyFortPresent))
				++castleCounter;
		}

		if (castleCounter != 1)
			return false;

		return true;
	}

}
