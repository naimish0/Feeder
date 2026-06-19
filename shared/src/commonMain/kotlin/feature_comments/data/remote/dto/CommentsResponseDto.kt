package feature_comments.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponseDto(
    val comments: List<CommentViewDto>
)

@Serializable
data class CommentViewDto(
    val comment: CommentDto,
    val creator: CommentPersonDto,
    val counts: CommentCountsDto,
    val saved: Boolean = false,
    @SerialName("my_vote") val myVote: Int? = null
)

@Serializable
data class CommentDto(
    val id: Long,
    @SerialName("creator_id") val creatorId: Long,
    @SerialName("post_id") val postId: Long,
    val content: String,
    val published: String,
    val path: String,                                // "0.123" or "0.123.456.789"
    val removed: Boolean = false,
    val deleted: Boolean = false,
    val distinguished: Boolean = false,
    val updated: String? = null
)

@Serializable
data class CommentPersonDto(
    val id: Long,
    val name: String,                               // username
    @SerialName("display_name") val displayName: String? = null,
    val avatar: String? = null,
    @SerialName("bot_account") val botAccount: Boolean = false
)

@Serializable
data class CommentCountsDto(
    @SerialName("comment_id") val commentId: Long,
    val score: Long,
    val upvotes: Long = 0L,
    val downvotes: Long = 0L,
    @SerialName("child_count") val childCount: Long = 0L  // useful for collapse badge
)
