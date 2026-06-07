package feature_feed.data.remote.api

import feature_feed.data.remote.dto.FeedResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText

class FeedApiImpl(private val client: HttpClient) : FeedApi {
    override suspend fun getHotPost(
        after: String?, limit: Int
    ): FeedResponseDto {
       return client.get("r/androiddev/hot") {
            parameter("limit", limit)
            after?.let {
                parameter("after", it)
            }
        }.body()
    }
}