package com.wanted.android.montage.sample.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 데모 Preview 격자 배경을 켤지 여부.
 *
 * 데모 화면 전체가 하나의 값을 함께 쓴다 — 한 데모에서 켜면 다른 데모로 이동해도 유지된다.
 * `WantedDesignSystemDemoActivity` 가 제공하고, 각 데모의 Option 스위치(`DSWantedCheckerBackgroundOptionCell`)가 바꾼다.
 */
val LocalDemoCheckerBackground = compositionLocalOf<MutableState<Boolean>> { mutableStateOf(false) }

/**
 * 데모 Preview 영역에 격자 배경을 그린다. [LocalDemoCheckerBackground] 가 꺼져 있으면 아무것도 하지 않는다.
 *
 * 켜져 있을 때만 [shape] 로 잘라, 꺼진 상태의 기존 Preview 모양은 바꾸지 않는다.
 */
fun Modifier.demoCheckerBackground(
    shape: Shape = RoundedCornerShape(8.dp),
): Modifier = composed {
    if (LocalDemoCheckerBackground.current.value) {
        clip(shape).checkerboardBackground()
    } else {
        this
    }
}

/**
 * 흰색·빨간색 격자 배경을 그린다.
 *
 * 컴포넌트가 자체 배경을 갖는지, 투명 영역·그림자·테두리가 어떻게 겹치는지 눈으로 확인하기 위한 디버그용 배경이다.
 * 빨간색은 눈이 피로하지 않도록 알파를 낮춰 은은하게 그린다.
 * 디자인 토큰이 아닌 확인용 패턴이라 색을 직접 지정한다.
 *
 * @param enabled false 면 아무것도 그리지 않는다.
 * @param cellSize 격자 한 칸의 크기.
 */
fun Modifier.checkerboardBackground(
    enabled: Boolean = true,
    cellSize: Dp = 8.dp,
): Modifier {
    if (!enabled) return this
    return drawBehind {
        drawRect(color = CHECKER_LIGHT_COLOR)

        val cell = cellSize.toPx()
        if (cell <= 0f) return@drawBehind
        val columns = (size.width / cell).toInt() + 1
        val rows = (size.height / cell).toInt() + 1
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                if ((row + column) % 2 == 0) continue
                drawRect(
                    color = CHECKER_RED_COLOR,
                    topLeft = Offset(column * cell, row * cell),
                    size = Size(cell, cell)
                )
            }
        }
    }
}

private val CHECKER_LIGHT_COLOR = Color.White
private val CHECKER_RED_COLOR = Color(0xFFFF4242).copy(alpha = 0.18f)
