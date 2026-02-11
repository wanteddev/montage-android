package com.wanted.android.wanted.design.input.filterbutton

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
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.FilterButtonVariant
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.filterButtonIconSize
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.filterButtonPadding
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.filterButtonTextPadding
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.getFilterButtonHorizontalArrangement
import com.wanted.android.wanted.design.input.filterbutton.WantedFilterButtonContract.getFilterButtonRadius
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_12
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * WantedFilterButton
 *
 * 사용 편의성을 위한 Filter button입니다.
 * 간단한 설정만으로 기본 스타일의 Filter button을 사용할 수 있으며,
 * 내부적으로 CompositionLocal을 활용하여 관련 상태를 주입합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFilterButton(
 *     text = "필터",
 *     activeLabel = "3",
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * @param text String: FilterButton에 표시될 텍스트입니다.
 * @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
 * @param activeLabel String: FilterButton이 활성화되었을 때 표시할 라벨 텍스트입니다.
 * @param size FilterButtonSize: FilterButton의 크기를 정의하는 enum 값입니다 (기본값: Small).
 * @param variant FilterButtonVariant: FilterButton의 스타일입니다 (기본값: Solid).
 * @param isActive Boolean: FilterButton의 활성화 상태입니다.
 * @param isEnable Boolean: FilterButton의 사용 가능 상태입니다.
 * @param isExpend Boolean: 확장 가능 상태입니다 (아이콘 변경 목적).
 * @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
 * @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedFilterButton(
    text: String,
    modifier: Modifier = Modifier,
    activeLabel: String = "",
    size: FilterButtonSize = FilterButtonSize.Small,
    variant: FilterButtonVariant = FilterButtonVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    isExpend: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
) {

    CompositionLocalProvider(
        LocalWantedFilterButtonEnable.provides(isEnable),
        LocalWantedFilterButtonSize.provides(size),
        LocalWantedFilterButtonVariant.provides(variant),
        LocalWantedFilterButtonActive.provides(isActive)
    ) {
        WantedFilterButton(
            modifier = modifier,
            text = text,
            activeLabel = activeLabel,
            filterButtonDefault = WantedFilterButtonDefaults
                .getDefault()
                .copy(iconColor = colorResource(id = WantedFilterButtonDefaults.getFilterIconColor())),
            isExpanded = isExpend,
            interactionSource = interactionSource,
            onClick = onClick
        )
    }
}

/**
 * WantedFilterButton
 *
 * 기본 설정을 외부에서 주입할 수 있는 커스터마이징 버전의 Filter button입니다.
 * 기본 스타일뿐만 아니라 텍스트 스타일, 색상 등 세부 속성을 설정할 수 있으며,
 * 내부적으로 주어진 FilterButtonDefault 설정값을 기반으로 Filter button을 구성합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFilterButton(
 *     text = "필터",
 *     activeLabel = "3",
 *     filterButtonDefault = customDefault,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text String: FilterButton에 표시될 텍스트입니다.
 * @param modifier Modifier: Modifier를 통해 스타일을 조정할 수 있습니다.
 * @param activeLabel String: FilterButton이 활성화되었을 때 표시할 라벨 텍스트입니다.
 * @param isExpanded Boolean: FilterButton이 확장 상태인지 여부입니다 (화살표 아이콘에 반영됨).
 * @param filterButtonDefault WantedFilterButtonDefault: 외부에서 주입하는 FilterButton의 기본 설정값입니다.
 * @param interactionSource MutableInteractionSource: 사용자 인터랙션 처리를 위한 객체입니다.
 * @param onClick (() -> Unit)?: 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedFilterButton(
    text: String,
    modifier: Modifier = Modifier,
    activeLabel: String = "",
    isExpanded: Boolean = false,
    filterButtonDefault: WantedFilterButtonDefault = WantedFilterButtonDefaults.getDefault(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
) {
    WantedFilterButton(
        modifier = modifier,
        interactionSource = interactionSource,
        size = filterButtonDefault.size,
        variant = filterButtonDefault.variant,
        isActive = filterButtonDefault.isActive,
        isEnable = filterButtonDefault.isEnabled,
        filterButtonDefault = filterButtonDefault,
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
                        style = filterButtonDefault.textStyle.merge(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        },
        rightIcon = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (isExpanded) {
                    painterResource(id = R.drawable.icon_normal_caret_up)
                } else {
                    painterResource(id = R.drawable.icon_normal_caret_down)
                },
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = filterButtonDefault.iconColor)
            )
        },
        onClick = onClick
    )
}

@Composable
private fun WantedFilterButton(
    modifier: Modifier = Modifier,
    size: FilterButtonSize = FilterButtonSize.Small,
    variant: FilterButtonVariant = FilterButtonVariant.Solid,
    isActive: Boolean = false,
    isEnable: Boolean = true,
    filterButtonDefault: WantedFilterButtonDefault = WantedFilterButtonDefaults.getDefault(
        size = size,
        variant = variant,
        isActive = isActive,
        isEnable = isEnable
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    WantedFilterButtonLayout(
        modifier = modifier
            .clip(RoundedCornerShape(getFilterButtonRadius(filterButtonDefault.size)))
            .background(filterButtonDefault.backgroundColor)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(getFilterButtonRadius(filterButtonDefault.size)),
                color = filterButtonDefault.borderColor
            )
            .clickOnce(
                interactionSource = interactionSource,
                indication = if (filterButtonDefault.variant == FilterButtonVariant.Solid) {
                    wantedRippleEffect(
                        color = DesignSystemTheme.colors.labelNormal.copy(
                            OPACITY_12
                        )
                    )
                } else {
                    wantedRippleEffect(
                        color = filterButtonDefault.backgroundColor.copy(OPACITY_12)
                    )
                },
                enabled = filterButtonDefault.isEnabled
            ) {
                onClick?.invoke()
            },
        filterButtonDefault = filterButtonDefault,
        content = content,
        rightIcon = rightIcon
    )
}


@Composable
private fun WantedFilterButtonLayout(
    filterButtonDefault: WantedFilterButtonDefault,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .filterButtonPadding(size = filterButtonDefault.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(getFilterButtonHorizontalArrangement(size = filterButtonDefault.size))
    ) {

        ProvideTextStyle(value = filterButtonDefault.textStyle) {
            Box(
                modifier = Modifier
                    .filterButtonTextPadding(filterButtonDefault.size)
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }

        rightIcon?.let {
            Box(
                modifier = Modifier.filterButtonIconSize(filterButtonDefault.size),
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
private fun FilterButtonPreView() {
    DesignSystemTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DesignSystemTheme.colors.backgroundNormalNormal
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
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        isEnable = false,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        isEnable = false,
                        size = FilterButtonSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        isExpend = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        isEnable = false,
                        isExpend = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        isExpend = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        size = FilterButtonSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Solid,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        variant = FilterButtonVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WantedFilterButton(
                        text = "텍스트",
                        activeLabel = "1",
                        variant = FilterButtonVariant.Solid,
                        isExpend = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        activeLabel = "2",
                        variant = FilterButtonVariant.Outlined,
                        isEnable = false,
                        isActive = true,
                        size = FilterButtonSize.Medium
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        activeLabel = "일",
                        variant = FilterButtonVariant.Solid,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Small
                    )
                    WantedFilterButton(
                        text = "텍스트",
                        activeLabel = "이",
                        variant = FilterButtonVariant.Outlined,
                        isEnable = false,
                        isExpend = true,
                        isActive = true,
                        size = FilterButtonSize.Small
                    )
                }
            }
        }
    }
}