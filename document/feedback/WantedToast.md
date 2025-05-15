---
title: Toast
description: 사용자 피드백을 위한 커스텀 토스트 컴포저블
image: /components/toast/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedToast

### 개요
Compose의 `SnackbarHost`를 기반으로 만든 Wanted 전용 커스텀 토스트 컴포저블입니다. 메시지, 아이콘, 스타일(variant) 등을 커스터마이징하여 다양한 알림 UI를 구현할 수 있습니다.

### 사용 예시
```kotlin
val hostState = remember { SnackbarHostState() }
WantedToast(snackBarHostState = hostState)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| snackBarHostState | SnackbarHostState | Snackbar 상태를 제어하는 상태 객체입니다. |
| modifier | Modifier | 외형 및 위치를 조정하는 Modifier입니다. |
| windowInsets | WindowInsets | 시스템 인셋 대응용 설정입니다. 기본값은 0입니다. |

---
<br />
<br />

## WantedToastVisuals

### 개요
SnackbarVisuals를 확장한 토스트 전용 시각 구성 객체입니다. 메시지, variant, 아이콘 등을 설정할 수 있습니다.

### 생성자 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| message | String | 토스트에 표시할 메시지입니다. |
| actionLabel | String | 액션 버튼 텍스트입니다. 기본은 빈 문자열입니다. |
| duration | SnackbarDuration | 노출 시간입니다. 기본값은 Short입니다. |
| withDismissAction | Boolean | 닫기 액션 버튼 표시 여부입니다. 기본값은 false입니다. |
| variant | WantedToastVariant | 토스트 메시지 유형 스타일입니다. 기본은 Message입니다. |
| icon | @Composable (() -> Unit)? | 커스텀 아이콘을 표시할 수 있는 Slot입니다. |

---
<br />
<br />

## WantedToastVariant

### 개요
토스트 메시지 스타일을 지정하는 sealed 클래스입니다. 각 스타일은 고유한 아이콘과 색상을 가집니다.

### 값 및 설명
| 값 | 설명 |
|:---|:---|
| Message | 일반 메시지 타입입니다. 아이콘 없음. |
| Positive | 긍정적 메시지. 체크 아이콘 표시, 녹색 계열 색상. |
| Cautionary | 주의 메시지. 느낌표 아이콘, 주황색 계열. |
| Negative | 오류/부정 메시지. 느낌표 아이콘, 빨간색 계열. |

---
<br />
<br />

## Note
- `WantedToast`는 내부적으로 `WantedToastImpl`과 `WantedToastLayout`으로 분리되어 구성됩니다.
- `WantedToastVisuals`는 `SnackbarVisuals`의 확장 클래스이므로 기본 Snackbar와 호환됩니다.
