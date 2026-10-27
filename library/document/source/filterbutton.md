/**
* WantedFilterButton
*
* 사용 편의성을 위한 Filter button입니다.
* 간단한 설정만으로 기본 스타일의 Filter button을 사용할 수 있으며,
* 내부적으로 CompositionLocal을 활용하여 관련 상태를 주입합니다.
*
* size·variant·isActive·isEnable 을 하나도 넘기지 않으면 Kotlin 오버로드 규칙상 filterButtonDefault 를 받는 오버로드가 선택되어, 크기·스타일이 CompositionLocal 기본값(Medium·Solid)으로 정해집니다. 이 오버로드의 기본값(Small)을 쓰려면 size 등을 명시합니다.
*
* 사용 예시:
* ```kotlin
* WantedFilterButton(
*     text = "필터",
*     activeLabel = "3",
*     size = FilterButtonSize.Small,
*     onClick = { /* 클릭 동작 */ }
* )
* ```
*
* @param text String: FilterButton에 표시될 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
* @param activeLabel String: 본문 텍스트 뒤에 함께 표시할 라벨(예: 선택 개수)입니다. isActive 와 무관하게 비어 있지 않으면 표시됩니다.
* @param size FilterButtonSize: FilterButton의 크기를 정의하는 enum 값입니다 (기본값: Small).
* @param variant FilterButtonVariant: FilterButton의 스타일입니다 (기본값: Solid).
* @param isActive Boolean: FilterButton의 활성화 상태입니다.
* @param isEnable Boolean: FilterButton의 사용 가능 상태입니다.
* @param isExpanded Boolean: 확장 가능 상태입니다 (아이콘 변경 목적).
* @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다. clickOnce 를 쓰는 디자인 시스템 컴포넌트의 직전 클릭(다른 컴포넌트 포함)으로부터 200ms 안의 클릭은 무시됩니다.
*/

/**
* WantedFilterButton
*
* 기본 설정을 외부에서 주입할 수 있는 커스터마이징 버전의 Filter button입니다.
* 기본 스타일뿐만 아니라 텍스트 스타일, 색상 등 세부 속성을 설정할 수 있으며,
* 내부적으로 주어진 FilterButtonDefault 설정값을 기반으로 Filter button을 구성합니다.
*
* 사용 예시:
* ```kotlin
* WantedFilterButton(
*     text = "필터",
*     activeLabel = "3",
*     filterButtonDefault = WantedFilterButtonDefaults.getDefault(size = FilterButtonSize.Small, isActive = true),
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: FilterButton에 표시될 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
* @param activeLabel String: 본문 텍스트 뒤에 함께 표시할 라벨(예: 선택 개수)입니다. isActive 와 무관하게 비어 있지 않으면 표시됩니다.
* @param isExpanded Boolean: FilterButton이 확장 상태인지 여부입니다 (화살표 아이콘에 반영됨).
* @param filterButtonDefault WantedFilterButtonDefault: 외부에서 주입하는 FilterButton의 기본 설정값입니다.
* @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다. clickOnce 를 쓰는 디자인 시스템 컴포넌트의 직전 클릭(다른 컴포넌트 포함)으로부터 200ms 안의 클릭은 무시됩니다.
*/

/**
* object WantedFilterButtonContract
*
* Filter button에 대한 전반적인 계약을 정의하는 객체입니다.
*
* 이 객체는 Filter button의 시각적 변형 및 크기에 대한 enum 클래스를 포함합니다.
*/

    /**
* enum class FilterButtonVariant
*
* WantedFilterButton의 시각적 스타일을 정의하는 enum 클래스입니다.
*
* 다음과 같은 두 가지 스타일 옵션을 제공합니다:
* - Solid: 배경이 채워진 형태입니다.
* - Outlined: 테두리만 있는 형태입니다.
*
* @see WantedFilterButton
*/

    /**
* enum class FilterButtonSize
*
* WantedFilterButton의 크기를 정의하는 enum 클래스입니다.
*
* 각 사이즈는 패딩, 아이콘 크기, 텍스트 간격 등에 영향을 미칩니다.
* 제공되는 크기 옵션은 다음과 같습니다:
* - Large: 큰 사이즈입니다.
* - Medium: 중간 사이즈입니다.
* - Small: 작은 사이즈입니다.
* - XSmall: 가장 작은 사이즈입니다.
*
* @see WantedFilterButton
*/

    /** 텍스트 좌우 패딩입니다. 전 사이즈 공통 2dp 입니다. */

    /** 라벨 ↔ activeLabel 사이 간격입니다. Small·XSmall 만 2dp 입니다. */

/**
* data class WantedFilterButtonDefault
*
* WantedFilterButton의 기본 스타일을 정의하는 데이터 클래스입니다.
* 크기, 스타일 변형, 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedFilterButtonDefault(
*     size = FilterButtonSize.Medium,
*     variant = FilterButtonVariant.Solid,
*     isActive = true,
*     isEnabled = true,
*     iconColor = Color.Black,
*     backgroundColor = Color.White,
*     borderColor = Color.Gray,
*     textStyle = TextStyle.Default
* )
* ```
*
* @param size FilterButtonSize: FilterButton의 크기를 설정합니다.
* @param variant FilterButtonVariant: FilterButton의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: FilterButton의 활성화 여부입니다.
* @param isEnabled Boolean: FilterButton의 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
* @return WantedFilterButtonDefault: 설정된 WantedFilterButtonDefault 객체를 반환합니다.
*/

/**
* object WantedFilterButtonDefaults
* WantedFilterButton의 기본 스타일을 제공하는 객체입니다.
*
*/

    /**
* fun getDefault(...)
* 기본 스타일에 맞춰 WantedFilterButtonDefault 객체를 생성합니다.
* 인자를 생략하면 CompositionLocal 값(LocalWantedFilterButtonSize 기본 Medium, LocalWantedFilterButtonVariant 기본 Solid, LocalWantedFilterButtonActive 기본 false, LocalWantedFilterButtonEnable 기본 true)을 씁니다.
*
* 사용 예시:
* ```kotlin
* val chipDefault = WantedFilterButtonDefaults.getDefault(
*     size = FilterButtonSize.Medium,
*     variant = FilterButtonVariant.Solid
* )
* ```
*
* @param size FilterButtonSize: FilterButton의 크기를 설정합니다.
* @param variant FilterButtonVariant: FilterButton의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @param iconColor Color: 아이콘의 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param textStyle TextStyle: 텍스트 스타일입니다.
* @return WantedFilterButtonDefault: 설정된 WantedFilterButtonDefault 객체를 반환합니다.
*/

    /**
* fun getFilterIconColor(...)
*
* Filter button 전용으로 아이콘 색상을 반환합니다.
* Variant, 활성화 여부, 사용 가능 여부에 따라 색상이 결정됩니다.
*
* 사용 예시:
* ```kotlin
* val iconColor = WantedFilterButtonDefaults.getFilterIconColor(
*     variant = FilterButtonVariant.Solid,
*     isActive = true
* )
* ```
*
* @param variant FilterButtonVariant: FilterButton의 스타일 변형입니다 (Solid, Outlined).
* @param isActive Boolean: 활성화 여부입니다.
* @param isEnable Boolean: 사용 가능 여부입니다.
* @return Int: 아이콘 색상에 해당하는 리소스 ID를 반환합니다.
*/