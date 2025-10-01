package com.wanted.android.wanted.design.feedback.tooltip

import android.content.res.Configuration
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.feedback.WantedToastIcon
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.OPACITY_61
import com.wanted.android.wanted.design.util.OPACITY_88
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.clickOnce
import kotlinx.coroutines.launch


enum class WantedTooltipSize {
    Small,
    Medium
}

enum class WantedTooltipAlign {
    Left,
    Center,
    Right
}


@Composable
fun WantedTooltip(
    modifier: Modifier,
    isShowTooltip: Boolean,
    text: String,
    size: WantedTooltipSize = WantedTooltipSize.Medium,
    align: WantedTooltipAlign = WantedTooltipAlign.Left,
    content: @Composable () -> Unit
) {
    var isShow by remember(isShowTooltip) { mutableStateOf(isShowTooltip) }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val backgroundColor = colorResource(id = R.color.background_normal_normal)
    val color = colorResource(id = R.color.inverse_background).copy(OPACITY_88)
    val color1 = colorResource(id = R.color.primary_normal).copy(OPACITY_5)

    var contentPositionY by remember { mutableFloatStateOf(0f) }
    var contentPositionX by remember { mutableFloatStateOf(0f) }
    var contentHeight by remember { mutableIntStateOf(0) }
    var contentWidth by remember { mutableIntStateOf(0) }

    var tooltipWidth by remember { mutableIntStateOf(0) }
    var tooltipHeight by remember { mutableIntStateOf(0) }
    var tooltipOffsetX by remember { mutableIntStateOf(0) }

    var caretPositionX by remember { mutableFloatStateOf(0f) }

    var isPopupAbove by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            contentPositionY = coordinates.positionInParent().y
            contentPositionX = coordinates.positionInWindow().x
            contentHeight = coordinates.size.height
            contentWidth = coordinates.size.width

            if (coordinates.positionInWindow().x < 0) {
                isShow = false
            } else {
                if (isShowTooltip) {
                    isShow = true
                }
            }
        }
    ) {
        content()

        if (isShow) {
            val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
            val spaceBelow = with(density) { screenHeight.dp.toPx() } - (contentPositionY + contentHeight)
            val spaceAbove = contentPositionY

            isPopupAbove = spaceBelow < estimatedTooltipHeight + SpacingBetweenTooltipAndAnchor &&
                    spaceAbove > estimatedTooltipHeight + SpacingBetweenTooltipAndAnchor

            // Tooltip offset 계산 및 저장
            tooltipOffsetX = calculateTooltipOffsetX(
                align = align,
                contentPositionX = contentPositionX,
                contentWidth = contentWidth,
                tooltipWidth = tooltipWidth,
                screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt(),
                paddingPx = with(density) { 20.dp.toPx() }.toInt()
            )

            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = tooltipOffsetX,
                    y = if (isPopupAbove) {
                        contentPositionY.toInt() - tooltipHeight - SpacingBetweenTooltipAndAnchor
                    } else {
                        contentPositionY.toInt() + contentHeight + SpacingBetweenTooltipAndAnchor
                    }
                ),
                properties = PopupProperties(
                    focusable = false,
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            ) {
                SocialProfileCountryNotificationTooltipLayout(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            tooltipWidth = coordinates.size.width
                            tooltipHeight = coordinates.size.height

                            // Caret은 content의 align 기준점을 가리키도록 계산
                            caretPositionX = calculateCaretPositionX(
                                align = align,
                                contentWidth = contentWidth,
                                tooltipOffsetX = tooltipOffsetX,
                                caretWidthPx = with(density) { 12.dp.toPx() }
                            )
                        }
                        .drawWithCache {
                            drawCaretWithPath(
                                density = this,
                                caretCenterPosition = Offset(
                                    x = caretPositionX,
                                    y = if (isPopupAbove) this.size.height.toFloat() else 0f
                                ),
                                spacingBetweenTooltipAndAnchor = SpacingBetweenTooltipAndAnchor.dp,
                                backgroundColor = backgroundColor,
                                containerColor = color,
                                containerColor1 = color1,
                                dpSize = DpSize(12.dp, 6.dp),
                                isCaretPointingUp = !isPopupAbove
                            )
                        },
                    spacingBetweenTooltipAndAnchor = SpacingBetweenTooltipAndAnchor.dp,
                    text = {
                        Text(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            text = text,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = WantedTextStyle(
                                colorRes = R.color.inverse_label,
                                style = DesignSystemTheme.typography.label1Medium
                            )
                        )

                    }
                )
            }
        }
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

    // Align에 따른 이상적인 tooltip 위치 계산
    val idealOffsetX = when (align) {
        WantedTooltipAlign.Left -> {
            // Content의 왼쪽 가장자리에 tooltip 왼쪽 가장자리 정렬
            0
        }

        WantedTooltipAlign.Center -> {
            // Content의 중앙에 tooltip 중앙 정렬
            (contentWidth - tooltipWidth) / 2
        }

        WantedTooltipAlign.Right -> {
            // Content의 오른쪽 가장자리에 tooltip 오른쪽 가장자리 정렬
            contentWidth - tooltipWidth
        }
    }

    // 화면 경계 체크 및 보정
    val tooltipAbsoluteLeft = contentPositionX + idealOffsetX
    val tooltipAbsoluteRight = tooltipAbsoluteLeft + tooltipWidth

    val adjustedOffsetX = when {
        // 왼쪽 경계를 벗어나는 경우
        tooltipAbsoluteLeft < paddingPx -> {
            (paddingPx - contentPositionX).toInt()
        }
        // 오른쪽 경계를 벗어나는 경우
        tooltipAbsoluteRight > screenWidthPx - paddingPx -> {
            (screenWidthPx - paddingPx - tooltipWidth - contentPositionX).toInt()
        }
        // 경계 내에 있는 경우
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
    caretWidthPx: Float
): Float {
    // Content의 align 기준점 계산 (content 기준 좌표)
    val contentAnchorX = when (align) {
        WantedTooltipAlign.Left -> {
            // Content의 왼쪽 가장자리에서 약간 안쪽
            20f + caretWidthPx
        }
        WantedTooltipAlign.Center -> {
            // Content의 중앙
            contentWidth / 2f
        }
        WantedTooltipAlign.Right -> {
            // Content의 오른쪽 가장자리에서 약간 안쪽
            contentWidth - 20f - caretWidthPx
        }
    }

    // Tooltip 기준 좌표로 변환
    // tooltipOffsetX = tooltip의 왼쪽 - content의 왼쪽
    // caretPositionX = contentAnchorX - tooltipOffsetX
    return contentAnchorX - tooltipOffsetX
}

