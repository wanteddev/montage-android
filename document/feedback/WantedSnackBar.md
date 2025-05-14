---
title: SnackBar
description: 커스텀 스낵바 UI 컴포저블
image: /components/snack-bar/design/thumbnail.png
createdAt: 2025-05-09
---

## WantedSnackBar

### 개요
Compose의 `SnackbarHost`를 기반으로 만든 커스텀 스낵바 컴포저블입니다. `SnackbarData.visuals`에 따라 메시지, 설명, 버튼, 아이콘 등 다양한 시각 요소를 지원합니다.

### 사용 예시
```kotlin
val snackbarHostState = remember { SnackbarHostState() }
WantedSnackBar(snackbarHostState = snackbarHostState)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| snackbarHostState | SnackbarHostState | 스낵바 표시 상태를 제어하는 상태 객체입니다. |
| modifier | Modifier | 외형 및 위치를 조정하는 Modifier입니다. |

---

## WantedSnackbarVisuals

### 개요
Compose 기본 `SnackbarVisuals`를 확장한 Wanted 전용 커스텀 클래스입니다. 설명, 패딩, 아이콘 등 추가 속성을 포함하여 시각적 구성을 보다 정교하게 제어할 수 있습니다.

### 생성자 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| message | String | 스낵바에 표시될 기본 메시지입니다. |
| actionLabel | String? | 버튼에 표시될 텍스트입니다. (nullable) |
| duration | SnackbarDuration | 스낵바 표시 시간입니다. 기본값은 Short입니다. |
| withDismissAction | Boolean | 닫기 액션 버튼을 표시할지 여부입니다. |
| description | String | 부가 설명 텍스트입니다. |
| padding | PaddingValues | 콘텐츠 내부 패딩입니다. |
| icon | @Composable (() -> Unit)? | 좌측에 표시될 아이콘입니다. (nullable) |

---

## Note
- `WantedSnackBar`는 `SnackbarData.visuals` 타입이 `WantedSnackbarVisuals`인지 여부에 따라 다르게 처리합니다.
- `SnackbarType` 및 `WantedSnackBarHostDefaults`도 함께 활용 가능합니다.
