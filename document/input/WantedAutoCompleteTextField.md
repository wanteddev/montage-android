---
title: AutoCompleteTextField
description: 자동완성 기능을 제공하는 텍스트 입력 컴포넌트
image: /components/auto-complete-text-field/design/thumbnail.png
createdAt: 2025-05-07
---

## WantedAutoCompleteTextField

### 개요
`WantedAutoCompleteTextField`는 입력값을 기반으로 드롭다운 자동완성 목록을 제공하는 컴포넌트입니다. 입력 필드 외에도 드롭다운 섹션, 직접입력 슬롯, 아이콘, 버튼 등을 구성할 수 있어 높은 커스터마이징이 가능합니다.

### 사용 예시
```kotlin
WantedAutoCompleteTextField(
    text = keyword,
    onValueChange = { keyword = it },
    onExpandedChange = { expanded = it },
    sectionItemCount = { 1 },
    sectionItem = { section, index -> /* 드롭다운 항목 */ }
)
```

### 파라미터

### WantedAutoCompleteTextField (String 기반)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| text | String | 입력된 텍스트입니다. |
| sectionItemCount | (Int) -> Int | 섹션 별 아이템 수를 반환하는 함수입니다. |
| onExpandedChange | (Boolean) -> Unit | 드롭다운 확장 상태 변경 콜백입니다. |
| modifier | Modifier | 외형 설정입니다. |
| placeholder | String | 플레이스홀더 텍스트입니다. |
| title | String | 상단 제목입니다. |
| description | String? | 하단 설명입니다. |
| rightButton | String? | 우측 버튼 텍스트입니다. |
| rightButtonVariant | RightVariant | 우측 버튼 스타일입니다. 기본값 Normal. |
| status | Status | 입력 상태입니다. 기본값 Normal. |
| enabled | Boolean | 입력 활성화 여부입니다. 기본값 true. |
| rightButtonEnabled | Boolean | 우측 버튼 활성화 여부입니다. 기본값 true. |
| maxLines | Int | 최대 줄 수입니다. 기본값 1. |
| minLines | Int | 최소 줄 수입니다. 기본값 1. |
| maxWordCount | Int | 최대 입력 글자 수입니다. 기본값 2000. |
| requiredBadge | Boolean | 필수 입력 뱃지 여부입니다. 기본값 false. |
| expended | Boolean | 드롭다운 확장 여부입니다. |
| anchorPadding | Dp | 필드와 드롭다운 간 간격입니다. 기본값 0.dp. |
| dropDownMaxHeight | Dp | 드롭다운 최대 높이입니다. 기본값 200.dp. |
| sectionTitleHorizontalPadding | Dp | 섹션 제목 좌우 패딩입니다. 기본값 20.dp. |
| sectionCount | Int | 드롭다운 섹션 수입니다. 기본값 1. |
| background | Color | 배경 색상입니다. |
| interactionSource | MutableInteractionSource | 포커스 추적용입니다. |
| focused | State<Boolean> | 포커스 상태입니다. |
| keyboardOptions | KeyboardOptions | 키보드 옵션입니다. |
| keyboardActions | KeyboardActions | 키보드 액션 설정입니다. |
| sectionTitle | ((Int) -> String)? | 섹션 제목을 반환하는 함수입니다. |
| onClickRightButton | () -> Unit | 우측 버튼 클릭 시 콜백입니다. |
| onValueChange | (String) -> Unit | 텍스트 변경 시 호출됩니다. |
| leadingIcon | (() -> Unit)? | 좌측 아이콘 슬롯입니다. |
| trailingIcon | (() -> Unit)? | 우측 아이콘 슬롯입니다. |
| rightContent | ((Dp) -> Unit)? | 우측 영역 콘텐츠 슬롯입니다. |
| topDirectInput | (() -> Unit)? | 드롭다운 상단 직접 입력 슬롯입니다. |
| bottomDirectInput | (() -> Unit)? | 드롭다운 하단 직접 입력 슬롯입니다. |
| sectionItem | @Composable (Int, Int) -> Unit | 드롭다운 항목 UI 입니다. |

### WantedAutoCompleteTextField (TextFieldValue 기반)
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| value | TextFieldValue | 커서 및 선택 포함한 입력 값입니다. |
| onValueChange | (TextFieldValue) -> Unit | 입력 값 변경 콜백입니다. |
| sectionItemCount | (Int) -> Int | 섹션별 아이템 수입니다. |
| sectionItem | @Composable (Int, Int) -> Unit | 섹션 아이템 UI 입니다. |
| onExpandedChange | (Boolean) -> Unit | 드롭다운 확장 상태 변경 콜백입니다. |
| 나머지 파라미터는 String 버전과 동일합니다. |

---
<br />
<br />

## Enum 설명

### Status
| 값 | 설명 |
|:---|:---|
| Normal | 기본 상태입니다. |
| Positive | 성공/완료 상태입니다. |
| Negative | 에러 상태입니다. |

### RightVariant
| 값 | 설명 |
|:---|:---|
| Normal | 강조된 버튼 스타일입니다. |
| Assistive | 보조적인 약한 버튼 스타일입니다. |

---
<br />
<br />

## Note
- `text.isNotEmpty()` 조건에 따라 드롭다운이 자동 확장됩니다.
- `topDirectInput`, `bottomDirectInput`은 사용자 정의 입력 요소를 자동완성 위/아래에 추가할 수 있습니다.
- `status = Negative`인 경우 상태 메시지 또는 우측 경고 아이콘이 표시됩니다.
