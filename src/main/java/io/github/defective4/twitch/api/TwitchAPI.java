package io.github.defective4.twitch.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.defective4.twitch.api.model.AuthToken;
import io.github.defective4.twitch.api.model.ChannelFollower;

public class TwitchAPI {
    private final char[] appToken;
    private final String baseURL;
    private final String clientId;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final int userId;
    private final char[] userToken;

    public TwitchAPI(String baseURL, String clientId, char[] userToken, char[] appToken, int userId) {
        this.baseURL = baseURL;
        this.clientId = clientId;
        this.userToken = userToken;
        this.appToken = appToken;
        this.userId = userId;
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

    private JsonObject makeJSONRequest(String path, String method) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) URI.create(baseURL + path).toURL().openConnection();
            if (!method.equalsIgnoreCase("GET")) connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "Bearer %s".formatted(new String(userToken)));
            connection.setRequestProperty("Client-Id", clientId);
            try (Reader reader = new InputStreamReader(connection.getInputStream())) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        } finally {
            if (connection != null) connection.disconnect();
        }
    }
}
