package feature_post_detail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDetailViewDto(
    val post: PostDetailDto,
    val creator: PostDetailPersonDto,
    val community: PostDetailCommunityDto,
    val counts: PostDetailCountsDto,
    val saved: Boolean = false,
    val read: Boolean = false
)