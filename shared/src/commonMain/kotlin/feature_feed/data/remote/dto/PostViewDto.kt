package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostViewDto(
    val post: PostDto,
    val creator: PersonDto,
    val community: CommunityDto,
    val counts: PostCountsDto,
    val saved: Boolean = false,
    val read: Boolean = false
)