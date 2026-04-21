package com.wanted.android.montage.sample.input.filterbutton

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoEvent
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoSideEffect
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreenContract.DSWantedFilterButtonDemoViewState
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedFilterButtonDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedFilterButtonDemoEvent, DSWantedFilterButtonDemoViewState, DSWantedFilterButtonDemoSideEffect>() {
	override fun setInitialState() = DSWantedFilterButtonDemoViewState()

	override fun handleEvents(event: DSWantedFilterButtonDemoEvent) {
		when (event) {
			is DSWantedFilterButtonDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedFilterButtonDemoEvent.CopyCode -> copyCode()
			is DSWantedFilterButtonDemoEvent.SetVariant -> setState { copy(selectedVariant = event.variant) }
			is DSWantedFilterButtonDemoEvent.SetSize -> setState { copy(selectedSize = event.size) }
			is DSWantedFilterButtonDemoEvent.SetActive -> setState { copy(isActive = event.isActive) }
			is DSWantedFilterButtonDemoEvent.SetEnable -> setState { copy(isEnable = event.isEnable) }
			is DSWantedFilterButtonDemoEvent.SetExpend -> setState { copy(isExpend = event.isExpend) }
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
		setEffect { DSWantedFilterButtonDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		val activeLabelLine = if (state.isActive) {
			"\n\tactiveLabel = \"1\","
		} else {
			""
		}

		return """
WantedFilterButton(
	text = "텍스트",${activeLabelLine}
	variant = FilterButtonVariant.${state.selectedVariant.name}, ${getDefaultString(state.selectedVariant == FilterButtonVariant.Solid)}
	size = FilterButtonSize.${state.selectedSize.name}, ${getDefaultString(state.selectedSize == FilterButtonSize.Small)}
	isActive = ${state.isActive}, ${getDefaultString(!state.isActive)}
	isEnable = ${state.isEnable}, ${getDefaultString(state.isEnable)}
	isExpend = ${state.isExpend}, ${getDefaultString(!state.isExpend)}
)
		""".trimIndent()
	}

	private fun getDefaultString(isDefault: Boolean): String {
		return if (isDefault) "// (default)" else ""
	}
}
