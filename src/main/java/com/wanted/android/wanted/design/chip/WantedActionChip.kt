package com.wanted.android.wanted.design.chip

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.getTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect
import java.io.NotActiveException

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40136&m=dev
 */

@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    size: ChipActionSize = ChipActionSize.SMALL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedActionChip(
        modifier = modifier,
        interactionSource = interactionSource,
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable,
        leftIcon = leftIcon?.let {
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = colorResource(
                            id = if (isEnable) {
                                R.color.label_normal
                            } else {
                                R.color.label_disable
                            }
                        )
                    )
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
                    colorFilter = ColorFilter.tint(
                        color = colorResource(
                            id = if (isEnable) {
                                R.color.label_normal
                            } else {
                                R.color.label_disable
                            }
                        )
                    )
                )
            }
        },
        onClick = onClick
    )
}

@Composable
private fun WantedActionChip(
    modifier: Modifier = Modifier,
    size: ChipActionSize = ChipActionSize.SMALL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leftIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedActionChipLayout(
        modifier = modifier
            .clickOnceForDesignSystem(
                interactionSource = interactionSource,
                indication = if (variant == ChipActionVariant.FILLED) {
                    wantedRippleEffect(color = colorResource(id = R.color.label_normal).copy(OPACITY_12))
                } else {
                    wantedRippleEffect(color = colorResource(id = R.color.primary_normal).copy(OPACITY_12))
                },
                enabled = isEnable
            ) {
                onClick?.invoke()
            },
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable,
        leftIcon = leftIcon,
        content = content,
        rightIcon = rightIcon
    )
}

@Composable
private fun WantedActionChipLayout(
    modifier: Modifier,
    size: ChipActionSize,
    variant: ChipActionVariant,
    isActive: Boolean = false,
    isEnable: Boolean,
    leftIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .actionChipBackground(variant, isEnable, size)
            .actionChipBorder(variant, size)
            .then(modifier)
            .actionChipPadding(size = size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            if (size == ChipActionSize.LARGE) 6.dp else 4.dp
        )
    ) {

        leftIcon?.let {
            Box(
                modifier = Modifier.actionChipIconSize(size),
                contentAlignment = Alignment.Center
            ) {
                leftIcon()
            }
        }


        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = ChipActionTextColor(isEnable = isEnable, variant = variant),
                style = ChipActionTextStyle(size = size)
            )
        ) {
            Box(
                modifier = Modifier.weight(1f, fill = false),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }

        rightIcon?.let {
            Box(
                modifier = Modifier.actionChipIconSize(size),
                contentAlignment = Alignment.Center
            ) {
                rightIcon()
            }
        }
    }
}

@Composable
private fun ChipActionTextStyle(
    size: ChipActionSize
): TextStyle {
    val textStyle = when (size) {
        ChipActionSize.LARGE -> WantedTextStyle.CAPTION1_MEDIUM
        ChipActionSize.NORMAL -> WantedTextStyle.BODY2_MEDIUM
        ChipActionSize.SMALL -> WantedTextStyle.LABEL1_MEDIUM
        ChipActionSize.XSMALL -> WantedTextStyle.CAPTION1_MEDIUM
    }

    return getTextStyle(textStyle = textStyle)
}

private fun Modifier.actionChipBackground(
    variant: ChipActionVariant,
    isEnable: Boolean,
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (variant) {
        ChipActionVariant.FILLED -> {
            if (isEnable) {
                background(
                    color = colorResource(id = R.color.fill_alternative),
                    shape = RoundedCornerShape(getCipRadius(size))
                ).then(clip(RoundedCornerShape(getCipRadius(size))))
            } else {
                background(
                    color = colorResource(id = R.color.interaction_disable),
                    shape = RoundedCornerShape(getCipRadius(size))
                ).then(clip(RoundedCornerShape(getCipRadius(size))))
            }
        }

        ChipActionVariant.OUTLINED -> {
            clip(RoundedCornerShape(getCipRadius(size)))
        }
    }
    this.then(modifier)
}

private fun Modifier.actionChipBorder(
    variant: ChipActionVariant,
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (variant) {
        ChipActionVariant.FILLED -> {
            Modifier
        }

        ChipActionVariant.OUTLINED -> {
            border(
                1.dp,
                color = colorResource(id = R.color.line_normal_neutral),
                shape = RoundedCornerShape(getCipRadius(size))
            )
        }
    }
    this.then(modifier)
}

private fun Modifier.actionChipPadding(
    size: ChipActionSize
): Modifier = composed {
    when (size) {
        ChipActionSize.LARGE -> {
            this.then(padding(vertical = 9.dp, horizontal = 12.dp))
        }

        ChipActionSize.NORMAL -> {
            this.then(padding(vertical = 7.dp, horizontal = 12.dp))
        }

        ChipActionSize.SMALL -> {
            this.then(padding(vertical = 6.dp, horizontal = 10.dp))
        }

        ChipActionSize.XSMALL -> {
            this.then(padding(vertical = 4.dp, horizontal = 6.dp))
        }
    }
}


private fun Modifier.actionChipIconSize(
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (size) {
        ChipActionSize.LARGE -> Modifier.size(16.dp)
        ChipActionSize.NORMAL -> Modifier.size(14.dp)
        ChipActionSize.SMALL -> Modifier.size(14.dp)
        ChipActionSize.XSMALL -> Modifier.size(12.dp)
    }
    this.then(modifier)
}

@Composable
private fun ChipActionTextColor(
    isEnable: Boolean,
    variant: ChipActionVariant
): Int {
    return when (variant) {
        ChipActionVariant.FILLED -> {
            if (isEnable) {
                R.color.label_normal
            } else {
                R.color.label_disable
            }
        }

        ChipActionVariant.OUTLINED -> {
            if (isEnable) {
                R.color.label_normal
            } else {
                R.color.label_disable
            }
        }
    }
}

@Composable
private fun getCipRadius(size: ChipActionSize) = when (size) {
    ChipActionSize.LARGE -> 6.dp
    ChipActionSize.NORMAL -> 6.dp
    ChipActionSize.SMALL -> 6.dp
    ChipActionSize.XSMALL -> 6.dp
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
fun ActionChipPreView() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.background_normal_normal)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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