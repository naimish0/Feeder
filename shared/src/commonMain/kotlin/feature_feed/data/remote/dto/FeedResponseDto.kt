package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeedResponseDto(
    val posts: List<PostViewDto>
)