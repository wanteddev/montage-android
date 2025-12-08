package com.wanted.android.wanted.design.presentation.popover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * interface WantedSimplePopoverState
 *
 * Popover의 표시/숨김 상태를 관리하는 인터페이스입니다.
 */
@Stable
interface WantedSimplePopoverState {
    fun show()
    fun dismiss()
    val isVisible: Boolean
}

@Stable
internal data class WantedPopoverState(
    val isVisible: Boolean = false,
    val contentPositionY: Float = 0f,
    val contentPositionX: Float = 0f,
    val contentPositionYInWindow: Float = 0f,
    val contentHeight: Int = 0,
    val contentWidth: Int = 0,
    val tooltipWidth: Int = 0,
    val tooltipHeight: Int = 0,
    val offsetX: Int = 0,
    val isPopupAbove: Boolean = false,
    val overlapBottom: Boolean = false
)

@Stable
internal interface WantedPopoverStateHolder {
    val state: WantedPopoverState

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
    fun calculatePopoverPosition(
        windowInsetsBottomPx: Float,
        windowInsetsTopPx: Float,
        screenHeightPx: Float,
        estimatedTooltipHeight: Float,
        positionTop: Boolean,
        align: WantedPopoverAlign,
        screenWidthPx: Int,
        paddingPx: Int
    )
}

internal class WantedSimplePopoverStateImpl(
    private val stateHolder: WantedPopoverStateHolder
) : WantedSimplePopoverState {
    override fun show() = stateHolder.show()
    override fun dismiss() = stateHolder.dismiss()
    override val isVisible: Boolean get() = stateHolder.state.isVisible
}

private class WantedPopoverStateHolderImpl(
    initialVisible: Boolean
) : WantedPopoverStateHolder {

    private var _state by mutableStateOf(WantedPopoverState(isVisible = initialVisible))
    override val state: WantedPopoverState get() = _state

    override fun show() {
        _state = _state.copy(isVisible = true)
    }

    override fun dismiss() {
        _state = _state.copy(isVisible = false)
    }

    override fun updateContentPosition(
        positionY: Float,
        positionX: Float,
        positionYInWindow: Float,
        height: Int,
        width: Int
    ) {
        _state = _state.copy(
            contentPositionY = positionY,
            contentPositionX = positionX,
            contentPositionYInWindow = positionYInWindow,
            contentHeight = height,
            contentWidth = width
        )
    }

    override fun updateTooltipSize(width: Int, height: Int) {
        _state = _state.copy(
            tooltipWidth = width,
            tooltipHeight = height
        )
    }

    override fun calculatePopoverPosition(
        windowInsetsBottomPx: Float,
        windowInsetsTopPx: Float,
        screenHeightPx: Float,
        estimatedTooltipHeight: Float,
        positionTop: Boolean,
        align: WantedPopoverAlign,
        screenWidthPx: Int,
        paddingPx: Int
    ) {
        // 실제 사용 가능한 화면 상단/하단 위치 계산
        val effectiveTopY = windowInsetsTopPx
        val effectiveBottomY = screenHeightPx - windowInsetsBottomPx

        // 컨텐츠 기준 위아래 공간 계산
        val spaceBelow = effectiveBottomY - (_state.contentPositionYInWindow + _state.contentHeight)
        val spaceAbove = _state.contentPositionYInWindow - effectiveTopY

        // 실제 툴팁 높이가 있으면 사용, 없으면 예상 높이 사용
        val tooltipHeightToCheck = if (_state.tooltipHeight > 0) _state.tooltipHeight else estimatedTooltipHeight.toInt()
        val requiredSpace = tooltipHeightToCheck + SPACING_BETWEEN_POPOVER

        // overlapBottom: 아래쪽 공간이 부족하고 위쪽 공간이 충분한 경우
        val newOverlapBottom = spaceBelow < requiredSpace && spaceAbove > requiredSpace
        val newOverlapTop = spaceAbove < requiredSpace && spaceBelow > requiredSpace

        val newIsPopupAbove = when {
            // 1. overlapBottom이 true인 경우: 강제로 위쪽에 배치
            newOverlapBottom -> true
            // 2. newOverlapTop이 true인 경우: 강제로 아래쪽에 배치
            newOverlapTop -> false
            // 3. positionTop이 true인 경우: 위쪽 공간이 충분하면 위쪽에 배치
            positionTop -> spaceAbove >= requiredSpace
            // 4. 기본값: 아래쪽에 배치
            else -> false
        }

        val baseOffsetX = calculatePopoverOffsetX(
            align = align,
            contentPositionX = _state.contentPositionX,
            contentWidth = _state.contentWidth,
            tooltipWidth = _state.tooltipWidth,
            screenWidthPx = screenWidthPx,
            paddingPx = paddingPx
        )

        // 값이 변경되었을 때만 상태 업데이트
        if (_state.overlapBottom != newOverlapBottom ||
            _state.isPopupAbove != newIsPopupAbove ||
            _state.offsetX != baseOffsetX
        ) {
            _state = _state.copy(
                overlapBottom = newOverlapBottom,
                isPopupAbove = newIsPopupAbove,
                offsetX = baseOffsetX
            )
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
            WantedPopoverAlign.Left -> 0f
            WantedPopoverAlign.Center -> (contentWidth - tooltipWidth) / 2f
            WantedPopoverAlign.Right -> (contentWidth - tooltipWidth).toFloat()
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
                idealOffsetX.toInt()
            }
        }

        return adjustedOffsetX
    }

    companion object {
        private const val SPACING_BETWEEN_POPOVER = 8
    }
}

// 내부용 StateHolder 생성 함수 (internal)
@Composable
internal fun rememberWantedPopoverStateHolder(
    initialVisible: Boolean = false
): WantedPopoverStateHolder = remember {
    WantedPopoverStateHolderImpl(initialVisible)
}

