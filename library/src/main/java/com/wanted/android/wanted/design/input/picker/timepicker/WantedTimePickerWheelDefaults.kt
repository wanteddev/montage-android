package com.wanted.android.wanted.design.input.picker.timepicker

import androidx.compose.runtime.Composable

/**
 * data class WantedTimePickerWheelDefault
 *
 * 시간 선택 Wheel의 기본 설정 값을 담는 데이터 클래스입니다.
 *
 * 시(hour), 분(minute), AM/PM 설정과 비활성 시간 표시 여부를 지정할 수 있습니다.
 *
 * @param enableMinHour Int: 선택 가능한 최소 시입니다
 * @param enableMaxHour Int: 선택 가능한 최대 시입니다
 * @param enableMinMinute Int: 선택 가능한 최소 분입니다
 * @param enableMaxMinute Int: 선택 가능한 최대 분입니다
 * @param enableAm Boolean: 오전(AM) 선택 허용 여부입니다
 * @param enablePm Boolean: 오후(PM) 선택 허용 여부입니다
 * @param isHideDisableTime Boolean: 비활성 시간 숨김 여부입니다
 */
data class WantedTimePickerWheelDefault(
    val enableMinHour: Int,
    val enableMaxHour: Int,
    val enableMinMinute: Int,
    val enableMaxMinute: Int,
    val enableAm: Boolean,
    val enablePm: Boolean,
    val isHideDisableTime: Boolean,
)


/**
 * object WantedTimePickerWheelDefaults
 *
 * 시간 선택 Wheel의 기본 설정을 제공하는 객체입니다.
 *
 * 시간과 분의 선택 가능 범위, AM/PM 허용 여부, 비활성 시간 숨김 옵션 등을 포함하며,
 * 유효하지 않은 값은 내부적으로 자동 보정됩니다.
 */
object WantedTimePickerWheelDefaults {

    /**
     * fun getDefault(...)
     * 
     * 시간 선택 Wheel 기본 설정 값을 반환합니다.
     *
     * 시간 선택 시 사용할 수 있는 시, 분, AM/PM 범위를 설정할 수 있으며,
     * 비활성 시간 숨김 여부도 조정 가능합니다. 잘못된 값은 자동으로 보정됩니다.
     * 반환 값은 WantedTimePickerWheelDefault 객체입니다.
     *
     * 사용 예시:
     * ```kotlin
     * val timeDefault = WantedTimePickerWheelDefaults.getDefault(
     *     enableAm = true,
     *     enablePm = true,
     *     enableMinHour = 9,
     *     enableMaxHour = 18,
     *     enableMinMinute = 0,
     *     enableMaxMinute = 45,
     *     isHideDisableTime = true
     * )
     * ```
     *
     * @param enableAm Boolean: 오전(AM) 선택을 허용할지 여부입니다. 기본값은 true입니다
     * @param enablePm Boolean: 오후(PM) 선택을 허용할지 여부입니다. 기본값은 true입니다. 단, AM이 false인 경우 자동으로 true로 설정됩니다
     * @param enableMinHour Int: 선택 가능한 최소 시입니다. 1 이상이어야 하며, 기본값은 1입니다
     * @param enableMaxHour Int: 선택 가능한 최대 시입니다. 12 이하이어야 하며, `enableMinHour`보다 작으면 자동 보정됩니다. 기본값은 12입니다
     * @param enableMinMinute Int: 선택 가능한 최소 분입니다. 0 이상이어야 하며, 기본값은 0입니다
     * @param enableMaxMinute Int: 선택 가능한 최대 분입니다. 59 이하이며, `enableMinMinute`보다 작으면 자동 보정됩니다. 기본값은 59입니다
     * @param isHideDisableTime Boolean: 비활성 시간대를 숨길지 여부입니다. 기본값은 false입니다
     * @return WantedTimePickerWheelDefault: 설정된 시간 선택 휠 기본값 객체입니다
     */
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