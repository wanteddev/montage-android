/**
* 다양한 스타일의 버튼을 생성하는 공통 Compose 함수입니다.
* ButtonVariant에 따라 Solid, Outlined, Text 버튼을 선택하여 렌더링합니다.
*
* 사용 예시:
* ```kotlin
* WantedButton(
*     text = "확인",
*     variant = ButtonVariant.SOLID,
*     type = ButtonType.PRIMARY,
*     size = ButtonSize.LARGE,
*     onClick = { /* 클릭 이벤트 처리 */ }
* )
* ```
*
* @param text String: 버튼에 표시할 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 버튼 외형을 조정합니다.
* @param type ButtonType: 버튼의 타입(PRIMARY, ASSISTIVE)을 지정합니다.
* @param size ButtonSize: 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
* @param variant ButtonVariant: 버튼의 형태(SOLID, OUTLINED, TEXT)를 지정합니다.
* @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
* @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
* @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* WantedButton을 더 세밀하게 제어하기 위한 Compose 함수입니다.
* WantedButtonDefault를 직접 주입하여 스타일과 상태를 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedButton(
*     text = "삭제",
*     buttonDefault = WantedButtonDefaults.getDefault(ButtonVariant.OUTLINED),
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: 버튼에 표시할 텍스트입니다.
* @param modifier Modifier: Modifier를 통해 버튼 외형을 조정합니다.
* @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
* @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param buttonDefault WantedButtonDefault: 버튼 기본 스타일 설정 객체입니다.
* @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* data class WantedButtonDefault
*
* 버튼의 스타일 및 속성을 정의한 데이터 클래스입니다.
* 각 버튼의 모든 시각적 속성과 상태를 개별적으로 설정할 수 있습니다.
*
* @param variant ButtonVariant: 버튼의 변형 타입입니다 (SOLID, OUTLINED, TEXT 등).
* @param type ButtonType: 버튼의 종류입니다 (PRIMARY, ASSISTIVE 등).
* @param enabled Boolean: 버튼의 활성화 여부입니다.
* @param size ButtonSize: 버튼의 크기입니다 (LARGE, MEDIUM, SMALL 등).
* @param contentColor Color: 버튼 콘텐츠의 색상입니다.
* @param leftIconTintColor Color: 왼쪽 아이콘의 색상입니다.
* @param rightIconTintColor Color: 오른쪽 아이콘의 색상입니다.
* @param backgroundColor Color: 버튼의 배경 색상입니다.
* @param borderColor Color: 버튼의 테두리 색상입니다.
* @param borderShape RoundedCornerShape: 버튼의 테두리 형태입니다.
* @param textStyle TextStyle: 버튼 텍스트의 스타일입니다.
* @param loadingSize Dp: 로딩 인디케이터의 크기입니다.
* @param loadingColor Color: 로딩 인디케이터의 색상입니다.
*/

/**
* object WantedButtonDefaults
*
* WantedButtonDefault의 기본값을 제공하는 객체입니다.
* Variant, Type, Size에 따라 적절한 스타일을 자동으로 설정합니다.
*/

/**
* fun getDefault(...)
*
* WantedButtonDefault의 기본 설정을 생성합니다.
*
* 버튼의 Variant, Type, Size에 따라 콘텐츠 색상, 배경 색상, 테두리 등의 스타일을 자동으로 설정합니다.
* 각 속성을 개별적으로 커스터마이징할 수도 있습니다.
*
* 사용 예시:
* ```kotlin
* val config = WantedButtonDefaults.getDefault(
*     variant = ButtonVariant.SOLID,
*     type = ButtonType.PRIMARY,
*     size = ButtonSize.LARGE
* )
* ```
*
* @param variant ButtonVariant: 버튼의 변형 타입입니다. 기본값은 ButtonVariant.SOLID입니다.
* @param type ButtonType: 버튼의 종류입니다. 기본값은 ButtonType.PRIMARY입니다.
* @param enabled Boolean: 버튼의 활성화 여부입니다. 기본값은 true입니다.
* @param size ButtonSize: 버튼의 크기입니다. 기본값은 ButtonSize.LARGE입니다.
* @param contentColor Color: 버튼 콘텐츠의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
* @param leftIconTintColor Color: 왼쪽 아이콘의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
* @param rightIconTintColor Color: 오른쪽 아이콘의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
* @param backgroundColor Color: 버튼의 배경 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
* @param borderColor Color: 버튼의 테두리 색상입니다. variant에 따라 자동 설정됩니다.
* @param borderShape RoundedCornerShape: 버튼의 테두리 형태입니다. variant, size에 따라 자동 설정됩니다.
* @param textStyle TextStyle: 버튼 텍스트의 스타일입니다. variant, type, size에 따라 자동 설정됩니다.
* @param loadingSize Dp: 로딩 인디케이터의 크기입니다. size에 따라 자동 설정됩니다.
* @param loadingColor Color: 로딩 인디케이터의 색상입니다. variant, type, enabled에 따라 자동 설정됩니다.
* @return WantedButtonDefault: 설정된 WantedButtonDefault 인스턴스를 반환합니다.
*/