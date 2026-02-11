package com.wanted.android.wanted.design.presentation.modal.bottomsheet

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.presentation.modal.view.WantedDialogLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Deprecated("Use WantedModalBottomSheet")
@Composable
fun WantedBottomSheetDialog(
    isVisible: Boolean,
    modalSize: ModalSize = ModalSize.Medium,
    durationMillis: Long = 200,
    onDismissRequest: () -> Unit = {},
    topBar: @Composable () -> Unit,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
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
                delay(durationMillis)
                dialogVisibility.value = false
                onDismissRequest()
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
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false,
            ),
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            SetDialogDestinationToEdgeToEdge()

            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically() + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                WantedBottomSheetLayout(
                    modalSize = modalSize,
                    topBar = topBar,
                    bottomBar = bottomBar,
                    content = content
                )
            }
        }
    }
}

@Deprecated("Use WantedModalBottomSheet")
@Composable
fun WantedBottomSheetLayout(
    modalSize: ModalSize,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
    shadowElevation: Dp = 4.dp,
    topBar: @Composable (() -> Unit)?,
    bottomBar: @Composable (() -> Unit)?,
    content: @Composable () -> Unit
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        shadowElevation = shadowElevation,
        color = DesignSystemTheme.colors.backgroundNormalNormal
    ) {
        Column(Modifier.fillMaxWidth()) {
            WantedDialogLayout(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = backgroundColor,
                modalSize = modalSize,
                topBar = topBar,
                content = {
                    Box(modifier = Modifier.padding(horizontal = modalSize.contentPadding)) {
                        content()
                    }
                },
                bottomBar = bottomBar
            )

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                if (WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() > 0.dp) {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                } else {
                    Spacer(Modifier.size(48.dp))
                }
            } else {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
            }
        }
    }
}

@Composable
fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

private tailrec fun Context.getActivityWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.getActivityWindow()
        else -> null
    }

@Composable
fun SetDialogDestinationToEdgeToEdge(
    gravity: Int = Gravity.BOTTOM,
    dimAmount: Float = 0.52f
) {
    val activityWindow = getActivityWindow()
    val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
    val parentView = LocalView.current.parent as View
    SideEffect {
        if (activityWindow != null && dialogWindow != null) {
            val attributes = WindowManager.LayoutParams()
            attributes.copyFrom(activityWindow.attributes)
            attributes.dimAmount = dimAmount
            attributes.type = dialogWindow.attributes.type
            attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialogWindow.attributes = attributes
            dialogWindow.setDimAmount(dimAmount)
            dialogWindow.setGravity(gravity)
            parentView.layoutParams = FrameLayout.LayoutParams(
                activityWindow.decorView.width,
                activityWindow.decorView.height
            )
        }
    }
}
