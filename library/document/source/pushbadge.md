/**
* object PushBadgeTypes
* Push badge Type 클래스입니다.
*
* 이 클래스는 Push badge의 표시 유형, 크기, 위치를 정의하는 열거형 클래스들을 포함합니다.
*/

/**
* enum class PushBadgeVariant
*
* Push 배지의 표시 유형을 정의합니다.
*
* - Dot: 작은 점 형태의 배지를 표시합니다.
* - Number: 숫자 형태로 개수를 표시합니다.
* - New: "N" 텍스트를 통해 새로운 항목을 표시합니다.
*/

/**
* enum class PushBadgeSize
*
* Push 배지의 크기를 정의합니다.
*
* - XSmall: 가장 작은 크기로 텍스트가 작게 표시됩니다.
* - Small: 중간 크기의 배지입니다.
* - Medium: 가장 큰 배지로 강조 표시 시 적합합니다.
*/

/**
* enum class PushBadgePosition
*
* Push 배지를 배치할 위치를 정의하는 열거형 클래스입니다.
*
* - TopStart: 상단의 좌측 위치입니다.
* - TopCenter: 상단의 중앙 위치입니다.
* - TopEnd: 상단의 우측 위치입니다.
* - MiddleStart: 중앙의 좌측 위치입니다.
* - MiddleCenter: 중앙의 중앙 위치입니다.
* - MiddleEnd: 중앙의 우측 위치입니다.
* - BottomStart: 하단의 좌측 위치입니다.
* - BottomCenter: 하단의 중앙 위치입니다.
* - BottomEnd: 하단의 우측 위치입니다.
*/

/**
* WantedPushBadge
*
* 아이콘이나 UI 요소에 붙여 표시되는 Push badge 컴포넌트입니다.
*
* Dot, Number, New 타입 중 하나를 선택할 수 있으며, 위치, 사이즈, 색상 등을 설정할 수 있습니다.
* 새로운 알림, 수량 표시, 강조 용도로 사용할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedPushBadge(
*     variant = PushBadgeVariant.Number,
*     count = "5",
*     position = PushBadgePosition.TopEnd,
*     size = PushBadgeSize.Small,
*     modifier = Modifier
* )
* ```
*
* @param modifier Modifier 배지의 배치, 정렬 등에 사용되는 Modifier입니다.
* @param variant PushBadgeVariant 표시할 배지 타입입니다. Dot, Number, New 중 선택할 수 있습니다.
* @param size PushBadgeSize 배지의 크기를 지정합니다. XSmall, Small, Medium 중 선택합니다.
* @param position PushBadgePosition 배지의 위치를 설정합니다. TopEnd 등 9가지 위치를 지원합니다.
* @param count String `Number` 타입일 경우 표시할 숫자입니다.
* @param background Color 배지의 배경 색상입니다. 기본값은 primary_normal입니다.
* @param contentColor Color 텍스트(숫자, "N")의 색상입니다. 기본값은 static_white입니다.
*/