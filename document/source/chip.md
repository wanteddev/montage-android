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
* @param text 표시할 텍스트입니다.
* @param size Chip의 크기를 설정합니다.
* @param variant Chip의 스타일 변형입니다. (Solid, Outlined)
* @param isActive 선택 여부 상태입니다.
* @param isEnable 사용 가능 여부입니다.
* @param leftIcon 왼쪽에 표시할 아이콘 리소스 ID입니다.
* @param rightIcon 오른쪽에 표시할 아이콘 리소스 ID입니다.
* @param modifier Modifier를 통한 외형 스타일 지정입니다.
* @param interactionSource 클릭 시의 상호작용 상태입니다.
* @param onClick 클릭 시 실행되는 콜백입니다.
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
* @param text 표시할 텍스트입니다.
* @param leftIcon 왼쪽 아이콘 리소스 ID입니다. nullable
* @param rightIcon 오른쪽 아이콘 리소스 ID입니다. nullable
* @param chipDefault 직접 지정한 Chip 스타일입니다.
* @param interactionSource 클릭 상호작용을 위한 상태 객체입니다.
* @param modifier Modifier를 통한 스타일 지정입니다.
* @param onClick 클릭 시 실행될 콜백입니다.
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
* @param size Chip 크기 설정입니다.
* @param variant Chip 스타일 변형입니다.
* @param isActive 활성화 여부입니다.
* @param isEnable 사용 가능 여부입니다.
* @param chipDefault Chip 스타일 객체입니다.
* @param interactionSource 터치 인터랙션 제어용 객체입니다.
* @param leftIcon 좌측 아이콘 Composable입니다.
* @param content 텍스트 또는 기타 Composable 콘텐츠입니다.
* @param rightIcon 우측 아이콘 Composable입니다.
* @param modifier Modifier를 통한 외형 스타일 지정입니다.
* @param onClick 클릭 이벤트 콜백입니다.
*/

/**
* WantedActionChip 기본 스타일을 정의하는 데이터 클래스입니다.
* 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedChipDefault(
*     size = ChipActionSize.Medium,
*     variant = ChipActionVariant.Solid,
*     isActive = true,
*     isEnable = true,
*     iconColor = Color.Black,
*     backgroundColor = Color.White,
*     borderColor = Color.Gray,
*     textStyle = TextStyle.Default
* )
* ```
*
* @param size Chip 크기 설정
* @param variant Chip 스타일 변형 (Solid, Outlined)
* @param isActive Chip 활성화 여부
* @param isEnable Chip 사용 가능 여부
* @param iconColor 아이콘 색상
* @param backgroundColor 배경 색상
* @param borderColor 테두리 색상
* @param textStyle 텍스트 스타일
*/

/**
* 기본 스타일에 맞춰 WantedChipDefault 객체를 생성합니다.
* 컴포즈 환경에 따라 Chip의 스타일을 동적으로 결정합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedChipDefaults.getDefault()
* ```
*
* @param size Chip 크기 설정
* @param variant Chip 스타일 변형 (Solid, Outlined)
* @param isActive 활성화 여부
* @param isEnable 사용 가능 여부
* @param iconColor 아이콘 색상
* @param backgroundColor 배경 색상
* @param borderColor 테두리 색상
* @param textStyle 텍스트 스타일
* @return 설정된 WantedChipDefault 객체 반환
*/

/**
* 필터 Chip 전용으로 아이콘 색상을 반환합니다.
* Chip의 variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
*
* 사용 예시:
* ```kotlin
* val iconColor = WantedChipDefaults.getFilterIconColor()
* ```
*
* @param variant Chip 스타일 변형 (Solid, Outlined)
* @param isActive 활성화 여부
* @param isEnable 사용 가능 여부
* @return 아이콘 색상에 해당하는 리소스 ID 반환
*/

/**
* object WantedActionContract
*
* ChipAction에 대한 전반적인 계약(Contract)을 정의하는 객체입니다.
*
* 이 객체는 ChipAction의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
*/

/**
* enum class ChipActionVariant
*
* ChipAction의 시각적 스타일을 정의하는 enum 클래스입니다.
*
* 다음과 같은 두 가지 스타일 옵션을 제공합니다:
* - Solid: 배경이 채워진 형태
* - Outlined: 테두리만 있는 형태
*
* @see WantedActionChip
*/

/**
* enum class ChipActionSize
*
* ChipAction의 크기를 정의하는 enum 클래스입니다.
*
* 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
* 제공되는 크기 옵션은 다음과 같습니다:
* - Large: 큰 사이즈
* - Medium: 중간 사이즈
* - Small: 작은 사이즈
* - XSmall: 가장 작은 사이즈
*
* @see WantedActionChip
*/

/**
* 사용 편의성을 위한 필터 Chip 컴포저블입니다.
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
* @param text: Chip에 표시될 텍스트
* @param modifier: Modifier를 통해 스타일을 조정할 수 있음
* @param activeLabel: Chip이 활성화되었을 때 표시할 라벨 텍스트
* @param size: Chip의 크기를 정의하는 enum 값 (기본값: Small)
* @param variant: Chip의 스타일 (기본값: Solid)
* @param isActive: Chip의 활성화 상태
* @param isEnable: Chip의 사용 가능 상태
* @param isExpend: 확장 가능 상태 (아이콘 변경 목적)
* @param interactionSource: 사용자 인터랙션 처리를 위한 객체
* @param onClick: 클릭 시 호출되는 콜백 함수
*/

/**
* Chip 기본 설정을 외부에서 주입할 수 있는 커스터마이징 버전의 필터 Chip 컴포저블입니다.
* 기본 스타일뿐만 아니라 텍스트 스타일, 색상 등 세부 속성을 설정 가능하며
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
* @param text: Chip에 표시될 텍스트
* @param modifier: Modifier를 통해 스타일을 조정할 수 있음
* @param activeLabel: Chip이 활성화되었을 때 표시할 라벨 텍스트
* @param isExpanded: Chip이 확장 상태인지 여부 (화살표 아이콘에 반영됨)
* @param chipDefault: 외부에서 주입하는 Chip의 기본 설정값
* @param interactionSource: 사용자 인터랙션 처리를 위한 객체
* @param onClick: 클릭 시 호출되는 콜백 함수
*/