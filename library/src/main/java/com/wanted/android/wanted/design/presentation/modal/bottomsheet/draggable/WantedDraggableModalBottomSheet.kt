package com.wanted.android.wanted.design.presentation.modal.bottomsheet.draggable

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedBottomSheetDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.noRippleClickable
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
internal fun WantedDraggableModalBottomSheet(
    isShow: Boolean,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = remember {
        DialogProperties(
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = false
        )
    },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    contentColor: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    dragHandle: @Composable (() -> Unit)? = { WantedBottomSheetDefaults.DragHandle() },
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    var layoutHeight by remember { mutableFloatStateOf(0f) }

    val currentDismissRequest by rememberUpdatedState(onDismissRequest)

    val dragState = remember(layoutHeight) {
        AnchoredDraggableState(
            initialValue = if (layoutHeight > 0) {
                SheetValue.Expanded
            } else {
                SheetValue.Hidden
            },
            anchors = DraggableAnchors {
                SheetValue.Hidden at layoutHeight
                SheetValue.Expanded at 0f
            },
            positionalThreshold = { d: Float -> d * 0.01f },
            velocityThreshold = { 0f },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    var isDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isShow) {
        if (isShow && dragState.currentValue == SheetValue.Hidden) {
            coroutineScope.launch {
                isDialogVisible = true
                dragState.animateTo(SheetValue.Expanded)
            }
        } else if (!isShow && dragState.currentValue == SheetValue.Expanded) {
            coroutineScope.launch {
                dragState.animateTo(SheetValue.Hidden)
                currentDismissRequest()
                isDialogVisible = false
            }
        }
    }

    LaunchedEffect(dragState.isAnimationRunning, isDialogVisible, dragState.currentValue, isShow) {
        if (dragState.isAnimationRunning) return@LaunchedEffect
        if (isDialogVisible && dragState.currentValue == SheetValue.Hidden) {
            if (isShow) {
                currentDismissRequest()
            }
            isDialogVisible = false
        }
    }

    if (isDialogVisible) {
        Dialog(
            properties = properties,
            onDismissRequest = {
                currentDismissRequest()
            }
        ) {
            SetUpEdgeToEdgeDialog(dimAmount = 0.5f)

            Box(
                modifier = Modifier
                    .windowInsetsPadding(WantedTopAppBarDefaults.windowInsets)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            if (properties.dismissOnClickOutside) {
                                coroutineScope.launch {
                                    dragState.animateTo(SheetValue.Hidden)
                                }
                            }
                        }
                )

                Box(modifier = Modifier) {
                    Column(
                        modifier = Modifier
                            .noRippleClickable {}
                            .fillMaxWidth()
                            .imePadding()
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = dragState
                                        .requireOffset()
                                        .roundToInt()
                                )
                            }
                            .clip(shape)
                            .background(contentColor)
                            .onGloballyPositioned {
                                layoutHeight = it.size.height.toFloat()
                            }
                            .windowInsetsPadding(contentWindowInsets())
                    ) {
                        dragHandle?.let {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .anchoredDraggable(
                                        state = dragState,
                                        orientation = Orientation.Vertical,
                                        enabled = true
                                    ),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                it()

                            }

                        }

                        content()
                    }

                    dragHandle?.let {
                        WantedHandleTouchArea(
                            modifier = Modifier
                                .height(58.dp)
                                .width(116.dp)
                                .align(Alignment.TopCenter)
                                .offset {
                                    IntOffset(
                                        x = 0,
                                        y = dragState
                                            .requireOffset()
                                            .roundToInt()
                                    )
                                },
                            draggableState = dragState

                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WantedHandleTouchArea(
    draggableState: AnchoredDraggableState<SheetValue>,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .anchoredDraggable(
                        state = draggableState,
                        orientation = Orientation.Vertical,
                        enabled = true
                    )
            )
        }
    ) { measurables, constraints ->
        val textPlaceable = measurables[0].measure(constraints)

        // Calculate the expanded dimensions
        val expandedHeight = textPlaceable.height

        layout(textPlaceable.width, expandedHeight) {
            textPlaceable.placeRelative(
                x = 0,
                y = -(textPlaceable.height / 2)
            )
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
    //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

    window.setSoftInputMode(
        if (Build.VERSION.SDK_INT >= 30) {
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        },
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.attributes.fitInsetsTypes = 0
        window.attributes.fitInsetsSides = 0
    }
}