package feature_post_detail.domain.usecase

import feature_post_detail.domain.model.PostDetail
import feature_post_detail.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow

class ObservePostDetailUseCase(private val repository: PostDetailRepository) {
    operator fun invoke(postId: Long): Flow<PostDetail?> = repository.observePost(postId)
}