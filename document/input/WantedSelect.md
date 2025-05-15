---
title: Select
description: 다양한 단일 및 다중 선택 UI를 제공하는 셀렉트 컴포넌트
image: /components/select/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedSelect

### 개요
WantedSelect는 단일 또는 다중 항목을 선택할 수 있는 Compose 기반 셀렉트 컴포넌트입니다. 사용자 인터페이스는 하단 시트 다이얼로그 형식으로 열리며, 선택된 항목은 Chip 또는 텍스트 형태로 표시됩니다. 단일 선택, 다중 선택, 커스텀 렌더링 방식, 오류 표시 등 다양한 사용 시나리오를 지원합니다.

### 사용 예시
```kotlin
WantedSelect(
    value = "선택값",
    title = "제목",
    selectValueList = listOf("옵션1", "옵션2"),
    onSelect = { 선택된값 -> ... }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| value | String | 현재 선택된 문자열 값입니다. |
| modifier | Modifier | 컴포넌트의 외형을 설정합니다. |
| title | String? | 상단에 표시할 제목입니다. |
| description | String? | 하단에 표시할 설명 텍스트입니다. |
| placeHolder | String | 선택 전 표시될 안내 문구입니다. |
| confirmText | String | 확인 버튼에 표시될 텍스트입니다. |
| isRequiredBadge | Boolean | 필수 입력 표시 여부입니다. |
| negative | Boolean | 오류 상태 표시 여부입니다. |
| focused | Boolean | 포커스 강조 여부입니다. |
| enabled | Boolean | 활성화 여부입니다. |
| selectValueList | List<String> | 선택 가능한 항목 문자열 리스트입니다. |
| selectedValue | String? | 초기 선택된 항목입니다. |
| bottomSheetType | BottomSheetDialogType | 다이얼로그 형태입니다. |
| selectType | SelectType | 선택 UI 방식입니다. |
| background | Color | 배경 색상입니다. |
| onClick | () -> Unit | 셀렉트 클릭 시 콜백입니다. |
| onSelect | (String) -> Unit | 항목 선택 완료 시 콜백입니다. |
| leadingIcon | @Composable (() -> Unit)? | 왼쪽 아이콘 Slot입니다. |

---
<br />
<br />

## WantedSelect (SelectData 기반)

### 사용 예시
```kotlin
WantedSelect(
    selectData = WantedSelectData(text = "디자인"),
    selectDataList = listOf(
        WantedSelectData(text = "디자인"),
        WantedSelectData(text = "기획")
    ),
    onSelectData = { 선택항목 -> ... }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| selectData | WantedSelectData? | 현재 선택된 항목입니다. |
| selectDataList | List<WantedSelectData> | 선택 가능한 항목 리스트입니다. |
| selectedData | WantedSelectData? | 초기 선택된 항목입니다. |
| onSelectData | (WantedSelectData) -> Unit | 선택 시 콜백입니다. |
| 기타 | 동일 | 일반 `WantedSelect` 파라미터와 동일 |

---
<br />
<br />

## WantedSelect (다중 선택용)

### 사용 예시
```kotlin
WantedSelect(
    selectedDataList = listOf(WantedSelectData("디자인")),
    selectDataList = listOf(WantedSelectData("디자인"), WantedSelectData("기획")),
    onSelectDataList = { 선택항목들 -> ... }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| selectedDataList | List<WantedSelectData> | 현재 선택된 항목 리스트입니다. |
| negativeDataList | List<WantedSelectData> | 오류 표시 항목 리스트입니다. |
| overflow | Boolean | Chip 줄바꿈 여부입니다. |
| render | MultiSelectRender | 렌더링 방식입니다. |
| onDeleteData | (WantedSelectData) -> Unit | 삭제 콜백입니다. |
| onSelectDataList | (List<WantedSelectData>) -> Unit | 선택 완료 콜백입니다. |
| 기타 | 동일 | 일반 `WantedSelect` 파라미터와 동일 |

---
<br />
<br />

## WantedSelectWithString

### 사용 예시
```kotlin
WantedSelectWithString(
    selectedValueList = listOf("기획"),
    selectValueList = listOf("기획", "디자인"),
    onSelectList = { 선택값들 -> ... }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| selectedValueList | List<String> | 선택된 문자열 리스트입니다. |
| negativeList | List<String> | 오류 항목 리스트입니다. |
| onDelete | (String) -> Unit | 삭제 콜백입니다. |
| onSelectList | (List<String>) -> Unit | 선택 완료 콜백입니다. |
| 기타 | 동일 | `WantedSelect`의 다중 선택 파라미터와 동일 |

---
<br />
<br />

## Enum 설명

### SelectType

| 값 | 설명 |
|:---|:---|
| CheckMark | 체크마크로 선택 표시 (단일 선택) |
| CheckBox | 체크박스로 선택 표시 (다중 선택) |
| Radio | 라디오 버튼으로 선택 표시 (단일 선택) |

### MultiSelectRender

| 값 | 설명 |
|:---|:---|
| Chip | 선택된 항목을 Chip 형태로 표시 |
| Text | 선택된 항목을 텍스트로 나열 |

---
<br />
<br />

## WantedSelectData 설명

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| id | String | 항목의 고유 ID입니다. |
| text | String | 사용자에게 표시할 텍스트입니다. |
| iconUrl | String | 아이콘 이미지 URL입니다. |
| iconRes | Int | Drawable 리소스 ID입니다. |
| tint | Int | 아이콘에 적용할 색상 리소스 ID입니다. |
| any | Any? | 부가 데이터를 위한 필드입니다. |

### 예시
```kotlin
val item = WantedSelectData(
    id = "01",
    text = "디자인",
    iconUrl = "https://icon.url",
    iconRes = R.drawable.ic_design,
    tint = R.color.primary_normal,
    any = DesignCategory.UI
)
```

---
<br />
<br />

## Note
- `WantedSelect`는 단일 선택/다중 선택 및 플레이스홀더, 오류, 포커스, 아이콘 등을 유연하게 커스터마이징할 수 있습니다.
- `WantedSelectWithString`은 문자열 기반 API로 진입 장벽을 낮춰 줍니다.
