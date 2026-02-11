package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import com.wanted.android.wanted.design.input.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.input.control.WantedCheckBox
import com.wanted.android.wanted.design.input.input.control.WantedCheckMark
import com.wanted.android.wanted.design.input.input.control.WantedRadioButton
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
import com.wanted.android.wanted.design.input.select.WantedSelectData
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant

@Composable
internal fun WantedMultiSelectBottomSheet(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    items: List<WantedSelectData>,
    confirmText: String,
    selectType: WantedSelectDefaults.SelectType = WantedSelectDefaults.SelectType.CheckBox,
    dialogType: ModalType = ModalType.Flexible,
    selectedItemList: List<WantedSelectData>,
    onSelect: (itemList: List<WantedSelectData>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selectItemList = remember(selectedItemList) { mutableStateOf(selectedItemList) }

    WantedModalBottomSheet(
        modifier = modifier,
        type = dialogType,
        isShow = isShow,
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    WantedListCell(
                        modifier = Modifier,
                        verticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
                        text = item.text,
                        trailingContent = when {
                            selectItemList.value.contains(item)
                                && selectType == WantedSelectDefaults.SelectType.CheckMark -> {
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
                            selectType == WantedSelectDefaults.SelectType.CheckBox -> {
                                {
                                    WantedCheckBox(
                                        modifier = Modifier,
                                        size = CheckBoxSize.Normal,
                                        checkState = if (selectItemList.value.contains(item)) {
                                            CheckBoxState.Checked
                                        } else {
                                            CheckBoxState.Unchecked
                                        },
                                        onCheckedChange = { }
                                    )
                                }
                            }

                            selectItemList.value.contains(item)
                                && selectType == WantedSelectDefaults.SelectType.Radio -> {
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
                            val list = selectItemList.value.toMutableList()
                            if (selectItemList.value.contains(item)) {
                                list.remove(item)
                            } else {
                                list.add(item)
                            }

                            selectItemList.value = list.toSet().toList()
                        }
                    )
                }
            }
        },
        bottomBar = if (confirmText.isNotEmpty()) {
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WantedButton(
                        text = "",
                        variant = ButtonVariant.OUTLINED,
                        type = ButtonType.ASSISTIVE,
                        leadingDrawable = R.drawable.icon_normal_refresh,
                        onClick = {
                            selectItemList.value = selectedItemList
                        }
                    )

                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = confirmText,
                        variant = ButtonVariant.SOLID,
                        onClick = {
                            onSelect(items.filter { item -> selectItemList.value.contains(item) })
                        }
                    )
                }
            }
        } else null,
        onDismissRequest = onDismissRequest
    )
}