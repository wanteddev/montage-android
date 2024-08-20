package com.wanted.android.wanted.design.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.element.WantedRadioButton
import com.wanted.android.wanted.design.list.WantedCell

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=16784-140148&m=dev
 */

@Composable
fun WantedMenuModal(
    properties: DialogProperties = DialogProperties(),
    items: List<String>,
    onDismissRequest: () -> Unit,
    onClick: (index: Int, value: String) -> Unit
) {
    WantedMenuModal(
        properties = properties,
        sectionCount = 2,
        itemCount = { items.size },
        onBindSectionTitle = null,
        onBindSectionItem = { _, index ->
            WantedCell(
                text = items[index],
                onClick = {
                    onClick(index, items[index])
                }
            )
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun WantedMenuModal(
    properties: DialogProperties = DialogProperties(),
    listType: WantedMenuContract.ListType = WantedMenuContract.ListType.Normal,
    items: List<String>,
    onDismissRequest: () -> Unit,
    onClick: (index: Int, value: String) -> Unit
) {
    WantedMenuModal(
        properties = properties,
        sectionCount = 2,
        itemCount = { items.size },
        onBindSectionTitle = null,
        onBindSectionItem = { _, index ->
            when (listType) {
                WantedMenuContract.ListType.Normal -> {
                    WantedCell(
                        text = items[index],
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }

                WantedMenuContract.ListType.Radio -> {
                    WantedCell(
                        text = items[index],
                        leftContent = {
                            WantedRadioButton(
                                checked = false,
                                onCheckedChange = {

                                }
                            )
                        },
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }

                WantedMenuContract.ListType.Check -> {
                    WantedCell(
                        text = items[index],
                        leftContent = {
                            WantedCheckBox(
                                checked = false,
                                onCheckedChange = {

                                }
                            )
                        },
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }
            }

        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun WantedMenuModal(
    properties: DialogProperties = DialogProperties(),
    sectionCount: Int,
    itemCount: (section: Int) -> Int,
    onBindSectionTitle: @Composable ((section: Int) -> Unit)? = null,
    onBindSectionItem: @Composable (section: Int, index: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedMenu(
            modifier = Modifier,
            sectionCount = sectionCount,
            itemCount = itemCount,
            onBindSectionTitle = onBindSectionTitle,
            onBindSectionItem = onBindSectionItem,
        )
    }
}
