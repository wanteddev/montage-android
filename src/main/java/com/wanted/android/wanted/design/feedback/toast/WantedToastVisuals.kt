package com.wanted.android.wanted.design.feedback.toast

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
class WantedToastVisuals(
    override val message: String,
    override val actionLabel: String = "",
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val variant: WantedToastVariant = WantedToastVariant.Message,
    val icon: @Composable (() -> Unit)? = null
) : SnackbarVisuals
