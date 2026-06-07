package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeedDataDto(
    val after: String?,
    val before: String?,
    val children: List<FeedPostWrapperDto>
)