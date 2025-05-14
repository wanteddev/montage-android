package com.wanted.android.wanted.design.input.picker.timepicker

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
import com.wanted.android.wanted.design.input.picker.WantedStringPicker
import com.wanted.android.wanted.design.presentation.modal.WantedModal
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
fun WantedTimePickerWheel(
    title: String = "",
    isAm: Boolean,
    hour: Int,
    minute: Int,
    confirm: String,
    cancel: String,
    onSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    default: WantedTimePickerWheelDefault = WantedTimePickerWheelDefaults.getDefault()
) {
    var isSelectAm by remember(isAm) { mutableStateOf(isAm) }
    var enablePeriod by remember(default) {
        mutableStateOf(
            when {
                default.enableAm && default.enablePm -> true
                default.enableAm -> isAm
                default.enablePm -> !isAm
                else -> false
            }
        )
    }

    var selectHour by remember(hour) { mutableIntStateOf(hour) }
    var enableHour by remember(default) {
        mutableStateOf(
            default.enableMinHour <= selectHour && selectHour <= default.enableMaxHour
        )
    }

    var selectMinute by remember(minute) { mutableIntStateOf(minute) }
    var enableMinute by remember(default) {
        mutableStateOf(
            default.enableMinMinute <= selectMinute && selectMinute <= default.enableMaxMinute
        )
    }

    WantedModal(
        modifier = modifier.padding(horizontal = 20.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        shape = RoundedCornerShape(28.dp),
        topBar = {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = WantedModalContract.ModalSize.Large.contentPadding)
                    .padding(horizontal = WantedModalContract.ModalSize.Large.contentPadding),
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.label_alternative)
            )
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
                        enabled = enablePeriod && enableHour && enableMinute,
                    ),
                    onClick = {
                        onSelected(isSelectAm, selectHour, selectMinute)
                        onDismissRequest()
                    }
                )
            }
        },
        onDismissRequest = onDismissRequest,
        content = {
            TimePickerLayout(
                modifier = Modifier.fillMaxWidth(),
                period = {
                    WantedStringPicker(
                        modifier = Modifier.fillMaxWidth(),
                        itemList = when {
                            default.enableAm && default.enablePm -> listOf("AM", "PM")
                            default.enableAm -> listOf("AM")
                            default.enablePm -> listOf("PM")
                            else -> listOf()
                        },
                        selectedIndex = if (isSelectAm) 0 else 1,
                        onSelect = { index, enable ->
                            isSelectAm = index == 0
                            enablePeriod = enable
                        }
                    )
                },
                hour = {
                    WantedNumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        start = if (default.isHideDisableTime) default.enableMinHour else 1,
                        end = if (default.isHideDisableTime) default.enableMaxHour else 12,
                        step = 1,
                        selectedValue = selectHour,
                        enableMinValue = default.enableMinHour,
                        enableMaxValue = default.enableMaxHour,
                        onSelect = { index, value, enable ->
                            selectHour = value
                            enableHour = enable
                        }
                    )

                },
                minute = {
                    WantedNumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        start = if (default.isHideDisableTime) default.enableMinMinute else 0,
                        end = if (default.isHideDisableTime) default.enableMaxMinute else 59,
                        step = 1,
                        selectedValue = selectMinute,
                        enableMinValue = default.enableMinMinute,
                        enableMaxValue = default.enableMaxMinute,
                        onSelect = { index, value, enable ->
                            selectMinute = value
                            enableMinute = enable
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun TimePickerLayout(
    period: @Composable () -> Unit,
    hour: @Composable () -> Unit,
    minute: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) { period() }
            Box(modifier = Modifier.weight(1f)) { hour() }
            Box(modifier = Modifier.weight(1f)) { minute() }
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
}

@DevicePreviews
@Composable
private fun WantedTimePickerWheelPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTimePickerWheel(
                    isAm = true,
                    title = "시간",
                    hour = 12,
                    minute = 0,
                    confirm = "확인",
                    cancel = "취소",
                    onSelected = { _, _, _ -> },
                    onDismissRequest = {}
                )
            }
        }
    }
}