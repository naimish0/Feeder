package feature_post_detail.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import core.util.formatCount
import core.util.toTimeAgo
import feature_post_detail.domain.model.PostDetail
import feature_post_detail.presentation.intent.PostDetailIntent
import feature_post_detail.presentation.state.PostDetailState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    state: PostDetailState, onIntent: (PostDetailIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        TopAppBar(
            title = {
            Text(text = state.post?.let { "c/${it.communityName}" } ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
        }, navigationIcon = {
            IconButton(onClick = { onIntent(PostDetailIntent.BackClicked) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
        )
    }) { paddingValues ->
        when (state.post) {
            null if state.isRefreshing -> {
                CircularProgressIndicator()
            }

            // ── Error — no post, not loading
            null if !state.isRefreshing -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CommentPost not found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ── Content — subsequent refresh uses PullToRefresh indicator
            else -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { onIntent(PostDetailIntent.Refresh) },
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                ) {
                    state.post?.let { post ->
                        PostDetailContent(post = post)
                    }
                }
            }
        }
    }
}

@Composable
private fun PostDetailContent(post: PostDetail) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 32.dp)
    ) {

        // ── Meta — community · author · time ─────────────────────────
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "c/${post.communityName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "  ·  u/${post.creatorName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "  ·  ${post.published.toTimeAgo()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(12.dp))

                // ── Title ─────────────────────────────────────────────
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // ── Body ──────────────────────────────────────────────
                if (!post.body.isNullOrBlank()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // ── URL ───────────────────────────────────────────────
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = post.thumbnailUrl ?: post.url,
                    contentDescription = "CommentPost thumbnail",
                    modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)
                )

                Spacer(Modifier.height(16.dp))

                // ── Score · Comment ──────────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "▲ ${post.score.formatCount()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "💬 ${post.commentsCount.formatCount()} Comment",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (post.saved) {
                        Text(
                            text = "🔖 Saved",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // ── Divider ───────────────────────────────────────────────────
        item {
            HorizontalDivider(
                thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        // ── Comment section header ────────────────────────────────────
        item {
            Text(
                text = "Comment",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        // ── Comment placeholder ───────────────────────────────────────
        item {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Comment coming soon",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun PostDetailScreenPreview() {
    MaterialTheme {
        PostDetailScreen(
            state = PostDetailState(
                post = PostDetail(
                    id = 1L,
                    title = "Kotlin Multiplatform is now stable",
                    body = "JetBrains announced today that Kotlin Multiplatform is production-ready. This is a huge milestone for the ecosystem.",
                    url = "https://kotlinlang.org",
                    thumbnailUrl = "https://lemmy.ml/api/v3/image_proxy?url=https%3A%2F%2Flemmy.pt%2Fpictrs%2Fimage%2FgIPQUt3mxw.png",
                    creatorName = "jetbrains",
                    communityName = "kotlin",
                    score = 1200L,
                    commentsCount = 48L,
                    published = "2024-01-01T12:00:00Z",
                    saved = false,
                    read = true
                )
            ), onIntent = {})
    }
}