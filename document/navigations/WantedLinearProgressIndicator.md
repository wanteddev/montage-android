---
title: LinearProgressIndicator
description: 진행률을 표시하는 선형 프로그레스 인디케이터
image: /components/linear-progress-indicator/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedLinearProgressIndicator

### 개요
`WantedLinearProgressIndicator`는 특정 작업의 진행률을 막대 형태로 시각적으로 표현하는 컴포넌트입니다. 진행률은 0에서 1 사이의 `Float` 값으로 표시되며, 가로 전체 너비의 2dp 높이로 기본 렌더링됩니다.

### 사용 예시
```kotlin
WantedLinearProgressIndicator(
    currentProgress = 0.3f
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| currentProgress | Float | 현재 진행률을 나타내며 0.0 ~ 1.0 범위입니다. |
| modifier | Modifier | 외형 및 배치 조정을 위한 Modifier입니다. 기본값은 Modifier입니다. |

---

## Note
- 배경 색상은 `fill_normal`, 진행 색상은 `primary_normal`로 설정되어 있습니다.
- `currentProgress` 값은 애니메이션을 통해 부드럽게 반영됩니다.
