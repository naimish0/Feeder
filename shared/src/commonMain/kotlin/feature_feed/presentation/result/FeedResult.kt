package feature_feed.presentation.result

import feature_feed.domain.model.Post

sealed interface FeedResult {
    data class PostsUpdated(val posts: List<Post>) : FeedResult
    data object RefreshStarted : FeedResult
    data object RefreshCompleted : FeedResult
    data class RefreshFailed(val message: String) : FeedResult
}