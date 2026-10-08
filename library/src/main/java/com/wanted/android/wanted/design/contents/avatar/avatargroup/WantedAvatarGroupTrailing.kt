package com.wanted.android.wanted.design.contents.avatar.avatargroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefaults
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedAvatarGroupTrailingText
 *
 * [WantedAvatarGroup]의 trailingContent 슬롯에 사용하는 텍스트 프리셋입니다.
 *
 * 색상 Foreground/Neutral/Secondary, 타이포 Label 1/Normal - Medium 토큰이 고정 적용되며,
 * 표시 상한(5명)을 초과한 인원을 안내하는 용도로 주로 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAvatarGroup(
 *     modelList = modelList,
 *     size = WantedAvatarGroupSize.XSmall,
 *     trailingContent = { WantedAvatarGroupTrailingText(text = "외 3명") }
 * )
 * ```
 *
 * @param text String: 표시할 문구입니다.
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 *
 * @see WantedAvatarGroup
 * @see WantedAvatarGroupTrailingTextButton
 */
@Composable
fun WantedAvatarGroupTrailingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = DesignSystemTheme.colors.foregroundNeutralSecondary,
        style = DesignSystemTheme.typography.label1Medium
    )
}

/**
 * WantedAvatarGroupTrailingTextButton
 *
 * [WantedAvatarGroup]의 trailingContent 슬롯에 사용하는 텍스트 버튼 프리셋입니다.
 *
 * [WantedTextButton] 위에 trailing 스펙의 색·타이포 토큰을 적용한 형태로,
 * [WantedAvatarGroupTrailingText]와 보이는 모습은 같고 클릭 영역만 추가됩니다.
 * 밑줄 등 별도의 시각적 구분은 두지 않습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAvatarGroup(
 *     modelList = modelList,
 *     size = WantedAvatarGroupSize.XSmall,
 *     trailingContent = {
 *         WantedAvatarGroupTrailingTextButton(text = "외 3명", onClick = { /* 클릭 동작 */ })
 *     }
 * )
 * ```
 *
 * @param text String: 표시할 문구입니다.
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param enabled Boolean: 클릭 가능 여부입니다.
 * @param onClick (() -> Unit): 클릭 시 호출될 콜백 함수입니다.
 *
 * @see WantedAvatarGroup
 * @see WantedAvatarGroupTrailingText
 */
@Composable
fun WantedAvatarGroupTrailingTextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    // WantedTextButtonSize.SMALL은 min-height 28dp + 상하 4dp 패딩이라 Figma Text Button 치수와 같다.
    // 색·타이포는 color 축(PRIMARY/ASSISTIVE)으로 표현되지 않아 Figma의 customize 속성대로 덮는다.
    WantedTextButton(
        text = text,
        modifier = modifier,
        size = WantedTextButtonSize.SMALL,
        enabled = enabled,
        onClick = onClick,
        buttonDefault = WantedTextButtonDefaults.getDefault(
            size = WantedTextButtonSize.SMALL,
            enabled = enabled,
            contentColor = DesignSystemTheme.colors.foregroundNeutralSecondary,
            textStyle = DesignSystemTheme.typography.label1Medium
        )
    )
}

@DevicePreviews
@Composable
private fun WantedAvatarGroupTrailingPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAvatarGroupTrailingText(text = "외 3명")

                WantedAvatarGroupTrailingTextButton(text = "외 3명", onClick = {})
            }
        }
    }
}
