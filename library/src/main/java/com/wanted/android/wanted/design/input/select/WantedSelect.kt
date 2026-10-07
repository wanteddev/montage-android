package com.wanted.android.wanted.design.input.select

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.framedstyle.focusRing
import com.wanted.android.wanted.design.input.select.view.WantedMultiSelectBottomSheet
import com.wanted.android.wanted.design.input.select.view.WantedMultiSelectContents
import com.wanted.android.wanted.design.input.select.view.WantedSelectBottomSheet
import com.wanted.android.wanted.design.input.select.view.WantedSelectContentLayout
import com.wanted.android.wanted.design.input.select.view.WantedSelectLayout
import com.wanted.android.wanted.design.input.select.view.WantedSelectPlaceHolder
import com.wanted.android.wanted.design.input.textinput.view.ComponentTitle
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect

private const val DEPRECATION_MESSAGE =
    "title·isRequiredBadge·description 을 포함한 오버로드는 WantedFormControl 로 분리되었습니다. " +
        "WantedFormControl(label, required, description) 의 input 슬롯에 " +
        "WantedSelect(selectData = ...) 또는 WantedSelect(selectedDataList = ...) 를 조합해서 사용하세요."

/**
 * WantedSelect
 *
 * 셀렉트 박스 본체만 렌더링하는 단일 선택 컴포넌트입니다.
 *
 * 라벨·필수 표시(*)·하단 메시지는 포함하지 않습니다. 해당 요소가 필요하면
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 * 선택 가능한 항목은 Bottom sheet 로 제공하며, 선택 결과를 콜백으로 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedData by remember { mutableStateOf<WantedSelectData?>(null) }
 * val selectDataList = listOf(
 *     WantedSelectData(id = "1", text = "백엔드"),
 *     WantedSelectData(id = "2", text = "프론트엔드")
 * )
 *
 * WantedFormControl(
 *     label = "직무",
 *     required = true,
 *     description = "직무를 선택해 주세요."
 * ) {
 *     WantedSelect(
 *         selectData = selectedData,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectDataList = selectDataList,
 *         onSelectData = { selectedData = it }
 *     )
 * }
 * ```
 *
 * @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다. null 이거나 text 가 비어 있으면 placeHolder 를 표시합니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
 * @param selectDataList List<WantedSelectData>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
 * @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
 * @param selectedData WantedSelectData?: Bottom sheet 진입 시 체크 표시할 항목입니다.
 * @param bottomSheetType WantedModalContract.ModalType: Bottom sheet 형식입니다. (Flexible, Fixed 등)
 * @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다. enabled 가 false 면 무시됩니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
 * @param onSelectData (WantedSelectData) -> Unit: 항목 선택이 확정됐을 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSelect(
    selectData: WantedSelectData?,
    placeHolder: String,
    enabled: Boolean,
    selectDataList: List<WantedSelectData>,
    modifier: Modifier = Modifier,
    status: WantedSelectDefaults.Status = WantedSelectDefaults.Status.Normal,
    focused: Boolean = false,
    confirmText: String = "",
    selectedData: WantedSelectData? = null,
    bottomSheetType: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckMark,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    onSelectData: (item: WantedSelectData) -> Unit = {}
) {
    val isShowBottomSheetDialog = remember { mutableStateOf(false) }
    val isFocus = remember(focused) { mutableStateOf(focused) }

    WantedSelectContent(
        modifier = modifier,
        negative = status == WantedSelectDefaults.Status.Negative,
        focused = isFocus.value,
        enabled = enabled,
        size = size,
        background = background,
        onClick = {
            isFocus.value = true
            onClick()

            if (selectDataList.isNotEmpty()) {
                isShowBottomSheetDialog.value = true
            }
        },
        leadingIcon = leadingIcon,
        contents = {
            SelectValueContent(
                selectData = selectData,
                placeHolder = placeHolder,
                enabled = enabled,
                size = size
            )
        }
    )

    WantedSelectBottomSheet(
        modifier = Modifier,
        isShow = selectDataList.isNotEmpty() && isShowBottomSheetDialog.value,
        items = selectDataList,
        confirmText = confirmText,
        selectType = selectType,
        bottomSheetType = bottomSheetType,
        selectedItem = selectedData,
        onSelect = { item ->
            isFocus.value = false
            onSelectData(item)
            isShowBottomSheetDialog.value = false
        },
        onDismissRequest = {
            isFocus.value = false
            isShowBottomSheetDialog.value = false
        }
    )
}

/**
 * WantedSelect
 *
 * 셀렉트 박스 본체만 렌더링하는 다중 선택 컴포넌트입니다.
 *
 * 라벨·필수 표시(*)·하단 메시지는 포함하지 않습니다. 해당 요소가 필요하면
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 * 선택된 항목은 render 값에 따라 Chip 또는 텍스트로 표시합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedDataList by remember { mutableStateOf(listOf<WantedSelectData>()) }
 * val selectDataList = listOf(
 *     WantedSelectData(id = "1", text = "개발"),
 *     WantedSelectData(id = "2", text = "디자인")
 * )
 *
 * WantedFormControl(
 *     label = "관심 분야",
 *     required = true
 * ) {
 *     WantedSelect(
 *         selectedDataList = selectedDataList,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectDataList = selectDataList,
 *         render = WantedSelectDefaults.MultiSelectRender.Chip,
 *         onDeleteData = { item -> selectedDataList = selectedDataList - item },
 *         onSelectDataList = { selectedDataList = it }
 *     )
 * }
 * ```
 *
 * @param selectedDataList List<WantedSelectData>: 현재 선택된 항목 리스트입니다. 비어 있으면 placeHolder 를 표시합니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
 * @param selectDataList List<WantedSelectData>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
 * @param errorDataList List<WantedSelectData>: 오류로 표시할 항목 리스트입니다. 포함된 Chip 이 negative 스타일로 표시됩니다. 컨테이너 상태는 status 가 결정합니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
 * @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목을 토글할 때마다 즉시 반영되고 Bottom sheet 가 열린 상태로 유지됩니다.
 * @param overflow Boolean: 선택 항목이 가로로 넘칠 때 줄바꿈할지 여부입니다. false 면 가로 스크롤로 표시합니다.
 * @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
 * @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다. (Chip, Text)
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다. Chip 가로 스크롤 시 gradient 색상으로도 사용됩니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
 * @param onDeleteData (WantedSelectData) -> Unit: Chip 의 삭제 버튼을 눌렀을 때 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
 * @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택이 반영됐을 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSelect(
    selectedDataList: List<WantedSelectData>,
    placeHolder: String,
    enabled: Boolean,
    selectDataList: List<WantedSelectData>,
    modifier: Modifier = Modifier,
    status: WantedSelectDefaults.Status = WantedSelectDefaults.Status.Normal,
    errorDataList: List<WantedSelectData> = emptyList(),
    focused: Boolean = false,
    confirmText: String = "",
    overflow: Boolean = false,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckBox,
    render: WantedSelectDefaults.MultiSelectRender = WantedSelectDefaults.MultiSelectRender.Text,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    leadingIcon: @Composable (() -> Unit)? = null,
    onDeleteData: (WantedSelectData) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectDataList: (itemList: List<WantedSelectData>) -> Unit = {}
) {
    val isShowBottomSheetDialog = remember { mutableStateOf(false) }
    val isFocus = remember(focused) { mutableStateOf(focused) }

    CompositionLocalProvider(LocalWantedSelectBackground.provides(background)) {
        WantedSelectContent(
            modifier = modifier,
            negative = status == WantedSelectDefaults.Status.Negative,
            focused = isFocus.value,
            enabled = enabled,
            overflow = overflow,
            size = size,
            render = render,
            background = background,
            onClick = {
                isFocus.value = true
                onClick()

                if (selectDataList.isNotEmpty()) {
                    isShowBottomSheetDialog.value = true
                }
            },
            leadingIcon = leadingIcon,
            contents = {
                WantedMultiSelectContents(
                    modifier = Modifier.fillMaxWidth(),
                    valueList = selectedDataList,
                    placeHolder = placeHolder,
                    errorList = errorDataList,
                    overflow = overflow,
                    enabled = enabled,
                    render = render,
                    size = size,
                    onDelete = onDeleteData
                )
            }
        )
    }

    WantedMultiSelectBottomSheet(
        modifier = Modifier,
        isShow = selectDataList.isNotEmpty() && isShowBottomSheetDialog.value,
        items = selectDataList,
        confirmText = confirmText,
        selectType = selectType,
        selectedItemList = selectedDataList,
        onSelect = { itemList ->
            onSelectDataList(itemList)

            // 확인 버튼이 있을 때만 확정 시점에 시트를 닫는다.
            // confirmText 가 비어 있으면 즉시 반영 모드이므로 계속 토글할 수 있도록 열어 둔다.
            if (confirmText.isNotEmpty()) {
                isFocus.value = false
                isShowBottomSheetDialog.value = false
            }
        },
        onDismissRequest = {
            isShowBottomSheetDialog.value = false
            isFocus.value = false
        }
    )
}

/**
 * WantedSelect
 *
 * 셀렉트 박스 본체만 렌더링하는 문자열 기반 단일 선택 컴포넌트입니다.
 *
 * 항목이 텍스트뿐이라 WantedSelectData 가 필요 없을 때 사용하는 편의 오버로드입니다.
 * 내부적으로 문자열을 `WantedSelectData(text = it)` 로 변환해 WantedSelect(selectData = ...) 에 위임합니다.
 * 라벨·필수 표시(*)·하단 메시지가 필요하면 WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedValue by remember { mutableStateOf("") }
 *
 * WantedFormControl(label = "직무", required = true) {
 *     WantedSelect(
 *         value = selectedValue,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectValueList = listOf("백엔드", "프론트엔드", "디자이너"),
 *         onSelect = { selectedValue = it }
 *     )
 * }
 * ```
 *
 * @param value String: 화면에 표시할 현재 선택된 값입니다. 비어 있으면 placeHolder 를 표시합니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
 * @param selectValueList List<String>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
 * @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
 * @param selectedValue String?: Bottom sheet 진입 시 체크 표시할 항목입니다.
 * @param bottomSheetType WantedModalContract.ModalType: Bottom sheet 형식입니다. (Flexible, Fixed 등)
 * @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다. enabled 가 false 면 무시됩니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
 * @param onSelect (String) -> Unit: 항목 선택이 확정됐을 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSelect(
    value: String,
    placeHolder: String,
    enabled: Boolean,
    selectValueList: List<String>,
    modifier: Modifier = Modifier,
    status: WantedSelectDefaults.Status = WantedSelectDefaults.Status.Normal,
    focused: Boolean = false,
    confirmText: String = "",
    selectedValue: String? = null,
    bottomSheetType: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckMark,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    onSelect: (item: String) -> Unit = {}
) {
    WantedSelect(
        modifier = modifier,
        selectData = WantedSelectData(text = value),
        placeHolder = placeHolder,
        status = status,
        enabled = enabled,
        selectDataList = selectValueList.map { WantedSelectData(text = it) },
        focused = focused,
        confirmText = confirmText,
        selectedData = selectedValue?.let { WantedSelectData(text = it) },
        bottomSheetType = bottomSheetType,
        selectType = selectType,
        size = size,
        background = background,
        leadingIcon = leadingIcon,
        onClick = onClick,
        onSelectData = { item -> onSelect(item.text) }
    )
}

/**
 * WantedSelect
 *
 * 셀렉트 박스 본체만 렌더링하는 문자열 기반 다중 선택 컴포넌트입니다.
 *
 * 항목이 텍스트뿐이라 WantedSelectData 가 필요 없을 때 사용하는 편의 오버로드입니다.
 * 내부적으로 문자열을 `WantedSelectData(text = it)` 로 변환해 WantedSelect(selectedDataList = ...) 에 위임합니다.
 * 라벨·필수 표시(*)·하단 메시지가 필요하면 WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * `List<WantedSelectData>` 오버로드와 JVM 시그니처가 겹치므로 @JvmName 으로 이름을 구분합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedValueList by remember { mutableStateOf(listOf<String>()) }
 *
 * WantedFormControl(label = "기술 스택") {
 *     WantedSelect(
 *         valueList = selectedValueList,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectValueList = listOf("Kotlin", "Java", "Swift"),
 *         render = WantedSelectDefaults.MultiSelectRender.Chip,
 *         onDelete = { deleted -> selectedValueList = selectedValueList - deleted },
 *         onSelectList = { selectedValueList = it }
 *     )
 * }
 * ```
 *
 * @param valueList List<String>: 현재 선택된 항목 리스트입니다. 비어 있으면 placeHolder 를 표시합니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
 * @param selectValueList List<String>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
 * @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
 * @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
 * @param errorList List<String>: 오류로 표시할 항목 리스트입니다. 포함된 Chip 이 negative 스타일로 표시됩니다. 컨테이너 상태는 status 가 결정합니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
 * @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목을 토글할 때마다 즉시 반영되고 Bottom sheet 가 열린 상태로 유지됩니다.
 * @param overflow Boolean: 선택 항목이 가로로 넘칠 때 줄바꿈할지 여부입니다. false 면 가로 스크롤로 표시합니다.
 * @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
 * @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다. (Chip, Text)
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다. Chip 가로 스크롤 시 gradient 색상으로도 사용됩니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
 * @param onDelete (String) -> Unit: Chip 의 삭제 버튼을 눌렀을 때 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
 * @param onSelectList (List<String>) -> Unit: 항목 선택이 반영됐을 때 호출되는 콜백입니다.
 */
