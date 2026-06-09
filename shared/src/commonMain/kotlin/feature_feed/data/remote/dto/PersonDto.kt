package feature_feed.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class PersonDto(
    val id: Long,
    val name: String
)