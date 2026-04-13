package com.wanted.android.montage.sample.actions.iconbutton

import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewState
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.IconButtonVariant
import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedIconButtonDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedIconButtonDemoEvent, DSWantedIconButtonDemoViewState, DSWantedIconButtonDemoSideEffect>() {
    override fun setInitialState() = DSWantedIconButtonDemoViewState()

    override fun handleEvents(event: DSWantedIconButtonDemoEvent) {
        when (event) {
            is DSWantedIconButtonDemoEvent.InitState -> setState { event.viewState }
            is DSWantedIconButtonDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedIconButtonDemoEvent.CopyCode -> copyCode()
            is DSWantedIconButtonDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedIconButtonDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
            is DSWantedIconButtonDemoEvent.SetVariant -> setState { copy(variant = event.variant) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedIconButtonDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            WantedIconButtonSize.Medium -> "WantedIconButtonSize.Medium"
            WantedIconButtonSize.Small -> "WantedIconButtonSize.Small"
        }
        val content = when (state.variant) {
            IconButtonVariant.Normal -> "WantedIconButtonNormal"
            IconButtonVariant.Outlined -> "WantedIconButtonOutlined"
            IconButtonVariant.Solid -> "WantedIconButtonSolid"
            IconButtonVariant.Background -> "WantedIconButtonBackground"
        }

        return """
            $content(
                modifier = Modifier,
                icon = R.drawable.icon_normal_company,
                size = $sizeString,
                enabled = ${state.enabled},
                onClick = { /* on click */ }
            )
        """.trimIndent()
    }
}