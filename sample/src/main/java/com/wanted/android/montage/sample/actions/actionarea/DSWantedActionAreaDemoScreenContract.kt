package com.wanted.android.montage.sample.actions.actionarea

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType

object DSWantedActionAreaDemoScreenContract {
    sealed interface DSWantedActionAreaDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedActionAreaDemoViewState) :
            DSWantedActionAreaDemoEvent

        // UI 제어
        data class ShowCode(val isShowCode: Boolean) : DSWantedActionAreaDemoEvent
        data class Sample(val isShowSample: Boolean) : DSWantedActionAreaDemoEvent
        data object CopyCode : DSWantedActionAreaDemoEvent

        // 옵션 변경
        data class SetType(val type: ActionAreaType) : DSWantedActionAreaDemoEvent
        data class SetSafeArea(val safeArea: Boolean) : DSWantedActionAreaDemoEvent
        data class SetCaption(val caption: Boolean) : DSWantedActionAreaDemoEvent
        data class SetDivider(val divider: Boolean) : DSWantedActionAreaDemoEvent
        data class SetNegative(val negative: Boolean) : DSWantedActionAreaDemoEvent
        data class SetNeutral(val neutral: Boolean) : DSWantedActionAreaDemoEvent
        data class SetExtra(val extra: Boolean) : DSWantedActionAreaDemoEvent
        data class SetBackground(val background: Boolean) : DSWantedActionAreaDemoEvent
        data class SetGradationColor(val colorIndex: Int) : DSWantedActionAreaDemoEvent
    }

    data class DSWantedActionAreaDemoViewState(
        val isLoading: Boolean = false,
        val isShowCode: Boolean = false,
        val isShowSample: Boolean = false,
        val code: String = "",
        val typeList: List<ActionAreaType> = ActionAreaType.entries.toList(),
        val type: ActionAreaType = ActionAreaType.Strong,
        val safeArea: Boolean = true,
        val caption: Boolean = false,
        val divider: Boolean = false,
        val negative: Boolean = false,
        val neutral: Boolean = false,
        val extra: Boolean = false,
        val background: Boolean = false,
        val gradationColorIndex: Int = 0,
        val gradationColorList: List<GradationColorOption> = listOf(
            GradationColorOption("White", 0xFFFFFFFF),
            GradationColorOption("Light Gray", 0xFFF5F5F5),
            GradationColorOption("Gray", 0xFFEEEEEE),
            GradationColorOption("Blue", 0xFFE3F2FD),
            GradationColorOption("Green", 0xFFE8F5E9),
            GradationColorOption("Red", 0xFFFFEBEE),
            GradationColorOption("Yellow", 0xFFFFF9C4),
        )
    ) : BaseViewState

    data class GradationColorOption(
        val name: String,
        val color: Long
    )

    sealed interface DSWantedActionAreaDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedActionAreaDemoSideEffect
    }


    sealed interface DSWantedActionAreaDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedActionAreaDemoViewEvent
        data object OnClickShowCode : DSWantedActionAreaDemoViewEvent
        data object OnClickSample : DSWantedActionAreaDemoViewEvent

        data object OnClickCopyCode : DSWantedActionAreaDemoViewEvent

        data class OnSelectType(val type: ActionAreaType) : DSWantedActionAreaDemoViewEvent
        data class OnChangeSafeArea(val safeArea: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeCaption(val caption: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeDivider(val divider: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeNegative(val negative: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeNeutral(val neutral: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeExtra(val extra: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeBackground(val background: Boolean) : DSWantedActionAreaDemoViewEvent
        data class OnChangeGradationColor(val colorIndex: Int) : DSWantedActionAreaDemoViewEvent
    }
}