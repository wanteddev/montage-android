package com.wanted.android.wanted.design.presentation.popover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedShadowSpreadStyle
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.base.wantedDropShadowSpared
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedPopover(
    modifier: Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    state: WantedPopoverState = rememberPopoverState(),
    align: WantedPopoverAlign = WantedPopoverAlign.Left,
    positionTop: Boolean = false,
    always: Boolean = false,
    body: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val isVisible by state.visibleState
    var isShow by remember { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        isShow = isVisible
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // WindowInsets를 사용하여 실제 사용 가능한 화면 높이 계산
    with(density) {
        windowInsets.getTop(density).toDp()
    }
    val windowInsetsBottom = with(density) {
        windowInsets.getBottom(density).toDp()
    }

    var contentPositionY by remember { mutableFloatStateOf(0f) }
    var contentPositionX by remember { mutableFloatStateOf(0f) }
    var contentPositionYInWindow by remember { mutableFloatStateOf(0f) }
    var contentHeight by remember { mutableIntStateOf(0) }
    var contentWidth by remember { mutableIntStateOf(0) }

    var tooltipWidth by remember { mutableIntStateOf(0) }
    var tooltipHeight by remember { mutableIntStateOf(0) }
    var offsetX by remember { mutableIntStateOf(0) }

    var isPopupAbove by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                val positionWindow = coordinates.positionInWindow()
                contentPositionY = coordinates.positionInParent().y
                contentPositionYInWindow = positionWindow.y
                contentPositionX = positionWindow.x
                contentHeight = coordinates.size.height
                contentWidth = coordinates.size.width

                if (positionWindow.x < 0) {
                    isShow = false
                } else {
                    if (state.isVisible) {
                        isShow = true
                    }
                }
            }
    ) {
        content()

        if (isShow) {
            val estimatedTooltipHeight = with(density) { 80.dp.toPx() }

            // windowInsets bottom을 고려하여 실제 사용 가능한 공간 계산
            val windowInsetsBottomPx = with(density) { windowInsetsBottom.toPx() }
            val screenHeightPx = with(density) { screenHeight.dp.toPx() }

            val enableBottomY = (screenHeightPx - windowInsetsBottomPx)
            // 아래쪽 공간: 전체 화면 높이 - windowInsets bottom - content 하단 위치
            val spaceBelow = enableBottomY - (contentPositionYInWindow + contentHeight)
            val spaceAbove = contentPositionYInWindow
            val overlapBottom = spaceBelow < estimatedTooltipHeight + SpacingBetweenPopover &&
                    spaceAbove > estimatedTooltipHeight + SpacingBetweenPopover
                    || spaceBelow < tooltipHeight + SpacingBetweenPopover

            // positionTop 옵션에 따른 popover 위치 결정
            isPopupAbove = when {
                overlapBottom -> true
                positionTop -> spaceAbove > estimatedTooltipHeight + SpacingBetweenPopover
                else -> false
            }

            // Shadow 영역 계산
            val shadowSpacing = remember {
                val shadows = WantedShadowSpreadStyle.Small().getShadow()
                var maxBlur = 0.dp
                var maxSpread = 0.dp
                var maxOffsetX = 0.dp
                var maxOffsetY = 0.dp

                shadows.forEach { shadow ->
                    maxBlur = maxOf(maxBlur, shadow.blurRadius)
                    maxSpread = maxOf(maxSpread, shadow.spreadRadius)
                    maxOffsetX = maxOf(maxOffsetX, kotlin.math.abs(shadow.offsetX.value).dp)
                    maxOffsetY = maxOf(maxOffsetY, kotlin.math.abs(shadow.offsetY.value).dp)
                }

                maxOf(maxBlur + maxSpread + maxOffsetX, maxBlur + maxSpread + maxOffsetY)
            }
            val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }

            // 기본 오프셋 계산 (shadow 영역을 고려하지 않음)
            val baseOffsetX = calculatePopoverOffsetX(
                align = align,
                contentPositionX = contentPositionX,
                contentWidth = contentWidth,
                tooltipWidth = tooltipWidth,
                screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt(),
                paddingPx = with(density) { 8.dp.toPx() }.toInt()
            )

            // Shadow 영역을 고려한 최종 오프셋 계산
            // PopoverWithShadow에서 Layout이 shadow 영역만큼 확장되므로,
            // content가 정확한 위치에 오도록 shadow spacing만큼 조정
            offsetX = baseOffsetX - shadowSpacingPx

            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = offsetX,
                    y = if (isPopupAbove) {
                        var positionY = contentPositionY.toInt() - tooltipHeight - SpacingBetweenPopover - shadowSpacingPx
                        if (overlapBottom) {
                            positionY = positionY - windowInsetsBottomPx.toInt() - contentHeight
                        }

                        positionY
                    } else {
                        contentPositionY.toInt() + contentHeight + SpacingBetweenPopover - shadowSpacingPx
                    }
                ),
                properties = PopupProperties(
                    focusable = !always,
                    dismissOnClickOutside = !always,
                    dismissOnBackPress = true,
                    clippingEnabled = false
                ),
                onDismissRequest = {
                    if (!always) {
                        state.dismiss()
                    }
                }
            ) {
                PopoverWithShadow(
                    modifier = Modifier.pointerInput(Unit) {},
                    shadowStyle = WantedShadowSpreadStyle.Small(),
                    onSizeChange = { width, height ->
                        tooltipWidth = width
                        tooltipHeight = height
                    },
                    content = body
                )
            }
        }
    }
}

