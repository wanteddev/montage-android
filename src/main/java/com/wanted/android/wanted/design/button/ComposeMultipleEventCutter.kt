package com.wanted.android.wanted.design.button

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role


fun Modifier.clickOnceForDesignSystem(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = { onClick.clickOnceForDesignSystem() }
)

fun Modifier.clickOnceForDesignSystem(
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
    onClick = { onClick.clickOnceForDesignSystem() }
)

fun (() -> Unit).clickOnceForDesignSystem(time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this() }
}

fun <T> ((T) -> Unit).clickOnceForDesignSystem(value: T, time: Long = 200L) {
    ComposeMultipleEventCutter.processEvent(time) { this(value) }
}

object ComposeMultipleEventCutter {
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

