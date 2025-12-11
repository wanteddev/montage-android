/**
* WantedProgressTracker
*
* 수평형 단계 진행 표시 컴포넌트입니다.
*
* 전체 단계 수와 현재 진행 단계를 기준으로 진행 바와 단계 라벨을 표시합니다.
*
* 사용 예시:
* ```kotlin
* var currentStep by remember { mutableIntStateOf(2) }
*
* WantedProgressTrackerHorizontal(
*     stepCount = 4,
*     currentStep = currentStep,
*     label = { index -> "${index + 1}단계" }
* )
* ```
*
* @param stepCount Int: 전체 단계 수입니다.
* @param currentStep Int: 현재 선택된 단계입니다. 1부터 시작합니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param label ((Int) -> String)?: 각 단계의 라벨 텍스트를 반환하는 함수입니다.
*/

/**
* WantedProgressTrackerVertical
*
* 수직형 단계 진행 표시 컴포넌트입니다.
*
* 각 단계마다 텍스트 또는 커스텀 콘텐츠를 함께 배치할 수 있으며,
* 단계별 진행 상태에 따라 스타일이 변경됩니다.
*
* 사용 예시:
* ```kotlin
* var currentStep by remember { mutableIntStateOf(2) }
*
* WantedProgressTrackerVertical(
*     stepCount = 4,
*     currentStep = currentStep,
*     label = { index -> "Step ${index + 1}" },
*     content = { index ->
*         Text("내용 $index")
*     }
* )
* ```
*
* @param stepCount Int: 전체 단계 수입니다.
* @param currentStep Int: 현재 진행 중인 단계입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param label ((Int) -> String)?: 각 단계의 텍스트 라벨을 반환하는 함수입니다.
* @param labelContent (@Composable (Int) -> Unit)?: 텍스트 라벨 대신 사용할 커스텀 콘텐츠 슬롯입니다.
* @param content @Composable (Int) -> Unit: 단계별 본문 콘텐츠입니다.
*/