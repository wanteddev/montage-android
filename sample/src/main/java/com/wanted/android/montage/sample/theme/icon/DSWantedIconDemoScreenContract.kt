package com.wanted.android.montage.sample.theme.icon

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

object DSWantedIconDemoScreenContract {
    sealed interface DSWantedIconDemoEvent : BaseEvent {
        data class SetQuery(val query: String) : DSWantedIconDemoEvent
        data class SetBackground(val background: IconDemoBackground) : DSWantedIconDemoEvent
        data class ClickIcon(val iconName: String) : DSWantedIconDemoEvent
    }

    data class DSWantedIconDemoViewState(
        val icons: List<DSWantedIconItem> = emptyList(),
        val query: String = "",
        val background: IconDemoBackground = IconDemoBackground.Default,
    ) : BaseViewState

    sealed interface DSWantedIconDemoSideEffect : BaseSideEffect {
        data class ShowIconNameSnackBar(val iconName: String) : DSWantedIconDemoSideEffect
    }

    sealed interface DSWantedIconDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedIconDemoViewEvent
        data class OnQueryChange(val query: String) : DSWantedIconDemoViewEvent
        data class OnSelectBackground(val background: IconDemoBackground) : DSWantedIconDemoViewEvent
        data class OnClickIcon(val iconName: String) : DSWantedIconDemoViewEvent
    }
}
