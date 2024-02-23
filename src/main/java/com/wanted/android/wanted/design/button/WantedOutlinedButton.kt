package com.wanted.android.wanted.design.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.getButtonDrawableSize
import com.wanted.android.wanted.design.util.getButtonHeight
import com.wanted.android.wanted.design.util.getButtonHorizontalPadding
import com.wanted.android.wanted.design.util.getButtonRadius
import com.wanted.android.wanted.design.util.getButtonSpaceBetweenTextAndIcon
import com.wanted.android.wanted.design.util.getButtonTypography
import com.wanted.android.wanted.design.util.getButtonWidth
import com.wanted.android.wanted.design.util.wantedRippleEffect

class WantedOutlinedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ButtonSize
    var text by mutableStateOf("")
    var buttonType by mutableStateOf(ButtonType.PRIMARY)
    var buttonStatus by mutableStateOf(ButtonStatus.ENABLE)
    var leftDrawable by mutableStateOf<Int?>(null)
    var rightDrawable by mutableStateOf<Int?>(null)
    var isClickOnce by mutableStateOf(true)
    private var buttonWidth: Int = -1
    private var onClickListener by mutableStateOf({})


    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.WantedButton).run {
                text = getString(R.styleable.WantedButton_text) ?: ""
                buttonType =
                    ButtonType.entries[getInteger(R.styleable.WantedButton_button_type, 0)]
                size = ButtonSize.entries[getInteger(R.styleable.WantedButton_button_size, 0)]
                leftDrawable = getResourceId(R.styleable.WantedButton_leftDrawable, 0)
                rightDrawable = getResourceId(R.styleable.WantedButton_rightDrawable, 0)
                buttonStatus = when (getBoolean(R.styleable.WantedButton_enabled, true)) {
                    true -> ButtonStatus.ENABLE
                    else -> ButtonStatus.DISABLE
                }
                isClickOnce = getBoolean(R.styleable.WantedButton_clickOnce, true)

                recycle()
            }

            context.obtainStyledAttributes(it, R.styleable.Layout).run {
                buttonWidth = getLayoutDimension(R.styleable.Layout_android_layout_width, -2)

                recycle()
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        buttonStatus = when (enabled) {
            true -> ButtonStatus.ENABLE
            else -> ButtonStatus.DISABLE
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        onClickListener = { listener?.onClick(this) }
    }

    @Composable
    override fun Content() {
        WantedOutlinedButton(
            text = text,
            modifier = getButtonWidth(buttonWidth = buttonWidth),
            type = buttonType,
            size = size,
            status = buttonStatus,
            leftDrawable = if (leftDrawable != 0) leftDrawable else null,
            rightDrawable = if (rightDrawable != 0) rightDrawable else null,
            isClickOnce = isClickOnce,
            clickListener = onClickListener
        )
    }
}

