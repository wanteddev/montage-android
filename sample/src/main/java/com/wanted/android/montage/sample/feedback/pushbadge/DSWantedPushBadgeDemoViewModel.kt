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
class DSWantedPushBadgeDemoViewModel @Inject constructor() :
	WantedStateViewModel<
		DSWantedPushBadgeDemoEvent,
		DSWantedPushBadgeDemoViewState,
		DSWantedPushBadgeDemoSideEffect,
		>() {
	override fun setInitialState() = DSWantedPushBadgeDemoViewState()

	override fun handleEvents(event: DSWantedPushBadgeDemoEvent) {
		when (event) {
			is DSWantedPushBadgeDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedPushBadgeDemoEvent.CopyCode -> copyCode()
			is DSWantedPushBadgeDemoEvent.SetVariant -> setState { copy(selectedVariant = event.variant) }
			is DSWantedPushBadgeDemoEvent.SetSize -> setState { copy(selectedSize = event.size) }
			is DSWantedPushBadgeDemoEvent.SetPosition -> setState { copy(selectedPosition = event.position) }
			is DSWantedPushBadgeDemoEvent.SetOutlineBorder -> setState { copy(outlineBorder = event.outlineBorder) }
			is DSWantedPushBadgeDemoEvent.SetOutlineBorderColor -> setState { copy(selectedOutlineBorderColor = event.outlineBorderColor) }
			is DSWantedPushBadgeDemoEvent.SetInset -> setState { copy(insetEnabled = event.insetEnabled) }
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

		val contentLine = when (state.selectedVariant) {
			PushBadgeVariant.Dot -> ""
			PushBadgeVariant.Text -> "\n\ttext = \"${state.sampleText}\","
			PushBadgeVariant.MaxCount ->
				"\n\ttext = \"${state.sampleCount}\",\n\tmaxCount = ${state.sampleMaxCount},"
		}
		val outlineBorderLine = if (state.outlineBorder) "\n\toutlineBorder = true," else ""
		val outlineBorderColorLine = if (state.outlineBorder && state.selectedOutlineBorderColor != OutlineBorderColorOption.Default) {
			"\n\toutlineBorderColor = ${state.selectedOutlineBorderColor.codeExpression},"
		} else {
			""
		}
		val insetLine = if (state.insetEnabled) {
			"\n\tinset = DpOffset(${state.sampleInset}.dp, ${state.sampleInset}.dp),"
		} else {
			""
		}

		return """
WantedPushBadge(
	variant = PushBadgeVariant.${state.selectedVariant.name}, ${defaultString(state.selectedVariant == PushBadgeVariant.Dot)}
	size = PushBadgeSize.${state.selectedSize.name}, ${defaultString(state.selectedSize == PushBadgeSize.XSmall)}
	position = PushBadgePosition.${state.selectedPosition.name}, ${defaultString(state.selectedPosition == PushBadgePosition.TopEnd)}$contentLine$outlineBorderLine$outlineBorderColorLine$insetLine
)
		""".trimIndent()
	}

	private fun defaultString(isDefault: Boolean): String {
		return if (isDefault) "// (default)" else ""
	}
}
