package com.wanted.android.wanted.design.feedback.toast

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.feedback.WantedToastIcon
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 *
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-42415&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1596-18812&m=dev
 **/
sealed class WantedToastVariant(
    @DrawableRes val resourceId: Int,
    @ColorRes val tinColor: Int
) {
    data object Message : WantedToastVariant(-1, -1)
    data object Positive : WantedToastVariant(
        R.drawable.ic_normal_circle_check_fill_svg,
        R.color.green_60
    )

    data object Cautionary : WantedToastVariant(
        R.drawable.ic_normal_circle_exclamation_fill_svg,
        R.color.orange_60
    )

    data object Negative : WantedToastVariant(
        R.drawable.ic_normal_circle_exclamation_fill_svg,
        R.color.red_60
    )
}

@Composable
fun WantedSnackBar(
    @StringRes text: Int,
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    variant: WantedToastVariant = WantedToastVariant.Message,
    @DrawableRes icon: Int? = null,
    @ColorRes tintColor: Int? = null
) {
    SnackbarHost(
        modifier = modifier.zIndex(1000f),
        hostState = hostState
    ) {
        WantedToast(
            modifier = Modifier,
            variant = variant,
            windowInsets = windowInsets,
            text = text,
            icon = icon,
            tintColor = tintColor
        )
    }
}

@Composable
fun WantedSnackBar(
    text: String,
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    variant: WantedToastVariant = WantedToastVariant.Message,
    icon: @Composable (() -> Unit)? = null
) {
    SnackbarHost(
        modifier = modifier.zIndex(1000f),
        hostState = hostState
    ) {
        WantedToast(
            modifier = Modifier,
            variant = variant,
            windowInsets = windowInsets,
            text = text,
            icon = icon
        )
    }
}

@Deprecated(
    """
    WantedSnackBar(
        @StringRes text: Int,
        hostState: SnackbarHostState,
        modifier: Modifier = Modifier,
        windowInsets: WindowInsets = WindowInsets(0),
        variant: WantedToastVariant = WantedToastVariant.Normal,
           @DrawableRes icon: Int? = null,
        @ColorRes tintColor: Int? = null
    )       
    
    hostState.show(coroutineScope)
    """
)
@Composable
fun WantedToast(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    variant: WantedToastVariant = WantedToastVariant.Message,
    @DrawableRes icon: Int? = null,
    @ColorRes tintColor: Int? = null
) {
    WantedToast(
        modifier = modifier,
        variant = variant,
        windowInsets = windowInsets,
        text = stringResource(id = text),
        icon = icon?.let {
            {
                WantedToastIcon(
                    resourceId = icon,
                    modifier = Modifier.fillMaxSize(),
                    tint = tintColor?.let {
                        colorResource(id = tintColor)
                    } ?: LocalContentColor.current
                )
            }
        }
    )
}

@Deprecated(
    """
    WantedSnackBar(
        text: String,
        hostState: SnackbarHostState,
        modifier: Modifier = Modifier,
        windowInsets: WindowInsets = WindowInsets(0),
        variant: WantedToastVariant = WantedToastVariant.Normal,
        icon: @Composable (() -> Unit)? = null
    )       
    
    hostState.show(coroutineScope)
    """
)
@Composable
fun WantedToast(
    text: String,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    variant: WantedToastVariant = WantedToastVariant.Message,
    icon: @Composable (() -> Unit)? = null
) {
    val iconSlot: @Composable (() -> Unit)? = when (variant) {
        WantedToastVariant.Message -> {
            icon?.let {
                {
                    icon()
                }
            }
        }

        else -> {
            {
                WantedToastIcon(
                    modifier = Modifier.fillMaxSize(),
                    resourceId = variant.resourceId,
                    tint = colorResource(variant.tinColor)
                )
            }
        }
    }

    WantedToastLayout(
        modifier = modifier,
        icon = iconSlot,
        windowInsets = windowInsets,
        content = {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    )
}

@Composable
private fun WantedToastLayout(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    icon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .windowInsetsPadding(windowInsets)
            .wrapContentHeight()
            .widthIn(max = 420.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(0.52f))
            .background(colorResource(id = R.color.primary_normal).copy(0.05f))
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon?.let {
            Box(modifier = Modifier.size(22.dp)) {
                icon()
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 5.dp)
                .weight(1f)
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.static_white,
                    style = DesignSystemTheme.typography.body2Bold
                )
            ) {
                content()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ToastNormalPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WantedToast(
                    variant = WantedToastVariant.Message,
                    text = "메시지에 마침표를 찍어요.메시지에 마침표를 찍어요.메시지에 마침표를 찍어요.메시지에 마침표를 찍어요.메시지에 마침표를 찍어요.메시지에 마침표를 찍어요.메시지에 마침표를 찍어요."
                )

                WantedToast(
                    variant = WantedToastVariant.Message,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToast(
                    variant = WantedToastVariant.Positive,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToast(
                    variant = WantedToastVariant.Cautionary,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToast(
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.ic_normal_eye_fill_svg),
                            modifier = Modifier.size(22.dp),
                            tint = colorResource(id = R.color.design_default_color_error)
                        )
                    },
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToast(
                    text = "메시지에 마침표를 찍어요."
                )
            }
        }
    }
}