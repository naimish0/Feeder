package feature_feed.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityDto(
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("title")
    val title: String
)