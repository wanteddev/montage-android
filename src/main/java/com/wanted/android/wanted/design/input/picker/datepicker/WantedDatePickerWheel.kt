package com.wanted.android.wanted.design.input.picker.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.input.picker.WantedNumberPicker
import com.wanted.android.wanted.design.presentation.modal.WantedModal
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
fun WantedDatePickerWheel(
    title: String,
    confirm: String,
    cancel: String,
    selectedYear: Int,
    selectedMonth: Int,
    onSelect: (year: Int, month: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    default: WantedDatePickerWheelDefault = WantedDatePickerWheelDefaults.getDefault(),
) {

    var selectYear by remember(selectedYear) { mutableIntStateOf(selectedYear) }
    var selectMonth by remember(selectedYear) { mutableIntStateOf(selectedYear) }
    var enabledYear by remember {
        mutableStateOf(
            default.enableMinYear <= selectYear && selectYear <= default.enableMaxYear
        )
    }
    var enabledMonth by remember {
        mutableStateOf(
            default.enableMinMonth <= selectMonth && selectMonth <= default.enableMaxMonth
        )
    }

    WantedModal(
        modifier = modifier.padding(horizontal = 20.dp),
        modalSize = WantedModalContract.ModalSize.Medium,
        shape = RoundedCornerShape(28.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        topBar = {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = WantedModalContract.ModalSize.Medium.contentPadding)
                    .padding(horizontal = WantedModalContract.ModalSize.Medium.contentPadding),
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.label_alternative)
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WantedNumberPicker(
                        modifier = Modifier.weight(1f),
                        start = if (default.isHideDisableDate) default.enableMinYear else default.startYear,
                        end = if (default.isHideDisableDate) default.enableMaxYear else default.endYear,
                        step = 1,
                        selectedValue = selectedYear,
                        enableMinValue = default.enableMinYear,
                        enableMaxValue = default.enableMaxYear,
                        onSelect = { index, value, enable ->
                            selectYear = value
                            enabledYear = enable
                        }
                    )

                    WantedNumberPicker(
                        modifier = Modifier.weight(1f),
                        start = if (default.isHideDisableDate) default.enableMinMonth else 1,
                        end = if (default.isHideDisableDate) default.enableMaxMonth else 12,
                        step = 1,
                        enableMinValue = default.enableMinMonth,
                        enableMaxValue = default.enableMaxMonth,
                        selectedValue = selectedMonth,
                        onSelect = { index, value, enable ->
                            selectMonth = value
                            enabledMonth = enable
                        }
                    )
                }

                Box(
                    Modifier
                        .background(
                            color = colorResource(R.color.fill_normal),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 4.dp)
                        .height(
                            with(LocalDensity.current) {
                                DesignSystemTheme.typography.heading1Medium.lineHeight.toDp()
                            }
                        )
                        .fillMaxWidth()

                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WantedButton(
                    modifier = Modifier.wrapContentSize(),
                    text = cancel,
                    buttonDefault = WantedButtonDefaults.getDefault(
                        shape = ButtonShape.OUTLINED,
                        size = ButtonSize.MEDIUM,
                        borderColor = Color.Transparent
                    ),
                    onClick = onDismissRequest
                )

                WantedButton(
                    modifier = Modifier.wrapContentSize(),
                    text = confirm,
                    buttonDefault = WantedButtonDefaults.getDefault(
                        shape = ButtonShape.OUTLINED,
                        size = ButtonSize.MEDIUM,
                        borderColor = Color.Transparent,
                        enabled = enabledYear && enabledMonth,
                    ),
                    onClick = {
                        onSelect(selectYear, selectMonth)
                        onDismissRequest()
                    }
                )
            }
        },
        onDismissRequest = onDismissRequest
    )
}

@DevicePreviews
@Composable
private fun WantedDatePickerWheelPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedDatePickerWheel(
                    modifier = Modifier,
                    title = "Data",
                    confirm = "확인",
                    cancel = "취소",
                    selectedYear = 2023,
                    selectedMonth = 1,
                    onDismissRequest = {},
                    onSelect = { _, _ -> }
                )
            }
        }
    }
}