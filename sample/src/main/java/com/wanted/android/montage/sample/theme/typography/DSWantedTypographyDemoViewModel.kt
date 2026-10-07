package com.wanted.android.montage.sample.theme.typography

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoEvent
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoSideEffect
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DSWantedTypographyDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTypographyDemoEvent, DSWantedTypographyDemoViewState, DSWantedTypographyDemoSideEffect>() {

    override fun setInitialState() = DSWantedTypographyDemoViewState()

    override fun handleEvents(event: DSWantedTypographyDemoEvent) {
        when (event) {
            is DSWantedTypographyDemoEvent.SetWeight -> setState { copy(weight = event.weight) }
            is DSWantedTypographyDemoEvent.SetSample -> setState { copy(sample = event.sample) }
            is DSWantedTypographyDemoEvent.SetShowLineBox -> setState { copy(showLineBox = event.enabled) }
            is DSWantedTypographyDemoEvent.ClickItem -> {
                setEffect {
                    DSWantedTypographyDemoSideEffect.ShowCopySnackBar("DesignSystemTheme.typography.${event.name}")
                }
            }
        }
    }
}
