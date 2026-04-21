package com.wanted.android.montage.sample.graph

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
sealed interface MontageDesignDemoNavContract {

    @Keep
    @Serializable
    data object DemoList : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ButtonDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ActionAreaDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ChipDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object IconButtonDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object FallbackViewDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object LoadingDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PullToRefreshDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PageCounterDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ProgressIndicatorDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ProgressTrackerDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PaginationDotsDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ToastDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DropShadowDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object CardDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object InputDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object NumberPickerDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object FilterButtonDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object FramedStyleDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TextFieldDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AutoCompleteTextFieldDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TextAreaDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TooltipDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PopoverDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PopupDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AutoCompleteDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object BottomSheetDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DialogTopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SearchFieldDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SearchTopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AvatarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AvatarGroupDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AccordionDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object CategoryDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ListCellDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ListCardDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AlertDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ContentBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TimePickerWheelDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PushBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SnackBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PlayBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SectionHeaderDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DatePickerDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DatePickerWheelDemo : MontageDesignDemoNavContract

    companion object {
        fun fromClassName(className: String): MontageDesignDemoNavContract? {
            return MontageDesignDemoNavContract::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .find { it::class.simpleName == className }
        }

        fun getAllDataObjects(): List<MontageDesignDemoNavContract> {
            val order = listOf(
                "DemoList",
                "ButtonDemo",
                "ActionAreaDemo",
                "ChipDemo",
                "IconButtonDemo",
                "CardDemo",
                "ListCellDemo",
                "ListCardDemo",
                "ContentBadgeDemo",
                "PlayBadgeDemo",
                "SectionHeaderDemo",
                "AvatarDemo",
                "AvatarGroupDemo",
                "AccordionDemo",
                "FallbackViewDemo",
                "LoadingDemo",
                "PullToRefreshDemo",
                "AlertDemo",
                "SnackBarDemo",
                "ToastDemo",
                "PushBadgeDemo",
                "InputDemo",
                "TextFieldDemo",
                "AutoCompleteTextFieldDemo",
                "TextAreaDemo",
                "SearchFieldDemo",
                "FilterButtonDemo",
                "NumberPickerDemo",
                "DatePickerDemo",
                "DatePickerWheelDemo",
                "TimePickerWheelDemo",
                "FramedStyleDemo",
                "TopAppBarDemo",
                "DialogTopAppBarDemo",
                "SearchTopAppBarDemo",
                "CategoryDemo",
                "PageCounterDemo",
                "PaginationDotsDemo",
                "ProgressIndicatorDemo",
                "ProgressTrackerDemo",
                "TooltipDemo",
                "PopoverDemo",
                "PopupDemo",
                "AutoCompleteDemo",
                "BottomSheetDemo",
                "DropShadowDemo"
            ).withIndex().associate { it.value to it.index }

            return MontageDesignDemoNavContract::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .sortedBy { order[it::class.simpleName] ?: Int.MAX_VALUE }
        }
    }
}
