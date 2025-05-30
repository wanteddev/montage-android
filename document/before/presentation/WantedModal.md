---
title: Modal
description: 모달 및 바텀시트를 구현하는 컴포저블 함수 모음
image: /components/modal/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedModal

### 개요
상단 앱바와 확인/취소 버튼을 포함한 기본 모달입니다. `WantedDialogTwoButtonImpl` 기반으로 구성되며, 버튼을 포함한 단순 모달 레이아웃을 제공합니다.

### 사용 예시
```kotlin
WantedModal(
    onDismissRequest = {},
    modifier = Modifier,
    type = ModalType.Flexible,
    properties = DialogProperties(),
    shape = RoundedCornerShape(12.dp),
    topBar = { WantedDialogTopAppBar(title = "제목") },
    positive = "확인",
    onClickPositive = {},
    content = { Text("내용") }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| onDismissRequest | () -> Unit | 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다. |
| modifier | Modifier | 외형 및 높이 조정 Modifier입니다. |
| type | ModalType | 모달의 유형 (Flexible, Fixed 등)입니다. |
| properties | DialogProperties | Compose Dialog 속성입니다. |
| shape | RoundedCornerShape | 모달의 모서리 둥글기입니다. |
| topBar | @Composable (() -> Unit)? | 상단 앱바 컴포저블입니다. |
| positive | String? | 확인 버튼 텍스트입니다. |
| negative | String? | 취소 버튼 텍스트입니다. |
| onClickPositive | (() -> Unit)? | 확인 버튼 클릭 시 콜백입니다. |
| onClickNegative | (() -> Unit)? | 취소 버튼 클릭 시 콜백입니다. |
| content | @Composable BoxScope.() -> Unit | 본문 콘텐츠 영역입니다. |

---
<br />
<br />

## WantedModal 확인/취소 버튼 없이 Slot 제공

### 사용 예시
```kotlin
WantedModal(
    onDismissRequest = {},
    modifier = Modifier,
    type = ModalType.Flexible,
    properties = DialogProperties(),
    shape = RoundedCornerShape(12.dp),
    topBar = { WantedDialogTopAppBar(title = "제목") },
    bottomBar = {
        Row(Modifier.fillMaxWidth()) {
            Text("커스텀 버튼")
        }
    },
    content = { Text("본문") }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| onDismissRequest | () -> Unit | 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다. |
| modifier | Modifier | 외형 및 높이 조정 Modifier입니다. |
| type | ModalType | 모달의 유형입니다. |
| properties | DialogProperties | Compose Dialog 속성입니다. |
| shape | RoundedCornerShape | 모달의 모서리 둥글기입니다. |
| topBar | @Composable (() -> Unit)? | 상단 앱바 Slot입니다. |
| bottomBar | (@Composable () -> Unit)? | 하단 바 Slot입니다. |
| content | @Composable BoxScope.() -> Unit | 본문 콘텐츠 Slot입니다. |

---
<br />
<br />

## WantedModal LazyColumn 콘텐츠 제공

### 사용 예시
```kotlin
WantedModal(
    onDismissRequest = {},
    modifier = Modifier,
    type = ModalType.Flexible,
    properties = DialogProperties(),
    shape = RoundedCornerShape(12.dp),
    topBar = { WantedDialogTopAppBar(title = "스크롤 모달") },
    lazyContent = {
        items(10) { index ->
            Text("항목 $index")
        }
    }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| onDismissRequest | () -> Unit | 모달 외부 클릭 등으로 닫힐 때 호출되는 콜백입니다. |
| modifier | Modifier | 외형 및 높이 조정 Modifier입니다. |
| type | ModalType | 모달의 유형입니다. |
| properties | DialogProperties | Compose Dialog 속성입니다. |
| shape | RoundedCornerShape | 모달의 모서리 둥글기입니다. |
| topBar | @Composable (() -> Unit)? | 상단 앱바 Slot입니다. |
| bottomBar | (@Composable () -> Unit)? | 하단 바 Slot입니다. |
| lazyContent | LazyListScope.() -> Unit | LazyColumn 기반 콘텐츠 Slot입니다. |

---
<br />
<br />

## Enum 설명

### ModalType
| 값 | 설명 |
|:---|:---|
| Flexible | 콘텐츠 기반으로 유동적인 크기의 모달입니다. |
| FixedWrapContent | 콘텐츠를 감싸는 높이 고정 모달입니다. |
| Fixed | 특정 높이를 지정한 고정 모달입니다. |
| FixedFullScreen | 전체 화면을 채우는 모달입니다. |
| FixedRatio | 화면 비율 기반의 고정 높이 모달입니다. |

### ModalSize
| 값 | 설명 |
|:---|:---|
| Small | (Deprecated) 작은 여백을 갖는 사이즈입니다. |
| Medium | 기본 사이즈로 20dp의 콘텐츠 패딩을 가집니다. |
| Large | 큰 콘텐츠 패딩과 여백을 제공하는 사이즈입니다. |
| XLarge | 매우 큰 여백으로 고해상도 UI에 적합합니다. |
| Custom | (Deprecated) 패딩 값 없이 구성된 커스텀 사이즈입니다. |

---
<br />
<br />

## Note
- `WantedModal`은 topBar와 bottomBar, 버튼 유무 등 다양한 형태의 레이아웃으로 확장 가능합니다.
- `WantedModalBottomSheet`는 시스템 시트 또는 커스텀 드래그 시트 중 선택적으로 사용됩니다.
