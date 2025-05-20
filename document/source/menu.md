/**
* 섹션 타이틀과 아이템으로 구성된 기본 메뉴 컴포저블입니다.
*
* 각 섹션별로 타이틀과 아이템을 바인딩할 수 있으며, 최대 높이 및 패딩, 배경이 설정된 리스트 형태로 표시됩니다.
*
* 사용 예시:
* ```kotlin
* WantedMenu(
*     sectionCount = 2,
*     itemCount = { 3 },
*     onBindSectionTitle = { Text("Section Title") },
*     onBindSectionItem = { section, index -> Text("Item $index") }
* )
* ```
*
* @param sectionCount Int: 섹션의 총 개수입니다.
* @param itemCount (section: Int) -> Int: 각 섹션의 아이템 개수를 반환합니다.
* @param onBindSectionItem @Composable (section: Int, index: Int) -> Unit: 각 아이템에 대한 컴포저블입니다.
* @param modifier Modifier: 메뉴 전체에 적용할 Modifier입니다.
* @param onBindSectionTitle @Composable ((section: Int) -> Unit)?: 섹션 타이틀 컴포저블입니다.
*/

/**
* object WantedMenuContract
*
* 메뉴(Menu) 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*
* 리스트 형태를 지정하는 enum 클래스 `ListType`을 포함합니다.
*/

/**
* enum class ListType
*
* 메뉴 항목의 리스트 타입을 정의하는 enum 클래스입니다.
*
* 항목의 시각적 구성 방식에 따라 다음과 같은 타입을 제공합니다:
* - Normal: 기본 텍스트만 있는 리스트
* - Radio: 우측에 라디오 버튼이 포함된 리스트
* - Check: 좌측에 체크박스가 포함된 리스트
*/

/**
* 문자열 리스트를 바탕으로 구성되는 기본 메뉴 모달입니다.
*
* 항목 클릭 시 index와 문자열 값을 반환합니다.
*
* 사용 예시:
* ```kotlin
* WantedMenuModal(
*     items = listOf("Option 1", "Option 2"),
*     onDismissRequest = { },
*     onClick = { index, value -> }
* )
* ```
*
* @param items List<String>: 표시할 문자열 항목 리스트입니다.
* @param onDismissRequest () -> Unit: 모달 외부 클릭 시 호출되는 콜백입니다.
* @param properties DialogProperties: 다이얼로그 속성입니다.
* @param onClick (index: Int, value: String) -> Unit: 항목 클릭 시 콜백입니다.
*/

/**
* 리스트 유형(Radio, Check, Normal)에 따라 구성되는 메뉴 모달입니다.
*
* 각 항목에 라디오 버튼, 체크박스를 표시할 수 있으며 클릭 시 index와 값을 반환합니다.
*
* 사용 예시:
* ```kotlin
* WantedMenuModal(
*     items = listOf("One", "Two"),
*     listType = WantedMenuContract.ListType.Check,
*     onDismissRequest = { },
*     onClick = { index, value -> }
* )
* ```
*
* @param items List<String>: 표시할 문자열 항목 리스트입니다.
* @param onDismissRequest () -> Unit: 다이얼로그 해제 콜백입니다.
* @param properties DialogProperties: 다이얼로그 속성입니다.
* @param listType ListType: 리스트 스타일입니다. (Normal, Radio, Check)
* @param onClick (index: Int, value: String) -> Unit: 클릭 이벤트 콜백입니다.
*/

/**
* 커스텀 섹션 타이틀 및 아이템을 사용하는 메뉴 다이얼로그입니다.
*
* 복잡한 레이아웃이 필요한 경우 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedMenuModal(
*     sectionCount = 2,
*     itemCount = { 3 },
*     onBindSectionItem = { section, index -> Text(\"아이템\") },
*     onDismissRequest = { }
* )
* ```
*
* @param sectionCount Int: 섹션 수입니다.
* @param itemCount (Int) -> Int: 섹션별 아이템 수를 반환합니다.
* @param onBindSectionItem @Composable (Int, Int) -> Unit: 섹션 아이템 구성 함수입니다.
* @param properties DialogProperties: 다이얼로그 속성입니다.
* @param onBindSectionTitle @Composable ((Int) -> Unit)? : 섹션 타이틀 구성 함수입니다.
* @param onDismissRequest () -> Unit: 다이얼로그 닫힘 콜백입니다.
*/