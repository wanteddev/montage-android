package com.wanted.android.montage.sample.content.listcell

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoEvent
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoSideEffect
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoViewEvent
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.DSWantedListCellDemoViewState
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.ExtraContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.LabelTrailingContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.LeadingContentType
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreenContract.TrailingContentType
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormal
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatar
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeColor
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import com.wanted.android.wanted.design.input.input.WantedInput
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputSize
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputVariant
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedListCellDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedListCellDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val clipboardManager = LocalClipboardManager.current
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
		when (sideEffect) {
			is DSWantedListCellDemoSideEffect.CopyCode -> {
				clipboardManager.setText(AnnotatedString(sideEffect.code))
			}
		}
	}

	DSWantedListCellDemoScreenImpl(
		modifier = modifier,
		viewState = viewState
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedListCellDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedListCellDemoViewEvent.OnClickShowCode -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.ShowCode(true))
			}

			is DSWantedListCellDemoViewEvent.OnSelectVerticalPadding -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.SetVerticalPadding(viewEvent.verticalPadding))
			}

			is DSWantedListCellDemoViewEvent.OnSelectVariant -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.SetVariant(viewEvent.variant))
			}

			else -> handleOptionViewEvent(viewEvent, viewModel)
		}
	}

	if (viewState.isShowCode) {
		WantedPopup(
			positive = "코드 복사",
			onClickPositive = {
				viewModel.setEvent(DSWantedListCellDemoEvent.CopyCode)
				viewModel.setEvent(DSWantedListCellDemoEvent.ShowCode(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedListCellDemoEvent.ShowCode(false))
			},
			content = {
				Text(text = viewState.code)
			}
		)
	}
}

private fun handleOptionViewEvent(
	viewEvent: DSWantedListCellDemoViewEvent,
	viewModel: DSWantedListCellDemoViewModel
) {
	when (viewEvent) {
		is DSWantedListCellDemoViewEvent.OnChangeDivider -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetDivider(viewEvent.divider))
		}

		is DSWantedListCellDemoViewEvent.OnChangeIsEnable -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetIsEnable(viewEvent.isEnable))
		}

		is DSWantedListCellDemoViewEvent.OnChangeSelected -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetSelected(viewEvent.selected))
		}

		is DSWantedListCellDemoViewEvent.OnChangeChevrons -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetChevrons(viewEvent.chevrons))
		}

		is DSWantedListCellDemoViewEvent.OnChangeEnabledInnerTouch -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetEnabledInnerTouch(viewEvent.enabledInnerTouch))
		}

		is DSWantedListCellDemoViewEvent.OnChangeShowDescription -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetShowDescription(viewEvent.showDescription))
		}

		is DSWantedListCellDemoViewEvent.OnChangeMultiLineText -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetMultiLineText(viewEvent.multiLineText))
		}

		is DSWantedListCellDemoViewEvent.OnChangeVerticalAlignCenter -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetVerticalAlignCenter(viewEvent.verticalAlignCenter))
		}

		is DSWantedListCellDemoViewEvent.OnSelectLeadingContent -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetLeadingContent(viewEvent.leadingContent))
		}

		is DSWantedListCellDemoViewEvent.OnSelectTrailingContent -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetTrailingContent(viewEvent.trailingContent))
		}

		is DSWantedListCellDemoViewEvent.OnSelectLabelTrailingContent -> {
			viewModel.setEvent(
				DSWantedListCellDemoEvent.SetLabelTrailingContent(viewEvent.labelTrailingContent)
			)
		}

		is DSWantedListCellDemoViewEvent.OnSelectExtraContent -> {
			viewModel.setEvent(DSWantedListCellDemoEvent.SetExtraContent(viewEvent.extraContent))
		}

		else -> Unit
	}
}

