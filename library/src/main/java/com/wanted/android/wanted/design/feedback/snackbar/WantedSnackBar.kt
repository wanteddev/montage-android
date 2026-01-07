package com.wanted.android.wanted.design.feedback.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * WantedSnackBar
 *
 * 앱 전역에서 사용할 수 있는 커스텀 Snackbar 호스트입니다.
 *
 * WantedSnackbarVisuals 사용 시 아이콘과 설명이 포함된 커스텀 레이아웃으로 표시되며,
 * 기본 SnackbarVisuals를 사용 시 표준 형태로 표시됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * val snackbarHostState = remember { SnackbarHostState() }
 * WantedSnackBar(snackbarHostState = snackbarHostState)
 * ```
 *
 * @param snackbarHostState SnackbarHostState 스낵바 표시 상태를 제어합니다.
 * @param modifier Modifier 스낵바 전체 레이아웃 조정 Modifier입니다.
 */
@Composable
fun WantedSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = Modifier.zIndex(1000f),
        hostState = snackbarHostState
    ) { data ->
        val visuals = data.visuals
        if (visuals is WantedSnackbarVisuals) {
            WantedSnackBarImpl(
                modifier = modifier,
                text = visuals.message,
                description = visuals.description,
                buttonText = visuals.actionLabel.orEmpty(),
                icon = visuals.icon,
                onClick = { data.performAction() }
            )
        } else {
            WantedSnackBarImpl(
                modifier = modifier,
                text = data.visuals.message,
                buttonText = visuals.actionLabel.orEmpty(),
                icon = null,
                onClick = { data.performAction() }
            )
        }
    }
}

@Composable
private fun WantedSnackBarImpl(
    text: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    description: String = "",
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    WantedSnackBarLayout(
        modifier = modifier,
        icon = icon,
        headingSlot = {
            if (text.isNotEmpty()) {
                Text(
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        descriptionSlot = {
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        },
        buttonTextSlot = {
            if (buttonText.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 54.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickOnce {
                            onClick()
                        }
                        .padding(vertical = 4.dp, horizontal = 7.dp),

                    text = buttonText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}


@Composable
private fun WantedSnackBarLayout(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    headingSlot: @Composable (() -> Unit) = {},
    descriptionSlot: @Composable (() -> Unit) = {},
    buttonTextSlot: @Composable (() -> Unit) = {},
) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .wrapContentHeight()
            .widthIn(max = 420.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .background(DesignSystemTheme.colors.inverseBackground.copy(0.52f))
            .background(DesignSystemTheme.colors.primaryNormal.copy(0.05f))
            .padding(start = 16.dp, end = 11.dp)
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 2.dp, vertical = 5.dp),
        ) {
            ProvideTextStyle(
                value = DesignSystemTheme.typography.body2Bold.copy(
                    color = DesignSystemTheme.colors.staticWhite
                )
            ) {
                headingSlot()
            }

            ProvideTextStyle(
                value = DesignSystemTheme.typography.label2Regular.copy(
                    color = DesignSystemTheme.colors.staticWhite
                )
            ) {
                descriptionSlot()
            }
        }

        Spacer(modifier = Modifier.size(7.dp))

        ProvideTextStyle(
            value = DesignSystemTheme.typography.body2Bold.copy(
                color = DesignSystemTheme.colors.staticWhite
            )
        ) {
            buttonTextSlot()
        }
    }
}


/**
 * fun SnackbarHostState.showSnackbar(...)
 *
 * Wanted 스타일의 Snackbar를 표시하는 확장 함수입니다.
 *
 * WantedSnackbarVisuals를 사용하여 표시하며,
 * 이미 표시 중인 Snackbar가 있다면 자동으로 닫고 새로운 Snackbar를 표시합니다.
 *
 * 사용 예시:
 * ```
 * val snackbarHostState = remember { SnackbarHostState() }
 * val scope = rememberCoroutineScope()
 *
 * snackbarHostState.showSnackbar(
 *     scope = scope,
 *     message = "저장되었습니다."
 * )
 * ```
 *
 * @param scope CoroutineScope 코루틴을 실행할 스코프입니다.
 * @param message String 스낵바에 표시할 메시지입니다.
 */
fun SnackbarHostState.showSnackbar(
    scope: CoroutineScope,
    message: String
) {
    scope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(
            visuals = WantedSnackbarVisuals(message = message)
        )
    }
}


@DevicePreviews
@Composable
private fun WantedSnackBarDescriptionExtraContentPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedSnackBarImpl(
                    text = "메시지에 마침표를 찍어요.",
                    description = "설명은 필요할 때만 써요.",
                    buttonText = "텍스트",
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.icon_normal_eye_fill),
                            modifier = Modifier
                                .size(32.dp),
                            tint = DesignSystemTheme.colors.statusNegative
                        )
                    },
                    onClick = {}
                )

                WantedSnackBarImpl(
                    text = "",
                    description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
                    buttonText = "텍스트",
                    icon = {
                        Icon(
                            contentDescription = "icon",
                            painter = painterResource(id = R.drawable.icon_normal_eye_fill),
                            modifier = Modifier.fillMaxSize(),
                            tint = DesignSystemTheme.colors.statusNegative
                        )
                    },
                    onClick = {}
                )

                WantedSnackBarImpl(
                    text = "",
                    description = "설명은 필요할 때만 써요, 설명은 필요할 때만 써요, 설명은 필요할 때만 써요",
                    buttonText = "텍스트",
                    onClick = {}
                )

                WantedSnackBarImpl(
                    text = "메시지에 마침표를 찍어요.",
                    buttonText = "텍스트",
                    onClick = {}
                )
            }
        }
    }
}