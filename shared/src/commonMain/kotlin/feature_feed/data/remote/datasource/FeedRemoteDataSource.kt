package feature_feed.data.remote.datasource

import feature_feed.data.remote.api.FeedApi
import feature_feed.data.remote.dto.FeedResponseDto

class FeedRemoteDataSource(private val feedApi: FeedApi) {
    suspend fun getHotPosts(after: String? = null, limit: Int = 25): FeedResponseDto {
        println("Naimish: getHotPosts before")
        try {
            val response = feedApi.getHotPost(after, limit)
            println("Naimish: after api call")
            return response
        } catch (e: Exception) {
            println("Naimish: API Exception = $e")
            e.printStackTrace()
            throw e
        }
    }
}