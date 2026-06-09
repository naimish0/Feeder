package core.network

object Endpoints {

    object Auth {
        const val LOGIN = "api/v3/user/login"
    }

    object Post {
        const val LIST = "api/v3/post/list"
        const val CREATE = "post"
    }

    object Community {
        const val LIST = "community/list"
    }

    object Comment {
        const val LIST = "comment/list"
    }
}