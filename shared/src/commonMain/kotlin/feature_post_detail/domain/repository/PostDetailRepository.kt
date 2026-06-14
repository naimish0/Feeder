package feature_post_detail.domain.repository

import feature_post_detail.domain.model.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostDetailRepository {
    fun observePost(postId: Long): Flow<PostDetail?>
    suspend fun refreshPost(postId: Long): Result<Unit>
}