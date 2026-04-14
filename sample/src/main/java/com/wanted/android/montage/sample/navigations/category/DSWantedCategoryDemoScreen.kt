package com.wanted.android.montage.sample.navigations.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoEvent
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoSideEffect
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoViewEvent
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.category.WantedCategory
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Size
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedCategoryDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedCategoryDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedCategoryDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedCategoryDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedCategoryDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedCategoryDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.ShowCode(true))
            }

            DSWantedCategoryDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.CopyCode)
            }

            is DSWantedCategoryDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedCategoryDemoViewEvent.OnAlternativeChanged -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.SetAlternative(viewEvent.isAlternative))
            }

            is DSWantedCategoryDemoViewEvent.OnHorizontalPaddingChanged -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.SetHorizontalPadding(viewEvent.enabled))
            }

            is DSWantedCategoryDemoViewEvent.OnVerticalPaddingChanged -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.SetVerticalPadding(viewEvent.enabled))
            }

            is DSWantedCategoryDemoViewEvent.OnItemClicked -> {
                viewModel.setEvent(DSWantedCategoryDemoEvent.ToggleItem(viewEvent.item))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedCategoryDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedCategoryDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedCategoryDemoEvent.ShowCode(false))
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
private fun DSWantedCategoryDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedCategoryDemoViewState,
    onViewEvent: (DSWantedCategoryDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedCategory") {
                onViewEvent(DSWantedCategoryDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedCategoryDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedCategoryDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedCategoryDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedCategory(
                    itemList = viewState.itemList,
                    selectedList = viewState.selectedItems,
                    size = viewState.size,
                    horizontalPadding = viewState.horizontalPadding,
                    isVerticalPadding = viewState.verticalPadding,
                    isAlternative = viewState.isAlternative,
                    onClick = { item, _ ->
                        onViewEvent(DSWantedCategoryDemoViewEvent.OnItemClicked(item))
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = listOf("Small", "Medium", "Large", "XLarge"),
                    onSelect = { sizeName ->
                        onViewEvent(
                            DSWantedCategoryDemoViewEvent.OnSizeChanged(getSizeFromName(sizeName))
                        )
                    }
                )
            },
            alternative = {
                DSWantedOptionSwitchCell(
                    text = "alternative : ${viewState.isAlternative}",
                    checkState = viewState.isAlternative,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedCategoryDemoViewEvent.OnAlternativeChanged(checked))
                    }
                )
            },
            horizontalPadding = {
                DSWantedOptionSwitchCell(
                    text = "horizontalPadding : ${viewState.horizontalPadding}",
                    checkState = viewState.horizontalPadding,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedCategoryDemoViewEvent.OnHorizontalPaddingChanged(checked)
                        )
                    }
                )
            },
            verticalPadding = {
                DSWantedOptionSwitchCell(
                    text = "verticalPadding : ${viewState.verticalPadding}",
                    checkState = viewState.verticalPadding,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedCategoryDemoViewEvent.OnVerticalPaddingChanged(checked)
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedCategoryDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    alternative: @Composable () -> Unit,
    horizontalPadding: @Composable () -> Unit,
    verticalPadding: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        DSWantedPreviewContainer {
            preview()
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        size()
        alternative()
        horizontalPadding()
        verticalPadding()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getSizeFromName(name: String): Size {
    return when (name) {
        "Small" -> Size.Small
        "Medium" -> Size.Medium
        "Large" -> Size.Large
        "XLarge" -> Size.XLarge
        else -> Size.Medium
    }
}

@DevicePreviews
@Composable
private fun DSWantedCategoryDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedCategoryDemoScreen(
            onClickBack = {}
        )
    }
}