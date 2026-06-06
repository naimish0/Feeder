package core.architecture

interface Reducer<State, Result> {
    fun reduce(currentState: State, result: Result): State
}