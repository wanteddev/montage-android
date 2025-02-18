package com.wanted.android.wanted.design.draggable

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class DragValue { Close, Open }

@Composable
fun DraggableModal(
    modifier: Modifier = Modifier,
    isShow: Boolean = false,
    dimAmount: Float = 0.5f,
    windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets,
    properties: DialogProperties = remember {
        DialogProperties(
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = false,
        )
    },
    positionalThreshold: (Float) -> Float = remember { { d: Float -> d * 0.01f } },
    onDismissRequest: () -> Unit = {},
    dragHandle: @Composable () -> Unit = { WantedDraggableModalDefaults.DragHandle() },
    content: @Composable (Dp) -> Unit = {},
) {
    val density = LocalDensity.current
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val coroutineScope = rememberCoroutineScope()
    var layoutHeight by remember { mutableStateOf(0.dp) }
    var dragSize by remember { mutableStateOf(IntSize.Zero) }
    val currentDismissRequest by rememberUpdatedState(onDismissRequest)
    val dragState = remember(layoutHeight, positionalThreshold, density) {
        AnchoredDraggableState(
            initialValue = DragValue.Close,
            anchors = DraggableAnchors {
                DragValue.Close at with(density) { layoutHeight.toPx() }
                DragValue.Open at 0f
            },
            positionalThreshold = positionalThreshold,
            velocityThreshold = { 0f },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    LaunchedEffect(isShow, dragState) {
        val nextValue = when (isShow) {
            true -> DragValue.Open
            false -> DragValue.Close
        }

        dragState.animateTo(nextValue)
    }

    if (isShow)
        Dialog(
            properties = properties,
            onDismissRequest = {
                coroutineScope.launch {
                    dragState.animateTo(DragValue.Close)
                }
            }
        ) {
            LaunchedEffect(
                dragState.isAnimationRunning,
                dragState.currentValue,
                dragState.settledValue
            ) {
                if (dragState.isAnimationRunning) return@LaunchedEffect
                if (dragState.currentValue == DragValue.Close && dragState.settledValue == DragValue.Close) {
                    currentDismissRequest()
                }
            }

            SetUpEdgeToEdgeDialog(
                dimAmount = dimAmount
            )
            Box(
                modifier
                    .windowInsetsPadding(windowInsets)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (!dragState.offset.isNaN()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    if (properties.dismissOnClickOutside) {
                                        coroutineScope.launch {
                                            dragState.animateTo(DragValue.Close)
                                        }
                                    }
                                }
                                .weight(1f)
                        )

                        BoxWithConstraints(
                            modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset {
                                        IntOffset(
                                            0,
                                            dragState
                                                .requireOffset()
                                                .roundToInt()
                                        )
                                    }
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .onGloballyPositioned {
                                            dragSize = it.size
                                        }
                                        .anchoredDraggable(
                                            state = dragState,
                                            orientation = Orientation.Vertical,
                                            enabled = true
                                        )
                                ) {
                                    dragHandle()
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    content(this@BoxWithConstraints.maxHeight - with(density) { dragSize.height.toDp() })
                                }
                            }
                            LaunchedEffect(layoutHeight) {
                                layoutHeight = maxHeight
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun SetUpEdgeToEdgeDialog(
    dimAmount: Float = 0.5f
) {
    val parentView = LocalView.current.parent as View
    val window = (parentView as DialogWindowProvider).window

    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    window.statusBarColor = android.graphics.Color.TRANSPARENT
    window.navigationBarColor = android.graphics.Color.TRANSPARENT
    if (Build.VERSION.SDK_INT >= 29) {
        window.isNavigationBarContrastEnforced = false
    }
    window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    window.setDimAmount(dimAmount)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.attributes.fitInsetsTypes = 0
        window.attributes.fitInsetsSides = 0
    }
}

@Preview
@Composable
fun DraggableModalPreview() {
    var isShow by remember {
        mutableStateOf(false)
    }
    DesignSystemTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    isShow = true
                }
            ) {
                Text("Open")
            }
        }
        DraggableModal(
            modifier = Modifier,
            isShow = isShow,
            onDismissRequest = {
                isShow = false
            }
        ) {
            Column(
                modifier = Modifier
                    .height((it.value * 0.95).dp)
                    .background(colorResource(R.color.background_normal_normal))
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(50) {
                    Box(
                        Modifier
                            .background(Color(it * 20, it * 20, it * 20))
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        }
    }
}

object WantedDraggableModalDefaults {
    @Composable
    fun DragHandle(
        modifier: Modifier = Modifier,
        color: Color = colorResource(id = R.color.background_elevated_normal),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color = color)
                .padding(top = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(width = 40.dp, height = 5.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .background(
                        colorResource(id = R.color.fill_strong),
                        RoundedCornerShape(1000.dp)
                    )
            )
        }
    }
}