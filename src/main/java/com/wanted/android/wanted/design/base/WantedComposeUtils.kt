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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
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
fun Modifier.getBoarderModifier(
    size: Dp,
    isCircleShape: Boolean,
    boarderType: BoarderType,
    cornerRadius: Dp = 0.dp,
    boarderWidth: Dp = 1.dp,
    boarderColor: Color = colorResource(id = R.color.label_normal).copy(OPACITY_5),
    backgroundColor: Color = colorResource(id = R.color.static_white)
) = this.then(
    when (boarderType) {
        BoarderType.None -> {
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

        BoarderType.OutLine -> {
            val localDensity = LocalDensity.current
            Modifier
                .drawBehind {
                    if (isCircleShape) {
                        drawCircle(
                            color = boarderColor,
                            radius = with(localDensity) { (size + boarderWidth * 2).toPx() } / 2,
                            center = center,
                            style = Fill
                        )
                    } else {
                        drawRoundRect(
                            color = boarderColor,
                            topLeft = Offset(
                                -with(localDensity) { boarderWidth.toPx() },
                                -with(localDensity) { boarderWidth.toPx() }),
                            size = Size(
                                with(localDensity) { (size + boarderWidth * 2).toPx() },
                                with(localDensity) { (size + boarderWidth * 2).toPx() }),
                            cornerRadius = CornerRadius(with(localDensity) { (cornerRadius + boarderWidth).toPx() }),
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

        BoarderType.InnerLine -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = backgroundColor)
                    .border(
                        width = boarderWidth,
                        color = boarderColor,
                        shape = CircleShape
                    )
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
                    .border(
                        width = boarderWidth,
                        color = boarderColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            }
        }
    }
)


enum class BoarderType {
    None,
    InnerLine,
    OutLine
}
