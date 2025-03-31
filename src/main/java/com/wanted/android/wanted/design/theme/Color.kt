package com.wanted.android.wanted.design.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.wanted.android.designsystem.R

// https://github.com/wanteddev/montage-common/tree/release/packages/montage-themes/tokens/color

private val DefaultColors = WantedColors(
    staticWhite = Color(0xffffffff),
    staticBlack = Color(0xff000000),

    primaryNormal = Color(0xff0066FF),
    primaryStrong = Color(0xff005EEB),
    primaryHeavy = Color(0xff0054D1),

    labelNormal = Color(0xff171719),
    labelStrong = Color(0xff000000),
    labelNeutral = Color(0xE02E2F33),
    labelAlternative = Color(0x9C37383C),
    labelAssistive = Color(0x4737383C),
    labelDisable = Color(0x2937383C),

    backgroundNormalNormal = Color(0xffffffff),
    backgroundNormalAlternative = Color(0xffF7F7F8),
    backgroundElevatedNormal = Color(0xffffffff),
    backgroundElevatedAlternative = Color(0xffF7F7F8),

    interactionInactive = Color(0xff989BA2),
    interactionDisable = Color(0xffF4F4F5),

    lineNormalNormal = Color(0x3870737C),
    lineNormalNeutral = Color(0x2970737C),
    lineNormalAlternative = Color(0x1470737C),
    lineSolidNormal = Color(0xffE1E2E4),
    lineSolidNeutral = Color(0xffEAEBEC),
    lineSolidAlternative = Color(0xffF4F4F5),

    statusPositive = Color(0xff00BF40),
    statusCautionary = Color(0xffFF9200),
    statusNegative = Color(0xffFF4242),

    accentLime = Color(0xff58CF04),
    accentCyan = Color(0xff00BDDE),
    accentLightBlue = Color(0xff00AEFF),
    accentViolet = Color(0xff6541F2),
    accentPurple = Color(0xffCB59FF),
    accentPink = Color(0xffF553DA),
    accentRedOrange = Color(0xffFF5E00),

    inversePrimary = Color(0xff3385FF),
    inverseBackground = Color(0xff1B1C1E),
    inverseLabel = Color(0xffF7F7F8),

    fillNormal = Color(0x1470737C),
    fillStrong = Color(0x2970737C),
    fillAlternative = Color(0x0D70737C),
    materialDimmer = Color(0x85171719),
)

@Composable
fun getDesignSystemColor() = WantedColors(
    staticWhite = colorResource(id = R.color.static_white),
    staticBlack = colorResource(id = R.color.static_black),

    primaryNormal = colorResource(id = R.color.primary_normal),
    primaryStrong = colorResource(id = R.color.primary_strong),
    primaryHeavy = colorResource(id = R.color.primary_heavy),

    labelNormal = colorResource(id = R.color.label_normal),
    labelStrong = colorResource(id = R.color.label_strong),
    labelNeutral = colorResource(id = R.color.label_neutral),
    labelAlternative = colorResource(id = R.color.label_alternative),
    labelAssistive = colorResource(id = R.color.label_assistive),
    labelDisable = colorResource(id = R.color.label_disable),

    backgroundNormalNormal = colorResource(id = R.color.background_normal_normal),
    backgroundNormalAlternative = colorResource(id = R.color.background_normal_alternative),
    backgroundElevatedNormal = colorResource(id = R.color.background_elevated_normal),
    backgroundElevatedAlternative = colorResource(id = R.color.background_elevated_alternative),

    interactionInactive = colorResource(id = R.color.interaction_inactive),
    interactionDisable = colorResource(id = R.color.interaction_disable),

    lineNormalNormal = colorResource(id = R.color.line_normal_normal),
    lineNormalNeutral = colorResource(id = R.color.line_solid_neutral),
    lineNormalAlternative = colorResource(id = R.color.line_normal_alternative),
    lineSolidNormal = colorResource(id = R.color.line_solid_normal),
    lineSolidNeutral = colorResource(id = R.color.line_solid_neutral),
    lineSolidAlternative = colorResource(id = R.color.line_solid_alternative),

    statusPositive = colorResource(id = R.color.status_positive),
    statusNegative = colorResource(id = R.color.status_negative),
    statusCautionary = colorResource(id = R.color.status_cautionary),

    accentLime = colorResource(id = R.color.accent_background_lime),
    accentCyan = colorResource(id = R.color.accent_background_cyan),
    accentLightBlue = colorResource(id = R.color.accent_background_lightblue),
    accentViolet = colorResource(id = R.color.accent_background_violet),
    accentPurple = colorResource(id = R.color.accent_background_purple),
    accentPink = colorResource(id = R.color.accent_background_pink),
    accentRedOrange = colorResource(id = R.color.accent_background_redorange),

    inversePrimary = colorResource(id = R.color.inverse_primary),
    inverseLabel = colorResource(id = R.color.inverse_label),
    inverseBackground = colorResource(id = R.color.inverse_background),

    fillNormal = colorResource(id = R.color.fill_normal),
    fillStrong = colorResource(id = R.color.fill_strong),
    fillAlternative = colorResource(id = R.color.fill_alternative),
    materialDimmer = colorResource(id = R.color.material_dimmer),
)

