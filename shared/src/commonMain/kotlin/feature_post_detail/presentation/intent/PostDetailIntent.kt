package feature_post_detail.presentation.intent

sealed interface PostDetailIntent {
    data object Refresh: PostDetailIntent
    data object BackClicked: PostDetailIntent
}