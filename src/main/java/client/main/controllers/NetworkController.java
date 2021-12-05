package client.main.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.EMove;
import MessagesBase.ERequestState;
import MessagesBase.HalfMap;
import MessagesBase.PlayerMove;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.GameState;
import client.main.data.ClientData;
import client.main.enums.Move;
import client.main.enums.PlayerGameState;
import client.main.map.Map;
import client.main.marshaller.Marshaller;
import client.main.movement.Movement;
import reactor.core.publisher.Mono;

/**
 * NetworkController
 * It is used to communicate with the server such as registering player, 
 * sending maps and moves of player and establishing the current state of the game.
 * 
 * Tasks: 
 * 1. registering player
 * 2. sending HalfMap 
 * 3. sending moves 
 * 4. getting Game State
 * 
 * Using Marshaller, Movement, ClientData & WebClient instances to process tasks
 * @author Veljko Radunovic 01528243
 */

public class NetworkController {
	
	/**
	 * defining class logger
	 */
	static Logger logger = LoggerFactory.getLogger(NetworkController.class);

	private String gameID;
	private String playerID;
	private String url;
	private WebClient baseWebClient;

	private ClientData clientData;
	private Marshaller marshaller;
	private Movement movement;

	public NetworkController(String gameID, String url) {
		this.gameID = gameID;
		this.url = url;
		this.baseWebClient = WebClient.builder().baseUrl(this.url + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

		clientData = new ClientData();
		marshaller = new Marshaller();
		movement = new Movement();
		logger.info("Wake up NetworkController for game: " + gameID + "on Server: " + url);
	}
	
	
	/**
	 * Instantiates a new player registration. 
	 * No parameters > taking all data from private variables
	 * Tasks: 
	 * 1. Generating PlayerRegistration object
	 * 2. Sending PlayerRegistration object to Server using POST request
	 */
	public void registerPlayer() {
		
		PlayerRegistration playerReg = new PlayerRegistration("Veljko", "Radunovic", "01528243");
		logger.info("[registerPlayer] PlayerRegistration object created: " + playerReg.getStudentFirstName() + " " + playerReg.getStudentLastName() + " " + playerReg.getStudentID());
		
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/players")
				.body(BodyInserters.fromValue(playerReg)).retrieve().bodyToMono(ResponseEnvelope.class);
		logger.info("[registerPlayer] Sending POST request for player registration");


		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		System.out.println(resultReg.getState());
		logger.info("[registerPlayer] Response from server: " + resultReg.getState());

		if (resultReg.getState() == ERequestState.Error)
			logger.error("[registerPlayer] Client error, errormessage: " + resultReg.getExceptionMessage());

		else {
			UniquePlayerIdentifier uniqueID = resultReg.getData().get();
			this.playerID = uniqueID.getUniquePlayerID().toString();
			System.out.println("my id: " + this.playerID);
			this.clientData.setPlayerID(this.playerID);
		}

	}

	/**
	 * Sends a previously generated map in MapController to the server.
	 * Tasks: 
	 * 1. Converting received Map object using Marshaller class to Servers HalfMap
	 * 2. Sending HalfMap object to Server using POST request
	 * @param Map map
	 */
	public void sendHalfMap(Map map) {
		
		//Check if it's my turn
		while(!clientData.getGameState().equals(PlayerGameState.MustAct)) { 
			 try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("[sendHalfMap] Check game state" +  clientData.getGameState());
		

		HalfMap halfMap = new HalfMap(this.playerID, marshaller.convertMapToServer(map));
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/halfmaps")
				.body(BodyInserters.fromValue(halfMap)) 
				.retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
		logger.info("[sendHalfMap] Response from server: " +  resultReg.getState());
	
		if (resultReg.getState() == ERequestState.Error) {
			logger.error("[sendHalfMap] HalfMap sending error -> " + resultReg.getExceptionMessage());
			System.exit(1);
		}

		else {
			logger.info("[sendHalfMap] HalfMap has been sent to server");
		}
	}

	
	
	/**
	 * Finds out by asking the server what the current state of the game is. 
	 * It extracts information such as Players and Map and GameStateID from the response envelope.
	 * Here we get information such as game state, whether the player should play, wait or it has lost or won the game 
	 * As well an information about the player gold status. After getting all information, it stores data into ClienData.
	 * Tasks: 
	 * 1. Getting GameState object using GET Request
	 * 2. Converting all data using Marshaller to ClientData
	 * 
	 * Using Marshaller, Movement, ClientData & WebClient instances to process tasks
	 * @param Map map
	 */
	public void getGameStatus() throws Exception {
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + this.gameID + "/states/" + this.playerID).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			logger.error("[getGameStatus] getGameStatus ERROR -> " + requestResult.getExceptionMessage());
		} else {
			logger.info("[getGameStatus] Updating gameState");
			GameState gameState = requestResult.getData().get();
			marshaller.marshallDataToClientData(this.clientData, gameState);
		}
	}

	
	
	/**
	 * Checks if it's the turn of the move or the game is over. 
	 * If the game continues and if the status for the player is "MustAct", the data is packed in ClientData and the map is handed over to Movement. 
	 * In Movement where movement algorithm will be calculated. 
	 * This method is used after the player has been registered and after the map has been sent
	 * Tasks: 
	 * 1. Checking the condition of the game
	 * 2. Repack map for the Movement
     * 3. Forward the calculated movement to the sendMyNextMove() method
     * 4. Repeat until the game is over
	 * Using Movement & ClientData instances to process tasks
	 */
	public void startPlaying() throws Exception {

		while (!clientData.getGameState().equals(PlayerGameState.Lost)
				|| !clientData.getGameState().equals(PlayerGameState.Won)) {

			if(clientData.getGameState().equals(PlayerGameState.MustAct)) {
				movement.setMap(clientData.getFullmap());
				sendMyNextMove(movement.calculateNextMove());
			}
		}
	}
	
	
	
	/**
	 * The purpose of this method is to send the resulting parameter from the startPlaying() method to the server.
	 * Tasks: 
	 * 1. Converting received Move object using Marshaller class to Servers EMove
	 * 2. Sending EMove object to Server using POST request
	 * Using Marshaller & WebClient instances to process tasks
	 * @param Move move
	 */
	public void sendMyNextMove(Move move) {

		EMove myNextMove = marshaller.convertMoveToServer(move);

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/moves")
				.body(BodyInserters.fromValue(PlayerMove.of(this.playerID, myNextMove))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
		System.out.println(resultReg.getState());

		if (resultReg.getState() == ERequestState.Error) {
			logger.error("[sendMyNextMove] Move sending faliled ->" + resultReg.getExceptionMessage());
			System.exit(1);
		}
		else {
			logger.info("[sendMyNextMove] Move has been sent -> " + move.toString());
			clientData.setGameState(PlayerGameState.MustWait);
		}

	}

	/**
	 * 
	 * Getters & Setters
	 * 
	*/
	
	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
