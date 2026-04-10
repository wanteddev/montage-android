package com.wanted.android.montage.sample.navigations.topbar.search

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoEvent
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoSideEffect
import com.wanted.android.montage.sample.navigations.topbar.search.DSWantedSearchTopAppBarDemoScreenContract.DSWantedSearchTopAppBarDemoViewState
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedSearchTopAppBarDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedSearchTopAppBarDemoEvent, DSWantedSearchTopAppBarDemoViewState, DSWantedSearchTopAppBarDemoSideEffect>() {
    override fun setInitialState() = DSWantedSearchTopAppBarDemoViewState().let { state ->
        state.copy(code = generateCode(state))
    }

    override fun handleEvents(event: DSWantedSearchTopAppBarDemoEvent) {
        when (event) {
            is DSWantedSearchTopAppBarDemoEvent.InitState -> setState { event.viewState }
            is DSWantedSearchTopAppBarDemoEvent.SetSearchText -> {
                setState {
                    val newState = copy(searchText = event.text)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.SetPlaceholder -> {
                setState {
                    val newState = copy(placeholder = event.placeholder)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.SetSize -> {
                setState {
                    val newState = copy(size = event.size)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.SetBackground -> {
                setState {
                    val newState = copy(background = event.background)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.SetEnabled -> {
                setState {
                    val newState = copy(enabled = event.enabled)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.SetActions -> {
                setState {
                    val newState = copy(actions = event.actions)
                    newState.copy(code = generateCode(newState))
                }
            }

            is DSWantedSearchTopAppBarDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.show) }
            }

            is DSWantedSearchTopAppBarDemoEvent.CopyCode -> {
                setEffect { DSWantedSearchTopAppBarDemoSideEffect.CopyCode(viewState.value.code) }
            }
        }
    }

    private fun generateCode(state: DSWantedSearchTopAppBarDemoViewState): String {
        val params = mutableListOf<String>()

        params.add("text = \"${state.searchText}\"")

        if (state.placeholder != "검색어를 입력하세요") {
            params.add("placeholder = \"${state.placeholder}\"")
        }

        val sizeString = when (state.size) {
            is Size.Small -> "Size.Small()"
            is Size.Medium -> "Size.Medium()"
            is Size.Custom -> "Size.Custom()"
        }
        if (state.size !is Size.Medium) {
            params.add("size = $sizeString")
        }

        if (!state.background) {
            params.add("background = colorResource(R.color.transparent)")
        }

        if (!state.enabled) {
            params.add("enabled = false")
        }

        if (state.actions) {
            params.add(
                """actions = {
                    WantedTopAppBarIconButton(
                        variant = Variant.Search,
                        painter = painterResource(R.drawable.ic_normal_share_svg),
                        onClick = { /* Handle action */ }
                    )
                }"""
            )
        }

        params.add("""onClickBack = { /* Handle back */ }""")
        params.add("""onValueChange = { /* Handle text change */ }""")

        val paramString = if (params.isNotEmpty()) {
            "(\n    ${params.joinToString(",\n    ")}\n)"
        } else {
            "()"
        }

        return "WantedSearchTopAppBar$paramString"
    }
}