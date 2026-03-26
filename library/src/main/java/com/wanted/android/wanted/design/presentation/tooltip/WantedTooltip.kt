package com.wanted.android.wanted.design.presentation.tooltip

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.OPACITY_88
import com.wanted.android.wanted.design.util.clickOnce
import kotlinx.coroutines.launch

/**
 * WantedTooltip
 *
 * 커스텀 Tooltip 컴포넌트입니다.
 *
 * 앵커 요소 주변에 텍스트를 표시하며, 화면 경계를 고려하여 자동으로 위치를 조정합니다.
 * Arrow를 통해 앵커와의 연관성을 시각적으로 표현합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val tooltipState = rememberTooltipState()
 *
 * WantedTooltip(
 *     modifier = Modifier.padding(16.dp),
 *     tooltipState = tooltipState,
 *     text = "이것은 도움말 텍스트입니다.",
 *     size = WantedTooltipSize.Medium,
 *     align = WantedTooltipAlign.Center
 * ) {
 *     Icon(
 *         painter = painterResource(id = R.drawable.ic_help),
 *         contentDescription = "도움말",
 *         modifier = Modifier.clickable { tooltipState.show() }
 *     )
 * }
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param tooltipState WantedTooltipState: Tooltip 의 표시/숨김 상태를 관리하는 객체입니다.
 * @param text String: Tooltip 에 표시할 텍스트입니다. 최대 3줄까지 표시되며, 초과 시 말줄임표로 처리됩니다.
 * @param size WantedTooltipSize: Tooltip 의 크기입니다.
 * @param align WantedTooltipAlign: 앵커 요소에 대한 Tooltip 의 정렬 방식입니다.
 * @param always Boolean: 외부 클릭으로 닫히지 않도록 할지 여부입니다.
 * @param positionTop Boolean: Tooltip 을 위쪽에 표시할지 여부입니다.
 * @param content (@Composable () -> Unit): Tooltip 을 트리거하는 앵커 콘텐츠 슬롯입니다.
 */
