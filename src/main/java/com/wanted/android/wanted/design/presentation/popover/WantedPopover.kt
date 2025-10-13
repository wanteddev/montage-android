package com.wanted.android.wanted.design.presentation.popover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
            val spaceBelow =
                with(density) { screenHeight.dp.toPx() } - (contentPositionYInWindow + contentHeight)
            val spaceAbove = contentPositionYInWindow

            // positionTop 옵션에 따른 popover 위치 결정
            isPopupAbove = if (positionTop) {
                // positionTop이 true일 때: 위쪽 공간이 충분하면 위에, 아니면 아래에
                spaceAbove > estimatedTooltipHeight + SpacingBetweenPopover
            } else {
                // positionTop이 false일 때: 기존 로직 (아래쪽 공간 부족시에만 위에)
                spaceBelow < estimatedTooltipHeight + SpacingBetweenPopover &&
                        spaceAbove > estimatedTooltipHeight + SpacingBetweenPopover
            }

            offsetX = calculatePopoverOffsetX(
                align = align,
                contentPositionX = contentPositionX,
                contentWidth = contentWidth,
                tooltipWidth = tooltipWidth,
                screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt(),
                paddingPx = with(density) { 8.dp.toPx() }.toInt()
            )

            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = offsetX,
                    y = if (isPopupAbove) {
                        // 위쪽에 표시할 때: content의 top에서 tooltip height와 spacing만큼 위로
                        contentPositionY.toInt() - tooltipHeight - SpacingBetweenPopover
                    } else {
                        // 아래쪽에 표시할 때: content의 bottom에서 spacing만큼 아래로
                        contentPositionY.toInt() + contentHeight + SpacingBetweenPopover
                    }
                ),
                properties = PopupProperties(
                    focusable = !always,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
                onDismissRequest = {
                    if (!always) {
                        state.dismiss()
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            tooltipWidth = coordinates.size.width
                            tooltipHeight = coordinates.size.height
                        }
                        .wantedDropShadowSpared(style = WantedShadowSpreadStyle.Small())
                ) {
                    body()
                }
            }
        }
    }
}

@Composable
fun WantedPopover(
    modifier: Modifier,
    state: WantedPopoverState = rememberPopoverState(),
    text: String,
    heading: String = "",
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
