package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.wanted.design.input.select.LocalWantedSelectBackground
import com.wanted.android.wanted.design.input.select.WantedSelectData
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
import com.wanted.android.wanted.design.input.select.chipSpacing
import com.wanted.android.wanted.design.input.select.inputTextStyle
import com.wanted.android.wanted.design.theme.DesignSystemTheme

@Composable
internal fun WantedMultiSelectContents(
    valueList: List<WantedSelectData>,
    errorList: List<WantedSelectData>,
    render: WantedSelectDefaults.MultiSelectRender,
    enabled: Boolean,
    overflow: Boolean,
    onDelete: (WantedSelectData) -> Unit,
    modifier: Modifier = Modifier,
    size: WantedSelectDefaults.Size = WantedSelectDefaults.Size.Large,
    placeHolder: String = ""
) {
    when {
        valueList.isEmpty() -> {
            WantedSelectPlaceHolder(
                modifier = modifier,
                placeHolder = placeHolder,
                enabled = enabled,
                size = size
            )
        }

        render == WantedSelectDefaults.MultiSelectRender.Chip -> {
            WantedMultiSelectChipList(
                modifier = modifier,
                valueList = valueList,
                overflow = overflow,
                errorList = errorList,
                enabled = enabled,
                size = size,
                onDelete = onDelete
            )
        }

        else -> {
            WantedMultiSelectText(
                modifier = modifier,
                valueList = valueList,
                overflow = overflow,
                enabled = enabled,
                size = size
            )
        }
    }
}

/**
 * fun WantedMultiSelectChipListOverflow(...)
 *
 * 다중 선택 값을 칩으로 나열하고, 한 줄에 다 들어가지 않으면 다음 줄로 넘겨 모두 표시하는 레이아웃입니다.
 *
 * @param modifier Modifier: 레이아웃에 적용할 Modifier입니다.
 * @param itemCount Int: 표시할 항목 수입니다. 0 부터 itemCount - 1 까지 onItemIndex 를 호출합니다.
 * @param spacing Dp: 항목 사이의 가로·세로 간격입니다. 기본값은 4dp 입니다.
 * @param onItemIndex @Composable (Int) -> Unit: index 번째 항목(보통 칩)을 그리는 슬롯입니다.
 */
@Composable
fun WantedMultiSelectChipListOverflow(
    modifier: Modifier = Modifier,
    itemCount: Int,
    spacing: Dp = 4.dp,
    onItemIndex: @Composable (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        for (index in 0..<itemCount) {
            onItemIndex(index)
        }
    }
}


@Composable
private fun WantedMultiSelectChipList(
    modifier: Modifier = Modifier,
    itemCount: Int,
    spacing: Dp = 4.dp,
    onItemIndex: @Composable (index: Int) -> Unit,
) {

    val lazyListState = rememberLazyListState()
    var canScrollForward by remember { mutableStateOf(false) }
    var canScrollBackward by remember { mutableStateOf(false) }


    LaunchedEffect(lazyListState.canScrollForward) {
        canScrollForward = lazyListState.canScrollForward
    }

    LaunchedEffect(lazyListState.canScrollBackward) {
        canScrollBackward = lazyListState.canScrollBackward
    }

    ConstraintLayout {
        val (startGradient, endGradient, row) = createRefs()
        LazyRow(
            modifier = modifier
                .constrainAs(row) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    height = Dimension.fillToConstraints // Match height of text
                }
                .fillMaxWidth(),
            state = lazyListState,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = spacing)
        ) {
            items(itemCount) { index ->
                onItemIndex(index)
            }
        }

        if (canScrollBackward) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .constrainAs(startGradient) {
                        start.linkTo(parent.start)
                        top.linkTo(row.top)
                        bottom.linkTo(row.bottom)
                        height = Dimension.fillToConstraints // Match height of text
                    }
                    .background(brush = Brush.horizontalGradient(colors = getGradientColorList().reversed())),
            )
        }

        if (canScrollForward) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .constrainAs(endGradient) {
                        end.linkTo(parent.end)
                        top.linkTo(row.top)
                        bottom.linkTo(row.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .background(brush = Brush.horizontalGradient(colors = getGradientColorList())),
            )
        }
    }
}

@Composable
private fun getGradientColorList() = listOf(
    LocalWantedSelectBackground.current.copy(0.0f),
    LocalWantedSelectBackground.current.copy(0.14f),
    LocalWantedSelectBackground.current.copy(0.27f),
    LocalWantedSelectBackground.current.copy(0.38f),
    LocalWantedSelectBackground.current.copy(0.48f),
    LocalWantedSelectBackground.current.copy(0.57f),
    LocalWantedSelectBackground.current.copy(0.65f),
    LocalWantedSelectBackground.current.copy(0.71f),
    LocalWantedSelectBackground.current.copy(0.77f),
    LocalWantedSelectBackground.current.copy(0.82f),
    LocalWantedSelectBackground.current.copy(0.86f),
    LocalWantedSelectBackground.current.copy(0.90f),
    LocalWantedSelectBackground.current.copy(0.93f),
    LocalWantedSelectBackground.current.copy(0.96f),
    LocalWantedSelectBackground.current.copy(0.98f),
    LocalWantedSelectBackground.current
)

@Composable
private fun WantedMultiSelectChipList(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    errorList: List<WantedSelectData>,
    overflow: Boolean,
    enabled: Boolean,
    size: WantedSelectDefaults.Size,
    onDelete: (WantedSelectData) -> Unit,
) {
    val content: @Composable (index: Int) -> Unit = { index ->
        WantedSelectChip(
            text = valueList[index].text,
            onClick = { onDelete(valueList[index]) },
            enable = enabled,
            error = errorList.contains(valueList[index]),
            leadingContent = if (valueList[index].iconUrl.isNotEmpty()) {
                {
                    GlideImage(
                        modifier = Modifier.size(11.dp),
                        model = valueList[index].iconUrl,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                }
            } else if (valueList[index].iconRes != 0) {
                {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = valueList[index].iconRes),
                        tint = if (valueList[index].tint != 0) {
                            colorResource(id = valueList[index].tint)
                        } else {
                            LocalContentColor.current
                        },
                        contentDescription = ""
                    )
                }
            } else {
                null
            }
        )
    }

    if (overflow) {
        WantedMultiSelectChipListOverflow(
            modifier = modifier,
            itemCount = valueList.size,
            spacing = size.chipSpacing,
            onItemIndex = content
        )
    } else {
        WantedMultiSelectChipList(
            modifier = modifier,
            itemCount = valueList.size,
            spacing = size.chipSpacing,
            onItemIndex = content
        )
    }
}

@Composable
private fun WantedMultiSelectText(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    overflow: Boolean,
    enabled: Boolean,
    size: WantedSelectDefaults.Size
) {
    Text(
        modifier = modifier.padding(horizontal = 4.dp),
        text = valueList.joinToString(separator = ", ") { value -> value.text },
        maxLines = if (overflow) Int.MAX_VALUE else 1,
        overflow = TextOverflow.Ellipsis,
        style = size.inputTextStyle,
        color = if (enabled) {
            DesignSystemTheme.colors.foregroundNeutralPrimary
        } else {
            DesignSystemTheme.colors.foregroundNeutralTertiary
        }
    )
}
