---
title: PlayBadge
description: 재생(Play) 아이콘을 원형 배지로 감싸서 표시하는 컴포저블
image: /components/play-badge/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedPlayBadge

### 개요
재생(Play) 아이콘을 원형 배지로 감싸서 표시하는 컴포저블입니다. 
회색 계열의 반투명 배경과 흰색의 플레이 아이콘으로 구성되며, 
세 가지 크기 옵션과 대체 색상 모드를 통해 다양한 재생 상태를 시각적으로 표현할 수 있습니다.

### 사용 예시
```kotlin
WantedPlayBadge(
    size = Size.Medium,
    isAlternative = true,
    modifier = Modifier
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| size | Size | 배지의 크기 옵션입니다. Small, Medium, Large를 지원합니다. |
| isAlternative | Boolean | true일 경우 더 어두운 회색 배경을 사용하여 대체 스타일로 표시합니다. |
| modifier | Modifier | 배지의 외형, 정렬, 패딩 등을 제어하는 Modifier입니다. |

---
<br />
<br />

## Enum 설명

### Size
| 값 | 설명 |
|:---|:---|
| Small | 작은 크기의 배지로, 36dp 배경과 24dp 아이콘 크기를 가집니다. |
| Medium | 중간 크기의 배지로, 60dp 배경과 40dp 아이콘 크기를 가집니다. |
| Large | 큰 크기의 배지로, 80dp 배경과 56dp 아이콘 크기를 가집니다. |

---
<br />
<br />

## Note
- `isAlternative` 설정은 `cool_neutral_30` 또는 `cool_neutral_40` 색상 차이를 통해 재생 상태에 대한 시각적 대비를 제공합니다.
- `modifier`를 통해 배치, 패딩 등을 커스터마이징할 수 있습니다.
