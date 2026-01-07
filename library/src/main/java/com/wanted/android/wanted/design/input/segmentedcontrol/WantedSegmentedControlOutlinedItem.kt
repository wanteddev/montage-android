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
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_5

/**
 * WantedSegmentedControlOutlinedItem
 *
 * SegmentedControlOutlined 내 개별 항목을 구성하는 컴포넌트입니다.
 *
 * 선택 여부에 따라 배경 색상, 테두리 색상, 텍스트 색상이 변경되며,
 * 첫 번째 또는 마지막 항목인 경우 모서리가 둥글게 처리됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSegmentedControlOutlinedItem(
 *     title = "옵션",
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
 * @param isSelected Boolean: 항목의 선택 여부입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param isFirst Boolean: 첫 번째 항목 여부입니다. true인 경우 좌측 모서리가 둥글게 처리됩니다.
 * @param isLast Boolean: 마지막 항목 여부입니다. true인 경우 우측 모서리가 둥글게 처리됩니다.
 * @param icon (@Composable () -> Unit)?: 항목 왼쪽에 표시할 선택적 아이콘 Composable입니다.
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
            DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_5)
        } else {
            DesignSystemTheme.colors.transparent
        },
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            DesignSystemTheme.colors.primaryNormal.copy(alpha = OPACITY_43)
        } else {
            DesignSystemTheme.colors.transparent
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
                style = when (LocalWantedSegmentedSize.current) {
                    SegmentedSize.Small -> DesignSystemTheme.typography.label2Medium
                    SegmentedSize.Medium -> DesignSystemTheme.typography.body2Medium
                    SegmentedSize.Large -> DesignSystemTheme.typography.headline2Medium
                },
                color = if (isSelected) {
                    DesignSystemTheme.colors.primaryNormal
                } else {
                    DesignSystemTheme.colors.labelAlternative
                }
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