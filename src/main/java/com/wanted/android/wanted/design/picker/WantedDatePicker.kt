package com.wanted.android.wanted.design.picker

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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedDatePicker(
    confirm: String,
    cancel: String,
    datePickerState: DatePickerState = rememberDatePickerState(),
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {


    DatePickerDialog(
        onDismissRequest = onDismiss,
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
        WantedDatePickerContent(state = datePickerState)
    }
}

@Composable
private fun WantedDatePickerContent(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
) {
    DatePicker(
        modifier = modifier,
        state = state,
        dateFormatter = dateFormatter,
        colors = DatePickerDefaults.colors(
            // 날짜 선택기 배경에 사용되는 색상
            containerColor = colorResource(R.color.background_normal_normal),
            // 날짜 선택기 제목에 사용되는 색상 (날짜 선택)
            titleContentColor = colorResource(R.color.label_alternative),
            // 날짜 선택기 헤드라인에 사용되는 색상 (선택한 날짜)
            headlineContentColor = colorResource(R.color.label_normal),
            // 요일 글자에 사용되는 색상
            weekdayContentColor = colorResource(R.color.label_normal),
            // DatePicker에 표시될 때 연도 선택 메뉴 버튼과 월 화살표 탐색에 사용되는 콘텐츠 색상.
            navigationContentColor = colorResource(R.color.label_normal),
            // 연도 항목 콘텐츠에 사용된 색상
            yearContentColor = colorResource(R.color.label_normal),
            // 연도를 선택할 때 현재 연도 콘텐츠에 사용된 색상
            currentYearContentColor = colorResource(R.color.static_white)

        )
    )
}

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