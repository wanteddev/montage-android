---
title: Category
description: 선택형 카테고리 UI 컴포넌트 및 관련 설정
image: /components/category/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedCategory

### 개요
`WantedCategory`는 사용자가 선택 가능한 태그 형태의 액션칩들을 가로 스크롤 방식으로 보여주는 컴포넌트입니다. 항목 리스트를 직접 넘기거나 LazyListScope로 원하는 레이아웃을 구성할 수 있으며, 선택 상태, 사이즈, 여백, 그라디언트 등 다양한 옵션을 제공합니다.

### 사용 예시
```kotlin
// 리스트 기반 구성
WantedCategory(
    itemList = listOf("개발", "디자인"),
    selectedList = listOf("개발"),
    onClick = { tag, selected -> ... }
)

// LazyListScope 기반 구성
WantedCategory {
    items(5) {
        WantedActionChip(...) 
    }
}
```

### 파라미터
#### WantedCategory (itemList 기반)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| itemList | List<String> | 항목으로 표시할 문자열 리스트입니다. |
| selectedList | List<String> | 현재 선택된 항목 리스트입니다. |
| modifier | Modifier | 외형 설정용 Modifier입니다. |
| state | LazyListState | 내부 LazyRow 스크롤 상태입니다. |
| size | Size | 항목의 사이즈 및 패딩 설정 enum입니다. |
| horizontalPadding | Boolean | 좌우 여백 포함 여부입니다. |
| isVerticalPadding | Boolean | 상하 여백 포함 여부입니다. |
| isAlternative | Boolean | 선택 시 스타일을 Outlined로 변경할지 여부입니다. |
| gradientColor | Color | 좌우 그라디언트 배경 색상입니다. |
| rightIcon | (@Composable (Dp) -> Unit)? | 우측 끝에 표시할 아이콘 슬롯입니다. |
| onClick | (String, Boolean) -> Unit | 항목 클릭 시 선택 여부와 함께 콜백이 호출됩니다. |

#### WantedCategory (LazyListScope 기반)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| content | LazyListScope.() -> Unit | 직접 항목을 구성하는 LazyListScope 블록입니다. |
| modifier | Modifier | 외형 설정용 Modifier입니다. |
| state | LazyListState | 내부 스크롤 상태입니다. |
| size | Size | 항목 크기 설정 enum입니다. |
| horizontalPadding | Boolean | 좌우 여백 포함 여부입니다. |
| isVerticalPadding | Boolean | 상하 여백 포함 여부입니다. |
| gradientColor | Color | 그라디언트 배경 색상입니다. |
| rightIcon | (@Composable (Dp) -> Unit)? | 우측 아이콘 슬롯입니다. |

---

## Enum 설명

### Size
| 값 | 설명 |
|:---|:---|
| Small | 세로 패딩이 4.dp, 수평 간격이 8.dp인 작은 칩입니다. |
| Medium | 세로 패딩이 6.dp, 수평 간격이 10.dp인 보통 크기입니다. |
| Large | 세로 패딩이 8.dp, 수평 간격이 12.dp인 큰 칩입니다. |

---

## WantedCategoryGradationColorCompositionLocal

### 개요
`WantedCategoryGradationColorCompositionLocal`은 카테고리 좌우 그라디언트 색상을 지정하는 CompositionLocal입니다.

### 사용 예시
```kotlin
CompositionLocalProvider(LocalCategoryGradationColor provides Color.Red) {
    WantedCategory(...)
}
```

### 프로퍼티
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| current | Color | 현재 CompositionLocal에 설정된 색상 값입니다. |

---

## Note
- `onClick` 콜백은 선택 상태를 Boolean 값으로 함께 전달하므로 단일/다중 선택 처리가 모두 가능합니다.
- `gradientColor`는 리스트 좌우 fade 효과 배경으로 사용됩니다.
- `LocalWantedCategoryGradationColor`는 기본 그라디언트 색상을 설정합니다.
