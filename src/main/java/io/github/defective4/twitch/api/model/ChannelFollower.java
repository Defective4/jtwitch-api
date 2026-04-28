package io.github.defective4.twitch.api.model;

import com.google.gson.annotations.SerializedName;

public record ChannelFollower(@SerializedName("user_id") String userId, @SerializedName("user_login") String userLogin,
        @SerializedName("user_name") String userName, @SerializedName("followed_at") String followedAt) {

}
