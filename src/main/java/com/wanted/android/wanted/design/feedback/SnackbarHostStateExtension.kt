package com.wanted.android.wanted.design.feedback

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.show(
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar("")
    }
}