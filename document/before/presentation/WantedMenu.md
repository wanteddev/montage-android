---
title: Menu
description: 다양한 섹션과 스타일을 지원하는 메뉴 및 메뉴 다이얼로그 컴포저블 모음
image: /components/menu/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedMenu

### 개요
섹션별 타이틀 및 항목으로 구성된 기본 메뉴 리스트입니다.
패딩, 최대 높이, 배경 설정이 포함되어 있으며, 커스텀 컴포저블로 섹션 타이틀 및 아이템을 구성할 수 있습니다.

### 사용 예시
```kotlin
WantedMenu(
    sectionCount = 2,
    itemCount = { 3 },
    onBindSectionTitle = { Text("섹션 제목") },
    onBindSectionItem = { section, index -> Text("아이템 $index") }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| sectionCount | Int | 섹션의 총 개수입니다. |
| itemCount | (Int) -> Int | 각 섹션의 아이템 수를 반환합니다. |
| onBindSectionItem | @Composable (Int, Int) -> Unit | 각 항목을 그리는 컴포저블입니다. |
| modifier | Modifier | 메뉴 외형을 조정하는 Modifier입니다. |
| onBindSectionTitle | @Composable ((Int) -> Unit)? | 섹션 타이틀을 그리는 컴포저블입니다. nullable. |

---
<br />
<br />

## WantedMenuModal (단순 리스트)

### 개요
기본 문자열 리스트로 구성된 메뉴 모달입니다. 클릭 시 index와 값을 콜백으로 반환합니다.

### 사용 예시
```kotlin
WantedMenuModal(
    items = listOf("옵션1", "옵션2"),
    onDismissRequest = { },
    onClick = { index, value -> }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| items | List<String> | 표시할 문자열 리스트입니다. |
| onDismissRequest | () -> Unit | 다이얼로그 해제 시 호출되는 콜백입니다. |
| properties | DialogProperties | 다이얼로그 속성입니다. |
| onClick | (Int, String) -> Unit | 항목 클릭 시 콜백입니다. |

---
<br />
<br />

## WantedMenuModal (ListType 버전)

### 개요
리스트 타입에 따라 라디오 버튼, 체크박스를 포함할 수 있는 메뉴 모달입니다.

### 사용 예시
```kotlin
WantedMenuModal(
    items = listOf("A", "B"),
    listType = WantedMenuContract.ListType.Check,
    onDismissRequest = {},
    onClick = { index, value -> }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| items | List<String> | 메뉴 항목 리스트입니다. |
| onDismissRequest | () -> Unit | 다이얼로그 닫힘 콜백입니다. |
| properties | DialogProperties | 다이얼로그 속성입니다. |
| listType | ListType | 리스트 스타일입니다. (Normal, Radio, Check) |
| onClick | (Int, String) -> Unit | 항목 클릭 시 콜백입니다. |

---
<br />
<br />

## WantedMenuModal (커스텀 섹션)

### 개요
섹션 타이틀 및 항목을 직접 구성하는 고급형 메뉴 모달입니다.

### 사용 예시
```kotlin
WantedMenuModal(
    sectionCount = 2,
    itemCount = { 3 },
    onBindSectionItem = { section, index -> Text("항목") },
    onDismissRequest = { }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| sectionCount | Int | 섹션 수입니다. |
| itemCount | (Int) -> Int | 섹션별 아이템 수입니다. |
| onBindSectionItem | @Composable (Int, Int) -> Unit | 아이템 구성 함수입니다. |
| properties | DialogProperties | 다이얼로그 속성입니다. |
| onBindSectionTitle | @Composable ((Int) -> Unit)? | 섹션 타이틀 구성 함수입니다. nullable. |
| onDismissRequest | () -> Unit | 닫힘 콜백입니다. |

---
<br />
<br />

## Enum 설명

### ListType
| 값 | 설명 |
|:---|:---|
| Normal | 기본 텍스트 항목입니다. |
| Radio | 우측 라디오 버튼이 포함된 항목입니다. |
| Check | 좌측 체크박스가 포함된 항목입니다. |

---
<br />
<br />

## Note
- `WantedCell`, `WantedRadioButton`, `WantedCheckBox` 컴포저블과 조합하여 사용됩니다.
- 모든 모달은 `Dialog` 기반으로 구성되어 있습니다.
