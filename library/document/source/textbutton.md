/**
* WantedTextButton
*
* Text 형태의 버튼을 생성하는 Compose 함수입니다.
*
* 사용 예시:
* ```kotlin
* WantedTextButton(
*     text = "확인",
*     color = WantedTextButtonColor.PRIMARY,
*     size = WantedTextButtonSize.MEDIUM,
*     onClick = { /* 클릭 이벤트 처리 */ }
* )
* ```
*
* @param text String: 버튼에 표시할 텍스트입니다.
* @param modifier Modifier: 버튼 외형을 조정하는 Modifier입니다.
* @param color WantedTextButtonColor: 버튼의 색(PRIMARY, ASSISTIVE)을 지정합니다.
* @param size WantedTextButtonSize: 버튼의 크기(SMALL, MEDIUM)를 지정합니다. LARGE/XSMALL은 공식 미지원(레거시)입니다.
* @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
* @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
* @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
* @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
* @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
* @param buttonDefault WantedTextButtonDefault: 버튼의 기본 스타일 설정입니다. color/size 축으로 표현되지 않는 색·타이포가 필요할 때만 contentColor/textStyle을 덮어 전달합니다.
*/

/**
* enum class WantedTextButtonColor
*
* [WantedTextButton]의 색 축입니다.
*
* Figma Text Button 컴포넌트의 `color` 속성과 1:1로 대응하며 PRIMARY / ASSISTIVE 두 가지만 존재합니다.
* Foreground/Neutral/Secondary 처럼 두 값으로 표현되지 않는 색은 축을 늘리지 않고
* [WantedTextButtonDefaults.getDefault]의 `contentColor`로 덮어 사용합니다.
*
* - PRIMARY: 브랜드 색(foregroundBrandPrimary)을 사용하는 기본 색입니다.
* - ASSISTIVE: 보조 색(foregroundNeutralTertiary)을 사용합니다.
*/

/**
* data class WantedTextButtonDefault
*
* [WantedTextButton]의 스타일을 정의한 데이터 클래스입니다.
*
* SOLID/OUTLINED와 공유하던 `WantedButtonDefault`와 달리 배경·테두리처럼 Text Button에 없는 속성을 갖지 않으며,
* 폐기된 `ButtonVariant`에도 의존하지 않습니다.
*
* @param color WantedTextButtonColor: 버튼의 색 축입니다.
* @param size WantedTextButtonSize: 버튼의 크기 축입니다.
* @param enabled Boolean: 버튼의 활성화 여부입니다.
* @param contentColor Color: 텍스트 색상입니다.
* @param leadingIconTintColor Color: 왼쪽 아이콘의 색상입니다.
* @param trailingIconTintColor Color: 오른쪽 아이콘의 색상입니다.
* @param textStyle TextStyle: 텍스트의 타이포그래피입니다.
* @param rippleColor Color: 터치 영역 리플의 색상입니다.
* @param loadingColor Color: 로딩 인디케이터의 색상입니다.
*/

/**
* object WantedTextButtonDefaults
*
* [WantedTextButtonDefault]의 기본값을 제공하는 객체입니다.
*
* Figma Text Button 컴포넌트가 정의한 `color` x `size` 축만으로 기본 스타일을 결정하고,
* 그 조합으로 표현되지 않는 색·타이포는 `contentColor` / `textStyle` 인자로 덮어 사용합니다.
* (Figma의 customize 속성 = contentColor, typography)
*/

/**
* fun getDefault(...)
*
* [WantedTextButtonDefault]의 기본 설정을 생성합니다.
*
* 사용 예시:
* ```kotlin
* val config = WantedTextButtonDefaults.getDefault(
*     color = WantedTextButtonColor.ASSISTIVE,
*     size = WantedTextButtonSize.SMALL
* )
* ```
*
* `enabled = false`일 때 텍스트·아이콘 색은 Figma 스펙상 고정이므로 호출부가 덮은 색보다 우선합니다.
* 덕분에 색을 덮는 호출부가 disabled 분기를 따로 들고 있지 않아도 됩니다.
*
* @param color WantedTextButtonColor: 버튼의 색 축입니다. 기본값은 PRIMARY입니다.
* @param size WantedTextButtonSize: 버튼의 크기 축입니다. 기본값은 MEDIUM입니다.
* @param enabled Boolean: 버튼의 활성화 여부입니다. 기본값은 true입니다.
* @param contentColor Color: 텍스트 색상입니다. color, enabled에 따라 자동 설정됩니다.
* @param leadingIconTintColor Color: 왼쪽 아이콘의 색상입니다. 기본값은 contentColor입니다.
* @param trailingIconTintColor Color: 오른쪽 아이콘의 색상입니다. 기본값은 contentColor입니다.
* @param textStyle TextStyle: 텍스트의 타이포그래피입니다. size에 따라 자동 설정됩니다.
* @param rippleColor Color: 터치 영역 리플의 색상입니다. color, contentColor에 따라 자동 설정됩니다.
* @param loadingSize Dp: 로딩 인디케이터의 크기입니다. size에 따라 자동 설정됩니다.
* @param loadingColor Color: 로딩 인디케이터의 색상입니다. color, contentColor에 따라 자동 설정됩니다.
* @return WantedTextButtonDefault: 설정된 WantedTextButtonDefault 인스턴스를 반환합니다.
*/

/**
* enum class WantedTextButtonSize
*
* [WantedTextButton]의 크기 축입니다.
*
* Figma Text Button 컴포넌트의 `size` 속성은 SMALL / MEDIUM 두 가지이며, 신규 코드는 이 둘만 사용합니다.
* LARGE / XSMALL은 [com.wanted.android.wanted.design.util.ButtonVariant.TEXT] 경유 호출부의
* 기존 렌더링을 유지하기 위한 레거시 값이므로 신규 사용을 금지합니다.
*
* - SMALL: 최소 높이 28dp · label1Bold 입니다.
* - MEDIUM: 최소 높이 32dp · body1Bold 입니다.
* - LARGE: 공식 미지원(레거시). 최소 높이 40dp · body1Bold 입니다.
* - XSMALL: 공식 미지원(레거시). 최소 높이 28dp · label1Bold 이며 아이콘 크기만 SMALL과 다릅니다.
*/