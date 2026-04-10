package com.wanted.android.montage.sample.content.playbadge

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoEvent
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoSideEffect
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoViewState
import com.wanted.android.wanted.design.contents.playtime.playbadge.WantedPlayBadgeDefaults.Size
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedPlayBadgeDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedPlayBadgeDemoEvent, DSWantedPlayBadgeDemoViewState, DSWantedPlayBadgeDemoSideEffect>() {
	override fun setInitialState() = DSWantedPlayBadgeDemoViewState()

	override fun handleEvents(event: DSWantedPlayBadgeDemoEvent) {
		when (event) {
			is DSWantedPlayBadgeDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedPlayBadgeDemoEvent.CopyCode -> copyCode()
			is DSWantedPlayBadgeDemoEvent.SetSize -> setSize(event.size)
			is DSWantedPlayBadgeDemoEvent.SetIsAlternative -> setIsAlternative(event.isAlternative)
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
		setEffect { DSWantedPlayBadgeDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		return """
WantedPlayBadge(
	size = Size.${viewState.value.selectedSize.name}, ${getDefaultString(viewState.value.selectedSize == Size.Medium)}
	isAlternative = ${viewState.value.isAlternative}, ${getDefaultString(!viewState.value.isAlternative)}
	modifier = Modifier
)
		""".trimIndent()
	}

	private fun getDefaultString(isDefault: Boolean): String {
		return if (isDefault) "// (default)" else ""
	}

	private fun setSize(size: Size) {
		setState { copy(selectedSize = size) }
	}

	private fun setIsAlternative(isAlternative: Boolean) {
		setState { copy(isAlternative = isAlternative) }
	}
}
