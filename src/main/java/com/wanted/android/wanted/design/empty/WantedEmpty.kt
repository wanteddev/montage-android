package com.wanted.android.wanted.design.empty

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=22838-255809&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=3263-262&m=dev
 */
@Composable
fun WantedEmpty(
    modifier: Modifier,
    illustration: @Composable (() -> Unit)? = null,
    heading: String? = null,
    description: String? = null,
    button: String? = null,
    onClick: () -> Unit = {}
) {
    WantedEmptyLayout(
        modifier = modifier.fillMaxWidth(),
        illustration = illustration,
        heading = heading?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        description = description?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        button = button?.let {
            {
                WantedButton(
                    text = it,
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.ASSISTIVE,
                    size = ButtonSize.LARGE,
                    onClick = onClick
                )
            }
        }
    )
}

@Composable
private fun WantedEmptyLayout(
    modifier: Modifier,
    illustration: @Composable (() -> Unit)? = null,
    heading: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    button: @Composable (() -> Unit)? = null,
) {

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 12.dp)
            .padding(top = illustration?.let { 0.dp } ?: 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        illustration?.let {
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                illustration()
            }
        }

        heading?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            ) {
                heading()
            }
        }

        description?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.body1ReadingRegular
                )
            ) {
                description()
            }
        }

        button?.let {
            Box(modifier = Modifier.padding(top = 12.dp)) {
                button()
            }
        }

        illustration?.let {
            Spacer(modifier = Modifier.padding(20.dp))
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
private fun WantedEmptyPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedEmpty(
                    modifier = Modifier,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                        "설명은 최대 두 줄로 작성해요.",
                    button = "텍스트",
                    onClick = {}

                )
            }
        }
    }
}