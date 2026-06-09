package feature_feed.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feeder.database.FeederDatabase
import feature_feed.data.local.datasource.FeedLocalDataSource
import feature_feed.data.remote.api.FeedApi
import feature_feed.data.remote.api.FeedApiImpl
import feature_feed.data.remote.datasource.FeedRemoteDataSource
import feature_feed.data.repository.FeedRepositoryImpl
import feature_feed.domain.repository.FeedRepository
import feature_feed.domain.usecase.ObserveFeedUseCase
import feature_feed.domain.usecase.RefreshFeedUseCase
import org.koin.dsl.module

val feedModule = module {
    single<FeedApi> {
        FeedApiImpl(get())
    }
    single {
        FeedRemoteDataSource(get())
    }
    single {
        val db =  get<FeederDatabase>()
         FeedLocalDataSource(
            database = db,
            postQueries = db.postQueries,
            feedPostsQueries = db.feedPostsQueries
        )
    }
    single {
        FeedRepositoryImpl(
            remoteDataSource = get(), localDataSource = get()
        )
    }
    single<FeedRepository> {
        get<FeedRepositoryImpl>()
    }
    single {
        ObserveFeedUseCase(get())
    }
    single {
        RefreshFeedUseCase(get())
    }
}