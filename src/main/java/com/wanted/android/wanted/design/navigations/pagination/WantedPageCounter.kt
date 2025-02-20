package com.wanted.android.wanted.design.navigations.pagination

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.navigations.pagination.WantedPaginationContract.WantedPageCounterSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_28
import com.wanted.android.wanted.design.util.OPACITY_35
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=4173-14558&m=dev
 */
@Composable
fun WantedPageCounter(
    modifier: Modifier = Modifier,
    size: WantedPageCounterSize = WantedPageCounterSize.Normal,
    totalPageCount: Int,
    currentIndex: Int,
    isAlternative: Boolean = false
) {
    PageCounterLayout(
        modifier = modifier,
        size = size,
        isAlternative = isAlternative,
        currentIndex = {
            Text(
                text = currentIndex.toString(),
                maxLines = 1,
                style = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = when (size) {
                        WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Bold
                        WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Bold
                    }
                ),
                color = colorResource(R.color.static_white)

            )
        },
        totalPageCount = {
            Text(
                text = totalPageCount.toString(),
                maxLines = 1,
                style = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = WantedTextStyle(
                        colorRes = R.color.label_alternative,
                        style = when (size) {
                            WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Bold
                            WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Bold
                        }
                    )
                ),
                color = colorResource(R.color.static_white)
            )
        }
    )
}

@Composable
private fun PageCounterLayout(
    modifier: Modifier = Modifier,
    size: WantedPageCounterSize,
    isAlternative: Boolean,
    currentIndex: @Composable () -> Unit,
    totalPageCount: @Composable () -> Unit,
) {
    val backgroundModifier = if (isAlternative) {
        Modifier
            .background(colorResource(R.color.cool_neutral_30).copy(OPACITY_61))
    } else {
        Modifier
            .background(colorResource(R.color.static_white).copy(OPACITY_35))
            .background(colorResource(R.color.static_black).copy(OPACITY_28))
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .then(backgroundModifier)
            .padding(
                horizontal = size.paddingHorizontal,
                vertical = size.paddingVertical
            ),
        horizontalArrangement = Arrangement.spacedBy(size.space),
        verticalAlignment = Alignment.CenterVertically
    ) {

        currentIndex()

        Text(
            modifier = Modifier.alpha(if (isAlternative) OPACITY_28 else OPACITY_52),
            text = "/",
            style = WantedTextStyle(
                colorRes = R.color.label_alternative,
                style = when (size) {
                    WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Regular
                    WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Regular
                }
            ),
            color = colorResource(R.color.static_white)
        )

        totalPageCount()
    }
}

@DevicePreviews
@Composable
private fun WantedPageCounterPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedPageCounter(
                    modifier = Modifier,
                    size = WantedPageCounterSize.Normal,
                    totalPageCount = 10,
                    currentIndex = 2,
                    isAlternative = false
                )

                WantedPageCounter(
                    modifier = Modifier,
                    size = WantedPageCounterSize.Normal,
                    totalPageCount = 10,
                    currentIndex = 2,
                    isAlternative = true
                )
            }
        }
    }
}