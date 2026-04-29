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

public class TwitchWebAPI {

    protected final String baseURI;
    protected final String clientId;
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected final char[] token;
    protected final int userId;

    public TwitchWebAPI(String baseURI, String clientId, int userId, char[] token) {
        this.baseURI = baseURI;
        this.clientId = clientId;
        this.userId = userId;
        this.token = token;
    }

    protected JsonObject makeJSONRequest(String path, String method) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) URI.create(baseURI + path).toURL().openConnection();
            if (!method.equalsIgnoreCase("GET")) connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "Bearer %s".formatted(new String(token)));
            connection.setRequestProperty("Client-Id", clientId);
            try (Reader reader = new InputStreamReader(connection.getInputStream())) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

}
