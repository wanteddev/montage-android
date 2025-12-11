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
* object WantedModalContract
*
* Modal 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
*
*/

/**
* sealed class ModalType
*
* Modal의 형태를 정의하는 sealed 클래스입니다.
* Flexible, FixedWrapContent, Fixed, FixedFullScreen, FixedRatio 형태를 제공합니다.
*/

/**
* data object Flexible
*
* 콘텐츠 크기에 따라 자동으로 조정되는 유동형 Modal 입니다.
*/

/**
* data class FixedWrapContent
*
* 콘텐츠 높이에 맞게 조정되는 고정형 Modal 입니다.
*
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class Fixed
*
* 특정 높이를 갖는 고정형 Modal 입니다.
*
* @property height Dp: Modal 의 높이입니다.
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedFullScreen
*
* 화면 전체를 덮는 고정형 Modal 입니다.
*
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedRatio
*
* 화면 비율을 기준으로 높이가 설정되는 고정형 Modal입니다.
*
* @property ratio Float: 0.0 ~ 1.0 사이의 높이 비율입니다.
* @property isCloseable Boolean: 닫기 가능 여부입니다.
* @property isSystemBottomSheet Boolean: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* enum class ModalSize
*
* Modal의 여백 및 패딩을 정의하는 enum 클래스입니다.
*
* - Small: 작은 크기의 Modal 입니다.
* - Medium: 중간 크기의 Modal 입니다.
* - Large: 큰 크기의 Modal 입니다.
* - XLarge: 매우 큰 크기의 Modal 입니다.
* - Custom: 커스텀 크기의 Modal 입니다. 모든 패딩이 0dp로 설정되어 사용자가 직접 정의 할 수 있습니다.
*/