# IconButtonNormal

## WantedIconButtonNormal

### 개요
원형 배경 없이 단순한 아이콘만을 표시하는 기본 아이콘 버튼입니다.  
필요 시 우측 상단에 PushBadge 컴포저블을 함께 표시할 수 있으며,  
터치 영역은 `WantedTouchArea`를 통해 넉넉하게 설정되어 있습니다.

### 사용 예시
```kotlin
WantedIconButtonNormal(
    icon = R.drawable.ic_icon,
    modifier = Modifier.size(24.dp),
    pushBadge = {
        WantedPushBadge()
    },
    onClick = { /* 클릭 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| icon | Int | 아이콘으로 사용할 drawable 리소스 ID입니다. |
| modifier | Modifier | 버튼 외형 및 배치를 제어하는 Modifier입니다. |
| enabled | Boolean | 버튼의 활성화 여부입니다. false일 경우 비활성 색상으로 표시됩니다. |
| tint | Color | 아이콘의 색상입니다. 기본값은 label_normal입니다. |
| pushBadge | (() -> Unit)? | 우측 상단에 표시될 PushBadge 등 컴포저블입니다. |
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
- pushBadge는 선택적이며 알림 숫자나 표시용으로 활용할 수 있습니다.
- 클릭 영역은 아이콘보다 넓게 설정되어 있어 UX를 고려한 설계입니다.
