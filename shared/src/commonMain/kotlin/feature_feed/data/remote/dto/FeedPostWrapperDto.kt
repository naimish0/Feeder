package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeedPostWrapperDto(
    val kind: String,
    val data: FeedPostDto
)