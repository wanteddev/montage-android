package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
internal fun WantedTopAppBarLayout(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .defaultMinSize(minHeight = 56.dp)
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        navigationIcon?.let {
            navigationIcon()
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            title?.let {
                ProvideTextStyle(
                    value = DesignSystemTheme.typography.headline2Bold.copy(
                        color = DesignSystemTheme.colors.labelStrong
                    )
                ) {
                    title()
                }
            }
        }

        actions?.let {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedTopAppBarLayoutPreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colors.backgroundNormalNormal),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            WantedTopAppBarLayout(
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                        onClick = { }
                    )
                },
                title = {
                    Text(text = "타이틀")
                },
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.icon_normal_share),
                        onClick = { }
                    )
                }
            )
        }
    }
}