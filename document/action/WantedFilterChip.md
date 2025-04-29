# FilterChip

## WantedFilterChip

### 개요
WantedFilterChip은 사용자가 선택할 수 있는 필터 Chip 컴포넌트입니다.
텍스트, 활성화 라벨, 확장 화살표 아이콘 등을 설정할 수 있으며, 클릭 이벤트를 지원합니다.

---

### WantedFilterChip (기본형)

### 개요
텍스트와 활성화 라벨, 확장 여부를 설정하여 기본형 필터 Chip을 생성합니다.

### 사용 예시
```kotlin
WantedFilterChip(
    text = "필터 텍스트",
    activeLabel = "활성 라벨",
    isExpend = true,
    onClick = { /* 클릭 처리 */ }
)
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| text | String | 표시할 필터 텍스트 |
| modifier | Modifier | Modifier를 통한 스타일 조정 |
| activeLabel | String | 활성화 상태일 때 표시할 추가 라벨 텍스트 |
| size | ChipActionSize | Chip 크기 설정 (Small, Medium 등) |
| variant | ChipActionVariant | Chip 스타일 변형 (Solid, Outlined) |
| isActive | Boolean | 활성화 여부 설정 |
| isEnable | Boolean | 사용 가능 여부 설정 |
| isExpend | Boolean | 확장 여부 (true면 위쪽 화살표 표시) |
| interactionSource | MutableInteractionSource | 터치 인터랙션 제어용 객체 |
| onClick | (() -> Unit)? | 클릭 시 실행할 콜백 함수 |

---

### WantedFilterChip (Custom Default 적용형)

**설명:**
원하는 스타일(WantedChipDefault)을 직접 주입하여 Chip을 생성합니다.

### 사용 예시
```kotlin
WantedFilterChip(
    text = "필터",
    activeLabel = "3",
    chipDefault = customDefault,
    onClick = { /* 클릭 처리 */ }
)
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| text | String | 표시할 필터 텍스트 |
| modifier | Modifier | Modifier를 통한 스타일 조정 |
| activeLabel | String | 활성화 상태일 때 표시할 추가 라벨 텍스트 |
| chipDefault | WantedChipDefault | 기본 스타일 직접 주입 |
| isExpend | Boolean | 확장 여부 (true면 위쪽 화살표 표시) |
| interactionSource | MutableInteractionSource | 터치 인터랙션 제어용 객체 |
| onClick | (() -> Unit)? | 클릭 시 실행할 콜백 함수 |

---

## 추가 참고사항

- **Note:** `isExpend = true` 설정 시 오른쪽 화살표 방향이 위를 가리킵니다.
- **Important:** `onClick`이 null이면 클릭 이벤트가 발생하지 않습니다.
- **SeeAlso:** [WantedActionChip 문서](#)

---

## Enum 설명

### ChipActionVariant

| 값 | 설명 |
|:---|:---|
| Solid | 배경이 채워진 스타일 |
| Outlined | 외곽선만 있는 스타일 |

### ChipActionSize

| 값 | 설명 |
|:---|:---|
| Large | 가장 큰 크기 |
| Medium | 중간 크기 |
| Small | 작은 크기 |
| XSmall | 매우 작은 크기 |

---

## WantedChipDefault

**WantedActionChip 기본 스타일을 정의하는 데이터 클래스**

### 사용 예시
```kotlin
val chipDefault = WantedChipDefault(
    size = ChipActionSize.Medium,
    variant = ChipActionVariant.Solid,
    isActive = true,
    isEnable = true,
    iconColor = Color.Black,
    backgroundColor = Color.White,
    borderColor = Color.Gray,
    textStyle = TextStyle.Default
)
```

| 프로퍼티 | 타입 | 설명 |
|:---|:---|:---|
| size | ChipActionSize | Chip 크기 설정 |
| variant | ChipActionVariant | Chip 스타일 변형 (Solid, Outlined) |
| isActive | Boolean | Chip 활성화 여부 |
| isEnable | Boolean | Chip 사용 가능 여부 |
| iconColor | Color | 아이콘 색상 |
| backgroundColor | Color | 배경 색상 |
| borderColor | Color | 테두리 색상 |
| textStyle | TextStyle | 텍스트 스타일 |

---

## WantedChipDefaults

### getDefault

**컴포즈 환경(Context)에 따라 기본 스타일을 반환합니다.**

### 사용 예시
```kotlin
val chipDefault = WantedChipDefaults.getDefault()
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| size | ChipActionSize | Chip 크기 설정 |
| variant | ChipActionVariant | Chip 스타일 변형 (Solid, Outlined) |
| isActive | Boolean | 활성화 여부 |
| isEnable | Boolean | 사용 가능 여부 |
| iconColor | Color | 아이콘 색상 (default 자동 설정) |
| backgroundColor | Color | 배경 색상 (default 자동 설정) |
| borderColor | Color | 테두리 색상 (default 자동 설정) |
| textStyle | TextStyle | 텍스트 스타일 (default 자동 설정) |

**@return**: 설정된 `WantedChipDefault` 객체 반환

### getFilterIconColor

**필터 Chip 전용 아이콘 색상을 반환합니다.**

### 사용 예시
```kotlin
val iconColor = WantedChipDefaults.getFilterIconColor()
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| variant | ChipActionVariant | Chip 스타일 변형 (Solid, Outlined) |
| isActive | Boolean | 활성화 여부 |
| isEnable | Boolean | 사용 가능 여부 |

**@return**: 아이콘 색상에 해당하는 리소스 ID 반환