---
title: Tab
description: 고정 및 스크롤 가능한 탭 컴포넌트와 구성 요소
image: /components/tab/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedTabRow

### 개요
`WantedTabRow`는 고정형 탭 레이아웃을 제공합니다. 항목 수에 따라 동일한 너비로 배치되며, `WantedTabItem`을 내부에 포함하여 클릭 및 선택 상태를 표현합니다.

### 사용 예시
```kotlin
WantedTabRow(
    itemSize = 3,
    selectedTabIndex = 1,
    content = { index -> "탭$index" },
    onClickItem = { index -> ... }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| itemSize | Int | 탭 항목 개수입니다. |
| selectedTabIndex | Int | 현재 선택된 탭 인덱스입니다. |
| modifier | Modifier | 배치 및 외형 설정입니다. |
| tabSize | TabSize | 탭 텍스트 크기입니다. 기본값은 Medium입니다. |
| onClickItem | (Int) -> Unit | 탭 클릭 시 호출되는 콜백입니다. |
| content | (Int) -> String | 각 탭의 텍스트를 반환하는 함수입니다. |

---
<br />
<br />

## WantedScrollableTabRow

### 개요
`WantedScrollableTabRow`는 가로로 스크롤 가능한 탭 레이아웃입니다. 탭 수가 많을 때 적합하며, 좌우 gradient 및 우측 아이콘 슬롯도 지원합니다.

### 사용 예시
```kotlin
WantedScrollableTabRow(
    itemCount = 5,
    selectedTabIndex = 2,
    content = { index -> "탭$index" }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| itemCount | Int | 탭 항목 개수입니다. |
| selectedTabIndex | Int | 현재 선택된 탭 인덱스입니다. |
| modifier | Modifier | 외형 설정용 Modifier입니다. |
| tabSize | TabSize | 탭 크기 설정입니다. 기본값은 Medium입니다. |
| horizontalPadding | Boolean | 양쪽 여백 포함 여부입니다. 기본값 true. |
| isLeftGradient | Boolean | 좌측 그라디언트 표시 여부입니다. 기본값 true. |
| isRightGradient | Boolean | 우측 그라디언트 표시 여부입니다. 기본값 true. |
| gradientColor | Color | 그라디언트 색상입니다. 기본값은 CompositionLocal 값입니다. |
| scrollState | ScrollState | 외부 스크롤 상태 전달입니다. |
| onClickItem | (Int) -> Unit | 탭 클릭 시 콜백입니다. |
| rightIcon | (@Composable (Dp) -> Unit)? | 우측 아이콘 슬롯입니다. |
| content | (Int) -> String | 탭 라벨 텍스트입니다. |

---
<br />
<br />

## WantedTabItem

### 개요
`WantedTabItem`은 단일 탭 항목으로, 선택 상태와 텍스트 레이아웃 처리 등을 포함합니다.

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| tabSize | TabSize | 탭 크기 설정입니다. |
| title | String | 탭에 표시될 텍스트입니다. |
| isSelect | Boolean | 선택 상태입니다. |
| modifier | Modifier | 외형 설정입니다. |
| onTextLayout | ((TextLayoutResult) -> Unit)? | 텍스트 레이아웃 콜백입니다. |
| onClick | () -> Unit | 탭 클릭 시 호출됩니다. |

---
<br />
<br />

## Enum 설명

### TabSize
| 값 | 설명 |
|:---|:---|
| Small | body2Bold 스타일의 작은 탭입니다. |
| Medium | headline2Bold 스타일의 중간 탭입니다. |
| Large | heading2Bold 스타일의 큰 탭입니다. |

---
<br />
<br />

## WantedTabGradationColorCompositionLocal

### 개요
`WantedTabGradationColorCompositionLocal`은 스크롤 가능한 탭의 그라디언트 색상을 설정하기 위한 CompositionLocal입니다.

### 사용 예시
```kotlin
CompositionLocalProvider(LocalTabGradationColor provides Color.Red) {
    WantedScrollableTabRow(...) 
}
```

### 프로퍼티
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| current | Color | 현재 설정된 그라디언트 색상입니다. |

---
<br />
<br />

## Note
- `tabSize`에 따라 글자 크기 및 패딩이 자동 조절됩니다.
- 스크롤 가능한 탭은 화면 너비를 초과할 경우 자동으로 scroll 가능하게 렌더링됩니다.
- `WantedTabGradationColorCompositionLocal`로 색상을 설정하면 다크모드, 테마에 맞춘 효과가 가능합니다.
