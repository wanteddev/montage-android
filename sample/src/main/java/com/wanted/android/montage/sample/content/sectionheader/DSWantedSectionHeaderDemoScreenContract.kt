package com.wanted.android.montage.sample.content.sectionheader

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.sectionheader.WantedSectionHeaderDefaults.Size

object DSWantedSectionHeaderDemoScreenContract {
	sealed interface DSWantedSectionHeaderDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedSectionHeaderDemoEvent
		data object CopyCode : DSWantedSectionHeaderDemoEvent
		data class ShowAll(val isShowAll: Boolean) : DSWantedSectionHeaderDemoEvent
		data class SetSize(val size: Size) : DSWantedSectionHeaderDemoEvent
		data class SetHeadingContents(val isChecked: Boolean) : DSWantedSectionHeaderDemoEvent
		data class SetTrailingContent(val isChecked: Boolean) : DSWantedSectionHeaderDemoEvent
	}

	data class DSWantedSectionHeaderDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",
		val isShowAll: Boolean = false,

		val size: Size = Size.Medium,
		val headingContents: Boolean = false,
		val trailingContent: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedSectionHeaderDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedSectionHeaderDemoSideEffect
	}

	sealed interface DSWantedSectionHeaderDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedSectionHeaderDemoViewEvent
		data object OnClickShowCode : DSWantedSectionHeaderDemoViewEvent
		data object OnClickCopyCode : DSWantedSectionHeaderDemoViewEvent
		data object OnClickShowAll : DSWantedSectionHeaderDemoViewEvent
		data class OnSelectSize(val size: Size) : DSWantedSectionHeaderDemoViewEvent
		data class OnClickHeadingContents(val isChecked: Boolean) : DSWantedSectionHeaderDemoViewEvent
		data class OnClickTrailingContent(val isChecked: Boolean) : DSWantedSectionHeaderDemoViewEvent
	}
}
