package com.wanted.android.wanted.design.badge

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_16
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.getContentBadgeDrawableSize
import com.wanted.android.wanted.design.util.getTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45460&m=dev
 */
enum class ContentBadgeSize {
    NORMAL, MEDIUM, LARGE
}

enum class ContentBadgeType {
    FILLED, OUTLINED
}

enum class ContentBadgeColor {
    NEUTRAL, ACCENT
}

class WantedContentBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ContentBadgeSize
    var text by mutableStateOf("")
    var type by mutableStateOf(ContentBadgeType.FILLED)
    var textColor by mutableStateOf(R.color.label_alternative)
    var backgroundColor by mutableStateOf<Int?>(null)
    var lineColor by mutableStateOf<Int?>(null)
    var leftDrawable by mutableStateOf<Int?>(null)
    var rightDrawable by mutableStateOf<Int?>(null)
    var backgroundAlpha by mutableStateOf(1f)

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.WantedContentBadge).run {
                text = getString(R.styleable.WantedContentBadge_text) ?: ""
                type = ContentBadgeType.entries[getInteger(
                    R.styleable.WantedContentBadge_badge_type,
                    0
                )]
                size = ContentBadgeSize.entries[getInteger(
                    R.styleable.WantedContentBadge_badge_size,
                    0
                )]
                textColor = getResourceId(
                    R.styleable.WantedContentBadge_textColor,
                    R.color.label_alternative
                )
                backgroundColor = getResourceId(R.styleable.WantedContentBadge_backgroundColor, 0)
                backgroundAlpha = getFloat(R.styleable.WantedContentBadge_backgroundAlpha, 1f)
                lineColor = getResourceId(R.styleable.WantedContentBadge_lineColor, 0)
                leftDrawable = getResourceId(R.styleable.WantedContentBadge_leftDrawable, 0)
                rightDrawable = getResourceId(R.styleable.WantedContentBadge_rightDrawable, 0)

                recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        WantedContentBadgeOld(
            text = text,
            type = type,
            size = size,
            textColor = colorResource(id = textColor),
            backgroundColor = if (backgroundColor != 0) colorResource(id = backgroundColor!!).copy(
                alpha = backgroundAlpha
            ) else null,
            lineColor = if (lineColor != 0) colorResource(id = lineColor!!) else null,
            leftDrawable = if (leftDrawable != 0) leftDrawable else null,
            rightDrawable = if (rightDrawable != 0) rightDrawable else null
        )
    }
}

@Composable
fun WantedContentBadge(
    modifier: Modifier = Modifier,
    text: String,
    type: ContentBadgeType = ContentBadgeType.FILLED,
    size: ContentBadgeSize = ContentBadgeSize.NORMAL,
    color: ContentBadgeColor = ContentBadgeColor.NEUTRAL,
    accentDefault: WantedContentBadgeDefault = WantedContentBadgeDefaults.getAccentDefault(),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    onClick: (() -> Unit)? = null
) {
    WantedContentBadgeLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getRadius(size = size)))
            .background(getBackground(type = type, color = color, default = accentDefault))
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getRadius(size = size)),
                color = getOutlineColor(type = type, color = color, default = accentDefault)
            )
            .clickOnceForDesignSystem(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (color == ContentBadgeColor.NEUTRAL) {
                    wantedRippleEffect(colorResource(id = R.color.label_normal).copy(OPACITY_12))
                } else {
                    wantedRippleEffect(accentDefault.backgroundColor.copy(OPACITY_12))
                },
                enabled = onClick != null
            ) { onClick?.invoke() },
        size = size,
        text = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = getContentColor(color = color, default = accentDefault)
            )
        },
        leftContent = leftDrawable?.let {
            {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = getContentColor(color = color, default = accentDefault)
                )
            }
        },
        rightContent = rightDrawable?.let {
            {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = getContentColor(color = color, default = accentDefault)
                )
            }
        }
    )
}

