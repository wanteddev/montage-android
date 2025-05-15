---
title: CheckBox
description: 체크박스 컴포저블
image: /components/check-box/design/thumbnail.png
createdAt: 2025-04-30
---


## WantedCheckBox

### 개요
다양한 스타일(CheckBox, RoundCheckBox, Check, Radio, Switch)을 지원하는 커스터마이징 가능한 체크박스 컴포저블입니다.  
각 스타일에 맞는 시각적 표현 및 상호작용이 정의되어 있으며, 크기, 상태, 활성화 여부 등을 설정할 수 있습니다.

### 사용 예시
```kotlin
WantedCheckBox(
    size = CheckBoxSize.Normal,
    style = CheckBoxStyle.CheckBox,
    checkState = CheckBoxState.Checked,
    tight = false,
    enabled = true,
    onCheckedChange = { isChecked -> /* 상태 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| onCheckedChange | (Boolean) -> Unit | 상태가 변경될 때 호출되는 콜백입니다. |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| size | CheckBoxSize | Small 또는 Normal 사이즈를 선택할 수 있습니다. |
| style | CheckBoxStyle | 표시할 체크박스 스타일입니다. |
| checkState | CheckBoxState | 체크박스의 상태를 설정합니다. |
| tight | Boolean | true일 경우 패딩이 줄어든 컴팩트한 형태로 표시됩니다. |
| enabled | Boolean | 체크박스 활성화 여부입니다. |
| interactionSource | MutableInteractionSource | 커스텀 인터랙션 처리를 위한 InteractionSource입니다. |

---
<br />
<br />

## Enum 설명

### CheckBoxSize
| 값 | 설명 |
|:---|:---|
| Normal | 일반 사이즈 (보통 24dp) |
| Small | 소형 사이즈 (보통 20dp) |

### CheckBoxStyle
| 값 | 설명 |
|:---|:---|
| CheckBox | 기본 사각형 체크박스 |
| RoundCheckBox | 원형 배경을 가진 체크박스 |
| Check | 체크 마크만 표시되는 스타일 |
| Radio | 라디오 버튼 형식 |
| Switch | 토글 스위치 형식 |

### CheckBoxState
| 값 | 설명 |
|:---|:---|
| Unchecked | 선택되지 않음 |
| Checked | 선택됨 |
| Indeterminate | 중간 상태 (예: 일부만 선택된 그룹 등) |

---
<br />
<br />

## Note
- CheckBox는 선택 상태가 필요한 모든 UI 상황에서 활용할 수 있습니다.
- Indeterminate 상태는 다중 선택 항목에서 일부만 선택된 경우에 적합합니다.