@Composable
private fun SocialProfileCountryNotificationTooltipLayout(
    modifier: Modifier = Modifier,
    spacingBetweenTooltipAndAnchor: Dp,
    text: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .sizeIn(
                minWidth = 64.dp,
                maxWidth = 296.dp //SpacingBetweenTooltipAndAnchor *2 포함해야 한다.
            )
            .padding(spacingBetweenTooltipAndAnchor)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(OPACITY_88))
            .background(colorResource(id = R.color.primary_normal).copy(OPACITY_5))
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            text()
        }
    }
}

private fun CacheDrawScope.drawCaretWithPath(
    density: Density,
    caretCenterPosition: Offset,
    spacingBetweenTooltipAndAnchor: Dp,
    backgroundColor: Color,
    containerColor: Color,
    containerColor1: Color,
    dpSize: DpSize,
    isCaretPointingUp: Boolean = true
): DrawResult {
    val path = Path()

    val caretHeightPx: Int
    val caretWidthPx: Int
    val tooltipAnchorSpacing: Int
    val anchorSize: Float

    with(density) {
        caretHeightPx = dpSize.height.roundToPx()
        caretWidthPx = dpSize.width.roundToPx()
        tooltipAnchorSpacing = spacingBetweenTooltipAndAnchor.roundToPx()
        anchorSize = 2.dp.roundToPx().toFloat()
    }

    val caretY = if (isCaretPointingUp) {
        tooltipAnchorSpacing.toFloat()
    } else {
        this.size.height - tooltipAnchorSpacing.toFloat()
    }

    val caretX = caretCenterPosition.x

    if (!isCaretPointingUp) {
        path.apply {
            moveTo(x = caretX, y = caretY)
            lineTo(x = caretX + caretWidthPx / 2, y = caretY)
            lineTo(x = caretX + anchorSize, y = caretY + caretHeightPx - anchorSize)

            arcTo(
                rect = Rect(
                    left = caretX - anchorSize / 2,
                    top = caretY + caretHeightPx - anchorSize,
                    right = caretX + anchorSize / 2,
                    bottom = caretY + caretHeightPx - anchorSize / 2
                ),
                startAngleDegrees = 45f,
                sweepAngleDegrees = 135f,
                forceMoveTo = false
            )

            lineTo(x = caretX - anchorSize, y = caretY + caretHeightPx - anchorSize)

            lineTo(x = caretX - caretWidthPx / 2, y = caretY)
            close()
        }
    } else {
        path.apply {
            moveTo(x = caretX, y = caretY)
            lineTo(x = caretX + caretWidthPx / 2, y = caretY)
            lineTo(x = caretX + anchorSize, y = caretY - caretHeightPx + anchorSize)

            arcTo(
                rect = Rect(
                    left = caretX - anchorSize / 2,
                    top = caretY - caretHeightPx + anchorSize / 2,
                    right = caretX + anchorSize / 2,
                    bottom = caretY - caretHeightPx + anchorSize
                ),
                startAngleDegrees = -45f,
                sweepAngleDegrees = -135f,
                forceMoveTo = false
            )

            lineTo(x = caretX - anchorSize, y = caretY - caretHeightPx + anchorSize)
            lineTo(x = caretX - caretWidthPx / 2, y = caretY)
            close()
        }
    }

    return onDrawWithContent {

        drawPath(path, backgroundColor)
        drawPath(path, containerColor)
        drawPath(path, containerColor1)

        drawContent()
    }
}

