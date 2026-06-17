package feature_comments.data.remote.dto

data class CommentDto(
    val id: String,
    val content: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val createdAt: String)