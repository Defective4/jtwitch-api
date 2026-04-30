package io.github.defective4.twitch.api;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import io.github.defective4.twitch.api.model.TwitchEvent;
import io.github.defective4.twitch.api.model.WebsocketMessage;
import io.github.defective4.twitch.api.model.WebsocketSession;

public class TwitchWebsocketAPI extends TwitchWebAPI {

    public interface TwitchListener {
        void newFollower(TwitchEvent event);

        void sessionOpened(WebsocketSession message);
    }

    public TwitchWebsocketAPI(String baseURI, String clientId, String userId, char[] token) {
        super(baseURI, clientId, userId, token);
    }

    public void connectWebsocket(TwitchListener listener) throws InterruptedException {
        new WebSocketClient(URI.create(baseURI)) {
            @Override
            public void onClose(int code, String reason, boolean remote) {}

            @Override
            public void onError(Exception ex) {}

            @Override
            public void onMessage(String message) {
                WebsocketMessage msg = gson.fromJson(message, WebsocketMessage.class);
                switch (msg.metadata().messageType()) {
                    case "session_welcome" -> listener.sessionOpened(msg.payload().session());
                    case "notification" -> {
                        switch (msg.metadata().subscriptionType()) {
                            case "channel.follow" -> listener.newFollower(msg.payload().event());
                            default -> {}
                        }
                    }
                    default -> {}
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {}
        }.connectBlocking();
    }
}
