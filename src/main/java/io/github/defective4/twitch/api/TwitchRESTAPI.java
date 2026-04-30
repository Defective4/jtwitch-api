package io.github.defective4.twitch.api;

import java.io.IOException;

import com.google.gson.JsonObject;

import io.github.defective4.twitch.api.model.AuthToken;
import io.github.defective4.twitch.api.model.ChannelFollower;
import io.github.defective4.twitch.api.model.EventSubRequest;
import io.github.defective4.twitch.api.model.TransportObject;

public class TwitchRESTAPI extends TwitchWebAPI {

    public TwitchRESTAPI(String baseURI, String clientId, String userId, char[] token) {
        super(baseURI, clientId, userId, token);
    }

    public AuthToken authorize(char[] clientSecret, String scope) throws IOException {
        JsonObject resp = makeJSONRequest(
                "/auth/authorize?client_id=%s&client_secret=%s&grant_type=user_token&user_id=%s&scope=%s"
                        .formatted(clientId, new String(clientSecret), userId, scope),
                "POST");
        return gson.fromJson(resp, AuthToken.class);
    }

    public ChannelFollower[] getChannelFollowers(int broadcasterId, int limit) throws IOException {
        JsonObject request = makeJSONRequest(
                "/mock/channels/followers?broadcaster_id=%s&first=%s".formatted(broadcasterId, limit), "GET");
        return gson.fromJson(request.get("data"), ChannelFollower[].class);
    }

    public void subscribeToEvent(String type, Object condition, String wsSession) throws IOException {
        EventSubRequest request = new EventSubRequest(type, "2", condition,
                new TransportObject("websocket", wsSession));
        makeJSONRequest("/eventsub/subscriptions", "POST", request);
    }
}
