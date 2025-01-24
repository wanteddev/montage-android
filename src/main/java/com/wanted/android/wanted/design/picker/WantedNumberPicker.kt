package com.wanted.android.wanted.design.picker

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
fun WantedNumberPicker(
    modifier: Modifier
) {

}

@Composable
fun WantedVerticalNumberPicker(
    modifier: Modifier = Modifier,
    itemList: List<Int>,
    pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        itemList.size
    },
    textStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.heading1Medium
    ),
    itemSize: Dp = with(LocalDensity.current) { textStyle.lineHeight.toDp() },
    visibleCount: Int = 7
) {
    BoxWithConstraints(
        modifier = modifier
            .background(Color.LightGray)
            .height(itemSize * visibleCount),
    ) {
        val padding = (maxHeight - itemSize) * 0.5f
        VerticalPager(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray),
            state = pagerState,
            contentPadding = PaddingValues(
                top = padding,
                bottom = padding
            ),
            pageSize = PageSize.Fixed(itemSize + 20.dp),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(itemList.size),
                snapAnimationSpec = spring(stiffness = Spring.StiffnessHigh)
            ),
            pageContent = { page ->
                PickerContent(
                    modifier = modifier
                        .height(height = itemSize),
                    pagerState = pagerState,
                    page = page,
                    title = itemList.get(index = page).toString()
                )
            }
        )
    }
}

@Composable
fun PickerContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    page: Int,
    title: String
) {
    var offsetY by remember { mutableStateOf(0.dp) }

    Text(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentSize()
            .offset(0.dp, offsetY)
            .graphicsLayer {
                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                val rotation = lerp(0f, 90f, pageOffset / 3)
                if (rotation > 90 || rotation < -90) {
                    rotationX = 90f
                } else {
                    rotationX = rotation
                }

                scaleX = lerp(
                    SELECT_SCALE,
                    DESELECT_SCALE,
                    if (pageOffset >= 0) pageOffset / 3 else -pageOffset / 3
                )
                scaleY = lerp(
                    SELECT_SCALE,
                    DESELECT_SCALE,
                    if (pageOffset >= 0) pageOffset / 3 else -pageOffset / 3
                )


                offsetY = if (pageOffset < 0) {
                    -(8 * (pageOffset * pageOffset)).dp
                } else {
                    (8 * (pageOffset * pageOffset)).dp
                }
                Log.d("_SMY", "graphicsLayer $page $title $offsetY $pageOffset")
            },
        text = title,
        textAlign = TextAlign.Center,
        style = WantedTextStyle(
            colorRes = R.color.label_normal,
            style = DesignSystemTheme.typography.heading1Medium
        )
    )
}

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

private const val SELECT_SCALE = 1f
private const val DESELECT_SCALE = 0.75f

@Composable
fun TiltedText(
    text: String,
    modifier: Modifier = Modifier,
    angle: Float = 45f, // 기울이는 각도
    textStyle: TextStyle = TextStyle(fontSize = 24.sp, color = Color.Black)
) {
    Text(
        text = text,
        style = textStyle,
        modifier = modifier
            .graphicsLayer(
                rotationX = angle // X축 기준으로 기울이기
            )
    )
}

@DevicePreviews
@Composable
private fun WantedNumberPickerPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedVerticalNumberPicker(
                    modifier = Modifier.fillMaxWidth(),
                    itemList = listOf(1, 2, 3, 4, 5)
                )
            }
        }
    }
}