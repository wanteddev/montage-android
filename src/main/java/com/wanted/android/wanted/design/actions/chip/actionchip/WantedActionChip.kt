package com.wanted.android.wanted.design.actions.chip.actionchip

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipSize
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.ActionChipVariant
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.actionChipIconSize
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.actionChipPadding
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.actionChipTextPadding
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.getActionChipHorizontalArrangement
import com.wanted.android.wanted.design.actions.chip.actionchip.WantedActionChipContract.getActionChipRadius
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipActive
import com.wanted.android.wanted.design.actions.chip.config.LocalWantedChipEnable
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.OPACITY_22
import com.wanted.android.wanted.design.util.OPACITY_5
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 사용자가 선택할 수 있는 Chip 컴포넌트를 생성합니다.
 * 텍스트, 아이콘, 크기, 활성화 여부 등의 다양한 설정을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedActionChip(
 *     text = "텍스트",
 *     leftIcon = R.drawable.ic_sample_icon,
 *     rightIcon = R.drawable.ic_sample_icon,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text String: 표시할 텍스트입니다.
 * @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
 * @param size ActionChipSize: Chip의 크기를 설정합니다.
 * @param variant ActionChipVariant: Chip의 스타일 변형입니다 (Solid, Outlined).
 * @param isActive Boolean: 선택 여부 상태입니다.
 * @param isEnable Boolean: 사용 가능 여부입니다.
 * @param leftIcon Int?: 왼쪽에 표시할 아이콘 리소스 ID입니다.
 * @param rightIcon Int?: 오른쪽에 표시할 아이콘 리소스 ID입니다.
 * @param interactionSource MutableInteractionSource: 클릭 시의 상호작용 상태입니다.
 * @param onClick (() -> Unit)?: 클릭 시 실행되는 콜백입니다.
 */
@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    size: ActionChipSize = ActionChipSize.Medium,
    variant: ActionChipVariant = ActionChipVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    CompositionLocalProvider(
        LocalWantedChipEnable.provides(isEnable),
        LocalWantedActionChipSize.provides(size),
        LocalWantedActionChipVariant.provides(variant),
        LocalWantedChipActive.provides(isActive)
    ) {
        WantedActionChip(
            text = text,
            modifier = modifier,
            interactionSource = interactionSource,
            chipDefault = WantedActionChipDefaults.getDefault(),
            leftIcon = leftIcon,
            rightIcon = rightIcon,
            onClick = onClick
        )
    }
}

/**
 * 커스텀 스타일을 적용할 수 있는 Chip 컴포넌트를 생성합니다.
 * 기본 스타일 외에 chipDefault를 통한 직접 설정이 가능합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedActionChip(
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
 * @param chipDefault WantedActionChipDefault: 직접 지정한 Chip 스타일입니다.
 * @param interactionSource MutableInteractionSource: 클릭 상호작용을 위한 상태 객체입니다.
 * @param onClick (() -> Unit)?: 클릭 시 실행될 콜백입니다.
 */
