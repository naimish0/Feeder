package core.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.feeder.database.FeederDatabase
import core.database.DatabaseDriverFactory
import feature_feed.presentation.viewmodel.FeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    // 1. Platform-specific SQLite driver (needs Android Context)
    single { DatabaseDriverFactory(androidContext()).createDriver() }

    // 2. FeederDatabase — now visible to all modules via Koin
    single { FeederDatabase(get()) }

    // 3. ViewModel registered properly with viewModel { } not single { }
    viewModel { FeedViewModel(get(), get()) }
}