@Composable
fun WantedTooltip(
    modifier: Modifier,
    tooltipState: WantedTooltipState = rememberTooltipState(),
    text: String,
    size: WantedTooltipSize = WantedTooltipSize.Medium,
    align: WantedTooltipAlign = WantedTooltipAlign.Left,
    always: Boolean = false,
    positionTop: Boolean = false,
    content: @Composable () -> Unit
) {
    val isVisible by tooltipState.visibleState
    var isShow by remember { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        isShow = isVisible
    }

    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val backgroundColor = DesignSystemTheme.colors.backgroundNormalNormal
    val color = DesignSystemTheme.colors.inverseBackground.copy(OPACITY_88)
    val color1 = DesignSystemTheme.colors.primaryNormal.copy(OPACITY_5)

    var contentPositionY by remember { mutableFloatStateOf(0f) }
    var contentPositionX by remember { mutableFloatStateOf(0f) }
    var contentPositionYInWindow by remember { mutableFloatStateOf(0f) }
    var contentHeight by remember { mutableIntStateOf(0) }
    var contentWidth by remember { mutableIntStateOf(0) }

    var tooltipWidth by remember { mutableIntStateOf(0) }
    var tooltipHeight by remember { mutableIntStateOf(0) }
    var tooltipOffsetX by remember { mutableIntStateOf(0) }

    var caretPositionX by remember { mutableFloatStateOf(0f) }

    var isPopupAbove by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            val positionWindow = coordinates.positionInWindow()
            contentPositionY = coordinates.positionInParent().y
            contentPositionYInWindow = positionWindow.y
            contentPositionX = positionWindow.x
            contentHeight = coordinates.size.height
            contentWidth = coordinates.size.width

            if (positionWindow.x < 0) {
                isShow = false
            } else {
                if (tooltipState.isVisible) {
                    isShow = true
                }
            }
        }
    ) {
        content()

        if (isShow) {
            val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
            val spaceBelow =
                with(density) { screenHeight.dp.toPx() } - (contentPositionYInWindow + contentHeight)
            val spaceAbove = contentPositionYInWindow

            isPopupAbove = if (positionTop) {
                true
            } else {
                spaceBelow < estimatedTooltipHeight + SpacingBetweenTooltipAndAnchor &&
                        spaceAbove > estimatedTooltipHeight + SpacingBetweenTooltipAndAnchor
            }

            tooltipOffsetX = calculateTooltipOffsetX(
                align = align,
                contentPositionX = contentPositionX,
                contentWidth = contentWidth,
                tooltipWidth = tooltipWidth,
                screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt(),
                paddingPx = with(density) { 2.dp.toPx() }.toInt()
            )

            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = tooltipOffsetX,
                    y = if (isPopupAbove) {
                        -tooltipHeight - SpacingBetweenTooltipAndAnchor
                    } else {
                        contentPositionY.toInt() + contentHeight + SpacingBetweenTooltipAndAnchor
                    }
                ),
                properties = PopupProperties(
                    focusable = !always,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
                onDismissRequest = {
                    if (!always) {
                        tooltipState.dismiss()
                    }
                }
            ) {
                WantedTooltipLayout(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            tooltipWidth = coordinates.size.width
                            tooltipHeight = coordinates.size.height

                            caretPositionX = calculateCaretPositionX(
                                align = align,
                                contentWidth = contentWidth,
                                tooltipOffsetX = tooltipOffsetX,
                                caretPaddingHorizontalPx = with(density) {
                                    if (size == WantedTooltipSize.Small) 1.dp.toPx() else 6.dp.toPx()
                                },
                                caretWidthPx = with(density) { 12.dp.toPx() }
                            )
                        }
                        .drawWithCache {
                            drawCaret(
                                context = context,
                                size = size,
                                backgroundColor = backgroundColor,
                                color = color,
                                color1 = color1,
                                isPopupAbove = isPopupAbove,
                                caretPositionX = caretPositionX
                            )
                        },
                    spacingBetweenTooltipAndAnchor = SpacingBetweenTooltipAndAnchor.dp,
                    size = size,
                    text = {
                        Text(
                            text = text,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
    }
}

private fun CacheDrawScope.drawCaret(
    context: Context,
    size: WantedTooltipSize,
    backgroundColor: Color,
    color: Color,
    color1: Color,
    isPopupAbove: Boolean,
    caretPositionX: Float
): DrawResult {
    val caretWidthPx =
        with(density) { if (size == WantedTooltipSize.Small) 14.dp.roundToPx() else 20.dp.roundToPx() }
    val caretHeightPx =
        with(density) { if (size == WantedTooltipSize.Small) 6.dp.roundToPx() else 8.dp.roundToPx() }

    val originalBitmap = drawableResourceToBitmap(
        context,
        if (size == WantedTooltipSize.Small) R.drawable.icon_tooltip_arrow_small else R.drawable.icon_tooltip_arrow_medium,
        caretWidthPx,
        caretHeightPx
    )

    val drawOffset = Offset(
        x = caretPositionX - caretWidthPx / 2,
        y = if (!isPopupAbove) {
            with(density) {
                SpacingBetweenTooltipAndAnchor.dp.roundToPx().toFloat()
            } - caretHeightPx
        } else {
            this.size.height - with(density) {
                SpacingBetweenTooltipAndAnchor.dp.roundToPx().toFloat()
            }
        }
    )

    return onDrawWithContent {
        val rectSize = Size(
            width = originalBitmap.width.toFloat(),
            height = originalBitmap.height.toFloat()
        )

        drawRect(
            color = backgroundColor,
            topLeft = drawOffset,
            size = rectSize
        )

        drawRect(
            color = color,
            topLeft = drawOffset,
            size = rectSize
        )

        drawRect(
            color = color1,
            topLeft = drawOffset,
            size = rectSize
        )

        // 화살표 모양으로 마스킹
        if (isPopupAbove) {
            // isPopupAbove가 true일 때 180도 회전
            val center = drawOffset + Offset(rectSize.width / 2, rectSize.height / 2)
            withTransform({
                rotate(180f, center)
            }) {
                drawImage(
                    image = originalBitmap.asImageBitmap(),
                    topLeft = drawOffset,
                    blendMode = BlendMode.DstIn
                )
            }
        } else {
            drawImage(
                image = originalBitmap.asImageBitmap(),
                topLeft = drawOffset,
                blendMode = BlendMode.DstIn
            )
        }

        drawContent()
    }
}

private fun calculateTooltipOffsetX(
    align: WantedTooltipAlign,
    contentPositionX: Float,
    contentWidth: Int,
    tooltipWidth: Int,
    screenWidthPx: Int,
    paddingPx: Int
): Int {
    if (tooltipWidth == 0) return 0

    val idealOffsetX = when (align) {
        WantedTooltipAlign.Left -> {
            0
        }

        WantedTooltipAlign.Center -> {
            (contentWidth - tooltipWidth) / 2
        }

        WantedTooltipAlign.Right -> {
            contentWidth - tooltipWidth
        }
    }

    val tooltipAbsoluteLeft = contentPositionX + idealOffsetX
    val tooltipAbsoluteRight = tooltipAbsoluteLeft + tooltipWidth

    val adjustedOffsetX = when {
        tooltipAbsoluteLeft < paddingPx -> {
            (paddingPx - contentPositionX).toInt()
        }

        tooltipAbsoluteRight > screenWidthPx - paddingPx -> {
            (screenWidthPx - paddingPx - tooltipWidth - contentPositionX).toInt()
        }

        else -> {
            idealOffsetX
        }
    }


    return adjustedOffsetX
}

private fun calculateCaretPositionX(
    align: WantedTooltipAlign,
    contentWidth: Int,
    tooltipOffsetX: Int,
    caretPaddingHorizontalPx: Float,
    caretWidthPx: Float
): Float {
    val center = contentWidth / 2f
    // caret의 중심점이 가리켜야 할 content 상의 위치
    val contentAnchorX = when (align) {
        WantedTooltipAlign.Left -> {
            val result = caretPaddingHorizontalPx + caretWidthPx
            if (result > center) center else result
        }

        WantedTooltipAlign.Center -> {
            // 중앙 정렬: content의 정중앙
            center
        }

        WantedTooltipAlign.Right -> {
            val result = contentWidth - caretPaddingHorizontalPx - caretWidthPx
            if (result < center) center else result
        }
    }

    return contentAnchorX - tooltipOffsetX
}

@Composable
private fun WantedTooltipLayout(
    modifier: Modifier = Modifier,
    spacingBetweenTooltipAndAnchor: Dp,
    size: WantedTooltipSize,
    text: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = if (size == WantedTooltipSize.Small) 36.dp else 64.dp,
                maxWidth = 296.dp
            )
            .padding(vertical = spacingBetweenTooltipAndAnchor)
            .clip(RoundedCornerShape(if (size == WantedTooltipSize.Small) 6.dp else 8.dp))
            .background(DesignSystemTheme.colors.backgroundNormalNormal)
            .background(DesignSystemTheme.colors.inverseBackground.copy(OPACITY_88))
            .background(DesignSystemTheme.colors.primaryNormal.copy(OPACITY_5))
            .padding(
                horizontal = if (size == WantedTooltipSize.Small) 8.dp else 10.dp,
                vertical = if (size == WantedTooltipSize.Small) 5.dp else 10.dp
            )
            .padding(horizontal = if (size == WantedTooltipSize.Small) 0.dp else 2.dp)
    ) {
        ProvideTextStyle(
            value = if (size == WantedTooltipSize.Small) {
                DesignSystemTheme.typography.caption2Medium
            } else {
                DesignSystemTheme.typography.label1Medium
            }.copy(
                DesignSystemTheme.colors.inverseLabel
            )
        ) {
            text()
        }
    }
}

