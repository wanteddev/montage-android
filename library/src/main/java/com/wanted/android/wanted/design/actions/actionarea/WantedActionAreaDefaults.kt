package com.wanted.android.wanted.design.actions.actionarea

import androidx.compose.runtime.Composable
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant

/**
 * enum class ActionAreaType
 *
 * 액션 영역의 타입을 정의하는 Enum 클래스입니다.
 *
 * 액션 영역의 시각적 스타일과 버튼 구성을 결정할 때 사용됩니다. UI 요구사항에 따라 다음의 세 가지 옵션을 제공합니다:
 * - Strong: 강조된 액션 영역입니다.
 * - Neutral: 중립적인 액션 영역입니다.
 * - Cancel: 취소 중심의 액션 영역입니다.
 *
 * @see WantedActionAreaDefault
 */
enum class ActionAreaType {
    Strong,
    Neutral,
    Cancel
}

/**
 * data class WantedActionAreaDefault
 *
 * ActionArea에 필요한 버튼 기본 스타일을 정의한 데이터 클래스입니다.
 *
 * 각 버튼의 WantedButtonDefault를 개별적으로 설정할 수 있습니다.
 *
 * @property type ActionAreaType: 액션 영역 타입입니다.
 * @property positiveButtonDefault WantedButtonDefault: 메인 액션 버튼 스타일을 설정합니다.
 * @property negativeButtonDefault WantedButtonDefault: 서브 액션 버튼 스타일을 설정합니다.
 * @property neutralButtonDefault WantedButtonDefault: 추가 액션 버튼 스타일을 설정합니다.
 *
 * @see ActionAreaType
 * @see WantedButtonDefault
 */
data class WantedActionAreaDefault(
    val type: ActionAreaType = ActionAreaType.Strong,
    val positiveButtonDefault: WantedButtonDefault,
    val negativeButtonDefault: WantedButtonDefault,
    val neutralButtonDefault: WantedButtonDefault
)

/**
 * object WantedActionAreaDefaults
 *
 * WantedActionAreaDefault의 기본값을 제공하는 객체입니다.
 *
 * 액션 영역 타입에 따라 적절한 버튼 스타일을 자동으로 설정합니다.
 *
 * @see WantedActionAreaDefault
 * @see ActionAreaType
 */
object WantedActionAreaDefaults {
    /**
     * fun getDefault(...)
     *
     * WantedActionAreaDefault의 기본 설정을 생성합니다.
     *
     * 액션 영역 타입에 따라 positive, negative, neutral 버튼의 기본 스타일을 자동으로 설정합니다.
     * 각 버튼의 스타일을 개별적으로 커스터마이징할 수도 있습니다.
     *
     * 사용 예시:
     * ```kotlin
     * val config = WantedActionAreaDefaults.getDefault(
     *     type = ActionAreaType.Strong
     * )
     * ```
     *
     * @param type ActionAreaType: 액션 영역의 타입입니다. 기본값은 ActionAreaType.Strong입니다.
     * @param positiveButtonDefault WantedButtonDefault: 메인 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @param negativeButtonDefault WantedButtonDefault: 서브 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @param neutralButtonDefault WantedButtonDefault: 추가 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @return WantedActionAreaDefault: 설정된 WantedActionAreaDefault 인스턴스를 반환합니다.
     *
     * @see WantedActionAreaDefault
     * @see ActionAreaType
     */
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
        return ButtonType.PRIMARY
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