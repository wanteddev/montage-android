/**
* 앱 전역에서 사용할 수 있는 커스텀 스낵바 호스트입니다.
*
* `SnackbarHostState`를 받아 `SnackbarData.visuals`가 `WantedSnackbarVisuals`인 경우 커스텀 레이아웃으로,
* 아닌 경우 기본 스낵바 텍스트와 버튼만 사용해 표시합니다.
*
* 사용 예시:
* ```kotlin
* val snackbarHostState = remember { SnackbarHostState() }
* WantedSnackBar(snackbarHostState = snackbarHostState)
* ```
*
* @param snackbarHostState SnackbarHostState: 스낵바 표시 상태를 제어합니다.
* @param modifier Modifier: 스낵바 전체 레이아웃 조정 Modifier입니다.
*/

/**
* class WantedSnackbarVisuals
*
* Wanted 전용 스낵바 메시지와 UI 요소를 지정하는 클래스입니다.
*
* 기본 메시지 외에 부가 설명, 아이콘, 패딩 등을 커스터마이징할 수 있습니다.
*
* @param message String: 스낵바에 표시될 기본 메시지입니다.
* @param actionLabel String?: 버튼에 표시될 텍스트입니다.
* @param duration SnackbarDuration: 스낵바 노출 지속 시간입니다.
* @param withDismissAction Boolean: 닫기 액션 포함 여부입니다.
* @param description String: 추가 설명 텍스트입니다.
* @param padding PaddingValues: 스낵바 콘텐츠에 적용될 패딩입니다.
* @param icon @Composable (() -> Unit)?: 스낵바 좌측에 표시할 아이콘입니다.
*/