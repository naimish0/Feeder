package core.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// N = Navigation type. Separate from State because it has different lifecycle requirements.
abstract class BaseViewModel<I : Any, A : Any, R : Any, S : Any, N : Any>(
    initialState: S
) : ViewModel() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        print("CoroutineExceptionHandler caught an exception: ${throwable.message}")
    }
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _navigation = Channel<N>(Channel.BUFFERED)
    val navigation: Flow<N> = _navigation.receiveAsFlow()

    protected val currentState: S get() = _state.value

    fun dispatch(intent: I) {
        viewModelScope.launch(coroutineExceptionHandler) {
            handleAction(toAction(intent))
        }
    }

    protected abstract fun toAction(intent: I): A
    protected abstract suspend fun handleAction(action: A)
    protected abstract fun reduce(state: S, result: R): S

    protected fun emitResult(result: R) {
        _state.update { reduce(it, result) }
    }

    protected suspend fun navigate(destination: N) {
        _navigation.send(destination)
    }
}