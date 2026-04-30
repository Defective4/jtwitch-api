package io.github.defective4.twitch.api.model;

import com.google.gson.annotations.SerializedName;

public record TransportObject(String method, @SerializedName("session_id") String sessionId) {

}
