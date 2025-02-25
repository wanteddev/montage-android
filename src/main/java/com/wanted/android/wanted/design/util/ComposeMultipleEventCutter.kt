package com.wanted.android.wanted.design.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role


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

