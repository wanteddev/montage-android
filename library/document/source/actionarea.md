/**
* WantedActionArea
*
* 하단 액션 버튼 영역을 생성합니다.
*
* 버튼은 main, alternative, sub 텍스트로 생성하며, 각 버튼에 클릭 콜백을 전달할 수 있습니다.
* 또한, Variant 속성을 활용하여 상단 영역에 부가적인 요소를 렌더링할 수 있습니다.
*
* 사용 예시 :
* ```kotlin
* WantedActionArea(
*     type = ActionAreaType.Strong,
*     main = "확인",
*     onClickMain = { /* 처리 */ },
*     alternative = "취소",
*     onClickAlternative = { /* 처리 */ },
*     sub = "건너뛰기",
*     onClickSub = { /* 처리 */ }
* )
* ```
*
* @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
* @param main String: 메인 액션 버튼의 텍스트입니다.
* @param modifier Modifier: Modifier를 설정합니다.
* @param isEnableMain Boolean: 메인 액션 버튼의 활성화 여부입니다.
* @param onClickMain () -> Unit: 메인 액션 버튼 클릭 콜백입니다.
* @param alternative String?: 대체 액션 버튼의 텍스트입니다.
* @param isEnableAlternative Boolean: 대체 액션 버튼의 활성화 여부입니다.
* @param sub String?: 보조 액션 버튼의 텍스트입니다.
* @param isEnableSub Boolean: 보조 액션 버튼의 활성화 여부입니다.
* @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
* @param captionIcon Int?: 캡션 텍스트 앞에 표시할 아이콘 리소스입니다. 기본값은 아이콘 없음이며, 권장 아이콘은 [WantedActionAreaDefaults.CAPTION_ICON]입니다.
* @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
* @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
* @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
* @param divider Boolean: 구분선 표시 여부를 지정합니다. extra가 있을 때만 표시됩니다.
* @param backgroundColor Color: Extra·버튼 영역의 배경색이자 sticky 그라데이션의 색상입니다. 그라데이션이 꺼져 있고 extra 도 없으면 배경을 칠하지 않습니다.
* @param onClickAlternative (() -> Unit)?: 대체 액션 버튼 클릭 콜백입니다.
* @param onClickSub (() -> Unit)?: 보조 액션 버튼 클릭 콜백입니다.
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
*     main = {
*         CustomMainButton(onClick = { ... })
*     },
*     alternative = {
*         CustomSecondaryButton(onClick = { ... })
*     }
* )
* ```
*
* @param modifier Modifier: Modifier를 설정합니다.
* @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
* @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
* @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
* @param backgroundColor Color: Extra·버튼 영역의 배경색이자 sticky 그라데이션의 색상입니다. 그라데이션이 꺼져 있고 extra 도 없으면 배경을 칠하지 않습니다.
* @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
* @param captionIcon Int?: 캡션 텍스트 앞에 표시할 아이콘 리소스입니다. 기본값은 아이콘 없음이며, 권장 아이콘은 [WantedActionAreaDefaults.CAPTION_ICON]입니다.
* @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
* @param divider Boolean: 구분선 표시 여부를 지정합니다. extra가 있을 때만 표시됩니다.
* @param main (@Composable () -> Unit): 메인 액션 버튼 Slot입니다.
* @param alternative (@Composable (() -> Unit)?): 대체 액션 버튼 Slot입니다.
* @param sub (@Composable (() -> Unit)?): 보조 액션 버튼 Slot입니다.
* @param extra (@Composable (() -> Unit)?): 추가적으로 표시할 컴포넌트입니다.
*/

/**
* Modifier.actionAreaBackground
*
* Extra 영역과 버튼 영역에 배경색을 칠합니다. 그라디언트가 꺼져 있고 Extra 도 없을 때만 배경을 걷어
* 페이지 배경이 그대로 비치게 둡니다(iOS 와 동일).
*
* 배경을 칠하지 않으면 다크모드에서 Extra 영역이 페이지 배경색으로 보이고,
* sticky 일 때 그라디언트 끝에 경계선이 생깁니다.
*
* @param background Boolean: 배경 그라데이션 표시 여부입니다.
* @param hasExtra Boolean: Extra 슬롯이 있는지 여부입니다.
* @param backgroundColor Color: 칠할 배경색입니다.
* @return Modifier: 배경이 적용된 Modifier 입니다.
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
* @property mainButtonDefault WantedButtonDefault: 메인 액션 버튼 스타일을 설정합니다.
* @property alternativeButtonDefault WantedButtonDefault: 대체 액션 버튼 스타일을 설정합니다.
* @property subButtonDefault WantedButtonDefault: 보조 액션 버튼 스타일을 설정합니다.
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
*
* @property CAPTION_ICON Int: 캡션 아이콘을 사용할 때 권장되는 기본 아이콘(`@DrawableRes`)입니다. 캡션 아이콘은 기본적으로 표시되지 않으며, 아이콘이 필요할 때 `WantedActionArea` 의 `captionIcon` 에 이 값을 전달합니다.
*/

/**
* fun getDefault(...)
*
* WantedActionAreaDefault의 기본 설정을 생성합니다.
*
* 액션 영역 타입에 따라 main, alternative, sub 버튼의 기본 스타일을 자동으로 설정합니다.
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
* @param mainButtonDefault WantedButtonDefault: 메인 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @param alternativeButtonDefault WantedButtonDefault: 대체 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @param subButtonDefault WantedButtonDefault: 보조 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
* @return WantedActionAreaDefault: 설정된 WantedActionAreaDefault 인스턴스를 반환합니다.
*
* @see WantedActionAreaDefault
* @see ActionAreaType
*/