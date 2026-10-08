/**
* enum class IconButtonBadgePosition
*
* IconButton 위에 표시되는 PushBadge 의 정렬 기준점을 정의합니다.
*
* - TopLeft: 좌측 상단입니다.
* - TopCenter: 상단 중앙입니다.
* - TopRight: 우측 상단입니다.
* - MiddleLeft: 좌측 중앙입니다.
* - MiddleCenter: 정중앙입니다.
* - MiddleRight: 우측 중앙입니다.
* - BottomLeft: 좌측 하단입니다.
* - BottomCenter: 하단 중앙입니다.
* - BottomRight: 우측 하단입니다.
*
* 정렬 기준이 되는 모서리는 variant 마다 다릅니다.
* Normal variant 는 아이콘 모서리 기준으로 정렬되며 `(boxSize - iconSize) / 2` 만큼 inset 보정이 적용되고,
* Background / Outlined / Solid variant 는 박스 모서리 기준으로 정렬되며 inset 보정이 없습니다.
*/

/** IconButton variant별 ripple 강도입니다. Light 0.08(normal·background·outlined) / Normal 0.12(background alternative) / Strong 0.20(solid). */

/** IconButton용 Indication을 반환합니다. [disabled] 이면 null 을 반환해 press/hover 효과를 끕니다. */

/** IconButton ripple 색을 반환합니다. [strength] 에 해당하는 불투명도를 [color] 에 적용합니다. ripple 을 직접 만들지 않고 색만 넘겨야 하는 경로(`WantedTouchArea` 의 `rippleColor` 등)에서 [iconButtonIndication] 과 같은 값을 쓰기 위한 진입점입니다. */

/**
* enum class IconButtonInteractionEffect
*
* IconButton 의 인터랙션 피드백 방식을 정의합니다. (Figma 4.0.0 IconButton 스펙)
*
* 인터랙션 차단 여부(불리언)와 피드백 방식을 따로 다루던 두 속성을 하나로 합친 값입니다.
* 값 이름은 Figma 4.0.0 컴포넌트 variant(Highlight / Dim / None) 및 iOS 와 동일하게 맞춥니다.
*
* - Highlight: 아이콘 뒤에 인터랙션 레이어(ripple)를 표시합니다. 기본값입니다.
* - Dim: 레이어 대신 아이콘 투명도를 낮춥니다. Top Navigation 처럼 레이어 형태가 어색한 자리에 씁니다. Normal variant 에만 제공합니다.
* - None: 인터랙션 피드백이 없습니다.
*
* 세 값 모두 클릭·터치 영역은 같습니다. 시각 피드백만 달라지고 Hit area = 박스 100% 규정은 그대로 유지됩니다.
*/

/** 아이콘 뒤 인터랙션 레이어(ripple) 표시 여부입니다. */

/** 눌림 상태에서 아이콘 투명도를 낮추는지 여부입니다. */

/** deprecated 된 `disableInteraction` 파라미터를 InteractionEffect 로 변환합니다. */

/** 인터랙션 피드백 색을 결정합니다. [interactionColor] 미지정 시 [Dim] 은 [tint] 를, 나머지는 [rippleFallback] 을 기준으로 합니다. */

/** Dim 눌림 상태의 아이콘 색입니다. 알파에 22%(Figma Pressed 레이어 불투명도)를 곱하며, Hover 가 없는 Android 는 Hovered(52%)를 구현하지 않습니다. */

