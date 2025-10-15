package com.wanted.android.wanted.design.presentation.popover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
interface WantedPopoverStateHolder {
    val isVisible: Boolean
    val visibleState: State<Boolean>
    val isShow: Boolean
    val contentPositionY: Float
    val contentPositionX: Float
    val contentPositionYInWindow: Float
    val contentHeight: Int
    val contentWidth: Int
    val tooltipWidth: Int
    val tooltipHeight: Int
    val offsetX: Int
    val isPopupAbove: Boolean
    val overlapBottom: Boolean

    fun show()
    fun dismiss()
    fun updateContentPosition(
        positionY: Float,
        positionX: Float,
        positionYInWindow: Float,
        height: Int,
        width: Int
    )
    fun updateTooltipSize(width: Int, height: Int)
    fun updateShowState(show: Boolean)
    fun calculatePopoverPosition(
        windowInsetsBottomPx: Float,
        screenHeightPx: Float,
        estimatedTooltipHeight: Float,
        positionTop: Boolean,
        shadowSpacingPx: Int,
        align: WantedPopoverAlign,
        screenWidthPx: Int,
        paddingPx: Int
    )
}

private class WantedPopoverStateHolderImpl(
    initialVisible: Boolean
) : WantedPopoverStateHolder {

    private val _visibleState = mutableStateOf(initialVisible)
    override val visibleState: State<Boolean>
        get() = _visibleState

    override var isShow by mutableStateOf(initialVisible)
    override var contentPositionY by mutableFloatStateOf(0f)
    override var contentPositionX by mutableFloatStateOf(0f)
    override var contentPositionYInWindow by mutableFloatStateOf(0f)
    override var contentHeight by mutableIntStateOf(0)
    override var contentWidth by mutableIntStateOf(0)
    override var tooltipWidth by mutableIntStateOf(0)
    override var tooltipHeight by mutableIntStateOf(0)
    override var offsetX by mutableIntStateOf(0)
    override var isPopupAbove by mutableStateOf(false)
    override var overlapBottom by mutableStateOf(false)

    override fun show() {
        _visibleState.value = true
    }

    override fun dismiss() {
        _visibleState.value = false
    }

    override val isVisible: Boolean
        get() = _visibleState.value

    override fun updateContentPosition(
        positionY: Float,
        positionX: Float,
        positionYInWindow: Float,
        height: Int,
        width: Int
    ) {
        contentPositionY = positionY
        contentPositionX = positionX
        contentPositionYInWindow = positionYInWindow
        contentHeight = height
        contentWidth = width
    }

    override fun updateTooltipSize(width: Int, height: Int) {
        tooltipWidth = width
        tooltipHeight = height
    }

    override fun updateShowState(show: Boolean) {
        isShow = show
    }

    override fun calculatePopoverPosition(
        windowInsetsBottomPx: Float,
        screenHeightPx: Float,
        estimatedTooltipHeight: Float,
        positionTop: Boolean,
        shadowSpacingPx: Int,
        align: WantedPopoverAlign,
        screenWidthPx: Int,
        paddingPx: Int
    ) {
        // 실제 사용 가능한 화면 하단 위치 계산
        val effectiveBottomY = screenHeightPx - windowInsetsBottomPx

        // 컨텐츠 기준 위아래 공간 계산
        val spaceBelow = effectiveBottomY - (contentPositionYInWindow + contentHeight)
        val spaceAbove = contentPositionYInWindow

        // 실제 툴팁 높이가 있으면 사용, 없으면 예상 높이 사용
        val tooltipHeightToCheck = if (tooltipHeight > 0) tooltipHeight else estimatedTooltipHeight.toInt()
        val requiredSpace = tooltipHeightToCheck + SPACING_BETWEEN_POPOVER

        // overlapBottom: 아래쪽 공간이 부족하고 위쪽 공간이 충분한 경우
        // 원래 로직에서 중요한 조건이었음
        overlapBottom = spaceBelow < requiredSpace && spaceAbove > requiredSpace

        // 위치 결정 로직 (원래 로직 복원)
        isPopupAbove = when {
            // 1. overlapBottom이 true인 경우: 강제로 위쪽에 배치
            overlapBottom -> true
            // 2. positionTop이 true인 경우: 위쪽 공간이 충분하면 위쪽에 배치
            positionTop -> spaceAbove >= requiredSpace
            // 3. 기본값: 아래쪽에 배치
            else -> false
        }

        val baseOffsetX = calculatePopoverOffsetX(
            align = align,
            contentPositionX = contentPositionX,
            contentWidth = contentWidth,
            tooltipWidth = tooltipWidth,
            screenWidthPx = screenWidthPx,
            paddingPx = paddingPx
        )

        offsetX = baseOffsetX - shadowSpacingPx
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
            WantedPopoverAlign.Left -> 0
            WantedPopoverAlign.Center -> (contentWidth - tooltipWidth) / 2
            WantedPopoverAlign.Right -> contentWidth - tooltipWidth
        }

        val contentLeft = contentPositionX + idealOffsetX
        val contentRight = contentLeft + tooltipWidth

        return when {
            contentLeft < paddingPx -> {
                (paddingPx - contentPositionX).toInt()
            }
            contentRight > screenWidthPx - paddingPx -> {
                (screenWidthPx - paddingPx - tooltipWidth - contentPositionX).toInt()
            }
            else -> {
                idealOffsetX
            }
        }
    }

    companion object {
        private const val SPACING_BETWEEN_POPOVER = 8
    }
}

@Composable
fun rememberWantedPopoverStateHolder(
    initialVisible: Boolean = false
): WantedPopoverStateHolder = remember {
    WantedPopoverStateHolderImpl(initialVisible)
}