@Composable
fun WantedPopover(
    modifier: Modifier,
    text: String,
    heading: String = "",
    state: WantedPopoverState = rememberPopoverState(),
    windowInsets: WindowInsets = WindowInsets.systemBars,
    align: WantedPopoverAlign = WantedPopoverAlign.Left,
    closeButton: Boolean = false,
    positionTop: Boolean = false,
    always: Boolean = false,
    action: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    WantedPopover(
        modifier = modifier,
        state = state,
        windowInsets = windowInsets,
        align = align,
        positionTop = positionTop,
        always = always,
        content = content,
        body = {
            WantedPopoverLayout(
                modifier = Modifier,
                text = {
                    PopoverBody(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        closeButton = if (heading.isEmpty()) closeButton else false,
                        onDismiss = {
                            state.dismiss()
                        }
                    )
                },
                heading = if (heading.isNotEmpty()) {
                    {
                        PopoverHeader(
                            modifier = Modifier.fillMaxWidth(),
                            heading = heading,
                            closeButton = closeButton,
                            onDismiss = {
                                state.dismiss()
                            }
                        )
                    }
                } else {
                    null
                },
                action = action
            )
        }
    )
}

@Composable
private fun PopoverHeader(
    modifier: Modifier = Modifier,
    heading: String,
    closeButton: Boolean,
    onDismiss: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .weight(1f),
            text = heading,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = WantedTextStyle(
                colorRes = R.color.label_normal,
                style =
                        DesignSystemTheme.typography.body2Bold
            )
        )

        if (closeButton) {
            WantedTouchArea(
                onClick = onDismiss,
                shape = CircleShape,
                verticalPadding = 1.dp,
                horizontalPadding = 1.dp,
                content = {
                    Icon(
                        modifier = Modifier
                            .padding(3.dp)
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.icon_normal_close),
                        contentDescription = null,
                        tint = colorResource(R.color.label_normal)
                    )
                }
            )
        }
    }
}


@Composable
private fun PopoverBody(
    modifier: Modifier = Modifier,
    text: String,
    closeButton: Boolean,
    onDismiss: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .weight(1f),
            text = text,
            style = WantedTextStyle(
                colorRes = R.color.label_neutral,
                style =
                        DesignSystemTheme.typography.label2Medium
            ),
            overflow = TextOverflow.Ellipsis
        )

        if (closeButton) {
            Box(
                modifier = Modifier.defaultMinSize(
                    minHeight = with(LocalDensity.current) {
                        DesignSystemTheme.typography.label2Medium.lineHeight.toDp()
                    }
                ),
                contentAlignment = Alignment.Center
            ) {
                WantedTouchArea(
                    onClick = onDismiss,
                    shape = CircleShape,
                    verticalPadding = 1.dp,
                    horizontalPadding = 1.dp,
                    content = {
                        Icon(
                            modifier = Modifier
                                .padding(3.dp)
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.icon_normal_close),
                            contentDescription = null,
                            tint = colorResource(R.color.label_normal)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun WantedPopoverLayout(
    modifier: Modifier = Modifier,
    heading: @Composable (() -> Unit)?,
    text: @Composable () -> Unit,
    action: @Composable (RowScope.() -> Unit)?
) {
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = 140.dp,
                maxWidth = 360.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_elevated_normal))
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp
            )
    ) {
        Column(
            modifier = Modifier
                .sizeIn(
                    minWidth = 140.dp,
                    maxWidth = 360.dp
                )
                .width(IntrinsicSize.Max)
                .wrapContentHeight()
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                heading?.invoke()
            }


            heading?.let {
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                text()
            }

            action?.let {
                Spacer(modifier = Modifier.padding(top = 12.dp))

                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {
                    action()
                }
            }
        }
    }
}


