package com.wanted.android.montage.sample.input.textinput.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionPicker
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewEvent
import com.wanted.android.montage.sample.input.textinput.textfield.DSWantedTextFieldDemoScreenContract.DSWantedTextFieldDemoViewState
import com.wanted.android.montage.sample.input.textinput.textfield.before.DSWantedTextFieldSampleModal
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.RightVariant
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.Status
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedTextFieldDemoScreen(
    modifier: Modifier,
    viewModel: DSWantedTextFieldDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current
    val focusRequester = remember { FocusRequester() }

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTextFieldDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }

            DSWantedTextFieldDemoSideEffect.Focus -> {
                focusRequester.requestFocus()
            }
        }
    }

    DSWantedTextFieldDemoScreenImpl(
        modifier = modifier,
        viewState = viewState,
        focusRequester = focusRequester
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedTextFieldDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTextFieldDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledLeadingIcon -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledLeadingIcon(viewEvent.enabledLeadingIcon)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledOverflowText -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledOverflowText(viewEvent.enabledOverflowText)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledRequiredBadge -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledRequiredBadge(viewEvent.enabledRequiredBadge)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeEnabledTrailingIcon -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetEnabledTrailingIcon(viewEvent.enabledTrailingIcon)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeRightButton -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetRightButton(viewEvent.enabledRightButton)
                )

            }

            is DSWantedTextFieldDemoViewEvent.OnChangeRightButtonVariant -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetRightButtonVariant(viewEvent.rightButtonVariant)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeRightContent -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.SetRightContent(viewEvent.enabledRightContent)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeStatus -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetStatus(viewEvent.status))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.CopyCode)
            }

            is DSWantedTextFieldDemoViewEvent.OnTextFieldValueChanged -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetTextFieldValue(viewEvent.text))
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMaxLinePicker -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxLinePicker(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMaxWordCountPicker -> {
                viewModel.setEvent(
                    DSWantedTextFieldDemoEvent.ShowMaxWordCountPicker(true)
                )
            }

            is DSWantedTextFieldDemoViewEvent.OnShowMinLinesPicker -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMinLinesPicker(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnClickShowSample -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowSample(true))
            }

            is DSWantedTextFieldDemoViewEvent.OnChangeTitle -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetTitle(viewEvent.title))
            }

            DSWantedTextFieldDemoViewEvent.OnClickFocus -> {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.Focus)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }

    if (viewState.isShowAll) {
        WantedModal(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllTextField(viewState, focusRequester)
            }
        )
    }

    if (viewState.isShowSample) {
        DSWantedTextFieldSampleModal {
            viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowSample(false))
        }
    }

    if (viewState.isShowMaxLinePicker) {
        DSWantedOptionPicker(
            title = "maxLines",
            selectedValue = viewState.maxLines,
            start = 1,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMaxLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxLinePicker(false))
            }
        )
    }

    if (viewState.isShowMinLinesPicker) {
        DSWantedOptionPicker(
            title = "minLines",
            selectedValue = viewState.minLines,
            start = 1,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMinLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMinLinesPicker(false))
            }
        )
    }

    if (viewState.isShowMaxWordCountPicker) {
        DSWantedOptionPicker(
            title = "minLines",
            selectedValue = viewState.maxWordCount,
            start = 1,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.SetMaxWordCount(it))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedTextFieldDemoEvent.ShowMaxWordCountPicker(false))
            }
        )
    }
}

