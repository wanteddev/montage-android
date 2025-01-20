package com.wanted.android.wanted.design.snackbar

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.wanted.android.wanted.design.toast.WantedToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun WantedSnackBarHost(
    hostState: SnackbarHostState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = {
            val visuals =
                it.visuals as? WantedSnackbarVisuals ?: return@SnackbarHost
            when (val type = visuals.snackbarType) {
                is SnackbarType.Snackbar -> {
                    WantedSnackBar(
                        modifier = Modifier
                            .padding(paddingValues = visuals.padding)
                            .navigationBarsPadding()
                            .imePadding(),
                        text = visuals.message,
                        snackbarData = it,
                        description = type.description,
                        buttonText = visuals.actionLabel ?: "",
                        icon = type.icon,
                        onClick = {}
                    )
                }

                is SnackbarType.Toast -> {
                    WantedToast(
                        modifier = Modifier
                            .padding(paddingValues = visuals.padding)
                            .navigationBarsPadding()
                            .imePadding(),
                        text = visuals.message,
                        variant = type.variant,
                        icon = type.icon
                    )
                }
            }
        }
    )


    ObserveAsEvents(
        flow = SnackbarController.events,
        key1 = hostState,
        key2 = coroutineScope
    ) { event ->
        coroutineScope.launch {
            hostState.currentSnackbarData?.dismiss()
            val result = hostState.showSnackbar(
                WantedSnackbarVisuals(
                    message = event.message,
                    actionLabel = event.action?.name,
                    duration = event.duration,
                    withDismissAction = event.withDismissAction,
                    snackbarType = event.snackbarType,
                    padding = event.padding
                )
            )
            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
}

@Composable
private fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}