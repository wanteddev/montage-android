package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellContract
import com.wanted.android.wanted.design.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.control.WantedCheckBox
import com.wanted.android.wanted.design.input.control.WantedCheckMark
import com.wanted.android.wanted.design.input.control.WantedRadioButton
import com.wanted.android.wanted.design.input.select.WantedSelectContract
import com.wanted.android.wanted.design.input.select.WantedSelectData
import com.wanted.android.wanted.design.presentation.modal.WantedModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.ButtonType

@Composable
internal fun WantedSelectBottomSheet(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    items: List<WantedSelectData>,
    confirmText: String,
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckMark,
    bottomSheetType: ModalType = ModalType.Flexible,
    selectedItem: WantedSelectData? = null,
    onSelect: (item: WantedSelectData) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selectItem = remember(selectedItem) { mutableStateOf(selectedItem) }

    WantedModalBottomSheet(
        modifier = modifier,
        isShow = isShow,
        type = bottomSheetType,
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    WantedListCell(
                        modifier = Modifier,
                        verticalPadding = WantedListCellContract.VerticalPadding.Medium,
                        text = item.text,
                        selected = selectItem.value == item,
                        trailingContent = when {
                            selectItem.value == item
                                && selectType == WantedSelectContract.SelectType.CheckMark -> {
                                {
                                    WantedCheckMark(
                                        modifier = Modifier,
                                        size = CheckBoxSize.Normal,
                                        checked = true,
                                        thick = false,
                                        onCheckedChange = { }
                                    )
                                }
                            }

                            else -> null
                        },
                        leadingContent = when {
                            selectItem.value == item
                                && selectType == WantedSelectContract.SelectType.CheckBox -> {
                                {
                                    WantedCheckBox(
                                        modifier = Modifier,
                                        size = CheckBoxSize.Normal,
                                        checkState = CheckBoxState.Checked,
                                        onCheckedChange = { }
                                    )
                                }
                            }

                            selectItem.value == item
                                && selectType == WantedSelectContract.SelectType.Radio -> {
                                {
                                    WantedRadioButton(
                                        modifier = Modifier,
                                        size = CheckBoxSize.Normal,
                                        checked = true,
                                        onCheckedChange = { }
                                    )
                                }
                            }

                            else -> null
                        },
                        onClick = {
                            if (confirmText.isEmpty()) {
                                onSelect(item)
                            } else {
                                selectItem.value = item
                            }
                        }
                    )
                }
            }
        },
        bottomBar = if (confirmText.isNotEmpty()) {
            {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WantedButton(
                        text = "",
                        variant = ButtonVariant.OUTLINED,
                        type = ButtonType.ASSISTIVE,
                        leadingDrawable = R.drawable.ic_normal_refresh_svg,
                        onClick = {
                            selectItem.value = selectedItem
                        }
                    )

                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = confirmText,
                        variant = ButtonVariant.SOLID,
                        onClick = {
                            onSelect(selectItem.value ?: WantedSelectData())
                        }
                    )
                }
            }
        } else null,
        onDismissRequest = onDismissRequest
    )
}