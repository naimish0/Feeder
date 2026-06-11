package com.example.feeder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import core.navigation.AppNavigation
import feature_feed.domain.model.Post
import feature_feed.presentation.intent.FeedIntent
import feature_feed.presentation.screen.FeedScreen
import feature_feed.presentation.state.FeedState

@Composable
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}

@Composable
private fun AppContent(
    state: FeedState, onIntent: (FeedIntent) -> Unit
) {
    MaterialTheme {
        FeedScreen(
            state = state, onIntent = onIntent
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
            ), Post(
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
        ), isRefreshing = false
    ), onIntent = {})
}