@JvmName("WantedSelectWithValueList")
@Composable
fun WantedSelect(
    valueList: List<String>,
    placeHolder: String,
    enabled: Boolean,
    selectValueList: List<String>,
    modifier: Modifier = Modifier,
    status: WantedSelectDefaults.Status = WantedSelectDefaults.Status.Normal,
    errorList: List<String> = emptyList(),
    focused: Boolean = false,
    confirmText: String = "",
    overflow: Boolean = false,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckBox,
    render: WantedSelectDefaults.MultiSelectRender = WantedSelectDefaults.MultiSelectRender.Text,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    leadingIcon: @Composable (() -> Unit)? = null,
    onDelete: (String) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectList: (itemList: List<String>) -> Unit = {}
) {
    WantedSelect(
        modifier = modifier,
        selectedDataList = valueList.map { WantedSelectData(text = it) },
        placeHolder = placeHolder,
        status = status,
        errorDataList = errorList.map { WantedSelectData(text = it) },
        enabled = enabled,
        selectDataList = selectValueList.map { WantedSelectData(text = it) },
        focused = focused,
        confirmText = confirmText,
        overflow = overflow,
        selectType = selectType,
        render = render,
        size = size,
        background = background,
        leadingIcon = leadingIcon,
        onDeleteData = { item -> onDelete(item.text) },
        onClick = onClick,
        onSelectDataList = { itemList -> onSelectList(itemList.map { it.text }) }
    )
}

