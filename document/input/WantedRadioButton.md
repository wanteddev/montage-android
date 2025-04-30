# RadioButton

## WantedRadioButton

### 개요
머터리얼 스타일의 원형 라디오 버튼 컴포저블입니다.  
체크 여부, 크기, 컴팩트 여부, 활성화 상태 등을 설정할 수 있으며,  
내부적으로 `WantedTouchArea`를 사용하여 터치 영역을 확장합니다.

### 사용 예시
```kotlin
WantedRadioButton(
    checked = true,
    size = CheckBoxSize.Normal,
    enabled = true,
    onCheckedChange = { selected -> /* 상태 변경 처리 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| checked | Boolean | 라디오 버튼이 선택된 상태인지 여부입니다. |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| enabled | Boolean | 라디오 버튼의 활성화 여부입니다. |
| tight | Boolean | true일 경우 내부 패딩이 줄어들어 컴팩트하게 표시됩니다. |
| interactionSource | MutableInteractionSource | 클릭, 호버 등 상호작용 처리를 위한 인터랙션 소스입니다. |
| size | CheckBoxSize | 라디오 버튼의 크기를 설정합니다. Normal 또는 Small. |
| onCheckedChange | (Boolean) -> Unit | 선택 상태 변경 시 호출되는 콜백입니다. |

---

## Enum 설명

### CheckBoxSize
| 값 | 설명 |
|:---|:---|
| Normal | 일반 사이즈 (보통 24dp) |
| Small | 소형 사이즈 (보통 20dp) |

---

## Note
- tight 모드는 공간이 제한적인 레이아웃에서 컴팩트한 라디오 버튼을 만들 때 유용합니다.
- `enabled = false`일 경우 아이콘 및 외곽선에 투명도가 적용되어 비활성 상태를 시각적으로 구분할 수 있습니다.
