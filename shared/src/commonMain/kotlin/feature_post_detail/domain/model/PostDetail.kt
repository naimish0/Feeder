package feature_post_detail.domain.model

// Separate from feature_feed.domain.model.CommentPost by design —
// PostDetail can diverge (more fields, different structure) as the feature grows
data class PostDetail(
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
    val read: Boolean
)