/**
 * WantedSelect
 *
 * (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 문자열 기반 단일 선택 컴포넌트입니다.
 *
 * title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectData = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * WantedFormControl(
 *     label = "직무",
 *     required = true,
 *     description = "메시지에 마침표를 찍어요.",
 *     status = WantedFormControlDefaults.Status.Negative
 * ) {
 *     WantedSelect(
 *         selectData = WantedSelectData(text = selectedValue),
 *         placeHolder = "선택해 주세요.",
 *         status = WantedSelectDefaults.Status.Negative,
 *         enabled = true,
 *         selectDataList = selectValueList.map { WantedSelectData(text = it) },
 *         onSelectData = { selectedValue = it.text }
 *     )
 * }
 * ```
 *
 * @param value String: 선택된 현재 값입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시할 부가 설명입니다.
 * @param placeHolder String: 선택 전 표시될 플레이스홀더입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
 * @param isRequiredBadge Boolean: 제목 옆에 필수 표시 뱃지를 보여줄지 여부입니다.
 * @param negative Boolean: 오류 상태 여부입니다.
 * @param focused Boolean: 포커스 시 테두리 강조 여부입니다.
 * @param enabled Boolean: 활성화 여부입니다.
 * @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
 * @param selectedValue String?: 초기 선택된 항목입니다.
 * @param bottomSheetType WantedModalContract.ModalType: BottomSheet 형식입니다.
 * @param selectType WantedSelectDefaults.SelectType: 선택 UI 타입입니다.
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 배경 색상입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 * @param onSelect (String) -> Unit: 선택 완료 시 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 왼쪽 아이콘 슬롯입니다.
 */
