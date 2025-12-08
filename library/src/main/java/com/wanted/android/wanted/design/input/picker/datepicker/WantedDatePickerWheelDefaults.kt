package com.wanted.android.wanted.design.input.picker.datepicker

import androidx.compose.runtime.Composable

/**
 * data class WantedDatePickerWheelDefault
 *
 * 날짜 선택 휠(DatePickerWheel)의 기본 설정 값을 담는 데이터 클래스입니다.
 *
 * 연도와 월 범위, 사용 가능한 최소/최대 값, 비활성 날짜 표시 여부를 지정할 수 있습니다.
 *
 * @param startYear Int: 휠에 표시할 시작 연도입니다.
 * @param endYear Int: 휠에 표시할 종료 연도입니다.
 * @param enableMinYear Int: 선택 가능한 최소 연도입니다.
 * @param enableMaxYear Int: 선택 가능한 최대 연도입니다.
 * @param enableMinMonth Int: 선택 가능한 최소 월입니다.
 * @param enableMaxMonth Int: 선택 가능한 최대 월입니다.
 * @param isHideDisableDate Boolean: 비활성 날짜를 숨길지 여부입니다.
 */
data class WantedDatePickerWheelDefault(
    val startYear: Int = 1900,
    val endYear: Int = 2100,
    val enableMinYear: Int,
    val enableMaxYear: Int,
    val enableMinMonth: Int,
    val enableMaxMonth: Int,
    val isHideDisableDate: Boolean,
)


/**
 * object WantedDatePickerWheelDefaults
 *
 * 날짜 선택 휠의 기본 설정을 제공하는 객체입니다.
 *
 * 유효하지 않은 값은 자동으로 보정되며, `WantedDatePickerWheelDefault` 객체를 생성합니다.
 */
object WantedDatePickerWheelDefaults {
    /**
     * fun getDefault(...)
     *
     * 날짜 선택 휠 기본 설정 값을 반환합니다.
     *
     * 연도 및 월 범위 설정, 비활성 날짜 숨김 여부 등을 지정할 수 있으며, 유효하지 않은 값은 자동으로 보정됩니다.
     * 날짜 선택 휠에서 사용되는 기본 설정 객체인 `WantedDatePickerWheelDefault`를 생성합니다.
     *
     * 사용 예시:
     * ```kotlin
     * val default = WantedDatePickerWheelDefaults.getDefault(
     *     startYear = 2000,
     *     endYear = 2030,
     *     enableMinYear = 2010,
     *     enableMaxYear = 2025,
     *     enableMinMonth = 3,
     *     enableMaxMonth = 11,
     *     isHideDisableDate = true
     * )
     * ```
     *
     * @param startYear Int: 휠 선택기에서 표시할 시작 연도입니다. 기본값은 1900입니다.
     * @param endYear Int: 휠 선택기에서 표시할 종료 연도입니다. 기본값은 2100입니다. `startYear`보다 작으면 자동으로 `startYear`로 보정됩니다.
     * @param enableMinYear Int: 선택 가능한 최소 연도입니다. 기본값은 1900입니다.
     * @param enableMaxYear Int: 선택 가능한 최대 연도입니다. `enableMinYear`보다 작으면 자동으로 `enableMinYear`로 보정됩니다.
     * @param enableMinMonth Int: 선택 가능한 최소 월입니다. 기본값은 1월입니다.
     * @param enableMaxMonth Int: 선택 가능한 최대 월입니다. `enableMinMonth`보다 작으면 자동으로 `enableMinMonth`로 보정됩니다.
     * @param isHideDisableDate Boolean: 선택 불가능한 날짜를 숨길지 여부를 설정합니다. 기본값은 false입니다.
     * @return WantedDatePickerWheelDefault: 설정된 날짜 선택 휠 기본값 객체입니다.
     */
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