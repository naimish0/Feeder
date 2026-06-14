package feature_post_detail.presentation.state

import feature_post_detail.domain.model.PostDetail

data class PostDetailState(
    val post: PostDetail? = null,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)