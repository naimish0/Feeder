package feature_feed.presentation.action

sealed interface FeedAction {
    data object ObserveFeed : FeedAction
    data object RefreshFeed : FeedAction
    data class NavigateToPost(val postId: Long) : FeedAction
}