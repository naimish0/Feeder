package feature_post_detail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDetailPersonDto(
    val id: Long,
    val name: String
)