/**
* fun WantedIconButtonBackground(...)
*
* 콘텐츠 위에 떠 있는 floating 아이콘 버튼. 이미지·미디어 등 오버레이 액션에 사용합니다.
* (Confluence IconButton 스펙 §3·§4)
*
* [size] 는 박스(컨테이너) 크기이며, 아이콘은 박스에서 자동 산출됩니다.
* - 아이콘 = `nearestDimensionToken(box × 2/3, tie → down)`
* - Border radius = `CircleShape` (radius.full)
* - 박스 = `clamp(24dp, N, 64dp)`, 글래스 레이어는 박스를 정확히 채움 (`inset: 0`)
* - Hit area = 박스 100%
*
* background 는 단일 [WantedIconButtonBackgroundSize.Default] (box 32dp, icon 20dp) preset 으로 수렴하며,
* 박스 커스텀은 [WantedIconButtonBackgroundSize.Custom] 으로 지정합니다.
*
* 사용 예시:
* ```kotlin
* WantedIconButtonBackground(
*     icon = R.drawable.ic_icon,
*     // size 미지정 → Default(box 32dp) 기본값
*     alternative = false,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param icon Int: 아이콘 drawable 리소스 ID
* @param modifier Modifier: 외형 및 배치를 제어하는 Modifier
* @param alternative Boolean: true 일 경우 다크 오버레이 위 사용 변형
* @param enabled Boolean: 활성화 여부
* @param size WantedIconButtonBackgroundSize: 박스 크기. 기본값 [WantedIconButtonBackgroundSize.Default]
* @param tint Color: 아이콘 색상 (기본값은 alternative 여부에 따라 자동 결정)
* @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트. 박스 모서리 기준으로 정렬됩니다.
* @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
* @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음. 기본값 false
* @param interactionColor Color: ripple 색상. 기본값 `foregroundNeutralPrimary`
* @param useNormalInteraction Boolean: true이면 light 대신 normal 강도 ripple 적용. 기본값 false
* @param onClick () -> Unit: 클릭 콜백
*/

/**
* sealed class WantedIconButtonBackgroundSize
*
* `WantedIconButtonBackground` 의 박스(컨테이너) 크기입니다. (Confluence IconButton 스펙 §3·§4)
*
* background 는 string preset 을 두지 않고 단일 [Default] preset 으로 수렴하며,
* 박스 커스텀은 [Custom] 으로만 지정합니다. 아이콘은 `box × 2/3` → dimension 토큰 스냅으로 자동 산출되고,
* 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
*
* - [Default] : box 32dp (기본값) → icon 20dp
* - [Custom]  : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
*/

/** preset(데이터 오브젝트) 목록. */

/**
* WantedIconButtonNormal
*
* 배경 없이 아이콘만 표시하는 기본 아이콘 버튼입니다.
*
* [size] 로 지정한 박스 크기에서 아이콘과 모서리 반경이 자동 산출되며, 인터랙션 피드백은 [interactionEffect] 로 지정합니다.
*
* 기본값에서는 레이아웃이 차지하는 크기가 박스(= 인터랙션 영역) 크기와 같습니다.
* 아이콘 크기만 차지하고 인터랙션 영역만 바깥으로 넘치게 하려면 [interactionOverflow] 를 켭니다.
*
* 사용 예시 :
* ```kotlin
* WantedIconButtonNormal(
*     icon = R.drawable.ic_icon,
*     size = WantedIconButtonNormalSize.Xlarge,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param icon Int: 아이콘 drawable 리소스 ID
* @param modifier Modifier: 외형 및 배치를 제어하는 Modifier (size 는 자동 산출되므로 외부 정렬/패딩 용도로 사용)
* @param size WantedIconButtonNormalSize: 박스 크기. 기본값 Xlarge, preset 외 크기는 [WantedIconButtonNormalSize.Custom] 으로 지정. [interactionOverflow] 가 true 이면 아이콘 크기를 뜻합니다(preset 은 24 · 20 · 18 · 16, Custom 은 숫자 그대로)
* @param enabled Boolean: 활성화 여부
* @param tint Color: 활성 시 아이콘 색상. 기본값 `foregroundNeutralPrimary`. 비활성 시 `foregroundDisablePrimary`
* @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트
* @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 TopRight, 아이콘 모서리 기준으로 inset 보정 적용
* @param interactionEffect IconButtonInteractionEffect: 인터랙션 피드백 방식. 기본값 [IconButtonInteractionEffect.Highlight]
* @param interactionColor Color: 인터랙션 피드백 색상. 미지정 시 ripple 은 `foregroundNeutralPrimary`, Dim 은 [tint] 를 기준으로 함
* @param interactionOverflow Boolean: true 이면 레이아웃 크기가 아이콘 크기가 되고, 인터랙션 영역 `max(24, ceil(아이콘 × 1.5 ÷ 4) × 4)` 이 상하좌우로 `(영역 - 아이콘) / 2` 만큼 넘칩니다. 기본값 false
* @param onClick () -> Unit: 클릭 콜백
*/

