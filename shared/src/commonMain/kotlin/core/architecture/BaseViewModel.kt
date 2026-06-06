package core.architecture

import core.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel(protected val dispatcherProvider: DispatcherProvider) {
    val scope = CoroutineScope(
        SupervisorJob() + dispatcherProvider.main
    )

    open fun clear() {
        scope.cancel()
    }
}