/**
* 다양한 스타일의 버튼을 생성하는 공통 Compose 함수입니다.
* ButtonShape에 따라 Solid, Outlined, Text 버튼을 선택하여 렌더링합니다.
*
* 사용 예시:
* ```kotlin
* WantedButton(
*     text = "확인",
*     buttonShape = ButtonShape.SOLID,
*     type = ButtonType.PRIMARY,
*     size = ButtonSize.LARGE,
*     onClick = { /* 클릭 이벤트 처리 */ }
* )
* ```
*
* @param text 버튼에 표시할 텍스트입니다.울라 ㅇㅜㄹ라
* @param modifier Modifier를 통해 버튼 외형을 조정합니다.
* @param buttonShape 버튼의 형태(SOLID, OUTLINED, TEXT)를 지정합니다.
* @param type 버튼의 타입(PRIMARY, SECONDARY, ASSISTIVE)을 지정합니다.
* @param size 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
* @param enabled 버튼 활성화 여부를 지정합니다.
* @param isLoading 로딩 상태를 표시할지 여부입니다.
* @param leadingDrawable 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param onClick 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* WantedButton을 더 세밀하게 제어하기 위한 Compose 함수입니다.
* ButtonDefault를 직접 주입하여 스타일과 상태를 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedButton(
*     text = "삭제",
*     buttonDefault = WantedButtonDefaults.getDefault(ButtonShape.OUTLINED),
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param modifier Modifier를 통해 버튼 외형을 조정합니다.
* @param text 버튼에 표시할 텍스트입니다.
* @param isLoading 로딩 상태를 표시할지 여부입니다.
* @param buttonDefault 버튼 기본 스타일 설정 객체입니다.
* @param leadingDrawable 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param onClick 버튼 클릭 시 호출되는 콜백입니다.
*/