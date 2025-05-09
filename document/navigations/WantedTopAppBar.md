---
title: TopAppBar
description: 다양한 스타일과 정렬을 지원하는 통합 상단 앱바 컴포저블
image: /components/top-app-bar/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedTopAppBar

### 개요
일반형(Normal), 확장형(Extended), 플로팅형(Floating)을 지원하는 통합 TopAppBar입니다.
타이틀 정렬 옵션, 스크롤 반응, 좌우 액션 커스터마이징 등을 지원합니다.

### 사용 예시
```kotlin
WantedTopAppBar(
    title = "타이틀",
    navigationIcon = { Icon(...) },
    actions = { IconButton(...) }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형 및 배치를 위한 Modifier입니다. |
| windowInsets | WindowInsets | 인셋을 적용합니다. |
| type | TopAppBarType | 앱바 유형(Normal, Floating, Extended)입니다. |
| background | Color | 앱바 배경 색상입니다. |
| titleAlignCenter | Boolean | 타이틀을 중앙 정렬할지 여부입니다. |
| scrollableState | ScrollableState? | 스크롤 상태 정보입니다. |
| title | String | 타이틀로 표시할 텍스트입니다. |
| navigationIcon | @Composable (() -> Unit)? | 좌측 아이콘 컴포저블입니다. |
| actions | @Composable RowScope.() -> Unit | 우측 액션 영역입니다. |

---

## WantedBackTopAppBar

### 개요
뒤로가기 아이콘이 포함된 기본 앱바입니다. 클릭 콜백 설정이 가능합니다.

### 사용 예시
```kotlin
WantedBackTopAppBar(
    title = "타이틀",
    onClickBack = { /* 뒤로가기 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형 및 배치를 위한 Modifier입니다. |
| windowInsets | WindowInsets | 인셋을 적용합니다. |
| type | TopAppBarType | 앱바 유형(Normal, Floating, Extended)입니다. |
| background | Color | 앱바 배경 색상입니다. |
| titleAlignCenter | Boolean | 타이틀을 중앙 정렬할지 여부입니다. |
| scrollableState | ScrollableState? | 스크롤 상태 정보입니다. |
| title | String | 타이틀로 표시할 텍스트입니다. |
| actions | @Composable RowScope.() -> Unit | 우측 액션 영역입니다. |
| onClickBack | () -> Unit | 뒤로가기 버튼 클릭 시 콜백입니다. |

---

## WantedCenterTopAppBar

### 개요
중앙 정렬 타이틀을 포함한 앱바입니다. Normal과 Extended 유형 모두 지원합니다.

### 사용 예시
```kotlin
WantedCenterTopAppBar(
    title = {
        Text("타이틀")
    },
    navigationIcon = {
        Icon(...) 
    },
    actions = {
        IconButton(...) 
    }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형 및 배치를 위한 Modifier입니다. |
| windowInsets | WindowInsets | 인셋을 적용합니다. |
| background | Color | 앱바 배경 색상입니다. |
| type | TopAppBarType | 앱바 유형(Normal, Extended)입니다. |
| scrollableState | ScrollableState? | 스크롤 상태 정보입니다. |
| navigationIcon | @Composable (() -> Unit)? | 좌측 아이콘 컴포저블입니다. |
| title | @Composable (() -> Unit)? | 중앙 정렬 타이틀 영역입니다. |
| actions | @Composable RowScope.() -> Unit | 우측 액션 영역입니다. |

---

## WantedTopAppBarIconButton

### 개요
TopAppBar에 사용하는 아이콘 버튼입니다. 타입에 따라 다양한 배경을 가질 수 있습니다.

### 사용 예시
```kotlin
WantedTopAppBarIconButton(
    painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| painter | Painter | 아이콘 이미지입니다. |
| modifier | Modifier | 외형 및 배치를 조정하는 Modifier입니다. |
| type | TopAppBarType | 앱바 유형으로 스타일에 영향을 줍니다. |
| enabled | Boolean | 버튼 활성화 여부입니다. |
| floatingStyleAlternative | Boolean | Floating 타입 대체 배경 여부입니다. |
| floatingStyleBackground | Boolean | Floating 타입 기본 배경 여부입니다. |
| interactionSource | MutableInteractionSource | 사용자 인터랙션 처리 객체입니다. |
| tint | Color | 아이콘 색상입니다. |
| onClick | () -> Unit | 클릭 시 콜백입니다. |

---

## Enum 설명

### TopAppBarType
| 값 | 설명 |
|:---|:---|
| Normal | 기본 상단 앱바 스타일입니다. |
| Extended | 확장된 형태로 두 줄 이상의 정보를 표시할 수 있습니다. |
| Floating | 배경이 투명하거나 공중에 떠 있는 형식입니다. |

---

## Note
- 모든 앱바는 `WantedTopAppBarDefaults.windowInsets`로 시스템 인셋 처리를 통일합니다.
- divider는 스크롤 상태에 따라 자동으로 나타납니다.