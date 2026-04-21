package com.wanted.android.montage.sample.content.contentbadge

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoEvent
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoSideEffect
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoViewState
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeColor
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedContentBadgeDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedContentBadgeDemoEvent, DSWantedContentBadgeDemoViewState, DSWantedContentBadgeDemoSideEffect>() {
	override fun setInitialState() = DSWantedContentBadgeDemoViewState()

	override fun handleEvents(event: DSWantedContentBadgeDemoEvent) {
		when (event) {
			is DSWantedContentBadgeDemoEvent.InitState -> setState { event.viewState }
			is DSWantedContentBadgeDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedContentBadgeDemoEvent.CopyCode -> copyCode()
			is DSWantedContentBadgeDemoEvent.SetType -> setType(event.type)
			is DSWantedContentBadgeDemoEvent.SetSize -> setSize(event.size)
			is DSWantedContentBadgeDemoEvent.SetColor -> setColor(event.color)
			is DSWantedContentBadgeDemoEvent.SetLeadingIcon -> setLeadingIcon(event.hasLeadingIcon)
			is DSWantedContentBadgeDemoEvent.SetTrailingIcon -> setTrailingIcon(event.hasTrailingIcon)
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
		setEffect { DSWantedContentBadgeDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val leadingIconCode = if (viewState.value.hasLeadingIcon) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		val trailingIconCode = if (viewState.value.hasTrailingIcon) {
			"R.drawable.icon_normal_bookmark"
		} else {
			"null"
		}

		return """
WantedContentBadge(
	text = "Badge",
	type = ContentBadgeType.${viewState.value.selectedType.name}, ${getDefaultString(viewState.value.selectedType == ContentBadgeType.Solid)}
	size = ContentBadgeSize.${viewState.value.selectedSize.name}, ${getDefaultString(viewState.value.selectedSize == ContentBadgeSize.Small)}
	color = ContentBadgeColor.${viewState.value.selectedColor.name}, ${getDefaultString(viewState.value.selectedColor == ContentBadgeColor.Neutral)}
	leadingDrawable = $leadingIconCode, ${getDefaultString(!viewState.value.hasLeadingIcon)}
	trailingDrawable = $trailingIconCode, ${getDefaultString(!viewState.value.hasTrailingIcon)}
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

	private fun setType(type: ContentBadgeType) {
		setState { copy(selectedType = type) }
	}

	private fun setSize(size: ContentBadgeSize) {
		setState { copy(selectedSize = size) }
	}

	private fun setColor(color: ContentBadgeColor) {
		setState { copy(selectedColor = color) }
	}

	private fun setLeadingIcon(hasLeadingIcon: Boolean) {
		setState { copy(hasLeadingIcon = hasLeadingIcon) }
	}

	private fun setTrailingIcon(hasTrailingIcon: Boolean) {
		setState { copy(hasTrailingIcon = hasTrailingIcon) }
	}
}
