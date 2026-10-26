package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_74

/**
 * fun WantedTextAreaCharacterCount(...)
 *
 * 입력한 글자 수를 `현재/최대` 형태로 표시하는 글자 수 표시 컴포넌트입니다.
 *
 * 본체 WantedTextArea 는 글자 수를 그리지 않으므로, WantedFormControl 의 accessory 슬롯에 이 컴포넌트를 넣어 표시합니다(deprecated WantedTextArea 오버로드만 내부에서 직접 그립니다). 다른 입력 컴포넌트에도 붙일 수 있습니다. 글자 수를 세거나 입력을 막지는 않으므로 current 와 error 는 호출부가 계산해서 넘깁니다.
 *
 * 사용 예시:
 * ```kotlin
 * val state = remember { mutableStateOf(TextFieldValue("")) }
 *
 * WantedFormControl(
 *     label = "자기소개",
 *     accessory = {
 *         WantedTextAreaCharacterCount(
 *             current = state.value.text.length,
 *             maxWordCount = 200,
 *             error = state.value.text.length > 200
 *         )
 *     },
 *     input = {
 *         WantedTextArea(
 *             value = state.value,
 *             maxWordCount = 200,
 *             onValueChange = { state.value = it }
 *         )
 *     }
 * )
 * ```
 *
 * @param current Int: 현재 글자 수입니다.
 * @param maxWordCount Int: 최대 글자 수입니다. `/최대` 로 표시됩니다.
 * @param modifier Modifier: 표시 영역에 적용할 Modifier입니다. 좌우 6dp 여백이 항상 더해집니다.
 * @param error Boolean: true 이면 현재 글자 수를 부정(에러) 색으로 표시합니다. 최대 글자 수 쪽 색은 바뀌지 않습니다.
 * @param enable Boolean: false 이면 두 숫자를 모두 비활성 색으로 표시하며, error 보다 우선합니다.
 */
@Composable
fun WantedTextAreaCharacterCount(
    current: Int,
    maxWordCount: Int,
    modifier: Modifier = Modifier,
    error: Boolean = false,
    enable: Boolean = true
) {
    Row(
        modifier = modifier.padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.alpha(OPACITY_74),
            text = "$current",
            style = DesignSystemTheme.typography.caption1Bold,
            color = when {
                !enable -> DesignSystemTheme.colors.foregroundDisablePrimary
                error -> DesignSystemTheme.colors.foregroundNegativePrimary
                else -> DesignSystemTheme.colors.foregroundNeutralTertiary
            }
        )

        Text(
            modifier = Modifier.alpha(OPACITY_74),
            text = "/$maxWordCount",
            style = DesignSystemTheme.typography.caption1Bold,
            color = when {
                !enable -> DesignSystemTheme.colors.foregroundDisablePrimary
                else -> DesignSystemTheme.colors.foregroundNeutralTertiary
            }
        )
    }
}