@Deprecated(message = DEPRECATION_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedSelect(
    value: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    placeHolder: String = "",
    confirmText: String = "",
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    selectValueList: List<String> = emptyList(),
    selectedValue: String? = null,
    bottomSheetType: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckMark,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    onClick: () -> Unit = {},
    onSelect: (item: String) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectLabeled(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        negative = negative,
        enabled = enabled,
        select = {
            WantedSelect(
                selectData = WantedSelectData(text = value),
                placeHolder = placeHolder,
                status = negative.toSelectStatus(),
                enabled = enabled,
                selectDataList = selectValueList.map { WantedSelectData(text = it) },
                focused = focused,
                confirmText = confirmText,
                selectedData = selectedValue?.let { WantedSelectData(text = it) },
                bottomSheetType = bottomSheetType,
                selectType = selectType,
                size = size,
                background = background,
                leadingIcon = leadingIcon,
                onClick = onClick,
                onSelectData = { item -> onSelect(item.text) }
            )
        }
    )
}

/**
 * WantedSelect
 *
 * (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 WantedSelectData 기반 단일 선택 컴포넌트입니다.
 *
 * title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectData = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * WantedFormControl(label = "직무", required = true) {
 *     WantedSelect(
 *         selectData = selectedData,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectDataList = selectDataList,
 *         onSelectData = { selectedData = it }
 *     )
 * }
 * ```
 *
 * @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 우측에 필수 뱃지를 표시할지 여부입니다.
 * @param negative Boolean: 오류 상태 여부입니다.
 * @param focused Boolean: 포커스 상태 여부입니다.
 * @param enabled Boolean: 컴포넌트 활성화 여부입니다.
 * @param selectDataList List<WantedSelectData>: 선택 가능한 항목 리스트입니다.
 * @param selectedData WantedSelectData?: 초기 선택된 항목입니다.
 * @param bottomSheetType WantedModalContract.ModalType: BottomSheet 형식입니다.
 * @param selectType WantedSelectDefaults.SelectType: 항목 선택 방식입니다.
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectData (WantedSelectData) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측에 표시할 커스텀 아이콘 슬롯입니다.
 */
@Deprecated(message = DEPRECATION_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedSelect(
    selectData: WantedSelectData?,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    selectDataList: List<WantedSelectData> = emptyList(),
    selectedData: WantedSelectData? = null,
    bottomSheetType: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckMark,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    onClick: () -> Unit = {},
    onSelectData: (item: WantedSelectData) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectLabeled(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        negative = negative,
        enabled = enabled,
        select = {
            WantedSelect(
                selectData = selectData,
                placeHolder = placeHolder,
                status = negative.toSelectStatus(),
                enabled = enabled,
                selectDataList = selectDataList,
                focused = focused,
                confirmText = confirmText,
                selectedData = selectedData,
                bottomSheetType = bottomSheetType,
                selectType = selectType,
                size = size,
                background = background,
                leadingIcon = leadingIcon,
                onClick = onClick,
                onSelectData = onSelectData
            )
        }
    )
}

/**
 * WantedSelect
 *
 * (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 WantedSelectData 기반 다중 선택 컴포넌트입니다.
 *
 * title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectedDataList = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 * negativeDataList 는 errorDataList 로 이름이 바뀌었습니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * WantedFormControl(label = "관심 분야", required = true) {
 *     WantedSelect(
 *         selectedDataList = selectedDataList,
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectDataList = selectDataList,
 *         onDeleteData = { item -> selectedDataList = selectedDataList - item },
 *         onSelectDataList = { selectedDataList = it }
 *     )
 * }
 * ```
 *
 * @param selectedDataList List<WantedSelectData>: 현재 선택된 항목 리스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
 * @param placeHolder String: 선택 전 표시될 안내 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 오른쪽에 필수 입력 뱃지를 표시할지 여부입니다.
 * @param negativeDataList List<WantedSelectData>: 오류 표시를 위한 항목 리스트입니다.
 * @param focused Boolean: 포커스 강조 상태 여부입니다.
 * @param enabled Boolean: 선택 가능 여부입니다.
 * @param overflow Boolean: 선택 항목이 넘칠 경우 줄바꿈 처리할지 여부입니다.
 * @param selectDataList List<WantedSelectData>: 선택 가능한 전체 항목 리스트입니다.
 * @param selectType WantedSelectDefaults.SelectType: 선택 UI 타입입니다.
 * @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다.
 * @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onDeleteData (WantedSelectData) -> Unit: 선택된 항목을 삭제할 때 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 셀렉트 박스 왼쪽에 표시할 커스텀 아이콘 슬롯입니다.
 */
@Deprecated(message = DEPRECATION_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedSelect(
    selectedDataList: List<WantedSelectData>,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negativeDataList: List<WantedSelectData> = emptyList(),
    focused: Boolean = false,
    enabled: Boolean = true,
    overflow: Boolean = false,
    selectDataList: List<WantedSelectData> = emptyList(),
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckBox,
    render: WantedSelectDefaults.MultiSelectRender = WantedSelectDefaults.MultiSelectRender.Text,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    onDeleteData: (WantedSelectData) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectDataList: (itemList: List<WantedSelectData>) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectLabeled(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        negative = negativeDataList.isNotEmpty(),
        enabled = enabled,
        select = {
            WantedSelect(
                selectedDataList = selectedDataList,
                placeHolder = placeHolder,
                status = negativeDataList.isNotEmpty().toSelectStatus(),
                errorDataList = negativeDataList,
                enabled = enabled,
                selectDataList = selectDataList,
                focused = focused,
                confirmText = confirmText,
                overflow = overflow,
                selectType = selectType,
                render = render,
                size = size,
                background = background,
                leadingIcon = leadingIcon,
                onDeleteData = onDeleteData,
                onClick = onClick,
                onSelectDataList = onSelectDataList
            )
        }
    )
}

/**
 * WantedSelectWithString
 *
 * (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 문자열 리스트 기반 다중 선택 컴포넌트입니다.
 *
 * title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
 * 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectedDataList = ...) 를
 * WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
 * 문자열은 `WantedSelectData(text = it)` 로 변환해서 전달합니다.
 *
 * 사용 예시:
 * ```kotlin
 * // 대체 방식
 * WantedFormControl(label = "기술 스택") {
 *     WantedSelect(
 *         selectedDataList = selectedValueList.map { WantedSelectData(text = it) },
 *         placeHolder = "선택해 주세요.",
 *         enabled = true,
 *         selectDataList = selectValueList.map { WantedSelectData(text = it) },
 *         onDeleteData = { item -> selectedValueList = selectedValueList - item.text },
 *         onSelectDataList = { itemList -> selectedValueList = itemList.map { it.text } }
 *     )
 * }
 * ```
 *
 * @param selectedValueList List<String>: 현재 선택된 문자열 항목 리스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼에 표시할 텍스트입니다. 비워두면 즉시 반영됩니다.
 * @param placeHolder String: 아무 항목도 선택되지 않았을 때 표시되는 안내 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 옆 필수 뱃지를 표시할지 여부입니다.
 * @param negativeList List<String>: 오류로 표시할 항목 리스트입니다.
 * @param focused Boolean: 포커스 상태 여부입니다.
 * @param enabled Boolean: 활성화 여부입니다.
 * @param overflow Boolean: Chip 렌더링 시 줄바꿈 여부입니다.
 * @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
 * @param selectType WantedSelectDefaults.SelectType: 선택 방식입니다.
 * @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 렌더링 형태입니다.
 * @param size WantedSelectDefaults.Size: 컴포넌트 크기입니다. (Large, Medium)
 * @param background Color: 컴포넌트 배경 색상입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 왼쪽에 표시할 선택적 아이콘 슬롯입니다.
 * @param onDelete (String) -> Unit: 선택된 항목에서 삭제 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 영역 클릭 시 호출되는 콜백입니다.
 * @param onSelectList (List<String>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 */
@Deprecated(message = DEPRECATION_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedSelectWithString(
    selectedValueList: List<String>,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negativeList: List<String> = emptyList(),
    focused: Boolean = false,
    enabled: Boolean = true,
    overflow: Boolean = false,
    selectValueList: List<String> = emptyList(),
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckBox,
    render: WantedSelectDefaults.MultiSelectRender = WantedSelectDefaults.MultiSelectRender.Text,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    background: Color = colorResource(id = R.color.effect_transparent_secondary),
    leadingIcon: (@Composable (() -> Unit))? = null,
    onDelete: (String) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectList: (itemList: List<String>) -> Unit = {}
) {
    WantedSelectLabeled(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        negative = negativeList.isNotEmpty(),
        enabled = enabled,
        select = {
            WantedSelect(
                selectedDataList = selectedValueList.map { WantedSelectData(text = it) },
                placeHolder = placeHolder,
                status = negativeList.isNotEmpty().toSelectStatus(),
                errorDataList = negativeList.map { WantedSelectData(text = it) },
                enabled = enabled,
                selectDataList = selectValueList.map { WantedSelectData(text = it) },
                focused = focused,
                confirmText = confirmText,
                overflow = overflow,
                selectType = selectType,
                render = render,
                size = size,
                background = background,
                leadingIcon = leadingIcon,
                onDeleteData = { onDelete(it.text) },
                onClick = onClick,
                onSelectDataList = { itemList -> onSelectList(itemList.map { it.text }) }
            )
        }
    )
}

// 선택된 값 또는 플레이스홀더를 렌더링합니다.
//
// 값이 없을 때는 placeholder 스타일로, 있을 때는 입력 값 스타일로 표시합니다.
@Composable
private fun SelectValueContent(
    selectData: WantedSelectData?,
    placeHolder: String,
    enabled: Boolean,
    size: WantedSelectDefaults.Size
) {
    if (selectData?.text.isNullOrEmpty()) {
        WantedSelectPlaceHolder(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = placeHolder,
            enabled = enabled,
            size = size
        )
        return
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        text = selectData.text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = size.inputTextStyle,
        color = if (enabled) {
            DesignSystemTheme.colors.foregroundNeutralPrimary
        } else {
            DesignSystemTheme.colors.foregroundNeutralTertiary
        }
    )
}

// (Deprecated 경로 전용) 제목·본체·메시지를 세로로 배치합니다.
//
// 신규 API 는 WantedFormControl 이 이 역할을 담당하므로 이 레이아웃을 사용하지 않습니다.
@Composable
private fun WantedSelectLabeled(
    select: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    enabled: Boolean = true
) {
    WantedSelectLayout(
        modifier = modifier,
        title = title?.let {
            {
                ComponentTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    isRequiredBadge = isRequiredBadge
                )
            }
        },
        select = select,
        description = description?.let {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && negative -> DesignSystemTheme.colors.foregroundNegativePrimary
                        else -> DesignSystemTheme.colors.foregroundNeutralTertiary
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}

// 셀렉트 박스 본체입니다.
//
// Focus ring · border · 배경 · 클릭 인터랙션과 Content 레이아웃을 담당합니다.
//
// overflow=true 이면 선택 항목이 여러 줄로 늘어나므로, leading content · 렌더 요소 · chevron 을
// 세로 가운데가 아니라 상단 기준으로 정렬합니다.
@Composable
private fun WantedSelectContent(
    background: Color,
    contents: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    overflow: Boolean = false,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    render: WantedSelectDefaults.MultiSelectRender = WantedSelectDefaults.MultiSelectRender.Text,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(size.borderRadius)

    WantedSelectContentLayout(
        modifier = modifier
            // 외부 4px Ring 은 focus 상태에서만 그린다. Negative 는 red(foregroundNegativePrimary 12%) ring,
            // 그 외에는 primary(lineBrandFocus) ring.
            .focusRing(
                visible = enabled && focused,
                shape = shape,
                color = if (negative) {
                    DesignSystemTheme.colors.foregroundNegativePrimary.copy(alpha = OPACITY_12)
                } else {
                    DesignSystemTheme.colors.lineBrandFocus
                }
            )
            // 기본 높이를 최소 높이로 두고, 더 큰 텍스트·콘텐츠에서 높이가 확장되도록 합니다.
            .defaultMinSize(minHeight = size.minHeight)
            // Modifier.border 는 drawContent() 뒤에 자기 stroke 를 그리므로 먼저 선언한 border 가 위에 얹힌다.
            // 반투명 상태 색 border 를 먼저(위), underlay border 를 나중(아래)에 선언해야
            // 상태 색 border 가 underlay 에 덮이지 않고 지정 opacity 로 렌더된다.
            .border(
                shape = shape,
                color = containerBorderColor(
                    enabled = enabled,
                    negative = negative,
                    focused = focused
                ),
                width = 1.dp
            )
            .border(
                shape = shape,
                color = containerUnderlayBorderColor(
                    enabled = enabled,
                    negative = negative,
                    focused = focused
                ),
                // 상태 색 border 와 같은 굵기(1dp)여야 한다. 굵기가 다르면 underlay 가 비쳐 두 겹으로 보인다.
                width = 1.dp
            )
            .clip(shape)
            .background(
                if (enabled) {
                    background
                } else {
                    DesignSystemTheme.colors.surfaceNeutralTertiary
                }
            )
            .clickOnce(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = getSelectRippleEffect(enabled = enabled)
            ) {
                onClick()
            }
            // overflow=true 로 여러 줄이 되면 최소 높이가 아니라 콘텐츠가 높이를 정하므로, containerPadding 만으로는
            // 한 줄(기본 높이)일 때보다 상하 여백이 작아진다. 상하는 기본 높이에서의 여백과 같게 둔다.
            .padding(
                horizontal = size.containerPadding,
                vertical = if (overflow) size.overflowVerticalPadding else size.containerPadding
            ),
        size = size,
        render = render,
        overflow = overflow,
        leadingIcon = leadingIcon,
        contents = contents,
        trailingIcon = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    id = if (focused) {
                        R.drawable.icon_normal_chevron_up_thick_small
                    } else {
                        R.drawable.icon_normal_chevron_down_thick_small
                    }
                ),
                tint = colorResource(
                    id = if (enabled) {
                        R.color.foreground_neutral_tertiary
                    } else {
                        R.color.foreground_disable_primary
                    }
                ),
                contentDescription = ""
            )
        }
    )
}

/** #3 상태 색 border 뒤에 깔리는 underlay border 색상입니다. */
@Composable
private fun containerUnderlayBorderColor(
    enabled: Boolean,
    negative: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.transparent
    negative || focused -> DesignSystemTheme.colors.backgroundNeutralPrimary.copy(alpha = OPACITY_43)
    else -> DesignSystemTheme.colors.transparent
}

/** #3 Container border 색상입니다. Negative + Focused 는 opacity 를 52% 로 올려 강조합니다. */
@Composable
private fun containerBorderColor(
    enabled: Boolean,
    negative: Boolean,
    focused: Boolean
): Color = when {
    !enabled -> DesignSystemTheme.colors.lineNeutralTertiary
    negative && focused -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_52)
    negative -> DesignSystemTheme.colors.foregroundNegativePrimary.copy(OPACITY_43)
    focused -> DesignSystemTheme.colors.surfaceBrandPrimary.copy(OPACITY_43)
    else -> DesignSystemTheme.colors.lineNeutralSecondary
}

// 눌림 표현은 상태와 무관하게 중립 색으로 통일한다. 상태 색(red/primary)을 ripple 에 쓰면
// 누르는 동안 컨테이너 전체가 그 색으로 칠해진다.
@Composable
private fun getSelectRippleEffect(enabled: Boolean) = if (enabled) {
    wantedRippleEffect(DesignSystemTheme.colorsOpacity.foregroundNeutralPrimaryOpacity12)
} else {
    null
}

val LocalWantedSelectBackground = WantedWantedSelectBackgroundCompositionLocal()


@JvmInline
value class WantedWantedSelectBackgroundCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Color> = staticCompositionLocalOf { Color.Transparent }
) {
    val current: Color
        @Composable get() = delegate.current

    infix fun provides(value: Color) = delegate provides value
}


