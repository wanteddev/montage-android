/**
* 카테고리 좌우 그라디언트 배경 색상 지정을 위한 CompositionLocal 래퍼입니다.
*
* `CompositionLocalProvider`를 통해 원하는 색상을 주입하여 내부 그라디언트 표현에 사용됩니다.
*
* 사용 예시:
* ```kotlin
* CompositionLocalProvider(LocalCategoryGradationColor provides Color.Red) {
*     ...
* }
* ```
*
* @property current Color: 현재 CompositionLocal에 저장된 색상입니다.
*/

/**
* 선택형 액션칩 목록을 표시하는 카테고리 컴포넌트입니다.
*
* 전달된 문자열 리스트를 기반으로 원하는 크기, 스타일, 정렬 및 선택 상태로 액션칩을 구성합니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory(
*     itemList = listOf("태그1", "태그2"),
*     selectedList = listOf("태그1"),
*     onClick = { tag, selected -> ... }
* )
* ```
*
* @param itemList List<String>: 표시할 문자열 목록입니다.
* @param selectedList List<String>: 선택된 항목 리스트입니다.
* @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
* @param state LazyListState: 내부 LazyRow 상태를 제어하는 객체입니다.
* @param size Size: 카테고리 항목 크기입니다.
* @param horizontalPadding Boolean: 좌우 여백 여부입니다.
* @param isVerticalPadding Boolean: 상하 여백 여부입니다.
* @param isAlternative Boolean: 선택 시 Outlined 스타일 적용 여부입니다.
* @param gradientColor Color: 좌우 그라디언트 배경 색상입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측에 표시할 아이콘 슬롯입니다.
* @param onClick (item: String, isSelected: Boolean) -> Unit: 항목 클릭 시 선택 상태를 포함하여 호출됩니다.
*/

/**
* 사용자 정의 콘텐츠로 구성할 수 있는 카테고리 컴포넌트입니다.
*
* LazyListScope 기반으로 직접 항목을 구성할 수 있으며, 좌우 그라디언트 및 아이콘 슬롯도 제공합니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory {
*     items(10) {
*         WantedActionChip(...)
*     }
* }
* ```
*
* @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
* @param state LazyListState: LazyRow의 스크롤 상태입니다.
* @param size Size: 액션칩 크기 및 여백 설정입니다.
* @param horizontalPadding Boolean: 좌우 패딩 여부입니다.
* @param isVerticalPadding Boolean: 상하 패딩 여부입니다.
* @param gradientColor Color: 좌우 그라디언트 색상입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
* @param content LazyListScope.() -> Unit: 내부 아이템 UI 구성 블록입니다.
*/

/**
* object WantedCategoryContract
*
* 카테고리 내 항목 UI 설정을 정의하는 객체입니다.
*
* 항목의 크기와 여백 설정을 위한 enum 클래스 `Size`를 포함하고 있습니다.
*/

/**
* enum class Size
*
* 카테고리 항목의 크기 및 여백 설정을 위한 enum 클래스입니다.
*
* 각 사이즈에 따라 항목의 세로 패딩 및 수평 간격이 다르게 적용됩니다.
*
* 포함된 사이즈 값:
* - Small: 세로 패딩 8dp, 수평 간격 4dp
* - Medium: 세로 패딩 8dp, 수평 간격 6dp
* - Large: 세로 패딩 10dp, 수평 간격 8dp
* - XLarge: 세로 패딩 10dp, 수평 간격 10dp
*
* @property verticalPadding `Dp`: 항목 세로 패딩입니다.
* @property horizontalSpacing `Dp`: 항목 수평 간격입니다.
*/