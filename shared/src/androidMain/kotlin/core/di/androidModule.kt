package core.di

import com.example.feeder.database.FeederDatabase
import core.database.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    // 1. Platform-specific SQLite driver (needs Android Context)
    single { DatabaseDriverFactory(androidContext()).createDriver() }

    // 2. FeederDatabase — now visible to all modules via Koin
    single { FeederDatabase(get()) }

}