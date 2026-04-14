package com.wanted.android.montage.sample.content.playbadge

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
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoEvent
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoSideEffect
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoViewEvent
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreenContract.DSWantedPlayBadgeDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.playtime.playbadge.WantedPlayBadge
import com.wanted.android.wanted.design.contents.playtime.playbadge.WantedPlayBadgeDefaults.Size
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPlayBadgeDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedPlayBadgeDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val clipboardManager = LocalClipboardManager.current
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
		when (sideEffect) {
			is DSWantedPlayBadgeDemoSideEffect.CopyCode -> {
				clipboardManager.setText(AnnotatedString(sideEffect.code))
			}
		}
	}

	DSWantedPlayBadgeDemoScreenImpl(
		modifier = modifier,
		viewState = viewState
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedPlayBadgeDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedPlayBadgeDemoViewEvent.OnClickShowCode -> {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.ShowCode(true))
			}
			is DSWantedPlayBadgeDemoViewEvent.OnSelectSize -> {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.SetSize(viewEvent.size))
			}
			is DSWantedPlayBadgeDemoViewEvent.OnChangeIsAlternative -> {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.SetIsAlternative(viewEvent.isAlternative))
			}
			is DSWantedPlayBadgeDemoViewEvent.OnClickCopyCode -> {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.CopyCode)
			}
		}
	}

	if (viewState.isShowCode) {
		WantedModal(
			positive = "코드 복사",
			onClickPositive = {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.CopyCode)
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.ShowCode(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedPlayBadgeDemoEvent.ShowCode(false))
			},
			content = {
				Text(text = viewState.code)
			}
		)
	}
}

@Composable
private fun DSWantedPlayBadgeDemoScreenImpl(
	modifier: Modifier = Modifier,
	viewState: DSWantedPlayBadgeDemoViewState,
	onViewEvent: (DSWantedPlayBadgeDemoViewEvent) -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedPlayBadge") {
				onViewEvent(DSWantedPlayBadgeDemoViewEvent.OnClickBack)
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
							onViewEvent(DSWantedPlayBadgeDemoViewEvent.OnClickShowCode)
						}
					)
				}
			)
		}
	) { innerPadding ->
		DSWantedPlayBadgeDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			preview = {
				WantedPlayBadge(
					size = viewState.selectedSize,
					isAlternative = viewState.isAlternative,
					modifier = Modifier
				)
			},
			size = {
				WantedSelect(
					value = "Size : ${viewState.selectedSize.name}",
					selectedValue = viewState.selectedSize.name,
					selectValueList = viewState.sizeList.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedPlayBadgeDemoViewEvent.OnSelectSize(Size.valueOf(it))
						)
					},
				)
			},
			isAlternative = {
				DSWantedOptionSwitchCell(
					text = "isAlternative : ${viewState.isAlternative}",
					checkState = viewState.isAlternative,
					onCheckChanged = {
						onViewEvent(DSWantedPlayBadgeDemoViewEvent.OnChangeIsAlternative(it))
					}
				)
			}
		)
	}
}

@Composable
private fun DSWantedPlayBadgeDemoScreenLayout(
	modifier: Modifier = Modifier,
	preview: @Composable () -> Unit,
	size: @Composable () -> Unit,
	isAlternative: @Composable () -> Unit,
) {
	Column(
		modifier = modifier.padding(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Text(
			text = "Preview",
			style = WantedTextStyle(
				colorRes = R.color.label_strong,
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
					colorRes = R.color.label_strong,
					style = DesignSystemTheme.typography.heading2Bold
				)
			)

			size()

			isAlternative()
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedPlayBadgeDemoScreenPreview() {
	DesignSystemTheme {
		val viewState = DSWantedPlayBadgeDemoViewState()
		DSWantedPlayBadgeDemoScreenImpl(
			viewState = viewState,
			onViewEvent = { }
		)
	}
}
