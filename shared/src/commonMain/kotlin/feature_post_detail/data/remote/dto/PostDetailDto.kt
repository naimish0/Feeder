package feature_post_detail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailDto(
    val id: Long,
    val title: String,
    val body: String?,
    val url: String?,
    val thumbnailUrl: String?,
    val creatorName: String,
    val communityName: String,
    val score: Long,
    val commentsCount: Long,
    val published: String,
    val saved: Boolean,
    val read: Boolean)