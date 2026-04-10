package com.wanted.android.montage.sample.input.datepicker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoEvent
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoViewEvent
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreenContract.DSWantedDatePickerDemoViewState
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.picker.datepicker.WantedDatePicker
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DSWantedDatePickerDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedDatePickerDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	DSWantedDatePickerDemoScreenContent(
		modifier = modifier,
		viewState = viewState,
		onViewEvent = { viewEvent ->
			when (viewEvent) {
				DSWantedDatePickerDemoViewEvent.OnClickBack -> onClickBack()
				DSWantedDatePickerDemoViewEvent.OnClickShowDatePicker -> {
					viewModel.setEvent(DSWantedDatePickerDemoEvent.ShowDatePicker)
				}
				DSWantedDatePickerDemoViewEvent.OnDismissDatePicker -> {
					viewModel.setEvent(DSWantedDatePickerDemoEvent.DismissDatePicker)
				}
				is DSWantedDatePickerDemoViewEvent.OnDateSelected -> {
					viewModel.onDateSelected(viewEvent.dateMillis)
				}
			}
		}
	)
}

@Composable
private fun DSWantedDatePickerDemoScreenContent(
	modifier: Modifier = Modifier,
	viewState: DSWantedDatePickerDemoViewState,
	onViewEvent: (DSWantedDatePickerDemoViewEvent) -> Unit
) {
	if (viewState.isShowDatePicker) {
		WantedDatePicker(
			confirm = "확인",
			cancel = "취소",
			onDateSelected = { dateMillis ->
				onViewEvent(DSWantedDatePickerDemoViewEvent.OnDateSelected(dateMillis))
			},
			onDismiss = { onViewEvent(DSWantedDatePickerDemoViewEvent.OnDismissDatePicker) }
		)
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedDatePicker") {
				onViewEvent(DSWantedDatePickerDemoViewEvent.OnClickBack)
			}
		}
	) { innerPadding ->
		DSWantedDatePickerDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			viewState = viewState,
			onViewEvent = onViewEvent
		)
	}
}

@Composable
private fun DSWantedDatePickerDemoScreenLayout(
	modifier: Modifier = Modifier,
	viewState: DSWantedDatePickerDemoViewState,
	onViewEvent: (DSWantedDatePickerDemoViewEvent) -> Unit
) {
	Column(
		modifier = modifier.padding(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp)
	) {
		Text(
			text = "Preview",
			style = WantedTextStyle(
				colorRes = R.color.label_strong,
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
			Column(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				WantedButton(
					modifier = Modifier.fillMaxWidth(),
					text = "DatePicker 보기",
					variant = ButtonVariant.SOLID,
					onClick = { onViewEvent(DSWantedDatePickerDemoViewEvent.OnClickShowDatePicker) }
				)

				viewState.selectedDateMillis?.let { millis ->
					val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
					Text(
						text = "선택된 날짜: ${dateFormat.format(Date(millis))}",
						style = WantedTextStyle(
							colorRes = R.color.label_normal,
							style = DesignSystemTheme.typography.body1Regular
						)
					)
				}
			}
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedDatePickerDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedDatePickerDemoScreenContent(
			viewState = DSWantedDatePickerDemoViewState(),
			onViewEvent = {}
		)
	}
}
