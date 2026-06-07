package feature_feed.data.remote.api

import feature_feed.data.remote.dto.FeedResponseDto
import feature_feed.data.remote.dto.LoginRequest
import feature_feed.data.remote.dto.LoginResponse

interface FeedApi {
    suspend fun getHotPost(after: String? = null, limit: Int =25): FeedResponseDto


}