@DevicePreviews
@Composable
private fun WantedSelectPreview() {
    val selectDataList = listOf(
        WantedSelectData(text = "선택값1"),
        WantedSelectData(text = "선택값2")
    )

    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectData = WantedSelectData(text = "Large (default)"),
                    placeHolder = "선택해 주세요",
                    enabled = true,
                    selectDataList = selectDataList,
                    size = WantedSelectDefaults.Size.Large
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectData = WantedSelectData(text = "Medium"),
                    placeHolder = "선택해 주세요",
                    enabled = true,
                    selectDataList = selectDataList,
                    size = WantedSelectDefaults.Size.Medium
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectData = WantedSelectData(text = "Medium + focused"),
                    placeHolder = "선택해 주세요",
                    enabled = true,
                    selectDataList = selectDataList,
                    focused = true,
                    size = WantedSelectDefaults.Size.Medium
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectData = null,
                    placeHolder = "선택해 주세요",
                    enabled = false,
                    selectDataList = selectDataList
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDataList = selectDataList,
                    placeHolder = "선택해 주세요",
                    enabled = true,
                    selectDataList = selectDataList,
                    render = WantedSelectDefaults.MultiSelectRender.Chip,
                    size = WantedSelectDefaults.Size.Medium
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDataList = selectDataList,
                    placeHolder = "선택해 주세요",
                    status = WantedSelectDefaults.Status.Negative,
                    errorDataList = selectDataList.take(1),
                    enabled = true,
                    selectDataList = selectDataList,
                    render = WantedSelectDefaults.MultiSelectRender.Chip
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSelectWithFormControlPreview() {
    val selectDataList = listOf(
        WantedSelectData(text = "선택값1"),
        WantedSelectData(text = "선택값2")
    )

    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedFormControl(
                    label = "주제",
                    required = true,
                    description = "메시지에 마침표를 찍어요."
                ) {
                    WantedSelect(
                        modifier = Modifier.fillMaxWidth(),
                        selectData = WantedSelectData(text = "선택값"),
                        placeHolder = "선택해 주세요",
                        enabled = true,
                        selectDataList = selectDataList
                    )
                }

                WantedFormControl(
                    label = "주제",
                    required = true,
                    description = "메시지에 마침표를 찍어요.",
                    status = WantedFormControlDefaults.Status.Negative
                ) {
                    WantedSelect(
                        modifier = Modifier.fillMaxWidth(),
                        selectData = WantedSelectData(text = "선택값"),
                        placeHolder = "선택해 주세요",
                        status = WantedSelectDefaults.Status.Negative,
                        enabled = true,
                        selectDataList = selectDataList
                    )
                }

                WantedFormControl(
                    label = "관심 분야",
                    size = WantedFormControlDefaults.Size.Medium
                ) {
                    WantedSelect(
                        modifier = Modifier.fillMaxWidth(),
                        selectedDataList = selectDataList,
                        placeHolder = "선택해 주세요",
                        enabled = true,
                        selectDataList = selectDataList,
                        render = WantedSelectDefaults.MultiSelectRender.Chip,
                        size = WantedSelectDefaults.Size.Medium
                    )
                }
            }
        }
    }
}
