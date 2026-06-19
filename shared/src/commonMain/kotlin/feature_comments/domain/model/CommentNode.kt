package feature_comments.domain.model

data class CommentNode(
    val comment: Comment,
    val depth: Int = 0,
    val isCollapsed: Boolean = false,
    val isHidden: Boolean = false
)
