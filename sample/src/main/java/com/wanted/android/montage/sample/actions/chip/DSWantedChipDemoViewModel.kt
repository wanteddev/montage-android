package com.wanted.android.montage.sample.actions.chip

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoEvent
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoSideEffect
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoViewState
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedChipDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedChipDemoEvent, DSWantedChipDemoViewState, DSWantedChipDemoSideEffect>() {
	override fun setInitialState() = DSWantedChipDemoViewState()

	override fun handleEvents(event: DSWantedChipDemoEvent) {
		when (event) {
			is DSWantedChipDemoEvent.InitState -> setState { event.viewState }
			is DSWantedChipDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedChipDemoEvent.CopyCode -> copyCode()
			is DSWantedChipDemoEvent.SetVariant -> setVariant(event.variant)
			is DSWantedChipDemoEvent.SetSize -> setSize(event.size)
			is DSWantedChipDemoEvent.SetActive -> setActive(event.isActive)
			is DSWantedChipDemoEvent.SetEnable -> setEnable(event.isEnable)
			is DSWantedChipDemoEvent.SetLeftIcon -> setLeftIcon(event.hasLeftIcon)
			is DSWantedChipDemoEvent.SetRightIcon -> setRightIcon(event.hasRightIcon)
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
		setEffect { DSWantedChipDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val leftIconCode = if (viewState.value.hasLeftIcon) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		val rightIconCode = if (viewState.value.hasRightIcon) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		return """
WantedChip(
	text = "텍스트",
	variant = ChipVariant.${viewState.value.selectedVariant.name}, ${getDefaultString(viewState.value.selectedVariant == ChipVariant.Solid)}
	size = ChipSize.${viewState.value.selectedSize.name}, ${getDefaultString(viewState.value.selectedSize == ChipSize.Medium)}
	isActive = ${viewState.value.isActive}, ${getDefaultString(!viewState.value.isActive)}
	isEnable = ${viewState.value.isEnable}, ${getDefaultString(viewState.value.isEnable)}
	leftIcon = $leftIconCode, ${getDefaultString(!viewState.value.hasLeftIcon)}
	rightIcon = $rightIconCode, ${getDefaultString(!viewState.value.hasRightIcon)}
	onClick = { /* 클릭 처리 */ }
)
		""".trimIndent()
	}

	private fun getDefaultString(isDefault: Boolean): String {
		return if (isDefault) {
			"// (default)"
		} else {
			""
		}
	}

	private fun setVariant(variant: ChipVariant) {
		setState { copy(selectedVariant = variant) }
	}

	private fun setSize(size: ChipSize) {
		setState { copy(selectedSize = size) }
	}

	private fun setActive(isActive: Boolean) {
		setState { copy(isActive = isActive) }
	}

	private fun setEnable(isEnable: Boolean) {
		setState { copy(isEnable = isEnable) }
	}

	private fun setLeftIcon(hasLeftIcon: Boolean) {
		setState { copy(hasLeftIcon = hasLeftIcon) }
	}

	private fun setRightIcon(hasRightIcon: Boolean) {
		setState { copy(hasRightIcon = hasRightIcon) }
	}
}
