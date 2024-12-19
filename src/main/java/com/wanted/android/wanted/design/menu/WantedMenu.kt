package com.wanted.android.wanted.design.menu

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.list.WantedCell
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=16784-140137&m=dev
 */
@Composable
fun WantedMenu(
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
            .background(colorResource(id = R.color.background_elevated_normal)),
        contentPadding = PaddingValues(20.dp),
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
                        onBindSectionTitle(section)
                    }
                }
            }

            items(itemCount(section)) { index ->
                onBindSectionItem(section, index)
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedMenuPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                WantedMenu(
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