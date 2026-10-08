package com.wanted.android.montage.sample.input.select

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoEvent
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoSideEffect
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoViewEvent
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoViewState
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.SelectMode
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoViewModel.Companion.CONFIRM_TEXT
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoViewModel.Companion.DESCRIPTION
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoViewModel.Companion.LABEL
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoViewModel.Companion.PLACE_HOLDER
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoViewModel.Companion.SELECT_VALUE_LIST
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.MultiSelectRender
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.SelectType
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.Size
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedSelectDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedSelectDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedSelectDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedSelectDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedSelectDemoViewEvent.OnClickBack -> onClickBack()

            is DSWantedSelectDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowCode(true))
            }

            is DSWantedSelectDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowAll(true))
            }

            is DSWantedSelectDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.CopyCode)
            }

            is DSWantedSelectDemoViewEvent.OnChangeMode -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetMode(viewEvent.mode))
            }

            is DSWantedSelectDemoViewEvent.OnChangeSize -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedSelectDemoViewEvent.OnChangeRender -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetRender(viewEvent.render))
            }

            is DSWantedSelectDemoViewEvent.OnChangeSelectType -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetSelectType(viewEvent.selectType))
            }

            is DSWantedSelectDemoViewEvent.OnChangeLeadingIcon -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetLeadingIcon(viewEvent.leadingIcon))
            }

            is DSWantedSelectDemoViewEvent.OnChangeConfirmText -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetConfirmText(viewEvent.confirmText))
            }

            is DSWantedSelectDemoViewEvent.OnChangeUseFormControl -> {
                viewModel.setEvent(
                    DSWantedSelectDemoEvent.SetUseFormControl(viewEvent.useFormControl)
                )
            }

            is DSWantedSelectDemoViewEvent.OnChangeFormControlLabel -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetFormControlLabel(viewEvent.label))
            }

            is DSWantedSelectDemoViewEvent.OnChangeFormControlRequired -> {
                viewModel.setEvent(
                    DSWantedSelectDemoEvent.SetFormControlRequired(viewEvent.required)
                )
            }

            is DSWantedSelectDemoViewEvent.OnChangeFormControlDescription -> {
                viewModel.setEvent(
                    DSWantedSelectDemoEvent.SetFormControlDescription(viewEvent.description)
                )
            }

            is DSWantedSelectDemoViewEvent.OnChangeFormControlSize -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetFormControlSize(viewEvent.size))
            }

            is DSWantedSelectDemoViewEvent.OnChangeFormControlLabelPlacement -> {
                viewModel.setEvent(
                    DSWantedSelectDemoEvent.SetFormControlLabelPlacement(viewEvent.labelPlacement)
                )
            }

            is DSWantedSelectDemoViewEvent.OnChangeNegative -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetNegative(viewEvent.negative))
            }

            is DSWantedSelectDemoViewEvent.OnChangeFocused -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetFocused(viewEvent.focused))
            }

            is DSWantedSelectDemoViewEvent.OnChangeEnabled -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetEnabled(viewEvent.enabled))
            }

            is DSWantedSelectDemoViewEvent.OnChangeOverflow -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetOverflow(viewEvent.overflow))
            }

            is DSWantedSelectDemoViewEvent.OnSelectValue -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.SetSelectedValue(viewEvent.value))
            }

            is DSWantedSelectDemoViewEvent.OnSelectValueList -> {
                viewModel.setEvent(
                    DSWantedSelectDemoEvent.SetSelectedValueList(viewEvent.valueList)
                )
            }

            is DSWantedSelectDemoViewEvent.OnDeleteValue -> {
                viewModel.setEvent(DSWantedSelectDemoEvent.DeleteSelectedValue(viewEvent.value))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedSelectDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowCode(false))
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
        WantedPopup(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedSelectDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllSelect(viewState = viewState)
            }
        )
    }
}

