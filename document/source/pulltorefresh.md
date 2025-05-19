/**
* 커스텀 애니메이션과 로티 인디케이터가 포함된 Pull-to-Refresh 컴포저블입니다.
*
* 시스템 다크 모드 여부에 따라 Lottie 애셋이 자동 전환되며, 유저가 스크롤을 아래로 당길 때
* 부드러운 그래픽과 함께 새로고침 로직을 트리거할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* val isRefreshing by remember { mutableStateOf(false) }
* WantedPullToRefreshBox(
*     isRefreshing = isRefreshing,
*     onRefresh = { /* 새로고침 처리 */ }
* ) {
*     LazyColumn { ... }
* }
* ```
*
* @param isRefreshing Boolean: 현재 새로고침 중인지 여부를 나타냅니다.
* @param onRefresh () -> Unit: 유저가 당겨서 새로고침을 요청했을 때 호출되는 콜백입니다.
* @param modifier Modifier: 외형 및 배치 설정용 modifier입니다.
* @param state PullToRefreshState: Pull-to-Refresh 상태를 관리하는 객체입니다. 기본값은 `rememberPullToRefreshState()`입니다.
* @param content @Composable () -> Unit: 새로고침 박스 내부에 배치할 UI 콘텐츠입니다.
*/