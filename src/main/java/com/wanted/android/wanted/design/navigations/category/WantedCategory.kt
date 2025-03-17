package com.wanted.android.wanted.design.navigations.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.actions.chip.WantedActionChip
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionSize
import com.wanted.android.wanted.design.actions.chip.WantedActionContract.ChipActionVariant
import com.wanted.android.wanted.design.navigations.category.WantedCategoryContract.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedCategory(
    modifier: Modifier,
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    isAlternative: Boolean = false,
    itemList: List<String>,
    selectedList: List<String>,
    gradientColor: Color = colorResource(R.color.background_normal_normal),
    rightIcon: @Composable (() -> Unit)? = null,
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
                WantedActionChip(
                    text = item,
                    variant = if (selectedList.contains(item)) {
                        if (isAlternative) {
                            ChipActionVariant.OUTLINED
                        } else {
                            ChipActionVariant.FILLED
                        }
                    } else {
                        ChipActionVariant.OUTLINED
                    },
                    size = when (size) {
                        Size.Small -> ChipActionSize.XSMALL
                        Size.Medium -> ChipActionSize.SMALL
                        Size.Large -> ChipActionSize.NORMAL
                        Size.XLarge -> ChipActionSize.LARGE
                    },
                    isActive = selectedList.contains(item),
                    onClick = {
                        onClick(item, !selectedList.contains(item))
                    }
                )
            }
        }
    )
}


@Composable
fun WantedCategory(
    modifier: Modifier,
    state: LazyListState = rememberLazyListState(),
    size: Size = Size.Medium,
    horizontalPadding: Boolean = false,
    isVerticalPadding: Boolean = false,
    gradientColor: Color = colorResource(R.color.background_normal_normal),
    content: LazyListScope.() -> Unit,
    rightIcon: @Composable (() -> Unit)? = null
) {
    CompositionLocalProvider(LocalCategoryGradationColor provides gradientColor) {
        WantedCategoryLayout(
            modifier = modifier
                .fillMaxWidth(),
            isLeftGradient = if (horizontalPadding) false else state.canScrollBackward,
            isRightGradient = when {
                horizontalPadding && rightIcon != null -> state.canScrollForward
                horizontalPadding -> false
                else -> state.canScrollForward
            },
            content = {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = if (isVerticalPadding) size.verticalPadding else 0.dp),
                    state = state,
                    contentPadding = PaddingValues(horizontal = if (horizontalPadding) 20.dp else 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size.horizontalSpacing)
                ) {
                    content()
                }
            },
            rightIcon = rightIcon
        )
    }
}


@Composable
private fun WantedCategoryLayout(
    modifier: Modifier = Modifier,
    isLeftGradient: Boolean,
    isRightGradient: Boolean,
    content: @Composable () -> Unit,
    rightIcon: @Composable (() -> Unit)?
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
                            .padding(bottom = 1.dp)
                            .constrainAs(leftGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                start.linkTo(tabRef.start)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        LocalCategoryGradationColor.current,
                                        colorResource(id = android.R.color.transparent)
                                    )
                                )
                            )
                    )
                }

                if (isRightGradient) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .constrainAs(rightGradientRef) {
                                top.linkTo(tabRef.top)
                                bottom.linkTo(tabRef.bottom)
                                end.linkTo(tabRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .width(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colorResource(id = android.R.color.transparent),
                                        LocalCategoryGradationColor.current
                                    )
                                )
                            )
                    )
                }
            }

            rightIcon?.let {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 8.dp)
                ) {
                    rightIcon()
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
                            WantedActionChip(
                                text = item,
                                variant = if (index == 0) {
                                    ChipActionVariant.FILLED
                                } else {
                                    ChipActionVariant.OUTLINED
                                },
                                size = ChipActionSize.SMALL,
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
                    onClick = { _, _ -> }
                )

                WantedCategory(
                    modifier = Modifier,
                    size = Size.Medium,
                    itemList = itemList,
                    isAlternative = true,
                    selectedList = listOf(itemList.first()),
                    onClick = { _, _ -> }
                )
            }
        }
    }
}