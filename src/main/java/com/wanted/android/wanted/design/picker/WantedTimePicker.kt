package com.wanted.android.wanted.design.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import java.util.Calendar


@Composable
fun WantedTimePicker(
    modifier: Modifier = Modifier,
    isEnableClock: Boolean = true
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    if (isEnableClock) {
        TimePicker(
            modifier = modifier,
            state = timePickerState,
            colors = TimePickerDefaults.colors()
        )
    } else {
        TimeInput(
            modifier = modifier,
            state = timePickerState,
            colors = TimePickerDefaults.colors()
        )
    }
}

@DevicePreviews
@Composable
private fun WantedTimePickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTimePicker(
                    modifier = Modifier
                )
            }
        }
    }
}