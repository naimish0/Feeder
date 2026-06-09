package feature_feed.domain.repository

import core.model.FeedType
import feature_feed.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun observeHotFeed(feedType: FeedType): Flow<List<Post>>

    suspend fun refreshHotFeed(feedType: FeedType): Result<Unit>
}