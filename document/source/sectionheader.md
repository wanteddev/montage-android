/**
* 섹션 타이틀 및 부가 아이콘, 행동 요소를 포함하는 섹션 헤더 컴포저블입니다.
*
* 다양한 크기 옵션과 좌측/우측 컴포저블 삽입을 통해 유연하게 커스터마이징이 가능하며,
* 일반적으로 리스트, 섹션 구분, 콘텐츠 제목 등에 사용됩니다.
*
* 사용 예시 :
* ```kotlin
* WantedSectionHeader(
*     title = "제목",
*     size = Size.Medium,
*     headingContents = {
*         Icon(painter = painterResource(R.drawable.ic_info), contentDescription = null)
*     },
*     trailingContent = {
*         Icon(painter = painterResource(R.drawable.ic_more), contentDescription = null)
*     },
*     modifier = Modifier
* )
* ```
*
* @param title String: 섹션 제목으로 표시되는 텍스트입니다.
* @param modifier Modifier: 외형, 정렬, 배치를 제어하는 Modifier입니다.
* @param size Size: 헤더의 텍스트 스타일 크기를 결정합니다 (XSmall, Small, Medium, Large).
* @param textStyle TextStyle?: 커스텀 텍스트 스타일을 지정할 수 있습니다. 기본값은 크기에 따라 자동 설정됩니다.
* @param headingContents (() -> Unit)?: 제목 오른쪽에 배치될 부가 정보 아이콘 등 컴포저블 콘텐츠입니다.
* @param trailingContent (RowScope.() -> Unit)?: 오른쪽 끝에 배치되는 행동 버튼 또는 아이콘입니다.
*/

/**
* object WantedSectionHeaderContract
*
* 섹션 헤더 컴포넌트에 사용되는 설정 값을 정의하는 객체입니다.
*
* 주로 텍스트 크기나 패딩 등의 기준이 되는 사이즈 정보를 제공합니다.
*/

/**
* enum class Size
*
* 섹션 헤더의 사이즈를 정의하는 enum 클래스입니다.
*
* 사이즈에 따라 텍스트 크기나 마진 등이 달라질 수 있습니다.
* 제공되는 사이즈 옵션은 다음과 같습니다:
* - XSmall: 매우 작은 사이즈
* - Small: 작은 사이즈
* - Medium: 중간 사이즈
* - Large: 큰 사이즈
*/