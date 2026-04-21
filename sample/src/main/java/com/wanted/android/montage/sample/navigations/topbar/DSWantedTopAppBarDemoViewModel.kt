package com.wanted.android.montage.sample.navigations.topbar

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.DSWantedTopAppBarDemoScreenContract.DSWantedTopAppBarDemoViewState
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedTopAppBarDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedTopAppBarDemoEvent, DSWantedTopAppBarDemoViewState, DSWantedTopAppBarDemoSideEffect>() {
    override fun setInitialState() = DSWantedTopAppBarDemoViewState().let { state ->
        state.copy(code = generateCode(state))
    }

    override fun handleEvents(event: DSWantedTopAppBarDemoEvent) {
        when (event) {
            is DSWantedTopAppBarDemoEvent.InitState -> setState { event.viewState }
            is DSWantedTopAppBarDemoEvent.SetType -> {
                setState {
                    val newState = copy(variant = event.type)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.SetBackground -> {
                setState {
                    val newState = copy(background = event.background)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.SetTitleAlignCenter -> {
                setState {
                    val newState = copy(titleAlignCenter = event.titleAlignCenter)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.SetNavigationIcon -> {
                setState {
                    val newState = copy(navigationIcon = event.navigationIcon)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.SetActions -> {
                setState {
                    val newState = copy(actions = event.actions)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.SetScrollable -> {
                setState {
                    val newState = copy(scrollable = event.scrollable)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedTopAppBarDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.show, code = generateCode(this)) }
            }

            is DSWantedTopAppBarDemoEvent.CopyCode -> {
                setEffect { DSWantedTopAppBarDemoSideEffect.CopyCode(viewState.value.code) }
            }
        }
    }

    private fun generateCode(state: DSWantedTopAppBarDemoViewState): String {
        val params = mutableListOf<String>()

        if (state.variant != Variant.Normal) {
            params.add("variant = Variant.${state.variant.name}")
        }

            params.add("background = ${state.background}")

        if (state.titleAlignCenter) {
            params.add("titleAlignCenter = true")
        }

        if (state.scrollable) {
            params.add("scrollableState = rememberScrollState()")
        }

        params.add("title = \"Sample Title\"")

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

        if (state.actions) {
            params.add(
                """actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(R.drawable.ic_normal_share_svg),
                        onClick = { /* Handle action */ }
                    )
                }"""
            )
        }

        val paramString = if (params.isNotEmpty()) {
            "(\n    ${params.joinToString(",\n    ")}\n)"
        } else {
            "()"
        }

        return "WantedTopAppBar$paramString"
    }
}