package com.wanted.android.wanted.design.loading.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedSkeletonText
 *
 * 텍스트를 표현하는 Skeleton 컴포넌트입니다.
 *
 * 다양한 길이 비율과 정렬 방식을 지원하며, Shimmer 애니메이션이 자동으로 적용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSkeletonText(
 *     length = WantedSkeletonLength.Ratio75,
 *     align = WantedSkeAlign.Left
 * )
 * ```
 *
 * @param length WantedSkeletonLength: 텍스트 길이 비율입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param align WantedSkeAlign: 정렬 방식입니다.
 * @param shape RoundedCornerShape: 모서리 곡률입니다.
 */
@Composable
fun WantedSkeletonText(
    length: WantedSkeletonLength,
    modifier: Modifier = Modifier,
    align: WantedSkeAlign = WantedSkeAlign.Left,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp)
) {
    WantedSkeletonText(
        modifier = modifier.defaultMinSize(minHeight = 22.dp),
        align = align,
        widthRadio = length.radio,
        shape = shape
    )
}

/**
 * WantedSkeletonText
 *
 * 커스텀 비율 기반의 텍스트를 표현하는 Skeleton 컴포넌트입니다.
 *
 * widthRadio 파라미터를 통해 너비 비율을 직접 지정할 수 있습니다.
 *
 * @param widthRadio Float: 텍스트 박스의 너비 비율입니다. 0.0 ~ 1.0 사이의 값을 사용합니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param align WantedSkeAlign: 정렬 방식입니다.
 * @param shape RoundedCornerShape: 스켈레톤 박스의 모서리 곡률입니다.
 * @param color Color: 스켈레톤 박스의 배경 색상입니다.
 */
@Composable
fun WantedSkeletonText(
    widthRadio: Float,
    modifier: Modifier = Modifier,
    align: WantedSkeAlign = WantedSkeAlign.Left,
    shape: RoundedCornerShape = RoundedCornerShape(3.dp),
    color: Color = DesignSystemTheme.colors.fillNormal
) {
    ConstraintLayout(
        modifier = modifier
            .defaultMinSize(minHeight = 22.dp)
            .fillMaxWidth()
    ) {
        val (row) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(row) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints  // Match width of text
                    height = Dimension.fillToConstraints // Match height of text
                }
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .wrapContentHeight(),
            horizontalArrangement = when (align) {
                WantedSkeAlign.Left -> Arrangement.Start
                WantedSkeAlign.Center -> Arrangement.Center
                WantedSkeAlign.Right -> Arrangement.End
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(widthRadio)
                    .fillMaxHeight()
                    .clip(shape)
                    .background(color = color)
                    .shimmer()

            )
        }

    }

}

/**
 * enum class WantedSkeletonLength
 *
 * 텍스트형 스켈레톤의 너비 비율을 정의하는 enum 클래스입니다.
 * - Ratio100: 100% 너비 비율입니다.
 * - Ratio75: 75% 너비 비율입니다.
 * - Ratio50: 50% 너비 비율입니다.
 * - Ratio25: 25% 너비 비율입니다.
 */
enum class WantedSkeletonLength(
    val radio: Float
) {
    Ratio100(radio = 1f),
    Ratio75(radio = 0.75f),
    Ratio50(radio = 0.5f),
    Ratio25(radio = 0.25f)
}

/**
 * enum class WantedSkeAlign
 *
 * 텍스트형 스켈레톤의 정렬 방식을 정의하는 enum 클래스입니다.
 * - Left: 왼쪽 정렬입니다.
 * - Center: 가운데 정렬입니다.
 * - Right: 오른쪽 정렬입니다.
 */
enum class WantedSkeAlign {
    Left,
    Center,
    Right,
}

@DevicePreviews
@Composable
fun WantedSkeletonPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio100,
                    align = WantedSkeAlign.Left,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio75,
                    align = WantedSkeAlign.Left,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio50,
                    align = WantedSkeAlign.Right,
                )

                WantedSkeletonText(
                    length = WantedSkeletonLength.Ratio25,
                    align = WantedSkeAlign.Center,
                )
            }
        }

    }
}
