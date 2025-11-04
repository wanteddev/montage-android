package com.wanted.android.wanted.design.beta.drag

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.channels.Channel


inline fun <T : Any> LazyListScope.draggableItems(
    items: List<T>,
    dragDropState: DragDropState,
    crossinline content: @Composable (Modifier, T, index: Int, Boolean) -> Unit,
) {
    itemsIndexed(
        items = items,
        contentType = { index, _ -> DraggableItem(index = index) })
    { index, item ->
        val isCurrentDraggingItem = dragDropState.draggingItemIndex == index
        val modifier = if (isCurrentDraggingItem) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationY = dragDropState.delta
                }
        } else {
            Modifier
        }
        content(modifier, item, index, isCurrentDraggingItem)
    }
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.dragContainer(dragDropState: DragDropState): Modifier = composed {
    pointerInput(dragDropState) {
        detectDragGestures(
            onDrag = { change, offset ->
                change.consume()
                dragDropState.onDrag(offset = offset)
            },
            onDragStart = { offset ->
                dragDropState.onDragStart(offset)
            },
            onDragEnd = { dragDropState.onDragInterrupted() },
            onDragCancel = { dragDropState.onDragInterrupted() }
        )
    }
}

@Composable
fun rememberDragDropState(
    lazyListState: LazyListState,
    onMove: (Int, Int) -> Unit,
    draggableItemsNum: Int
): DragDropState {

    val state = remember(lazyListState) {
        DragDropState(
            draggableItemsNum = draggableItemsNum,
            stateList = lazyListState,
            onMove = onMove,
        )
    }
    LaunchedEffect(state) {
        while (true) {
            val diff = state.scrollChannel.receive()
            lazyListState.scrollBy(diff)
        }
    }
    return state
}

class DragDropState(
    private val draggableItemsNum: Int,
    private val stateList: LazyListState,
    private val onMove: (Int, Int) -> Unit,
) {
    var draggingItemIndex: Int? by mutableStateOf(null)

    var draggingEnable: Boolean by mutableStateOf(false)

    var delta by mutableFloatStateOf(0f)

    val scrollChannel = Channel<Float>()

    private var draggingItem: LazyListItemInfo? = null

    internal fun onDragStart(offset: Offset) {
        if (!draggingEnable) return
        stateList.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                (it.contentType as? DraggableItem)?.let { draggableItem ->
                    draggingItem = it
                    draggingItemIndex = draggableItem.index
                }
            }
    }

    internal fun onDragEnable() {
        resetItem()
        draggingEnable = true
    }

    internal fun onDragDisable() {
        draggingEnable = false
    }

    private fun resetItem() {
        draggingItem = null
        draggingItemIndex = null
        delta = 0f
    }

    internal fun onDragInterrupted() {
        resetItem()
        onDragDisable()
    }

    internal fun onDrag(offset: Offset) {
        if (!draggingEnable) return

        delta += offset.y

        val currentDraggingItemIndex =
            draggingItemIndex ?: return
        val currentDraggingItem =
            draggingItem ?: return

        val startOffset = currentDraggingItem.offset + delta
        val endOffset = currentDraggingItem.offset + currentDraggingItem.size + delta
        val middleOffset = startOffset + (endOffset - startOffset) / 2

        val targetItem = stateList.layoutInfo.visibleItemsInfo.find { item ->
            middleOffset.toInt() in item.offset..item.offset + item.size &&
                    currentDraggingItem.index != item.index &&
                    item.contentType is DraggableItem
        }

        if (targetItem != null) {
            val targetIndex = (targetItem.contentType as DraggableItem).index
            onMove(currentDraggingItemIndex, targetIndex)
            draggingItemIndex = targetIndex
            delta += currentDraggingItem.offset - targetItem.offset
            draggingItem = targetItem
        } else {
            val startOffsetToTop = startOffset - stateList.layoutInfo.viewportStartOffset
            val endOffsetToBottom = endOffset - stateList.layoutInfo.viewportEndOffset
            val scroll = when {
                startOffsetToTop < 0 -> startOffsetToTop.coerceAtMost(0f)
                endOffsetToBottom > 0 -> endOffsetToBottom.coerceAtLeast(0f)
                else -> 0f
            }

            if (scroll != 0f && currentDraggingItemIndex != 0 && currentDraggingItemIndex != draggableItemsNum - 1) {
                scrollChannel.trySend(scroll)
            }
        }
    }
}

data class DraggableItem(val index: Int)

@Stable
fun Modifier.dragItem(dragDropState: DragDropState?) = this.then(
    Modifier.pointerInput(dragDropState) {
        detectTapGestures(
            onPress = {
                dragDropState?.onDragEnable()
            }
        )
    }
)

@Deprecated("wantedDropShadow 사용")
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}