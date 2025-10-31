package com.wanted.android.wanted.design.actions.chip.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * open class WantedChipDefault
 *
 * Chip 컴포넌트의 기본 스타일을 정의하는 베이스 클래스입니다.
 *
 * 이 클래스는 다양한 Chip 타입들이 상속하여 사용할 수 있는 공통 속성을 제공합니다.
 * 활성화 여부, 사용 가능 여부, 아이콘 색상, 배경 색상, 테두리 색상, 텍스트 스타일을 포함합니다.
 *
 * @param isActive Boolean: Chip의 활성화(선택) 여부입니다.
 * @param isEnable Boolean: Chip의 사용 가능 여부입니다.
 * @param iconColor Color: 아이콘의 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다.
 * @param borderColor Color: 테두리 색상입니다.
 * @param textStyle TextStyle: 텍스트 스타일입니다.
 */
open class WantedChipDefault(
    open val isActive: Boolean = false,
    open val isEnable: Boolean = true,
    open val iconColor: Color,
    open val backgroundColor: Color,
    open val borderColor: Color,
    open val textStyle: TextStyle
)