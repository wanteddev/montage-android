package com.wanted.android.wanted.design.navigations.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


/**
 * WantedTabItem
 *
 * 단일 Tab 항목을 구성하는 컴포넌트입니다.
 *
 * 선택 상태에 따라 텍스트 스타일이 변경되며, 클릭 이벤트를 처리합니다.
 *
 * @param tabSize TabSize: 탭의 텍스트 크기입니다.
 * @param title String: 탭에 표시할 텍스트입니다.
 * @param active Boolean: 선택 여부입니다.
 * @param enable Boolean: 활성화 여부입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param onTextLayout ((TextLayoutResult) -> Unit)?: 텍스트 레이아웃 결과를 전달하는 콜백입니다.
 * @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedTabItem(
    tabSize: WantedTabDefaults.TabSize,
    title: String,
    active: Boolean,
    enable: Boolean,
    modifier: Modifier = Modifier,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedTouchArea(
        modifier = modifier,
        enabled = enable,
        content = {
            Text(
                modifier = Modifier,
                text = title,
                style = when (tabSize) {
                    WantedTabDefaults.TabSize.Large -> DesignSystemTheme.typography.heading2Bold
                    WantedTabDefaults.TabSize.Medium -> DesignSystemTheme.typography.headline2Bold
                    else -> DesignSystemTheme.typography.body2Bold
                },
                color = when {
                    !enable -> DesignSystemTheme.colors.labelDisable
                    active -> DesignSystemTheme.colors.labelStrong
                    else -> DesignSystemTheme.colors.labelAssistive
                },
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    onTextLayout?.invoke(textLayoutResult)
                }
            )
        },
        horizontalPadding = 12.dp,
        verticalPadding = if (tabSize == WantedTabDefaults.TabSize.Large) 14.dp else 12.dp,
        isUseRipple = false,
        rippleColor = DesignSystemTheme.colors.transparent,
        onClick = onClick
    )

}

@DevicePreviews
@Composable
private fun WantedTabItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Small,
                    title = "텍스트",
                    active = false,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Small,
                    title = "텍스트",
                    active = true,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Medium,
                    title = "텍스트",
                    active = false,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Medium,
                    title = "텍스트",
                    active = true,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Large,
                    title = "텍스트",
                    enable = true,
                    active = false,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabDefaults.TabSize.Large,
                    title = "텍스트",
                    active = true,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )
            }
        }
    }
}