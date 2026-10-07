# Migration Guide

[English](MIGRATION_3.7.0_4.0.0.md) | [한국어](MIGRATION_3.7.0_4.0.0.ko.md)

Changes required to move between major versions. Newest first.

**This document covers breaking changes only.** New components and parameters are listed by name under [Added APIs](#added-apis); see the [release notes](https://github.com/wanteddev/montage-android/releases) for details.

Each major section is written against the **last release tag of the previous major**. APIs that appeared and disappeared during development are not listed, since nobody upgrading from the previous release ever saw them.

---

## 4.0

**Baseline: v3.7.0 → 4.0.0**

Breaking changes fall into four groups. Working top to bottom clears compile errors fastest.

| Order | Section | Kind | What you do |
|---|---|---|---|
| 1 | [Renamed](#1-renamed) | Mechanical | mostly `sed` |
| 2 | [Removed, needs rework](#2-removed-needs-rework) | Restructure | Move to the replacement API |
| 3 | [No replacement](#3-no-replacement) | Judgement call | You have to choose |
| 4 | [Visual changes](#4-visual-changes) | Eyeball it | **No compile errors** |

**Do not skip section 4.** Those entries keep the same API and change only values, so the build passes while the screen changes. A clean build does not mean the migration is done.

Section 1 is not entirely `sed` either. Some color tokens change their value along with their name ([Tokens whose value also changes](#tokens-whose-value-also-changes)), and a word-boundary substitution is not Kotlin-syntax aware, so it also rewrites local variables with the same name and the same name inside comments and strings. Skim the diff once you are done substituting.

**Many APIs were kept as `@Deprecated(WARNING)` instead of being removed**, such as the label-embedding overloads of `WantedTextField`, `WantedTextArea`, and `WantedSelect`. Call sites that use them compile with only a warning, but they render with the 4.0 spec. Leaving the warnings in place does not exempt you from [4. Visual changes](#4-visual-changes).

---

## 1. Renamed

### 1.1 Semantic color tokens

**Both** the `DesignSystemTheme.colors` (`WantedColorScheme`) properties and the XML color resources (`R.color.*` / `@color/*`) are renamed. Only `staticWhite`, `staticBlack`, and `transparent` stay the same. Most entries change only the name, but **a few also change their color value.** Be sure to check [Tokens whose value also changes](#tokens-whose-value-also-changes).

#### Naming rule

```
usage + role + variant

usage      foreground  text and icons
           background  page background
           surface     background of elements on the page (cards, fields, buttons)
           line        borders and dividers
           effect      dim and translucent layers

role       Neutral  Brand  Positive  Cautionary  Negative  Disable  Inactive  Accent{Color}

variant    Primary → Secondary → Tertiary → Quaternary  (decreasing contrast)
           Strong / Heavy   darker
           Subtle           lighter
           Inverse          for use on an inverted background
           Focus            focus ring
           Opaque           opaque version (the suffix-less one is translucent)
```

Kotlin properties are camelCase, and XML resources use the same name in snake_case (`foregroundNeutralPrimary` ↔ `foreground_neutral_primary`). The exception: **`LightBlue` is written as one word, `lightblue`, in resource names** (`foregroundAccentLightBlue` ↔ `foreground_accent_lightblue`). This is the same rule as 3.x.

The `Solid` that sat in the middle of 3.x names moves to the end as `Opaque` in 4.0. Just watch for the position change, as in **`lineSolidNormal` → `lineNeutralPrimaryOpaque`**.

3.x names where the group name appears twice, such as `backgroundNormalNormal` and `lineNormalNormal`, also move as shown in the tables below.

#### Foreground - text and icons

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | Value |
|---|---|---|
| `labelNormal` / `label_normal` | `foregroundNeutralPrimary` / `foreground_neutral_primary` | Same |
| `labelStrong` / `label_strong` | `foregroundNeutralStrong` / `foreground_neutral_strong` | Same |
| `labelNeutral` / `label_neutral` | `foregroundNeutralSecondary` / `foreground_neutral_secondary` | Same |
| `labelAlternative` / `label_alternative` | `foregroundNeutralTertiary` / `foreground_neutral_tertiary` | Same |
| `labelAssistive` / `label_assistive` | `foregroundNeutralQuaternary` / `foreground_neutral_quaternary` | Same |
| `inverseLabel` / `inverse_label` | `foregroundNeutralInverse` / `foreground_neutral_inverse` | **Slight change in dark** |
| `labelDisable` / `label_disable` | `foregroundDisablePrimary` / `foreground_disable_primary` | Same |
| `interactionInactive` / `interaction_inactive` | `foregroundInactivePrimary` / `foreground_inactive_primary` | Same |
| `inversePrimary` / `inverse_primary` | `foregroundBrandInverse` / `foreground_brand_inverse` | Same |
| `statusPositive` / `status_positive` | `foregroundPositivePrimary` / `foreground_positive_primary` | Same |
| `statusCautionary` / `status_cautionary` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | Same |
| `statusNegative` / `status_negative` | `foregroundNegativePrimary` / `foreground_negative_primary` | Same |
| `accentForegroundBlue` / `accent_foreground_blue` | `foregroundBrandPrimary` / `foreground_brand_primary` | **Changed** |
| `accentForegroundGreen` / `accent_foreground_green` | `foregroundPositivePrimary` / `foreground_positive_primary` | **Changed in light** |
| `accentForegroundOrange` / `accent_foreground_orange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **Changed** |
| `accentForegroundRed` / `accent_foreground_red` | `foregroundNegativeStrong` / `foreground_negative_strong` | Same |
| `accentForegroundLime` / `accent_foreground_lime` | `foregroundAccentLime` / `foreground_accent_lime` | Same |
| `accentForegroundCyan` / `accent_foreground_cyan` | `foregroundAccentCyan` / `foreground_accent_cyan` | Same |
| `accentForegroundLightBlue` / `accent_foreground_lightblue` | `foregroundAccentLightBlue` / `foreground_accent_lightblue` | Same |
| `accentForegroundViolet` / `accent_foreground_violet` | `foregroundAccentViolet` / `foreground_accent_violet` | Same |
| `accentForegroundPurple` / `accent_foreground_purple` | `foregroundAccentPurple` / `foreground_accent_purple` | Same |
| `accentForegroundPink` / `accent_foreground_pink` | `foregroundAccentPink` / `foreground_accent_pink` | Same |
| `accentForegroundRedOrange` / `accent_foreground_redorange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **Changed** |

> Among `accentForeground{Color}`, Blue, Green, Orange, Red, and RedOrange **changed role from `Accent{Color}` to `Brand`, `Positive`, `Cautionary`, and `Negative`** (RedOrange goes to `Cautionary` along with Orange). Do not just swap the name - confirm the spot really carries that role.
>
> The dark values of the 3.x accent tokens already matched 4.0, so Lime, Cyan, LightBlue, Violet, Purple, Pink, and Red **keep their values**.

#### Background · Surface - page and element backgrounds

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | Value |
|---|---|---|
| `backgroundNormalNormal` / `background_normal_normal` | `backgroundNeutralPrimary` / `background_neutral_primary` | Same |
| `backgroundNormalAlternative` / `background_normal_alternative` | `backgroundNeutralSecondary` / `background_neutral_secondary` | Same |
| `backgroundElevatedNormal` / `background_elevated_normal` | `surfaceElevatedPrimary` / `surface_elevated_primary` | Same |
| `backgroundElevatedAlternative` / `background_elevated_alternative` | `surfaceElevatedSecondary` / `surface_elevated_secondary` | Same |
| `fillNormal` / `fill_normal` | `surfaceNeutralSecondary` / `surface_neutral_secondary` | Same |
| `fillAlternative` / `fill_alternative` | `surfaceNeutralTertiary` / `surface_neutral_tertiary` | Same |
| `fillStrong` / `fill_strong` | `surfaceNeutralStrong` / `surface_neutral_strong` | Same |
| `backgroundStatusPositive` / `background_status_positive` | `surfacePositivePrimary` / `surface_positive_primary` | Same |
| `backgroundStatusCautionary` / `background_status_cautionary` | `surfaceCautionaryPrimary` / `surface_cautionary_primary` | Same |
| `backgroundStatusNegative` / `background_status_negative` | `surfaceNegativePrimary` / `surface_negative_primary` | Same |
| `inverseBackground` / `inverse_background` | `surfaceNeutralInverse` / `surface_neutral_inverse` | Same |
| `primaryNormal` / `primary_normal` | `surfaceBrandPrimary` / `surface_brand_primary` (fill) · `foregroundBrandPrimary` / `foreground_brand_primary` (text and icons) | Same |
| `primaryStrong` / `primary_strong` | `surfaceBrandStrong` / `surface_brand_strong` | Same |
| `primaryHeavy` / `primary_heavy` | `surfaceBrandHeavy` / `surface_brand_heavy` | Same |
| `interactionDisable` / `interaction_disable` | `surfaceDisablePrimary` / `surface_disable_primary` | Same |
| `accentBackgroundLime` / `accent_background_lime` | `surfaceAccentLimeOpaque` / `surface_accent_lime_opaque` | Same |
| `accentBackgroundCyan` / `accent_background_cyan` | `surfaceAccentCyanOpaque` / `surface_accent_cyan_opaque` | Same |
| `accentBackgroundLightBlue` / `accent_background_lightblue` | `surfaceAccentLightBlueOpaque` / `surface_accent_lightblue_opaque` | Same |
| `accentBackgroundViolet` / `accent_background_violet` | `surfaceAccentVioletOpaque` / `surface_accent_violet_opaque` | Same |
| `accentBackgroundPurple` / `accent_background_purple` | `surfaceAccentPurpleOpaque` / `surface_accent_purple_opaque` | Same |
| `accentBackgroundPink` / `accent_background_pink` | `surfaceAccentPinkOpaque` / `surface_accent_pink_opaque` | Same |
| `accentBackgroundRedOrange` / `accent_background_redorange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **Changed** |

> **`primaryNormal` splits in two depending on use.** Where it painted a fill, such as a button background, use `surfaceBrandPrimary`; where it painted foreground, such as text, icons, cursors, or indicators, use `foregroundBrandPrimary`. The two tokens currently share the same light and dark values, so picking the wrong one looks identical today, but it will drift once the token values diverge. Send the bulk substitution to `surfaceBrandPrimary`, then pick out the foreground spots and fix them.
>
> Only two `background` tokens remain (Primary and Secondary) for the page background. The rest moved to `surface`, and the translucent `backgroundTransparent*` pair moved to `effect`.
>
> `accentBackgroundRedOrange` is a background token, but the Semantic Token guide maps it to the **foreground token `foregroundCautionaryPrimary`**. Spots that used it as a text or icon color can move as is; spots that painted a fill should pick the `surface` token that fits (`surfaceCautionaryPrimary` and so on).
>
> `accentBackground{Color}` maps to the **opaque** variant by default. If you were layering it over something translucent, use the suffix-less `surfaceAccent{Color}` (8% alpha).
>
> `fillNormal` (→ `surfaceNeutralSecondary`) and `lineNormalAlternative` (→ `lineNeutralTertiary`) have the same light and dark values. If 3.x code used `lineNormalAlternative` for a fill or `fillNormal` for a border, do not move it by name - pick **the one that matches the use** (fill → `surface`, border → `line`). The screen stays the same.

#### Line - borders and dividers

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | Value |
|---|---|---|
| `lineNormalNormal` / `line_normal_normal` | `lineNeutralPrimary` / `line_neutral_primary` | **Slight change in dark** |
| `lineNormalNeutral` / `line_normal_neutral` | `lineNeutralSecondary` / `line_neutral_secondary` | Same |
| `lineNormalAlternative` / `line_normal_alternative` | `lineNeutralTertiary` / `line_neutral_tertiary` | Same |
| `lineSolidNormal` / `line_solid_normal` | `lineNeutralPrimaryOpaque` / `line_neutral_primary_opaque` | Same |
| `lineSolidNeutral` / `line_solid_neutral` | `lineNeutralSecondaryOpaque` / `line_neutral_secondary_opaque` | Same |
| `lineSolidAlternative` / `line_solid_alternative` | `lineNeutralTertiaryOpaque` / `line_neutral_tertiary_opaque` | Same |
| `lineStatusPositiveNormal` / `line_status_positive_normal` | `linePositivePrimary` / `line_positive_primary` | Same |
| `lineStatusCautionaryNormal` / `line_status_cautionary_normal` | `lineCautionaryPrimary` / `line_cautionary_primary` | Same |

> The 4.0 `lineBrand*` and `lineNegative*` tokens have no 3.x counterpart and are [Added APIs](#added-apis).

#### Effect - dim and translucent layers

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | Value |
|---|---|---|
| `materialDimmer` / `material_dimmer` | `effectDimmerPrimary` / `effect_dimmer_primary` | **Changed in dark** |
| `backgroundTransparentNormal` / `background_transparent_normal` | `effectTransparentPrimary` / `effect_transparent_primary` | Same |
| `backgroundTransparentAlternative` / `background_transparent_alternative` | `effectTransparentSecondary` / `effect_transparent_secondary` | Same |

#### XML-only temporary tokens

Two resources that existed only in XML, with no Kotlin property, were removed. Move them to the token with the same value.

| 3.x | 4.0 | Value |
|---|---|---|
| `@color/primary_normal_temp` | `@color/surface_brand_primary` | Same |
| `@color/status_positive_temp` | `@color/foreground_positive_primary` | Same |

#### Tokens whose value also changes

Moving the name alone **changes the color.** Check these spots by eye after substituting.

| 3.x → 4.0 | Light | Dark |
|---|---|---|
| `accentForegroundBlue` → `foregroundBrandPrimary` | `blue_45` `#005EEB` → `blue_50` `#0066FF` | `blue_65` `#4F95FF` → `blue_60` `#3385FF` |
| `accentForegroundGreen` → `foregroundPositivePrimary` | `green_40` `#009632` → `green_50` `#00BF40` | Same (`green_60`) |
| `accentForegroundOrange` → `foregroundCautionaryPrimary` | `orange_39` `#D17600` → `orange_50` `#FF9200` | `orange_50` `#FF9200` → `orange_60` `#FFA938` |
| `materialDimmer` → `effectDimmerPrimary` | Same (`#85171719`, 52%) | `#74171719` (about 45%) → `#BD171719` (74%) **the dim gets noticeably darker** |
| `lineNormalNormal` → `lineNeutralPrimary` | Same | `cool_neutral_50` 32% → 35% |
| `inverseLabel` → `foregroundNeutralInverse` | Same | `neutral_10` `#171717` → `cool_neutral_10` `#171719` |
| `accentForegroundRedOrange` → `foregroundCautionaryPrimary` | `redorange_48` `#F55A00` → `orange_50` `#FF9200` | `redorange_60` `#FF7B2E` → `orange_60` `#FFA938` |
| `accentBackgroundRedOrange` → `foregroundCautionaryPrimary` | `redorange_50` `#FF5E00` → `orange_50` `#FF9200` | `redorange_60` `#FF7B2E` → `orange_60` `#FFA938` |

`accentForegroundBlue`, `Orange`, and the two RedOrange tokens change clearly **in both light and dark**, and `accentForegroundGreen` changes clearly in light. `materialDimmer` corrects a 3.x dark value whose alpha was `0x74` (about 45%) instead of the intended 74%, so **the dim in dark mode gets visibly darker.** `lineNormalNormal` and `inverseLabel` differ by 1-3%, which is effectively invisible.

#### Tokens not in the tables

Primitive color resources (`cool_neutral_*`, `blue_*`, `redorange_*` …) keep both their names and values. 4.0 only adds opacity-variant primitives; nothing is removed or changed.

Tokens new in 4.0 (`lineBrandFocus`, `lineNegativeFocus`, `surfaceBrandSubtle`, `surfaceNegativeStrong`, `surfaceAccent*`, and so on) have no 3.x counterpart and are not in these tables. See [Added APIs](#added-apis).

---

### 1.2 Opacity-derived tokens

`DesignSystemTheme.colorsOpacity` (`WantedColorOpacityScheme`) and the matching XML resources (`*_opacityNN`) follow the base token names. Only the prefix changes; the trailing `OpacityNN` stays.

| 3.x prefix (`colorsOpacity.` / `R.color.`) | 4.0 prefix | Available steps |
|---|---|---|
| `primaryNormalOpacity` / `primary_normal_opacity` | `surfaceBrandPrimaryOpacity` / `surface_brand_primary_opacity` | 5·8·12·22·28·35·52·61·74·88 |
| `labelNormalOpacity` / `label_normal_opacity` | `foregroundNeutralPrimaryOpacity` / `foreground_neutral_primary_opacity` | 5·8·12 |
| `labelStrongOpacity` / `label_strong_opacity` | `foregroundNeutralStrongOpacity` / `foreground_neutral_strong_opacity` | 5·8·12·35·52·74·88 |
| `labelAlternativeOpacity` / `label_alternative_opacity` | `foregroundNeutralTertiaryOpacity` / `foreground_neutral_tertiary_opacity` | 5·8·12·35·52·74·88 |
| `backgroundNormalNormalOpacity` / `background_normal_normal_opacity` | `backgroundNeutralPrimaryOpacity` / `background_neutral_primary_opacity` | 0·61 |
| `backgroundElevatedNormalOpacity` / `background_elevated_normal_opacity` | `surfaceElevatedPrimaryOpacity` / `surface_elevated_primary_opacity` | 0·12·88·97 |
| `lineNormalOpacity` / `line_normal_opacity` | `lineNeutralPrimaryOpaqueOpacity` / `line_neutral_primary_opaque_opacity` | 28·61 |
| `lineAlternativeOpacity` / `line_alternative_opacity` | `lineNeutralTertiaryOpaqueOpacity` / `line_neutral_tertiary_opaque_opacity` | 52 |
| `statusPositiveOpacity` / `status_positive_opacity` | `foregroundPositivePrimaryOpacity` / `foreground_positive_primary_opacity` | 5·8·12·16·43 |
| `statusNegativeOpacity` / `status_negative_opacity` | `foregroundNegativePrimaryOpacity` / `foreground_negative_primary_opacity` | 8 |
| `accentCyanOpacity` / `accent_cyan_opacity` | `surfaceAccentCyanOpaqueOpacity` / `surface_accent_cyan_opaque_opacity` | 8·35 |
| `accentLightBlueOpacity` / `accent_lightblue_opacity` | `surfaceAccentLightBlueOpaqueOpacity` / `surface_accent_lightblue_opaque_opacity` | 5·8·12 |
| `accentVioletOpacity` / `accent_violet_opacity` | `surfaceAccentVioletOpaqueOpacity` / `surface_accent_violet_opaque_opacity` | 5·8·12 |
| `accentPinkOpacity` / `accent_pink_opacity` | `surfaceAccentPinkOpaqueOpacity` / `surface_accent_pink_opaque_opacity` | 8 |
| `accentLimeOpacity` / `accent_lime_opacity` | `surfaceAccentLimeOpaqueOpacity` / `surface_accent_lime_opaque_opacity` | 8 |

> Despite their names, `lineNormalOpacity` and `lineAlternativeOpacity` apply alpha to the **opaque line (`lineSolid*`) color**, so they go to `…OpaqueOpacity`. The `accent*Opacity` tokens are likewise based on the opaque accent background, hence `surfaceAccent{Color}OpaqueOpacity`.

**The values change too.** Many 3.x derived tokens used an RGB different from their base token (old palette values); 4.0 corrects this so each applies alpha to the base semantic token's RGB. The alpha stays the same. Check [the opacity-derived tokens in 4.1](#opacity-derived-tokens).

---

### 1.3 XML styles

The default text color references in the library's `styles.xml` move to the new names. The values are the same. If your app inherits these styles and sets `android:textColor` to `@color/label_alternative` again, change that too.

| Style | 3.x | 4.0 |
|---|---|---|
| `WantedAppTheme.RadioButtonStyle` · `WantedTextViewFontStyle` · `WantedButtonFontStyle` · `WantedEditTextFontStyle` | `@color/label_alternative` | `@color/foreground_neutral_tertiary` |

Color resources that library components pass as default arguments also move to the new names (`WantedComposeShimmer(colorRes)`, `WantedComposeShimmerLinear(colorRes)`, and so on). Call sites that passed 3.x resource names (`R.color.label_normal` and the like) are cleaned up by the substitution above.

---

### 1.4 Bulk substitution

No 3.x token name survives in 4.0, and no new name collides with an old one. So **substituting on word boundaries** cannot chain substitutions or hit the wrong match.

**The default macOS `sed` (BSD) does not understand `\b`.** With `\b` it **changes nothing**, without any error. On BSD, use `[[:<:]]` · `[[:>:]]` for boundaries (`\b` on GNU sed).

```bash
# behavior check - BSD sed
echo 'colors.labelNormal' | sed -E 's/labelNormal\b/X/'                 # colors.labelNormal  (unchanged)
echo 'colors.labelNormal' | sed -E 's/[[:<:]]labelNormal[[:>:]]/X/'     # colors.X
```

Boundary characters include `_`, so names sharing a prefix stay safely apart.

- `label_normal` does not touch `label_normal_opacity5`.
- `labelNormal` does not touch `labelNormalOpacity12`.
- `fill_alternative` inside an app-defined resource name such as `job_detail_fill_alternative` is not matched either.

```bash
# check first
grep -rnwE "labelNormal|label_normal" --include="*.kt" --include="*.xml" .

# substitute - Kotlin (colors.* / colorsOpacity.*) + XML / R.color resource names
find . \( -name "*.kt" -o -name "*.xml" \) -not -path "*/build/*" -exec sed -i '' -E \
  -e 's/[[:<:]]labelNormal[[:>:]]/foregroundNeutralPrimary/g' \
  -e 's/[[:<:]]label_normal[[:>:]]/foreground_neutral_primary/g' \
  -e 's/[[:<:]]backgroundNormalNormal[[:>:]]/backgroundNeutralPrimary/g' \
  -e 's/[[:<:]]background_normal_normal[[:>:]]/background_neutral_primary/g' \
  {} +
```

The full rule set is in [Appendix: full color token substitution rules](#appendix-full-color-token-substitution-rules).

---

### 1.5 Component renames

#### ActionArea

Button roles are renamed from `positive`/`negative`/`neutral` to **`main`/`alternative`/`sub`**. This applies to all three overloads.

| 3.x | 4.0 |
|---|---|
| `positive` · `isEnablePositive` · `onClickPositive` | `main` · `isEnableMain` · `onClickMain` |
| `negative` · `isEnableNegative` · `onClickNegative` | `alternative` · `isEnableAlternative` · `onClickAlternative` |
| `neutral` · `isEnableNeutral` · `onClickNeutral` | `sub` · `isEnableSub` · `onClickSub` |
| `gradationColor` | `backgroundColor` (default also changes, see [4.1](#actionarea-1)) |
| `WantedActionAreaDefault.positiveButtonDefault` | `mainButtonDefault` |
| `WantedActionAreaDefault.negativeButtonDefault` | `alternativeButtonDefault` |
| `WantedActionAreaDefault.neutralButtonDefault` | `subButtonDefault` |

The argument names of `WantedActionAreaDefaults.getDefault(...)` change by the same rule.

```kotlin
// 3.x
WantedActionArea(
    type = ActionAreaType.Strong,
    positive = "OK",
    onClickPositive = { … },
    negative = "Cancel",
    onClickNegative = { … },
    gradationColor = DesignSystemTheme.colors.backgroundNormalNormal,
)

// 4.0
WantedActionArea(
    type = ActionAreaType.Strong,
    main = "OK",
    onClickMain = { … },
    alternative = "Cancel",
    onClickAlternative = { … },
    backgroundColor = DesignSystemTheme.colors.backgroundNeutralPrimary,
)
```

`main`/`alternative`/`sub` name **roles**, not positions. The 3.x `negative` meant "alternative action", not "negative", so moving it mechanically to `alternative` is correct.

In the `WantedActionArea(main: String, …)` overload, `modifier` moves from last to third, after `type` and `main`. Call sites that pass named arguments are unaffected.

Names like `onClickPositive` also exist on other components such as FallbackView and Popup. Rename them **only inside `WantedActionArea(` call sites**.

#### Chip

`leftIcon`/`rightIcon` become `leadingContent`/`trailingContent`. This applies to all three overloads, and the types (`Int?` or `@Composable (() -> Unit)?`) stay the same.

| 3.x | 4.0 |
|---|---|
| `WantedChip(leftIcon = …)` | `WantedChip(leadingContent = …)` |
| `WantedChip(rightIcon = …)` | `WantedChip(trailingContent = …)` |

#### TextButton

`WantedTextButton` takes **Text Button-specific types** instead of the `ButtonType`, `ButtonSize`, and `WantedButtonDefault` it shared with `WantedButton`. This applies only to the Compose function; the XML `WantedTextButton` View class still uses `ButtonType`/`ButtonSize`.

| Parameter | 3.x type | 4.0 type |
|---|---|---|
| `color` | `ButtonType` (`PRIMARY` / `ASSISTIVE`) | `WantedTextButtonColor` (`PRIMARY` / `ASSISTIVE`) |
| `size` | `ButtonSize` (`MEDIUM` / `SMALL` / `LARGE`) | `WantedTextButtonSize` (`MEDIUM` default / `SMALL` / `LARGE` is legacy) |
| `buttonDefault` | `WantedButtonDefault` | `WantedTextButtonDefault` |

```kotlin
// 3.x
WantedTextButton(text = "More", color = ButtonType.ASSISTIVE, size = ButtonSize.SMALL, onClick = { … })

// 4.0
WantedTextButton(text = "More", color = WantedTextButtonColor.ASSISTIVE, size = WantedTextButtonSize.SMALL, onClick = { … })
```

Where you overrode the style through `buttonDefault`, move to `WantedTextButtonDefaults.getDefault(color, size, enabled, contentColor = …, textStyle = …)`. In `WantedTextButtonDefault`, the icon color properties are `leftIconTintColor`/`rightIconTintColor` → `leadingIconTintColor`/`trailingIconTintColor` (the shared `WantedButtonDefault` keeps left/right). With `enabled = false`, `foregroundDisablePrimary` takes precedence over the `contentColor` and icon colors you pass.

The Figma Text Button spec has only two sizes, `SMALL` and `MEDIUM`. `LARGE` is kept only to preserve calls that go through `ButtonVariant.TEXT`; do not use it in new code.

`WantedButton(variant = ButtonVariant.TEXT)` is `@Deprecated` (to be removed in 5.0). It compiles and forwards to `WantedTextButton` internally, but call `WantedTextButton` directly to clear the warning. Passing `ButtonType.NEGATIVE` with `TEXT` renders in the `PRIMARY` color.

#### TextField · AutoCompleteTextField

`right*` becomes `trailing*`, and the button style parameter is gone. This is the same for both the string and `TextFieldValue` overloads, and for both `WantedAutoCompleteTextField` overloads.

| 3.x | 4.0 |
|---|---|
| `rightButton` | `trailingButton` |
| `rightButtonEnabled` | `trailingButtonEnabled` |
| `onClickRightButton` | `onClickTrailingButton` |
| `rightButtonVariant: WantedTextFieldDefaults.RightVariant` | **Removed** ([3.1](#31-textfield-trailing-button-variant)) |

```kotlin
// 3.x
WantedTextField(
    text = code,
    rightButton = "Verify",
    rightButtonVariant = WantedTextFieldDefaults.RightVariant.Assistive,
    rightButtonEnabled = isValid,
    onClickRightButton = { verify() },
    onValueChange = { code = it }
)

// 4.0
WantedTextField(
    text = code,
    trailingButton = "Verify",
    trailingButtonEnabled = isValid,
    onClickTrailingButton = { verify() },
    onValueChange = { code = it }
)
```

#### TextArea

| 3.x | 4.0 | Applies to |
|---|---|---|
| `rightButton` | `button` | string and `TextFieldValue` overloads |
| `onClickRightButton` | `onClickButton` | 〃 |
| `negative: Boolean` | `status: WantedTextAreaDefaults.Status` (`true` → `.Negative`, `false` → `.Normal`) | all overloads |
| `leftContent` / `rightContent` | `leadingContent` / `trailingContent` | `TextFieldValue` overload |

`negative` was **dropped from the deprecated overloads too.** These call sites fail to compile.

```kotlin
// 3.x
WantedTextArea(text = memo, negative = isError, rightButton = "Save", onClickRightButton = save)

// 4.0
WantedTextArea(
    text = memo,
    status = if (isError) WantedTextAreaDefaults.Status.Negative else WantedTextAreaDefaults.Status.Normal,
    button = "Save",
    onClickButton = save
)
```

The `text:` overload that took both `trailingContent` and `rightButton` is split into overloads that take one or the other. For the change in button appearance, see [2.2](#22-textarea-trailing-button).

#### Select

| 3.x | 4.0 |
|---|---|
| `WantedSelectWithString(selectedValueList = …)` | `WantedSelect(valueList = …)` |
| `negative: Boolean` | `status: WantedSelectDefaults.Status` |
| `negativeDataList` | `errorDataList` |
| `negativeList` | `errorList` |

`WantedSelectWithString` and the label-embedding overloads (`title`, `isRequiredBadge`, `description`) remain as deprecated and still accept the 3.x names (`negative`, `negativeDataList`). This table applies **when you move to the new body overloads**. In the new body overloads, `placeHolder`, `enabled`, and `selectDataList` (or `selectValueList`) are required arguments with no default.

#### FilterButton

| 3.x | 4.0 |
|---|---|
| `isExpend` | `isExpanded` |

Both public overloads change. It is a typo fix, so behavior is the same.

#### SegmentedControl

The Solid and Outlined branches are merged into one, and `Solid` drops out of the name.

| 3.x | 4.0 |
|---|---|
| `WantedSegmentedControlSolid(…)` | `WantedSegmentedControl(…)` |
| `WantedSegmentedControlSolidItem(title: String, isSelected, modifier, icon)` | `WantedSegmentedControlItem(isSelected, modifier, title: String? = null, icon)` |
| `WantedSegmentedControlOutlined` · `WantedSegmentedControlOutlinedItem` | **Removed** ([3.2](#32-segmentedcontrol-outlined)) |

In `WantedSegmentedControlItem` the first argument is now `isSelected`, and `title` is nullable. **Calls that pass positional arguments, like `("Tab", true)`, fail to compile**, so switch to named arguments.

```kotlin
// 3.x
WantedSegmentedControlSolid(itemCount = 2, selectedIndex = index, item = { i ->
    WantedSegmentedControlSolidItem(title = tabs[i], isSelected = i == index)
})

// 4.0
WantedSegmentedControl(itemCount = 2, selectedIndex = index, item = { i ->
    WantedSegmentedControlItem(isSelected = i == index, title = tabs[i])
})
```

#### SearchField · SearchTopAppBar

`WantedSearchFieldDefaults.Size` changes from a data class carrying its height to name-only `data object`s, and **the names shift by one step.** `WantedSearchField` and `WantedSearchTopAppBar` both use it.

| 3.x | Height | 4.0 | Height |
|---|---|---|---|
| `Size.Medium()` | 48 | `Size.Large` | 48 |
| `Size.Small()` | 40 | `Size.Medium` | 40 |
| `Size.Custom(padding, minHeight)` | as given | **Removed** ([3.3](#33-searchfield-sizecustom--focused)) | - |

**Dropping only the parentheses, `Size.Medium()` → `Size.Medium`, compiles but shrinks the height from 48 to 40.** The 3.x `Medium()` is the 4.0 `Large`.

| Other parameters | 3.x | 4.0 |
|---|---|---|
| `focused: State<Boolean>` | present | **Removed** ([3.3](#33-searchfield-sizecustom--focused)) |
| `textStyle` | `TextStyle` (default `body1Regular`) | `TextStyle?` (default `null` → per-size style) |
| `cursorBrush` | `Brush` | `Brush?` (default `null` → primary color) |

#### ListCell · Accordion

| 3.x | 4.0 | Applies to |
|---|---|---|
| `caption` / `annotatedCaption` / `captionStyle` | `description` / `annotatedDescription` / `descriptionStyle` | ListCell |
| `fillWidth = false` | `variant = WantedListCellDefaults.Variant.Inset` (default) | ListCell · Accordion |
| `fillWidth = true` | `variant = WantedListCellDefaults.Variant.Full` | ListCell · Accordion |
| `interactionPadding = InteractionPadding.Default(…)` | Removed - folded into `variant` | ListCell |

`Inset` extends only the touch area by 12 on each side, and `Full` gives the cell its own 20 horizontal padding. These are the same padding values 3.x `fillWidth` used, so padding does not change, but **the pressed-area radius does** (see [4.1](#listcell)). For `InteractionPadding.Custom(n)`, see [3.7](#37-listcell-interactionpaddingcustom).

```kotlin
// 3.x
WantedListCell(text = "Text", caption = "Caption", fillWidth = true, onClick = {})

// 4.0
WantedListCell(text = "Text", description = "Caption", variant = WantedListCellDefaults.Variant.Full, onClick = {})
```

The slot restructuring is in [2.4 ListCell slots](#24-listcell-slots).

#### Avatar

| 3.x | 4.0 |
|---|---|
| `WantedAvatarType.Academic` | `WantedAvatarType.Academy` |
| `R.drawable.icon_avatar_placeholder_academic` | `R.drawable.icon_avatar_placeholder_academy` |

`cornerRadius` in `WantedAvatarSize.Custom(size, cornerRadius)` now has a default (`ceil(size × 0.25 / 2) × 2 + 2`). Values you were passing keep working.

The `size` and `type` changes on `WantedAvatarGroup` are in [3.10](#310-avatargroup-type--size).

#### PushBadge

| 3.x | 4.0 |
|---|---|
| `count` | `text` |
| `PushBadgeVariant.Number` + `count = "12"` | `PushBadgeVariant.MaxCount` + `text = "12"` (`99+` above 99) |
| `PushBadgeVariant.New` (fixed "N") | `PushBadgeVariant.Text` + `text = "N"` |
| `WantedPushBadgeBorder(…)` | `WantedPushBadge(…, outlineBorder = true)` |
| `WantedPushBadgeBorder(borderColor = c)` | `WantedPushBadge(outlineBorder = true, outlineBorderColor = c)` |

```kotlin
// 3.x
WantedPushBadge(variant = PushBadgeVariant.Number, count = "5")
WantedPushBadge(variant = PushBadgeVariant.New)
WantedPushBadgeBorder(variant = PushBadgeVariant.Dot, borderColor = DesignSystemTheme.colors.staticWhite)

// 4.0
WantedPushBadge(variant = PushBadgeVariant.MaxCount, text = "5")
WantedPushBadge(variant = PushBadgeVariant.Text, text = "N")
WantedPushBadge(variant = PushBadgeVariant.Dot, outlineBorder = true, outlineBorderColor = DesignSystemTheme.colors.staticWhite)
```

**When moving `Number` to `MaxCount`, a non-numeric `text` shows `0`.** `MaxCount` parses it as `text.toIntOrNull() ?: 0`. Where you passed non-numeric strings (`"9+"`, `"N"`, and so on), move to `Text`.

The default outline color changes from `staticWhite` to `backgroundNeutralPrimary`. If you intended a white outline in dark mode, pass `outlineBorderColor` explicitly. For the thickness, see [3.11](#311-pushbadge-borderwidth).

#### FallbackView

| 3.x | 4.0 |
|---|---|
| `positive` / `onClickPositive` | `main` / `onClickMain` |
| `negative` / `onClickNegative` | `alternative` / `onClickAlternative` |
| `positiveColor` / `negativeColor` | **Removed** ([3.8](#38-fallbackview-button-color)) |

The padding restructuring is in [2.5](#25-fallbackview-padding), and the image slot in [3.9](#39-fallbackview-image-slot).

#### Popup

`WantedModal` becomes `WantedPopup`. All three overloads (button, `bottomBar` slot, and `lazyContent`) have a counterpart.

| 3.x | 4.0 |
|---|---|
| `WantedModal(…)` | `WantedPopup(…)` |
| `type: ModalType` | `resize: WantedPopupContract.Resize` |
| `shape: RoundedCornerShape` | `popupDefault = WantedPopupDefaults.getDefault(shape = …)` |
| `size: ModalSize` | `popupDefault = WantedPopupDefaults.getDefault(…)` ([2.7](#27-popup--bottomsheet-padding)) |

| 3.x `ModalType` | 4.0 `Resize` |
|---|---|
| `Flexible` (default) · `FixedWrapContent` | `Hug` (default) |
| `Fixed(height)` | `Fixed(height)` |
| `FixedFullScreen` · `FixedRatio(ratio)` | **No replacement** ([3.12](#312-popup-fixedfullscreen--fixedratio)) |

`WantedModalContract.ModalType` itself remains. **BottomSheet still takes `ModalType`; only Popup moved to `Resize`.** The structural change to the button overload is in [2.6](#26-popup-button-overload).

#### BottomSheet

| 3.x | 4.0 |
|---|---|
| `WantedModalBottomSheet(modalSize = ModalSize.Medium)` | `WantedModalBottomSheet(sheetDefault = WantedBottomSheetDefaults.getDefault())` (can be omitted) |

The `sheetDefault` default is picked automatically from `type` (`getFullDefault()` for `FixedFullScreen`). Replacing `modalSize` is **not a name-only substitution.** Content padding appears, so read [2.7](#27-popup--bottomsheet-padding) and [4.1](#popup--bottomsheet) together.

The legacy `WantedBottomSheetDialog` and `WantedBottomSheetLayout` still take `modalSize: ModalSize`.

#### DialogTopAppBar

`WantedDialogTopAppBarContract.Variant` changes from `enum class` to `sealed class`.

| 3.x | 4.0 |
|---|---|
| `Variant.Normal` · `Variant.Emphasized` | unchanged |
| `Variant.Floating` | `Variant.Floating()` (`iconBackground: Boolean = false`) |
| `Variant.Display` | **No replacement** ([3.13](#313-dialogtopappbar-variantdisplay)) |
| `Variant.values()` / `entries` / `valueOf` | `Variant.presets` |

`Floating` is now a `data class`, so **using it without parentheses fails to compile**. In a `when`, branch with `is Variant.Floating`.

#### TopAppBar icon button

| 3.x | 4.0 |
|---|---|
| `WantedTopAppBarIconButton(variant = …)` | Removed (a value 3.x never used for rendering either) |
| `LocalWantedTopBarIconVariant` · `WantedTopBarIconVariantCompositionLocal` | Removed |

Delete the `variant =` arguments and any `LocalWantedTopBarIconVariant provides …` wrappers. For press feedback, see [4.1](#topappbar--dialogtopappbar).

#### Category

| 3.x | 4.0 |
|---|---|
| `isAlternative = false` | `variant = WantedCategoryDefaults.Variant.Normal()` (default) |
| `isAlternative = true` | `variant = WantedCategoryDefaults.Variant.Alternative` |

#### Names kept as deprecated

These only warn; they do not break compilation. The rendering is the same.

| 3.x | 4.0 |
|---|---|
| `ContentBadgeSize.Large` | `ContentBadgeSize.Medium` |
| `WantedToastVariant.Message` | `WantedToastVariant.Normal` |
| `ButtonVariant.TEXT` | `WantedTextButton` |

---

## 2. Removed, needs rework

### 2.1 Input labels, messages, and character counts

In 3.x, `WantedTextField`, `WantedTextArea`, and `WantedSelect` drew their own title (`title`), required mark (`requiredBadge`), and bottom message (`description`). In 4.0 the three components draw **only the input box**, and the label, message, and character count are laid out by a new wrapper, **`WantedFormControl`**.

**Passing parameters to an input component does not wrap it automatically.** Call sites wrap it themselves in `WantedFormControl { … }`. The label-embedding overloads remain as deprecated, so for now they only warn.

| 3.x (component parameter) | 4.0 `WantedFormControl` |
|---|---|
| `title` | `label` (not drawn when empty) |
| `requiredBadge` / `isRequiredBadge` | `required` |
| `description` | `description` |
| `status` / `negative` (message color) | `status: WantedFormControlDefaults.Status` (`Normal`/`Positive`/`Negative`) |
| TextArea built-in character count | `accessory = { WantedTextAreaCharacterCount(…) }` |
| - | `size` (`Large` / `Medium`), `labelPlacement` (`Top` / `Leading`), `enabled` |

**The status has to be passed twice.** The `status` on `WantedFormControl` only changes the message color; the red border on the input box comes from the value you give the inner component (`error` / `status`). Compute both from the same condition.

#### TextField

```kotlin
// 3.x
WantedTextField(
    text = email,
    title = "Email",
    requiredBadge = true,
    placeholder = "Enter your email.",
    description = if (isInvalid) errorMessage else null,
    status = if (isInvalid) WantedTextFieldDefaults.Status.Negative else WantedTextFieldDefaults.Status.Normal,
    onValueChange = { email = it }
)

// 4.0
WantedFormControl(
    label = "Email",
    required = true,
    description = if (isInvalid) errorMessage else null,
    status = if (isInvalid) WantedFormControlDefaults.Status.Negative else WantedFormControlDefaults.Status.Normal,
) {
    WantedTextField(
        value = emailValue,                    // TextFieldValue
        placeholder = "Enter your email.",
        error = isInvalid,
        enabled = true,
        trailingButtonEnabled = true,
        complete = false,
        maxLines = 1,
        minLines = 1,
        maxWordCount = 2000,
        enabledOverflowText = false,
        interactionSource = remember { MutableInteractionSource() },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        onValueChange = { emailValue = it }
    )
}
```

The 4.0 body overload **accepts only `TextFieldValue`**, and `error`, `enabled`, `trailingButtonEnabled`, `complete`, `maxLines`, `minLines`, `maxWordCount`, `enabledOverflowText`, `interactionSource`, `keyboardOptions`, and `keyboardActions` are **required arguments with no default**. The 3.x `status` splits into two Booleans, `error` (Negative) and `complete` (Positive).

There is no string (`text: String`) body overload. Screens holding string state should switch to `TextFieldValue`.

The body overload's `background` default is `backgroundNeutralPrimary`, which **differs** from the deprecated overloads' default (`effectTransparentSecondary`). If the background changes when you migrate, pass `background` explicitly.

#### TextArea with a character counter

```kotlin
// 3.x - the character counter was attached automatically at the bottom left inside the input
WantedTextArea(
    text = feedback,
    title = "Feedback",
    placeholder = "Tell us what you liked or what could be better.",
    maxWordCount = 1000,
    onValueChange = { feedback = it }
)

// 4.0
WantedFormControl(
    label = "Feedback",
    accessory = {
        WantedTextAreaCharacterCount(current = feedbackValue.text.length, maxWordCount = 1000)
    }
) {
    WantedTextArea(
        value = feedbackValue,
        placeholder = "Tell us what you liked or what could be better.",
        maxWordCount = 1000,
        onValueChange = { feedbackValue = it }
    )
}
```

- **The 4.0 body overload does not draw a counter.** The 3.x automatic counter survives only in the deprecated overloads (when the bottom `leadingContent` and `trailingContent` are both empty). After moving to the body overload, put it in `accessory` yourself, and **the position moves from the bottom left inside the input to the bottom right outside it**.
- Where emoji counted as one character (`isGraphemeClusterCount = true`), the call site has to compute `current` in graphemes. The body overload's `isGraphemeClusterCount` only affects the input limit.

The body overload's defaults also differ from 3.x. Where you relied on a line limit, set it explicitly.

| Item | 3.x / deprecated overloads | 4.0 body overload |
|---|---|---|
| `minLines` | 1 | **2** |
| `maxLines` | 3 | 6 |
| `resize` | `maxLines` is always the cap (`Limit`) | **`Normal` - grows without a cap** (`maxLines` is used only with `Limit`) |
| `description = ""` | no description line | an empty line |

#### Select

```kotlin
// 3.x
WantedSelect(
    selectData = selected,
    title = "Job category",
    isRequiredBadge = true,
    description = if (isError) "Select a job category." else null,
    negative = isError,
    placeHolder = "Select",
    selectDataList = jobs,
    onSelectData = { selected = it }
)

// 4.0
WantedFormControl(
    label = "Job category",
    required = true,
    description = if (isError) "Select a job category." else null,
    status = if (isError) WantedFormControlDefaults.Status.Negative else WantedFormControlDefaults.Status.Normal,
) {
    WantedSelect(
        selectData = selected,
        placeHolder = "Select",
        enabled = true,
        selectDataList = jobs,
        status = if (isError) WantedSelectDefaults.Status.Negative else WantedSelectDefaults.Status.Normal,
        onSelectData = { selected = it }
    )
}
```

#### Placing the label on the left

With `labelPlacement = Leading`, the label sits to the left of the input (gap 16). The label width is **at most 50% of the parent width** and truncates beyond that. The label is vertically centered within a `min(input height, 48)` area, so even for tall inputs like TextArea it stays near the first line.

There is no API that aligns the label column width across fields. When stacking several `Leading` labels, the call site has to decide the width.

---

### 2.2 TextArea trailing button

In 3.x, passing `rightButton` drew a **text button** at the bottom right. The 4.0 `button` is an **Outlined · Assistive button** (Large → `ButtonSize.SMALL`, Medium → `ButtonSize.XSMALL`). Renaming alone changes the button's appearance.

If you pass both `button` and `trailingContent`, `button` wins and `trailingContent` is ignored.

---

### 2.3 IconButton sizing

3.x set the IconButton size **through `modifier`**. 4.0 takes a size type, `size:`, and derives the icon size and corner radius from the box size.

| Component | 3.x sizing | 4.0 sizing |
|---|---|---|
| `WantedIconButtonNormal` | `modifier = Modifier.size(icon)` | `size: WantedIconButtonNormalSize` (default `Xlarge`) |
| `WantedIconButtonBackground` | `modifier = Modifier.size(icon)` | `size: WantedIconButtonBackgroundSize` (default `Default`) |
| `WantedIconButtonOutlined` · `Solid` | `size: WantedIconButtonSize` or the `padding:` overload | `size: WantedIconButtonSize` |

#### WantedIconButtonNormal

| 4.0 | Box (touch) | Icon | Corner |
|---|---|---|---|
| `.Xlarge` (default) | 36 | 24 | 10 |
| `.Large` | 32 | 20 | 10 |
| `.Medium` | 28 | 18 | 8 |
| `.Small` | 24 | 16 | 8 |
| `.Custom(n.dp)` | `clamp(24, n, 64)` | `n × 2/3` | `n × 0.3` |

In 3.x the size passed to `modifier` was the **icon size**, so choose the preset by icon size.

| 3.x | 4.0 |
|---|---|
| `Modifier.size(24.dp)` | `size = WantedIconButtonNormalSize.Xlarge` |
| `Modifier.size(20.dp)` | `size = WantedIconButtonNormalSize.Large` |
| `Modifier.size(18.dp)` | `size = WantedIconButtonNormalSize.Medium` |
| `Modifier.size(16.dp)` | `size = WantedIconButtonNormalSize.Small` |

```kotlin
// 3.x
WantedIconButtonNormal(
    modifier = Modifier.size(24.dp),
    icon = R.drawable.icon_normal_share,
    onClick = onClickShare,
)

// 4.0
WantedIconButtonNormal(
    icon = R.drawable.icon_normal_share,
    size = WantedIconButtonNormalSize.Xlarge,
    onClick = onClickShare,
)
```

**Be sure to remove `size(…)` from `modifier`.** If you leave it, it still compiles, but the 4.0 box is forced to that size (`Modifier.size(24.dp)` + `Xlarge` → box 24), making the touch area smaller than in 3.x.

The glyph size is the same, but **the space it occupies grows** (24 → 36). In 3.x only the icon size took up layout, and the touch area (8 on each side) spilled outside the layout. **To keep 3.x spacing,** turn on `interactionOverflow = true`. The occupied space shrinks to the icon size while the touch area stays put and spills over on all four sides.

```kotlin
// 4.0 - occupies 24 like 3.x, touch area 36
WantedIconButtonNormal(
    icon = R.drawable.icon_normal_share,
    size = WantedIconButtonNormalSize.Xlarge,
    interactionOverflow = true,
    onClick = onClickShare,
)
```

With `interactionOverflow = true`, the meaning of `size` changes.

- **When on, the number is the icon size.** Presets use the icon sizes in the table above (24, 20, 18, 16), and `Custom(n)` uses `n` as the icon size. The touch area is `max(24, ceil(icon × 1.5 ÷ 4) × 4)`, and the overflow is `(touch area - icon) ÷ 2`.
- **When off, the number is the box size.** `Custom(22.dp)` clamps the box to 24, and the icon becomes 16.

| `interactionOverflow = true` icon | 12 | 16 | 18 | 20 | 22 | 24 | 28 | 32 |
|---|---|---|---|---|---|---|---|---|
| Touch area | 24 | 24 | 28 | 32 | 36 | 36 | 44 | 48 |

#### WantedIconButtonBackground

`WantedIconButtonBackgroundSize.Default` (box 32 / icon 20) is the only preset; anything else is `.Custom(n.dp)` (icon `n × 2/3`). If 3.x used `Modifier.size(20.dp)`, `Default` is the same size. Here too, leaving `size(…)` in `modifier` shrinks the box to that size, so remove it.

#### WantedIconButtonOutlined · WantedIconButtonSolid

`WantedIconButtonSize` changes from `enum class` to `sealed class`. The names `Medium` (40) and `Small` (32) stay, so call sites do not change, but enum-only APIs and properties are gone.

| 3.x | 4.0 |
|---|---|
| `WantedIconButtonSize.Medium.size` | `WantedIconButtonSize.Medium.boxSize` |
| `WantedIconButtonSize.Medium.padding` | Removed - the icon is `box × 0.47` automatically |
| `WantedIconButtonSize.entries` / `values()` | `WantedIconButtonSize.presets` |
| `WantedIconButtonOutlined(icon, modifier, padding = 10.dp, …)` | `WantedIconButtonOutlined(icon, size = WantedIconButtonSize.Medium, …)` |
| `WantedIconButtonSolid(icon, modifier, padding = 10.dp, …)` | `WantedIconButtonSolid(icon, size = WantedIconButtonSize.Medium, …)` |

The 3.x `padding` overload took the box size through `modifier`. If the box was not 40 or 32, move to `WantedIconButtonSize.Custom(box.dp)`, and confirm that the icon size is now derived from the box ([3.5](#35-iconbutton-box-to-icon-ratio)).

---

### 2.4 ListCell slots

The slot type changes from `@Composable () -> Unit` to `@Composable RowScope.() -> Unit`, and two slots are added.

| 3.x | 4.0 |
|---|---|
| `leadingContent: (@Composable () -> Unit)?` | `leadingContent: (@Composable RowScope.() -> Unit)?` |
| `trailingContent: (@Composable () -> Unit)?` | `trailingContent: (@Composable RowScope.() -> Unit)?` |
| - | `labelTrailingContent` (badge next to the title, height 22, gap 4) |
| - | `extraContent` (free slot below the description) |

Call sites that write the lambda inline (`leadingContent = { Icon(...) }`) still compile. **Call sites that pass a variable of type `@Composable () -> Unit` fail to compile.** Wrap it, as in `leadingContent = { slot() }`.

Putting several items in the leading or trailing slot lays them out horizontally with a gap of 8. Where 3.x code wrapped them in a `Row` to set the gap, you can drop that `Row`.

With `selected = true` and an empty `trailingContent`, **a check icon appears automatically on the right.** Where 3.x code added its own check icon to show selection, make sure they do not double up (filling `trailingContent` suppresses the automatic check).

If a control inside a slot (checkbox, switch, and so on) needs to receive touches directly, turn on `enabledInnerTouch = true`. With the default `false`, the cell's click area covers the slot, so tapping anywhere only calls `onClick`.

---

### 2.5 FallbackView padding

A `padding: WantedFallbackPadding` parameter is added, and **the vertical padding is now built into the component.**

```kotlin
enum class WantedFallbackPadding(internal val verticalPadding: Dp) {
    Normal(verticalPadding = 160.dp),   // 160 above and below (default)
    Compact(verticalPadding = 80.dp)    // 80 above and below
}
```

3.x had only 12 above and below the text and button group. The 4.0 default, `Normal`, is 160 on each side, so **if you change nothing, the empty state grows about 300dp taller.**

```kotlin
// 3.x - padding came from outside
WantedFallbackView(
    modifier = Modifier.padding(vertical = 80.dp),
    heading = "No data.",
    positive = "Add",
    onClickPositive = { add() }
)

// 4.0 - leave it to the component
WantedFallbackView(
    padding = WantedFallbackPadding.Compact,
    heading = "No data.",
    main = "Add",
    onClickMain = { add() }
)
```

**Remove the outer vertical padding and any fixed height.** Leaving them in place doubles the padding, and a fixed `height()` will overflow, since the content no longer fits inside the minimum 320 (or 160). Placing it in a narrow spot, such as the middle of a list or a bottom sheet, is especially risky.

| 3.x call site | 4.0 |
|---|---|
| outer `padding(vertical = 160.dp)` | remove (default `Normal`) |
| outer `padding(vertical = 80.dp)` | remove + `padding = WantedFallbackPadding.Compact` |
| placed in a narrow area with no outer padding | even `Compact` adds 80 each, so check the screen |

There is no API for vertical padding other than 160 and 80. Horizontal padding is still up to the calling screen.

---

### 2.6 Popup button overload

The 3.x button `WantedModal` took a `topBar` slot plus two buttons, `positive`/`negative`. The 4.0 button `WantedPopup` takes only **`title: String?` plus one Main Action**. `negative`, `onClickNegative`, and `topBar` were dropped from this overload.

| 3.x (button overload) | 4.0 |
|---|---|
| `topBar = { WantedDialogTopAppBar(title = "Title") }` | `title = "Title"` |
| `positive` · `onClickPositive` | unchanged (one Main Action) |
| `negative` · `onClickNegative` | Removed - `bottomBar` overload + `WantedActionArea` |

```kotlin
// 3.x
WantedModal(
    topBar = { WantedDialogTopAppBar(title = "Title") },
    positive = "OK",
    negative = "Cancel",
    onClickPositive = { onConfirm() },
    onClickNegative = { onCancel() },
    onDismissRequest = { onCancel() }
) {
    Text("Content")
}

// 4.0 - with two buttons, put an ActionArea in the bottomBar overload yourself
WantedPopup(
    onDismissRequest = { onCancel() },
    topBar = {
        WantedDialogCloseTopAppBar(
            variant = WantedDialogTopAppBarContract.Variant.Emphasized,
            title = "Title",
            onClickClose = { onCancel() }
        )
    },
    bottomBar = {
        // Popup supplies the padding, so turn off the ActionArea's safeArea and divider
        WantedActionArea(
            type = ActionAreaType.Neutral,
            main = "OK",
            onClickMain = { onConfirm() },
            sub = "Cancel",
            onClickSub = { onCancel() },
            safeArea = false,
            divider = false
        )
    }
) {
    Text("Content")
}
```

The 3.x buttons were vertically stacked `WantedButton`s (confirm Solid Primary, cancel Outlined Primary). The 4.0 button overload uses `WantedActionArea(type = Strong)`. **The buttons take on the ActionArea spec.**

**The button overload now always draws a close button.** Even with a null `title`, a top bar with only a close button is attached, and tapping it calls `onDismissRequest`. Popups that had no top bar in 3.x because they passed no `topBar` **gain a close button** in 4.0. To use it without a top bar, move to the `bottomBar` overload (`topBar = null`).

---

### 2.7 Popup · BottomSheet padding

3.x picked content, button, and top bar padding all at once from a single `ModalSize` enum (Small/Medium/Large/XLarge/Custom). 4.0 **takes padding per area as a data class.**

```kotlin
// 3.x - pick a padding preset
WantedModal(size = ModalSize.Custom, onDismissRequest = { … }, bottomBar = { … }) { … }

// 4.0 - change only what you need
WantedPopup(
    onDismissRequest = { … },
    popupDefault = WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp),
    bottomBar = { … }
) { … }
```

| `WantedPopupDefaults.getDefault(...)` argument | Default |
|---|---|
| `shape` | `RoundedCornerShape(24.dp)` |
| `width` | `360.dp` (max width) |
| `navigationPadding` | `0.dp` |
| `contentHorizontalPadding` | `28.dp` |
| `contentVerticalPadding` | `0.dp` |
| `actionHorizontalPadding` | `24.dp` |
| `actionVerticalPadding` | `20.dp` |
| `actionBottomPadding` | `4.dp` |
| `actionAreaType` | `ActionAreaType.Strong` |

| `WantedBottomSheetDefaults` | Radius (top) | Content horizontal | Content vertical | Button area horizontal | Button area vertical |
|---|---|---|---|---|---|
| `getDefault()` | 32 | 28 | 0 | 24 | 20 |
| `getFullDefault()` (`FixedFullScreen`) | 32 | 24 | 20 | 20 | 20 |
| `getWithoutContentPadding(type)` | as above | **0** | **0** | as above | as above |

For sheets that used `ModalSize.Custom` (all zeros), `getWithoutContentPadding()` is the closest. The top bar and button area padding, however, keep their spec values instead of 0.

---

## 3. No replacement

There is no mechanical mapping for these, so **you have to choose at the call site.** Dropping in something similar on autopilot means the result changes without anyone noticing.

### 3.1 TextField trailing button variant

The choice between `RightVariant.Normal` (blue text) and `RightVariant.Assistive` (black text) is gone, fixed to **a single Outlined · Assistive button**. Verify and confirm buttons that used `Normal` for blue emphasis change color. If emphasis is essential, put your own button in the `trailingContent` slot.

### 3.2 SegmentedControl Outlined

`WantedSegmentedControlOutlined` and `WantedSegmentedControlOutlinedItem` are removed entirely. The bordered segment is **merged into Solid (track + floating knob)**, and there is no counterpart component. Moving over changes its appearance.

### 3.3 SearchField Size.Custom · focused

`Size.Custom(padding, minHeight)` is removed. 4.0 has only `Large` (48) and `Medium` (40), so places that used another height must pick one, and the height changes. Overriding with `Modifier.height()` may not match the internal padding (Large 8 / Medium 6).

The `focused: State<Boolean>` used to inject focus state from outside is also gone. Focus is computed internally from `interactionSource`; move focus with `focusRequester`.

### 3.4 Warning icon in the negative state

In 3.x, TextField and Select automatically attached **a red exclamation icon on the right** when in error and unfocused. 4.0 does not draw this icon, and there is no option to turn it on. Errors are shown only by the red border and the `WantedFormControl` message.

### 3.5 IconButton box-to-icon ratio

4.0 IconButton derives the icon size from the box, so **you cannot set the box and icon independently.** Places where 3.x used `padding:` or `modifier` to break the ratio, such as "box 40 with icon 22", must pick the nearest preset or `Custom`, and the icon size changes.

### 3.6 Outlined Button + NEGATIVE

`ButtonType.NEGATIVE` is new, but `OUTLINED` has no negative spec. `WantedButton(variant = OUTLINED, type = NEGATIVE)` compiles, but logs an error at runtime and **renders as `PRIMARY`.** Use `SOLID` if you need negative emphasis.

### 3.7 ListCell InteractionPadding.Custom

`WantedListCellDefaults.InteractionPadding` (Default / Custom) is removed entirely. Only the fixed values of `Variant` are available (Inset touch extension 12 / Full horizontal padding 20).

Values that match a Variant, like `Custom(12.dp)` or `Custom(20.dp)`, map to `Inset` or `Full` respectively, but **any other value (`Custom(16.dp)` and so on) has no counterpart and the padding changes.** Choose whether to match it with an outer `Modifier.padding` or `cellDefault.contentPadding` (new in 4.0), or to follow the design's padding.

### 3.8 FallbackView button color

`positiveColor` and `negativeColor` (`ButtonType`) are dropped, and the buttons are fixed to `OUTLINED` + `ASSISTIVE`. Buttons that used `PRIMARY` for emphasis **become gray outlined buttons.**

### 3.9 FallbackView image slot

The 4.0 design drops the image slot. Only a deprecated overload remains for existing screens, taking `image` as **the first required parameter**.

- That overload has no `padding`, and the 4.0 vertical padding (160/80) does not apply.
- Places where 3.x passed `image = null` explicitly fail to compile, since it is a non-null required parameter. Removing the `image` argument switches to the image-less 4.0 overload, and the padding becomes 160 ([2.5](#25-fallbackview-padding)).
- The overload may be removed later, so if the illustration is essential, assemble the layout yourself at the call site.

### 3.10 AvatarGroup type · size

| 3.x | 4.0 |
|---|---|
| `size: WantedAvatarSize` (5 sizes + Custom) | `size: WantedAvatarGroupSize` (`XSmall` · `Small` only) |
| `type: WantedAvatarType` | Removed - fixed to `Person` |

- `WantedAvatarSize.XSmall` → `WantedAvatarGroupSize.XSmall`, `Small` → `WantedAvatarGroupSize.Small`. **`Medium`, `Large`, `XLarge`, and `Custom` have no counterpart**, so the avatars shrink to 32 or less.
- Groups of company or school logos built with `type = Company` or `Academic` **change from rounded squares to circles.** If you need the square shape, overlap standalone `WantedAvatar`s yourself.
- At most 5 are drawn at a time. **The 6th onward is silently cut off**, so show the overflow yourself with `trailingContent`, as in "+N more".

```kotlin
// 3.x
WantedAvatarGroup(
    modelList = urls,
    size = WantedAvatarSize.XSmall,
    type = WantedAvatarType.Person,
    trailingContent = { Text("+3 more") }
)

// 4.0
WantedAvatarGroup(
    modelList = urls,
    size = WantedAvatarGroupSize.XSmall,
    trailingContent = { WantedAvatarGroupTrailingText(text = "+3 more") }
)
```

### 3.11 PushBadge borderWidth

There is no counterpart to `WantedPushBadgeBorder(borderWidth:)`. The outline thickness is fixed per size (Dot: XSmall 0.5 / Small 1 / Medium 1, Text: XSmall 1 / Small 1.5 / Medium 2). Places that used the 3.x default of 2 mostly get a thinner outline.

### 3.12 Popup FixedFullScreen · FixedRatio

`Resize` has only `Hug` and `Fixed(height)`. Places where 3.x showed a Popup at full screen height (`FixedFullScreen`) or a screen ratio (`FixedRatio`) must compute the height themselves and pass `Fixed(height)`, or move to a BottomSheet. `Fixed` is still capped at 760dp.

### 3.13 DialogTopAppBar Variant.Display

The modal top bar's `Display` (min height 72, title `title3Bold`) is gone. Moving to `Emphasized` (min height 56, title `headline2Bold`) **makes the title smaller and the bar shorter.** The screen top bar (`WantedTopAppBarContract.Variant.Display`) is still there.

---

## 4. Visual changes

**These compile cleanly but change the screen.** The API is unchanged, so they only surface once you look at the result of the migration.

### 4.1 Component specs

#### Opacity-derived tokens

Renaming alone ([1.2](#12-opacity-derived-tokens)) compiles, but the tokens below **change RGB and therefore color.** The alpha is the same.

| 3.x → 4.0 | Light RGB | Dark RGB |
|---|---|---|
| `primaryNormalOpacity*` → `surfaceBrandPrimaryOpacity*` | `#3366FF` → `#0066FF` | `#5B84FF` → `#3385FF` |
| `labelNormalOpacity*` → `foregroundNeutralPrimaryOpacity*` | `#171717` → `#171719` | `#F7F7F7` → `#F7F7F8` |
| `labelAlternativeOpacity*` → `foregroundNeutralTertiaryOpacity*` | `#8A8A8A` → `#37383C` **noticeably darker** | `#737373` → `#AEB0B6` **noticeably lighter** |
| `lineNormalOpacity*` → `lineNeutralPrimaryOpaqueOpacity*` | Same | `#36373B` → `#37383C` |
| `statusPositiveOpacity*` → `foregroundPositivePrimaryOpacity*` | `#07BA9C` (teal) → `#00BF40` (green) | `#33D4B9` → `#1ED45A` |
| `statusNegativeOpacity8` → `foregroundNegativePrimaryOpacity8` | `#FF425F` → `#FF4242` | `#FF667D` → `#FF6363` |
| `accentCyanOpacity*` → `surfaceAccentCyanOpaqueOpacity*` | Same | `#00BDDE` → `#28D0ED` |
| `accentLightBlueOpacity*` → `surfaceAccentLightBlueOpaqueOpacity*` | `#0095FF` → `#00AEFF` | `#0095FF` → `#3DC2FF` |
| `accentVioletOpacity*` → `surfaceAccentVioletOpaqueOpacity*` | `#7A45E5` → `#6541F2` | `#7A45E5` → `#7D5EF7` |
| `accentPinkOpacity8` → `surfaceAccentPinkOpaqueOpacity8` | `#F342A0` → `#F553DA` | `#F342A0` → `#FA73E3` |
| `accentLimeOpacity8` → `surfaceAccentLimeOpaqueOpacity8` | `#93C400` → `#58CF04` | `#93C400` → `#6BE016` |

`labelAlternativeOpacity*` and `statusPositiveOpacity*` differ **enough to change hue family.** The rest (`labelStrongOpacity*`, `backgroundNormalNormalOpacity*`, `backgroundElevatedNormalOpacity*`, `lineAlternativeOpacity52`) keep their values.

#### Button

| Item | 3.x | 4.0 |
|---|---|---|
| radius | large 12 / medium 10 / small 8 | large **14** / medium **12** / small **10** / xsmall 8 |
| Height constraint | fixed `height` 48 / 40 / 32 | `heightIn(min =)` 48 / 40 / 32 / xsmall 28 |
| Horizontal padding | 28 / 20 / 14 | **20 / 16 / 12** / xsmall 10 |
| Vertical padding | 4 | 13 / 10 / 8 / xsmall 6 |
| Icon-text gap | 6 / 5 / 4 | 6 / **4** / 4 / xsmall 4 |
| Typography (PRIMARY) | `body1Bold` / `body2Bold` / `label2Bold` | **`body2Bold` / `label1Bold` / `caption1Bold`** |
| Typography (ASSISTIVE) | `body1Medium` / `body2Medium` / `label2Medium` | **`body2Bold` / `label1Bold` / `caption1Bold`** (bolder) |
| Loading indicator | 18 / 16 / 14 | **16 / 14 / 12** / xsmall 12 |
| Solid disabled text | `labelAssistive` | `foregroundDisablePrimary` (lighter) |

**Typography dropping one step per size is the most noticeable change.** The button height stays the same but the text gets smaller, and ASSISTIVE gets bolder, from Medium to Bold. Horizontal padding also shrinks, so `wrapContent` buttons get narrower.

The height went from fixed to minimum, but the text is still single-line with truncation. Long labels do not wrap; the button only grows taller **when the system font size is enlarged** (3.x clipped the text).

#### TextButton

| Item | 3.x | 4.0 |
|---|---|---|
| Height | unconstrained (text + 4 above and below) | `heightIn(min =)` medium 32 / small 28 / large **40** |
| Icon size | large 18 / medium **18** / small 16 | large 18 / medium **20** / small 16 |
| Icon-text gap | small 4 / others 5 | medium **4** / small 4 / large 5 |
| Vertical touch-area extension | 4 | **0** |
| Loading indicator | 18 / 16 / 14 | large 16 / medium 16 / small **12** |

MEDIUM and SMALL keep the same layout height as 3.x. **`LARGE` gains a minimum height of 40 and gets taller than in 3.x.** The same applies to places that used `ButtonVariant.TEXT` + `ButtonSize.LARGE`.

#### IconButton

| Item | 3.x | 4.0 |
|---|---|---|
| Normal occupied space | icon size (touch area spills 8 on each side) | **box size** (Xlarge 36). Icon size with `interactionOverflow = true` |
| Normal touch area | icon + 16, circular | box, rounded square (radius 10) |
| Normal pushBadge position | outward from the icon's top-right | aligned to the icon's top-right corner |
| Outlined · Solid icon | Medium **20** / Small **18** | Medium **18** / Small **16** |
| Outlined disabled background | `transparent` | `backgroundNeutralPrimary` |
| Outlined · Solid ripple | none | Outlined 8% / Solid 20% |
| Background `alternative` icon | `coolNeutral50` 88% | **`staticWhite` 88%** |
| Background `alternative` background | `coolNeutral30` opaque | `coolNeutral30` **61%** |

**For Normal, the larger occupied space has the widest blast radius.** In dense spots like toolbars and list rows, gaps widen and row heights grow. To keep 3.x spacing, turn on `interactionOverflow = true` ([2.3](#23-iconbutton-sizing)).

#### Chip

| Item | 3.x | 4.0 |
|---|---|---|
| Typography | large `body2` / medium `body2` / small `label1` / xsmall `caption1` | large **`label1`** / medium **`label2`** / small **`caption1`** / xsmall **`caption2`** (Medium) |
| Height | 40 / 36 / 32 / 24 | 40 / 36 / 32 / 24 (same) |
| Horizontal padding | 12 / 11 / 8 / 7 | 12 / **10** / 8 / **6** |
| radius | 10 / 10 / 8 / 6 | **12** / 10 / **10** / **8** |
| Icon-text gap | 3 / 3 / 2 / 2 | **2 / 2** / 2 / **0** |
| **Solid active background** | `inverseBackground` (black) | **`surfaceBrandPrimary` 5%** (light blue) |
| **Solid active text and icon** | `inverseLabel` (white) | **`foregroundBrandPrimary`** (blue) |
| Outlined active border | `primaryNormal` 43% | `surfaceBrandPrimary` **28%** (lighter) |

**The Solid active chip changes from a black fill to a light blue fill with blue text.** Be sure to check filter and tab-style chips that use `isActive = true`. The height is the same while the text drops one step, so chips get narrower. Where chips are laid out in a row, the wrap points change.

#### FilterButton

| Item | 3.x | 4.0 |
|---|---|---|
| Typography XSmall / Small / Medium / Large | `caption1` / `label1` / `body2` / `body2` | **`caption2` / `caption1` / `label2` / `label1`** (Medium) |
| radius | 6 / 8 / 10 / 10 | **8 / 10 / 10 / 12** |
| activeLabel weight | SemiBold | Medium, same as the body |
| **Solid active** background / text | `inverseBackground` (black) / `inverseLabel` (white) | **`surfaceBrandPrimary` 5%** / **`foregroundBrandPrimary` (blue)** |
| Outlined active border | `primaryNormal` 43% | **`surfaceBrandPrimary` 28%** |
| Outlined active icon | `labelNormal` (black) | **`foregroundBrandPrimary` (blue)** |

Filter bars laid out horizontally get different button widths, so **their wrap and scroll points change.**

#### ActionArea

| Item | 3.x | 4.0 |
|---|---|---|
| `divider` default | `false` | **`true`** (drawn only when there is `extra`) |
| `extra` divider color | `lineNormalNeutral` (16%) | `lineNeutralTertiary` (8%, lighter) |
| `extra` slot horizontal padding | 20 | **24** |
| Background | gradient only, the area itself is transparent | **filled with `backgroundColor`** when `background = true` or there is `extra` |
| Background and gradient default color | `backgroundNormalNormal` | `surfaceElevatedPrimary` (one step lighter in dark) |
| Caption typography | `label2Regular` | **`label2Medium`** (bolder) |
| Alternative (`alternative`) button | `OUTLINED` / **`PRIMARY`** | `OUTLINED` / **`ASSISTIVE`** |
| `Cancel` main button | **`OUTLINED`** / `ASSISTIVE` | **`SOLID`** / `ASSISTIVE` |
| `Strong` secondary (`sub`) button vertical padding | 0 | **8** (area grows 16 taller) |
| `Neutral` secondary button minimum width | 84 | label width |

**The alternative action label changes from blue to black, and the `Cancel` main button changes from outlined to a gray fill.**

Screens using the `extra` slot had no divider in 3.x, but get one by default in 4.0. To remove it as in 3.x, pass `divider = false`.

4.0 always fills with `backgroundColor` when there is `extra`. If the screen background is `backgroundNeutralPrimary` and you keep the default (`surfaceElevatedPrimary`), **only the button area looks one step lighter in dark mode.** Pass `backgroundColor` to match the screen background.

#### TextField

| Item | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| Height | padding 12 + line height | min Large **48** / Medium **40** |
| Input typography | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| Shadow | `XSmall` | **none** |
| Focus indicator | 2dp border | 1dp border + **outer 4dp ring** (`lineBrandFocus`, red 12% on error) |
| Trailing button | a separate cell attached to the field (left divider, `body1Bold` text) | an Outlined · Assistive button **inside** the field |
| Complete (Positive) check icon color | `primaryNormal` (blue) | **`foregroundPositivePrimary` (green)** |
| Error exclamation icon | present | **none** ([3.4](#34-warning-icon-in-the-negative-state)) |
| Multiple lines | **always one line** regardless of `maxLines` | one line only when `maxLines` and `minLines` are both 1. **Places passing `maxLines > 1` actually become multi-line** |
| Long value when unfocused | clipped | **truncated (…)** |

#### TextArea

| Item | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| Input typography | `body1Regular` | Large **`body2ReadingRegular`** / Medium **`label1ReadingRegular`** |
| Shadow | `XSmall` | **none** |
| Focus indicator | 2dp border | 1dp border + outer 4dp ring |
| Bottom button | text button | **Outlined · Assistive** button |
| Character count typography | `label2Medium` | **`caption1Bold`** |

#### Select

| Item | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| Height | padding 12 + content | min Large **48** / Medium **40** |
| Value and placeholder typography | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| Shadow | `XSmall` | **none** |
| Placeholder color | `labelAssistive` | **`foregroundNeutralTertiary`** (darker) |
| Vertical alignment | chevron always at top | top only with `overflow = true`, otherwise **center** |
| Pressed ripple | red on error, blue on focus | **neutral** regardless of state |
| Error exclamation icon | present | **none** |
| Multi-select chip | custom layout | **`WantedChip` XSmall Outlined** - gains a border |
| Multi-select chip icon | always close (×) even with `iconRes` | the icon from `WantedSelectData.iconRes` |
| Radio selection sheet | radio shown **only on the selected item** | **radio on every item** (empty circle when unselected); tapping the radio also selects |

#### SearchField · SearchTopAppBar

| Item | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| Input typography | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| Icon | 24 | Large **20** / Medium **18** |
| Cursor color | text color | `foregroundBrandPrimary` |

#### SegmentedControl

| Item | 3.x | 4.0 |
|---|---|---|
| Track radius | 12 | Small **10** / Medium **12** / Large **14** |
| Item height | determined by padding | min Small **24** / Medium **32** / Large **40** |
| Typography Small / Medium / Large | `label2` / `body2` / `headline2` | **`caption1` / `label1` / `body2`** (one step smaller each) |
| Icon | 20 | Small **14** / Medium **16** / Large **18** |

#### ListCell

| Item | 3.x | 4.0 |
|---|---|---|
| `ellipsis` default | `true` | **`false`** |
| Title max lines | always `textMaxLine` (default 1) | `textMaxLine` **only with `ellipsis = true`**, otherwise unlimited |
| `verticalAlignCenter` default | `ellipsis` (= `true`) | **`false`** |
| Slot alignment reference | the whole text area | **the first title line** |
| Title typography | `body1Regular` | **`body2Medium`** (`body2Bold` when selected) |
| Disabled | whole cell at 43% opacity | `foregroundDisablePrimary` only on title, description, check, and chevron. **Slot content is not dimmed** |
| Disabled click | only the ripple turns off; `onClick` still fires | **click blocked** |
| selected | only the title color turns primary | title `foregroundBrandPrimary` + `body2Bold`; **a check icon appears automatically** when trailing is empty |
| Pressed radius - Inset (`fillWidth = false`) | 0 | **16** |
| Pressed radius - Full (`fillWidth = true`) | 12 | **0** |
| `VerticalPadding.None` | ripple present | **no interaction** |

**The `ellipsis` and `verticalAlignCenter` defaults changing has the widest blast radius.** Cells that truncated to one line by default in 3.x **wrap to multiple lines and grow taller** in 4.0, and icons align **to the first line** instead of the title center. To keep the 3.x look, set them explicitly.

```kotlin
// keep the 3.x default behavior (one-line truncation + vertical centering) in 4.0
WantedListCell(
    text = title,
    ellipsis = true,
    verticalAlignCenter = true,
    onClick = {}
)
```

Image slots such as avatars and thumbnails in disabled cells are no longer dimmed automatically. To match 3.x, apply `alpha(OPACITY_43)` at the call site.

#### ListCard

| Item | 3.x | 4.0 |
|---|---|---|
| Pressed-area shape | fixed left 20 / right 12 | `RoundedCornerShape(12.dp)` (changeable via `cardDefault.interactionShape`) |

The pressed-area shape of `WantedCard` stays the same.

#### Avatar · AvatarGroup

Company and Academy cornerRadius grows by **+2** at every size.

| size | 3.x | 4.0 |
|---|---|---|
| `XSmall` (24) | 6 | 8 |
| `Small` (32) | 8 | 10 |
| `Medium` (40) | 10 | 12 |
| `Large` (48) | 12 | 14 |
| `XLarge` (56) | 14 | 16 |

| Item | 3.x | 4.0 |
|---|---|---|
| Default placeholder (`placeHolder = null`) | per-type illustration | `surfaceNeutralStrong` fill + `staticWhite` 28% icon glyph (2/3 of the avatar, `icon_normal_person_fill` / `company_fill` / `graduation_fill`) |
| Group border width | 2 | **1.5** |
| Push badge | right at the avatar corner | **background-colored outline** + per-size inset |
| AvatarGroup avatar-trailing gap | XSmall 6 / others 8 | XSmall **8** / Small **10** |
| AvatarGroup trailing slot height | fixed 24 | XSmall 24 / Small **32** |

Where you pass `placeHolder` explicitly, that resource is drawn in its original colors as in 3.x.

#### PushBadge

| Item | 3.x | 4.0 |
|---|---|---|
| Text badge height | the larger of the min height (16/20/24) and the content | XSmall 16 / Small 20 / Medium 24 |
| Single character | min width + padding | **fixed square (1:1)** |
| Font scaling | unlimited | stops at 1.3x |

The icon or avatar the badge sits on does not grow with the font size, so a badge that kept growing would cover its target.

#### FallbackView

| Item | 3.x | 4.0 |
|---|---|---|
| Vertical padding | 12 | **160** (`Compact` 80) |
| Title typography | `heading2Bold` | **`headline1Bold`** |
| Description typography | `body1ReadingRegular` | **`body2ReadingRegular`** |
| Description color | `labelAlternative` | **`foregroundNeutralSecondary`** (darker) |
| Button size | `SMALL` | **`MEDIUM`** |
| Button color | `positiveColor` · `negativeColor` | fixed `ASSISTIVE` |

#### Popup · BottomSheet

Corners and padding across modals were adjusted. **The API is unchanged, so the build will not tell you.**

| Item | 3.x (`ModalSize.Medium`) | 4.0 |
|---|---|---|
| Popup radius | 12 | **24** |
| Popup max width | unlimited | **360** |
| Popup content horizontal padding | 20 | **28** |
| Popup button area padding | 20 on all sides | horizontal **24** · top 20 · bottom **24** |
| BottomSheet radius (top) | 16 | **32** |
| BottomSheet content padding | **0** (the component adds none) | horizontal **28** |
| BottomSheet button area padding | 20 on all sides | horizontal **24** · vertical 20 |
| `FixedFullScreen` sheet content padding | 0 | horizontal 24 · vertical 20 |
| Top bar slot padding | 4 above and below | **0** (the top bar owns its padding) |
| Button position in fixed-height types | right below the content | the content fills the remaining space, so **the buttons stick to the bottom** |

**BottomSheet content padding going from 0 to 28 has the widest reach.** 3.x sheets supplied content padding at the call site, so leaving them as is doubles the padding and narrows the content. To keep the existing padding, pass `sheetDefault = WantedBottomSheetDefaults.getWithoutContentPadding(type)`. For Popups whose content supplied its own padding, pass `WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp)`.

#### DialogTopAppBar

| Item | 3.x | 4.0 |
|---|---|---|
| `Emphasized` padding | 8 vertical · 16 horizontal (+ 4 above and below from the modal slot) | **24 on all sides** (`navigationPadding`) |
| leading ↔ title gap | 12 | **16** (12 for `Search`) |
| Close button | icon 24 + circular ripple 40 | occupies **24**, container 36 spills outside, **the icon dims** on press |

An `Emphasized` top bar inside a modal, which was 12 vertical and 16 horizontal in 3.x, **grows to 24 vertical and 24 horizontal in 4.0, making the bar 24dp taller and moving the title 8dp inward.** If you need to keep the 3.x look, reduce `navigationPadding` yourself. Full-screen modals use `WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING` (20).

```kotlin
// 4.0 - to get the same 12 above and below as 3.x
WantedDialogCloseTopAppBar(
    variant = WantedDialogTopAppBarContract.Variant.Emphasized,
    navigationPadding = 12.dp,
    title = "Title",
    onClickClose = onDismiss
)
```

`Normal` and `Floating` keep the center-aligned layout (16 horizontal), so `navigationPadding` does not affect them.

#### TopAppBar · DialogTopAppBar

| Item | 3.x | 4.0 |
|---|---|---|
| Icon button press feedback | circular ripple | **no ripple, icon opacity 22%** (`IconButtonInteractionEffect.Dim`) |
| Icon · touch area | 24 · 40 | 24 · 40 (same) |

Pass `interactionEffect = IconButtonInteractionEffect.Highlight` to bring the ripple back.

#### Category

| Item | 3.x | 4.0 |
|---|---|---|
| `Normal` selected chip | background `inverseBackground`, text `inverseLabel` | background **`foregroundNeutralStrong`**, text `foregroundNeutralInverse` |
| `Alternative` selected chip border | `primaryNormal` 43% | `surfaceBrandPrimary` 43% |

In light mode the `Normal` selected chip becomes a slightly deeper black. Size and typography changes of the chip itself follow [Chip](#chip-1).

#### Everything else

- **Pagination Dots**: with 5 pages or fewer, every dot is drawn full size even if that exceeds `visibleDotCount`. In 4-5 page carousels the indicator gets wider.
- **AutoComplete**: `sectionTitleHorizontalPadding` (default 20) applies as horizontal padding not only to section titles but **also to items and the direct-input slots**. Items that supplied their own horizontal padding get it doubled, so if you put a `WantedListCell` in an item, leave it at `variant = Inset` (the default).
- **Alert dialog buttons · Tooltip action buttons**: now `WantedTextButton`. The Alert negative button grows from SMALL → **MEDIUM**.
- **Date / Time wheel Picker**: now shown as a `WantedPopup`, so the radius changes from 28 → 24.
- **Tooltip**: the tail position is clamped inside the tooltip width, and on narrow screens the edge margin is reduced to the space available.
- **WantedInput**: with `label = ""` it no longer reserves space for the label. Invisible padding disappears where you used only the control with an empty label.
- **InnerLine border** (`Modifier.getBorderModifier(borderType = BorderType.InnerLine)`): the rounded border edge no longer looks broken in the dark theme.
- **Global toast** (`WantedGlobalToastManager`): skips showing on Activities that cannot host Compose content.

Typography and Shape definitions are unchanged.

### 4.2 Full list

**Walk through these screens after migrating.**

| Target | What changes |
|---|---|
| **Color tokens** | `accentForegroundBlue` and `Orange` change color in both light and dark, `accentForegroundGreen` in light. The dark mode dim (`materialDimmer`) gets noticeably darker. See [Tokens whose value also changes](#tokens-whose-value-also-changes) |
| **Opacity-derived tokens** | RGB is corrected to the base token, so colors change. `labelAlternativeOpacity*` and `statusPositiveOpacity*` change hue family |
| **RedOrange tokens** | Moving to `foregroundCautionaryPrimary` (orange) **changes the color** |
| **Button** | Typography drops one step and horizontal padding shrinks, so `wrapContent` buttons get narrower. radius +2. ASSISTIVE text gets bolder |
| **TextButton** | `LARGE` grows to a minimum height of 40. MEDIUM icon 18 → 20 |
| **IconButton** | The space Normal occupies grows from the icon size to the box size (36). Leaving `size` in `modifier` shrinks the touch area. Outlined and Solid icons shrink by 2dp |
| **Chip · FilterButton** | Typography drops one step, and **Solid active changes from black to light blue**. Wrap points in horizontal rows change |
| **ActionArea** | With `extra`, a divider appears and the background is filled. The alternative action label turns blue → black, and the `Cancel` main button outlined → gray fill. `Strong` + `sub` grows 16 taller |
| **Input components** | radius, typography, and height change; the shadow disappears and a focus ring appears. The error exclamation icon disappears. Moving to `WantedFormControl` moves the TextArea counter |
| **TextField** | Places passing `maxLines > 1` actually become multi-line. The trailing button becomes an Outlined button inside the field, and the complete check goes blue → green |
| **TextArea** | Moving to the body overload gives 2-6 lines by default with no height cap, and the counter disappears |
| **Select** | The Radio sheet draws a radio on every item. Multi-select chips gain a border |
| **SearchField** | `Size` names shift by one step, so dropping only the parentheses changes the height 48 → 40 |
| **SegmentedControl** | Height and typography change, and Outlined is gone |
| **ListCell** | Long titles **wrap instead of truncating**, and slots align to the first line. Disabled cells block clicks and their slots are not dimmed. Selected cells get an automatic check icon |
| **ListCard** | The pressed area goes from left 20 / right 12 to 12 |
| **Avatar · AvatarGroup** | Company and Academy radius +2, placeholder replaced. AvatarGroup is fixed to circles and at most 5 |
| **PushBadge** | Single-character badges are square, font scaling capped at 1.3. The default outline color becomes the background color |
| **FallbackView** | 160 vertical padding built in, buttons fixed to MEDIUM · ASSISTIVE, title and description typography one step smaller each. The image slot disappears |
| **Popup** | radius 24, max width 360, content horizontal 28. The button overload always gets a close button, and its buttons become an ActionArea |
| **BottomSheet** | radius 32, **new 28 horizontal content padding** |
| **DialogTopAppBar** | `Emphasized` padding grows to 24 on all sides, so the bar gets taller. Pressing close dims the icon |
| **TopAppBar** | Pressing an icon button dims the icon instead of showing a ripple |
| **Category** | The `Normal` selected chip gets slightly darker in light mode |
| **Pagination Dots** | With 5 pages or fewer, every dot is full size |
| **AutoComplete** | Items also get 20 horizontal padding |

---

## Added APIs

**Not needed for the migration.** These are new in 4.0 and listed here for reference only.

### New components

| Component | Description |
|---|---|
| `WantedFormControl` | Lays out an input component's label, required mark, description, and accessory. `WantedFormControlDefaults` (`Status`, `Size`, `LabelPlacement`) |
| `WantedPopup` | Replaces `WantedModal`. `WantedPopupContract.Resize`, `WantedPopupDefaults.getDefault(...)` |
| `WantedDialogSearchTopAppBar` | Modal top bar with a search field |
| `WantedSegmentedControlItem` | icon + text / Icon Only item |
| `WantedAvatarGroupTrailingText` · `WantedAvatarGroupTrailingTextButton` | AvatarGroup trailing slot presets |
| `WantedTextAreaCharacterCount` | Character count for the `WantedFormControl` accessory |
| `Modifier.focusRing(visible, shape, color, width)` | Focus ring |

### New token groups

`DesignSystemTheme.spacing`, `radius`, `dimension`, and `primitive`. 3.x had no dp tokens. They are provided automatically inside `DesignSystemTheme { }`.

| Group | Values (dp) |
|---|---|
| `spacing` | 0, 2, 4, 6, 8, 10, 12, 14, 16, 20, 24, 32, 40, 48, 56, 64, 72, 80 |
| `radius` | 0, 4, 8, 10, 12, 14, 16, 20, 24, 28, 32, `radiusFull` |
| `dimension` | 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64 |
| `primitive` | Raw values. Use the three groups above instead of these directly |

```kotlin
Box(
    modifier = Modifier
        .padding(DesignSystemTheme.spacing.spacing20)
        .clip(RoundedCornerShape(DesignSystemTheme.radius.radius12))
        .size(DesignSystemTheme.dimension.dimension40)
)
```

New semantic color tokens were added too: `surfaceNeutralPrimary`, `surfaceBrandSubtle`, `surfaceNegativeStrong`, `surfaceAccent*` (translucent 8%), `lineBrand{Primary,Strong,Focus}`, `lineNegative{Primary,Strong,Focus}`, and `lineAccent*`. Primitive opacity-variant resources (`blue_50_opacity5` and so on) were added as well.

### Per-component additions

| Component | Added |
|---|---|
| `WantedButton` | `ButtonSize.XSMALL`, `ButtonType.NEGATIVE` (Solid only) |
| `WantedTextButton` | `WantedTextButtonColor`, `WantedTextButtonSize`, `WantedTextButtonDefault`, `WantedTextButtonDefaults.getDefault(...)` |
| `WantedIconButtonNormal` | `size`, `badgePosition`, `interactionEffect` (`Highlight`/`Dim`/`None`), `interactionColor`, `interactionOverflow` |
| `WantedIconButtonBackground` | `size`, `pushBadge`, `badgePosition`, `disableInteraction`, `interactionColor`, `useNormalInteraction` |
| `WantedIconButtonOutlined` · `Solid` | `pushBadge`, `badgePosition`, `disableInteraction`, `interactionColor`, `WantedIconButtonSize.Custom` |
| IconButton common | `WantedIconButtonNormalSize`, `WantedIconButtonBackgroundSize`, `IconButtonBadgePosition` (9 positions), `*.presets` |
| `WantedActionArea` | `captionIcon`, `WantedActionAreaDefaults.CAPTION_ICON` |
| `WantedTextField` | input body overload (`value`, `error`, `complete`, …), `size` |
| `WantedTextArea` | input body overload, `size`, `resize` (`Normal`/`Limit`/`Fixed`), `status` |
| `WantedSelect` | 4 body overloads, `size`, `status`, `leadingIcon`, `errorDataList` / `errorList` |
| `WantedSearchField` | `variant` (`Solid`/`Outlined`) |
| `WantedSegmentedControl` | `iconOnly`, `LocalWantedSegmentedIconOnly` |
| `WantedCheckBox` | overload taking `style`, `size`, `checkState`, `tight` made public (internal in 3.7.0) |
| `Modifier.framedStyle` | `focused` |
| `WantedListCell` | `variant`, `cellDefault: WantedListCellDefault` (background, shape, border, contentPadding), `labelTrailingContent`, `extraContent`, `enabledInnerTouch`, `WantedListCellDefaults.getDefault(...)` |
| `WantedCardDefault` | `backgroundColor`, `shape`, `border`, `interactionShape`, `contentPadding` |
| `WantedAvatar` · `WantedAvatarGroup` | `contentDescription`, `WantedAvatarGroupSize` |
| `WantedPushBadge` | `maxCount`, `outlineBorder`, `outlineBorderColor`, `textStyle`, `inset` |
| `WantedFallbackView` | `padding: WantedFallbackPadding` |
| `WantedModalBottomSheet` | `sheetDefault`, `WantedBottomSheetDefaults.getDefault()` · `getFullDefault()` · `getWithoutContentPadding(type)` |
| `WantedDialogTopAppBar` | `Variant.Search`, `Variant.Floating(iconBackground)`, `navigationPadding`, `NAVIGATION_PADDING` · `FULL_NAVIGATION_PADDING` |
| `WantedTopAppBarIconButton` | `interactionEffect` |
| `WantedCategory` | `WantedCategoryDefault`, `getDefault()` · `getChipDefault()`, `Variant`, `Size.rightIconSize` |
| `WantedPopover` | `screenEdgePadding` (default 8) |
| `WantedTooltip` | `screenEdgePadding` (default 2) |

---

## Checklist

### Substitution

- [ ] No old names left after the color token substitution - `grep -rnE "(colors|colorsOpacity)\.(label|fill|interaction|inverse|status|accent|primary|material|backgroundNormal|backgroundElevated|backgroundTransparent|backgroundStatus|lineNormal|lineSolid|lineStatus|lineAlternative)" --include="*.kt" .`
- [ ] Old resource names - `grep -rnE "(R\.color\.|@color/)(label|fill|interaction|inverse|status|accent|primary|material|background_normal|background_elevated|background_transparent|background_status|line_normal|line_solid|line_status|line_alternative)_" --include="*.kt" --include="*.xml" .`
- [ ] No `\b` in your sed (macOS BSD sed ignores it and **changes nothing**)
- [ ] If you sent `primaryNormal` to `surfaceBrandPrimary` in bulk, the text and icon spots were fixed to `foregroundBrandPrimary`
- [ ] Spots that painted a fill with `accentBackgroundRedOrange` - its mapping is a foreground token, so check whether a `surface` token fits better
- [ ] `when` on `ButtonType` · `ButtonSize` without `else` - new entries cause compile errors
- [ ] `grep -rn "WantedActionArea(" -A20 --include="*.kt" . | grep -E "positive|negative|neutral|gradationColor"`
- [ ] `grep -rn "WantedChip(" -A10 --include="*.kt" . | grep -E "leftIcon|rightIcon"`
- [ ] `grep -rn "WantedTextButton(" -A8 --include="*.kt" . | grep -E "ButtonType\.|ButtonSize\."` - moved to the dedicated types
- [ ] `grep -rn "rightButton\|onClickRightButton\|RightVariant\|leftContent\|rightContent" --include="*.kt" .`
- [ ] `grep -rn "WantedTextArea(" -A25 --include="*.kt" . | grep "negative ="` - to `status`
- [ ] `grep -rn "isExpend\|WantedSegmentedControl\(Solid\|Outlined\)\|WantedSelectWithString" --include="*.kt" .`
- [ ] `grep -rn "WantedSegmentedControlItem(\"" --include="*.kt" .` - positional calls
- [ ] `grep -rn "\(^\|[^A-Za-z]\)Size\.\(Medium\|Small\|Custom\)(" --include="*.kt" .` - SearchField `Medium()` → `Large`, `Small()` → `Medium`
- [ ] `grep -rn "fillWidth\s*=\|InteractionPadding\." --include="*.kt" .` - ListCell · Accordion to `variant`
- [ ] `grep -rn "WantedAvatarType\.Academic\|icon_avatar_placeholder_academic" --include="*.kt" .`
- [ ] `grep -rn "PushBadgeVariant\.\(New\|Number\)\|WantedPushBadgeBorder" --include="*.kt" .` - no non-numeric values where you moved to `MaxCount`
- [ ] `grep -rn "WantedModal(" --include="*.kt" .` - to `WantedPopup`
- [ ] `grep -rn "WantedModalBottomSheet(" -A12 --include="*.kt" . | grep "modalSize"`
- [ ] `grep -rn "DialogTopAppBarContract.Variant.Display\|Variant\.Floating\b[^(]" --include="*.kt" .`
- [ ] `grep -rn "LocalWantedTopBarIconVariant\|isAlternative\s*=" --include="*.kt" .`

### Structure

- [ ] `title`, `requiredBadge`, and `description` on TextField, TextArea, and Select moved to `WantedFormControl` (the `is deprecated` build warnings find them too)
- [ ] `WantedFormControl.status` and the inner component's `error`/`status` computed from the same condition
- [ ] A character count `accessory` added where you moved to the TextArea body overload, and the `minLines` · `resize` default differences checked
- [ ] `background` where you moved to the TextField body overload (its default differs from the deprecated overloads)
- [ ] `grep -rn "WantedIconButtonNormal(\|WantedIconButtonBackground(" -A6 --include="*.kt" . | grep "Modifier.size("` - `modifier` size moved to `size:` and removed
- [ ] `grep -rn "WantedIconButton\(Outlined\|Solid\)(" -A6 --include="*.kt" . | grep "padding ="` - the removed `padding:` overload
- [ ] Places passing a `@Composable () -> Unit` variable directly to a ListCell slot
- [ ] Outer `padding(vertical =)` · `height()` cleaned up around `WantedFallbackView`
- [ ] `WantedModal` with two buttons moved to `bottomBar` + `WantedActionArea`
- [ ] BottomSheets and Popups that supplied their own content padding - `getWithoutContentPadding()` · `contentHorizontalPadding = 0.dp`

### Visual review

- [ ] Screens that used `accentForeground{Blue,Orange}` (light and dark), `accentForegroundGreen` (light)
- [ ] Dark mode screens with a dim - the content below is not hidden too much now that the dim is darker
- [ ] Spots that used `labelAlternativeOpacity*` · `statusPositiveOpacity*`
- [ ] Color of spots that used RedOrange (red-orange → orange)
- [ ] Solid and Outlined button widths, `LARGE` TextButton height
- [ ] Toolbars and rows using `WantedIconButtonNormal` - if gaps widened, `interactionOverflow = true`
- [ ] Solid active Chip and FilterButton (black → light blue), wrapping in chip rows and filter bars
- [ ] ActionArea with `extra` - divider and background color, `alternative` and `Cancel` button colors
- [ ] TextFields that passed `maxLines` of 2 or more, TextField and TextArea with a trailing button
- [ ] TextField and Select in the error state - meaning still comes through without the exclamation icon
- [ ] ListCells without an explicit `ellipsis` - long title wrapping, slot alignment, image slots in disabled cells, automatic check when selected
- [ ] Avatar placeholder without an image, Company and Academy `AvatarGroup`
- [ ] `WantedFallbackView` - the 160 vertical padding does not overflow inside bottom sheets and lists
- [ ] Button Popups that had no top bar - acceptable to gain a close button
- [ ] Popup and BottomSheet content horizontal padding not doubled
- [ ] Modal `Emphasized` top bar height and title position
- [ ] Images and lists touching the corners are not clipped now that the radius is larger (Popup 24, Sheet 32)
- [ ] Every screen in [4. Visual changes](#4-visual-changes), on device or in the emulator

The fastest way to review spec changes is to build the `sample` app at both versions and compare.

```bash
# the version you are coming from
git worktree add --detach ../baseline v3.7.0
(cd ../baseline && ./gradlew :sample:installDebug)

# the version you are moving to - same applicationId, so it installs over the old one. Capture screens and alternate installs
./gradlew :sample:installDebug
```

Install both on the same device, switch between them, and put the components from [4. Visual changes](#4-visual-changes) side by side. If something looks different that is not on that list, the document missed it - please let us know.

---

## Appendix: full color token substitution rules

Renames the `colors.*` · `colorsOpacity.*` properties and the `R.color.*` · `@color/*` resource names in one pass. This is for macOS (BSD) sed; on GNU sed, change `-i ''` to `-i` and `[[:<:]]` · `[[:>:]]` to `\b`.

```bash
find . \( -name "*.kt" -o -name "*.xml" \) -not -path "*/build/*" -exec sed -i '' -E \
  -e 's/[[:<:]]primaryNormal[[:>:]]/surfaceBrandPrimary/g' \
  -e 's/[[:<:]]primaryStrong[[:>:]]/surfaceBrandStrong/g' \
  -e 's/[[:<:]]primaryHeavy[[:>:]]/surfaceBrandHeavy/g' \
  -e 's/[[:<:]]labelNormal[[:>:]]/foregroundNeutralPrimary/g' \
  -e 's/[[:<:]]labelStrong[[:>:]]/foregroundNeutralStrong/g' \
  -e 's/[[:<:]]labelNeutral[[:>:]]/foregroundNeutralSecondary/g' \
  -e 's/[[:<:]]labelAlternative[[:>:]]/foregroundNeutralTertiary/g' \
  -e 's/[[:<:]]labelAssistive[[:>:]]/foregroundNeutralQuaternary/g' \
  -e 's/[[:<:]]labelDisable[[:>:]]/foregroundDisablePrimary/g' \
  -e 's/[[:<:]]backgroundNormalNormal[[:>:]]/backgroundNeutralPrimary/g' \
  -e 's/[[:<:]]backgroundNormalAlternative[[:>:]]/backgroundNeutralSecondary/g' \
  -e 's/[[:<:]]backgroundElevatedNormal[[:>:]]/surfaceElevatedPrimary/g' \
  -e 's/[[:<:]]backgroundElevatedAlternative[[:>:]]/surfaceElevatedSecondary/g' \
  -e 's/[[:<:]]backgroundTransparentNormal[[:>:]]/effectTransparentPrimary/g' \
  -e 's/[[:<:]]backgroundTransparentAlternative[[:>:]]/effectTransparentSecondary/g' \
  -e 's/[[:<:]]backgroundStatusNegative[[:>:]]/surfaceNegativePrimary/g' \
  -e 's/[[:<:]]backgroundStatusCautionary[[:>:]]/surfaceCautionaryPrimary/g' \
  -e 's/[[:<:]]backgroundStatusPositive[[:>:]]/surfacePositivePrimary/g' \
  -e 's/[[:<:]]interactionInactive[[:>:]]/foregroundInactivePrimary/g' \
  -e 's/[[:<:]]interactionDisable[[:>:]]/surfaceDisablePrimary/g' \
  -e 's/[[:<:]]lineNormalNormal[[:>:]]/lineNeutralPrimary/g' \
  -e 's/[[:<:]]lineNormalNeutral[[:>:]]/lineNeutralSecondary/g' \
  -e 's/[[:<:]]lineNormalAlternative[[:>:]]/lineNeutralTertiary/g' \
  -e 's/[[:<:]]lineSolidNormal[[:>:]]/lineNeutralPrimaryOpaque/g' \
  -e 's/[[:<:]]lineSolidNeutral[[:>:]]/lineNeutralSecondaryOpaque/g' \
  -e 's/[[:<:]]lineSolidAlternative[[:>:]]/lineNeutralTertiaryOpaque/g' \
  -e 's/[[:<:]]lineStatusCautionaryNormal[[:>:]]/lineCautionaryPrimary/g' \
  -e 's/[[:<:]]lineStatusPositiveNormal[[:>:]]/linePositivePrimary/g' \
  -e 's/[[:<:]]statusPositive[[:>:]]/foregroundPositivePrimary/g' \
  -e 's/[[:<:]]statusNegative[[:>:]]/foregroundNegativePrimary/g' \
  -e 's/[[:<:]]statusCautionary[[:>:]]/foregroundCautionaryPrimary/g' \
  -e 's/[[:<:]]accentBackgroundLime[[:>:]]/surfaceAccentLimeOpaque/g' \
  -e 's/[[:<:]]accentBackgroundCyan[[:>:]]/surfaceAccentCyanOpaque/g' \
  -e 's/[[:<:]]accentBackgroundLightBlue[[:>:]]/surfaceAccentLightBlueOpaque/g' \
  -e 's/[[:<:]]accentBackgroundViolet[[:>:]]/surfaceAccentVioletOpaque/g' \
  -e 's/[[:<:]]accentBackgroundPurple[[:>:]]/surfaceAccentPurpleOpaque/g' \
  -e 's/[[:<:]]accentBackgroundPink[[:>:]]/surfaceAccentPinkOpaque/g' \
  -e 's/[[:<:]]accentForegroundRed[[:>:]]/foregroundNegativeStrong/g' \
  -e 's/[[:<:]]accentForegroundOrange[[:>:]]/foregroundCautionaryPrimary/g' \
  -e 's/[[:<:]]accentForegroundLime[[:>:]]/foregroundAccentLime/g' \
  -e 's/[[:<:]]accentForegroundGreen[[:>:]]/foregroundPositivePrimary/g' \
  -e 's/[[:<:]]accentForegroundCyan[[:>:]]/foregroundAccentCyan/g' \
  -e 's/[[:<:]]accentForegroundLightBlue[[:>:]]/foregroundAccentLightBlue/g' \
  -e 's/[[:<:]]accentForegroundBlue[[:>:]]/foregroundBrandPrimary/g' \
  -e 's/[[:<:]]accentForegroundViolet[[:>:]]/foregroundAccentViolet/g' \
  -e 's/[[:<:]]accentForegroundPurple[[:>:]]/foregroundAccentPurple/g' \
  -e 's/[[:<:]]accentForegroundPink[[:>:]]/foregroundAccentPink/g' \
  -e 's/[[:<:]]inversePrimary[[:>:]]/foregroundBrandInverse/g' \
  -e 's/[[:<:]]inverseLabel[[:>:]]/foregroundNeutralInverse/g' \
  -e 's/[[:<:]]inverseBackground[[:>:]]/surfaceNeutralInverse/g' \
  -e 's/[[:<:]]fillNormal[[:>:]]/surfaceNeutralSecondary/g' \
  -e 's/[[:<:]]fillStrong[[:>:]]/surfaceNeutralStrong/g' \
  -e 's/[[:<:]]fillAlternative[[:>:]]/surfaceNeutralTertiary/g' \
  -e 's/[[:<:]]materialDimmer[[:>:]]/effectDimmerPrimary/g' \
  -e 's/[[:<:]]primaryNormalOpacity5[[:>:]]/surfaceBrandPrimaryOpacity5/g' \
  -e 's/[[:<:]]primaryNormalOpacity8[[:>:]]/surfaceBrandPrimaryOpacity8/g' \
  -e 's/[[:<:]]primaryNormalOpacity12[[:>:]]/surfaceBrandPrimaryOpacity12/g' \
  -e 's/[[:<:]]primaryNormalOpacity22[[:>:]]/surfaceBrandPrimaryOpacity22/g' \
  -e 's/[[:<:]]primaryNormalOpacity28[[:>:]]/surfaceBrandPrimaryOpacity28/g' \
  -e 's/[[:<:]]primaryNormalOpacity35[[:>:]]/surfaceBrandPrimaryOpacity35/g' \
  -e 's/[[:<:]]primaryNormalOpacity52[[:>:]]/surfaceBrandPrimaryOpacity52/g' \
  -e 's/[[:<:]]primaryNormalOpacity61[[:>:]]/surfaceBrandPrimaryOpacity61/g' \
  -e 's/[[:<:]]primaryNormalOpacity74[[:>:]]/surfaceBrandPrimaryOpacity74/g' \
  -e 's/[[:<:]]primaryNormalOpacity88[[:>:]]/surfaceBrandPrimaryOpacity88/g' \
  -e 's/[[:<:]]labelNormalOpacity5[[:>:]]/foregroundNeutralPrimaryOpacity5/g' \
  -e 's/[[:<:]]labelNormalOpacity8[[:>:]]/foregroundNeutralPrimaryOpacity8/g' \
  -e 's/[[:<:]]labelNormalOpacity12[[:>:]]/foregroundNeutralPrimaryOpacity12/g' \
  -e 's/[[:<:]]labelStrongOpacity5[[:>:]]/foregroundNeutralStrongOpacity5/g' \
  -e 's/[[:<:]]labelStrongOpacity8[[:>:]]/foregroundNeutralStrongOpacity8/g' \
  -e 's/[[:<:]]labelStrongOpacity12[[:>:]]/foregroundNeutralStrongOpacity12/g' \
  -e 's/[[:<:]]labelStrongOpacity35[[:>:]]/foregroundNeutralStrongOpacity35/g' \
  -e 's/[[:<:]]labelStrongOpacity52[[:>:]]/foregroundNeutralStrongOpacity52/g' \
  -e 's/[[:<:]]labelStrongOpacity74[[:>:]]/foregroundNeutralStrongOpacity74/g' \
  -e 's/[[:<:]]labelStrongOpacity88[[:>:]]/foregroundNeutralStrongOpacity88/g' \
  -e 's/[[:<:]]labelAlternativeOpacity5[[:>:]]/foregroundNeutralTertiaryOpacity5/g' \
  -e 's/[[:<:]]labelAlternativeOpacity8[[:>:]]/foregroundNeutralTertiaryOpacity8/g' \
  -e 's/[[:<:]]labelAlternativeOpacity12[[:>:]]/foregroundNeutralTertiaryOpacity12/g' \
  -e 's/[[:<:]]labelAlternativeOpacity35[[:>:]]/foregroundNeutralTertiaryOpacity35/g' \
  -e 's/[[:<:]]labelAlternativeOpacity52[[:>:]]/foregroundNeutralTertiaryOpacity52/g' \
  -e 's/[[:<:]]labelAlternativeOpacity74[[:>:]]/foregroundNeutralTertiaryOpacity74/g' \
  -e 's/[[:<:]]labelAlternativeOpacity88[[:>:]]/foregroundNeutralTertiaryOpacity88/g' \
  -e 's/[[:<:]]backgroundNormalNormalOpacity0[[:>:]]/backgroundNeutralPrimaryOpacity0/g' \
  -e 's/[[:<:]]backgroundNormalNormalOpacity61[[:>:]]/backgroundNeutralPrimaryOpacity61/g' \
  -e 's/[[:<:]]backgroundElevatedNormalOpacity0[[:>:]]/surfaceElevatedPrimaryOpacity0/g' \
  -e 's/[[:<:]]backgroundElevatedNormalOpacity12[[:>:]]/surfaceElevatedPrimaryOpacity12/g' \
  -e 's/[[:<:]]backgroundElevatedNormalOpacity88[[:>:]]/surfaceElevatedPrimaryOpacity88/g' \
  -e 's/[[:<:]]backgroundElevatedNormalOpacity97[[:>:]]/surfaceElevatedPrimaryOpacity97/g' \
  -e 's/[[:<:]]lineNormalOpacity28[[:>:]]/lineNeutralPrimaryOpaqueOpacity28/g' \
  -e 's/[[:<:]]lineNormalOpacity61[[:>:]]/lineNeutralPrimaryOpaqueOpacity61/g' \
  -e 's/[[:<:]]lineAlternativeOpacity52[[:>:]]/lineNeutralTertiaryOpaqueOpacity52/g' \
  -e 's/[[:<:]]statusPositiveOpacity5[[:>:]]/foregroundPositivePrimaryOpacity5/g' \
  -e 's/[[:<:]]statusPositiveOpacity8[[:>:]]/foregroundPositivePrimaryOpacity8/g' \
  -e 's/[[:<:]]statusPositiveOpacity12[[:>:]]/foregroundPositivePrimaryOpacity12/g' \
  -e 's/[[:<:]]statusPositiveOpacity16[[:>:]]/foregroundPositivePrimaryOpacity16/g' \
  -e 's/[[:<:]]statusPositiveOpacity43[[:>:]]/foregroundPositivePrimaryOpacity43/g' \
  -e 's/[[:<:]]statusNegativeOpacity8[[:>:]]/foregroundNegativePrimaryOpacity8/g' \
  -e 's/[[:<:]]accentCyanOpacity8[[:>:]]/surfaceAccentCyanOpaqueOpacity8/g' \
  -e 's/[[:<:]]accentCyanOpacity35[[:>:]]/surfaceAccentCyanOpaqueOpacity35/g' \
  -e 's/[[:<:]]accentLightBlueOpacity5[[:>:]]/surfaceAccentLightBlueOpaqueOpacity5/g' \
  -e 's/[[:<:]]accentLightBlueOpacity8[[:>:]]/surfaceAccentLightBlueOpaqueOpacity8/g' \
  -e 's/[[:<:]]accentLightBlueOpacity12[[:>:]]/surfaceAccentLightBlueOpaqueOpacity12/g' \
  -e 's/[[:<:]]accentVioletOpacity5[[:>:]]/surfaceAccentVioletOpaqueOpacity5/g' \
  -e 's/[[:<:]]accentVioletOpacity8[[:>:]]/surfaceAccentVioletOpaqueOpacity8/g' \
  -e 's/[[:<:]]accentVioletOpacity12[[:>:]]/surfaceAccentVioletOpaqueOpacity12/g' \
  -e 's/[[:<:]]accentPinkOpacity8[[:>:]]/surfaceAccentPinkOpaqueOpacity8/g' \
  -e 's/[[:<:]]accentLimeOpacity8[[:>:]]/surfaceAccentLimeOpaqueOpacity8/g' \
  -e 's/[[:<:]]primary_normal[[:>:]]/surface_brand_primary/g' \
  -e 's/[[:<:]]primary_strong[[:>:]]/surface_brand_strong/g' \
  -e 's/[[:<:]]primary_heavy[[:>:]]/surface_brand_heavy/g' \
  -e 's/[[:<:]]label_normal[[:>:]]/foreground_neutral_primary/g' \
  -e 's/[[:<:]]label_strong[[:>:]]/foreground_neutral_strong/g' \
  -e 's/[[:<:]]label_neutral[[:>:]]/foreground_neutral_secondary/g' \
  -e 's/[[:<:]]label_alternative[[:>:]]/foreground_neutral_tertiary/g' \
  -e 's/[[:<:]]label_assistive[[:>:]]/foreground_neutral_quaternary/g' \
  -e 's/[[:<:]]label_disable[[:>:]]/foreground_disable_primary/g' \
  -e 's/[[:<:]]background_normal_normal[[:>:]]/background_neutral_primary/g' \
  -e 's/[[:<:]]background_normal_alternative[[:>:]]/background_neutral_secondary/g' \
  -e 's/[[:<:]]background_elevated_normal[[:>:]]/surface_elevated_primary/g' \
  -e 's/[[:<:]]background_elevated_alternative[[:>:]]/surface_elevated_secondary/g' \
  -e 's/[[:<:]]background_transparent_normal[[:>:]]/effect_transparent_primary/g' \
  -e 's/[[:<:]]background_transparent_alternative[[:>:]]/effect_transparent_secondary/g' \
  -e 's/[[:<:]]background_status_negative[[:>:]]/surface_negative_primary/g' \
  -e 's/[[:<:]]background_status_cautionary[[:>:]]/surface_cautionary_primary/g' \
  -e 's/[[:<:]]background_status_positive[[:>:]]/surface_positive_primary/g' \
  -e 's/[[:<:]]interaction_inactive[[:>:]]/foreground_inactive_primary/g' \
  -e 's/[[:<:]]interaction_disable[[:>:]]/surface_disable_primary/g' \
  -e 's/[[:<:]]line_normal_normal[[:>:]]/line_neutral_primary/g' \
  -e 's/[[:<:]]line_normal_neutral[[:>:]]/line_neutral_secondary/g' \
  -e 's/[[:<:]]line_normal_alternative[[:>:]]/line_neutral_tertiary/g' \
  -e 's/[[:<:]]line_solid_normal[[:>:]]/line_neutral_primary_opaque/g' \
  -e 's/[[:<:]]line_solid_neutral[[:>:]]/line_neutral_secondary_opaque/g' \
  -e 's/[[:<:]]line_solid_alternative[[:>:]]/line_neutral_tertiary_opaque/g' \
  -e 's/[[:<:]]line_status_cautionary_normal[[:>:]]/line_cautionary_primary/g' \
  -e 's/[[:<:]]line_status_positive_normal[[:>:]]/line_positive_primary/g' \
  -e 's/[[:<:]]status_positive[[:>:]]/foreground_positive_primary/g' \
  -e 's/[[:<:]]status_negative[[:>:]]/foreground_negative_primary/g' \
  -e 's/[[:<:]]status_cautionary[[:>:]]/foreground_cautionary_primary/g' \
  -e 's/[[:<:]]accent_background_lime[[:>:]]/surface_accent_lime_opaque/g' \
  -e 's/[[:<:]]accent_background_cyan[[:>:]]/surface_accent_cyan_opaque/g' \
  -e 's/[[:<:]]accent_background_lightblue[[:>:]]/surface_accent_lightblue_opaque/g' \
  -e 's/[[:<:]]accent_background_violet[[:>:]]/surface_accent_violet_opaque/g' \
  -e 's/[[:<:]]accent_background_purple[[:>:]]/surface_accent_purple_opaque/g' \
  -e 's/[[:<:]]accent_background_pink[[:>:]]/surface_accent_pink_opaque/g' \
  -e 's/[[:<:]]accent_foreground_red[[:>:]]/foreground_negative_strong/g' \
  -e 's/[[:<:]]accent_foreground_orange[[:>:]]/foreground_cautionary_primary/g' \
  -e 's/[[:<:]]accent_foreground_lime[[:>:]]/foreground_accent_lime/g' \
  -e 's/[[:<:]]accent_foreground_green[[:>:]]/foreground_positive_primary/g' \
  -e 's/[[:<:]]accent_foreground_cyan[[:>:]]/foreground_accent_cyan/g' \
  -e 's/[[:<:]]accent_foreground_lightblue[[:>:]]/foreground_accent_lightblue/g' \
  -e 's/[[:<:]]accent_foreground_blue[[:>:]]/foreground_brand_primary/g' \
  -e 's/[[:<:]]accent_foreground_violet[[:>:]]/foreground_accent_violet/g' \
  -e 's/[[:<:]]accent_foreground_purple[[:>:]]/foreground_accent_purple/g' \
  -e 's/[[:<:]]accent_foreground_pink[[:>:]]/foreground_accent_pink/g' \
  -e 's/[[:<:]]inverse_primary[[:>:]]/foreground_brand_inverse/g' \
  -e 's/[[:<:]]inverse_label[[:>:]]/foreground_neutral_inverse/g' \
  -e 's/[[:<:]]inverse_background[[:>:]]/surface_neutral_inverse/g' \
  -e 's/[[:<:]]fill_normal[[:>:]]/surface_neutral_secondary/g' \
  -e 's/[[:<:]]fill_strong[[:>:]]/surface_neutral_strong/g' \
  -e 's/[[:<:]]fill_alternative[[:>:]]/surface_neutral_tertiary/g' \
  -e 's/[[:<:]]material_dimmer[[:>:]]/effect_dimmer_primary/g' \
  -e 's/[[:<:]]primary_normal_opacity5[[:>:]]/surface_brand_primary_opacity5/g' \
  -e 's/[[:<:]]primary_normal_opacity8[[:>:]]/surface_brand_primary_opacity8/g' \
  -e 's/[[:<:]]primary_normal_opacity12[[:>:]]/surface_brand_primary_opacity12/g' \
  -e 's/[[:<:]]primary_normal_opacity22[[:>:]]/surface_brand_primary_opacity22/g' \
  -e 's/[[:<:]]primary_normal_opacity28[[:>:]]/surface_brand_primary_opacity28/g' \
  -e 's/[[:<:]]primary_normal_opacity35[[:>:]]/surface_brand_primary_opacity35/g' \
  -e 's/[[:<:]]primary_normal_opacity52[[:>:]]/surface_brand_primary_opacity52/g' \
  -e 's/[[:<:]]primary_normal_opacity61[[:>:]]/surface_brand_primary_opacity61/g' \
  -e 's/[[:<:]]primary_normal_opacity74[[:>:]]/surface_brand_primary_opacity74/g' \
  -e 's/[[:<:]]primary_normal_opacity88[[:>:]]/surface_brand_primary_opacity88/g' \
  -e 's/[[:<:]]label_normal_opacity5[[:>:]]/foreground_neutral_primary_opacity5/g' \
  -e 's/[[:<:]]label_normal_opacity8[[:>:]]/foreground_neutral_primary_opacity8/g' \
  -e 's/[[:<:]]label_normal_opacity12[[:>:]]/foreground_neutral_primary_opacity12/g' \
  -e 's/[[:<:]]label_strong_opacity5[[:>:]]/foreground_neutral_strong_opacity5/g' \
  -e 's/[[:<:]]label_strong_opacity8[[:>:]]/foreground_neutral_strong_opacity8/g' \
  -e 's/[[:<:]]label_strong_opacity12[[:>:]]/foreground_neutral_strong_opacity12/g' \
  -e 's/[[:<:]]label_strong_opacity35[[:>:]]/foreground_neutral_strong_opacity35/g' \
  -e 's/[[:<:]]label_strong_opacity52[[:>:]]/foreground_neutral_strong_opacity52/g' \
  -e 's/[[:<:]]label_strong_opacity74[[:>:]]/foreground_neutral_strong_opacity74/g' \
  -e 's/[[:<:]]label_strong_opacity88[[:>:]]/foreground_neutral_strong_opacity88/g' \
  -e 's/[[:<:]]label_alternative_opacity5[[:>:]]/foreground_neutral_tertiary_opacity5/g' \
  -e 's/[[:<:]]label_alternative_opacity8[[:>:]]/foreground_neutral_tertiary_opacity8/g' \
  -e 's/[[:<:]]label_alternative_opacity12[[:>:]]/foreground_neutral_tertiary_opacity12/g' \
  -e 's/[[:<:]]label_alternative_opacity35[[:>:]]/foreground_neutral_tertiary_opacity35/g' \
  -e 's/[[:<:]]label_alternative_opacity52[[:>:]]/foreground_neutral_tertiary_opacity52/g' \
  -e 's/[[:<:]]label_alternative_opacity74[[:>:]]/foreground_neutral_tertiary_opacity74/g' \
  -e 's/[[:<:]]label_alternative_opacity88[[:>:]]/foreground_neutral_tertiary_opacity88/g' \
  -e 's/[[:<:]]background_normal_normal_opacity0[[:>:]]/background_neutral_primary_opacity0/g' \
  -e 's/[[:<:]]background_normal_normal_opacity61[[:>:]]/background_neutral_primary_opacity61/g' \
  -e 's/[[:<:]]background_elevated_normal_opacity0[[:>:]]/surface_elevated_primary_opacity0/g' \
  -e 's/[[:<:]]background_elevated_normal_opacity12[[:>:]]/surface_elevated_primary_opacity12/g' \
  -e 's/[[:<:]]background_elevated_normal_opacity88[[:>:]]/surface_elevated_primary_opacity88/g' \
  -e 's/[[:<:]]background_elevated_normal_opacity97[[:>:]]/surface_elevated_primary_opacity97/g' \
  -e 's/[[:<:]]line_normal_opacity28[[:>:]]/line_neutral_primary_opaque_opacity28/g' \
  -e 's/[[:<:]]line_normal_opacity61[[:>:]]/line_neutral_primary_opaque_opacity61/g' \
  -e 's/[[:<:]]line_alternative_opacity52[[:>:]]/line_neutral_tertiary_opaque_opacity52/g' \
  -e 's/[[:<:]]status_positive_opacity5[[:>:]]/foreground_positive_primary_opacity5/g' \
  -e 's/[[:<:]]status_positive_opacity8[[:>:]]/foreground_positive_primary_opacity8/g' \
  -e 's/[[:<:]]status_positive_opacity12[[:>:]]/foreground_positive_primary_opacity12/g' \
  -e 's/[[:<:]]status_positive_opacity16[[:>:]]/foreground_positive_primary_opacity16/g' \
  -e 's/[[:<:]]status_positive_opacity43[[:>:]]/foreground_positive_primary_opacity43/g' \
  -e 's/[[:<:]]status_negative_opacity8[[:>:]]/foreground_negative_primary_opacity8/g' \
  -e 's/[[:<:]]accent_cyan_opacity8[[:>:]]/surface_accent_cyan_opaque_opacity8/g' \
  -e 's/[[:<:]]accent_cyan_opacity35[[:>:]]/surface_accent_cyan_opaque_opacity35/g' \
  -e 's/[[:<:]]accent_lightblue_opacity5[[:>:]]/surface_accent_lightblue_opaque_opacity5/g' \
  -e 's/[[:<:]]accent_lightblue_opacity8[[:>:]]/surface_accent_lightblue_opaque_opacity8/g' \
  -e 's/[[:<:]]accent_lightblue_opacity12[[:>:]]/surface_accent_lightblue_opaque_opacity12/g' \
  -e 's/[[:<:]]accent_violet_opacity5[[:>:]]/surface_accent_violet_opaque_opacity5/g' \
  -e 's/[[:<:]]accent_violet_opacity8[[:>:]]/surface_accent_violet_opaque_opacity8/g' \
  -e 's/[[:<:]]accent_violet_opacity12[[:>:]]/surface_accent_violet_opaque_opacity12/g' \
  -e 's/[[:<:]]accent_pink_opacity8[[:>:]]/surface_accent_pink_opaque_opacity8/g' \
  -e 's/[[:<:]]accent_lime_opacity8[[:>:]]/surface_accent_lime_opaque_opacity8/g' \
  -e 's/[[:<:]]primary_normal_temp[[:>:]]/surface_brand_primary/g' \
  -e 's/[[:<:]]status_positive_temp[[:>:]]/foreground_positive_primary/g' \
  -e 's/[[:<:]]accentForegroundRedOrange[[:>:]]/foregroundCautionaryPrimary/g' \
  -e 's/[[:<:]]accentBackgroundRedOrange[[:>:]]/foregroundCautionaryPrimary/g' \
  -e 's/[[:<:]]accent_foreground_redorange[[:>:]]/foreground_cautionary_primary/g' \
  -e 's/[[:<:]]accent_background_redorange[[:>:]]/foreground_cautionary_primary/g' \
  {} +
```

---

## 3.0

If you are upgrading from a version older than 3.0, see the [release notes](https://github.com/wanteddev/montage-android/releases).
