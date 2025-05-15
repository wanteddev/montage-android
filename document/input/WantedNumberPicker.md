---
title: NumberPicker
description: 숫자 리스트 또는 범위 기반으로 항목을 선택할 수 있는 휠형 NumberPicker입니다.
image: /components/number-picker/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedNumberPicker

### 개요
`WantedNumberPicker`는 숫자를 선택할 수 있는 휠형 컴포넌트입니다.  
지정한 범위(start~end) 또는 커스텀 숫자 리스트(List<Int>)를 기반으로 항목을 생성할 수 있으며,  
선택된 항목은 콜백으로 전달됩니다. 내부적으로 `WantedStringPicker`를 사용하여 시각적 효과와 함께 작동합니다.

---
<br />
<br />

## 기본형 - start~end 범위 기반

### 사용 예시
```kotlin
WantedNumberPicker(
    start = 1,
    end = 12,
    step = 1,
    selectedValue = 5,
    onSelect = { index, value, enabled ->
        // 선택된 숫자 처리
    }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| start | Int | 시작 숫자입니다. |
| end | Int | 종료 숫자입니다. |
| step | Int | 증가 단위입니다. |
| modifier | Modifier | 외형을 설정하는 Modifier입니다. |
| itemList | List<Int> | 자동 생성된 숫자 리스트입니다. |
| selectedValue | Int | 기본 선택값입니다. |
| enableMinValue | Int | 선택 가능한 최소값입니다. |
| enableMaxValue | Int | 선택 가능한 최대값입니다. |
| pagerState | PagerState | 스크롤 상태입니다. |
| textStyle | TextStyle | 텍스트 스타일입니다. |
| itemSize | Dp | 항목 하나의 높이입니다. |
| visibleCount | Int | 한 화면에 보이는 항목 개수입니다. |
| userScrollEnabled | Boolean | 스크롤 허용 여부입니다. |
| onSelect | (Int, Int, Boolean) -> Unit | 선택 시 호출되는 콜백입니다. (index, value, enabled) |

---
<br />
<br />

## 오버로드형 - List<Int> 직접 지정

### 사용 예시
```kotlin
WantedNumberPicker(
    itemList = listOf(0, 15, 30, 45),
    selectedIndex = 2,
    onSelect = { index, enabled ->
        // index 처리
    }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형을 설정하는 Modifier입니다. |
| itemList | List<Int> | 직접 지정한 숫자 리스트입니다. |
| selectedIndex | Int | 초기 선택 인덱스입니다. |
| enableStartIndex | Int | 선택 가능 시작 인덱스입니다. |
| enableEndIndex | Int | 선택 가능 종료 인덱스입니다. |
| pagerState | PagerState | 스크롤 상태입니다. |
| textStyle | TextStyle | 텍스트 스타일입니다. |
| itemSize | Dp | 항목 하나의 높이입니다. |
| visibleCount | Int | 보이는 항목 개수입니다. |
| onSelect | (Int, Boolean) -> Unit | 선택 시 호출되는 콜백입니다. (index, enabled) |

---
<br />
<br />

## Note
- `WantedStringPicker`를 내부적으로 활용하여 3D 회전, 크기 변화, 햅틱 피드백 등 시각적 효과를 제공합니다.
- 리스트 항목은 숫자 외 다른 형태로 변환하여 사용할 수도 있습니다 (ex. `itemList.map { "$it 분" }`).
