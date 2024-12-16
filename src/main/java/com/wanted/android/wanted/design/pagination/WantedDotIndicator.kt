package com.wanted.android.wanted.design.pagination

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_8

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

fun PagerState.calculateCurrentOffsetForPage1(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}


private const val SELECT_SCALE = 1f
private const val DESELECT_SCALE_80 = 0.8f
private const val DESELECT_SCALE_60 = 0.6f
//
@Composable
fun WantedDotIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    pagerState: PagerState
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(pageCount) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(8.dp)
                    .background(
                        color = if (pagerState.calculateCurrentOffsetForPage(it).toInt() == it) {
                            colorResource(id = R.color.label_normal)
                        } else {
                            colorResource(id = R.color.label_normal).copy(OPACITY_43)
                        },
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.label_normal).copy(OPACITY_8),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun WantedDotIndicator(
    modifier: Modifier = Modifier,
    visibleCount: Int,
    currentIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(visibleCount) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(8.dp)
                    .background(
                        color = if (currentIndex == it) {
                            colorResource(id = R.color.label_normal)
                        } else {
                            colorResource(id = R.color.label_normal).copy(OPACITY_43)
                        },
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.label_normal).copy(OPACITY_8),
                        shape = CircleShape
                    )
            )
        }
    }
}

private fun getCurrentIndex(
    totalCount:
) {

}

/**
 * 배너를 무한 스크롤 하기 위한 마지막 더미 페이지 인지 확인
 */
private fun isAfterLastPage(itemCount: Int, currentPage: Int): Boolean {
    return itemCount + 1 == currentPage
}

private fun isBeforeFistPage(currentPage: Int): Boolean {
    return currentPage == 0
}

private fun getLastPageIndex(itemCount: Int): Int {
    return itemCount - 1
}
@Composable
private fun getCurrentIndex(itemCount: Int, currentPage: Int): Int {
    return when {
        isAfterLastPage(itemCount, currentPage) -> 0
        isBeforeFistPage(currentPage) -> getLastPageIndex(itemCount)
        else -> currentPage - 1
    }
}


@DevicePreviews
@Composable
private fun WantedDotIndicatorPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
//                WantedDotIndicator(
//                    pagerState = rememberPagerState(initialPage = 0) { 10 }
//                )
            }
        }
    }
}