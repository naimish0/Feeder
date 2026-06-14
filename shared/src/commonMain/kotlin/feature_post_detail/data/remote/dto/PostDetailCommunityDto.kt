package feature_post_detail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDetailCommunityDto(
    val id: Long,
    val name: String,
    val title: String
)
