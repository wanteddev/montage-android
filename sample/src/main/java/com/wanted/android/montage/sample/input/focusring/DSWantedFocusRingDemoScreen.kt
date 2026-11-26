package com.wanted.android.montage.sample.input.focusring

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedCheckerBackgroundOptionCell
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoEvent
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoSideEffect
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoViewEvent
import com.wanted.android.montage.sample.input.focusring.DSWantedFocusRingDemoScreenContract.DSWantedFocusRingDemoViewState
import com.wanted.android.montage.sample.util.demoCheckerBackground
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.framedstyle.focusRing
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextArea
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedFocusRingDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedFocusRingDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedFocusRingDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedFocusRingDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedFocusRingDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedFocusRingDemoViewEvent.OnChangeVisible -> {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.SetVisible(viewEvent.visible))
            }

            is DSWantedFocusRingDemoViewEvent.OnChangeNegative -> {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.SetNegative(viewEvent.negative))
            }

            is DSWantedFocusRingDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.CopyCode)
            }

            is DSWantedFocusRingDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.ShowCode(true))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedFocusRingDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedFocusRingDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }
}

@Composable
private fun DSWantedFocusRingDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedFocusRingDemoViewState,
    onViewEvent: (DSWantedFocusRingDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "focusRing") {
                onViewEvent(DSWantedFocusRingDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedFocusRingDemoViewEvent.OnClickShowCode)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedFocusRingDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                DSWantedFocusRingPreview(visible = viewState.visible, negative = viewState.negative)
            },
            visible = {
                DSWantedOptionSwitchCell(
                    text = "visible : ${viewState.visible}",
                    checkState = viewState.visible,
                    onCheckChanged = {
                        onViewEvent(DSWantedFocusRingDemoViewEvent.OnChangeVisible(it))
                    }
                )
            },
            negative = {
                DSWantedOptionSwitchCell(
                    text = "negative : ${viewState.negative}",
                    checkState = viewState.negative,
                    onCheckChanged = {
                        onViewEvent(DSWantedFocusRingDemoViewEvent.OnChangeNegative(it))
                    }
                )
            },
            undefinedStates = {
                DSWantedUndefinedStates()
            }
        )
    }
}

@Composable
private fun DSWantedFocusRingDemoScreenLayout(
    modifier: Modifier,
    preview: @Composable () -> Unit,
    visible: @Composable () -> Unit,
    negative: @Composable () -> Unit,
    undefinedStates: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        DSWantedDemoSectionTitle(text = "Preview", topPadding = false)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .demoCheckerBackground()
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DSWantedDemoSectionTitle(text = "Option", topPadding = false)

            DSWantedCheckerBackgroundOptionCell()

            visible()

            negative()

            undefinedStates()
        }
    }
}

// framedStyle 없이 1dp 테두리 상자 바깥에 Ring 만 그려 표시 여부·색을 바꿔 본다.
@Composable
private fun DSWantedFocusRingPreview(
    visible: Boolean,
    negative: Boolean
) {
    val shape = RoundedCornerShape(14.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .padding(vertical = 10.dp)
                .size(50.dp)
                .focusRing(
                    visible = visible,
                    shape = shape,
                    color = if (negative) {
                        DesignSystemTheme.colors.lineNegativeFocus
                    } else {
                        DesignSystemTheme.colors.lineBrandFocus
                    }
                )
                .border(width = 1.dp, color = DesignSystemTheme.colors.lineNeutralSecondary, shape = shape)
        )

        DSWantedDemoLabel(
            text = if (negative) "color : lineNegativeFocus · width : 4dp" else "color : lineBrandFocus · width : 4dp"
        )
    }
}

// 포커스 링 시안에 정의가 없는 입력 컴포넌트 상태 조합을 한 화면에 모아, 디자이너가 현재 외형을 확인할 수 있게 한다.
@Composable
private fun DSWantedUndefinedStates() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DSWantedDemoSectionTitle(text = "시안 미정의 상태 - 입력 컴포넌트")

        DSWantedDemoLabel(text = "TextField · error · focused")
        DSWantedUndefinedTextField(error = true, enabled = true, focused = true)
        DSWantedDemoLabel(text = "TextField · error · disabled")
        DSWantedUndefinedTextField(error = true, enabled = false, focused = false)
        DSWantedDemoLabel(text = "TextField · focused · disabled (Ring 미표시)")
        DSWantedUndefinedTextField(error = false, enabled = false, focused = true)

        DSWantedDemoLabel(text = "TextArea · Negative · disabled")
        WantedTextArea(
            modifier = Modifier.fillMaxWidth(),
            value = TextFieldValue("입력값"),
            status = WantedTextAreaDefaults.Status.Negative,
            enabled = false,
            onValueChange = {}
        )

        DSWantedDemoLabel(text = "Select · Negative · focused")
        DSWantedUndefinedSelect(negative = true, enabled = true, focused = true)
        DSWantedDemoLabel(text = "Select · Negative · disabled")
        DSWantedUndefinedSelect(negative = true, enabled = false, focused = false)
        DSWantedDemoLabel(text = "Select · focused · disabled (Ring 미표시)")
        DSWantedUndefinedSelect(negative = false, enabled = false, focused = true)
    }
}

@Composable
private fun DSWantedDemoSectionTitle(text: String, topPadding: Boolean = true) {
    Text(
        modifier = Modifier.padding(top = if (topPadding) 16.dp else 0.dp),
        text = text,
        style = WantedTextStyle(
            colorRes = R.color.foreground_neutral_strong,
            style = DesignSystemTheme.typography.heading2Bold
        )
    )
}

@Composable
private fun DSWantedDemoLabel(text: String) {
    Text(
        text = text,
        style = WantedTextStyle(
            colorRes = R.color.foreground_neutral_secondary,
            style = DesignSystemTheme.typography.label1Regular
        )
    )
}

@Composable
private fun DSWantedUndefinedTextField(
    error: Boolean,
    enabled: Boolean,
    focused: Boolean
) {
    WantedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = TextFieldValue("입력값"),
        placeholder = "",
        error = error,
        enabled = enabled,
        trailingButtonEnabled = false,
        complete = false,
        maxLines = 1,
        minLines = 1,
        maxWordCount = Int.MAX_VALUE,
        enabledOverflowText = false,
        interactionSource = remember { MutableInteractionSource() },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        // 실제 포커스 없이 포커스 외형을 고정해서 보여 준다.
        focused = remember(focused) { mutableStateOf(focused) }
    )
}

@Composable
private fun DSWantedUndefinedSelect(
    negative: Boolean,
    enabled: Boolean,
    focused: Boolean
) {
    WantedSelect(
        modifier = Modifier.fillMaxWidth(),
        selectData = null,
        placeHolder = "선택",
        enabled = enabled,
        selectDataList = emptyList(),
        status = if (negative) WantedSelectDefaults.Status.Negative else WantedSelectDefaults.Status.Normal,
        focused = focused
    )
}

@DevicePreviews
@Composable
private fun DSWantedFocusRingDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedFocusRingDemoScreenContent(
            viewState = DSWantedFocusRingDemoViewState(),
            onViewEvent = { }
        )
    }
}
