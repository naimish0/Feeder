package feature_post_detail.domain.usecase

import feature_post_detail.domain.repository.PostDetailRepository

class RefreshPostDetailUseCase(private val repository: PostDetailRepository) {
    suspend operator fun invoke(postId: Long): Result<Unit> = repository.refreshPost(postId)
}