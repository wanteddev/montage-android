package com.wanted.android.montage.sample.content.accordion

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoEvent
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoSideEffect
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoViewEvent
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreenContract.DSWantedAccordionDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.accordion.WantedAccordion
import com.wanted.android.wanted.design.contents.accordion.WantedAccordionDefaults.VerticalPadding
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAccordionDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedAccordionDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val clipboardManager = LocalClipboardManager.current
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
		when (sideEffect) {
			is DSWantedAccordionDemoSideEffect.CopyCode -> {
				clipboardManager.setText(AnnotatedString(sideEffect.code))
			}
		}
	}

	DSWantedAccordionDemoScreenContent(
		modifier = modifier,
		viewState = viewState
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedAccordionDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedAccordionDemoViewEvent.OnClickShowCode -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowCode(true))
			}
			is DSWantedAccordionDemoViewEvent.OnClickCopyCode -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.CopyCode)
			}
			is DSWantedAccordionDemoViewEvent.OnClickShowAll -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowAll(true))
			}
			is DSWantedAccordionDemoViewEvent.OnClickExpanded -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ToggleExpanded(viewEvent.isExpanded))
			}
			is DSWantedAccordionDemoViewEvent.OnClickDescription -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetDescription(viewEvent.isChecked))
			}
			is DSWantedAccordionDemoViewEvent.OnClickFillWidth -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetFillWidth(viewEvent.isChecked))
			}
			is DSWantedAccordionDemoViewEvent.OnClickDivider -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetDivider(viewEvent.isChecked))
			}
			is DSWantedAccordionDemoViewEvent.OnClickLeadingIcon -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetLeadingIcon(viewEvent.isChecked))
			}
			is DSWantedAccordionDemoViewEvent.OnClickContent -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetContent(viewEvent.isChecked))
			}
			is DSWantedAccordionDemoViewEvent.OnSelectVerticalPadding -> {
				viewModel.setEvent(DSWantedAccordionDemoEvent.SetVerticalPadding(viewEvent.verticalPadding))
			}
		}
	}

	if (viewState.isShowCode) {
		WantedModal(
			positive = "코드 복사",
			onClickPositive = {
				viewModel.setEvent(DSWantedAccordionDemoEvent.CopyCode)
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowCode(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowCode(false))
			},
			content = {
				Text(
					modifier = Modifier
						.padding(vertical = 20.dp)
						.verticalScroll(rememberScrollState()),
					text = viewState.code
				)
			}
		)
	}

	if (viewState.isShowAll) {
		WantedModal(
			positive = "확인",
			onClickPositive = {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowAll(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedAccordionDemoEvent.ShowAll(false))
			},
			content = {
				DSWantedAccordionDemoSampleAll(viewState)
			}
		)
	}
}

