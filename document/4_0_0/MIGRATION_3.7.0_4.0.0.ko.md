# 마이그레이션 가이드

[English](MIGRATION_3.7.0_4.0.0.md) | [한국어](MIGRATION_3.7.0_4.0.0.ko.md)

메이저 버전 업그레이드에 필요한 변경 사항을 정리합니다. 최신 버전이 위에 옵니다.

**이 문서는 브레이킹 체인지만 다룹니다.** 새로 생긴 컴포넌트와 파라미터는 [추가된 API](#추가된-api)에 이름만 모아뒀고, 자세한 내용은 [릴리즈 노트](https://github.com/wanteddev/montage-android/releases)에 있습니다.

각 메이저 절의 기준선은 **직전 메이저의 마지막 릴리즈 태그**입니다. 개발 중에 생겼다가 없어진 API는 이전 버전 사용처가 겪을 일이 없으므로 적지 않습니다.

---

## 4.0

**기준선: v3.7.0 → 4.0.0**

브레이킹 체인지가 네 갈래입니다. 위에서 아래 순서로 진행하면 컴파일 에러가 가장 빨리 줄어듭니다.

| 순서 | 절 | 성격 | 하는 일 |
|---|---|---|---|
| 1 | [이름이 바뀐 것](#1-이름이-바뀐-것) | 기계적 치환 | 대부분 `sed`로 끝납니다 |
| 2 | [없어져서 다시 짜야 하는 것](#2-없어져서-다시-짜야-하는-것) | 구조 재작성 | 대체 API로 옮깁니다 |
| 3 | [대응이 없는 것](#3-대응이-없는-것) | 직접 선택 | 사용처가 판단해야 합니다 |
| 4 | [화면이 달라지는 것](#4-화면이-달라지는-것) | 화면 확인 | **컴파일 에러가 나지 않습니다** |

**4번을 건너뛰지 마세요.** API는 그대로인데 값만 바뀐 항목이라 빌드가 통과해도 화면이 달라집니다. 치환이 끝났다고 마이그레이션이 끝난 게 아닙니다.

1번도 전부 `sed`로 끝나지는 않습니다. 컬러 토큰 일부는 이름과 함께 색값까지 바뀌고([값이 함께 바뀌는 토큰](#값이-함께-바뀌는-토큰)), 단어 경계 치환은 Kotlin 문법을 모르기 때문에 같은 이름의 지역 변수나 주석·문자열 속 이름도 함께 바꿉니다. 치환 뒤 diff를 한 번 훑어주세요.

`WantedTextField`·`WantedTextArea`·`WantedSelect`의 라벨 내장 오버로드처럼 **지우지 않고 `@Deprecated(WARNING)`로 남긴 API가 많습니다.** 이 API를 쓰던 곳은 경고만 나고 컴파일되지만, 렌더링은 4.0 스펙을 따릅니다. 경고를 남겨 두어도 [4. 화면이 달라지는 것](#4-화면이-달라지는-것)은 그대로 적용됩니다.

---

## 1. 이름이 바뀐 것

### 1.1 컬러 시맨틱 토큰

`DesignSystemTheme.colors`(`WantedColorScheme`)의 프로퍼티와 XML 컬러 리소스(`R.color.*` / `@color/*`) 이름이 **둘 다** 바뀝니다. `staticWhite`·`staticBlack`·`transparent`만 그대로입니다. 대부분은 이름만 바뀌지만 **몇 건은 색값도 함께 바뀝니다.** [값이 함께 바뀌는 토큰](#값이-함께-바뀌는-토큰)을 꼭 확인해주세요.

#### 새 이름 규칙

```
용도 + 역할 + 변형

용도   foreground  텍스트·아이콘
       background  화면 바탕
       surface     화면 위 요소의 배경(카드·필드·버튼)
       line        테두리·구분선
       effect      딤·투명 레이어

역할   Neutral  Brand  Positive  Cautionary  Negative  Disable  Inactive  Accent{색}

변형   Primary → Secondary → Tertiary → Quaternary  (대비가 낮아지는 순서)
       Strong / Heavy   더 진함
       Subtle           더 옅음
       Inverse          반전 배경 위에서 쓰는 색
       Focus            포커스 링
       Opaque           불투명 버전 (접미사 없는 쪽이 반투명)
```

Kotlin 프로퍼티는 camelCase, XML 리소스는 같은 이름의 snake_case입니다(`foregroundNeutralPrimary` ↔ `foreground_neutral_primary`). 단 **`LightBlue`는 리소스에서 `lightblue`로 붙여 씁니다**(`foregroundAccentLightBlue` ↔ `foreground_accent_lightblue`). 3.x와 같은 규칙입니다.

3.x에서 이름 중간에 붙던 `Solid`가 4.0에서는 맨 뒤 `Opaque`로 갑니다. **`lineSolidNormal` → `lineNeutralPrimaryOpaque`**처럼 자리까지 바뀐다는 점만 주의하면 됩니다.

3.x 이름 중 `backgroundNormalNormal`, `lineNormalNormal`처럼 그룹명이 두 번 붙는 형태도 아래 표대로 옮기면 됩니다.

#### Foreground - 텍스트·아이콘

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | 값 |
|---|---|---|
| `labelNormal` / `label_normal` | `foregroundNeutralPrimary` / `foreground_neutral_primary` | 동일 |
| `labelStrong` / `label_strong` | `foregroundNeutralStrong` / `foreground_neutral_strong` | 동일 |
| `labelNeutral` / `label_neutral` | `foregroundNeutralSecondary` / `foreground_neutral_secondary` | 동일 |
| `labelAlternative` / `label_alternative` | `foregroundNeutralTertiary` / `foreground_neutral_tertiary` | 동일 |
| `labelAssistive` / `label_assistive` | `foregroundNeutralQuaternary` / `foreground_neutral_quaternary` | 동일 |
| `inverseLabel` / `inverse_label` | `foregroundNeutralInverse` / `foreground_neutral_inverse` | **다크 미세 변경** |
| `labelDisable` / `label_disable` | `foregroundDisablePrimary` / `foreground_disable_primary` | 동일 |
| `interactionInactive` / `interaction_inactive` | `foregroundInactivePrimary` / `foreground_inactive_primary` | 동일 |
| `inversePrimary` / `inverse_primary` | `foregroundBrandInverse` / `foreground_brand_inverse` | 동일 |
| `statusPositive` / `status_positive` | `foregroundPositivePrimary` / `foreground_positive_primary` | 동일 |
| `statusCautionary` / `status_cautionary` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | 동일 |
| `statusNegative` / `status_negative` | `foregroundNegativePrimary` / `foreground_negative_primary` | 동일 |
| `accentForegroundBlue` / `accent_foreground_blue` | `foregroundBrandPrimary` / `foreground_brand_primary` | **변경** |
| `accentForegroundGreen` / `accent_foreground_green` | `foregroundPositivePrimary` / `foreground_positive_primary` | **라이트 변경** |
| `accentForegroundOrange` / `accent_foreground_orange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **변경** |
| `accentForegroundRed` / `accent_foreground_red` | `foregroundNegativeStrong` / `foreground_negative_strong` | 동일 |
| `accentForegroundLime` / `accent_foreground_lime` | `foregroundAccentLime` / `foreground_accent_lime` | 동일 |
| `accentForegroundCyan` / `accent_foreground_cyan` | `foregroundAccentCyan` / `foreground_accent_cyan` | 동일 |
| `accentForegroundLightBlue` / `accent_foreground_lightblue` | `foregroundAccentLightBlue` / `foreground_accent_lightblue` | 동일 |
| `accentForegroundViolet` / `accent_foreground_violet` | `foregroundAccentViolet` / `foreground_accent_violet` | 동일 |
| `accentForegroundPurple` / `accent_foreground_purple` | `foregroundAccentPurple` / `foreground_accent_purple` | 동일 |
| `accentForegroundPink` / `accent_foreground_pink` | `foregroundAccentPink` / `foreground_accent_pink` | 동일 |
| `accentForegroundRedOrange` / `accent_foreground_redorange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **변경** |

> `accentForeground{색}` 중 Blue·Green·Orange·Red·RedOrange는 **역할이 `Accent{색}`에서 `Brand`·`Positive`·`Cautionary`·`Negative`로 바뀌었습니다(RedOrange는 Orange와 함께 `Cautionary`).** 이름만 옮기지 말고 그 자리가 정말 그 역할인지 확인해주세요.
>
> 3.x accent 계열의 다크 값은 이미 4.0과 같아서, Lime·Cyan·LightBlue·Violet·Purple·Pink·Red는 **값이 그대로**입니다.

#### Background · Surface - 화면 바탕과 요소 배경

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | 값 |
|---|---|---|
| `backgroundNormalNormal` / `background_normal_normal` | `backgroundNeutralPrimary` / `background_neutral_primary` | 동일 |
| `backgroundNormalAlternative` / `background_normal_alternative` | `backgroundNeutralSecondary` / `background_neutral_secondary` | 동일 |
| `backgroundElevatedNormal` / `background_elevated_normal` | `surfaceElevatedPrimary` / `surface_elevated_primary` | 동일 |
| `backgroundElevatedAlternative` / `background_elevated_alternative` | `surfaceElevatedSecondary` / `surface_elevated_secondary` | 동일 |
| `fillNormal` / `fill_normal` | `surfaceNeutralSecondary` / `surface_neutral_secondary` | 동일 |
| `fillAlternative` / `fill_alternative` | `surfaceNeutralTertiary` / `surface_neutral_tertiary` | 동일 |
| `fillStrong` / `fill_strong` | `surfaceNeutralStrong` / `surface_neutral_strong` | 동일 |
| `backgroundStatusPositive` / `background_status_positive` | `surfacePositivePrimary` / `surface_positive_primary` | 동일 |
| `backgroundStatusCautionary` / `background_status_cautionary` | `surfaceCautionaryPrimary` / `surface_cautionary_primary` | 동일 |
| `backgroundStatusNegative` / `background_status_negative` | `surfaceNegativePrimary` / `surface_negative_primary` | 동일 |
| `inverseBackground` / `inverse_background` | `surfaceNeutralInverse` / `surface_neutral_inverse` | 동일 |
| `primaryNormal` / `primary_normal` | `surfaceBrandPrimary` / `surface_brand_primary` (채움) · `foregroundBrandPrimary` / `foreground_brand_primary` (텍스트·아이콘) | 동일 |
| `primaryStrong` / `primary_strong` | `surfaceBrandStrong` / `surface_brand_strong` | 동일 |
| `primaryHeavy` / `primary_heavy` | `surfaceBrandHeavy` / `surface_brand_heavy` | 동일 |
| `interactionDisable` / `interaction_disable` | `surfaceDisablePrimary` / `surface_disable_primary` | 동일 |
| `accentBackgroundLime` / `accent_background_lime` | `surfaceAccentLimeOpaque` / `surface_accent_lime_opaque` | 동일 |
| `accentBackgroundCyan` / `accent_background_cyan` | `surfaceAccentCyanOpaque` / `surface_accent_cyan_opaque` | 동일 |
| `accentBackgroundLightBlue` / `accent_background_lightblue` | `surfaceAccentLightBlueOpaque` / `surface_accent_lightblue_opaque` | 동일 |
| `accentBackgroundViolet` / `accent_background_violet` | `surfaceAccentVioletOpaque` / `surface_accent_violet_opaque` | 동일 |
| `accentBackgroundPurple` / `accent_background_purple` | `surfaceAccentPurpleOpaque` / `surface_accent_purple_opaque` | 동일 |
| `accentBackgroundPink` / `accent_background_pink` | `surfaceAccentPinkOpaque` / `surface_accent_pink_opaque` | 동일 |
| `accentBackgroundRedOrange` / `accent_background_redorange` | `foregroundCautionaryPrimary` / `foreground_cautionary_primary` | **변경** |

> **`primaryNormal`은 쓰임새에 따라 둘로 갈라집니다.** 버튼 채움처럼 면을 칠하던 자리는 `surfaceBrandPrimary`, 텍스트·아이콘·커서·인디케이터처럼 전경을 칠하던 자리는 `foregroundBrandPrimary`입니다. 두 토큰은 지금 라이트·다크 값이 같아서 잘못 골라도 화면은 같지만, 이후 토큰 값이 갈라지면 그때 어긋납니다. 일괄 치환은 `surfaceBrandPrimary`로 보내고 전경 자리를 골라 고쳐주세요.
>
> `background`는 화면 바탕(Primary·Secondary) 두 종만 남았습니다. 나머지 배경 토큰은 `surface`로, 투명 레이어인 `backgroundTransparent*`는 `effect`로 갈라졌습니다.
>
> `accentBackgroundRedOrange`는 배경 토큰이지만 Semantic Token 가이드의 대응이 **전경 토큰 `foregroundCautionaryPrimary`**입니다. 텍스트·아이콘 색으로 쓰던 자리는 그대로 옮기면 되고, 면을 칠하던 자리라면 용도에 맞는 `surface` 토큰(`surfaceCautionaryPrimary` 등)을 골라주세요.
>
> `accentBackground{색}`은 기본이 **불투명(`Opaque`)** 대응입니다. 반투명 위에 겹쳐 쓰던 자리라면 접미사 없는 `surfaceAccent{색}`(8% 알파)을 쓰세요.
>
> `fillNormal`(→ `surfaceNeutralSecondary`)과 `lineNormalAlternative`(→ `lineNeutralTertiary`)는 라이트·다크 값이 같습니다. 3.x에서 채움에 `lineNormalAlternative`를, 테두리에 `fillNormal`을 쓰던 자리가 있다면 이름대로 옮기지 말고 **용도에 맞는 쪽**을 고르세요(채움 → `surface`, 테두리 → `line`). 화면은 그대로입니다.

#### Line - 테두리·구분선

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | 값 |
|---|---|---|
| `lineNormalNormal` / `line_normal_normal` | `lineNeutralPrimary` / `line_neutral_primary` | **다크 미세 변경** |
| `lineNormalNeutral` / `line_normal_neutral` | `lineNeutralSecondary` / `line_neutral_secondary` | 동일 |
| `lineNormalAlternative` / `line_normal_alternative` | `lineNeutralTertiary` / `line_neutral_tertiary` | 동일 |
| `lineSolidNormal` / `line_solid_normal` | `lineNeutralPrimaryOpaque` / `line_neutral_primary_opaque` | 동일 |
| `lineSolidNeutral` / `line_solid_neutral` | `lineNeutralSecondaryOpaque` / `line_neutral_secondary_opaque` | 동일 |
| `lineSolidAlternative` / `line_solid_alternative` | `lineNeutralTertiaryOpaque` / `line_neutral_tertiary_opaque` | 동일 |
| `lineStatusPositiveNormal` / `line_status_positive_normal` | `linePositivePrimary` / `line_positive_primary` | 동일 |
| `lineStatusCautionaryNormal` / `line_status_cautionary_normal` | `lineCautionaryPrimary` / `line_cautionary_primary` | 동일 |

> 4.0의 `lineBrand*`·`lineNegative*`는 3.x에 대응 토큰이 없는 [추가된 API](#추가된-api)입니다.

#### Effect - 딤·투명 레이어

| 3.x (`colors.` / `R.color.`) | 4.0 (`colors.` / `R.color.`) | 값 |
|---|---|---|
| `materialDimmer` / `material_dimmer` | `effectDimmerPrimary` / `effect_dimmer_primary` | **다크 변경** |
| `backgroundTransparentNormal` / `background_transparent_normal` | `effectTransparentPrimary` / `effect_transparent_primary` | 동일 |
| `backgroundTransparentAlternative` / `background_transparent_alternative` | `effectTransparentSecondary` / `effect_transparent_secondary` | 동일 |

#### XML 전용 임시 토큰

Kotlin 프로퍼티 없이 XML에만 있던 두 리소스가 제거됐습니다. 값이 같은 토큰으로 옮기면 됩니다.

| 3.x | 4.0 | 값 |
|---|---|---|
| `@color/primary_normal_temp` | `@color/surface_brand_primary` | 동일 |
| `@color/status_positive_temp` | `@color/foreground_positive_primary` | 동일 |

#### 값이 함께 바뀌는 토큰

이름만 옮기면 **색이 달라집니다.** 아래 자리는 치환 후 눈으로 확인해주세요.

| 3.x → 4.0 | 라이트 | 다크 |
|---|---|---|
| `accentForegroundBlue` → `foregroundBrandPrimary` | `blue_45` `#005EEB` → `blue_50` `#0066FF` | `blue_65` `#4F95FF` → `blue_60` `#3385FF` |
| `accentForegroundGreen` → `foregroundPositivePrimary` | `green_40` `#009632` → `green_50` `#00BF40` | 동일 (`green_60`) |
| `accentForegroundOrange` → `foregroundCautionaryPrimary` | `orange_39` `#D17600` → `orange_50` `#FF9200` | `orange_50` `#FF9200` → `orange_60` `#FFA938` |
| `materialDimmer` → `effectDimmerPrimary` | 동일 (`#85171719`, 52%) | `#74171719`(약 45%) → `#BD171719`(74%) **딤이 확실히 진해짐** |
| `lineNormalNormal` → `lineNeutralPrimary` | 동일 | `cool_neutral_50` 32% → 35% |
| `inverseLabel` → `foregroundNeutralInverse` | 동일 | `neutral_10` `#171717` → `cool_neutral_10` `#171719` |
| `accentForegroundRedOrange` → `foregroundCautionaryPrimary` | `redorange_48` `#F55A00` → `orange_50` `#FF9200` | `redorange_60` `#FF7B2E` → `orange_60` `#FFA938` |
| `accentBackgroundRedOrange` → `foregroundCautionaryPrimary` | `redorange_50` `#FF5E00` → `orange_50` `#FF9200` | `redorange_60` `#FF7B2E` → `orange_60` `#FFA938` |

`accentForegroundBlue`·`Orange`와 RedOrange 두 토큰은 **라이트·다크 모두**, `accentForegroundGreen`은 라이트에서 확실히 달라집니다. `materialDimmer`는 3.x 다크 값의 알파가 의도(74%)와 달리 `0x74`(약 45%)였던 것을 바로잡은 것이라 **다크 모드 딤이 눈에 띄게 진해집니다.** `lineNormalNormal`·`inverseLabel`은 1~3% 수준이라 사실상 티가 나지 않습니다.

#### 표에 없는 토큰

프리미티브 컬러 리소스(`cool_neutral_*`, `blue_*`, `redorange_*` …)는 이름·값 모두 그대로입니다. 4.0에서 opacity 변형 프리미티브가 추가됐을 뿐 삭제·변경은 없습니다.

4.0에서 새로 생긴 토큰(`lineBrandFocus`, `lineNegativeFocus`, `surfaceBrandSubtle`, `surfaceNegativeStrong`, `surfaceAccent*` 등)은 3.x에 대응이 없으므로 이 표에 없습니다. [추가된 API](#추가된-api)를 보세요.

---

### 1.2 투명도 파생 토큰

`DesignSystemTheme.colorsOpacity`(`WantedColorOpacityScheme`)와 대응 XML 리소스(`*_opacityNN`)도 기준 토큰 이름을 따라 바뀝니다. 접두어만 바꾸면 되고 뒤의 `OpacityNN`은 그대로입니다.

| 3.x 접두어 (`colorsOpacity.` / `R.color.`) | 4.0 접두어 | 있는 단계 |
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

> `lineNormalOpacity`·`lineAlternativeOpacity`는 이름과 달리 **불투명 선(`lineSolid*`) 색**에 알파를 건 토큰이라 `…OpaqueOpacity`로 갑니다. `accent*Opacity`도 불투명 accent 배경 기준이라 `surfaceAccent{색}OpaqueOpacity`입니다.

**값도 바뀝니다.** 3.x 파생 토큰 다수가 기준 토큰과 다른 RGB(구 팔레트 값)를 쓰고 있었는데, 4.0은 기준 시맨틱 토큰의 RGB에 알파만 거는 형태로 바로잡았습니다. 알파는 그대로입니다. [4.1의 투명도 파생 토큰](#투명도-파생-토큰)을 확인해주세요.

---

### 1.3 XML 스타일

라이브러리 `styles.xml`의 텍스트 기본색 참조가 새 이름으로 바뀌었습니다. 값은 같습니다. 앱이 이 스타일을 상속해 `android:textColor`를 `@color/label_alternative`로 다시 지정했다면 함께 바꿔주세요.

| 스타일 | 3.x | 4.0 |
|---|---|---|
| `WantedAppTheme.RadioButtonStyle` · `WantedTextViewFontStyle` · `WantedButtonFontStyle` · `WantedEditTextFontStyle` | `@color/label_alternative` | `@color/foreground_neutral_tertiary` |

라이브러리 컴포넌트가 기본 인자로 넘기던 컬러 리소스도 새 이름으로 바뀌었습니다(`WantedComposeShimmer(colorRes)`, `WantedComposeShimmerLinear(colorRes)` 등). 호출부에서 3.x 리소스 이름(`R.color.label_normal` 등)을 넘기던 자리는 위 치환으로 함께 정리됩니다.

---

### 1.4 일괄 치환

3.x 토큰 이름이 4.0에 하나도 남지 않고, 새 이름 중 옛 이름과 겹치는 것도 없습니다. 그래서 **단어 경계로 끊어 치환하면** 연쇄 치환이나 엉뚱한 매칭이 생기지 않습니다.

**macOS 기본 `sed`(BSD)는 `\b`를 모릅니다.** `\b`를 쓰면 에러 없이 **아무것도 바꾸지 않습니다.** BSD에서는 `[[:<:]]` · `[[:>:]]`로 경계를 겁니다(GNU sed라면 `\b`).

```bash
# 동작 확인 - BSD sed
echo 'colors.labelNormal' | sed -E 's/labelNormal\b/X/'                 # colors.labelNormal  (안 바뀜)
echo 'colors.labelNormal' | sed -E 's/[[:<:]]labelNormal[[:>:]]/X/'     # colors.X
```

경계 문자에 `_`가 포함되므로 접두어가 같은 이름은 안전하게 갈립니다.

- `label_normal`은 `label_normal_opacity5`를 건드리지 않습니다.
- `labelNormal`은 `labelNormalOpacity12`를 건드리지 않습니다.
- 앱에서 만든 `job_detail_fill_alternative` 같은 리소스 이름 안의 `fill_alternative`도 걸리지 않습니다.

```bash
# 확인 먼저
grep -rnwE "labelNormal|label_normal" --include="*.kt" --include="*.xml" .

# 치환 - Kotlin(colors.* / colorsOpacity.*) + XML·R.color 리소스 이름
find . \( -name "*.kt" -o -name "*.xml" \) -not -path "*/build/*" -exec sed -i '' -E \
  -e 's/[[:<:]]labelNormal[[:>:]]/foregroundNeutralPrimary/g' \
  -e 's/[[:<:]]label_normal[[:>:]]/foreground_neutral_primary/g' \
  -e 's/[[:<:]]backgroundNormalNormal[[:>:]]/backgroundNeutralPrimary/g' \
  -e 's/[[:<:]]background_normal_normal[[:>:]]/background_neutral_primary/g' \
  {} +
```

전체 규칙은 [부록: 컬러 토큰 치환 규칙 전체](#부록-컬러-토큰-치환-규칙-전체)에 있습니다.

---

### 1.5 컴포넌트 API 리네임

#### ActionArea

버튼 역할 이름이 `positive`/`negative`/`neutral`에서 **`main`/`alternative`/`sub`**로 바뀌었습니다. 세 오버로드 모두 해당합니다.

| 3.x | 4.0 |
|---|---|
| `positive` · `isEnablePositive` · `onClickPositive` | `main` · `isEnableMain` · `onClickMain` |
| `negative` · `isEnableNegative` · `onClickNegative` | `alternative` · `isEnableAlternative` · `onClickAlternative` |
| `neutral` · `isEnableNeutral` · `onClickNeutral` | `sub` · `isEnableSub` · `onClickSub` |
| `gradationColor` | `backgroundColor` (기본값도 바뀜, [4.1](#actionarea-1) 참고) |
| `WantedActionAreaDefault.positiveButtonDefault` | `mainButtonDefault` |
| `WantedActionAreaDefault.negativeButtonDefault` | `alternativeButtonDefault` |
| `WantedActionAreaDefault.neutralButtonDefault` | `subButtonDefault` |

`WantedActionAreaDefaults.getDefault(...)`의 인자 이름도 같은 규칙으로 바뀝니다.

```kotlin
// 3.x
WantedActionArea(
    type = ActionAreaType.Strong,
    positive = "확인",
    onClickPositive = { … },
    negative = "취소",
    onClickNegative = { … },
    gradationColor = DesignSystemTheme.colors.backgroundNormalNormal,
)

// 4.0
WantedActionArea(
    type = ActionAreaType.Strong,
    main = "확인",
    onClickMain = { … },
    alternative = "취소",
    onClickAlternative = { … },
    backgroundColor = DesignSystemTheme.colors.backgroundNeutralPrimary,
)
```

`main`/`alternative`/`sub`는 위치가 아니라 **역할** 이름입니다. 3.x의 `negative`는 "부정"이 아니라 "대체 액션"이었으므로 기계적으로 `alternative`로 옮기면 됩니다.

`WantedActionArea(main: String, …)` 오버로드는 `modifier`가 맨 뒤에서 `type`·`main` 다음 세 번째 자리로 옮겨졌습니다. 이름 있는 인자로 넘겼다면 영향이 없습니다.

`onClickPositive` 같은 이름은 FallbackView·Popup 등 다른 컴포넌트에도 있습니다. **`WantedActionArea(` 호출부 안에서만** 바꿔주세요.

#### Chip

`leftIcon`/`rightIcon`이 `leadingContent`/`trailingContent`로 바뀌었습니다. 세 오버로드 모두 해당하고, 타입(`Int?` 또는 `@Composable (() -> Unit)?`)은 그대로입니다.

| 3.x | 4.0 |
|---|---|
| `WantedChip(leftIcon = …)` | `WantedChip(leadingContent = …)` |
| `WantedChip(rightIcon = …)` | `WantedChip(trailingContent = …)` |

#### TextButton

`WantedTextButton`이 `WantedButton`과 공유하던 `ButtonType`·`ButtonSize`·`WantedButtonDefault` 대신 **Text Button 전용 타입**을 받습니다. Compose 함수만 해당하며, XML용 `WantedTextButton` View 클래스는 그대로 `ButtonType`/`ButtonSize`를 씁니다.

| 파라미터 | 3.x 타입 | 4.0 타입 |
|---|---|---|
| `color` | `ButtonType` (`PRIMARY` / `ASSISTIVE`) | `WantedTextButtonColor` (`PRIMARY` / `ASSISTIVE`) |
| `size` | `ButtonSize` (`MEDIUM` / `SMALL` / `LARGE`) | `WantedTextButtonSize` (`MEDIUM` 기본 / `SMALL` / `LARGE`는 레거시) |
| `buttonDefault` | `WantedButtonDefault` | `WantedTextButtonDefault` |

```kotlin
// 3.x
WantedTextButton(text = "더보기", color = ButtonType.ASSISTIVE, size = ButtonSize.SMALL, onClick = { … })

// 4.0
WantedTextButton(text = "더보기", color = WantedTextButtonColor.ASSISTIVE, size = WantedTextButtonSize.SMALL, onClick = { … })
```

`buttonDefault`로 스타일을 덮던 자리는 `WantedTextButtonDefaults.getDefault(color, size, enabled, contentColor = …, textStyle = …)`로 옮깁니다. `WantedTextButtonDefault`에서는 아이콘 색 프로퍼티가 `leftIconTintColor`/`rightIconTintColor` → `leadingIconTintColor`/`trailingIconTintColor`입니다(공용 `WantedButtonDefault`는 left/right 그대로). `enabled = false`이면 넘긴 `contentColor`·아이콘 색보다 `foregroundDisablePrimary`가 우선합니다.

Figma Text Button 스펙에 있는 크기는 `SMALL`/`MEDIUM` 둘뿐입니다. `LARGE`는 `ButtonVariant.TEXT` 경유 호출을 유지하려고 남긴 값이니 새로 쓰지 마세요.

`WantedButton(variant = ButtonVariant.TEXT)`는 `@Deprecated`(5.0 제거 예정)입니다. 컴파일은 되고 내부에서 `WantedTextButton`으로 넘겨지지만, 경고를 없애려면 `WantedTextButton`을 직접 호출하세요. `ButtonType.NEGATIVE`를 `TEXT`로 넘기면 `PRIMARY` 색으로 그려집니다.

#### TextField · AutoCompleteTextField

`right*`이 `trailing*`으로 바뀌고, 버튼 스타일 파라미터는 없어졌습니다. 문자열·`TextFieldValue` 두 오버로드, `WantedAutoCompleteTextField` 두 오버로드 모두 같습니다.

| 3.x | 4.0 |
|---|---|
| `rightButton` | `trailingButton` |
| `rightButtonEnabled` | `trailingButtonEnabled` |
| `onClickRightButton` | `onClickTrailingButton` |
| `rightButtonVariant: WantedTextFieldDefaults.RightVariant` | **제거** ([3.1](#31-textfield-우측-버튼-variant)) |

```kotlin
// 3.x
WantedTextField(
    text = code,
    rightButton = "인증",
    rightButtonVariant = WantedTextFieldDefaults.RightVariant.Assistive,
    rightButtonEnabled = isValid,
    onClickRightButton = { verify() },
    onValueChange = { code = it }
)

// 4.0
WantedTextField(
    text = code,
    trailingButton = "인증",
    trailingButtonEnabled = isValid,
    onClickTrailingButton = { verify() },
    onValueChange = { code = it }
)
```

#### TextArea

| 3.x | 4.0 | 대상 |
|---|---|---|
| `rightButton` | `button` | 문자열·`TextFieldValue` 오버로드 |
| `onClickRightButton` | `onClickButton` | 〃 |
| `negative: Boolean` | `status: WantedTextAreaDefaults.Status` (`true` → `.Negative`, `false` → `.Normal`) | 전 오버로드 |
| `leftContent` / `rightContent` | `leadingContent` / `trailingContent` | `TextFieldValue` 오버로드 |

`negative`는 deprecated 오버로드에서도 **빠졌습니다.** 컴파일 에러가 나는 자리입니다.

```kotlin
// 3.x
WantedTextArea(text = memo, negative = isError, rightButton = "저장", onClickRightButton = save)

// 4.0
WantedTextArea(
    text = memo,
    status = if (isError) WantedTextAreaDefaults.Status.Negative else WantedTextAreaDefaults.Status.Normal,
    button = "저장",
    onClickButton = save
)
```

`trailingContent`와 `rightButton`을 함께 받던 `text:` 오버로드는 둘 중 하나만 받는 오버로드로 나뉘었습니다. 버튼 모양 변화는 [2.2](#22-textarea-우측-버튼)를 보세요.

#### Select

| 3.x | 4.0 |
|---|---|
| `WantedSelectWithString(selectedValueList = …)` | `WantedSelect(valueList = …)` |
| `negative: Boolean` | `status: WantedSelectDefaults.Status` |
| `negativeDataList` | `errorDataList` |
| `negativeList` | `errorList` |

`WantedSelectWithString`과 라벨 내장 오버로드(`title`·`isRequiredBadge`·`description`)는 deprecated로 남아 3.x 이름(`negative`·`negativeDataList`)을 그대로 받습니다. 이 표는 **새 본체 오버로드로 옮길 때** 적용됩니다. 새 본체 오버로드는 `placeHolder`·`enabled`·`selectDataList`(또는 `selectValueList`)가 기본값 없는 필수 인자입니다.

#### FilterButton

| 3.x | 4.0 |
|---|---|
| `isExpend` | `isExpanded` |

두 공개 오버로드 모두 바뀌었습니다. 오타 수정이라 동작은 같습니다.

#### SegmentedControl

Solid/Outlined 두 갈래가 하나로 합쳐지면서 이름에서 `Solid`가 빠졌습니다.

| 3.x | 4.0 |
|---|---|
| `WantedSegmentedControlSolid(…)` | `WantedSegmentedControl(…)` |
| `WantedSegmentedControlSolidItem(title: String, isSelected, modifier, icon)` | `WantedSegmentedControlItem(isSelected, modifier, title: String? = null, icon)` |
| `WantedSegmentedControlOutlined` · `WantedSegmentedControlOutlinedItem` | **제거** ([3.2](#32-segmentedcontrol-outlined)) |

`WantedSegmentedControlItem`은 첫 인자가 `isSelected`로 바뀌고 `title`이 nullable이 됐습니다. **위치 인자로 `("탭", true)`처럼 부르던 곳은 컴파일 에러**가 나니 이름 인자로 바꾸세요.

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

`WantedSearchFieldDefaults.Size`가 높이를 들고 다니던 data class에서 이름만 있는 `data object`로 바뀌었고, **이름이 한 칸씩 밀렸습니다.** `WantedSearchField`와 `WantedSearchTopAppBar`가 같이 씁니다.

| 3.x | 높이 | 4.0 | 높이 |
|---|---|---|---|
| `Size.Medium()` | 48 | `Size.Large` | 48 |
| `Size.Small()` | 40 | `Size.Medium` | 40 |
| `Size.Custom(padding, minHeight)` | 지정값 | **제거** ([3.3](#33-searchfield-sizecustom--focused)) | - |

**`Size.Medium()` → `Size.Medium`으로 괄호만 지우면 컴파일은 되지만 높이가 48 → 40으로 줄어듭니다.** 3.x `Medium()`은 4.0 `Large`입니다.

| 그 밖의 파라미터 | 3.x | 4.0 |
|---|---|---|
| `focused: State<Boolean>` | 있음 | **제거** ([3.3](#33-searchfield-sizecustom--focused)) |
| `textStyle` | `TextStyle` (기본 `body1Regular`) | `TextStyle?` (기본 `null` → size별 스타일) |
| `cursorBrush` | `Brush` | `Brush?` (기본 `null` → primary 색) |

#### ListCell · Accordion

| 3.x | 4.0 | 대상 |
|---|---|---|
| `caption` / `annotatedCaption` / `captionStyle` | `description` / `annotatedDescription` / `descriptionStyle` | ListCell |
| `fillWidth = false` | `variant = WantedListCellDefaults.Variant.Inset` (기본값) | ListCell · Accordion |
| `fillWidth = true` | `variant = WantedListCellDefaults.Variant.Full` | ListCell · Accordion |
| `interactionPadding = InteractionPadding.Default(…)` | 제거 - `variant`에 통합 | ListCell |

`Inset`은 터치 영역만 좌우 12 확장하고, `Full`은 셀이 좌우 여백 20을 직접 갖습니다. 3.x `fillWidth`가 쓰던 여백 값 그대로라 여백은 달라지지 않지만, **눌림 영역 radius는 바뀝니다**([4.1](#listcell) 참고). `InteractionPadding.Custom(n)`은 [3.7](#37-listcell-interactionpaddingcustom)을 보세요.

```kotlin
// 3.x
WantedListCell(text = "텍스트", caption = "캡션", fillWidth = true, onClick = {})

// 4.0
WantedListCell(text = "텍스트", description = "캡션", variant = WantedListCellDefaults.Variant.Full, onClick = {})
```

슬롯 재구성은 [2.4 ListCell 슬롯](#24-listcell-슬롯)에 있습니다.

#### Avatar

| 3.x | 4.0 |
|---|---|
| `WantedAvatarType.Academic` | `WantedAvatarType.Academy` |
| `R.drawable.icon_avatar_placeholder_academic` | `R.drawable.icon_avatar_placeholder_academy` |

`WantedAvatarSize.Custom(size, cornerRadius)`의 `cornerRadius`에 기본값(`ceil(size × 0.25 / 2) × 2 + 2`)이 생겼습니다. 넘기던 값은 그대로 동작합니다.

`WantedAvatarGroup`의 `size`·`type` 변경은 [3.10](#310-avatargroup-type--size)에 있습니다.

#### PushBadge

| 3.x | 4.0 |
|---|---|
| `count` | `text` |
| `PushBadgeVariant.Number` + `count = "12"` | `PushBadgeVariant.MaxCount` + `text = "12"` (99 초과 시 `99+`) |
| `PushBadgeVariant.New` ("N" 고정) | `PushBadgeVariant.Text` + `text = "N"` |
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

**`Number`를 `MaxCount`로 옮길 때 `text`가 숫자가 아니면 `0`이 표시됩니다.** `MaxCount`는 `text.toIntOrNull() ?: 0`으로 해석합니다. 숫자가 아닌 문자열(`"9+"`, `"N"` 등)을 넣던 자리는 `Text`로 옮기세요.

외곽 테두리 기본색이 `staticWhite` → `backgroundNeutralPrimary`로 바뀌었습니다. 다크 모드에서 흰 테두리를 의도했다면 `outlineBorderColor`를 직접 넘기세요. 두께는 [3.11](#311-pushbadge-borderwidth)를 보세요.

#### FallbackView

| 3.x | 4.0 |
|---|---|
| `positive` / `onClickPositive` | `main` / `onClickMain` |
| `negative` / `onClickNegative` | `alternative` / `onClickAlternative` |
| `positiveColor` / `negativeColor` | **제거** ([3.8](#38-fallbackview-버튼-색)) |

여백 구조 변경은 [2.5](#25-fallbackview-여백), 이미지 슬롯은 [3.9](#39-fallbackview-이미지-슬롯)에 있습니다.

#### Popup

`WantedModal`이 `WantedPopup`으로 바뀌었습니다. 오버로드 세 개(버튼형·`bottomBar` 슬롯형·`lazyContent`형)가 모두 대응합니다.

| 3.x | 4.0 |
|---|---|
| `WantedModal(…)` | `WantedPopup(…)` |
| `type: ModalType` | `resize: WantedPopupContract.Resize` |
| `shape: RoundedCornerShape` | `popupDefault = WantedPopupDefaults.getDefault(shape = …)` |
| `size: ModalSize` | `popupDefault = WantedPopupDefaults.getDefault(…)` ([2.7](#27-popup--bottomsheet-여백-지정)) |

| 3.x `ModalType` | 4.0 `Resize` |
|---|---|
| `Flexible`(기본) · `FixedWrapContent` | `Hug`(기본) |
| `Fixed(height)` | `Fixed(height)` |
| `FixedFullScreen` · `FixedRatio(ratio)` | **대응 없음** ([3.12](#312-popup-fixedfullscreen--fixedratio)) |

`WantedModalContract.ModalType` 자체는 남아 있습니다. **BottomSheet는 계속 `ModalType`을 받고, Popup만 `Resize`로 갈라졌습니다.** 버튼형 오버로드의 구조 변경은 [2.6](#26-popup-버튼형-오버로드)에 있습니다.

#### BottomSheet

| 3.x | 4.0 |
|---|---|
| `WantedModalBottomSheet(modalSize = ModalSize.Medium)` | `WantedModalBottomSheet(sheetDefault = WantedBottomSheetDefaults.getDefault())` (생략 가능) |

`sheetDefault` 기본값은 `type`을 보고 자동으로 고릅니다(`FixedFullScreen`이면 `getFullDefault()`). `modalSize`를 넘기던 자리는 **이름만 바꾸는 치환이 아닙니다.** 본문 여백이 생기므로 [2.7](#27-popup--bottomsheet-여백-지정)과 [4.1](#popup--bottomsheet)을 함께 봐주세요.

레거시 `WantedBottomSheetDialog`·`WantedBottomSheetLayout`은 그대로 `modalSize: ModalSize`를 받습니다.

#### DialogTopAppBar

`WantedDialogTopAppBarContract.Variant`가 `enum class`에서 `sealed class`로 바뀌었습니다.

| 3.x | 4.0 |
|---|---|
| `Variant.Normal` · `Variant.Emphasized` | 그대로 |
| `Variant.Floating` | `Variant.Floating()` (`iconBackground: Boolean = false`) |
| `Variant.Display` | **대응 없음** ([3.13](#313-dialogtopappbar-variantdisplay)) |
| `Variant.values()` / `entries` / `valueOf` | `Variant.presets` |

`Floating`이 `data class`가 되어 **괄호 없이 쓰면 컴파일 에러**가 납니다. `when`에서는 `is Variant.Floating`으로 분기합니다.

#### TopAppBar 아이콘 버튼

| 3.x | 4.0 |
|---|---|
| `WantedTopAppBarIconButton(variant = …)` | 제거 (3.x에서도 렌더에 쓰이지 않던 값) |
| `LocalWantedTopBarIconVariant` · `WantedTopBarIconVariantCompositionLocal` | 제거 |

`variant =`를 넘기던 호출부와 `LocalWantedTopBarIconVariant provides …`로 감싸던 코드는 지우면 됩니다. 누름 피드백은 [4.1](#topappbar--dialogtopappbar)을 보세요.

#### Category

| 3.x | 4.0 |
|---|---|
| `isAlternative = false` | `variant = WantedCategoryDefaults.Variant.Normal()` (기본값) |
| `isAlternative = true` | `variant = WantedCategoryDefaults.Variant.Alternative` |

#### deprecated로 남은 이름

컴파일 에러는 나지 않고 경고만 납니다. 렌더 결과도 같습니다.

| 3.x | 4.0 |
|---|---|
| `ContentBadgeSize.Large` | `ContentBadgeSize.Medium` |
| `WantedToastVariant.Message` | `WantedToastVariant.Normal` |
| `ButtonVariant.TEXT` | `WantedTextButton` |

---

## 2. 없어져서 다시 짜야 하는 것

### 2.1 입력 컴포넌트의 라벨·메시지·글자수

3.x는 `WantedTextField`·`WantedTextArea`·`WantedSelect`가 제목(`title`), 필수 표시(`requiredBadge`), 하단 메시지(`description`)까지 직접 그렸습니다. 4.0은 세 컴포넌트가 **입력 칸만** 그리고, 라벨·메시지·글자수는 새 컴포넌트 **`WantedFormControl`**이 감싸서 배치합니다.

**입력 컴포넌트에 파라미터를 넘긴다고 자동으로 감싸지지 않습니다.** 호출부가 `WantedFormControl { … }`로 직접 감싸야 합니다. 라벨 내장 오버로드는 deprecated로 남아 있어 당장은 경고만 납니다.

| 3.x (컴포넌트 파라미터) | 4.0 `WantedFormControl` |
|---|---|
| `title` | `label` (빈 문자열이면 그리지 않음) |
| `requiredBadge` / `isRequiredBadge` | `required` |
| `description` | `description` |
| `status` / `negative` (메시지 색) | `status: WantedFormControlDefaults.Status` (`Normal`/`Positive`/`Negative`) |
| TextArea 내장 글자수 | `accessory = { WantedTextAreaCharacterCount(…) }` |
| - | `size` (`Large` / `Medium`), `labelPlacement` (`Top` / `Leading`), `enabled` |

**상태는 두 번 넘겨야 합니다.** `WantedFormControl`의 `status`는 메시지 색만 바꾸고, 입력 칸의 빨간 테두리는 안쪽 컴포넌트에 준 값(`error` / `status`)이 정합니다. 둘을 같은 조건에서 계산하세요.

#### TextField

```kotlin
// 3.x
WantedTextField(
    text = email,
    title = "이메일",
    requiredBadge = true,
    placeholder = "이메일을 입력해 주세요.",
    description = if (isInvalid) errorMessage else null,
    status = if (isInvalid) WantedTextFieldDefaults.Status.Negative else WantedTextFieldDefaults.Status.Normal,
    onValueChange = { email = it }
)

// 4.0
WantedFormControl(
    label = "이메일",
    required = true,
    description = if (isInvalid) errorMessage else null,
    status = if (isInvalid) WantedFormControlDefaults.Status.Negative else WantedFormControlDefaults.Status.Normal,
) {
    WantedTextField(
        value = emailValue,                    // TextFieldValue
        placeholder = "이메일을 입력해 주세요.",
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

4.0 본체 오버로드는 **`TextFieldValue`만 받고**, `error`·`enabled`·`trailingButtonEnabled`·`complete`·`maxLines`·`minLines`·`maxWordCount`·`enabledOverflowText`·`interactionSource`·`keyboardOptions`·`keyboardActions`가 **기본값 없는 필수 인자**입니다. 3.x `status`는 `error`(Negative)·`complete`(Positive) 두 Boolean으로 갈라집니다.

문자열(`text: String`) 본체 오버로드는 없습니다. 문자열 상태를 쓰던 화면은 `TextFieldValue`로 바꾸세요.

본체 오버로드의 `background` 기본값은 `backgroundNeutralPrimary`로, deprecated 오버로드의 기본값(`effectTransparentSecondary`)과 **다릅니다.** 옮길 때 배경색이 바뀌면 `background`를 명시하세요.

#### TextArea + 글자수

```kotlin
// 3.x - 글자수 카운터가 입력 칸 왼쪽 아래에 자동으로 붙었다
WantedTextArea(
    text = feedback,
    title = "피드백",
    placeholder = "좋았던 점이나 아쉬운 점을 적어주세요.",
    maxWordCount = 1000,
    onValueChange = { feedback = it }
)

// 4.0
WantedFormControl(
    label = "피드백",
    accessory = {
        WantedTextAreaCharacterCount(current = feedbackValue.text.length, maxWordCount = 1000)
    }
) {
    WantedTextArea(
        value = feedbackValue,
        placeholder = "좋았던 점이나 아쉬운 점을 적어주세요.",
        maxWordCount = 1000,
        onValueChange = { feedbackValue = it }
    )
}
```

- **4.0 본체 오버로드는 카운터를 그리지 않습니다.** 3.x의 자동 카운터는 deprecated 오버로드에서만 유지됩니다(하단 `leadingContent`·`trailingContent`가 둘 다 비어 있을 때). 본체로 옮기면 `accessory`에 직접 넣어야 하고, **위치도 입력 칸 안 왼쪽 아래 → 입력 칸 밖 오른쪽 아래**로 바뀝니다.
- 이모지를 한 글자로 세던(`isGraphemeClusterCount = true`) 자리는 `current`를 호출부가 grapheme 단위로 계산해 넘겨야 합니다. 본체 오버로드의 `isGraphemeClusterCount`는 입력 제한에만 쓰입니다.

본체 오버로드 기본값도 3.x와 다릅니다. 줄 수 제한을 기대하던 자리는 명시하세요.

| 항목 | 3.x / deprecated 오버로드 | 4.0 본체 오버로드 |
|---|---|---|
| `minLines` | 1 | **2** |
| `maxLines` | 3 | 6 |
| `resize` | 항상 `maxLines`가 상한 (`Limit`) | **`Normal` - 상한 없이 계속 늘어남** (`maxLines`는 `Limit`에서만 사용) |
| `description = ""` | 설명 줄 없음 | 빈 줄 |

#### Select

```kotlin
// 3.x
WantedSelect(
    selectData = selected,
    title = "직군",
    isRequiredBadge = true,
    description = if (isError) "직군을 선택해 주세요." else null,
    negative = isError,
    placeHolder = "선택해 주세요",
    selectDataList = jobs,
    onSelectData = { selected = it }
)

// 4.0
WantedFormControl(
    label = "직군",
    required = true,
    description = if (isError) "직군을 선택해 주세요." else null,
    status = if (isError) WantedFormControlDefaults.Status.Negative else WantedFormControlDefaults.Status.Normal,
) {
    WantedSelect(
        selectData = selected,
        placeHolder = "선택해 주세요",
        enabled = true,
        selectDataList = jobs,
        status = if (isError) WantedSelectDefaults.Status.Negative else WantedSelectDefaults.Status.Normal,
        onSelectData = { selected = it }
    )
}
```

#### 라벨을 왼쪽에 두기

`labelPlacement = Leading`이면 라벨이 입력 왼쪽에 놓입니다(간격 16). 라벨 폭은 **부모 폭의 50%까지**이고 넘치면 말줄임됩니다. 라벨은 `min(입력 높이, 48)` 영역 안에서 세로 가운데 정렬되므로 TextArea처럼 긴 입력에서도 첫 줄 근처에 붙습니다.

여러 필드의 라벨 열 폭을 맞춰 주는 API는 없습니다. `Leading` 라벨을 여러 개 쌓을 때 폭을 맞추려면 호출부에서 정해야 합니다.

---

### 2.2 TextArea 우측 버튼

3.x는 `rightButton`을 넘기면 하단 오른쪽에 **텍스트 버튼**을 그렸습니다. 4.0의 `button`은 **Outlined · Assistive 버튼**(Large → `ButtonSize.SMALL`, Medium → `ButtonSize.XSMALL`)입니다. 이름만 바꿔도 버튼 모양이 바뀝니다.

`button`과 `trailingContent`를 함께 주면 `button`이 이기고 `trailingContent`는 무시됩니다.

---

### 2.3 IconButton 크기 지정

3.x는 IconButton의 크기를 **`modifier`로** 정했습니다. 4.0은 크기 타입 `size:`를 받고, 아이콘 크기·모서리는 박스 크기에서 자동으로 계산합니다.

| 컴포넌트 | 3.x 크기 지정 | 4.0 크기 지정 |
|---|---|---|
| `WantedIconButtonNormal` | `modifier = Modifier.size(아이콘)` | `size: WantedIconButtonNormalSize` (기본 `Xlarge`) |
| `WantedIconButtonBackground` | `modifier = Modifier.size(아이콘)` | `size: WantedIconButtonBackgroundSize` (기본 `Default`) |
| `WantedIconButtonOutlined` · `Solid` | `size: WantedIconButtonSize` 또는 `padding:` 오버로드 | `size: WantedIconButtonSize` |

#### WantedIconButtonNormal

| 4.0 | 박스(터치) | 아이콘 | 모서리 |
|---|---|---|---|
| `.Xlarge` (기본값) | 36 | 24 | 10 |
| `.Large` | 32 | 20 | 10 |
| `.Medium` | 28 | 18 | 8 |
| `.Small` | 24 | 16 | 8 |
| `.Custom(n.dp)` | `clamp(24, n, 64)` | `n × 2/3` | `n × 0.3` |

3.x에서 `modifier`에 준 크기가 **아이콘 크기**였으므로 아이콘 크기로 프리셋을 고릅니다.

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

**`modifier`의 `size(…)`는 반드시 지우세요.** 남겨 두면 컴파일은 되지만 4.0 박스가 그 크기로 강제되어(`Modifier.size(24.dp)` + `Xlarge` → 박스 24) 터치 영역이 3.x보다 작아집니다.

글리프 크기는 같고 **차지하는 자리가 커집니다**(24 → 36). 3.x는 아이콘 크기만 레이아웃을 차지하고 터치 영역(상하좌우 8)은 레이아웃 밖으로 넘쳤습니다. **3.x처럼 간격을 유지하려면** `interactionOverflow = true`를 켭니다. 차지하는 자리는 아이콘 크기로 줄고 터치 영역은 그대로 남아 상하좌우로 넘칩니다.

```kotlin
// 4.0 - 차지하는 자리는 3.x와 같은 24, 터치 영역은 36
WantedIconButtonNormal(
    icon = R.drawable.icon_normal_share,
    size = WantedIconButtonNormalSize.Xlarge,
    interactionOverflow = true,
    onClick = onClickShare,
)
```

`interactionOverflow = true`일 때 `size`의 뜻이 바뀝니다.

- **켜면 숫자는 아이콘 크기입니다.** 프리셋은 위 표의 아이콘 크기(24·20·18·16)를, `Custom(n)`은 `n`을 아이콘 크기로 씁니다. 터치 영역은 `max(24, ceil(아이콘 × 1.5 ÷ 4) × 4)`이고 넘침은 `(터치 영역 - 아이콘) ÷ 2`입니다.
- **끄면 숫자는 박스 크기입니다.** `Custom(22.dp)`는 박스가 24로 clamp되고 아이콘은 16이 됩니다.

| `interactionOverflow = true` 아이콘 | 12 | 16 | 18 | 20 | 22 | 24 | 28 | 32 |
|---|---|---|---|---|---|---|---|---|
| 터치 영역 | 24 | 24 | 28 | 32 | 36 | 36 | 44 | 48 |

#### WantedIconButtonBackground

`WantedIconButtonBackgroundSize.Default`(박스 32 / 아이콘 20) 하나만 프리셋이고, 그 외는 `.Custom(n.dp)`입니다(아이콘 `n × 2/3`). 3.x에서 `Modifier.size(20.dp)`였다면 `Default`가 같은 크기입니다. 여기서도 `modifier`의 `size(…)`를 남기면 박스가 그 크기로 줄어드니 지워주세요.

#### WantedIconButtonOutlined · WantedIconButtonSolid

`WantedIconButtonSize`가 `enum class`에서 `sealed class`로 바뀌었습니다. `Medium`(40)·`Small`(32) 이름은 그대로라 호출부는 바뀌지 않지만, enum 전용 API와 프로퍼티는 없어졌습니다.

| 3.x | 4.0 |
|---|---|
| `WantedIconButtonSize.Medium.size` | `WantedIconButtonSize.Medium.boxSize` |
| `WantedIconButtonSize.Medium.padding` | 제거 - 아이콘은 `box × 0.47`로 자동 |
| `WantedIconButtonSize.entries` / `values()` | `WantedIconButtonSize.presets` |
| `WantedIconButtonOutlined(icon, modifier, padding = 10.dp, …)` | `WantedIconButtonOutlined(icon, size = WantedIconButtonSize.Medium, …)` |
| `WantedIconButtonSolid(icon, modifier, padding = 10.dp, …)` | `WantedIconButtonSolid(icon, size = WantedIconButtonSize.Medium, …)` |

3.x `padding` 오버로드는 박스 크기를 `modifier`로 받았습니다. 박스가 40·32가 아니었다면 `WantedIconButtonSize.Custom(박스.dp)`로 옮기고, 아이콘 크기는 박스에서 자동으로 정해진다는 점을 확인해주세요([3.5](#35-iconbutton-박스아이콘-비율)).

---

### 2.4 ListCell 슬롯

슬롯 타입이 `@Composable () -> Unit`에서 `@Composable RowScope.() -> Unit`으로 바뀌고 슬롯이 둘 늘었습니다.

| 3.x | 4.0 |
|---|---|
| `leadingContent: (@Composable () -> Unit)?` | `leadingContent: (@Composable RowScope.() -> Unit)?` |
| `trailingContent: (@Composable () -> Unit)?` | `trailingContent: (@Composable RowScope.() -> Unit)?` |
| - | `labelTrailingContent` (타이틀 옆 배지, 높이 22, 간격 4) |
| - | `extraContent` (설명 아래 자유 슬롯) |

람다를 그 자리에서 쓰던 호출부(`leadingContent = { Icon(...) }`)는 그대로 컴파일됩니다. **`@Composable () -> Unit` 타입 변수를 넘기던 자리는 컴파일 에러가 납니다.** `leadingContent = { slot() }`처럼 감싸 주세요.

leading·trailing 슬롯에 여러 개를 넣으면 간격 8로 가로 배치됩니다. 3.x처럼 `Row`로 감싸 직접 간격을 주던 자리는 감싼 `Row`를 걷어낼 수 있습니다.

`selected = true`이고 `trailingContent`가 비어 있으면 **우측에 체크 아이콘이 자동으로 나옵니다.** 3.x에서 선택 표시로 체크 아이콘을 직접 넣던 자리는 중복되지 않는지 확인하세요(`trailingContent`를 채우면 자동 체크는 나오지 않습니다).

슬롯 안 컨트롤(체크박스·스위치 등)이 직접 터치를 받아야 하면 `enabledInnerTouch = true`를 켭니다. 기본값 `false`에서는 셀 클릭 영역이 슬롯 위를 덮어 어디를 눌러도 `onClick`만 호출됩니다.

---

### 2.5 FallbackView 여백

`padding: WantedFallbackPadding` 파라미터가 생기고 **상하 여백이 컴포넌트에 내장됐습니다.**

```kotlin
enum class WantedFallbackPadding(internal val verticalPadding: Dp) {
    Normal(verticalPadding = 160.dp),   // 위아래 각 160 (기본값)
    Compact(verticalPadding = 80.dp)    // 위아래 각 80
}
```

3.x는 텍스트·버튼 묶음 위아래에 12만 있었습니다. 4.0 기본값 `Normal`은 위아래 각각 160이라, **아무것도 안 바꾸면 빈 화면 높이가 약 300dp 늘어납니다.**

```kotlin
// 3.x - 밖에서 여백을 줬습니다
WantedFallbackView(
    modifier = Modifier.padding(vertical = 80.dp),
    heading = "데이터가 없습니다.",
    positive = "추가하기",
    onClickPositive = { add() }
)

// 4.0 - 컴포넌트에 맡깁니다
WantedFallbackView(
    padding = WantedFallbackPadding.Compact,
    heading = "데이터가 없습니다.",
    main = "추가하기",
    onClickMain = { add() }
)
```

**밖에서 주던 상하 여백과 고정 높이를 반드시 정리하세요.** 그대로 두면 이중 적용되고, `height()` 고정은 최소 여백 320(또는 160)에 콘텐츠까지 들어가지 않아 넘칩니다. 리스트 중간이나 바텀시트처럼 좁은 자리에 넣는 경우가 특히 위험합니다.

| 3.x 호출부 | 4.0 |
|---|---|
| 바깥 `padding(vertical = 160.dp)` | 제거 (기본 `Normal`) |
| 바깥 `padding(vertical = 80.dp)` | 제거 + `padding = WantedFallbackPadding.Compact` |
| 바깥 여백 없이 좁은 영역에 배치 | `Compact`로도 80씩 생기므로 화면 확인 |

160·80 외의 상하 여백을 지정하는 API는 없습니다. 좌우 여백은 여전히 호출하는 화면이 줍니다.

---

### 2.6 Popup 버튼형 오버로드

3.x 버튼형 `WantedModal`은 `topBar` 슬롯 + `positive`/`negative` 두 버튼을 받았습니다. 4.0 버튼형 `WantedPopup`은 **`title: String?` + Main Action 하나**만 받습니다. `negative`·`onClickNegative`·`topBar`는 이 오버로드에서 빠졌습니다.

| 3.x (버튼형 오버로드) | 4.0 |
|---|---|
| `topBar = { WantedDialogTopAppBar(title = "제목") }` | `title = "제목"` |
| `positive` · `onClickPositive` | 그대로 (Main Action 한 개) |
| `negative` · `onClickNegative` | 제거 - `bottomBar` 오버로드 + `WantedActionArea` |

```kotlin
// 3.x
WantedModal(
    topBar = { WantedDialogTopAppBar(title = "제목") },
    positive = "확인",
    negative = "취소",
    onClickPositive = { onConfirm() },
    onClickNegative = { onCancel() },
    onDismissRequest = { onCancel() }
) {
    Text("내용")
}

// 4.0 - 버튼이 둘이면 bottomBar 오버로드로 ActionArea 를 직접 넣는다
WantedPopup(
    onDismissRequest = { onCancel() },
    topBar = {
        WantedDialogCloseTopAppBar(
            variant = WantedDialogTopAppBarContract.Variant.Emphasized,
            title = "제목",
            onClickClose = { onCancel() }
        )
    },
    bottomBar = {
        // 여백은 Popup 이 넣으므로 ActionArea 의 safeArea·divider 는 끈다
        WantedActionArea(
            type = ActionAreaType.Neutral,
            main = "확인",
            onClickMain = { onConfirm() },
            sub = "취소",
            onClickSub = { onCancel() },
            safeArea = false,
            divider = false
        )
    }
) {
    Text("내용")
}
```

3.x 버튼은 세로로 쌓인 `WantedButton`(확인 Solid Primary, 취소 Outlined Primary)이었습니다. 4.0 버튼형 오버로드는 `WantedActionArea(type = Strong)`를 씁니다. **버튼 모양이 ActionArea 스펙으로 바뀝니다.**

**버튼형 오버로드는 이제 닫기 버튼을 항상 그립니다.** `title`이 null이어도 닫기 버튼만 있는 상단 바가 붙고, 누르면 `onDismissRequest`가 불립니다. 3.x에서 `topBar`를 넘기지 않아 상단 바가 없던 팝업은 4.0에서 **닫기 버튼이 새로 생깁니다.** 상단 바 없이 쓰려면 `bottomBar` 오버로드(`topBar = null`)로 옮기세요.

---

### 2.7 Popup · BottomSheet 여백 지정

3.x는 `ModalSize` 열거형(Small/Medium/Large/XLarge/Custom) 하나로 본문·버튼·상단 바 여백을 한꺼번에 골랐습니다. 4.0은 **영역별 여백을 데이터 클래스로 따로 받습니다.**

```kotlin
// 3.x - 여백 프리셋을 고른다
WantedModal(size = ModalSize.Custom, onDismissRequest = { … }, bottomBar = { … }) { … }

// 4.0 - 필요한 값만 바꾼다
WantedPopup(
    onDismissRequest = { … },
    popupDefault = WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp),
    bottomBar = { … }
) { … }
```

| `WantedPopupDefaults.getDefault(...)` 인자 | 기본값 |
|---|---|
| `shape` | `RoundedCornerShape(24.dp)` |
| `width` | `360.dp` (최대 폭) |
| `navigationPadding` | `0.dp` |
| `contentHorizontalPadding` | `28.dp` |
| `contentVerticalPadding` | `0.dp` |
| `actionHorizontalPadding` | `24.dp` |
| `actionVerticalPadding` | `20.dp` |
| `actionBottomPadding` | `4.dp` |
| `actionAreaType` | `ActionAreaType.Strong` |

| `WantedBottomSheetDefaults` | radius(위쪽) | 본문 좌우 | 본문 상하 | 버튼 영역 좌우 | 버튼 영역 상하 |
|---|---|---|---|---|---|
| `getDefault()` | 32 | 28 | 0 | 24 | 20 |
| `getFullDefault()` (`FixedFullScreen`) | 32 | 24 | 20 | 20 | 20 |
| `getWithoutContentPadding(type)` | 위와 같음 | **0** | **0** | 위와 같음 | 위와 같음 |

`ModalSize.Custom`(전부 0)을 쓰던 시트는 `getWithoutContentPadding()`이 가장 가깝습니다. 다만 상단 바·버튼 영역 여백은 0이 아니라 스펙 값이 남습니다.

---

## 3. 대응이 없는 것

기계적 대응이 없어 **사용처가 직접 골라야 하는** 항목입니다. 비슷한 걸 임의로 넣으면 결과가 달라진 걸 모르고 넘어가게 됩니다.

### 3.1 TextField 우측 버튼 variant

`RightVariant.Normal`(파란 글자) / `RightVariant.Assistive`(검정 글자) 선택이 없어지고 **Outlined · Assistive 버튼 한 가지**로 고정됩니다. `Normal`로 파란 강조를 하던 인증·확인 버튼은 색이 바뀝니다. 강조가 꼭 필요하면 `trailingContent` 슬롯에 버튼을 직접 넣어야 합니다.

### 3.2 SegmentedControl Outlined

`WantedSegmentedControlOutlined`와 `WantedSegmentedControlOutlinedItem`이 통째로 제거됐습니다. 테두리형 세그먼트는 **Solid(트랙 + 떠 있는 knob) 하나로 통합**되고, 대응 컴포넌트가 없습니다. 옮기면 모양이 바뀝니다.

### 3.3 SearchField Size.Custom · focused

`Size.Custom(padding, minHeight)`이 제거됐습니다. 4.0은 `Large`(48) / `Medium`(40) 두 가지뿐이라 다른 높이를 쓰던 자리는 둘 중 하나를 골라야 하고 높이가 바뀝니다. `Modifier.height()`로 덮어쓰면 내부 패딩(Large 8 / Medium 6)과 맞지 않을 수 있습니다.

포커스 상태를 바깥에서 주입하던 `focused: State<Boolean>`도 빠졌습니다. 포커스는 내부에서 `interactionSource`로 계산하고, 포커스 이동은 `focusRequester`로 합니다.

### 3.4 Negative 상태의 경고 아이콘

3.x는 TextField·Select가 에러이고 포커스가 없을 때 **우측에 빨간 느낌표 아이콘**을 자동으로 붙였습니다. 4.0은 이 아이콘을 그리지 않으며 켜는 옵션도 없습니다. 에러 표시는 빨간 테두리와 `WantedFormControl` 메시지만 남습니다.

### 3.5 IconButton 박스·아이콘 비율

4.0 IconButton은 아이콘 크기를 박스에서 계산하므로 **박스와 아이콘을 따로 정할 수 없습니다.** 3.x에서 `padding:`이나 `modifier`로 "박스 40에 아이콘 22"처럼 비율을 벗어나게 쓰던 자리는 가장 가까운 프리셋·`Custom` 중에서 골라야 하고, 아이콘 크기가 바뀝니다.

### 3.6 Outlined Button + NEGATIVE

`ButtonType.NEGATIVE`가 새로 생겼지만 `OUTLINED`에는 negative 스펙이 없습니다. `WantedButton(variant = OUTLINED, type = NEGATIVE)`는 컴파일되지만 런타임에 에러 로그를 남기고 **`PRIMARY`로 그립니다.** negative 강조가 필요하면 `SOLID`를 쓰세요.

### 3.7 ListCell InteractionPadding.Custom

`WantedListCellDefaults.InteractionPadding`(Default / Custom)이 통째로 제거됐습니다. 여백은 `Variant`의 고정값(Inset 터치 확장 12 / Full 좌우 여백 20)만 쓸 수 있습니다.

`Custom(12.dp)`·`Custom(20.dp)`처럼 Variant 값과 같은 수치는 각각 `Inset`·`Full`로 옮기면 되지만, **그 외 값(`Custom(16.dp)` 등)은 대응이 없어 여백이 바뀝니다.** 셀 바깥 `Modifier.padding`이나 `cellDefault.contentPadding`(4.0 신규)으로 맞출지, 시안의 여백을 따를지 직접 골라야 합니다.

### 3.8 FallbackView 버튼 색

`positiveColor`·`negativeColor`(`ButtonType`)가 빠지고 버튼이 `OUTLINED` + `ASSISTIVE`로 고정됐습니다. `PRIMARY`로 강조하던 버튼은 **회색 테두리 버튼으로 바뀝니다.**

### 3.9 FallbackView 이미지 슬롯

4.0 디자인에서 이미지 슬롯이 없어졌습니다. 기존 화면 호환용으로 `image`를 **첫 번째 필수 파라미터**로 받는 deprecated 오버로드만 남아 있습니다.

- 이 오버로드에는 `padding`이 없고 4.0 상하 여백(160/80)도 적용되지 않습니다.
- 3.x에서 `image = null`을 명시하던 자리는 non-null 필수 파라미터라 컴파일 에러가 납니다. `image` 인자를 지우면 이미지 없는 4.0 오버로드로 넘어가면서 여백이 160으로 바뀝니다([2.5](#25-fallbackview-여백)).
- 오버로드는 이후 제거될 수 있으니, 삽화가 꼭 필요하면 호출부에서 직접 조립하세요.

### 3.10 AvatarGroup type · size

| 3.x | 4.0 |
|---|---|
| `size: WantedAvatarSize` (5종 + Custom) | `size: WantedAvatarGroupSize` (`XSmall` · `Small`만) |
| `type: WantedAvatarType` | 제거 - `Person` 고정 |

- `WantedAvatarSize.XSmall` → `WantedAvatarGroupSize.XSmall`, `Small` → `WantedAvatarGroupSize.Small`. **`Medium`·`Large`·`XLarge`·`Custom`은 대응이 없어** 아바타가 32 이하로 작아집니다.
- `type = Company`·`Academic`으로 회사·학교 로고를 묶던 자리는 **둥근 사각형에서 원형으로** 바뀝니다. 사각형이 꼭 필요하면 단독 `WantedAvatar`를 직접 겹쳐 배치해야 합니다.
- 한 번에 최대 5개까지만 그립니다. **6번째부터는 조용히 잘리므로**, 초과 인원은 `trailingContent`로 "외 N명"을 직접 표시하세요.

```kotlin
// 3.x
WantedAvatarGroup(
    modelList = urls,
    size = WantedAvatarSize.XSmall,
    type = WantedAvatarType.Person,
    trailingContent = { Text("외 3명") }
)

// 4.0
WantedAvatarGroup(
    modelList = urls,
    size = WantedAvatarGroupSize.XSmall,
    trailingContent = { WantedAvatarGroupTrailingText(text = "외 3명") }
)
```

### 3.11 PushBadge borderWidth

`WantedPushBadgeBorder(borderWidth:)`에 대응하는 값이 없습니다. 테두리 두께가 사이즈별 고정값(Dot: XSmall 0.5 / Small 1 / Medium 1, Text: XSmall 1 / Small 1.5 / Medium 2)으로 정해집니다. 3.x 기본값 2를 쓰던 자리는 대부분 테두리가 얇아집니다.

### 3.12 Popup FixedFullScreen · FixedRatio

`Resize`에는 `Hug`와 `Fixed(height)`만 있습니다. 3.x에서 Popup을 화면 높이 전체(`FixedFullScreen`)나 화면 비율(`FixedRatio`)로 띄우던 자리는 직접 높이를 계산해 `Fixed(height)`로 넘기거나 BottomSheet로 옮겨야 합니다. `Fixed`는 여전히 최대 760dp로 잘립니다.

### 3.13 DialogTopAppBar Variant.Display

모달 상단 바의 `Display`(최소 높이 72, 제목 `title3Bold`)가 없어졌습니다. `Emphasized`(최소 높이 56, 제목 `headline2Bold`)로 옮기면 **제목이 작아지고 바 높이가 줄어듭니다.** 화면 상단 바(`WantedTopAppBarContract.Variant.Display`)는 그대로 있습니다.

---

## 4. 화면이 달라지는 것

**컴파일은 통과하지만 화면이 달라지는 항목입니다.** API가 그대로라 치환 작업이 끝난 뒤 화면을 봐야 드러납니다.

### 4.1 컴포넌트 스펙

#### 투명도 파생 토큰

이름 치환([1.2](#12-투명도-파생-토큰))만 하면 컴파일은 통과하지만, 아래 토큰은 **RGB가 바뀌어 색이 달라집니다.** 알파는 같습니다.

| 3.x → 4.0 | 라이트 RGB | 다크 RGB |
|---|---|---|
| `primaryNormalOpacity*` → `surfaceBrandPrimaryOpacity*` | `#3366FF` → `#0066FF` | `#5B84FF` → `#3385FF` |
| `labelNormalOpacity*` → `foregroundNeutralPrimaryOpacity*` | `#171717` → `#171719` | `#F7F7F7` → `#F7F7F8` |
| `labelAlternativeOpacity*` → `foregroundNeutralTertiaryOpacity*` | `#8A8A8A` → `#37383C` **확실히 진해짐** | `#737373` → `#AEB0B6` **확실히 밝아짐** |
| `lineNormalOpacity*` → `lineNeutralPrimaryOpaqueOpacity*` | 동일 | `#36373B` → `#37383C` |
| `statusPositiveOpacity*` → `foregroundPositivePrimaryOpacity*` | `#07BA9C`(청록) → `#00BF40`(초록) | `#33D4B9` → `#1ED45A` |
| `statusNegativeOpacity8` → `foregroundNegativePrimaryOpacity8` | `#FF425F` → `#FF4242` | `#FF667D` → `#FF6363` |
| `accentCyanOpacity*` → `surfaceAccentCyanOpaqueOpacity*` | 동일 | `#00BDDE` → `#28D0ED` |
| `accentLightBlueOpacity*` → `surfaceAccentLightBlueOpaqueOpacity*` | `#0095FF` → `#00AEFF` | `#0095FF` → `#3DC2FF` |
| `accentVioletOpacity*` → `surfaceAccentVioletOpaqueOpacity*` | `#7A45E5` → `#6541F2` | `#7A45E5` → `#7D5EF7` |
| `accentPinkOpacity8` → `surfaceAccentPinkOpaqueOpacity8` | `#F342A0` → `#F553DA` | `#F342A0` → `#FA73E3` |
| `accentLimeOpacity8` → `surfaceAccentLimeOpaqueOpacity8` | `#93C400` → `#58CF04` | `#93C400` → `#6BE016` |

`labelAlternativeOpacity*`와 `statusPositiveOpacity*`는 **색상 계열 자체가 달라질 만큼** 차이가 큽니다. 나머지(`labelStrongOpacity*`, `backgroundNormalNormalOpacity*`, `backgroundElevatedNormalOpacity*`, `lineAlternativeOpacity52`)는 값이 같습니다.

#### Button

| 항목 | 3.x | 4.0 |
|---|---|---|
| radius | large 12 / medium 10 / small 8 | large **14** / medium **12** / small **10** / xsmall 8 |
| 높이 제약 | `height` 고정 48 / 40 / 32 | `heightIn(min =)` 48 / 40 / 32 / xsmall 28 |
| 좌우 패딩 | 28 / 20 / 14 | **20 / 16 / 12** / xsmall 10 |
| 상하 패딩 | 4 | 13 / 10 / 8 / xsmall 6 |
| 아이콘↔텍스트 간격 | 6 / 5 / 4 | 6 / **4** / 4 / xsmall 4 |
| 타이포 (PRIMARY) | `body1Bold` / `body2Bold` / `label2Bold` | **`body2Bold` / `label1Bold` / `caption1Bold`** |
| 타이포 (ASSISTIVE) | `body1Medium` / `body2Medium` / `label2Medium` | **`body2Bold` / `label1Bold` / `caption1Bold`** (굵어짐) |
| 로딩 인디케이터 | 18 / 16 / 14 | **16 / 14 / 12** / xsmall 12 |
| Solid 비활성 텍스트 | `labelAssistive` | `foregroundDisablePrimary` (더 옅음) |

**타이포가 사이즈별로 한 단계 내려가는 게 가장 눈에 띕니다.** 버튼 높이는 같지만 글자가 작아지고, ASSISTIVE는 Medium에서 Bold로 굵어집니다. 좌우 패딩도 줄어 `wrapContent` 버튼은 폭이 좁아집니다.

높이가 고정에서 최소값으로 바뀌었지만 텍스트는 여전히 한 줄 말줄임입니다. 긴 라벨이 줄바꿈되지는 않고, **시스템 글자 크기를 키웠을 때만** 버튼이 세로로 커집니다(3.x는 글자가 잘렸습니다).

#### TextButton

| 항목 | 3.x | 4.0 |
|---|---|---|
| 높이 | 제약 없음 (텍스트 + 상하 4) | `heightIn(min =)` medium 32 / small 28 / large **40** |
| 아이콘 크기 | large 18 / medium **18** / small 16 | large 18 / medium **20** / small 16 |
| 아이콘↔텍스트 간격 | small 4 / 그 외 5 | medium **4** / small 4 / large 5 |
| 터치 영역 상하 확장 | 4 | **0** |
| 로딩 인디케이터 | 18 / 16 / 14 | large 16 / medium 16 / small **12** |

MEDIUM·SMALL은 레이아웃 높이가 3.x와 같습니다. **`LARGE`는 최소 높이 40이 생겨 3.x보다 커집니다.** `ButtonVariant.TEXT` + `ButtonSize.LARGE`로 쓰던 자리도 같습니다.

#### IconButton

| 항목 | 3.x | 4.0 |
|---|---|---|
| Normal 차지하는 자리 | 아이콘 크기 (터치 영역은 상하좌우 8 넘침) | **박스 크기**(Xlarge 36). `interactionOverflow = true`면 아이콘 크기 |
| Normal 터치 영역 | 아이콘 + 16, 원형 | 박스, 둥근 사각(radius 10) |
| Normal pushBadge 위치 | 아이콘 우상단에서 바깥으로 | 아이콘 우상단 모서리에 정렬 |
| Outlined·Solid 아이콘 | Medium **20** / Small **18** | Medium **18** / Small **16** |
| Outlined 비활성 배경 | `transparent` | `backgroundNeutralPrimary` |
| Outlined·Solid 리플 | 없음 | Outlined 8% / Solid 20% |
| Background `alternative` 아이콘 | `coolNeutral50` 88% | **`staticWhite` 88%** |
| Background `alternative` 배경 | `coolNeutral30` 불투명 | `coolNeutral30` **61%** |

**Normal은 차지하는 자리가 커지는 게 파급이 가장 큽니다.** 툴바·리스트 행처럼 아이콘이 촘촘한 자리는 간격이 벌어지고 행 높이가 늘어납니다. 3.x 간격을 유지하려면 `interactionOverflow = true`를 켜세요([2.3](#23-iconbutton-크기-지정)).

#### Chip

| 항목 | 3.x | 4.0 |
|---|---|---|
| 타이포 | large `body2` / medium `body2` / small `label1` / xsmall `caption1` | large **`label1`** / medium **`label2`** / small **`caption1`** / xsmall **`caption2`** (Medium) |
| 높이 | 40 / 36 / 32 / 24 | 40 / 36 / 32 / 24 (같음) |
| 좌우 패딩 | 12 / 11 / 8 / 7 | 12 / **10** / 8 / **6** |
| radius | 10 / 10 / 8 / 6 | **12** / 10 / **10** / **8** |
| 아이콘↔텍스트 간격 | 3 / 3 / 2 / 2 | **2 / 2** / 2 / **0** |
| **Solid 활성 배경** | `inverseBackground` (검정) | **`surfaceBrandPrimary` 5%** (옅은 파랑) |
| **Solid 활성 텍스트·아이콘** | `inverseLabel` (흰색) | **`foregroundBrandPrimary`** (파랑) |
| Outlined 활성 테두리 | `primaryNormal` 43% | `surfaceBrandPrimary` **28%** (옅어짐) |

**Solid 활성 칩이 검정 채움에서 옅은 파랑 채움 + 파랑 글자로 바뀝니다.** `isActive = true`로 쓰는 필터·탭형 칩 화면을 꼭 확인해주세요. 높이는 같고 글자가 한 단계 작아져 칩 폭이 줄어듭니다. 가로로 나열되는 칩 줄의 줄바꿈 지점이 달라집니다.

#### FilterButton

| 항목 | 3.x | 4.0 |
|---|---|---|
| 타이포 XSmall / Small / Medium / Large | `caption1` / `label1` / `body2` / `body2` | **`caption2` / `caption1` / `label2` / `label1`** (Medium) |
| radius | 6 / 8 / 10 / 10 | **8 / 10 / 10 / 12** |
| activeLabel 굵기 | SemiBold | 본문과 같은 Medium |
| **Solid 활성** 배경 / 글자 | `inverseBackground`(검정) / `inverseLabel`(흰색) | **`surfaceBrandPrimary` 5%** / **`foregroundBrandPrimary`(파랑)** |
| Outlined 활성 테두리 | `primaryNormal` 43% | **`surfaceBrandPrimary` 28%** |
| Outlined 활성 아이콘 | `labelNormal`(검정) | **`foregroundBrandPrimary`(파랑)** |

가로로 나열되는 필터 바는 버튼 폭이 달라져 **줄바꿈·스크롤 지점이 바뀝니다.**

#### ActionArea

| 항목 | 3.x | 4.0 |
|---|---|---|
| `divider` 기본값 | `false` | **`true`** (`extra`가 있을 때만 그림) |
| `extra` 구분선 색 | `lineNormalNeutral` (16%) | `lineNeutralTertiary` (8%, 옅어짐) |
| `extra` 슬롯 좌우 여백 | 20 | **24** |
| 배경 | 그라데이션만, 영역 자체는 투명 | `background = true` 또는 `extra`가 있으면 **`backgroundColor`로 채움** |
| 배경·그라데이션 기본색 | `backgroundNormalNormal` | `surfaceElevatedPrimary` (다크에서 한 단계 밝음) |
| 캡션 타이포 | `label2Regular` | **`label2Medium`** (굵어짐) |
| 대체(`alternative`) 버튼 | `OUTLINED` / **`PRIMARY`** | `OUTLINED` / **`ASSISTIVE`** |
| `Cancel` 메인 버튼 | **`OUTLINED`** / `ASSISTIVE` | **`SOLID`** / `ASSISTIVE` |
| `Strong` 보조(`sub`) 버튼 상하 여백 | 0 | **8** (영역 높이 16 증가) |
| `Neutral` 보조 버튼 최소 폭 | 84 | 라벨 길이 |

**대체 액션 라벨이 파란색에서 검정으로, `Cancel`의 메인 버튼이 테두리형에서 회색 채움으로 바뀝니다.**

`extra` 슬롯을 쓰는 화면은 3.x에서 구분선이 없었는데 4.0에서 기본으로 생깁니다. 3.x처럼 없애려면 `divider = false`를 넘기세요.

4.0은 `extra`가 있으면 항상 `backgroundColor`로 채웁니다. 화면 바탕이 `backgroundNeutralPrimary`인데 기본값(`surfaceElevatedPrimary`)을 그대로 두면 **다크 모드에서 버튼 영역만 한 단계 밝게** 보입니다. 화면 바탕과 맞추려면 `backgroundColor`를 넘기세요.

#### TextField

| 항목 | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| 높이 | 패딩 12 + 줄 높이 | 최소 Large **48** / Medium **40** |
| 입력 타이포 | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| 그림자 | `XSmall` | **없음** |
| 포커스 표시 | 테두리 2dp | 테두리 1dp + **바깥 4dp 링**(`lineBrandFocus`, 에러면 빨강 12%) |
| 우측 버튼 | 필드에 붙은 별도 칸(좌측 경계선, `body1Bold` 글자) | 필드 **안쪽** Outlined · Assistive 버튼 |
| 완료(Positive) 체크 아이콘 색 | `primaryNormal` (파랑) | **`foregroundPositivePrimary` (초록)** |
| 에러 느낌표 아이콘 | 있음 | **없음** ([3.4](#34-negative-상태의-경고-아이콘)) |
| 여러 줄 | `maxLines`와 무관하게 **항상 한 줄** | `maxLines`·`minLines`가 둘 다 1일 때만 한 줄. **`maxLines > 1`을 넘기던 곳은 실제로 여러 줄이 됩니다** |
| 비포커스 긴 값 | 잘림 | **말줄임(…)** |

#### TextArea

| 항목 | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| 입력 타이포 | `body1Regular` | Large **`body2ReadingRegular`** / Medium **`label1ReadingRegular`** |
| 그림자 | `XSmall` | **없음** |
| 포커스 표시 | 테두리 2dp | 테두리 1dp + 바깥 4dp 링 |
| 하단 버튼 | 텍스트 버튼 | **Outlined · Assistive** 버튼 |
| 글자수 타이포 | `label2Medium` | **`caption1Bold`** |

#### Select

| 항목 | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| 높이 | 패딩 12 + 콘텐츠 | 최소 Large **48** / Medium **40** |
| 값·placeholder 타이포 | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| 그림자 | `XSmall` | **없음** |
| placeholder 색 | `labelAssistive` | **`foregroundNeutralTertiary`** (진해짐) |
| 세로 정렬 | chevron 항상 위쪽 | `overflow = true`일 때만 위쪽, 그 외 **가운데** |
| 눌림 리플 | 에러면 빨강, 포커스면 파랑 | 상태와 무관하게 **중립색** |
| 에러 느낌표 아이콘 | 있음 | **없음** |
| 다중 선택 칩 | 자체 레이아웃 | **`WantedChip` XSmall Outlined** - 테두리 생김 |
| 다중 선택 칩 아이콘 | `iconRes`를 줘도 항상 닫기(×) | `WantedSelectData.iconRes`의 아이콘 |
| Radio 선택 시트 | **선택된 항목에만** 라디오 표시 | **모든 항목에 라디오**(미선택은 빈 원), 라디오를 눌러도 선택 |

#### SearchField · SearchTopAppBar

| 항목 | 3.x | 4.0 |
|---|---|---|
| radius | 12 | Large **14** / Medium **12** |
| 입력 타이포 | `body1Regular` | Large **`body2Regular`** / Medium **`label1Regular`** |
| 아이콘 | 24 | Large **20** / Medium **18** |
| 커서 색 | 텍스트 색 | `foregroundBrandPrimary` |

#### SegmentedControl

| 항목 | 3.x | 4.0 |
|---|---|---|
| 트랙 radius | 12 | Small **10** / Medium **12** / Large **14** |
| 항목 높이 | 패딩으로 결정 | 최소 Small **24** / Medium **32** / Large **40** |
| 타이포 Small / Medium / Large | `label2` / `body2` / `headline2` | **`caption1` / `label1` / `body2`** (한 단계씩 작아짐) |
| 아이콘 | 20 | Small **14** / Medium **16** / Large **18** |

#### ListCell

| 항목 | 3.x | 4.0 |
|---|---|---|
| `ellipsis` 기본값 | `true` | **`false`** |
| 타이틀 최대 줄 수 | 항상 `textMaxLine`(기본 1) | **`ellipsis = true`일 때만** `textMaxLine`, 아니면 무제한 |
| `verticalAlignCenter` 기본값 | `ellipsis`(= `true`) | **`false`** |
| 슬롯 정렬 기준 | 텍스트 영역 전체 | **타이틀 첫 줄** |
| 타이틀 타이포 | `body1Regular` | **`body2Medium`** (selected 시 `body2Bold`) |
| 비활성 | 셀 전체 투명도 43% | 타이틀·설명·체크·chevron만 `foregroundDisablePrimary`. **슬롯 콘텐츠는 흐려지지 않음** |
| 비활성 클릭 | 리플만 꺼지고 `onClick` 호출됨 | **클릭 차단** |
| selected | 타이틀 색만 primary | 타이틀 `foregroundBrandPrimary` + `body2Bold`, trailing이 비면 **체크 아이콘 자동 표시** |
| 눌림 radius - Inset(`fillWidth = false`) | 0 | **16** |
| 눌림 radius - Full(`fillWidth = true`) | 12 | **0** |
| `VerticalPadding.None` | 리플 있음 | **인터랙션 없음** |

**`ellipsis`·`verticalAlignCenter` 기본값이 바뀐 게 파급이 가장 큽니다.** 3.x에서 기본값으로 한 줄 말줄임되던 셀이 4.0에서는 **여러 줄로 줄바꿈되어 셀이 높아지고**, 아이콘이 타이틀 중앙이 아니라 **첫 줄 기준으로 붙습니다.** 3.x 모양을 유지하려면 명시하세요.

```kotlin
// 3.x 기본 동작(한 줄 말줄임 + 수직 중앙)을 4.0 에서 유지
WantedListCell(
    text = title,
    ellipsis = true,
    verticalAlignCenter = true,
    onClick = {}
)
```

비활성 셀의 아바타·썸네일 등 이미지 슬롯은 이제 자동으로 흐려지지 않습니다. 3.x처럼 보이게 하려면 호출부에서 `alpha(OPACITY_43)`을 직접 적용합니다.

#### ListCard

| 항목 | 3.x | 4.0 |
|---|---|---|
| 눌림 영역 모양 | 좌 20 / 우 12 고정 | `RoundedCornerShape(12.dp)` (`cardDefault.interactionShape`로 변경 가능) |

`WantedCard`의 눌림 영역 모양은 그대로입니다.

#### Avatar · AvatarGroup

Company·Academy cornerRadius가 전 사이즈에서 **+2** 됩니다.

| size | 3.x | 4.0 |
|---|---|---|
| `XSmall` (24) | 6 | 8 |
| `Small` (32) | 8 | 10 |
| `Medium` (40) | 10 | 12 |
| `Large` (48) | 12 | 14 |
| `XLarge` (56) | 14 | 16 |

| 항목 | 3.x | 4.0 |
|---|---|---|
| 기본 placeholder (`placeHolder = null`) | 타입별 전용 일러스트 | `surfaceNeutralStrong` 면 + `staticWhite` 28% 아이콘 글리프(아바타의 2/3 크기, `icon_normal_person_fill` / `company_fill` / `graduation_fill`) |
| 그룹 테두리 두께 | 2 | **1.5** |
| 푸시뱃지 | 아바타 모서리에 그대로 | **배경색 외곽 테두리** + 사이즈별 inset |
| AvatarGroup 아바타–trailing 간격 | XSmall 6 / 그 외 8 | XSmall **8** / Small **10** |
| AvatarGroup trailing 슬롯 높이 | 24 고정 | XSmall 24 / Small **32** |

`placeHolder`를 직접 넘긴 자리는 3.x처럼 그 리소스를 원본 색으로 그립니다.

#### PushBadge

| 항목 | 3.x | 4.0 |
|---|---|---|
| 텍스트 뱃지 높이 | 최소 높이(16/20/24)와 내용 중 큰 값 | XSmall 16 / Small 20 / Medium 24 |
| 한 글자 | 최소 너비 + 패딩 | **정사각(1:1) 고정** |
| 글자 크기 확대 | 제한 없음 | 1.3배에서 멈춤 |

뱃지가 얹히는 아이콘·아바타는 글자 크기를 따라 커지지 않아서, 뱃지만 계속 커지면 대상을 덮어버리기 때문입니다.

#### FallbackView

| 항목 | 3.x | 4.0 |
|---|---|---|
| 상하 여백 | 12 | **160** (`Compact` 80) |
| 제목 타이포 | `heading2Bold` | **`headline1Bold`** |
| 설명 타이포 | `body1ReadingRegular` | **`body2ReadingRegular`** |
| 설명 색 | `labelAlternative` | **`foregroundNeutralSecondary`** (진해짐) |
| 버튼 사이즈 | `SMALL` | **`MEDIUM`** |
| 버튼 색 | `positiveColor`·`negativeColor` | `ASSISTIVE` 고정 |

#### Popup · BottomSheet

모달 전반의 모서리와 여백이 조정됐습니다. **API가 그대로라 빌드로는 드러나지 않습니다.**

| 항목 | 3.x (`ModalSize.Medium`) | 4.0 |
|---|---|---|
| Popup radius | 12 | **24** |
| Popup 최대 폭 | 제한 없음 | **360** |
| Popup 본문 좌우 여백 | 20 | **28** |
| Popup 버튼 영역 여백 | 사방 20 | 좌우 **24** · 위 20 · 아래 **24** |
| BottomSheet radius(위쪽) | 16 | **32** |
| BottomSheet 본문 여백 | **0** (컴포넌트가 넣지 않음) | 좌우 **28** |
| BottomSheet 버튼 영역 여백 | 사방 20 | 좌우 **24** · 상하 20 |
| `FixedFullScreen` 시트 본문 여백 | 0 | 좌우 24 · 상하 20 |
| 상단 바 슬롯 여백 | 상하 4 | **0** (여백은 상단 바가 가짐) |
| 고정 높이 타입의 버튼 위치 | 콘텐츠 바로 아래 | 본문이 남은 공간을 채워 **버튼이 하단에 붙음** |

**BottomSheet 본문 여백이 0에서 28로 바뀐 게 파급이 가장 큽니다.** 3.x 시트는 본문 여백을 호출부가 직접 줬기 때문에 그대로 두면 여백이 이중으로 들어가 콘텐츠가 좁아집니다. 기존 여백을 유지하려면 `sheetDefault = WantedBottomSheetDefaults.getWithoutContentPadding(type)`을 넘기세요. Popup도 본문에 직접 여백을 주던 자리는 `WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp)`를 넘기세요.

#### DialogTopAppBar

| 항목 | 3.x | 4.0 |
|---|---|---|
| `Emphasized` 여백 | 상하 8 · 좌우 16 (+ 모달 슬롯 상하 4) | **사방 24** (`navigationPadding`) |
| leading ↔ 제목 간격 | 12 | **16** (`Search`는 12) |
| 닫기 버튼 | 아이콘 24 + 원형 리플 40 | 차지하는 자리 **24**, 컨테이너 36이 바깥으로 넘침, 누름 시 **아이콘 흐려짐** |

모달 안 `Emphasized` 상단 바는 3.x 기준 위아래 12·좌우 16이었던 것이 4.0에서 **위아래 24·좌우 24로 커져 바가 24dp 높아지고 제목이 8dp 안쪽으로 들어갑니다.** 3.x 모양을 유지해야 하면 `navigationPadding`을 직접 줄입니다. 전체 화면 모달은 `WantedDialogTopAppBarDefaults.FULL_NAVIGATION_PADDING`(20)을 씁니다.

```kotlin
// 4.0 - 3.x 와 같은 위아래 12 를 내려면
WantedDialogCloseTopAppBar(
    variant = WantedDialogTopAppBarContract.Variant.Emphasized,
    navigationPadding = 12.dp,
    title = "제목",
    onClickClose = onDismiss
)
```

`Normal`·`Floating`은 중앙 정렬 레이아웃(좌우 16)을 그대로 쓰므로 `navigationPadding`의 영향을 받지 않습니다.

#### TopAppBar · DialogTopAppBar

| 항목 | 3.x | 4.0 |
|---|---|---|
| 아이콘 버튼 누름 피드백 | 원형 리플 | **리플 없음, 아이콘 불투명도 22%** (`IconButtonInteractionEffect.Dim`) |
| 아이콘 · 터치 영역 | 24 · 40 | 24 · 40 (같음) |

`interactionEffect = IconButtonInteractionEffect.Highlight`를 넘기면 리플이 돌아옵니다.

#### Category

| 항목 | 3.x | 4.0 |
|---|---|---|
| `Normal` 선택 칩 | 배경 `inverseBackground`, 글자 `inverseLabel` | 배경 **`foregroundNeutralStrong`**, 글자 `foregroundNeutralInverse` |
| `Alternative` 선택 칩 테두리 | `primaryNormal` 43% | `surfaceBrandPrimary` 43% |

라이트 모드에서 `Normal` 선택 칩이 조금 더 진한 검정이 됩니다. 칩 자체의 크기·타이포 변화는 [Chip](#chip-1)을 따릅니다.

#### 그 외

- **Pagination Dots**: 전체 페이지가 5개 이하이면 `visibleDotCount`보다 많아도 모든 점을 풀사이즈로 그립니다. 페이지 4~5개짜리 캐러셀에서 인디케이터 폭이 넓어집니다.
- **AutoComplete**: `sectionTitleHorizontalPadding`(기본 20)이 섹션 타이틀뿐 아니라 **항목과 직접 입력 슬롯에도** 좌우 여백으로 적용됩니다. 항목 안에서 직접 좌우 여백을 주던 자리는 이중 적용되니, 항목에 `WantedListCell`을 넣는다면 `variant = Inset`(기본값)으로 두세요.
- **Alert 다이얼로그 버튼 · Tooltip 액션 버튼**: `WantedTextButton`으로 바뀌었습니다. Alert의 negative 버튼은 SMALL → **MEDIUM**으로 커집니다.
- **Date / Time 휠 Picker**: `WantedPopup`으로 뜨면서 radius가 28 → 24로 바뀝니다.
- **Tooltip**: 꼬리 위치가 툴팁 폭 안쪽으로 제한되고, 화면이 좁으면 남는 폭만큼만 가장자리 여백을 둡니다.
- **WantedInput**: `label = ""`이면 라벨 자리 여백을 잡지 않습니다. 빈 라벨로 컨트롤만 쓰던 자리에서 보이지 않던 여백이 사라집니다.
- **InnerLine 테두리**(`Modifier.getBorderModifier(borderType = BorderType.InnerLine)`): 다크 테마에서 둥근 테두리 가장자리가 끊겨 보이던 현상이 없어집니다.
- **전역 토스트**(`WantedGlobalToastManager`): Compose 콘텐츠를 띄울 수 없는 Activity에서는 표시를 건너뜁니다.

Typography·Shape 정의는 바뀌지 않았습니다.

### 4.2 전체 목록

**마이그레이션 후 이 목록의 화면을 눈으로 확인해주세요.**

| 대상 | 무엇이 달라지나 |
|---|---|
| **컬러 토큰** | `accentForegroundBlue`·`Orange`는 라이트·다크 모두, `accentForegroundGreen`은 라이트에서 색이 바뀝니다. 다크 모드 딤(`materialDimmer`)이 확실히 진해집니다. [값이 함께 바뀌는 토큰](#값이-함께-바뀌는-토큰) 참고 |
| **투명도 파생 토큰** | RGB가 기준 토큰으로 바로잡혀 색이 바뀝니다. `labelAlternativeOpacity*`·`statusPositiveOpacity*`는 색 계열이 달라집니다 |
| **RedOrange 토큰** | `foregroundCautionaryPrimary`(주황)로 옮기면서 **색이 바뀝니다** |
| **Button** | 타이포가 한 단계 작아지고 좌우 패딩이 줄어 `wrapContent` 버튼이 좁아집니다. radius +2. ASSISTIVE 글자가 굵어집니다 |
| **TextButton** | `LARGE`가 최소 높이 40으로 커집니다. MEDIUM 아이콘이 18 → 20 |
| **IconButton** | Normal이 차지하는 자리가 아이콘 크기에서 박스 크기(36)로 커집니다. `modifier`에 `size`를 남기면 터치 영역이 작아집니다. Outlined·Solid 아이콘이 2dp 작아집니다 |
| **Chip · FilterButton** | 타이포가 한 단계 작아지고, **Solid 활성이 검정에서 옅은 파랑으로** 바뀝니다. 가로 나열의 줄바꿈 지점이 달라집니다 |
| **ActionArea** | `extra`가 있으면 구분선이 생기고 배경을 채웁니다. 대체 액션 라벨이 파랑 → 검정, `Cancel` 메인 버튼이 테두리형 → 회색 채움. `Strong` + `sub`는 16 높아집니다 |
| **입력 컴포넌트** | radius·타이포·높이가 바뀌고 그림자가 없어지며 포커스 링이 생깁니다. 에러 느낌표 아이콘이 사라집니다. `WantedFormControl`로 옮기면 TextArea 카운터 위치가 바뀝니다 |
| **TextField** | `maxLines > 1`을 넘기던 곳이 실제로 여러 줄이 됩니다. 우측 버튼이 필드 안 Outlined 버튼으로, 완료 체크가 파랑 → 초록 |
| **TextArea** | 본체 오버로드로 옮기면 기본 2~6줄에 높이 상한이 없고 카운터가 사라집니다 |
| **Select** | Radio 시트가 모든 항목에 라디오를 그립니다. 다중 선택 칩에 테두리가 생깁니다 |
| **SearchField** | `Size` 이름이 한 칸씩 밀려 괄호만 지우면 높이가 48 → 40 |
| **SegmentedControl** | 높이·타이포가 바뀌고 Outlined가 없어집니다 |
| **ListCell** | 긴 타이틀이 **말줄임 대신 줄바꿈**되고, 슬롯이 첫 줄 기준으로 정렬됩니다. 비활성 셀은 클릭이 막히고 슬롯이 흐려지지 않습니다. selected면 체크 아이콘이 자동으로 붙습니다 |
| **ListCard** | 눌림 영역이 좌 20 / 우 12에서 12로 |
| **Avatar · AvatarGroup** | Company·Academy radius +2, placeholder 교체. AvatarGroup은 원형 고정·최대 5명 |
| **PushBadge** | 한 글자 뱃지가 정사각, 글자 확대 상한 1.3. 외곽 테두리 기본색이 배경색으로 |
| **FallbackView** | 상하 여백 160 내장, 버튼 MEDIUM·ASSISTIVE 고정, 제목·설명 타이포가 한 단계씩 작아짐. 이미지 슬롯이 사라집니다 |
| **Popup** | radius 24, 최대 폭 360, 본문 좌우 28. 버튼형 오버로드는 닫기 버튼이 항상 붙고 버튼이 ActionArea로 바뀝니다 |
| **BottomSheet** | radius 32, **본문 좌우 여백 28이 새로 생김** |
| **DialogTopAppBar** | `Emphasized` 여백이 사방 24로 커져 바가 높아집니다. 닫기 버튼 누름이 아이콘 흐려짐으로 |
| **TopAppBar** | 아이콘 버튼 누름이 리플 대신 아이콘 흐려짐 |
| **Category** | `Normal` 선택 칩이 라이트에서 조금 더 진해집니다 |
| **Pagination Dots** | 5페이지 이하면 모든 점이 풀사이즈 |
| **AutoComplete** | 항목에도 좌우 20 여백 |

---

## 추가된 API

**마이그레이션에 필요 없습니다.** 4.0에서 새로 쓸 수 있게 된 것들이라 참고용으로만 적어둡니다.

### 신규 컴포넌트

| 컴포넌트 | 설명 |
|---|---|
| `WantedFormControl` | 입력 컴포넌트의 라벨·필수 표시·설명·accessory 배치. `WantedFormControlDefaults`(`Status`·`Size`·`LabelPlacement`) |
| `WantedPopup` | `WantedModal` 대체. `WantedPopupContract.Resize`, `WantedPopupDefaults.getDefault(...)` |
| `WantedDialogSearchTopAppBar` | 검색 필드가 들어간 모달 상단 바 |
| `WantedSegmentedControlItem` | icon + text / Icon Only 항목 |
| `WantedAvatarGroupTrailingText` · `WantedAvatarGroupTrailingTextButton` | AvatarGroup trailing 슬롯 프리셋 |
| `WantedTextAreaCharacterCount` | `WantedFormControl` accessory용 글자수 |
| `Modifier.focusRing(visible, shape, color, width)` | 포커스 링 |

### 신규 토큰 그룹

`DesignSystemTheme.spacing` · `radius` · `dimension` · `primitive`. 3.x에는 dp 토큰이 없었습니다. `DesignSystemTheme { }` 안에서 자동 제공됩니다.

| 그룹 | 값 (dp) |
|---|---|
| `spacing` | 0, 2, 4, 6, 8, 10, 12, 14, 16, 20, 24, 32, 40, 48, 56, 64, 72, 80 |
| `radius` | 0, 4, 8, 10, 12, 14, 16, 20, 24, 28, 32, `radiusFull` |
| `dimension` | 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64 |
| `primitive` | 원시 값. 직접 쓰지 말고 위 세 그룹을 쓰세요 |

```kotlin
Box(
    modifier = Modifier
        .padding(DesignSystemTheme.spacing.spacing20)
        .clip(RoundedCornerShape(DesignSystemTheme.radius.radius12))
        .size(DesignSystemTheme.dimension.dimension40)
)
```

시맨틱 컬러 토큰도 새로 생긴 게 있습니다: `surfaceNeutralPrimary`, `surfaceBrandSubtle`, `surfaceNegativeStrong`, `surfaceAccent*`(반투명 8%), `lineBrand{Primary,Strong,Focus}`, `lineNegative{Primary,Strong,Focus}`, `lineAccent*`. 프리미티브 opacity 변형 리소스(`blue_50_opacity5` 등)도 추가됐습니다.

### 컴포넌트별 추가

| 컴포넌트 | 추가된 것 |
|---|---|
| `WantedButton` | `ButtonSize.XSMALL`, `ButtonType.NEGATIVE` (Solid 전용) |
| `WantedTextButton` | `WantedTextButtonColor`, `WantedTextButtonSize`, `WantedTextButtonDefault`, `WantedTextButtonDefaults.getDefault(...)` |
| `WantedIconButtonNormal` | `size`, `badgePosition`, `interactionEffect`(`Highlight`/`Dim`/`None`), `interactionColor`, `interactionOverflow` |
| `WantedIconButtonBackground` | `size`, `pushBadge`, `badgePosition`, `disableInteraction`, `interactionColor`, `useNormalInteraction` |
| `WantedIconButtonOutlined` · `Solid` | `pushBadge`, `badgePosition`, `disableInteraction`, `interactionColor`, `WantedIconButtonSize.Custom` |
| IconButton 공통 | `WantedIconButtonNormalSize`, `WantedIconButtonBackgroundSize`, `IconButtonBadgePosition`(9방향), `*.presets` |
| `WantedActionArea` | `captionIcon`, `WantedActionAreaDefaults.CAPTION_ICON` |
| `WantedTextField` | 입력 본체 오버로드(`value`, `error`, `complete`, …), `size` |
| `WantedTextArea` | 입력 본체 오버로드, `size`, `resize`(`Normal`/`Limit`/`Fixed`), `status` |
| `WantedSelect` | 본체 오버로드 4종, `size`, `status`, `leadingIcon`, `errorDataList` / `errorList` |
| `WantedSearchField` | `variant`(`Solid`/`Outlined`) |
| `WantedSegmentedControl` | `iconOnly`, `LocalWantedSegmentedIconOnly` |
| `WantedCheckBox` | `style`·`size`·`checkState`·`tight`를 받는 오버로드 공개 (3.7.0에서는 internal) |
| `Modifier.framedStyle` | `focused` |
| `WantedListCell` | `variant`, `cellDefault: WantedListCellDefault`(배경·shape·border·contentPadding), `labelTrailingContent`, `extraContent`, `enabledInnerTouch`, `WantedListCellDefaults.getDefault(...)` |
| `WantedCardDefault` | `backgroundColor`, `shape`, `border`, `interactionShape`, `contentPadding` |
| `WantedAvatar` · `WantedAvatarGroup` | `contentDescription`, `WantedAvatarGroupSize` |
| `WantedPushBadge` | `maxCount`, `outlineBorder`, `outlineBorderColor`, `textStyle`, `inset` |
| `WantedFallbackView` | `padding: WantedFallbackPadding` |
| `WantedModalBottomSheet` | `sheetDefault`, `WantedBottomSheetDefaults.getDefault()` · `getFullDefault()` · `getWithoutContentPadding(type)` |
| `WantedDialogTopAppBar` | `Variant.Search`, `Variant.Floating(iconBackground)`, `navigationPadding`, `NAVIGATION_PADDING` · `FULL_NAVIGATION_PADDING` |
| `WantedTopAppBarIconButton` | `interactionEffect` |
| `WantedCategory` | `WantedCategoryDefault`, `getDefault()` · `getChipDefault()`, `Variant`, `Size.rightIconSize` |
| `WantedPopover` | `screenEdgePadding` (기본 8) |
| `WantedTooltip` | `screenEdgePadding` (기본 2) |

---

## 체크리스트

### 치환

- [ ] 컬러 토큰 치환 후 옛 이름이 남지 않았는지 - `grep -rnE "(colors|colorsOpacity)\.(label|fill|interaction|inverse|status|accent|primary|material|backgroundNormal|backgroundElevated|backgroundTransparent|backgroundStatus|lineNormal|lineSolid|lineStatus|lineAlternative)" --include="*.kt" .`
- [ ] 옛 리소스 이름 - `grep -rnE "(R\.color\.|@color/)(label|fill|interaction|inverse|status|accent|primary|material|background_normal|background_elevated|background_transparent|background_status|line_normal|line_solid|line_status|line_alternative)_" --include="*.kt" --include="*.xml" .`
- [ ] sed에 `\b`를 쓰지 않았는지 (macOS BSD sed는 무시하고 **아무것도 안 바꿉니다**)
- [ ] `primaryNormal`을 일괄로 `surfaceBrandPrimary`로 보냈다면 텍스트·아이콘 자리를 `foregroundBrandPrimary`로 고쳤는지
- [ ] `accentBackgroundRedOrange`를 면(배경)으로 칠하던 자리 - 대응이 전경 토큰이라 `surface` 토큰으로 바꿔야 하는지
- [ ] `ButtonType` · `ButtonSize`를 `else` 없이 분기하는 `when` - 항목이 늘어 컴파일 에러
- [ ] `grep -rn "WantedActionArea(" -A20 --include="*.kt" . | grep -E "positive|negative|neutral|gradationColor"`
- [ ] `grep -rn "WantedChip(" -A10 --include="*.kt" . | grep -E "leftIcon|rightIcon"`
- [ ] `grep -rn "WantedTextButton(" -A8 --include="*.kt" . | grep -E "ButtonType\.|ButtonSize\."` - 전용 타입으로 옮겼는지
- [ ] `grep -rn "rightButton\|onClickRightButton\|RightVariant\|leftContent\|rightContent" --include="*.kt" .`
- [ ] `grep -rn "WantedTextArea(" -A25 --include="*.kt" . | grep "negative ="` - `status`로
- [ ] `grep -rn "isExpend\|WantedSegmentedControl\(Solid\|Outlined\)\|WantedSelectWithString" --include="*.kt" .`
- [ ] `grep -rn "WantedSegmentedControlItem(\"" --include="*.kt" .` - 위치 인자 호출
- [ ] `grep -rn "\(^\|[^A-Za-z]\)Size\.\(Medium\|Small\|Custom\)(" --include="*.kt" .` - SearchField `Medium()` → `Large`, `Small()` → `Medium`
- [ ] `grep -rn "fillWidth\s*=\|InteractionPadding\." --include="*.kt" .` - ListCell·Accordion `variant`로
- [ ] `grep -rn "WantedAvatarType\.Academic\|icon_avatar_placeholder_academic" --include="*.kt" .`
- [ ] `grep -rn "PushBadgeVariant\.\(New\|Number\)\|WantedPushBadgeBorder" --include="*.kt" .` - `MaxCount`로 옮긴 자리에 숫자 아닌 값이 없는지
- [ ] `grep -rn "WantedModal(" --include="*.kt" .` - `WantedPopup`으로
- [ ] `grep -rn "WantedModalBottomSheet(" -A12 --include="*.kt" . | grep "modalSize"`
- [ ] `grep -rn "DialogTopAppBarContract.Variant.Display\|Variant\.Floating\b[^(]" --include="*.kt" .`
- [ ] `grep -rn "LocalWantedTopBarIconVariant\|isAlternative\s*=" --include="*.kt" .`

### 구조

- [ ] TextField·TextArea·Select의 `title`·`requiredBadge`·`description`을 `WantedFormControl`로 옮겼는지 (빌드 경고 `is deprecated`로도 찾을 수 있습니다)
- [ ] `WantedFormControl.status`와 안쪽 컴포넌트 `error`/`status`를 같은 조건으로 줬는지
- [ ] TextArea 본체로 옮긴 곳에 글자수 `accessory`를 넣었는지, `minLines`·`resize` 기본값 차이를 확인했는지
- [ ] TextField 본체로 옮긴 곳의 `background` (기본값이 deprecated 오버로드와 다름)
- [ ] `grep -rn "WantedIconButtonNormal(\|WantedIconButtonBackground(" -A6 --include="*.kt" . | grep "Modifier.size("` - `modifier` 크기를 `size:`로 옮기고 지웠는지
- [ ] `grep -rn "WantedIconButton\(Outlined\|Solid\)(" -A6 --include="*.kt" . | grep "padding ="` - 제거된 `padding:` 오버로드
- [ ] ListCell 슬롯에 `@Composable () -> Unit` 변수를 그대로 넘기던 곳
- [ ] `WantedFallbackView` 사용처의 바깥 `padding(vertical =)` · `height()` 정리
- [ ] 두 버튼을 쓰던 `WantedModal` - `bottomBar` + `WantedActionArea`로 옮겼는지
- [ ] 본문 여백을 직접 주던 BottomSheet·Popup - `getWithoutContentPadding()` · `contentHorizontalPadding = 0.dp`

### 화면 확인

- [ ] `accentForeground{Blue,Orange}`를 쓰던 화면 (라이트·다크), `accentForegroundGreen` (라이트)
- [ ] 딤을 쓰는 다크 모드 화면 - 딤이 진해진 만큼 아래 콘텐츠가 너무 가려지지 않는지
- [ ] `labelAlternativeOpacity*` · `statusPositiveOpacity*`를 쓰던 자리
- [ ] RedOrange를 쓰던 자리의 색 (적주황 → 주황)
- [ ] Solid·Outlined 버튼 폭, `LARGE` TextButton 높이
- [ ] `WantedIconButtonNormal`을 쓰는 툴바·행 - 간격이 벌어졌다면 `interactionOverflow = true`
- [ ] Solid 활성 Chip·FilterButton (검정 → 옅은 파랑), 칩 줄·필터 바의 줄바꿈
- [ ] `extra`를 쓰는 ActionArea - 구분선·배경색, `alternative`·`Cancel` 버튼 색
- [ ] `maxLines`를 2 이상 넘기던 TextField, 우측 버튼이 있는 TextField·TextArea
- [ ] 에러 상태 TextField·Select - 느낌표 아이콘이 사라져도 의미가 전달되는지
- [ ] `ellipsis`를 명시하지 않은 ListCell - 긴 타이틀 줄바꿈, 슬롯 정렬, 비활성 셀의 이미지 슬롯, selected 자동 체크
- [ ] 이미지 없는 Avatar placeholder, Company·Academy `AvatarGroup`
- [ ] `WantedFallbackView` - 상하 160 여백이 바텀시트·리스트 안에서 넘치지 않는지
- [ ] 상단 바 없이 쓰던 버튼형 Popup - 닫기 버튼이 새로 생겨도 되는지
- [ ] Popup·BottomSheet 본문 좌우 여백이 이중으로 들어가지 않는지
- [ ] 모달 `Emphasized` 상단 바 높이·제목 위치
- [ ] radius가 커진 만큼(Popup 24, Sheet 32) 모서리에 닿는 이미지·리스트가 잘리지 않는지
- [ ] [4. 화면이 달라지는 것](#4-화면이-달라지는-것) 목록의 화면을 실기기/에뮬레이터에서 확인

스펙 변경은 `sample` 앱을 두 버전으로 빌드해 대조하는 게 가장 빠릅니다.

```bash
# 올라오기 전 버전
git worktree add --detach ../baseline v3.7.0
(cd ../baseline && ./gradlew :sample:installDebug)

# 올라갈 버전 - 같은 applicationId 라 덮어 설치된다. 화면을 캡처해 두고 번갈아 설치한다
./gradlew :sample:installDebug
```

두 빌드를 같은 기기에 번갈아 설치해 [4. 화면이 달라지는 것](#4-화면이-달라지는-것) 목록의 컴포넌트를 나란히 놓고 봅니다. 목록에 없는데 달라 보이는 게 있으면 문서가 빠뜨린 것이니 알려주세요.

---

## 부록: 컬러 토큰 치환 규칙 전체

`colors.*` · `colorsOpacity.*` 프로퍼티와 `R.color.*` · `@color/*` 리소스 이름을 한 번에 바꿉니다. macOS(BSD) sed 기준이며, GNU sed라면 `-i ''`를 `-i`로, `[[:<:]]`·`[[:>:]]`를 `\b`로 바꿉니다.

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

3.0 이전 버전에서 올라오는 경우는 [릴리즈 노트](https://github.com/wanteddev/montage-android/releases)를 참고해주세요.
