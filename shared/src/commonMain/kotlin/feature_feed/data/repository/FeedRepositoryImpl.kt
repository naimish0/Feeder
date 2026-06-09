package feature_feed.data.repository

import core.model.FeedType
import feature_feed.data.local.datasource.FeedLocalDataSource
import feature_feed.data.mapper.toDbModel
import feature_feed.data.mapper.toDomainModel
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import feature_feed.domain.model.Post
import feature_feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedRepositoryImpl(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource
) : FeedRepository {

    override fun observeHotFeed(feedType: FeedType): Flow<List<Post>> {
        return localDataSource.observeFeed(FeedType.HOT).map { entities ->
                entities.map { entity ->
                    println("Naimish: observeHotFeed entity: $entity")
                    entity.toDomainModel()
                }
            }
    }

    override suspend fun refreshHotFeed(feedType: FeedType): Result<Unit> {
        return runCatching {
            val response = remoteDataSource.getHotPosts()
            println("Naimish: refreshHotFeed Response: $response")
            localDataSource.replaceFeed(
                feedType = FeedType.HOT, posts = response.posts.map {
                    it.toDbModel()
                })
        }
    }
}