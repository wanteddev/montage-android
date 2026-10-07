package com.wanted.android.montage.sample.theme.typography

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent

internal object DSWantedTypographyDemoScreenContract {
    sealed interface DSWantedTypographyDemoEvent : BaseEvent {
        data class SetWeight(val weight: TypographyWeight) : DSWantedTypographyDemoEvent
        data class SetSample(val sample: TypographySample) : DSWantedTypographyDemoEvent
        data class SetShowLineBox(val enabled: Boolean) : DSWantedTypographyDemoEvent
        data class ClickItem(val name: String) : DSWantedTypographyDemoEvent
    }

    data class DSWantedTypographyDemoViewState(
        val items: List<DSWantedTypographyItem> = DSWantedTypographyCatalog.items,
        val weight: TypographyWeight = TypographyWeight.All,
        val sample: TypographySample = TypographySample.Korean,
        val showLineBox: Boolean = false,
    ) : BaseViewState {
        val filteredItems: List<DSWantedTypographyItem>
            get() = if (weight == TypographyWeight.All) items else items.filter { it.weight == weight }
    }

    sealed interface DSWantedTypographyDemoSideEffect : BaseSideEffect {
        data class ShowCopySnackBar(val code: String) : DSWantedTypographyDemoSideEffect
    }

    sealed interface DSWantedTypographyDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedTypographyDemoViewEvent
        data class OnSelectWeight(val weight: TypographyWeight) : DSWantedTypographyDemoViewEvent
        data class OnSelectSample(val sample: TypographySample) : DSWantedTypographyDemoViewEvent
        data class OnChangeShowLineBox(val enabled: Boolean) : DSWantedTypographyDemoViewEvent
        data class OnClickItem(val name: String) : DSWantedTypographyDemoViewEvent
    }
}
