# Android 디자인 시스템 3.7.0 → 4.0 변경사항

[English](CHANGES_3.7.0_4.0.0.md) | [한국어](CHANGES_3.7.0_4.0.0.ko.md)

> - **비교 기준**: [v3.7.0](https://github.com/wanteddev/montage-android/releases/tag/v3.7.0) → 4.0.0
> - v3.7.0 이후에 생겼다가 4.0에서 다시 바뀐 API(ListCell·Card의 `cellDefault` 확장 등)는 v3.7.0 사용처가 겪을 일이 없으므로 **변경이 아닌 신규 API**로 적습니다.
> - 호출부를 고쳐야 하는 변경은 **[1장](#1-마이그레이션-체크리스트)에만** 모았습니다. 컴포넌트별 섹션(4~6장)에는 신규 API와 스펙 변경만 적습니다.
> - 단계별 코드 마이그레이션 방법은 [MIGRATION_3.7.0_4.0.0.ko.md](MIGRATION_3.7.0_4.0.0.ko.md)를 참고하세요.

## 목차

1. [마이그레이션 체크리스트](#1-마이그레이션-체크리스트)
   - [1.1 이름 변경](#11-이름-변경) · [1.2 타입·구조 변경](#12-타입구조-변경) · [1.3 삭제](#13-삭제) · [1.4 Deprecated](#14-deprecated) · [1.5 기본값·동작 변경](#15-기본값동작-변경-화면-확인-필요)
2. [공통 디자인 규칙](#2-공통-디자인-규칙)
3. [Foundation](#3-foundation)
4. [Actions](#4-actions)
5. [Input](#5-input)
6. [Contents · Feedback · Navigation · Presentation](#6-contents--feedback--navigation--presentation)
7. [도구 · 데모](#7-도구--데모)

---

## 1. 마이그레이션 체크리스트

| 절 | 성격 | 컴파일 에러 |
|---|---|---|
| [1.1 이름 변경](#11-이름-변경) | 기계적 치환 | O |
| [1.2 타입·구조 변경](#12-타입구조-변경) | 타입/시그니처 교체 | O |
| [1.3 삭제](#13-삭제) | 대체 API로 이전 | O |
| [1.4 Deprecated](#14-deprecated) | 점진적 이전 | 경고만 |
| [1.5 기본값·동작 변경](#15-기본값동작-변경-화면-확인-필요) | **화면 확인** | **X — 빌드는 통과하지만 화면이 달라짐** |

### 1.1 이름 변경

| 컴포넌트 | 3.7.0 | 4.0 | 비고 |
|---|---|---|---|
| Popup | `WantedModal` | `WantedPopup` | |
| SegmentedControl | `WantedSegmentedControlSolid`<br>`WantedSegmentedControlSolidItem(title, isSelected, modifier, icon)` | `WantedSegmentedControl`<br>`WantedSegmentedControlItem(isSelected, modifier, title: String? = null, icon)` | ⚠️ `title`이 세 번째 선택 인자로 이동 → **위치 인자 호출은 깨짐** |
| Avatar | `WantedAvatarType.Academic`<br>`icon_avatar_placeholder_academic` | `WantedAvatarType.Academy`<br>`icon_avatar_placeholder_academy` | |
| Chip | `leftIcon` / `rightIcon` | `leadingContent` / `trailingContent` | |
| TextButton | `leftIconTintColor` / `rightIconTintColor` | `leadingIconTintColor` / `trailingIconTintColor` | `WantedTextButtonDefault`에만 해당. 공용 `WantedButtonDefault`는 left/right 유지 |
| TextField<br>AutoCompleteTextField | `rightButton`<br>`rightButtonEnabled`<br>`onClickRightButton` | `trailingButton`<br>`trailingButtonEnabled`<br>`onClickTrailingButton` | |
| TextArea | `rightButton` / `onClickRightButton`<br>`leftContent` / `rightContent` | `button` / `onClickButton`<br>`leadingContent` / `trailingContent` | |
| ActionArea | `positive` / `negative` / `neutral` (+ `isEnable*`, `onClick*`)<br>`gradationColor`<br>`WantedActionAreaDefault`의 positive/negative/neutral 필드 | `main` / `alternative` / `sub`<br>`backgroundColor`<br>`main` / `alternative` / `subButtonDefault` | |
| FallbackView | `positive` / `negative`<br>`onClickPositive` / `onClickNegative` | `main` / `alternative`<br>`onClickMain` / `onClickAlternative` | |
| ListCell | `caption` / `annotatedCaption` / `captionStyle` | `description` / `annotatedDescription` / `descriptionStyle` | |
| PushBadge | `count`<br>`PushBadgeVariant.Number`<br>`PushBadgeVariant.New` ("N" 고정) | `text`<br>`PushBadgeVariant.Text` (상한이 필요하면 `MaxCount`, 기본 99 → "99+")<br>`PushBadgeVariant.Text` + `text = "N"` | ⚠️ `New`를 `MaxCount`로 옮기면 "N"을 숫자로 해석함 |
| FilterButton | `isExpend` | `isExpanded` | 오타 수정 |

### 1.2 타입·구조 변경

| 컴포넌트 | 3.7.0 | 4.0 | 비고 |
|---|---|---|---|
| TextButton | `color: ButtonType`<br>`size: ButtonSize`<br>`buttonDefault: WantedButtonDefault` | `color: WantedTextButtonColor`<br>`size: WantedTextButtonSize`<br>`buttonDefault: WantedTextButtonDefault` | |
| Button | `ButtonType` (PRIMARY / ASSISTIVE)<br>`ButtonSize` (LARGE / MEDIUM / SMALL) | `ButtonType.NEGATIVE` 추가<br>`ButtonSize.XSMALL` 추가 | ⚠️ `else` 없는 `when` 분기는 컴파일 오류 |
| IconButton | `WantedIconButtonSize` enum (`size`, `padding`) | sealed class (`Medium` 40 / `Small` 32 / `Custom`) | `.size` → `.boxSize`<br>`.padding` 삭제<br>`values()` / `entries` → `presets` |
| IconButton Normal | `modifier`(예: `Modifier.size(24.dp)`)로 아이콘 크기 지정 | `size`로 박스 크기 지정 | 아이콘 크기는 자동 계산 |
| TextArea | `negative: Boolean` | `status: WantedTextAreaDefaults.Status` | |
| ListCell<br>Accordion | `fillWidth: Boolean` | `variant: WantedListCellDefaults.Variant` | `fillWidth = true` → `Full`<br>`fillWidth = false` → `Inset` |
| ListCell | `leadingContent` / `trailingContent`:<br>`(@Composable () -> Unit)?` 단일 슬롯 | `(@Composable RowScope.() -> Unit)?` | 여러 개 배치 가능, 간격 8dp |
| AvatarGroup | `size: WantedAvatarSize` | `size: WantedAvatarGroupSize` | XSmall / Small만 지원 |
| SearchField<br>SearchTopAppBar | `Size.Small()` (40dp)<br>`Size.Medium()` (48dp)<br>`Size.Custom(...)`<br>`textStyle`, `cursorBrush` non-null | `data object Size.Medium` (40dp)<br>`data object Size.Large` (48dp)<br>—<br>nullable (null이면 size 기본값) | ⚠️ 3.7.0 `Medium()` → 4.0 `Large`<br>⚠️ 3.7.0 `Small()` → 4.0 `Medium`<br>**이름만 바꾸면 높이가 8dp 줄어듦** |
| Category | `isAlternative: Boolean` | `variant: WantedCategoryDefaults.Variant` | |
| TopAppBarIconButton | `variant` | `interactionEffect: IconButtonInteractionEffect = Dim` | |
| DialogTopAppBar | `Variant` enum, `Variant.Floating` | sealed class, `Variant.Floating()` 호출 | `values()` / `entries` / `name` 사용 불가 |
| Popup | `type: ModalType`<br>`shape`, `size: ModalSize`<br>`topBar` 슬롯 | `WantedPopupContract.Resize` (Hug / Fixed)<br>`WantedPopupDefault`<br>`title: String?` | |
| BottomSheet | `modalSize: ModalSize` | `sheetDefault: WantedBottomSheetDefault` | |
| ActionArea | `modifier`가 뒤쪽 | `modifier`가 앞쪽 (Compose 관례) | String 오버로드는 `type`, `main` 다음 세 번째 |

### 1.3 삭제

| 컴포넌트 | 삭제된 API | 대체 |
|---|---|---|
| TextField<br>AutoCompleteTextField | `WantedTextFieldDefaults.RightVariant`, `rightButtonVariant` | 없음 (우측 버튼은 Outlined·Assistive 고정) |
| SearchField<br>SearchTopAppBar | `focused`, `Size.Small`, `Size.Custom` | `Size.Large` / `Size.Medium` |
| SegmentedControl | `WantedSegmentedControlOutlined`<br>`WantedSegmentedControlOutlinedItem` | `WantedSegmentedControl` + `WantedSegmentedControlItem`<br>(`SolidItem`은 [1.1](#11-이름-변경) 참고) |
| ListCell | `WantedListCellDefaults.InteractionPadding`<br>`interactionPadding` 파라미터 | `variant` |
| AvatarGroup | `type` | 없음 (항상 Person) |
| PushBadge | `WantedPushBadgeBorder` | `WantedPushBadge(outlineBorder = true)`<br>(기본 테두리색이 다름, [1.5](#15-기본값동작-변경-화면-확인-필요) 참고) |
| FallbackView | `positiveColor` / `negativeColor` | 없음 (버튼 스타일 내부 고정) |
| IconButton | Outlined·Solid의 `padding` 오버로드 (private 전환) | `size` |
| TopAppBar | `LocalWantedTopBarIconVariant`<br>`WantedTopBarIconVariantCompositionLocal` | `interactionEffect` |
| DialogTopAppBar | `Variant.Display` | — |
| Popup | 기본 오버로드의 `negative`, `onClickNegative` | 없음 (Close Button 항상 표시) |
| TextArea | `trailingContent`와 `rightButton`을 함께 받던 `text:` 오버로드 | 둘 중 하나만 받는 오버로드로 분리 |

### 1.4 Deprecated

> deprecated API는 당분간 유지하고 이후 일괄 삭제합니다. deprecated API는 3.7.0 동작을 유지합니다(TextArea는 [1.5](#15-기본값동작-변경-화면-확인-필요) 참고).

| 컴포넌트 | Deprecated | 대체 |
|---|---|---|
| Button | `ButtonVariant.TEXT`<br>(5.0 제거 예정. 기존 호출은 내부에서 TextButton으로 변환돼 동작하며, 기본 LARGE는 TextButton LARGE 40dp로 그려짐) | `WantedTextButton` |
| IconButton | `WantedIconButtonNormal(icon, disableInteraction, …)` | `interactionEffect` |
| TextField | `title` / `requiredBadge` / `description` 오버로드 2개 | `WantedFormControl { 본체 오버로드 }` |
| TextArea | `title` / `description` / `requiredBadge` 오버로드 4개 | `WantedFormControl { 본체 오버로드 }` |
| Select | `title` / `isRequiredBadge` / `description` / `negative` 오버로드 3개 | `WantedFormControl { 본체 오버로드 }` |
| Select | `WantedSelectWithString` | `WantedSelect(valueList = …)` |
| FallbackView | `image` 슬롯 오버로드 | 이미지 없는 오버로드 |
| ContentBadge | `ContentBadgeSize.Large` | `ContentBadgeSize.Medium` (스펙 동일) |
| Toast | `WantedToastVariant.Message` | `WantedToastVariant.Normal` (렌더링 동일) |

FormControl 이전 예시:

```kotlin
// 3.7.0
WantedTextField(title = "이름", value = name, ...)

// 4.0
WantedFormControl(label = "이름") {
    WantedTextField(value = name, ...)
}
```

### 1.5 기본값·동작 변경 (화면 확인 필요)

> ⚠️ **컴파일은 그대로 되지만 화면이 달라지는 항목입니다.** 치환이 끝났다고 마이그레이션이 끝난 것이 아닙니다.

| 대상 | 3.7.0 | 4.0 | 확인 포인트 |
|---|---|---|---|
| Opacity 컬러 | 레거시 hex 잔존 (#3366FF 등) | 기준 토큰 RGB + N% 알파 | opacity 토큰을 쓰는 화면의 색 |
| BottomSheet | content 여백 없음 | 시트가 여백 포함<br>(일반 좌우 28dp, Full 24 / 20dp) | 여백을 직접 주던 화면은 이중 여백 → `getWithoutContentPadding()` |
| AutoComplete 드롭다운 | 항목 좌우 여백 없음 (셀이 직접 줌) | 리스트가 항목·직접입력 슬롯에 좌우 20dp<br>(`sectionTitleHorizontalPadding`) | 안의 ListCell은 `Inset`(기본값)으로 두고 자체 여백 제거. 남겨 두면 40dp |
| Select 바텀시트 (Radio) | 선택된 항목에만 라디오 표시 | 모든 항목에 라디오 표시 (미선택은 빈 라디오)<br>라디오를 직접 눌러도 선택 | `SelectType.Radio` 목록의 항목 정렬·폭 |
| SearchField | 기본 `Size.Medium()` (48dp)<br>입력 글자 body1Regular | 기본 `Size.Large` (48dp, 높이 동일)<br>입력 글자 body2Regular | 입력 글자 크기 |
| DialogTopAppBar | 내부 여백 세로 8 / 가로 16dp | `navigationPadding` 사방 24dp<br>(Full 시트 스펙 20dp = `FULL_NAVIGATION_PADDING`) | 3.7.0용 여백 보정이 남은 시트의 제목 위치·높이 |
| PushBadge | `WantedPushBadgeBorder` 테두리 기본색 `staticWhite`, 두께 2dp | `outlineBorder` 기본색 `backgroundNeutralPrimary`, 두께는 사이즈별 규격 | 다크모드 배지 테두리 색 |
| IconButton Outlined | `disableBackground` transparent | `backgroundNeutralPrimary` | 비활성 버튼 배경 |
| ListCard | 눌림 영역 비대칭 라운드 (좌 20 / 우 12dp, 고정) | `cardDefault.interactionShape` 기본 `RoundedCornerShape(12.dp)`<br>(WantedCard는 기존 모양 유지) | 눌림 영역 모양 |
| AvatarGroup | 개수 제한 없음 | 최대 5명 | 6명 이상 노출 화면 |

변경 폭이 큰 컴포넌트는 아래에 따로 정리합니다.

#### ListCell

| 항목 | 3.7.0 | 4.0 |
|---|---|---|
| `ellipsis` 기본값 | `true` | `false` |
| `verticalAlignCenter` 기본값 | `= ellipsis`<br>슬롯을 텍스트 영역 전체(제목+설명) 가운데에 정렬 | `false`<br>슬롯을 제목 첫 줄 기준으로 정렬 (true여도 첫 줄 가운데) |
| `textMaxLine` | 항상 적용 | `ellipsis = true`일 때만 적용 |
| 비활성 표현 | 셀 전체 투명도 43% | 제목·설명·Check·chevron에만 disable 색<br>슬롯은 호출부에서 처리 |

**확인 포인트**: 긴 텍스트 셀의 줄 수 · 제목 2줄 이상/설명 있는 셀의 아이콘 정렬 · 비활성 셀 색

#### TextArea

| 항목 | 3.7.0 | 4.0 본체 오버로드<br>`WantedTextArea(value, …)` | 4.0 deprecated 오버로드<br>(title·description 포함 4개) |
|---|---|---|---|
| min / max 줄 | 1 / 3 | 2 / 6 | 1 / 3 |
| 기본 `resize` | — (`maxLines`가 항상 상한) | `Normal` (높이 무제한, `maxLines`는 `Limit`에서만 사용) | `Limit` |
| 글자 수 카운터 | 슬롯이 비면 왼쪽 아래 자동 표시 | 자동 표시 없음 (FormControl `accessory`로 이동) | 슬롯이 비면 자동 표시 |
| `description = ""` | 설명 줄 없음 | 빈 줄 | 무시 |

**확인 포인트**: deprecated → 본체 오버로드로 옮길 때 `resize`·카운터를 직접 지정해야 3.7.0과 같습니다.

#### ActionArea

| 항목 | 3.7.0 | 4.0 |
|---|---|---|
| `divider` 기본값 | `false` | `true` (extra 있을 때만 표시) |
| Strong sub 슬롯 여백 | 없음 | 위아래 8dp (텍스트 버튼 전용, 전체 폭 버튼은 `alternative`) |
| 배경 | `background = true`여도 그라데이션만 화면 배경색(`backgroundNormalNormal`)으로 그림 | 영역 전체를 `surfaceElevatedPrimary`로 칠함 |

**확인 포인트**: extra 영역 구분선 · sub 간격 이중 여백 · 다크모드 하단 띠

#### FallbackView

| 항목 | 3.7.0 | 4.0 |
|---|---|---|
| 위아래 여백 | 8dp | `padding` 기본 `Normal` = 각 160dp (`Compact` 80dp) |
| 버튼 | SMALL | MEDIUM |
| 제목 | heading2Bold | headline1Bold |
| 설명 | body1ReadingRegular | body2ReadingRegular |

**확인 포인트**: 바텀시트·리스트 안 빈 화면이 잘리거나 길어짐 · 제목·설명 크기

---

## 2. 공통 디자인 규칙

### 2.1 Semantic Color Token

- 모든 컴포넌트의 기본 색이 새 Semantic Token(**용도 / 역할 / 변형**)으로 바뀌었습니다.
  - `WantedColorScheme`: 59 → 76개
  - `WantedColorOpacityScheme`: 72개 그대로, 이름만 변경
- Opacity 변형 이름도 기준 토큰을 따릅니다.
  - 예: `primary_normal_opacityN` → `surface_brand_primary_opacityN`
- Atomic Color는 opacity 변형 추가만 있습니다.
- **토큰 교체 외 변경이 없는 컴포넌트**: Radio, Switch, CheckMark, Slider, Tab, Progress Indicator, Progress Tracker, Snackbar, Alert, Section Header, Loading, Card Description, beta 패키지
- 티켓: WRP-1054, WRP-1612

### 2.2 Form Control 분리

라벨·필수 표시·설명·글자 수는 `WantedFormControl`이 담당하고, TextField·TextArea·Select는 **입력 본체만** 그립니다.

| 컴포넌트 | Status | Size |
|---|---|---|
| FormControl | Normal / Positive / Negative | Large (label1Bold) / Medium (label2Bold) |
| TextField | `error` 파라미터 | Large 48dp·r14 / Medium 40dp·r12 |
| TextArea | Normal / Negative | Large 48dp / Medium 44dp |
| Select | Normal / Negative | Large 48dp / Medium 40dp |
| SearchField | — | Large 48dp / Medium 40dp |

### 2.3 Focus Ring · Border

- 신규 Modifier
  - `Modifier.framedStyle(focused = …)`
  - `Modifier.focusRing(visible, shape, color = lineBrandFocus, width = 4.dp)`
- TextField · TextArea · Select 공통 규칙
  - 포커스 시 4dp Focus Ring (Negative는 red 12%)
  - 포커스 border 1dp, 색 `primaryNormal` → `lineBrandStrong`
  - Negative + Focus는 border opacity 52%

### 2.4 Active 스타일 (Chip · FilterButton)

| 항목 | 3.7.0 | 4.0 |
|---|---|---|
| Solid Active | 검정 반전(inverse) | brand 5% 배경 + brand 텍스트 |
| Outlined Active 테두리 | brand 43% | brand 28% |

---

## 3. Foundation

### 3.1 치수 토큰 (신규)

`DesignSystemTheme.primitive` / `.spacing` / `.radius` / `.dimension`으로 접근합니다. (WRP-3160: Radius 28·32 추가)

| 토큰 | 용도 | 값 (dp) |
|---|---|---|
| `primitive` | 원시 값 (직접 사용 비권장) | 0, 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72, 80, 9999 |
| `spacing` | 간격·패딩·마진 | 0, 2, 4, 6, 8, 10, 12, 14, 16, 20, 24, 32, 40, 48, 56, 64, 72, 80 |
| `radius` | 모서리 | 0, 4, 8, 10, 12, 14, 16, 20, 24, 28, 32, `radiusFull` |
| `dimension` | 높이·너비 | 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64 |

### 3.2 아이콘

| 구분 | 아이콘 | 티켓 |
|---|---|---|
| 추가 | `icon_normal_circle_{check, close, exclamation, info, plus, question}_opaque` | WRP-1252 |
| 추가 | `icon_normal_hash_tag` | WRP-2263 |
| 추가 | `icon_normal_image_fill`, `icon_normal_position_review` | WRP-1074 |
| path 수정 | `icon_normal_company`, `_fill`, `_check`, `_check_fill`, `_plus`, `_plus_fill` | WRP-1074 |

---

## 4. Actions

### Button

- **신규 API**
  - `ButtonType.NEGATIVE`
  - `ButtonSize.XSMALL`
- **스펙 변경**
  - 높이·너비를 최소값으로 잡아 fontScale 확대에 대응
  - Solid NEGATIVE: negative 12% 배경 + `foregroundNegativeStrong`
  - Outlined NEGATIVE는 미지원 (PRIMARY로 그리고 에러 로그)
- 티켓: WRP-863, WRP-2096

**사이즈 (L / M / S / XS)**

| 항목 | 3.7.0 | 4.0 |
|---|---|---|
| 최소 높이 | 48 / 40 / 32 / — | 48 / 40 / 32 / 28 |
| 가로 패딩 | 28 / 20 / 14 / — | 20 / 16 / 12 / 10 |
| 세로 패딩 | 4 | 13 / 10 / 8 / 6 |
| Radius | 12 / 10 / 8 / — | 14 / 12 / 10 / 8 |
| 아이콘 | 스펙보다 크게 그려짐 | 20 / 18 / 16 / 14 |
| 로딩 | 18 / 16 / 14 / — | 16 / 14 / 12 / 12 |
| 타이포 | type·size별 상이 | body2Bold / label1Bold / caption1Bold / caption1Bold |

### TextButton

- **신규 API**
  - `WantedTextButtonColor` (PRIMARY / ASSISTIVE)
  - `WantedTextButtonSize` (SMALL / MEDIUM)
  - `WantedTextButtonDefaults.getDefault()`
- **스펙 변경**
  - 최소 높이 S 28 / M 32, 세로 패딩 4
  - 터치 영역 가로 패딩 M 7 / S 6, radius 6
  - ASSISTIVE 색 `foregroundNeutralTertiary`
  - Dialog 버튼·Tooltip 액션 버튼이 TextButton으로 전환 (Dialog Negative는 MEDIUM)
- 티켓: WRP-771, WRP-942, WRP-2096

### IconButton

- **신규 API**
  - `IconButtonInteractionEffect` (Highlight / Dim / None)
  - `IconButtonBadgePosition` (9방향)
  - `WantedIconButtonNormalSize` (36 / 32 / 28 / 24 / Custom)
  - `WantedIconButtonBackgroundSize` (32 / Custom)
  - 공통: `pushBadge`, `badgePosition`, `interactionColor`, `disableInteraction`
  - Normal: `interactionOverflow` / Background: `useNormalInteraction`
- **스펙 변경**
  - 아이콘 크기 = 박스 × 2/3 (Normal·Background), × 0.47 (Outlined·Solid)
  - 박스 24~64dp 제한, 터치 영역 = 박스
  - Ripple 0.08 / Background normal 0.12 / Solid 0.20
  - 배지 위치: Normal은 아이콘 모서리, 나머지는 박스 모서리 기준
- 티켓: WRP-889, WRP-927, WRP-963, WRP-964, WRP-965, WRP-2568, WRP-2814, WRP-3052, WRP-3163

### Chip

- **스펙 변경**

  | 항목 | XS | S | M | L |
  |---|---|---|---|---|
  | 최소 높이 | 24 | 32 | 36 | 40 |
  | Radius | 8 | 10 | — | 12 |
  | 아이콘–텍스트 간격 | 0 | — | 2 | 2 |
  | 타이포 (Medium) | caption2 | caption1 | label2 | label1 |

  - `—`는 원문에 별도 값이 없는 항목
  - 패딩 증가
  - Active ripple brand 색
- 티켓: WRP-1716, WRP-2220, WRP-2281, WRP-2841

### ActionArea

- **신규 API**
  - `captionIcon` (권장값 `CAPTION_ICON`)
- **스펙 변경**
  - Alternative 버튼: Outlined Assistive
  - Cancel 타입 main 버튼: Solid Assistive
  - 캡션 label2Medium, extra 가로 패딩 24
  - Strong 타입 sub 위아래 8, Neutral 타입 sub 최소 폭 제거
- 티켓: WRP-2463, WRP-3028

---

## 5. Input

> Status·Size·Focus 규칙은 [2장 공통 디자인 규칙](#2-공통-디자인-규칙)을 참고하세요.

### FormControl

- **신규 API**
  - `WantedFormControl(label, required, description, accessory, size, status, labelPlacement, enabled, input)`
  - `LabelPlacement` (Top / Leading)
- **스펙 변경**
  - 라벨 1줄 말줄임
  - 필수 표시는 라벨 옆 인라인·하단 정렬
- 티켓: WRP-1243, WRP-1374, WRP-1664, WRP-2221, WRP-2835

### TextField · AutoCompleteTextField

- **신규 API**
  - 본체 `WantedTextField(value: TextFieldValue, …)`
  - `complete` (비포커스 시 체크 아이콘)
- **스펙 변경**
  - 비포커스 single line 말줄임
  - TalkBack 값 중복 낭독 제거
  - Negative 우측 느낌표 아이콘 제거
- 티켓: WRP-1050, WRP-2830

### TextArea

- **신규 API**
  - 본체 `WantedTextArea(value, …)`
  - `Resize` (Normal / Limit / Fixed)
  - `button` (지정 시 trailingContent 대신 버튼)
- **스펙 변경**
  - 글자 수 caption1Bold
  - placeholder Reading 계열 타이포
  - 하단 영역 12dp 패딩, 버튼 우측 정렬
- 티켓: WRP-1248, WRP-2222, WRP-2267

### Select

- **신규 API**
  - 본체 `WantedSelect` 오버로드 4개
  - `leadingIcon`, `errorDataList` / `errorList`
- **스펙 변경**
  - ripple 중립색
  - Chip 스타일 개편 (Negative: red 5% 배경·22% 보더)
  - 여러 줄 overflow 시 상하 여백 유지
  - Radio 바텀시트: 모든 항목에 라디오 표시, 라디오 직접 탭으로도 선택
- 티켓: WRP-1723, WRP-2847

### Checkbox

- **신규 API**
  - `WantedCheckBox(onCheckedChange, modifier, size, style, checkState, tight, enabled, interactionSource)` 공개
  - 3.7.0에서는 internal이라 deprecated `WantedCheckBox(checked, …)`가 안내하는 대체를 호출할 수 없었음

### SearchField

- **신규 API**
  - `variant` (Solid / Outlined)
- **스펙 변경**
  - clear 아이콘은 값이 있고 enabled면 항상 노출
- 티켓: WRP-2007, WRP-2884

### SegmentedControl

- **신규 API**
  - `WantedSegmentedControlItem` (icon + text / Icon Only)
  - `iconOnly`, `LocalWantedSegmentedIconOnly`
  - `SegmentedSize` 치수 프로퍼티, `ContainerPadding` (4dp)
- **스펙 변경**
  - 항목 세로 패딩 적용
- 티켓: WRP-1629, WRP-2837

### FilterButton

- **스펙 변경**
  - 타이포 한 단계 축소 (XSmall caption2Medium ~ Large label1Medium)
  - activeLabel SemiBold 제거
  - 패딩·간격·radius 조정
- 티켓: WRP-1770, WRP-2223, WRP-2850

### Date / Time Picker

- **스펙 변경**
  - `WantedPopup` 기반, 고정 radius 28 제거
- 티켓: WRP-3124

---

## 6. Contents · Feedback · Navigation · Presentation

### Contents

| 컴포넌트 | 신규 API | 스펙 변경 | 티켓 |
|---|---|---|---|
| ListCell<br>Accordion | • `Variant` — Inset (패딩 0 / outset 12 / radius 16) · Full (20 / 0 / 0)<br>• `labelTrailingContent`, `extraContent`, `enabledInnerTouch`<br>• ListCell `cellDefault: WantedListCellDefault` (`backgroundColor`, `shape`, `border`, `contentPadding`)<br>• `WantedListCellDefaults.getDefault()` | • disabled 셀 클릭 차단<br>• selected: primary 색 + Body2 Bold, 선택 semantics<br>• `VerticalPadding.None`이면 인터랙션 없음<br>• Select·AutoComplete 내부 셀은 Inset 고정 | WRP-2179<br>WRP-3027 |
| Card<br>ListCard | • `WantedCardDefault`의 `backgroundColor`, `shape`, `border`, `interactionShape`, `contentPadding`<br>• `WantedCardDefaults.getDefault()` | • ListCard 눌림 영역 좌 20 / 우 12dp → 12dp 라운드 (`interactionShape`로 변경 가능)<br>• WantedCard 눌림 영역은 그대로 (`interactionShape` 미적용) | — |
| Avatar | • `contentDescription`<br>• `Custom`의 `cornerRadius` 기본 공식 | • Radius 사이즈별 +2 (8 ~ 16)<br>• Push Badge outline + inset<br>• `placeHolder`가 null이면 type별 기본 placeholder | WRP-1927<br>WRP-2854 |
| AvatarGroup | • `WantedAvatarGroupSize` (XSmall / Small)<br>• `WantedAvatarGroupTrailingText`, `…TrailingTextButton`<br>• `contentDescription` | • 테두리 2 → 1.5dp<br>• trailing 높이 24 / 32dp 고정 | WRP-1947<br>WRP-2883 |
| ContentBadge | • `ContentBadgeSize.Medium` | — | WRP-557 |
| PushBadge | • `maxCount`, `outlineBorder`, `outlineBorderColor`, `textStyle`, `inset` | • MaxCount 초과 시 "99+"<br>• fontScale 1.3까지만 반영 | WRP-1862<br>WRP-2853 |
| Category | • `WantedCategoryDefault`, `getDefault()`, `getChipDefault()`<br>• `Size.rightIconSize` (20 / 22 / 24) | • Normal 활성: `foregroundNeutralStrong` 배경 + inverse 텍스트<br>• Alternative 활성 테두리 brand 43% | WRP-3199 |

### Feedback

| 컴포넌트 | 신규 API | 스펙 변경 | 티켓 |
|---|---|---|---|
| FallbackView | • `padding: WantedFallbackPadding` (Normal 160 / Compact 80dp) | • 버튼 SMALL → MEDIUM<br>• 제목 heading2Bold → headline1Bold<br>• 설명 body1ReadingRegular → body2ReadingRegular | WRP-2036<br>WRP-2268<br>WRP-2885 |
| Toast | • `WantedToastVariant.Normal` (기본값) | • 전역 토스트(`WantedGlobalToastManager`)는 Compose 콘텐츠를 띄울 수 없는 Activity(`ViewTreeLifecycleOwner` 없음)에서 표시를 건너뜀 (크래시 방지) | WRP-558 |

### Navigation

| 컴포넌트 | 신규 API | 스펙 변경 | 티켓 |
|---|---|---|---|
| TopAppBar | — | • 아이콘 버튼 24dp, 터치 40dp 원형 | WRP-2568 |
| DialogTopAppBar | • `WantedDialogSearchTopAppBar`<br>• `navigationPadding` (`NAVIGATION_PADDING` 24 / `FULL_NAVIGATION_PADDING` 20)<br>• `Variant.Search`, `Floating(iconBackground)` | • 닫기 버튼 24dp · 컨테이너 36dp · Dim<br>• 최소 높이 56dp, Leading–제목 간격 16dp (Search 12dp) | WRP-3283 |
| Pagination Dots | — | • 전체 5페이지 이하면 모든 dot 풀사이즈 | WRP-995 |

### Presentation

| 컴포넌트 | 신규 API | 스펙 변경 | 티켓 |
|---|---|---|---|
| Popover<br>Tooltip | • `screenEdgePadding` — 화면 경계에서 유지할 최소 여백 (기본 Popover 8dp / Tooltip 2dp, 3.7.0 고정값과 동일) | • Tooltip 꼬리 위치를 툴팁 폭 안으로 제한<br>• Tooltip 좌우 여백이 화면보다 크면 남는 공간만큼만 여백 적용 | — |
| Popup | • `WantedPopupDefaults.getDefault()` (radius 24, width 360, content 좌우 28dp) | • Close Button 항상 표시<br>• 확인 버튼은 `WantedActionArea` (Strong) | WRP-3124 |
| BottomSheet | • `WantedBottomSheetDefaults.getDefault()` / `getFullDefault()` / `getWithoutContentPadding()` | • Radius 16 → 32<br>• 높이 고정 타입은 Action Area 하단 고정 | WRP-3125 |

---

## 7. 도구 · 데모

| 항목 | 내용 | 티켓 |
|---|---|---|
| designdemo | TextButton, FormControl, SegmentedControl, Select, FallbackView 데모<br>ColorToken · Icon · Typography 카탈로그 | — |
| 사용량 추적 | `./gradlew :app:trackComposableUsageWithException`<br>컴포넌트별 사용량 JSON (Preview 제외) | WRP-1442 |
| sync 워크플로우 | designdemo → montage-android sample 동기화 | WRP-654 |
| KDoc → MDX | configuration cache 호환, `@param` / `@property` 형식 정리 | WRP-2638, WRP-2634, WRP-957 |
