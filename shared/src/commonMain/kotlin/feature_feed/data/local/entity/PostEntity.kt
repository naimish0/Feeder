package feature_feed.data.local.entity

data class PostEntity(
    val id: Long,
    val title: String,
    val body: String?,
    val creatorName: String,
    val communityName: String,
    val score: Int,
    val commentsCount: Int,
    val published: String,
    val thumbnailUrl: String?
)