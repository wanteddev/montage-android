package com.wanted.android.montage.sample.theme.icon

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoEvent
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoSideEffect
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedIconDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedIconDemoEvent, DSWantedIconDemoViewState, DSWantedIconDemoSideEffect>() {

    override fun setInitialState() = DSWantedIconDemoViewState(icons = dsWantedIconCatalog)

    override fun handleEvents(event: DSWantedIconDemoEvent) {
        when (event) {
            is DSWantedIconDemoEvent.SetQuery -> setState { copy(query = event.query) }
            is DSWantedIconDemoEvent.SetBackground -> setState { copy(background = event.background) }
            is DSWantedIconDemoEvent.ClickIcon -> {
                setEffect { DSWantedIconDemoSideEffect.ShowIconNameSnackBar(event.iconName) }
            }
        }
    }
}
