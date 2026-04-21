package com.wanted.android.montage.sample.content.contentbadge

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
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoEvent
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoSideEffect
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoViewEvent
import com.wanted.android.montage.sample.content.contentbadge.DSWantedContentBadgeDemoScreenContract.DSWantedContentBadgeDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeColor
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeType
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedContentBadgeDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedContentBadgeDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedContentBadgeDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedContentBadgeDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedContentBadgeDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedContentBadgeDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.ShowCode(true))
            }

            is DSWantedContentBadgeDemoViewEvent.OnSelectType -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedContentBadgeDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedContentBadgeDemoViewEvent.OnSelectColor -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.SetColor(viewEvent.color))
            }

            is DSWantedContentBadgeDemoViewEvent.OnChangeLeadingIcon -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.SetLeadingIcon(viewEvent.hasLeadingIcon))
            }

            is DSWantedContentBadgeDemoViewEvent.OnChangeTrailingIcon -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.SetTrailingIcon(viewEvent.hasTrailingIcon))
            }

            is DSWantedContentBadgeDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.CopyCode)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedContentBadgeDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedContentBadgeDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedContentBadgeDemoViewState,
    onViewEvent: (DSWantedContentBadgeDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedContentBadge") {
                onViewEvent(DSWantedContentBadgeDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedContentBadgeDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedContentBadgeDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedContentBadge(
                    text = "Badge",
                    type = viewState.selectedType,
                    size = viewState.selectedSize,
                    color = viewState.selectedColor,
                    leadingDrawable = if (viewState.hasLeadingIcon) {
                        R.drawable.icon_normal_bookmark
                    } else null,
                    trailingDrawable = if (viewState.hasTrailingIcon) {
                        R.drawable.icon_normal_bookmark
                    } else null,
                    onClick = {
                        onViewEvent(DSWantedContentBadgeDemoViewEvent.OnClickCopyCode)
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "Type : ${viewState.selectedType.name}",
                    selectedValue = viewState.selectedType.name,
                    selectValueList = viewState.typeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedContentBadgeDemoViewEvent.OnSelectType(
                                ContentBadgeType.valueOf(
                                    it
                                )
                            )
                        )
                    },
                )
            },
            size = {
                WantedSelect(
                    value = "Size : ${viewState.selectedSize.name}",
                    selectedValue = viewState.selectedSize.name,
                    selectValueList = viewState.sizeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedContentBadgeDemoViewEvent.OnSelectSize(
                                ContentBadgeSize.valueOf(
                                    it
                                )
                            )
                        )
                    },
                )
            },
            color = {
                WantedSelect(
                    value = "Color : ${viewState.selectedColor.name}",
                    selectedValue = viewState.selectedColor.name,
                    selectValueList = viewState.colorList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedContentBadgeDemoViewEvent.OnSelectColor(
                                ContentBadgeColor.valueOf(
                                    it
                                )
                            )
                        )
                    },
                )
            },
            leadingIcon = {
                DSWantedOptionSwitchCell(
                    text = "leadingIcon : ${if (viewState.hasLeadingIcon) "icon" else null}",
                    checkState = viewState.hasLeadingIcon,
                    onCheckChanged = {
                        onViewEvent(DSWantedContentBadgeDemoViewEvent.OnChangeLeadingIcon(it))
                    }
                )
            },
            trailingIcon = {
                DSWantedOptionSwitchCell(
                    text = "trailingIcon : ${if (viewState.hasTrailingIcon) "icon" else null}",
                    checkState = viewState.hasTrailingIcon,
                    onCheckChanged = {
                        onViewEvent(DSWantedContentBadgeDemoViewEvent.OnChangeTrailingIcon(it))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedContentBadgeDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    size: @Composable () -> Unit,
    color: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
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

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Option",
                style = WantedTextStyle(
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            type()

            size()

            color()

            leadingIcon()

            trailingIcon()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedContentBadgeDemoScreenPreview() {
    DesignSystemTheme {
        val viewState = DSWantedContentBadgeDemoViewState()
        DSWantedContentBadgeDemoScreenImpl(
            viewState = viewState,
            onViewEvent = { }
        )
    }
}
