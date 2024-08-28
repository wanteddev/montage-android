package com.wanted.android.wanted.design.actionarea

import androidx.compose.runtime.Composable
import com.wanted.android.wanted.design.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType


enum class ActionAreaType {
    Strong,
    Neutral,
    Compact,
    Cancel
}

data class WantedActionAreaDefault(
    val type: ActionAreaType = ActionAreaType.Strong,
    val positiveButtonDefault: WantedButtonDefault,
    val negativeButtonDefault: WantedButtonDefault,
    val neutralButtonDefault: WantedButtonDefault
)

object WantedActionAreaDefaults {
    @Composable
    fun getDefault(
        type: ActionAreaType = ActionAreaType.Strong,
        positiveButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            shape = getPositiveButtonShape(type),
            type = getPositiveButtonType(type),
            size = getPositiveButtonSize(type)
        ),
        negativeButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            shape = getNegativeButtonShape(type),
            type = getNegativeButtonType(type),
            size = getNegativeButtonSize(type)
        ),
        neutralButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            shape = getNeutralButtonShape(type),
            type = getNeutralButtonType(type),
            size = getNeutralButtonSize(type)
        )
    ) = WantedActionAreaDefault(
        type = type,
        positiveButtonDefault = positiveButtonDefault,
        negativeButtonDefault = negativeButtonDefault,
        neutralButtonDefault = neutralButtonDefault,
    )

    private fun getPositiveButtonShape(type: ActionAreaType): ButtonShape {
        return when (type) {
            ActionAreaType.Cancel -> ButtonShape.OUTLINED
            else -> ButtonShape.SOLID
        }
    }

    private fun getPositiveButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Cancel -> ButtonType.ASSISTIVE
            else -> ButtonType.PRIMARY
        }
    }

    private fun getPositiveButtonSize(type: ActionAreaType): ButtonSize {
        return ButtonSize.LARGE
    }

    private fun getNegativeButtonShape(type: ActionAreaType): ButtonShape {
        return ButtonShape.OUTLINED
    }

    private fun getNegativeButtonType(type: ActionAreaType): ButtonType {
        return ButtonType.SECONDARY
    }

    private fun getNegativeButtonSize(type: ActionAreaType): ButtonSize {
        return ButtonSize.LARGE
    }

    private fun getNeutralButtonShape(type: ActionAreaType): ButtonShape {
        return when (type) {
            ActionAreaType.Strong -> ButtonShape.TEXT
            else -> ButtonShape.OUTLINED
        }
    }

    private fun getNeutralButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Strong -> ButtonType.ASSISTIVE
            else -> ButtonType.ASSISTIVE
        }
    }

    private fun getNeutralButtonSize(type: ActionAreaType): ButtonSize {
        return when (type) {
            ActionAreaType.Strong -> ButtonSize.SMALL
            else -> ButtonSize.LARGE
        }
    }
}