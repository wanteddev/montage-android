package com.wanted.android.wanted.design.feedback.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant
import kotlinx.coroutines.flow.MutableSharedFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val withDismissAction: Boolean = false,
    val snackbarType: SnackbarType = SnackbarType.Toast(variant = WantedToastVariant.Normal),
    val padding: PaddingValues = PaddingValues()
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

@Stable
class WantedSnackbarVisuals(
    override val actionLabel: String?,
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val snackbarType: SnackbarType = SnackbarType.Toast(variant = WantedToastVariant.Normal),
    val padding: PaddingValues = PaddingValues()
) : SnackbarVisuals

sealed interface SnackbarType {
    data class Toast(
        val variant: WantedToastVariant = WantedToastVariant.Normal,
        val icon: @Composable (() -> Unit)? = null
    ) : SnackbarType

    data class Snackbar(
        val description: String = "",
        val icon: @Composable (() -> Unit)? = null
    ) : SnackbarType
}

object SnackbarController {
    private val _events = MutableSharedFlow<SnackbarEvent>()
    val events = _events

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.emit(event)
    }
}