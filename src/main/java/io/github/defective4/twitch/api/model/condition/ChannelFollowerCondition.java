package io.github.defective4.twitch.api.model.condition;

import com.google.gson.annotations.SerializedName;

public record ChannelFollowerCondition(@SerializedName("broadcaster_user_id") String broadcasterId,
        @SerializedName("moderator_user_id") String moderatorId) {
}
