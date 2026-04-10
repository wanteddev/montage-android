package com.wanted.android.montage.sample.actions.button

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoEvent
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreenContract.DSWantedButtonDemoViewState
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedButtonDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedButtonDemoEvent, DSWantedButtonDemoViewState, DSWantedButtonDemoSideEffect>() {
    override fun setInitialState() = DSWantedButtonDemoViewState()

    override fun handleEvents(event: DSWantedButtonDemoEvent) {
        when (event) {
            is DSWantedButtonDemoEvent.InitState -> setState { event.viewState }
            is DSWantedButtonDemoEvent.ShowCode -> showCode(event.isShowCode)
            is DSWantedButtonDemoEvent.CopyCode -> copyCode()
            is DSWantedButtonDemoEvent.SetButtonShape -> setButtonShape(event.buttonVariant)
            is DSWantedButtonDemoEvent.SetType -> setType(event.type)
            is DSWantedButtonDemoEvent.SetSize -> setSize(event.buttonSize)
            is DSWantedButtonDemoEvent.SetEnable -> setEnable(event.enabled)
            is DSWantedButtonDemoEvent.SetLoading -> setLoading(event.isLoading)
            is DSWantedButtonDemoEvent.SetEnableLeadingDrawable -> setEnableLeftDrawable(event.enableLeftDrawable)
            is DSWantedButtonDemoEvent.SetEnableTrailingDrawable -> setEnableRightDrawable(event.enableRightDrawable)
            is DSWantedButtonDemoEvent.ShowAll -> showAll(event.isShowAll)
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
        setEffect { DSWantedButtonDemoSideEffect.CopyCode(getCode()) }
    }

    private fun showAll(isShowAll: Boolean) {
        setState { copy(isShowAll = isShowAll) }
    }

    private fun getCode(): String {
        return """
            WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "preview", 
                    buttonShape = ButtonShape.${viewState.value.selectedButtonVariant}, ${isButtonShapeDefault()}
                    type = ButtonType.${viewState.value.selectedType}, ${isButtonTypeDefault()}
                    size = ButtonSize.${viewState.value.selectedSize}, ${isButtonSizeDefault()}
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

    private fun isButtonShapeDefault(): String {
        return getDefaultString(viewState.value.selectedButtonVariant == ButtonVariant.SOLID)
    }

    private fun isButtonTypeDefault(): String {
        return getDefaultString(viewState.value.selectedType == ButtonType.PRIMARY)
    }

    private fun isButtonSizeDefault(): String {
        return getDefaultString(viewState.value.selectedSize == ButtonSize.LARGE)
    }

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) {
            "// (default)"
        } else {
            ""
        }
    }

    private fun setButtonShape(buttonVariant: ButtonVariant) {
        setState { copy(selectedButtonVariant = buttonVariant) }
    }

    private fun setType(type: ButtonType) {
        setState { copy(selectedType = type) }
    }

    private fun setSize(size: ButtonSize) {
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