@Composable
private fun DSWantedSelectDemoScreenContent(
    viewState: DSWantedSelectDemoViewState,
    modifier: Modifier = Modifier,
    onViewEvent: (DSWantedSelectDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedSelect") {
                onViewEvent(DSWantedSelectDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedSelectDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WantedButton(
                            modifier = Modifier.weight(1f),
                            text = "모든 옵션 보기",
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                onViewEvent(DSWantedSelectDemoViewEvent.OnClickShowAll)
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        DSWantedSelectDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            isMultiSelect = viewState.selectedMode == SelectMode.Multi,
            isUseFormControl = viewState.useFormControl,
            preview = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DSWantedSelectSample(
                        viewState = viewState,
                        onViewEvent = onViewEvent
                    )

                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedSelectDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            },
            mode = {
                DSWantedSelectOption(
                    label = "mode",
                    selectedName = viewState.selectedMode.name,
                    nameList = viewState.mode.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnChangeMode(SelectMode.valueOf(it))
                        )
                    }
                )
            },
            size = {
                DSWantedSelectOption(
                    label = "size",
                    selectedName = viewState.selectedSize.name,
                    nameList = viewState.size.map { it.name },
                    onSelect = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeSize(Size.valueOf(it)))
                    }
                )
            },
            render = {
                DSWantedSelectOption(
                    label = "render",
                    selectedName = viewState.selectedRender.name,
                    nameList = viewState.render.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnChangeRender(
                                MultiSelectRender.valueOf(it)
                            )
                        )
                    }
                )
            },
            selectType = {
                DSWantedSelectOption(
                    label = "selectType",
                    selectedName = viewState.selectedSelectType.name,
                    nameList = viewState.selectType.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnChangeSelectType(SelectType.valueOf(it))
                        )
                    }
                )
            },
            leadingIcon = {
                DSWantedOptionSwitchCell(
                    text = "leadingIcon : ${if (viewState.leadingIcon) "Icon" else null}",
                    checkState = viewState.leadingIcon,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeLeadingIcon(it))
                    }
                )
            },
            confirmText = {
                DSWantedOptionSwitchCell(
                    text = "confirmText : ${if (viewState.confirmText) CONFIRM_TEXT else null}",
                    checkState = viewState.confirmText,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeConfirmText(it))
                    }
                )
            },
            negative = {
                DSWantedOptionSwitchCell(
                    text = "status : ${if (viewState.negative) "Negative" else "Normal"}",
                    checkState = viewState.negative,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeNegative(it))
                    }
                )
            },
            focused = {
                DSWantedOptionSwitchCell(
                    text = "focused : ${viewState.focused}",
                    checkState = viewState.focused,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeFocused(it))
                    }
                )
            },
            enabled = {
                DSWantedOptionSwitchCell(
                    text = "enabled : ${viewState.enabled}",
                    checkState = viewState.enabled,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeEnabled(it))
                    }
                )
            },
            selectedCount = {
                DSWantedSelectOption(
                    label = "selectedCount",
                    selectedName = viewState.selectedValueList.size.toString(),
                    nameList = (0..SELECT_VALUE_LIST.size).map { it.toString() },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnSelectValueList(
                                SELECT_VALUE_LIST.take(it.toInt())
                            )
                        )
                    }
                )
            },
            overflow = {
                DSWantedOptionSwitchCell(
                    text = "overflow : ${viewState.overflow}",
                    checkState = viewState.overflow,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeOverflow(it))
                    }
                )
            },
            useFormControl = {
                DSWantedOptionSwitchCell(
                    text = "WantedFormControl 사용 : ${viewState.useFormControl}",
                    checkState = viewState.useFormControl,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeUseFormControl(it))
                    }
                )
            },
            formControlLabel = {
                DSWantedOptionSwitchCell(
                    text = "formControl.label : ${if (viewState.formControlLabel) LABEL else null}",
                    checkState = viewState.formControlLabel,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeFormControlLabel(it))
                    }
                )
            },
            formControlRequired = {
                DSWantedOptionSwitchCell(
                    text = "formControl.required : ${viewState.formControlRequired}",
                    checkState = viewState.formControlRequired,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeFormControlRequired(it))
                    }
                )
            },
            formControlDescription = {
                DSWantedOptionSwitchCell(
                    text = "formControl.description : " +
                        "${if (viewState.formControlDescription) "String" else null}",
                    checkState = viewState.formControlDescription,
                    onCheckChanged = {
                        onViewEvent(DSWantedSelectDemoViewEvent.OnChangeFormControlDescription(it))
                    }
                )
            },
            formControlSize = {
                DSWantedSelectOption(
                    label = "formControl.size",
                    selectedName = viewState.selectedFormControlSize.name,
                    nameList = viewState.formControlSize.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnChangeFormControlSize(
                                WantedFormControlDefaults.Size.valueOf(it)
                            )
                        )
                    }
                )
            },
            formControlLabelPlacement = {
                DSWantedSelectOption(
                    label = "formControl.labelPlacement",
                    selectedName = viewState.selectedFormControlLabelPlacement.name,
                    nameList = viewState.formControlLabelPlacement.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedSelectDemoViewEvent.OnChangeFormControlLabelPlacement(
                                WantedFormControlDefaults.LabelPlacement.valueOf(it)
                            )
                        )
                    }
                )
            }
        )
    }
}

