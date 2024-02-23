package com.wanted.android.wanted.design.beta.topbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    isEnableDivider: Boolean = false,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            backgroundColor = MaterialTheme.colors.background,
            contentPadding = PaddingValues(),
            elevation = 0.dp
        ) {
            navigationIcon?.invoke() ?: kotlin.run {
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_16)))
            }

            Box(modifier = Modifier.weight(1f)) {
                ProvideTextStyle(
                    value = WantedTextStyle(
                        colorRes = R.color.label_strong,
                        style = DesignSystemTheme.typography.heading2Bold
                    )
                ) {
                    title()
                }
            }

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_16)))

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            ) {
                actions()
            }
        }

        if (isEnableDivider) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.line_normal_alternative)
            )
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
private fun CustomTopAppBarPreview() {
    DesignSystemTheme {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    navigationIcon = {
                        TopAppBarIconButton(
                            painter = painterResource(id = R.drawable.icon_1_line_arrow_left_12),
                            onClick = {}
                        )
                    },
                    title = { Text(text = "타이틀") },
                    actions = {
                        TopAppBarIconButton(
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