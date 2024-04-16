package com.wanted.android.wanted.design.beta.bottomsheet

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.WantedSolidButton
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    backgroundColor: Color = colorResource(id = R.color.background_normal_normal),
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
    onDismissed: (() -> Unit)? = null
) {
    val visibleState: MutableTransitionState<Boolean> = remember { MutableTransitionState(false) }
    val dialogVisibility: MutableState<Boolean> = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            dialogVisibility.value = true
            visibleState.targetState = true
        } else if (visibleState.currentState) {
            visibleState.targetState = false
            launch {
                delay(200)
                dialogVisibility.value = false
                onDismissed?.invoke()
            }
        }
    }

    LaunchedEffect(key1 = visibleState.currentState) {
        if (!isVisible) {
            visibleState.targetState = false
            dialogVisibility.value = false
        }
    }


    if (dialogVisibility.value) {
        Layout(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .fillMaxSize()
                .background(colorResource(id = R.color.material_dimmer)),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickOnceForDesignSystem(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onDismissRequest() },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AnimatedVisibility(
                        visibleState = visibleState,
                        enter = slideInVertically() + expandVertically(
                            expandFrom = Alignment.Top
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ),
                        exit = slideOutVertically { 0 } + shrinkVertically(
                            shrinkTowards = Alignment.Top
                        ) + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                .background(backgroundColor)
                                .then(modifier)
                                .clickable(false) { }
                        ) {
                            content()
                        }

                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                val placeables = measurables.fastMap { it.measure(constraints) }
                val width = placeables.fastMaxBy { it.width }?.width ?: constraints.minWidth
                val height =
                    (placeables.fastMaxBy { it.height }?.height ?: constraints.minHeight)
                layout(width, height) {
                    placeables.fastForEach { it.placeRelative(0, 0) }
                }
            }
        )
    }
}

@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    backgroundColor: Color = colorResource(id = R.color.background_normal_normal),
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    confirm: String? = null,
    isEnableConfirm: Boolean = false,
    onClickConfirm: (() -> Unit)? = null,
    onDismissRequest: () -> Unit,
    onDismissed: (() -> Unit)? = null
) {
    val visibleState: MutableTransitionState<Boolean> = remember { MutableTransitionState(false) }
    val dialogVisibility: MutableState<Boolean> = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            dialogVisibility.value = true
            visibleState.targetState = true
        } else if (visibleState.currentState) {
            visibleState.targetState = false
            launch {
                delay(200)
                dialogVisibility.value = false
                onDismissed?.invoke()
            }
        }
    }

    LaunchedEffect(key1 = visibleState.currentState) {
        if (!isVisible) {
            visibleState.targetState = false
            dialogVisibility.value = false
        }
    }

    if (dialogVisibility.value) {
        Layout(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .fillMaxSize()
                .background(colorResource(id = R.color.material_dimmer)),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clickOnceForDesignSystem(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onDismissRequest() },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AnimatedVisibility(
                        visibleState = visibleState,
                        enter = slideInVertically() + expandVertically(
                            expandFrom = Alignment.Top
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ),
                        exit = slideOutVertically { 0 } + shrinkVertically(
                            shrinkTowards = Alignment.Top
                        ) + fadeOut()
                    ) {
                        CustomDialogContentImpl(
                            modifier = modifier,
                            backgroundColor = backgroundColor,
                            title = title,
                            content = content,
                            confirm = confirm,
                            isEnableConfirm = isEnableConfirm,
                            onClickConfirm = onClickConfirm,
                            onDismissRequest = {
                                onDismissRequest()
                            }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                val placeables = measurables.fastMap { it.measure(constraints) }
                val width = placeables.fastMaxBy { it.width }?.width ?: constraints.minWidth
                val height =
                    (placeables.fastMaxBy { it.height }?.height ?: constraints.minHeight)
                layout(width, height) {
                    placeables.fastForEach { it.placeRelative(0, 0) }
                }
            }
        )
    }
}

@Composable
private fun CustomDialogContentImpl(
    modifier: Modifier,
    backgroundColor: Color,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    confirm: String? = null,
    isEnableConfirm: Boolean = false,
    onClickConfirm: (() -> Unit)? = null,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(backgroundColor)
            .then(modifier)
            .clickable(false) { }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 74.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                ProvideTextStyle(
                    value = WantedTextStyle(
                        colorRes = R.color.label_strong,
                        style = DesignSystemTheme.typography.heading1Bold
                    )
                ) {
                    title()
                }
            }

            Icon(
                modifier = Modifier
                    .padding(2.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickOnceForDesignSystem { onDismissRequest() }
                    .padding(18.dp),
                painter = painterResource(id = R.drawable.ic_normal_close_svg),
                contentDescription = "close button",
                tint = colorResource(id = R.color.label_normal)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            content()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = android.R.color.transparent),
                                backgroundColor
                            )
                        )
                    )
            )
        }

        confirm?.let {
            WantedSolidButton(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_20))
                    .fillMaxWidth(),
                text = confirm,
                status = if (isEnableConfirm) ButtonStatus.ENABLE else ButtonStatus.DISABLE,
                clickListener = {
                    onClickConfirm?.invoke()
                }
            )
        }

    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun OnBoardingCareerScreenPreview() {
    DesignSystemTheme {
        Surface {
            CustomBottomSheet(
                modifier = Modifier.height(480.dp),
                isVisible = true,
                title = { Text(text = "title") },
                content = {

                },
                onDismissRequest = {}
            )
        }
    }
}