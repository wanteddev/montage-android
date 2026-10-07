package com.wanted.android.montage.sample.input.segmentedcontrol

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoEvent
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoSideEffect
import com.wanted.android.montage.sample.input.segmentedcontrol.DSWantedSegmentedControlDemoScreenContract.DSWantedSegmentedControlDemoViewState
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedSegmentedControlDemoViewModel @Inject constructor() :
    WantedStateViewModel<
        DSWantedSegmentedControlDemoEvent,
        DSWantedSegmentedControlDemoViewState,
        DSWantedSegmentedControlDemoSideEffect,
        >() {

    override fun setInitialState() = DSWantedSegmentedControlDemoViewState()

    override fun handleEvents(event: DSWantedSegmentedControlDemoEvent) {
        when (event) {
            is DSWantedSegmentedControlDemoEvent.ShowCode -> showCode(event.isShowCode)
            is DSWantedSegmentedControlDemoEvent.CopyCode -> copyCode()
            is DSWantedSegmentedControlDemoEvent.SetSize -> setState { copy(selectedSize = event.size) }
            is DSWantedSegmentedControlDemoEvent.SetMode -> setState {
                copy(selectedMode = event.mode, selectedIndex = 0)
            }

            is DSWantedSegmentedControlDemoEvent.SetSelectedIndex -> setState {
                copy(selectedIndex = event.index)
            }

            is DSWantedSegmentedControlDemoEvent.SetIcon -> setState {
                copy(
                    selectedIcons = selectedIcons.toMutableList().apply {
                        set(event.index, event.icon)
                    }
                )
            }
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
        setEffect { DSWantedSegmentedControlDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeArg = "size = SegmentedSize.${state.selectedSize.name}, ${defaultString(state.selectedSize == SegmentedSize.Medium)}"

        return when (state.selectedMode) {
            SegmentedContentMode.Text -> """
WantedSegmentedControl(
    items = listOf("텍스트1", "텍스트2", "텍스트3"),
    selectedIndex = selectedIndex,
    $sizeArg
    onClick = { selectedIndex = it }
)
            """.trimIndent()

            SegmentedContentMode.IconText -> """
WantedSegmentedControl(
    itemCount = 3,
    selectedIndex = selectedIndex,
    $sizeArg
    onClick = { selectedIndex = it },
    item = { index ->
        WantedSegmentedControlItem(
            modifier = Modifier.fillMaxWidth(),
            title = "텍스트${'$'}{index + 1}",
            isSelected = index == selectedIndex,
            icon = { Icon(...) }
        )
    }
)
            """.trimIndent()

            SegmentedContentMode.IconOnly -> """
WantedSegmentedControl(
    itemCount = 3,
    selectedIndex = selectedIndex,
    $sizeArg
    iconOnly = true,
    onClick = { selectedIndex = it },
    item = { index ->
        WantedSegmentedControlItem(
            isSelected = index == selectedIndex,
            icon = { Icon(...) }
        )
    }
)
            """.trimIndent()
        }
    }

    private fun defaultString(isDefault: Boolean): String {
        return if (isDefault) "// (default)" else ""
    }
}
