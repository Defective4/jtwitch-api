package io.github.defective4.twitch.api.model;

import com.google.gson.annotations.SerializedName;

public record MessageMetadata(@SerializedName("message_type") String messageType,
        @SerializedName("subscription_type") String subscriptionType) {

}