private fun drawableResourceToBitmap(
    context: Context,
    drawableRes: Int,
    width: Int,
    height: Int
): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
        ?: throw IllegalArgumentException("Drawable resource not found: $drawableRes")

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)
    return bitmap
}

@Deprecated(
    message = "Use the new WantedTooltip with WantedTooltipState instead",
    replaceWith = ReplaceWith(
        "WantedTooltip(modifier, rememberTooltipState(), text, content = content)",
        "com.wanted.android.wanted.design.presentation.tooltip.rememberTooltipState"
    )
)
@Composable
fun WantedTooltip(
    text: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    isShowCloseButton: Boolean = false,
    isShowArrow: Boolean = true,
    state: TooltipState = remember { TooltipState(false) },
    onClickAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var anchorLayoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val backgroundColor = DesignSystemTheme.colors.backgroundNormalNormal
    val color = DesignSystemTheme.colors.inverseBackground.copy(OPACITY_88)
    val color1 = DesignSystemTheme.colors.primaryNormal.copy(OPACITY_5)

    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            val density = LocalDensity.current
            val configuration = LocalConfiguration.current
            val customModifier = if (isShowArrow) {
                Modifier
                    .drawCaret(anchorLayoutCoordinates) {
                        drawCaretWithPath(
                            density = density,
                            configuration = configuration,
                            spacingBetweenTooltipAndAnchor = SpacingBetweenTooltipAndAnchor.dp,
                            backgroundColor = backgroundColor,
                            containerColor = color,
                            containerColor1 = color1,
                            dpSize = DpSize(
                                width = 12.dp,
                                height = 6.dp
                            ),
                            anchorLayoutCoordinates = anchorLayoutCoordinates
                        )
                    }
                    .then(modifier)

            } else {
                modifier
            }

            WantedTooltipContentsLayout(
                modifier = customModifier,
                spacingBetweenTooltipAndAnchor = if (isShowArrow) {
                    SpacingBetweenTooltipAndAnchor.dp
                } else {
                    SpacingBetweenTooltipAndAnchorNotArrow.dp
                },
                text = {
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        text = text,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = DesignSystemTheme.typography.label1Medium,
                        color = DesignSystemTheme.colors.inverseLabel
                    )

                },
                onClose = if (isShowCloseButton) {
                    {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .clickOnce {
                                    scope.launch {
                                        state.dismiss()
                                    }
                                }
                                .padding(2.dp),
                            painter = painterResource(id = R.drawable.icon_normal_close),
                            tint = DesignSystemTheme.colors.inverseLabel.copy(alpha = OPACITY_61),
                            contentDescription = ""
                        )
                    }
                } else null,
                action = action?.let {
                    {
                        WantedButton(
                            modifier = Modifier,
                            text = it,
                            buttonDefault = WantedButtonDefaults.getDefault(
                                variant = ButtonVariant.TEXT,
                                type = ButtonType.ASSISTIVE,
                                size = ButtonSize.SMALL,
                            ).copy(
                                contentColor = DesignSystemTheme.colors.inverseLabel.copy(alpha = OPACITY_61)
                            ),
                            onClick = {
                                onClickAction?.invoke()
                            }
                        )
                    }
                }
            )
        },
        state = state,
        content = {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .onGloballyPositioned { coordinates ->
                        anchorLayoutCoordinates = coordinates
                    }
                    .clickOnce {
                        scope.launch {
                            state.show()
                        }
                    }
            ) {
                content()
            }
        }
    )
}

