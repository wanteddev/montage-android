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
* @param currentIndex Int: 현재 페이지 번호입니다. 값을 그대로 표시하므로 1부터 시작하는 번호를 넘깁니다(0부터 세는 인덱스면 +1).
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
* - Small: label2Bold 타이포, 좌우 10dp·상하 4dp 여백, 숫자 간격 3dp 입니다.
* - Normal: body2Bold 타이포, 좌우 12dp·상하 6dp 여백, 숫자 간격 4dp 입니다.
*/