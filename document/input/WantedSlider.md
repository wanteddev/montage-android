---
title: Slider
description: 단일 및 범위 선택 슬라이더 컴포넌트
image: /components/slider/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedSlider

### 개요
WantedSlider는 단일 값 또는 범위를 선택할 수 있는 슬라이더 컴포넌트입니다. 단일값을 위한 Slider와 범위 선택을 위한 RangeSlider 두 가지 버전을 제공합니다. UI 구성에 따라 헤더나 라벨, 슬라이더 색상, 활성화 상태 등을 설정할 수 있습니다.

### 사용 예시
```kotlin
// 단일 값 선택
WantedSlider(
    value = 30f,
    valueRange = 0f..100f,
    onValueChange = { value -> /* 처리 */ }
)

// 범위 선택
WantedSlider(
    value = 10f..90f,
    valueRange = 0f..100f,
    onValueChange = { range -> /* 처리 */ }
)
```

### 파라미터

### WantedSlider (단일 값)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| value | Float | 현재 선택된 값입니다. |
| valueRange | ClosedFloatingPointRange<Float> | 선택 가능한 값의 범위입니다. |
| modifier | Modifier | 슬라이더의 레이아웃과 스타일을 설정합니다. |
| header | String | 상단에 표시할 텍스트입니다. |
| label | String | 하단에 표시할 라벨 텍스트입니다. |
| enabled | Boolean | 슬라이더 활성화 여부입니다. |
| onValueChange | (Float) -> Unit | 값이 변경될 때 호출되는 콜백입니다. |

### WantedSlider (범위 값)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| value | ClosedFloatingPointRange<Float> | 현재 선택된 값의 범위입니다. |
| valueRange | ClosedFloatingPointRange<Float> | 선택 가능한 값의 최소/최대 범위입니다. |
| modifier | Modifier | 슬라이더의 레이아웃과 스타일을 설정합니다. |
| header | String | 상단에 표시할 텍스트입니다. |
| labelMin | String | 하단에 표시할 최소값 라벨 텍스트입니다. |
| labelMax | String | 하단에 표시할 최대값 라벨 텍스트입니다. |
| enabled | Boolean | 슬라이더 활성화 여부입니다. |
| onValueChange | (ClosedFloatingPointRange<Float>) -> Unit | 범위가 변경될 때 호출되는 콜백입니다. |

---
<br />
<br />

## Note
- 내부적으로 `WantedRangeSlider`를 사용하여 UI와 동작을 구성합니다.
- 단일 값 슬라이더는 내부적으로 `value = 0f..value` 형태로 구성됩니다.
- `header`, `labelMin`, `labelMax`는 UI 표현에만 사용됩니다.
