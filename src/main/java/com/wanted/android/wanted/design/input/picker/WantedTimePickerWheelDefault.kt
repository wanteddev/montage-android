package com.wanted.android.wanted.design.input.picker

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
        enableMinHour = enableMinHour,
        enableMaxHour = enableMaxHour,
        enableMinMinute = enableMinMinute,
        enableMaxMinute = enableMaxMinute,
        isHideDisableTime = isHideDisableTime,
    )
}