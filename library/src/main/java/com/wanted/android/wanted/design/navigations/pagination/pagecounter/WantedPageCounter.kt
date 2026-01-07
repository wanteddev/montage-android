package com.wanted.android.wanted.design.navigations.pagination.pagecounter

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
import com.wanted.android.wanted.design.navigations.pagination.pagecounter.WantedPaginationCounterDefaults.WantedPageCounterSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_28
import com.wanted.android.wanted.design.util.OPACITY_35
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.OPACITY_61


/**
 * WantedPageCounter
 *
 * 현재 페이지와 전체 페이지 수를 표시하는 컴포넌트입니다.
 *
 * n/n 형식으로 페이지 정보를 표시하며, Normal과 Alternative 스타일을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * var currentPage by remember { mutableIntStateOf(1) }
 *
 * WantedPageCounter(
 *     totalPageCount = 10,
 *     currentIndex = currentPage,
 *     isAlternative = true
 * )
 * ```
 *
 * @param totalPageCount Int: 전체 페이지 수입니다.
 * @param currentIndex Int: 현재 페이지 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param size WantedPageCounterSize: 컴포넌트의 크기입니다.
 * @param isAlternative Boolean: alternative 배경 스타일 적용 여부입니다.
 */
@Composable
fun WantedPageCounter(
    totalPageCount: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    size: WantedPageCounterSize = WantedPageCounterSize.Normal,
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
                style = when (size) {
                    WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Bold
                    WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Bold
                },
                color = DesignSystemTheme.colors.staticWhite

            )
        },
        totalPageCount = {
            Text(
                text = totalPageCount.toString(),
                maxLines = 1,
                style = when (size) {
                    WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Bold
                    WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Bold
                },
                color = DesignSystemTheme.colors.staticWhite
            )
        }
    )
}

@Composable
private fun PageCounterLayout(
    size: WantedPageCounterSize,
    isAlternative: Boolean,
    currentIndex: @Composable () -> Unit,
    totalPageCount: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundModifier = if (isAlternative) {
        Modifier
            .background(colorResource(R.color.cool_neutral_30).copy(OPACITY_61))
    } else {
        Modifier
            .background(DesignSystemTheme.colors.staticWhite.copy(OPACITY_35))
            .background(DesignSystemTheme.colors.staticBlack.copy(OPACITY_28))
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
            style = when (size) {
                WantedPageCounterSize.Small -> DesignSystemTheme.typography.label2Regular
                WantedPageCounterSize.Normal -> DesignSystemTheme.typography.body2Regular
            },
            color = DesignSystemTheme.colors.staticWhite
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