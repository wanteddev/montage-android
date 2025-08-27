package com.wanted.android.wanted.design.actions.actionarea

import androidx.compose.runtime.Composable
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType

/**
 * enum class ActionAreaType
 *
 * 액션 영역의 타입을 정의하는 Enum 클래스입니다.
 * Strong, Neutral, Cancel 세 가지 타입이 존재합니다.
 */
enum class ActionAreaType {
    Strong,
    Neutral,
    Cancel
}

/**
 * data class WantedActionAreaDefault
 *
 * ActionArea에 필요한 버튼 기본 스타일을 묶어둔 데이터 클래스입니다.
 * 각 버튼의 WantedButtonDefault를 개별 설정할 수 있습니다.
 *
 * @param type 액션 영역 타입
 * @param positiveButtonDefault 메인 액션 버튼 스타일 설정
 * @param negativeButtonDefault 서브 액션 버튼 스타일 설정
 * @param neutralButtonDefault 추가 액션 버튼 스타일 설정
 */
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
            variant = getPositiveButtonVariant(type),
            type = getPositiveButtonType(type),
            size = getPositiveButtonSize(type)
        ),
        negativeButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            variant = getNegativeButtonVariant(type),
            type = getNegativeButtonType(type),
            size = getNegativeButtonSize(type)
        ),
        neutralButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            variant = getNeutralButtonVariant(type),
            type = getNeutralButtonType(type),
            size = getNeutralButtonSize(type)
        )
    ) = WantedActionAreaDefault(
        type = type,
        positiveButtonDefault = positiveButtonDefault,
        negativeButtonDefault = negativeButtonDefault,
        neutralButtonDefault = neutralButtonDefault,
    )

    private fun getPositiveButtonVariant(type: ActionAreaType): ButtonVariant {
        return when (type) {
            ActionAreaType.Cancel -> ButtonVariant.OUTLINED
            else -> ButtonVariant.SOLID
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

    private fun getNegativeButtonVariant(type: ActionAreaType): ButtonVariant {
        return ButtonVariant.OUTLINED
    }

    private fun getNegativeButtonType(type: ActionAreaType): ButtonType {
        return ButtonType.SECONDARY
    }

    private fun getNegativeButtonSize(type: ActionAreaType): ButtonSize {
        return ButtonSize.LARGE
    }

    private fun getNeutralButtonVariant(type: ActionAreaType): ButtonVariant {
        return when (type) {
            ActionAreaType.Strong -> ButtonVariant.TEXT
            else -> ButtonVariant.OUTLINED
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