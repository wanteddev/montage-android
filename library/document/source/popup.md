/**
* WantedPopup
*
* 상단 앱바와 Main Action 버튼을 포함한 기본 Popup 컴포넌트입니다.
*
* 사용 예시:
* ```kotlin
* var showModal by remember { mutableStateOf(true) }
*
* if (showModal) {
*     WantedPopup(
*         title = "제목",
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
* @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
* @param title String?: Navigation 의 제목입니다. null 이면 제목 없이 Close Button 만 노출합니다. Close Button 은 [onDismissRequest] 로 Popup 을 닫습니다.
* @param positive String?: Main Action 의 텍스트입니다.
* @param onClickPositive (() -> Unit)?: Main Action 클릭 시 호출되는 콜백입니다.
* @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
*/

/**
* WantedPopup
*
* 커스텀 하단 바를 포함한 모달 컴포넌트입니다.
*
* Main Action 버튼 대신 커스텀 bottomBar를 사용할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var showModal by remember { mutableStateOf(true) }
*
* if (showModal) {
*     WantedPopup(
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
* @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
* @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
* @param content (@Composable BoxScope.() -> Unit): 본문 콘텐츠 슬롯입니다.
*/

/**
* WantedPopup
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
*     WantedPopup(
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
* @param resize Resize: Popup 의 높이 결정 방식입니다. 기본값은 Hug 입니다.
* @param properties DialogProperties: Dialog 속성입니다.
* @param popupDefault WantedPopupDefault: Popup 의 모양·여백 설정입니다.
* @param topBar (@Composable () -> Unit)?: 상단 앱바 슬롯입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바 슬롯입니다.
* @param lazyContent (LazyListScope.() -> Unit): LazyColumn 콘텐츠 슬롯입니다.
*/

/**
* object WantedPopupContract
*
* Popup 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
*/

/**
* sealed class Resize
*
* Popup 의 높이 결정 방식을 정의하는 sealed 클래스입니다.
* Hug, Fixed 두 가지를 제공합니다.
*/

/**
* data object Hug
*
* 콘텐츠 높이에 맞춰 Popup 높이가 결정됩니다. 기본값입니다.
*/

/**
* data class Fixed
*
* 지정한 높이로 Popup 높이가 고정됩니다.
*
* @property height Dp: Popup 의 높이입니다.
*/

/**
* data class WantedPopupDefault
*
* Popup 의 모양과 여백을 정의하는 데이터 클래스입니다.
*
* 값을 바꿔 쓸 때는 [WantedPopupDefaults.getDefault] 의 인자로 넘기거나 `copy` 를 사용합니다.
*
* Popup 의 여백은 디자인 4.0.0 기준으로 ModalSize 와 무관하게 통일되어 있고,
* 모바일 Popup 은 Medium 한 가지만 사용하므로 [width] 도 하나로 고정입니다.
* (BottomSheet 는 계속 ModalSize 의 padding 값을 사용하므로 그쪽 값은 건드리지 않습니다.)
*
* @property shape RoundedCornerShape: Popup 의 모서리 둥글기입니다.
* @property width Dp: Popup 의 최대 너비입니다. 실제 너비는 Dialog 창 너비로 한 번 더 제한되므로, 폰에서는 창 너비를 그대로 따르고 큰 화면에서만 이 값으로 잘립니다.
* @property navigationPadding Dp: Navigation 영역에 Popup 이 추가로 얹는 여백입니다. 여백 자체는 Navigation 컴포넌트가 소유하므로 기본값은 0dp 입니다.
* @property contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
* @property contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
* @property actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
* @property actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
* @property actionBottomPadding Dp: Action Area 하단에 추가로 붙는 여백입니다.
* @property actionAreaType ActionAreaType: Action Area 의 타입입니다.
*
* @see WantedPopupDefaults
*/

/**
* object WantedPopupDefaults
*
* [WantedPopupDefault] 의 기본값을 제공하는 객체입니다.
*
* @see WantedPopupDefault
*/

/**
* fun getDefault(...)
*
* Popup 의 기본 설정을 생성합니다.
*
* 사용 예시:
* ```kotlin
* // 본문 좌우 여백만 없애고 나머지는 기본값을 쓴다
* WantedPopup(
*     onDismissRequest = { },
*     popupDefault = WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp),
*     content = { }
* )
* ```
*
* @param shape RoundedCornerShape: Popup 의 모서리 둥글기입니다.
* @param width Dp: Popup 의 최대 너비입니다.
* @param navigationPadding Dp: Navigation 영역에 Popup 이 추가로 얹는 여백입니다.
* @param contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
* @param contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
* @param actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
* @param actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
* @param actionBottomPadding Dp: Action Area 하단에 추가로 붙는 여백입니다.
* @param actionAreaType ActionAreaType: Action Area 의 타입입니다.
* @return WantedPopupDefault: 설정된 WantedPopupDefault 인스턴스를 반환합니다.
*
* @see WantedPopupDefault
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