/**
* WantedDialogCenterTopAppBar
*
* 중앙 정렬 타이틀을 가진 다이얼로그용 Top app bar 컴포넌트입니다.
*
* 타이틀이 중앙에 정렬되며, 다양한 Variant를 지원합니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCenterTopAppBar(
*     title = "다이얼로그 제목",
*     actions = { IconButton(...) }
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param variant Variant: 앱바 형태입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param title String: 타이틀 텍스트입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
*/

/**
* WantedDialogCenterCloseTopAppBar
*
* 중앙 정렬 타이틀과 닫기 버튼이 포함된 다이얼로그용 Top app bar 컴포넌트입니다.
*
* 우측에 닫기 아이콘이 고정으로 배치됩니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCenterCloseTopAppBar(
*     title = "제목",
*     onClickClose = { /* 닫기 처리 */ }
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param variant Variant: 앱바 형태입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param title String: 중앙 타이틀 텍스트입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param onClickClose () -> Unit: 닫기 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* WantedDialogTopAppBar
*
* 다이얼로그용 TopAppBar 컴포넌트입니다.
*
* 타이틀과 좌우 컴포넌트를 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogTopAppBar(
*     title = "다이얼로그 제목",
*     navigationIcon = { Icon(...) },
*     actions = { IconButton(...) }
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param variant Variant: 앱바 형태입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param title String: 타이틀 텍스트입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
*/

/**
* WantedDialogCloseTopAppBar
*
* 닫기 버튼이 포함된 다이얼로그용 TopAppBar 컴포넌트입니다.
*
* 우측에 닫기 아이콘이 고정으로 배치됩니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCloseTopAppBar(
*     title = "제목",
*     onClickClose = { /* 닫기 처리 */ }
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param variant Variant: 앱바 형태입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param title String: 타이틀 텍스트입니다.
* @param onClickClose () -> Unit: 닫기 버튼 클릭 시 호출되는 콜백입니다.
*/