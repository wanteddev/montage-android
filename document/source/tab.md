/**
* WantedScrollableTabRow
*
* 스크롤 가능한 탭 레이아웃 컴포넌트입니다.
*
* 좌우 gradient 효과, 우측 아이콘을 지원하며, 선택된 탭은 하단 인디케이터로 강조됩니다.
*
* 사용 예시:
* ```kotlin
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedScrollableTabRow(
*     itemCount = 5,
*     selectedTabIndex = selectedIndex,
*     content = { index -> "탭$index" },
*     onClickItem = { selectedIndex = it }
* )
* ```
*
* @param itemCount Int: 탭 항목 수입니다.
* @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param disableIndexList List<Int>: 비활성화할 탭 인덱스 리스트입니다.
* @param tabSize TabSize: 탭 크기 설정입니다.
* @param horizontalPadding Boolean: 양쪽 여백 적용 여부입니다.
* @param isLeftGradient Boolean: 왼쪽 gradient 표시 여부입니다.
* @param isRightGradient Boolean: 오른쪽 gradient 표시 여부입니다.
* @param gradientColor Color: gradient 색상입니다.
* @param scrollState ScrollState: 스크롤 상태를 관리하는 객체입니다.
* @param onClickItem (Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
* @param rightIcon (@Composable (Dp) -> Unit)?: 탭 우측에 추가할 아이콘 슬롯입니다.
* @param content (Int) -> String: 각 탭의 텍스트를 반환하는 함수입니다.
*/

/**
* object WantedTabContract
*
* 탭 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class TabSize
*
* 탭 텍스트의 크기를 정의하는 enum 클래스입니다.
* Small, Medium, Large 세 가지 크기가 존재합니다.
*/

/**
* WantedTabItem
*
* 단일 탭 항목을 구성하는 컴포넌트입니다.
*
* 선택 상태에 따라 텍스트 스타일이 변경되며, 클릭 이벤트를 처리합니다.
*
* @param tabSize TabSize: 탭의 텍스트 크기입니다.
* @param title String: 탭에 표시할 텍스트입니다.
* @param active Boolean: 선택 여부입니다.
* @param enable Boolean: 활성화 여부입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param onTextLayout ((TextLayoutResult) -> Unit)?: 텍스트 레이아웃 결과를 전달하는 콜백입니다.
* @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
*/

/**
* WantedTabRow
*
* 고정형 탭 레이아웃 컴포넌트입니다.
*
* 탭 항목들이 전체 너비에 균등하게 배치되며, 선택된 탭은 하단 인디케이터로 강조됩니다.
*
* 사용 예시:
* ```kotlin
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedTabRow(
*     itemSize = 3,
*     selectedTabIndex = selectedIndex,
*     content = { index -> "탭$index" },
*     onClickItem = { selectedIndex = it }
* )
* ```
*
* @param itemSize Int: 탭 항목 수입니다.
* @param selectedTabIndex Int: 현재 선택된 탭 인덱스입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param tabSize TabSize: 탭 텍스트 크기입니다.
* @param disableIndexList List<Int>: 비활성화할 탭 인덱스 리스트입니다.
* @param onClickItem (Int) -> Unit: 탭 클릭 시 호출되는 콜백입니다.
* @param content (Int) -> String: 각 탭의 텍스트를 반환하는 함수입니다.
*/