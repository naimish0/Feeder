package feature_feed.domain.usecase

import core.model.FeedType
import feature_feed.domain.model.Post
import feature_feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class ObserveFeedUseCase(
    private val repository: FeedRepository
) {

    operator fun invoke(
        feedType: FeedType
    ): Flow<List<Post>> {
        print("Naimish: ObserveFeedUseCase invoke")
        return repository.observeHotFeed(feedType)
    }
}