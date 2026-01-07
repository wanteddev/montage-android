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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.chipIconSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.chipPadding
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.chipTextPadding
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.getchipRadius
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * WantedChip
 *
 * 사용자가 선택할 수 있는 Chip 컴포넌트를 생성합니다.
 * 텍스트, 아이콘, 크기, 활성화 여부 등의 다양한 설정을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedChip(
 *     text = "텍스트",
 *     leftIcon = R.drawable.ic_sample_icon,
 *     rightIcon = R.drawable.ic_sample_icon,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text String: 표시할 텍스트입니다.
 * @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
 * @param size ChipSize: Chip의 크기를 설정합니다.
 * @param variant ChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
 * @param isActive Boolean: 선택 여부 상태입니다.
 * @param isEnable Boolean: 사용 가능 여부입니다.
 * @param leftIcon Int?: 왼쪽에 표시할 아이콘 리소스 ID입니다.
 * @param rightIcon Int?: 오른쪽에 표시할 아이콘 리소스 ID입니다.
 * @param interactionSource MutableInteractionSource: 클릭 시의 상호작용 상태입니다.
 * @param onClick (() -> Unit)?: 클릭 시 실행되는 콜백입니다.
 */
@Composable
fun WantedChip(
    text: String,
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.Medium,
    variant: ChipVariant = ChipVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    CompositionLocalProvider(
        LocalWantedChipEnable.provides(isEnable),
        LocalWantedChipSize.provides(size),
        LocalWantedChipVariant.provides(variant),
        LocalWantedChipActive.provides(isActive)
    ) {
        WantedChip(
            text = text,
            modifier = modifier,
            interactionSource = interactionSource,
            chipDefault = WantedChipDefaults.getDefault(),
            leftIcon = leftIcon,
            rightIcon = rightIcon,
            onClick = onClick
        )
    }
}

/**
 * WantedChip
 *
 * 커스텀 스타일을 적용할 수 있는 Chip 컴포넌트를 생성합니다.
 * 기본 스타일 외에 WantedChipDefault를 통한 직접 설정이 가능합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedChip(
 *     text = "커스텀 텍스트",
 *     chipDefault = customChipDefault,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text String: 표시할 텍스트입니다.
 * @param modifier Modifier: Modifier를 통한 스타��� 지정입니다.
 * @param leftIcon Int?: 왼쪽 아이콘 리소스 ID입니다.
 * @param rightIcon Int?: 오른쪽 아이콘 리소스 ID입니다.
 * @param chipDefault WantedChipDefault: 직접 지정한 Chip 스타일입니다.
 * @param interactionSource MutableInteractionSource: 클릭 상호작용을 위한 상태 객체입니다.
 * @param onClick (() -> Unit)?: 클릭 시 실행될 콜백입니다.
 */
@Composable
fun WantedChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedChip(
        modifier = modifier,
        interactionSource = interactionSource,
        size = chipDefault.size,
        variant = chipDefault.variant,
        isActive = chipDefault.isActive,
        isEnable = chipDefault.isEnable,
        chipDefault = chipDefault,
        leftIcon = leftIcon?.let {
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
                )
            }
        },
        content = {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        rightIcon = rightIcon?.let {
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = rightIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = chipDefault.iconColor)
                )
            }
        },
        onClick = onClick
    )
}

/**
 *
 * WantedChip
 *
 * 좌우 아이콘과 텍스트 Content를 포함한 Chip Layout을 구성합니다.
 * 직접 Chip 기본 스타일 및 구성요소를 입력할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedChip(
 *     content = { Text("Content") },
 *     leftIcon = { Icon(...) },
 *     rightIcon = { Icon(...) }
 * )
 * ```
 *
 * @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
 * @param size ChipSize: Chip 크기 설정입니다.
 * @param variant ChipVariant: Chip 스타일 변형입니다.
 * @param isActive Boolean: 활성화 여부입니다.
 * @param isEnable Boolean: 사용 가능 여부입니다.
 * @param chipDefault WantedChipDefault: Chip 스타일 객체입니다.
 * @param interactionSource MutableInteractionSource: 터치 인터랙션 제어용 객체입니다.
 * @param content (@Composable () -> Unit): 텍스트 또는 기타 Composable 콘텐츠입니다.
 * @param leftIcon (@Composable (() -> Unit)?): 좌측 아이콘 Composable입니다.
 * @param rightIcon (@Composable (() -> Unit)?): 우측 아이콘 Composable입니다.
 * @param onClick (() -> Unit)?: 클릭 이벤트 콜백입니다.
 */
