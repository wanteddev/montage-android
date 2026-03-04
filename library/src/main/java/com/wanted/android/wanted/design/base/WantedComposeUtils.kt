package com.wanted.android.wanted.design.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5

@Composable
fun String.accent(
    subText: String,
    textStyle: TextStyle
): AnnotatedString {
    val spanStyle = SpanStyle(
        fontFamily = textStyle.fontFamily,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        letterSpacing = textStyle.letterSpacing,
        color = textStyle.color
    )

    return buildAnnotatedString {
        var startIndex = 0
        while (startIndex < this@accent.length) {
            val index = this@accent.indexOf(subText, startIndex)
            if (index == -1) {
                append(this@accent.substring(startIndex))
                break
            } else {
                append(this@accent.substring(startIndex, index))
                withStyle(style = spanStyle) {
                    append(subText)
                }
                startIndex = index + subText.length
            }
        }
    }
}

@Composable
fun String.accent(
    vararg subText: String,
    textStyle: TextStyle
): AnnotatedString {
    val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
    subText.forEach {
        var start = this.indexOf(it, 0)
        while (start != -1) {
            spanStyles.add(
                AnnotatedString.Range(
                    SpanStyle(
                        fontFamily = textStyle.fontFamily,
                        fontSize = textStyle.fontSize,
                        fontWeight = textStyle.fontWeight,
                        letterSpacing = textStyle.letterSpacing,
                        color = textStyle.color
                    ),
                    start = start,
                    end = start + it.length
                )
            )
            start = this.indexOf(it, start + it.length)
        }
    }

    return AnnotatedString(text = this, spanStyles = spanStyles)
}

@Composable
fun Modifier.getBorderModifier(
    size: Dp,
    isCircleShape: Boolean,
    borderType: BorderType,
    cornerRadius: Dp = 0.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = DesignSystemTheme.colors.staticWhite,
    innerBorderColor: Color = DesignSystemTheme.colors.lineNormalAlternative,
    backgroundColor: Color = DesignSystemTheme.colors.staticWhite,
) = this.then(
    when (borderType) {
        BorderType.None -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = backgroundColor)
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
            }
        }

        BorderType.OutLine -> {
            val localDensity = LocalDensity.current
            Modifier
                .drawBehind {
                    if (isCircleShape) {
                        drawCircle(
                            color = borderColor,
                            radius = with(localDensity) { (size + borderWidth * 2).toPx() } / 2,
                            center = center,
                            style = Fill
                        )
                    } else {
                        drawRoundRect(
                            color = borderColor,
                            topLeft = Offset(
                                -with(localDensity) { borderWidth.toPx() },
                                -with(localDensity) { borderWidth.toPx() }),
                            size = Size(
                                with(localDensity) { (size + borderWidth * 2).toPx() },
                                with(localDensity) { (size + borderWidth * 2).toPx() }),
                            cornerRadius = CornerRadius(with(localDensity) { (cornerRadius + borderWidth).toPx() }),
                            style = Fill
                        )
                    }
                }
                .background(
                    color = backgroundColor,
                    shape = if (isCircleShape) {
                        CircleShape
                    } else {
                        RoundedCornerShape(cornerRadius)
                    }
                )
        }

        BorderType.InnerLine -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = backgroundColor)
                    .border(
                        width = borderWidth,
                        color = innerBorderColor,
                        shape = CircleShape
                    )
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
                    .border(
                        width = borderWidth,
                        color = innerBorderColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            }
        }
    }
)

enum class BorderType {
    None,
    InnerLine,
    OutLine
}
