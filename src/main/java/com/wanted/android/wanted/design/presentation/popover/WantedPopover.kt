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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
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
    val stateHolder = rememberWantedPopoverStateHolder(initialVisible = state.isVisible)
    val isVisible by state.visibleState

    LaunchedEffect(isVisible) {
        stateHolder.updateShowState(isVisible)
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    PopoverContainer(
        modifier = modifier,
        stateHolder = stateHolder,
        state = state,
        windowInsets = windowInsets,
        density = density,
        configuration = configuration,
        align = align,
        positionTop = positionTop,
        always = always,
        body = body,
        content = content
    )
}

@Composable
private fun PopoverContainer(
    modifier: Modifier,
    stateHolder: WantedPopoverStateHolder,
    state: WantedPopoverState,
    windowInsets: WindowInsets,
    density: Density,
    configuration: android.content.res.Configuration,
    align: WantedPopoverAlign,
    positionTop: Boolean,
    always: Boolean,
    body: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // WindowInsets 계산 (WindowInsets(0)인 경우 모두 0이 됨)
    val windowInsetsTopPx = with(density) { windowInsets.getTop(density).toDp().toPx() }
    val windowInsetsBottomPx = with(density) { windowInsets.getBottom(density).toDp().toPx() }
    val screenHeightPx = with(density) { screenHeight.dp.toPx() }
    val screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt()

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                handleContentPositioning(coordinates, stateHolder, state)
            }
    ) {
        content()

        if (stateHolder.isShow) {
            PopoverPopup(
                stateHolder = stateHolder,
                state = state,
                density = density,
                windowInsetsTopPx = windowInsetsTopPx,
                windowInsetsBottomPx = windowInsetsBottomPx,
                screenHeightPx = screenHeightPx,
                screenWidthPx = screenWidthPx,
                align = align,
                positionTop = positionTop,
                always = always,
                body = body
            )
        }
    }
}

@Composable
private fun PopoverPopup(
    stateHolder: WantedPopoverStateHolder,
    state: WantedPopoverState,
    density: Density,
    windowInsetsTopPx: Float,
    windowInsetsBottomPx: Float,
    screenHeightPx: Float,
    screenWidthPx: Int,
    align: WantedPopoverAlign,
    positionTop: Boolean,
    always: Boolean,
    body: @Composable () -> Unit
) {
    val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
    val shadowSpacing = calculateShadowSpacing()
    val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }
    val paddingPx = with(density) { 8.dp.toPx().toInt() }

    // Popover 위치 계산
    // WindowInsets(0)인 경우: windowInsetsTopPx = 0, windowInsetsBottomPx = 0
    // 실제 사용 가능한 화면 높이 = screenHeightPx - windowInsetsTopPx - windowInsetsBottomPx
    val availableScreenHeightPx = screenHeightPx - windowInsetsTopPx - windowInsetsBottomPx

    stateHolder.calculatePopoverPosition(
        windowInsetsBottomPx = windowInsetsBottomPx,
        screenHeightPx = availableScreenHeightPx,
        estimatedTooltipHeight = estimatedTooltipHeight,
        positionTop = positionTop,
        shadowSpacingPx = shadowSpacingPx,
        align = align,
        screenWidthPx = screenWidthPx,
        paddingPx = paddingPx
    )

    Popup(
        alignment = Alignment.TopStart,
        offset = calculatePopupOffset(stateHolder, windowInsetsBottomPx, shadowSpacingPx),
        properties = createPopupProperties(always),
        onDismissRequest = {
            if (!always) {
                state.dismiss()
            }
        }
    ) {
        PopoverWithShadow(
            shadowStyle = WantedShadowSpreadStyle.Small(),
            onSizeChange = { width, height ->
                stateHolder.updateTooltipSize(width, height)
            },
            content = body
        )
    }
}

private fun handleContentPositioning(
    coordinates: androidx.compose.ui.layout.LayoutCoordinates,
    stateHolder: WantedPopoverStateHolder,
    state: WantedPopoverState
) {
    val positionWindow = coordinates.positionInWindow()

    stateHolder.updateContentPosition(
        positionY = coordinates.positionInParent().y,
        positionX = positionWindow.x,
        positionYInWindow = positionWindow.y,
        height = coordinates.size.height,
        width = coordinates.size.width
    )

    if (positionWindow.x < 0) {
        stateHolder.updateShowState(false)
    } else if (state.isVisible) {
        stateHolder.updateShowState(true)
    }
}

