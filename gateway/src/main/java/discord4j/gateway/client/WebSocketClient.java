/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J. If not, see <http://www.gnu.org/licenses/>.
 */
package discord4j.gateway.client;

import discord4j.gateway.WebSocketHandler;
import discord4j.gateway.adapter.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;

import java.net.URI;

/**
 * WebSocket client over Reactor Netty.
 */
public class WebSocketClient {

	private final HttpClient httpClient;

	public WebSocketClient() {
		this(HttpClient.create());
	}

	public WebSocketClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Mono<Void> execute(URI url, WebSocketHandler handler) {
		return this.httpClient
				.ws(url.toString(), headers -> {}, null)
				.flatMap(response ->
						response.receiveWebsocket((in, out) ->
								handler.handle(new WebSocketSession(in, out))));
	}

}
