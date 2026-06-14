package feature_post_detail.data.repository

import core.architecture.BaseRepository
import core.security.SessionManager
import feature_post_detail.data.local.datasource.PostDetailLocalDataSource
import feature_post_detail.data.mapper.toDbModel
import feature_post_detail.data.mapper.toPostDetail
import feature_post_detail.data.remote.datasource.PostDetailRemoteDataSource
import feature_post_detail.domain.model.PostDetail
import feature_post_detail.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostDetailRepositoryImpl(
    private val remoteDataSource: PostDetailRemoteDataSource,
    private val localDataSource: PostDetailLocalDataSource,
    sessionManager: SessionManager                   // passed to BaseRepository for execute()
) : PostDetailRepository, BaseRepository(sessionManager) {

    override fun observePost(postId: Long): Flow<PostDetail?> =
        localDataSource.observePost(postId)          // emits null if not cached yet
            .map { it?.toPostDetail() }

    override suspend fun refreshPost(postId: Long): Result<Unit> {
        return execute {
            val response = remoteDataSource.getPost(postId)
            localDataSource.upsertPost(response.postView.toDbModel())
            localDataSource.updateLastViewed(postId) // mark viewed after successful fetch
        }
    }
}