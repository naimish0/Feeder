package feature_comments.domain.repository

import feature_comments.data.remote.dto.CommentDto

interface CommentsRepository {
    suspend fun getComments(postId: String): List<CommentDto>
}