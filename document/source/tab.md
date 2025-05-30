/**
* 탭 컴포넌트 내 그라디언트 색상을 설정하기 위한 CompositionLocal입니다.
*
* `CompositionLocalProvider`로 주입한 색상은 좌우 gradient 영역에 사용됩니다.
*
* 사용 예시:
* ```kotlin
* CompositionLocalProvider(LocalTabGradationColor provides Color.Red) {
*     ...
* }
* ```
*
* @property current Color: 현재 설정된 색상 값입니다.
*/

/**
* Data class that contains information about a tab's position on screen, used for calculating
* where to place the indicator that shows which tab is selected.
*
* @property left the left edge's x position from the start of the [TabRow]
* @property right the right edge's x position from the start of the [TabRow]
* @property width the width of this tab
*/

/**
* Class holding onto state needed for [ScrollableTabRow]
*/

/**
* @return the offset required to horizontally center the tab inside this TabRow.
* If the tab is at the start / end, and there is not enough space to fully centre the tab, this
* will just clamp to the min / max position given the max width.
*/

/**
* The default padding from the starting edge before a tab in a [ScrollableTabRow].
*/

/**
* [AnimationSpec] used when scrolling to a tab that is not fully visible.
*/

/**
* 스크롤 가능한 탭 레이아웃을 제공하는 컴포저블입니다.
*
* 좌우 gradient 효과, 우측 아이콘, 가변 너비를 지원하며,
* 선택된 탭은 SecondaryIndicator로 강조됩니다.
*
* 사용 예시:
* ```kotlin
* WantedScrollableTabRow(
*     itemCount = 5,
*     selectedTabIndex = 2,
*     content = { index -> "탭$index" }
* )
* ```
*
* @param itemCount Int: 탭 항목 수입니다.
* @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
* @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
* @param tabSize TabSize: 탭 크기 설정입니다.
* @param horizontalPadding Boolean: 양쪽 여백을 둘지 여부입니다.
* @param isLeftGradient Boolean: 왼쪽에 gradient를 표시할지 여부입니다.
* @param isRightGradient Boolean: 오른쪽에 gradient를 표시할지 여부입니다.
* @param gradientColor Color: gradient 색상입니다.
* @param scrollState ScrollState: 외부에서 전달받은 스크롤 상태입니다.
* @param onClickItem (index: Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
* @param rightIcon @Composable ((Dp) -> Unit)? : 탭 우측에 추가할 아이콘 슬롯입니다.
* @param content (index: Int) -> String: 각 탭의 텍스트를 반환합니다.
*/

/**
* object WantedTabContract
*
* 탭(Tab) UI 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
*
* 탭의 텍스트 크기를 조정하기 위한 enum 클래스 `TabSize`를 포함합니다.
*/

/**
* enum class TabSize
*
* 탭 텍스트의 크기를 정의하는 enum 클래스입니다.
*
* 텍스트 스타일에 따라 세 가지 크기를 제공합니다:
* - Small: `body2Bold` 스타일
* - Medium: `headline2Bold` 스타일
* - Large: `heading2Bold` 스타일
*/

/**
* 단일 탭 항목을 구성하는 텍스트 기반 컴포저블입니다.
*
* 선택 상태 및 텍스트 레이아웃 정보, 클릭 처리 등을 포함합니다.
*
* @param tabSize TabSize: 탭의 텍스트 스타일 및 패딩을 결정합니다.
* @param title String: 탭에 표시할 텍스트입니다.
* @param isSelect Boolean: 선택 여부입니다.
* @param modifier Modifier: 외형 설정용 Modifier입니다.
* @param onTextLayout ((TextLayoutResult) -> Unit)? : 텍스트 레이아웃 결과 콜백입니다.
* @param onClick () -> Unit: 클릭 시 호출됩니다.
*/

/**
* 고정형 탭 레이아웃을 제공하는 컴포저블입니다.
*
* 항목 수만큼 `WantedTabItem`을 구성하여 Material3의 TabRow와 함께 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedTabRow(
*     itemSize = 3,
*     selectedTabIndex = 1,
*     content = { index -> "탭$index" },
*     onClickItem = { index -> ... }
* )
* ```
*
* @param itemSize Int: 탭 항목 수입니다.
* @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
* @param modifier Modifier: 레이아웃 설정용 Modifier입니다.
* @param tabSize TabSize: 탭 텍스트 스타일 크기입니다.
* @param onClickItem (index: Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
* @param content (index: Int) -> String: 탭에 표시할 텍스트를 반환하는 함수입니다.
*/