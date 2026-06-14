package feature_post_detail.presentation.result

import feature_post_detail.domain.model.PostDetail

sealed interface PostDetailResult {
    data class PostLoaded(val post: PostDetail?) : PostDetailResult
    data object RefreshStarted : PostDetailResult
    data object RefreshCompleted : PostDetailResult
    data class RefreshFailed(val message: String) : PostDetailResult
}