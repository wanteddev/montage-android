package com.wanted.android.montage.sample.input.timepickerwheel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoEvent
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoViewEvent
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreenContract.DSWantedTimePickerWheelDemoViewState
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.picker.timepicker.WantedTimePickerWheel
import com.wanted.android.wanted.design.input.picker.timepicker.WantedTimePickerWheelDefaults
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedTimePickerWheelDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedTimePickerWheelDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	DSWantedTimePickerWheelDemoScreenImpl(
		modifier = modifier,
		viewState = viewState,
	) { viewEvent ->
		when (viewEvent) {
			is DSWantedTimePickerWheelDemoViewEvent.OnClickBack -> onClickBack()
			is DSWantedTimePickerWheelDemoViewEvent.OnClickOpenPicker -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.ShowPicker(true))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnDismissPicker -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.ShowPicker(false))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnChangeIsAm -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.SetIsAm(viewEvent.isAm))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnChangeEnableAm -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.SetEnableAm(viewEvent.enableAm))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnChangeEnablePm -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.SetEnablePm(viewEvent.enablePm))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnChangeIsHideDisableTime -> {
				viewModel.setEvent(DSWantedTimePickerWheelDemoEvent.SetIsHideDisableTime(viewEvent.isHide))
			}
			is DSWantedTimePickerWheelDemoViewEvent.OnTimeSelected -> {
				viewModel.setEvent(
					DSWantedTimePickerWheelDemoEvent.SetSelectedTime(
						isAm = viewEvent.isAm,
						hour = viewEvent.hour,
						minute = viewEvent.minute,
					)
				)
			}
		}
	}
}

@Composable
private fun DSWantedTimePickerWheelDemoScreenImpl(
	modifier: Modifier = Modifier,
	viewState: DSWantedTimePickerWheelDemoViewState,
	onViewEvent: (DSWantedTimePickerWheelDemoViewEvent) -> Unit
) {
	if (viewState.isShowPicker) {
		WantedTimePickerWheel(
			isAm = viewState.isAm,
			hour = viewState.hour,
			minute = viewState.minute,
			confirm = "확인",
			cancel = "취소",
			title = "시간 선택",
			default = WantedTimePickerWheelDefaults.getDefault(
				enableAm = viewState.enableAm,
				enablePm = viewState.enablePm,
				isHideDisableTime = viewState.isHideDisableTime,
			),
			onSelected = { isAm, hour, minute ->
				onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnTimeSelected(isAm, hour, minute))
			},
			onDismissRequest = {
				onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnDismissPicker)
			}
		)
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedTimePickerWheel") {
				onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnClickBack)
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 20.dp),
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
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Text(
						text = viewState.selectedTimeText,
						style = WantedTextStyle(
							colorRes = R.color.label_strong,
							style = DesignSystemTheme.typography.body1Bold
						)
					)
					WantedButton(
						text = "시간 선택",
						onClick = {
							onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnClickOpenPicker)
						}
					)
				}
			}

			Spacer(Modifier.size(10.dp))

			Column(
				modifier = Modifier
					.verticalScroll(rememberScrollState())
					.padding(vertical = 24.dp),
				verticalArrangement = Arrangement.spacedBy(10.dp),
			) {
				Text(
					text = "Option",
					style = WantedTextStyle(
						colorRes = R.color.label_strong,
						style = DesignSystemTheme.typography.heading2Bold
					)
				)

				DSWantedOptionSwitchCell(
					text = "isAm : ${viewState.isAm}",
					checkState = viewState.isAm,
					onCheckChanged = {
						onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnChangeIsAm(it))
					}
				)

				DSWantedOptionSwitchCell(
					text = "enableAm : ${viewState.enableAm}",
					checkState = viewState.enableAm,
					onCheckChanged = {
						onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnChangeEnableAm(it))
					}
				)

				DSWantedOptionSwitchCell(
					text = "enablePm : ${viewState.enablePm}",
					checkState = viewState.enablePm,
					onCheckChanged = {
						onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnChangeEnablePm(it))
					}
				)

				DSWantedOptionSwitchCell(
					text = "isHideDisableTime : ${viewState.isHideDisableTime}",
					checkState = viewState.isHideDisableTime,
					onCheckChanged = {
						onViewEvent(DSWantedTimePickerWheelDemoViewEvent.OnChangeIsHideDisableTime(it))
					}
				)
			}
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedTimePickerWheelDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedTimePickerWheelDemoScreenImpl(
			viewState = DSWantedTimePickerWheelDemoViewState(),
			onViewEvent = {}
		)
	}
}
