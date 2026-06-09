package feature_feed.presentation.state

import feature_feed.domain.model.Post

data class FeedState(
    val posts: List<Post> = emptyList(),
    val isRefreshing: Boolean = false
)