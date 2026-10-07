# Android Design System 3.7.0 → 4.0 Changes

[English](CHANGES_3.7.0_4.0.0.md) | [한국어](CHANGES_3.7.0_4.0.0.ko.md)

> - **Baseline**: [v3.7.0](https://github.com/wanteddev/montage-android/releases/tag/v3.7.0) → 4.0.0
> - APIs that appeared after v3.7.0 and changed again before 4.0 (e.g. the `cellDefault` extension on ListCell and Card) never reached v3.7.0 users, so they are listed as **new APIs, not changes**.
> - Changes that require call-site updates are collected **only in [Section 1](#1-migration-checklist)**. Per-component sections (4–6) list only new APIs and spec changes.
> - For step-by-step code migration, see [MIGRATION_3.7.0_4.0.0.md](MIGRATION_3.7.0_4.0.0.md).

## Contents

1. [Migration checklist](#1-migration-checklist)
   - [1.1 Renames](#11-renames) · [1.2 Type and structure changes](#12-type-and-structure-changes) · [1.3 Removed](#13-removed) · [1.4 Deprecated](#14-deprecated) · [1.5 Default and behavior changes](#15-default-and-behavior-changes-visual-check-required)
2. [Common design rules](#2-common-design-rules)
3. [Foundation](#3-foundation)
4. [Actions](#4-actions)
5. [Input](#5-input)
6. [Contents · Feedback · Navigation · Presentation](#6-contents--feedback--navigation--presentation)
7. [Tools and demo](#7-tools-and-demo)

---

## 1. Migration checklist

| Section | Kind | Compile error |
|---|---|---|
| [1.1 Renames](#11-renames) | Mechanical rename | Yes |
| [1.2 Type and structure changes](#12-type-and-structure-changes) | Swap types / signatures | Yes |
| [1.3 Removed](#13-removed) | Move to replacement API | Yes |
| [1.4 Deprecated](#14-deprecated) | Gradual migration | Warning only |
| [1.5 Default and behavior changes](#15-default-and-behavior-changes-visual-check-required) | **Visual check** | **No — the build passes but the UI changes** |

### 1.1 Renames

| Component | 3.7.0 | 4.0 | Notes |
|---|---|---|---|
| Popup | `WantedModal` | `WantedPopup` | |
| SegmentedControl | `WantedSegmentedControlSolid`<br>`WantedSegmentedControlSolidItem(title, isSelected, modifier, icon)` | `WantedSegmentedControl`<br>`WantedSegmentedControlItem(isSelected, modifier, title: String? = null, icon)` | ⚠️ `title` moved to the third, optional parameter → **positional calls break** |
| Avatar | `WantedAvatarType.Academic`<br>`icon_avatar_placeholder_academic` | `WantedAvatarType.Academy`<br>`icon_avatar_placeholder_academy` | |
| Chip | `leftIcon` / `rightIcon` | `leadingContent` / `trailingContent` | |
| TextButton | `leftIconTintColor` / `rightIconTintColor` | `leadingIconTintColor` / `trailingIconTintColor` | Only on `WantedTextButtonDefault`. The shared `WantedButtonDefault` keeps left/right |
| TextField<br>AutoCompleteTextField | `rightButton`<br>`rightButtonEnabled`<br>`onClickRightButton` | `trailingButton`<br>`trailingButtonEnabled`<br>`onClickTrailingButton` | |
| TextArea | `rightButton` / `onClickRightButton`<br>`leftContent` / `rightContent` | `button` / `onClickButton`<br>`leadingContent` / `trailingContent` | |
| ActionArea | `positive` / `negative` / `neutral` (+ `isEnable*`, `onClick*`)<br>`gradationColor`<br>positive/negative/neutral fields of `WantedActionAreaDefault` | `main` / `alternative` / `sub`<br>`backgroundColor`<br>`main` / `alternative` / `subButtonDefault` | |
| FallbackView | `positive` / `negative`<br>`onClickPositive` / `onClickNegative` | `main` / `alternative`<br>`onClickMain` / `onClickAlternative` | |
| ListCell | `caption` / `annotatedCaption` / `captionStyle` | `description` / `annotatedDescription` / `descriptionStyle` | |
| PushBadge | `count`<br>`PushBadgeVariant.Number`<br>`PushBadgeVariant.New` (fixed "N") | `text`<br>`PushBadgeVariant.Text` (use `MaxCount` for an upper bound; default 99 → "99+")<br>`PushBadgeVariant.Text` + `text = "N"` | ⚠️ Moving `New` to `MaxCount` makes it parse "N" as a number |
| FilterButton | `isExpend` | `isExpanded` | Typo fix |

### 1.2 Type and structure changes

| Component | 3.7.0 | 4.0 | Notes |
|---|---|---|---|
| TextButton | `color: ButtonType`<br>`size: ButtonSize`<br>`buttonDefault: WantedButtonDefault` | `color: WantedTextButtonColor`<br>`size: WantedTextButtonSize`<br>`buttonDefault: WantedTextButtonDefault` | |
| Button | `ButtonType` (PRIMARY / ASSISTIVE)<br>`ButtonSize` (LARGE / MEDIUM / SMALL) | Added `ButtonType.NEGATIVE`<br>Added `ButtonSize.XSMALL` | ⚠️ Exhaustive `when` without `else` fails to compile |
| IconButton | `WantedIconButtonSize` enum (`size`, `padding`) | sealed class (`Medium` 40 / `Small` 32 / `Custom`) | `.size` → `.boxSize`<br>`.padding` removed<br>`values()` / `entries` → `presets` |
| IconButton Normal | Icon size set via `modifier` (e.g. `Modifier.size(24.dp)`) | Box size set via `size` | Icon size is computed automatically |
| TextArea | `negative: Boolean` | `status: WantedTextAreaDefaults.Status` | |
| ListCell<br>Accordion | `fillWidth: Boolean` | `variant: WantedListCellDefaults.Variant` | `fillWidth = true` → `Full`<br>`fillWidth = false` → `Inset` |
| ListCell | `leadingContent` / `trailingContent`:<br>`(@Composable () -> Unit)?` single slot | `(@Composable RowScope.() -> Unit)?` | Multiple items allowed, 8dp spacing |
| AvatarGroup | `size: WantedAvatarSize` | `size: WantedAvatarGroupSize` | XSmall / Small only |
| SearchField<br>SearchTopAppBar | `Size.Small()` (40dp)<br>`Size.Medium()` (48dp)<br>`Size.Custom(...)`<br>`textStyle`, `cursorBrush` non-null | `data object Size.Medium` (40dp)<br>`data object Size.Large` (48dp)<br>—<br>nullable (null falls back to the size default) | ⚠️ 3.7.0 `Medium()` → 4.0 `Large`<br>⚠️ 3.7.0 `Small()` → 4.0 `Medium`<br>**Renaming alone shrinks the height by 8dp** |
| Category | `isAlternative: Boolean` | `variant: WantedCategoryDefaults.Variant` | |
| TopAppBarIconButton | `variant` | `interactionEffect: IconButtonInteractionEffect = Dim` | |
| DialogTopAppBar | `Variant` enum, `Variant.Floating` | sealed class, call `Variant.Floating()` | `values()` / `entries` / `name` no longer available |
| Popup | `type: ModalType`<br>`shape`, `size: ModalSize`<br>`topBar` slot | `WantedPopupContract.Resize` (Hug / Fixed)<br>`WantedPopupDefault`<br>`title: String?` | |
| BottomSheet | `modalSize: ModalSize` | `sheetDefault: WantedBottomSheetDefault` | |
| ActionArea | `modifier` near the end | `modifier` near the front (Compose convention) | In the String overload it is third, after `type` and `main` |

### 1.3 Removed

| Component | Removed API | Replacement |
|---|---|---|
| TextField<br>AutoCompleteTextField | `WantedTextFieldDefaults.RightVariant`, `rightButtonVariant` | None (trailing button is fixed to Outlined · Assistive) |
| SearchField<br>SearchTopAppBar | `focused`, `Size.Small`, `Size.Custom` | `Size.Large` / `Size.Medium` |
| SegmentedControl | `WantedSegmentedControlOutlined`<br>`WantedSegmentedControlOutlinedItem` | `WantedSegmentedControl` + `WantedSegmentedControlItem`<br>(for `SolidItem`, see [1.1](#11-renames)) |
| ListCell | `WantedListCellDefaults.InteractionPadding`<br>`interactionPadding` parameter | `variant` |
| AvatarGroup | `type` | None (always Person) |
| PushBadge | `WantedPushBadgeBorder` | `WantedPushBadge(outlineBorder = true)`<br>(default border color differs, see [1.5](#15-default-and-behavior-changes-visual-check-required)) |
| FallbackView | `positiveColor` / `negativeColor` | None (button style fixed internally) |
| IconButton | `padding` overloads of Outlined · Solid (now private) | `size` |
| TopAppBar | `LocalWantedTopBarIconVariant`<br>`WantedTopBarIconVariantCompositionLocal` | `interactionEffect` |
| DialogTopAppBar | `Variant.Display` | — |
| Popup | `negative`, `onClickNegative` on the default overload | None (Close Button always shown) |
| TextArea | `text:` overload that took both `trailingContent` and `rightButton` | Split into overloads that take only one of them |

### 1.4 Deprecated

> Deprecated APIs are kept for now and will be removed together later. They keep their 3.7.0 behavior (for TextArea, see [1.5](#15-default-and-behavior-changes-visual-check-required)).

| Component | Deprecated | Replacement |
|---|---|---|
| Button | `ButtonVariant.TEXT`<br>(to be removed in 5.0. Existing calls are converted to TextButton internally; the default LARGE renders as TextButton LARGE at 40dp) | `WantedTextButton` |
| IconButton | `WantedIconButtonNormal(icon, disableInteraction, …)` | `interactionEffect` |
| TextField | 2 overloads with `title` / `requiredBadge` / `description` | `WantedFormControl { core overload }` |
| TextArea | 4 overloads with `title` / `description` / `requiredBadge` | `WantedFormControl { core overload }` |
| Select | 3 overloads with `title` / `isRequiredBadge` / `description` / `negative` | `WantedFormControl { core overload }` |
| Select | `WantedSelectWithString` | `WantedSelect(valueList = …)` |
| FallbackView | Overload with an `image` slot | Overload without an image |
| ContentBadge | `ContentBadgeSize.Large` | `ContentBadgeSize.Medium` (same spec) |
| Toast | `WantedToastVariant.Message` | `WantedToastVariant.Normal` (renders the same) |

FormControl migration example:

```kotlin
// 3.7.0
WantedTextField(title = "Name", value = name, ...)

// 4.0
WantedFormControl(label = "Name") {
    WantedTextField(value = name, ...)
}
```

### 1.5 Default and behavior changes (visual check required)

> ⚠️ **These still compile, but the UI changes.** Finishing the renames does not mean the migration is done.

| Target | 3.7.0 | 4.0 | What to check |
|---|---|---|---|
| Opacity colors | Some legacy hex values remained (#3366FF, etc.) | Base token RGB + N% alpha | Colors on screens using opacity tokens |
| BottomSheet | No content padding | Sheet includes padding<br>(regular 28dp horizontal, Full 24 / 20dp) | Screens that added their own padding get double padding → `getWithoutContentPadding()` |
| AutoComplete dropdown | No horizontal padding on items (cells provided it) | List applies 20dp horizontal padding to items and the direct-input slot<br>(`sectionTitleHorizontalPadding`) | Keep inner ListCells at `Inset` (default) and remove their own padding; otherwise it becomes 40dp |
| Select bottom sheet (Radio) | Radio shown only on the selected item | Radio shown on every item (empty radio when unselected)<br>Tapping the radio itself also selects | Item alignment and width in `SelectType.Radio` lists |
| SearchField | Default `Size.Medium()` (48dp)<br>Input text body1Regular | Default `Size.Large` (48dp, same height)<br>Input text body2Regular | Input text size |
| DialogTopAppBar | Inner padding 8 vertical / 16dp horizontal | `navigationPadding` 24dp on all sides<br>(Full sheet spec 20dp = `FULL_NAVIGATION_PADDING`) | Title position and height on sheets that still carry 3.7.0 padding workarounds |
| PushBadge | `WantedPushBadgeBorder` default border `staticWhite`, 2dp | `outlineBorder` default `backgroundNeutralPrimary`, width per size spec | Badge border color in dark mode |
| IconButton Outlined | `disableBackground` transparent | `backgroundNeutralPrimary` | Disabled button background |
| ListCard | Asymmetric pressed-area corners (left 20 / right 12dp, fixed) | `cardDefault.interactionShape` defaults to `RoundedCornerShape(12.dp)`<br>(WantedCard keeps its original shape) | Pressed-area shape |
| AvatarGroup | No limit | Up to 5 | Screens showing 6 or more |

Components with larger changes are broken out below.

#### ListCell

| Item | 3.7.0 | 4.0 |
|---|---|---|
| `ellipsis` default | `true` | `false` |
| `verticalAlignCenter` default | `= ellipsis`<br>Slots centered on the whole text area (title + description) | `false`<br>Slots aligned to the first title line (centered on the first line even when true) |
| `textMaxLine` | Always applied | Applied only when `ellipsis = true` |
| Disabled appearance | Whole cell at 43% opacity | Disabled color only on title, description, Check, chevron<br>Slots are handled by the caller |

**What to check**: line count of long-text cells · icon alignment in cells with 2+ title lines or a description · disabled cell colors

#### TextArea

| Item | 3.7.0 | 4.0 core overload<br>`WantedTextArea(value, …)` | 4.0 deprecated overloads<br>(4 with title · description) |
|---|---|---|---|
| min / max lines | 1 / 3 | 2 / 6 | 1 / 3 |
| Default `resize` | — (`maxLines` is always the cap) | `Normal` (unbounded height; `maxLines` used only with `Limit`) | `Limit` |
| Character counter | Shown bottom-left automatically when slots are empty | Not shown automatically (moved to FormControl `accessory`) | Shown automatically when slots are empty |
| `description = ""` | No description line | Empty line | Ignored |

**What to check**: when moving from a deprecated overload to the core overload, set `resize` and the counter explicitly to match 3.7.0.

#### ActionArea

| Item | 3.7.0 | 4.0 |
|---|---|---|
| `divider` default | `false` | `true` (shown only when extra exists) |
| Strong sub slot padding | None | 8dp top and bottom (text buttons only; use `alternative` for full-width buttons) |
| Background | Even with `background = true`, only the gradient is drawn in the screen background color (`backgroundNormalNormal`) | Whole area filled with `surfaceElevatedPrimary` |

**What to check**: divider above the extra area · doubled sub spacing · bottom band in dark mode

#### FallbackView

| Item | 3.7.0 | 4.0 |
|---|---|---|
| Vertical padding | 8dp | `padding` default `Normal` = 160dp each (`Compact` 80dp) |
| Button | SMALL | MEDIUM |
| Title | heading2Bold | headline1Bold |
| Description | body1ReadingRegular | body2ReadingRegular |

**What to check**: empty states inside bottom sheets or lists getting clipped or taller · title and description sizes

---

## 2. Common design rules

### 2.1 Semantic Color Token

- Default colors of every component moved to the new Semantic Tokens (**usage / role / variant**).
  - `WantedColorScheme`: 59 → 76
  - `WantedColorOpacityScheme`: still 72, renamed only
- Opacity variant names follow their base token.
  - e.g. `primary_normal_opacityN` → `surface_brand_primary_opacityN`
- Atomic Color only gains opacity variants.
- **Components with no change other than tokens**: Radio, Switch, CheckMark, Slider, Tab, Progress Indicator, Progress Tracker, Snackbar, Alert, Section Header, Loading, Card Description, beta package
- Tickets: WRP-1054, WRP-1612

### 2.2 Form Control split

`WantedFormControl` now owns the label, required mark, description, and character count; TextField, TextArea, and Select draw **only the input itself**.

| Component | Status | Size |
|---|---|---|
| FormControl | Normal / Positive / Negative | Large (label1Bold) / Medium (label2Bold) |
| TextField | `error` parameter | Large 48dp·r14 / Medium 40dp·r12 |
| TextArea | Normal / Negative | Large 48dp / Medium 44dp |
| Select | Normal / Negative | Large 48dp / Medium 40dp |
| SearchField | — | Large 48dp / Medium 40dp |

### 2.3 Focus Ring · Border

- New Modifiers
  - `Modifier.framedStyle(focused = …)`
  - `Modifier.focusRing(visible, shape, color = lineBrandFocus, width = 4.dp)`
- Shared rules for TextField · TextArea · Select
  - 4dp Focus Ring on focus (red 12% for Negative)
  - Focus border 1dp, color `primaryNormal` → `lineBrandStrong`
  - Negative + Focus uses 52% border opacity

### 2.4 Active style (Chip · FilterButton)

| Item | 3.7.0 | 4.0 |
|---|---|---|
| Solid Active | Black inverse | brand 5% background + brand text |
| Outlined Active border | brand 43% | brand 28% |

---

## 3. Foundation

### 3.1 Dimension tokens (new)

Accessed via `DesignSystemTheme.primitive` / `.spacing` / `.radius` / `.dimension`. (WRP-3160: added Radius 28 · 32)

| Token | Usage | Values (dp) |
|---|---|---|
| `primitive` | Raw values (direct use not recommended) | 0, 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72, 80, 9999 |
| `spacing` | Gaps, padding, margins | 0, 2, 4, 6, 8, 10, 12, 14, 16, 20, 24, 32, 40, 48, 56, 64, 72, 80 |
| `radius` | Corners | 0, 4, 8, 10, 12, 14, 16, 20, 24, 28, 32, `radiusFull` |
| `dimension` | Height, width | 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64 |

### 3.2 Icons

| Type | Icon | Ticket |
|---|---|---|
| Added | `icon_normal_circle_{check, close, exclamation, info, plus, question}_opaque` | WRP-1252 |
| Added | `icon_normal_hash_tag` | WRP-2263 |
| Added | `icon_normal_image_fill`, `icon_normal_position_review` | WRP-1074 |
| Path updated | `icon_normal_company`, `_fill`, `_check`, `_check_fill`, `_plus`, `_plus_fill` | WRP-1074 |

---

## 4. Actions

### Button

- **New API**
  - `ButtonType.NEGATIVE`
  - `ButtonSize.XSMALL`
- **Spec changes**
  - Height and width are minimums, so the button grows with fontScale
  - Solid NEGATIVE: negative 12% background + `foregroundNegativeStrong`
  - Outlined NEGATIVE is not supported (drawn as PRIMARY with an error log)
- Tickets: WRP-863, WRP-2096

**Sizes (L / M / S / XS)**

| Item | 3.7.0 | 4.0 |
|---|---|---|
| Min height | 48 / 40 / 32 / — | 48 / 40 / 32 / 28 |
| Horizontal padding | 28 / 20 / 14 / — | 20 / 16 / 12 / 10 |
| Vertical padding | 4 | 13 / 10 / 8 / 6 |
| Radius | 12 / 10 / 8 / — | 14 / 12 / 10 / 8 |
| Icon | Drawn larger than spec | 20 / 18 / 16 / 14 |
| Loading | 18 / 16 / 14 / — | 16 / 14 / 12 / 12 |
| Typography | Varied by type and size | body2Bold / label1Bold / caption1Bold / caption1Bold |

### TextButton

- **New API**
  - `WantedTextButtonColor` (PRIMARY / ASSISTIVE)
  - `WantedTextButtonSize` (SMALL / MEDIUM)
  - `WantedTextButtonDefaults.getDefault()`
- **Spec changes**
  - Min height S 28 / M 32, vertical padding 4
  - Touch-area horizontal padding M 7 / S 6, radius 6
  - ASSISTIVE color `foregroundNeutralTertiary`
  - Dialog buttons and Tooltip action buttons now use TextButton (Dialog Negative is MEDIUM)
- Tickets: WRP-771, WRP-942, WRP-2096

### IconButton

- **New API**
  - `IconButtonInteractionEffect` (Highlight / Dim / None)
  - `IconButtonBadgePosition` (9 positions)
  - `WantedIconButtonNormalSize` (36 / 32 / 28 / 24 / Custom)
  - `WantedIconButtonBackgroundSize` (32 / Custom)
  - Shared: `pushBadge`, `badgePosition`, `interactionColor`, `disableInteraction`
  - Normal: `interactionOverflow` / Background: `useNormalInteraction`
- **Spec changes**
  - Icon size = box × 2/3 (Normal · Background), × 0.47 (Outlined · Solid)
  - Box limited to 24–64dp, touch area = box
  - Ripple 0.08 / Background normal 0.12 / Solid 0.20
  - Badge position: icon corner for Normal, box corner for the rest
- Tickets: WRP-889, WRP-927, WRP-963, WRP-964, WRP-965, WRP-2568, WRP-2814, WRP-3052, WRP-3163

### Chip

- **Spec changes**

  | Item | XS | S | M | L |
  |---|---|---|---|---|
  | Min height | 24 | 32 | 36 | 40 |
  | Radius | 8 | 10 | — | 12 |
  | Icon–text gap | 0 | — | 2 | 2 |
  | Typography (Medium) | caption2 | caption1 | label2 | label1 |

  - `—` means no separate value is specified
  - Increased padding
  - Brand-colored ripple when Active
- Tickets: WRP-1716, WRP-2220, WRP-2281, WRP-2841

### ActionArea

- **New API**
  - `captionIcon` (recommended value `CAPTION_ICON`)
- **Spec changes**
  - Alternative button: Outlined Assistive
  - Cancel-type main button: Solid Assistive
  - Caption label2Medium, extra horizontal padding 24
  - Strong-type sub 8 top and bottom; Neutral-type sub minimum width removed
- Tickets: WRP-2463, WRP-3028

---

## 5. Input

> For Status, Size, and Focus rules, see [Section 2 Common design rules](#2-common-design-rules).

### FormControl

- **New API**
  - `WantedFormControl(label, required, description, accessory, size, status, labelPlacement, enabled, input)`
  - `LabelPlacement` (Top / Leading)
- **Spec changes**
  - Label truncated to a single line
  - Required mark inline next to the label, bottom-aligned
- Tickets: WRP-1243, WRP-1374, WRP-1664, WRP-2221, WRP-2835

### TextField · AutoCompleteTextField

- **New API**
  - Core `WantedTextField(value: TextFieldValue, …)`
  - `complete` (check icon when unfocused)
- **Spec changes**
  - Single-line ellipsis when unfocused
  - Removed duplicate TalkBack announcement of the value
  - Removed trailing exclamation icon in Negative
- Tickets: WRP-1050, WRP-2830

### TextArea

- **New API**
  - Core `WantedTextArea(value, …)`
  - `Resize` (Normal / Limit / Fixed)
  - `button` (when set, a button replaces trailingContent)
- **Spec changes**
  - Character count caption1Bold
  - Reading-style typography for placeholder
  - 12dp padding on the bottom area, button right-aligned
- Tickets: WRP-1248, WRP-2222, WRP-2267

### Select

- **New API**
  - 4 core `WantedSelect` overloads
  - `leadingIcon`, `errorDataList` / `errorList`
- **Spec changes**
  - Neutral ripple color
  - Reworked Chip style (Negative: red 5% background, 22% border)
  - Keeps vertical padding on multi-line overflow
  - Radio bottom sheet: radio on every item; tapping the radio also selects
- Tickets: WRP-1723, WRP-2847

### Checkbox

- **New API**
  - `WantedCheckBox(onCheckedChange, modifier, size, style, checkState, tight, enabled, interactionSource)` is now public
  - In 3.7.0 it was internal, so the replacement suggested by the deprecated `WantedCheckBox(checked, …)` could not be called

### SearchField

- **New API**
  - `variant` (Solid / Outlined)
- **Spec changes**
  - Clear icon always shown when there is a value and the field is enabled
- Tickets: WRP-2007, WRP-2884

### SegmentedControl

- **New API**
  - `WantedSegmentedControlItem` (icon + text / Icon Only)
  - `iconOnly`, `LocalWantedSegmentedIconOnly`
  - `SegmentedSize` dimension properties, `ContainerPadding` (4dp)
- **Spec changes**
  - Vertical padding applied to items
- Tickets: WRP-1629, WRP-2837

### FilterButton

- **Spec changes**
  - Typography one step smaller (XSmall caption2Medium ~ Large label1Medium)
  - Removed SemiBold from activeLabel
  - Adjusted padding, spacing, and radius
- Tickets: WRP-1770, WRP-2223, WRP-2850

### Date / Time Picker

- **Spec changes**
  - Built on `WantedPopup`; fixed radius 28 removed
- Ticket: WRP-3124

---

## 6. Contents · Feedback · Navigation · Presentation

### Contents

| Component | New API | Spec changes | Tickets |
|---|---|---|---|
| ListCell<br>Accordion | • `Variant` — Inset (padding 0 / outset 12 / radius 16) · Full (20 / 0 / 0)<br>• `labelTrailingContent`, `extraContent`, `enabledInnerTouch`<br>• ListCell `cellDefault: WantedListCellDefault` (`backgroundColor`, `shape`, `border`, `contentPadding`)<br>• `WantedListCellDefaults.getDefault()` | • Clicks blocked on disabled cells<br>• selected: primary color + Body2 Bold, selection semantics<br>• No interaction when `VerticalPadding.None`<br>• Cells inside Select · AutoComplete fixed to Inset | WRP-2179<br>WRP-3027 |
| Card<br>ListCard | • `backgroundColor`, `shape`, `border`, `interactionShape`, `contentPadding` on `WantedCardDefault`<br>• `WantedCardDefaults.getDefault()` | • ListCard pressed area left 20 / right 12dp → 12dp rounded (configurable via `interactionShape`)<br>• WantedCard pressed area unchanged (`interactionShape` not applied) | — |
| Avatar | • `contentDescription`<br>• Default `cornerRadius` formula for `Custom` | • Radius +2 per size (8 ~ 16)<br>• Push Badge outline + inset<br>• Type-specific default placeholder when `placeHolder` is null | WRP-1927<br>WRP-2854 |
| AvatarGroup | • `WantedAvatarGroupSize` (XSmall / Small)<br>• `WantedAvatarGroupTrailingText`, `…TrailingTextButton`<br>• `contentDescription` | • Border 2 → 1.5dp<br>• Trailing height fixed at 24 / 32dp | WRP-1947<br>WRP-2883 |
| ContentBadge | • `ContentBadgeSize.Medium` | — | WRP-557 |
| PushBadge | • `maxCount`, `outlineBorder`, `outlineBorderColor`, `textStyle`, `inset` | • "99+" when exceeding MaxCount<br>• fontScale honored up to 1.3 | WRP-1862<br>WRP-2853 |
| Category | • `WantedCategoryDefault`, `getDefault()`, `getChipDefault()`<br>• `Size.rightIconSize` (20 / 22 / 24) | • Normal active: `foregroundNeutralStrong` background + inverse text<br>• Alternative active border brand 43% | WRP-3199 |

### Feedback

| Component | New API | Spec changes | Tickets |
|---|---|---|---|
| FallbackView | • `padding: WantedFallbackPadding` (Normal 160 / Compact 80dp) | • Button SMALL → MEDIUM<br>• Title heading2Bold → headline1Bold<br>• Description body1ReadingRegular → body2ReadingRegular | WRP-2036<br>WRP-2268<br>WRP-2885 |
| Toast | • `WantedToastVariant.Normal` (default) | • The global toast (`WantedGlobalToastManager`) skips display on Activities that cannot host Compose content (no `ViewTreeLifecycleOwner`) to prevent crashes | WRP-558 |

### Navigation

| Component | New API | Spec changes | Tickets |
|---|---|---|---|
| TopAppBar | — | • Icon buttons 24dp with a 40dp circular touch area | WRP-2568 |
| DialogTopAppBar | • `WantedDialogSearchTopAppBar`<br>• `navigationPadding` (`NAVIGATION_PADDING` 24 / `FULL_NAVIGATION_PADDING` 20)<br>• `Variant.Search`, `Floating(iconBackground)` | • Close button 24dp · container 36dp · Dim<br>• Min height 56dp, Leading–title gap 16dp (Search 12dp) | WRP-3283 |
| Pagination Dots | — | • All dots full-size when there are 5 pages or fewer | WRP-995 |

### Presentation

| Component | New API | Spec changes | Tickets |
|---|---|---|---|
| Popover<br>Tooltip | • `screenEdgePadding` — minimum margin kept from screen edges (default Popover 8dp / Tooltip 2dp, same as the fixed 3.7.0 values) | • Tooltip tail position clamped within the tooltip width<br>• If Tooltip horizontal margins exceed the screen, only the remaining space is used | — |
| Popup | • `WantedPopupDefaults.getDefault()` (radius 24, width 360, content 28dp horizontal) | • Close Button always shown<br>• Confirm button uses `WantedActionArea` (Strong) | WRP-3124 |
| BottomSheet | • `WantedBottomSheetDefaults.getDefault()` / `getFullDefault()` / `getWithoutContentPadding()` | • Radius 16 → 32<br>• Fixed-height types pin the Action Area to the bottom | WRP-3125 |

---

## 7. Tools and demo

| Item | Description | Tickets |
|---|---|---|
| designdemo | Demos for TextButton, FormControl, SegmentedControl, Select, FallbackView<br>ColorToken · Icon · Typography catalogs | — |
| Usage tracking | `./gradlew :app:trackComposableUsageWithException`<br>Per-component usage JSON (excluding Previews) | WRP-1442 |
| Sync workflow | Syncs designdemo → montage-android sample | WRP-654 |
| KDoc → MDX | Configuration cache compatibility, cleaned up `@param` / `@property` format | WRP-2638, WRP-2634, WRP-957 |
