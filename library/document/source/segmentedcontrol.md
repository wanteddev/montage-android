/**
* WantedSegmentedControl
*
* 문자열 리스트 기반의 Segmented Control 컴포넌트입니다.
*
* 선택된 항목을 강조 표시하며, 애니메이션되는 Knob으로 선택 상태를 표현합니다.
*
* 사용 예시:
* ```kotlin
* val items = listOf("전체", "읽음", "안읽음")
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedSegmentedControl(
*     items = items,
*     selectedIndex = selectedIndex,
*     onClick = { selectedIndex = it }
* )
* ```
*
* @param items List<String>: 표시할 항목 텍스트 리스트입니다.
* @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param size SegmentedSize: 컴포넌트의 크기입니다. Small, Medium, Large 중 선택할 수 있습니다.
* @param onClick (Int) -> Unit: 항목 클릭 시 선택된 인덱스를 전달하는 콜백 함수입니다.
*/

/**
* WantedSegmentedControl
*
* 사용자 정의 항목으로 구성할 수 있는 Segmented Control 컴포넌트입니다.
*
* 각 항목을 커스텀 컴포넌트로 구성할 수 있으며, 선택 애니메이션은 Knob 위치 이동으로 표현됩니다.
* `iconOnly = true` 로 설정하면 각 세그먼트가 사이즈별 고정 너비로 배치되고, [LocalWantedSegmentedIconOnly]
* 를 통해 하위 [WantedSegmentedControlItem] 이 아이콘 전용으로 렌더링됩니다.
*
* 사용 예시:
* ```kotlin
* var selectedIndex by remember { mutableIntStateOf(0) }
*
* WantedSegmentedControl(
*     itemCount = 3,
*     selectedIndex = selectedIndex,
*     item = { index ->
*         WantedSegmentedControlItem(
*             title = "옵션 $index",
*             isSelected = index == selectedIndex,
*             icon = { Icon(...) }
*         )
*     },
*     onClick = { selectedIndex = it }
* )
* ```
*
* @param itemCount Int: 표시할 항목 개수입니다.
* @param selectedIndex Int: 현재 선택된 항목의 인덱스입니다.
* @param item @Composable (Int) -> Unit: 각 항목을 렌더링하는 Composable 슬롯입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param size SegmentedSize: 컴포넌트의 크기입니다. Small, Medium, Large 중 선택할 수 있습니다.
* @param iconOnly Boolean: 아이콘 전용 모드 여부입니다. true 이면 세그먼트가 사이즈별 고정 너비로 배치됩니다.
* @param onClick (Int) -> Unit: 항목 클릭 시 선택된 인덱스를 전달하는 콜백 함수입니다.
*/

/**
* WantedSegmentedControlItem
*
* SegmentedControl 내 개별 항목을 구성하는 컴포넌트입니다.
*
* 선택 여부에 따라 텍스트·아이콘 색상이 변경되며, 선택 시 강조 색상(foregroundNeutralPrimary)이 적용됩니다.
* 사이즈별 타이포·아이콘 크기·간격은 [LocalWantedSegmentedSize] 로 전달된 값을 따릅니다.
*
* 아이콘 전용(Icon Only) 여부는 파라미터가 아니라 상위 컨트롤이 제공하는 [LocalWantedSegmentedIconOnly]
* 로 결정됩니다. 컨트롤([WantedSegmentedControl])에서 `iconOnly = true` 로 설정하면 이 항목은
* 텍스트 없이 아이콘만 중앙 정렬하며, 사이즈별 고정 너비(`iconOnlyWidth`)를 갖습니다.
* 아이콘 크기도 icon + text 모드(`iconSize`)보다 한 단계 큰 `iconOnlyIconSize` 를 사용합니다.
*
* 아이콘 슬롯은 사이즈별 크기(`iconSize` / `iconOnlyIconSize`)를 가진 Box 로 감쌉니다. 이 Box 는
* 최대 크기만 제한하므로 슬롯보다 **작은** 아이콘은 확대되지 않습니다. 스펙 크기로 꽉 채우려면
* 호출측에서 아이콘에 `Modifier.fillMaxSize()` 를 지정해야 합니다.
* (`Modifier.requiredSize` 처럼 제약을 무시하는 Modifier 는 슬롯을 벗어나므로 사용하지 않습니다.)
*
* 사용 예시:
* ```kotlin
* // text / icon + text
* WantedSegmentedControlItem(
*     title = "알림",
*     isSelected = true,
*     icon = { Icon(modifier = Modifier.fillMaxSize(), ...) }
* )
*
* // icon only — 컨트롤에서 iconOnly = true 를 지정하면 title 없이 아이콘만 렌더링됩니다.
* WantedSegmentedControlItem(
*     isSelected = true,
*     icon = { Icon(modifier = Modifier.fillMaxSize(), ...) }
* )
* ```
*
* @param isSelected Boolean: 항목의 선택 여부입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 항목에 표시할 텍스트입니다. Icon Only 모드에서는 무시됩니다.
* @param icon (@Composable () -> Unit)?: 표시할 아이콘 Composable입니다. Icon Only 모드에서는 필수입니다. 사이즈별 아이콘 슬롯을 꽉 채우려면 `Modifier.fillMaxSize()` 를 지정합니다.
*/

/**
* object WantedSegmentedDefaults
*
* SegmentedControl 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
*/

/** 컨트롤(트랙) 내부 여백입니다. 모든 사이즈 공통값입니다. */

/**
* enum class SegmentedSize
*
* SegmentedControl 의 크기를 정의하는 enum 클래스입니다.
* 각 사이즈는 Figma 스펙에 맞춘 고정 높이·라운딩·패딩·아이콘 크기 값을 가집니다.
*
* @param height 항목의 기준 높이입니다(=트랙 전체 높이 − [ContainerPadding]×2). 항목은 이 값을 최소 높이(defaultMinSize)로 사용하며, 시스템 폰트 확대 시 그 이상으로 늘어납니다.
* @param containerRadius 컨트롤(트랙) 모서리 반경입니다.
* @param knobRadius 선택 강조 Knob 의 모서리 반경입니다.
* @param horizontalPadding text / icon+text 세그먼트의 좌우 여백입니다.
* @param verticalPadding text / icon+text 세그먼트의 상하 여백입니다.
* @param iconTextGap 아이콘과 텍스트 사이 간격입니다.
* @param iconSize icon + text 세그먼트의 아이콘 크기입니다.
* @param iconOnlyIconSize Icon Only 세그먼트의 아이콘 크기입니다. icon + text 보다 한 단계 큽니다.
* @param iconOnlyWidth Icon Only 세그먼트의 고정 너비입니다. 높이([height])보다 2dp 넓습니다.
*/