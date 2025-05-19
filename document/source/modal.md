/**
* 상단 앱바와 확인/취소 버튼을 포함한 기본 모달입니다.
*
* `WantedDialogTwoButtonImpl`을 기반으로 레이아웃을 구성합니다.
*
* 사용 예시:
* ```kotlin
* WantedModal(
*     topBar = { WantedDialogTopAppBar(title = "제목") },
*     positive = "확인",
*     onClickPositive = {},
*     onDismissRequest = {},
*     content = { Text("내용") }
* )
* ```
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
* @param type ModalType: 모달의 유형을 결정합니다 (Flexible, Fixed 등).
* @param properties DialogProperties: Compose Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥근 정도입니다.
* @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
* @param positive String?: 확인 버튼 텍스트입니다.
* @param negative String?: 취소 버튼 텍스트입니다.
* @param onClickPositive (() -> Unit)?: 확인 버튼 클릭 시 콜백입니다.
* @param onClickNegative (() -> Unit)?: 취소 버튼 클릭 시 콜백입니다.
* @param content @Composable BoxScope.() -> Unit: 본문 영역입니다.
*/

/**
* 확인/취소 버튼 없이 콘텐츠 중심으로 구성된 모달입니다.
*
* 커스텀 bottomBar를 삽입할 수 있으며, `WantedDialogLayout`을 기반으로 구성됩니다.
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
* @param type ModalType: 모달의 유형 (Flexible, Fixed 등)을 설정합니다.
* @param properties DialogProperties: Compose Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
* @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바를 위한 Slot입니다.
* @param content @Composable BoxScope.() -> Unit: 본문 콘텐츠를 구성하는 Composable Slot입니다.
*/

/**
* LazyColumn 기반의 스크롤 가능한 콘텐츠를 포함한 모달입니다.
*
* 콘텐츠가 많은 경우 LazyColumn을 사용하여 스크롤 처리가 가능합니다.
*
* @param onDismissRequest () -> Unit: 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다.
* @param modifier Modifier: 외형 및 높이 조정 Modifier입니다.
* @param type ModalType: 모달의 유형 (Flexible, Fixed 등)을 설정합니다.
* @param properties DialogProperties: Compose Dialog 속성입니다.
* @param shape RoundedCornerShape: 모달의 모서리 둥글기입니다.
* @param topBar @Composable (() -> Unit)?: 상단 앱바 컴포저블입니다.
* @param bottomBar (@Composable () -> Unit)?: 하단 바를 위한 Slot입니다.
* @param lazyContent LazyListScope.() -> Unit: LazyColumn 콘텐츠를 구성하는 Slot입니다.
*/

/**
* 시스템 또는 사용자 정의 가능한 BottomSheet 형태의 모달입니다.
*
* `isSystemBottomSheet` 여부에 따라 `ModalBottomSheet` 또는 `WantedDraggableModalBottomSheet`를 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedModalBottomSheet(
*     isShow = true,
*     onDismissRequest = {},
*     content = { Text("시트 내용") }
* )
* ```
*
* @param isShow Boolean: 모달 표시 여부입니다.
* @param onDismissRequest () -> Unit: 닫힘 콜백입니다.
* @param modifier Modifier: 외형 조정 Modifier입니다.
* @param background Color: 배경 색상입니다.
* @param type ModalType: 시트 유형 (Flexible, Fixed 등)입니다.
* @param modalSize ModalSize: 콘텐츠 패딩 등을 조절하는 크기 설정입니다.
* @param dismissOnClickOutside Boolean: 외부 클릭 시 닫힘 여부입니다.
* @param topBar @Composable (() -> Unit)?: 상단 바입니다.
* @param bottomBar @Composable (() -> Unit)?: 하단 바입니다.
* @param content @Composable () -> Unit: 본문 콘텐츠입니다.
*/

/**
* object WantedModalContract
*
* 모달(Modal) UI 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
*
* 모달의 형태와 크기를 설정할 수 있는 `ModalType`, `ModalSize`를 포함합니다.
*/

/**
* sealed class ModalType
*
* 모달의 유형(Flexible, Fixed 등)을 정의하는 sealed 클래스입니다.
*
* 각 타입은 모달의 높이, 닫기 가능 여부, 시스템 BottomSheet 사용 여부 등을 설정합니다.
*/

/**
* data object Flexible
*
* 콘텐츠 크기에 따라 자동으로 조정되는 유동형 모달입니다.
*/

/**
* data class FixedWrapContent
*
* 콘텐츠 높이에 맞게 wrap되는 고정형 모달입니다.
*
* @param isCloseable `Boolean`: 닫기 가능 여부입니다.
* @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class Fixed
*
* 특정 높이를 갖는 고정형 모달입니다.
*
* @param height `Dp`: 지정할 모달 높이입니다.
* @param isCloseable `Boolean`: 닫기 가능 여부입니다.
* @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedFullScreen
*
* 화면 전체를 덮는 고정형 모달입니다.
*
* @param isCloseable `Boolean`: 닫기 가능 여부입니다.
* @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* data class FixedRatio
*
* 화면의 일정 비율을 기준으로 높이가 설정되는 고정형 모달입니다.
*
* @param ratio `Float`: 0.0~1.0 사이의 높이 비율입니다.
* @param isCloseable `Boolean`: 닫기 가능 여부입니다.
* @param isSystemBottomSheet `Boolean`: 시스템 BottomSheet 사용 여부입니다.
*/

/**
* enum class ModalSize
*
* 모달의 여백 및 내부 간격을 설정하는 enum 클래스입니다.
*
* 각 크기 옵션은 콘텐츠 패딩, 하단 바 간격, 타이틀 여백 등을 정의합니다.
*
* 포함된 값:
* - Medium: 기본형
* - Large: 넓은 여백
* - XLarge: 확장된 여백
*
* Deprecated:
* - Small
* - Custom
*
* @property contentPadding `Dp`: 콘텐츠에 적용될 기본 패딩입니다.
* @property bottomBarPadding `Dp`: 하단 바에 적용될 패딩입니다.
* @property titleVerticalPadding `Dp`: 타이틀 영역의 세로 패딩입니다.
* @property titleHorizontalPadding `Dp`: 타이틀 영역의 가로 패딩입니다.
*/

/**
* object WantedModalDefaults
*
* 모달(Modal) UI의 기본 구성 요소 및 유틸리티 기능을 제공하는 객체입니다.
*/

/**
* DragHandle
*
* 드래그 가능한 핸들 표시를 위한 컴포저블입니다.
*
* 시트 상단에 표시되는 손잡이 형태의 UI를 그리며, 색상과 모양을 커스터마이징할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedModalDefaults.DragHandle()
* ```
*
* @param modifier `Modifier`: 핸들의 위치 및 크기를 조정하기 위한 Modifier입니다.
* @param color `Color`: 핸들의 배경 색상입니다. 기본값은 `background_elevated_normal`입니다.
* @param shape `Shape`: 핸들의 모양입니다. 기본값은 `MaterialTheme.shapes.extraLarge`입니다.
*/