@Composable
fun WantedActionChip(
    text: String,
    modifier: Modifier = Modifier,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    chipDefault: WantedActionChipDefault = WantedActionChipDefaults.getDefault(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null
) {
    WantedActionChip(
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
 * 좌우 아이콘과 텍스트 Content를 포함한 Chip Layout을 구성합니다.
 * 직접 Chip 기본 스타일 및 구성요소를 입력할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedActionChip(
 *     content = { Text("Content") },
 *     leftIcon = { Icon(...) },
 *     rightIcon = { Icon(...) }
 * )
 * ```
 *
 * @param modifier Modifier: Modifier를 통한 외형 스타일 지정입니다.
 * @param size ActionChipSize: Chip 크기 설정입니다.
 * @param variant ActionChipVariant: Chip 스타일 변형입니다.
 * @param isActive Boolean: 활성화 여부입니다.
 * @param isEnable Boolean: 사용 가능 여부입니다.
 * @param chipDefault WantedActionChipDefault: Chip 스타일 객체입니다.
 * @param interactionSource MutableInteractionSource: 터치 인터랙션 제어용 객체입니다.
 * @param content (@Composable () -> Unit): 텍스트 또는 기타 Composable 콘텐츠입니다.
 * @param leftIcon (@Composable (() -> Unit)?): 좌측 아이콘 Composable입니다.
 * @param rightIcon (@Composable (() -> Unit)?): 우측 아이콘 Composable입니다.
 * @param onClick (() -> Unit)?: 클릭 이벤트 콜백입니다.
 */
@Composable
fun WantedActionChip(
    modifier: Modifier = Modifier,
    size: ActionChipSize = ActionChipSize.Small,
    variant: ActionChipVariant = ActionChipVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    chipDefault: WantedActionChipDefault = WantedActionChipDefaults.getDefault(
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
    WantedActionChipLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getActionChipRadius(chipDefault.size)))
            .background(chipDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getActionChipRadius(chipDefault.size)),
                color = chipDefault.borderColor
            )
            .clickOnce(
                interactionSource = interactionSource,
                indication = if (chipDefault.variant == ActionChipVariant.Solid) {
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
private fun WantedActionChipLayout(
    modifier: Modifier,
    chipDefault: WantedActionChipDefault,
    content: @Composable () -> Unit,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .actionChipPadding(size = chipDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(getActionChipHorizontalArrangement(size = chipDefault.size))
    ) {
        leftIcon?.let {
            Box(
                modifier = Modifier.actionChipIconSize(chipDefault.size),
                contentAlignment = Alignment.Center
            ) {
                leftIcon()
            }
        }


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
                modifier = Modifier.actionChipIconSize(chipDefault.size),
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
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        size = ActionChipSize.Small
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        isEnable = false,
                        size = ActionChipSize.Small
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        size = ActionChipSize.Small
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        isEnable = false,
                        size = ActionChipSize.Small
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        isEnable = false,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        isEnable = false,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        size = ActionChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        isEnable = false,
                        size = ActionChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        size = ActionChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        isEnable = false,
                        size = ActionChipSize.Small,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Solid,
                        isEnable = false,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        size = ActionChipSize.Small,
                        leftIcon = R.drawable.icon_normal_bookmark,
                        rightIcon = R.drawable.icon_normal_bookmark
                    )
                    WantedActionChip(
                        text = "텍스트",
                        variant = ActionChipVariant.Outlined,
                        isEnable = false,
                        size = ActionChipSize.Small,
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
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            size = ActionChipSize.Medium
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            isEnable = false,
                            size = ActionChipSize.Medium
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            size = ActionChipSize.Medium
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            isEnable = false,
                            size = ActionChipSize.Medium
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            isEnable = false,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            isEnable = false,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            size = ActionChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            isEnable = false,
                            size = ActionChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            size = ActionChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            isEnable = false,
                            size = ActionChipSize.Medium,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Solid,
                            isEnable = false,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            size = ActionChipSize.Medium,
                            leftIcon = R.drawable.icon_normal_bookmark,
                            rightIcon = R.drawable.icon_normal_bookmark
                        )
                        WantedActionChip(
                            text = "텍스트",
                            variant = ActionChipVariant.Outlined,
                            isEnable = false,
                            size = ActionChipSize.Medium,
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
                            WantedActionChip(
                                text = "텍스트",
                                variant = ActionChipVariant.Solid,
                                isActive = true,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ActionChipSize.Small
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ActionChipVariant.Solid,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ActionChipSize.Small
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ActionChipVariant.Outlined,
                                isActive = true,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ActionChipSize.Small
                            )
                            WantedActionChip(
                                text = "텍스트",
                                variant = ActionChipVariant.Outlined,
                                isActive = true,
                                isEnable = false,
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                size = ActionChipSize.Small
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedActionChipDefaults.getDefault(
                                    variant = ActionChipVariant.Solid,
                                    isActive = false,
                                    size = ActionChipSize.Small,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedActionChipDefaults.getDefault(
                                    variant = ActionChipVariant.Solid,
                                    isActive = false,
                                    size = ActionChipSize.Small,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                chipDefault = WantedActionChipDefaults.getDefault(
                                    variant = ActionChipVariant.Outlined,
                                    isActive = false,
                                    size = ActionChipSize.Small,
                                    isEnable = true,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12),
                                    borderColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_22)
                                )
                            )
                            WantedActionChip(
                                text = "텍스트",
                                leftIcon = R.drawable.icon_normal_bookmark,
                                rightIcon = R.drawable.icon_normal_bookmark,
                                chipDefault = WantedActionChipDefaults.getDefault(
                                    variant = ActionChipVariant.Outlined,
                                    isActive = false,
                                    size = ActionChipSize.Small,
                                    isEnable = false,
                                    textStyle = WantedTextStyle(
                                        colorRes = R.color.status_negative,
                                        style = DesignSystemTheme.typography.label1Medium,
                                        alpha = OPACITY_12
                                    ),
                                    iconColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_12),
                                    backgroundColor = colorResource(id = R.color.status_negative)
                                        .copy(OPACITY_5),
                                    borderColor = colorResource(id = R.color.status_negative)
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
                        WantedActionChip(
                            text = "this is very very long long long long long long text",
                            variant = ActionChipVariant.Solid,
                            size = ActionChipSize.Medium,
                            modifier = Modifier.widthIn(max = 200.dp)
                        )
                    }
                }
            }
        }
    }
}