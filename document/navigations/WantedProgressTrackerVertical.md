---
title: ProgressTracker
description: 단계 기반 진행 상황을 수직으로 표시하는 컴포넌트
image: /components/progress-tracker/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedProgressTrackerVertical

### 개요
`WantedProgressTrackerVertical`는 수직 방향으로 각 단계와 그에 따른 콘텐츠를 함께 배치하는 컴포넌트입니다. 각 단계는 커스텀 라벨과 본문 콘텐츠를 포함할 수 있으며, 긴 설명이나 양식 처리 등에 적합합니다.

### 사용 예시
```kotlin
WantedProgressTrackerVertical(
    stepCount = 4,
    currentStep = 3,
    label = { index -> "Step ${index + 1}" },
    content = { index -> Text("내용 $index") }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| stepCount | Int | 전체 단계 수입니다. |
| currentStep | Int | 현재 진행 중인 단계입니다. |
| modifier | Modifier | 외형 및 배치 조정을 위한 Modifier입니다. |
| label | ((Int) -> String)? | 단계별 텍스트 라벨 반환 함수입니다. |
| labelContent | @Composable ((Int) -> Unit)? | 텍스트 대신 사용할 커스텀 라벨 컴포저블입니다. |
| content | @Composable (Int) -> Unit | 단계별 콘텐츠 슬롯입니다. |

---

## Note
- `currentStep`은 1부터 시작하는 index이며, 강조 색상 및 연결선 렌더링 기준입니다.
- `label` 또는 `labelContent`를 통해 텍스트 또는 UI 기반 라벨을 유연하게 구성할 수 있습니다.
- 수직형은 설명 위주의 단계가 많은 케이스에 적합합니다.
