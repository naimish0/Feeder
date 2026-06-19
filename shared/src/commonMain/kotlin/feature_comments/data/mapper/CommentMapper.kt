package feature_comments.data.mapper

import com.example.feeder.database.Comments
import feature_comments.data.remote.dto.CommentViewDto

fun CommentViewDto.toDbModel(): Comments {
    // path "0.100.200.300"
    // segments = ["0", "100", "200", "300"]
    // parent = segments[size-2] if size >= 3, else null
    val segments = comment.path.split(".")
    val parentCommentId = if (segments.size >= 3) {
        segments[segments.size - 2].toLongOrNull()
    } else null

    return Comments(
        id = comment.id,
        post_id = comment.postId,
        creator_id = creator.id,
        creator_name = creator.displayName ?: creator.name,  // prefer display name
        content = comment.content,
        score = counts.score,
        published = comment.published,
        parent_comment_id = parentCommentId,
        path = comment.path
    )
}

fun Comments.toDomainModel(): feature_comments.domain.model.Comment {
    return feature_comments.domain.model.Comment(
        id = id,
        post_id = post_id,
        creator_id = creator_id,
        creator_name = creator_name,
        content = content,
        score = score,
        published = published,
        parent_comment_id = parent_comment_id,
        path = path
    )
}
