package com.wanted.android.montage.sample.input.textinput.textarea

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.wanted.android.montage.sample.getStateList
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetDescription
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetEnabled
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetEnabledOverflowText
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetEnabledRequiredBadge
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetGraphemeClusterCount
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetLeadingContent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetNegative
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetRightButton
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetTextFieldValue
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetTitle
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.SetTrailingContent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowAll
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowCode
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowMaxLinePicker
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowMaxWordCountPicker
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowMinLinesPicker
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoEvent.ShowSample
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoSideEffect
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoViewEvent
import com.wanted.android.montage.sample.input.textinput.textarea.DSWantedTextAreaDemoScreenContract.DSWantedTextAreaDemoViewState
import com.wanted.android.montage.sample.input.textinput.textarea.before.DSWantedTextAreaSampleModal
import com.wanted.android.montage.sample.toMap
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextArea
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedTextAreaDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedTextAreaDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    val clipboardManager = LocalClipboardManager.current
    val focusRequester = remember { FocusRequester() }

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTextAreaDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }

            DSWantedTextAreaDemoSideEffect.Focus -> {
                focusRequester.requestFocus()
            }
        }
    }


    DSWantedTextAreaDemoScreenImpl(
        modifier = modifier,
        viewState = viewState,
        focusRequester = focusRequester
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedTextAreaDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTextAreaDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(SetEnabled(viewEvent.enabled))
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeEnabledOverflowText -> {
                viewModel.setEvent(
                    SetEnabledOverflowText(viewEvent.enabledOverflowText)
                )
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeEnabledRequiredBadge -> {
                viewModel.setEvent(
                    SetEnabledRequiredBadge(viewEvent.enabledRequiredBadge)
                )
            }


            is DSWantedTextAreaDemoViewEvent.OnChangeRightButton -> {
                viewModel.setEvent(
                    SetRightButton(viewEvent.enabledRightButton)
                )

            }

            is DSWantedTextAreaDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(ShowAll(true))
            }

            is DSWantedTextAreaDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(ShowCode(true))
            }

            is DSWantedTextAreaDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedTextAreaDemoEvent.CopyCode)
            }

            is DSWantedTextAreaDemoViewEvent.OnTextFieldValueChanged -> {
                viewModel.setEvent(SetTextFieldValue(viewEvent.text))
            }

            is DSWantedTextAreaDemoViewEvent.OnShowMaxLinePicker -> {
                viewModel.setEvent(ShowMaxLinePicker(true))
            }

            is DSWantedTextAreaDemoViewEvent.OnShowMaxWordCountPicker -> {
                viewModel.setEvent(
                    ShowMaxWordCountPicker(true)
                )
            }

            is DSWantedTextAreaDemoViewEvent.OnShowMinLinesPicker -> {
                viewModel.setEvent(ShowMinLinesPicker(true))
            }

            is DSWantedTextAreaDemoViewEvent.OnClickShowSample -> {
                viewModel.setEvent(ShowSample(true))
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeTitle -> {
                viewModel.setEvent(SetTitle(viewEvent.title))
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeDescription -> {
                viewModel.setEvent(SetDescription(viewEvent.description))
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeGraphemeClusterCount -> {
                viewModel.setEvent(
                    SetGraphemeClusterCount(viewEvent.isGraphemeClusterCount)
                )
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeLeadingContent -> {
                viewModel.setEvent(
                    SetLeadingContent(viewEvent.leadingContent)
                )
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeNegative -> {
                viewModel.setEvent(SetNegative(viewEvent.negative))
            }

            is DSWantedTextAreaDemoViewEvent.OnChangeTrailingContent -> {
                viewModel.setEvent(
                    SetTrailingContent(viewEvent.trailingContent)
                )
            }

            DSWantedTextAreaDemoViewEvent.OnClickFocus -> {
                viewModel.setEvent(DSWantedTextAreaDemoEvent.Focus)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedTextAreaDemoEvent.CopyCode)
                viewModel.setEvent(ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowCode(false))
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
                viewModel.setEvent(ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowAll(false))
            },
            content = {
                DSWantedAllTextArea(viewState)
            }
        )
    }

    if (viewState.isShowSample) {
        DSWantedTextAreaSampleModal {
            viewModel.setEvent(ShowSample(false))
        }
    }

    if (viewState.isShowMaxLinePicker) {
        DSWantedOptionPicker(
            title = "maxLines",
            selectedValue = viewState.maxLines,
            start = 1,
            end = 2000,
            onSelect = {
                viewModel.setEvent(DSWantedTextAreaDemoEvent.SetMaxLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowMaxLinePicker(false))
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
                viewModel.setEvent(DSWantedTextAreaDemoEvent.SetMinLines(it))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowMinLinesPicker(false))
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
                viewModel.setEvent(DSWantedTextAreaDemoEvent.SetMaxWordCount(it))
            },
            onDismissRequest = {
                viewModel.setEvent(ShowMaxWordCountPicker(false))
            }
        )
    }
}

