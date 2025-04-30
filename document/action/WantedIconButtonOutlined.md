# IconButtonOutlined

## WantedIconButtonOutlined

### 개요
외곽선이 있는 스타일의 아이콘 버튼입니다.  
`WantedIconButtonSize` enum을 사용하여 간편하게 크기 및 내부 여백을 설정할 수 있으며,  
외곽선 색상, 아이콘 색상, 배경색 등을 활성/비활성 상태별로 설정할 수 있습니다.

### 사용 예시
```kotlin
WantedIconButtonOutlined(
    icon = R.drawable.ic_icon,
    size = WantedIconButtonSize.Medium,
    onClick = { /* 클릭 처리 */ }
)

WantedIconButtonOutlined(
    icon = R.drawable.ic_icon,
    modifier = Modifier.size(40.dp),
    padding = 8.dp,
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| icon | Int | 버튼에 표시할 drawable 리소스 ID입니다. |
| size | WantedIconButtonSize | 버튼의 크기와 내부 여백을 지정하는 enum입니다. *(첫 번째 오버로드 전용)* |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| padding | Dp | 아이콘 내부 패딩입니다. *(두 번째 오버로드 전용)* |
| enabled | Boolean | 버튼 활성화 여부입니다. |
| outlineColor | Color | 활성화 상태 외곽선 색상입니다. |
| disableOutlineColor | Color | 비활성 상태 외곽선 색상입니다. |
| tint | Color | 활성 상태 아이콘 색상입니다. |
| disableTint | Color | 비활성 상태 아이콘 색상입니다. |
| background | Color | 활성 상태 배경 색상입니다. |
| disableBackground | Color | 비활성 상태 배경 색상입니다. |
| onClick | () -> Unit | 클릭 시 호출되는 콜백입니다. |

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
- `size`를 사용할 경우 enum에서 제공하는 크기 및 패딩이 자동 설정되며 일관된 디자인 유지에 유리합니다.
- `modifier.size()`와 `padding`을 조합하면 커스터마이징된 형태로 사용할 수 있습니다.
