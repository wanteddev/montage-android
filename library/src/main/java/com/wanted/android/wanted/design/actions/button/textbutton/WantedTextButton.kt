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
import com.wanted.android.wanted.design.actions.button.view.WantedButtonSideIcon
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.loading.loading.WantedCircularProgressIndicator
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
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
            color = buttonType.toWantedTextButtonColor(),
            size = size.toWantedTextButtonSize(),
            enabled = buttonStatus,
            leadingDrawable = if (leftDrawable != 0) leftDrawable else null,
            trailingDrawable = if (rightDrawable != 0) rightDrawable else null,
            onClick = onClickListener
        )
    }
}


/**
 * WantedTextButton
 *
 * Text 형태의 버튼을 생성하는 Compose 함수입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTextButton(
 *     text = "확인",
 *     color = WantedTextButtonColor.PRIMARY,
 *     size = WantedTextButtonSize.MEDIUM,
 *     onClick = { /* 클릭 이벤트 처리 */ }
 * )
 * ```
 *
 * @param text String: 버튼에 표시할 텍스트입니다.
 * @param modifier Modifier: 버튼 외형을 조정하는 Modifier입니다.
 * @param color WantedTextButtonColor: 버튼의 색(PRIMARY, ASSISTIVE)을 지정합니다.
 * @param size WantedTextButtonSize: 버튼의 크기(SMALL, MEDIUM)를 지정합니다. LARGE/XSMALL은 공식 미지원(레거시)입니다.
 * @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
 * @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
 * @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 * @param buttonDefault WantedTextButtonDefault: 버튼의 기본 스타일 설정입니다. color/size 축으로 표현되지 않는 색·타이포가 필요할 때만 contentColor/textStyle을 덮어 전달합니다.
 */
@Composable
fun WantedTextButton(
    text: String,
    modifier: Modifier = Modifier,
    color: WantedTextButtonColor = WantedTextButtonColor.PRIMARY,
    size: WantedTextButtonSize = WantedTextButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {},
    buttonDefault: WantedTextButtonDefault = WantedTextButtonDefaults.getDefault(
        color = color,
        size = size,
        enabled = enabled
    )
) {
    WantedTouchArea(
        modifier = modifier,
        verticalPadding = 0.dp,
        horizontalPadding = getTextButtonTouchAreaHorizontalPadding(buttonDefault.size),
        shape = getTextButtonTouchAreaShape(buttonDefault.size),
        enabled = enabled,
        rippleColor = buttonDefault.rippleColor,
        content = {
            WantedTextContent(
                text = text,
                modifier = Modifier,
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
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    buttonDefault: WantedTextButtonDefault = WantedTextButtonDefaults.getDefault()
) {
    val textColor = remember(buttonDefault.enabled, buttonDefault.contentColor) {
        mutableStateOf(buttonDefault.contentColor)
    }

    val trailingIconTintColor = remember(buttonDefault.enabled, buttonDefault.trailingIconTintColor) {
        mutableStateOf(buttonDefault.trailingIconTintColor)
    }

    val leadingIconTintColor = remember(buttonDefault.enabled, buttonDefault.leadingIconTintColor) {
        mutableStateOf(buttonDefault.leadingIconTintColor)
    }

    WantedTextButtonLayout(
        modifier = modifier
            .textButtonHeight(buttonDefault.size)
            .textButtonWidth(buttonDefault.size, text.isEmpty())
            .textButtonVerticalPadding(buttonDefault.size),
        horizontalArrangement = Arrangement.spacedBy(
            space = getTextButtonContentSpacing(buttonDefault.size),
            alignment = Alignment.CenterHorizontally
        ),
        leftDrawable = leadingDrawable?.let {
            {
                WantedButtonSideIcon(
                    modifier = Modifier
                        .textButtonDrawableSize(buttonDefault.size)
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = leadingIconTintColor.value
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
                        .textButtonDrawableSize(buttonDefault.size)
                        .alpha(if (isLoading) 0f else 1f),
                    drawableRes = it,
                    tint = trailingIconTintColor.value
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary),
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
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        )


        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            color = WantedTextButtonColor.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallNoDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )


        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            color = WantedTextButtonColor.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = WantedTextButtonSize.SMALL,
            color = WantedTextButtonColor.ASSISTIVE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallLeftDrawableEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = WantedTextButtonSize.MEDIUM,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = WantedTextButtonSize.LARGE,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonLargeMaxWidthEnable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )

        WantedTextButton(
            text = "Button",
            isLoading = true,
            size = WantedTextButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewWantedTextButtonSmallNoDrawableDisable() {
    Column(
        modifier = Modifier
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
            enabled = false,
            modifier = Modifier.wrapContentSize(),
            leadingDrawable = R.drawable.icon_normal_bookmark,
            trailingDrawable = R.drawable.icon_normal_heart
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.SMALL,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.MEDIUM,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.MEDIUM,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.wrapContentSize()
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
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
            .background(DesignSystemTheme.colors.backgroundNeutralPrimary)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        WantedTextButton(
            text = "Button",
            size = WantedTextButtonSize.LARGE,
            enabled = false,
            isLoading = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
