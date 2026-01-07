package com.wanted.android.wanted.design.actions.button.textbutton

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
import com.wanted.android.wanted.design.actions.button.view.WantedButtonSideIcon
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.loading.loading.WantedCircularProgressIndicator
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.clickOnce
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
            modifier = Modifier.getTextButtonSize(
                buttonWidth = buttonWidth,
                buttonHeight = buttonHeight
            ),
            color = buttonType,
            size = size,
            enabled = buttonStatus,
            leadingDrawable = if (leftDrawable != 0) leftDrawable else null,
            trailingDrawable = if (rightDrawable != 0) rightDrawable else null,
            onClick = onClickListener
        )
    }
}


/**
 * Text 형태의 버튼을 생성하는 Compose 함수입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTextButton(
 *     text = "확인",
 *     type = ButtonType.PRIMARY,
 *     size = ButtonSize.LARGE,
 *     onClick = { /* 클릭 이벤트 처리 */ }
 * )
 * ```
 *
 * @param text String: 버튼에 표시할 텍스트입니다.
 * @param modifier Modifier: 버튼 외형을 조정하는 Modifier입니다.
 * @param color ButtonType: 버튼의 타입(PRIMARY, ASSISTIVE)을 지정합니다.
 * @param size ButtonSize: 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
 * @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
 * @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
 * @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 * @param buttonDefault: WantedButtonDefault 버튼의 기본 스타일 설정입니다.
 */
@Composable
fun WantedTextButton(
    text: String,
    modifier: Modifier = Modifier,
    color: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {},
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        variant = ButtonVariant.TEXT,
        type = color,
        size = size,
        enabled = enabled
    )
) {
    WantedTouchArea(
        modifier = modifier,
        verticalPadding = 4.dp,
        horizontalPadding = if (size == ButtonSize.SMALL) 6.dp else 7.dp,
        shape = RoundedCornerShape(6.dp),
        enabled = enabled,
        rippleColor = if (color == ButtonType.PRIMARY) {
            buttonDefault.contentColor.copy(alpha = OPACITY_12)
        } else {
            DesignSystemTheme.colorsOpacity.labelNormalOpacity12
        },
        content = {
            WantedTextContent(
                text = text,
                modifier = Modifier,
                type = color,
                size = size,
                enabled = enabled,
                isLoading = isLoading,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                buttonDefault = buttonDefault
            )
        },
        onClick = {
            if (!isLoading) {
                onClick.clickOnce()
            }
        }
    )
}

@Composable
private fun WantedTextContent(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
        variant = ButtonVariant.TEXT,
        type = type,
        size = size,
        enabled = enabled
    )
) {
    val textColor = remember(buttonDefault.enabled, buttonDefault.contentColor) {
        mutableStateOf(buttonDefault.contentColor)
    }

    val rightIconTintColor = remember(buttonDefault.enabled, buttonDefault.rightIconTintColor) {
        mutableStateOf(buttonDefault.rightIconTintColor)
    }

    val leftIconTintColor = remember(buttonDefault.enabled, buttonDefault.leftIconTintColor) {
        mutableStateOf(buttonDefault.leftIconTintColor)
    }

    WantedTextButtonLayout(
        modifier = modifier
            .buttonHeight(ButtonVariant.TEXT, buttonDefault.size)
            .buttonWidth(buttonDefault.size, text.isEmpty())
            .buttonVerticalPadding(text.isNotEmpty())
            .buttonHorizontalPadding(
                ButtonVariant.TEXT,
                buttonDefault.size,
                text.isEmpty()
            ),
        horizontalArrangement = Arrangement.spacedBy(
            space = when (size) {
                ButtonSize.SMALL -> 4.dp
                else -> 5.dp
            },
            alignment = Alignment.CenterHorizontally
        ),
        leftDrawable = leadingDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = Modifier
                        .buttonDrawableSize(
                            variant = ButtonVariant.TEXT,
                            size = buttonDefault.size
                        )
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = leftIconTintColor.value
                )
            }
        },
        text = {
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
                WantedButtonSideIcon(
                    modifier = Modifier
                        .buttonDrawableSize(
                            variant = ButtonVariant.TEXT,
                            size = buttonDefault.size
                        )
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = rightIconTintColor.value
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
private fun PreviewTextButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DesignSystemTheme.colors.backgroundNormalNormal),
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
            color = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            color = ButtonType.ASSISTIVE,
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
            color = ButtonType.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallRightDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallTwoDrawablesEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            isLoading = true,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonMediumEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedTextButton(
            text = "Button",
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
private fun PreviewWantedTextButtonSmallRightDrawableDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
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
private fun PreviewWantedTextButtonSmallTwoDrawablesDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = ButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
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
private fun PreviewWantedTextButtonMediumDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
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
