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
* @param backgroundColor Color: 앱바 배경 색상입니다.
* @param background Boolean: 앱바 배경을 표시할지 여부입니다.
* @param variant Variant: 앱바 형태입니다.
* @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
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
* @param backgroundColor Color: 앱바 배경 색상입니다.
* @param background Boolean: 앱바 배경을 표시할지 여부입니다.
* @param variant Variant: 앱바 형태입니다.
* @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param title String: 타이틀 텍스트입니다.
* @param onClickClose () -> Unit: 닫기 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* WantedDialogSearchTopAppBar
*
* 검색 필드가 포함된 다이얼로그용 TopAppBar 컴포넌트입니다. (Variant.Search)
*
* 제목 대신 검색 필드(폭 가변)를 두고, 우측에 취소 텍스트 버튼을 배치합니다.
* 화면용 WantedSearchTopAppBar 와 같은 WantedSearchField(Medium, 높이 40)를 사용합니다.
*
* 사용 예시:
* ```kotlin
* var keyword by remember { mutableStateOf("") }
*
* WantedDialogSearchTopAppBar(
*     text = keyword,
*     placeholder = "검색어를 입력해 주세요.",
*     cancelText = "취소",
*     onClickCancel = { /* 닫기 처리 */ },
*     onValueChange = { keyword = it }
* )
* ```
*
* @param text String: 검색 필드에 입력된 텍스트입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param backgroundColor Color: 앱바 배경 색상입니다.
* @param background Boolean: 앱바 배경을 표시할지 여부입니다.
* @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param placeholder String: 검색 필드의 placeholder 입니다.
* @param enabled Boolean: 검색 필드 입력 가능 여부입니다.
* @param keyboardOptions KeyboardOptions: 키보드 옵션입니다.
* @param keyboardActions KeyboardActions: 키보드 액션입니다.
* @param focusRequester FocusRequester?: 검색 필드 포커스를 제어하는 객체입니다.
* @param cancelText String: 우측 취소 텍스트 버튼 문구입니다. 빈 문자열이면 버튼을 표시하지 않습니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. 기본값은 없음이며, 넣으면 24 아이콘이 검색 필드 왼쪽에 붙습니다.
* @param onClickCancel () -> Unit: 취소 버튼 클릭 시 호출되는 콜백입니다.
* @param onValueChange (String) -> Unit: 검색어 변경 시 호출되는 콜백입니다.
*/

/**
* WantedDialogTopAppBar
*
* 닫기 버튼이 포함된 다이얼로그용 TopAppBar 컴포넌트입니다.
*
* 우측에 닫기 아이콘이 고정으로 배치됩니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogTopAppBar(
*     title = "제목",
*     navigationIcon = { },
*     actions = { }
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
* @param backgroundColor Color: 앱바 배경 색상입니다.
* @param background Boolean: 앱바 배경을 표시할지 여부입니다.
* @param variant Variant: 앱바 형태입니다.
* @param navigationPadding Dp: Navigation 의 상하좌우 여백입니다. 기본값은 Popup · Bottom Sheet 값(24)이고 Full 은 [WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING] 을 넘깁니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 관리하는 객체입니다.
* @param navigationIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param title (@Composable () -> Unit)?: 타이틀 텍스트 슬롯입니다.
* @param actions (@Composable RowScope.() -> Unit)?: 우측 액션 슬롯입니다.
*/

/**
* object WantedDialogTopAppBarContract
*
* DialogTopAppBar 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* sealed class Variant
*
* TopDialogAppBar 의 형태를 정의하는 sealed 클래스입니다.
* Normal, Emphasized, Floating, Search 네 가지를 제공합니다.
*/

/**
* data object Normal
*
* Title 이 Center 에 위치합니다. Full 에서만 사용합니다.
*/

/**
* data object Emphasized
*
* Title 이 왼쪽에 위치합니다. Popup · Bottom Sheet 의 기본값입니다.
*/

/**
* data class Floating
*
* 플로팅 형태입니다. Title 없이 Leading · Trailing 버튼만 콘텐츠 위에 떠 있습니다.
*
* @property iconBackground Boolean: Leading · Trailing 아이콘 버튼에 반투명 원형 배경을 넣을지 여부입니다. 상단에 이미지가 깔릴 때 사용하며 기본값은 false 입니다. Text 버튼에는 적용되지 않습니다.
*/

/**
* data object Search
*
* 제목 대신 검색 필드(폭 가변)를 두는 형태입니다. Popup · Bottom Sheet · Full 어디서나 사용할 수 있습니다.
* 여백은 다른 variant 와 같은 navigationPadding 을 쓰고, 바 높이는 검색 필드(40) + 상하 여백으로 정해집니다.
* (Popup · Bottom Sheet 88, Full 80)
*/

/**
* object WantedDialogTopAppBarDefaults
*
* Modal Navigation(Popup · Bottom Sheet · Full) 의 여백 기본값을 제공하는 객체입니다. (Figma 4.0.0)
*
* Navigation 여백은 Modal 이 아니라 Navigation 컴포넌트가 소유합니다 — Modal 쪽 `navigationPadding` 은 0 으로 두고 이 값을 씁니다.
*/