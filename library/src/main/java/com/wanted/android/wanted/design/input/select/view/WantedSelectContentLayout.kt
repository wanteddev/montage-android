package com.wanted.android.wanted.design.input.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.input.select.WantedSelectDefaults
import com.wanted.android.wanted.design.input.select.leadingContentSize
import com.wanted.android.wanted.design.input.select.leadingIconSize

// Content 영역의 최소 높이입니다. Container min-height 정책의 기준이 됩니다.
private val CONTENT_MIN_HEIGHT = 24.dp

// Content 영역 좌우 padding 및 요소 사이 gap 입니다.
private val CONTENT_HORIZONTAL_PADDING = 4.dp
private val CONTENT_SPACING = 2.dp

// render=chip 일 때 leading content 영역이 추가로 가지는 우측 padding 입니다.
private val LEADING_CONTENT_CHIP_END_PADDING = 4.dp

// Chevron 아이콘 영역 크기와 내부 padding 입니다.
private val TRAILING_ICON_BOX_SIZE = 24.dp
private val TRAILING_ICON_PADDING = 4.dp

// Select 본체의 가로 배치(leading content · 렌더 요소 · chevron)를 담당합니다.
//
// @param overflow 선택 항목이 여러 줄로 늘어나는 모드인지 여부입니다. true 면 세로 가운데가 아니라
// 상단 기준으로 정렬해, 콘텐츠가 길어져도 leading content 와 chevron 이 첫 줄에 맞춰집니다.
@Composable
internal fun WantedSelectContentLayout(
    size: WantedSelectDefaults.Size,
    render: WantedSelectDefaults.MultiSelectRender,
    modifier: Modifier = Modifier,
    overflow: Boolean = false,
    contents: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    // 컨테이너(modifier)가 최소 높이를 갖고 Row 는 콘텐츠 높이만 차지하도록 Box 로 감싼다.
    // overflow=true 여도 한 줄(기본 높이)일 때는 Row 가 세로 가운데에 놓이고, 높이가 늘어나면
    // Row 가 컨테이너를 채우며 Top 정렬로 leading content 와 chevron 이 첫 줄에 맞춰진다.
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = CONTENT_MIN_HEIGHT)
                .padding(horizontal = CONTENT_HORIZONTAL_PADDING),
            horizontalArrangement = Arrangement.spacedBy(space = CONTENT_SPACING),
            verticalAlignment = if (overflow) Alignment.Top else Alignment.CenterVertically
        ) {

            leadingIcon?.let {
                Box(
                    modifier = Modifier
                        .padding(end = leadingContentEndPadding(render))
                        .size(size.leadingContentSize),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.size(size.leadingIconSize),
                        contentAlignment = Alignment.Center
                    ) {
                        leadingIcon()
                    }
                }
            }

            // overflow=true 는 Row 가 Top 정렬이라, 한 줄 콘텐츠가 chevron 영역(24)보다 낮으면 위로 붙는다.
            // 콘텐츠 영역을 chevron 높이 이상으로 두고 그 안에서 세로 가운데 정렬해 첫 줄을 chevron 과 맞춘다.
            // 여러 줄로 늘어나면 콘텐츠가 이 높이를 넘어서므로 정렬은 영향이 없다.
            Box(
                modifier = Modifier
                    .weight(weight = 1f, fill = false)
                    .defaultMinSize(minHeight = TRAILING_ICON_BOX_SIZE),
                contentAlignment = Alignment.CenterStart
            ) {
                contents()
            }

            Box(
                modifier = Modifier
                    .size(TRAILING_ICON_BOX_SIZE)
                    .padding(TRAILING_ICON_PADDING),
                contentAlignment = Alignment.Center
            ) {
                trailingIcon()
            }
        }
    }
}

// #5 render 방식에 따라 leading content 영역이 차지하는 너비가 달라집니다.
//
// render=chip 일 때만 우측 padding 4dp를 가집니다.
private fun leadingContentEndPadding(
    render: WantedSelectDefaults.MultiSelectRender
) = when (render) {
    WantedSelectDefaults.MultiSelectRender.Chip -> LEADING_CONTENT_CHIP_END_PADDING
    WantedSelectDefaults.MultiSelectRender.Text -> 0.dp
}
