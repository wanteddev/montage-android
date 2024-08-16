package com.wanted.android.wanted.design.menu

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.element.WantedCheckBox
import com.wanted.android.wanted.design.element.WantedRadioButton
import com.wanted.android.wanted.design.list.WantedCell
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=16784-140148&m=dev
 */

@Composable
fun WantedMenu(
    properties: DialogProperties = DialogProperties(),
    items: List<String>,
    onDismissRequest: () -> Unit,
    onClick: (index: Int, value: String) -> Unit
) {
    WantedMenu(
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
fun WantedMenu(
    properties: DialogProperties = DialogProperties(),
    items: List<String>,
    type: WantedMenuContract.Type = WantedMenuContract.Type.Normal,
    onDismissRequest: () -> Unit,
    onClick: (index: Int, value: String) -> Unit
) {
    WantedMenu(
        properties = properties,
        sectionCount = 2,
        itemCount = { items.size },
        onBindSectionTitle = null,
        onBindSectionItem = { _, index ->
            when (type) {
                WantedMenuContract.Type.Normal -> {
                    WantedCell(
                        text = items[index],
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }

                WantedMenuContract.Type.Radio -> {
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

                WantedMenuContract.Type.Check -> {
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
fun WantedMenu(
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
        WantedMenuImpl(
            modifier = Modifier,
            sectionCount = sectionCount,
            itemCount = itemCount,
            onBindSectionTitle = onBindSectionTitle,
            onBindSectionItem = onBindSectionItem,
        )
    }
}

@Composable
private fun WantedMenuImpl(
    modifier: Modifier = Modifier,
    sectionCount: Int,
    itemCount: (section: Int) -> Int,
    onBindSectionTitle: @Composable ((section: Int) -> Unit)? = null,
    onBindSectionItem: @Composable (section: Int, index: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(minWidth = 140.dp)
            .sizeIn(maxHeight = 400.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.background_elevated_normal))
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                color = colorResource(id = R.color.line_solid_neutral)
            ),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for (section in 0..sectionCount) {
            item {
                onBindSectionTitle?.let {
                    ProvideTextStyle(
                        value = WantedTextStyle(
                            colorRes = R.color.label_alternative,
                            style = DesignSystemTheme.typography.caption1Bold
                        )
                    ) {
                        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                            onBindSectionTitle(section)
                        }
                    }
                }
            }

            items(itemCount(section)) { index ->
                onBindSectionItem(section, index)
            }
        }
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedMenuPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                WantedMenuImpl(
                    sectionCount = 2,
                    itemCount = {
                        if (it == 0) {
                            2
                        } else {
                            3
                        }
                    },
                    onBindSectionTitle = {
                        Text(text = "$it title")
                    },
                    onBindSectionItem = { section, index ->
                        WantedCell(
                            text = "section $section",
                            caption = "index $index",
                            onClick = {

                            }
                        )
                    }
                )
            }
        }
    }
}