/**
* WantedTimePicker
*
* 시계 및 입력 형식을 지원하는 시간 선택 Dialog입니다.
*
* 사용자는 Dial(시계)·Input(키보드 입력) 형식으로 전환할 수 있으며, 전환은 호출측이 onClickChangeMode 에서 isEnableClock 값을 뒤집어야 반영됩니다.
*
* 확인/취소 버튼을 제공하지만 선택한 시간은 콜백으로 전달되지 않습니다(내부 상태, 초기값은 현재 시각). 선택 값이 필요하면 WantedTimePickerWheel 을 사용합니다.
*
* 내부적으로 TimePicker 또는 TimeInput을 사용합니다.
*
* 사용 예시:
* ```kotlin
* var showPicker by remember { mutableStateOf(true) }
* var isClock by remember { mutableStateOf(true) }
*
* if (showPicker) {
*     WantedTimePicker(
*         title = "시간 선택",
*         confirm = "확인",
*         cancel = "취소",
*         onClickConfirm = { showPicker = false },
*         onClickCancel = { showPicker = false },
*         onDismissRequest = { showPicker = false },
*         onClickChangeMode = { isClock = !isClock },
*         isEnableClock = isClock
*     )
* }
* ```
*
* @param title String: Dialog 상단 제목 텍스트입니다
* @param confirm String: 확인 버튼 텍스트입니다
* @param onClickConfirm () -> Unit: 확인 버튼 클릭 시 호출되는 콜백입니다. 선택한 시간은 전달하지 않으며 Dialog 를 닫지 않습니다.
* @param onClickChangeMode () -> Unit: 입력 형식 전환 버튼 클릭 시 호출되는 콜백입니다. 호출측이 isEnableClock 값을 뒤집어야 Dial·Input 전환이 반영됩니다.
* @param onDismissRequest () -> Unit: Dialog 외부 클릭 또는 닫기 시 호출되는 콜백입니다
* @param cancel String?: 취소 버튼에 표시할 텍스트입니다. null일 경우 버튼이 표시되지 않습니다
* @param isEnableClock Boolean: true이면 시계 기반 TimePicker를 사용하고, false이면 TimeInput을 사용합니다
* @param onClickCancel () -> Unit: 취소 버튼 클릭 시 호출되는 콜백입니다. Dialog 를 닫지 않으므로 호출부에서 표시 상태를 바꿔야 합니다. 기본값은 빈 함수입니다
*/

/**
* WantedTimePickerWheel
*
* 시간(AM/PM, 시, 분)을 Wheel 형태로 선택할 수 있는 Dialog입니다.
*
* 사용자는 오전/오후(AM/PM), 시(hour), 분(minute)을 각각 Wheel을 통해 선택할 수 있습니다.
* WantedNumberPicker 및 WantedStringPicker를 활용하여 선택 UI를 제공합니다.
*
* 확인 버튼을 누르면 onSelected 로 선택 값이 전달된 뒤 onDismissRequest 가 이어서 호출되고, 취소 또는 외부 클릭 시에는 onDismissRequest 만 호출됩니다.
* 선택값이 default 의 허용 범위를 벗어나면 확인 버튼이 비활성화됩니다.
*
* 제약: default.enableAm=false(PM 만 허용)이면 휠에 PM 항목 하나만 노출되는데, 내부가 index==0 을 AM 으로 해석하므로 onSelected 의 isAm 이 true 로 전달됩니다. PM 만 허용할 때는 이 값을 신뢰하지 말고 호출측에서 PM 으로 취급해야 합니다.
*
* 사용 예시:
* ```kotlin
* WantedTimePickerWheel(
*     isAm = true,
*     hour = 9,
*     minute = 30,
*     confirm = "확인",
*     cancel = "취소",
*     onSelected = { isAm, hour, minute ->
*         // 시간 선택 처리
*     },
*     onDismissRequest = { /* Dialog 닫기 처리 */ }
* )
* ```
*
* @param isAm Boolean: 초기 오전/오후 여부입니다. true이면 AM, false이면 PM입니다
* @param hour Int: 초기 선택된 시(hour)입니다. 12시간제 1..12 값이며, 24시간제 값(예: 14)을 넣으면 목록에 없어 목록의 첫 항목(기본 1시, isHideDisableTime 이면 enableMinHour)이 선택됩니다
* @param minute Int: 초기 선택된 분(minute)입니다
* @param confirm String: 확인 버튼에 표시할 텍스트입니다
* @param cancel String: 취소 버튼에 표시할 텍스트입니다
* @param modifier Modifier: 외형을 설정하는 Modifier입니다
* @param title String: Dialog 상단에 표시될 제목입니다. 기본값은 빈 문자열입니다
* @param default WantedTimePickerWheelDefault: 시간 선택 휠에 대한 기본 설정 값입니다
* @param onSelected (Boolean, Int, Int) -> Unit: 시간 선택 완료 시 호출되는 콜백입니다. 파라미터는 (isAm, hour, minute) 순입니다
* @param onDismissRequest () -> Unit: Dialog 닫기 요청 시 호출되는 콜백입니다
*/

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

/**
* object WantedTimePickerWheelDefaults
*
* 시간 선택 Wheel의 기본 설정을 제공하는 객체입니다.
*
* 시간과 분의 선택 가능 범위, AM/PM 허용 여부, 비활성 시간 숨김 옵션 등을 포함하며,
* 유효하지 않은 값은 내부적으로 자동 보정됩니다.
*/

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
*     enableMaxHour = 12,
*     enableMinMinute = 0,
*     enableMaxMinute = 45,
*     isHideDisableTime = true
* )
* ```
*
* @param enableAm Boolean: 오전(AM) 선택을 허용할지 여부입니다. 기본값은 true입니다
* @param enablePm Boolean: 오후(PM) 선택을 허용할지 여부입니다. 기본값은 true입니다. 단, AM이 false인 경우 자동으로 true로 설정됩니다
* @param enableMinHour Int: 선택 가능한 최소 시입니다. 1 이상이어야 하며, 기본값은 1입니다
* @param enableMaxHour Int: 선택 가능한 최대 시입니다. 12 이하로 지정해야 하며(12 초과 값은 보정되지 않습니다), [enableMinHour]보다 작으면 [enableMinHour] 로 보정됩니다. 기본값은 12입니다
* @param enableMinMinute Int: 선택 가능한 최소 분입니다. 0 이상이어야 하며, 기본값은 0입니다
* @param enableMaxMinute Int: 선택 가능한 최대 분입니다. 59 이하이며, `enableMinMinute`보다 작으면 자동 보정됩니다. 기본값은 59입니다
* @param isHideDisableTime Boolean: 비활성 시간대를 숨길지 여부입니다. 기본값은 false입니다
* @return WantedTimePickerWheelDefault: 설정된 시간 선택 휠 기본값 객체입니다
*/