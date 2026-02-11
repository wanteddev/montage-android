package com.wanted.android.wanted.design.input.picker.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedDatePicker
 *
 * 날짜를 선택할 수 있는 DatePicker 다이얼로그를 제공합니다.
 *
 * 사용자는 날짜를 선택하고 확인 또는 취소할 수 있으며, 선택된 날짜는 콜백을 통해 전달됩니다.
 * 내부적으로 Material 3의 `DatePickerDialog` 및 `DatePicker` 컴포넌트를 사용하여 구성됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedDatePicker(
 *     confirm = "확인",
 *     cancel = "취소",
 *     onDateSelected = { selectedDate ->
 *         // 선택된 날짜 처리
 *     },
 *     onDismiss = {
 *         // 다이얼로그 닫기 처리
 *     }
 * )
 * ```
 *
 * @param confirm String: 확인 버튼에 표시할 텍스트입니다.
 * @param cancel String: 취소 버튼에 표시할 텍스트입니다.
 * @param datePickerState DatePickerState: 선택된 날짜 및 UI 상태를 관리하는 상태 객체입니다. 기본값은 `rememberDatePickerState()`입니다.
 * @param dateFormatter DatePickerFormatter :날짜 포맷터로, 선택된 날짜의 포맷을 지정합니다. 기본값은 `DatePickerDefaults.dateFormatter()`입니다.
 * @param onDateSelected (Long?) -> Unit: 날짜 선택 완료 시 호출되는 콜백입니다. 선택된 날짜의 Unix 타임스탬프(Long) 또는 null이 전달됩니다.
 * @param onDismiss () -> Unit: 다이얼로그가 닫힐 때 호출되는 콜백입니다.
 */
@Composable
fun WantedDatePicker(
    confirm: String,
    cancel: String,
    datePickerState: DatePickerState = rememberDatePickerState(),
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    onDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = getWantedDatePickerDefaults(),
        confirmButton = {
            TextButton(
                shape = RoundedCornerShape(10.dp),
                content = {
                    Text(
                        modifier = Modifier,
                        text = confirm,
                        style = MaterialTheme.typography.labelLarge,
                        color = DesignSystemTheme.colors.primaryNormal
                    )
                },
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            )
        },
        dismissButton = {
            TextButton(
                shape = RoundedCornerShape(10.dp),
                content = {
                    Text(
                        modifier = Modifier,
                        text = cancel,
                        style = MaterialTheme.typography.labelLarge,
                        color = DesignSystemTheme.colors.primaryNormal
                    )
                },
                onClick = {
                    onDismiss()
                }
            )
        }
    ) {
        DatePicker(
            modifier = Modifier,
            state = datePickerState,
            dateFormatter = dateFormatter,
            colors = getWantedDatePickerDefaults()
        )
    }
}

@Composable
private fun getWantedDatePickerDefaults() = DatePickerDefaults.colors(
    // 날짜 선택기 배경에 사용되는 색상
    containerColor = DesignSystemTheme.colors.backgroundNormalNormal,
    // 날짜 선택기 제목에 사용되는 색상 (날짜 선택)
    titleContentColor = DesignSystemTheme.colors.labelAlternative,
    // 날짜 선택기 헤드라인에 사용되는 색상 (선택한 날짜)
    headlineContentColor = DesignSystemTheme.colors.labelNormal,
    // DateRangePicker에 월이 표시될 때 나타나는 월 및 연도 하위 제목 레이블에 사용되는 색상.
    subheadContentColor = DesignSystemTheme.colors.labelNormal,
    // 요일 글자에 사용되는 색상
    weekdayContentColor = DesignSystemTheme.colors.labelNormal,
    // DatePicker에 표시될 때 연도 선택 메뉴 버튼과 월 화살표 탐색에 사용되는 콘텐츠 색상.
    navigationContentColor = DesignSystemTheme.colors.labelNormal,
    // 연도 항목 텍스트 색상
    yearContentColor = DesignSystemTheme.colors.labelNormal,
    // 연도 항목 텍스트 비활성화 색상
    disabledYearContentColor = DesignSystemTheme.colors.labelDisable,
    // 연도를 선택할 때 현재 연도 텍스트 색상
    currentYearContentColor = DesignSystemTheme.colors.labelNormal,
    // 연도를 선택할 때 선택된 연도 텍스트
    selectedYearContentColor = DesignSystemTheme.colors.staticWhite,
    // 연도를 선택할 때 선택된 연도 비활성화 텍스트
    disabledSelectedYearContentColor = DesignSystemTheme.colors.labelDisable,
    // 선택된 연도 항목 컨테이너에 사용된 색상
    selectedYearContainerColor = DesignSystemTheme.colors.primaryNormal,
    // 달력 일 텍스트 색상
    dayContentColor = DesignSystemTheme.colors.labelNormal,
    // 달력 일 텍스트 비활성화 색상
    disabledDayContentColor = DesignSystemTheme.colors.labelDisable,
    // 달력 일 선택한 텍스트 색상
    selectedDayContentColor = DesignSystemTheme.colors.staticWhite,
    // 달력 일 선택한 텍스트 비활성화 색상
    disabledSelectedDayContentColor = DesignSystemTheme.colors.staticWhite,
    // 달력 일 선택한 배경 색상
    selectedDayContainerColor = DesignSystemTheme.colors.primaryNormal,
    // 달력 일 선택한 배경 비활성화 색상
    disabledSelectedDayContainerColor = DesignSystemTheme.colors.labelDisable,
    // 현재 날짜를 표시하는 요일 텍스트 색상
    todayContentColor = DesignSystemTheme.colors.primaryNormal,
    // 현재 날짜를 표시하는 요일 테두리 색상
    todayDateBorderColor = DesignSystemTheme.colors.primaryNormal,
    // 날짜 범위 선택 범위 내에 있는 요일에 사용되는 텍스트 색상
    dayInSelectionRangeContentColor = DesignSystemTheme.colors.staticWhite,
    // 날짜 범위 선택 범위 내에 있는 요일에 사용되는 배경 색상
    dayInSelectionRangeContainerColor = DesignSystemTheme.colors.primaryNormal,
    // 날짜 선택기에서 사용되는 구분선에 사용되는 색상
    dividerColor = DesignSystemTheme.colors.lineNormalNormal,
    dateTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = DesignSystemTheme.colors.backgroundNormalNormal,
        unfocusedContainerColor = DesignSystemTheme.colors.backgroundNormalNormal,
        errorContainerColor = DesignSystemTheme.colors.interactionDisable,
        focusedIndicatorColor = DesignSystemTheme.colors.primaryNormal,
        focusedLabelColor = DesignSystemTheme.colors.labelAlternative,
        unfocusedLabelColor = DesignSystemTheme.colors.labelAlternative,
    )
)

@DevicePreviews
@Composable
private fun WantedDatePickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                DatePicker(
                    state = rememberDatePickerState()
                )

                DatePicker(
                    state = rememberDatePickerState(
                        initialDisplayMode = DisplayMode.Input
                    )
                )

            }
        }
    }
}