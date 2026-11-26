package com.wanted.android.wanted.design.navigations.topbar

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * fun WantedTopAppBarToolbarSlot(...)
 *
 * TopAppBar toolbar 영역에 넣는 Slot 프리셋입니다.
 *
 * 아이템을 가로로 배치하고 세로 중앙 정렬합니다. 폭을 넘으면 줄바꿈·말줄임 없이 가로 스크롤하며, 좌우 20dp 여백은 콘텐츠 처음과 끝에만 붙습니다.
 * 하단에 1dp 구분선(lineNeutralTertiary)을 그립니다. 배경은 칠하지 않으며 TopAppBar 배경을 그대로 씁니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedTopAppBar(
 *     title = "제목",
 *     toolbar = {
 *         WantedTopAppBarToolbarSlot {
 *             WantedChip(text = "필터1", onClick = {})
 *             WantedChip(text = "필터2", onClick = {})
 *         }
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param content @Composable RowScope.() -> Unit: 가로로 배치할 아이템 슬롯입니다.
 */
@Composable
fun WantedTopAppBarToolbarSlot(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(
                    horizontal = WantedTopAppBarToolbarDefaults.SlotHorizontalPadding,
                    vertical = WantedTopAppBarToolbarDefaults.SlotVerticalPadding
                ),
            horizontalArrangement = Arrangement.spacedBy(WantedTopAppBarToolbarDefaults.SlotItemSpacing),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )

        HorizontalDivider(color = DesignSystemTheme.colors.lineNeutralTertiary)
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
private fun WantedTopAppBarToolbarPreview() {
    DesignSystemTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            WantedTopAppBar(
                title = "title",
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                },
                toolbar = {
                    WantedTopAppBarToolbarSlot {
                        repeat(10) { index ->
                            WantedChip(text = "item $index", onClick = { })
                        }
                    }
                }
            )

            WantedTopAppBar(
                title = "title",
                titleAlignCenter = true,
                toolbar = {
                    WantedTopAppBarToolbarSlot {
                        WantedChip(text = "item", onClick = { })
                    }
                }
            )
        }
    }
}
