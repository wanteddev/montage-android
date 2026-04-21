package com.wanted.android.montage.sample.content.sectionheader

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
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoEvent
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoSideEffect
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoViewEvent
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreenContract.DSWantedSectionHeaderDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.sectionheader.WantedSectionHeader
import com.wanted.android.wanted.design.contents.sectionheader.WantedSectionHeaderDefaults.Size
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedSectionHeaderDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedSectionHeaderDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val clipboardManager = LocalClipboardManager.current
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
		when (sideEffect) {
			is DSWantedSectionHeaderDemoSideEffect.CopyCode -> {
				clipboardManager.setText(AnnotatedString(sideEffect.code))
			}
		}
	}

	DSWantedSectionHeaderDemoScreenContent(
		modifier = modifier,
		viewState = viewState
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedSectionHeaderDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedSectionHeaderDemoViewEvent.OnClickShowCode -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowCode(true))
			}
			is DSWantedSectionHeaderDemoViewEvent.OnClickCopyCode -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.CopyCode)
			}
			is DSWantedSectionHeaderDemoViewEvent.OnClickShowAll -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowAll(true))
			}
			is DSWantedSectionHeaderDemoViewEvent.OnSelectSize -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.SetSize(viewEvent.size))
			}
			is DSWantedSectionHeaderDemoViewEvent.OnClickHeadingContents -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.SetHeadingContents(viewEvent.isChecked))
			}
			is DSWantedSectionHeaderDemoViewEvent.OnClickTrailingContent -> {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.SetTrailingContent(viewEvent.isChecked))
			}
		}
	}

	if (viewState.isShowCode) {
		WantedModal(
			positive = "코드 복사",
			onClickPositive = {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.CopyCode)
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowCode(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowCode(false))
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
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowAll(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedSectionHeaderDemoEvent.ShowAll(false))
			},
			content = {
				DSWantedSectionHeaderDemoSampleAll()
			}
		)
	}
}

@Composable
private fun DSWantedSectionHeaderDemoScreenContent(
	modifier: Modifier = Modifier,
	viewState: DSWantedSectionHeaderDemoViewState,
	onViewEvent: (DSWantedSectionHeaderDemoViewEvent) -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedSectionHeader") {
				onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnClickBack)
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
							onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnClickShowCode)
						}
					)
				},
				neutral = {
					WantedButton(
						modifier = Modifier.fillMaxWidth(),
						text = "모든 옵션 보기",
						variant = ButtonVariant.OUTLINED,
						onClick = {
							onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnClickShowAll)
						}
					)
				}
			)
		}
	) { innerPadding ->
		DSWantedSectionHeaderDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			preview = {
				WantedSectionHeader(
					title = "제목",
					modifier = Modifier.fillMaxWidth(),
					size = viewState.size,
					headingContents = if (viewState.headingContents) {
						{
							Icon(
								modifier = Modifier.size(20.dp),
								painter = painterResource(R.drawable.icon_normal_arrow_right),
								contentDescription = "",
								tint = DesignSystemTheme.colors.labelNormal
							)
						}
					} else null,
					trailingContent = if (viewState.trailingContent) {
						{
							Icon(
								modifier = Modifier.size(20.dp),
								painter = painterResource(R.drawable.icon_normal_arrow_right),
								contentDescription = "",
								tint = DesignSystemTheme.colors.labelNormal
							)
						}
					} else null
				)
			},
			size = {
				WantedSelect(
					value = "size : ${viewState.size.name}",
					selectedValue = viewState.size.name,
					selectValueList = Size.entries.map { it.name },
					onSelect = {
						onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnSelectSize(Size.valueOf(it)))
					}
				)
			},
			headingContents = {
				DSWantedOptionSwitchCell(
					text = "headingContents : ${viewState.headingContents}",
					checkState = viewState.headingContents,
					onCheckChanged = {
						onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnClickHeadingContents(it))
					}
				)
			},
			trailingContent = {
				DSWantedOptionSwitchCell(
					text = "trailingContent : ${viewState.trailingContent}",
					checkState = viewState.trailingContent,
					onCheckChanged = {
						onViewEvent(DSWantedSectionHeaderDemoViewEvent.OnClickTrailingContent(it))
					}
				)
			}
		)
	}
}

@Composable
private fun DSWantedSectionHeaderDemoScreenLayout(
	modifier: Modifier = Modifier,
	preview: @Composable () -> Unit,
	size: @Composable () -> Unit,
	headingContents: @Composable () -> Unit,
	trailingContent: @Composable () -> Unit,
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

		DSWantedPreviewContainer {
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

			size()
			headingContents()
			trailingContent()
		}
	}
}

@Composable
private fun DSWantedSectionHeaderDemoSampleAll() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(rememberScrollState())
			.padding(vertical = 20.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		Size.entries.forEach { size ->
			WantedSectionHeader(
				title = "제목 (${ size.name })",
				modifier = Modifier.fillMaxWidth(),
				size = size,
				headingContents = {
					Icon(
						painter = painterResource(com.wanted.android.designsystem.R.drawable.icon_normal_arrow_right),
						contentDescription = "",
						tint = DesignSystemTheme.colors.labelNormal
					)
				},
				trailingContent = {
					Icon(
						painter = painterResource(com.wanted.android.designsystem.R.drawable.icon_normal_arrow_right),
						contentDescription = "",
						tint = DesignSystemTheme.colors.labelNormal
					)
				}
			)
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedSectionHeaderDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedSectionHeaderDemoScreenContent(
			viewState = DSWantedSectionHeaderDemoViewState(),
			onViewEvent = {}
		)
	}
}
