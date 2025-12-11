/**
* WantedDotIndicator
*
* 페이지네이션용 Dot Indicator 컴포넌트입니다.
*
* 현재 페이지 위치를 중심으로 일정 개수의 Dot을 표시하며,
* 선택 상태에 따라 크기와 색상이 애니메이션으로 변경됩니다.
*
* 사용 예시:
* ```kotlin
* var currentPage by remember { mutableIntStateOf(0) }
*
* WantedDotIndicator(
*     totalPageCount = 10,
*     visibleDotCount = 5,
*     currentIndex = currentPage
* )
* ```
*
* @param totalPageCount Int: 전체 페이지 수입니다.
* @param visibleDotCount Int: 화면에 표시할 최대 dot 개수입니다.
* @param currentIndex Int: 현재 선택된 페이지 인덱스입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param size WantedDotIndicatorSize: dot의 크기입니다.
* @param type WantedDotIndicatorType: dot의 스타일 타입입니다.
*/

/**
* object WantedPaginationDotDefaults
*
* Pagination 컴포넌트에서 사용하는 설정값을 정의하는 객체입니다.
*/

/**
* enum class WantedDotIndicatorSize
*
* Dot Indicator 의 크기를 정의하는 enum 클래스입니다.
* - Small: 작은 크기의 dot입니다.
* - Medium: 중간 크기의 dot입니다.
*/

/**
* enum class WantedDotIndicatorType
*
* Dot Indicator의 색상을 정의하는 enum 클래스입니다.
* - Normal: 일반 스타일의 dot입니다.
* - White: 흰색 스타일의 dot입니다.
*/