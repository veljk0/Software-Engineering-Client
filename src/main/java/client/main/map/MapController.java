package client.main.map;

public class MapController {

	private static MapController controller = null;
	private Map map;
	private MapGenerator mapGenerator;

	private MapController() {
	}

	public MapController getMapControllerInstance() {
		if (this.controller.equals(null)) {
			this.controller = new MapController();
		}
		return this.controller;
	}

	public void updateMap(Map map) {
	}

}
