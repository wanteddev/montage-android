package com.wanted.android.montage.sample.actions.iconbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewState
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.IconButtonVariant
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackground
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormal
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonOutlined
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSolid
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedIconButtonDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedIconButtonDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedIconButtonDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedIconButtonDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedIconButtonDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedIconButtonDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(true))
            }

            DSWantedIconButtonDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.CopyCode)
            }

            is DSWantedIconButtonDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedIconButtonDemoViewEvent.OnEnabledChanged -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedIconButtonDemoViewEvent.OnVariantChanged -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.SetVariant(viewEvent.variant))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(false))
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
private fun DSWantedIconButtonDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedIconButtonDemoViewState,
    onViewEvent: (DSWantedIconButtonDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedIconButton") {
                onViewEvent(DSWantedIconButtonDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedIconButtonDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedIconButtonDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedIconButtonDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                val iconSize = when (viewState.size) {
                    WantedIconButtonSize.Medium -> 40.dp
                    WantedIconButtonSize.Small -> 32.dp
                }

                when (viewState.variant) {
                    IconButtonVariant.Normal -> WantedIconButtonNormal(
                        modifier = Modifier.size(iconSize),
                        icon = R.drawable.icon_normal_company,
                        enabled = viewState.enabled,
                        onClick = {}
                    )

                    IconButtonVariant.Outlined -> WantedIconButtonOutlined(
                        modifier = Modifier,
                        icon = R.drawable.icon_normal_company,
                        size = viewState.size,
                        enabled = viewState.enabled,
                        onClick = {}
                    )

                    IconButtonVariant.Solid -> WantedIconButtonSolid(
                        modifier = Modifier,
                        icon = R.drawable.icon_normal_company,
                        size = viewState.size,
                        enabled = viewState.enabled,
                        onClick = {}
                    )

                    IconButtonVariant.Background -> WantedIconButtonBackground(
                        modifier = Modifier.size(iconSize),
                        icon = R.drawable.icon_normal_company,
                        enabled = viewState.enabled,
                        onClick = {}
                    )
                }
            },
            variant = {
                WantedSelect(
                    value = "variant : ${viewState.variant.name}",
                    selectedValue = viewState.variant.name,
                    selectValueList = listOf("Normal", "Outlined", "Solid", "Background"),
                    onSelect = { variantName ->
                        onViewEvent(
                            DSWantedIconButtonDemoViewEvent.OnVariantChanged(
                                getVariantFromName(variantName)
                            )
                        )
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = listOf("Medium", "Small"),
                    onSelect = { sizeName ->
                        onViewEvent(
                            DSWantedIconButtonDemoViewEvent.OnSizeChanged(getSizeFromName(sizeName))
                        )
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedIconButtonDemoViewEvent.OnEnabledChanged(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedIconButtonDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    variant: @Composable () -> Unit,
    size: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
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
        variant()
        size()
        enabled()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getSizeFromName(name: String): WantedIconButtonSize {
    return when (name) {
        "Medium" -> WantedIconButtonSize.Medium
        "Small" -> WantedIconButtonSize.Small
        else -> WantedIconButtonSize.Medium
    }
}

private fun getVariantFromName(name: String): IconButtonVariant {
    return when (name) {
        "Normal" -> IconButtonVariant.Normal
        "Outlined" -> IconButtonVariant.Outlined
        "Solid" -> IconButtonVariant.Solid
        "Background" -> IconButtonVariant.Background
        else -> IconButtonVariant.Outlined
    }
}

@DevicePreviews
@Composable
private fun DSWantedIconButtonDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedIconButtonDemoScreen(
            onClickBack = {}
        )
    }
}
