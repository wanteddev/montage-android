package com.wanted.android.wanted.design.tab

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
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedTabItem(
    modifier: Modifier = Modifier,
    tabSize: WantedTabContract.TabSize,
    title: String,
    isSelect: Boolean,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: () -> Unit
) {
    WantedTouchArea(
        modifier = modifier,
        content = {
            Text(
                modifier = Modifier,
                text = title,
                style = WantedTextStyle(
                    colorRes = if (isSelect) {
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
                    isSelect = false,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Small,
                    title = "텍스트",
                    isSelect = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Medium,
                    title = "텍스트",
                    isSelect = false,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Medium,
                    title = "텍스트",
                    isSelect = true,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Large,
                    title = "텍스트",
                    isSelect = false,
                    onClick = {},
                    onTextLayout = {}
                )

                WantedTabItem(
                    modifier = Modifier,
                    tabSize = WantedTabContract.TabSize.Large,
                    title = "텍스트",
                    isSelect = true,
                    onClick = {},
                    onTextLayout = {}
                )
            }
        }
    }
}