package com.wanted.android.montage.sample.content.sectionheader

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoEvent
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoSideEffect
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedSectionHeaderDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedSectionHeaderDemoEvent, DSWantedSectionHeaderDemoViewState, DSWantedSectionHeaderDemoSideEffect>() {
	override fun setInitialState() = DSWantedSectionHeaderDemoViewState()

	override fun handleEvents(event: DSWantedSectionHeaderDemoEvent) {
		when (event) {
			is DSWantedSectionHeaderDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedSectionHeaderDemoEvent.CopyCode -> copyCode()
			is DSWantedSectionHeaderDemoEvent.ShowAll -> setState { copy(isShowAll = event.isShowAll) }
			is DSWantedSectionHeaderDemoEvent.SetSize -> setState { copy(size = event.size) }
			is DSWantedSectionHeaderDemoEvent.SetHeadingContents -> setState { copy(headingContents = event.isChecked) }
			is DSWantedSectionHeaderDemoEvent.SetTrailingContent -> setState { copy(trailingContent = event.isChecked) }
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
		setEffect { DSWantedSectionHeaderDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		return """
			WantedSectionHeader(
				title = "제목",
				modifier = Modifier,
				size = Size.${state.size.name},${if (state.headingContents) """
				headingContents = { Icon(...) },""" else ""}${if (state.trailingContent) """
				trailingContent = { Icon(...) },""" else ""}
			)
		""".trimIndent()
	}
}
