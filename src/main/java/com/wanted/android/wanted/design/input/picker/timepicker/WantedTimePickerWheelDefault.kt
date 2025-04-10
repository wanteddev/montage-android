package com.wanted.android.wanted.design.input.picker.timepicker

import androidx.compose.runtime.Composable


data class WantedTimePickerWheelDefault(
    val enableMinHour: Int,
    val enableMaxHour: Int,
    val enableMinMinute: Int,
    val enableMaxMinute: Int,
    val enableAm: Boolean,
    val enablePm: Boolean,
    val isHideDisableTime: Boolean,
)

object WantedTimePickerWheelDefaults {
    @Composable
    fun getDefault(
        enableAm: Boolean = true,
        enablePm: Boolean = true,
        enableMinHour: Int = 1,
        enableMaxHour: Int = 12,
        enableMinMinute: Int = 0,
        enableMaxMinute: Int = 59,
        isHideDisableTime: Boolean = false,
    ) = WantedTimePickerWheelDefault(
        enableAm = enableAm,
        enablePm = if (enableAm) enablePm else true,
        enableMinHour = if (enableMinHour > 0) enableMinHour else 1,
        enableMaxHour = when {
            enableMinHour > 12 -> 12
            enableMaxHour < enableMinHour -> enableMinHour
            else -> enableMaxHour
        },
        enableMinMinute = if (enableMinMinute >= 0) enableMinMinute else 0,
        enableMaxMinute = when {
            enableMaxMinute > 59 -> 59
            enableMaxMinute < enableMinMinute -> enableMinMinute
            else -> enableMaxMinute
        },
        isHideDisableTime = isHideDisableTime,
    )
}