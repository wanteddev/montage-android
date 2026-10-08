package com.wanted.android.wanted.design.navigations.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Size
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Variant
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedCategory
 *
 * 선택 가능한 Chip 목록을 표시하는 컴포넌트입니다.
 *
 * 문자열 리스트를 기반으로 Chip을 구성하며, 선택 상태를 관리할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * var selectedList by remember { mutableStateOf(listOf("태그1")) }
 *
 * WantedCategory(
 *     itemList = listOf("태그1", "태그2", "태그3"),
 *     selectedList = selectedList,
 *     onClick = { item, isSelected ->
 *         selectedList = if (isSelected) {
 *             selectedList + item
 *         } else {
 *             selectedList - item
 *         }
 *     }
 * )
 * ```
 *
 * @param itemList List<String>: 표시할 항목 문자열 리스트입니다.
 * @param selectedList List<String>: 선택된 항목 리스트입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param disableItemList List<String>: 비활성화할 항목 리스트입니다.
 * @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
 * @param size Size: 카테고리 항목의 크기입니다.
 * @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다.
 * @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다.
 * @param variant Variant: 선택 항목 스타일입니다. Normal 이면 채워진 배경으로, Alternative 면 primary 테두리·옅은 배경으로 표시합니다. 선택 항목 색상은 Normal 에서만 지정할 수 있습니다.
 * @param gradientColor Color: 좌우 그라디언트 배경 색상입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 우측에 표시할 아이콘 슬롯입니다.
 * @param onClick (String, Boolean) -> Unit: 항목 클릭 시 호출되는 콜백입니다. 선택된 항목과 선택 여부를 전달합니다.
 */
@Composable
fun WantedCategory(
    itemList: List<String>,
    selectedList: List<String>,
    modifier: Modifier = Modifier,
    disableItemList: List<String> = emptyList(),
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    variant: Variant = Variant.Normal(),
    gradientColor: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    onClick: (item: String, isSelected: Boolean) -> Unit
) {
    WantedCategory(
        modifier = modifier,
        state = state,
        size = size,
        horizontalPadding = horizontalPadding,
        isVerticalPadding = isVerticalPadding,
        gradientColor = gradientColor,
        rightIcon = rightIcon,
        content = {
            itemsIndexed(itemList) { index, item ->
                WantedChip(
                    text = item,
                    chipDefault = WantedCategoryDefaults.getChipDefault(
                        size = size,
                        variant = variant,
                        isActive = selectedList.contains(item),
                        isEnable = !disableItemList.contains(item)
                    ),
                    onClick = {
                        onClick(item, !selectedList.contains(item))
                    }
                )
            }
        }
    )
}

/**
 * WantedCategory
 *
 * 사용자 정의 콘텐츠로 구성할 수 있는 Category 컴포넌트입니다.
 *
 * LazyListScope를 통해 항목을 직접 구성할 수 있으며, 그라디언트 효과와 우측 아이콘을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCategory {
 *     items(tagList) { tag ->
 *         WantedActionChip(
 *             text = tag,
 *             onClick = { /* 처리 */ }
 *         )
 *     }
 * }
 * ```
 *
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
 * @param size Size: 액션칩의 크기 및 여백 설정입니다.
 * @param horizontalPadding Boolean: 좌우 패딩 적용 여부입니다.
 * @param isVerticalPadding Boolean: 상하 패딩 적용 여부입니다.
 * @param gradientColor Color: 좌우 그라디언트 색상입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param content LazyListScope.() -> Unit: 내부 아이템을 구성하는 블록입니다.
 */
@Composable
fun WantedCategory(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    gradientColor: Color = DesignSystemTheme.colors.backgroundNeutralPrimary,
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    WantedCategory(
        modifier = modifier,
        state = state,
        categoryDefault = WantedCategoryDefaults.getDefault(
            size = size,
            horizontalPadding = horizontalPadding,
            isVerticalPadding = isVerticalPadding,
            gradientColor = gradientColor
        ),
        rightIcon = rightIcon,
        content = content
    )
}

