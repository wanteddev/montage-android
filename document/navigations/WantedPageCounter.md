---
title: PageCounter
description: 페이지 위치를 숫자로 표시하는 페이지 카운터 컴포넌트
image: /components/page-counter/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedPageCounter

### 개요
`WantedPageCounter`는 현재 페이지 번호와 전체 페이지 수를 숫자로 표시하는 UI 컴포넌트입니다. 일반 스타일과 투명한 alternative 스타일을 지원하며, dot indicator보다 명확한 위치 표시가 필요할 때 사용됩니다.

### 사용 예시
```kotlin
WantedPageCounter(
    totalPageCount = 10,
    currentIndex = 2,
    isAlternative = true
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| totalPageCount | Int | 전체 페이지 수입니다. |
| currentIndex | Int | 현재 선택된 페이지 인덱스입니다. |
| modifier | Modifier | 외형 및 배치 설정을 위한 Modifier입니다. |
| size | WantedPageCounterSize | 컴포넌트 크기를 설정합니다. 기본값은 Normal입니다. |
| isAlternative | Boolean | 투명 배경 및 라이트 스타일 여부입니다. 기본값은 false입니다. |

---

## Enum 설명

### WantedPageCounterSize
| 값 | 설명 |
|:---|:---|
| Small | 작고 간결한 스타일. 패딩 10dp x 4dp, 간격 3dp. |
| Normal | 기본 크기. 패딩 12dp x 6dp, 간격 4dp. |

---

## Note
- `currentIndex`는 0부터 시작하는 index이므로 사용자 출력에는 +1 처리가 필요할 수 있습니다.
- `isAlternative`가 true일 경우 배경이 반투명하게 변경되어 어두운 이미지 위에 잘 어울립니다.
