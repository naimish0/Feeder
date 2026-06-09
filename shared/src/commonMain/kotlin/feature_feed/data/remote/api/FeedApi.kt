package feature_feed.data.remote.api

import feature_feed.data.remote.dto.FeedResponseDto

interface FeedApi {
    suspend fun getHotPost(after: String? = null, limit: Int = 25): FeedResponseDto
}