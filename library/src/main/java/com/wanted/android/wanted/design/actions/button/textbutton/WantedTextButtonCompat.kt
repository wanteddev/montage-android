package com.wanted.android.wanted.design.actions.button.textbutton

import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType

// 폐기된 ButtonVariant.TEXT 경유 호출부(WantedButton(variant = ButtonVariant.TEXT, ...))를
// WantedTextButton의 전용 축으로 옮기기 위한 매퍼입니다.
// 신규 코드는 WantedTextButtonColor / WantedTextButtonSize 를 직접 사용하고 이 매퍼를 쓰지 않습니다.
internal fun ButtonType.toWantedTextButtonColor(): WantedTextButtonColor = when (this) {
    ButtonType.ASSISTIVE -> WantedTextButtonColor.ASSISTIVE
    // NEGATIVE는 Text Button 스펙에 없어 예전부터 PRIMARY와 같은 색으로 렌더링됐다.
    ButtonType.PRIMARY, ButtonType.NEGATIVE -> WantedTextButtonColor.PRIMARY
}

internal fun ButtonSize.toWantedTextButtonSize(): WantedTextButtonSize = when (this) {
    ButtonSize.LARGE -> WantedTextButtonSize.LARGE
    ButtonSize.MEDIUM -> WantedTextButtonSize.MEDIUM
    ButtonSize.SMALL -> WantedTextButtonSize.SMALL
    ButtonSize.XSMALL -> WantedTextButtonSize.XSMALL
}
