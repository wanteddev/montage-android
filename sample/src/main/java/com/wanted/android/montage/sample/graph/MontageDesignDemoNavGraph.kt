package com.wanted.android.montage.sample.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreen
import com.wanted.android.montage.sample.actions.button.DSWantedButtonDemoScreen
import com.wanted.android.montage.sample.actions.chip.DSWantedChipDemoScreen
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreen
import com.wanted.android.montage.sample.base.dropshadow.DSWantedDropShadowDemoScreen
import com.wanted.android.montage.sample.content.accordion.DSWantedAccordionDemoScreen
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreen
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreen
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreen
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreen
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreen
import com.wanted.android.montage.sample.content.listcell.DSWantedListCellDemoScreen
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreen
import com.wanted.android.montage.sample.content.playbadge.DSWantedPlayBadgeDemoScreen
import com.wanted.android.montage.sample.content.sectionheader.DSWantedSectionHeaderDemoScreen
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreen
import com.wanted.android.montage.sample.feedback.fallback.DSWantedFallbackViewDemoScreen
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreen
import com.wanted.android.montage.sample.loading.pulltorefresh.DSWantedPullToRefreshDemoScreen
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreen
import com.wanted.android.montage.sample.navigations.paginationdots.DSWantedPaginationDotsDemoScreen
import com.wanted.android.montage.sample.navigations.progressindicator.DSWantedProgressIndicatorDemoScreen
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreen
import com.wanted.android.montage.sample.feedback.pushbadge.DSWantedPushBadgeDemoScreen
import com.wanted.android.montage.sample.feedback.snackbar.DSWantedSnackBarDemoScreen
import com.wanted.android.montage.sample.feedback.toast.DSWantedToastDemoScreen
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AccordionDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ActionAreaDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.CategoryDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AlertDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AvatarDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AvatarGroupDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ChipDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ButtonDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.IconButtonDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.CardDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.DatePickerDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.DatePickerWheelDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.DialogTopAppBarDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.DropShadowDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.FallbackViewDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.LoadingDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PullToRefreshDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PageCounterDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ProgressIndicatorDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ProgressTrackerDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PaginationDotsDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.FramedStyleDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.FilterButtonDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.InputDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ListCellDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ListCardDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.NumberPickerDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PlayBadgeDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PopoverDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PopupDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AutoCompleteDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.BottomSheetDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.PushBadgeDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.SearchFieldDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.SearchTopAppBarDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.SectionHeaderDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.SnackBarDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.TextAreaDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.TextFieldDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.AutoCompleteTextFieldDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.TimePickerWheelDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.DemoList
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.ToastDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.TooltipDemo
import com.wanted.android.montage.sample.graph.MontageDesignDemoNavContract.TopAppBarDemo
import com.wanted.android.montage.sample.input.datepicker.DSWantedDatePickerDemoScreen
import com.wanted.android.montage.sample.input.datepickerwheel.DSWantedDatePickerWheelDemoScreen
import com.wanted.android.montage.sample.input.framedstyle.DSWantedFramedStyleDemoScreen
import com.wanted.android.montage.sample.input.filterbutton.DSWantedFilterButtonDemoScreen
import com.wanted.android.montage.sample.input.input.DSWantedInputDemoScreen
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreen
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreen
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreen
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreen
import com.wanted.android.montage.sample.input.textinput.autocompletetextfield.DSWantedAutoCompleteTextFieldDemoScreen
import com.wanted.android.montage.sample.input.timepickerwheel.DSWantedTimePickerWheelDemoScreen
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreen
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreen
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreen
import com.wanted.android.montage.sample.presentation.popover.DSWantedPopoverDemoScreen
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreen
import com.wanted.android.montage.sample.presentation.tooltip.DSWantedTooltipDemoScreen
import com.wanted.android.montage.sample.presentation.autocomplete.DSWantedAutoCompleteDemoScreen
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreen

@Composable
fun MontageDesignDemoNavGraph(
    navHostController: NavHostController,
    startDestination: Any,
    onClickBack: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        addMontageDesignDemoNavGraph(
            navHostController = navHostController,
            onClickBack = {
                if (!navHostController.popBackStack()) {
                    onClickBack()
                }
            }
        )
    }
}

