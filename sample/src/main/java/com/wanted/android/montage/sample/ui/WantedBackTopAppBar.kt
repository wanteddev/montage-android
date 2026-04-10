package com.wanted.android.montage.sample.ui

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarIconButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedBackTopAppBar(
    modifier: Modifier = Modifier,
    variant: Variant = Variant.Normal,
    backgroundColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    background: Boolean = true,
    scrollableState: ScrollableState? = null,
    titleAlignCenter: Boolean = false,
    title: String = "",
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClickBack: () -> Unit = {}
) {
    WantedTopAppBar(
        modifier = modifier,
        backgroundColor = backgroundColor,
        background = background,
        variant = variant,
        scrollableState = scrollableState,
        titleAlignCenter = titleAlignCenter,
        navigationIcon = {
            WantedTopAppBarIconButton(
                painter = painterResource(R.drawable.icon_normal_arrow_left),
                onClick = onClickBack
            )
        },
        title = {
            Text(text = title)
        },
        actions = actions
    )
}
