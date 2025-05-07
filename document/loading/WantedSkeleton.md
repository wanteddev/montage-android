---
title: Skeleton
description: 로딩 상태를 위한 스켈레톤 컴포넌트 및 shimmer 효과
image: /components/skeleton/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedSkeletonCircle

### 개요
`WantedSkeletonCircle`은 원형 형태의 로딩 스켈레톤 컴포넌트입니다. shimmer 애니메이션이 자동 적용되며, 주로 아바타, 아이콘 등의 플레이스홀더로 사용됩니다.

### 사용 예시
```kotlin
WantedSkeletonCircle(modifier = Modifier.size(80.dp))
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 원형 크기 및 배치를 위한 Modifier입니다. |
| color | Color | 배경 색상입니다. 기본값은 fill_normal 색상입니다. |

---

## WantedSkeletonRectangle

### 개요
`WantedSkeletonRectangle`은 사각형 형태의 스켈레톤 컴포넌트입니다. 크기, 색상, 모서리 곡률을 조절할 수 있습니다.

### 사용 예시
```kotlin
WantedSkeletonRectangle(modifier = Modifier.size(200.dp))
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외형 및 배치 조정용입니다. |
| shape | RoundedCornerShape | 모서리 곡률입니다. 기본값은 3.dp입니다. |
| color | Color | 배경 색상입니다. 기본값은 fill_normal입니다. |

---

## WantedSkeletonText

### 개요
`WantedSkeletonText`는 다양한 길이와 정렬 옵션을 제공하는 텍스트 라벨 형태의 스켈레톤 컴포넌트입니다. `WantedSkeletonLength`와 `WantedSkeAlign`을 통해 유연하게 조정할 수 있습니다.

### 사용 예시
```kotlin
WantedSkeletonText(length = WantedSkeletonLength.Ratio75, align = WantedSkeAlign.Left)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| length | WantedSkeletonLength | 텍스트 길이 비율입니다. |
| modifier | Modifier | 외형 및 레이아웃 조정용입니다. |
| align | WantedSkeAlign | 텍스트 정렬 방식입니다. 기본값은 Left입니다. |
| shape | RoundedCornerShape | 스켈레톤 박스의 모서리 곡률입니다. 기본값은 3.dp입니다. |

### Enum 설명
#### WantedSkeletonLength
| 값 | 설명 |
|:---|:---|
| Ratio100 | 너비 100% |
| Ratio75 | 너비 75% |
| Ratio50 | 너비 50% |
| Ratio25 | 너비 25% |

#### WantedSkeAlign
| 값 | 설명 |
|:---|:---|
| Left | 좌측 정렬 |
| Center | 가운데 정렬 |
| Right | 우측 정렬 |

---

## Modifier.shimmer

### 개요
`shimmer`는 Modifier에 적용하는 확장 함수로, 흐르는 애니메이션 효과를 제공합니다. 일반적으로 스켈레톤 UI와 함께 사용됩니다.

### 사용 예시
```kotlin
Modifier.shimmer()
```

### 반환
| 타입 | 설명 |
|:---|:---|
| Modifier | shimmer 애니메이션이 적용된 Modifier입니다. |

---

## Modifier.shimmerLinear

### 개요
`shimmerLinear`는 직선 방향의 그라디언트가 적용되는 shimmer 애니메이션입니다. shimmer보다 더 명확한 효과를 원할 때 사용할 수 있습니다.

### 사용 예시
```kotlin
Modifier.shimmerLinear(colorRes = R.color.fill_alternative, alpha = 0.16f)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| colorRes | Int | 사용할 색상의 리소스 ID입니다. 기본값은 fill_alternative입니다. |
| alpha | Float | 그라디언트의 투명도입니다. 기본값은 OPACITY_16입니다. |

### 반환
| 타입 | 설명 |
|:---|:---|
| Modifier | shimmerLinear 효과가 적용된 Modifier입니다. |

---

## Note
- 모든 스켈레톤 컴포넌트는 shimmer 또는 shimmerLinear 효과와 함께 사용하여 시각적 피드백을 제공합니다.
- shimmerLinear는 커스텀 그라디언트 각도 및 범위로 차별화된 애니메이션을 제공합니다.
