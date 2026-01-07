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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * WantedPopover
 *
 * 커스텀 콘텐츠를 포함한 Popover 컴포넌트입니다.
 *
 * 특정 위치에 Popover를 표시하며, 다양한 정렬 옵션을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState()
 *
 * WantedPopover(
 *     modifier = Modifier,
 *     state = popoverState,
 *     align = WantedPopoverAlign.Center,
 *     body = { Text("Popover 내용") },
 *     content = {
 *         Button(onClick = { popoverState.show() }) {
 *             Text("클릭")
 *         }
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param state WantedSimplePopoverState?: Popover의 표시/숨김 상태를 관리하는 객체입니다.
 * @param align WantedPopoverAlign: Popover의 정렬 방식입니다.
 * @param positionTop Boolean: Popover를 위쪽에 표시할지 여부입니다.
 * @param always Boolean: 외부 클릭으로 닫히지 않도록 할지 여부입니다.
 * @param body (@Composable () -> Unit): Popover 내부 콘텐츠 슬롯입니다.
 * @param content (@Composable () -> Unit): Popover가 연결될 기준 콘텐츠 슬롯입니다.
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
            if (externalState.isVisible) {
                stateHolder.show()
            } else {
                stateHolder.dismiss()
            }
        }
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // align, positionTop 변경 시 위치 재계산 강제 실행
    LaunchedEffect(align, positionTop) {
        if (stateHolder.state.contentHeight > 0 && stateHolder.state.contentWidth > 0) {
            val windowInsetsBottomPx =
                with(density) { windowInsets.getBottom(density).toDp().toPx() }
            val windowInsetsTopPx =
                with(density) { windowInsets.getTop(density).toDp().toPx() }
            val screenHeight = configuration.screenHeightDp
            val screenHeightPx = with(density) { screenHeight.dp.toPx() }
            val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
            val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }.toInt()
            val paddingPx = with(density) { 8.dp.toPx().toInt() }

            stateHolder.calculatePopoverPosition(
                windowInsetsBottomPx = windowInsetsBottomPx,
                windowInsetsTopPx = windowInsetsTopPx,
                screenHeightPx = screenHeightPx,
                estimatedTooltipHeight = estimatedTooltipHeight,
                positionTop = positionTop,
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
        },
        onCalculatePosition = { windowInsetsBottomPx, screenHeightPx, estimatedTooltipHeight, windowInsetsTopPx, screenWidthPx, paddingPx ->
            stateHolder.calculatePopoverPosition(
                windowInsetsBottomPx = windowInsetsBottomPx,
                windowInsetsTopPx = windowInsetsTopPx,
                screenHeightPx = screenHeightPx,
                estimatedTooltipHeight = estimatedTooltipHeight,
                positionTop = positionTop,
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
    onCalculatePosition: (Float, Float, Float, Float, Int, Int) -> Unit,
    onTooltipSizeChanged: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // WindowInsets 계산 (WindowInsets(0)인 경우 모두 0이 됨)
    val windowInsetsBottomPx = with(density) { windowInsets.getBottom(density).toDp().toPx() }
    val windowInsetsTopPx = with(density) { windowInsets.getTop(density).toDp().toPx() }
    val screenHeightPx = with(density) { screenHeight.dp.toPx() }
    val screenWidthPx = with(density) { screenWidth.dp.toPx() }.toInt()

    Box(
        modifier = modifier.onGloballyPositioned(onContentPositioned)
    ) {
        content()

        if (popoverState.isVisible) {
            PopoverPopup(
                popoverState = popoverState,
                density = density,
                windowInsetsBottomPx = windowInsetsBottomPx,
                windowInsetsTopPx = windowInsetsTopPx,
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
    windowInsetsTopPx: Float,
    screenHeightPx: Float,
    screenWidthPx: Int,
    align: WantedPopoverAlign,
    positionTop: Boolean,
    always: Boolean,
    body: @Composable () -> Unit,
    onCalculatePosition: (Float, Float, Float, Float, Int, Int) -> Unit,
    onTooltipSizeChanged: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val estimatedTooltipHeight = with(density) { 80.dp.toPx() }
    val paddingPx = with(density) { 8.dp.toPx().toInt() }

    LaunchedEffect(
        popoverState.contentPositionY,
        popoverState.contentPositionX,
        popoverState.contentPositionYInWindow,
        popoverState.contentHeight,
        popoverState.contentWidth,
        popoverState.tooltipWidth,
        popoverState.tooltipHeight,
        windowInsetsBottomPx,
        screenHeightPx,
        screenWidthPx,
        align,
        positionTop
    ) {
        if (popoverState.contentHeight > 0 && popoverState.contentWidth > 0) {
            onCalculatePosition(
                windowInsetsBottomPx,
                screenHeightPx,
                estimatedTooltipHeight,
                windowInsetsTopPx,
                screenWidthPx,
                paddingPx
            )
        }
    }

    val popoverSpacingPx = with(density) { 8.dp.toPx().toInt() }

    val popupOffset = calculatePopupOffset(popoverState, windowInsetsBottomPx, popoverSpacingPx)

    val popupProperties = createPopupProperties(always)

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
        Card(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    onTooltipSizeChanged(coordinates.size.width, coordinates.size.height)
                }
                .graphicsLayer {
                    shadowElevation = 10.dp.toPx()
                    ambientShadowColor = Color.Black.copy(alpha = 0.2f)
                    spotShadowColor = Color.Black.copy(alpha = 0.5f)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            body()
        }
    }
}

private fun calculatePopupOffset(
    popoverState: WantedPopoverState,
    windowInsetsBottomPx: Float,
    spacingBetweenPopoverPx: Int
): IntOffset {
    return IntOffset(
        x = popoverState.offsetX,
        y = if (popoverState.isPopupAbove) {
            // 위쪽에 표시할 때: content 위치에서 툴팁 높이와 간격
            var positionY =
                popoverState.contentPositionY.toInt() - popoverState.tooltipHeight - spacingBetweenPopoverPx

            // overlapBottom 조건일 때 추가 보정
            if (popoverState.overlapBottom) {
                positionY = positionY - windowInsetsBottomPx.toInt() - popoverState.contentHeight
            }

            positionY
        } else {
            // 아래쪽에 표시할 때: content 아래 + 간격
            popoverState.contentPositionY.toInt() + popoverState.contentHeight + spacingBetweenPopoverPx
        }
    )
}

private fun createPopupProperties(always: Boolean): PopupProperties {
    return PopupProperties(
        focusable = !always,
        dismissOnClickOutside = !always,
        dismissOnBackPress = true,
        clippingEnabled = true
    )
}

/**
 * WantedPopover
 *
 * 텍스트와 헤딩을 포함한 기본 Popover 컴포넌트입니다.
 *
 * 텍스트, 헤딩, 닫기 버튼, 액션 버튼 등을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState()
 *
 * WantedPopover(
 *     modifier = Modifier,
 *     text = "Popover 내용 텍스트",
 *     heading = "제목",
 *     state = popoverState,
 *     closeButton = true,
 *     action = {
 *         Button(onClick = { popoverState.dismiss() }) {
 *             Text("확인")
 *         }
 *     },
 *     content = {
 *         Button(onClick = { popoverState.show() }) {
 *             Text("클릭")
 *         }
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param text String: Popover에 표시할 본문 텍스트입니다.
 * @param heading String: Popover에 표시할 헤딩 텍스트입니다. 빈 문자열인 경우 표시되지 않습니다.
 * @param state WantedSimplePopoverState?: Popover의 표시/숨김 상태를 관리하는 객체입니다.
 * @param windowInsets WindowInsets: 적용할 WindowInsets입니다.
 * @param align WantedPopoverAlign: Popover의 정렬 방식입니다.
 * @param closeButton Boolean: 닫기 버튼 표시 여부입니다.
 * @param positionTop Boolean: Popover를 위쪽에 표시할지 여부입니다.
 * @param always Boolean: 외부 클릭으로 닫히지 않도록 할지 여부입니다.
 * @param action (@Composable RowScope.() -> Unit)?: Popover 하단에 표시할 액션 버튼 슬롯입니다.
 * @param content (@Composable () -> Unit): Popover가 연결될 기준 콘텐츠 슬롯입니다.
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
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = DesignSystemTheme.typography.body2Bold,
            color = DesignSystemTheme.colors.labelNormal
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
            style = DesignSystemTheme.typography.label2Medium,
            color = DesignSystemTheme.colors.labelNeutral,
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
                tint = DesignSystemTheme.colors.labelNormal
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
            .background(DesignSystemTheme.colors.backgroundElevatedNormal)
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

/**
 * fun rememberPopoverState(...)
 *
 * popover 상태를 관리하는 State 객체를 생성하고 관리합니다.
 *
 * 사용 예시:
 * ```kotlin
 * val popoverState = rememberPopoverState(initialVisible = false)
 *
 * // popover 표시
 * popoverState.show()
 *
 * // popover 숨김
 * popoverState.dismiss()
 * ```
 *
 * @param initialVisible Boolean: 초기 표시 상태입니다.
 * @return WantedSimplePopoverState: popover 상태를 관리하는 객체입니다.
 */
@Composable
fun rememberPopoverState(initialVisible: Boolean = false): WantedSimplePopoverState {
    return WantedSimplePopoverStateImpl(rememberWantedPopoverStateHolder(initialVisible))
}

/**
 * enum class WantedPopoverAlign
 *
 * popover 의 정렬 방식을 정의하는 enum 클래스입니다.
 * - Left: 왼쪽 정렬입니다.
 * - Center: 중앙 정렬입니다.
 * - Right: 오른쪽 정렬입니다.
 */
enum class WantedPopoverAlign {
    Left,
    Center,
    Right
}