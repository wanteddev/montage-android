/**
* 아코디언 형태로 확장/축소가 가능한 UI 컴포넌트입니다.
*
* 사용자는 제목(Title) 영역을 클릭하여 추가 설명(Description)과 콘텐츠(Content)를 확장하거나 축소할 수 있습니다.
* FAQ, 설정 메뉴, 리스트 등 다양한 인터페이스에서 유용하게 활용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedAccordion(
*     title = "제목",
*     modifier = Modifier,
*     description = "설명",
*     isExpanded = false,
*     onChangeExpanded = { expanded -> },
*     content = {
*         Text("확장 콘텐츠")
*     }
* )
* ```
*
* @param title String: 아코디언 헤더에 표시될 제목 텍스트입니다.
* @param modifier Modifier: 레이아웃 외형 및 동작을 위한 Modifier입니다.
* @param titleMaxLine Int: 제목 텍스트의 최대 줄 수입니다. 기본값은 Int.MAX_VALUE입니다.
* @param description String?: 제목 하단에 표시될 부가 설명입니다. 선택 사항입니다.
* @param titleStyle TextStyle: 제목에 적용할 텍스트 스타일입니다.
* @param descriptionStyle TextStyle: 설명 텍스트에 적용할 스타일입니다.
* @param isExpanded Boolean: 현재 아코디언이 확장 상태인지 여부를 나타냅니다.
* @param fillWidth Boolean: 콘텐츠의 가로 너비를 전체로 채울지 여부입니다.
* @param divider Boolean: 하단 Divider 표시 여부입니다.
* @param verticalPadding VerticalPadding: 헤더 영역의 수직 패딩 값입니다.
* @param leadingIcon @Composable (() -> Unit)?: 제목 좌측에 위치할 아이콘입니다. 선택 사항입니다.
* @param trail @Composable () -> Unit: 제목 우측에 위치할 아이콘입니다. 기본은 확장/축소 화살표입니다.
* @param content @Composable (() -> Unit)?: 확장 상태일 때 표시될 추가 콘텐츠입니다. 선택 사항입니다.
* @param onChangeExpanded (Boolean) -> Unit: 확장 상태 변경 시 호출되는 콜백입니다.
*
* @see WantedAccordionHeader
* @see WantedAccordionContract.VerticalPadding
* @see WantedAccordionTrailArrowIcon
*/

/**
* object WantedAccordionContract
*
* 아코디언 컴포넌트에서 사용되는 상수 및 설정 값을 정의하는 객체입니다.
*
* 이 객체는 주로 아코디언 헤더의 시각적 구성 요소 중 패딩 관련 값을 제공합니다.
*/

/**
* enum class VerticalPadding
*
* 아코디언 헤더 영역의 수직 패딩 크기를 정의하는 enum 클래스입니다.
*
* 아코디언 헤더 내 콘텐츠 간 간격을 조정할 때 사용됩니다. UI 요구사항에 따라 다음의 세 가지 옵션을 제공합니다:
* - Padding16: 16dp 수직 패딩
* - Padding12: 12dp 수직 패딩
* - Padding8: 8dp 수직 패딩
*
* @property value `Dp`: 실제 적용되는 패딩 값입니다.
*
* @see WantedAccordion
*/