package com.wanted.android.wanted.design.contents.card

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

// 카드 썸네일 이미지를 테두리 안쪽 선에 맞춰 자르는 Shape 입니다.
//
// 썸네일 영역은 바깥 반지름 [cornerRadius](가이드 12dp)의 둥근 테두리를 [borderWidth] 두께로 그립니다.
// 이미지를 테두리와 같은 바깥 선으로 자르면, 반쯤만 칠해지는 둥근 모서리의 바깥 픽셀에 어두운 이미지가 섞여
// 테두리 바깥에 회색 띠가 생기고 선이 떠 보입니다. 이미지를 테두리 두께만큼 안쪽에서 자르면 바깥 가장자리에는
// 배경과 테두리만 남아 띠가 사라집니다.
//
// 테두리가 불투명해 원래도 이미지 바깥 [borderWidth] 만큼은 가려져 있었으므로 보이는 이미지 영역은 같고,
// 크기·레이아웃도 바뀌지 않습니다. 안쪽 반지름은 바깥 반지름에서 테두리 두께를 뺀 값(12dp - 1dp = 11dp)으로,
// 테두리 안쪽 선과 같은 중심을 공유합니다. 화면에 보이는 카드 모서리는 테두리 바깥 선이라 가이드 12dp 그대로입니다.
internal class WantedThumbnailClipShape(
    private val cornerRadius: Dp,
    private val borderWidth: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val inset = with(density) { borderWidth.toPx() }
        val radius = with(density) { (cornerRadius - borderWidth).toPx() }.coerceAtLeast(0f)
        return Outline.Rounded(
            RoundRect(
                left = inset,
                top = inset,
                right = size.width - inset,
                bottom = size.height - inset,
                cornerRadius = CornerRadius(radius)
            )
        )
    }
}
