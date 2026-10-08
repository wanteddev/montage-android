package com.wanted.android.montage.sample.presentation.popup

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
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoSideEffect
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewEvent
import com.wanted.android.montage.sample.presentation.popup.DSWantedPopupDemoScreenContract.DSWantedPopupDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopupContract.Resize
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopupDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedPopupDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedPopupDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedPopupDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedPopupDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedPopupDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedPopupDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(true))
            }

            DSWantedPopupDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.CopyCode)
            }

            is DSWantedPopupDemoViewEvent.OnShowPopupChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetShowPopup(viewEvent.show))
            }

            is DSWantedPopupDemoViewEvent.OnResizeChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetResize(viewEvent.resize))
            }

            is DSWantedPopupDemoViewEvent.OnUseTopBarChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetUseTopBar(viewEvent.use))
            }

            is DSWantedPopupDemoViewEvent.OnUseActionAreaChanged -> {
                viewModel.setEvent(DSWantedPopupDemoEvent.SetUseActionArea(viewEvent.use))
            }

            is DSWantedPopupDemoViewEvent.OnActionAreaTypeChanged -> {
                viewModel.setEvent(
                    DSWantedPopupDemoEvent.SetActionAreaType(viewEvent.actionAreaType)
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedPopupDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedPopupDemoEvent.ShowCode(false))
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
private fun DSWantedPopupDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedPopupDemoViewState,
    onViewEvent: (DSWantedPopupDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedPopup") {
                onViewEvent(DSWantedPopupDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedPopupDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedPopupDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedPopupDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "팝업 열기",
                    onClick = {
                        onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(true))
                    }
                )
            },
            resize = {
                WantedSelect(
                    value = "resize : ${viewState.resize::class.simpleName}",
                    selectedValue = viewState.resize::class.simpleName ?: "Hug",
                    selectValueList = listOf("Hug", "Fixed"),
                    onSelect = { resizeName ->
                        val resize = when (resizeName) {
                            "Fixed" -> Resize.Fixed(height = 400.dp)
                            else -> Resize.Hug
                        }
                        onViewEvent(DSWantedPopupDemoViewEvent.OnResizeChanged(resize))
                    }
                )
            },
            title = {
                DSWantedOptionSwitchCell(
                    text = "title : ${viewState.useTopBar}",
                    checkState = viewState.useTopBar,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedPopupDemoViewEvent.OnUseTopBarChanged(checked))
                    }
                )
            },
            actionArea = {
                DSWantedOptionSwitchCell(
                    text = "actionArea : ${viewState.useActionArea}",
                    checkState = viewState.useActionArea,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedPopupDemoViewEvent.OnUseActionAreaChanged(checked))
                    }
                )
            },
            actionAreaType = {
                WantedSelect(
                    value = "actionAreaType : ${viewState.actionAreaType.name}",
                    selectedValue = viewState.actionAreaType.name,
                    selectValueList = ActionAreaType.entries.map { it.name },
                    onSelect = { typeName ->
                        val actionAreaType = ActionAreaType.entries
                            .firstOrNull { it.name == typeName }
                            ?: ActionAreaType.Strong
                        onViewEvent(
                            DSWantedPopupDemoViewEvent.OnActionAreaTypeChanged(actionAreaType)
                        )
                    }
                )
            }
        )
    }

    if (viewState.showPopup) {
        WantedPopup(
            onDismissRequest = {
                onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(false))
            },
            resize = viewState.resize,
            popupDefault = WantedPopupDefaults.getDefault(
                actionAreaType = viewState.actionAreaType
            ),
            title = "제목".takeIf { viewState.useTopBar },
            positive = "확인",
            // onClickPositive 가 null 이면 Popup 이 Action Area 를 그리지 않는다.
            onClickPositive = if (viewState.useActionArea) {
                { onViewEvent(DSWantedPopupDemoViewEvent.OnShowPopupChanged(false)) }
            } else {
                null
            },
            content = {
                Text(text = "Popup Content")
            }
        )
    }
}

@Composable
private fun DSWantedPopupDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    resize: @Composable () -> Unit,
    title: @Composable () -> Unit,
    actionArea: @Composable () -> Unit,
    actionAreaType: @Composable () -> Unit,
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
                colorRes = R.color.foreground_neutral_strong,
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
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        resize()
        title()
        actionArea()
        actionAreaType()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@DevicePreviews
@Composable
private fun DSWantedPopupDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedPopupDemoScreen(
            onClickBack = {}
        )
    }
}