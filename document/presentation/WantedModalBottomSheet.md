---
title: ModalBottomSheet
description: 바텀 시트 모달 컴포넌트
image: /components/modal-bottom-sheet/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedModalBottomSheet

### 개요
시스템 또는 사용자 정의 가능한 BottomSheet 모달 컴포넌트입니다. `ModalBottomSheet` 또는 `WantedDraggableModalBottomSheet` 중 적절한 컴포저블을 선택해 표시합니다.

### 사용 예시
```kotlin
WantedModalBottomSheet(
    isShow = true,
    onDismissRequest = {},
    topBar = { Text("상단 바") },
    bottomBar = { Text("하단 바") },
    content = { Text("본문") }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| isShow | Boolean | 모달의 표시 여부입니다. |
| onDismissRequest | () -> Unit | 닫힘 콜백입니다. |
| modifier | Modifier | 외형을 조정합니다. |
| background | Color | 모달 배경 색상입니다. |
| type | ModalType | 모달 유형 (Flexible, Fixed 등)입니다. |
| modalSize | ModalSize | 콘텐츠 영역의 패딩 및 크기 설정입니다. |
| dismissOnClickOutside | Boolean | 외부 클릭 시 닫힐지 여부를 결정합니다. |
| topBar | @Composable (() -> Unit)? | 상단 바 Slot입니다. |
| bottomBar | @Composable (() -> Unit)? | 하단 바 Slot입니다. |
| content | @Composable () -> Unit | 본문 콘텐츠 Slot입니다. |

---

## DragHandle

### 개요
드래그 가능한 모달 시트 상단에 표시되는 핸들입니다. 색상, 모양 등 커스터마이징이 가능합니다.

### 사용 예시
```kotlin
WantedModalDefaults.DragHandle()
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 핸들의 외형을 조정하는 Modifier입니다. |
| color | Color | 핸들의 배경 색상입니다. 기본은 배경 색상입니다. |
| shape | Shape | 핸들의 형태입니다. 기본은 extraLarge입니다. |

---

## Enum 설명

### ModalType
| 값 | 설명 |
|:---|:---|
| Flexible | 콘텐츠 기반의 유동적인 높이의 시트입니다. |
| FixedWrapContent | 콘텐츠 크기만큼 wrap 되는 고정 시트입니다. |
| Fixed | 지정된 높이로 설정된 고정 시트입니다. |
| FixedFullScreen | 전체 화면을 채우는 시트입니다. |
| FixedRatio | 화면 비율 기반으로 설정된 고정 시트입니다. |

### ModalSize
| 값 | 설명 |
|:---|:---|
| Small | (Deprecated) 작은 여백과 패딩을 가진 사이즈입니다. |
| Medium | 기본 콘텐츠 패딩을 제공하는 중간 크기입니다. |
| Large | 넓은 여백과 콘텐츠 영역을 가진 크기입니다. |
| XLarge | 매우 큰 크기로 고해상도 환경에 적합합니다. |
| Custom | (Deprecated) 패딩 설정이 없는 사용자 정의 사이즈입니다. |
