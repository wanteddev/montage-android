package com.wanted.android.wanted.design.chip

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.chip.config.LocalWantedChipSize
import com.wanted.android.wanted.design.chip.config.LocalWantedChipVariant
import com.wanted.android.wanted.design.chip.config.WantedChipDefault
import com.wanted.android.wanted.design.chip.config.WantedChipDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40136&m=dev
 */

@Composable
fun WantedFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    size: ChipActionSize = ChipActionSize.SMALL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    isExpend: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {

    CompositionLocalProvider(
        LocalWantedChipEnable.provides(isEnable),
        LocalWantedChipSize.provides(size),
        LocalWantedChipVariant.provides(variant),
        LocalWantedChipActive.provides(isActive)
    ) {
        WantedFilterChip(
            text = text,
            modifier = modifier,
            chipDefault = WantedChipDefaults
                .getDefault()
                .copy(iconColor = colorResource(id = WantedChipDefaults.getFilterIconColor())),
            isExpend = isExpend,
            interactionSource = interactionSource,
            onClick = onClick
        )
    }
}

@Composable
fun WantedFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(),
    isExpend: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedActionChip(
        modifier = modifier,
        interactionSource = interactionSource,
        size = chipDefault.size,
        variant = chipDefault.variant,
        isActive = chipDefault.isActive,
        isEnable = chipDefault.isEnable,
        chipDefault = chipDefault,
        content = {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        rightIcon = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (isExpend) {
                    painterResource(id = R.drawable.ic_normal_caret_up_svg)
                } else {
                    painterResource(id = R.drawable.ic_normal_caret_down_svg)
                },
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
            )
        },
        onClick = onClick
    )
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
private fun ActionChipPreView() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.background_normal_normal)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        size = ChipActionSize.NORMAL
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isExpend = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        isExpend = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isExpend = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        isExpend = true,
                        size = ChipActionSize.NORMAL
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.NORMAL
                    )
                }
            }
        }
    }
}