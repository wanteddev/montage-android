package com.wanted.android.wanted.design.presentation.modal.bottomsheet

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

data class BottomSheetScrollPolicy(
    val connection: NestedScrollConnection
)

@Composable
fun rememberBottomSheetScrollPolicy(
    listState: LazyListState
): BottomSheetScrollPolicy {
    return remember(listState) {
        BottomSheetScrollPolicy(
            connection = object : NestedScrollConnection {

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val deltaY = available.y
                    val shouldConsume = deltaY < 0

                    if (shouldConsume) {
                        return Offset(x = 0f, y = deltaY)
                    }

                    return Offset.Zero
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    val shouldConsume = available.y < 0f

                    if (shouldConsume) {
                        return Velocity(x = 0f, y = available.y)
                    }

                    return Velocity.Zero
                }
            }
        )
    }
}


