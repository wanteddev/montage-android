# IconButtonSolid

## WantedIconButtonSolid

### 개요
배경이 채워진 원형 스타일의 Solid 아이콘 버튼입니다.  
`WantedIconButtonSize` enum을 통해 간편하게 크기 및 패딩을 설정할 수 있으며,  
배경색과 아이콘 색상도 상태에 따라 커스터마이징 가능합니다.

### 사용 예시
```kotlin
WantedIconButtonSolid(
    icon = R.drawable.ic_icon,
    size = WantedIconButtonSize.Medium,
    onClick = { /* 클릭 처리 */ }
)

WantedIconButtonSolid(
    icon = R.drawable.ic_icon,
    modifier = Modifier.size(40.dp),
    padding = 8.dp,
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| icon | Int | 아이콘으로 사용할 drawable 리소스 ID입니다. |
| size | WantedIconButtonSize | 버튼 크기와 내부 패딩을 정의하는 enum입니다. *(첫 번째 오버로드 전용)* |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| enabled | Boolean | 버튼 활성화 여부입니다. |
| padding | Dp | 아이콘 내부 패딩입니다. *(두 번째 오버로드 전용)* |
| tint | Color | 아이콘 색상입니다. 기본값은 static_white입니다. |
| background | Color | 배경 색상입니다. 기본값은 primary_normal입니다. |
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
- Solid 버튼은 강조 목적에 적합하며, 배경이 있어 시각적으로 두드러집니다.
- 비활성 상태일 경우 fill_normal 배경과 label_disable 색상이 자동 적용됩니다.
