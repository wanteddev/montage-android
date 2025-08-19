package com.wanted.android.wanted.design.feedback

import androidx.compose.material3.SnackbarHostState
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant
import com.wanted.android.wanted.design.feedback.toast.WantedToastVisuals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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