package com.wanted.android.montage.sample.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class WantedStateViewModel<Event : BaseEvent, State : BaseViewState, Effect : BaseSideEffect> :
    ViewModel() {

    private val initialState: State by lazy { setInitialState() }
    abstract fun setInitialState(): State

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<State> = _viewState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()

    private val _sideEffect = Channel<Effect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    abstract fun handleEvents(event: Event)

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: State.() -> State) {
        _viewState.value = _viewState.value.reducer()
    }

    protected fun setEffect(builder: () -> Effect) {
        viewModelScope.launch { _sideEffect.send(builder()) }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { handleEvents(it) }
        }
    }
}
