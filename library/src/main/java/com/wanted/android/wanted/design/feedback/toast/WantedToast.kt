package com.wanted.android.wanted.design.feedback.toast

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
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.WantedToastIcon
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * WantedToast
 *
 * SnackbarHost를 기반으로 한 Wanted 커스텀 Toast 컴포넌트입니다.
 *
 * WantedToastVisuals 를 사용하면 Variant와 아이콘이 포함된 Toast로 표시되고
 * 기본 SnackbarVisuals 를 를 사용하면 메시지만 표시됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * val snackbarHostState = remember { SnackbarHostState() }
 * WantedToast(snackbarHostState = snackbarHostState)
 * ```
 *
 * @param snackbarHostState SnackbarHostState: Snackbar 상태를 관리합니다.
 * @param modifier Modifier: 외형을 조정합니다.
 * @param windowInsets WindowInsets: 시스템 인셋 대응을 위한 설정입니다.
 */
@Composable
fun WantedToast(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
) {
    SnackbarHost(
        modifier = modifier.zIndex(1000f),
        hostState = snackbarHostState
    ) {
        val visuals = it.visuals
        if (visuals is WantedToastVisuals) {
            WantedToastImpl(
                variant = visuals.variant,
                windowInsets = windowInsets,
                text = visuals.message,
                icon = visuals.icon
            )
        } else {
            WantedToastImpl(
                windowInsets = windowInsets,
                text = visuals.message,
            )
        }
    }
}

@Composable
internal fun WantedToastImpl(
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
                    backgroundResourceId = variant.backgroundResourceId,
                    backgroundColor = colorResource(id = variant.backgroundTintColor),
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
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .background(DesignSystemTheme.colors.inverseBackground.copy(0.52f))
            .background(DesignSystemTheme.colors.primaryNormal.copy(0.05f))
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
                value = DesignSystemTheme.typography.body2Bold.copy(
                    color = DesignSystemTheme.colors.staticWhite
                )
            ) {
                content()
            }
        }
    }
}


/**
 * fun SnackbarHostState.showToast(...)
 *
 * Wanted 스타일의 Toast를 표시하는 확장 함수입니다.
 *
 * WantedToastVisuals를 사용하여 표시하며,
 * Variant를 통해 메시지 타입(긍정, 주의, 부정 등)을 지정할 수 있습니다.
 * 이미 표시 중인 Toast가 있다면 자동으로 닫고 새로운 Toast를 표시합니다.
 *
 * 사용 예시:
 * ```
 * val snackbarHostState = remember { SnackbarHostState() }
 * val scope = rememberCoroutineScope()
 *
 * snackbarHostState.showToast(
 *     scope = scope,
 *     message = "저장되었습니다.",
 *     variant = WantedToastVariant.Positive
 * )
 * ```
 *
 * @param scope CoroutineScope: 코루틴을 실행할 스코프입니다.
 * @param message String: 토스트에 표시할 메시지입니다.
 * @param variant WantedToastVariant: 토스트 스타일입니다. 기본값은 Message입니다.
 */
fun SnackbarHostState.showToast(
    scope: CoroutineScope,
    message: String,
    variant: WantedToastVariant = WantedToastVariant.Message,
) {
    scope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(
            visuals = WantedToastVisuals(
                message = message,
                variant = variant
            )
        )
    }
}

@DevicePreviews
@Composable
private fun ToastNormalPreview() {
    DesignSystemTheme {
        val hostState: SnackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                WantedToast(hostState)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedButton(text = "toast") {
                    coroutineScope.launch {
                        hostState.showSnackbar(message = "메시지에 마침표를 찍어요.")
                    }
                }

                WantedButton(text = "toast Visuals") {
                    coroutineScope.launch {
                        hostState.showSnackbar(
                            visuals = WantedToastVisuals(
                                variant = WantedToastVariant.Positive,
                                message = "메시지에 마침표를 찍어요."
                            )
                        )
                    }
                }

                WantedToastImpl(
                    variant = WantedToastVariant.Message,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToastImpl(
                    variant = WantedToastVariant.Positive,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToastImpl(
                    variant = WantedToastVariant.Cautionary,
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToastImpl(
                    variant = WantedToastVariant.Negative,
                    text = "메시지에 마침표를 찍어요."
                )


                WantedToastImpl(
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.icon_normal_eye_fill),
                            modifier = Modifier.size(22.dp),
                            tint = DesignSystemTheme.colors.statusNegative
                        )
                    },
                    text = "메시지에 마침표를 찍어요."
                )

                WantedToastImpl(
                    text = "메시지에 마침표를 찍어요."
                )
            }
        }
    }
}