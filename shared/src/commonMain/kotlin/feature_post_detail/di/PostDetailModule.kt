package feature_post_detail.di

import com.example.feeder.database.FeederDatabase
import feature_post_detail.data.local.datasource.PostDetailLocalDataSource
import feature_post_detail.data.remote.api.PostDetailApi
import feature_post_detail.data.remote.api.PostDetailApiImpl
import feature_post_detail.data.remote.datasource.PostDetailRemoteDataSource
import feature_post_detail.data.repository.PostDetailRepositoryImpl
import feature_post_detail.domain.repository.PostDetailRepository
import feature_post_detail.domain.usecase.ObservePostDetailUseCase
import feature_post_detail.domain.usecase.RefreshPostDetailUseCase
import feature_post_detail.presentation.viewmodel.PostDetailViewModel
import org.koin.core.module.dsl.viewModel  // Koin 4.0.0
import org.koin.dsl.module

val postDetailModule = module {

    single<PostDetailApi> { PostDetailApiImpl(get()) }
    single { PostDetailRemoteDataSource(get()) }
    single {
        val db = get<FeederDatabase>()
        PostDetailLocalDataSource(postQueries = db.postQueries)
    }
    single { PostDetailRepositoryImpl(get(), get(), get()) }
    single<PostDetailRepository> { get<PostDetailRepositoryImpl>() }

    single { ObservePostDetailUseCase(get()) }
    single { RefreshPostDetailUseCase(get()) }

    viewModel { (postId: Long) -> PostDetailViewModel(postId, get(), get()) }
}