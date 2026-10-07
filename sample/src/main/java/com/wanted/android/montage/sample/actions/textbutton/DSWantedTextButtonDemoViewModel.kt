package com.wanted.android.montage.sample.actions.textbutton

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoEvent
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.textbutton.DSWantedTextButtonDemoScreenContract.DSWantedTextButtonDemoViewState
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedTextButtonDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTextButtonDemoEvent, DSWantedTextButtonDemoViewState, DSWantedTextButtonDemoSideEffect>() {
    override fun setInitialState() = DSWantedTextButtonDemoViewState()

    override fun handleEvents(event: DSWantedTextButtonDemoEvent) {
        when (event) {
            is DSWantedTextButtonDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTextButtonDemoEvent.ShowCode -> showCode(event.isShowCode)
            is DSWantedTextButtonDemoEvent.CopyCode -> copyCode()
            is DSWantedTextButtonDemoEvent.SetType -> setType(event.type)
            is DSWantedTextButtonDemoEvent.SetSize -> setSize(event.buttonSize)
            is DSWantedTextButtonDemoEvent.SetEnable -> setEnable(event.enabled)
            is DSWantedTextButtonDemoEvent.SetLoading -> setLoading(event.isLoading)
            is DSWantedTextButtonDemoEvent.SetEnableLeadingDrawable -> setEnableLeftDrawable(event.enableLeftDrawable)
            is DSWantedTextButtonDemoEvent.SetEnableTrailingDrawable -> setEnableRightDrawable(event.enableRightDrawable)
            is DSWantedTextButtonDemoEvent.ShowAll -> showAll(event.isShowAll)
        }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun copyCode() {
        setEffect { DSWantedTextButtonDemoSideEffect.CopyCode(getCode()) }
    }

    private fun showAll(isShowAll: Boolean) {
        setState { copy(isShowAll = isShowAll) }
    }

    private fun getCode(): String {
        return """
            WantedTextButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "preview",
                    color = WantedTextButtonColor.${viewState.value.selectedType}, ${isButtonTypeDefault()}
                    size = WantedTextButtonSize.${viewState.value.selectedSize}, ${isButtonSizeDefault()}
                    enabled = ${viewState.value.enabled}, ${getDefaultString(viewState.value.enabled)}
                    isLoading = ${viewState.value.isLoading}, ${getDefaultString(!viewState.value.isLoading)}
                    leadingDrawable = if (enableLeadingDrawable) {
                        iconRes
                    } else null, // (default null)
                    trailingDrawable = if (enableTrailingDrawable) {
                        iconRes
                    } else null, // (default null)
                    onClick = {}
                )
        """.trimIndent()
    }

    private fun isButtonTypeDefault(): String {
        return getDefaultString(viewState.value.selectedType == WantedTextButtonColor.PRIMARY)
    }

    private fun isButtonSizeDefault(): String {
        return getDefaultString(viewState.value.selectedSize == WantedTextButtonSize.MEDIUM)
    }

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) {
            "// (default)"
        } else {
            ""
        }
    }

    private fun setType(type: WantedTextButtonColor) {
        setState { copy(selectedType = type) }
    }

    private fun setSize(size: WantedTextButtonSize) {
        setState { copy(selectedSize = size) }
    }

    private fun setEnable(enabled: Boolean) {
        setState { copy(enabled = enabled) }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

    private fun setEnableLeftDrawable(enabled: Boolean) {
        setState { copy(enabledLeadingDrawable = enabled) }
    }

    private fun setEnableRightDrawable(enabled: Boolean) {
        setState { copy(enabledTrailingDrawable = enabled) }
    }

}
