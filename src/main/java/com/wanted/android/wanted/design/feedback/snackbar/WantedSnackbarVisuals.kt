package com.wanted.android.wanted.design.feedback.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant

@Stable
class WantedSnackbarVisuals(
    override val actionLabel: String? = null,
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val description: String = "",
    val padding: PaddingValues = PaddingValues(),
    val icon: @Composable (() -> Unit)? = null
) : SnackbarVisuals

sealed interface SnackbarType {
    val icon: @Composable (() -> Unit)?
//    data class Toast(
//        val variant: WantedToastVariant = WantedToastVariant.Message,
//        override val icon: @Composable (() -> Unit)? = null
//    ) : SnackbarType

    data class Snackbar(
        val description: String = "",
        val buttonText: String = "",
        override val icon: @Composable (() -> Unit)? = null
    ) : SnackbarType
}

object WantedSnackBarHostDefaults  {
    val windowInsets: WindowInsets
        @Composable
        get() {
            return WindowInsets.safeDrawing
                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
        }
}
