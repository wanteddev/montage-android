package com.wanted.android.wanted.design.beta.topbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.topbar.WantedTopAppBar
import com.wanted.android.wanted.design.topbar.WantedTopAppBarContract.TopAppBarType
import com.wanted.android.wanted.design.topbar.view.WantedTopAppBarIconButton


@Composable
fun CustomMainTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {

    WantedTopAppBar(
        modifier = modifier,
        type = TopAppBarType.Extended,
        title = {
            title()
        },
        actions = {
            actions()
        }
    )
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
private fun CustomTopAppBarPreview() {
    DesignSystemTheme {
        Scaffold(
            topBar = {
                WantedTopAppBar(
                    navigationIcon = {
                        WantedTopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_1_line_arrow_left_12),
                            onClick = {}
                        )
                    },
                    title = "타이틀",
                    actions = {
                        WantedTopAppBarIconButton(
                            modifier = Modifier,
                            painter = painterResource(id = R.drawable.button_search_line_12dp_svg),
                            onClick = {}
                        )
                    }
                )
            }
        ) { innerPadding ->
            Modifier.padding(innerPadding)
        }
    }
}