private fun calculatePopoverOffsetX(
    align: WantedPopoverAlign,
    contentPositionX: Float,
    contentWidth: Int,
    tooltipWidth: Int,
    screenWidthPx: Int,
    paddingPx: Int
): Int {
    if (tooltipWidth == 0) return 0

    val idealOffsetX = when (align) {
        WantedPopoverAlign.Left -> {
            0
        }

        WantedPopoverAlign.Center -> {
            (contentWidth - tooltipWidth) / 2
        }

        WantedPopoverAlign.Right -> {
            contentWidth - tooltipWidth
        }
    }

    // Shadow는 무시하고 content만 기준으로 화면 경계 계산
    // Shadow가 화면 밖으로 나가는 것은 허용하되, content 자체는 정확한 위치에 배치
    val contentLeft = contentPositionX + idealOffsetX
    val contentRight = contentLeft + tooltipWidth

    val adjustedOffsetX = when {
        // 왼쪽 경계 체크: content만 화면 안에 들어오도록 (shadow는 잘릴 수 있음)
        contentLeft < paddingPx -> {
            (paddingPx - contentPositionX).toInt()
        }

        // 오른쪽 경계 체크: content만 화면 안에 들어오도록 (shadow는 잘릴 수 있음)
        contentRight > screenWidthPx - paddingPx -> {
            (screenWidthPx - paddingPx - tooltipWidth - contentPositionX).toInt()
        }

        else -> {
            idealOffsetX
        }
    }

    return adjustedOffsetX
}


private const val SpacingBetweenPopover = 8


enum class WantedPopoverAlign {
    Left,
    Center,
    Right
}


interface WantedPopoverState {
    fun show()
    fun dismiss()
    val isVisible: Boolean
    val visibleState: State<Boolean>
}

private class WantedPopoverStateImpl(
    initialVisible: Boolean
) : WantedPopoverState {

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


@Composable
fun rememberPopoverState(initialVisible: Boolean = false): WantedPopoverState =
        remember { WantedPopoverStateImpl(initialVisible) }

@Composable
private fun PopoverWithShadow(
    modifier: Modifier = Modifier,
    shadowStyle: WantedShadowSpreadStyle,
    onSizeChange: (width: Int, height: Int) -> Unit = { _, _ -> },
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    // Shadow의 blur radius를 기반으로 필요한 여백 계산
    val shadowSpacing = remember(shadowStyle) {
        val shadows = shadowStyle.getShadow()
        var maxBlur = 0.dp
        var maxSpread = 0.dp
        var maxOffsetX = 0.dp
        var maxOffsetY = 0.dp

        shadows.forEach { shadow ->
            maxBlur = maxOf(maxBlur, shadow.blurRadius)
            maxSpread = maxOf(maxSpread, shadow.spreadRadius)
            maxOffsetX = maxOf(maxOffsetX, kotlin.math.abs(shadow.offsetX.value).dp)
            maxOffsetY = maxOf(maxOffsetY, kotlin.math.abs(shadow.offsetY.value).dp)
        }

        // Shadow가 표시될 수 있는 최대 영역 계산
        maxOf(maxBlur + maxSpread + maxOffsetX, maxBlur + maxSpread + maxOffsetY)
    }

    Layout(
        modifier = modifier,
        content = {
            // Shadow 배경 (터치 불가능)
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        // Shadow가 적용된 실제 content의 크기를 측정
                        onSizeChange(coordinates.size.width, coordinates.size.height)
                    }
                    .wantedDropShadowSpared(style = shadowStyle)
            ) {
                content()
            }
        }
    ) { measurables, constraints ->
        val placeable = measurables[0].measure(constraints)

        // Shadow 영역을 고려한 전체 크기 계산
        val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }
        val totalWidth = placeable.width + (shadowSpacingPx * 2)
        val totalHeight = placeable.height + (shadowSpacingPx * 2)

        layout(totalWidth, totalHeight) {
            // Content를 중앙에 배치하여 shadow가 모든 방향으로 표시될 수 있도록 함
            // 하지만 터치 영역은 실제 content 크기만 유지됨
            placeable.placeRelative(
                x = (totalWidth - placeable.width) / 2,
                y = (totalHeight - placeable.height) / 2
            )
        }
    }
}
