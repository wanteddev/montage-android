---
title: Switch
description: 머터리얼 스타일의 원형 라디오 버튼 컴포저블
image: /components/switch/design/thumbnail.png
createdAt: 2025-04-30
---

## WantedSwitch

### 개요
커스텀 스타일의 토글 스위치 컴포저블입니다.  
머터리얼 스위치와 유사하지만, 색상/사이즈/애니메이션 등을 사용자 정의한 컴포넌트입니다.  
선택 여부 및 활성화 상태에 따라 thumb 위치와 색상이 전환됩니다.

### 사용 예시
```kotlin
WantedSwitch(
    checked = true,
    size = CheckBoxSize.Normal,
    enabled = true,
    onCheckedChange = { toggled -> /* 상태 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| checked | Boolean | 스위치가 켜진 상태인지 여부입니다. |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| enabled | Boolean | 스위치 활성화 여부입니다. |
| size | CheckBoxSize | 스위치의 크기 설정 (Normal, Small). |
| interactionSource | MutableInteractionSource | 상호작용 효과(리플 등)를 처리하기 위한 인터랙션 소스입니다. |
| onCheckedChange | (Boolean) -> Unit | 상태가 변경될 때 호출되는 콜백입니다. |

---
<br />
<br />

## Enum 설명

### CheckBoxSize
| 값 | 설명 |
|:---|:---|
| Normal | 일반 사이즈 (보통 24dp) |
| Small | 소형 사이즈 (보통 20dp) |

---
<br />
<br />

## Note
- 체크 상태에 따라 슬라이더 위치가 자연스럽게 이동하며 색상이 바뀝니다.
- 내부적으로 `Animatable`을 사용하여 부드러운 전환을 제공합니다.
- 터치 영역은 `clickOnce` 유틸로 중복 입력을 방지하며 구성되어 있습니다.
