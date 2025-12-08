/**
* WantedPageCounter
*
* 현재 페이지와 전체 페이지 수를 표시하는 컴포넌트입니다.
*
* n/n 형식으로 페이지 정보를 표시하며, Normal과 Alternative 스타일을 제공합니다.
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
* object WantedPaginationCounterDefaults
*
* Page counter 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class WantedPageCounterSize
*
* Page counter 의 크기를 정의하는 enum 클래스입니다.
* - Small: 작은 크기의 Page counter 입니다.
* - Normal: 일반 크기의 Page counter 입니다.
*/