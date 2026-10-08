package com.wanted.android.wanted.design.input.segmentedcontrol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedSegmentedControlItem
 *
 * SegmentedControl 내 개별 항목을 구성하는 컴포넌트입니다.
 *
 * 선택 여부에 따라 텍스트·아이콘 색상이 변경되며, 선택 시 강조 색상(foregroundNeutralPrimary)이 적용됩니다.
 * 사이즈별 타이포·아이콘 크기·간격은 [LocalWantedSegmentedSize] 로 전달된 값을 따릅니다.
 *
 * 아이콘 전용(Icon Only) 여부는 파라미터가 아니라 상위 컨트롤이 제공하는 [LocalWantedSegmentedIconOnly]
 * 로 결정됩니다. 컨트롤([WantedSegmentedControl])에서 `iconOnly = true` 로 설정하면 이 항목은
 * 텍스트 없이 아이콘만 중앙 정렬하며, 사이즈별 고정 너비(`iconOnlyWidth`)를 갖습니다.
 * 아이콘 크기도 icon + text 모드(`iconSize`)보다 한 단계 큰 `iconOnlyIconSize` 를 사용합니다.
 *
 * 아이콘 슬롯은 사이즈별 크기(`iconSize` / `iconOnlyIconSize`)를 가진 Box 로 감쌉니다. 이 Box 는
 * 최대 크기만 제한하므로 슬롯보다 **작은** 아이콘은 확대되지 않습니다. 스펙 크기로 꽉 채우려면
 * 호출측에서 아이콘에 `Modifier.fillMaxSize()` 를 지정해야 합니다.
 * (`Modifier.requiredSize` 처럼 제약을 무시하는 Modifier 는 슬롯을 벗어나므로 사용하지 않습니다.)
 *
 * 사용 예시:
 * ```kotlin
 * // text / icon + text
 * WantedSegmentedControlItem(
 *     title = "알림",
 *     isSelected = true,
 *     icon = { Icon(modifier = Modifier.fillMaxSize(), ...) }
 * )
 *
 * // icon only — 컨트롤에서 iconOnly = true 를 지정하면 title 없이 아이콘만 렌더링됩니다.
 * WantedSegmentedControlItem(
 *     isSelected = true,
 *     icon = { Icon(modifier = Modifier.fillMaxSize(), ...) }
 * )
 * ```
 *
 * @param isSelected Boolean: 항목의 선택 여부입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param title String?: 항목에 표시할 텍스트입니다. Icon Only 모드에서는 무시됩니다.
 * @param icon (@Composable () -> Unit)?: 표시할 아이콘 Composable입니다. Icon Only 모드에서는 필수입니다. 사이즈별 아이콘 슬롯을 꽉 채우려면 `Modifier.fillMaxSize()` 를 지정합니다.
 */
@Composable
fun WantedSegmentedControlItem(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: @Composable (() -> Unit)? = null
) {
    val size = LocalWantedSegmentedSize.current
    val iconOnly = LocalWantedSegmentedIconOnly.current
    val contentColor = if (isSelected) {
        DesignSystemTheme.colors.foregroundNeutralPrimary
    } else {
        DesignSystemTheme.colors.foregroundNeutralTertiary
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        if (iconOnly) {
            Box(
                // Icon Only 세그먼트는 정사각이 아니라 높이보다 2dp 넓은 고정 크기다.
                modifier = modifier.size(width = size.iconOnlyWidth, height = size.height),
                contentAlignment = Alignment.Center
            ) {
                icon?.let {
                    Box(modifier = Modifier.size(size.iconOnlyIconSize)) {
                        it()
                    }
                }
            }
        } else {
            Row(
                modifier = modifier
                    .defaultMinSize(minHeight = size.height)
                    .padding(horizontal = size.horizontalPadding)
                    .padding(vertical = size.verticalPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = size.iconTextGap,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                icon?.let {
                    Box(modifier = Modifier.size(size.iconSize)) {
                        it()
                    }
                }

                title?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = size.textStyle,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSegmentedControlItemPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSegmentedControlItem(
                    title = "타이틀",
                    isSelected = false
                )

                WantedSegmentedControlItem(
                    title = "타이틀",
                    isSelected = true
                )

                WantedSegmentedControlItem(
                    title = "타이틀",
                    isSelected = true,
                    icon = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                CompositionLocalProvider(LocalWantedSegmentedIconOnly provides true) {
                    WantedSegmentedControlItem(
                        isSelected = true,
                        icon = {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = R.drawable.icon_normal_circle_exclamation_fill),
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
        }
    }
}