@Composable
private fun DSWantedTextAreaDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedTextAreaDemoViewState,
    focusRequester: FocusRequester,
    onViewEvent: (DSWantedTextAreaDemoViewEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedTextArea") {
                onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickShowCode)
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
                                onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickShowSample)
                            }
                        )

                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "모든 옵션 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickShowAll)
                            }
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        DSWantedTextAreaDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WantedTextArea(
                        text = viewState.text,
                        placeholder = "텍스트를 입력해 주세요.",
                        title = if (viewState.title) "Title" else "",
                        description = if (viewState.description) "Description" else "",
                        rightButton = if (viewState.rightButton) "확인" else "",
                        leadingContent = if (viewState.leadingContent) {
                            {
                                Icon(
                                    modifier = Modifier.wrapContentSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        trailingContent = if (viewState.trailingContent) {
                            {
                                Icon(
                                    modifier = Modifier.wrapContentSize(),
                                    painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                                    tint = colorResource(R.color.primary_normal),
                                    contentDescription = ""
                                )
                            }
                        } else null,
                        enabled = viewState.enabled,
                        negative = viewState.negative,
                        maxLines = viewState.maxLines,
                        minLines = viewState.minLines,
                        maxWordCount = viewState.maxWordCount,
                        enabledOverflowText = viewState.enabledOverflowText,
                        isGraphemeClusterCount = viewState.isGraphemeClusterCount,
                        focusRequester = focusRequester,
                        requiredBadge = viewState.requiredBadge,
                        onClickRightButton = {},
                        onValueChange = { value ->
                            onViewEvent(DSWantedTextAreaDemoViewEvent.OnTextFieldValueChanged(value))
                        }
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }

            },
            title = {
                DSWantedOptionSwitchCell(
                    text = "title : ${if (viewState.title) "Title" else null}",
                    checkState = viewState.title,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeTitle(it)
                        )
                    }
                )
            },
            description = {
                DSWantedOptionSwitchCell(
                    text = "description : ${if (viewState.description) "String" else null}",
                    checkState = viewState.description,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeDescription(it)
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
                            DSWantedTextAreaDemoViewEvent.OnChangeRightButton(it)
                        )
                    }
                )
            },
            leadingContent = {
                DSWantedOptionSwitchCell(
                    text = "leadingContent : ${viewState.leadingContent}",
                    checkState = viewState.leadingContent,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeLeadingContent(it)
                        )
                    }
                )
            },
            trailingContent = {
                DSWantedOptionSwitchCell(
                    text = "trailingContent : ${viewState.trailingContent}",
                    checkState = viewState.trailingContent,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeTrailingContent(it)
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
                            DSWantedTextAreaDemoViewEvent.OnChangeEnabled(it)
                        )
                    }
                )
            },
            negative = {
                DSWantedOptionSwitchCell(
                    text = "negative : ${viewState.negative}",
                    checkState = viewState.negative,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeNegative(it)
                        )
                    }
                )
            },
            maxLines = {
                WantedSelect(
                    value = "maxLines : ${viewState.maxLines}",
                    focused = viewState.isShowMinLinesPicker,
                    onClick = {
                        onViewEvent(DSWantedTextAreaDemoViewEvent.OnShowMaxLinePicker)
                    }
                )
            },
            minLines = {
                WantedSelect(
                    value = "minLines : ${viewState.minLines}",
                    focused = viewState.isShowMinLinesPicker,
                    onClick = {
                        onViewEvent(DSWantedTextAreaDemoViewEvent.OnShowMinLinesPicker)
                    }
                )
            },
            maxWordCount = {
                WantedSelect(
                    value = "maxWordCount : ${viewState.maxWordCount}",
                    focused = viewState.isShowMaxWordCountPicker,
                    onClick = {
                        onViewEvent(DSWantedTextAreaDemoViewEvent.OnShowMaxWordCountPicker)
                    }
                )
            },
            enabledOverflowText = {
                DSWantedOptionSwitchCell(
                    text = "enabledOverflowText : ${viewState.enabledOverflowText}",
                    checkState = viewState.enabledOverflowText,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeEnabledOverflowText(it)
                        )
                    }
                )
            },
            isGraphemeClusterCount = {
                DSWantedOptionSwitchCell(
                    text = "isGraphemeClusterCount : ${if (viewState.isGraphemeClusterCount) "Icon" else null}",
                    checkState = viewState.isGraphemeClusterCount,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeGraphemeClusterCount(it)
                        )
                    }
                )
            },
            requiredBadge = {
                DSWantedOptionSwitchCell(
                    text = "requiredBadge : ${if (viewState.requiredBadge) "Icon" else null}",
                    checkState = viewState.requiredBadge,
                    onCheckChanged = {
                        onViewEvent(
                            DSWantedTextAreaDemoViewEvent.OnChangeEnabledRequiredBadge(it)
                        )
                    }
                )
            },
            focus = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Focus",
                    onClick = {
                        onViewEvent(DSWantedTextAreaDemoViewEvent.OnClickFocus)
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedTextAreaDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    rightButton: @Composable () -> Unit,
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    negative: @Composable () -> Unit,
    maxLines: @Composable () -> Unit,
    minLines: @Composable () -> Unit,
    maxWordCount: @Composable () -> Unit,
    enabledOverflowText: @Composable () -> Unit,
    isGraphemeClusterCount: @Composable () -> Unit,
    requiredBadge: @Composable () -> Unit,
    focus: @Composable () -> Unit
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

            title()

            description()

            rightButton()

            leadingContent()

            trailingContent()

            enabled()

            negative()

            maxLines()

            minLines()

            maxWordCount()

            enabledOverflowText()

            isGraphemeClusterCount()

            requiredBadge()

            focus()
        }
    }
}


