package feature_post_detail.data.remote.api

import core.network.Endpoints
import feature_post_detail.data.remote.dto.PostDetailResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostDetailApiImpl(private val client: HttpClient) : PostDetailApi {
    override suspend fun getPost(postId: Long): PostDetailResponseDto {
        return client.get(Endpoints.Post.LIST) {
            contentType(ContentType.Application.Json)
            parameter("id", postId)
        }.body()
    }
}