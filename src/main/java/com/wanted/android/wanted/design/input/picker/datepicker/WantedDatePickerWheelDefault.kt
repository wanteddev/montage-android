package com.wanted.android.wanted.design.input.picker.datepicker

import androidx.compose.runtime.Composable


data class WantedDatePickerWheelDefault(
    val startYear: Int = 1900,
    val endYear: Int = 2100,
    val enableMinYear: Int,
    val enableMaxYear: Int,
    val enableMinMonth: Int,
    val enableMaxMonth: Int,
    val isHideDisableDate: Boolean,
)

object WantedDatePickerWheelDefaults {
    @Composable
    fun getDefault(
        startYear: Int = 1900,
        endYear: Int = 2100,
        enableMinYear: Int = 1900,
        enableMaxYear: Int = 2100,
        enableMinMonth: Int = 1,
        enableMaxMonth: Int = 12,
        isHideDisableDate: Boolean = false,
    ) = WantedDatePickerWheelDefault(
        startYear = startYear,
        endYear = if (endYear < startYear) startYear else endYear,
        enableMinYear = enableMinYear,
        enableMaxYear = if (enableMaxYear < enableMinYear) enableMinYear else enableMaxYear,
        enableMinMonth = enableMinMonth,
        enableMaxMonth = if (enableMaxMonth < enableMinMonth) enableMinMonth else enableMaxMonth,
        isHideDisableDate = isHideDisableDate
    )
}