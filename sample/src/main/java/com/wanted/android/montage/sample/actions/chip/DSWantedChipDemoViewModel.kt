package com.wanted.android.montage.sample.actions.chip

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.ChipCustomColor
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
			is DSWantedChipDemoEvent.SetLeadingContent -> setLeadingContent(event.hasLeadingContent)
			is DSWantedChipDemoEvent.SetTrailingContent -> setTrailingContent(event.hasTrailingContent)
			is DSWantedChipDemoEvent.SetCustomColor -> setCustomColor(event.customColor)
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
		val state = viewState.value
		val leadingContentCode = if (state.hasLeadingContent) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		val trailingContentCode = if (state.hasTrailingContent) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		if (state.selectedCustomColor != ChipCustomColor.None) {
			return getCustomColorCode(state, leadingContentCode, trailingContentCode)
		}

		return """
WantedChip(
	text = "텍스트",
	variant = ChipVariant.${state.selectedVariant.name}, ${getDefaultString(state.selectedVariant == ChipVariant.Solid)}
	size = ChipSize.${state.selectedSize.name}, ${getDefaultString(state.selectedSize == ChipSize.Medium)}
	isActive = ${state.isActive}, ${getDefaultString(!state.isActive)}
	isEnable = ${state.isEnable}, ${getDefaultString(state.isEnable)}
	leadingContent = $leadingContentCode, ${getDefaultString(!state.hasLeadingContent)}
	trailingContent = $trailingContentCode, ${getDefaultString(!state.hasTrailingContent)}
	onClick = { /* 클릭 처리 */ }
)
		""".trimIndent()
	}

	private fun getCustomColorCode(
		state: DSWantedChipDemoViewState,
		leadingContentCode: String,
		trailingContentCode: String
	): String {
		val colorToken = when (state.selectedCustomColor) {
			ChipCustomColor.Primary -> "DesignSystemTheme.colors.surfaceBrandPrimary"
			ChipCustomColor.Negative -> "DesignSystemTheme.colors.foregroundNegativePrimary"
			ChipCustomColor.Positive -> "DesignSystemTheme.colors.foregroundPositivePrimary"
			ChipCustomColor.None -> "DesignSystemTheme.colors.surfaceBrandPrimary"
		}
		val borderLine = if (state.selectedVariant == ChipVariant.Outlined) {
			"\n\t\tborderColor = $colorToken.copy(alpha = OPACITY_28),"
		} else {
			""
		}

		return """
WantedChip(
	text = "텍스트",
	leadingContent = $leadingContentCode,
	trailingContent = $trailingContentCode,
	chipDefault = WantedChipDefaults.getDefault(
		variant = ChipVariant.${state.selectedVariant.name},
		size = ChipSize.${state.selectedSize.name},
		isActive = ${state.isActive},
		isEnable = ${state.isEnable},
		backgroundColor = $colorToken.copy(alpha = OPACITY_5),$borderLine
	),
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

	private fun setLeadingContent(hasLeadingContent: Boolean) {
		setState { copy(hasLeadingContent = hasLeadingContent) }
	}

	private fun setTrailingContent(hasTrailingContent: Boolean) {
		setState { copy(hasTrailingContent = hasTrailingContent) }
	}

	private fun setCustomColor(customColor: ChipCustomColor) {
		setState { copy(selectedCustomColor = customColor) }
	}
}
