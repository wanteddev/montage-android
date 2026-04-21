package com.wanted.android.montage.sample.base.dropshadow

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoEvent
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoSideEffect
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.DSWantedDropShadowDemoViewState
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.ShadowPreviewBackground
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreenContract.ShadowSampleContentBackground
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedDropShadowDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedDropShadowDemoEvent, DSWantedDropShadowDemoViewState, DSWantedDropShadowDemoSideEffect>() {
    override fun setInitialState() = DSWantedDropShadowDemoViewState()

    override fun handleEvents(event: DSWantedDropShadowDemoEvent) {
        when (event) {
            is DSWantedDropShadowDemoEvent.InitState -> setState { event.viewState }
            is DSWantedDropShadowDemoEvent.CopyCode -> copyCode()
            is DSWantedDropShadowDemoEvent.ShowCode -> showCode(event.isShow)
            is DSWantedDropShadowDemoEvent.SetContentBackground -> setContentBackground(event.background)
            is DSWantedDropShadowDemoEvent.SetPreviewBackground -> setPreviewBackground(event.background)
            is DSWantedDropShadowDemoEvent.SetStyle -> setStyle(event.style)
        }
    }

    private fun copyCode() {
        setEffect { DSWantedDropShadowDemoSideEffect.CopyCode(getCode()) }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun getCode(): String {
        return """
            Box(
                Modifier
                    .size(100.dp)
                    .wantedDropShadow(
                        style = WantedShadowStyle.${viewState.value.style.javaClass.simpleName}(
                            backgroundColor = ${
            if (viewState.value.previewBackground == ShadowPreviewBackground.ContentBackground) {
                viewState.value.contentBackground.color
            } else {
                viewState.value.previewBackground.color
            }
        },
                        )
                    )
            )
            """
    }

    private fun setStyle(style: WantedShadowStyle) {
        setState { copy(style = style) }
    }

    private fun setPreviewBackground(background: ShadowPreviewBackground) {
        setState { copy(previewBackground = background) }
    }

    private fun setContentBackground(contentBackground: ShadowSampleContentBackground) {
        setState { copy(contentBackground = contentBackground) }
    }
}