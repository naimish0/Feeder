package feature_post_detail.data.remote.api

import feature_post_detail.data.remote.dto.PostDetailResponseDto

interface PostDetailApi {
    suspend fun getPost(postId: Long): PostDetailResponseDto
}