---
title: CircularLoading
description: 원형 로딩 인디케이터 및 dim 배경 지원 컴포넌트
image: /components/circular-loading/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedCircularLoading

### 개요
`WantedCircularLoading`은 화면 중앙에 원형 로딩 인디케이터를 표시하는 컴포넌트입니다. 필요에 따라 dim 배경을 사용할 수 있으며, 전체 화면에서 로딩을 표시할 때 적합합니다.

### 사용 예시
```kotlin
WantedCircularLoading(
    size = 32.dp,
    circleColor = Color.Gray,
    dimColor = Color.Black.copy(alpha = 0.3f)
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형 및 배치 설정입니다. |
| circleColor | Color | 로딩 인디케이터 색상입니다. 기본값은 theme의 line_solid_normal입니다. |
| dimColor | Color | 배경 dim 색상입니다. 기본값은 투명입니다. |
| size | Dp | 로딩 인디케이터의 크기입니다. 기본값은 24.dp입니다. |

---

## WantedCircularProgressIndicator

### 개요
`WantedCircularProgressIndicator`는 커스터마이징 가능한 원형 인디케이터입니다. 선의 굵기는 컴포넌트 크기의 10%로 설정되며, 라운드 캡 형태로 표현됩니다.

### 사용 예시
```kotlin
WantedCircularProgressIndicator(
    modifier = Modifier.size(40.dp),
    color = Color.Red
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 크기 및 레이아웃 설정입니다. 기본값은 최소 크기 24.dp입니다. |
| color | Color | 인디케이터 색상입니다. 기본값은 theme의 line_solid_normal입니다. |

---

## Note
- `WantedCircularLoading`은 `WantedCircularProgressIndicator`를 포함하여 사용되며, 배경 dim 처리가 가능합니다.
- 기본 크기와 색상은 디자인 시스템 리소스를 따릅니다.
