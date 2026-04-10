package com.wanted.android.montage.sample.actions.chip

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant

object DSWantedChipDemoScreenContract {
	sealed interface DSWantedChipDemoEvent : BaseEvent {
		data class InitState(val viewState: DSWantedChipDemoViewState) : DSWantedChipDemoEvent
		data class ShowCode(val isShowCode: Boolean) : DSWantedChipDemoEvent
		data object CopyCode : DSWantedChipDemoEvent
		data class SetVariant(val variant: ChipVariant) : DSWantedChipDemoEvent
		data class SetSize(val size: ChipSize) : DSWantedChipDemoEvent
		data class SetActive(val isActive: Boolean) : DSWantedChipDemoEvent
		data class SetEnable(val isEnable: Boolean) : DSWantedChipDemoEvent
		data class SetLeftIcon(val hasLeftIcon: Boolean) : DSWantedChipDemoEvent
		data class SetRightIcon(val hasRightIcon: Boolean) : DSWantedChipDemoEvent
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
		val hasLeftIcon: Boolean = false,
		val hasRightIcon: Boolean = false,
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
		data class OnChangeLeftIcon(val hasLeftIcon: Boolean) : DSWantedChipDemoViewEvent
		data class OnChangeRightIcon(val hasRightIcon: Boolean) : DSWantedChipDemoViewEvent
	}
}
