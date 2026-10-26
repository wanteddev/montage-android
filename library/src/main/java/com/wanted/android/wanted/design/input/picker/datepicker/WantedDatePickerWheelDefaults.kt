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
 * @param enableMinMonth Int: 선택 가능한 최소 월입니다. 모든 연도에 공통으로 적용됩니다.
 * @param enableMaxMonth Int: 선택 가능한 최대 월입니다. 모든 연도에 공통으로 적용됩니다.
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
     * @param enableMaxYear Int: 선택 가능한 최대 연도입니다. 기본값은 2100입니다. `enableMinYear`보다 작으면 자동으로 `enableMinYear`로 보정됩니다. isHideDisableDate 가 false 이면 endYear 이하로 지정해야 합니다 — 휠 목록(startYear..endYear)을 넘으면 첫 해만 선택할 수 있게 됩니다(예: endYear 만 2030 으로 줄이고 이 값을 기본값으로 두는 경우).
     * @param enableMinMonth Int: 선택 가능한 최소 월입니다. 연도와 무관하게 모든 연도에 공통으로 적용됩니다. 기본값은 1월입니다.
     * @param enableMaxMonth Int: 선택 가능한 최대 월입니다. 모든 연도에 공통으로 적용됩니다. [enableMinMonth]보다 작으면 자동으로 [enableMinMonth]로 보정됩니다. 1..12 범위로 지정해야 합니다. isHideDisableDate 가 false 이면 12 를 넘길 때 1월만 선택할 수 있게 되고, true 이면 존재하지 않는 13월 이상이 휠에 표시됩니다.
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