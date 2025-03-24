package com.wanted.android.wanted.design.actions.chip

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipSize
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipVariant
import com.wanted.android.wanted.design.actions.chip.config.WantedChipDefault
import com.wanted.android.wanted.design.actions.chip.config.WantedChipDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40136&m=dev
 */

@Composable
fun WantedFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    activeLabel: String = "",
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
            modifier = modifier,
            text = text,
            activeLabel = activeLabel,
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
    activeLabel: String = "",
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(),
    isExpend: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedFilterChip(
        modifier = modifier,
        interactionSource = interactionSource,
        size = chipDefault.size,
        variant = chipDefault.variant,
        isActive = chipDefault.isActive,
        isEnable = chipDefault.isEnable,
        chipDefault = chipDefault,
        content = {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f, fill = false),
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (activeLabel.isNotEmpty()) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = activeLabel,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = chipDefault.textStyle.merge(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
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

@Composable
private fun WantedFilterChip(
    modifier: Modifier = Modifier,
    size: ChipActionSize = ChipActionSize.SMALL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedFilterChipLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getChipRadius(chipDefault.size)))
            .background(chipDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getChipRadius(chipDefault.size)),
                color = chipDefault.borderColor
            )
            .clickOnce(
                interactionSource = interactionSource,
                indication = if (chipDefault.variant == ChipActionVariant.FILLED) {
                    wantedRippleEffect(
                        color = colorResource(id = R.color.label_normal).copy(
                            OPACITY_12
                        )
                    )
                } else {
                    wantedRippleEffect(
                        color = chipDefault.backgroundColor.copy(OPACITY_12)
                    )
                },
                enabled = chipDefault.isEnable
            ) {
                onClick?.invoke()
            },
        chipDefault = chipDefault,
        content = content,
        rightIcon = rightIcon
    )
}


@Composable
private fun WantedFilterChipLayout(
    modifier: Modifier,
    chipDefault: WantedChipDefault,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .filterChipPadding(size = chipDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(getFilterChipHorizontalArrangement(size = chipDefault.size))
    ) {

        ProvideTextStyle(value = chipDefault.textStyle) {
            Box(
                modifier = Modifier
                    .actionChipTextPadding(chipDefault.size)
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }

        rightIcon?.let {
            Box(
                modifier = Modifier.filterChipIconSize(chipDefault.size),
                contentAlignment = Alignment.Center
            ) {
                rightIcon()
            }
        }
    }
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "1",
                        variant = ChipActionVariant.FILLED,
                        isExpend = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "2",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        isActive = true,
                        size = ChipActionSize.NORMAL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "일",
                        variant = ChipActionVariant.FILLED,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.SMALL
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "이",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.SMALL
                    )
                }
            }
        }
    }
}