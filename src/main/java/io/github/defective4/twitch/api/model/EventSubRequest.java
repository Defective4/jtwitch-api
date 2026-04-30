package io.github.defective4.twitch.api.model;

public record EventSubRequest(String type, String version, Object condition, TransportObject transport) {
}