@Composable
private fun DSWantedListCellDemoScreenImpl(
	modifier: Modifier = Modifier,
	viewState: DSWantedListCellDemoViewState,
	onViewEvent: (DSWantedListCellDemoViewEvent) -> Unit
) {
	// 클릭 차단 확인용 화면 로컬 카운터. 디자인시스템 동작 확인 목적이라 ViewState 로 올리지 않는다.
	var clickCount by remember { mutableIntStateOf(0) }
	// 슬롯에 넣은 체크박스·라디오·스위치의 체크 상태. 위와 같은 이유로 화면 로컬에 두고,
	// leading·trailing 에 함께 넣어도 서로 따라 바뀌지 않도록 슬롯별로 나눈다.
	var leadingChecked by remember { mutableStateOf(false) }
	var trailingChecked by remember { mutableStateOf(false) }

	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedListCell") {
				onViewEvent(DSWantedListCellDemoViewEvent.OnClickBack)
			}
		},
		bottomBar = {
			WantedActionArea(
				modifier = Modifier.navigationBarsPadding(),
				background = true,
				main = {
					WantedButton(
						modifier = Modifier.fillMaxWidth(),
						text = "코드 보기",
						onClick = {
							onViewEvent(DSWantedListCellDemoViewEvent.OnClickShowCode)
						}
					)
				}
			)
		}
	) { innerPadding ->
		DSWantedListCellDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			preview = {
				WantedListCell(
					text = demoListCellText(viewState.multiLineText),
					description = if (viewState.showDescription) "설명" else "",
					variant = viewState.selectedVariant,
					verticalPadding = viewState.selectedVerticalPadding,
					divider = viewState.divider,
					isEnable = viewState.isEnable,
					selected = viewState.selected,
					chevrons = viewState.chevrons,
					verticalAlignCenter = viewState.verticalAlignCenter,
					labelTrailingContent = viewState.labelTrailingContent.takeIf {
						it != LabelTrailingContentType.None
					}?.let { type ->
						{ DSWantedListCellDemoLabelTrailingSample(type, enabled = viewState.isEnable) }
					},
					extraContent = viewState.extraContent.takeIf { it != ExtraContentType.None }?.let { type ->
						{ DSWantedListCellDemoExtraSample(type, enabled = viewState.isEnable) }
					},
					leadingContent = viewState.leadingContent.takeIf { it != LeadingContentType.None }?.let { type ->
						{
							DSWantedListCellDemoLeadingSample(
								type = type,
								enabled = viewState.isEnable,
								checked = leadingChecked,
								onCheckedChange = { leadingChecked = it }
							)
						}
					},
					trailingContent = viewState.trailingContent.takeIf { it != TrailingContentType.None }?.let { type ->
						{
							DSWantedListCellDemoTrailingSample(
								type = type,
								enabled = viewState.isEnable,
								checked = trailingChecked,
								onCheckedChange = { trailingChecked = it }
							)
						}
					},
					enabledInnerTouch = viewState.enabledInnerTouch,
					onClick = { clickCount++ }
				)
			},
			clickCount = {
				Text(
					text = "클릭 횟수 : $clickCount (isEnable=false 면 증가하지 않음)",
					style = DesignSystemTheme.typography.label2Regular,
					color = DesignSystemTheme.colors.foregroundNeutralTertiary
				)
			},
			verticalPadding = {
				WantedSelect(
					value = "VerticalPadding : ${viewState.selectedVerticalPadding.name}",
					selectedValue = viewState.selectedVerticalPadding.name,
					selectValueList = viewState.verticalPaddingList.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectVerticalPadding(
								WantedListCellDefaults.VerticalPadding.valueOf(it)
							)
						)
					}
				)
			},
			variant = {
				WantedSelect(
					value = "variant : ${viewState.selectedVariant.name}",
					selectedValue = viewState.selectedVariant.name,
					selectValueList = viewState.variantList.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectVariant(
								WantedListCellDefaults.Variant.valueOf(it)
							)
						)
					}
				)
			},
			divider = {
				DSWantedOptionSwitchCell(
					text = "divider : ${viewState.divider}",
					checkState = viewState.divider,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeDivider(it))
					}
				)
			},
			isEnable = {
				DSWantedOptionSwitchCell(
					text = "isEnable : ${viewState.isEnable}",
					checkState = viewState.isEnable,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeIsEnable(it))
					}
				)
			},
			selected = {
				DSWantedOptionSwitchCell(
					text = "selected : ${viewState.selected}",
					checkState = viewState.selected,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeSelected(it))
					}
				)
			},
			chevrons = {
				DSWantedOptionSwitchCell(
					text = "chevrons : ${viewState.chevrons}",
					checkState = viewState.chevrons,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeChevrons(it))
					}
				)
			},
			enabledInnerTouch = {
				DSWantedOptionSwitchCell(
					text = "enabledInnerTouch : ${viewState.enabledInnerTouch}",
					checkState = viewState.enabledInnerTouch,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeEnabledInnerTouch(it))
					}
				)
			},
			showDescription = {
				DSWantedOptionSwitchCell(
					text = "description : ${if (viewState.showDescription) "설명" else "none"}",
					checkState = viewState.showDescription,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeShowDescription(it))
					}
				)
			},
			multiLineText = {
				DSWantedOptionSwitchCell(
					text = "multiLineText : ${viewState.multiLineText}",
					checkState = viewState.multiLineText,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeMultiLineText(it))
					}
				)
			},
			verticalAlignCenter = {
				DSWantedOptionSwitchCell(
					text = "verticalAlignCenter : ${viewState.verticalAlignCenter}",
					checkState = viewState.verticalAlignCenter,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeVerticalAlignCenter(it))
					}
				)
			},
			leadingContent = {
				WantedSelect(
					value = "leadingContent : ${viewState.leadingContent.name}",
					selectedValue = viewState.leadingContent.name,
					selectValueList = LeadingContentType.entries.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectLeadingContent(LeadingContentType.valueOf(it))
						)
					}
				)
			},
			trailingContent = {
				WantedSelect(
					value = "trailingContent : ${viewState.trailingContent.name}",
					selectedValue = viewState.trailingContent.name,
					selectValueList = TrailingContentType.entries.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectTrailingContent(TrailingContentType.valueOf(it))
						)
					}
				)
			},
			labelTrailingContent = {
				WantedSelect(
					value = "labelTrailingContent : ${viewState.labelTrailingContent.name}",
					selectedValue = viewState.labelTrailingContent.name,
					selectValueList = LabelTrailingContentType.entries.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectLabelTrailingContent(
								LabelTrailingContentType.valueOf(it)
							)
						)
					}
				)
			},
			extraContent = {
				WantedSelect(
					value = "extraContent : ${viewState.extraContent.name}",
					selectedValue = viewState.extraContent.name,
					selectValueList = ExtraContentType.entries.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedListCellDemoViewEvent.OnSelectExtraContent(ExtraContentType.valueOf(it))
						)
					}
				)
			}
		)
	}
}

