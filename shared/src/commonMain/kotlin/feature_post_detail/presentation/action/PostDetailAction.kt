package feature_post_detail.presentation.action

sealed interface PostDetailAction {
    data object ObservePost : PostDetailAction
    data object RefreshPost : PostDetailAction
    data object NavigateBack : PostDetailAction
}