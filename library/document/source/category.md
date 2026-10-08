/**
* WantedCategory
*
* 선택 가능한 Chip 목록을 표시하는 컴포넌트입니다.
*
* 문자열 리스트를 기반으로 Chip을 구성하며, 선택 상태를 관리할 수 있습니다.
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
* @param variant Variant: 선택 항목 스타일입니다. Normal 이면 채워진 배경으로, Alternative 면 primary 테두리·옅은 배경으로 표시합니다. 선택 항목 색상은 Normal 에서만 지정할 수 있습니다.
* @param gradientColor Color: 좌우 그라디언트 배경 색상입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측에 표시할 아이콘 슬롯입니다.
* @param onClick (String, Boolean) -> Unit: 항목 클릭 시 호출되는 콜백입니다. 선택된 항목과 선택 여부를 전달합니다.
*/

/**
* WantedCategory
*
* 사용자 정의 콘텐츠로 구성할 수 있는 Category 컴포넌트입니다.
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
* WantedCategory
*
* [WantedCategoryDefault] 로 표시 스펙(크기·여백·그라디언트 색상)을 지정하는 Category 컴포넌트입니다.
*
* LazyListScope를 통해 항목을 직접 구성할 수 있으며, 그라디언트 효과와 우측 아이콘을 지원합니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory(
*     categoryDefault = WantedCategoryDefaults.getDefault(
*         size = WantedCategoryDefaults.Size.Large,
*         gradientColor = DesignSystemTheme.colors.surfaceElevatedPrimary
*     )
* ) {
*     items(tagList) { tag ->
*         WantedChip(
*             text = tag,
*             onClick = { /* 처리 */ }
*         )
*     }
* }
* ```
*
* @param categoryDefault WantedCategoryDefault: 크기·여백·그라디언트 색상을 담는 표시 스펙입니다. [WantedCategoryDefaults.getDefault] 로 생성합니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
* @param content LazyListScope.() -> Unit: 내부 아이템을 구성하는 블록입니다.
*/

/**
* data class WantedCategoryDefault
*
* WantedCategory 의 표시 스펙을 지정하는 데이터 클래스입니다.
*
* 항목 크기·여백과 좌우 그라디언트 색상을 함께 담습니다. 기본값은 [WantedCategoryDefaults.getDefault] 로 생성합니다.
*
* @param size Size: 카테고리 항목의 크기 및 여백입니다.
* @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다.
* @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다.
* @param gradientColor Color: 좌우 그라디언트 색상입니다.
*/

/**
* object WantedCategoryDefaults
*
* 카테고리 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class Size
*
* 카테고리 항목의 크기 및 여백을 정의하는 Enum 클래스입니다.
*
* 카테고리 항목의 시각적 크기와 간격을 결정할 때 사용됩니다. UI 요구사항에 따라 다음의 네 가지 옵션을 제공합니다:
* - Small: 작은 크기의 카테고리 항목입니다.
* - Medium: 중간 크기의 카테고리 항목입니다.
* - Large: 큰 크기의 카테고리 항목입니다.
* - XLarge: 매우 큰 크기의 카테고리 항목입니다.
*
* @property verticalPadding Dp: 상하 여백 적용 시 LazyRow 의 상하 여백입니다.
* @property horizontalSpacing Dp: 항목 간 가로 간격입니다.
* @property rightIconSize Dp: 우측 아이콘 슬롯의 크기입니다.
*
* @see WantedCategoryDefault
*/

/**
* fun getDefault(...)
*
* WantedCategory 의 기본 표시 스펙을 생성합니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory(
*     categoryDefault = WantedCategoryDefaults.getDefault(
*         size = WantedCategoryDefaults.Size.Large,
*         gradientColor = DesignSystemTheme.colors.surfaceElevatedPrimary
*     )
* ) {
*     items(tagList) { tag -> WantedChip(text = tag) }
* }
* ```
*
* @param size Size: 카테고리 항목의 크기 및 여백입니다. 기본값은 Medium 입니다.
* @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다. 기본값은 false 입니다.
* @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다. 기본값은 false 입니다.
* @param gradientColor Color: 좌우 그라디언트 색상입니다. 기본값은 backgroundNeutralPrimary 입니다.
* @return WantedCategoryDefault: 표시 스펙 객체를 반환합니다.
*/

/**
* sealed class Variant
*
* 카테고리에서 선택된 항목의 스타일을 정의하는 sealed 클래스입니다. (Figma Category/Resource/Chip Normal · Alternative)
*
* - Normal: 선택 항목을 채워진 배경으로 표시합니다. 선택 항목 색상을 커스텀할 수 있습니다.
* - Alternative: 선택 항목을 primary 테두리·옅은 배경으로 표시합니다. 색상 커스텀을 제공하지 않습니다.
*
* 사용 예시:
* ```kotlin
* WantedCategory(
*     itemList = itemList,
*     selectedList = selectedList,
*     variant = WantedCategoryDefaults.Variant.Normal(
*         activeBackgroundColor = DesignSystemTheme.colors.surfaceBrandPrimary,
*         activeContentColor = DesignSystemTheme.colors.staticWhite
*     ),
*     onClick = { _, _ -> }
* )
* ```
*/

/**
* data class Normal
*
* 선택 항목을 채워진 배경으로 표시합니다. 테두리는 없습니다.
*
* 지정하지 않은 색상(Color.Unspecified)은 기본 토큰으로 표시합니다.
*
* @param activeBackgroundColor Color: 선택된 항목의 배경 색상입니다. 기본값은 foregroundNeutralStrong 입니다.
* @param activeContentColor Color: 선택된 항목의 텍스트·아이콘 색상입니다. 기본값은 foregroundNeutralInverse 입니다.
*/

/**
* data object Alternative
*
* 선택 항목을 surfaceBrandPrimary 5% 배경 + surfaceBrandPrimary 43% 테두리로 표시합니다.
*/

/**
* fun getChipDefault(...)
*
* Category 항목 Chip 스타일을 생성합니다.
*
* 미선택 상태는 Outlined Chip 기본값과 같고, 선택 상태만 [Variant] 에 따라 칠합니다.
* - Normal 선택: [Variant.Normal] 의 배경·콘텐츠 색, 테두리 없음
* - Normal 선택 + 비활성: surfaceDisablePrimary 배경, 테두리 없음
* - Alternative 선택: surfaceBrandPrimary 5% 배경 + surfaceBrandPrimary 43% 테두리 (Outlined Chip 기본 테두리는 28%)
*
* @param size Size: 카테고리 항목의 크기입니다.
* @param variant Variant: 선택 항목 스타일입니다.
* @param isActive Boolean: 선택 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @return WantedChipDefault: Category 항목 Chip 스타일을 반환합니다.
*/