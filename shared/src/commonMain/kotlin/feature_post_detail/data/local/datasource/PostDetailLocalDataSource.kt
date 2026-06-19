package feature_post_detail.data.local.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.feeder.database.PostQueries
import com.example.feeder.database.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class PostDetailLocalDataSource(
    private val postQueries: PostQueries
) {

    // getPostById already defined in CommentPost.sq — emits null if not cached yet
    fun observePost(postId: Long): Flow<Posts?> =
        postQueries.getPostById(postId).asFlow().mapToOneOrNull(Dispatchers.Default)

    suspend fun upsertPost(post: Posts) {
        postQueries.insertPost(
            id = post.id,
            title = post.title,
            body = post.body,
            url = post.url,
            thumbnail_url = post.thumbnail_url,
            creator_id = post.creator_id,
            creator_name = post.creator_name,
            community_id = post.community_id,
            community_name = post.community_name,
            score = post.score,
            comments_count = post.comments_count,
            published = post.published,
            saved = post.saved,
            read = post.read,
            last_updated = post.last_updated,
            last_viewed_at = post.last_viewed_at
        )
    }

    suspend fun updateLastViewed(postId: Long) {
        postQueries.updateLastViewed(
            last_viewed_at = kotlin.time.Clock.System.now().toEpochMilliseconds(), id = postId
        )
    }
}