/**
 * 텍스트와 버튼, 닫기 아이콘, 화살표 등을 포함할 수 있는 사용자 정의 Tooltip 컴포저블입니다.
 *
 * 머터리얼 `TooltipBox`를 기반으로 하며, 텍스트 설명과 함께 선택적으로 "더 알아보기" 버튼과 닫기 아이콘을 추가할 수 있습니다.
 * 툴팁에 화살표 표시 여부도 설정할 수 있으며, 상태 관리를 위해 `TooltipState`를 사용합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedTooltip(
 *     text = "툴팁 내용입니다.",
 *     action = "더 보기",
 *     isShowCloseButton = true,
 *     content = {
 *         Icon(
 *             painter = painterResource(id = R.drawable.ic_info),
 *             contentDescription = null
 *         )
 *     },
 *     state = remember { TooltipState(true) }
 * )
 * ```
 *
 * @param text String: 툴팁에 표시할 텍스트입니다. 최대 3줄까지 표시됩니다.
 * @param modifier Modifier: 툴팁 외형 및 배치 제어를 위한 Modifier입니다.
 * @param action String?: 우측 하단에 표시할 보조 액션 버튼 텍스트입니다. null일 경우 표시되지 않습니다.
 * @param isShowCloseButton Boolean: true일 경우 닫기 아이콘을 표시합니다.
 * @param isShowArrow Boolean: true일 경우 앵커를 가리키는 화살표를 표시합니다.
 * @param state TooltipState: 툴팁의 상태를 제어하는 객체입니다.
 * @param onClickAction (() -> Unit)?: 보조 액션 버튼 클릭 시 호출되는 콜백입니다.
 * @param content () -> Unit: 툴팁을 보여줄 기준 콘텐츠입니다. 클릭 시 툴팁이 열립니다.
 */
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

    val backgroundColor = colorResource(id = R.color.background_normal_normal)
    val color = colorResource(id = R.color.inverse_background).copy(0.88f)
    val color1 = colorResource(id = R.color.primary_normal).copy(0.05f)

    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            val density = LocalDensity.current
            val configuration = LocalConfiguration.current
            val customModifier = if (isShowArrow) {
                Modifier
                    .drawCaret { anchorLayoutCoordinates ->
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
                        style = WantedTextStyle(
                            colorRes = R.color.inverse_label,
                            style = DesignSystemTheme.typography.label1Medium
                        )
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
                            tint = colorResource(id = R.color.inverse_label).copy(alpha = OPACITY_61),
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
                                contentColor = colorResource(id = R.color.inverse_label).copy(alpha = OPACITY_61)
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
    Column(
        modifier = modifier
            .sizeIn(
                minWidth = 64.dp,
                maxWidth = 296.dp //SpacingBetweenTooltipAndAnchor *2 포함해야 한다.
            )
            .padding(spacingBetweenTooltipAndAnchor)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.background_normal_normal))
            .background(colorResource(id = R.color.inverse_background).copy(0.88f))
            .background(colorResource(id = R.color.primary_normal).copy(0.05f))
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

private const val SpacingBetweenTooltipAndAnchor = 8
private const val SpacingBetweenTooltipAndAnchorNotArrow = 2

@DevicePreviews
@Composable
private fun WantedTooltipPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WantedTooltip(
                    text = "메시지에 마침표를 찍어요.",
                    action = "더 알아보기",
                    isShowCloseButton = true,
                    content = {
                        WantedToastIcon(
                            modifier = Modifier.size(22.dp),
                            resourceId = R.drawable.icon_normal_circle_exclamation_fill,
                            tint = colorResource(id = R.color.status_negative)
                        )
                    },
                    state = remember { TooltipState(true) }
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedTooltipAlignPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Left Align
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier.padding(start = 40.dp),
                        isShowTooltip = true,
                        text = "Left Align - 툴팁이 왼쪽에 정렬됩니다.",
                        align = WantedTooltipAlign.Left,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        colorResource(id = R.color.primary_normal),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }

                // Center Align
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier,
                        isShowTooltip = true,
                        text = "Center Align - 툴팁이 중앙에 정렬됩니다.",
                        align = WantedTooltipAlign.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        colorResource(id = R.color.background_normal_alternative),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }

                // Right Align
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier.padding(end = 40.dp),
                        isShowTooltip = true,
                        text = "Right Align - 툴팁이 오른쪽에 정렬됩니다.",
                        align = WantedTooltipAlign.Right,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        colorResource(id = R.color.status_negative),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedTooltipBoundaryPreview() {
    DesignSystemTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(100.dp)
            ) {
                // Left Align at screen edge - 경계 처리 테스트
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier.padding(start = 8.dp),
                        isShowTooltip = true,
                        text = "화면 왼쪽 끝: Tooltip은 보정되지만 Caret은 Content를 가리킵니다.",
                        align = WantedTooltipAlign.Left,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        colorResource(id = R.color.primary_normal),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }

                // Center content near left edge
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier.padding(start = 40.dp),
                        isShowTooltip = true,
                        text = "Center Align 왼쪽 근처: Tooltip 보정시에도 Caret은 중앙 유지",
                        align = WantedTooltipAlign.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        colorResource(id = R.color.background_normal_alternative),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }

                // Right Align at screen edge - 경계 처리 테스트
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedTooltip(
                        modifier = Modifier.padding(end = 8.dp),
                        isShowTooltip = true,
                        text = "화면 오른쪽 끝: Tooltip은 보정되지만 Caret은 Content를 가리킵니다.",
                        align = WantedTooltipAlign.Right,
                        content = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        colorResource(id = R.color.status_negative),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    )
                }
            }
        }
    }
}