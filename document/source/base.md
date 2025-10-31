/**
* fun String.accent(...)
*
* 문자열 내에서 특정 부분 텍스트를 강조하여 AnnotatedString을 생성합니다.
*
* 전체 문자열에서 subText를 찾아 지정된 textStyle을 적용합니다.
* 동일한 subText가 여러 번 나타나면 모두 강조됩니다.
*
* 사용 예시:
* ```kotlin
* "안녕하세요 홍길동님".accent(
*     subText = "홍길동",
*     textStyle = TextStyle(color = Color.Blue)
* )
* ```
*
* @param subText String: 강조할 부분 텍스트입니다.
* @param textStyle TextStyle: 강조 부분에 적용할 텍스트 스타일입니다.
* @return AnnotatedString: 강조 스타일이 적용된 AnnotatedString을 반환합니다.
*/

/**
* fun String.accent(...)
*
* 문자열 내에서 여러 부분 텍스트를 강조하여 AnnotatedString을 생성합니다.
*
* 전체 문자열에서 여러 개의 subText를 찾아 지정된 textStyle을 적용합니다.
* 각 subText가 여러 번 나타나면 모두 강조됩니다.
*
* 사용 예시:
* ```kotlin
* "안녕하세요 홍길동님, 반갑습니다 홍길동님".accent(
*     "홍길동", "반갑습니다",
*     textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
* )
* ```
*
* @param subText vararg String: 강조할 여러 부분 텍스트들입니다.
* @param textStyle TextStyle: 강조 부분에 적용할 텍스트 스타일입니다.
* @return AnnotatedString: 강조 스타일이 적용된 AnnotatedString을 반환합니다.
*/

/**
* fun Modifier.getBorderModifier(...)
*
* 컴포넌트에 테두리 스타일을 적용하는 Modifier 확장 함수입니다.
*
* 원형 또는 둥근 모서리 형태에 대해 테두리 없음, 외곽선, 내부선 타입을 지원합니다.
* 테두리 타입에 따라 적절한 Modifier 체인을 반환합니다.
*
* 사용 예시:
* ```kotlin
* Modifier.getBorderModifier(
*     size = 40.dp,
*     isCircleShape = true,
*     borderType = BorderType.OutLine,
*     borderWidth = 2.dp,
*     borderColor = Color.Gray
* )
* ```
*
* @param size Dp: 컴포넌트의 크기입니다.
* @param isCircleShape Boolean: 원형 여부입니다. true면 원형, false면 둥근 모서리입니다.
* @param borderType BorderType: 테두리 타입입니다 (None, OutLine, InnerLine).
* @param cornerRadius Dp: 모서리 반경입니다. 기본값은 0.dp입니다.
* @param borderWidth Dp: 테두리 두께입니다. 기본값은 1.dp입니다.
* @param borderColor Color: 테두리 색상입니다.
* @param backgroundColor Color: 배경 색상입니다.
* @return Modifier: 테두리 스타일이 적용된 Modifier를 반환합니다.
*/

/**
* enum class BorderType
*
* 컴포넌트의 테두리 스타일 타입을 정의하는 enum 클래스입니다.
*
* 다음과 같은 세 가지 타입을 제공합니다:
* - None: 테두리가 없는 형태입니다.
* - InnerLine: 내부에 그려지는 테두리입니다.
* - OutLine: 외부로 확장되는 테두리입니다.
*/

/**
* fun Modifier.wantedDropShadow(...)
*
* 컴포넌트에 드롭 섀도우 효과를 적용하는 Modifier 확장 함수입니다.
*
* WantedShadowStyle을 사용하여 미리 정의된 섀도우 스타일을 적용합니다.
* 배경색과 테두리 반경을 함께 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* Box(
*     Modifier
*         .size(100.dp)
*         .wantedDropShadow(WantedShadowStyle.Medium())
* )
* ```
*
* @param style WantedShadowStyle: 적용할 섀도우 스타일입니다.
* @return Modifier: 드롭 섀도우가 적용된 Modifier를 반환합니다.
*/

/**
* fun Modifier.wantedDropShadowSpread(...)
*
* 컴포넌트에 확산형 드롭 섀도우 효과를 적용하는 Modifier 확장 함수입니다.
*
* WantedShadowSpreadStyle을 사용하여 확산형 섀도우 스타일을 적용합니다.
* 일반 드롭 섀도우보다 더 넓게 퍼지는 효과를 제공합니다.
*
* 사용 예시:
* ```kotlin
* Box(
*     Modifier
*         .size(100.dp)
*         .wantedDropShadowSpread(WantedShadowSpreadStyle.Medium())
* )
* ```
*
* @param style WantedShadowSpreadStyle: 적용할 확산형 섀도우 스타일입니다.
* @return Modifier: 확산형 드롭 섀도우가 적용된 Modifier를 반환합니다.
*/

/**
* sealed class WantedShadowStyle
*
* 드롭 섀도우의 기본 스타일을 정의하는 sealed 클래스입니다.
* 다양한 크기의 섀도우 프리셋을 제공합니다.
*
* 제공되는 스타일:
* - XSmall: 가장 작은 섀도우입니다.
* - Small: 작은 섀도우입니다.
* - Medium: 중간 크기의 섀도우입니다.
* - Large: 큰 섀도우입니다.
* - XLarge: 가장 큰 섀도우입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다.
* @param backgroundColor Color: 배경 색상입니다.
*/

