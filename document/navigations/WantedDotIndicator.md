---
title: DotIndicator
description: 페이지네이션용 점 형태의 인디케이터
image: /components/dot-indicator/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedDotIndicator

### 개요
`WantedDotIndicator`는 여러 페이지 또는 항목이 있을 때 현재 위치를 dot 형태로 표시하는 컴포넌트입니다. 선택된 페이지는 강조되고, 나머지는 작고 투명하게 표현됩니다. dot의 수, 스타일, 크기를 설정할 수 있으며, 다크 모드와 밝은 배경 모두 대응됩니다.

### 사용 예시
```kotlin
WantedDotIndicator(
    totalPageCount = 10,
    visibleDotCount = 5,
    currentIndex = 2,
    type = WantedDotIndicatorType.Normal,
    size = WantedDotIndicatorSize.Small
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| totalPageCount | Int | 전체 페이지 수입니다. |
| visibleDotCount | Int | 화면에 표시할 최대 dot 수입니다. 기본값은 5입니다. |
| currentIndex | Int | 현재 선택된 페이지 index입니다. |
| modifier | Modifier | 배치 및 외형 설정을 위한 Modifier입니다. |
| size | WantedDotIndicatorSize | dot의 크기입니다. Small 또는 Medium을 지정할 수 있습니다. |
| type | WantedDotIndicatorType | dot의 스타일입니다. Normal 또는 White 중 선택합니다. |

---

## Enum 설명

### WantedDotIndicatorSize
| 값 | 설명 |
|:---|:---|
| Small | 2~6dp의 작은 dot 크기로 구성됩니다. |
| Medium | 6~10dp의 중간 dot 크기로 구성됩니다. |

### WantedDotIndicatorType
| 값 | 설명 |
|:---|:---|
| Normal | 배경이 채워진 일반 dot 스타일입니다. |
| White | 흰색 테두리와 반투명 배경이 적용된 스타일입니다. |

---

## Note
- `visibleDotCount`는 중앙 중심으로 표시되며 양끝이 생략(...)될 수 있습니다.
- `White` 타입은 어두운 배경에서 더 적합합니다.
