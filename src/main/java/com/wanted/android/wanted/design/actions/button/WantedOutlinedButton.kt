package com.wanted.android.wanted.design.actions.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.actions.button.view.WantedButtonLayout
import com.wanted.android.wanted.design.actions.button.view.WantedButtonSideIcon
import com.wanted.android.wanted.design.loading.loading.WantedCircularProgressIndicator
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.getButtonDrawableSize
import com.wanted.android.wanted.design.util.getButtonRadius
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
class WantedOutlinedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ButtonSize
    var text by mutableStateOf("")
    var buttonType by mutableStateOf(ButtonType.PRIMARY)
    var buttonStatus by mutableStateOf(true)
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
                buttonStatus = getBoolean(R.styleable.WantedButton_enabled, true)
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
        buttonStatus = enabled
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
            enabled = buttonStatus,
            leftDrawable = if (leftDrawable != 0) leftDrawable else null,
            rightDrawable = if (rightDrawable != 0) rightDrawable else null,
            clickListener = onClickListener
        )
    }
}


@Composable
internal fun WantedOutlinedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        shape = ButtonShape.OUTLINED,
        type = type,
        size = size,
        enabled = enabled
    ),
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    clickListener: () -> Unit = {}
) {
    WantedButtonLayout(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    size = getButtonRadius(
                        ButtonShape.SOLID,
                        size = buttonDefault.size
                    )
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = wantedRippleEffect(
                    if (type == ButtonType.PRIMARY) {
                        buttonDefault.contentColor.copy(alpha = OPACITY_12)
                    } else {
                        colorResource(id = R.color.label_normal_opacity12)
                    }
                ),
                enabled = enabled,
                onClick = {
                    if (!isLoading) {
                        clickListener.clickOnce()
                    }
                }
            )
            .border(
                BorderStroke(1.dp, buttonDefault.borderColor),
                RoundedCornerShape(size = getButtonRadius(ButtonShape.OUTLINED, buttonDefault.size))
            ),
        buttonShape = ButtonShape.OUTLINED,
        buttonSize = buttonDefault.size,
        leftDrawable = leftDrawable?.let {
            {
                if (!isLoading) {
                    WantedButtonSideIcon(
                        modifier = getButtonDrawableSize(
                            shape = ButtonShape.OUTLINED,
                            size = buttonDefault.size
                        ),
                        drawableRes = it,
                        tint = buttonDefault.leftIconTintColor
                    )
                }
            }
        },
        text = when {
            text.isNotEmpty() -> {
                {
                    if (isLoading) {
                        WantedCircularProgressIndicator(
                            modifier = Modifier.size(buttonDefault.loadingSize),
                            color = buttonDefault.loadingColor
                        )
                    }
                    Text(
                        text = text,
                        modifier = Modifier
                            .wrapContentHeight()
                            .alpha(if (isLoading) 0f else 1f),
                        style = buttonDefault.textStyle,
                        color = buttonDefault.contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> null
        },
        rightDrawable = rightDrawable?.let {
            {
                if (!isLoading) {
                    WantedButtonSideIcon(
                        modifier = getButtonDrawableSize(
                            shape = ButtonShape.OUTLINED,
                            size = buttonDefault.size
                        ),
                        drawableRes = it,
                        tint = buttonDefault.rightIconTintColor
                    )
                }
            }
        }
    )
}

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
        PreviewWantedOutlinedButtonLoading()

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
fun PreviewWantedOutlinedButtonIconOnlySmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_fill_svg
        )

        WantedOutlinedButton(
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}


@Preview
@Composable
fun PreviewWantedOutlinedButtonLoading() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.MEDIUM,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallRightDrawableEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallTwoDrawablesEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
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
fun PreviewWantedOutlinedButtonMediumEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeMaxWidthEnable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallLeftDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallRightDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonSmallTwoDrawablesDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leftDrawable = R.drawable.ic_normal_bookmark_svg,
            rightDrawable = R.drawable.ic_normal_heart_svg
        )

    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonMediumDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewWantedOutlinedButtonLargeMaxWidthDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )

        WantedOutlinedButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
