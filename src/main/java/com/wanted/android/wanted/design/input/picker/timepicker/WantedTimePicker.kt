package com.wanted.android.wanted.design.input.picker.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import java.util.Calendar

/**
 * 시계 및 입력 형식을 지원하는 시간 선택 다이얼로그입니다.
 *
 * 사용자는 시계 기반 또는 직접 입력 기반의 시간 선택 UI를 전환하여 사용할 수 있으며,
 * 선택 후 확인/취소 버튼으로 결과를 확정하거나 다이얼로그를 닫을 수 있습니다.
 * 내부적으로 `TimePicker` 또는 `TimeInput`을 사용하며, 선택 모드 전환 버튼을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTimePicker(
 *     title = "시간 선택",
 *     confirm = "확인",
 *     cancel = "취소",
 *     onClickConfirm = { /* 확인 처리 */ },
 *     onClickCancel = { /* 취소 처리 */ },
 *     onDismissRequest = { /* 다이얼로그 종료 처리 */ },
 *     onClickChangeMode = { /* 입력 형식 전환 처리 */ },
 *     isEnableClock = true
 * )
 * ```
 *
 * @param title String: 다이얼로그 상단 제목 텍스트입니다.
 * @param confirm String: 확인 버튼 텍스트입니다.
 * @param onClickConfirm () -> Unit: 확인 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClickChangeMode () -> Unit: 입력 형식 전환 버튼 클릭 시 호출되는 콜백입니다.
 * @param onDismissRequest () -> Unit: 다이얼로그 외부 클릭 또는 닫기 시 호출되는 콜백입니다.
 * @param cancel String?: 취소 버튼에 표시할 텍스트입니다. null일 경우 버튼이 표시되지 않습니다.
 * @param isEnableClock Boolean: true이면 시계 기반 TimePicker를 사용하고, false이면 TimeInput을 사용합니다.
 * @param onClickCancel () -> Unit: 취소 버튼 클릭 시 호출되는 콜백입니다. 기본값은 빈 함수입니다.
 */
@Composable
fun WantedTimePicker(
    title: String,
    confirm: String,
    onClickConfirm: () -> Unit,
    onClickChangeMode: () -> Unit,
    onDismissRequest: () -> Unit,
    cancel: String? = null,
    isEnableClock: Boolean = true,
    onClickCancel: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        WantedTimePickerContent(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(colorResource(R.color.background_elevated_normal)),
            isEnableClock = isEnableClock,
            title = title,
            confirm = confirm,
            cancel = cancel,
            onClickConfirm = onClickConfirm,
            onClickCancel = onClickCancel,
            onClickChangeMode = onClickChangeMode
        )
    }
}


@Composable
private fun WantedTimePickerContent(
    title: String,
    confirm: String,
    onClickConfirm: () -> Unit,
    onClickChangeMode: () -> Unit,
    modifier: Modifier = Modifier,
    isEnableClock: Boolean = true,
    cancel: String? = null,
    onClickCancel: () -> Unit = {},
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    TimePickerLayout(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = colorResource(R.color.label_alternative)
            )
        },
        content = {
            if (isEnableClock) {
                TimePicker(
                    state = timePickerState,
                    colors = getWantedTimePickerDefaults()
                )
            } else {
                TimeInput(
                    state = timePickerState,
                    colors = getWantedTimePickerDefaults(
                        timeSelectorSelectedContainerColor = colorResource(R.color.transparent),
                        timeSelectorSelectedContentColor = colorResource(R.color.label_normal)
                    )
                )
            }
        },
        modeChange = {
            TimepickerModeChangeButton(
                modifier = Modifier,
                isEnableClock = isEnableClock,
                onClickChangeMode = onClickChangeMode
            )
        },
        cancel = {
            cancel?.let {
                TimepickerButton(
                    text = cancel,
                    onClick = onClickCancel
                )
            }
        },
        confirm = {
            TimepickerButton(
                text = confirm,
                onClick = onClickConfirm
            )
        }
    )
}


@Composable
private fun TimepickerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        content = {
            Text(
                modifier = Modifier,
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = colorResource(R.color.primary_normal)
            )
        },
        onClick = {
            onClick()
        }
    )
}

@Composable
private fun TimepickerModeChangeButton(
    isEnableClock: Boolean,
    modifier: Modifier = Modifier,
    onClickChangeMode: () -> Unit = {},
) {
    WantedTouchArea(
        modifier = modifier,
        verticalPadding = 4.dp,
        horizontalPadding = 4.dp,
        shape = CircleShape,
        content = {
            if (isEnableClock) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    imageVector = Icons.Outlined.Keyboard,
                    tint = colorResource(R.color.label_alternative),
                    contentDescription = ""
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    imageVector = Icons.Outlined.AccessTime,
                    tint = colorResource(R.color.label_alternative),
                    contentDescription = ""
                )
            }
        },
        onClick = {
            onClickChangeMode()
        }
    )
}

@Composable
private fun TimePickerLayout(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modeChange: @Composable () -> Unit,
    cancel: @Composable () -> Unit,
    confirm: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .padding(top = 24.dp, bottom = 20.dp)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        title()

        content()

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            modeChange()

            Spacer(Modifier.weight(1f))

            cancel()

            confirm()
        }
    }
}

@Composable
private fun getWantedTimePickerDefaults(
    timeSelectorSelectedContainerColor: Color = colorResource(R.color.primary_normal),
    timeSelectorSelectedContentColor: Color = colorResource(R.color.static_white),
) = TimePickerDefaults.colors(
    // 시계 동그란 배경
    clockDialColor = colorResource(R.color.fill_alternative),
    // 시계 선택된 숫자 색상
    clockDialSelectedContentColor = colorResource(R.color.static_white),
    // 시계 선택 되지 않은 숫자 색상
    clockDialUnselectedContentColor = colorResource(R.color.label_normal),
    // 시계 초침
    selectorColor = colorResource(R.color.primary_normal),
    // 시간 선택기의 배경 색상.
    containerColor = colorResource(R.color.background_elevated_normal),
    // 오전 / 오후 barder 색상
    periodSelectorBorderColor = colorResource(R.color.line_normal_normal),
    // 오전 / 오후 select 배경
    periodSelectorSelectedContainerColor = colorResource(R.color.fill_normal),
    //오전 / 오후 unSelect 배경
    periodSelectorUnselectedContainerColor = colorResource(R.color.background_elevated_normal),
    // 오전 / 오후 select text
    periodSelectorSelectedContentColor = colorResource(R.color.label_normal),
    // 오전 / 오후 unselect text
    periodSelectorUnselectedContentColor = colorResource(R.color.label_alternative),
    // 시간 선택기의 select 배경
    timeSelectorSelectedContainerColor = timeSelectorSelectedContainerColor,
    // 시간 선택기의 unSelect 배경
    timeSelectorUnselectedContainerColor = colorResource(R.color.fill_normal),
    // 시간 선택기의 select text
    timeSelectorSelectedContentColor = timeSelectorSelectedContentColor,
    // 시간 선택기의 select text
    timeSelectorUnselectedContentColor = colorResource(R.color.label_normal)
)

@DevicePreviews
@Composable
private fun WantedTimePickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(colorResource(R.color.background_elevated_normal)),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedTimePickerContent(
                    isEnableClock = true,
                    title = "시간선택",
                    cancel = "취소",
                    confirm = "확인",
                    onClickConfirm = {
                    },
                    onClickCancel = {
                    },
                    onClickChangeMode = {
                    }
                )

                WantedTimePickerContent(
                    isEnableClock = false,
                    title = "시간선택",
                    cancel = "취소",
                    confirm = "확인",
                    onClickConfirm = {
                    },
                    onClickCancel = {
                    },
                    onClickChangeMode = {
                    }
                )
            }
        }
    }
}