@Composable
fun WantedChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.Small,
    variant: ChipVariant = ChipVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    chipDefault: WantedChipDefault = WantedChipDefaults.getDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit = {},
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    WantedChipLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getchipRadius(chipDefault.size)))
            .background(chipDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getchipRadius(chipDefault.size)),
                color = chipDefault.borderColor
            )
            .clickOnce(
                interactionSource = interactionSource,
                indication = if (chipDefault.variant == ChipVariant.Solid) {
                    wantedRippleEffect(
                        color = DesignSystemTheme.colors.labelNormal.copy(
                            OPACITY_12
                        )
                    )
                } else {
                    wantedRippleEffect(
                        color = chipDefault.backgroundColor.copy(OPACITY_12)
                    )
                },
                enabled = chipDefault.isEnable && onClick != null
            ) {
                onClick?.invoke()
            },
        chipDefault = chipDefault,
        leftIcon = leftIcon,
        content = content,
        rightIcon = rightIcon
    )
}

@Composable
private fun WantedChipLayout(
    modifier: Modifier,
    chipDefault: WantedChipDefault,
    content: @Composable () -> Unit,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .chipPadding(size = chipDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            WantedChipContract.getChipHorizontalArrangement(
                size = chipDefault.size
            )
        )
    ) {
        leftIcon?.let {
            Box(
                modifier = Modifier.Companion.chipIconSize(chipDefault.size),
                contentAlignment = Alignment.Center
            ) {
                leftIcon()
            }
        }


        ProvideTextStyle(value = chipDefault.textStyle) {
            Box(
                modifier = Modifier.Companion
                    .chipTextPadding(chipDefault.size)
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }

        rightIcon?.let {
            Box(
                modifier = Modifier.chipIconSize(chipDefault.size),
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
private fun ChipPreView() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DesignSystemTheme.colors.backgroundNormalNormal
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        size = ChipSize.Small
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        isEnable = false,
                        size = ChipSize.Small
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        size = ChipSize.Small
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        isEnable = false,
                        size = ChipSize.Small
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        isEnable = false,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        isEnable = false,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        size = ChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        isEnable = false,
                        size = ChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        size = ChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        isEnable = false,
                        size = ChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Solid,
                        isEnable = false,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedChip(
                        text = "텍스트",
                        variant = ChipVariant.Outlined,
                        isEnable = false,
                        size = ChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            size = ChipSize.Medium
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            isEnable = false,
                            size = ChipSize.Medium
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            size = ChipSize.Medium
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            isEnable = false,
                            size = ChipSize.Medium
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            isEnable = false,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            isEnable = false,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            size = ChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            isEnable = false,
                            size = ChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            size = ChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            isEnable = false,
                            size = ChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Solid,
                            isEnable = false,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedChip(
                            text = "텍스트",
                            variant = ChipVariant.Outlined,
                            isEnable = false,
                            size = ChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WantedChip(
                                text = "텍스트",
                                variant = ChipVariant.Solid,
                                isActive = true,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ChipSize.Small
                            )
                            WantedChip(
                                text = "텍스트",
                                variant = ChipVariant.Solid,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ChipSize.Small
                            )
                            WantedChip(
                                text = "텍스트",
                                variant = ChipVariant.Outlined,
                                isActive = true,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ChipSize.Small
                            )
                            WantedChip(
                                text = "텍스트",
                                variant = ChipVariant.Outlined,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ChipSize.Small
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WantedChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipVariant.Solid,
                                    isActive = false,
                                    size = ChipSize.Small,
                                    isEnable = true,
                                    textStyle = DesignSystemTheme.typography.label1Medium.copy(
                                        DesignSystemTheme.colors.statusNegative
                                    ),
                                    iconColor = DesignSystemTheme.colors.statusNegative,
                                    backgroundColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipVariant.Solid,
                                    isActive = false,
                                    size = ChipSize.Small,
                                    isEnable = true,
                                    textStyle = DesignSystemTheme.typography.label1Medium.copy(
                                        DesignSystemTheme.colors.statusNegative
                                    ),
                                    iconColor = DesignSystemTheme.colors.statusNegative,
                                    backgroundColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedChip(
                                text = "텍스트",
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipVariant.Outlined,
                                    isActive = false,
                                    size = ChipSize.Small,
                                    isEnable = true,
                                    textStyle = DesignSystemTheme.typography.label1Medium.copy(
                                        DesignSystemTheme.colors.statusNegative
                                    ),
                                    iconColor = DesignSystemTheme.colors.statusNegative,
                                    backgroundColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_12),
                                    borderColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_22)
                                )
                            )
                            WantedChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedChipDefaults.getDefault(
                                    variant = ChipVariant.Outlined,
                                    isActive = false,
                                    size = ChipSize.Small,
                                    isEnable = false,
                                    textStyle = DesignSystemTheme.typography.label1Medium.copy(
                                        DesignSystemTheme.colors.statusNegative.copy(OPACITY_12)
                                    ),
                                    iconColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_12),
                                    backgroundColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_5),
                                    borderColor = DesignSystemTheme.colors.statusNegative
                                        .copy(OPACITY_12)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedChip(
                            text = "this is very very long long long long long long text",
                            variant = ChipVariant.Solid,
                            size = ChipSize.Medium,
                            modifier = Modifier.widthIn(max = 200.dp)
                        )
                    }
                }
            }
        }
    }
}