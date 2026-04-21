package com.wanted.android.montage.sample.input.datepickerwheel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoEvent
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoViewEvent
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreenContract.DSWantedDatePickerWheelDemoViewState
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.picker.datepicker.WantedDatePickerWheel
import com.wanted.android.wanted.design.input.picker.datepicker.WantedDatePickerWheelDefaults
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedDatePickerWheelDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedDatePickerWheelDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    DSWantedDatePickerWheelDemoScreenContent(
        modifier = modifier,
        viewState = viewState,
        onViewEvent = { viewEvent ->
            when (viewEvent) {
                DSWantedDatePickerWheelDemoViewEvent.OnClickBack -> onClickBack()
                DSWantedDatePickerWheelDemoViewEvent.OnClickShowDatePickerWheel -> {
                    viewModel.setEvent(DSWantedDatePickerWheelDemoEvent.ShowDatePickerWheel)
                }
                DSWantedDatePickerWheelDemoViewEvent.OnDismissDatePickerWheel -> {
                    viewModel.setEvent(DSWantedDatePickerWheelDemoEvent.DismissDatePickerWheel)
                }
                is DSWantedDatePickerWheelDemoViewEvent.OnSelectDate -> {
                    viewModel.setEvent(
                        DSWantedDatePickerWheelDemoEvent.SelectDate(viewEvent.year, viewEvent.month)
                    )
                }
                DSWantedDatePickerWheelDemoViewEvent.OnClickToggleHideDisableDate -> {
                    viewModel.setEvent(DSWantedDatePickerWheelDemoEvent.ToggleHideDisableDate)
                }
            }
        }
    )
}

@Composable
private fun DSWantedDatePickerWheelDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedDatePickerWheelDemoViewState,
    onViewEvent: (DSWantedDatePickerWheelDemoViewEvent) -> Unit
) {
    if (viewState.isShowDatePickerWheel) {
        WantedDatePickerWheel(
            title = "날짜 선택",
            confirm = "확인",
            cancel = "취소",
            selectedYear = viewState.selectedYear,
            selectedMonth = viewState.selectedMonth,
            default = WantedDatePickerWheelDefaults.getDefault(
                isHideDisableDate = viewState.isHideDisableDate
            ),
            onSelect = { year, month ->
                onViewEvent(DSWantedDatePickerWheelDemoViewEvent.OnSelectDate(year, month))
            },
            onDismissRequest = {
                onViewEvent(DSWantedDatePickerWheelDemoViewEvent.OnDismissDatePickerWheel)
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedDatePickerWheel") {
                onViewEvent(DSWantedDatePickerWheelDemoViewEvent.OnClickBack)
            }
        }
    ) { innerPadding ->
        DSWantedDatePickerWheelDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            viewState = viewState,
            onViewEvent = onViewEvent
        )
    }
}

@Composable
private fun DSWantedDatePickerWheelDemoScreenLayout(
    modifier: Modifier = Modifier,
    viewState: DSWantedDatePickerWheelDemoViewState,
    onViewEvent: (DSWantedDatePickerWheelDemoViewEvent) -> Unit
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

        DSWantedPreviewContainer {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "선택된 날짜: ${viewState.selectedYear}년 ${viewState.selectedMonth}월",
                    style = WantedTextStyle(
                        colorRes = R.color.label_normal,
                        style = DesignSystemTheme.typography.body1Regular
                    )
                )

                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "DatePickerWheel 보기",
                    variant = ButtonVariant.SOLID,
                    onClick = { onViewEvent(DSWantedDatePickerWheelDemoViewEvent.OnClickShowDatePickerWheel) }
                )
            }
        }

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
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

            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = "비활성 날짜 숨기기",
                variant = if (viewState.isHideDisableDate) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
                onClick = { onViewEvent(DSWantedDatePickerWheelDemoViewEvent.OnClickToggleHideDisableDate) }
            )
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedDatePickerWheelDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedDatePickerWheelDemoScreenContent(
            viewState = DSWantedDatePickerWheelDemoViewState(),
            onViewEvent = {}
        )
    }
}
