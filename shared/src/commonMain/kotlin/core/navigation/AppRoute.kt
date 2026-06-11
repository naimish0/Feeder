package core.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object Loading: AppRoute
    @Serializable
    data object Feed: AppRoute
    @Serializable
    data object Login: AppRoute
    @Serializable
    data class PostDetail(val postId: Long): AppRoute
}