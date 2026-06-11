package feature_feed.presentation.intent

sealed interface FeedIntent {
    object Refresh: FeedIntent
    data class PostClicked(val postId: Long): FeedIntent
}