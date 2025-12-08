/**
* 사용자가 선택할 수 있는 Chip 컴포넌트를 생성합니다.
* 텍스트, 아이콘, 크기, 활성화 여부 등의 다양한 설정을 지원합니다.
*
* 사용 예시:
* ```kotlin
* WantedActionChip(
*     text = "텍스트",
*     leftIcon = R.drawable.ic_sample_icon,
*     rightIcon = R.drawable.ic_sample_icon,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: 표시할 텍스트입니다.
* @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
* @param size ActionChipSize: Chip의 크기를 설정합니다.
* @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 선택 여부 상태입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @param leftIcon Int?: 왼쪽에 표시할 아이콘 리소스 ID입니다.
* @param rightIcon Int?: 오른쪽에 표시할 아이콘 리소스 ID입니다.
* @param interactionSource MutableInteractionSource: 클릭 시의 상호작용 상태입니다.
* @param onClick (() -> Unit)?: 클릭 시 실행되는 콜백입니다.
*/

/**
* 커스텀 스타일을 적용할 수 있는 Chip 컴포넌트를 생성합니다.
* 기본 스타일 외에 chipDefault를 통한 직접 설정이 가능합니다.
*
* 사용 예시:
* ```kotlin
* WantedActionChip(
*     text = "커스텀 텍스트",
*     chipDefault = customChipDefault,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: 표시할 텍스트입니다.
* @param modifier Modifier: Modifier를 통한 스타��� 지정입니다.
* @param leftIcon Int?: 왼쪽 아이콘 리소스 ID입니다.
* @param rightIcon Int?: 오른쪽 아이콘 리소스 ID입니다.
* @param chipDefault WantedActionChipDefault: 직접 지정한 Chip 스타일입니다.
* @param interactionSource MutableInteractionSource: 클릭 상호작용을 위한 상태 객체입니다.
* @param onClick (() -> Unit)?: 클릭 시 실행될 콜백입니다.
*/

/**
* 좌우 아이콘과 텍스트 Content를 포함한 Chip Layout을 구성합니다.
* 직접 Chip 기본 스타일 및 구성요소를 입력할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedActionChip(
*     content = { Text("Content") },
*     leftIcon = { Icon(...) },
*     rightIcon = { Icon(...) }
* )
* ```
*
* @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
* @param size ActionChipSize: Chip 크기 설정입니다.
* @param variant ActionChipVariant: Chip 스타일 변형입니다.
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @param chipDefault WantedActionChipDefault: Chip 스타일 객체입니다.
* @param interactionSource MutableInteractionSource: 터치 인터랙션 제어용 객체입니다.
* @param content (@Composable () -> Unit): 텍스트 또는 기타 Composable 콘텐츠입니다.
* @param leftIcon (@Composable (() -> Unit)?): 좌측 아이콘 Composable입니다.
* @param rightIcon (@Composable (() -> Unit)?): 우측 아이콘 Composable입니다.
* @param onClick (() -> Unit)?: 클릭 이벤트 콜백입니다.
*/

/**
* object WantedActionChipContract
*
* ActionChip에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
*
* 이 객체는 ActionChip의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
*/

/**
* enum class ActionChipVariant
*
* WantedActionChip의 시각적 스타일을 정의하는 enum 클래스입니다.
*
* 다음과 같은 두 가지 스타일 옵션을 제공합니다:
* - Solid: 배경이 채워진 형태입니다.
* - Outlined: 테두리만 있는 형태입니다.
*
* @see WantedActionChip
*/

/**
* enum class ActionChipSize
*
* WantedActionChip의 크기를 정의하는 enum 클래스입니다.
*
* 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
* 제공되는 크기 옵션은 다음과 같습니다:
* - Large: 큰 사이즈입니다.
* - Medium: 중간 사이즈입니다.
* - Small: 작은 사이즈입니다.
* - XSmall: 가장 작은 사이즈입니다.
*
* @see WantedActionChip
*/

/**
* data class WantedActionChipDefault
*
* WantedActionChip의 기본 스타일을 정의하는 데이터 클래스입니다.
* 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedActionChipDefault(
*     size = ActionChipSize.Medium,
*     variant = ActionChipVariant.Solid,
*     isActive = true,
*     isEnable = true,
*     iconColor = Color.Black,
*     backgroundColor = Color.White,
*     borderColor = Color.Gray,
*     textStyle = TextStyle.Default
* )
* ```
*
* @param size ActionChipSize: Chip의 크기를 설정합니다.
* @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: Chip의 활성화 여부입니다.
* @param isEnable Boolean: Chip의 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
*/

/**
* fun getDefault(...)
*
* 기본 스타일에 맞춰 WantedActionChipDefault 객체를 생성합니다.
* Compose 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedActionChipDefaults.getDefault(
*     size = ActionChipSize.Medium,
*     variant = ActionChipVariant.Solid
* )
* ```
*
* @param size ActionChipSize: Chip의 크기를 설정합니다.
* @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
* @return WantedActionChipDefault: 설정된 WantedActionChipDefault 객체를 반환합니다.
*/

/**
* 필터 Chip 전용으로 아이콘 색상을 반환합니다.
* Chip의 variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
*
* 사용 예시:
* ```kotlin
* val iconColor = WantedActionChipDefaults.getFilterIconColor(
*     variant = ActionChipVariant.Solid,
*     isActive = true
* )
* ```
*
* @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @return Int: 아이콘 색상에 해당하는 리소스 ID를 반환합니다.
*/