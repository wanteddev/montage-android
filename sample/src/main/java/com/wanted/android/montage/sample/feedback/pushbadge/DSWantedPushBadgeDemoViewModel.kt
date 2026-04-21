package com.wanted.android.montage.sample.feedback.pushbadge

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoEvent
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoSideEffect
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreenContract.DSWantedPushBadgeDemoViewState
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedPushBadgeDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPushBadgeDemoEvent, DSWantedPushBadgeDemoViewState, DSWantedPushBadgeDemoSideEffect>() {
	override fun setInitialState() = DSWantedPushBadgeDemoViewState()

	override fun handleEvents(event: DSWantedPushBadgeDemoEvent) {
		when (event) {
			is DSWantedPushBadgeDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedPushBadgeDemoEvent.CopyCode -> copyCode()
			is DSWantedPushBadgeDemoEvent.SetVariant -> setVariant(event.variant)
			is DSWantedPushBadgeDemoEvent.SetSize -> setSize(event.size)
			is DSWantedPushBadgeDemoEvent.SetPosition -> setPosition(event.position)
			is DSWantedPushBadgeDemoEvent.SetBordered -> setBordered(event.bordered)
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
		setEffect { DSWantedPushBadgeDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		val componentName = if (state.bordered) "WantedPushBadgeBorder" else "WantedPushBadge"
		val countLine = if (state.selectedVariant == PushBadgeVariant.Number) {
			"\n\tcount = \"1\","
		} else {
			""
		}
		val borderedLine = if (state.bordered) {
			"\n\tbordered = true,"
		} else {
			""
		}

		return """
$componentName(
	variant = PushBadgeVariant.${state.selectedVariant.name}, ${getDefaultString(state.selectedVariant == PushBadgeVariant.Dot)}
	size = PushBadgeSize.${state.selectedSize.name}, ${getDefaultString(state.selectedSize == PushBadgeSize.XSmall)}
	position = PushBadgePosition.${state.selectedPosition.name}, ${getDefaultString(state.selectedPosition == PushBadgePosition.TopEnd)}$countLine$borderedLine
)
		""".trimIndent()
	}

	private fun getDefaultString(isDefault: Boolean): String {
		return if (isDefault) "// (default)" else ""
	}

	private fun setVariant(variant: PushBadgeVariant) {
		setState { copy(selectedVariant = variant) }
	}

	private fun setSize(size: PushBadgeSize) {
		setState { copy(selectedSize = size) }
	}

	private fun setPosition(position: PushBadgePosition) {
		setState { copy(selectedPosition = position) }
	}

	private fun setBordered(bordered: Boolean) {
		setState { copy(bordered = bordered) }
	}
}
