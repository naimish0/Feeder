import core.dispatcher.DefaultDispatcherProvider
import core.network.HttpClientFactory
import org.koin.dsl.module


val coreModule = module {
    single {
        DefaultDispatcherProvider()
    }
    single {
        HttpClientFactory.create()
    }
}