package feature_comments.data.remote.api

import core.network.Endpoints
import feature_comments.data.remote.dto.CommentsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CommentsApiImpl(private val httpClient: HttpClient) : CommentsApi {
    override suspend fun getComments(postId: Long): CommentsResponseDto {
        return httpClient.get(Endpoints.Comment.LIST) {
           contentType(ContentType.Application.Json)
        }.body()
    }
}