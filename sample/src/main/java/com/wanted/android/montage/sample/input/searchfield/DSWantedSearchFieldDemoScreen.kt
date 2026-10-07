package com.wanted.android.montage.sample.input.searchfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoSideEffect
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoViewEvent
import com.wanted.android.montage.sample.input.searchfield.DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Size
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults.Variant
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedSearchFieldDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedSearchFieldDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedSearchFieldDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedSearchFieldDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedSearchFieldDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedSearchFieldDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.ShowCode(
                        true
                    )
                )
            }

            DSWantedSearchFieldDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.CopyCode)
            }

            is DSWantedSearchFieldDemoViewEvent.OnTextChanged -> {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.SetText(
                        viewEvent.text
                    )
                )
            }

            is DSWantedSearchFieldDemoViewEvent.OnVariantChanged -> {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.SetVariant(
                        viewEvent.variant
                    )
                )
            }

            is DSWantedSearchFieldDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.SetSize(
                        viewEvent.size
                    )
                )
            }

            is DSWantedSearchFieldDemoViewEvent.OnEnabledChanged -> {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.SetEnabled(
                        viewEvent.enabled
                    )
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.CopyCode)
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.ShowCode(
                        false
                    )
                )
            },
            onDismissRequest = {
                viewModel.setEvent(
                    DSWantedSearchFieldDemoScreenContract.DSWantedSearchFieldDemoEvent.ShowCode(
                        false
                    )
                )
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }
}

@Composable
private fun DSWantedSearchFieldDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedSearchFieldDemoViewState,
    onViewEvent: (DSWantedSearchFieldDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedSearchField") {
                onViewEvent(DSWantedSearchFieldDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedSearchFieldDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedSearchFieldDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->

        DSWantedSearchFieldDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedSearchField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewState.text,
                    placeholder = "검색어를 입력해주세요",
                    enabled = viewState.enabled,
                    variant = viewState.variant,
                    size = viewState.size,
                    onValueChange = { onViewEvent(DSWantedSearchFieldDemoViewEvent.OnTextChanged(it)) },
                )
            },
            variant = {
                WantedSelect(
                    value = "variant : ${viewState.variant.name}",
                    selectedValue = viewState.variant.name,
                    selectValueList = Variant.entries.map { it.name },
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedSearchFieldDemoViewEvent.OnVariantChanged(
                                Variant.valueOf(typeName)
                            )
                        )
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${getSizeName(viewState.size)}",
                    selectedValue = getSizeName(viewState.size),
                    selectValueList = SIZE_LIST.map { getSizeName(it) },
                    onSelect = { sizeName ->
                        val newSize = SIZE_LIST.first { getSizeName(it) == sizeName }
                        onViewEvent(DSWantedSearchFieldDemoViewEvent.OnSizeChanged(newSize))
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedSearchFieldDemoViewEvent.OnEnabledChanged(checked))
                    }
                )
            }

        )

    }
}


@Composable
private fun DSWantedSearchFieldDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    variant: @Composable () -> Unit,
    size: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_neutral_primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            preview()
        }

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Option",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            variant()

            size()

            enabled()

        }
    }
}

private val SIZE_LIST = listOf(Size.Large, Size.Medium)

private fun getSizeName(size: Size): String = when (size) {
    Size.Large -> "Large"
    Size.Medium -> "Medium"
}

@DevicePreviews
@Composable
private fun DSWantedSearchFieldDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedSearchFieldDemoScreenContent(
            viewState = DSWantedSearchFieldDemoViewState(),
            onViewEvent = { }
        )
    }
}
