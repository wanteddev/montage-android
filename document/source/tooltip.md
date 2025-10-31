/**
* WantedTooltip
*
* 커스텀 툴팁 컴포넌트입니다.
*
* 앵커 요소 주변에 텍스트를 표시하며, 화면 경계를 고려하여 자동으로 위치를 조정합니다.
* 화살표를 통해 앵커와의 연관성을 시각적으로 표현합니다.
*
* 사용 예시:
* ```kotlin
* val tooltipState = rememberTooltipState()
*
* WantedTooltip(
*     modifier = Modifier.padding(16.dp),
*     tooltipState = tooltipState,
*     text = "이것은 도움말 텍스트입니다.",
*     size = WantedTooltipSize.Medium,
*     align = WantedTooltipAlign.Center
* ) {
*     Icon(
*         painter = painterResource(id = R.drawable.ic_help),
*         contentDescription = "도움말",
*         modifier = Modifier.clickable { tooltipState.show() }
*     )
* }
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param tooltipState WantedTooltipState: 툴팁의 표시/숨김 상태를 관리하는 객체입니다.
* @param text String: 툴팁에 표시할 텍스트입니다. 최대 3줄까지 표시되며, 초과 시 말줄임표로 처리됩니다.
* @param size WantedTooltipSize: 툴팁의 크기입니다.
* @param align WantedTooltipAlign: 앵커 요소에 대한 툴팁의 정렬 방식입니다.
* @param always Boolean: 외부 클릭으로 닫히지 않도록 할지 여부입니다.
* @param positionTop Boolean: 툴팁을 위쪽에 표시할지 여부입니다.
* @param content (@Composable () -> Unit): 툴팁을 트리거하는 앵커 콘텐츠 슬롯입니다.
*/

/**
* interface WantedTooltipState
*
* 툴팁의 표시/숨김 상태를 관리하는 인터페이스입니다.
*
* 툴팁을 표시하거나 숨기기 위한 메서드를 제공하며, 현재 표시 상태를 확인할 수 있습니다.
*/

/**
* rememberTooltipState
*
* 툴팁 상태를 관리하는 State 객체를 생성하고 기억합니다.
*
* 사용 예시:
* ```kotlin
* val tooltipState = rememberTooltipState(initialVisible = false)
*
* // 툴팁 표시
* tooltipState.show()
*
* // 툴팁 숨김
* tooltipState.dismiss()
* ```
*
* @param initialVisible Boolean: 초기 표시 상태입니다.
* @return WantedTooltipState: 툴팁 상태를 관리하는 객체입니다.
*/

/**
* enum class WantedTooltipSize
*
* 툴팁의 크기를 정의하는 enum 클래스입니다.
* Small, Medium 두 가지 크기가 존재합니다.
*/

/**
* enum class WantedTooltipAlign
*
* 툴팁의 정렬 방식을 정의하는 enum 클래스입니다.
* Left, Center, Right 세 가지 정렬 방식이 존재합니다.
*/