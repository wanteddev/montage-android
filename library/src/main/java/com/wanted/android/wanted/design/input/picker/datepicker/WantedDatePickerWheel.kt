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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.input.picker.numberpicker.WantedNumberPicker
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedDatePickerWheel
 *
 * 연도 및 월을 선택할 수 있는 휠 형태의 날짜 선택 모달입니다.
 *
 * 사용자는 연도와 월을 각각 NumberPicker를 통해 선택할 수 있으며, 확인/취소 버튼을 통해 선택 값을 확정하거나 취소할 수 있습니다.
 * 선택된 연도와 월은 콜백을 통해 반환되며, 기본 설정값은 `WantedDatePickerWheelDefaults.getDefault()`를 통해 조절 가능합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDatePickerWheel(
 *     title = "날짜 선택",
 *     confirm = "확인",
 *     cancel = "취소",
 *     selectedYear = 2023,
 *     selectedMonth = 1,
 *     onSelect = { year, month ->
 *         // 선택된 연도 및 월 처리
 *     },
 *     onDismissRequest = {
 *         // 모달 닫힘 처리
 *     }
 * )
 * ```
 *
 * @param title String: 다이얼로그 상단에 표시될 제목 텍스트입니다.
 * @param confirm String: 확인 버튼 텍스트입니다.
 * @param cancel String: 취소 버튼 텍스트입니다.
 * @param selectedYear Int: 초기 선택된 연도입니다.
 * @param selectedMonth Int: 초기 선택된 월입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param default WantedDatePickerWheelDefault: 날짜 휠 설정을 지정하는 기본값 객체입니다.
 * @param onSelect (Int, Int) -> Unit: 날짜 선택 후 확인 버튼을 눌렀을 때 호출되는 콜백입니다.
 * @param onDismissRequest () -> Unit: 다이얼로그가 닫힐 때 호출되는 콜백입니다.
 */
@Composable
fun WantedDatePickerWheel(
    title: String,
    confirm: String,
    cancel: String,
    selectedYear: Int,
    selectedMonth: Int,
    modifier: Modifier = Modifier,
    default: WantedDatePickerWheelDefault = WantedDatePickerWheelDefaults.getDefault(),
    onSelect: (year: Int, month: Int) -> Unit = { _, _ -> },
    onDismissRequest: () -> Unit = {}
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
                color = DesignSystemTheme.colors.labelAlternative
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
                        enabled = enabledYear && enabledMonth,
                    ),
                    onClick = {
                        onSelect(selectYear, selectMonth)
                        onDismissRequest()
                    }
                )
            }
        },
        onDismissRequest = onDismissRequest,
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
                            color = DesignSystemTheme.colors.fillNormal,
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