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
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedDropShadow
import com.wanted.android.wanted.design.input.textinput.view.ComponentTitle
import com.wanted.android.wanted.design.input.select.view.WantedMultiSelectBottomSheet
import com.wanted.android.wanted.design.input.select.view.WantedMultiSelectContents
import com.wanted.android.wanted.design.input.select.view.WantedSelectBottomSheet
import com.wanted.android.wanted.design.input.select.view.WantedSelectContentLayout
import com.wanted.android.wanted.design.input.select.view.WantedSelectLayout
import com.wanted.android.wanted.design.input.select.view.WantedSelectPlaceHolder
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect


/**
 * 단일 항목을 선택할 수 있는 Select 컴포넌트입니다.
 *
 * 선택 가능한 항목을 다이얼로그(BottomSheet)로 제공하며, 선택 시 콜백으로 결과를 반환합니다.
 * 내부적으로 placeholder, 에러 처리, 포커스 상태 등을 지원하며, 사용자가 원하는 형식으로 렌더링할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSelect(
 *     value = "선택값",
 *     title = "제목",
 *     selectValueList = listOf("옵션1", "옵션2"),
 *     onSelect = { 선택된값 -> ... }
 * )
 * ```
 *
 * @param value String: 선택된 현재 값입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 선택 항목 아래에 표시할 부가 설명입니다.
 * @param placeHolder String: 선택 전 표시될 플레이스홀더입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 옆에 필수 표시 뱃지를 보여줄지 여부입니다.
 * @param negative Boolean: 오류 상태 여부입니다.
 * @param focused Boolean: 포커스 시 테두리 강조 여부입니다.
 * @param enabled Boolean: 활성화 여부입니다.
 * @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
 * @param selectedValue String?: 초기 선택된 항목입니다.
 * @param bottomSheetType BottomSheetDialogType: 다이얼로그 형식입니다.
 * @param selectType SelectType: 선택 UI 타입 (체크박스, 라디오 등)입니다.
 * @param background Color: 배경 색상입니다.
 * @param onClick () -> Unit: 클릭 시 동작할 콜백입니다.
 * @param onSelect (String) -> Unit: 선택 완료 시 호출되는 콜백입니다.
 * @param leadingIcon (() -> Unit)?: 왼쪽 아이콘 슬롯입니다.
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
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckMark,
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
 * 단일 항목 선택용 셀렉트 컴포넌트입니다.
 *
 * 선택된 항목을 텍스트 형태로 표시하고, 클릭 시 BottomSheet를 통해 항목을 선택할 수 있습니다.
 * placeholder, 오류 표시, 포커스 강조, 비활성화 상태, 아이콘 삽입 등의 UI 커스터마이징을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSelect(
 *     title = "직무 선택",
 *     selectDataList = listOf(
 *         WantedSelectData(text = "백엔드"),
 *         WantedSelectData(text = "프론트엔드")
 *     ),
 *     selectedData = WantedSelectData(text = "백엔드"),
 *     onSelectData = { item -> /* 선택 처리 */ }
 * )
 * ```
 *
 * @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시될 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택 적용됩니다.
 * @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 우측에 필수 뱃지를 표시할지 여부입니다.
 * @param negative Boolean: 에러 상태 여부입니다. true일 경우 강조 색상 및 아이콘이 노출됩니다.
 * @param focused Boolean: 포커스 상태 여부입니다. true일 경우 테두리가 강조됩니다.
 * @param enabled Boolean: 컴포넌트 활성화 여부입니다.
 * @param selectDataList List<WantedSelectData>: 선택 가능한 항목 리스트입니다.
 * @param selectedData WantedSelectData?: 초기 선택된 항목입니다.
 * @param bottomSheetType BottomSheetDialogType: 하단 다이얼로그 형식입니다 (Flexible, FullScreen 등).
 * @param selectType SelectType: 항목 선택 방식입니다 (CheckMark, CheckBox, Radio).
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectData (WantedSelectData) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon @Composable (() -> Unit)?: 좌측에 표시할 커스텀 아이콘 슬롯입니다.
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
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckMark,
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
                    text = selectData?.text ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WantedTextStyle(
                        colorRes = if (enabled) {
                            R.color.label_normal
                        } else {
                            R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.body1Regular
                    )
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
 * 여러 항목을 선택할 수 있는 멀티 셀렉트 컴포넌트입니다.
 *
 * 선택된 항목들을 Chip 또는 텍스트 형태로 보여주며, 클릭 시 BottomSheet 형태의 다중 선택 다이얼로그를 표시합니다.
 * 항목을 선택한 뒤 확인 버튼으로 확정하거나, confirmText가 비어있을 경우 선택 시 바로 확정됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSelect(
 *     title = "관심 분야",
 *     selectedDataList = listOf(WantedSelectData(text = "개발")),
 *     selectDataList = listOf(
 *         WantedSelectData(text = "개발"),
 *         WantedSelectData(text = "디자인")
 *     ),
 *     onSelectDataList = { list -> /* 선택 완료 처리 */ },
 *     onDeleteData = { item -> /* 삭제 처리 */ }
 * )
 * ```
 *
 * @param selectedDataList List<WantedSelectData>: 현재 선택된 항목 리스트입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래에 표시될 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
 * @param placeHolder String: 선택 전 표시될 안내 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 오른쪽에 필수 입력 뱃지를 표시할지 여부입니다.
 * @param negativeDataList List<WantedSelectData>: 에러 표시를 위한 항목 리스트입니다.
 * @param focused Boolean: 포커스 강조 상태 여부입니다.
 * @param enabled Boolean: 선택 가능 여부입니다.
 * @param overflow Boolean: 선택 항목이 넘칠 경우 줄바꿈 처리할지 여부입니다.
 * @param selectDataList List<WantedSelectData>: 선택 가능한 전체 항목 리스트입니다.
 * @param selectType WantedSelectContract.SelectType: 선택 UI 타입입니다 (CheckBox, CheckMark, Radio 중 하나).
 * @param render WantedSelectContract.MultiSelectRender: 선택 항목 표시 방식입니다 (Text 또는 Chip).
 * @param background Color: 셀렉트 박스의 배경 색상입니다.
 * @param onDeleteData (WantedSelectData) -> Unit: 선택된 항목을 삭제할 때 호출되는 콜백입니다.
 * @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
 * @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
 * @param leadingIcon @Composable (() -> Unit)?: 셀렉트 박스 왼쪽에 표시할 커스텀 아이콘 슬롯입니다.
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
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckBox,
    render: WantedSelectContract.MultiSelectRender = WantedSelectContract.MultiSelectRender.Text,
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
 * 문자열 리스트 기반으로 구성된 멀티 셀렉트 UI 컴포넌트입니다.
 *
 * 여러 개의 문자열 값을 선택하고, 선택된 항목을 Chip 또는 텍스트 형태로 보여주는 UI를 구성합니다.
 * `WantedSelect`의 문자열 래핑 버전으로, 내부적으로 `WantedSelectData`로 변환 후 처리합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSelectWithString(
 *     selectedValueList = listOf("개발자", "디자이너"),
 *     selectValueList = listOf("기획자", "개발자", "디자이너"),
 *     onSelectList = { 선택된리스트 -> ... },
 *     onClick = { /* 열기 처리 */ }
 * )
 * ```
 *
 * @param selectedValueList List<String>: 현재 선택된 문자열 항목 리스트입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param title String?: 상단에 표시할 제목입니다.
 * @param description String?: 셀렉트 아래 설명 텍스트입니다.
 * @param confirmText String: 확인 버튼에 표시할 텍스트입니다. 비워두면 즉시 반영됩니다.
 * @param placeHolder String: 아무 항목도 선택되지 않았을 때 표시되는 안내 텍스트입니다.
 * @param isRequiredBadge Boolean: 제목 옆 필수 뱃지를 표시할지 여부입니다.
 * @param negativeList List<String>: 오류로 표시할 항목 리스트입니다.
 * @param focused Boolean: 포커스 상태 여부입니다.
 * @param enabled Boolean: 비활성화 상태 여부입니다.
 * @param overflow Boolean: Chip 렌더링 시 줄바꿈 여부입니다.
 * @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
 * @param selectType SelectType: 선택 방식 (CheckBox, CheckMark, Radio) 중 하나입니다.
 * @param render MultiSelectRender: 선택 항목 렌더링 형태 (Chip 또는 Text)입니다.
 * @param background Color: 컴포넌트 배경 색상입니다.
 * @param leadingIcon (() -> Unit)?: 왼쪽에 표시할 선택적 아이콘 슬롯입니다.
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
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckBox,
    render: WantedSelectContract.MultiSelectRender = WantedSelectContract.MultiSelectRender.Text,
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
                val (shadow, select) = createRefs()
                WantedDropShadow(
                    Modifier
                        .constrainAs(shadow) {
                            top.linkTo(select.top)
                            bottom.linkTo(select.bottom)
                            start.linkTo(select.start)
                            end.linkTo(select.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    background = background,
                    shape = RoundedCornerShape(12.dp)
                )

                WantedSelectContentLayout(
                    modifier = Modifier
                        .constrainAs(select) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                negative || focused -> {
                                    colorResource(id = R.color.background_normal_normal)
                                        .copy(alpha = OPACITY_43)
                                }

                                else -> colorResource(R.color.transparent)
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            color = when {
                                !enabled -> colorResource(R.color.line_normal_alternative)
                                negative -> colorResource(R.color.status_negative).copy(OPACITY_43)
                                focused -> colorResource(R.color.primary_normal).copy(OPACITY_43)
                                else -> colorResource(R.color.line_normal_neutral)
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (enabled) {
                                background
                            } else {
                                colorResource(R.color.fill_alternative)
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
                                tint = colorResource(id = R.color.status_negative),
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
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && negative -> R.color.status_negative
                            else -> R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.caption1Regular
                    ),
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
    negative -> wantedRippleEffect(colorResource(id = R.color.status_negative))
    focused -> wantedRippleEffect(colorResource(id = R.color.primary_normal))
    else -> wantedRippleEffect(colorResource(id = R.color.label_normal_opacity12))
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
                    render = WantedSelectContract.MultiSelectRender.Chip,
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
                    render = WantedSelectContract.MultiSelectRender.Chip,
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
                    render = WantedSelectContract.MultiSelectRender.Chip,
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