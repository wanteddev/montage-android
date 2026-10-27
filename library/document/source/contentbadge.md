/**
* WantedContentBadge
*
* 텍스트와 아이콘, 배경 스타일을 조합하여 Content badge를 구성하는 컴포넌트입니다.
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
*     leadingDrawable = R.drawable.icon_normal_bookmark,
*     trailingDrawable = R.drawable.icon_normal_arrow_right,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param text String: 배지에 표시할 텍스트입니다. 1줄까지 표시하고 넘치면 말줄임(…) 처리됩니다.
* @param modifier Modifier: 배지 외형과 배치를 설정합니다.
* @param type ContentBadgeType: 배지 스타일입니다. Solid 는 accentDefault 의 backgroundColor 배경, Outlined 는 투명 배경에 outLineColor 1dp 테두리입니다.
* @param size ContentBadgeSize: 배지 크기를 지정합니다 (XSmall, Small, Medium).
* @param color ContentBadgeColor: accentDefault 를 지정하지 않을 때의 기본 색 구성과 눌림 효과 색을 정합니다 (Neutral, Accent).
* @param accentDefault WantedContentBadgeDefault: 배지의 실제 색 구성(글자·배경·테두리)입니다. 지정하면 color 와 무관하게 이 값을 쓰며, 기본값은 color 에 따라 getAccentDefault()/getNeutralDefault() 입니다.
* @param leadingDrawable Int?: 텍스트 왼쪽에 표시할 아이콘 리소스 ID입니다.
* @param trailingDrawable Int?: 텍스트 오른쪽에 표시할 아이콘 리소스 ID입니다.
* @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다. clickOnce 를 쓰는 디자인 시스템 컴포넌트의 직전 클릭(다른 컴포넌트 포함)으로부터 200ms 안의 클릭은 무시됩니다.
*/

/**
* data class WantedContentBadgeDefault
*
* Content badge의 시각적 속성을 정의하는 데이터 클래스입니다.
*
* 텍스트 또는 아이콘 색상(contentColor), 배경색(backgroundColor), 테두리 색상(outLineColor)을 설정합니다.
*
* @param contentColor Color: 콘텐츠 색상입니다.
* @param backgroundColor Color: 배경 색상입니다. 기본값은 contentColor에 OPACITY_8을 적용한 색입니다.
* @param outLineColor Color: 테두리 색상입니다. 기본값은 contentColor입니다.
*/

/**
* object WantedContentBadgeDefaults
*
* 기본 Content badge 구성을 제공하는 객체입니다.
*
* Accent, Neutral 스타일의 구성을 제공합니다.
*/

    /**
* fun getAccentDefault(...)
*
* Accent 스타일의 기본 Content badge를 반환합니다.
*
* 사용 예시:
* ```
* val badgeDefault = WantedContentBadgeDefaults.getAccentDefault()
* WantedContentBadge(
*     text = "New",
*     color = ContentBadgeColor.Accent,
*     accentDefault = badgeDefault
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
* Neutral 스타일의 기본 Content badge를 반환합니다.
*
* 배경은 surfaceNeutralSecondary, 테두리는 foregroundNeutralTertiary 16% 로 고정되며 contentColor 를 바꿔도 따라 바뀌지 않습니다.
*
* 사용 예시:
* ```
* val badgeDefault = WantedContentBadgeDefaults.getNeutralDefault()
* WantedContentBadge(
*     text = "Label",
*     accentDefault = badgeDefault
* )
* ```
*
* @param contentColor Color: 콘텐츠 색상입니다. 기본값은 foregroundNeutralTertiary 색상입니다.
* @return WantedContentBadgeDefault: 중립 배지 구성을 반환합니다.
*/