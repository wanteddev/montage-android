package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedContract.SegmentedSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * SegmentedControlOutlined 내 개별 항목을 구성하는 아이템 컴포넌트입니다.
 *
 * 선택 여부에 따라 배경 색상, 테두리 색상, 텍스트 색상이 달라지며,
 * 좌우 끝 항목 여부에 따라 각진 모서리 처리가 적용됩니다.
 * 또한, 필요 시 아이콘을 함께 표시할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSegmentedControlOutlinedItem(
 *     title = "Option",
 *     isSelected = true,
 *     isFirst = true,
 *     isLast = false,
 *     icon = {
 *         Icon(
 *             painter = painterResource(id = R.drawable.ic_info),
 *             contentDescription = null
 *         )
 *     }
 * )
 * ```
 *
 * @param title String: 항목에 표시할 텍스트입니다.
 * @param isSelected Boolean: 항목이 선택된 상태인지 여부입니다.
 * @param modifier Modifier: 외형을 설정하는 Modifier입니다.
 * @param isFirst Boolean: 해당 항목이 첫 번째 항목인지 여부입니다. 좌측 모서리 스타일에 영향을 줍니다.
 * @param isLast Boolean: 해당 항목이 마지막 항목인지 여부입니다. 우측 모서리 스타일에 영향을 줍니다.
 * @param icon (() -> Unit)?: 항목 왼쪽에 표시할 선택적 아이콘 Composable입니다.
 */
@Composable
fun WantedSegmentedControlOutlinedItem(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    icon: @Composable (() -> Unit)? = null
) {
    val textColor = remember(isSelected) {
        mutableIntStateOf(
            if (isSelected) {
                R.color.primary_normal
            } else {
                R.color.label_alternative
            }
        )
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_5)
        } else {
            colorResource(id = R.color.transparent)
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            colorResource(id = R.color.primary_normal).copy(alpha = OPACITY_43)
        } else {
            colorResource(id = R.color.transparent)
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )


    CompositionLocalProvider(
        value = LocalContentColor provides colorResource(id = textColor.intValue)
    ) {
        Row(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = when {
                        isFirst -> RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        isLast -> RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                )
                .background(
                    color = backgroundColor,
                    shape = when {
                        isFirst -> RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        isLast -> RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                )
                .padding(
                    vertical = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> 7.dp
                        SegmentedSize.Medium -> 9.dp
                        SegmentedSize.Large -> 12.dp
                    },
                    horizontal = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> 6.dp
                        SegmentedSize.Medium -> 8.dp
                        SegmentedSize.Large -> 8.dp
                    }
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Box(modifier = Modifier.size(20.dp)) {
                    icon()
                }
            }

            Text(
                modifier = Modifier.wrapContentSize(),
                text = title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = WantedTextStyle(
                    colorRes = if (isSelected) {
                        R.color.primary_normal
                    } else {
                        R.color.label_alternative
                    },
                    style = when (LocalWantedSegmentedSize.current) {
                        SegmentedSize.Small -> DesignSystemTheme.typography.label2Medium
                        SegmentedSize.Medium -> DesignSystemTheme.typography.body2Medium
                        SegmentedSize.Large -> DesignSystemTheme.typography.headline2Medium
                    }
                )
            )
        }

    }
}

@DevicePreviews
@Composable
private fun WantedSegmentedControlOutlinedItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentedControlOutlinedItem(
                    modifier = Modifier,
                    title = "title",
                    isSelected = true
                )

                WantedSegmentedControlOutlinedItem(
                    modifier = Modifier,
                    title = "title",
                    isSelected = false
                )
            }
        }
    }
}