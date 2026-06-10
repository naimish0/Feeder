package com.example.feeder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import feature_feed.domain.model.Post
import feature_feed.presentation.screen.FeedScreen
import feature_feed.presentation.state.FeedState
import feature_feed.presentation.viewmodel.FeedViewModel

@Composable
fun App(feedViewModel: FeedViewModel) {
    val state by feedViewModel.state.collectAsStateWithLifecycle()
    AppContent(
        state = state,
        onRefresh = feedViewModel::refresh,
        onPostClick = { _ ->
            // Navigation to PostDetail — wired when Navigation is added
        }
    )
}

@Composable
private fun AppContent(
    state: FeedState,
    onRefresh: () -> Unit,
    onPostClick: (Long) -> Unit
) {
    MaterialTheme {
        FeedScreen(
            state = state,
            onRefresh = onRefresh,
            onPostClick = onPostClick
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    AppContent(
        state = FeedState(
            posts = listOf(
                Post(
                    id = 1,
                    title = "Jetpack Compose Multiplatform is awesome!",
                    body = "This is a sample body for the post card. It should show how the text wraps and how the card looks with content.",
                    url = null,
                    thumbnailUrl = null,
                    creatorName = "compose_user",
                    communityName = "androiddev",
                    score = 1337,
                    commentsCount = 42,
                    published = "2023-10-27T12:00:00Z",
                    saved = true,
                    read = false
                ),
                Post(
                    id = 2,
                    title = "Kotlin Multiplatform Shared UI",
                    body = "Sharing UI code across platforms using Compose Multiplatform is a game changer for mobile development.",
                    url = null,
                    thumbnailUrl = null,
                    creatorName = "kotlin_fan",
                    communityName = "kotlin",
                    score = 950,
                    commentsCount = 18,
                    published = "2023-10-28T09:00:00Z",
                    saved = false,
                    read = true
                )
            ),
            isRefreshing = false
        ),
        onRefresh = {},
        onPostClick = {}
    )
}