package io.github.defective4.twitch.api.model;

public record EndpointPayload(WebsocketSession session, TwitchEvent event) {
}
