package com.wanted.android.wanted.design.actionarea

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.wanted.android.wanted.design.actionarea.WantedActionAreaContract.ActionAreaType
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType

object WantedActionAreaContract {
    enum class ActionAreaType {
        Strong,
        Neutral,
        Compact,
        Single
    }
}


val LocalWantedActionAreaButtonStatus = WantedActionAreaButtonTypeCompositionLocal()

interface WantedActionAreaButtonTypeLoader {
    fun getPositiveButtonShape(type: ActionAreaType): ButtonShape
    fun getPositiveButtonType(type: ActionAreaType): ButtonType
    fun getPositiveButtonSize(type: ActionAreaType): ButtonSize

    fun getNegativeButtonShape(type: ActionAreaType): ButtonShape
    fun getNegativeButtonType(type: ActionAreaType): ButtonType
    fun getNegativeButtonSize(type: ActionAreaType): ButtonSize

    fun getNeutralButtonShape(type: ActionAreaType): ButtonShape
    fun getNeutralButtonType(type: ActionAreaType): ButtonType
    fun getNeutralButtonSize(type: ActionAreaType): ButtonSize
}

internal class WantedActionAreaButtonTypeLoaderImpl : WantedActionAreaButtonTypeLoader {
    override fun getPositiveButtonShape(type: ActionAreaType): ButtonShape {
        return when (type) {
            ActionAreaType.Single -> ButtonShape.OUTLINED
            else -> ButtonShape.SOLID
        }
    }

    override fun getPositiveButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Single -> ButtonType.ASSISTIVE
            else -> ButtonType.PRIMARY
        }
    }

    override fun getPositiveButtonSize(type: ActionAreaType): ButtonSize {
        return ButtonSize.LARGE
    }

    override fun getNegativeButtonShape(type: ActionAreaType): ButtonShape {
        return ButtonShape.OUTLINED
    }

    override fun getNegativeButtonType(type: ActionAreaType): ButtonType {
        return ButtonType.SECONDARY
    }

    override fun getNegativeButtonSize(type: ActionAreaType): ButtonSize {
        return ButtonSize.LARGE
    }

    override fun getNeutralButtonShape(type: ActionAreaType): ButtonShape {
        return when (type) {
            ActionAreaType.Strong -> ButtonShape.TEXT
            else -> ButtonShape.OUTLINED
        }
    }

    override fun getNeutralButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Strong -> ButtonType.ASSISTIVE
            else -> ButtonType.SECONDARY
        }
    }

    override fun getNeutralButtonSize(type: ActionAreaType): ButtonSize {
        return when (type) {
            ActionAreaType.Strong -> ButtonSize.SMALL
            else -> ButtonSize.LARGE
        }
    }
}

@JvmInline
value class WantedActionAreaButtonTypeCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<WantedActionAreaButtonTypeLoader> = staticCompositionLocalOf { WantedActionAreaButtonTypeLoaderImpl() }
) {
    val current: WantedActionAreaButtonTypeLoader
        @Composable get() = delegate.current

    infix fun provides(value: WantedActionAreaButtonTypeLoader) = delegate provides value
}
