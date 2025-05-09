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
import com.wanted.android.wanted.design.input.ComponentTitle
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
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-44983&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1934-43909&t=33KjAy2RlyzyhLH6-4
 */
@Composable
fun WantedSelectWithString(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    selectedValueList: List<String>,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negativeList: List<String> = emptyList(),
    focused: Boolean = false,
    enabled: Boolean = true,
    overflow: Boolean = false,
    selectValueList: List<String> = emptyList(),
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckBox,
    render: WantedSelectContract.MultiSelectRender = WantedSelectContract.MultiSelectRender.Text,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onDelete: (String) -> Unit = {},
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    onSelectList: (itemList: List<String>) -> Unit = { },
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
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    selectedDataList: List<WantedSelectData>,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negativeDataList: List<WantedSelectData> = emptyList(),
    focused: Boolean = false,
    enabled: Boolean = true,
    overflow: Boolean = false,
    selectDataList: List<WantedSelectData> = emptyList(),
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckBox,
    render: WantedSelectContract.MultiSelectRender = WantedSelectContract.MultiSelectRender.Text,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onDeleteData: (WantedSelectData) -> Unit = {},
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    onSelectDataList: (itemList: List<WantedSelectData>) -> Unit = { },
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

@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    value: String,
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
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClick: () -> Unit = {},
    onSelect: (item: String) -> Unit = { },
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


@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    confirmText: String = "",
    selectData: WantedSelectData?,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    selectDataList: List<WantedSelectData> = emptyList(),
    selectedData: WantedSelectData? = null,
    bottomSheetType: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible,
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckMark,
    background: Color = colorResource(id = R.color.background_normal_normal),
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


@Composable
private fun WantedSelectImpl(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    isRequiredBadge: Boolean = false,
    negative: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    background: Color,
    onClick: () -> Unit = {},
    contents: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
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
                                colorResource(R.color.interaction_disable)
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
                                    R.drawable.ic_normal_chevron_up_thick_small_svg
                                } else {
                                    R.drawable.ic_normal_chevron_down_thick_small_svg
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
                                painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
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