@Composable
private fun DSWantedAccordionDemoScreenContent(
	modifier: Modifier = Modifier,
	viewState: DSWantedAccordionDemoViewState,
	onViewEvent: (DSWantedAccordionDemoViewEvent) -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedAccordion") {
				onViewEvent(DSWantedAccordionDemoViewEvent.OnClickBack)
			}
		},
		bottomBar = {
			WantedActionArea(
				modifier = Modifier.navigationBarsPadding(),
				background = true,
				positive = {
					WantedButton(
						modifier = Modifier.fillMaxWidth(),
						text = "코드 보기",
						onClick = {
							onViewEvent(DSWantedAccordionDemoViewEvent.OnClickShowCode)
						}
					)
				},
				neutral = {
					WantedButton(
						modifier = Modifier.fillMaxWidth(),
						text = "모든 옵션 보기",
						variant = ButtonVariant.OUTLINED,
						onClick = {
							onViewEvent(DSWantedAccordionDemoViewEvent.OnClickShowAll)
						}
					)
				}
			)
		}
	) { innerPadding ->
		DSWantedAccordionDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			preview = {
				WantedAccordion(
					title = "제목",
					isExpanded = viewState.isExpanded,
					description = if (viewState.description) "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요." else null,
					fillWidth = viewState.fillWidth,
					divider = viewState.divider,
					verticalPadding = viewState.verticalPadding,
					leadingIcon = if (viewState.leadingIcon) {
						{
							Icon(
								modifier = Modifier.size(20.dp),
								painter = painterResource(R.drawable.icon_normal_arrow_right),
								contentDescription = "",
								tint = DesignSystemTheme.colors.labelNormal
							)
						}
					} else null,
					content = if (viewState.content) {
						{
							Text(
								text = "확장된 콘텐츠 영역입니다.",
								style = DesignSystemTheme.typography.body2Regular.copy(
									color = DesignSystemTheme.colors.labelNeutral
								)
							)
						}
					} else null,
					onChangeExpanded = { expanded ->
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickExpanded(expanded))
					}
				)
			},
			isExpanded = {
				DSWantedOptionSwitchCell(
					text = "isExpanded : ${viewState.isExpanded}",
					checkState = viewState.isExpanded,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickExpanded(it))
					}
				)
			},
			description = {
				DSWantedOptionSwitchCell(
					text = "description : ${viewState.description}",
					checkState = viewState.description,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickDescription(it))
					}
				)
			},
			fillWidth = {
				DSWantedOptionSwitchCell(
					text = "fillWidth : ${viewState.fillWidth}",
					checkState = viewState.fillWidth,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickFillWidth(it))
					}
				)
			},
			divider = {
				DSWantedOptionSwitchCell(
					text = "divider : ${viewState.divider}",
					checkState = viewState.divider,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickDivider(it))
					}
				)
			},
			leadingIcon = {
				DSWantedOptionSwitchCell(
					text = "leadingIcon : ${viewState.leadingIcon}",
					checkState = viewState.leadingIcon,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickLeadingIcon(it))
					}
				)
			},
			content = {
				DSWantedOptionSwitchCell(
					text = "content : ${viewState.content}",
					checkState = viewState.content,
					onCheckChanged = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnClickContent(it))
					}
				)
			},
			verticalPadding = {
				WantedSelect(
					value = "verticalPadding : ${viewState.verticalPadding.name}",
					selectedValue = viewState.verticalPadding.name,
					selectValueList = VerticalPadding.entries.map { it.name },
					onSelect = {
						onViewEvent(DSWantedAccordionDemoViewEvent.OnSelectVerticalPadding(VerticalPadding.valueOf(it)))
					}
				)
			}
		)
	}
}

@Composable
private fun DSWantedAccordionDemoScreenLayout(
	modifier: Modifier = Modifier,
	preview: @Composable () -> Unit,
	isExpanded: @Composable () -> Unit,
	description: @Composable () -> Unit,
	fillWidth: @Composable () -> Unit,
	divider: @Composable () -> Unit,
	leadingIcon: @Composable () -> Unit,
	content: @Composable () -> Unit,
	verticalPadding: @Composable () -> Unit,
) {
	Column(
		modifier = modifier.padding(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Text(
			text = "Preview",
			style = WantedTextStyle(
				colorRes = com.wanted.android.designsystem.R.color.label_strong,
				style = DesignSystemTheme.typography.heading2Bold
			)
		)

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = colorResource(R.color.line_normal_normal),
					shape = RoundedCornerShape(8.dp)
				)
				.padding(20.dp),
			contentAlignment = Alignment.Center
		) {
			preview()
		}

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
					colorRes = com.wanted.android.designsystem.R.color.label_strong,
					style = DesignSystemTheme.typography.heading2Bold
				)
			)

			isExpanded()
			description()
			fillWidth()
			divider()
			leadingIcon()
			content()
			verticalPadding()
		}
	}
}

@Composable
private fun DSWantedAccordionDemoSampleAll(
	viewState: DSWantedAccordionDemoViewState
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(rememberScrollState())
			.padding(vertical = 20.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		VerticalPadding.entries.forEach { padding ->
			listOf(true, false).forEach { expanded ->
				listOf(true, false).forEach { hasFillWidth ->
					WantedAccordion(
						title = "제목",
						isExpanded = expanded,
						description = "설명 텍스트",
						fillWidth = hasFillWidth,
						divider = viewState.divider,
						verticalPadding = padding,
						content = {
							Text(
								text = "확장된 콘텐츠 영역입니다.",
								style = DesignSystemTheme.typography.body2Regular.copy(
									color = DesignSystemTheme.colors.labelNeutral
								)
							)
						},
						onChangeExpanded = {}
					)
				}
			}
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedAccordionDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedAccordionDemoScreenContent(
			viewState = DSWantedAccordionDemoViewState(),
			onViewEvent = {}
		)
	}
}
