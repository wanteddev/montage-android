package com.wanted.android.montage.sample.content.listcard

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoEvent
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoSideEffect
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedListCardDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedListCardDemoEvent, DSWantedListCardDemoViewState, DSWantedListCardDemoSideEffect>() {
    override fun setInitialState() = DSWantedListCardDemoViewState()

    override fun handleEvents(event: DSWantedListCardDemoEvent) {
        when (event) {
            is DSWantedListCardDemoEvent.InitState -> setState { event.viewState }
            is DSWantedListCardDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedListCardDemoEvent.CopyCode -> copyCode()
            is DSWantedListCardDemoEvent.SetLoading -> setState { copy(isLoading = event.loading) }
            is DSWantedListCardDemoEvent.SetTopContent -> {
                setState { copy(topContentEnabled = event.enabled) }
            }

            is DSWantedListCardDemoEvent.SetBottomContent -> {
                setState { copy(bottomContentEnabled = event.enabled) }
            }

            is DSWantedListCardDemoEvent.SetLeadingContent -> {
                setState { copy(leadingContentEnabled = event.enabled) }
            }

            is DSWantedListCardDemoEvent.SetTrailingContent -> {
                setState { copy(trailingContentEnabled = event.enabled) }
            }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedListCardDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val topContentLine = if (state.topContentEnabled) {
            "topContent = { WantedContentBadge(text = \\\"상단\\\") },"
        } else {
            "topContent = null,"
        }
        val bottomContentLine = if (state.bottomContentEnabled) {
            "bottomContent = { WantedContentBadge(text = \\\"하단\\\") },"
        } else {
            "bottomContent = null,"
        }
        val leadingContentLine = if (state.leadingContentEnabled) {
            "leadingContent = { Icon(painter = painterResource(R.drawable.icon_normal_company), contentDescription = null) },"
        } else {
            "leadingContent = null,"
        }
        val trailingContentLine = if (state.trailingContentEnabled) {
            "trailingContent = { Icon(painter = painterResource(R.drawable.icon_normal_company), contentDescription = null) },"
        } else {
            "trailingContent = null,"
        }

        return """
            WantedListCard(
                modifier = Modifier.fillMaxWidth(),
                title = \"ListCard Title\",
                caption = \"Caption\",
                extraCaption = \"Extra Caption\",
                isLoading = ${state.isLoading},
                $topContentLine
                $bottomContentLine
                $leadingContentLine
                $trailingContentLine
                onClick = { /* on click */ }
            )
        """.trimIndent()
    }
}