package com.wanted.android.montage.sample.content.listcell

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults

object DSWantedListCellDemoScreenContract {

	/**
	 * leadingContent 슬롯에 넣어 볼 수 있는 요소 종류입니다.
	 *
	 * 슬롯은 임의 Composable 을 받으므로, Figma·Web(ListCellContent variant)에서 쓰는 요소를 데모에서 열거한다.
	 * Multiple 은 여러 개를 넣었을 때의 항목 간 간격(8dp)을 확인하기 위한 조합이다.
	 */
	enum class LeadingContentType {
		None,
		Icon,
		CheckBox,
		Radio,
		Avatar,
		Multiple
	}

	/**
	 * trailingContent 슬롯에 넣어 볼 수 있는 요소 종류입니다.
	 *
	 * selected 일 때 이 슬롯이 None 이면 Check 아이콘이, 그 밖의 값이면 넣은 요소만 보인다.
	 */
	enum class TrailingContentType {
		None,
		Value,
		Icon,
		IconButton,
		TextButton,
		ContentBadge,
		CheckMark,
		Switch,
		Multiple
	}

	/** labelTrailingContent(타이틀 옆) 슬롯에 넣어 볼 수 있는 요소 종류입니다. Multiple 은 간격 4dp 확인용이다. */
	enum class LabelTrailingContentType {
		None,
		ContentBadge,
		Icon,
		Multiple
	}

	/** extraContent(설명 아래) 슬롯에 넣어 볼 수 있는 요소 종류입니다. */
	enum class ExtraContentType {
		None,
		Text,
		ContentBadge,
		TextButton
	}

	sealed interface DSWantedListCellDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedListCellDemoEvent
		data object CopyCode : DSWantedListCellDemoEvent
		data class SetVerticalPadding(val verticalPadding: WantedListCellDefaults.VerticalPadding) :
			DSWantedListCellDemoEvent

		data class SetVariant(val variant: WantedListCellDefaults.Variant) :
			DSWantedListCellDemoEvent

		data class SetDivider(val divider: Boolean) : DSWantedListCellDemoEvent
		data class SetIsEnable(val isEnable: Boolean) : DSWantedListCellDemoEvent
		data class SetSelected(val selected: Boolean) : DSWantedListCellDemoEvent
		data class SetChevrons(val chevrons: Boolean) : DSWantedListCellDemoEvent
		data class SetEnabledInnerTouch(val enabledInnerTouch: Boolean) : DSWantedListCellDemoEvent
		data class SetShowDescription(val showDescription: Boolean) : DSWantedListCellDemoEvent
		data class SetMultiLineText(val multiLineText: Boolean) : DSWantedListCellDemoEvent
		data class SetVerticalAlignCenter(val verticalAlignCenter: Boolean) : DSWantedListCellDemoEvent
		data class SetLeadingContent(val leadingContent: LeadingContentType) : DSWantedListCellDemoEvent
		data class SetTrailingContent(val trailingContent: TrailingContentType) : DSWantedListCellDemoEvent
		data class SetLabelTrailingContent(val labelTrailingContent: LabelTrailingContentType) :
			DSWantedListCellDemoEvent

		data class SetExtraContent(val extraContent: ExtraContentType) : DSWantedListCellDemoEvent
	}

	data class DSWantedListCellDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val verticalPaddingList: List<WantedListCellDefaults.VerticalPadding> = WantedListCellDefaults.VerticalPadding.entries.toList(),
		val selectedVerticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,

		val variantList: List<WantedListCellDefaults.Variant> = WantedListCellDefaults.Variant.entries.toList(),
		val selectedVariant: WantedListCellDefaults.Variant = WantedListCellDefaults.Variant.Inset,

		val divider: Boolean = false,
		val isEnable: Boolean = true,
		val selected: Boolean = false,
		val chevrons: Boolean = false,
		val enabledInnerTouch: Boolean = false,
		val showDescription: Boolean = false,
		val multiLineText: Boolean = false,
		val verticalAlignCenter: Boolean = false,
		val leadingContent: LeadingContentType = LeadingContentType.None,
		val trailingContent: TrailingContentType = TrailingContentType.None,
		val labelTrailingContent: LabelTrailingContentType = LabelTrailingContentType.None,
		val extraContent: ExtraContentType = ExtraContentType.None,
	) : BaseViewState

	sealed interface DSWantedListCellDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedListCellDemoSideEffect
	}

	sealed interface DSWantedListCellDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedListCellDemoViewEvent
		data object OnClickShowCode : DSWantedListCellDemoViewEvent

		data class OnSelectVerticalPadding(val verticalPadding: WantedListCellDefaults.VerticalPadding) :
			DSWantedListCellDemoViewEvent

		data class OnSelectVariant(val variant: WantedListCellDefaults.Variant) :
			DSWantedListCellDemoViewEvent

		data class OnChangeDivider(val divider: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeIsEnable(val isEnable: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeSelected(val selected: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeChevrons(val chevrons: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeEnabledInnerTouch(val enabledInnerTouch: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeShowDescription(val showDescription: Boolean) :
			DSWantedListCellDemoViewEvent

		data class OnChangeMultiLineText(val multiLineText: Boolean) : DSWantedListCellDemoViewEvent
		data class OnChangeVerticalAlignCenter(val verticalAlignCenter: Boolean) :
			DSWantedListCellDemoViewEvent

		data class OnSelectLeadingContent(val leadingContent: LeadingContentType) :
			DSWantedListCellDemoViewEvent

		data class OnSelectTrailingContent(val trailingContent: TrailingContentType) :
			DSWantedListCellDemoViewEvent

		data class OnSelectLabelTrailingContent(val labelTrailingContent: LabelTrailingContentType) :
			DSWantedListCellDemoViewEvent

		data class OnSelectExtraContent(val extraContent: ExtraContentType) : DSWantedListCellDemoViewEvent
	}
}
