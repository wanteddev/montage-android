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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
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
 * @param dateFormatter DatePickerFormatter: 날짜 포맷터로, 선택된 날짜의 포맷을 지정합니다. 기본값은 `DatePickerDefaults.dateFormatter()`입니다.
 * @param onDateSelected Function1<Long?, Unit>: 날짜 선택 완료 시 호출되는 콜백입니다. 선택된 날짜의 Unix 타임스탬프(Long) 또는 null이 전달됩니다.
 * @param onDismiss Function0<Unit>: 다이얼로그가 닫힐 때 호출되는 콜백입니다.
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
                        color = colorResource(R.color.primary_normal)
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
                        color = colorResource(R.color.primary_normal)
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
    containerColor = colorResource(R.color.background_normal_normal),
    // 날짜 선택기 제목에 사용되는 색상 (날짜 선택)
    titleContentColor = colorResource(R.color.label_alternative),
    // 날짜 선택기 헤드라인에 사용되는 색상 (선택한 날짜)
    headlineContentColor = colorResource(R.color.label_normal),
    // DateRangePicker에 월이 표시될 때 나타나는 월 및 연도 하위 제목 레이블에 사용되는 색상.
    subheadContentColor = colorResource(R.color.label_normal),
    // 요일 글자에 사용되는 색상
    weekdayContentColor = colorResource(R.color.label_normal),
    // DatePicker에 표시될 때 연도 선택 메뉴 버튼과 월 화살표 탐색에 사용되는 콘텐츠 색상.
    navigationContentColor = colorResource(R.color.label_normal),
    // 연도 항목 텍스트 색상
    yearContentColor = colorResource(R.color.label_normal),
    // 연도 항목 텍스트 비활성화 색상
    disabledYearContentColor = colorResource(R.color.label_disable),
    // 연도를 선택할 때 현재 연도 텍스트 색상
    currentYearContentColor = colorResource(R.color.label_normal),
    // 연도를 선택할 때 선택된 연도 텍스트
    selectedYearContentColor = colorResource(R.color.static_white),
    // 연도를 선택할 때 선택된 연도 비활성화 텍스트
    disabledSelectedYearContentColor = colorResource(R.color.label_disable),
    // 선택된 연도 항목 컨테이너에 사용된 색상
    selectedYearContainerColor = colorResource(R.color.primary_normal),
    // 달력 일 텍스트 색상
    dayContentColor = colorResource(R.color.label_normal),
    // 달력 일 텍스트 비활성화 색상
    disabledDayContentColor = colorResource(R.color.label_disable),
    // 달력 일 선택한 텍스트 색상
    selectedDayContentColor = colorResource(R.color.static_white),
    // 달력 일 선택한 텍스트 비활성화 색상
    disabledSelectedDayContentColor = colorResource(R.color.static_white),
    // 달력 일 선택한 배경 색상
    selectedDayContainerColor = colorResource(R.color.primary_normal),
    // 달력 일 선택한 배경 비활성화 색상
    disabledSelectedDayContainerColor = colorResource(R.color.label_disable),
    // 현재 날짜를 표시하는 요일 텍스트 색상
    todayContentColor = colorResource(R.color.primary_normal),
    // 현재 날짜를 표시하는 요일 테두리 색상
    todayDateBorderColor = colorResource(R.color.primary_normal),
    // 날짜 범위 선택 범위 내에 있는 요일에 사용되는 텍스트 색상
    dayInSelectionRangeContentColor = colorResource(R.color.static_white),
    // 날짜 범위 선택 범위 내에 있는 요일에 사용되는 배경 색상
    dayInSelectionRangeContainerColor = colorResource(R.color.primary_normal),
    // 날짜 선택기에서 사용되는 구분선에 사용되는 색상
    dividerColor = colorResource(R.color.line_normal_normal),
    dateTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = colorResource(R.color.background_normal_normal),
        unfocusedContainerColor = colorResource(R.color.background_normal_normal),
        errorContainerColor = colorResource(R.color.interaction_disable),
        focusedIndicatorColor = colorResource(R.color.primary_normal),
        focusedLabelColor = colorResource(R.color.label_alternative),
        unfocusedLabelColor = colorResource(R.color.label_alternative),
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