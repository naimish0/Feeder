package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostCountsDto(
    val score: Int,
    val comments: Int
)