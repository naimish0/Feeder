package feature_feed.data.remote.datasource

import feature_feed.data.remote.api.FeedApi
import feature_feed.data.remote.dto.FeedResponseDto

class FeedRemoteDataSource (private val feedApi: FeedApi) {
    suspend fun getHotPosts(after: String ?= null, limit: Int = 25): FeedResponseDto {
        return feedApi.getHotPost(after, limit)
    }
}