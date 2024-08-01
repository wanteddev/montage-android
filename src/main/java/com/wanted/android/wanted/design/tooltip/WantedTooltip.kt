package com.wanted.android.wanted.design.tooltip

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CaretProperties
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import kotlinx.coroutines.launch

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-42420&m=dev
 * 설명 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=15860-702&t=ZRVJBpRSRH4Fe1nQ-4
 */
@Composable
fun WantedTooltip(
    modifier: Modifier = Modifier,
    text: String,
    isUseCloseButton: Boolean = false,
    positionProvider: PopupPositionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    state: TooltipState = remember { TooltipState(true) }
) {
    val scope = rememberCoroutineScope()

    TooltipBox(
        modifier = modifier,
        positionProvider = positionProvider,
        tooltip = {
            val density = LocalDensity.current
            val configuration = LocalConfiguration.current
            val customModifier = Modifier
                .drawCaret { anchorLayoutCoordinates ->
                    drawCaretWithPath(
                        density,
                        configuration,
                        Color.Red,
                        TooltipDefaults.caretProperties.copy(caretWidth = 12.dp, caretHeight = 6.dp),
                        anchorLayoutCoordinates
                    )

                }
                .then(modifier)

            WantedTooltipContents(
                modifier = customModifier,
                text = text,
                isUseCloseButton = isUseCloseButton
            )

        },
        state = state,
        content = {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
                    .clickOnceForDesignSystem {
                        scope.launch {
                            state.show()
                        }
                    }
            )
        }
    )
}

@Composable
private fun WantedTooltipContents(
    modifier: Modifier = Modifier,
    text: String,
    isUseCloseButton: Boolean,
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .sizeIn(
                    minWidth = 64.dp,
                    maxWidth = 280.dp
                )
                .padding(SpacingBetweenTooltipAndAnchor.dp)
                .background(colorResource(id = R.color.inverse_background))
                .background(colorResource(id = R.color.primary_normal))
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
        }

        if (isUseCloseButton) {

        }
    }
}

@ExperimentalMaterial3Api
private fun CacheDrawScope.drawCaretWithPath(
    density: Density,
    configuration: Configuration,
    containerColor: Color,
    caretProperties: CaretProperties,
    anchorLayoutCoordinates: LayoutCoordinates?
): DrawResult {
    val path = Path()

    if (anchorLayoutCoordinates != null) {
        val caretHeightPx: Int
        val caretWidthPx: Int
        val screenWidthPx: Int
        val tooltipAnchorSpacing: Int
        val anchorSize: Float
        with(density) {
            caretHeightPx = caretProperties.caretHeight.roundToPx()
            caretWidthPx = caretProperties.caretWidth.roundToPx()
            screenWidthPx = configuration.screenWidthDp.dp.roundToPx()
            tooltipAnchorSpacing = SpacingBetweenTooltipAndAnchor.dp.roundToPx()
            anchorSize = 1.dp.roundToPx() / 2f
        }
        val anchorBounds = anchorLayoutCoordinates.boundsInWindow()
        val anchorLeft = anchorBounds.left
        val anchorRight = anchorBounds.right
        val anchorTop = anchorBounds.top
        val anchorMid = (anchorRight + anchorLeft) / 2
        val anchorWidth = anchorRight - anchorLeft
        val tooltipWidth = this.size.width
        val tooltipHeight = this.size.height
        val isCaretTop = anchorTop - tooltipHeight - tooltipAnchorSpacing < 0
        val caretY = if (isCaretTop) {
            0f
        } else {
            tooltipHeight
        }

        val position =
            if (anchorMid + tooltipWidth / 2 > screenWidthPx) {
                val anchorMidFromRightScreenEdge =
                    screenWidthPx - anchorMid
                val caretX = tooltipWidth - anchorMidFromRightScreenEdge
                Offset(caretX, caretY)
            } else {
                val tooltipLeft =
                    anchorLeft - (this.size.width / 2 - anchorWidth / 2)
                val caretX = anchorMid - maxOf(tooltipLeft, 0f)
                Offset(caretX, caretY)
            }



        if (isCaretTop) {
            path.apply {
                moveTo(x = position.x, y = position.y)
                lineTo(x = position.x + caretWidthPx / 2, y = position.y)
                lineTo(x = position.x, y = position.y - caretHeightPx + caretHeightPx / 2)
                lineTo(x = position.x - caretWidthPx / 2, y = position.y)
                lineTo(x = position.x, y = position.y)
                close()
            }
        } else {
            path.apply {
                moveTo(x = position.x, y = position.y)
                lineTo(x = position.x + caretWidthPx / 2, y = position.y)

                lineTo(x = position.x + anchorSize, y = position.y + caretHeightPx - anchorSize)

                arcTo(
                    rect = Rect(
                        left = position.x - anchorSize,
                        top = position.y + caretHeightPx,
                        right = position.x + anchorSize,
                        bottom = position.y + caretHeightPx
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )

//                lineTo(x = position.x, y = position.y + caretHeightPx / 2)

                lineTo(x = position.x - anchorSize, y = position.y + caretHeightPx - anchorSize)

                lineTo(x = position.x - caretWidthPx / 2, y = position.y)



                close()
            }
        }
    }

    return onDrawWithContent {
        if (anchorLayoutCoordinates != null) {
            drawContent()
            drawPath(
                path = path,
                color = containerColor
            )
        }
    }
}

private const val SpacingBetweenTooltipAndAnchor = 8