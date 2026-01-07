package com.wanted.android.wanted.design.actions.button

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.actions.button.config.buttonDrawableSize
import com.wanted.android.wanted.design.actions.button.config.buttonHeight
import com.wanted.android.wanted.design.actions.button.config.buttonHorizontalPadding
import com.wanted.android.wanted.design.actions.button.config.buttonVerticalPadding
import com.wanted.android.wanted.design.actions.button.config.buttonWidth
import com.wanted.android.wanted.design.actions.button.view.WantedButtonLayout
import com.wanted.android.wanted.design.actions.button.view.WantedButtonSideIcon
import com.wanted.android.wanted.design.loading.loading.WantedCircularProgressIndicator
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.getButtonWidth
import com.wanted.android.wanted.design.util.wantedRippleEffect


class WantedSolidButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    lateinit var size: ButtonSize
    var text by mutableStateOf("")
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
        WantedSolidButton(
            text = text,
            modifier = Modifier.getButtonWidth(buttonWidth = buttonWidth),
            size = size,
            enabled = buttonStatus,
            leadingDrawable = if (leftDrawable != 0) leftDrawable else null,
            trailingDrawable = if (rightDrawable != 0) rightDrawable else null,
            onClick = onClickListener
        )
    }
}

@Composable
internal fun WantedSolidButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {},
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        variant = ButtonVariant.SOLID,
        type = type,
        size = size,
        enabled = enabled
    )
) {
    WantedButtonLayout(
        modifier = modifier
            .background(
                color = buttonDefault.backgroundColor,
                shape = buttonDefault.borderShape
            )
            .clip(buttonDefault.borderShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = wantedRippleEffect(DesignSystemTheme.colorsOpacity.labelNormalOpacity12),
                enabled = buttonDefault.enabled,
                onClick = {
                    if (!isLoading) {
                        onClick.clickOnce()
                    }
                }
            )
            .buttonHeight(ButtonVariant.SOLID, buttonDefault.size)
            .buttonWidth(buttonDefault.size, text.isEmpty())
            .buttonVerticalPadding(text.isNotEmpty())
            .buttonHorizontalPadding(ButtonVariant.SOLID, buttonDefault.size, text.isEmpty()),
        horizontalArrangement = Arrangement.spacedBy(
            space = when (size) {
                ButtonSize.LARGE -> 6.dp
                ButtonSize.MEDIUM -> 5.dp
                else -> 4.dp
            },
            alignment = Alignment.CenterHorizontally
        ),
        leftDrawable = leadingDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = Modifier
                        .buttonDrawableSize(
                            variant = ButtonVariant.SOLID,
                            size = buttonDefault.size
                        )
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = buttonDefault.leftIconTintColor
                )
            }
        },
        text = when {
            text.isNotEmpty() -> {
                {
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
        rightDrawable = trailingDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = Modifier
                        .buttonDrawableSize(
                            variant = ButtonVariant.SOLID,
                            size = buttonDefault.size
                        )
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = buttonDefault.rightIconTintColor
                )
            }
        },
        loading = if (isLoading) {
            {
                WantedCircularProgressIndicator(
                    modifier = Modifier.size(buttonDefault.loadingSize),
                    color = buttonDefault.loadingColor
                )
            }
        } else null
    )
}

@Preview
@Composable
private fun PreviewSolidButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DesignSystemTheme.colors.backgroundNormalNormal),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewWantedSolidButtonLoading()

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
private fun PreviewWantedSolidButtonIconOnlySmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark_fill
        )

        WantedSolidButton(
            text = "",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark_fill
        )
    }
}


@Preview
@Composable
private fun PreviewWantedSolidButtonLoading() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            isLoading = true,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            isLoading = true,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonSmallRightDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonSmallTwoDrawablesEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonMediumEnable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonLargeEnable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonLargeMaxWidthEnable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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

        WantedSolidButton(
            text = "Button",
            isLoading = true,
            size = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonSmallLeftDrawableDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonSmallRightDrawableDisable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonSmallTwoDrawablesDisable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.SMALL,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedSolidButtonMediumDisable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.MEDIUM,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonLargeDisable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.wrapContentSize()
        )

        WantedSolidButton(
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
private fun PreviewWantedSolidButtonLargeMaxWidthDisable() {

    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
            text = "Button",
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )

        WantedSolidButton(
            text = "Button",
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
