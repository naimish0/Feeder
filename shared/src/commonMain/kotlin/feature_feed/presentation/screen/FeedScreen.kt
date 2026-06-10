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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import feature_feed.domain.model.Post
import feature_feed.presentation.state.FeedState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    state: FeedState,
    onRefresh: () -> Unit,
    onPostClick: (postId: Long) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Feeder",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // Empty state — DB has nothing yet
                state.posts.isEmpty() && !state.isRefreshing -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pull down to load posts",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Feed list
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(
                            items = state.posts,
                            key = { it.id }
                        ) { post ->
                            PostCard(
                                post = post,
                                onClick = { onPostClick(post.id) }
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground
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
private fun FeedScreenPreview() {
    MaterialTheme {
        FeedScreen(
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
            onRefresh = {}
        )
    }
}

@Preview
@Composable
private fun FeedScreenEmptyPreview() {
    MaterialTheme {
        FeedScreen(
            state = FeedState(
                posts = emptyList(),
                isRefreshing = false
            ),
            onRefresh = {}
        )
    }
}
