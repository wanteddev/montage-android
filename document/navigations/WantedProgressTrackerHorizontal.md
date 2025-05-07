---
title: ProgressTracker
description: 단계 기반 진행 상황을 수평/수직으로 표시하는 컴포넌트
image: /components/progress-tracker/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedProgressTrackerHorizontal

### 개요
`WantedProgressTrackerHorizontal`는 수평 방향으로 단계 진행 상태를 시각화하는 컴포넌트입니다. 각 단계는 선과 원으로 연결되며, 선택된 단계는 강조 색상으로 표시됩니다. 간단한 프로세스 또는 페이지 흐름 시각화에 적합합니다.

### 사용 예시
```kotlin
WantedProgressTrackerHorizontal(
    stepCount = 4,
    currentStep = 2,
    label = { index -> "${index + 1}단계" }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| stepCount | Int | 전체 단계 수입니다. |
| currentStep | Int | 현재 선택된 단계 (1부터 시작)입니다. |
| modifier | Modifier | 외형 및 배치 조정을 위한 Modifier입니다. |
| label | ((Int) -> String)? | 각 단계에 표시할 텍스트를 반환하는 함수입니다. |

---

## Note
- `currentStep`은 1부터 시작하는 index이며, 강조 색상 및 연결선 렌더링 기준입니다.
- 수평형은 페이지 전환 기반의 단순 단계 시각화에 적합합니다.
