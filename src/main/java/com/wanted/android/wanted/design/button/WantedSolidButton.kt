package com.wanted.android.wanted.design.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
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
import com.wanted.android.wanted.design.util.getButtonRadius
import com.wanted.android.wanted.design.util.getButtonTypography
import com.wanted.android.wanted.design.util.getButtonWidth
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 *
 * Button Design System link
 * https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=423-8298&t=YgKbX4F2B73vuTbS-0
 *
 * Button Design System Guide
 * https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1373-26592&t=a5QyDszAwnnfpwdQ-0
 */
class WantedSolidButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ButtonSize
    var text by mutableStateOf("")
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
        WantedSolidButton(
            text = text,
            modifier = getButtonWidth(buttonWidth = buttonWidth),
            size = size,
            status = buttonStatus,
            leftDrawable = if (leftDrawable != 0) leftDrawable else null,
            rightDrawable = if (rightDrawable != 0) rightDrawable else null,
            clickListener = onClickListener
        )
    }
}

@Composable
fun WantedSolidButton(
    modifier: Modifier = Modifier,
    text: String = "",
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    clickListener: () -> Unit = {}
) {
    val textColor = remember(status, type) {
        when {
            status != ButtonStatus.ENABLE -> R.color.label_assistive
            type == ButtonType.ASSISTIVE -> R.color.label_neutral
            else -> R.color.static_white
        }
    }

    val backgroundColor = remember(status, type) {
        when {
            status != ButtonStatus.ENABLE -> R.color.interaction_disable
            type == ButtonType.ASSISTIVE -> R.color.fill_normal
            else -> R.color.primary_normal
        }
    }

    WantedButtonLayout(
        modifier = modifier
            .background(
                colorResource(id = backgroundColor),
                RoundedCornerShape(size = getButtonRadius(ButtonShape.SOLID, size = size))
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = wantedRippleEffect(colorResource(id = R.color.label_normal_opacity12)),
                enabled = status == ButtonStatus.ENABLE,
                onClick = {
                    clickListener.clickOnceForDesignSystem()
                }
            ),
        buttonShape = ButtonShape.SOLID,
        buttonSize = size,
        leftDrawable = leftDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = getButtonDrawableSize(shape = ButtonShape.SOLID, size = size),
                    drawableRes = it,
                    tint = colorResource(id = textColor)
                )
            }
        },
        text = if (text.isNotEmpty()) {
            {
                Text(
                    text = text,
                    modifier = Modifier
                        .wrapContentHeight(),
                    style = getButtonTypography(shape = ButtonShape.SOLID, type, size = size),
                    color = colorResource(id = textColor),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            null
        },
        rightDrawable = rightDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = getButtonDrawableSize(shape = ButtonShape.SOLID, size = size),
                    drawableRes = it,
                    tint = colorResource(id = textColor)
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewSolidButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.background_normal_normal)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewWantedSolidButtonSmallNoDrawableEnable()

        PreviewWantedSolidButtonSmallLeftDrawableEnable()

        PreviewWantedSolidButtonSmallRightDrawableEnable()

        PreviewWantedSolidButtonSmallTwoDrawablesEnable()

        PreviewWantedSolidButtonMediumEnable()

        PreviewWantedSolidButtonLargeEnable()

        PreviewWantedSolidButtonLargeMaxWidthEnable()

        PreviewWantedSolidButtonSmallNoDrawableDisable()

        PreviewWantedSolidButtonSmallLeftDrawableDisable()

        PreviewWantedSolidButtonSmallRightDrawableDisable()

        PreviewWantedSolidButtonSmallTwoDrawablesDisable()

        PreviewWantedSolidButtonMediumDisable()

        PreviewWantedSolidButtonLargeDisable()

        PreviewWantedSolidButtonLargeMaxWidthDisable()
    }
}


@Preview
@Composable
fun PreviewWantedSolidButtonIconOnlySmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedSolidButton(
            text = "",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_fill_svg
        )

        WantedSolidButton(
            text = "",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_fill_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )


        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }

}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallRightDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallTwoDrawablesEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonMediumEnable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonLargeEnable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonLargeMaxWidthEnable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallLeftDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallRightDrawableDisable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonSmallTwoDrawablesDisable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
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
fun PreviewWantedSolidButtonMediumDisable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonLargeDisable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedSolidButtonLargeMaxWidthDisable() {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            status = ButtonStatus.DISABLE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
