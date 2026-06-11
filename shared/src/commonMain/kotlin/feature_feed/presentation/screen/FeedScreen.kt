package feature_feed.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import feature_feed.domain.model.Post
import feature_feed.presentation.component.PostCard
import feature_feed.presentation.intent.FeedIntent
import feature_feed.presentation.state.FeedState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    state: FeedState, onIntent: (FeedIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        TopAppBar(
            title = { Text("Feeder", style = MaterialTheme.typography.titleLarge) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
    }) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(FeedIntent.Refresh) },
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            when {
                state.posts.isEmpty() && !state.isRefreshing -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pull down to load posts",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(items = state.posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onClick = { onIntent(FeedIntent.PostClicked(post.id)) })
                            HorizontalDivider(
                                thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedScreenLoadingPreview() {
    MaterialTheme {
        FeedScreen(
            state = FeedState(isRefreshing = true), onIntent = {})
    }
}

@Preview
@Composable
private fun FeedScreenEmptyPreview() {
    MaterialTheme {
        FeedScreen(
            state = FeedState(isRefreshing = false), onIntent = {})
    }
}

@Preview
@Composable
private fun FeedScreenPopulatedPreview() {
    MaterialTheme {
        FeedScreen(
            state = FeedState(
                isRefreshing = false, posts = previewPosts()
            ), onIntent = {})
    }
}

@Preview
@Composable
private fun FeedScreenErrorPreview() {
    MaterialTheme {
        FeedScreen(
            state = FeedState(
                isRefreshing = false, posts = previewPosts(), errorMessage = "Failed to load posts"
            ), onIntent = {})
    }
}

// Shared preview data — private to this file, not exported
private fun previewPosts() = listOf(
    Post(
        id = 1,
        title = "Jetpack Compose Multiplatform is awesome!",
        body = "This is a sample body. It shows how the text wraps inside the card.",
        url = null,
        thumbnailUrl = "https://example.com/thumb.jpg",
        creatorName = "compose_user",
        communityName = "androiddev",
        score = 1337,
        commentsCount = 42,
        published = "2024-01-10T12:00:00Z",
        saved = true,
        read = false
    ), Post(
        id = 2,
        title = "Kotlin Multiplatform Shared UI",
        body = null,
        url = "https://kotlinlang.org",
        thumbnailUrl = null,
        creatorName = "kotlin_fan",
        communityName = "kotlin",
        score = 950,
        commentsCount = 18,
        published = "2024-01-09T09:00:00Z",
        saved = false,
        read = true
    )
)