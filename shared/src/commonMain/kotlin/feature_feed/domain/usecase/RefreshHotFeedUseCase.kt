package feature_feed.domain.usecase

import core.model.FeedType
import feature_feed.domain.repository.FeedRepository

class RefreshFeedUseCase(
    private val repository: FeedRepository
) {

    suspend operator fun invoke(
        feedType: FeedType
    ): Result<Unit> {
        print("RefreshFeedUseCase invoke: $feedType")
        return repository.refreshHotFeed(feedType)
    }
}