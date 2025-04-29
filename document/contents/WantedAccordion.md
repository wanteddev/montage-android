# Accordion

## WantedAccordion

### 사용 예시
```kotlin
WantedAccordion(
    modifier = Modifier,
    title = "제목",
    description = "상세 설명",
    isExpanded = false,
    onChangeExpanded = { expanded -> },
    content = {
        Text("추가 콘텐츠")
    }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 아코디언의 레이아웃 및 스타일을 조정합니다. |
| title | String | 아코디언 헤더에 표시될 텍스트입니다. |
| titleMaxLine | Int | 제목 텍스트의 최대 줄 수를 지정합니다. 기본값은 Int.MAX_VALUE입니다. |
| description | String? | 제목 하단에 표시될 부가 설명 텍스트입니다. 선택 사항입니다. |
| titleStyle | TextStyle | 제목에 적용할 텍스트 스타일입니다. |
| descriptionStyle | TextStyle | 설명에 적용할 텍스트 스타일입니다. |
| isExpanded | Boolean | 현재 아코디언이 확장(expanded) 상태인지 여부를 지정합니다. |
| fillWidth | Boolean | 내용이 가로 전체를 채울지 여부를 결정합니다. |
| divider | Boolean | 하단에 Divider를 표시할지 여부를 설정합니다. |
| verticalPadding | VerticalPadding | 헤더 영역의 수직 패딩을 설정합니다. |
| content | @Composable (() -> Unit)? | 확장 시 표시할 추가적인 Composable 콘텐츠입니다. 선택 사항입니다. |
| onChangeExpanded | (Boolean) -> Unit | 확장/축소 상태가 변경될 때 호출되는 콜백입니다. |
| leadingIcon | @Composable (() -> Unit)? | 제목 왼쪽에 표시할 아이콘입니다. 선택 사항입니다. |
| trail | @Composable () -> Unit | 제목 오른쪽에 표시할 트레일 아이콘입니다. 기본은 확장/축소를 나타내는 화살표입니다. |

---

## Enum 설명

### VerticalPadding
| 값 | 설명 |
|:---|:---|
| Padding16 | 수직 패딩을 16dp로 설정합니다. |
| Padding12 | 수직 패딩을 12dp로 설정합니다. |
| Padding8 | 수직 패딩을 8dp로 설정합니다. |

---

## Note
- 아코디언 형태로 콘텐츠를 접거나 펼칠 수 있어 리스트, FAQ, 설정 화면 등 다양한 UI에 활용할 수 있습니다.
- trail 파라미터를 사용해 커스텀 아이콘으로 확장/축소 표시를 변경할 수 있습니다.

## SeeAlso
- [WantedAccordionHeader](#)
- [WantedAccordionContract.VerticalPadding](#)
- [WantedAccordionTrailArrowIcon](#)
