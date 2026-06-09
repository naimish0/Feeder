package feature_feed.data.remote.api

import core.network.Endpoints
import feature_feed.data.remote.dto.FeedResponseDto
import feature_feed.data.remote.dto.PostViewDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class FeedApiImpl(private val client: HttpClient) : FeedApi {
    override suspend fun getHotPost(
        after: String?, limit: Int
    ): FeedResponseDto {
        return client.get(Endpoints.Post.LIST) {
                contentType(ContentType.Application.Json)
            }.body()
    }
}