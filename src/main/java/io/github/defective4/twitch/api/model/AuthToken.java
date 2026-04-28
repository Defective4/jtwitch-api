package io.github.defective4.twitch.api.model;

import com.google.gson.annotations.SerializedName;

public record AuthToken(@SerializedName("access_token") String accessToken,
        @SerializedName("expires_in") long expiresIn, @SerializedName("token_type") String tokenType) {

}
