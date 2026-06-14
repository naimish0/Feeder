package feature_post_detail.data.mapper

import com.example.feeder.database.Posts
import feature_post_detail.data.remote.dto.PostDetailViewDto
import feature_post_detail.domain.model.PostDetail

fun PostDetailViewDto.toDbModel(): Posts = Posts(
    id = post.id,
    title = post.title,
    body = post.body,
    url = post.url,
    thumbnail_url = post.thumbnailUrl,
    creator_id = creator.id,
    creator_name = creator.name,
    community_id = community.id,
    community_name = community.name,
    score = counts.score,
    comments_count = counts.comments,
    published = post.published,
    saved = if (saved) 1L else 0L,
    read = if (read) 1L else 0L,
    last_updated = kotlin.time.Clock.System.now().toEpochMilliseconds(),
    last_viewed_at = null
)

fun Posts.toPostDetail(): PostDetail = PostDetail(
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