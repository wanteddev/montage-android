# Cell

## WantedCell

### 개요
텍스트와 캡션, 아이콘 등의 요소를 조합하여 리스트 항목 형태의 셀 컴포넌트를 구성합니다. 
사용자는 텍스트 및 보조 캡션, 좌우 콘텐츠, 클릭 이벤트 및 시각적 상태 등을 구성할 수 있습니다. 
두 가지 형태의 API (`String` 기반, `AnnotatedString` 기반)를 제공하여 유연한 텍스트 스타일링을 지원합니다.

### 사용 예시
```kotlin
WantedCell(
    text = "텍스트",
    caption = "캡션",
    fillWidth = true,
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| text | String | 셀에 표시할 메인 텍스트입니다. |
| modifier | Modifier | 셀 외형, 배치, 패딩 등을 조정합니다. |
| textMaxLine | Int | 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다. |
| caption | String | 서브 텍스트(캡션)으로 보조 정보를 제공합니다. |
| fillWidth | Boolean | true일 경우 셀이 부모 너비를 가득 채웁니다. |
| verticalPadding | VerticalPadding | 셀 상하 패딩 크기를 조정합니다. |
| interactionPadding | InteractionPadding | 터치 영역의 좌우 여백을 지정합니다. |
| divider | Boolean | true일 경우 셀 하단에 구분선을 표시합니다. |
| isEnable | Boolean | 셀의 활성화 여부를 설정합니다. 비활성화 시 알파값이 줄어듭니다. |
| isActive | Boolean | true일 경우 메인 텍스트 색상을 primary로 강조합니다. |
| ellipsis | Boolean | true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다. |
| verticalAlignCenter | Boolean | true일 경우 텍스트를 수직 중앙 정렬합니다. |
| chevrons | Boolean | true일 경우 우측에 chevron 아이콘을 표시합니다. |
| leftContent | @Composable (() -> Unit)? | 좌측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다. |
| rightContent | @Composable (() -> Unit)? | 우측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다. |
| onClick | (() -> Unit)? | 셀 클릭 시 호출되는 콜백 함수입니다. |

---

## WantedCell 함수 설명

### 사용 예시
```kotlin
WantedCell(
    annotatedString = AnnotatedString("텍스트"),
    annotatedCaption = AnnotatedString("캡션"),
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| annotatedString | AnnotatedString | 표시할 메인 텍스트입니다. |
| modifier | Modifier | 셀 외형, 배치, 패딩 등을 조정합니다. |
| annotatedCaption | AnnotatedString | 서브 텍스트(캡션)입니다. |
| fillWidth | Boolean | true일 경우 셀이 부모 너비를 가득 채웁니다. |
| verticalPadding | VerticalPadding | 셀 상하 패딩 크기를 조정합니다. |
| interactionPadding | InteractionPadding | 터치 영역의 좌우 여백을 지정합니다. |
| divider | Boolean | true일 경우 셀 하단에 구분선을 표시합니다. |
| isEnable | Boolean | 셀의 활성화 여부를 설정합니다. |
| isActive | Boolean | true일 경우 텍스트 색상을 primary로 강조합니다. |
| ellipsis | Boolean | true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다. |
| verticalAlignCenter | Boolean | true일 경우 텍스트를 수직 중앙 정렬합니다. |
| chevrons | Boolean | true일 경우 우측에 chevron 아이콘을 표시합니다. |
| textMaxLine | Int | 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다. |
| titleStyle | TextStyle? | 메인 텍스트의 커스텀 스타일을 설정할 수 있습니다. |
| captionStyle | TextStyle? | 캡션 텍스트의 커스텀 스타일을 설정할 수 있습니다. |
| leftContent | @Composable (() -> Unit)? | 좌측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다. |
| rightContent | @Composable (() -> Unit)? | 우측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다. |
| onClick | (() -> Unit)? | 셀 클릭 시 호출되는 콜백 함수입니다. |

---

## Enum 설명

### VerticalPadding
| 값 | 설명 |
|:---|:---|
| None | 패딩 없음 (0dp) |
| Small | 작은 여백 (8dp) |
| Medium | 기본 여백 (12dp) |
| Large | 큰 여백 (16dp) |

---

## Sealed Class 설명

### InteractionPadding
| 타입 | 설명 |
|:---|:---|
| Default(fillWidth: Boolean) | fillWidth 값에 따라 자동으로 12dp 또는 20dp 패딩을 설정합니다. |
| Custom(padding: Dp) | 직접 지정한 Dp 값으로 패딩을 설정할 수 있습니다. |

---

## Note
- `fillWidth = true`로 설정할 경우 텍스트 정렬이나 터치 영역 설정에 영향을 줄 수 있습니다.
- `InteractionPadding.Default(fillWidth)`는 fillWidth 값에 따라 좌우 여백이 자동으로 설정됩니다.
