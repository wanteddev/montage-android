---
title: AutoComplete
description: 섹션별 항목 및 직접 입력 영역을 포함한 자동완성 드롭다운 컴포저블
image: /components/auto-complete/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedAutoComplete

### 개요
`ExposedDropdownMenuBoxScope` 내에서 사용되는 자동완성 드롭다운 컴포저블입니다.

섹션별 리스트를 구성하고 섹션 타이틀을 표시하며, 드롭다운 상단 또는 하단에 직접 입력 컴포저블을 배치할 수 있습니다.
스크롤 위치에 따라 현재 섹션 타이틀을 고정 표시하는 기능을 포함합니다.

### 사용 예시
```kotlin
ExposedDropdownMenuBox {
    WantedAutoComplete(
        expended = true,
        onExpandedChange = { /* 확장 상태 변경 */ },
        sectionCount = 2,
        sectionTitle = { section -> "섹션 $section" },
        sectionItemCount = { section -> 3 },
        sectionItem = { section, index ->
            Text("아이템 $index")
        }
    )
}
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| expended | Boolean | 드롭다운 확장 여부입니다. |
| onExpandedChange | (Boolean) -> Unit | 확장 상태가 변경될 때 호출되는 콜백입니다. |
| sectionCount | Int | 표시할 섹션의 총 개수입니다. |
| sectionItemCount | (Int) -> Int | 각 섹션별 아이템 수를 반환하는 함수입니다. |
| sectionItem | @Composable (Int, Int) -> Unit | 섹션별 아이템 컴포저블입니다. |
| modifier | Modifier | 드롭다운 전체에 적용할 Modifier입니다. |
| containerColor | Color | 드롭다운 배경 색상입니다. |
| sectionTitleHorizontalPadding | Dp | 섹션 타이틀 좌우 패딩입니다. |
| sectionTitle | ((Int) -> String)? | 섹션별 타이틀 텍스트를 반환하는 함수입니다. nullable. |
| topDirectInput | @Composable (() -> Unit)? | 드롭다운 상단 고정 영역입니다. nullable. |
| bottomDirectInput | @Composable (() -> Unit)? | 드롭다운 하단 영역입니다. nullable. |

---
<br />
<br />

## Note
- `sectionOffsets`를 활용해 스크롤 위치에 따라 현재 섹션을 추적합니다.
- 섹션 타이틀은 상단 고정 영역으로 표현됩니다.
- 입력 영역은 드롭다운 내부의 상단/하단 위치에 고정 배치할 수 있습니다.
