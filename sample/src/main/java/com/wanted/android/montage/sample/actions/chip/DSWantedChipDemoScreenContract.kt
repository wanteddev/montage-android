package com.wanted.android.montage.sample.actions.chip

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant

object DSWantedChipDemoScreenContract {

	/**
	 * 데모에서 backgroundColor / borderColor 커스텀을 시연하기 위한 색상 선택지입니다.
	 * None이면 기본 스타일을 사용하고, 그 외에는 해당 색상으로 배경(및 Outlined의 테두리)을 커스텀합니다.
	 */
	enum class ChipCustomColor {
		None, Primary, Negative, Positive
	}

	sealed interface DSWantedChipDemoEvent : BaseEvent {
		data class InitState(val viewState: DSWantedChipDemoViewState) : DSWantedChipDemoEvent
		data class ShowCode(val isShowCode: Boolean) : DSWantedChipDemoEvent
		data object CopyCode : DSWantedChipDemoEvent
		data class SetVariant(val variant: ChipVariant) : DSWantedChipDemoEvent
		data class SetSize(val size: ChipSize) : DSWantedChipDemoEvent
		data class SetActive(val isActive: Boolean) : DSWantedChipDemoEvent
		data class SetEnable(val isEnable: Boolean) : DSWantedChipDemoEvent
		data class SetLeadingContent(val hasLeadingContent: Boolean) : DSWantedChipDemoEvent
		data class SetTrailingContent(val hasTrailingContent: Boolean) : DSWantedChipDemoEvent
		data class SetCustomColor(val customColor: ChipCustomColor) : DSWantedChipDemoEvent
	}

	data class DSWantedChipDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val variantList: List<ChipVariant> = ChipVariant.entries.toList(),
		val selectedVariant: ChipVariant = ChipVariant.Solid,

		val sizeList: List<ChipSize> = ChipSize.entries.toList(),
		val selectedSize: ChipSize = ChipSize.Medium,

		val isActive: Boolean = false,
		val isEnable: Boolean = true,
		val hasLeadingContent: Boolean = false,
		val hasTrailingContent: Boolean = false,

		val customColorList: List<ChipCustomColor> = ChipCustomColor.entries.toList(),
		val selectedCustomColor: ChipCustomColor = ChipCustomColor.None,
	) : BaseViewState

	sealed interface DSWantedChipDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedChipDemoSideEffect
	}

	sealed interface DSWantedChipDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedChipDemoViewEvent
		data object OnClickShowCode : DSWantedChipDemoViewEvent
		data object OnClickCopyCode : DSWantedChipDemoViewEvent
		data class OnSelectVariant(val variant: ChipVariant) : DSWantedChipDemoViewEvent
		data class OnSelectSize(val size: ChipSize) : DSWantedChipDemoViewEvent
		data class OnChangeActive(val isActive: Boolean) : DSWantedChipDemoViewEvent
		data class OnChangeEnable(val isEnable: Boolean) : DSWantedChipDemoViewEvent
		data class OnChangeLeadingContent(val hasLeadingContent: Boolean) : DSWantedChipDemoViewEvent
		data class OnChangeTrailingContent(val hasTrailingContent: Boolean) : DSWantedChipDemoViewEvent
		data class OnSelectCustomColor(val customColor: ChipCustomColor) : DSWantedChipDemoViewEvent
	}
}
