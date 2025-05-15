---
title: PushBadge
description: 아이콘이나 UI 요소에 붙여 표시되는 푸시 뱃지 컴포저블
image: /components/push-badge/design/thumbnail.png
createdAt: 2025-04-30
---


## WantedPushBadge

### 개요
아이콘이나 UI 요소에 붙여 표시되는 푸시 뱃지 컴포저블입니다.  
`Dot`, `Number`, `New` 타입 중 하나를 선택할 수 있으며, 배지 위치, 사이즈, 색상 등을 설정할 수 있습니다.  
새로운 알림, 수량 표시, 간단한 포인트 강조 등에 적합합니다.

### 사용 예시
```kotlin
WantedPushBadge(
    variant = PushBadgeVariant.Number,
    count = "5",
    position = PushBadgePosition.TopEnd,
    size = PushBadgeSize.Small,
    modifier = Modifier
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 배지의 배치, 정렬 등에 사용되는 Modifier입니다. |
| variant | PushBadgeVariant | 표시할 배지 타입입니다. Dot, Number, New 중 선택할 수 있습니다. |
| size | PushBadgeSize | 배지의 크기를 지정합니다. XSmall, Small, Medium 중 선택합니다. |
| position | PushBadgePosition | 배지의 위치를 설정합니다. TopEnd 등 9가지 위치를 지원합니다. |
| count | String | Number 타입일 경우 표시할 숫자입니다. |
| background | Color | 배지의 배경 색상입니다. 기본값은 primary_normal입니다. |
| contentColor | Color | 텍스트(숫자, "N")의 색상입니다. 기본값은 static_white입니다. |

---
<br />
<br />

## Enum 설명

### PushBadgeVariant
| 값 | 설명 |
|:---|:---|
| Dot | 작은 점 형태의 뱃지를 표시합니다. |
| Number | 숫자 형태로 개수를 표시합니다. |
| New | "N" 텍스트를 통해 새로운 항목을 표시합니다. |

### PushBadgeSize
| 값 | 설명 |
|:---|:---|
| XSmall | 가장 작은 크기로 텍스트가 작게 표시됩니다. |
| Small | 중간 크기의 뱃지입니다. |
| Medium | 가장 큰 뱃지로 강조 표시 시 적합합니다. |

### PushBadgePosition
| 값 | 설명 |
|:---|:---|
| TopStart | 상단 좌측에 배치됩니다. |
| TopCenter | 상단 중앙에 배치됩니다. |
| TopEnd | 상단 우측에 배치됩니다. |
| MiddleStart | 중앙 좌측에 배치됩니다. |
| MiddleCenter | 중앙에 배치됩니다. |
| MiddleEnd | 중앙 우측에 배치됩니다. |
| BottomStart | 하단 좌측에 배치됩니다. |
| BottomCenter | 하단 중앙에 배치됩니다. |
| BottomEnd | 하단 우측에 배치됩니다. |

---
<br />
<br />

## Note
- Dot 타입은 색상만으로 상태를 표시할 때 사용하며, 사이즈에 따라 여백이 달라집니다.
- Number 타입은 개수(문자열)를 표시하고, New 타입은 "N"을 표시합니다.
- 뱃지는 `getOffset()` 함수에 의해 기준 뷰의 특정 위치에 자동 배치됩니다.
