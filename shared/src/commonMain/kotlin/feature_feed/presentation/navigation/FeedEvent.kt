package feature_feed.presentation.navigation

sealed interface FeedNavigation {
    data class ToPostDetail(val postId: Long) : FeedNavigation
}