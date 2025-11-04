package com.wanted.android.wanted.design.util

import androidx.compose.runtime.Composable
import com.wanted.android.wanted.design.theme.DesignSystemTheme

enum class WantedTextStyle {
    DISPLAY1_REGULAR, DISPLAY1_MEDIUM, DISPLAY1_BOLD,
    DISPLAY2_REGULAR, DISPLAY2_MEDIUM, DISPLAY2_BOLD,
    DISPLAY3_REGULAR, DISPLAY3_MEDIUM, DISPLAY3_BOLD,
    TITLE1_REGULAR, TITLE1_MEDIUM, TITLE1_BOLD,
    TITLE2_REGULAR, TITLE2_MEDIUM, TITLE2_BOLD,
    TITLE3_REGULAR, TITLE3_MEDIUM, TITLE3_BOLD,
    HEADING1_REGULAR, HEADING1_MEDIUM, HEADING1_BOLD,
    HEADING2_REGULAR, HEADING2_MEDIUM, HEADING2_BOLD,
    HEADLINE1_REGULAR, HEADLINE1_MEDIUM, HEADLINE1_BOLD,
    HEADLINE2_REGULAR, HEADLINE2_MEDIUM, HEADLINE2_BOLD,
    BODY1_REGULAR, BODY1_MEDIUM, BODY1_BOLD,
    BODY1READING_REGULAR, BODY1READING_MEDIUM, BODY1READING_BOLD,
    BODY2_REGULAR, BODY2_MEDIUM, BODY2_BOLD,
    BODY2READING_REGULAR, BODY2READING_MEDIUM, BODY2READING_BOLD,
    LABEL1_REGULAR, LABEL1_MEDIUM, LABEL1_BOLD,
    LABEL1READING_REGULAR, LABEL1READING_MEDIUM, LABEL1READING_BOLD,
    LABEL2_REGULAR, LABEL2_MEDIUM, LABEL2_BOLD,
    CAPTION1_REGULAR, CAPTION1_MEDIUM, CAPTION1_BOLD,
    CAPTION2_REGULAR, CAPTION2_MEDIUM, CAPTION2_BOLD,
}

