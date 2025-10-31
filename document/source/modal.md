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
* object WantedModalContract
*
* 모달 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* sealed class ModalType
*
* 모달의 형태를 정의하는 sealed 클래스입니다.
* Flexible, FixedWrapContent, Fixed, FixedFullScreen, FixedRatio 다섯 가지 형태가 존재합니다.
*/

/**
* data object Flexible
*
* 콘텐츠 크기에 따라 자동으로 조정되는 유동형 모달입니다.
*/

/**
* data class FixedWrapContent
*
* 콘텐츠 높이에 맞게 조정되는 고정형 모달입니다.
*
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class Fixed
*
* 특정 높이를 갖는 고정형 모달입니다.
*
* @property height Dp: 모달의 높이입니다.
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedFullScreen
*
* 화면 전체를 덮는 고정형 모달입니다.
*
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedRatio
*
* 화면 비율을 기준으로 높이가 설정되는 고정형 모달입니다.
*
* @property ratio Float: 0.0 ~ 1.0 사이의 높이 비율입니다.
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* enum class ModalSize
*
* 모달의 여백 및 패딩을 정의하는 enum 클래스입니다.
* Medium, Large, XLarge 세 가지 크기가 존재합니다.
*
* @property contentPadding Dp: 콘텐츠 패딩입니다.
* @property bottomBarPadding Dp: 하단 바 패딩입니다.
* @property titleVerticalPadding Dp: 타이틀 세로 패딩입니다.
* @property titleHorizontalPadding Dp: 타이틀 가로 패딩입니다.
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