/**
* 화면 중앙에 원형 로딩 인디케이터를 표시하는 컴포저블입니다.
*
* dim 배경 포함 여부, 크기, 색상을 설정할 수 있으며 전체 화면에 로딩을 표시할 때 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedCircularLoading(
*     size = 32.dp,
*     circleColor = Color.Gray,
*     dimColor = Color.Black.copy(alpha = 0.3f)
* )
* ```
*
* @param modifier Modifier: 외형 및 배치 설정입니다.
* @param circleColor Color: 로딩 인디케이터 색상입니다.
* @param dimColor Color: 배경 dim 색상입니다.
* @param size Dp: 로딩 인디케이터 크기입니다.
*/

/**
* 커스터마이징 가능한 원형 로딩 인디케이터입니다.
*
* 기본 크기는 28dp이며, 선 두께는 전체 크기의 10%입니다. StrokeCap은 Round입니다.
*
* 사용 예시:
* ```kotlin
* WantedCircularProgressIndicator(modifier = Modifier.size(40.dp), color = Color.Red)
* ```
*
* @param modifier Modifier: 크기 및 레이아웃 조정용입니다.
* @param color Color: 인디케이터 색상입니다.
*/

/**
* Wanted 로고 기반의 로딩 애니메이션을 표시하는 컴포저블입니다.
*
* `isUseDim`이 true일 경우, Dialog 기반 dim 처리된 로딩 레이어가 출력됩니다.
*
* 사용 예시:
* ```kotlin
* WantedLogoLoading(isUseDim = true)
* ```
*
* @param modifier Modifier: 레이아웃 설정입니다.
* @param isUseDim Boolean: dim 배경 사용 여부입니다.
*/

/**
* 다이얼로그를 통한 Wanted 로고 로딩 표시 컴포저블입니다.
*
* 외부 dismiss를 막는 속성을 포함한 Dialog 위에 로고 애니메이션이 표시됩니다.
*
* @param onDismissRequest () -> Unit: 다이얼로그 닫기 요청 콜백입니다.
* @param properties DialogProperties: Dialog 구성 속성입니다.
*/