/** 옵션 선택용 Select 입니다. 데모 화면 자체도 신규 API 로 구성합니다. */
@Composable
private fun DSWantedSelectOption(
    label: String,
    selectedName: String,
    nameList: List<String>,
    onSelect: (String) -> Unit
) {
    WantedSelect(
        modifier = Modifier.fillMaxWidth(),
        value = "$label : $selectedName",
        placeHolder = PLACE_HOLDER,
        enabled = true,
        selectValueList = nameList,
        selectedValue = selectedName,
        onSelect = onSelect
    )
}

@Composable
private fun DSWantedSelectDemoScreenLayout(
    isMultiSelect: Boolean,
    isUseFormControl: Boolean,
    preview: @Composable () -> Unit,
    mode: @Composable () -> Unit,
    size: @Composable () -> Unit,
    render: @Composable () -> Unit,
    selectType: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    confirmText: @Composable () -> Unit,
    negative: @Composable () -> Unit,
    focused: @Composable () -> Unit,
    enabled: @Composable () -> Unit,
    selectedCount: @Composable () -> Unit,
    overflow: @Composable () -> Unit,
    useFormControl: @Composable () -> Unit,
    formControlLabel: @Composable () -> Unit,
    formControlRequired: @Composable () -> Unit,
    formControlDescription: @Composable () -> Unit,
    formControlSize: @Composable () -> Unit,
    formControlLabelPlacement: @Composable () -> Unit,
    modifier: Modifier = Modifier
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

            DSWantedSelectSectionTitle(text = "Option")

            mode()

            size()

            selectType()

            leadingIcon()

            confirmText()

            negative()

            focused()

            enabled()

            // render / overflow 는 다중 선택에서만 의미가 있다.
            // overflow 는 선택값이 한 줄을 넘어야 차이가 보이므로 선택 개수를 바로 바꿀 수 있게 둔다.
            if (isMultiSelect) {
                render()

                selectedCount()

                overflow()
            }

            useFormControl()

            // FormControl 옵션은 래핑을 사용할 때만 조정 가능하므로 구간을 따로 구분한다.
            if (isUseFormControl) {
                DSWantedSelectSectionTitle(text = "FormControl Option")

                formControlLabel()

                formControlRequired()

                formControlDescription()

                formControlSize()

                formControlLabelPlacement()
            }
        }
    }
}

/** 옵션 목록을 구간별로 나누는 섹션 타이틀입니다. */
@Composable
private fun ColumnScope.DSWantedSelectSectionTitle(text: String) {
    Text(
        modifier = Modifier.align(Alignment.Start),
        text = text,
        style = WantedTextStyle(
            colorRes = R.color.foreground_neutral_strong,
            style = DesignSystemTheme.typography.heading2Bold
        )
    )
}

/**
 * 옵션 상태를 반영한 미리보기 한 벌입니다.
 *
 * useFormControl 이 켜져 있으면 라벨·필수 표시·메시지를 담당하는 WantedFormControl 의
 * input 슬롯에 WantedSelect 를 조합한다. 꺼져 있으면 셀렉트 박스 본체만 렌더링한다.
 */
@Composable
private fun DSWantedSelectSample(
    viewState: DSWantedSelectDemoViewState,
    onViewEvent: (DSWantedSelectDemoViewEvent) -> Unit
) {
    val select: @Composable () -> Unit = {
        DSWantedSelectPreviewField(
            viewState = viewState,
            onViewEvent = onViewEvent
        )
    }

    if (!viewState.useFormControl) {
        select()
        return
    }

    WantedFormControl(
        label = if (viewState.formControlLabel) LABEL else "",
        required = viewState.formControlRequired,
        description = if (viewState.formControlDescription) DESCRIPTION else null,
        size = viewState.selectedFormControlSize,
        status = viewState.negative.toFormControlStatus(),
        labelPlacement = viewState.selectedFormControlLabelPlacement,
        enabled = viewState.enabled,
        input = select
    )
}

