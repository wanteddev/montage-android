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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.clickOnceForDesignSystem
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
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40136&m=dev
 */

@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    size: ChipActionSize = ChipActionSize.NORMAL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    CompositionLocalProvider(
        LocalWantedChipEnable.provides(isEnable),
        LocalWantedChipSize.provides(size),
        LocalWantedChipVariant.provides(variant),
        LocalWantedChipActive.provides(isActive)
    ) {
        WantedActionChip(
            text = text,
            modifier = modifier,
            interactionSource = interactionSource,
            chipDefault = WantedChipDefaults.getDefault(),
            leftIcon = leftIcon,
            rightIcon = rightIcon,
            onClick = onClick
        )
    }
}

@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(),
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
        leftIcon = leftIcon?.let {
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
                )
            }
        },
        content = {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        rightIcon = rightIcon?.let {
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = rightIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
                )
            }
        },
        onClick = onClick
    )
}

@Composable
fun WantedActionChip(
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
    leftIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedActionChipLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getChipRadius(chipDefault.size)))
            .background(chipDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getChipRadius(chipDefault.size)),
                color = chipDefault.borderColor
            )
            .clickOnceForDesignSystem(
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
        leftIcon = leftIcon,
        content = content,
        rightIcon = rightIcon
    )
}

@Composable
private fun WantedActionChipLayout(
    modifier: Modifier,
    chipDefault: WantedChipDefault,
    leftIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .actionChipPadding(size = chipDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(getActionChipHorizontalArrangement(size = chipDefault.size))
    ) {
        leftIcon?.let {
            Box(
                modifier = Modifier.actionChipIconSize(chipDefault.size),
                contentAlignment = Alignment.Center
            ) {
                leftIcon()
            }
        }


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
                modifier = Modifier.actionChipIconSize(chipDefault.size),
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
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        size = ChipActionSize.SMALL
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        size = ChipActionSize.SMALL
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        size = ChipActionSize.SMALL
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        size = ChipActionSize.SMALL
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        size = ChipActionSize.SMALL,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        size = ChipActionSize.SMALL,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.FILLED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ChipActionVariant.OUTLINED,
                        isEnable = false,
                        size = ChipActionSize.SMALL,
                        leftIcon = R.drawable.ic_normal_bookmark_svg,
                        rightIcon = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.NORMAL
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.NORMAL
                        )
                        WantedActionChip(
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
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.NORMAL,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.NORMAL,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            isEnable = false,
                            size = ChipActionSize.NORMAL,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WantedActionChip(
                                text = "텍스트",
                                variant = ChipActionVariant.FILLED,
                                isActive = true,
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                size = ChipActionSize.SMALL
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ChipActionVariant.FILLED,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                size = ChipActionSize.SMALL
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ChipActionVariant.OUTLINED,
                                isActive = true,
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                size = ChipActionSize.SMALL
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ChipActionVariant.OUTLINED,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                size = ChipActionSize.SMALL
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipActionVariant.FILLED,
                                    isActive = false,
                                    size = ChipActionSize.SMALL,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipActionVariant.FILLED,
                                    isActive = false,
                                    size = ChipActionSize.SMALL,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipActionVariant.OUTLINED,
                                    isActive = false,
                                    size = ChipActionSize.SMALL,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12),
                                    borderColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_22)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.ic_normal_bookmark_svg,
                                rightIcon = R.drawable.ic_normal_bookmark_svg,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipActionVariant.OUTLINED,
                                    isActive = false,
                                    size = ChipActionSize.SMALL,
                                    isEnable = false,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium,
                                        alpha = OPACITY_12
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_5),
                                    borderColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "this is very very long long long long long long text",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.NORMAL,
                            modifier = Modifier.widthIn(max = 200.dp)
                        )
                    }
                }
            }
        }
    }
}