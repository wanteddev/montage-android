package com.wanted.android.wanted.design.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.view.WantedButtonLayout
import com.wanted.android.wanted.design.button.view.WantedButtonSideIcon
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.getButtonDrawableSize
import com.wanted.android.wanted.design.util.getButtonSpaceBetweenTextAndIcon
import com.wanted.android.wanted.design.util.getButtonTypography
import com.wanted.android.wanted.design.util.getTextButtonSize
import com.wanted.android.wanted.design.util.getWantedRippleEffect

class WantedTextButton @JvmOverloads constructor(
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
    private var buttonHeight: Int = -1
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
                buttonHeight = getLayoutDimension(R.styleable.Layout_android_layout_height, -2)

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
        NewWantedTextButton(
            text = text,
            modifier = getTextButtonSize(buttonWidth = buttonWidth, buttonHeight = buttonHeight),
            type = buttonType,
            size = size,
            status = buttonStatus,
            leftDrawable = if (leftDrawable != 0) leftDrawable else null,
            rightDrawable = if (rightDrawable != 0) rightDrawable else null,
            clickListener = onClickListener
        )
    }
}

@Composable
fun NewWantedTextButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    clickListener: () -> Unit = {}
) {
    val textColor = remember(status, type) {
        when {
            status != ButtonStatus.ENABLE -> R.color.label_disable
            type == ButtonType.ASSISTIVE -> R.color.label_alternative
            else -> R.color.primary_normal
        }
    }

    WantedButtonLayout(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = getWantedRippleEffect(type),
            enabled = status == ButtonStatus.ENABLE,
            onClick = {
                clickListener.clickOnceForDesignSystem()
            }
        ),
        buttonShape = ButtonShape.TEXT,
        buttonSize = size,
        leftDrawable = leftDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = getButtonDrawableSize(shape = ButtonShape.TEXT, size = size),
                    drawableRes = it,
                    tint = colorResource(id = textColor)
                )
            }
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.wrapContentHeight(),
                style = getButtonTypography(shape = ButtonShape.TEXT, type, size = size),
                color = colorResource(id = textColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        rightDrawable = rightDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = getButtonDrawableSize(shape = ButtonShape.TEXT, size = size),
                    drawableRes = it,
                    tint = colorResource(id = textColor)
                )
            }
        }
    )
}

@Composable
fun WantedTextButton(
    text: String,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    modifier: Modifier = Modifier.wrapContentSize(),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    isClickOnce: Boolean = true,
    clickListener: (() -> Unit)? = null,
) {
    val disableTextColor = R.color.label_disable

    val roundedCornerShape = RoundedCornerShape(6.dp)

    Row(
        modifier = modifier
            .clip(roundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = getWantedRippleEffect(type),
                onClick = {
                    if (status == ButtonStatus.ENABLE) {
                        if (isClickOnce) {
                            clickListener?.clickOnceForDesignSystem()
                        } else {
                            clickListener?.invoke()
                        }
                    }
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftDrawable?.let {
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(ButtonShape.TEXT, size),
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
                        ButtonShape.TEXT,
                        size
                    )
                )
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .wrapContentHeight(),
            style = getButtonTypography(ButtonShape.TEXT, type, size),
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
                        ButtonShape.TEXT,
                        size
                    )
                )
            )
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(ButtonShape.TEXT, size),
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
        ButtonType.PRIMARY -> R.color.primary_normal
        else -> R.color.label_alternative
    }

@Preview
@Composable
fun PreviewTextButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.background_normal_normal)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewWantedTextButtonSmallNoDrawableEnable()

        PreviewWantedTextButtonSmallLeftDrawableEnable()

        PreviewWantedTextButtonSmallRightDrawableEnable()

        PreviewWantedTextButtonSmallTwoDrawablesEnable()

        PreviewWantedTextButtonMediumEnable()

        PreviewWantedTextButtonLargeEnable()

        PreviewWantedTextButtonLargeMaxWidthEnable()

        PreviewWantedTextButtonSmallNoDrawableDisable()

        PreviewWantedTextButtonSmallLeftDrawableDisable()

        PreviewWantedTextButtonSmallRightDrawableDisable()

        PreviewWantedTextButtonSmallTwoDrawablesDisable()

        PreviewWantedTextButtonMediumDisable()

        PreviewWantedTextButtonLargeDisable()

        PreviewWantedTextButtonLargeMaxWidthDisable()
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallRightDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallTwoDrawablesEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonMediumEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonLargeEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonLargeMaxWidthEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallLeftDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallRightDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonSmallTwoDrawablesDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonMediumDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonLargeDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedTextButtonLargeMaxWidthDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.fillMaxWidth()
        )

        NewWantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
