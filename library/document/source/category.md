/**
* WantedCategory
*
* 선택 가능한 액션칩 목록을 표시하는 카테고리 컴포넌트입니다.
*
* 문자열 리스트를 기반으로 액션칩을 구성하며, 선택 상태를 관리할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var selectedList by remember { mutableStateOf(listOf("태그1")) }
*
* WantedCategory(
*     itemList = listOf("태그1", "태그2", "태그3"),
*     selectedList = selectedList,
*     onClick = { item, isSelected ->
*         selectedList = if (isSelected) {
*             selectedList + item
*         } else {
*             selectedList - item
*         }
*     }
* )
* ```
*
* @param itemList List<String>: 표시할 항목 문자열 리스트입니다.
* @param selectedList List<String>: 선택된 항목 리스트입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param disableItemList List<String>: 비활성화할 항목 리스트입니다.
* @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
* @param size Size: 카테고리 항목의 크기입니다.
* @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다.
* @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다.
* @param isAlternative Boolean: 선택 시 Outlined 스타일 적용 여부입니다.
* @param gradientColor Color: 좌우 그라디언트 배경 색상입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측에 표시할 아이콘 슬롯입니다.
* @param onClick (String, Boolean) -> Unit: 항목 클릭 시 호출되는 콜백입니다. 선택된 항목과 선택 여부를 전달합니다.
*/

/**
* WantedCategory
*
* 사용자 정의 콘텐츠로 구성할 수 있는 카테고리 컴포넌트입니다.
*
* LazyListScope를 통해 항목을 직접 구성할 수 있으며, 그라디언트 효과와 우측 아이콘을 지원합니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory {
*     items(tagList) { tag ->
*         WantedActionChip(
*             text = tag,
*             onClick = { /* 처리 */ }
*         )
*     }
* }
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
* @param size Size: 액션칩의 크기 및 여백 설정입니다.
* @param horizontalPadding Boolean: 좌우 패딩 적용 여부입니다.
* @param isVerticalPadding Boolean: 상하 패딩 적용 여부입니다.
* @param gradientColor Color: 좌우 그라디언트 색상입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
* @param content LazyListScope.() -> Unit: 내부 아이템을 구성하는 블록입니다.
*/

/**
* object WantedCategoryDefaults
*
* 카테고리 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class Size
*
* 카테고리 항목의 크기 및 여백을 정의하는 enum 클래스입니다.
* Small, Medium, Large, XLarge 네 가지 크기가 존재합니다.
*
* @property verticalPadding Dp: 항목의 세로 패딩입니다.
* @property horizontalSpacing Dp: 항목 간 수평 간격입니다.
*/