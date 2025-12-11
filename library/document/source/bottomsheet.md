/**
* object WantedModalDefaults
*
* Bottom sheet 상단에 표시되는 드래그 핸들 컴포넌트입니다.
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

/**
* WantedModalBottomSheet
*
* Bottom sheet 형태의 모달 컴포넌트입니다.
*
* 시스템 Bottom sheet 또는 커스텀 드래그가 가능한 Bottom sheet를 사용할 수 있습니다.
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