@Composable
fun WantedOutlinedButton(
    text: String,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    modifier: Modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    isClickOnce: Boolean = true,
    clickListener: (() -> Unit)? = null,
) {
    val disableTextColor = R.color.label_disable

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(size = getButtonRadius(ButtonShape.OUTLINED, type, size)))
            .border(
                BorderStroke(1.dp, getStrokeColor(status, type)),
                RoundedCornerShape(size = getButtonRadius(ButtonShape.OUTLINED, type, size))
            )
            .height(getButtonHeight(ButtonShape.OUTLINED, type, size))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (status == ButtonStatus.ENABLE) wantedRippleEffect() else null,
                onClick = {
                    if (status == ButtonStatus.ENABLE) {
                        if (isClickOnce) {
                            clickListener?.clickOnce()
                        } else {
                            clickListener?.invoke()
                        }
                    }
                }
            )
            .padding(horizontal = getButtonHorizontalPadding(ButtonShape.OUTLINED, type, size)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftDrawable?.let {
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(ButtonShape.OUTLINED, type, size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(
                    color = colorResource(
                        id = if (status == ButtonStatus.ENABLE) {
                            getEnableTextColor(type)
                        } else disableTextColor
                    )
                )
            )
            Spacer(
                modifier = Modifier.width(
                    getButtonSpaceBetweenTextAndIcon(
                        ButtonShape.OUTLINED,
                        type,
                        size
                    )
                )
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .wrapContentHeight(),
            style = getButtonTypography(ButtonShape.OUTLINED, type, size),
            color = if (status == ButtonStatus.ENABLE) {
                colorResource(id = getEnableTextColor(type))
            } else colorResource(id = disableTextColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        rightDrawable?.let {
            Spacer(
                modifier = Modifier.width(
                    getButtonSpaceBetweenTextAndIcon(
                        ButtonShape.OUTLINED,
                        type,
                        size
                    )
                )
            )
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(ButtonShape.OUTLINED, type, size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(
                    color = colorResource(
                        id = if (status == ButtonStatus.ENABLE) {
                            getEnableTextColor(type)
                        } else disableTextColor
                    )
                )
            )
        }
    }
}

@Composable
private fun getEnableTextColor(type: ButtonType): Int =
    when (type) {
        ButtonType.ASSISTIVE -> R.color.label_normal
        else -> R.color.primary_normal
    }

@Composable
private fun getStrokeColor(status: ButtonStatus, type: ButtonType): Color = colorResource(
    if (status == ButtonStatus.ENABLE && type == ButtonType.PRIMARY) {
        R.color.primary_normal
    } else {
        R.color.line_normal_normal
    }
)

@Preview
@Composable
fun PreviewOutlinedButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.background_normal_normal)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewWantedOutlinedButtonSmallNoDrawableEnable()

        PreviewWantedOutlinedButtonSmallLeftDrawableEnable()

        PreviewWantedOutlinedButtonSmallRightDrawableEnable()

        PreviewWantedOutlinedButtonSmallTwoDrawablesEnable()

        PreviewWantedOutlinedButtonMediumEnable()

        PreviewWantedOutlinedButtonLargeEnable()

        PreviewWantedOutlinedButtonLargeMaxWidthEnable()

        PreviewWantedOutlinedButtonSmallNoDrawableDisable()

        PreviewWantedOutlinedButtonSmallLeftDrawableDisable()

        PreviewWantedOutlinedButtonSmallRightDrawableDisable()

        PreviewWantedOutlinedButtonSmallTwoDrawablesDisable()

        PreviewWantedOutlinedButtonMediumDisable()

        PreviewWantedOutlinedButtonLargeDisable()

        PreviewWantedOutlinedButtonLargeMaxWidthDisable()
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallNoDrawableEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallLeftDrawableEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        modifier = Modifier.wrapContentSize(),
        leftDrawable = R.drawable.ic_normal_bookmark_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallRightDrawableEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        modifier = Modifier.wrapContentSize(),
        rightDrawable = R.drawable.ic_normal_heart_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallTwoDrawablesEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        modifier = Modifier.wrapContentSize(),
        leftDrawable = R.drawable.ic_normal_bookmark_svg,
        rightDrawable = R.drawable.ic_normal_heart_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonMediumEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.MEDIUM,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.LARGE,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeMaxWidthEnable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.LARGE,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallNoDrawableDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallLeftDrawableDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize(),
        leftDrawable = R.drawable.ic_normal_bookmark_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallRightDrawableDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize(),
        rightDrawable = R.drawable.ic_normal_heart_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallTwoDrawablesDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.SMALL,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize(),
        leftDrawable = R.drawable.ic_normal_bookmark_svg,
        rightDrawable = R.drawable.ic_normal_heart_svg
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonMediumDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.MEDIUM,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.LARGE,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeMaxWidthDisable() {
    WantedOutlinedButton(
        text = "Button",
        size = ButtonSize.LARGE,
        status = ButtonStatus.DISABLE,
        modifier = Modifier.fillMaxWidth()
    )
}
