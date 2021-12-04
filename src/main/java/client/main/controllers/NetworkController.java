/**
	 * Network Controller
	 * @author Veljko Radunovic 01528243
	 */
package client.main.controllers;

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

public class NetworkController {

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
	}

	public void registerPlayer() {
		PlayerRegistration playerReg = new PlayerRegistration("Veljko", "Radunovic", "01528243");
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/players")
				.body(BodyInserters.fromValue(playerReg)).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		System.out.println(resultReg.getState());

		if (resultReg.getState() == ERequestState.Error)
			System.out.println("Client error, errormessage: " + resultReg.getExceptionMessage());

		else {
			UniquePlayerIdentifier uniqueID = resultReg.getData().get();
			this.playerID = uniqueID.getUniquePlayerID().toString();
			System.out.println("my id: " + this.playerID);
			this.clientData.setPlayerID(this.playerID);
		}

	}

	public void sendHalfMap(Map map) {
		HalfMap halfMap = new HalfMap(this.playerID, marshaller.convertMapToServer(map));
		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/halfmaps")
				.body(BodyInserters.fromValue(halfMap)) // specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
		System.out.println(resultReg.getState());

		if (resultReg.getState() == ERequestState.Error)
			System.out.println("HALF MAP SENDING FAILED -> " + resultReg.getExceptionMessage());

		else {
			System.out.println("HALF MAP HAS BEEN SENT TO SERVER");
			// logger.info("[ ---- MAP WAS SENT ----- ]");
		}
	}

	public void getGameStatus() throws Exception {

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + this.gameID + "/states/" + this.playerID).retrieve().bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			System.out.println("getGameStatus ERROR -> " + requestResult.getExceptionMessage());
		} else {
			GameState gameState = requestResult.getData().get();
			marshaller.marshallDataToClientData(this.clientData, gameState);
		}
	}

	public void startPlaying() throws Exception {
		Thread.sleep(400);
		getGameStatus();

		while (!clientData.getGameState().equals(PlayerGameState.Lost)
				|| !clientData.getGameState().equals(PlayerGameState.Won)) {

			movement.setMap(clientData.getFullmap());
			sendMyNextMove(movement.calculateNextMove());

			Thread.sleep(400);
			getGameStatus();
		}
	}

	public void sendMyNextMove(Move move) {

		EMove myNextMove = marshaller.convertMoveToServer(move);

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/moves")
				.body(BodyInserters.fromValue(PlayerMove.of(this.playerID, myNextMove))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
		System.out.println(resultReg.getState());

		if (resultReg.getState() == ERequestState.Error)
			System.out.println("MOVE SENDING FAILED -> " + resultReg.getExceptionMessage());

		else {
			System.out.println("MY MOVE HAS BEEN SENT");
		}

	}

	// --------------Getters & Setters----------------------------- //

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
