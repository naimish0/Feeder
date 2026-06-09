package feature_feed.data.local.datasource

import com.example.feeder.database.Posts
import kotlinx.coroutines.flow.Flow

interface FeedLocalDataSource {

    fun observePosts(): Flow<List<Posts>>

    suspend fun replacePosts(
        posts: List<Posts>
    )
}