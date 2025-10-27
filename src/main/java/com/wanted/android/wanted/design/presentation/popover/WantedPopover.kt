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
import com.wanted.android.wanted.design.base.wantedDropShadowSpread
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 사용자가 지정한 콘텐츠에 대해 팝오버를 표시하는 Composable입니다.
 * 팝오버는 특정 위치에 나타나며, 다양한 정렬 옵션과 위치 설정을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState()
 * WantedPopover(
 *     modifier = Modifier,
 *     state = popoverState,
 *     align = WantedPopoverAlign.Center,
 *     body = { Text("팝오버 내용") },
 *     content = { Button("클릭", onClick = { popoverState.show() }) }
 * )
 * ```
 *
 * @param modifier Modifier를 통한 외형 스타일 지정입니다.
 * @param windowInsets 윈도우 인셋 설정입니다. 기본값은 0입니다.
 * @param state 팝오버의 표시/숨김 상태를 관리하는 상태 객체입니다.
 * @param align 팝오버의 정렬 방식입니다. (Left, Center, Right)
 * @param positionTop 팝오버를 위쪽에 표시할지 여부입니다.
 * @param always 팝오버가 항상 표시될지 여부입니다. true인 경우 외부 클릭으로 닫히지 않습니다.
 * @param body 팝오버 내부에 표시할 콘텐츠입니다.
 * @param content 팝오버가 연결될 기준 콘텐츠입니다.
 */
@Composable
fun WantedPopover(
    modifier: Modifier,
    windowInsets: WindowInsets = WindowInsets(0),
    state: WantedSimplePopoverState? = null,
    align: WantedPopoverAlign = WantedPopoverAlign.Left,
    positionTop: Boolean = false,
    always: Boolean = false,
    body: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val stateHolder = rememberWantedPopoverStateHolder(
        initialVisible = state?.isVisible ?: false
    )

    // External state와 동기화
    state?.let { externalState ->
        LaunchedEffect(externalState.isVisible) {
            stateHolder.updateShowState(externalState.isVisible)
        }
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // align, positionTop 변경 시 위치 재계산 강제 실행
    LaunchedEffect(align, positionTop) {
        if (stateHolder.state.contentHeight > 0 && stateHolder.state.contentWidth > 0) {
            val windowInsetsBottomPx =
                with(density) { windowInsets.getBottom(density).toDp().toPx() }
            val screenHeight = configuration.screenHeightDp
            val screenHeightPx = with(density) { screenHeight.dp.toPx() }
            val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
            val shadowSpacing = calculateShadowSpacing()
            val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }
            val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }.toInt()
            val paddingPx = with(density) { 8.dp.toPx().toInt() }

            stateHolder.calculatePopoverPosition(
                windowInsetsBottomPx = windowInsetsBottomPx,
                screenHeightPx = screenHeightPx,
                estimatedTooltipHeight = estimatedTooltipHeight,
                positionTop = positionTop,
                shadowSpacingPx = shadowSpacingPx,
                align = align,
                screenWidthPx = screenWidthPx,
                paddingPx = paddingPx
            )
        }
    }

    PopoverContainer(
        modifier = modifier,
        popoverState = stateHolder.state,
        windowInsets = windowInsets,
        density = density,
        configuration = configuration,
        always = always,
        align = align,
        positionTop = positionTop,
        body = body,
        content = content,
        onContentPositioned = { coordinates ->
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
            } else if (stateHolder.state.isVisible) {
                stateHolder.updateShowState(true)
            }
        },
        onCalculatePosition = { windowInsetsBottomPx, screenHeightPx, estimatedTooltipHeight, shadowSpacingPx, screenWidthPx, paddingPx ->
            stateHolder.calculatePopoverPosition(
                windowInsetsBottomPx = windowInsetsBottomPx,
                screenHeightPx = screenHeightPx,
                estimatedTooltipHeight = estimatedTooltipHeight,
                positionTop = positionTop,
                shadowSpacingPx = shadowSpacingPx,
                align = align,
                screenWidthPx = screenWidthPx,
                paddingPx = paddingPx
            )
        },
        onTooltipSizeChanged = { width, height ->
            stateHolder.updateTooltipSize(width, height)
        },
        onDismiss = {
            state?.dismiss() ?: stateHolder.dismiss()
        }
    )
}

@Composable
private fun PopoverContainer(
    modifier: Modifier,
    popoverState: WantedPopoverState,
    windowInsets: WindowInsets,
    density: Density,
    configuration: android.content.res.Configuration,
    always: Boolean,
    align: WantedPopoverAlign,
    positionTop: Boolean,
    body: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onContentPositioned: (androidx.compose.ui.layout.LayoutCoordinates) -> Unit,
    onCalculatePosition: (Float, Float, Float, Int, Int, Int) -> Unit,
    onTooltipSizeChanged: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // WindowInsets 계산 (WindowInsets(0)인 경우 모두 0이 됨)
    val windowInsetsBottomPx = with(density) { windowInsets.getBottom(density).toDp().toPx() }
    val screenHeightPx = with(density) { screenHeight.dp.toPx() }
    val screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt()

    Box(
        modifier = modifier.onGloballyPositioned(onContentPositioned)
    ) {
        content()

        if (popoverState.isShow) {
            PopoverPopup(
                popoverState = popoverState,
                density = density,
                windowInsetsBottomPx = windowInsetsBottomPx,
                screenHeightPx = screenHeightPx,
                screenWidthPx = screenWidthPx,
                align = align,
                positionTop = positionTop,
                always = always,
                body = body,
                onCalculatePosition = onCalculatePosition,
                onTooltipSizeChanged = onTooltipSizeChanged,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun PopoverPopup(
    popoverState: WantedPopoverState,
    density: Density,
    windowInsetsBottomPx: Float,
    screenHeightPx: Float,
    screenWidthPx: Int,
    align: WantedPopoverAlign,
    positionTop: Boolean,
    always: Boolean,
    body: @Composable () -> Unit,
    onCalculatePosition: (Float, Float, Float, Int, Int, Int) -> Unit,
    onTooltipSizeChanged: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
    val shadowSpacing = remember { calculateShadowSpacing() }
    val shadowSpacingPx = with(density) { shadowSpacing.toPx().toInt() }
    val paddingPx = with(density) { 8.dp.toPx().toInt() }

    // Popover 위치 계산
    // WindowInsets(0)인 경우: windowInsetsTopPx = 0, windowInsetsBottomPx = 0
    // 실제 사용 가능한 화면 높이 = screenHeightPx - windowInsetsTopPx - windowInsetsBottomPx
    val fullScreenHeightPx = screenHeightPx

    LaunchedEffect(
        popoverState.contentPositionY,
        popoverState.contentPositionX,
        popoverState.contentPositionYInWindow,
        popoverState.contentHeight,
        popoverState.contentWidth,
        popoverState.tooltipWidth,
        popoverState.tooltipHeight,
        windowInsetsBottomPx,
        fullScreenHeightPx,
        shadowSpacingPx,
        screenWidthPx,     // 화면 폭 변경 시 재계산
        align,             // align 변경 시 재계산
        positionTop        // positionTop 변경 시 재계산
    ) {
        if (popoverState.contentHeight > 0 && popoverState.contentWidth > 0) {
            onCalculatePosition(
                windowInsetsBottomPx,
                fullScreenHeightPx,
                estimatedTooltipHeight,
                shadowSpacingPx,
                screenWidthPx,
                paddingPx
            )
        }
    }

    val popoverSpacingPx = with(density) { SPACING_BETWEEN_POPOVER_DP.dp.toPx().toInt() }

    val popupOffset = remember(
        popoverState.offsetX,
        popoverState.isPopupAbove,
        popoverState.contentPositionY,
        popoverState.tooltipHeight,
        popoverState.contentHeight,
        popoverState.overlapBottom,
        windowInsetsBottomPx,
        shadowSpacingPx,
        popoverSpacingPx,
        align,           // align이 offset 계산에 영향
        positionTop      // positionTop이 위치 결정에 영향
    ) {
        calculatePopupOffset(popoverState, windowInsetsBottomPx, shadowSpacingPx, popoverSpacingPx)
    }

    // Popup properties도 remember로 캐시
    // Always update popupProperties when 'always' changes
    val popupProperties = remember(always) {
        createPopupProperties(always)
    }

    Popup(
        alignment = Alignment.TopStart,
        offset = popupOffset,
        properties = popupProperties,
        onDismissRequest = {
            if (!always) {
                onDismiss()
            }
        }
    ) {
        PopoverWithShadow(
            shadowStyle = WantedShadowSpreadStyle.Small(),
            onSizeChange = onTooltipSizeChanged,
            content = body
        )
    }
}

private fun calculatePopupOffset(
    popoverState: WantedPopoverState,
    windowInsetsBottomPx: Float,
    shadowSpacingPx: Int,
    spacingBetweenPopoverPx: Int
): IntOffset {
    return IntOffset(
        x = popoverState.offsetX,
        y = if (popoverState.isPopupAbove) {
            // 위쪽에 표시할 때: content 위치에서 툴팁 높이와 간격, 그림자 여백 제거
            var positionY =
                popoverState.contentPositionY.toInt() - popoverState.tooltipHeight - spacingBetweenPopoverPx - shadowSpacingPx

            // overlapBottom 조건일 때 추가 보정 (원래 로직)
            if (popoverState.overlapBottom) {
                positionY = positionY - windowInsetsBottomPx.toInt() - popoverState.contentHeight
            }

            positionY
        } else {
            // 아래쪽에 표시할 때: content 아래 + 간격 - 그림자 여백
            popoverState.contentPositionY.toInt() + popoverState.contentHeight + spacingBetweenPopoverPx - shadowSpacingPx
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

/**
 * 텍스트와 헤딩을 포함한 기본적인 팝오버를 표시하는 Composable입니다.
 * 텍스트, 헤딩, 닫기 버튼, 액션 버튼 등의 다양한 옵션을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState()
 * WantedPopover(
 *     modifier = Modifier,
 *     text = "팝오버 내용 텍스트",
 *     heading = "제목",
 *     state = popoverState,
 *     closeButton = true,
 *     action = { Button("확인") { } },
 *     content = { Button("클릭", onClick = { popoverState.show() }) }
 * )
 * ```
 *
 * @param modifier Modifier를 통한 외형 스타일 지정입니다.
 * @param text 팝오버에 표시할 본문 텍스트입니다.
 * @param heading 팝오버에 표시할 헤딩 텍스트입니다. 빈 문자열인 경우 표시되지 않습니다.
 * @param state 팝오버의 표시/숨김 상태를 관리하는 상태 객체입니다.
 * @param windowInsets 윈도우 인셋 설정입니다. 기본값은 시스템 바입니다.
 * @param align 팝오버의 정렬 방식입니다. (Left, Center, Right)
 * @param closeButton 닫기 버튼 표시 여부입니다.
 * @param positionTop 팝오버를 위쪽에 표시할지 여부입니다.
 * @param always 팝오버가 항상 표시될지 여부입니다. true인 경우 외부 클릭으로 닫히지 않습니다.
 * @param action 팝오버 하단에 표시할 액션 버튼들입니다.
 * @param content 팝오버가 연결될 기준 콘텐츠입니다.
 */
