
# Accordion

## WantedAccordion

### 개요  
아코디언 형태로 확장 및 축소가 가능한 UI 컴포넌트입니다.


### 사용 예시
```kotlin
WantedAccordion(
    modifier = Modifier,
    title = "제목",
    description = "설명",
    isExpanded = false,
    onChangeExpanded = { expanded -> },
    content = {
        Text("확장 콘텐츠")
    }
)
```

### 파라미터

| 이름 | 타입 | 설명 |
|:---|:---|:---|
| title | String | 아코디언 헤더에 표시될 제목 텍스트입니다. |
| modifier | Modifier | 레이아웃 외형 및 스타일 조정을 위한 Modifier입니다. |
| titleMaxLine | Int | 제목 텍스트의 최대 줄 수입니다. 기본값은 `Int.MAX_VALUE`입니다. |
| description | String? | 제목 하단에 표시될 부가 설명입니다. 선택 사항입니다. |
| titleStyle | TextStyle | 제목 텍스트에 적용할 스타일입니다. |
| descriptionStyle | TextStyle | 설명 텍스트에 적용할 스타일입니다. |
| isExpanded | Boolean | 현재 아코디언이 확장 상태인지 여부입니다. |
| fillWidth | Boolean | 콘텐츠가 가로 전체 너비를 차지할지 여부입니다. |
| divider | Boolean | 아코디언 하단에 Divider를 표시할지 여부입니다. |
| verticalPadding | VerticalPadding | 제목 영역의 수직 패딩을 설정합니다. |
| leadingIcon | @Composable (() -> Unit)? | 제목 좌측에 표시할 아이콘입니다. 선택 사항입니다. |
| trail | @Composable () -> Unit | 제목 우측에 표시할 트레일 아이콘입니다. 기본값은 확장/축소 화살표입니다. |
| content | @Composable (() -> Unit)? | 아코디언 확장 시 표시할 추가 콘텐츠입니다. 선택 사항입니다. |
| onChangeExpanded | (Boolean) -> Unit | 확장/축소 상태 변경 시 호출되는 콜백입니다. |

---

## Enum 설명

### VerticalPadding

| 값 | 설명 |
|:---|:---|
| Padding16 | 헤더 영역에 16dp의 수직 패딩을 적용합니다. |
| Padding12 | 헤더 영역에 12dp의 수직 패딩을 적용합니다. |
| Padding8 | 헤더 영역에 8dp의 수직 패딩을 적용합니다. |

---

## Note
- 아코디언 형태로 콘텐츠를 접거나 펼칠 수 있어 리스트, FAQ, 설정 화면 등 다양한 UI에 활용할 수 있습니다.
- trail 파라미터를 사용해 커스텀 아이콘으로 확장/축소 표시를 변경할 수 있습니다.

## SeeAlso
- `WantedAccordionHeader`
- `WantedAccordionContract.VerticalPadding`
- `WantedAccordionTrailArrowIcon`
