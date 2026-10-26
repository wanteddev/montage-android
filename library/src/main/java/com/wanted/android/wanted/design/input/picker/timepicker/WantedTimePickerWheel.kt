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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.input.picker.numberpicker.WantedNumberPicker
import com.wanted.android.wanted.design.input.picker.numberpicker.WantedStringPicker
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedTimePickerWheel
 *
 * 시간(AM/PM, 시, 분)을 Wheel 형태로 선택할 수 있는 Dialog입니다.
 *
 * 사용자는 오전/오후(AM/PM), 시(hour), 분(minute)을 각각 Wheel을 통해 선택할 수 있습니다.
 * WantedNumberPicker 및 WantedStringPicker를 활용하여 선택 UI를 제공합니다.
 *
 * 확인 버튼을 누르면 onSelected 로 선택 값이 전달된 뒤 onDismissRequest 가 이어서 호출되고, 취소 또는 외부 클릭 시에는 onDismissRequest 만 호출됩니다.
 * 선택값이 default 의 허용 범위를 벗어나면 확인 버튼이 비활성화됩니다.
 *
 * 제약: default.enableAm=false(PM 만 허용)이면 휠에 PM 항목 하나만 노출되는데, 내부가 index==0 을 AM 으로 해석하므로 onSelected 의 isAm 이 true 로 전달됩니다. PM 만 허용할 때는 이 값을 신뢰하지 말고 호출측에서 PM 으로 취급해야 합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTimePickerWheel(
 *     isAm = true,
 *     hour = 9,
 *     minute = 30,
 *     confirm = "확인",
 *     cancel = "취소",
 *     onSelected = { isAm, hour, minute ->
 *         // 시간 선택 처리
 *     },
 *     onDismissRequest = { /* Dialog 닫기 처리 */ }
 * )
 * ```
 *
 * @param isAm Boolean: 초기 오전/오후 여부입니다. true이면 AM, false이면 PM입니다
 * @param hour Int: 초기 선택된 시(hour)입니다. 12시간제 1..12 값이며, 24시간제 값(예: 14)을 넣으면 목록에 없어 목록의 첫 항목(기본 1시, isHideDisableTime 이면 enableMinHour)이 선택됩니다
 * @param minute Int: 초기 선택된 분(minute)입니다
 * @param confirm String: 확인 버튼에 표시할 텍스트입니다
 * @param cancel String: 취소 버튼에 표시할 텍스트입니다
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다
 * @param title String: Dialog 상단에 표시될 제목입니다. 기본값은 빈 문자열입니다
 * @param default WantedTimePickerWheelDefault: 시간 선택 휠에 대한 기본 설정 값입니다
 * @param onSelected (Boolean, Int, Int) -> Unit: 시간 선택 완료 시 호출되는 콜백입니다. 파라미터는 (isAm, hour, minute) 순입니다
 * @param onDismissRequest () -> Unit: Dialog 닫기 요청 시 호출되는 콜백입니다
 */
@Composable
fun WantedTimePickerWheel(
    isAm: Boolean,
    hour: Int,
    minute: Int,
    confirm: String,
    cancel: String,
    modifier: Modifier = Modifier,
    title: String = "",
    default: WantedTimePickerWheelDefault = WantedTimePickerWheelDefaults.getDefault(),
    onSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit = { _, _, _ -> },
    onDismissRequest: () -> Unit = {}
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

    WantedPopup(
        modifier = modifier.padding(horizontal = 20.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        topBar = {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = WantedModalContract.ModalSize.Large.contentPadding)
                    .padding(horizontal = WantedModalContract.ModalSize.Large.contentPadding),
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = DesignSystemTheme.colors.foregroundNeutralTertiary
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
                        variant = ButtonVariant.OUTLINED,
                        size = ButtonSize.MEDIUM,
                        borderColor = Color.Transparent
                    ),
                    onClick = onDismissRequest
                )

                WantedButton(
                    modifier = Modifier.wrapContentSize(),
                    text = confirm,
                    buttonDefault = WantedButtonDefaults.getDefault(
                        variant = ButtonVariant.OUTLINED,
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
                    color = DesignSystemTheme.colors.surfaceNeutralSecondary,
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