/**
 * WantedCategory
 *
 * [WantedCategoryDefault] 로 표시 스펙(크기·여백·그라디언트 색상)을 지정하는 Category 컴포넌트입니다.
 *
 * LazyListScope를 통해 항목을 직접 구성할 수 있으며, 그라디언트 효과와 우측 아이콘을 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCategory(
 *     categoryDefault = WantedCategoryDefaults.getDefault(
 *         size = WantedCategoryDefaults.Size.Large,
 *         gradientColor = DesignSystemTheme.colors.surfaceElevatedPrimary
 *     )
 * ) {
 *     items(tagList) { tag ->
 *         WantedChip(
 *             text = tag,
 *             onClick = { /* 처리 */ }
 *         )
 *     }
 * }
 * ```
 *
 * @param categoryDefault WantedCategoryDefault: 크기·여백·그라디언트 색상을 담는 표시 스펙입니다. [WantedCategoryDefaults.getDefault] 로 생성합니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param state LazyListState: LazyRow의 스크롤 상태를 관리하는 객체입니다.
 * @param rightIcon (@Composable (Dp) -> Unit)?: 우측 아이콘 슬롯입니다.
 * @param content LazyListScope.() -> Unit: 내부 아이템을 구성하는 블록입니다.
 */
@Composable
fun WantedCategory(
    categoryDefault: WantedCategoryDefault,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    rightIcon: @Composable ((Dp) -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    CompositionLocalProvider(LocalCategoryGradationColor provides categoryDefault.gradientColor) {
        WantedCategoryLayout(
            modifier = modifier
                .fillMaxWidth(),
            size = categoryDefault.size,
            isLeftGradient = if (categoryDefault.horizontalPadding) false else state.canScrollBackward,
            isRightGradient = when {
                categoryDefault.horizontalPadding && rightIcon != null -> state.canScrollForward
                categoryDefault.horizontalPadding -> false
                else -> state.canScrollForward
            },
            content = {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = if (categoryDefault.isVerticalPadding) {
                                categoryDefault.size.verticalPadding
                            } else {
                                0.dp
                            }
                        ),
                    state = state,
                    contentPadding = PaddingValues(
                        horizontal = if (categoryDefault.horizontalPadding) {
                            WantedCategoryDefaults.contentHorizontalPadding
                        } else {
                            0.dp
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(categoryDefault.size.horizontalSpacing)
                ) {
                    content()
                }
            },
            rightIcon = rightIcon
        )
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WantedCategoryLayout(
    isLeftGradient: Boolean,
    isRightGradient: Boolean,
    rightIcon: @Composable ((Dp) -> Unit)?,
    modifier: Modifier = Modifier,
    size: Size = Size.Medium,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConstraintLayout(
                modifier = Modifier.weight(1f)
            ) {
                val (tabRef, leftGradientRef, rightGradientRef) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(tabRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    content()
                }

                if (isLeftGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = WantedCategoryDefaults.gradientBottomPadding)
                            .constrainAs(leftGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                start.linkTo(tabRef.start)
                                height = Dimension.fillToConstraints
                            }
                            .width(WantedCategoryDefaults.gradientWidth)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        LocalCategoryGradationColor.current,
                                        DesignSystemTheme.colors.transparent
                                    )
                                )
                            )
                    )
                }

                if (isRightGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = WantedCategoryDefaults.gradientBottomPadding)
                            .constrainAs(rightGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                end.linkTo(tabRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .width(WantedCategoryDefaults.gradientWidth)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        DesignSystemTheme.colors.transparent,
                                        LocalCategoryGradationColor.current
                                    )
                                )
                            )
                    )
                }
            }

            rightIcon?.let {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(
                            start = WantedCategoryDefaults.rightIconStartPadding,
                            end = WantedCategoryDefaults.rightIconEndPadding
                        )
                        .size(size.rightIconSize)
                ) {
                    rightIcon(maxHeight)
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedCategoryPreview() {
    DesignSystemTheme {
        val itemList = mutableListOf<String>()
        repeat(20) {
            itemList.add("텍스트$it")
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    content = {
                        itemsIndexed(itemList) { index, item ->
                            WantedChip(
                                text = item,
                                variant = if (index == 0) {
                                    ChipVariant.Solid
                                } else {
                                    ChipVariant.Outlined
                                },
                                size = ChipSize.Small,
                                isActive = index == 0
                            )
                        }
                    }
                )

                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    itemList = itemList,
                    selectedList = listOf(itemList.first()),
                    disableItemList = listOf(itemList[2]),
                    onClick = { _, _ -> }
                )

                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    itemList = itemList,
                    variant = Variant.Alternative,
                    selectedList = listOf(itemList.first()),
                    disableItemList = listOf(itemList.first()),
                    onClick = { _, _ -> }
                )
            }
        }
    }
}