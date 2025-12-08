/**
* WantedPullToRefreshBox
*
* Pull-to-Refresh 기능을 제공하는 컴포넌트입니다.
*
* 시스템 다크 모드에 따라 Lottie 애니메이션이 자동으로 전환되며,
* 사용자가 화면을 아래로 당겨 새로고침을 실행할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var isRefreshing by remember { mutableStateOf(false) }
*
* WantedPullToRefreshBox(
*     isRefreshing = isRefreshing,
*     onRefresh = {
*         isRefreshing = true
*         // 새로고침 처리
*         isRefreshing = false
*     }
* ) {
*     LazyColumn {
*         items(list) { item ->
*             Text(text = item)
*         }
*     }
* }
* ```
*
* @param isRefreshing Boolean: 현재 새로고침 중인지 여부입니다.
* @param onRefresh () -> Unit: 사용자가 당겨서 새로고침을 요청했을 때 호출되는 콜백입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param state PullToRefreshState: Pull-to-Refresh 상태를 관리하는 객체입니다.
* @param content @Composable BoxScope.() -> Unit: 새로고침 박스 내부에 배치할 콘텐츠입니다.
*/