package com.wanted.android.wanted.design.input.select

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults
import com.wanted.android.wanted.design.base.wantedDropShadow
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
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * WantedSelect
 *
 * 단일 항목을 선택할 수 있는 Select 컴포넌트입니다.
 *
 * 선택 가능한 항목을 Bottom sheet로 제공하며, 선택 시 콜백으로 결과를 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedValue by remember { mutableStateOf("") }
 *
 * WantedSelect(
 *     value = selectedValue,
 *     title = "직무 선택",
 *     placeholder = "직무를 선택해주세요",
 *     selectValueList = listOf("백엔드", "프론트엔드", "디자이너"),
 *     onSelect = { selectedValue = it }
 * )
 * ```
 *
 * @param value String: 선택된 현재 값입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시할 부가 설명입니다.
 * @param placeHolder String: 선택 전 표시될 플레이스홀더입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 옆에 필수 표시 뱃지를 보여줄지 여부입니다.
 * @param negative Boolean: 오류 상태 여부입니다.
 * @param focused Boolean: 포커스 시 테두리 강조 여부입니다.
 * @param enabled Boolean: 활성화 여부입니다.
 * @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
 * @param selectedValue String?: 초기 선택된 항목입니다.
 * @param bottomSheetType ModalType: BottomSheet 형식입니다.
 * @param selectType SelectType: 선택 UI 타입입니다.
 * @param background Color: 배경 색상입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 * @param onSelect (String) -> Unit: 선택 완료 시 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 왼쪽 아이콘 슬롯입니다.
 */
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
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    onClick: () -> Unit = {},
    onSelect: (item: String) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelect(
        modifier = modifier,
        title = title,
        description = description,
        confirmText = confirmText,
        selectData = WantedSelectData(text = value),
        placeHolder = placeHolder,
        isRequiredBadge = isRequiredBadge,
        negative = negative,
        focused = focused,
        enabled = enabled,
        bottomSheetType = bottomSheetType,
        selectDataList = selectValueList.map { WantedSelectData(text = it) },
        selectedData = selectedValue?.let { WantedSelectData(text = it) },
        selectType = selectType,
        background = background,
        onClick = onClick,
        leadingIcon = leadingIcon,
        onSelectData = { item ->
            onSelect(item.text)
        }
    )
}

/**
 * WantedSelect
 *
 * WantedSelectData를 사용하는 단일 항목 선택 컴포넌트입니다.
 *
 * 선택된 항목을 화면에 표시하고, 클릭 시 Bottom sheet를 통해 항목을 선택할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedData by remember { mutableStateOf<WantedSelectData?>(null) }
 *
 * WantedSelect(
 *     title = "직무 선택",
 *     selectData = selectedData,
 *     selectDataList = listOf(
 *         WantedSelectData(id = "1", text = "백엔드"),
 *         WantedSelectData(id = "2", text = "프론트엔드")
 *     ),
 *     onSelectData = { selectedData = it }
 * )
 * ```
 *
 * @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 우측에 필수 뱃지를 표시할지 여부입니다.
 * @param negative Boolean: 오류 상태 여부입니다. true일 경우 강조 색상 및 아이콘이 표시됩니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true일 경우 테두리가 강조됩니다.
 * @param enabled Boolean: 컴포넌트 활성화 여부입니다.
 * @param selectDataList List<WantedSelectData>: 선택 가능한 항목 리스트입니다.
 * @param selectedData WantedSelectData?: 초기 선택된 항목입니다.
 * @param bottomSheetType ModalType: BottomSheet 형식입니다.
 * @param selectType SelectType: 항목 선택 방식입니다.
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectData (WantedSelectData) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 좌측에 표시할 커스텀 아이콘 슬롯입니다.
 */
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
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    onClick: () -> Unit = {},
    onSelectData: (item: WantedSelectData) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {

    val isShowBottomSheetDialog = remember { mutableStateOf(false) }
    val isFocus = remember(focused) { mutableStateOf(focused) }

    WantedSelectImpl(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        negative = negative,
        focused = isFocus.value,
        enabled = enabled,
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
            if (selectData?.text.isNullOrEmpty()) {
                WantedSelectPlaceHolder(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = placeHolder,
                    enabled = enabled
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    text = selectData.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = DesignSystemTheme.typography.body1Regular,
                    color = if (enabled) {
                        DesignSystemTheme.colors.labelNormal
                    } else {
                        DesignSystemTheme.colors.labelAlternative
                    }
                )
            }
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
 * 여러 항목을 선택할 수 있는 Multi select 컴포넌트입니다.
 *
 * 선택된 항목들을 Chip 또는 텍스트 형태로 표시하며, 클릭 시 Bottom sheet 형태의 다중 선택 Dialog를 표시합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedList by remember { mutableStateOf(listOf<WantedSelectData>()) }
 *
 * WantedSelect(
 *     title = "관심 분야",
 *     selectedDataList = selectedList,
 *     selectDataList = listOf(
 *         WantedSelectData(id = "1", text = "개발"),
 *         WantedSelectData(id = "2", text = "디자인")
 *     ),
 *     onSelectDataList = { selectedList = it },
 *     onDeleteData = { item ->
 *         selectedList = selectedList.filter { it.id != item.id }
 *     }
 * )
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
 * @param selectType SelectType: 선택 UI 타입입니다.
 * @param render MultiSelectRender: 선택 항목 표시 방식입니다.
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onDeleteData (WantedSelectData) -> Unit: 선택된 항목을 삭제할 때 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 셀렉트 박스 왼쪽에 표시할 커스텀 아이콘 슬롯입니다.
 */
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
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    onDeleteData: (WantedSelectData) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectDataList: (itemList: List<WantedSelectData>) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {

    val isShowBottomSheetDialog = remember { mutableStateOf(false) }
    val isFocus = remember(focused) { mutableStateOf(focused) }

    CompositionLocalProvider(LocalWantedSelectBackground.provides(background)) {
        WantedSelectImpl(
            modifier = modifier,
            title = title,
            description = description,
            isRequiredBadge = isRequiredBadge,
            negative = negativeDataList.isNotEmpty(),
            focused = isFocus.value,
            enabled = enabled,
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
                    errorList = negativeDataList,
                    overflow = overflow,
                    enabled = enabled,
                    render = render,
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
            isFocus.value = false
            isShowBottomSheetDialog.value = false
            onSelectDataList(itemList)
        },
        onDismissRequest = {
            isShowBottomSheetDialog.value = false
            isFocus.value = false
        }
    )
}

/**
 * WantedSelectWithString
 *
 * 문자열 리스트 기반의 Multi select 컴포넌트입니다.
 *
 * 여러 개의 문자열 값을 선택하고, 선택된 항목을 Chip 또는 텍스트 형태로 표시합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedList by remember { mutableStateOf(listOf<String>()) }
 *
 * WantedSelectWithString(
 *     title = "기술 스택",
 *     selectedValueList = selectedList,
 *     selectValueList = listOf("Kotlin", "Java", "Swift"),
 *     onSelectList = { selectedList = it },
 *     onDelete = { item ->
 *         selectedList = selectedList.filter { it != item }
 *     }
 * )
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
 * @param selectType SelectType: 선택 방식입니다.
 * @param render MultiSelectRender: 선택 항목 렌더링 형태입니다.
 * @param background Color: 컴포넌트 배경 색상입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 왼쪽에 표시할 선택적 아이콘 슬롯입니다.
 * @param onDelete (String) -> Unit: 선택된 항목에서 삭제 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 영역 클릭 시 호출되는 콜백입니다.
 * @param onSelectList (List<String>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 */
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
    background: Color = colorResource(id = R.color.background_transparent_alternative),
    leadingIcon: (@Composable (() -> Unit))? = null,
    onDelete: (String) -> Unit = {},
    onClick: () -> Unit = {},
    onSelectList: (itemList: List<String>) -> Unit = {}
) {
    WantedSelect(
        modifier = modifier,
        title = title,
        description = description,
        confirmText = confirmText,
        selectedDataList = selectedValueList.map { WantedSelectData(text = it) },
        placeHolder = placeHolder,
        isRequiredBadge = isRequiredBadge,
        negativeDataList = negativeList.map { WantedSelectData(text = it) },
        focused = focused,
        enabled = enabled,
        overflow = overflow,
        selectDataList = selectValueList.map { WantedSelectData(text = it) },
        selectType = selectType,
        render = render,
        background = background,
        onDeleteData = {
            onDelete(it.text)
        },
        onClick = onClick,
        leadingIcon = leadingIcon,
        onSelectDataList = { itemList ->
            onSelectList(itemList.map { it.text })
        }
    )
}


@Composable
private fun WantedSelectImpl(
    background: Color,
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    contents: @Composable () -> Unit
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
        select = {
            ConstraintLayout {
                WantedSelectContentLayout(
                    modifier = Modifier
                        .wantedDropShadow(WantedDropShadowDefaults.WantedShadowStyle.XSmall())
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                negative || focused -> {
                                    DesignSystemTheme.colors.backgroundNormalNormal
                                        .copy(alpha = OPACITY_43)
                                }

                                else -> DesignSystemTheme.colors.transparent
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                !enabled -> DesignSystemTheme.colors.lineNormalAlternative
                                negative -> DesignSystemTheme.colors.statusNegative.copy(OPACITY_43)
                                focused -> DesignSystemTheme.colors.primaryNormal.copy(OPACITY_43)
                                else -> DesignSystemTheme.colors.lineNormalNeutral
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (enabled) {
                                background
                            } else {
                                DesignSystemTheme.colors.fillAlternative
                            }
                        )
                        .clickOnce(
                            enabled = enabled,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = getSelectRippleEffect(
                                enabled = enabled,
                                focused = focused,
                                negative = negative
                            )
                        ) {
                            onClick()
                        }
                        .padding(12.dp),
                    leadingIcon = leadingIcon,
                    contents = {
                        contents()
                    },
                    rightButton = {
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
                                    R.color.label_alternative
                                } else {
                                    R.color.label_disable
                                }
                            ),
                            contentDescription = ""
                        )
                    },
                    trailingIcon = if (negative && !focused && enabled) {
                        {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(1.dp),
                                painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                                tint = DesignSystemTheme.colors.statusNegative,
                                contentDescription = ""
                            )
                        }
                    } else {
                        null
                    }
                )

            }

        },
        description = description?.let {
            {
                Text(
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = when {
                        enabled && negative -> DesignSystemTheme.colors.statusNegative
                        else -> DesignSystemTheme.colors.labelAlternative
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    )
}

@Composable
private fun getSelectRippleEffect(
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
) = when {
    !enabled -> null
    negative -> wantedRippleEffect(DesignSystemTheme.colors.statusNegative)
    focused -> wantedRippleEffect(DesignSystemTheme.colors.primaryNormal)
    else -> wantedRippleEffect(DesignSystemTheme.colorsOpacity.labelNormalOpacity12)
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
                    value = "asdf",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    placeHolder = "선택해 주세요",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    focused = true,
                    placeHolder = "선택해 주세요",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    description = "메시지에 마침표를 찍어요.",
                    negative = true,
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    focused = true,
                    negative = true,
                    value = "선택값",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    enabled = false,
                    value = "선택값",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    selectedDataList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    onDeleteData = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    selectedDataList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    negativeDataList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDeleteData = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    render = WantedSelectDefaults.MultiSelectRender.Chip,
                    selectedDataList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    negativeDataList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDeleteData = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    render = WantedSelectDefaults.MultiSelectRender.Chip,
                    focused = true,
                    selectedDataList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    negativeDataList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDeleteData = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    render = WantedSelectDefaults.MultiSelectRender.Chip,
                    enabled = false,
                    selectedDataList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    negativeDataList = listOf(
                        WantedSelectData(text = "선택값2")
                    ),
                    onDeleteData = { },
                    onClick = {}
                )
            }
        }
    }
}