/**
* 중앙 정렬된 타이틀을 포함하는 상단 앱바입니다.
*
* 앱바 유형(Normal, Extended)에 따라 레이아웃이 달라지며, 아이콘, 타이틀, 액션을 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedCenterTopAppBar(
*     title = { Text("타이틀") },
*     navigationIcon = { Icon(...) },
*     actions = { IconButton(...) }
* )
* ```
*
* @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
* @param windowInsets WindowInsets: 인셋을 적용하여 상태바 등 시스템 UI를 고려한 여백을 처리합니다.
* @param background Color: 앱바의 배경 색상입니다.
* @param type Variant: 앱바의 유형으로 Normal 또는 Extended를 설정할 수 있습니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 반영하여 divider 표시 여부를 조절합니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 영역에 표시할 컴포저블입니다.
* @param title @Composable (() -> Unit)?: 중앙 타이틀 영역에 표시할 컴포저블입니다.
* @param actions @Composable RowScope.() -> Unit: 우측 액션 버튼 영역입니다.
*/

/**
* 중앙 정렬 타이틀을 가진 다이얼로그용 앱바입니다.
*
* 일반 앱바와 다르게 타이틀이 가운데 정렬되며 다양한 스타일을 지원합니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCenterTopAppBar(
*     title = "다이얼로그 제목",
*     actions = { IconButton(...) }
* )
* ```
*
* @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
* @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
* @param background Color: 앱바 배경 색상입니다.
* @param type Variant: 앱바 유형 (Normal, Floating, Extended)입니다.
* @param scrollableState ScrollableState?: 스크롤 상태입니다.
* @param title String: 타이틀 텍스트입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param actions @Composable (RowScope.() -> Unit)?: 우측 액션 컴포저블입니다.
*/

/**
* 중앙 정렬 타이틀과 닫기 버튼이 포함된 다이얼로그용 앱바입니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCenterCloseTopAppBar(
*     title = "제목",
*     onClickBack = { /* 닫기 처리 */ }
* )
* ```
*
* @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
* @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
* @param background Color: 앱바 배경 색상입니다.
* @param type Variant: 앱바 유형 (Normal, Floating, Extended)입니다.
* @param scrollableState ScrollableState?: 스크롤 상태입니다.
* @param title String: 중앙 타이틀 텍스트입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param onClickBack () -> Unit: 닫기 아이콘 클릭 시 콜백입니다.
*/

/**
* 다이얼로그 스타일의 일반 앱바입니다.
*
* 다이얼로그에서 사용하는 상단 앱바 형태로, 타이틀 및 좌우 컴포넌트를 설정할 수 있습니다.
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
* @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
* @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
* @param background Color: 앱바의 배경 색상입니다.
* @param type Variant: 앱바 스타일 (Normal, Floating, Extended)입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 기반으로 Divider를 제어합니다.
* @param title String: 타이틀 텍스트입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param actions @Composable RowScope.() -> Unit?: 우측 액션 아이콘 영역입니다.
*/

/**
* 닫기 버튼이 포함된 다이얼로그 앱바입니다.
*
* 우측에 닫기 아이콘을 고정 배치하며 클릭 콜백을 받을 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedDialogCloseTopAppBar(
*     title = "제목",
*     onClickBack = { /* 닫기 동작 */ }
* )
* ```
*
* @param modifier Modifier: 앱바 외형 및 배치를 조정하는 Modifier입니다.
* @param windowInsets WindowInsets: 인셋 정보를 적용합니다.
* @param background Color: 앱바의 배경 색상입니다.
* @param type Variant: 앱바 스타일 (Normal, Floating, Extended)입니다.
* @param scrollableState ScrollableState?: 스크롤 상태를 기반으로 Divider를 제어합니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param title String: 타이틀 텍스트입니다.
* @param onClickBack () -> Unit: 닫기 아이콘 클릭 시 실행되는 콜백입니다.
*/

