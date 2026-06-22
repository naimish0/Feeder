package com.example.feeder

import core.model.FeedType
import feature_feed.domain.model.Post
import feature_feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFeedRepository(
    private val posts: List<Post>,
    private val refreshResult: Result<Unit>
) : FeedRepository {

    val observedFeedTypes = mutableListOf<FeedType>()
    val refreshedFeedTypes = mutableListOf<FeedType>()

    override fun observeHotFeed(feedType: FeedType): Flow<List<Post>> {
        observedFeedTypes += feedType
        return flowOf(posts)
    }

    override suspend fun refreshHotFeed(feedType: FeedType): Result<Unit> {
        refreshedFeedTypes += feedType
        return refreshResult
    }
}