package com.wanted.android.wanted.design.actions.button.textbutton

/**
 * enum class WantedTextButtonSize
 *
 * [WantedTextButton]의 크기 축입니다.
 *
 * Figma Text Button 컴포넌트의 `size` 속성은 SMALL / MEDIUM 두 가지이며, 신규 코드는 이 둘만 사용합니다.
 * LARGE / XSMALL은 [com.wanted.android.wanted.design.util.ButtonVariant.TEXT] 경유 호출부의
 * 기존 렌더링을 유지하기 위한 레거시 값이므로 신규 사용을 금지합니다.
 *
 * - SMALL: 최소 높이 28dp · label1Bold 입니다.
 * - MEDIUM: 최소 높이 32dp · body1Bold 입니다.
 * - LARGE: 공식 미지원(레거시). 최소 높이 40dp · body1Bold 입니다.
 * - XSMALL: 공식 미지원(레거시). 최소 높이 28dp · label1Bold 이며 아이콘 크기만 SMALL과 다릅니다.
 */
enum class WantedTextButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
    XSMALL
}
