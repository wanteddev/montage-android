package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_74
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * WantedTextAreaCharacterCount
 *
 * TextArea 하단에 표시되는 글자 수 카운터 컴포넌트입니다.
 *
 * 현재 입력된 글자 수와 최대 글자 수를 "현재/최대" 형식으로 표시합니다.
 *
 * @param current Int: 현재 입력된 글자 수입니다.
 * @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param error Boolean: 오류 상태 여부입니다. true인 경우 현재 글자 수가 빨간색으로 표시됩니다.
 * @param enable Boolean: 활성화 여부입니다.
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
            style = WantedTextStyle(
                colorRes = when {
                    !enable -> R.color.label_disable
                    error -> R.color.status_negative
                    else -> R.color.label_alternative
                },
                style = DesignSystemTheme.typography.label2Medium
            )
        )

        Text(
            modifier = Modifier.alpha(OPACITY_74),
            text = "/$maxWordCount",
            style = WantedTextStyle(
                colorRes =  when {
                    !enable -> R.color.label_disable
                    else -> R.color.label_alternative
                },
                style = DesignSystemTheme.typography.label2Medium
            )
        )
    }
}