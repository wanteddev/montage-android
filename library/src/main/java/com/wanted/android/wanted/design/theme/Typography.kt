package com.wanted.android.wanted.design.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.wanted.android.designsystem.R

val pretendard = FontFamily(
    Font(R.font.regular, FontWeight.W400),
    Font(R.font.medium, FontWeight.W500),
    Font(R.font.semi_bold, FontWeight.W600),
)

val pretendardBold = FontFamily(
    Font(R.font.regular, FontWeight.W400),
    Font(R.font.medium, FontWeight.W500),
    Font(R.font.bold, FontWeight.W700),
)

@Immutable
data class WantedTypography(
    val display1Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 56.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0319).em,
        lineHeight = 72.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display1Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 56.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.0319).em,
        lineHeight = 72.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display1Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 56.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0319).em,
        lineHeight = 72.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display2Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 40.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0282).em,
        lineHeight = 52.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display2Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 40.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.0282).em,
        lineHeight = 52.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display2Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 40.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0282).em,
        lineHeight = 52.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display3Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 36.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.027).em,
        lineHeight = 48.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display3Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 36.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.027).em,
        lineHeight = 48.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val display3Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 36.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.027).em,
        lineHeight = 48.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title1Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 32.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0253).em,
        lineHeight = 44.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title1Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 32.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.0253).em,
        lineHeight = 44.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title1Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 32.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0253).em,
        lineHeight = 44.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title2Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 28.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0236).em,
        lineHeight = 38.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title2Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 28.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.0236).em,
        lineHeight = 38.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title2Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 28.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0236).em,
        lineHeight = 38.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title3Regular: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.023).em,
        lineHeight = 32.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title3Medium: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 24.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.023).em,
        lineHeight = 32.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val title3Bold: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 24.sp,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.023).em,
        lineHeight = 32.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0194).em,
        lineHeight = 30.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 22.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.0194).em,
        lineHeight = 30.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 22.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = (-0.0194).em,
        lineHeight = 30.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.012).em,
        lineHeight = 28.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.012).em,
        lineHeight = 28.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val heading2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = (-0.012).em,
        lineHeight = 28.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.002).em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = (-0.002).em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = (-0.002).em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 17.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 17.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val headline2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 17.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0057.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0057.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0057.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1ReadingRegular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0057.em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1ReadingMedium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0057.em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body1ReadingBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0057.em,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0096.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0096.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0096.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2ReadingRegular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0096.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2ReadingMedium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0096.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val body2ReadingBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 15.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0096.em,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0145.em,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0145.em,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0145.em,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1ReadingRegular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0145.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1ReadingMedium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0145.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label1ReadingBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0145.em,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0194.em,
        lineHeight = 18.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0194.em,
        lineHeight = 18.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val label2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 13.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0194.em,
        lineHeight = 18.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0252.em,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0252.em,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0252.em,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 11.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.0311.em,
        lineHeight = 14.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.0311.em,
        lineHeight = 14.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    val caption2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 11.sp,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.0311.em,
        lineHeight = 14.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
)

internal val pretendardTypography = Typography().let {
    it.copy(
        displayLarge = it.displayLarge.copy(fontFamily = pretendard),
        displayMedium = it.displayMedium.copy(fontFamily = pretendard),
        displaySmall = it.displaySmall.copy(fontFamily = pretendard),
        headlineLarge = it.headlineLarge.copy(fontFamily = pretendard),
        headlineMedium = it.headlineMedium.copy(fontFamily = pretendard),
        headlineSmall = it.headlineSmall.copy(fontFamily = pretendard),
        titleLarge = it.titleLarge.copy(fontFamily = pretendard),
        titleMedium = it.titleMedium.copy(fontFamily = pretendard),
        titleSmall = it.titleSmall.copy(fontFamily = pretendard),
        bodyLarge = it.bodyLarge.copy(fontFamily = pretendard),
        bodyMedium = it.bodyMedium.copy(fontFamily = pretendard),
        bodySmall = it.bodySmall.copy(fontFamily = pretendard),
        labelLarge = it.labelLarge.copy(fontFamily = pretendard),
        labelMedium = it.labelMedium.copy(fontFamily = pretendard),
        labelSmall = it.labelSmall.copy(fontFamily = pretendard),
    )
}


val LocalWantedTypography = staticCompositionLocalOf { WantedTypography() }

