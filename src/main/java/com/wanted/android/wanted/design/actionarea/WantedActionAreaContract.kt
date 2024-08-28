package com.wanted.android.wanted.design.actionarea

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actionarea.WantedActionAreaContract.ActionAreaType


val LocalWantedActionAreaType = WantedActionAreaTypeCompositionLocal()

object WantedActionAreaContract {
    enum class ActionAreaType {
        Strong,
        Neutral,
        Compact,
        Cancel
    }
}

@JvmInline
value class WantedActionAreaTypeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<ActionAreaType> = staticCompositionLocalOf { ActionAreaType.Strong }
) {
    val current: ActionAreaType
        @Composable get() = delegate.current

    infix fun provides(value: ActionAreaType) = delegate provides value
}
