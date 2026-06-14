package feature_post_detail.data.remote.datasource

import feature_post_detail.data.remote.api.PostDetailApi
import feature_post_detail.data.remote.dto.PostDetailResponseDto

class PostDetailRemoteDataSource(private val api: PostDetailApi) {
    suspend fun getPost(postId: Long): PostDetailResponseDto = api.getPost(postId)
}