@Composable
fun getTextStyle(textStyle: WantedTextStyle) = when (textStyle) {
    WantedTextStyle.DISPLAY1_REGULAR -> DesignSystemTheme.typography.display1Regular
    WantedTextStyle.DISPLAY1_MEDIUM -> DesignSystemTheme.typography.display1Medium
    WantedTextStyle.DISPLAY1_BOLD -> DesignSystemTheme.typography.display1Bold
    WantedTextStyle.DISPLAY2_REGULAR -> DesignSystemTheme.typography.display2Regular
    WantedTextStyle.DISPLAY2_MEDIUM -> DesignSystemTheme.typography.display2Medium
    WantedTextStyle.DISPLAY2_BOLD -> DesignSystemTheme.typography.display2Bold
    WantedTextStyle.DISPLAY3_REGULAR -> DesignSystemTheme.typography.display3Regular
    WantedTextStyle.DISPLAY3_MEDIUM -> DesignSystemTheme.typography.display3Medium
    WantedTextStyle.DISPLAY3_BOLD -> DesignSystemTheme.typography.display3Bold
    WantedTextStyle.TITLE1_REGULAR -> DesignSystemTheme.typography.title1Regular
    WantedTextStyle.TITLE1_MEDIUM -> DesignSystemTheme.typography.title1Medium
    WantedTextStyle.TITLE1_BOLD -> DesignSystemTheme.typography.title1Bold
    WantedTextStyle.TITLE2_REGULAR -> DesignSystemTheme.typography.title2Regular
    WantedTextStyle.TITLE2_MEDIUM -> DesignSystemTheme.typography.title2Medium
    WantedTextStyle.TITLE2_BOLD -> DesignSystemTheme.typography.title2Bold
    WantedTextStyle.TITLE3_REGULAR -> DesignSystemTheme.typography.title3Regular
    WantedTextStyle.TITLE3_MEDIUM -> DesignSystemTheme.typography.title3Medium
    WantedTextStyle.TITLE3_BOLD -> DesignSystemTheme.typography.title3Bold
    WantedTextStyle.HEADING1_REGULAR -> DesignSystemTheme.typography.heading1Regular
    WantedTextStyle.HEADING1_MEDIUM -> DesignSystemTheme.typography.heading1Medium
    WantedTextStyle.HEADING1_BOLD -> DesignSystemTheme.typography.heading1Bold
    WantedTextStyle.HEADING2_REGULAR -> DesignSystemTheme.typography.heading2Regular
    WantedTextStyle.HEADING2_MEDIUM -> DesignSystemTheme.typography.heading2Medium
    WantedTextStyle.HEADING2_BOLD -> DesignSystemTheme.typography.heading2Bold
    WantedTextStyle.HEADLINE1_REGULAR -> DesignSystemTheme.typography.headline1Regular
    WantedTextStyle.HEADLINE1_MEDIUM -> DesignSystemTheme.typography.headline1Medium
    WantedTextStyle.HEADLINE1_BOLD -> DesignSystemTheme.typography.headline1Bold
    WantedTextStyle.HEADLINE2_REGULAR -> DesignSystemTheme.typography.headline2Regular
    WantedTextStyle.HEADLINE2_MEDIUM -> DesignSystemTheme.typography.headline2Medium
    WantedTextStyle.HEADLINE2_BOLD -> DesignSystemTheme.typography.headline2Bold
    WantedTextStyle.BODY1_REGULAR -> DesignSystemTheme.typography.body1Regular
    WantedTextStyle.BODY1_MEDIUM -> DesignSystemTheme.typography.body1Medium
    WantedTextStyle.BODY1_BOLD -> DesignSystemTheme.typography.body1Bold
    WantedTextStyle.BODY1READING_REGULAR -> DesignSystemTheme.typography.body1ReadingRegular
    WantedTextStyle.BODY1READING_MEDIUM -> DesignSystemTheme.typography.body1ReadingMedium
    WantedTextStyle.BODY1READING_BOLD -> DesignSystemTheme.typography.body1ReadingBold
    WantedTextStyle.BODY2_REGULAR -> DesignSystemTheme.typography.body2Regular
    WantedTextStyle.BODY2_MEDIUM -> DesignSystemTheme.typography.body2Medium
    WantedTextStyle.BODY2_BOLD -> DesignSystemTheme.typography.body2Bold
    WantedTextStyle.BODY2READING_REGULAR -> DesignSystemTheme.typography.body2ReadingRegular
    WantedTextStyle.BODY2READING_MEDIUM -> DesignSystemTheme.typography.body2ReadingMedium
    WantedTextStyle.BODY2READING_BOLD -> DesignSystemTheme.typography.body2ReadingBold
    WantedTextStyle.LABEL1_REGULAR -> DesignSystemTheme.typography.label1Regular
    WantedTextStyle.LABEL1_MEDIUM -> DesignSystemTheme.typography.label1Medium
    WantedTextStyle.LABEL1_BOLD -> DesignSystemTheme.typography.label1Bold
    WantedTextStyle.LABEL1READING_REGULAR -> DesignSystemTheme.typography.label1ReadingRegular
    WantedTextStyle.LABEL1READING_MEDIUM -> DesignSystemTheme.typography.label1ReadingMedium
    WantedTextStyle.LABEL1READING_BOLD -> DesignSystemTheme.typography.label1ReadingBold
    WantedTextStyle.LABEL2_REGULAR -> DesignSystemTheme.typography.label2Regular
    WantedTextStyle.LABEL2_MEDIUM -> DesignSystemTheme.typography.label2Medium
    WantedTextStyle.LABEL2_BOLD -> DesignSystemTheme.typography.label2Bold
    WantedTextStyle.CAPTION1_REGULAR -> DesignSystemTheme.typography.caption1Regular
    WantedTextStyle.CAPTION1_MEDIUM -> DesignSystemTheme.typography.caption1Medium
    WantedTextStyle.CAPTION1_BOLD -> DesignSystemTheme.typography.caption1Bold
    WantedTextStyle.CAPTION2_REGULAR -> DesignSystemTheme.typography.caption2Regular
    WantedTextStyle.CAPTION2_MEDIUM -> DesignSystemTheme.typography.caption2Medium
    WantedTextStyle.CAPTION2_BOLD -> DesignSystemTheme.typography.caption2Bold
}