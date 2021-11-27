package client.main.connection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.ERequestState;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import reactor.core.publisher.Mono;

public class NetworkConnector {

	private String gameID;
	private String playerID;
	private String url;
	private WebClient baseWebClient;

	public NetworkConnector(String gameID, String url) {
		this.gameID = gameID;
		this.url = url;
		// http://swe1.wst.univie.ac.at:18235/games
		this.baseWebClient = WebClient.builder().baseUrl(this.url + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

	}

	public void registerPlayer() {
		PlayerRegistration playerReg = new PlayerRegistration("aaaaa", "bbbbbb", "1234567");
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/players")
				.body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

		// WebClient support asynchronous message exchange, in SE1 we use a synchronous
		// one for the sake of simplicity. So calling block is fine.
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		// always check for errors, and if some are reported at least print them to the
		// console (logging should be preferred)
		// so that you become aware of them during debugging! The provided server gives
		// you very helpful error messages.
		if (resultReg.getState() == ERequestState.Error) {
			// typically happens if you forgot to create a new game before the client
			// execution or
			// forgot to adapt the run configuration so that it supplies the id of the new
			// game to the client
			// open http://swe1.wst.univie.ac.at:18235/games in your browser to create a new
			// game and obtain its game id
			System.out.println("Client error, errormessage: " + resultReg.getExceptionMessage());
		} else {
			UniquePlayerIdentifier uniqueID = resultReg.getData().get();
			// System.out.println("My Player ID:" + uniqueID.getUniquePlayerID());
			this.playerID = uniqueID.getUniquePlayerID().toString();
			System.out.println("my id: " + this.playerID);
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
