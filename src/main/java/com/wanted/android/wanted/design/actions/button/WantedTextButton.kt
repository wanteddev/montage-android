package com.wanted.android.wanted.design.actions.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
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
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.loading.loading.WantedCircularProgressIndicator
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.getButtonDrawableSize
import com.wanted.android.wanted.design.util.getTextButtonSize

class WantedTextButton @JvmOverloads constructor(
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
                buttonStatus = getBoolean(R.styleable.WantedButton_enabled, true)
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
        buttonStatus = enabled
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        onClickListener = { listener?.onClick(this) }
    }

    @Composable
    override fun Content() {
        WantedTextButton(
            text = text,
            modifier = getTextButtonSize(buttonWidth = buttonWidth, buttonHeight = buttonHeight),
            type = buttonType,
            size = size,
            enabled = buttonStatus,
            leadingDrawable = if (leftDrawable != 0) leftDrawable else null,
            trailingDrawable = if (rightDrawable != 0) rightDrawable else null,
            onClick = onClickListener
        )
    }
}

@Composable
internal fun WantedTextButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        shape = ButtonShape.TEXT,
        type = type,
        size = size,
        enabled = enabled
    ),
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {}
) {
    val textColor = remember(buttonDefault.enabled) { mutableStateOf(buttonDefault.contentColor) }

    val rightIconTintColor =
        remember(buttonDefault.enabled) { mutableStateOf(buttonDefault.rightIconTintColor) }
    val leftIconTintColor =
        remember(buttonDefault.enabled) { mutableStateOf(buttonDefault.leftIconTintColor) }

    WantedTouchArea(
        modifier = modifier,
        verticalPadding = 4.dp,
        horizontalPadding = if (size == ButtonSize.SMALL) 6.dp else 7.dp,
        shape = RoundedCornerShape(6.dp),
        enabled = enabled,
        rippleColor = if (type == ButtonType.PRIMARY) {
            buttonDefault.contentColor.copy(alpha = OPACITY_12)
        } else {
            colorResource(id = R.color.label_normal_opacity12)
        },
        content = {
            WantedButtonLayout(
                modifier = Modifier,
                buttonShape = ButtonShape.TEXT,
                buttonSize = buttonDefault.size,
                leftDrawable = leadingDrawable?.let {
                    {
                        if (!isLoading) {
                            WantedButtonSideIcon(
                                modifier = getButtonDrawableSize(
                                    shape = ButtonShape.TEXT,
                                    size = buttonDefault.size
                                ),
                                drawableRes = it,
                                tint = leftIconTintColor.value
                            )
                        }
                    }
                },
                text =
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
                        color = textColor.value,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )

                },
                rightDrawable = trailingDrawable?.let {
                    {
                        if (!isLoading) {
                            WantedButtonSideIcon(
                                modifier = getButtonDrawableSize(
                                    shape = ButtonShape.TEXT,
                                    size = buttonDefault.size
                                ),
                                drawableRes = it,
                                tint = rightIconTintColor.value
                            )
                        }
                    }
                }
            )
        },
        onClick = {
            if (!isLoading) {
                onClick.clickOnce()
            }
        }
    )

}

@Preview
@Composable
private fun PreviewTextButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.background_normal_normal)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(Color.Yellow.copy(alpha = 0.5f))
        ) {
            PreviewWantedTextButtonSmallNoDrawableEnableNoBackground()
        }


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
private fun PreviewWantedTextButtonSmallNoDrawableEnableNoBackground() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )


        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallNoDrawableEnable() {
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


        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            type = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallLeftDrawableEnable() {
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
            leadingDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallRightDrawableEnable() {
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
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallTwoDrawablesEnable() {
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
            leadingDrawable = R.drawable.ic_normal_bookmark_svg,
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg,
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonMediumEnable() {
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

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeEnable() {
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

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeMaxWidthEnable() {
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

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallLeftDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallRightDrawableDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallTwoDrawablesDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg,
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.ic_normal_bookmark_svg,
            trailingDrawable = R.drawable.ic_normal_heart_svg
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonMediumDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeMaxWidthDisable() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.background_normal_normal))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