@Composable
private fun DSWantedTextFieldDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedTextFieldDemoViewState,
    focusRequester: FocusRequester,
    onViewEvent: (DSWantedTextFieldDemoViewEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedTextField") {
                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "샘플 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowSample)
                            }
                        )

                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "모든 옵션 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickShowAll)
                            }
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        DSWantedTextFieldDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WantedTextField(
                        text = viewState.text,
                        placeholder = "텍스트를 입력해 주세요.",
                        maxLines = viewState.maxLines,
                        minLines = viewState.minLines,
                        maxWordCount = viewState.maxWordCount,
                        leadingIcon = if (viewState.enabledLeadingIcon) {
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        trailingIcon = if (viewState.enabledTrailingIcon) {
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        rightButton = if (viewState.rightButton) "확인" else null,
                        rightButtonVariant = viewState.selectedRightButtonVariant,
                        trailingContent = if (viewState.rightContent) {
                            {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        status = viewState.selectedStatus,
                        enabled = viewState.enabled,
                        title = if (viewState.title) "Title" else "",
                        requiredBadge = viewState.enabledRequiredBadge,
                        enabledOverflowText = viewState.enabledOverflowText,
                        focusRequester = focusRequester,
                        onValueChange = { value ->
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnTextFieldValueChanged(value))
                        }
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }

            },
            leadingIcon = {
                DSWantedOptionSwitchCell(
                    text = "leadingIcon : ${if (viewState.enabledLeadingIcon) "icon" else null}",
                    checkState = viewState.enabledLeadingIcon,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledLeadingIcon(it)
                        )
                    }
                )
            },
            trailingIcon = {
                DSWantedOptionSwitchCell(
                    text = "trailingIcon : ${if (viewState.enabledTrailingIcon) "icon" else null}",
                    checkState = viewState.enabledTrailingIcon,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledTrailingIcon(it)
                        )
                    }
                )
            },
            rightButton = {
                DSWantedOptionSwitchCell(
                    text = "rightButton : ${if (viewState.rightButton) "String" else null}",
                    checkState = viewState.rightButton,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeRightButton(it)
                        )
                    }
                )
            },
            rightButtonVariant = {
                WantedSelect(
                    value = "Button Shape : ${viewState.selectedRightButtonVariant.name}",
                    selectedValue = viewState.selectedRightButtonVariant.name,
                    selectValueList = viewState.rightButtonVariant.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeRightButtonVariant(
                                RightVariant.valueOf(it)
                            )
                        )
                    },
                )
            },
            rightContent = {
                DSWantedOptionSwitchCell(
                    text = "rightContent : ${if (viewState.rightContent) "Icon" else null}",
                    checkState = viewState.rightContent,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeRightContent(it)
                        )
                    }
                )
            },
            status = {
                WantedSelect(
                    value = "status : ${viewState.selectedStatus.name}",
                    selectedValue = viewState.selectedStatus.name,
                    selectValueList = viewState.status.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeStatus(Status.valueOf(it))
                        )
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabled(it)
                        )
                    }
                )
            },
            title = {
                DSWantedOptionSwitchCell(
                    text = "title : ${if (viewState.title) "Title" else null}",
                    checkState = viewState.title,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeTitle(it)
                        )
                    }
                )
            },
            requiredBadge = {
                DSWantedOptionSwitchCell(
                    text = "requiredBadge : ${if (viewState.enabledRequiredBadge) "Icon" else null}",
                    checkState = viewState.enabledRequiredBadge,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledRequiredBadge(it)
                        )
                    }
                )
            },
            enabledOverflowText = {
                DSWantedOptionSwitchCell(
                    text = "enabledOverflowText : ${viewState.enabledOverflowText}",
                    checkState = viewState.enabledOverflowText,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextFieldDemoViewEvent.OnChangeEnabledOverflowText(it)
                        )
                    }
                )
            },
            maxLines = {
                WantedSelect(
                    value = "maxLines : ${viewState.maxLines}",
                    focused = viewState.isShowMinLinesPicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMaxLinePicker)
                    }
                )
            },
            minLines = {
                WantedSelect(
                    value = "minLines : ${viewState.minLines}",
                    focused = viewState.isShowMinLinesPicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMinLinesPicker)
                    }
                )
            },
            maxWordCount = {
                WantedSelect(
                    value = "maxWordCount : ${viewState.maxWordCount}",
                    focused = viewState.isShowMaxWordCountPicker,
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnShowMaxWordCountPicker)
                    }
                )
            },
            focus = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Focus",
                    onClick = {
                        onViewEvent(DSWantedTextFieldDemoViewEvent.OnClickFocus)
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedTextFieldDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    rightButton: @Composable () -> Unit,
    rightButtonVariant: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    status: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    title: @Composable () -> Unit,
    requiredBadge: @Composable () -> Unit,
    enabledOverflowText: @Composable () -> Unit,
    maxLines: @Composable () -> Unit,
    minLines: @Composable () -> Unit,
    maxWordCount: @Composable () -> Unit,
    focus: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_normal_normal),
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
                    colorRes = R.color.label_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            leadingIcon()

            trailingIcon()

            rightButton()

            rightButtonVariant()

            rightContent()

            status()

            enabled()

            title()

            requiredBadge()

            enabledOverflowText()

            maxLines()

            minLines()

            maxWordCount()

            focus()
        }
    }
}


@Composable
private fun DSWantedAllTextField(
    viewState: DSWantedTextFieldDemoViewState,
    focusRequester: FocusRequester
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        viewState.rightButtonVariant.forEach { buttonVariant ->
            viewState.status.forEach { status ->
                item {
                    WantedTextField(
                        text = "$buttonVariant + $status",
                        placeholder = "텍스트를 입력해 주세요.",
                        leadingIcon = if (viewState.enabledLeadingIcon) {
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        trailingIcon = if (viewState.enabledTrailingIcon) {
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        rightButton = if (viewState.rightButton) "확인" else null,
                        rightButtonVariant = buttonVariant,
                        trailingContent = if (viewState.rightContent) {
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        status = status,
                        enabled = viewState.enabled,
                        requiredBadge = viewState.enabledRequiredBadge,
                        enabledOverflowText = viewState.enabledOverflowText,
                        onValueChange = { value ->
                        },
                        focusRequester = focusRequester
                    )
                }

            }
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedTextFieldDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTextFieldDemoScreenImpl(
            viewState = DSWantedTextFieldDemoViewState(),
            focusRequester = remember { FocusRequester() },
            onViewEvent = { }
        )
    }
}