private fun calculatePopupOffset(
    stateHolder: WantedPopoverStateHolder,
    windowInsetsBottomPx: Float,
    shadowSpacingPx: Int
): IntOffset {
    return IntOffset(
        x = stateHolder.offsetX,
        y = if (stateHolder.isPopupAbove) {
            // 위쪽에 표시할 때: content 위치에서 툴팁 높이와 간격, 그림자 여백 제거
            var positionY = stateHolder.contentPositionY.toInt() - stateHolder.tooltipHeight - SPACING_BETWEEN_POPOVER - shadowSpacingPx

            // overlapBottom 조건일 때 추가 보정 (원래 로직)
            if (stateHolder.overlapBottom) {
                positionY = positionY - windowInsetsBottomPx.toInt() - stateHolder.contentHeight
            }

            positionY
        } else {
            // 아래쪽에 표시할 때: content 아래 + 간격 - 그림자 여백
            stateHolder.contentPositionY.toInt() + stateHolder.contentHeight + SPACING_BETWEEN_POPOVER - shadowSpacingPx
        }
    )
}

private fun createPopupProperties(always: Boolean): PopupProperties {
    return PopupProperties(
        focusable = !always,
        dismissOnClickOutside = !always,
        dismissOnBackPress = true,
        clippingEnabled = false
    )
}

private fun calculateShadowSpacing(): androidx.compose.ui.unit.Dp {
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

    return maxOf(maxBlur + maxSpread + maxOffsetX, maxBlur + maxSpread + maxOffsetY)
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
                        onDismiss = { state.dismiss() }
                    )
                },
                heading = if (heading.isNotEmpty()) {
                    {
                        PopoverHeader(
                            modifier = Modifier.fillMaxWidth(),
                            heading = heading,
                            closeButton = closeButton,
                            onDismiss = { state.dismiss() }
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
                style = DesignSystemTheme.typography.body2Bold
            )
        )

        if (closeButton) {
            PopoverCloseButton(onDismiss = onDismiss)
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
                style = DesignSystemTheme.typography.label2Medium
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
                PopoverCloseButton(onDismiss = onDismiss)
            }
        }
    }
}

@Composable
private fun PopoverCloseButton(onDismiss: () -> Unit) {
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

@Composable
private fun WantedPopoverLayout(
    modifier: Modifier = Modifier,
    heading: @Composable (() -> Unit)?,
    text: @Composable () -> Unit,
    action: @Composable (RowScope.() -> Unit)?
) {
    Box(
        modifier = modifier
            .sizeIn(minWidth = 140.dp, maxWidth = 360.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.background_elevated_normal))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .sizeIn(minWidth = 140.dp, maxWidth = 360.dp)
                .width(IntrinsicSize.Max)
                .wrapContentHeight()
        ) {
            heading?.let {
                Box(modifier = Modifier.fillMaxWidth()) {
                    it()
                }
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
                    it()
                }
            }
        }
    }
}

@Composable
private fun PopoverWithShadow(
    modifier: Modifier = Modifier,
    shadowStyle: WantedShadowSpreadStyle,
    onSizeChange: (width: Int, height: Int) -> Unit = { _, _ -> },
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val shadowSpacing = calculateShadowSpacing()

    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        onSizeChange(coordinates.size.width, coordinates.size.height)
                    }
                    .wantedDropShadowSpared(style = shadowStyle)
            ) {
                content()
            }
        }
    ) { measurables, constraints ->
        val placeable = measurables[0].measure(constraints)
        val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }
        val totalWidth = placeable.width + (shadowSpacingPx * 2)
        val totalHeight = placeable.height + (shadowSpacingPx * 2)

        layout(totalWidth, totalHeight) {
            placeable.placeRelative(
                x = (totalWidth - placeable.width) / 2,
                y = (totalHeight - placeable.height) / 2
            )
        }
    }
}

// State Management
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
    override val visibleState: State<Boolean> get() = _visibleState

    override fun show() {
        _visibleState.value = true
    }

    override fun dismiss() {
        _visibleState.value = false
    }

    override val isVisible: Boolean get() = _visibleState.value
}

@Composable
fun rememberPopoverState(initialVisible: Boolean = false): WantedPopoverState =
        remember { WantedPopoverStateImpl(initialVisible) }

// Enums and Constants
enum class WantedPopoverAlign {
    Left,
    Center,
    Right
}

private const val SPACING_BETWEEN_POPOVER = 8
