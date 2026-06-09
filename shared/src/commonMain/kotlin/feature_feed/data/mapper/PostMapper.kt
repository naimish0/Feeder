package feature_feed.data.mapper

import com.example.feeder.database.Posts
import feature_feed.data.remote.dto.PostViewDto
import feature_feed.domain.model.Post
import kotlin.time.Clock

fun PostViewDto.toDbModel(): Posts {
    return Posts(
        id = post.id,
        title = post.title,
        body = post.body,
        url = post.url,
        thumbnail_url = post.thumbnailUrl,
        creator_id = creator.id,
        creator_name = creator.name,
        community_id = community.id,
        community_name = community.name,
        score = counts.score.toLong(),
        comments_count = counts.comments.toLong(),
        published = post.published,
        saved = if (saved) 1L else 0L,
        last_updated = Clock.System.now().toEpochMilliseconds(),
        last_viewed_at = null,
        read = if (read) 1L else 0L
    )
}

fun Posts.toDomainModel(): Post {
    return Post(
        id = id,

        title = title,

        body = body,

        url = url,

        thumbnailUrl = thumbnail_url,

        creatorName = creator_name,

        communityName = community_name,

        score = score,

        commentsCount = comments_count,

        published = published,

        saved = saved == 1L,

        read = read == 1L
    )
}