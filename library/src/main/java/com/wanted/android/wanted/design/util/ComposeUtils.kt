package com.wanted.android.wanted.design.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.theme.pretendardBold

@Composable
fun FontFixTextStyle(
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontSynthesis: FontSynthesis? = null,
    fontFeatureSettings: String? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    baselineShift: BaselineShift? = null,
    textGeometricTransform: TextGeometricTransform? = null,
    localeList: LocaleList? = null,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    shadow: Shadow? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    textDirection: TextDirection = TextDirection.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textIndent: TextIndent? = null,
) = TextStyle(
    color = color,
    fontSize = fontSize,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    fontSynthesis = fontSynthesis,
    fontFamily = pretendardBold,
    fontFeatureSettings = fontFeatureSettings,
    letterSpacing = letterSpacing,
    baselineShift = baselineShift,
    textGeometricTransform = textGeometricTransform,
    localeList = localeList,
    background = background,
    textDecoration = textDecoration,
    shadow = shadow,
    textAlign = textAlign,
    textDirection = textDirection,
    lineHeight = lineHeight,
    textIndent = textIndent,
)

@Composable
fun WantedTextStyle(colorRes: Int, style: TextStyle, alpha: Float? = null) = style.merge(
    TextStyle(
        color = colorResource(
            id = colorRes
        ).let { color ->
            alpha?.let { color.copy(alpha = it) } ?: color
        }
    )
)

@Composable
fun wantedRippleEffect(
    color: Color = DesignSystemTheme.colorsOpacity.labelNormalOpacity12,
) = ripple(
    color = color,
)

@Composable
fun Int.dpToPx() = this.toFloat().dpToPx().toInt()

@Composable
fun Float.dpToPx() = LocalDensity.current.run { this@dpToPx.dp.toPx() }

@Composable
fun Int.pxToDp() = LocalDensity.current.run { this@pxToDp.toDp() }

@Composable
fun Dp.dpToSp() = with(LocalDensity.current) { this@dpToSp.toSp() }

@Composable
fun String.bold(subText: String): AnnotatedString {
    val start = this.indexOf(subText)
    val spanStyles = listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold),
            start = start,
            end = start + subText.length
        )
    )

    return AnnotatedString(text = this, spanStyles = spanStyles)
}

@Composable
fun String.bold(vararg subText: String): AnnotatedString {
    val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
    subText.forEach {
        var start = this.indexOf(it, 0)
        while(start != -1) {
            spanStyles.add(
                AnnotatedString.Range(
                    SpanStyle(fontWeight = FontWeight.Bold),
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
fun String.toAnnotatedString(): AnnotatedString  {
    val spanned = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)
    return buildAnnotatedString {
        append(spanned.toString())

        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            when (span) {
                is StyleSpan -> when (span.style) {
                    Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
                }

                is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
            }
        }
    }
}

@Composable
fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }

            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
@Composable
fun hasSoftNavigationBar(): Boolean {
    val resources = LocalContext.current.resources
    val resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return resourceId > 0 && resources.getBoolean(resourceId)
}