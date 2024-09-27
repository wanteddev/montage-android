package com.wanted.android.wanted.design.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

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
