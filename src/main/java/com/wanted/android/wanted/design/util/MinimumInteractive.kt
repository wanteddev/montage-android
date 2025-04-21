package com.wanted.android.wanted.design.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

internal class MinimumInteractiveModifier(
    val verticalPadding: Dp,
    val horizontalPadding: Dp
) : ModifierNodeElement<MinimumInteractiveModifierNode>() {

    override fun create(): MinimumInteractiveModifierNode =
        MinimumInteractiveModifierNode(verticalPadding, horizontalPadding)

    override fun update(node: MinimumInteractiveModifierNode) {}

    override fun InspectorInfo.inspectableProperties() {
        name = "minimumInteractiveComponentSize"
        // TODO: b/214589635 - surface this information through the layout inspector in a better way
        //  - for now just add some information to help developers debug what this size represents.
        properties["README"] =
            "Reserves at least 48.dp in size to disambiguate touch " +
                    "interactions if the element would measure smaller"
    }

    override fun hashCode(): Int = System.identityHashCode(this)

    override fun equals(other: Any?) = (other === this)
}

internal class MinimumInteractiveModifierNode(
    val verticalPadding: Dp,
    val horizontalPadding: Dp
) : Modifier.Node(), CompositionLocalConsumerModifierNode, LayoutModifierNode {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        // 추가 패딩을 픽셀로 변환
        val vPadPx = verticalPadding.roundToPx()
        val hPadPx = horizontalPadding.roundToPx()

        // 콘텐츠를 측정
        val placeable = measurable.measure(constraints)

        // 터치 가능한 영역: 콘텐츠 + 2*패딩, 최소 크기 준수
        val targetWidth = placeable.width + 2 * hPadPx
        val targetHeight = placeable.height + 2 * vPadPx

        return layout(targetWidth, targetHeight) {
            // 콘텐츠를 가운데 배치하되, 패딩만큼 오프셋
            val xOffset = hPadPx + (targetWidth - 2 * hPadPx - placeable.width) / 2
            val yOffset = vPadPx + (targetHeight - 2 * vPadPx - placeable.height) / 2
            placeable.placeRelative(xOffset, yOffset)
        }
    }
}

