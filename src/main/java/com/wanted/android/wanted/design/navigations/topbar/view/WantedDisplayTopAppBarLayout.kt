package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
internal fun WantedDisplayTopAppBarLayout(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp)
            .wrapContentHeight()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationIcon?.let {
                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    navigationIcon()
                }

                Spacer(modifier = Modifier.size(8.dp))
            } ?: kotlin.run {
                title?.let {
                    TopBarTitle(title)
                }
                Spacer(modifier = Modifier.size(16.dp))
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
            )

            Spacer(modifier = Modifier.size(16.dp))

            actions?.let {
                actions()
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        if (navigationIcon != null && title != null) {
            Spacer(modifier = Modifier.size(8.dp))
            TopBarTitle(title)
        }
    }
}

@Composable
private fun TopBarTitle(
    title: @Composable () -> Unit
) {
    Box(Modifier.padding(start = 20.dp)) {
        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.title3Bold
            )
        ) {
            title()
        }
    }
}


@DevicePreviews
@Composable
private fun WantedDisplayTopAppBarLayoutPreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background_normal_normal)),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            WantedDisplayTopAppBarLayout(
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
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                }
            )

            WantedDisplayTopAppBarLayout(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = "타이틀")
                },
                actions = {
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                    WantedTopAppBarIconButton(
                        painter = painterResource(id = R.drawable.ic_normal_share_svg),
                        onClick = { }
                    )
                }
            )
        }
    }
}