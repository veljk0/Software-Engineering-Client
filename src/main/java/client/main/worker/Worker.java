package client.main.worker;

import client.main.controllers.NetworkController;

public class Worker implements Runnable {
	
	private NetworkController network;
	
	public Worker(NetworkController network) {
		this.network = network;
	}

	@Override
	public void run() {
		while(true)
		try {
			Thread.sleep(400);
			network.getGameStatus();
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
