package feature_feed.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Long,

    @SerialName("name")
    val title: String,

    val body: String? = null,

    val url: String? = null,

    @SerialName("thumbnail_url")
    val thumbnailUrl: String? = null,

    val published: String
)