package feature_comments.data.repository

import feature_comments.data.remote.dto.CommentsResponseDto
import feature_comments.domain.repository.CommentsRepository

interface CommentsRepositoryImpl : CommentsRepository {
    override suspend fun getComments(postId: String): CommentsResponseDto {
        TODO("Not yet implemented")
    }
}