package feature_comments.data.remote.api

import feature_comments.data.remote.dto.CommentsResponseDto

interface CommentsApi {
    suspend fun getComments(postId: Long): List<CommentsResponseDto>
}