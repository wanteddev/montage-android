package com.wanted.android.montage.sample.navigations.topbar.dialogtopbar

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.dialogtopbar.DSWantedDialogTopAppBarDemoScreenContract.DSWantedDialogTopAppBarDemoViewState
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedDialogTopAppBarDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedDialogTopAppBarDemoEvent, DSWantedDialogTopAppBarDemoViewState, DSWantedDialogTopAppBarDemoSideEffect>() {
    override fun setInitialState() = DSWantedDialogTopAppBarDemoViewState().let { state ->
        state.copy(code = generateCode(state))
    }

    override fun handleEvents(event: DSWantedDialogTopAppBarDemoEvent) {
        when (event) {
            is DSWantedDialogTopAppBarDemoEvent.InitState -> setState { event.viewState }
            is DSWantedDialogTopAppBarDemoEvent.SetVariant -> {
                setState {
                    val newState = copy(variant = event.variant)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetBackground -> {
                setState {
                    val newState = copy(background = event.background)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetBackgroundColorEnabled -> {
                setState {
                    val newState = copy(backgroundColorEnabled = event.backgroundColorEnabled)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetNavigationIcon -> {
                setState {
                    val newState = copy(navigationIcon = event.navigationIcon)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetActions -> {
                setState {
                    val newState = copy(actions = event.actions)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetShowCloseButton -> {
                setState {
                    val newState = copy(showCloseButton = event.showCloseButton)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetScrollable -> {
                setState {
                    val newState = copy(scrollable = event.scrollable)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetListRevert -> {
                setState {
                    copy(listRevert = event.listRevert)
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.SetUseBottomSheet -> {
                setState {
                    val newState = copy(useBottomSheet = event.useBottomSheet)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedDialogTopAppBarDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.show, code = generateCode(this)) }
            }

            is DSWantedDialogTopAppBarDemoEvent.ShowModal -> {
                setState { copy(isShowModal = event.show) }
            }

            is DSWantedDialogTopAppBarDemoEvent.CopyCode -> {
                setEffect { DSWantedDialogTopAppBarDemoSideEffect.CopyCode(viewState.value.code) }
            }
        }
    }

    private fun generateCode(state: DSWantedDialogTopAppBarDemoViewState): String {
        val params = mutableListOf<String>()

        if (state.variant != Variant.Normal) {
            params.add("variant = Variant.${state.variant.name}")
        }

        if (state.backgroundColorEnabled) {
            params.add("backgroundColor = DesignSystemTheme.colors.backgroundElevatedNormal")
        } else {
            params.add("backgroundColor = Color.Transparent")
        }

        params.add("background = ${state.background}")

        if (state.scrollable) {
            params.add("scrollableState = rememberScrollState()")
        }

        params.add("title = \"Dialog Title\"")

        if (state.navigationIcon) {
            params.add(
                """navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_arrow_left),
                        onClick = { /* Handle back */ }
                    )
                }"""
            )
        }

        if (state.actions && !state.showCloseButton) {
            params.add(
                """actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.icon_normal_share),
                        onClick = { /* Handle action */ }
                    )
                }"""
            )
        }

        if (state.showCloseButton) {
            params.add("onClickClose = { /* Handle close */ }")
        }

        val paramString = if (params.isNotEmpty()) {
            "(\n    ${params.joinToString(",\n    ")}\n)"
        } else {
            "()"
        }

        val componentName = if (state.showCloseButton) {
            "WantedDialogCloseTopAppBar"
        } else {
            "WantedDialogTopAppBar"
        }

        return "$componentName$paramString"
    }
}
