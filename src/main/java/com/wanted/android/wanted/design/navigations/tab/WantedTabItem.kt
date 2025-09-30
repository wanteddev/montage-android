package com.wanted.android.wanted.design.navigations.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * 단일 탭 항목을 구성하는 텍스트 기반 컴포저블입니다.
 *
 * 선택 상태 및 텍스트 레이아웃 정보, 클릭 처리 등을 포함합니다.
 *
 * @param tabSize TabSize: 탭의 텍스트 스타일 및 패딩을 결정합니다.
 * @param title String: 탭에 표시할 텍스트입니다.
 * @param active Boolean: 선택 여부입니다.
 * @param modifier Modifier: 외형 설정용 Modifier입니다.
 * @param onTextLayout ((TextLayoutResult) -> Unit)? : 텍스트 레이아웃 결과 콜백입니다.
 * @param onClick () -> Unit: 클릭 시 호출됩니다.
 */
@Composable
fun WantedTabItem(
    tabSize: WantedTabContract.TabSize,
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
                style = WantedTextStyle(
                    colorRes = if(!enable) {
                        R.color.label_disable
                    } else if (active) {
                        R.color.label_strong
                    } else {
                        R.color.label_assistive
                    },
                    style = when (tabSize) {
                        WantedTabContract.TabSize.Large -> {
                            DesignSystemTheme.typography.heading2Bold
                        }

                        WantedTabContract.TabSize.Medium -> {
                            DesignSystemTheme.typography.headline2Bold
                        }

                        else -> {
                            DesignSystemTheme.typography.body2Bold
                        }
                    }
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    onTextLayout?.invoke(textLayoutResult)
                }
            )
        },
        horizontalPadding = 12.dp,
        verticalPadding = if (tabSize == WantedTabContract.TabSize.Large) 14.dp else 12.dp,
        isUseRipple = false,
        rippleColor = colorResource(R.color.transparent),
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
                    tabSize = WantedTabContract.TabSize.Small,
                    title = "텍스트",
                    active = false,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Small,
                    title = "텍스트",
                    active = true,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Medium,
                    title = "텍스트",
                    active = false,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Medium,
                    title = "텍스트",
                    active = true,
                    enable = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Large,
                    title = "텍스트",
                    enable = true,
                    active = false,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Large,
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