fun NavGraphBuilder.addMontageDesignDemoNavGraph(
    navHostController: NavHostController,
    onClickBack: () -> Unit
) {
    composable<DemoList> {
        MontageDesignDemoListScreen(
            items = MontageDesignDemoNavContract.getAllDataObjects()
                .filterNot { it is DemoList },
            onSelect = { destination ->
                navHostController.navigate(destination)
            }
        )
    }

    composable<ButtonDemo> {
        DSWantedButtonDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ActionAreaDemo> {
        DSWantedActionAreaDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ChipDemo> {
        DSWantedChipDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<IconButtonDemo> {
        DSWantedIconButtonDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<FallbackViewDemo> {
        DSWantedFallbackViewDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<LoadingDemo> {
        DSWantedLoadingDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PullToRefreshDemo> {
        DSWantedPullToRefreshDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PageCounterDemo> {
        DSWantedPageCounterDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ProgressIndicatorDemo> {
        DSWantedProgressIndicatorDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ProgressTrackerDemo> {
        DSWantedProgressTrackerDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PaginationDotsDemo> {
        DSWantedPaginationDotsDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ToastDemo> {
        DSWantedToastDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<DropShadowDemo> {
        DSWantedDropShadowDemoScreen(
            modifier = Modifier,
            onClickBack = onClickBack
        )
    }

    composable<CardDemo> {
        DSWantedCardDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<InputDemo> {
        DSWantedInputDemoScreen(
            modifier = Modifier,
            onClickBack = onClickBack
        )
    }

    composable<NumberPickerDemo> {
        DSWantedNumberPickerDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<FilterButtonDemo> {
        DSWantedFilterButtonDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<FramedStyleDemo> {
        DSWantedFramedStyleDemoScreen(
            modifier = Modifier,
            onClickBack = onClickBack
        )
    }

    composable<TextFieldDemo> {
        DSWantedTextFieldDemoScreen(
            modifier = Modifier,
            onClickBack = onClickBack
        )
    }

    composable<AutoCompleteTextFieldDemo> {
        DSWantedAutoCompleteTextFieldDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<TextAreaDemo> {
        DSWantedTextAreaDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<TooltipDemo> {
        DSWantedTooltipDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PopoverDemo> {
        DSWantedPopoverDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PopupDemo> {
        DSWantedPopupDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<AutoCompleteDemo> {
        DSWantedAutoCompleteDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<BottomSheetDemo> {
        DSWantedBottomSheetDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<TopAppBarDemo> {
        DSWantedTopAppBarDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<DialogTopAppBarDemo> {
        DSWantedDialogTopAppBarDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<SearchFieldDemo> {
        DSWantedSearchFieldDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<SearchTopAppBarDemo> {
        DSWantedSearchTopAppBarDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<AvatarDemo> {
        DSWantedAvatarDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<AvatarGroupDemo> {
        DSWantedAvatarGroupDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<AccordionDemo> {
        DSWantedAccordionDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<CategoryDemo> {
        DSWantedCategoryDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<AlertDemo> {
        DSWantedAlertDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ListCellDemo> {
        DSWantedListCellDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<ListCardDemo> {
        DSWantedListCardDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<MontageDesignDemoNavContract.ContentBadgeDemo> {
        DSWantedContentBadgeDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<SnackBarDemo> {
        DSWantedSnackBarDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PlayBadgeDemo> {
        DSWantedPlayBadgeDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<SectionHeaderDemo> {
        DSWantedSectionHeaderDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<PushBadgeDemo> {
        DSWantedPushBadgeDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<DatePickerWheelDemo> {
        DSWantedDatePickerWheelDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<DatePickerDemo> {
        DSWantedDatePickerDemoScreen(
            onClickBack = onClickBack
        )
    }

    composable<TimePickerWheelDemo> {
        DSWantedTimePickerWheelDemoScreen(
            onClickBack = onClickBack
        )
    }
}
