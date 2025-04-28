package com.wanted.android.wanted.design.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize

/**
 * 버튼의 형태를 정의하는 enum 클래스입니다.
 * - SOLID : 배경이 채워진 기본형 버튼
 * - OUTLINED : 테두리만 있는 버튼
 * - TEXT : 텍스트만 표시되는 버튼
 */
enum class ButtonShape {
    SOLID, OUTLINED, TEXT
}

/**
 * 버튼의 활성화 상태를 정의하는 enum 클래스입니다.
 * - ENABLE : 활성 상태
 * - DISABLE : 비활성 상태
 */
enum class ButtonStatus {
    ENABLE, DISABLE
}

/**
 * 버튼의 유형을 정의하는 enum 클래스입니다.
 * - PRIMARY : 주요 액션에 사용
 * - SECONDARY : 보조 액션에 사용
 * - ASSISTIVE : 보조 정보나 옵션에 사용
 */
enum class ButtonType {
    PRIMARY, SECONDARY, ASSISTIVE
}

/**
 * 버튼의 크기를 정의하는 enum 클래스입니다.
 * - LARGE : 큰 버튼
 * - MEDIUM : 중간 크기 버튼
 * - SMALL : 작은 버튼
 */
enum class ButtonSize {
    LARGE, MEDIUM, SMALL
}

@Composable
internal fun getButtonWidth(buttonWidth: Int): Modifier =
    when (buttonWidth) {
        -1 -> Modifier.fillMaxWidth()
        -2 -> Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
        else -> Modifier.width(buttonWidth.dp)
    }

@Composable
internal fun getTextButtonSize(buttonWidth: Int, buttonHeight: Int): Modifier {
    val widthModifier = getButtonWidth(buttonWidth = buttonWidth)
    val heightModifier = when (buttonHeight) {
        -1 -> Modifier.fillMaxHeight()
        -2 -> Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        else -> Modifier.height(buttonHeight.dp)
    }

    return Modifier
        .then(widthModifier)
        .then(heightModifier)
}

internal fun getButtonHeight(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 48.dp else 40.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 32.dp
    }

internal fun getButtonRadius(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 12.dp else 10.dp
        ButtonSize.MEDIUM -> 10.dp
        ButtonSize.SMALL -> 8.dp
    }

internal fun getButtonSpaceBetweenTextAndIcon(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 6.dp else 5.dp
        ButtonSize.MEDIUM -> 5.dp
        ButtonSize.SMALL -> 4.dp
    }

@Composable
internal fun getButtonTypography(
    shape: ButtonShape,
    type: ButtonType,
    size: ButtonSize
): TextStyle =
    getTextStyle(
        textStyle =
        when (shape) {
            ButtonShape.TEXT -> when (size) {
                ButtonSize.SMALL -> WantedTextStyle.LABEL1_BOLD
                else -> WantedTextStyle.BODY1_BOLD
            }

            else -> if (type == ButtonType.ASSISTIVE) {
                when (size) {
                    ButtonSize.LARGE -> WantedTextStyle.BODY1_BOLD
                    ButtonSize.MEDIUM -> WantedTextStyle.BODY2_BOLD
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_BOLD
                }
            } else {
                when (size) {
                    ButtonSize.LARGE -> WantedTextStyle.BODY1_MEDIUM
                    ButtonSize.MEDIUM -> WantedTextStyle.BODY2_MEDIUM
                    ButtonSize.SMALL -> WantedTextStyle.LABEL2_MEDIUM
                }
            }
        }
    )

@Composable
internal fun getButtonHorizontalPadding(
    shape: ButtonShape,
    size: ButtonSize
): Dp =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) 28.dp else 20.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.SMALL -> 14.dp
    }

@Composable
internal fun getButtonDrawableSize(
    shape: ButtonShape,
    size: ButtonSize
): Modifier =
    when (size) {
        ButtonSize.LARGE -> if (isLargeSizeButtonExist(shape)) Modifier
            .height(20.dp)
            .wrapContentWidth()
        else Modifier
            .height(18.dp)
            .wrapContentWidth()

        ButtonSize.MEDIUM -> Modifier
            .height(18.dp)
            .wrapContentWidth()

        ButtonSize.SMALL -> Modifier
            .height(16.dp)
            .wrapContentWidth()
    }

private fun isLargeSizeButtonExist(shape: ButtonShape): Boolean =
    when (shape) {
        ButtonShape.SOLID -> true
        ButtonShape.OUTLINED -> true
        ButtonShape.TEXT -> false
    }

@Composable
internal fun getContentBadgeDrawableSize(
    size: ContentBadgeSize
): Modifier =
    when (size) {
        ContentBadgeSize.Large -> Modifier
            .height(16.dp)
            .wrapContentWidth()

        ContentBadgeSize.Small -> Modifier
            .height(14.dp)
            .wrapContentWidth()

        ContentBadgeSize.XSmall -> Modifier
            .size(12.dp)
            .wrapContentWidth()
    }