/**
* abstract fun getShadow()
*
* 섀도우 토큰을 반환하는 함수입니다.
* 하위 클래스에서 구현해야 합니다.
*
* @return List<WantedShadowToken>: 섀도우 토큰의 리스트입니다.
*/

/**
* data class XSmall
*
* XSmall 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class Small
*
* Small 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class Medium
*
* Medium 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class Large
*
* Large 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class XLarge
*
* XLarge 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* sealed class WantedShadowSpreadStyle
*
* 확산형 드롭 섀도우의 스타일을 정의하는 sealed 클래스입니다.
* 일반 드롭 섀도우보다 더 넓게 퍼지는 효과를 제공합니다.
*
* 제공되는 스타일:
* - Small: 작은 확산형 섀도우입니다.
* - Medium: 중간 크기의 확산형 섀도우입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다.
* @param backgroundColor Color: 배경 색상입니다.
*/

/**
* abstract fun getShadow()
*
* 섀도우 토큰을 반환하는 함수입니다.
* 하위 클래스에서 구현해야 합니다.
*
* @return List<WantedShadowToken>: 섀도우 토큰의 리스트입니다.
*/

/**
* data class Small
*
* Small 확산형 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class Medium
*
* Medium 확산형 섀도우 스타일을 반환하는 데이터 클래스입니다.
*
* @param borderRadius Dp: 테두리의 반경입니다. 기본값은 12.dp입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 Color.White입니다.
*/

/**
* data class WantedShadowToken
*
* 섀도우의 개별 속성을 정의하는 데이터 클래스입니다.
* 섀도우의 위치, 크기, 색상 등을 설정할 수 있습니다.
*
* @param offsetX Dp: 섀도우의 수평 오프셋입니다.
* @param offsetY Dp: 섀도우의 수직 오프셋입니다.
* @param blurRadius Dp: 섀도우의 블러 반경입니다.
* @param spreadRadius Dp: 섀도우의 확산 반경입니다.
* @param color Color: 섀도우의 색상입니다.
*/

/**
* 블러 효과를 사용한 드롭 섀도우를 생성하는 컴포저블입니다.
*
* 간단한 블러 효과로 섀도우를 구현하며, 커스텀 Shape를 지원합니다.
* 배경색, 섀도우 색상, 블러 정도를 개별적으로 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedDropShadow(
*     modifier = Modifier.size(100.dp),
*     background = Color.White,
*     dropShadowColor = Color.Black.copy(0.1f),
*     blur = 2.dp,
*     shape = RoundedCornerShape(12.dp)
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param background Color: 배경 색상입니다.
* @param dropShadowColor Color: 드롭 섀도우의 색상입니다.
* @param blur Dp: 블러의 정도입니다.
* @param shape Shape: 컴포넌트의 형태입니다.
*/

/**
* 터치 영역을 확장하고 클릭 가능하도록 만드는 래퍼 컴포저블입니다.
*
* 내부 콘텐츠의 사이즈를 계산하여 사용자의 터치 영역을 넓혀주는 목적이며,
* 실제 콘텐츠 주변에 여백(Padding)을 추가하여 UX를 향상시킵니다.
*
* 리플 효과, 클릭 이벤트, 터치 활성화 여부 등을 설정할 수 있으며,
* LocalWantedTouchArea를 활용해 내부 터치 허용 여부를 Composition으로 전달받습니다.
*
* 사용 예시:
* ```kotlin
* WantedTouchArea(
*     horizontalPadding = 16.dp,
*     verticalPadding = 12.dp,
*     shape = RoundedCornerShape(6.dp),
*     onClick = { /* 클릭 이벤트 */ }
* ) {
*     Icon(painter = painterResource(id = R.drawable.ic_example), contentDescription = null)
* }
* ```
*
* @param modifier Modifier: 전체 래퍼의 외형 및 배치를 제어합니다.
* @param verticalPadding Dp: 상하 터치 영역 확장 값입니다.
* @param horizontalPadding Dp: 좌우 터치 영역 확장 값입니다.
* @param shape Shape: 터치 영역의 모양입니다. 기본값은 6dp의 라운드 사각형입니다.
* @param enabled Boolean: 클릭 가능 여부를 설정합니다.
* @param enabledInnerTouch Boolean: 내부 콘텐츠 사이즈 계산 여부입니다. 내부 CompositionLocal에서 기본값을 제공합니다.
* @param rippleColor Color: 리플 효과의 색상입니다. 기본값은 Unspecified입니다.
* @param isUseRipple Boolean: true일 경우 리플 효과를 사용합니다.
* @param interactionSource MutableInteractionSource: 상호작용 상태 관리를 위한 InteractionSource입니다.
* @param content (@Composable BoxScope.() -> Unit): 실제 표시할 콘텐츠입니다.
* @param onClick (() -> Unit)?: 클릭 이벤트가 발생할 경우 호출되는 콜백입니다. null일 경우 클릭이 비활성화됩니다.
*/

/**
* preview가 아니고 innerTouch가 되어야 할경우만 계산한다.
* view를 계산하게되면 간혹 IllegalStateException 발생
* java.lang.IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is not supported.
*/

layout(width = placeable.width, height = placeable.height) { /* no placement → draw pass에서 skip */ }