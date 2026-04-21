package com.wanted.android.montage.sample.content.accordion

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoEvent
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoSideEffect
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedAccordionDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedAccordionDemoEvent, DSWantedAccordionDemoViewState, DSWantedAccordionDemoSideEffect>() {
	override fun setInitialState() = DSWantedAccordionDemoViewState()

	override fun handleEvents(event: DSWantedAccordionDemoEvent) {
		when (event) {
			is DSWantedAccordionDemoEvent.InitState -> setState { event.viewState }
			is DSWantedAccordionDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedAccordionDemoEvent.CopyCode -> copyCode()
			is DSWantedAccordionDemoEvent.ShowAll -> setState { copy(isShowAll = event.isShowAll) }
			is DSWantedAccordionDemoEvent.ToggleExpanded -> {
				setState { copy(isExpanded = event.isExpanded) }
			}
			is DSWantedAccordionDemoEvent.SetDescription -> {
				setState { copy(description = event.isChecked) }
			}
			is DSWantedAccordionDemoEvent.SetFillWidth -> {
				setState { copy(fillWidth = event.isChecked) }
			}
			is DSWantedAccordionDemoEvent.SetDivider -> {
				setState { copy(divider = event.isChecked) }
			}
			is DSWantedAccordionDemoEvent.SetLeadingIcon -> {
				setState { copy(leadingIcon = event.isChecked) }
			}
			is DSWantedAccordionDemoEvent.SetContent -> {
				setState { copy(content = event.isChecked) }
			}
			is DSWantedAccordionDemoEvent.SetVerticalPadding -> {
				setState { copy(verticalPadding = event.verticalPadding) }
			}
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
		setEffect { DSWantedAccordionDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		return """
			WantedAccordion(
				title = "제목",
				isExpanded = ${state.isExpanded},
				fillWidth = ${state.fillWidth},
				divider = ${state.divider},
				verticalPadding = VerticalPadding.${state.verticalPadding.name},${if (state.description) """
				description = "제목에 대한 상세 내용을 입력해주세요.",""" else ""}${if (state.leadingIcon) """
				leadingIcon = { Icon(...) },""" else ""}${if (state.content) """
				content = { /* 확장 콘텐츠 */ },""" else ""}
				onChangeExpanded = { expanded -> }
			)
		""".trimIndent()
	}
}
