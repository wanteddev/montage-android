/**
* 사용 편의성을 위한 필터 Chip입니다.
* 간단한 설정만으로 기본 스타일의 필터 Chip을 사용할 수 있으며,
* 내부적으로 CompositionLocal을 활용하여 관련 상태를 주입합니다.
*
* 사용 예시:
* ```kotlin
* WantedFilterChip(
*     text = "필터",
*     activeLabel = "3",
*     onClick = { /* 클릭 동작 */ }
* )
* ```
*
* @param text String: Chip에 표시될 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
* @param activeLabel String: Chip이 활성화되었을 때 표시할 라벨 텍스트입니다.
* @param size FilterChipSize: Chip의 크기를 정의하는 enum 값입니다 (기본값: Small).
* @param variant FilterChipVariant: Chip의 스타일입니다 (기본값: Solid).
* @param isActive Boolean: Chip의 활성화 상태입니다.
* @param isEnable Boolean: Chip의 사용 가능 상태입니다.
* @param isExpend Boolean: 확장 가능 상태입니다 (아이콘 변경 목적).
* @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다.
*/

/**
* Chip 기본 설정을 외부에서 주입할 수 있는 커스터마이징 버전의 필터 Chip입니다.
* 기본 스타일뿐만 아니라 텍스트 스타일, 색상 등 세부 속성을 설정할 수 있으며,
* 내부적으로 주어진 chipDefault 설정값을 기반으로 Chip을 구성합니다.
*
* 사용 예시:
* ```kotlin
* WantedFilterChip(
*     text = "필터",
*     activeLabel = "3",
*     chipDefault = customDefault,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: Chip에 표시될 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
* @param activeLabel String: Chip이 활성화되었을 때 표시할 라벨 텍스트입니다.
* @param isExpanded Boolean: Chip이 확장 상태인지 여부입니다 (화살표 아이콘에 반영됨).
* @param chipDefault WantedFilterChipDefault: 외부에서 주입하는 Chip의 기본 설정값입니다.
* @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다.
*/

/**
* object WantedFilterChipContract
*
* FilterChip에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
*
* 이 객체는 FilterChip의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
*/

/**
* enum class FilterChipVariant
*
* WantedFilterChip의 시각적 스타일을 정의하는 enum 클래스입니다.
*
* 다음과 같은 두 가지 스타일 옵션을 제공합니다:
* - Solid: 배경이 채워진 형태입니다.
* - Outlined: 테두리만 있는 형태입니다.
*
* @see WantedFilterChip
*/

/**
* enum class FilterChipSize
*
* WantedFilterChip의 크기를 정의하는 enum 클래스입니다.
*
* 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
* 제공되는 크기 옵션은 다음과 같습니다:
* - Large: 큰 사이즈입니다.
* - Medium: 중간 사이즈입니다.
* - Small: 작은 사이즈입니다.
* - XSmall: 가장 작은 사이즈입니다.
*
* @see WantedFilterChip
*/

/**
* data class WantedFilterChipDefault
*
* WantedFilterChip의 기본 스타일을 정의하는 데이터 클래스입니다.
* 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedFilterChipDefault(
*     size = FilterChipSize.Medium,
*     variant = FilterChipVariant.Solid,
*     isActive = true,
*     isEnable = true,
*     iconColor = Color.Black,
*     backgroundColor = Color.White,
*     borderColor = Color.Gray,
*     textStyle = TextStyle.Default
* )
* ```
*
* @param size FilterChipSize: Chip의 크기를 설정합니다.
* @param variant FilterChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: Chip의 활성화 여부입니다.
* @param isEnabled Boolean: Chip의 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
* @return WantedFilterChipDefault: 설정된 WantedFilterChipDefault 객체를 반환합니다.
*/

/**
* fun getDefault(...)
* 기본 스타일에 맞춰 WantedFilterChipDefault 객체를 생성합니다.
* Compose 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedFilterChipDefaults.getDefault(
*     size = FilterChipSize.Medium,
*     variant = FilterChipVariant.Solid
* )
* ```
*
* @param size FilterChipSize: Chip의 크기를 설정합니다.
* @param variant FilterChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
* @return WantedFilterChipDefault: 설정된 WantedFilterChipDefault 객체를 반환합니다.
*/

/**
* fun getFilterIconColor(...)
*
* 필터 Chip 전용으로 아이콘 색상을 반환합니다.
* Chip의 variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
*
* 사용 예시:
* ```kotlin
* val iconColor = WantedFilterChipDefaults.getFilterIconColor(
*     variant = FilterChipVariant.Solid,
*     isActive = true
* )
* ```
*
* @param variant FilterChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @return Int: 아이콘 색상에 해당하는 리소스 ID를 반환합니다.
*/