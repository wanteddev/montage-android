package com.wanted.android.montage.sample.presentation.popover

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoEvent
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoSideEffect
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreenContract.DSWantedPopoverDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedPopoverDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPopoverDemoEvent, DSWantedPopoverDemoViewState, DSWantedPopoverDemoSideEffect>() {
    override fun setInitialState() = DSWantedPopoverDemoViewState(isLoading = false)

    override fun handleEvents(event: DSWantedPopoverDemoEvent) {
        when (event) {
            is DSWantedPopoverDemoEvent.InitState -> setState { event.viewState }
            is DSWantedPopoverDemoEvent.OnChangeAlways -> setState { copy(always = event.always) }
            is DSWantedPopoverDemoEvent.OnChangeAlign -> setState { copy(align = event.align) }
            is DSWantedPopoverDemoEvent.OnChangeHeading -> setState { copy(heading = event.heading) }
            is DSWantedPopoverDemoEvent.OnChangeHeadingText -> setState { copy(headingText = event.headingText) }
            is DSWantedPopoverDemoEvent.OnChangeCloseButton -> setState { copy(closeButton = event.closeButton) }
            is DSWantedPopoverDemoEvent.OnChangePositionTop -> setState { copy(positionTop = event.positionTop) }
            is DSWantedPopoverDemoEvent.OnChangeAction -> setState { copy(action = event.action) }
            is DSWantedPopoverDemoEvent.ShowCode -> showCode(event.show)
            DSWantedPopoverDemoEvent.CopyCode -> {
                setEffect {
                    DSWantedPopoverDemoSideEffect.CopyCode(generateCodeString())
                }
            }
        }
    }

    private fun generateCodeString(): String {
        val state = viewState.value
        val headingLine = if (state.heading) "\n    heading = \"${state.headingText}\"," else ""
        val actionBlock = if (state.action) {
            """
                action = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.TEXT,
                        type = ButtonType.ASSISTIVE,
                        text = "취소",
                    )
            
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.TEXT,
                        text = "확인",
                    )
                }"""
        } else {
            ""
        }

        return """
            WantedPopover(
                modifier = Modifier.wrapContentSize(),
                state = popoverState,
                text = "아주긴 텍스트로 툴팁을 띄웠을 때 이렇게 됩니다.",$headingLine
                align = WantedPopoverAlign.${state.align.name},
                closeButton = ${state.closeButton},
                positionTop = ${state.positionTop},
                always = ${state.always},
                content = {
                    WantedButton(
                        text = "Button",
                        size = ButtonSize.SMALL,
                        onClick = {
                            if (popoverState.isVisible) {
                                popoverState.dismiss()
                            } else {
                                popoverState.show()
                            }
                        }
                    )
                }$actionBlock
            )
        """.trimIndent()
    }

    private fun showCode(isShow: Boolean) {
        setState {
            copy(
                isShowCode = isShow,
                code = generateCodeString()
            )
        }
    }
}