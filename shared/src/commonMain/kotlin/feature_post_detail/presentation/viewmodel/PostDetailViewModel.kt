package feature_post_detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.architecture.BaseViewModel
import feature_post_detail.domain.usecase.ObservePostDetailUseCase
import feature_post_detail.domain.usecase.RefreshPostDetailUseCase
import feature_post_detail.presentation.action.PostDetailAction
import feature_post_detail.presentation.intent.PostDetailIntent
import feature_post_detail.presentation.navigation.PostDetailNavigation
import feature_post_detail.presentation.result.PostDetailResult
import feature_post_detail.presentation.state.PostDetailState
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: Long,
    private val observePostDetailUseCase: ObservePostDetailUseCase,
    private val refreshPostDetailUseCase: RefreshPostDetailUseCase
) : BaseViewModel<PostDetailIntent, PostDetailAction, PostDetailResult, PostDetailState, PostDetailNavigation>(
    initialState = PostDetailState()
) {

    init {
        viewModelScope.launch { handleAction(PostDetailAction.ObservePost) }
        dispatch(PostDetailIntent.Refresh)
    }

    override fun toAction(intent: PostDetailIntent): PostDetailAction = when (intent) {
        PostDetailIntent.Refresh      -> PostDetailAction.RefreshPost
        PostDetailIntent.BackClicked  -> PostDetailAction.NavigateBack
    }

    // Block body — has early return guard
    override suspend fun handleAction(action: PostDetailAction) {
        when (action) {

            PostDetailAction.ObservePost -> {
                observePostDetailUseCase(postId).collect { post ->
                    emitResult(PostDetailResult.PostLoaded(post))
                }
            }

            PostDetailAction.RefreshPost -> {
                if (currentState.isRefreshing) return
                emitResult(PostDetailResult.RefreshStarted)
                refreshPostDetailUseCase(postId)
                    .onSuccess { emitResult(PostDetailResult.RefreshCompleted) }
                    .onFailure { error ->
                        emitResult(
                            PostDetailResult.RefreshFailed(
                                error.message ?: "Failed to load post"
                            )
                        )
                    }
            }

            PostDetailAction.NavigateBack -> {
                navigate(PostDetailNavigation.Back)
            }
        }
    }

    // Expression body — no early returns
    override fun reduce(state: PostDetailState, result: PostDetailResult): PostDetailState =
        when (result) {
            is PostDetailResult.PostLoaded    -> state.copy(post = result.post)
            PostDetailResult.RefreshStarted   -> state.copy(isRefreshing = true, errorMessage = null)
            PostDetailResult.RefreshCompleted -> state.copy(isRefreshing = false)
            is PostDetailResult.RefreshFailed -> state.copy(isRefreshing = false, errorMessage = result.message)
        }
}