# ActionChip

## WantedActionChip

### 개요
WantedActionChip은 사용자가 선택할 수 있는 다양한 스타일의 Chip UI 컴포넌트입니다.
텍스트, 아이콘, 크기, 활성화 상태, 사용 가능 여부 등을 조정할 수 있으며, 기본 스타일(WantedChipDefault)을 커스터마이징할 수 있습니다.


## WantedActionChip

### 기본형

**텍스트, 아이콘 등을 지정하여 기본 Chip을 생성합니다.**

**사용 예시:**
```kotlin
WantedActionChip(
    text = "텍스트",
    leftIcon = R.drawable.ic_sample_icon,
    rightIcon = R.drawable.ic_sample_icon,
    onClick = { /* 클릭 처리 */ }
)
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| text | String | Chip에 표시할 텍스트 |
| modifier | Modifier | Modifier를 통한 외형 설정 |
| leftIcon | Int? | 왼쪽 아이콘 리소스 ID (nullable) |
| rightIcon | Int? | 오른쪽 아이콘 리소스 ID (nullable) |
| size | ChipActionSize | Chip 크기 설정 (ChipActionSize 참조) |
| variant | ChipActionVariant | Chip 스타일 변형 (ChipActionVariant 참조) |
| isActive | Boolean | 활성화 여부 |
| isEnable | Boolean | 사용 가능 여부 |
| interactionSource | MutableInteractionSource | 사용자 터치 이벤트 관리 객체 |
| onClick | (() -> Unit)? | 클릭 이벤트 콜백 |

### 커스텀 스타일 적용형

**chipDefault를 통해 직접 스타일을 지정할 수 있습니다.**

**사용 예시:**
```kotlin
WantedActionChip(
    text = "커스텀 텍스트",
    chipDefault = customChipDefault,
    onClick = { /* 클릭 처리 */ }
)
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| text | String | Chip에 표시할 텍스트 |
| modifier | Modifier | Modifier를 통한 외형 설정 |
| leftIcon | Int? | 왼쪽 아이콘 리소스 ID (nullable) |
| rightIcon | Int? | 오른쪽 아이콘 리소스 ID (nullable) |
| chipDefault | WantedChipDefault | 기본 스타일 직접 지정 |
| interactionSource | MutableInteractionSource | 사용자 터치 이벤트 관리 객체 |
| onClick | (() -> Unit)? | 클릭 이벤트 콜백 |

### Composable Slot 사용형

**아이콘 및 콘텐츠를 Composable 형태로 직접 정의합니다.**

**사용 예시:**
```kotlin
WantedActionChip(
    leftIcon = { Icon(...) },
    content = { Text("Content") },
    rightIcon = { Icon(...) }
)
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | Modifier를 통한 외형 설정 |
| size | ChipActionSize | Chip 크기 설정 |
| variant | ChipActionVariant | Chip 스타일 변형 |
| isActive | Boolean | 활성화 여부 |
| isEnable | Boolean | 사용 가능 여부 |
| chipDefault | WantedChipDefault | 기본 스타일 직접 지정 |
| interactionSource | MutableInteractionSource | 사용자 터치 이벤트 관리 객체 |
| leftIcon | @Composable (() -> Unit)? | 왼쪽 아이콘 Composable 슬롯 |
| content | @Composable () -> Unit | 텍스트 또는 콘텐츠 Composable 슬롯 |
| rightIcon | @Composable (() -> Unit)? | 오른쪽 아이콘 Composable 슬롯 |
| onClick | (() -> Unit)? | 클릭 이벤트 콜백 |

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

**사용 예시:**
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

**사용 예시:**
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

**사용 예시:**
```kotlin
val iconColor = WantedChipDefaults.getFilterIconColor()
```

| 파라미터 | 타입 | 설명 |
|:---|:---|:---|
| variant | ChipActionVariant | Chip 스타일 변형 (Solid, Outlined) |
| isActive | Boolean | 활성화 여부 |
| isEnable | Boolean | 사용 가능 여부 |

**@return**: 아이콘 색상에 해당하는 리소스 ID 반환

---

## 기타 참고사항

- **Note:** `isActive`가 true이면 강조 스타일이 적용됩니다.
- **Important:** `onClick`이 null이면 클릭 불가 상태로 렌더링됩니다.
- **SeeAlso:** [DesignSystemTheme](#)