/**
* 통합 상단 앱바 컴포저블로, 일반형, Floating형, Extended형을 포함합니다.
*
* 타이틀 정렬, 배경, 스크롤 상태, 좌우 아이콘 등을 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedTopAppBar(
*     title = "타이틀",
*     navigationIcon = { Icon(...) },
*     actions = { IconButton(...) }
* )
* ```
*
* @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
* @param windowInsets WindowInsets: 인셋을 적용합니다.
* @param type Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param titleAlignCenter Boolean: 타이틀을 중앙 정렬할지 여부입니다.
* @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
* @param title String: 타이틀로 표시할 텍스트입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
*/

/**
* 뒤로 가기 아이콘이 포함된 앱바를 제공합니다.
*
* 사용 예시:
* ```kotlin
* WantedBackTopAppBar(
*     title = "타이틀",
*     onClickBack = { /* 뒤로가기 처리 */ }
* )
* ```
*
* @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
* @param windowInsets WindowInsets: 인셋을 적용합니다.
* @param type Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param titleAlignCenter Boolean: 타이틀을 중앙 정렬할지 여부입니다.
* @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
* @param title String: 타이틀로 표시할 텍스트입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
* @param onClickBack () -> Unit: 뒤로가기 아이콘 클릭 시 호출되는 콜백입니다.
*/

/**
* 일반 TopAppBar 형식을 제공합니다. 정렬/타입에 따라 내부 레이아웃이 달라집니다.
*
* @param modifier Modifier: 외형 및 배치를 위한 Modifier입니다.
* @param windowInsets WindowInsets: 인셋을 적용합니다.
* @param type Variant: 앱바 유형(Normal, Floating, Display, Search)입니다.
* @param background Color: 앱바 배경 색상입니다.
* @param scrollableState ScrollableState?: 스크롤 상태 정보입니다.
* @param title @Composable (() -> Unit)?: 타이틀 컴포저블입니다.
* @param navigationIcon @Composable (() -> Unit)?: 좌측 아이콘 컴포저블입니다.
* @param actions @Composable RowScope.() -> Unit: 우측 액션 영역입니다.
*/

/**
* object WantedTopAppBarContract
*
* 상단 앱바(TopAppBar) 컴포넌트의 UI 설정 값을 정의하는 객체입니다.
*
* 앱바의 형태를 제어하는 enum 클래스 `Variant`을 포함합니다.
*/

/**
* enum class Variant
*
* TopAppBar의 형태를 정의하는 enum 클래스입니다.
*
* 제공되는 앱바 타입은 다음과 같습니다:
* - Normal: 기본 상단 앱바 형태
* - Extended: 높이가 확장된 형태
* - Floating: 투명 배경 또는 떠 있는 형태의 앱바
*/

/**
* TopAppBar에 적용되는 기본 WindowInsets입니다.
*
* 시스템 바(systemBars)의 수평 및 상단 영역만을 고려합니다.
*
* 사용 예시:
* ```kotlin
* Modifier.windowInsetsPadding(WantedTopAppBarDefaults.windowInsets)
* ```
*
* @return WindowInsets: 시스템 바 인셋 중 수평 및 상단 영역만 포함한 Insets입니다.
*/

/**
* TopAppBar에 사용되는 아이콘 버튼 컴포저블입니다.
*
* Floating 타입에 따라 다양한 배경 스타일이 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedTopAppBarIconButton(
*     painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
*     onClick = { /* 클릭 동작 */ }
* )
* ```
*
* @param painter Painter: 아이콘으로 표시할 이미지입니다.
* @param modifier Modifier: 버튼의 크기, 외형, 배치를 조정하는 Modifier입니다.
* @param type Variant: 앱바 타입으로 스타일에 영향을 미칩니다.
* @param enabled Boolean: 버튼 활성화 여부입니다.
* @param floatingStyleAlternative Boolean: Floating 타입의 대체 스타일 여부입니다.
* @param floatingStyleBackground Boolean: Floating 타입의 기본 배경 스타일 사용 여부입니다.
* @param interactionSource MutableInteractionSource: 사용자 인터랙션을 처리하는 객체입니다.
* @param tint Color: 아이콘 색상입니다.
* @param onClick () -> Unit: 버튼 클릭 시 실행되는 콜백입니다.
*/

/**
* 시스템에 정의되어 있는 IconButton의 default size 56.dp
* size를 40으로 줄이면 ripple 효과만 56.dp 로 보인다.
*/