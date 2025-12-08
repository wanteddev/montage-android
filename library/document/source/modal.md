/**
* WantedModal
*
* 상단 앱바와 확인/취소 버튼을 포함한 기본 모달 컴포넌트입니다.
*
* 사용 예시:
* ```kotlin
* var showModal by remember { mutableStateOf(true) }
*
* if (showModal) {
*     WantedModal(
*         topBar = { WantedDialogTopAppBar(title = "제목") },
*         positive = "확인",
*         onClickPositive = { showModal = false },
*         onDismissRequest = { showModal = false },
*         content = { Text("내용") }
*     )
* }
* ```
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param type ModalType: 모달의 형태입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
* @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
* @param positive String?: 확인 버튼 텍스트입니다.
* @param negative String?: 취소 버튼 텍스트입니다.
* @param onClickPositive (() -> Unit)?: 확인 버튼 클릭 시 호출되는 콜백입니다.
* @param onClickNegative (() -> Unit)?: 취소 버튼 클릭 시 호출되는 콜백입니다.
* @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
*/

/**
* WantedModal
*
* 커스텀 하단 바를 포함한 모달 컴포넌트입니다.
*
* 확인/취소 버튼 대신 커스텀 bottomBar를 사용할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var showModal by remember { mutableStateOf(true) }
*
* if (showModal) {
*     WantedModal(
*         topBar = { WantedDialogTopAppBar(title = "제목") },
*         bottomBar = {
*             Button(onClick = { showModal = false }) {
*                 Text("닫기")
*             }
*         },
*         onDismissRequest = { showModal = false },
*         content = { Text("내용") }
*     )
* }
* ```
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param type ModalType: 모달의 형태입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
* @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
* @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
*/

/**
* WantedModal
*
* LazyColumn 기반의 스크롤 가능한 모달 컴포넌트입니다.
*
* 많은 양의 콘텐츠를 스크롤하여 표시할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var showModal by remember { mutableStateOf(true) }
*
* if (showModal) {
*     WantedModal(
*         topBar = { WantedDialogTopAppBar(title = "제목") },
*         onDismissRequest = { showModal = false },
*         lazyContent = {
*             items(20) { index ->
*                 Text("아이템 $index")
*             }
*         }
*     )
* }
* ```
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param type ModalType: 모달의 형태입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
* @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
* @param lazyContent (LazyListScope.() -> Unit): LazyColumn 콘텐츠 슬롯입니다.
*/

/**
* WantedModalBottomSheet
*
* BottomSheet 형태의 모달 컴포넌트입니다.
*
* 시스템 BottomSheet 또는 커스텀 드래그 가능한 BottomSheet를 사용할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var showSheet by remember { mutableStateOf(false) }
*
* Button(onClick = { showSheet = true }) {
*     Text("시트 열기")
* }
*
* WantedModalBottomSheet(
*     isShow = showSheet,
*     onDismissRequest = { showSheet = false },
*     content = { Text("시트 내용") }
* )
* ```
*
* @param isShow Boolean: 모달 표시 여부입니다.
* @param onDismissRequest () -> Unit: 모달이 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param background Color: 배경 색상입니다.
* @param type ModalType: 모달의 형태입니다.
* @param modalSize ModalSize: 콘텐츠 패딩 등을 조절하는 크기 설정입니다.
* @param dismissOnClickOutside Boolean: 외부 클릭 시 닫힘 여부입니다.
* @param topBar (@Composable () -> Unit)?: 상단 바 슬롯입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
* @param content (@Composable () -> Unit): 본문 콘텐츠 슬롯입니다.
*/

/**
* object WantedModalDefaults
*
* 모달 컴포넌트의 기본 구성 요소를 제공하는 객체입니다.
*/

/**
* fun DragHandle(...)
*
* BottomSheet 상단에 표시되는 드래그 핸들 컴포넌트입니다.
*
* 사용 예시:
* ```kotlin
* WantedModalDefaults.DragHandle()
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param color Color: 핸들의 배경 색상입니다.
* @param shape Shape: 핸들의 모양입니다.
*/