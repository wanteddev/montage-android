/**
* WantedCircularLoading
*
* 화면 중앙에 원형으로 로딩 상태를 안내하는 컴포넌트입니다.
*
* dim 배경 포함 여부, 크기, 색상을 설정할 수 있습니다.
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
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param circleColor Color: 로딩 인디케이터 색상입니다.
* @param dimColor Color: 배경 dim 색상입니다.
* @param size Dp: 로딩 인디케이터 크기입니다.
*/

/**
* WantedCircularProgressIndicator
*
* 커스터마이징 가능한 원형 Loading Indicator입니다.
*
* 기본 크기는 24dp이며, 선 굵기는 전체 크기의 10%로 자동 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedCircularProgressIndicator(
*     modifier = Modifier.size(40.dp),
*     color = Color.Red
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param color Color: 인디케이터 색상입니다.
*/

/**
* WantedLogoLoading
*
* Wanted 심볼 로딩 애니메이션을 표시하는 컴포넌트입니다.
*
* isUseDim이 true인 경우 Dialog 기반으로 dim 처리된 로딩 화면이 표시됩니다.
*
* 사용 예시:
* ```kotlin
* WantedLogoLoading(isUseDim = true)
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param isUseDim Boolean: dim 배경 사용 여부입니다.
*/

/**
* WantedLogoLoadingDialog
*
* Dialog 기반의 Wanted 심볼 로딩 컴포넌트입니다.
*
* 외부 터치나 뒤로가기로 닫히지 않도록 설정된 Dialog에 로고 애니메이션이 표시됩니다.
*
* @param onDismissRequest () -> Unit: 다이얼로그 닫기 요청 시 호출되는 콜백입니다.
* @param properties DialogProperties: Dialog 속성 설정입니다.
*/

/**
* WantedLogoProgressIndicator
*
* Wanted 심볼 Lottie 애니메이션을 표시하는 컴포넌트입니다.
*
* 시스템 다크 모드 설정에 따라 자동으로 라이트/다크 테마의 로딩 애니메이션이 표시됩니다.
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
*/