package feature_post_detail.presentation.navigation

sealed interface PostDetailNavigation {
    data object Back : PostDetailNavigation
}