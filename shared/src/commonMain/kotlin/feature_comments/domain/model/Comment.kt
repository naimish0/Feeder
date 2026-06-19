package feature_comments.domain.model

 data class Comment(
    val id: Long,
    val post_id: Long,
    val creator_id: Long?,
    val creator_name: String,
    val content: String,
    val score: Long,
    val published: String,
    val parent_comment_id: Long?,
    val path: String
)