@Composable
fun WantedPopover(
    modifier: Modifier,
    text: String,
    heading: String = "",
    state: WantedSimplePopoverState? = null,
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
                text = {
                    PopoverBody(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        closeButton = if (heading.isEmpty()) closeButton else false,
                        onDismiss = { state?.dismiss() }
                    )
                },
                heading = if (heading.isNotEmpty()) {
                    {
                        PopoverHeader(
                            modifier = Modifier.fillMaxWidth(),
                            heading = heading,
                            closeButton = closeButton,
                            onDismiss = { state?.dismiss() }
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

// Stateless UI Components
@Composable
private fun PopoverHeader(
    modifier: Modifier = Modifier,
    heading: String,
    closeButton: Boolean,
    onDismiss: (() -> Unit)?
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

        if (closeButton && onDismiss != null) {
            PopoverCloseButton(onDismiss = onDismiss)
        }
    }
}

@Composable
private fun PopoverBody(
    modifier: Modifier = Modifier,
    text: String,
    closeButton: Boolean,
    onDismiss: (() -> Unit)?
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

        if (closeButton && onDismiss != null) {
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
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
}

@Composable
private fun PopoverWithShadow(
    modifier: Modifier = Modifier,
    shadowStyle: WantedShadowSpreadStyle,
    onSizeChange: (width: Int, height: Int) -> Unit = { _, _ -> },
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    // shadowSpacing 계산을 remember로 캐시
    val shadowSpacing = remember { calculateShadowSpacing() }

    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        onSizeChange(coordinates.size.width, coordinates.size.height)
                    }
                    .wantedDropShadowSpread(style = shadowStyle)
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

/**
 * 팝오버 상태를 관리하는 State 객체를 생성하고 기억합니다.
 * 팝오버의 표시/숨김 상태를 관리하는 상태 객체를 생성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState(initialVisible = false)
 *
 * // 팝오버 표시
 * popoverState.show()
 *
 * // 팝오버 숨김
 * popoverState.dismiss()
 * ```
 *
 * @param initialVisible 초기 표시 상태입니다. 기본값은 false입니다.
 * @return 팝오버 상태를 관리하는 WantedSimplePopoverState 객체
 */
@Composable
fun rememberPopoverState(initialVisible: Boolean = false): WantedSimplePopoverState {
    val stateHolder = rememberWantedPopoverStateHolder(initialVisible)
    return remember(stateHolder) { WantedSimplePopoverStateImpl(stateHolder) }
}

// Constants
private const val SPACING_BETWEEN_POPOVER_DP = 8
