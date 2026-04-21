package com.wanted.android.montage.sample.content.listcell

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults

object DSWantedListCellDemoScreenContract {

	sealed interface DSWantedListCellDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedListCellDemoEvent
		data object CopyCode : DSWantedListCellDemoEvent
		data class SetVerticalPadding(val verticalPadding: WantedListCellDefaults.VerticalPadding) :
			DSWantedListCellDemoEvent

		data class SetFillWidth(val fillWidth: Boolean) : DSWantedListCellDemoEvent
		data class SetDivider(val divider: Boolean) : DSWantedListCellDemoEvent
		data class SetIsEnable(val isEnable: Boolean) : DSWantedListCellDemoEvent
		data class SetSelected(val selected: Boolean) : DSWantedListCellDemoEvent
		data class SetChevrons(val chevrons: Boolean) : DSWantedListCellDemoEvent
		data class SetShowCaption(val showCaption: Boolean) : DSWantedListCellDemoEvent
	}

	data class DSWantedListCellDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val verticalPaddingList: List<WantedListCellDefaults.VerticalPadding> = WantedListCellDefaults.VerticalPadding.entries.toList(),
		val selectedVerticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,

		val fillWidth: Boolean = false,
		val divider: Boolean = false,
		val isEnable: Boolean = true,
		val selected: Boolean = false,
		val chevrons: Boolean = false,
		val showCaption: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedListCellDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedListCellDemoSideEffect
	}

	sealed interface DSWantedListCellDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedListCellDemoViewEvent
		data object OnClickShowCode : DSWantedListCellDemoViewEvent
		data object OnClickCopyCode : DSWantedListCellDemoViewEvent

		data class OnSelectVerticalPadding(val verticalPadding: WantedListCellDefaults.VerticalPadding) :
			DSWantedListCellDemoViewEvent

		data class OnChangeFillWidth(val fillWidth: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeDivider(val divider: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeIsEnable(val isEnable: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeSelected(val selected: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeChevrons(val chevrons: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeShowCaption(val showCaption: Boolean) : DSWantedListCellDemoViewEvent
	}
}