@Composable
private fun DSWantedListCellDemoScreenLayout(
	modifier: Modifier,
	preview: @Composable () -> Unit,
	clickCount: @Composable () -> Unit,
	verticalPadding: @Composable () -> Unit,
	variant: @Composable () -> Unit,
	divider: @Composable () -> Unit,
	isEnable: @Composable () -> Unit,
	selected: @Composable () -> Unit,
	chevrons: @Composable () -> Unit,
	enabledInnerTouch: @Composable () -> Unit,
	showDescription: @Composable () -> Unit,
	multiLineText: @Composable () -> Unit,
	verticalAlignCenter: @Composable () -> Unit,
	leadingContent: @Composable () -> Unit,
	trailingContent: @Composable () -> Unit,
	labelTrailingContent: @Composable () -> Unit,
	extraContent: @Composable () -> Unit,
) {
	Column(
		modifier = modifier.padding(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Text(
			text = "Preview",
			style = WantedTextStyle(
				colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
				style = DesignSystemTheme.typography.heading2Bold
			)
		)

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = colorResource(R.color.line_neutral_primary),
					shape = RoundedCornerShape(8.dp)
				)
				.padding(20.dp),
			contentAlignment = Alignment.Center
		) {
			preview()
		}

		clickCount()

		Spacer(Modifier.size(10.dp))

		Column(
			modifier = Modifier
				.verticalScroll(rememberScrollState())
				.padding(vertical = 24.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				modifier = Modifier.align(Alignment.Start),
				text = "Option",
				style = WantedTextStyle(
					colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
					style = DesignSystemTheme.typography.heading2Bold
				)
			)

			verticalPadding()
			variant()
			divider()
			isEnable()
			selected()
			chevrons()
			enabledInnerTouch()
			showDescription()
			multiLineText()
			verticalAlignCenter()
			leadingContent()
			trailingContent()
			labelTrailingContent()
			extraContent()
		}
	}
}

