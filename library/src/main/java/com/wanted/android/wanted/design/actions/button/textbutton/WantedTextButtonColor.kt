package com.wanted.android.wanted.design.actions.button.textbutton

/**
 * enum class WantedTextButtonColor
 *
 * [WantedTextButton]의 색 축입니다.
 *
 * Figma Text Button 컴포넌트의 `color` 속성과 1:1로 대응하며 PRIMARY / ASSISTIVE 두 가지만 존재합니다.
 * Foreground/Neutral/Secondary 처럼 두 값으로 표현되지 않는 색은 축을 늘리지 않고
 * [WantedTextButtonDefaults.getDefault]의 `contentColor`로 덮어 사용합니다.
 *
 * - PRIMARY: 브랜드 색(foregroundBrandPrimary)을 사용하는 기본 색입니다.
 * - ASSISTIVE: 보조 색(foregroundNeutralTertiary)을 사용합니다.
 */
enum class WantedTextButtonColor {
    PRIMARY,
    ASSISTIVE
}
