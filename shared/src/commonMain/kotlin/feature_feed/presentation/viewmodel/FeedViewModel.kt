package feature_feed.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import core.architecture.BaseViewModel
import core.model.FeedType
import feature_feed.domain.usecase.ObserveFeedUseCase
import feature_feed.domain.usecase.RefreshFeedUseCase
import feature_feed.presentation.action.FeedAction
import feature_feed.presentation.intent.FeedIntent
import feature_feed.presentation.navigation.FeedNavigation
import feature_feed.presentation.result.FeedResult
import feature_feed.presentation.state.FeedState
import kotlinx.coroutines.launch

class FeedViewModel(
    private val observeFeedUseCase: ObserveFeedUseCase,
    private val refreshFeedUseCase: RefreshFeedUseCase,
) : BaseViewModel<FeedIntent, FeedAction, FeedResult, FeedState, FeedNavigation>(
    initialState = FeedState()
) {

    init {
        viewModelScope.launch { handleAction(FeedAction.ObserveFeed) }
        dispatch(FeedIntent.Refresh)
    }

    override fun toAction(intent: FeedIntent): FeedAction = when (intent) {
        FeedIntent.Refresh -> FeedAction.RefreshFeed
        is FeedIntent.PostClicked -> FeedAction.NavigateToPost(intent.postId)
    }

    // Block body { } instead of expression body = when()
    // This makes return@when unnecessary — use plain return instead
    override suspend fun handleAction(action: FeedAction) {
        when (action) {
            FeedAction.ObserveFeed -> {
                observeFeedUseCase(FeedType.HOT).collect { posts ->
                    emitResult(FeedResult.PostsUpdated(posts))
                }
            }

            FeedAction.RefreshFeed -> {
                if (currentState.isRefreshing) return
                emitResult(FeedResult.RefreshStarted)
                refreshFeedUseCase(FeedType.HOT).onSuccess { emitResult(FeedResult.RefreshCompleted) }
                    .onFailure { error ->
                        emitResult(FeedResult.RefreshFailed(error.message ?: "Failed to load posts"))
                    }
            }

            is FeedAction.NavigateToPost -> {
                navigate(FeedNavigation.ToPostDetail(action.postId))
            }
        }
    }

    override fun reduce(state: FeedState, result: FeedResult): FeedState = when (result) {
        is FeedResult.PostsUpdated -> state.copy(posts = result.posts, errorMessage = null)
        FeedResult.RefreshStarted -> state.copy(isRefreshing = true, errorMessage = null)
        FeedResult.RefreshCompleted -> state.copy(isRefreshing = false)
        is FeedResult.RefreshFailed -> state.copy(isRefreshing = false, errorMessage = result.message)
    }
}