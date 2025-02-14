package com.wanted.android.wanted.design.select.view

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
import com.wanted.android.wanted.design.button.WantedButton
import com.wanted.android.wanted.design.dialog.WantedModalBottomSheet
import com.wanted.android.wanted.design.dialog.WantedModalContract.BottomSheetDialogType
import com.wanted.android.wanted.design.element.CheckBoxSize
import com.wanted.android.wanted.design.element.CheckBoxState
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.element.WantedCheckMark
import com.wanted.android.wanted.design.element.WantedRadioButton
import com.wanted.android.wanted.design.list.WantedCell
import com.wanted.android.wanted.design.list.WantedCellContract
import com.wanted.android.wanted.design.select.WantedSelectContract
import com.wanted.android.wanted.design.select.WantedSelectData
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonType

@Composable
fun WantedMultiSelectBottomSheet(
    modifier: Modifier = Modifier,
    items: List<WantedSelectData>,
    confirmText: String,
    selectType: WantedSelectContract.SelectType = WantedSelectContract.SelectType.CheckBox,
    dialogType: BottomSheetDialogType = BottomSheetDialogType.Flexible,
    selectedItemList: List<WantedSelectData>,
    onSelect: (itemList: List<WantedSelectData>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selectItemList = remember(selectedItemList) { mutableStateOf(selectedItemList) }

    WantedModalBottomSheet(
        modifier = modifier,
        type = dialogType,
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    WantedCell(
                        modifier = Modifier,
                        verticalPadding = WantedCellContract.VerticalPadding.Padding12,
                        text = item.text,
                        rightContent = when {
                            selectItemList.value.contains(item)
                                    && selectType == WantedSelectContract.SelectType.CheckMark -> {
                                {
                                    WantedCheckMark(
                                        modifier = Modifier,
                                        size = CheckBoxSize.Normal,
                                        checked = true,
                                        onCheckedChange = { }
                                    )
                                }
                            }

                            else -> null
                        },
                        leftContent = when {
                            selectType == WantedSelectContract.SelectType.CheckBox -> {
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
                        buttonShape = ButtonShape.OUTLINED,
                        type = ButtonType.ASSISTIVE,
                        leftDrawable = R.drawable.ic_normal_refresh_svg,
                        onClick = {
                            selectItemList.value = selectedItemList
                        }
                    )

                    WantedButton(
                        modifier = Modifier.weight(1f),
                        text = confirmText,
                        buttonShape = ButtonShape.SOLID,
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