/**
* object WantedSegmentedContract
*
* SegmentedControl UI 구성 요소에 사용되는 설정 값을 정의하는 객체입니다.
*
* 세그먼트 크기를 정의하는 enum 클래스 `SegmentedSize`를 포함하고 있습니다.
*/

/**
* enum class SegmentedSize
*
* SegmentedControl의 크기를 정의하는 enum 클래스입니다.
*
* 사용되는 UI 환경에 따라 다음과 같은 크기를 제공합니다:
* - Small: 작은 크기
* - Medium: 중간 크기
* - Large: 큰 크기
*/

/**
* LocalWantedSegmentedSize
*
* SegmentedControl 내에서 사용할 수 있는 전역 CompositionLocal 변수입니다.
*
* 기본적으로 `SegmentedSize.Medium`을 제공합니다.
*/

/**
* 텍스트 리스트를 기반으로 하는 Segmented Control 컴포넌트입니다.
*
* 아이템들을 테두리 형태로 구분하여 선택할 수 있으며, 선택된 항목의 인덱스를 콜백으로 전달합니다.
* 내부적으로 `WantedSegmentedControlOutlinedItem`을 사용하며, 선택 상태에 따라 스타일이 변경됩니다.
*
* 사용 예시:
* ```kotlin
* val items = listOf("One", "Two", "Three")
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedSegmentedControlOutlined(
*     items = items,
*     selectedIndex = selectedIndex,
*     onClick = { selectedIndex = it }
* )
* ```
*
* @param items List<String>: 항목에 표시할 텍스트 리스트입니다.
* @param selectedIndex Int: 선택된 항목의 인덱스입니다.
* @param modifier Modifier: 외형을 설정하는 Modifier입니다.
* @param size WantedSegmentedContract.SegmentedSize: 세그먼트의 크기입니다 (Small, Medium, Large).
* @param onClick (index: Int) -> Unit: 항목 클릭 시 선택된 인덱스를 반환하는 콜백입니다.
*/

/**
* 사용자 정의 슬롯 방식으로 구성할 수 있는 Segmented Control 컴포저블입니다.
*
* 항목 수와 개별 항목 Composable을 입력받아 세그먼트를 구성하며,
* 클릭 시 콜백을 통해 선택된 인덱스를 반환합니다.
*
* 사용 예시:
* ```kotlin
* WantedSegmentedControlOutlined(
*     itemCount = 3,
*     item = { index ->
*         WantedSegmentedControlOutlinedItem(
*             title = "Item $index",
*             isSelected = selectedIndex == index
*         )
*     },
*     onClick = { index -> selectedIndex = index }
* )
* ```
*
* @param itemCount Int: 표시할 항목의 수입니다.
* @param item (index: Int) -> Unit: 각 인덱스에 대응하는 항목 Composable 슬롯입니다.
* @param modifier Modifier: 외형을 설정하는 Modifier입니다.
* @param size WantedSegmentedContract.SegmentedSize: 세그먼트 크기 설정입니다.
* @param onClick (index: Int) -> Unit: 항목 클릭 시 호출되는 콜백입니다.
*/

/**
* SegmentedControlOutlined 내 개별 항목을 구성하는 아이템 컴포넌트입니다.
*
* 선택 여부에 따라 배경 색상, 테두리 색상, 텍스트 색상이 달라지며,
* 좌우 끝 항목 여부에 따라 각진 모서리 처리가 적용됩니다.
* 또한, 필요 시 아이콘을 함께 표시할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedSegmentedControlOutlinedItem(
*     title = "Option",
*     isSelected = true,
*     isFirst = true,
*     isLast = false,
*     icon = {
*         Icon(
*             painter = painterResource(id = R.drawable.ic_info),
*             contentDescription = null
*         )
*     }
* )
* ```
*
* @param title String: 항목에 표시할 텍스트입니다.
* @param isSelected Boolean: 항목이 선택된 상태인지 여부입니다.
* @param modifier Modifier: 외형을 설정하는 Modifier입니다.
* @param isFirst Boolean: 해당 항목이 첫 번째 항목인지 여부입니다. 좌측 모서리 스타일에 영향을 줍니다.
* @param isLast Boolean: 해당 항목이 마지막 항목인지 여부입니다. 우측 모서리 스타일에 영향을 줍니다.
* @param icon (() -> Unit)?: 항목 왼쪽에 표시할 선택적 아이콘 Composable입니다.
*/

/**
* 문자열 리스트 기반의 Solid 스타일 Segmented Control 컴포넌트입니다.
*
* 선택된 항목을 강조 표시하며, 내부적으로 애니메이션 되는 Knob과 함께 사용됩니다.
* 항목은 텍스트 기반으로 자동 생성되며, 클릭 시 인덱스가 콜백으로 반환됩니다.
*
* 사용 예시:
* ```kotlin
* val items = listOf("전체", "읽음", "안읽음")
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedSegmentedControlSolid(
*     items = items,
*     selectedIndex = selectedIndex,
*     onClick = { selectedIndex = it }
* )
* ```
*
* @param items List<String>: 표시할 항목 텍스트 리스트입니다.
* @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
* @param modifier Modifier: 외형을 설정하는 Modifier입니다.
* @param size SegmentedSize: 항목의 사이즈 설정입니다 (Small, Medium, Large).
* @param onClick (index: Int) -> Unit: 항목 클릭 시 선택 인덱스를 반환하는 콜백입니다.
*/

/**
* 사용자 정의 항목(item slot)과 애니메이션 Knob이 포함된 Solid 스타일 Segmented Control 컴포넌트입니다.
*
* 각 항목을 커스텀 Composable로 구성할 수 있으며, 선택 애니메이션은 Knob 위치 이동으로 표현됩니다.
*
* 사용 예시:
* ```kotlin
* WantedSegmentedControlSolid(
*     itemCount = 3,
*     selectedIndex = selectedIndex,
*     item = { index ->
*         WantedSegmentedControlSolidItem(
*             title = "옵션 $index",
*             isSelected = index == selectedIndex
*         )
*     },
*     onClick = { selectedIndex = it }
* )
* ```
*
* @param itemCount Int: 표시할 항목 개수입니다.
* @param selectedIndex Int: 현재 선택된 항목 인덱스입니다.
* @param item (index: Int) -> Unit: 항목 렌더링을 위한 Composable 슬롯입니다.
* @param modifier Modifier: 외형을 설정하는 Modifier입니다.
* @param size SegmentedSize: 항목 크기 설정입니다.
* @param onClick (index: Int) -> Unit: 클릭 시 호출되는 콜백입니다.
*/

/**
* SegmentedControlSolid 내 개별 항목을 구성하는 아이템 컴포넌트입니다.
*
* 선택 상태에 따라 텍스트 색상이 달라지며, 선택 시 강조 색상(`label_normal`)이 적용됩니다.
* `SegmentedSize`에 따라 패딩 및 텍스트 스타일이 달라지며, 아이콘을 함께 표시할 수 있습니다.
* Solid 형태의 SegmentedControl에서 각 항목을 표현할 때 사용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedSegmentedControlSolidItem(
*     title = "알림",
*     isSelected = true,
*     icon = {
*         Icon(
*             painter = painterResource(id = R.drawable.ic_info),
*             contentDescription = null
*         )
*     }
* )
* ```
*
* @param title String: 항목에 표시할 텍스트입니다.
* @param isSelected Boolean: 현재 항목이 선택되었는지 여부입니다.
* @param modifier Modifier: 외형을 조정하는 Modifier입니다.
* @param icon (() -> Unit)?: 항목 왼쪽에 표시할 선택적 아이콘 Composable입니다.
*/