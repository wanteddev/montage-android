package com.wanted.android.montage.sample.content.listcell

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoEvent
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoSideEffect
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoViewState
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedListCellDemoViewModel @Inject constructor() :
	WantedStateViewModel<DSWantedListCellDemoEvent, DSWantedListCellDemoViewState, DSWantedListCellDemoSideEffect>() {

	override fun setInitialState() = DSWantedListCellDemoViewState()

	override fun handleEvents(event: DSWantedListCellDemoEvent) {
		when (event) {
			is DSWantedListCellDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedListCellDemoEvent.CopyCode -> copyCode()
			is DSWantedListCellDemoEvent.SetVerticalPadding -> setState {
				copy(
					selectedVerticalPadding = event.verticalPadding
				)
			}

			is DSWantedListCellDemoEvent.SetFillWidth -> setState { copy(fillWidth = event.fillWidth) }
			is DSWantedListCellDemoEvent.SetDivider -> setState { copy(divider = event.divider) }
			is DSWantedListCellDemoEvent.SetIsEnable -> setState { copy(isEnable = event.isEnable) }
			is DSWantedListCellDemoEvent.SetSelected -> setState { copy(selected = event.selected) }
			is DSWantedListCellDemoEvent.SetChevrons -> setState { copy(chevrons = event.chevrons) }
			is DSWantedListCellDemoEvent.SetShowCaption -> setState { copy(showCaption = event.showCaption) }
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
		setEffect { DSWantedListCellDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		val lines = mutableListOf<String>()
		lines.add("WantedListCell(")
		lines.add("    text = \"텍스트\",")
		if (state.showCaption) {
			lines.add("    caption = \"캡션\",")
		}
		if (state.fillWidth) {
			lines.add("    fillWidth = true,")
		}
		if (state.selectedVerticalPadding != WantedListCellDefaults.VerticalPadding.Medium) {
			lines.add("    verticalPadding = WantedListCellDefaults.VerticalPadding.${state.selectedVerticalPadding.name},")
		}
		if (state.divider) {
			lines.add("    divider = true,")
		}
		if (!state.isEnable) {
			lines.add("    isEnable = false,")
		}
		if (state.selected) {
			lines.add("    selected = true,")
		}
		if (state.chevrons) {
			lines.add("    chevrons = true,")
		}
		lines.add("    onClick = {}")
		lines.add(")")
		return lines.joinToString("\n")
	}
}
