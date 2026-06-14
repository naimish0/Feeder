package feature_feed.presentation.component   // ← component, not screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import core.util.formatCount
import core.util.toTimeAgo
import feature_feed.domain.model.Post

@Composable
fun PostCard(
    post: Post, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Surface(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {

            // Row 1 — Community · Author
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
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Row 2 — Title + Thumbnail placeholder
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                if (post.thumbnailUrl != null) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier.size(72.dp).clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        // IMG placeholder — replace with Coil3/Kamel when image loading is added
                        AsyncImage(
                            model = post.thumbnailUrl,
                            contentDescription = "Thumbnail for ${post.title}",
                            modifier = Modifier.fillMaxWidth().height(72.dp)
                        )
                    }
                }
            }

            // Body preview — only shown when present
            if (!post.body.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 3 — Score · Comments · Time · Saved
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
                    text = "💬 ${post.commentsCount.formatCount()}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = post.published.toTimeAgo(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (post.saved) {
                    Text(
                        text = "🔖", style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PostCardWithThumbnailPreview() {
    MaterialTheme {
        PostCard(
            post = Post(
                id = 1,
                title = "Jetpack Compose Multiplatform is awesome!",
                body = "This is a sample body showing how text wraps inside the card.",
                url = null,
                thumbnailUrl = "https://example.com/thumb.jpg",  // triggers placeholder box
                creatorName = "compose_user",
                communityName = "androiddev",
                score = 1337,
                commentsCount = 42,
                published = "2024-01-10T12:00:00Z",
                saved = true,
                read = false
            ), onClick = {}, modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun PostCardNoThumbnailPreview() {
    MaterialTheme {
        PostCard(
            post = Post(
                id = 2,
                title = "Kotlin Multiplatform Shared UI — a longer title that wraps to two lines",
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
            ), onClick = {}, modifier = Modifier.padding(16.dp)
        )
    }
}