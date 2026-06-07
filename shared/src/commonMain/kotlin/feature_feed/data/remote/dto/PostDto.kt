package feature_feed.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedPostDto(
    val id: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val score: Int,
    @SerialName("num_comments")
    val numComments: Int,
    @SerialName("created_utc")
    val createdUtc: Long,
    val permalink: String,
    val thumbnail: String?,
    val url: String,
    @SerialName("is_video")
    val isVideo: Boolean
)