/**
* IconButtonNormalContent
*
* 아이콘과 PushBadge 를 그립니다. 배지는 [badgePosition] 기준으로 정렬한 뒤 [badgeInset] 만큼 안쪽으로 당깁니다.
*
* 컨테이너 크기는 [modifier] 로 **항상 명시**합니다. 크기를 주지 않으면 wrapContent 가 되어
* 아이콘보다 큰 [pushBadge] 가 컨테이너를 키웁니다.
*
* @param icon Int: 아이콘 drawable 리소스 ID
* @param iconSize Dp: 아이콘 크기
* @param iconColor Color: 아이콘 색상 (enabled·인터랙션 상태가 반영된 최종 색)
* @param pushBadge @Composable (() -> Unit)?: 표시할 PushBadge 등 컴포넌트
* @param badgePosition IconButtonBadgePosition: 배지 정렬 기준점
* @param badgeInset Dp: 배지 inset 보정값. 컨테이너가 아이콘 크기와 같으면 0
* @param modifier Modifier: 컨테이너 크기·클립·클릭을 지정하는 Modifier
*/

/**
* WantedIconButtonNormal
*
* `disableInteraction` 을 사용하는 이전 시그니처입니다.
* [IconButtonInteractionEffect] 로 대체되었으니 신규 코드에서는 사용하지 않습니다.
*
* @param icon Int: 아이콘 drawable 리소스 ID
* @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음
* @param modifier Modifier: 외형 및 배치를 제어하는 Modifier
* @param size WantedIconButtonNormalSize: 박스 크기. 기본값 [WantedIconButtonNormalSize.Xlarge]
* @param enabled Boolean: 활성화 여부
* @param tint Color: 활성 시 아이콘 색상. 기본값 `foregroundNeutralPrimary`. 비활성 시 `foregroundDisablePrimary`
* @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트
* @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
* @param interactionColor Color: 인터랙션 피드백 색상. 미지정 시 ripple 은 `foregroundNeutralPrimary`
* @param onClick () -> Unit: 클릭 콜백
*/

/**
* sealed class WantedIconButtonNormalSize
*
* `WantedIconButtonNormal` 의 박스(컨테이너) 크기입니다. (Confluence IconButton 스펙 §3·§4)
*
* `size` 는 박스 크기를 뜻하며, 아이콘·radius 는 박스에서 자동 산출됩니다
* (`icon = box × 2/3` → dimension 토큰 스냅, `radius = box × 0.3` → radius 토큰 스냅).
* 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
*
* - [Xlarge] : box 36dp (기본값) → icon 24dp, radius 10dp
* - [Large]  : box 32dp → icon 20dp, radius 10dp
* - [Medium] : box 28dp → icon 18dp, radius 8dp
* - [Small]  : box 24dp → icon 16dp, radius 8dp
* - [Custom] : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
*
* `interactionOverflow = true` 이면 size 는 **아이콘 크기**를 뜻합니다(Web 방식, WRP-3163).
* 인터랙션 영역은 아이콘에서 `max(24, ceil(icon × 1.5 ÷ 4) × 4)` 로 계산합니다.
* - preset 은 위 표의 아이콘 크기(24 · 20 · 18 · 16)를 쓰므로 인터랙션 영역은 36 · 32 · 28 · 24 로 같습니다.
* - [Custom] 은 숫자가 그대로 아이콘 크기가 됩니다. 예) Custom(20.dp) → icon 20dp, 인터랙션 영역 32dp
*/

/** preset(데이터 오브젝트) 목록 — 큰 → 작은 순. */

