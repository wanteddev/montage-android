---
title: SegmentedControlSolid
description: 선택 가능한 항목을 고정 배경과 애니메이션 Knob으로 표시하는 Solid 스타일 Segmented Control입니다.
image: /components/segmented-control-solid/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedSegmentedControlSolid

### 개요
`WantedSegmentedControlSolid`는 고정된 배경과 애니메이션 되는 Knob을 통해 항목 선택을 표시하는 Segmented Control입니다.  
각 항목은 `SolidItem` 형태로 구성되며, 커스텀 아이템도 지원합니다.  
선택된 항목은 `Knob`이 슬라이딩되며 강조되며, 선택 인덱스는 콜백으로 반환됩니다.

---
<br />
<br />

## 텍스트 리스트 기반 사용

### 사용 예시
```kotlin
val items = listOf("전체", "읽음", "안읽음")
var selectedIndex by remember { mutableIntStateOf(0) }

WantedSegmentedControlSolid(
    items = items,
    selectedIndex = selectedIndex,
    onClick = { selectedIndex = it }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| items | List<String> | 항목에 표시할 텍스트 리스트입니다. |
| selectedIndex | Int | 현재 선택된 항목 인덱스입니다. |
| modifier | Modifier | 외형을 설정하는 Modifier입니다. |
| size | SegmentedSize | 항목의 크기를 설정합니다. Small / Medium / Large 중 선택합니다. |
| onClick | (Int) -> Unit | 항목 클릭 시 호출되는 콜백입니다. |

---
<br />
<br />

## 슬롯 기반 커스텀 항목 사용

### 사용 예시
```kotlin
WantedSegmentedControlSolid(
    itemCount = 3,
    selectedIndex = selectedIndex,
    item = { index ->
        WantedSegmentedControlSolidItem(
            title = "옵션 $index",
            isSelected = selectedIndex == index
        )
    },
    onClick = { index -> selectedIndex = index }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| itemCount | Int | 항목의 총 개수입니다. |
| selectedIndex | Int | 현재 선택된 인덱스입니다. |
| item | (Int) -> Unit | 개별 항목을 그리는 Composable 슬롯입니다. |
| modifier | Modifier | 외형을 설정하는 Modifier입니다. |
| size | SegmentedSize | 항목의 크기입니다. |
| onClick | (Int) -> Unit | 항목 클릭 시 호출되는 콜백입니다. |

---
<br />
<br />

## WantedSegmentedControlSolidItem

### 개요
`SegmentedControlSolid`의 개별 항목을 구성하는 기본 아이템입니다.  
선택 상태에 따라 텍스트 색상이 강조되며, 필요 시 아이콘도 함께 표시할 수 있습니다.

### 사용 예시
```kotlin
WantedSegmentedControlSolidItem(
    title = "알림",
    isSelected = true,
    icon = {
        Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = null
        )
    }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| title | String | 항목에 표시할 텍스트입니다. |
| isSelected | Boolean | 항목이 선택된 상태인지 여부입니다. |
| modifier | Modifier | 외형을 조정하는 Modifier입니다. |
| icon | (() -> Unit)? | 항목 왼쪽에 표시할 선택적 아이콘 Composable입니다. |

---
<br />
<br />

## Note
- Knob은 선택 인덱스에 따라 부드럽게 애니메이션 되며, 그림자와 배경 색상이 함께 적용됩니다.
- `WantedSegmentedControlSolidItem`을 직접 정의하거나, slot 형태로 커스터마이징 가능합니다.
