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

/**
 * fun rememberBottomSheetScrollPolicy(...)
 *
 * Bottom Sheet 안의 LazyColumn 스크롤 잔여값이 시트로 전달되어 튕기는 현상을 막는 NestedScrollConnection 정책을 만듭니다.
 *
 * 내용이 위쪽으로 더 스크롤될 수 없을 때 남는 위 방향(음수) 잔여 스크롤·플링만 소비하고, 아래 방향은 시트로 넘깁니다. 결과의 connection 을 LazyColumn 의 부모에 Modifier.nestedScroll 로 붙입니다.
 *
 * 사용 예시:
 * ```kotlin
 * val listState = rememberLazyListState()
 * val scrollPolicy = rememberBottomSheetScrollPolicy(listState)
 *
 * Column(modifier = Modifier.nestedScroll(scrollPolicy.connection)) {
 *     LazyColumn(state = listState) {
 *         // items(...)
 *     }
 * }
 * ```
 *
 * @param listState LazyListState: 정책을 연결할 LazyColumn 의 상태입니다. 값이 바뀔 때 정책을 새로 만드는 remember 키로만 쓰이며 스크롤 판정에는 사용되지 않습니다.
 * @return BottomSheetScrollPolicy: nestedScroll 에 연결할 connection 을 담은 정책입니다.
 */
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


