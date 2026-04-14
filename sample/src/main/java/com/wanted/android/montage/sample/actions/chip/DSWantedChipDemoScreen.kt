package com.wanted.android.montage.sample.actions.chip

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
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoEvent
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoSideEffect
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoViewEvent
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreenContract.DSWantedChipDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedChipDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedChipDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val clipboardManager = LocalClipboardManager.current
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
		when (sideEffect) {
			is DSWantedChipDemoSideEffect.CopyCode -> {
				clipboardManager.setText(AnnotatedString(sideEffect.code))
			}
		}
	}

	DSWantedChipDemoScreenImpl(
		modifier = modifier,
		viewState = viewState
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedChipDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedChipDemoViewEvent.OnClickShowCode -> {
				viewModel.setEvent(DSWantedChipDemoEvent.ShowCode(true))
			}
			is DSWantedChipDemoViewEvent.OnSelectVariant -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetVariant(viewEvent.variant))
			}
			is DSWantedChipDemoViewEvent.OnSelectSize -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetSize(viewEvent.size))
			}
			is DSWantedChipDemoViewEvent.OnChangeActive -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetActive(viewEvent.isActive))
			}
			is DSWantedChipDemoViewEvent.OnChangeEnable -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetEnable(viewEvent.isEnable))
			}
			is DSWantedChipDemoViewEvent.OnChangeLeftIcon -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetLeftIcon(viewEvent.hasLeftIcon))
			}
			is DSWantedChipDemoViewEvent.OnChangeRightIcon -> {
				viewModel.setEvent(DSWantedChipDemoEvent.SetRightIcon(viewEvent.hasRightIcon))
			}
			is DSWantedChipDemoViewEvent.OnClickCopyCode -> {
				viewModel.setEvent(DSWantedChipDemoEvent.CopyCode)
			}
		}
	}

	if (viewState.isShowCode) {
		WantedModal(
			positive = "코드 복사",
			onClickPositive = {
				viewModel.setEvent(DSWantedChipDemoEvent.CopyCode)
				viewModel.setEvent(DSWantedChipDemoEvent.ShowCode(false))
			},
			onDismissRequest = {
				viewModel.setEvent(DSWantedChipDemoEvent.ShowCode(false))
			},
			content = {
				Text(text = viewState.code)
			}
		)
	}
}

@Composable
private fun DSWantedChipDemoScreenImpl(
	modifier: Modifier = Modifier,
	viewState: DSWantedChipDemoViewState,
	onViewEvent: (DSWantedChipDemoViewEvent) -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedChip") {
				onViewEvent(DSWantedChipDemoViewEvent.OnClickBack)
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
							onViewEvent(DSWantedChipDemoViewEvent.OnClickShowCode)
						}
					)
				}
			)
		}
	) { innerPadding ->
		DSWantedChipDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			preview = {
				WantedChip(
					text = "텍스트",
					variant = viewState.selectedVariant,
					size = viewState.selectedSize,
					isActive = viewState.isActive,
					isEnable = viewState.isEnable,
					leftIcon = if (viewState.hasLeftIcon) {
						R.drawable.icon_normal_bookmark
					} else null,
					rightIcon = if (viewState.hasRightIcon) {
						R.drawable.icon_normal_bookmark
					} else null,
					onClick = {
						onViewEvent(DSWantedChipDemoViewEvent.OnClickCopyCode)
					}
				)
			},
			variant = {
				WantedSelect(
					value = "Variant : ${viewState.selectedVariant.name}",
					selectedValue = viewState.selectedVariant.name,
					selectValueList = viewState.variantList.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedChipDemoViewEvent.OnSelectVariant(ChipVariant.valueOf(it))
						)
					},
				)
			},
			size = {
				WantedSelect(
					value = "Size : ${viewState.selectedSize.name}",
					selectedValue = viewState.selectedSize.name,
					selectValueList = viewState.sizeList.map { it.name },
					onSelect = {
						onViewEvent(
							DSWantedChipDemoViewEvent.OnSelectSize(ChipSize.valueOf(it))
						)
					},
				)
			},
			isActive = {
				DSWantedOptionSwitchCell(
					text = "isActive : ${viewState.isActive}",
					checkState = viewState.isActive,
					onCheckChanged = {
						onViewEvent(DSWantedChipDemoViewEvent.OnChangeActive(it))
					}
				)
			},
			isEnable = {
				DSWantedOptionSwitchCell(
					text = "isEnable : ${viewState.isEnable}",
					checkState = viewState.isEnable,
					onCheckChanged = {
						onViewEvent(DSWantedChipDemoViewEvent.OnChangeEnable(it))
					}
				)
			},
			leftIcon = {
				DSWantedOptionSwitchCell(
					text = "leftIcon : ${if (viewState.hasLeftIcon) "icon" else null}",
					checkState = viewState.hasLeftIcon,
					onCheckChanged = {
						onViewEvent(DSWantedChipDemoViewEvent.OnChangeLeftIcon(it))
					}
				)
			},
			rightIcon = {
				DSWantedOptionSwitchCell(
					text = "rightIcon : ${if (viewState.hasRightIcon) "icon" else null}",
					checkState = viewState.hasRightIcon,
					onCheckChanged = {
						onViewEvent(DSWantedChipDemoViewEvent.OnChangeRightIcon(it))
					}
				)
			}
		)
	}
}

@Composable
private fun DSWantedChipDemoScreenLayout(
	modifier: Modifier = Modifier,
	preview: @Composable () -> Unit,
	variant: @Composable () -> Unit,
	size: @Composable () -> Unit,
	isActive: @Composable () -> Unit,
	isEnable: @Composable () -> Unit,
	leftIcon: @Composable () -> Unit,
	rightIcon: @Composable () -> Unit
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

			variant()

			size()

			isActive()

			isEnable()

			leftIcon()

			rightIcon()
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedChipDemoScreenPreview() {
	DesignSystemTheme {
		val viewState = DSWantedChipDemoViewState()
		DSWantedChipDemoScreenImpl(
			viewState = viewState,
			onViewEvent = { }
		)
	}
}
