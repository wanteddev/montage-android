package com.wanted.android.montage.sample.theme.color

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoEvent
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoSideEffect
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DSWantedColorTokenDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedColorTokenDemoEvent, DSWantedColorTokenDemoViewState, DSWantedColorTokenDemoSideEffect>() {

    override fun setInitialState() = DSWantedColorTokenDemoViewState()

    override fun handleEvents(event: DSWantedColorTokenDemoEvent) {
        when (event) {
            is DSWantedColorTokenDemoEvent.SetQuery -> setState { copy(query = event.query) }
            is DSWantedColorTokenDemoEvent.SetGroup -> setState { copy(group = event.group) }
            is DSWantedColorTokenDemoEvent.ClickItem -> {
                setEffect { DSWantedColorTokenDemoSideEffect.ShowCopySnackBar(event.code) }
            }
        }
    }
}
