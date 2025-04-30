package com.wanted.android.wanted.design.feedback.contentbadge

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
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.getContentBadgeDrawableSize
import com.wanted.android.wanted.design.util.getTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45460&m=dev
 */
enum class ContentBadgeSize {
    XSmall, Small, Large
}

enum class ContentBadgeType {
    Solid, Outlined
}

enum class ContentBadgeColor {
    Neutral, Accent
}

class  WantedContentBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ContentBadgeSize
    var text by mutableStateOf("")
    var type by mutableStateOf(ContentBadgeType.Solid)
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
            leadingDrawable = if (leftDrawable != 0) leftDrawable else null,
            trailingDrawable = if (rightDrawable != 0) rightDrawable else null
        )
    }
}

@Composable
fun WantedContentBadge(
    modifier: Modifier = Modifier,
    text: String,
    type: ContentBadgeType = ContentBadgeType.Solid,
    size: ContentBadgeSize = ContentBadgeSize.Small,
    color: ContentBadgeColor = ContentBadgeColor.Neutral,
    accentDefault: WantedContentBadgeDefault = if (color == ContentBadgeColor.Accent) {
        WantedContentBadgeDefaults.getAccentDefault()
    } else {
        WantedContentBadgeDefaults.getNeutralDefault()
    },
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: (() -> Unit)? = null
) {
    WantedContentBadgeLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getRadius(size = size)))
            .background(getBackground(type = type, default = accentDefault))
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getRadius(size = size)),
                color = getOutlineColor(type = type, default = accentDefault)
            )
            .clickOnce(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (color == ContentBadgeColor.Neutral) {
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
                color = getContentColor(default = accentDefault)
            )
        },
        leadingContent = leadingDrawable?.let {
            {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = getContentColor(default = accentDefault)
                )
            }
        },
        trailingContent = trailingDrawable?.let {
            {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = getContentColor(default = accentDefault)
                )
            }
        }
    )
}

@Composable
fun WantedContentBadgeOld(
    text: String,
    type: ContentBadgeType = ContentBadgeType.Solid,
    size: ContentBadgeSize = ContentBadgeSize.XSmall,
    textColor: Color,
    backgroundColor: Color? = null,
    lineColor: Color? = null,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    leadingContent: @Composable (BoxScope.() -> Unit)? = null,
    trailingContent: @Composable (BoxScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val roundedCornerShape = RoundedCornerShape(getRadius(size))

    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(roundedCornerShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .then(if (type == ContentBadgeType.Solid) {
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
                ContentBadgeSize.Large -> 4.dp
                ContentBadgeSize.Small -> 3.dp
                ContentBadgeSize.XSmall -> 2.dp
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.let {
            Box(
                modifier = getContentBadgeDrawableSize(size),
            ) {
                it()
            }
        }
        leadingDrawable?.let {
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
        trailingDrawable?.let {
            Image(
                painter = painterResource(id = it),
                modifier = getContentBadgeDrawableSize(size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(textColor)
            )
        }
        trailingContent?.let {
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
    leadingContent: @Composable (BoxScope.() -> Unit)? = null,
    text: @Composable () -> Unit,
    trailingContent: @Composable (BoxScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(getRadius(size)))
            .padding(horizontal = getHorizontalPadding(size))
            .padding(vertical = getVerticalPadding(size)),
        horizontalArrangement = Arrangement.spacedBy(space = getHorizontalAlimentSpace(size)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.let {
            Box(modifier = Modifier.size(getIconSize(size))) {
                leadingContent()
            }
        }

        ProvideTextStyle(value = getContentBadgeTypography(size = size)) {
            text()
        }

        trailingContent?.let {
            Box(modifier = Modifier.size(getIconSize(size))) {
                trailingContent()
            }
        }
    }
}


@Composable
private fun getRadius(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.Large -> 8.dp
    ContentBadgeSize.Small -> 6.dp
    ContentBadgeSize.XSmall -> 6.dp
}

@Composable
private fun getHorizontalPadding(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.XSmall -> 6.dp
    ContentBadgeSize.Small -> 6.dp
    ContentBadgeSize.Large -> 8.dp
}

@Composable
private fun getVerticalPadding(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.XSmall -> 3.dp
    ContentBadgeSize.Small -> 4.dp
    ContentBadgeSize.Large -> 7.dp
}

@Composable
private fun getHorizontalAlimentSpace(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.XSmall -> 1.dp
    ContentBadgeSize.Small -> 3.dp
    ContentBadgeSize.Large -> 4.dp
}

@Composable
private fun getIconSize(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.Large -> 16.dp
    ContentBadgeSize.Small -> 13.dp
    ContentBadgeSize.XSmall -> 12.dp
}

@Composable
private fun getBackground(
    type: ContentBadgeType,
    default: WantedContentBadgeDefault
): Color {
    return if (type == ContentBadgeType.Solid) {
        default.backgroundColor
    } else {
        colorResource(id = R.color.transparent)
    }
}


@Composable
private fun getOutlineColor(
    type: ContentBadgeType,
    default: WantedContentBadgeDefault
): Color {
    return if (type == ContentBadgeType.Solid) {
        colorResource(id = R.color.transparent)
    } else {
        default.outLineColor
    }
}

@Composable
private fun getContentColor(
    default: WantedContentBadgeDefault
): Color {
    return default.contentColor
}


private fun getPadding(size: ContentBadgeSize): Pair<Dp, Dp> =
    when (size) {
        ContentBadgeSize.XSmall -> Pair(4.dp, 3.dp)
        ContentBadgeSize.Small -> Pair(8.dp, 4.dp)
        ContentBadgeSize.Large -> Pair(12.dp, 6.dp)
    }

@Composable
private fun getContentBadgeTypography(
    size: ContentBadgeSize
): TextStyle =
    getTextStyle(
        textStyle = when (size) {
            ContentBadgeSize.Large -> WantedTextStyle.LABEL2_MEDIUM
            ContentBadgeSize.Small -> WantedTextStyle.CAPTION1_MEDIUM
            ContentBadgeSize.XSmall -> WantedTextStyle.CAPTION2_MEDIUM
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
                        size = ContentBadgeSize.XSmall,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        color = ContentBadgeColor.Accent,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.XSmall,
                        color = ContentBadgeColor.Accent,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
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
                        size = ContentBadgeSize.Small,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        color = ContentBadgeColor.Accent,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Small,
                        color = ContentBadgeColor.Accent,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
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
                        size = ContentBadgeSize.Large,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        color = ContentBadgeColor.Accent,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )

                    WantedContentBadge(
                        modifier = Modifier,
                        text = "Badge",
                        size = ContentBadgeSize.Large,
                        color = ContentBadgeColor.Accent,
                        type = ContentBadgeType.Outlined,
                        leadingDrawable = R.drawable.ic_normal_bookmark_svg,
                        trailingDrawable = R.drawable.ic_normal_bookmark_svg,
                        onClick = {}
                    )
                }
            }
        }
    }
}