package com.wanted.android.wanted.design.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp

@Composable
@Stable
internal fun Modifier.clickOnce(
    verticalPadding: Dp,
    horizontalPadding: Dp,
    enabled: Boolean = true,
    rippleColor: Color = Color.Unspecified,
    isUseRipple: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)
): Modifier = this
    .clickOnce(
        interactionSource = interactionSource,
        indication = if (isUseRipple) ripple(bounded = true, color = rippleColor) else null,
        enabled = enabled,
    ) { onClick() }
    .then(MinimumInteractiveModifier(verticalPadding, horizontalPadding))


internal fun Modifier.clickOnce(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = { onClick.clickOnce() }
)

internal fun Modifier.clickOnce(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.clickable(
    interactionSource = interactionSource,
    indication = indication,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = { onClick.clickOnce() }
)

internal fun (() -> Unit).clickOnce(time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this() }
}

internal fun <T> ((T) -> Unit).clickOnce(value: T, time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this(value) }
}

internal fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

internal object ComposeMultipleEventCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    fun processEvent(time: Long, event: () -> Unit) {
        if (now - lastEventTimeMs >= time) {
            event.invoke()
        }

        lastEventTimeMs = now
    }
}

