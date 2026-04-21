package com.wanted.android.montage.sample.input.filterbutton

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonVariant

object DSWantedFilterButtonDemoScreenContract {
	sealed interface DSWantedFilterButtonDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedFilterButtonDemoEvent
		data object CopyCode : DSWantedFilterButtonDemoEvent
		data class SetVariant(val variant: FilterButtonVariant) : DSWantedFilterButtonDemoEvent
		data class SetSize(val size: FilterButtonSize) : DSWantedFilterButtonDemoEvent
		data class SetActive(val isActive: Boolean) : DSWantedFilterButtonDemoEvent
		data class SetEnable(val isEnable: Boolean) : DSWantedFilterButtonDemoEvent
		data class SetExpend(val isExpend: Boolean) : DSWantedFilterButtonDemoEvent
	}

	data class DSWantedFilterButtonDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val variantList: List<FilterButtonVariant> = FilterButtonVariant.entries.toList(),
		val selectedVariant: FilterButtonVariant = FilterButtonVariant.Solid,

		val sizeList: List<FilterButtonSize> = FilterButtonSize.entries.toList(),
		val selectedSize: FilterButtonSize = FilterButtonSize.Small,

		val isActive: Boolean = false,
		val isEnable: Boolean = true,
		val isExpend: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedFilterButtonDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedFilterButtonDemoSideEffect
	}

	sealed interface DSWantedFilterButtonDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedFilterButtonDemoViewEvent
		data object OnClickShowCode : DSWantedFilterButtonDemoViewEvent
		data object OnClickCopyCode : DSWantedFilterButtonDemoViewEvent
		data class OnSelectVariant(val variant: FilterButtonVariant) : DSWantedFilterButtonDemoViewEvent
		data class OnSelectSize(val size: FilterButtonSize) : DSWantedFilterButtonDemoViewEvent
		data class OnChangeActive(val isActive: Boolean) : DSWantedFilterButtonDemoViewEvent
		data class OnChangeEnable(val isEnable: Boolean) : DSWantedFilterButtonDemoViewEvent
		data class OnChangeExpend(val isExpend: Boolean) : DSWantedFilterButtonDemoViewEvent
	}
}
