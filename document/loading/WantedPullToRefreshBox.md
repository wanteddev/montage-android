---
title: PullToRefreshBox
description: 커스텀 애니메이션이 포함된 풀 투 리프레시 박스 컴포넌트
image: /components/pull-to-refresh-box/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedPullToRefreshBox

### 개요
`WantedPullToRefreshBox`는 Lottie 애니메이션 기반의 커스텀 풀 투 리프레시 컴포넌트입니다. 사용자가 콘텐츠를 아래로 당겨 새로고침을 유도할 수 있으며, 다크 모드 대응 및 부드러운 트랜지션 효과가 포함되어 있습니다.

### 사용 예시
```kotlin
val isRefreshing by remember { mutableStateOf(false) }
WantedPullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = { /* 새로고침 처리 */ }
) {
    LazyColumn { ... }
}
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| isRefreshing | Boolean | 현재 새로고침 중인지 여부입니다. |
| onRefresh | () -> Unit | 사용자가 당겨서 새로고침 요청 시 호출됩니다. |
| modifier | Modifier | 레이아웃 및 스타일 설정을 위한 Modifier입니다. 기본값은 Modifier입니다. |
| state | PullToRefreshState | Pull-to-Refresh 상태를 관리하는 객체입니다. 기본값은 `rememberPullToRefreshState()`입니다. |
| content | @Composable () -> Unit | 새로고침 박스 내부 콘텐츠입니다. |

---

## Note
- 다크모드에 따라 Lottie 애셋(`pullToRefresh-pull-dark.json` 또는 `pullToRefresh-pull.json`)이 자동으로 선택됩니다.
- 애니메이션은 사용자 조작에 따라 투명도, 크기, 위치 등을 포함하여 부드럽게 변화합니다.
- `PullToRefreshState`를 직접 관리할 경우 더욱 정밀한 제어가 가능합니다.
