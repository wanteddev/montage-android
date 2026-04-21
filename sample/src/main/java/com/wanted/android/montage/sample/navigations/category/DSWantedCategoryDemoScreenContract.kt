package com.wanted.android.montage.sample.navigations.category

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Size

object DSWantedCategoryDemoScreenContract {
    sealed interface DSWantedCategoryDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedCategoryDemoViewState) :
            DSWantedCategoryDemoEvent

        data class ShowCode(val isShowCode: Boolean) : DSWantedCategoryDemoEvent
        data object CopyCode : DSWantedCategoryDemoEvent
        data class SetSize(val size: Size) : DSWantedCategoryDemoEvent
        data class SetAlternative(val isAlternative: Boolean) : DSWantedCategoryDemoEvent
        data class SetHorizontalPadding(val enabled: Boolean) : DSWantedCategoryDemoEvent
        data class SetVerticalPadding(val enabled: Boolean) : DSWantedCategoryDemoEvent
        data class ToggleItem(val item: String) : DSWantedCategoryDemoEvent
    }

    data class DSWantedCategoryDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",
        val size: Size = Size.Medium,
        val isAlternative: Boolean = false,
        val horizontalPadding: Boolean = false,
        val verticalPadding: Boolean = false,
        val selectedItems: List<String> = listOf("디자인"),
        val itemList: List<String> = listOf("개발", "디자인", "기획", "마케팅", "영업")
    ) : BaseViewState

    sealed interface DSWantedCategoryDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedCategoryDemoSideEffect
    }

    sealed interface DSWantedCategoryDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedCategoryDemoViewEvent
        data object OnClickShowCode : DSWantedCategoryDemoViewEvent
        data object OnClickCopyCode : DSWantedCategoryDemoViewEvent
        data class OnSizeChanged(val size: Size) : DSWantedCategoryDemoViewEvent
        data class OnAlternativeChanged(val isAlternative: Boolean) : DSWantedCategoryDemoViewEvent
        data class OnHorizontalPaddingChanged(val enabled: Boolean) : DSWantedCategoryDemoViewEvent
        data class OnVerticalPaddingChanged(val enabled: Boolean) : DSWantedCategoryDemoViewEvent
        data class OnItemClicked(val item: String) : DSWantedCategoryDemoViewEvent
    }
}