@Composable
private fun DSWantedSelectPreviewField(
    viewState: DSWantedSelectDemoViewState,
    onViewEvent: (DSWantedSelectDemoViewEvent) -> Unit
) {
    when (viewState.selectedMode) {
        SelectMode.Single -> {
            WantedSelect(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.selectedValue,
                placeHolder = PLACE_HOLDER,
                status = viewState.negative.toSelectStatus(),
                enabled = viewState.enabled,
                selectValueList = SELECT_VALUE_LIST,
                focused = viewState.focused,
                confirmText = if (viewState.confirmText) CONFIRM_TEXT else "",
                selectedValue = viewState.selectedValue,
                selectType = viewState.selectedSelectType,
                size = viewState.selectedSize,
                leadingIcon = leadingIconSlot(viewState.leadingIcon),
                onSelect = {
                    onViewEvent(DSWantedSelectDemoViewEvent.OnSelectValue(it))
                }
            )
        }

        SelectMode.Multi -> {
            WantedSelect(
                modifier = Modifier.fillMaxWidth(),
                valueList = viewState.selectedValueList,
                placeHolder = PLACE_HOLDER,
                status = viewState.negative.toSelectStatus(),
                errorList = if (viewState.negative) {
                    viewState.selectedValueList.take(1)
                } else {
                    emptyList()
                },
                enabled = viewState.enabled,
                selectValueList = SELECT_VALUE_LIST,
                focused = viewState.focused,
                confirmText = if (viewState.confirmText) CONFIRM_TEXT else "",
                overflow = viewState.overflow,
                selectType = viewState.selectedSelectType,
                render = viewState.selectedRender,
                size = viewState.selectedSize,
                leadingIcon = leadingIconSlot(viewState.leadingIcon),
                onDelete = {
                    onViewEvent(DSWantedSelectDemoViewEvent.OnDeleteValue(it))
                },
                onSelectList = { itemList ->
                    onViewEvent(DSWantedSelectDemoViewEvent.OnSelectValueList(itemList))
                }
            )
        }
    }
}

private fun leadingIconSlot(enabled: Boolean): (@Composable () -> Unit)? {
    if (!enabled) return null

    return {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.icon_normal_circle_check_fill),
            tint = colorResource(R.color.foreground_brand_primary),
            contentDescription = ""
        )
    }
}

/** 데모의 negative 스위치를 컴포넌트 Status 로 변환합니다. */
private fun Boolean.toSelectStatus(): WantedSelectDefaults.Status = if (this) {
    WantedSelectDefaults.Status.Negative
} else {
    WantedSelectDefaults.Status.Normal
}

internal fun Boolean.toFormControlStatus(): WantedFormControlDefaults.Status = if (this) {
    WantedFormControlDefaults.Status.Negative
} else {
    WantedFormControlDefaults.Status.Normal
}

/**
 * Size × render × overflow 조합을 한 번에 훑어보기 위한 목록입니다.
 *
 * Large/Medium 사이 padding·radius·typography·Chip 간격 차이를 나란히 비교합니다.
 * 다중 선택은 한 줄을 넘도록 전체 항목을 선택한 상태로 overflow false/true 를 이어 붙입니다.
 */
@Composable
private fun DSWantedAllSelect(viewState: DSWantedSelectDemoViewState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        viewState.size.forEach { size ->
            item {
                WantedFormControl(label = "$size / 단일") {
                    WantedSelect(
                        modifier = Modifier.fillMaxWidth(),
                        value = "$size / 단일",
                        placeHolder = PLACE_HOLDER,
                        status = viewState.negative.toSelectStatus(),
                        enabled = viewState.enabled,
                        selectValueList = SELECT_VALUE_LIST,
                        size = size,
                        leadingIcon = leadingIconSlot(viewState.leadingIcon)
                    )
                }
            }

            viewState.render.forEach { render ->
                listOf(false, true).forEach { overflow ->
                    item {
                        DSWantedAllMultiSelect(
                            viewState = viewState,
                            size = size,
                            render = render,
                            overflow = overflow
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DSWantedAllMultiSelect(
    viewState: DSWantedSelectDemoViewState,
    size: Size,
    render: MultiSelectRender,
    overflow: Boolean
) {
    WantedFormControl(label = "$size / $render / overflow=$overflow") {
        WantedSelect(
            modifier = Modifier.fillMaxWidth(),
            valueList = SELECT_VALUE_LIST,
            placeHolder = PLACE_HOLDER,
            status = viewState.negative.toSelectStatus(),
            errorList = if (viewState.negative) {
                SELECT_VALUE_LIST.take(1)
            } else {
                emptyList()
            },
            enabled = viewState.enabled,
            selectValueList = SELECT_VALUE_LIST,
            overflow = overflow,
            render = render,
            size = size,
            leadingIcon = leadingIconSlot(viewState.leadingIcon)
        )
    }
}

@DevicePreviews
@Composable
private fun DSWantedSelectDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedSelectDemoScreenContent(
            viewState = DSWantedSelectDemoViewState(),
            onViewEvent = { }
        )
    }
}
