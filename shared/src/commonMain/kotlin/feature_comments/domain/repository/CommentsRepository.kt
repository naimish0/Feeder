package feature_comments.domain.repository

import feature_comments.data.remote.dto.CommentsResponseDto

interface CommentsRepository {
    suspend fun getComments(postId: String): CommentsResponseDto
}