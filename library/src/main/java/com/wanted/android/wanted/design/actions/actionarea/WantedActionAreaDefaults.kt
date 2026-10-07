package com.wanted.android.wanted.design.actions.actionarea

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.wanted.android.designsystem.R
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
 * @property mainButtonDefault WantedButtonDefault: 메인 액션 버튼 스타일을 설정합니다.
 * @property alternativeButtonDefault WantedButtonDefault: 대체 액션 버튼 스타일을 설정합니다.
 * @property subButtonDefault WantedButtonDefault: 보조 액션 버튼 스타일을 설정합니다.
 *
 * @see ActionAreaType
 * @see WantedButtonDefault
 */
data class WantedActionAreaDefault(
    val type: ActionAreaType = ActionAreaType.Strong,
    val mainButtonDefault: WantedButtonDefault,
    val alternativeButtonDefault: WantedButtonDefault,
    val subButtonDefault: WantedButtonDefault
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
 *
 * @property CAPTION_ICON Int: 캡션 아이콘을 사용할 때 권장되는 기본 아이콘(`@DrawableRes`)입니다. 캡션 아이콘은 기본적으로 표시되지 않으며, 아이콘이 필요할 때 `WantedActionArea` 의 `captionIcon` 에 이 값을 전달합니다.
 */
object WantedActionAreaDefaults {
    // 캡션 아이콘을 사용할 때 권장되는 기본 아이콘입니다.
    //
    // 캡션 아이콘은 기본적으로 표시되지 않으며, 아이콘이 필요할 때 이 값을 전달합니다.
    //
    // 사용 예시:
    // ```kotlin
    // WantedActionArea(
    //     caption = "캡션",
    //     captionIcon = WantedActionAreaDefaults.CAPTION_ICON,
    //     ...
    // )
    // ```
    @DrawableRes
    val CAPTION_ICON: Int = R.drawable.icon_normal_circle_info

    /**
     * fun getDefault(...)
     *
     * WantedActionAreaDefault의 기본 설정을 생성합니다.
     *
     * 액션 영역 타입에 따라 main, alternative, sub 버튼의 기본 스타일을 자동으로 설정합니다.
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
     * @param mainButtonDefault WantedButtonDefault: 메인 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @param alternativeButtonDefault WantedButtonDefault: 대체 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @param subButtonDefault WantedButtonDefault: 보조 액션 버튼의 기본 스타일입니다. 타입에 따라 자동 설정됩니다.
     * @return WantedActionAreaDefault: 설정된 WantedActionAreaDefault 인스턴스를 반환합니다.
     *
     * @see WantedActionAreaDefault
     * @see ActionAreaType
     */
    @Composable
    fun getDefault(
        type: ActionAreaType = ActionAreaType.Strong,
        mainButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            variant = getMainButtonVariant(),
            type = getMainButtonType(type),
            size = getMainButtonSize()
        ),
        alternativeButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            variant = getAlternativeButtonVariant(),
            type = getAlternativeButtonType(),
            size = getAlternativeButtonSize()
        ),
        subButtonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(
            variant = getSubButtonVariant(type),
            type = getSubButtonType(type),
            size = getSubButtonSize(type)
        )
    ) = WantedActionAreaDefault(
        type = type,
        mainButtonDefault = mainButtonDefault,
        alternativeButtonDefault = alternativeButtonDefault,
        subButtonDefault = subButtonDefault,
    )

    internal fun getMainButtonVariant(): ButtonVariant {
        return ButtonVariant.SOLID
    }

    internal fun getMainButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Cancel -> ButtonType.ASSISTIVE
            else -> ButtonType.PRIMARY
        }
    }

    internal fun getMainButtonSize(): ButtonSize {
        return ButtonSize.LARGE
    }

    internal fun getAlternativeButtonVariant(): ButtonVariant {
        return ButtonVariant.OUTLINED
    }

    internal fun getAlternativeButtonType(): ButtonType {
        return ButtonType.ASSISTIVE
    }

    internal fun getAlternativeButtonSize(): ButtonSize {
        return ButtonSize.LARGE
    }

    internal fun getSubButtonVariant(type: ActionAreaType): ButtonVariant {
        return when (type) {
            ActionAreaType.Strong -> ButtonVariant.TEXT
            else -> ButtonVariant.OUTLINED
        }
    }

    internal fun getSubButtonType(type: ActionAreaType): ButtonType {
        return when (type) {
            ActionAreaType.Strong -> ButtonType.ASSISTIVE
            else -> ButtonType.ASSISTIVE
        }
    }

    internal fun getSubButtonSize(type: ActionAreaType): ButtonSize {
        return when (type) {
            ActionAreaType.Strong -> ButtonSize.SMALL
            else -> ButtonSize.LARGE
        }
    }
}