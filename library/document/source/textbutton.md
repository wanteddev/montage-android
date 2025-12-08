/**
* Text 형태의 버튼을 생성하는 Compose 함수입니다.
*
* 사용 예시:
* ```kotlin
* WantedTextButton(
*     text = "확인",
*     type = ButtonType.PRIMARY,
*     size = ButtonSize.LARGE,
*     onClick = { /* 클릭 이벤트 처리 */ }
* )
* ```
*
* @param text String: 버튼에 표시할 텍스트입니다.
* @param modifier Modifier: 버튼 외형을 조정하는 Modifier입니다.
* @param color ButtonType: 버튼의 타입(PRIMARY, ASSISTIVE)을 지정합니다.
* @param size ButtonSize: 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
* @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
* @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
* @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
* @param buttonDefault: WantedButtonDefault 버튼의 기본 스타일 설정입니다.
*/