@Composable
private fun DSWantedAllTextArea(
    viewState: DSWantedTextAreaDemoViewState
) {
    val map = viewState.toMap()
    val list = getStateList(
        map = map,
        includeKeyList = listOf(
            "text", "title", "description", "rightButton",
            "leadingContent", "trailingContent", "requiredBadge",
            "enabled", "negative"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(list) { values ->
            WantedTextArea(
                text = values["text"].toString(),
                placeholder = "placeholder",
                title = if (values["title"] as Boolean) "Title" else "",
                description = if (values["description"] as Boolean) "Description" else "",
                rightButton = if (values["rightButton"] as Boolean) "확인" else "",
                leadingContent = if (values["leadingContent"] as Boolean) {
                    {
                        Icon(
                            modifier = Modifier.wrapContentSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                            tint = colorResource(R.color.primary_normal),
                            contentDescription = ""
                        )
                    }
                } else null,
                trailingContent = if (values["trailingContent"] as Boolean) {
                    {
                        Icon(
                            modifier = Modifier.wrapContentSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
                            tint = colorResource(R.color.primary_normal),
                            contentDescription = ""
                        )
                    }
                } else null,
                enabled = values["enabled"] as Boolean,
                negative = values["negative"] as Boolean,
                maxLines = values["maxLines"] as Int,
                minLines = values["minLines"] as Int,
                maxWordCount = values["maxWordCount"] as Int,
                enabledOverflowText = values["enabledOverflowText"] as Boolean,
                isGraphemeClusterCount = values["isGraphemeClusterCount"] as Boolean,
                requiredBadge = values["requiredBadge"] as Boolean,
                onClickRightButton = {},
                onValueChange = { value -> }
            )
        }
    }
}


@DevicePreviews
@Composable
private fun DSWantedTextAreaDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTextAreaDemoScreenImpl(
            viewState = DSWantedTextAreaDemoViewState(),
            focusRequester = FocusRequester(),
            onViewEvent = { }
        )
    }
}
