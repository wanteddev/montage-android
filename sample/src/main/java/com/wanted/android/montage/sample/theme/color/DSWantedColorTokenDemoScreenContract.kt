package com.wanted.android.montage.sample.theme.color

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

internal object DSWantedColorTokenDemoScreenContract {
    sealed interface DSWantedColorTokenDemoEvent : BaseEvent {
        data class SetQuery(val query: String) : DSWantedColorTokenDemoEvent
        data class SetGroup(val group: ColorTokenGroup) : DSWantedColorTokenDemoEvent
        data class ClickItem(val code: String) : DSWantedColorTokenDemoEvent
    }

    data class DSWantedColorTokenDemoViewState(
        val items: List<DSWantedColorTokenItem> = DSWantedColorTokenCatalog.items,
        val query: String = "",
        val group: ColorTokenGroup = ColorTokenGroup.All,
    ) : BaseViewState {
        val filteredItems: List<DSWantedColorTokenItem>
            get() = items.filter { item ->
                (group == ColorTokenGroup.All || item.group == group) && item.matches(query)
            }
    }

    sealed interface DSWantedColorTokenDemoSideEffect : BaseSideEffect {
        data class ShowCopySnackBar(val code: String) : DSWantedColorTokenDemoSideEffect
    }

    sealed interface DSWantedColorTokenDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedColorTokenDemoViewEvent
        data class OnQueryChange(val query: String) : DSWantedColorTokenDemoViewEvent
        data class OnSelectGroup(val group: ColorTokenGroup) : DSWantedColorTokenDemoViewEvent
        data class OnClickItem(val code: String) : DSWantedColorTokenDemoViewEvent
    }
}
