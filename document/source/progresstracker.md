/**
* 수평형 단계 진행 표시 컴포저블입니다.
*
* 전체 단계 수와 현재 진행 단계를 기준으로 진행 바와 단계 라벨을 렌더링합니다.
* 각 단계는 번호와 함께 완료/진행 중/예정 상태로 표시되며, 선택된 단계에 강조 스타일이 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedProgressTrackerHorizontal(
*     stepCount = 4,
*     currentStep = 2,
*     label = { index -> "${index + 1}단계" }
* )
* ```
*
* @param stepCount Int: 전체 단계 수입니다.
* @param currentStep Int: 현재 선택된 단계 (1부터 시작)입니다.
* @param modifier Modifier: 외형 및 배치 조정을 위한 Modifier입니다.
* @param label ((Int) -> String)?: 각 단계별 라벨 텍스트를 반환하는 함수입니다.
*/

/**
* 수직형 단계 진행 표시 컴포저블입니다.
*
* 각 단계마다 텍스트 또는 커스텀 콘텐츠를 함께 배치할 수 있으며, 단계별 진행 상태에 따라 스타일이 달라집니다.
* 세로 레이아웃으로 복잡한 정보 구조에 적합합니다.
*
* 사용 예시:
* ```kotlin
* WantedProgressTrackerVertical(
*     stepCount = 4,
*     currentStep = 3,
*     label = { index -> "Step ${index + 1}" },
*     content = { index -> Text("내용 $index") }
* )
* ```
*
* @param stepCount Int: 총 단계 수입니다.
* @param currentStep Int: 현재 진행 중인 단계입니다.
* @param modifier Modifier: 외형 및 배치 조정을 위한 Modifier입니다.
* @param label ((Int) -> String)?: 각 단계의 텍스트 라벨 함수입니다.
* @param labelContent @Composable ((Int) -> Unit)?: 텍스트 라벨 대신 사용할 커스텀 UI 슬롯입니다.
* @param content @Composable (Int) -> Unit: 단계별 본문 콘텐츠입니다.
*/