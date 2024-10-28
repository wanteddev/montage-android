package com.wanted.android.wanted.design.card

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
fun WantedCard(
    modifier: Modifier = Modifier,
    isList: Boolean = false
) {

}

@Composable
private fun WantedCardLayoutVertical(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
) {

}

@Composable
private fun WantedCardLayoutHorizontal(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
) {

}


@Composable
private fun WantedCardDescriptionLayout(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    cation: @Composable (() -> Unit)? = null,
    extraCaption: @Composable (() -> Unit)? = null,
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null
) {

}

@Composable
fun WantedThumbnailOverly(
    modifier: Modifier = Modifier,
    title: String? = null,
    toggleIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.Top,
    ) {

        title?.let {
            Text(
                modifier = Modifier.weight(1f, fill = false),
                text = title
            )
        }

        toggleIcon?.invoke()
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
private fun WantedCardPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

            }
        }
    }
}