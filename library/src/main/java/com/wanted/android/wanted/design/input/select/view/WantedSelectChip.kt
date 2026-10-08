package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.actions.chip.WantedChipDefault
import com.wanted.android.wanted.design.actions.chip.WantedChipDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_5

// Select 내부에서 사용하는 선택 항목 Chip 입니다.
//
// #4 4.0.0 Chip(XSmall / Outlined) UI를 사용하며, negative 상태와 disable 상태를 별도로 관리합니다.
@Composable
internal fun WantedSelectChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    error: Boolean = false,
    leadingContent: @Composable (() -> Unit)? = null
) {
    val chipDefault = selectChipDefault(enable = enable, error = error)

    WantedChip(
        modifier = modifier,
        chipDefault = chipDefault,
        leadingContent = leadingContent,
        content = {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        trailingContent = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.icon_normal_close),
                tint = chipDefault.iconColor,
                contentDescription = ""
            )
        },
        onClick = onClick
    )
}

// #4 enable / error 조합에 따른 Select Chip 스타일입니다.
//
// disable이 negative보다 우선합니다.
@Composable
private fun selectChipDefault(
    enable: Boolean,
    error: Boolean
): WantedChipDefault = when {
    !enable -> {
        WantedChipDefaults.getDefault(
            size = ChipSize.XSmall,
            variant = ChipVariant.Outlined,
            isEnable = false
        )
    }

    error -> {
        WantedChipDefaults.getDefault(
            size = ChipSize.XSmall,
            variant = ChipVariant.Outlined,
            isEnable = true,
            iconColor = DesignSystemTheme.colors.foregroundNegativePrimary,
            backgroundColor = DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_5),
            borderColor = DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_22),
            textStyle = DesignSystemTheme.typography.caption1Medium.copy(
                color = DesignSystemTheme.colors.foregroundNegativePrimary
            )
        )
    }

    else -> {
        WantedChipDefaults.getDefault(
            size = ChipSize.XSmall,
            variant = ChipVariant.Outlined,
            isEnable = true
        )
    }
}

@DevicePreviews
@Composable
private fun WantedSelectChipPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedSelectChip(
                    text = "선택1",
                    onClick = { }
                )

                WantedSelectChip(
                    text = "선택1",
                    enable = false,
                    onClick = { }
                )


                WantedSelectChip(
                    text = "선택1",
                    error = true,
                    onClick = { }
                )


                WantedSelectChip(
                    text = "선택1",
                    leadingContent = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                            tint = DesignSystemTheme.colors.foregroundCautionaryPrimary,
                            contentDescription = ""
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}
