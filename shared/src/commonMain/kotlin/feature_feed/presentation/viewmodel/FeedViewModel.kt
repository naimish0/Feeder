package feature_feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.model.FeedType
import feature_feed.domain.model.Post
import feature_feed.domain.usecase.ObserveFeedUseCase
import feature_feed.domain.usecase.RefreshFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val observeHotFeedUseCase: ObserveFeedUseCase,
    private val refreshHotFeedUseCase: RefreshFeedUseCase,
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        // Coroutine 1: continuously observe DB (never ends — must be separate)
        viewModelScope.launch {
            observeHotFeedUseCase.invoke(FeedType.HOT).collect { posts ->
                _posts.value = posts
            }
        }

        // Coroutine 2: trigger first network sync
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            refreshHotFeedUseCase.invoke(FeedType.HOT)
            _isRefreshing.value = false
        }
    }
}