@Stable
class WantedColors(
    staticWhite: Color,
    staticBlack: Color,

    primaryNormal: Color,
    primaryStrong: Color,
    primaryHeavy: Color,

    labelNormal: Color,
    labelStrong: Color,
    labelNeutral: Color,
    labelAlternative: Color,
    labelAssistive: Color,
    labelDisable: Color,

    backgroundNormalNormal: Color,
    backgroundNormalAlternative: Color,
    backgroundElevatedNormal: Color,
    backgroundElevatedAlternative: Color,

    interactionInactive: Color,
    interactionDisable: Color,

    lineNormalNormal: Color,
    lineNormalNeutral: Color,
    lineNormalAlternative: Color,
    lineSolidNormal: Color,
    lineSolidNeutral: Color,
    lineSolidAlternative: Color,

    statusPositive: Color,
    statusNegative: Color,
    statusCautionary: Color,

    accentLime: Color,
    accentCyan: Color,
    accentLightBlue: Color,
    accentViolet: Color,
    accentPurple: Color,
    accentPink: Color,
    accentRedOrange: Color,

    inversePrimary: Color,
    inverseBackground: Color,
    inverseLabel: Color,

    fillNormal: Color,
    fillStrong: Color,
    fillAlternative: Color,
    materialDimmer: Color,
) {
    var staticWhite by mutableStateOf(staticWhite)
        private set
    var staticBlack by mutableStateOf(staticBlack)
        private set

    var primaryNormal by mutableStateOf(primaryNormal)
        private set
    var primaryStrong by mutableStateOf(primaryStrong)
        private set
    var primaryHeavy by mutableStateOf(primaryHeavy)
        private set

    var labelNormal by mutableStateOf(labelNormal)
        private set
    var labelStrong by mutableStateOf(labelStrong)
        private set
    var labelNeutral by mutableStateOf(labelNeutral)
        private set
    var labelAlternative by mutableStateOf(labelAlternative)
        private set
    var labelAssistive by mutableStateOf(labelAssistive)
        private set
    var labelDisable by mutableStateOf(labelDisable)
        private set

    var backgroundNormalNormal by mutableStateOf(backgroundNormalNormal)
        private set
    var backgroundNormalAlternative by mutableStateOf(backgroundNormalAlternative)
        private set
    var backgroundElevatedNormal by mutableStateOf(backgroundElevatedNormal)
        private set
    var backgroundElevatedAlternative by mutableStateOf(backgroundElevatedAlternative)
        private set

    var interactionInactive by mutableStateOf(interactionInactive)
        private set
    var interactionDisable by mutableStateOf(interactionDisable)
        private set

    var lineNormalNormal by mutableStateOf(lineNormalNormal)
        private set
    var lineNormalNeutral by mutableStateOf(lineNormalNeutral)
        private set
    var lineNormalAlternative by mutableStateOf(lineNormalAlternative)
        private set
    var lineSolidNormal by mutableStateOf(lineSolidNormal)
        private set
    var lineSolidNeutral by mutableStateOf(lineSolidNeutral)
        private set
    var lineSolidAlternative by mutableStateOf(lineSolidAlternative)
        private set

    var statusPositive by mutableStateOf(statusPositive)
        private set
    var statusNegative by mutableStateOf(statusNegative)
        private set
    var statusCautionary by mutableStateOf(statusCautionary)
        private set

    var accentLime by mutableStateOf(accentLime)
        private set
    var accentCyan by mutableStateOf(accentCyan)
        private set
    var accentLightBlue by mutableStateOf(accentLightBlue)
        private set
    var accentViolet by mutableStateOf(accentViolet)
        private set
    var accentPurple by mutableStateOf(accentPurple)
        private set
    var accentPink by mutableStateOf(accentPink)
        private set
    var accentRedOrange by mutableStateOf(accentRedOrange)
        private set

    var inversePrimary by mutableStateOf(inversePrimary)
        private set
    var inverseBackground by mutableStateOf(inverseBackground)
        private set
    var inverseLabel by mutableStateOf(inverseLabel)
        private set

    var fillNormal by mutableStateOf(fillNormal)
        private set
    var fillStrong by mutableStateOf(fillStrong)
        private set
    var fillAlternative by mutableStateOf(fillAlternative)
        private set
    var materialDimmer by mutableStateOf(materialDimmer)
        private set

    fun update(other: WantedColors) {
        staticWhite = other.staticWhite
        staticBlack = other.staticBlack

        primaryNormal = other.primaryNormal
        primaryStrong = other.primaryStrong
        primaryHeavy = other.primaryHeavy

        labelNormal = other.labelNormal
        labelStrong = other.labelStrong
        labelNeutral = other.labelNeutral
        labelAlternative = other.labelAlternative
        labelAssistive = other.labelAssistive
        labelDisable = other.labelDisable

        backgroundNormalNormal = other.backgroundNormalNormal
        backgroundNormalAlternative = other.backgroundNormalAlternative
        backgroundElevatedNormal = other.backgroundElevatedNormal
        backgroundElevatedAlternative = other.backgroundElevatedAlternative

        interactionInactive = other.interactionInactive
        interactionDisable = other.interactionDisable

        lineNormalNormal = other.lineNormalNormal
        lineNormalNeutral = other.lineNormalNeutral
        lineNormalAlternative = other.lineNormalAlternative
        lineSolidNormal = other.lineSolidNormal
        lineSolidNeutral = other.lineSolidNeutral
        lineSolidAlternative = other.lineSolidAlternative

        statusPositive = other.statusPositive
        statusNegative = other.statusNegative
        statusCautionary = other.statusCautionary

        accentLime = other.accentLime
        accentCyan = other.accentCyan
        accentLightBlue = other.accentLightBlue
        accentViolet = other.accentViolet
        accentPurple = other.accentPurple
        accentPink = other.accentPink
        accentRedOrange = other.accentRedOrange

        inversePrimary = other.inversePrimary
        inverseBackground = other.inverseBackground
        inverseLabel = other.inverseLabel

        fillNormal = other.fillNormal
        fillStrong = other.fillStrong
        fillAlternative = other.fillAlternative
        materialDimmer = other.materialDimmer
    }

    fun getColor(isDarkTheme: Boolean) = if (isDarkTheme) {
        darkColorScheme(
            primary = primaryNormal,
            secondary = primaryNormal,
            background = backgroundNormalNormal,
            surface = backgroundNormalNormal,
            error = backgroundNormalNormal,
            onPrimary = labelNormal,
            onSecondary = labelNormal,
            onBackground = labelNormal,
            onSurface = labelNormal,
            onError = statusNegative,
        )
    } else {
        lightColorScheme(
            primary = primaryNormal,
            secondary = primaryNormal,
            background = backgroundNormalNormal,
            surface = backgroundNormalNormal,
            error = backgroundNormalNormal,
            onPrimary = labelNormal,
            onSecondary = labelNormal,
            onBackground = labelNormal,
            onSurface = labelNormal,
            onError = statusNegative
        )
    }
}

// preview
internal val LocalWantedColor = staticCompositionLocalOf {
    DefaultColors
}