package com.wanted.android.wanted.design.presentation.menu

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
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.contents.cell.WantedCell
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 섹션 타이틀과 아이템으로 구성된 기본 메뉴 컴포저블입니다.
 *
 * 각 섹션별로 타이틀과 아이템을 바인딩할 수 있으며, 최대 높이 및 패딩, 배경이 설정된 리스트 형태로 표시됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedMenu(
 *     sectionCount = 2,
 *     itemCount = { 3 },
 *     onBindSectionTitle = { Text("Section Title") },
 *     onBindSectionItem = { section, index -> Text("Item $index") }
 * )
 * ```
 *
 * @param sectionCount Int: 섹션의 총 개수입니다.
 * @param itemCount (section: Int) -> Int: 각 섹션의 아이템 개수를 반환합니다.
 * @param onBindSectionItem @Composable (section: Int, index: Int) -> Unit: 각 아이템에 대한 컴포저블입니다.
 * @param modifier Modifier: 메뉴 전체에 적용할 Modifier입니다.
 * @param onBindSectionTitle @Composable ((section: Int) -> Unit)?: 섹션 타이틀 컴포저블입니다.
 */
@Composable
fun WantedMenu(
    sectionCount: Int,
    itemCount: (section: Int) -> Int,
    onBindSectionItem: @Composable (section: Int, index: Int) -> Unit,
    modifier: Modifier = Modifier,
    onBindSectionTitle: @Composable ((section: Int) -> Unit)? = null
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