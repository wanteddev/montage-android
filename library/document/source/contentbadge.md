/**
* 텍스트와 아이콘, 배경 스타일을 조합하여 콘텐츠 배지를 구성하는 컴포저블입니다.
*
* Accent 또는 Neutral 컬러 테마를 선택할 수 있으며, 크기와 테두리 스타일을 설정할 수 있습니다.
* 클릭 이벤트, 좌우 아이콘 표시 등 다양한 커스터마이징이 가능합니다.
*
* 사용 예시:
* ```kotlin
* WantedContentBadge(
*     text = "Badge",
*     size = ContentBadgeSize.Small,
*     color = ContentBadgeColor.Accent,
*     leadingDrawable = R.drawable.ic_icon,
*     trailingDrawable = R.drawable.ic_icon,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: 배지에 표시할 텍스트입니다.
* @param modifier Modifier: 배지 외형과 배치를 설정합니다.
* @param type ContentBadgeType: Solid 또는 Outlined 형식의 배지 스타일입니다.
* @param size ContentBadgeSize: 배지 크기를 지정합니다 (XSmall, Small, Large).
* @param color ContentBadgeColor: 컬러 테마를 지정합니다 (Neutral, Accent).
* @param accentDefault WantedContentBadgeDefault: Accent 또는 Neutral 컬러 설정의 기본값을 지정합니다.
* @param leadingDrawable Int?: 텍스트 왼쪽에 표시할 아이콘 리소스 ID입니다.
* @param trailingDrawable Int?: 텍스트 오른쪽에 표시할 아이콘 리소스 ID입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다.
*/

/**
* NORMAL
*/

/**
* MEDIUM
*/

/**
* LARGE
*/

/**
* data class WantedContentBadgeDefault
*
* 콘텐츠 배지의 시각적 속성을 정의하는 데이터 클래스입니다.
*
* 배지에 표시될 텍스트 또는 아이콘 색상(contentColor), 배경색(backgroundColor), 테두리 색상(outLineColor)을 설정합니다.
*
* @param contentColor Color: 콘텐츠 색상입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 contentColor에 OPACITY_8을 적용한 색입니다.
* @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor입니다.
*/

/**
* object WantedContentBadgeDefaults
*
* 기본 콘텐츠 배지 구성을 제공하는 객체입니다.
*
* 강조(accent) 스타일 및 중립(neutral) 스타일의 배지 구성을 제공합니다.
*/

/**
* fun getAccentDefault(...)
*
* 강조 스타일의 기본 콘텐츠 배지를 반환합니다.
*
* 사용 예시:
* ```
* val badgeDefault = WantedContentBadgeDefaults.getAccentDefault()
* WantedContentBadge(
*     text = "New",
*     badgeDefault = badgeDefault
* )
* ```
*
* @param contentColor Color: 콘텐츠 색상입니다. 기본값은 cyan 계열 색상입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 contentColor에 OPACITY_8을 적용한 색입니다.
* @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor입니다.
* @return WantedContentBadgeDefault: 강조 배지 구성을 반환합니다.
*/

/**
*
* fun getNeutralDefault(...)
*
* 중립 스타일의 기본 콘텐츠 배지를 반환합니다.
*
* 사용 예시:
* ```
* val badgeDefault = WantedContentBadgeDefaults.getNeutralDefault()
* WantedContentBadge(
*     text = "Label",
*     badgeDefault = badgeDefault
* )
* ```
*
* @param contentColor Color: 콘텐츠 색상입니다. 기본값은 label_alternative 색상입니다.
* @return WantedContentBadgeDefault: 중립 배지 구성을 반환합니다.
*/