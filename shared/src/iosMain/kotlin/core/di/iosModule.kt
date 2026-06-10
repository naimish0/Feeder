package core.di

import com.example.feeder.database.FeederDatabase
import core.database.DatabaseDriverFactory
import feature_feed.presentation.viewmodel.FeedViewModel
import org.koin.dsl.module

val iosModule = module {
    // iOS SQLite driver — no Context needed on iOS
    single { DatabaseDriverFactory().createDriver() }

    // FeederDatabase — same as Android
    single { FeederDatabase(get()) }

    // ViewModel registered as single on iOS — viewModel { } DSL is Android-only
    single { FeedViewModel(get(), get()) }
}