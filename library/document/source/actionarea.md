/**
* WantedActionArea
*
* 하단 액션 버튼 영역을 생성합니다.
*
* 버튼은 positive, negative, neutral 텍스트로 생성하며, 각 버튼에 클릭 콜백을 전달할 수 있습니다.
* 또한, Variant 속성을 활용하여 상단 영역에 부가적인 요소를 렌더링할 수 있습니다.
*
* 사용 예시 :
* ```kotlin
* WantedActionArea(
*     type = ActionAreaType.Strong,
*     positive = "확인",
*     onClickPositive = { /* 처리 */ },
*     negative = "취소",
*     onClickNegative = { /* 처리 */ },
*     neutral = "건너뛰기",
*     onClickNeutral = { /* 처리 */ }
* )
* ```
*
* @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
* @param positive String: 메인(긍정) 액션 버튼의 텍스트입니다.
* @param isEnablePositive Boolean: 메인 액션 버튼의 활성화 여부입니다.
* @param onClickPositive () -> Unit: 메인 액션 버튼 클릭 콜백입니다.
* @param negative String?: 서브(부정) 액션 버튼의 텍스트입니다.
* @param isEnableNegative Boolean: 서브 액션 버튼의 활성화 여부입니다.
* @param neutral String?: 추가(중립) 액션 버튼의 텍스트입니다.
* @param isEnableNeutral Boolean: 추가 액션 버튼의 활성화 여부입니다.
* @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
* @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
* @param modifier Modifier: Modifier를 설정합니다.
* @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
* @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
* @param divider Boolean: 구분선 표시 여부를 지정합니다.
* @param gradationColor Color: 배경 그라데이션 색상을 설정합니다.
* @param onClickNegative (() -> Unit)?: 서브 액션 버튼 클릭 콜백입니다.
* @param onClickNeutral (() -> Unit)?: 추가 액션 버튼 클릭 콜백입니다.
* @param extra (@Composable () -> Unit)?: 추가적으로 표시할 컴포넌트입니다.
*/

/**
* WantedActionArea
*
* Slot을 활용하여 커스텀 버튼을 직접 전달할 수 있습니다.
* 버튼의 스타일 및 배치를 완전히 자유롭게 제어할 수 있습니다.
*
* 사용 예시 :
* ```kotlin
* WantedActionArea(
*     type = ActionAreaType.Strong,
*     positive = {
*         CustomMainButton(onClick = { ... })
*     },
*     negative = {
*         CustomSecondaryButton(onClick = { ... })
*     }
* )
* ```
*
* @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
* @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
* @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
* @param gradationColor Color: 배경 그라데이션 색상을 설정합니다.
* @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
* @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
* @param modifier Modifier: Modifier를 설정합니다.
* @param positive (@Composable () -> Unit): 메인(긍정) 액션 버튼 Slot입니다.
* @param negative (@Composable (() -> Unit)?): 서브(부정) 액션 버튼 Slot입니다.
* @param neutral (@Composable (() -> Unit)?): 추가(중립) 액션 버튼 Slot입니다.
* @param extra (@Composable (() -> Unit)?): 추가적으로 표시할 컴포넌트입니다.
*/

/**
* enum class ActionAreaType
*
* 액션 영역의 타입을 정의하는 Enum 클래스입니다.
*
* 액션 영역의 시각적 스타일과 버튼 구성을 결정할 때 사용됩니다. UI 요구사항에 따라 다음의 세 가지 옵션을 제공합니다:
* - Strong: 강조된 액션 영역입니다.
* - Neutral: 중립적인 액션 영역입니다.
* - Cancel: 취소 중심의 액션 영역입니다.
*
* @see WantedActionAreaDefault
*/

/**
* data class WantedActionAreaDefault
*
* ActionArea에 필요한 버튼 기본 스타일을 정의한 데이터 클래스입니다.
*
* 각 버튼의 WantedButtonDefault를 개별적으로 설정할 수 있습니다.
*
* @property type ActionAreaType: 액션 영역 타입입니다.
* @property positiveButtonDefault WantedButtonDefault: 메인 액션 버튼 스타일을 설정합니다.
* @property negativeButtonDefault WantedButtonDefault: 서브 액션 버튼 스타일을 설정합니다.
* @property neutralButtonDefault WantedButtonDefault: 추가 액션 버튼 스타일을 설정합니다.
*
* @see ActionAreaType
* @see WantedButtonDefault
*/

/**
* object WantedActionAreaDefaults
*
* WantedActionAreaDefault의 기본값을 제공하는 객체입니다.
*
* 액션 영역 타입에 따라 적절한 버튼 스타일을 자동으로 설정합니다.
*
* @see WantedActionAreaDefault
* @see ActionAreaType
*/

/**
* fun getDefault(...)
*
* WantedActionAreaDefault의 기본 설정을 생성합니다.
*
* 액션 영역 타입에 따라 positive, negative, neutral 버튼의 기본 스타일을 자동으로 설정합니다.
* 각 버튼의 스타일을 개별적으로 커스터마이징할 수도 있습니다.
*
* 사용 예시:
* ```kotlin
* val config = WantedActionAreaDefaults.getDefault(
*     type = ActionAreaType.Strong
* )
* ```
*
* @param type ActionAreaType: 액션 영역의 타입입니다. 기본값은 ActionAreaType.Strong입니다.
* @param positiveButtonDefault WantedButtonDefault: 메인 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @param negativeButtonDefault WantedButtonDefault: 서브 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @param neutralButtonDefault WantedButtonDefault: 추가 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @return WantedActionAreaDefault: 설정된 WantedActionAreaDefault 인스턴스를 반환합니다.
*
* @see WantedActionAreaDefault
* @see ActionAreaType
*/