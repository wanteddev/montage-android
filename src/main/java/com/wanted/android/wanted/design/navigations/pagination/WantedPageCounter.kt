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
import com.wanted.android.wanted.design.navigations.pagination.WantedPaginationContract.WantedPageCounterSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_28
import com.wanted.android.wanted.design.util.OPACITY_35
import com.wanted.android.wanted.design.util.OPACITY_52
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.WantedTextStyle


/**
 * 현재 페이지와 전체 페이지 수를 함께 표시하는 페이지 카운터 컴포저블입니다.
 *
 * `2 / 10` 형식으로 페이지 정보를 출력하며, 일반형과 alternative 스타일(투명 배경) 중 선택할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedPageCounter(
 *     totalPageCount = 10,
 *     currentIndex = 2,
 *     isAlternative = true
 * )
 * ```
 *
 * @param totalPageCount Int: 전체 페이지 수입니다.
 * @param currentIndex Int: 현재 페이지 인덱스입니다.
 * @param modifier Modifier: 배치 및 외형 설정을 위한 Modifier입니다.
 * @param size WantedPageCounterSize: 크기 설정입니다 (Small 또는 Normal).
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