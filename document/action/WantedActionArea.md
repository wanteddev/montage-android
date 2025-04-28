# ActionArea 

## WantedActionArea 

### 개요
WantedActionArea는 화면 하단에 액션 버튼 영역을 생성하는 Compose 함수입니다.
스크롤, 안전 영역 처리, 배경 그라데이션 등을 지원하며, 다양한 버튼 조합을 구성할 수 있습니다.

### 사용 예시
```kotlin
WantedActionArea(
    type = ActionAreaType.Strong,
    positive = "확인",
    negative = "취소",
    neutral = "나중에",
    onClickPositive = { /* 긍정 액션 */ },
    onClickNegative = { /* 부정 액션 */ },
    onClickNeutral = { /* 중립 액션 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외부 Modifier를 설정합니다. |
| safeArea | Boolean | 안전 영역(Safe Area) 고려 여부입니다. (기본값: true) |
| background | Boolean | 배경 그라데이션을 표시할지 여부입니다. (기본값: false) |
| gradationColor | Color | 배경 그라데이션 색상입니다. |
| type | ActionAreaType | ActionArea의 타입(Strong, Neutral, Cancel)입니다. |
| positive | String | 메인(긍정) 액션 텍스트입니다. |
| negative | String? | 서브(부정) 액션 텍스트입니다. (선택사항) |
| neutral | String? | 추가(중립) 액션 텍스트입니다. (선택사항) |
| scrollableState | ScrollableState? | 스크롤 가능 상태를 제어합니다. (선택사항) |
| onClickPositive | () -> Unit | 메인 액션 클릭 콜백입니다. |
| onClickNegative | (() -> Unit)? | 서브 액션 클릭 콜백입니다. (선택사항) |
| onClickNeutral | (() -> Unit)? | 추가 액션 클릭 콜백입니다. (선택사항) |
| caption | String? | 액션 영역 상단에 표시할 캡션입니다. (선택사항) |
| variant | @Composable (() -> Unit)? | 추가 컴포넌트를 삽입할 수 있습니다. (선택사항) |

---

## WantedActionArea (Composable Slot 사용) 함수 설명

### 개요
직접 Composable Slot으로 Positive, Negative, Neutral 버튼을 주입할 수 있는 버전입니다.
커스텀 UI가 필요한 경우 사용합니다.

### 사용 예시
```kotlin
WantedActionArea(
    positive = { WantedButton(text = "확인", onClick = { /* 긍정 액션 */ }) },
    negative = { WantedButton(text = "취소", onClick = { /* 부정 액션 */ }) },
    neutral = { WantedButton(text = "나중에", onClick = { /* 중립 액션 */ }) }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외부 Modifier를 설정합니다. |
| safeArea | Boolean | 안전 영역(Safe Area) 고려 여부입니다. (기본값: true) |
| background | Boolean | 배경 그라데이션 표시 여부입니다. (기본값: false) |
| gradationColor | Color | 배경 그라데이션 색상입니다. |
| type | ActionAreaType | ActionArea의 타입입니다. (기본값: Strong) |
| scrollableState | ScrollableState? | 스크롤 가능 상태 제어입니다. (선택사항) |
| positive | @Composable () -> Unit | 메인(긍정) 액션 Slot입니다. |
| negative | @Composable (() -> Unit)? | 서브(부정) 액션 Slot입니다. (선택사항) |
| neutral | @Composable (() -> Unit)? | 추가(중립) 액션 Slot입니다. (선택사항) |
| caption | String? | 캡션 텍스트입니다. (선택사항) |
| variant | @Composable (() -> Unit)? | 추가 삽입 컴포넌트입니다. (선택사항) |

---

## WantedActionArea (Deprecated) 함수 설명

> ⚠️ **Warning: Deprecated API**
>
> 이 버전은 사용이 중단되었습니다. 대신 `WantedActionArea` (Slot 방식)를 사용하세요.

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | Modifier 설정입니다. |
| safeArea | Boolean | Safe Area 고려 여부입니다. |
| background | Boolean | 배경 그라데이션 표시 여부입니다. |
| gradationColor | Color | 배경 색상입니다. |
| actionAreaDefault | WantedActionAreaDefault | 버튼 스타일 및 타입 기본 설정입니다. |
| positive | String | 메인 액션 텍스트입니다. |
| negative | String? | 서브 액션 텍스트입니다. (선택사항) |
| neutral | String? | 추가 액션 텍스트입니다. (선택사항) |
| scrollableState | ScrollableState? | 스크롤 상태 관리입니다. (선택사항) |
| onClickPositive | () -> Unit | 메인 액션 콜백입니다. |
| onClickNegative | (() -> Unit)? | 서브 액션 콜백입니다. (선택사항) |
| onClickNeutral | (() -> Unit)? | 추가 액션 콜백입니다. (선택사항) |
| caption | String? | 캡션 텍스트입니다. (선택사항) |
| variant | @Composable (() -> Unit)? | 추가 삽입 컴포넌트입니다. (선택사항) |

---

## Enum 설명

### ActionAreaType
| 값 | 설명 |
|:---|:---|
| Strong | 메인, 서브, 중립 버튼을 모두 강조하는 형태 |
| Neutral | 메인 버튼을 강조하고 나머지를 보조하는 형태 |
| Cancel | 메인 버튼만 남기고 취소용 버튼을 강조하는 형태 |

---

## Data Class 설명

### WantedActionAreaDefault

**개요**

WantedActionAreaDefault는 ActionArea를 구성하는 각 버튼(positive, negative, neutral)의 기본 스타일(WantedButtonDefault)을 설정하는 데이터 클래스입니다.

ActionAreaType에 따라 자동으로 버튼 타입, 모양, 크기 등이 지정됩니다.

### 필드
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| type | ActionAreaType | ActionArea의 기본 타입입니다. (Strong, Neutral, Cancel) |
| positiveButtonDefault | WantedButtonDefault | Positive(긍정) 버튼의 스타일 설정입니다. |
| negativeButtonDefault | WantedButtonDefault | Negative(부정) 버튼의 스타일 설정입니다. |
| neutralButtonDefault | WantedButtonDefault | Neutral(중립) 버튼의 스타일 설정입니다. |

---

## Note
- `WantedActionArea`는 화면 하단 고정 영역이나 Dialog용 버튼 그룹에 적합합니다.
- `ActionAreaType`에 따라 버튼 스타일 및 배치가 달라지며, `WantedActionAreaDefault`로 세부 조정이 가능합니다.
- 스크롤이 필요한 경우 `scrollableState`를 연동하여 자연스러운 레이아웃을 구성할 수 있습니다.
