package com.wanted.android.montage.sample.content.listcell

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoEvent
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoSideEffect
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoViewState
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.ExtraContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.LabelTrailingContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.LeadingContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.TrailingContentType
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedListCellDemoViewModel @Inject constructor() :
	WantedStateViewModel<DSWantedListCellDemoEvent, DSWantedListCellDemoViewState, DSWantedListCellDemoSideEffect>() {

	override fun setInitialState() = DSWantedListCellDemoViewState()

	override fun handleEvents(event: DSWantedListCellDemoEvent) {
		when (event) {
			is DSWantedListCellDemoEvent.ShowCode -> showCode(event.isShowCode)
			is DSWantedListCellDemoEvent.CopyCode -> copyCode()
			is DSWantedListCellDemoEvent.SetVerticalPadding -> setState {
				copy(
					selectedVerticalPadding = event.verticalPadding
				)
			}

			is DSWantedListCellDemoEvent.SetVariant -> setState { copy(selectedVariant = event.variant) }
			is DSWantedListCellDemoEvent.SetDivider -> setState { copy(divider = event.divider) }
			is DSWantedListCellDemoEvent.SetIsEnable -> setState { copy(isEnable = event.isEnable) }
			is DSWantedListCellDemoEvent.SetSelected -> setState { copy(selected = event.selected) }
			is DSWantedListCellDemoEvent.SetChevrons -> setState { copy(chevrons = event.chevrons) }
			is DSWantedListCellDemoEvent.SetEnabledInnerTouch -> setState {
				copy(enabledInnerTouch = event.enabledInnerTouch)
			}

			is DSWantedListCellDemoEvent.SetShowDescription -> setState { copy(showDescription = event.showDescription) }
			else -> handleTextLayoutEvent(event)
		}
	}

	private fun handleTextLayoutEvent(event: DSWantedListCellDemoEvent) {
		when (event) {
			is DSWantedListCellDemoEvent.SetMultiLineText -> setState { copy(multiLineText = event.multiLineText) }
			is DSWantedListCellDemoEvent.SetVerticalAlignCenter -> setState {
				copy(verticalAlignCenter = event.verticalAlignCenter)
			}

			else -> handleSlotContentEvent(event)
		}
	}

	private fun handleSlotContentEvent(event: DSWantedListCellDemoEvent) {
		when (event) {
			is DSWantedListCellDemoEvent.SetLeadingContent -> setState { copy(leadingContent = event.leadingContent) }
			is DSWantedListCellDemoEvent.SetTrailingContent -> setState { copy(trailingContent = event.trailingContent) }
			is DSWantedListCellDemoEvent.SetLabelTrailingContent -> setState {
				copy(
					labelTrailingContent = event.labelTrailingContent
				)
			}

			is DSWantedListCellDemoEvent.SetExtraContent -> setState { copy(extraContent = event.extraContent) }
			else -> Unit
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
		setEffect { DSWantedListCellDemoSideEffect.CopyCode(getCode()) }
	}

	private fun getCode(): String {
		val state = viewState.value
		val lines = mutableListOf<String>()
		lines.add("WantedListCell(")
		lines.add("    text = \"${demoListCellText(state.multiLineText)}\",")
		if (state.showDescription) {
			lines.add("    description = \"설명\",")
		}
		if (state.selectedVariant != WantedListCellDefaults.Variant.Inset) {
			lines.add("    variant = WantedListCellDefaults.Variant.${state.selectedVariant.name},")
		}
		if (state.selectedVerticalPadding != WantedListCellDefaults.VerticalPadding.Medium) {
			lines.add("    verticalPadding = WantedListCellDefaults.VerticalPadding.${state.selectedVerticalPadding.name},")
		}
		if (state.divider) {
			lines.add("    divider = true,")
		}
		if (!state.isEnable) {
			lines.add("    isEnable = false,")
		}
		if (state.selected) {
			lines.add("    selected = true,")
		}
		if (state.chevrons) {
			lines.add("    chevrons = true,")
		}
		if (state.verticalAlignCenter) {
			lines.add("    verticalAlignCenter = true,")
		}
		labelTrailingContentCode(state.labelTrailingContent)?.let { lines.add("    labelTrailingContent = { $it },") }
		extraContentCode(state.extraContent)?.let { lines.add("    extraContent = { $it },") }
		leadingContentCode(state.leadingContent)?.let { lines.add("    leadingContent = { $it },") }
		trailingContentCode(state.trailingContent)?.let { lines.add("    trailingContent = { $it },") }
		if (state.enabledInnerTouch) {
			lines.add("    enabledInnerTouch = true,")
		}
		lines.add("    onClick = {}")
		lines.add(")")
		return lines.joinToString("\n")
	}

	// 코드 보기에는 슬롯에 넣는 요소의 대표 호출만 한 줄로 보여 준다. 상태·enabled 처리 등은 화면 구현을 참고한다.
	private fun leadingContentCode(type: LeadingContentType): String? = when (type) {
		LeadingContentType.None -> null
		LeadingContentType.Icon -> ICON_CODE
		LeadingContentType.CheckBox -> CHECK_BOX_CODE
		LeadingContentType.Radio -> "WantedInput(variant = WantedInputVariant.Radio, checkBoxState = checkBoxState)"
		LeadingContentType.Avatar -> "WantedAvatar(type = WantedAvatarType.Person, size = WantedAvatarSize.Small)"
		LeadingContentType.Multiple -> "${CHECK_BOX_CODE}; $ICON_CODE"
	}

	private fun trailingContentCode(type: TrailingContentType): String? = when (type) {
		TrailingContentType.None -> null
		TrailingContentType.Value -> "Text(text = \"값\")"
		TrailingContentType.Icon -> ICON_CODE
		TrailingContentType.IconButton ->
			"WantedIconButtonNormal(icon = R.drawable.icon_normal_more_vertical, " +
				"size = WantedIconButtonNormalSize.Small)"

		TrailingContentType.TextButton ->
			"WantedTextButton(text = \"Button\", color = WantedTextButtonColor.ASSISTIVE, " +
				"size = WantedTextButtonSize.SMALL)"

		TrailingContentType.ContentBadge -> "WantedContentBadge(text = \"Badge\")"
		TrailingContentType.CheckMark -> "WantedInput(variant = WantedInputVariant.CheckMark, checkBoxState = checkBoxState)"
		TrailingContentType.Switch -> "WantedInput(variant = WantedInputVariant.Switch, checkBoxState = checkBoxState)"
		TrailingContentType.Multiple -> "Text(text = \"값\"); $ICON_CODE"
	}

	private fun labelTrailingContentCode(type: LabelTrailingContentType): String? = when (type) {
		LabelTrailingContentType.None -> null
		LabelTrailingContentType.ContentBadge -> LABEL_BADGE_CODE
		LabelTrailingContentType.Icon -> ICON_CODE
		LabelTrailingContentType.Multiple ->
			"$LABEL_BADGE_CODE; " +
				"WantedContentBadge(text = \"New\", size = ContentBadgeSize.XSmall, color = ContentBadgeColor.Accent)"
	}

	private fun extraContentCode(type: ExtraContentType): String? = when (type) {
		ExtraContentType.None -> null
		ExtraContentType.Text -> "Text(text = \"Extra Content\")"
		ExtraContentType.ContentBadge ->
			"Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) { " +
				"WantedContentBadge(text = \"Badge\"); WantedContentBadge(text = \"Badge\") }"

		ExtraContentType.TextButton -> "WantedTextButton(text = \"Button\", size = WantedTextButtonSize.SMALL)"
	}
}

/**
 * 데모 셀 타이틀. 여러 줄은 슬롯이 텍스트 영역 전체가 아니라 첫 줄에 맞춰지는지 확인하기 위한 값이다.
 */
internal fun demoListCellText(multiLineText: Boolean): String = if (multiLineText) {
	"여러 줄 타이틀입니다. 슬롯이 텍스트 영역 전체가 아니라 첫 줄에 맞춰지는지 확인합니다"
} else {
	"텍스트"
}

private const val ICON_CODE = "Icon(painter = painterResource(R.drawable.icon_normal_bookmark), contentDescription = null)"
private const val CHECK_BOX_CODE = "WantedInput(variant = WantedInputVariant.CheckBox, checkBoxState = checkBoxState)"
private const val LABEL_BADGE_CODE = "WantedContentBadge(text = \"Badge\", size = ContentBadgeSize.XSmall)"
