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