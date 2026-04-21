package com.wanted.android.montage.sample.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 중복 클릭 방지 Modifier.
 */
fun Modifier.clickOnce(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime = remember { System.currentTimeMillis() }
    this.clickable(
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = {
            val now = System.currentTimeMillis()
            if (now - lastClickTime >= 200L) {
                lastClickTime = now
                onClick()
            }
        }
    )
}

/**
 * List가 비어있으면 null, 아니면 block 반환.
 */
@Composable
fun <T> List<T>.isNullOrBlock(
    block: @Composable (List<T>) -> Unit
): @Composable (() -> Unit)? {
    return if (isEmpty()) null else { { block(this) } }
}

/**
 * String이 비어있으면 null, 아니면 block 반환.
 */
@Composable
fun String.isNullOrBlock(
    block: @Composable (String) -> Unit
): @Composable (() -> Unit)? {
    return if (isEmpty()) null else { { block(this) } }
}

/**
 * Boolean이 false면 null, true면 block 반환.
 */
@Composable
fun Boolean.isNullOrBlock(
    block: @Composable () -> Unit
): @Composable (() -> Unit)? {
    return if (!this) null else { { block() } }
}
