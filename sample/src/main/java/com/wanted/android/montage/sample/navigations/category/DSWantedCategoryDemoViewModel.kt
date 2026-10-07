package com.wanted.android.montage.sample.navigations.category

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoEvent
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoSideEffect
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoViewState
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Size
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Variant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedCategoryDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedCategoryDemoEvent, DSWantedCategoryDemoViewState, DSWantedCategoryDemoSideEffect>() {
    override fun setInitialState() = DSWantedCategoryDemoViewState()

    override fun handleEvents(event: DSWantedCategoryDemoEvent) {
        when (event) {
            is DSWantedCategoryDemoEvent.InitState -> setState { event.viewState }
            is DSWantedCategoryDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedCategoryDemoEvent.CopyCode -> copyCode()
            is DSWantedCategoryDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedCategoryDemoEvent.SetVariant -> setState { copy(variant = event.variant) }

            is DSWantedCategoryDemoEvent.SetCustomColors -> {
                setState { copy(customColors = event.enabled) }
            }

            is DSWantedCategoryDemoEvent.SetHorizontalPadding -> {
                setState { copy(horizontalPadding = event.enabled) }
            }

            is DSWantedCategoryDemoEvent.SetVerticalPadding -> {
                setState { copy(verticalPadding = event.enabled) }
            }

            is DSWantedCategoryDemoEvent.ToggleItem -> toggleItem(event.item)
        }
    }

    private fun toggleItem(item: String) {
        setState {
            if (selectedItems.contains(item)) {
                copy(selectedItems = selectedItems - item)
            } else {
                copy(selectedItems = selectedItems + item)
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedCategoryDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            Size.Small -> "Size.Small"
            Size.Medium -> "Size.Medium"
            Size.Large -> "Size.Large"
            Size.XLarge -> "Size.XLarge"
        }
        val variantCode = when {
            state.variant is Variant.Alternative -> "Variant.Alternative"
            state.customColors -> "Variant.Normal(\n" +
                "        activeBackgroundColor = DesignSystemTheme.colors.surfaceBrandPrimary,\n" +
                "        activeContentColor = DesignSystemTheme.colors.staticWhite\n" +
                "    )"
            else -> "Variant.Normal()"
        }
        val selectedListCode = state.selectedItems.joinToString(", ", "listOf(", ")") {
            "\"${it.replace("\\", "\\\\").replace("\"", "\\\"")}\""
        }

        return """
            WantedCategory(
                itemList = listOf("개발", "디자인", "기획", "마케팅", "영업"),
                selectedList = $selectedListCode,
                size = $sizeString,
                horizontalPadding = ${state.horizontalPadding},
                isVerticalPadding = ${state.verticalPadding},
                variant = $variantCode,
                onClick = { item, isSelected -> /* handle */ }
            )
        """.trimIndent()
    }
}