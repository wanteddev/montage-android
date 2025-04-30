---
title: DatePickerWheel
description: 연도와 월을 휠 형태로 선택할 수 있는 날짜 선택 모달입니다.
image: /components/date-picker-wheel/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedDatePickerWheel

### 개요
`WantedDatePickerWheel`은 사용자에게 연도와 월을 선택할 수 있는 휠 형태의 날짜 선택 모달을 제공합니다.  
각 항목은 NumberPicker 형태로 구성되며, 선택된 연도와 월은 콜백을 통해 반환됩니다.  
선택 가능한 범위와 조건은 `WantedDatePickerWheelDefault` 객체를 통해 세부적으로 설정할 수 있습니다.

### 사용 예시
```kotlin
WantedDatePickerWheel(
    title = "날짜 선택",
    confirm = "확인",
    cancel = "취소",
    selectedYear = 2023,
    selectedMonth = 1,
    onSelect = { year, month -> 
        // 연도 및 월 처리
    },
    onDismissRequest = {
        // 다이얼로그 종료 처리
    }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| title | String | 다이얼로그 상단에 표시될 제목 텍스트입니다. |
| confirm | String | 확인 버튼 텍스트입니다. |
| cancel | String | 취소 버튼 텍스트입니다. |
| selectedYear | Int | 초기 선택된 연도입니다. |
| selectedMonth | Int | 초기 선택된 월입니다. |
| modifier | Modifier | 외형을 설정하는 Modifier입니다. |
| default | WantedDatePickerWheelDefault | 날짜 휠 설정을 지정하는 기본값 객체입니다. |
| onSelect | (year: Int, month: Int) -> Unit | 날짜 선택 후 확인 버튼 클릭 시 호출되는 콜백입니다. |
| onDismissRequest | () -> Unit | 다이얼로그가 닫힐 때 호출되는 콜백입니다. |

---

## WantedDatePickerWheelDefaults.getDefault

### 개요
날짜 선택 휠에 사용할 수 있는 기본값을 설정할 수 있는 함수입니다.  
연도 및 월 범위, 비활성 날짜 표시 여부 등을 세부적으로 제어할 수 있으며, `WantedDatePickerWheelDefault` 객체를 반환합니다.

### 사용 예시
```kotlin
val wheelDefault = WantedDatePickerWheelDefaults.getDefault(
    startYear = 2000,
    endYear = 2030,
    enableMinYear = 2010,
    enableMaxYear = 2025,
    enableMinMonth = 3,
    enableMaxMonth = 11,
    isHideDisableDate = true
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| startYear | Int | 휠 선택기에서 표시할 시작 연도입니다. 기본값은 1900입니다. |
| endYear | Int | 휠 선택기에서 표시할 종료 연도입니다. `startYear`보다 작으면 자동 보정됩니다. 기본값은 2100입니다. |
| enableMinYear | Int | 선택 가능한 최소 연도입니다. 기본값은 1900입니다. |
| enableMaxYear | Int | 선택 가능한 최대 연도입니다. `enableMinYear`보다 작으면 자동 보정됩니다. 기본값은 2100입니다. |
| enableMinMonth | Int | 선택 가능한 최소 월입니다. 기본값은 1입니다. |
| enableMaxMonth | Int | 선택 가능한 최대 월입니다. `enableMinMonth`보다 작으면 자동 보정됩니다. 기본값은 12입니다. |
| isHideDisableDate | Boolean | 선택 불가능한 날짜를 숨길지 여부입니다. 기본값은 false입니다. |

---

## Note
- `WantedDatePickerWheelDefault`를 통해 사용 가능한 연도/월 범위, 비활성 날짜 숨김 여부 등을 세부 설정할 수 있습니다.
- 연도/월 선택 상태에 따라 확인 버튼의 활성 상태가 결정됩니다.
