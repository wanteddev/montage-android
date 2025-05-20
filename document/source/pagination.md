/**
* 페이지네이션용 dot indicator를 표시하는 컴포저블입니다.
*
* 현재 페이지 위치를 중심으로 일정 개수의 dot을 보여주며, 선택 상태에 따라 색상, 크기, 애니메이션이 다르게 표시됩니다.
* `type`이 `Normal`일 경우 배경이 채워진 형태, `White`일 경우 테두리만 있는 흰색 스타일로 구성됩니다.
*
* 사용 예시:
* ```kotlin
* WantedDotIndicator(
*     totalPageCount = 10,
*     visibleDotCount = 5,
*     currentIndex = 2
* )
* ```
*
* @param totalPageCount Int: 전체 페이지 수입니다.
* @param visibleDotCount Int: 화면에 표시할 최대 dot 수입니다.
* @param currentIndex Int: 현재 선택된 페이지 index입니다.
* @param modifier Modifier: 배치와 외형을 위한 Modifier입니다.
* @param size WantedDotIndicatorSize: dot의 크기를 결정합니다. 기본값은 Medium입니다.
* @param type WantedDotIndicatorType: dot 스타일 타입입니다. 기본값은 Normal입니다.
*/

/**
* dot 이 짝수일때 Center
*/

/**
* 처음
*/

/**
* 마지막
*/

/**
* 중간
*/

/**
* 현재 페이지와 전체 페이지 수를 함께 표시하는 페이지 카운터 컴포저블입니다.
*
* `2 / 10` 형식으로 페이지 정보를 출력하며, 일반형과 alternative 스타일(투명 배경) 중 선택할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedPageCounter(
*     totalPageCount = 10,
*     currentIndex = 2,
*     isAlternative = true
* )
* ```
*
* @param totalPageCount Int: 전체 페이지 수입니다.
* @param currentIndex Int: 현재 페이지 인덱스입니다.
* @param modifier Modifier: 배치 및 외형 설정을 위한 Modifier입니다.
* @param size WantedPageCounterSize: 크기 설정입니다 (Small 또는 Normal).
* @param isAlternative Boolean: alternative 배경 스타일 적용 여부입니다.
*/

/**
* object WantedPaginationContract
*
* 페이지네이션 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*
* Dot indicator, Page counter와 관련된 시각적 스타일과 크기를 제어할 수 있는 enum 클래스를 포함합니다.
*/

/**
* enum class WantedDotIndicatorSize
*
* 페이지네이션의 Dot Indicator 크기를 정의하는 enum 클래스입니다.
*
* 포함된 크기 옵션:
* - Small: 2~6dp 범위
* - Medium: 6~10dp 범위
*/

/**
* enum class WantedDotIndicatorType
*
* Dot Indicator의 시각적 스타일을 정의하는 enum 클래스입니다.
*
* 포함된 스타일 옵션:
* - Normal: 배경이 채워진 원형 스타일
* - White: 흰색 테두리와 반투명 배경 스타일
*/

/**
* enum class WantedPageCounterSize
*
* 페이지 카운터 UI의 크기를 정의하는 enum 클래스입니다.
*
* 각 크기는 내부 패딩 및 항목 간 간격을 조정합니다.
*
* 포함된 크기 옵션:
* - Small: 좌우 10dp, 상하 4dp, 간격 3dp
* - Normal: 좌우 12dp, 상하 6dp, 간격 4dp
*
* @property paddingHorizontal `Dp`: 좌우 패딩 값입니다.
* @property paddingVertical `Dp`: 상하 패딩 값입니다.
* @property space `Dp`: 항목 간 간격입니다.
*/