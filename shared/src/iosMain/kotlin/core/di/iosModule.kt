package core.di

import com.example.feeder.database.FeederDatabase
import core.database.DatabaseDriverFactory
import org.koin.dsl.module

val iosModule = module {
    // iOS SQLite driver — no Context needed on iOS
    single { DatabaseDriverFactory().createDriver() }

    // FeederDatabase — same as Android
    single { FeederDatabase(get()) }

}