@Composable
private fun WantedTooltipContentsLayout(
    modifier: Modifier = Modifier,
    spacingBetweenTooltipAndAnchor: Dp,
    text: @Composable () -> Unit,
    onClose: @Composable (() -> Unit)?,
    action: @Composable (() -> Unit)?
) {
    val backgroundColor = DesignSystemTheme.colors.backgroundNormalNormal
    val color = DesignSystemTheme.colors.inverseBackground.copy(OPACITY_88)
    val color1 = DesignSystemTheme.colors.primaryNormal.copy(OPACITY_5)

    Column(
        modifier = modifier
            .sizeIn(
                minWidth = 64.dp,
                maxWidth = 296.dp
            )
            .padding(spacingBetweenTooltipAndAnchor)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .background(color)
            .background(color1)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
        ) {

            Box(
                modifier = Modifier.weight(1f, fill = false),
                contentAlignment = Alignment.CenterStart
            ) {
                text()
            }

            onClose?.let {
                Box(modifier = Modifier.size(20.dp)) {
                    onClose()
                }
            }
        }

        action?.let {
            Box(
                modifier = Modifier.padding(start = 2.dp, top = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                action()
            }
        }
    }
}


private fun CacheDrawScope.drawCaretWithPath(
    density: Density,
    configuration: Configuration,
    spacingBetweenTooltipAndAnchor: Dp,
    backgroundColor: Color,
    containerColor: Color,
    containerColor1: Color,
    dpSize: DpSize,
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
            caretHeightPx = dpSize.height.roundToPx()
            caretWidthPx = dpSize.width.roundToPx()
            screenWidthPx = configuration.screenWidthDp.dp.roundToPx()
            tooltipAnchorSpacing = spacingBetweenTooltipAndAnchor.roundToPx()
            anchorSize = 2.dp.roundToPx().toFloat()
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
            tooltipAnchorSpacing.toFloat()
        } else {
            tooltipHeight - tooltipAnchorSpacing
        }

        val position = if (anchorMid + tooltipWidth / 2 > screenWidthPx) {
            val anchorMidFromRightScreenEdge = screenWidthPx - anchorMid
            val caretX = tooltipWidth - anchorMidFromRightScreenEdge
            Offset(caretX, caretY)
        } else {
            val tooltipLeft = anchorLeft - (this.size.width / 2 - anchorWidth / 2)
            val caretX = anchorMid - maxOf(tooltipLeft, 0f)
            Offset(caretX, caretY)
        }

        if (isCaretTop) {
            path.apply {
                moveTo(x = position.x, y = position.y)
                lineTo(x = position.x + caretWidthPx / 2, y = position.y)
                lineTo(x = position.x + anchorSize, y = position.y - caretHeightPx + anchorSize)

                arcTo(
                    rect = Rect(
                        left = position.x - anchorSize / 2,
                        top = position.y - caretHeightPx + anchorSize / 2,
                        right = position.x + anchorSize / 2,
                        bottom = position.y - caretHeightPx + anchorSize
                    ),
                    startAngleDegrees = -45f,
                    sweepAngleDegrees = -135f,
                    forceMoveTo = false
                )

                lineTo(x = position.x - anchorSize, y = position.y - caretHeightPx + anchorSize)
                lineTo(x = position.x - caretWidthPx / 2, y = position.y)
                close()
            }
        } else {
            path.apply {
                moveTo(x = position.x, y = position.y)
                lineTo(x = position.x + caretWidthPx / 2, y = position.y)
                lineTo(x = position.x + anchorSize, y = position.y + caretHeightPx - anchorSize)

                arcTo(
                    rect = Rect(
                        left = position.x - anchorSize / 2,
                        top = position.y + caretHeightPx - anchorSize,
                        right = position.x + anchorSize / 2,
                        bottom = position.y + caretHeightPx - anchorSize / 2
                    ),
                    startAngleDegrees = 45f,
                    sweepAngleDegrees = 135f,
                    forceMoveTo = false
                )

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
                color = backgroundColor
            )
            drawPath(
                path = path,
                color = containerColor
            )
            drawPath(
                path = path,
                color = containerColor1
            )
        }
    }
}

