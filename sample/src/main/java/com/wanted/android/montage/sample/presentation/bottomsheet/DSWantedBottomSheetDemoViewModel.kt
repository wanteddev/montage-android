package com.wanted.android.montage.sample.presentation.bottomsheet

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoSideEffect
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewState
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedBottomSheetDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedBottomSheetDemoEvent, DSWantedBottomSheetDemoViewState, DSWantedBottomSheetDemoSideEffect>() {
    override fun setInitialState() = DSWantedBottomSheetDemoViewState()

    override fun handleEvents(event: DSWantedBottomSheetDemoEvent) {
        when (event) {
            is DSWantedBottomSheetDemoEvent.InitState -> {
                setState { event.viewState }
            }

            is DSWantedBottomSheetDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedBottomSheetDemoEvent.CopyCode -> copyCode()
            is DSWantedBottomSheetDemoEvent.SetModalType -> {
                setState { copy(modalType = event.type) }
            }

            is DSWantedBottomSheetDemoEvent.SetDismissOnClickOutside -> {
                setState { copy(dismissOnClickOutside = event.dismiss) }
            }

            is DSWantedBottomSheetDemoEvent.SetShowSheet -> {
                setState { copy(isShowSheet = event.show) }
            }

            is DSWantedBottomSheetDemoEvent.SetNavigationVariant -> {
                setState { copy(navigationVariant = event.variant) }
            }

            is DSWantedBottomSheetDemoEvent.SetCloseButtonBackground -> {
                setState { copy(closeButtonBackground = event.use) }
            }

            is DSWantedBottomSheetDemoEvent.SetContentPadding -> {
                setState { copy(useContentPadding = event.use) }
            }

            is DSWantedBottomSheetDemoEvent.SetUseActionArea -> {
                setState { copy(useActionArea = event.use) }
            }

        }
    }

    private fun copyCode() {
        setEffect { DSWantedBottomSheetDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val typeString = when (val type = state.modalType) {
            ModalType.Flexible -> "ModalType.Flexible"
            is ModalType.FixedWrapContent -> {
                "ModalType.FixedWrapContent(" +
                    "isCloseable = ${type.isCloseable}, " +
                    "isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.Fixed -> {
                "ModalType.Fixed(" +
                    "height = ${type.height.value}.dp, " +
                    "isCloseable = ${type.isCloseable}, " +
                    "isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.FixedFullScreen -> {
                "ModalType.FixedFullScreen(" +
                    "isCloseable = ${type.isCloseable}, " +
                    "isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }

            is ModalType.FixedRatio -> {
                "ModalType.FixedRatio(" +
                    "ratio = ${type.ratio}f, " +
                    "isCloseable = ${type.isCloseable}, " +
                    "isSystemBottomSheet = ${type.isSystemBottomSheet})"
            }
        }
        val variantExpression = variantExpression(state)
        val sheetDefaultLine = sheetDefaultLine(state)
        val bottomBarLine = bottomBarLine(state.useActionArea)

        return """
            WantedModalBottomSheet(
                isShow = ${state.isShowSheet},
                onDismissRequest = { /* on dismiss */ },
                type = $typeString,$sheetDefaultLine
                dismissOnClickOutside = ${state.dismissOnClickOutside},
                topBar = {
                    WantedDialogCloseTopAppBar(
                        variant = $variantExpression,
                        title = "Bottom Sheet",
                        onClickClose = { /* on dismiss */ }
                    )
                },$bottomBarLine
                content = { Text("Bottom Sheet") }
            )
        """.trimIndent()
    }

    // 아이콘 배경은 Floating 만 갖는 속성이라 Floating 일 때만 인자로 싣는다.
    private fun variantExpression(state: DSWantedBottomSheetDemoViewState): String =
        if (state.navigationVariant is Variant.Floating) {
            "Variant.Floating(iconBackground = ${state.closeButtonBackground})"
        } else {
            "Variant.${state.navigationVariant::class.simpleName}"
        }

    // Content 여백은 컴포넌트 기본값(스펙)이라 켤 때는 아무것도 안 넘긴다. 끌 때만 명시한다.
    private fun sheetDefaultLine(state: DSWantedBottomSheetDemoViewState): String {
        if (state.useContentPadding) return ""

        return "\n                sheetDefault = WantedBottomSheetDefaults" +
            ".getWithoutContentPadding(),"
    }

    private fun bottomBarLine(useActionArea: Boolean): String {
        if (!useActionArea) return ""

        return "\n                bottomBar = {" +
            "\n                    WantedActionArea(safeArea = false, divider = false) { }" +
            "\n                },"
    }
}
