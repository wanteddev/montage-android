package com.wanted.android.montage.sample.content.accordion

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.accordion.WantedAccordionDefaults.VerticalPadding

object DSWantedAccordionDemoScreenContract {
	sealed interface DSWantedAccordionDemoEvent : BaseEvent {
		data class InitState(val viewState: DSWantedAccordionDemoViewState) : DSWantedAccordionDemoEvent
		data class ShowCode(val isShowCode: Boolean) : DSWantedAccordionDemoEvent
		data object CopyCode : DSWantedAccordionDemoEvent
		data class ShowAll(val isShowAll: Boolean) : DSWantedAccordionDemoEvent
		data class ToggleExpanded(val isExpanded: Boolean) : DSWantedAccordionDemoEvent
		data class SetDescription(val isChecked: Boolean) : DSWantedAccordionDemoEvent
		data class SetFillWidth(val isChecked: Boolean) : DSWantedAccordionDemoEvent
		data class SetDivider(val isChecked: Boolean) : DSWantedAccordionDemoEvent
		data class SetLeadingIcon(val isChecked: Boolean) : DSWantedAccordionDemoEvent
		data class SetContent(val isChecked: Boolean) : DSWantedAccordionDemoEvent
		data class SetVerticalPadding(val verticalPadding: VerticalPadding) : DSWantedAccordionDemoEvent
	}

	data class DSWantedAccordionDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",
		val isShowAll: Boolean = false,

		val isExpanded: Boolean = true,
		val description: Boolean = true,
		val fillWidth: Boolean = false,
		val divider: Boolean = true,
		val leadingIcon: Boolean = false,
		val content: Boolean = true,
		val verticalPadding: VerticalPadding = VerticalPadding.Padding12,
	) : BaseViewState

	sealed interface DSWantedAccordionDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedAccordionDemoSideEffect
	}

	sealed interface DSWantedAccordionDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedAccordionDemoViewEvent
		data object OnClickShowCode : DSWantedAccordionDemoViewEvent
		data object OnClickCopyCode : DSWantedAccordionDemoViewEvent
		data object OnClickShowAll : DSWantedAccordionDemoViewEvent
		data class OnClickExpanded(val isExpanded: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnClickDescription(val isChecked: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnClickFillWidth(val isChecked: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnClickDivider(val isChecked: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnClickLeadingIcon(val isChecked: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnClickContent(val isChecked: Boolean) : DSWantedAccordionDemoViewEvent
		data class OnSelectVerticalPadding(val verticalPadding: VerticalPadding) : DSWantedAccordionDemoViewEvent
	}
}
