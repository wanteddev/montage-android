package com.wanted.android.montage.sample.content.listcell

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
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
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
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

			is DSWantedListCellDemoViewEvent.OnClickCopyCode -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.CopyCode)
			}

			is DSWantedListCellDemoViewEvent.OnSelectVerticalPadding -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.SetVerticalPadding(viewEvent.verticalPadding))
			}

			is DSWantedListCellDemoViewEvent.OnChangeFillWidth -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.SetFillWidth(viewEvent.fillWidth))
			}

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

			is DSWantedListCellDemoViewEvent.OnChangeShowCaption -> {
				viewModel.setEvent(DSWantedListCellDemoEvent.SetShowCaption(viewEvent.showCaption))
			}
		}
	}

	if (viewState.isShowCode) {
		WantedModal(
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

@Composable
private fun DSWantedListCellDemoScreenImpl(
	modifier: Modifier = Modifier,
	viewState: DSWantedListCellDemoViewState,
	onViewEvent: (DSWantedListCellDemoViewEvent) -> Unit
) {
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
				positive = {
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
					text = "텍스트",
					caption = if (viewState.showCaption) "캡션" else "",
					fillWidth = viewState.fillWidth,
					verticalPadding = viewState.selectedVerticalPadding,
					divider = viewState.divider,
					isEnable = viewState.isEnable,
					selected = viewState.selected,
					chevrons = viewState.chevrons,
					onClick = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnClickCopyCode)
					}
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
			fillWidth = {
				DSWantedOptionSwitchCell(
					text = "fillWidth : ${viewState.fillWidth}",
					checkState = viewState.fillWidth,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeFillWidth(it))
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
			showCaption = {
				DSWantedOptionSwitchCell(
					text = "caption : ${if (viewState.showCaption) "캡션" else "none"}",
					checkState = viewState.showCaption,
					onCheckChanged = {
						onViewEvent(DSWantedListCellDemoViewEvent.OnChangeShowCaption(it))
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
	verticalPadding: @Composable () -> Unit,
	fillWidth: @Composable () -> Unit,
	divider: @Composable () -> Unit,
	isEnable: @Composable () -> Unit,
	selected: @Composable () -> Unit,
	chevrons: @Composable () -> Unit,
	showCaption: @Composable () -> Unit,
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

			verticalPadding()
			fillWidth()
			divider()
			isEnable()
			selected()
			chevrons()
			showCaption()
		}
	}
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
