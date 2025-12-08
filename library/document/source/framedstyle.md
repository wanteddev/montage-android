/**
* fun Modifier.framedStyle(...)
*
* 프레임 스타일을 적용하는 Modifier 확장 함수입니다.
*
* 테두리, 섀도우, 모서리 둥글기 등을 적용하여 카드나 입력 필드 등에 프레임 스타일을 부여합니다.
* 상태(Normal, Negative, Selected)에 따라 다른 색상과 테두리 두께가 적용됩니다.
*
* 사용 예시:
* ```
* Box(
*     modifier = Modifier
*         .size(100.dp)
*         .framedStyle(
*             status = WantedFramedStyleStatus.Selected,
*             enabled = true
*         )
* )
* ```
*
* @param status WantedFramedStyleStatus 프레임 상태입니다. Normal, Negative, Selected 중 하나를 선택합니다.
* @param shape RoundedCornerShape 모서리 둥글기 형태입니다. 기본값은 12dp입니다.
* @param enabled Boolean 활성화 여부입니다. false일 경우 불투명도가 낮아집니다.
* @param shadow WantedShadowStyle 적용할 섀도우 스타일입니다. 기본값은 XSmall입니다.
* @return Modifier 스타일이 적용된 Modifier를 반환합니다.
*/

/**
* enum class WantedFramedStyleStatus
*
* 프레임 스타일의 상태를 정의하는 enum 클래스입니다.
*
* 각 상태에 따라 테두리 색상과 두께가 달라집니다:
* - Normal: 일반 상태입니다. 기본 테두리 색상이 적용됩니다.
* - Negative: 오류 또는 부정적인 상태입니다. 빨간색 계열 테두리가 적용됩니다.
* - Selected: 선택된 상태입니다. 파란색 계열의 2dp 테두리가 적용됩니다.
*/