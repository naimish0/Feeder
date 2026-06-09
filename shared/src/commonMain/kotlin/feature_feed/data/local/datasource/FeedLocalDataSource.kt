package feature_feed.data.local.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.feeder.database.FeedPostsQueries
import com.example.feeder.database.FeederDatabase
import com.example.feeder.database.PostQueries
import com.example.feeder.database.Posts
import core.model.FeedType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock

class FeedLocalDataSource(
    private val database: FeederDatabase,
    private val postQueries: PostQueries,
    private val feedPostsQueries: FeedPostsQueries
) {

    fun observeFeed(
        feedType: FeedType
    ): Flow<List<Posts>> {
        return feedPostsQueries.getPostsForFeed(feedType.name).asFlow()
            .mapToList(Dispatchers.Default)
    }

    suspend fun replaceFeed(
        feedType: FeedType, posts: List<Posts>
    ) {
        database.transaction {
            posts.forEach { post ->
                print("Naimish: replaceFeed, Post: $post")
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
            feedPostsQueries.deleteFeedMappings(
                feed_type = feedType.name
            )
            posts.forEach { post ->
                feedPostsQueries.insertFeedMapping(
                    feed_type = feedType.name, post_id = post.id
                )
            }
        }
    }

    suspend fun updateSavedState(
        postId: Long, saved: Boolean
    ) {
        postQueries.updateSavedState(
            saved = if (saved) 1L else 0L, id = postId
        )
    }

    suspend fun updateLastViewed(
        postId: Long
    ) {
        postQueries.updateLastViewed(
            last_viewed_at = Clock.System.now().toEpochMilliseconds(), id = postId
        )
    }
}