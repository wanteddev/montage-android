/**
* 하단에 위치한 액션 버튼 영역을 생성합니다.
*
* positive, negative, neutral 텍스트를 통해 버튼을 생성하며, 각 클릭 콜백도 함께 전달합니다.
* variant 영역을 통해 상단에 부가 요소를 렌더링할 수 있습니다.
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
* @param type 액션 영역의 타입을 설정합니다.
* @param positive 메인(긍정) 액션 버튼 텍스트입니다.
* @param onClickPositive 메인 액션 버튼 클릭 콜백입니다.
* @param negative 서브(부정) 액션 버튼 텍스트입니다.
* @param neutral 추가(중립) 액션 버튼 텍스트입니다.
* @param caption 액션 영역 상단에 표시할 캡션입니다.
* @param scrollableState 스크롤 가능한 경우 상태를 전달합니다.
* @param modifier Modifier를 설정합니다.
* @param background 배경 그라데이션 표시 여부를 지정합니다.
* @param safeArea SafeArea를 적용할지 여부를 지정합니다.
* @param gradationColor 배경 그라데이션 색상을 설정합니다.
* @param onClickNegative 서브 액션 버튼 클릭 콜백입니다.
* @param onClickNeutral 추가 액션 버튼 클릭 콜백입니다.
* @param variant 추가적으로 표시할 컴포넌트입니다.
*/

/**
* Slot을 활용하여 커스텀 버튼을 직접 전달하는 방식의 액션 영역입니다.
* 버튼 스타일, 배치 등을 완전히 제어할 수 있습니다.
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
* @param type 액션 영역의 타입을 설정합니다.
* @param safeArea SafeArea를 적용할지 여부를 지정합니다.
* @param background 배경 그라데이션 표시 여부를 지정합니다.
* @param gradationColor 배경 그라데이션 색상을 설정합니다.
* @param caption 액션 영역 상단에 표시할 캡션입니다.
* @param scrollableState 스크롤 가능한 경우 상태를 전달합니다.
* @param modifier Modifier를 설정합니다.
* @param positive 메인(긍정) 액션 버튼 Slot입니다.
* @param negative 서브(부정) 액션 버튼 Slot입니다.
* @param neutral 추가(중립) 액션 버튼 Slot입니다.
* @param variant 추가적으로 표시할 컴포넌트입니다.
*/

/**
* ActionAreaType
*
* 액션 영역의 타입을 정의하는 Enum 클래스입니다.
* Strong, Neutral, Cancel 세 가지 타입이 존재합니다.
*/

/**
* WantedActionAreaDefault
*
* ActionArea에 필요한 버튼 기본 스타일을 묶어둔 데이터 클래스입니다.
* 각 버튼의 WantedButtonDefault를 개별 설정할 수 있습니다.
*
* @param type 액션 영역 타입
* @param positiveButtonDefault 메인 액션 버튼 스타일 설정
* @param negativeButtonDefault 서브 액션 버튼 스타일 설정
* @param neutralButtonDefault 추가 액션 버튼 스타일 설정
*/