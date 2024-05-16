package com.wanted.android.wanted.design.badge

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.getContentBadgeDrawableSize
import com.wanted.android.wanted.design.util.getContentBadgeSpaceBetweenTextAndIcon
import com.wanted.android.wanted.design.util.getContentBadgeTypography

enum class ContentBadgeSize {
    XSMALL, SMALL, MEDIUM
}

enum class ContentBadgeType {
    FILLED, OUTLINED
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
        WantedContentBadge(
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
    text: String,
    type: ContentBadgeType = ContentBadgeType.FILLED,
    size: ContentBadgeSize = ContentBadgeSize.XSMALL,
    textColor: Color,
    backgroundColor: Color? = null,
    lineColor: Color? = null,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null
) {
    val roundedCornerShape = RoundedCornerShape(getRadius(size))

    Row(modifier = Modifier
        .wrapContentSize()
        .clip(roundedCornerShape)
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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        leftDrawable?.let {
            Image(
                painter = painterResource(id = it), modifier = getContentBadgeDrawableSize(size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(textColor)
            )
            Spacer(
                modifier = Modifier.width(
                    getContentBadgeSpaceBetweenTextAndIcon(size)
                )
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
            Spacer(
                modifier = Modifier.width(
                    getContentBadgeSpaceBetweenTextAndIcon(size)
                )
            )
            Image(
                painter = painterResource(id = it),
                modifier = getContentBadgeDrawableSize(size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(textColor)
            )
        }
    }
}

@Composable
private fun getRadius(size: ContentBadgeSize) = when (size) {
    ContentBadgeSize.MEDIUM -> 8.dp
    ContentBadgeSize.SMALL -> 6.dp
    ContentBadgeSize.XSMALL -> 4.dp
}

private fun getPadding(size: ContentBadgeSize): Pair<Dp, Dp> =
    when (size) {
        ContentBadgeSize.XSMALL -> Pair(4.dp, 3.dp)
        ContentBadgeSize.SMALL -> Pair(8.dp, 4.dp)
        ContentBadgeSize.MEDIUM -> Pair(12.dp, 6.dp)
    }

@Preview
@Composable
fun PreviewContentBadges() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.background_normal_normal)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewWantedContentBadgeXSmallNoDrawable()

        PreviewWantedContentBadgeXSmallLeftDrawable()

        PreviewWantedContentBadgeXSmallRightDrawable()

        PreviewWantedContentBadgeSmallOutlined()

        PreviewWantedContentBadgeMediumOutlined()
    }
}

@Preview
@Composable
fun PreviewWantedContentBadgeXSmallNoDrawable() {
    WantedContentBadge(
        text = "Button",
        size = ContentBadgeSize.XSMALL,
        textColor = colorResource(id = R.color.label_alternative),
        backgroundColor = colorResource(id = R.color.fill_normal),
    )
}

@Preview
@Composable
fun PreviewWantedContentBadgeXSmallLeftDrawable() {
    WantedContentBadge(
        text = "Button",
        size = ContentBadgeSize.XSMALL,
        textColor = colorResource(id = R.color.label_alternative),
        backgroundColor = colorResource(id = R.color.fill_normal),
        leftDrawable = R.drawable.ic_normal_bookmark_svg
    )
}

@Preview
@Composable
fun PreviewWantedContentBadgeXSmallRightDrawable() {
    WantedContentBadge(
        text = "Button",
        size = ContentBadgeSize.XSMALL,
        textColor = colorResource(id = R.color.label_alternative),
        backgroundColor = colorResource(id = R.color.fill_normal),
        rightDrawable = R.drawable.ic_normal_heart_svg
    )
}

@Preview
@Composable
fun PreviewWantedContentBadgeSmallOutlined() {
    WantedContentBadge(
        text = "Button",
        size = ContentBadgeSize.SMALL,
        type = ContentBadgeType.OUTLINED,
        textColor = colorResource(id = R.color.accent_cyan),
        lineColor = colorResource(id = R.color.accent_cyan)
    )
}

@Preview
@Composable
fun PreviewWantedContentBadgeMediumOutlined() {
    WantedContentBadge(
        text = "Button",
        size = ContentBadgeSize.MEDIUM,
        type = ContentBadgeType.OUTLINED,
        textColor = colorResource(id = R.color.accent_cyan),
        lineColor = colorResource(id = R.color.accent_cyan)
    )
}
