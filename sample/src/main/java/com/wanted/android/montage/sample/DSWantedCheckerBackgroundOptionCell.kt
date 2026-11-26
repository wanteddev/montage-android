package com.wanted.android.montage.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wanted.android.montage.sample.util.LocalDemoCheckerBackground

/**
 * 데모 Preview 격자 배경을 켜고 끄는 Option 스위치.
 *
 * 값은 데모 화면 전체가 공유하는 [LocalDemoCheckerBackground] 에 둔다 — 화면별 ViewState 에 두지 않는다.
 */
@Composable
internal fun DSWantedCheckerBackgroundOptionCell(
    modifier: Modifier = Modifier
) {
    val checkerBackground = LocalDemoCheckerBackground.current
    DSWantedOptionSwitchCell(
        modifier = modifier,
        text = "격자 배경 : ${checkerBackground.value}",
        checkState = checkerBackground.value,
        onCheckChanged = { checkerBackground.value = it }
    )
}
