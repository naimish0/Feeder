package feature_comments.data.repository

import core.network.Endpoints
import feature_comments.data.remote.dto.CommentDto
import feature_comments.domain.repository.CommentsRepository

interface CommentsRepositoryImpl : CommentsRepository {
    override suspend fun getComments(postId: String): List<CommentDto> {
       TODO()
    }
}