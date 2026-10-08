package com.wanted.android.montage.sample.actions.iconbutton

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewState
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.IconButtonVariant
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonInteractionEffect
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackgroundSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedIconButtonDemoViewModel @Inject constructor() :
    WantedStateViewModel<
            DSWantedIconButtonDemoEvent,
            DSWantedIconButtonDemoViewState,
            DSWantedIconButtonDemoSideEffect
            >() {
    override fun setInitialState() = DSWantedIconButtonDemoViewState()

    override fun handleEvents(event: DSWantedIconButtonDemoEvent) {
        when (event) {
            is DSWantedIconButtonDemoEvent.InitState -> {
                setState { event.viewState }
            }

            is DSWantedIconButtonDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedIconButtonDemoEvent.CopyCode -> {
                copyCode()
            }

            is DSWantedIconButtonDemoEvent.SetContainerSize -> {
                setState { copy(containerSize = event.size, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetNormalSize -> {
                setState { copy(normalSize = event.size, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetUseCustomSize -> {
                setState { copy(useCustomSize = event.useCustomSize, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetCustomBoxSize -> {
                setState { copy(customBoxSize = event.boxSizeDp, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetEnabled -> {
                setState { copy(enabled = event.enabled, code = getCode()) }
            }

            // variant·인터랙션·배지 등 외형 옵션은 분리해서 처리한다.
            else -> handleAppearanceEvent(event)
        }
    }

    /** variant·인터랙션·배지 등 외형 옵션 이벤트를 처리합니다. */
    private fun handleAppearanceEvent(event: DSWantedIconButtonDemoEvent) {
        when (event) {
            is DSWantedIconButtonDemoEvent.SetVariant -> {
                setState {
                    copy(
                        variant = event.variant,
                        interactionEffect = coerceInteractionEffect(event.variant, interactionEffect),
                        interactionOverflow = coerceInteractionOverflow(event.variant, interactionOverflow),
                        code = getCode()
                    )
                }
            }

            is DSWantedIconButtonDemoEvent.SetInteractionEffect -> {
                setState { copy(interactionEffect = event.interactionEffect, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetInteractionOverflow -> {
                setState { copy(interactionOverflow = event.interactionOverflow, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetUseNormalInteraction -> {
                setState { copy(useNormalInteraction = event.useNormalInteraction, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetShowBadge -> {
                setState { copy(showBadge = event.showBadge, code = getCode()) }
            }

            is DSWantedIconButtonDemoEvent.SetBadgePosition -> {
                setState { copy(badgePosition = event.badgePosition, code = getCode()) }
            }

            else -> Unit
        }
    }

    private fun copyCode() {
        setEffect { DSWantedIconButtonDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val content = when (state.variant) {
            IconButtonVariant.Normal -> "WantedIconButtonNormal"
            IconButtonVariant.Outlined -> "WantedIconButtonOutlined"
            IconButtonVariant.Solid -> "WantedIconButtonSolid"
            IconButtonVariant.Background -> "WantedIconButtonBackground"
        }
        val sizeTypeName = when (state.variant) {
            IconButtonVariant.Outlined, IconButtonVariant.Solid -> "WantedIconButtonSize"
            IconButtonVariant.Normal -> "WantedIconButtonNormalSize"
            IconButtonVariant.Background -> "WantedIconButtonBackgroundSize"
        }
        val sizeLine = if (state.useCustomSize) {
            "    size = $sizeTypeName.Custom(${state.customBoxSize}.dp),"
        } else {
            val sizeValue = when (state.variant) {
                IconButtonVariant.Outlined, IconButtonVariant.Solid -> state.containerSize
                IconButtonVariant.Normal -> state.normalSize
                IconButtonVariant.Background -> WantedIconButtonBackgroundSize.Default
            }
            "    size = $sizeTypeName.$sizeValue,"
        }

        val lines = buildList {
            add("$content(")
            add("    icon = R.drawable.icon_wanted,")
            add(sizeLine)
            add("    enabled = ${state.enabled},")
            addAll(optionLines(state))
            add("    onClick = { /* on click */ }")
            add(")")
        }
        return lines.joinToString("\n")
    }

    /** 배지·인터랙션 등 선택 옵션의 코드 라인을 만듭니다. 선택되지 않은 옵션은 생략합니다. */
    private fun optionLines(state: DSWantedIconButtonDemoViewState): List<String> = buildList {
        if (state.showBadge) {
            add("    pushBadge = { WantedPushBadge() },")
            add("    badgePosition = IconButtonBadgePosition.${state.badgePosition.name},")
        }
        interactionLine(state)?.let { add(it) }
        if (state.variant == IconButtonVariant.Normal && state.interactionOverflow) {
            add("    interactionOverflow = true,")
        }
        if (state.variant == IconButtonVariant.Background && state.useNormalInteraction) {
            add("    useNormalInteraction = true,")
        }
    }

    /** Dim 은 Normal variant 전용이므로, 다른 variant 로 바뀌면 Highlight 로 되돌립니다. */
    private fun coerceInteractionEffect(
        variant: IconButtonVariant,
        interactionEffect: IconButtonInteractionEffect
    ): IconButtonInteractionEffect {
        val dimOnNonNormal = variant != IconButtonVariant.Normal &&
                interactionEffect == IconButtonInteractionEffect.Dim
        return if (dimOnNonNormal) IconButtonInteractionEffect.Highlight else interactionEffect
    }

    /** interactionOverflow 는 Normal variant 전용이므로, 다른 variant 로 바뀌면 끕니다. */
    private fun coerceInteractionOverflow(
        variant: IconButtonVariant,
        interactionOverflow: Boolean
    ): Boolean = interactionOverflow && variant == IconButtonVariant.Normal

    /**
     * 인터랙션 관련 코드 라인을 만듭니다.
     *
     * Normal variant 는 interactionEffect 를, 나머지 variant 는 아직 disableInteraction 을 사용합니다.
     */
    private fun interactionLine(state: DSWantedIconButtonDemoViewState): String? = when {
        state.variant == IconButtonVariant.Normal -> {
            if (state.interactionEffect == IconButtonInteractionEffect.Highlight) {
                null
            } else {
                "    interactionEffect = IconButtonInteractionEffect.${state.interactionEffect.name},"
            }
        }

        state.interactionEffect == IconButtonInteractionEffect.None -> "    disableInteraction = true,"

        else -> null
    }
}
