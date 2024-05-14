package com.wanted.android.wanted.design.chip

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.getTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect

@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    size: ChipActionSize = ChipActionSize.SMALL,
    variant: ChipActionVariant = ChipActionVariant.FILLED,
    status: ChipActionStatus = ChipActionStatus.ENABLE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isClickOnce: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .actionChipBackground(variant, size)
            .actionChipBorder(variant, status, size)
            .clickable(
                interactionSource = interactionSource,
                indication = when (status) {
                    ChipActionStatus.ENABLE -> wantedRippleEffect(color = colorResource(id = R.color.label_normal))
                    else -> null
                },
                enabled = status == ChipActionStatus.ENABLE
            ) {
                when {
                    status == ChipActionStatus.DISABLE -> Unit
                    isClickOnce -> onClick?.clickOnceForDesignSystem()
                    else -> onClick?.invoke()
                }
            }
            .actionChipPadding(size = size),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leftIcon != null) {
            Row(Modifier.wrapContentSize()) {
                Image(
                    painter = painterResource(id = leftIcon),
                    modifier = Modifier.actionChipIconSize(size),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    colorFilter = ColorFilter.tint(
                        color = colorResource(
                            id = if (status != ChipActionStatus.DISABLE) {
                                R.color.label_normal
                            } else R.color.label_disable
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.actionChipSpace(size))
        }

        Row {
            Text(
                text = text,
                modifier = Modifier
                    .wrapContentHeight(),
                style = ChipActionTextStyle(size = size),
                color = ChipActionTextColor(status = status, variant = variant),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (rightIcon != null) {
            Spacer(modifier = Modifier.actionChipSpace(size))
            Row(Modifier.wrapContentSize()) {
                Image(
                    painter = painterResource(id = rightIcon),
                    modifier = Modifier.actionChipIconSize(size),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    colorFilter = ColorFilter.tint(
                        color = colorResource(
                            id = if (status != ChipActionStatus.DISABLE) {
                                R.color.label_normal
                            } else R.color.label_disable
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun ChipActionTextStyle(
    size: ChipActionSize
): TextStyle {
    val textStyle = when (size) {
        ChipActionSize.LARGE -> {
            WantedTextStyle.CAPTION1_MEDIUM
        }

        ChipActionSize.MEDIUM -> {
            WantedTextStyle.LABEL1_MEDIUM
        }

        ChipActionSize.SMALL -> {
            WantedTextStyle.BODY2_MEDIUM
        }
    }

    return getTextStyle(textStyle = textStyle)
}


private fun Modifier.actionChipBackground(
    variant: ChipActionVariant,
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (variant) {
        ChipActionVariant.FILLED -> {
            background(
                color = colorResource(id = R.color.fill_alternative),
                shape = RoundedCornerShape(getCipRadius(size))
            ).then(clip(RoundedCornerShape(getCipRadius(size))))
        }

        ChipActionVariant.OUTLINED -> {
            clip(RoundedCornerShape(getCipRadius(size)))
        }
    }
    this.then(modifier)
}

private fun Modifier.actionChipBorder(
    variant: ChipActionVariant,
    status: ChipActionStatus,
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (variant) {
        ChipActionVariant.FILLED -> {
            Modifier
        }

        ChipActionVariant.OUTLINED -> {
            if (status == ChipActionStatus.ENABLE) {
                border(
                    1.dp,
                    color = colorResource(id = R.color.line_normal_normal),
                    shape = RoundedCornerShape(getCipRadius(size))
                )
            } else {
                border(
                    1.dp,
                    color = colorResource(id = R.color.line_normal_alternative),
                    shape = RoundedCornerShape(getCipRadius(size))
                )
            }
        }
    }
    this.then(modifier)
}

private fun Modifier.actionChipPadding(
    size: ChipActionSize
): Modifier = composed {
    when (size) {
        ChipActionSize.LARGE -> {
            this.then(padding(start = 16.dp, top = 9.dp, end = 16.dp, bottom = 9.dp))
        }

        ChipActionSize.MEDIUM -> {
            this.then(padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp))
        }

        ChipActionSize.SMALL -> {
            this.then(padding(start = 9.dp, top = 4.dp, end = 9.dp, bottom = 4.dp))
        }
    }
}

private fun Modifier.actionChipSpace(
    size: ChipActionSize
): Modifier = composed {
    when (size) {
        ChipActionSize.LARGE -> {
            this.then(width(5.dp))
        }

        ChipActionSize.MEDIUM -> {
            this.then(width(4.dp))
        }

        ChipActionSize.SMALL -> {
            this.then(width(4.dp))
        }
    }
}


private fun Modifier.actionChipIconSize(
    size: ChipActionSize
): Modifier = composed {
    val modifier = when (size) {
        ChipActionSize.LARGE -> {
            Modifier
                .height(16.dp)
                .wrapContentWidth()
        }

        ChipActionSize.MEDIUM -> {
            Modifier
                .height(14.dp)
                .wrapContentWidth()
        }

        ChipActionSize.SMALL -> {
            Modifier
                .height(12.dp)
                .wrapContentWidth()
        }
    }
    this.then(modifier)
}

@Composable
private fun ChipActionTextColor(
    status: ChipActionStatus,
    variant: ChipActionVariant
): Color {
    return when (variant) {
        ChipActionVariant.FILLED -> {
            when (status) {
                ChipActionStatus.ENABLE -> colorResource(id = R.color.label_normal)
                ChipActionStatus.DISABLE -> colorResource(id = R.color.label_assistive)
            }
        }

        ChipActionVariant.OUTLINED -> {
            when (status) {
                ChipActionStatus.ENABLE -> colorResource(id = R.color.label_normal)
                ChipActionStatus.DISABLE -> colorResource(id = R.color.label_disable)
            }
        }
    }
}

@Composable
private fun getCipRadius(size: ChipActionSize) = when (size) {
    ChipActionSize.LARGE -> 8.dp
    ChipActionSize.MEDIUM -> 6.dp
    ChipActionSize.SMALL -> 4.dp
}


enum class ChipActionVariant {
    FILLED, OUTLINED
}

enum class ChipActionStatus {
    ENABLE, DISABLE
}

enum class ChipActionSize {
    LARGE, MEDIUM, SMALL
}

private fun (() -> Unit).debounce(time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this() }
}

private fun <T> ((T) -> Unit).debounce(value: T, time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this(value) }
}

private object ComposeMultipleEventCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    fun processEvent(time: Long, event: () -> Unit) {
        if (now - lastEventTimeMs >= time) {
            event.invoke()
        }

        lastEventTimeMs = now
    }
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                        status = ChipActionStatus.DISABLE,
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
                            size = ChipActionSize.MEDIUM
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.MEDIUM
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
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
                            size = ChipActionSize.MEDIUM,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.MEDIUM,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
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
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.FILLED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            size = ChipActionSize.MEDIUM,
                            leftIcon = R.drawable.ic_normal_bookmark_svg,
                            rightIcon = R.drawable.ic_normal_bookmark_svg
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ChipActionVariant.OUTLINED,
                            status = ChipActionStatus.DISABLE,
                            size = ChipActionSize.MEDIUM,
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
                            size = ChipActionSize.MEDIUM,
                            modifier = Modifier.widthIn(max = 200.dp)
                        )
                    }
                }
            }
        }
    }
}