// 슬롯 콘텐츠는 셀의 isEnable 을 자동으로 따르지 않으므로(WantedListCell KDoc), 데모도 호출부처럼
// 컨트롤·버튼에는 enabled 를, 아이콘·텍스트에는 disable 색을 적용한다.
// 아바타와 ContentBadge 는 disable 색·enabled 가 없어 색으로 표현할 수 없으므로 Opacity/43 을 적용한다.
@Composable
private fun RowScope.DSWantedListCellDemoLeadingSample(
	type: LeadingContentType,
	enabled: Boolean,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit
) {
	when (type) {
		LeadingContentType.Icon -> DSWantedListCellDemoIcon(enabled = enabled)
		LeadingContentType.CheckBox -> DSWantedListCellDemoControl(
			variant = WantedInputVariant.CheckBox,
			enabled = enabled,
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		LeadingContentType.Radio -> DSWantedListCellDemoControl(
			variant = WantedInputVariant.Radio,
			enabled = enabled,
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		LeadingContentType.Avatar -> WantedAvatar(
			modifier = Modifier.alpha(if (enabled) 1f else OPACITY_43),
			type = WantedAvatarType.Person,
			size = WantedAvatarSize.Small
		)

		LeadingContentType.Multiple -> {
			DSWantedListCellDemoControl(
				variant = WantedInputVariant.CheckBox,
				enabled = enabled,
				checked = checked,
				onCheckedChange = onCheckedChange
			)
			DSWantedListCellDemoIcon(enabled = enabled)
		}

		LeadingContentType.None -> Unit
	}
}

@Composable
private fun RowScope.DSWantedListCellDemoTrailingSample(
	type: TrailingContentType,
	enabled: Boolean,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit
) {
	when (type) {
		TrailingContentType.Value -> DSWantedListCellDemoValue(enabled = enabled)
		TrailingContentType.Icon -> DSWantedListCellDemoIcon(enabled = enabled)
		TrailingContentType.IconButton -> WantedIconButtonNormal(
			icon = R.drawable.icon_normal_more_vertical,
			size = WantedIconButtonNormalSize.Small,
			enabled = enabled
		)

		TrailingContentType.TextButton -> WantedTextButton(
			text = "Button",
			color = WantedTextButtonColor.ASSISTIVE,
			size = WantedTextButtonSize.SMALL,
			enabled = enabled
		)

		TrailingContentType.ContentBadge -> DSWantedListCellDemoBadge(text = "Badge", enabled = enabled)
		TrailingContentType.CheckMark -> DSWantedListCellDemoControl(
			variant = WantedInputVariant.CheckMark,
			enabled = enabled,
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		TrailingContentType.Switch -> DSWantedListCellDemoControl(
			variant = WantedInputVariant.Switch,
			enabled = enabled,
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		TrailingContentType.Multiple -> {
			DSWantedListCellDemoValue(enabled = enabled)
			DSWantedListCellDemoIcon(enabled = enabled)
		}

		TrailingContentType.None -> Unit
	}
}

@Composable
private fun RowScope.DSWantedListCellDemoLabelTrailingSample(type: LabelTrailingContentType, enabled: Boolean) {
	when (type) {
		LabelTrailingContentType.ContentBadge -> DSWantedListCellDemoBadge(
			text = "Badge",
			enabled = enabled,
			size = ContentBadgeSize.XSmall
		)

		LabelTrailingContentType.Icon -> DSWantedListCellDemoIcon(enabled = enabled)
		LabelTrailingContentType.Multiple -> {
			DSWantedListCellDemoBadge(text = "Badge", enabled = enabled, size = ContentBadgeSize.XSmall)
			DSWantedListCellDemoBadge(
				text = "New",
				enabled = enabled,
				size = ContentBadgeSize.XSmall,
				color = ContentBadgeColor.Accent
			)
		}

		LabelTrailingContentType.None -> Unit
	}
}

@Composable
private fun DSWantedListCellDemoExtraSample(type: ExtraContentType, enabled: Boolean) {
	when (type) {
		ExtraContentType.Text -> Text(
			text = "Extra Content",
			style = DesignSystemTheme.typography.label2Regular,
			color = if (enabled) {
				DesignSystemTheme.colors.foregroundNeutralTertiary
			} else {
				DesignSystemTheme.colors.foregroundDisablePrimary
			}
		)

		// extraContent 는 가로 배치 시 권장 간격이 6dp 다(WantedListCell KDoc).
		ExtraContentType.ContentBadge -> Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
			DSWantedListCellDemoBadge(text = "Badge", enabled = enabled)
			DSWantedListCellDemoBadge(text = "Badge", enabled = enabled)
		}

		ExtraContentType.TextButton -> WantedTextButton(
			text = "Button",
			size = WantedTextButtonSize.SMALL,
			enabled = enabled
		)

		ExtraContentType.None -> Unit
	}
}

@Composable
private fun DSWantedListCellDemoIcon(enabled: Boolean) {
	Icon(
		modifier = Modifier.size(22.dp),
		painter = painterResource(R.drawable.icon_normal_bookmark),
		contentDescription = null,
		tint = if (enabled) {
			DesignSystemTheme.colors.foregroundNeutralPrimary
		} else {
			DesignSystemTheme.colors.foregroundDisablePrimary
		}
	)
}

@Composable
private fun DSWantedListCellDemoBadge(
	text: String,
	enabled: Boolean,
	size: ContentBadgeSize = ContentBadgeSize.Small,
	color: ContentBadgeColor = ContentBadgeColor.Neutral
) {
	WantedContentBadge(
		modifier = Modifier.alpha(if (enabled) 1f else OPACITY_43),
		text = text,
		size = size,
		color = color
	)
}

@Composable
private fun DSWantedListCellDemoValue(enabled: Boolean) {
	Text(
		text = "값",
		style = DesignSystemTheme.typography.body2Regular,
		color = if (enabled) {
			DesignSystemTheme.colors.foregroundNeutralTertiary
		} else {
			DesignSystemTheme.colors.foregroundDisablePrimary
		}
	)
}

@Composable
private fun DSWantedListCellDemoControl(
	variant: WantedInputVariant,
	enabled: Boolean,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit
) {
	WantedInput(
		variant = variant,
		size = WantedInputSize.Small,
		checkBoxState = if (checked) CheckBoxState.Checked else CheckBoxState.Unchecked,
		enabled = enabled,
		onCheckedChange = onCheckedChange
	)
}

@DevicePreviews
@Composable
private fun DSWantedListCellDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedListCellDemoScreenImpl(
			viewState = DSWantedListCellDemoViewState(),
			onViewEvent = {}
		)
	}
}
