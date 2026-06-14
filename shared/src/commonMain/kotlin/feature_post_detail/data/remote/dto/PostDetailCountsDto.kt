package feature_post_detail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailCountsDto(
    @SerialName("post_id") val postId: Long,
    val comments: Long,
    val score: Long
)