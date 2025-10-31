/**
* WantedDotIndicator
*
* 페이지네이션용 Dot Indicator 컴포넌트입니다.
*
* 현재 페이지 위치를 중심으로 일정 개수의 dot을 표시하며,
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
* WantedPageCounter
*
* 현재 페이지와 전체 페이지 수를 표시하는 페이지 카운터 컴포넌트입니다.
*
* "2 / 10" 형식으로 페이지 정보를 표시하며, 일반형과 alternative 스타일을 선택할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var currentPage by remember { mutableIntStateOf(1) }
*
* WantedPageCounter(
*     totalPageCount = 10,
*     currentIndex = currentPage,
*     isAlternative = true
* )
* ```
*
* @param totalPageCount Int: 전체 페이지 수입니다.
* @param currentIndex Int: 현재 페이지 인덱스입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param size WantedPageCounterSize: 컴포넌트의 크기입니다.
* @param isAlternative Boolean: alternative 배경 스타일 적용 여부입니다.
*/

/**
* object WantedPaginationContract
*
* 페이지네이션 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class WantedDotIndicatorSize
*
* Dot Indicator의 크기를 정의하는 enum 클래스입니다.
* Small, Medium 두 가지 크기가 존재합니다.
*/

/**
* enum class WantedDotIndicatorType
*
* Dot Indicator의 스타일을 정의하는 enum 클래스입니다.
* Normal, White 두 가지 스타일이 존재합니다.
*/

/**
* enum class WantedPageCounterSize
*
* 페이지 카운터의 크기를 정의하는 enum 클래스입니다.
* Small, Normal 두 가지 크기가 존재합니다.
*
* @property paddingHorizontal Dp: 좌우 패딩 값입니다.
* @property paddingVertical Dp: 상하 패딩 값입니다.
* @property space Dp: 항목 간 간격입니다.
*/