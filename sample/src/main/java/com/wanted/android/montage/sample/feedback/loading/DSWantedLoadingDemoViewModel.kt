package com.wanted.android.montage.sample.feedback.loading

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoEvent
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoSideEffect
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoViewState
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.LoadingType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedLoadingDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedLoadingDemoEvent, DSWantedLoadingDemoViewState, DSWantedLoadingDemoSideEffect>() {
    override fun setInitialState() = DSWantedLoadingDemoViewState()

    override fun handleEvents(event: DSWantedLoadingDemoEvent) {
        when (event) {
            is DSWantedLoadingDemoEvent.InitState -> setState { event.viewState }
            is DSWantedLoadingDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedLoadingDemoEvent.CopyCode -> copyCode()
            is DSWantedLoadingDemoEvent.SetLoadingType -> setState { copy(loadingType = event.type) }
            is DSWantedLoadingDemoEvent.SetUseDim -> setState { copy(useDim = event.useDim) }
            is DSWantedLoadingDemoEvent.SetShowLoading -> setState { copy(showLoading = event.show) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedLoadingDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        return when (state.loadingType) {
            LoadingType.Circular -> {
                """
                    WantedCircularLoading(
                        size = 32.dp,
                        circleColor = DesignSystemTheme.colors.lineSolidNormal,
                        dimColor = ${if (state.useDim) "DesignSystemTheme.colors.staticBlack.copy(alpha = 0.3f)" else "Color.Transparent"}
                    )
                """.trimIndent()
            }

            LoadingType.Logo -> {
                """
                    WantedLogoLoading(
                        isUseDim = ${state.useDim}
                    )
                """.trimIndent()
            }
        }
    }
}