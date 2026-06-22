package com.example.feeder
import core.model.FeedType
import feature_feed.domain.model.Post
import feature_feed.domain.usecase.ObserveFeedUseCase
import feature_feed.domain.usecase.RefreshFeedUseCase
import feature_feed.presentation.intent.FeedIntent
import feature_feed.presentation.navigation.FeedNavigation
import feature_feed.presentation.viewmodel.FeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {


    @Test
    fun `init observes hot feed and refreshes successfully`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        try {
            val repository = FakeFeedRepository(
                posts = listOf(samplePost()),
                refreshResult = Result.success(Unit)
            )

            val viewModel = FeedViewModel(
                observeFeedUseCase = ObserveFeedUseCase(repository),
                refreshFeedUseCase = RefreshFeedUseCase(repository)
            )

            advanceUntilIdle()

            assertEquals(listOf(FeedType.HOT), repository.observedFeedTypes)
            assertEquals(listOf(FeedType.HOT), repository.refreshedFeedTypes)
            assertEquals(listOf(samplePost()), viewModel.state.value.posts)
            assertFalse(viewModel.state.value.isRefreshing)
            assertNull(viewModel.state.value.errorMessage)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `refresh failure sets fallback error message`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        try {
            val repository = FakeFeedRepository(
                posts = emptyList(),
                refreshResult = Result.failure(IllegalStateException())
            )

            val viewModel = FeedViewModel(
                observeFeedUseCase = ObserveFeedUseCase(repository),
                refreshFeedUseCase = RefreshFeedUseCase(repository)
            )

            advanceUntilIdle()

            assertEquals(listOf(FeedType.HOT), repository.observedFeedTypes)
            assertEquals(listOf(FeedType.HOT), repository.refreshedFeedTypes)
            assertFalse(viewModel.state.value.isRefreshing)
            assertEquals("Failed to load posts", viewModel.state.value.errorMessage)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `post clicked emits navigation to post detail`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        try {
            val repository = FakeFeedRepository(
                posts = emptyList(),
                refreshResult = Result.success(Unit)
            )

            val viewModel = FeedViewModel(
                observeFeedUseCase = ObserveFeedUseCase(repository),
                refreshFeedUseCase = RefreshFeedUseCase(repository)
            )

            val destination = async { viewModel.navigation.first() }

            viewModel.dispatch(FeedIntent.PostClicked(42L))
            advanceUntilIdle()

            assertEquals(FeedNavigation.ToPostDetail(42L), destination.await())
        } finally {
            Dispatchers.resetMain()
        }
    }

    private fun samplePost() = Post(
        id = 1L,
        title = "Sample title",
        body = "Sample body",
        url = null,
        thumbnailUrl = null,
        creatorName = "tester",
        communityName = "androiddev",
        score = 100L,
        commentsCount = 5L,
        published = "2024-01-01T00:00:00Z",
        saved = false,
        read = false
    )
}