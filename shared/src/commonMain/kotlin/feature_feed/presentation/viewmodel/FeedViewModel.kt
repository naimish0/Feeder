package feature_feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.model.FeedType
import feature_feed.domain.usecase.ObserveFeedUseCase
import feature_feed.domain.usecase.RefreshFeedUseCase
import feature_feed.presentation.state.FeedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val observeHotFeedUseCase: ObserveFeedUseCase,
    private val refreshHotFeedUseCase: RefreshFeedUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FeedState())
    val state: StateFlow<FeedState> = _state.asStateFlow()

    init {
        // Coroutine 1: continuously observe DB (never ends — must be separate)
        viewModelScope.launch {
            observeHotFeedUseCase.invoke(FeedType.HOT).collect { posts ->
                _state.update { it.copy(posts = posts) }
            }
        }

        // Coroutine 2: trigger first network sync
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            refreshHotFeedUseCase.invoke(FeedType.HOT)
            _state.update { it.copy(isRefreshing = false) }
        }
    }
}