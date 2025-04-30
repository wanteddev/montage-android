# IconButtonBackground

## WantedIconButtonBackground

### 개요
아이콘에 배경 원형 효과와 터치 영역을 추가한 아이콘 버튼 컴포저블입니다.  
활성/비활성 상태, 대체 배경 여부, 아이콘 색상 등 시각적 스타일을 지정할 수 있으며,  
터치 영역이 확대되어 사용자 편의성을 높입니다.

### 사용 예시
```kotlin
WantedIconButtonBackground(
    icon = R.drawable.ic_icon,
    modifier = Modifier.size(24.dp),
    alternative = true,
    enabled = true,
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| icon | Int | 아이콘으로 사용할 drawable 리소스 ID입니다. |
| modifier | Modifier | 버튼 외형 및 배치를 제어하는 Modifier입니다. |
| alternative | Boolean | true일 경우 대체 배경 색상을 적용합니다. |
| enabled | Boolean | 버튼 활성화 여부를 지정합니다. false일 경우 흐리게 표시됩니다. |
| tint | Color | 아이콘의 색상입니다. 기본값은 회색 계열이며 alternative에 따라 달라집니다. |
| onClick | () -> Unit | 버튼 클릭 시 호출되는 콜백입니다. |

---

## Enum 설명

### WantedIconButtonSize
| 값 | 설명 |
|:---|:---|
| Medium | 표준 사이즈 (40dp)로, 대부분의 UI에 적합합니다. |
| Small | 소형 사이즈 (32dp)로, 공간이 제한된 영역에 적합합니다. |

각 값은 버튼의 전체 크기(`size`)와 아이콘과 배경 간의 간격을 나타내는 `padding`을 제공합니다.

---

## Note
- `alternative = true`로 설정하면 dark background에 적합한 색상이 적용됩니다.
- `enabled = false`일 경우 opacity가 적용되어 비활성화 상태를 시각적으로 표현합니다.
