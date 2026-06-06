package core.database

import com.example.feeder.database.FeederDatabase

class DatabaseFactory(private val driverFactory: DatabaseDriverFactory) {

    fun create(): FeederDatabase {
        return FeederDatabase(
            driver = driverFactory.createDriver()
        )
    }
}