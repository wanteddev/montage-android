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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.input.select.LocalWantedSelectBackground
import com.wanted.android.wanted.design.input.select.WantedSelectData
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
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
    placeHolder: String = ""
) {
    when {
        valueList.isEmpty() -> {
            WantedSelectPlaceHolder(
                modifier = modifier,
                placeHolder = placeHolder,
                enabled = enabled,
            )
        }

        render == WantedSelectDefaults.MultiSelectRender.Chip -> {
            WantedMultiSelectChipList(
                modifier = modifier,
                valueList = valueList,
                overflow = overflow,
                errorList = errorList,
                enabled = enabled,
                onDelete = onDelete
            )
        }

        else -> {
            WantedMultiSelectText(
                modifier = modifier,
                valueList = valueList,
                overflow = overflow,
                enabled = enabled
            )
        }
    }
}

@Composable
fun WantedMultiSelectChipListOverflow(
    modifier: Modifier = Modifier,
    itemCount: Int,
    onItemIndex: @Composable (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
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
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
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
    onDelete: (WantedSelectData) -> Unit,
) {
    val content: @Composable (index: Int) -> Unit = { index ->
        WantedSelectChip(
            text = valueList[index].text,
            onClick = { onDelete(valueList[index]) },
            enable = enabled,
            error = errorList.contains(valueList[index]),
            leadingIcon = if (valueList[index].iconUrl.isNotEmpty()) {
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
                        painter = painterResource(id = R.drawable.icon_normal_close),
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
            onItemIndex = content
        )
    } else {
        WantedMultiSelectChipList(
            modifier = modifier,
            itemCount = valueList.size,
            onItemIndex = content
        )
    }
}

@Composable
private fun WantedMultiSelectText(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    overflow: Boolean,
    enabled: Boolean
) {
    Text(
        modifier = modifier.padding(horizontal = 4.dp),
        text = valueList.joinToString(separator = ", ") { value -> value.text },
        maxLines = if (overflow) Int.MAX_VALUE else 1,
        overflow = TextOverflow.Ellipsis,
        style = DesignSystemTheme.typography.body1Regular,
        color = if (enabled) {
            DesignSystemTheme.colors.labelNormal
        } else {
            DesignSystemTheme.colors.labelAlternative
        }
    )
}
