package com.wanted.android.montage.sample.content.card.vertical

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoEvent
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoSideEffect
import com.wanted.android.montage.sample.content.card.vertical.DSWantedCardDemoScreenContract.DSWantedCardDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedCardDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedCardDemoEvent, DSWantedCardDemoViewState, DSWantedCardDemoSideEffect>() {
    override fun setInitialState() = DSWantedCardDemoViewState()

    override fun handleEvents(event: DSWantedCardDemoEvent) {
        when (event) {
            is DSWantedCardDemoEvent.InitState -> setState { event.viewState }
            is DSWantedCardDemoEvent.ShowCode -> showCode(event.isShowCode)
            is DSWantedCardDemoEvent.CopyCode -> copyCode()
            is DSWantedCardDemoEvent.ShowAll -> showAll(event.isShowAll)
            is DSWantedCardDemoEvent.SetOverlayCaption -> {
                setState { copy(overlayCaption = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetBottomContent -> {
                setState { copy(bottomContent = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetCaption -> {
                setState { copy(caption = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetExtraCaption -> {
                setState { copy(extraCaption = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetOverlayToggleIcon -> {
                setState { copy(overlayToggleIcon = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetSubCaption -> {
                setState { copy(subCaption = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetTitle -> {
                setState { copy(title = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetTopContent -> {
                setState { copy(topContent = event.isChecked) }
            }

            is DSWantedCardDemoEvent.SetLoading -> {
                setState { copy(isLoading = event.isChecked) }
            }
        }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun copyCode() {
        setEffect { DSWantedCardDemoSideEffect.CopyCode(getCode()) }
    }

    private fun showAll(isShowAll: Boolean) {
        setState { copy(isShowAll = isShowAll) }
    }

    private fun getCode(): String {
        return """ 
            WantedCard(
                    modifier = modifier.width(152.dp),
                    title = ${if (viewState.value.title) "\"title\"" else "\"\""},
                    overlayCaption =  ${if (viewState.value.overlayCaption) "\"overlayCaption\"" else "\"\""},
                    caption =  ${if (viewState.value.caption) "\"caption\"" else "\"\""},
                    subCaption =  ${if (viewState.value.subCaption) "\"subCaption\"" else "\"\""},
                    extraCaption =  ${if (viewState.value.extraCaption) "\"extraCaption\"" else "\"\""},
                    thumbnail = {
                       GlideImage(
                            modifier = modifier
                                .fillMaxWidth()
                                .aspectRatio(4 / 3f),
                            model = thumbnail,
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                       )
                    },
                    overlayToggleIcon = if (enableOverlayToggleIcon) {
                        {
                            composable
                        }
                    } else null, // (default null)
                    bottomContent =  if (enableBottomContent) {
                        {
                            composable
                        }
                    } else null, // (default null)
                    isLoading = ${viewState.value.isLoading},
                    onClick = { }
                )
        """.trimIndent()
    }
}