package com.wanted.android.montage.sample.input.select

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoEvent
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoSideEffect
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.DSWantedSelectDemoViewState
import com.wanted.android.montage.sample.input.select.DSWantedSelectDemoScreenContract.SelectMode
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.MultiSelectRender
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.SelectType
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults.Size
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedSelectDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedSelectDemoEvent, DSWantedSelectDemoViewState, DSWantedSelectDemoSideEffect>() {
    override fun setInitialState() = DSWantedSelectDemoViewState()

    override fun handleEvents(event: DSWantedSelectDemoEvent) {
        when (event) {
            is DSWantedSelectDemoEvent.CopyCode -> {
                setEffect { DSWantedSelectDemoSideEffect.CopyCode(getCode()) }
            }

            is DSWantedSelectDemoEvent.ShowCode -> {
                setState {
                    copy(
                        code = getCode(),
                        isShowCode = event.isShowCode
                    )
                }
            }

            is DSWantedSelectDemoEvent.ShowAll -> {
                setState { copy(isShowAll = event.isShowAll) }
            }

            is DSWantedSelectDemoEvent.SetMode -> {
                // 모드를 바꾸면 선택 방식 기본값도 함께 맞춘다. (단일 CheckMark / 다중 CheckBox)
                setState {
                    copy(
                        selectedMode = event.mode,
                        selectedSelectType = when (event.mode) {
                            SelectMode.Single -> SelectType.CheckMark
                            SelectMode.Multi -> SelectType.CheckBox
                        }
                    )
                }
            }

            is DSWantedSelectDemoEvent.SetSize -> setState { copy(selectedSize = event.size) }
            is DSWantedSelectDemoEvent.SetRender -> setState { copy(selectedRender = event.render) }
            is DSWantedSelectDemoEvent.SetSelectType -> {
                setState { copy(selectedSelectType = event.selectType) }
            }

            is DSWantedSelectDemoEvent.SetLeadingIcon -> {
                setState { copy(leadingIcon = event.leadingIcon) }
            }

            is DSWantedSelectDemoEvent.SetConfirmText -> {
                setState { copy(confirmText = event.confirmText) }
            }

            is DSWantedSelectDemoEvent.SetUseFormControl -> {
                setState { copy(useFormControl = event.useFormControl) }
            }

            is DSWantedSelectDemoEvent.SetFormControlLabel -> {
                setState { copy(formControlLabel = event.label) }
            }

            is DSWantedSelectDemoEvent.SetFormControlRequired -> {
                setState { copy(formControlRequired = event.required) }
            }

            is DSWantedSelectDemoEvent.SetFormControlDescription -> {
                setState { copy(formControlDescription = event.description) }
            }

            is DSWantedSelectDemoEvent.SetFormControlSize -> {
                setState { copy(selectedFormControlSize = event.size) }
            }

            is DSWantedSelectDemoEvent.SetFormControlLabelPlacement -> {
                setState { copy(selectedFormControlLabelPlacement = event.labelPlacement) }
            }

            is DSWantedSelectDemoEvent.SetNegative -> setState { copy(negative = event.negative) }
            is DSWantedSelectDemoEvent.SetFocused -> setState { copy(focused = event.focused) }
            is DSWantedSelectDemoEvent.SetEnabled -> setState { copy(enabled = event.enabled) }
            is DSWantedSelectDemoEvent.SetOverflow -> setState { copy(overflow = event.overflow) }

            is DSWantedSelectDemoEvent.SetSelectedValue -> {
                setState { copy(selectedValue = event.value) }
            }

            is DSWantedSelectDemoEvent.SetSelectedValueList -> {
                setState { copy(selectedValueList = event.valueList) }
            }

            is DSWantedSelectDemoEvent.DeleteSelectedValue -> {
                setState {
                    copy(selectedValueList = selectedValueList.filterNot { it == event.value })
                }
            }
        }
    }

    private fun getCode(): String {
        val state = viewState.value

        val selectCode = when (state.selectedMode) {
            SelectMode.Single -> getSingleSelectCode(state)
            SelectMode.Multi -> getMultiSelectCode(state)
        }

        if (!state.useFormControl) return selectCode

        // FormControl 로 감쌀 때는 Select 코드를 input 슬롯 깊이만큼 들여쓴다.
        return getFormControlHeaderCode(state) +
            "\n" + selectCode.prependIndent(CODE_INDENT) + "\n}"
    }

    private fun getFormControlHeaderCode(state: DSWantedSelectDemoViewState): String {
        val statusCode = if (state.negative) "Negative" else "Normal"

        val argumentList = listOf(
            "label = ${if (state.formControlLabel) "\"$LABEL\"" else "\"\""}, " +
                getDefaultString(!state.formControlLabel),
            "required = ${state.formControlRequired}, " +
                getDefaultString(!state.formControlRequired),
            "description = ${if (state.formControlDescription) "\"$DESCRIPTION\"" else "null"}, " +
                getDefaultString(!state.formControlDescription),
            "size = WantedFormControlDefaults.Size.${state.selectedFormControlSize}, " +
                getDefaultString(
                    state.selectedFormControlSize == WantedFormControlDefaults.Size.Large
                ),
            "status = WantedFormControlDefaults.Status.$statusCode, " +
                getDefaultString(!state.negative),
            "labelPlacement = WantedFormControlDefaults.LabelPlacement." +
                "${state.selectedFormControlLabelPlacement}, " +
                getDefaultString(
                    state.selectedFormControlLabelPlacement ==
                        WantedFormControlDefaults.LabelPlacement.Top
                ),
            "enabled = ${state.enabled}, ${getDefaultString(state.enabled)}"
        )

        return argumentList.joinToString(
            separator = "\n",
            prefix = "WantedFormControl(\n",
            postfix = "\n) {"
        ) { argument -> argument.trimEnd().prependIndent(CODE_INDENT) }
    }

    private fun getSingleSelectCode(state: DSWantedSelectDemoViewState): String {
        val argumentList = listOf(
            "value = selectedValue, // 선택된 값 상태",
            "placeHolder = \"$PLACE_HOLDER\",",
            "status = Status.${statusName(state)}, ${getDefaultString(!state.negative)}",
            "enabled = ${state.enabled},",
            "selectValueList = selectValueList,",
            "focused = ${state.focused}, ${getDefaultString(!state.focused)}",
            "confirmText = ${state.confirmTextCode}, ${getDefaultString(!state.confirmText)}",
            "selectedValue = selectedValue, ${getDefaultString(true)}",
            "selectType = SelectType.${state.selectedSelectType}, " +
                getDefaultString(state.selectedSelectType == SelectType.CheckMark),
            "size = Size.${state.selectedSize}, " +
                getDefaultString(state.selectedSize == Size.Large)
        ) + getLeadingIconCodeList(state) + "onSelect = { selectedValue = it }"

        return wrapCall(name = "WantedSelect", argumentList = argumentList)
    }

    private fun getMultiSelectCode(state: DSWantedSelectDemoViewState): String {
        val errorCode = if (state.negative) "selectedValueList.take(1)" else "emptyList()"

        val argumentList = listOf(
            "valueList = selectedValueList, // 선택된 값 목록 상태",
            "placeHolder = \"$PLACE_HOLDER\",",
            "status = Status.${statusName(state)}, ${getDefaultString(!state.negative)}",
            "errorList = $errorCode, ${getDefaultString(!state.negative)}",
            "enabled = ${state.enabled},",
            "selectValueList = selectValueList,",
            "focused = ${state.focused}, ${getDefaultString(!state.focused)}",
            "confirmText = ${state.confirmTextCode}, ${getDefaultString(!state.confirmText)}",
            "overflow = ${state.overflow}, ${getDefaultString(!state.overflow)}",
            "selectType = SelectType.${state.selectedSelectType}, " +
                getDefaultString(state.selectedSelectType == SelectType.CheckBox),
            "render = MultiSelectRender.${state.selectedRender}, " +
                getDefaultString(state.selectedRender == MultiSelectRender.Text),
            "size = Size.${state.selectedSize}, " +
                getDefaultString(state.selectedSize == Size.Large)
        ) + getLeadingIconCodeList(state) + listOf(
            "onDelete = { deleted -> selectedValueList = selectedValueList - deleted },",
            "onSelectList = { selectedValueList = it }"
        )

        return wrapCall(name = "WantedSelect", argumentList = argumentList)
    }

    private fun statusName(state: DSWantedSelectDemoViewState) =
        if (state.negative) "Negative" else "Normal"

    private fun getLeadingIconCodeList(state: DSWantedSelectDemoViewState): List<String> {
        if (!state.leadingIcon) {
            return listOf("leadingIcon = null, ${getDefaultString(true)}")
        }

        return listOf(
            "leadingIcon = {",
            "${CODE_INDENT}Icon(",
            "$CODE_INDENT${CODE_INDENT}modifier = Modifier.fillMaxSize(),",
            "$CODE_INDENT${CODE_INDENT}painter = painterResource(" +
                "id = R.drawable.icon_normal_circle_check_fill),",
            "$CODE_INDENT${CODE_INDENT}contentDescription = \"\"",
            "$CODE_INDENT)",
            "},"
        )
    }

    /** 인자 목록을 `이름(\n  인자…\n)` 형태의 호출 코드로 조립합니다. */
    private fun wrapCall(name: String, argumentList: List<String>): String {
        return argumentList.joinToString(
            separator = "\n",
            prefix = "$name(\n",
            postfix = "\n)"
        ) { argument -> argument.trimEnd().prependIndent(CODE_INDENT) }
    }

    private val DSWantedSelectDemoViewState.confirmTextCode: String
        get() = if (confirmText) "\"$CONFIRM_TEXT\"" else "\"\""

    private fun getDefaultString(isDefault: Boolean): String {
        return if (isDefault) {
            "// (default)"
        } else {
            ""
        }
    }

    companion object {
        const val LABEL = "직무"
        const val DESCRIPTION = "메시지에 마침표를 찍어요."
        const val CONFIRM_TEXT = "확인"
        const val PLACE_HOLDER = "선택해 주세요."

        val SELECT_VALUE_LIST = listOf(
            "백엔드 개발자",
            "프론트엔드 개발자",
            "안드로이드 개발자",
            "iOS 개발자",
            "데이터 엔지니어",
            "프로덕트 디자이너"
        )

        private const val CODE_INDENT = "    "
    }
}