/**
* WantedIconButtonOutlined
*
* WantedIconButtonSize를 기반으로 하는 Outlined 스타일의 아이콘 버튼입니다.
* (Confluence IconButton 스펙 §3 — 2026-06-24)
*
* 아이콘 크기 산출: `icon = round(box × 0.47)` → dimension 토큰 스냅(동률이면 작은 값).
* 중앙정렬 inset = `(box - icon) / 2`. padding 방식 제거.
* Radius = `radius.full` (CircleShape). 박스는 `clamp(24dp, N, 64dp)`.
*
* 사용 예시:
* ```kotlin
* WantedIconButtonOutlined(
*     icon = R.drawable.ic_icon,
*     size = WantedIconButtonSize.Medium,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param icon Int: 버튼에 표시할 drawable 리소스 ID입니다.
* @param size WantedIconButtonSize: 박스 크기를 지정하는 sealed class입니다. preset(Medium/Small) 외 박스는 [WantedIconButtonSize.Custom] 으로 지정합니다.
* @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
* @param enabled Boolean: 버튼의 활성화 여부입니다.
* @param outlineColor Color: 활성화 상태의 외곽선 색상입니다.
* @param disableOutlineColor Color: 비활성 상태의 외곽선 색상입니다.
* @param tint Color: 활성 상태의 아이콘 색상입니다.
* @param disableTint Color: 비활성 상태의 아이콘 색상입니다.
* @param background Color: 활성 상태의 배경 색상입니다.
* @param disableBackground Color: 비활성 상태의 배경 색상입니다. 기본값 `backgroundNeutralPrimary`
* @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트. 박스 모서리 기준으로 정렬됩니다.
* @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
* @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음. 기본값 false
* @param interactionColor Color: ripple 색상. 기본값 `foregroundNeutralPrimary`
* @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
*/

/**
* sealed class WantedIconButtonSize
*
* `WantedIconButtonOutlined` / `WantedIconButtonSolid` 의 박스(컨테이너) 크기입니다.
* (Confluence IconButton 스펙 §3·§4)
*
* `size` 는 박스 크기를 뜻하며, 아이콘은 `box × 0.47` → dimension 토큰 스냅(동률 → 작은 값)으로 자동 산출됩니다.
* radius 는 full(CircleShape), 박스는 `clamp(24dp, N, 64dp)` 범위로 클램프됩니다.
*
* - [Medium] : box 40dp (기본값) → icon 18dp
* - [Small]  : box 32dp → icon 16dp
* - [Custom] : 임의 박스 크기. `copy` 로 커스텀할 수 있습니다.
*/

/** preset(데이터 오브젝트) 목록 — 큰 → 작은 순. */

/**
* WantedIconButtonSolid
*
* WantedIconButtonSize를 사용하여 간편하게 크기와 패딩을 지정할 수 있는 Solid 스타일 아이콘 버튼입니다.
* (Confluence IconButton 스펙 §3 — 2026-06-24)
*
* 아이콘 크기 산출: `icon = round(box × 0.47)` → dimension 토큰 스냅(동률이면 작은 값).
* 중앙정렬 inset = `(box - icon) / 2`. padding 방식 제거.
* Radius = `radius.full` (CircleShape). 박스는 `clamp(24dp, N, 64dp)`.
*
* 사용 예시:
* ```kotlin
* WantedIconButtonSolid(
*     icon = R.drawable.ic_icon,
*     size = WantedIconButtonSize.Medium,
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param icon Int: 아이콘으로 사용할 drawable 리소스 ID입니다.
* @param size WantedIconButtonSize: 박스 크기를 정의하는 sealed class입니다. preset(Medium/Small) 외 박스는 [WantedIconButtonSize.Custom] 으로 지정합니다.
* @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
* @param enabled Boolean: 버튼의 활성화 여부입니다.
* @param tint Color: 아이콘의 색상입니다. 기본값은 흰색입니다.
* @param background Color: 배경 색상입니다. 기본값은 surfaceBrandPrimary입니다.
* @param pushBadge @Composable (() -> Unit)?: 지정 위치에 표시할 PushBadge 등 컴포넌트. 박스 모서리 기준으로 정렬됩니다.
* @param badgePosition IconButtonBadgePosition: pushBadge 정렬 기준점. 기본값 [IconButtonBadgePosition.TopRight]
* @param disableInteraction Boolean: true이면 press/hover ripple 효과 없음. 기본값 false
* @param interactionColor Color: ripple 색상. 기본값 `foregroundNeutralPrimary`
* @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
*/