@Composable
fun WantedContentBadgeOld(
    text: String,
    type: ContentBadgeType = ContentBadgeType.FILLED,
    size: ContentBadgeSize = ContentBadgeSize.NORMAL,
    textColor: Color,
    backgroundColor: Color? = null,
    lineColor: Color? = null,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    leftContent: @Composable (BoxScope.() -> Unit)? = null,
    rightContent: @Composable (BoxScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val roundedCornerShape = RoundedCornerShape(getRadius(size))

    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(roundedCornerShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .then(if (type == ContentBadgeType.FILLED) {
                backgroundColor?.let {
                    Modifier.background(color = it, shape = roundedCornerShape)
                } ?: Modifier
            } else {
                lineColor?.let {
                    Modifier.border(
                        BorderStroke(1.dp, it), roundedCornerShape
                    )
                } ?: Modifier
            })
            .padding(horizontal = getPadding(size).first, vertical = getPadding(size).second),
        horizontalArrangement = Arrangement.spacedBy(
            space = when (size) {
                ContentBadgeSize.LARGE -> 4.dp
                ContentBadgeSize.MEDIUM -> 3.dp
                ContentBadgeSize.NORMAL -> 2.dp
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent?.let {
            Box(
                modifier = getContentBadgeDrawableSize(size),
            ) {
                it()
            }
        }
        leftDrawable?.let {
            Image(
                painter = painterResource(id = it), modifier = getContentBadgeDrawableSize(size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(textColor)
            )
        }
        Text(
            text = text,
            modifier = Modifier.wrapContentSize(),
            style = getContentBadgeTypography(size),
            color = textColor.copy(alpha = 1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        rightDrawable?.let {
            Image(
                painter = painterResource(id = it),
                modifier = getContentBadgeDrawableSize(size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(textColor)
            )
        }
        rightContent?.let {
            Box(
                modifier = getContentBadgeDrawableSize(size),
            ) {
                it()
            }
        }
    }
}


@Composable
fun WantedContentBadgeLayout(
    modifier: Modifier = Modifier,
    size: ContentBadgeSize,
    leftContent: @Composable (BoxScope.() -> Unit)? = null,
    text: @Composable () -> Unit,
    rightContent: @Composable (BoxScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(getRadius(size)))
            .padding(horizontal = getHorizontalPadding(size))
            .padding(vertical = getVerticalPadding(size)),
        horizontalArrangement = Arrangement.spacedBy(space = getHorizontalAlimentSpace(size)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent?.let {
            Box(modifier = Modifier.size(getIconSize(size))) {
                leftContent()
            }
        }

        ProvideTextStyle(value = getContentBadgeTypography(size = size)) {
            text()
        }

        rightContent?.let {
            Box(modifier = Modifier.size(getIconSize(size))) {
                rightContent()
            }
        }
    }
}


@Composable
private fun getRadius(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.LARGE -> 8.dp
    ContentBadgeSize.MEDIUM -> 6.dp
    ContentBadgeSize.NORMAL -> 6.dp
}

@Composable
private fun getHorizontalPadding(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.LARGE -> 8.dp
    ContentBadgeSize.MEDIUM -> 6.dp
    ContentBadgeSize.NORMAL -> 6.dp
}

@Composable
private fun getVerticalPadding(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.LARGE -> 7.dp
    ContentBadgeSize.MEDIUM -> 4.dp
    ContentBadgeSize.NORMAL -> 3.dp
}

@Composable
private fun getHorizontalAlimentSpace(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.LARGE -> 4.dp
    ContentBadgeSize.MEDIUM -> 3.dp
    ContentBadgeSize.NORMAL -> 1.dp
}

@Composable
private fun getIconSize(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.LARGE -> 16.dp
    ContentBadgeSize.MEDIUM -> 13.dp
    ContentBadgeSize.NORMAL -> 12.dp
}

@Composable
private fun getBackground(
    type: ContentBadgeType,
    color: ContentBadgeColor,
    default: WantedContentBadgeDefault
): Color {
    return if (type == ContentBadgeType.FILLED) {
        if (color == ContentBadgeColor.NEUTRAL) {
            colorResource(id = R.color.fill_normal)
        } else {
            default.backgroundColor
        }
    } else {
        colorResource(id = R.color.transparent)
    }
}


@Composable
private fun getOutlineColor(
    type: ContentBadgeType,
    color: ContentBadgeColor,
    default: WantedContentBadgeDefault
): Color {
    return if (type == ContentBadgeType.FILLED) {
        colorResource(id = R.color.transparent)
    } else {
        if (color == ContentBadgeColor.NEUTRAL) {
            colorResource(id = R.color.label_alternative).copy(OPACITY_16)
        } else {
            default.outLineColor
        }
    }
}

@Composable
private fun getContentColor(
    color: ContentBadgeColor,
    default: WantedContentBadgeDefault
): Color {
    return if (color == ContentBadgeColor.NEUTRAL) {
        colorResource(id = R.color.label_alternative)
    } else {
        default.contentColor
    }
}


private fun getPadding(size: ContentBadgeSize): Pair<Dp, Dp> =
    when (size) {
        ContentBadgeSize.NORMAL -> Pair(4.dp, 3.dp)
        ContentBadgeSize.MEDIUM -> Pair(8.dp, 4.dp)
        ContentBadgeSize.LARGE -> Pair(12.dp, 6.dp)
    }

@Composable
private fun getContentBadgeTypography(
    size: ContentBadgeSize
): TextStyle =
    getTextStyle(
        textStyle = when (size) {
            ContentBadgeSize.LARGE -> WantedTextStyle.LABEL2_MEDIUM
            ContentBadgeSize.MEDIUM -> WantedTextStyle.CAPTION1_MEDIUM
            ContentBadgeSize.NORMAL -> WantedTextStyle.CAPTION2_MEDIUM
        }
    )

@Preview
@Composable
private fun PreviewContentBadges() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.background_normal_normal)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(colorResource(id = R.color.background_normal_normal)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                /**
                 * NORMAL
                 */
                Text(text = "NORMAL")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        color = ContentBadgeColor.ACCENT,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.NORMAL,
                        color = ContentBadgeColor.ACCENT,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                }

                /**
                 * MEDIUM
                 */
                Text(text = "MEDIUM")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        color = ContentBadgeColor.ACCENT,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.MEDIUM,
                        color = ContentBadgeColor.ACCENT,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                }

                /**
                 * LARGE
                 */
                Text(text = "LARGE")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        color = ContentBadgeColor.ACCENT,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.LARGE,
                        color = ContentBadgeColor.ACCENT,
                        type = ContentBadgeType.OUTLINED,
                        leftDrawable = R.drawable.ic_normal_bookmark_svg,
                        rightDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                }
            }
        }
    }
}