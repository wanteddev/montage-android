package com.wanted.android.wanted.design.actions.chip

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipSize
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipVariant
import com.wanted.android.wanted.design.actions.chip.config.WantedChipDefault
import com.wanted.android.wanted.design.actions.chip.config.WantedChipDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 사용 편의성을 위한 필터 Chip 컴포저블입니다.
 * 간단한 설정만으로 기본 스타일의 필터 Chip을 사용할 수 있으며,
 * 내부적으로 CompositionLocal을 활용하여 관련 상태를 주입합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFilterChip(
 *     text = "필터",
 *     activeLabel = "3",
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * @param text: Chip에 표시될 텍스트
 * @param modifier: Modifier를 통해 스타일을 조정할 수 있음
 * @param activeLabel: Chip이 활성화되었을 때 표시할 라벨 텍스트
 * @param size: Chip의 크기를 정의하는 enum 값 (기본값: Small)
 * @param variant: Chip의 스타일 (기본값: Solid)
 * @param isActive: Chip의 활성화 상태
 * @param isEnable: Chip의 사용 가능 상태
 * @param isExpend: 확장 가능 상태 (아이콘 변경 목적)
 * @param interactionSource: 사용자 인터랙션 처리를 위한 객체
 * @param onClick: 클릭 시 호출되는 콜백 함수
 */
@Composable
fun WantedFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    activeLabel: String = "",
    size: ChipActionSize = ChipActionSize.Small,
    variant: ChipActionVariant = ChipActionVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    isExpend: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {

    CompositionLocalProvider(
        LocalWantedChipEnable.provides(isEnable),
        LocalWantedChipSize.provides(size),
        LocalWantedChipVariant.provides(variant),
        LocalWantedChipActive.provides(isActive)
    ) {
        WantedFilterChip(
            modifier = modifier,
            text = text,
            activeLabel = activeLabel,
            chipDefault = WantedChipDefaults
                .getDefault()
                .copy(iconColor = colorResource(id = WantedChipDefaults.getFilterIconColor())),
            isExpanded = isExpend,
            interactionSource = interactionSource,
            onClick = onClick
        )
    }
}

/**
 * Chip 기본 설정을 외부에서 주입할 수 있는 커스터마이징 버전의 필터 Chip 컴포저블입니다.
 * 기본 스타일뿐만 아니라 텍스트 스타일, 색상 등 세부 속성을 설정 가능하며
 * 내부적으로 주어진 chipDefault 설정값을 기반으로 Chip을 구성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFilterChip(
 *     text = "필터",
 *     activeLabel = "3",
 *     chipDefault = customDefault,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text: Chip에 표시될 텍스트
 * @param modifier: Modifier를 통해 스타일을 조정할 수 있음
 * @param activeLabel: Chip이 활성화되었을 때 표시할 라벨 텍스트
 * @param isExpanded: Chip이 확장 상태인지 여부 (화살표 아이콘에 반영됨)
 * @param chipDefault: 외부에서 주입하는 Chip의 기본 설정값
 * @param interactionSource: 사용자 인터랙션 처리를 위한 객체
 * @param onClick: 클릭 시 호출되는 콜백 함수
 */
@Composable
fun WantedFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    activeLabel: String = "",
    isExpanded: Boolean = false,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedFilterChip(
        modifier = modifier,
        interactionSource = interactionSource,
        size = chipDefault.size,
        variant = chipDefault.variant,
        isActive = chipDefault.isActive,
        isEnable = chipDefault.isEnable,
        chipDefault = chipDefault,
        content = {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f, fill = false),
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (activeLabel.isNotEmpty()) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = activeLabel,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = chipDefault.textStyle.merge(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        },
        rightIcon = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (isExpanded) {
                    painterResource(id = R.drawable.ic_normal_caret_up_svg)
                } else {
                    painterResource(id = R.drawable.ic_normal_caret_down_svg)
                },
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
            )
        },
        onClick = onClick
    )
}

@Composable
private fun WantedFilterChip(
    modifier: Modifier = Modifier,
    size: ChipActionSize = ChipActionSize.Small,
    variant: ChipActionVariant = ChipActionVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedFilterChipLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getChipRadius(chipDefault.size)))
            .background(chipDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getChipRadius(chipDefault.size)),
                color = chipDefault.borderColor
            )
            .clickOnce(
                interactionSource = interactionSource,
                indication = if (chipDefault.variant == ChipActionVariant.Solid) {
                    wantedRippleEffect(
                        color = colorResource(id = R.color.label_normal).copy(
                            OPACITY_12
                        )
                    )
                } else {
                    wantedRippleEffect(
                        color = chipDefault.backgroundColor.copy(OPACITY_12)
                    )
                },
                enabled = chipDefault.isEnable
            ) {
                onClick?.invoke()
            },
        chipDefault = chipDefault,
        content = content,
        rightIcon = rightIcon
    )
}


@Composable
private fun WantedFilterChipLayout(
    chipDefault: WantedChipDefault,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .filterChipPadding(size = chipDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(getFilterChipHorizontalArrangement(size = chipDefault.size))
    ) {

        ProvideTextStyle(value = chipDefault.textStyle) {
            Box(
                modifier = Modifier
                    .actionChipTextPadding(chipDefault.size)
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }

        rightIcon?.let {
            Box(
                modifier = Modifier.filterChipIconSize(chipDefault.size),
                contentAlignment = Alignment.Center
            ) {
                rightIcon()
            }
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Composable
private fun ActionChipPreView() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.background_normal_normal)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        isEnable = false,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        isEnable = false,
                        size = ChipActionSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        isExpend = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        isEnable = false,
                        isExpend = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        isExpend = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        size = ChipActionSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Solid,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        variant = ChipActionVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "1",
                        variant = ChipActionVariant.Solid,
                        isExpend = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "2",
                        variant = ChipActionVariant.Outlined,
                        isEnable = false,
                        isActive = true,
                        size = ChipActionSize.Medium
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "일",
                        variant = ChipActionVariant.Solid,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Small
                    )
                    WantedFilterChip(
                        text = "텍스트",
                        activeLabel = "이",
                        variant = ChipActionVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = ChipActionSize.Small
                    )
                }
            }
        }
    }
}