/**
 * interface WantedTooltipState
 *
 * Tooltip의 표시/숨김 상태를 관리하는 인터페이스입니다.
 *
 * Tooltip을 표시하거나 숨기기 위한 메서드를 제공하며, 현재 표시 상태를 확인할 수 있습니다.
 */
interface WantedTooltipState {
    fun show()
    fun dismiss()
    val isVisible: Boolean
    val visibleState: State<Boolean>
}

private class WantedTooltipStateImpl(
    initialVisible: Boolean
) : WantedTooltipState {

    private val _visibleState = mutableStateOf(initialVisible)
    override val visibleState: State<Boolean>
        get() = _visibleState

    override fun show() {
        _visibleState.value = true
    }

    override fun dismiss() {
        _visibleState.value = false
    }

    override val isVisible: Boolean
        get() = _visibleState.value
}

/**
 * rememberTooltipState
 *
 * Tooltip 상태를 관리하는 State 객체를 생성하고 관리합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val tooltipState = rememberTooltipState(initialVisible = false)
 *
 * // Tooltip  표시
 * tooltipState.show()
 *
 * // Tooltip  숨김
 * tooltipState.dismiss()
 * ```
 *
 * @param initialVisible Boolean: 초기 표시 상태입니다.
 * @return WantedTooltipState: Tooltip  상태를 관리하는 객체입니다.
 */
@Composable
fun rememberTooltipState(initialVisible: Boolean = false): WantedTooltipState =
    remember { WantedTooltipStateImpl(initialVisible) }


private const val SpacingBetweenTooltipAndAnchor = 8
private const val SpacingBetweenTooltipAndAnchorNotArrow = 2

/**
 * enum class WantedTooltipSize
 *
 * Tooltip 의 크기를 정의하는 enum 클래스입니다.
 * - Small: 작은 크기의 Tooltip 입니다.
 * - Medium:중간 크기의 Tooltip 입니다.
 */
enum class WantedTooltipSize {
    Small,
    Medium
}

/**
 * enum class WantedTooltipAlign
 *
 * Tooltip 의 정렬 방식을 정의하는 enum 클래스입니다.
 * - Left: 왼쪽 정렬입니다.
 * - Center: 중앙 정렬입니다.
 * - Right: 오른쪽 정렬입니다.
 */
enum class WantedTooltipAlign {
    Left,
    Center,
    Right
}

/**
 * Modifier extension for drawing caret (arrow) on tooltip
 */
private fun Modifier.drawCaret(
    anchorLayoutCoordinates: LayoutCoordinates?,
    onDraw: CacheDrawScope.() -> DrawResult
): Modifier = this.drawWithCache {
    onDraw()
}