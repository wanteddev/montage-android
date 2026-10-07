package com.wanted.android.wanted.design.input.formcontrol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// 라벨 영역의 최대 높이입니다.
//
// LabelPlacement.Leading일 때 라벨은 Input 높이만큼 늘어나되 이 값을 넘지 않으며,
// `min(inputHeight, LabelMaxHeight)` 영역 안에서 수직 중앙정렬됩니다.
// (TextArea처럼 Input이 길어져도 라벨은 상단 영역 기준으로 정렬)
private val LabelMaxHeight = 48.dp

/** 라벨과 입력 영역 사이 가로 간격입니다. */
private val LabelHorizontalGap = 16.dp

/** Input과 Footer 사이 세로 간격입니다. */
private val ContentVerticalGap = 8.dp

// LabelPlacement.Leading에서 라벨이 차지할 수 있는 최대 가로 폭 비율입니다.
//
// 긴 라벨 하나가 입력 영역을 0폭으로 밀어내지 않도록, 라벨 폭을 부모 가로 폭의 이 비율로 제한합니다.
// 라벨이 이 폭을 넘으면 라벨 텍스트가 ellipsis 처리되고, 나머지 폭은 입력/푸터가 확보합니다.
private const val LEADING_LABEL_MAX_WIDTH_RATIO = 0.5f

// WantedFormControl의 저수준 레이아웃 컴포넌트입니다.
//
// 라벨, 입력, 푸터 슬롯의 배치(Top=Column / Leading=Row 배치)와 간격만을 책임집니다.
// 실제 컨텐츠 구성(라벨 텍스트, 메시지 등)은 상위 WantedFormControl에서 처리합니다.
//
// @param input (@Composable () -> Unit): 필수 입력 컨트롤 슬롯입니다.
// @param modifier Modifier: Modifier를 설정합니다.
// @param label (@Composable () -> Unit)?: 라벨 슬롯입니다. null이면 렌더링하지 않습니다.
// @param footer (@Composable () -> Unit)?: 메시지/글자수 슬롯입니다. null이면 렌더링하지 않습니다.
// @param labelPlacement WantedFormControlDefaults.LabelPlacement: 라벨 배치 방향을 지정합니다. (Top 또는 Leading)
@Composable
internal fun WantedFormControlLayout(
    input: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    labelPlacement: WantedFormControlDefaults.LabelPlacement = WantedFormControlDefaults.LabelPlacement.Top,
) {
    when (labelPlacement) {
        WantedFormControlDefaults.LabelPlacement.Top -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(ContentVerticalGap)
            ) {
                label?.invoke()
                input()
                footer?.invoke()
            }
        }

        WantedFormControlDefaults.LabelPlacement.Leading -> {
            LeadingPlacementLayout(
                modifier = modifier,
                label = label,
                input = input,
                footer = footer,
            )
        }
    }
}

// LabelPlacement.Leading 전용 레이아웃입니다.
//
// 라벨을 좌측에 두고, 남은 가로 폭을 Input/Footer가 차지합니다.
// 라벨은 `min(inputHeight, [LabelMaxHeight])` 영역(Input 상단 기준) 안에서 수직 중앙정렬되어,
// 1줄 입력(TextField)과 여러 줄 입력(TextArea)을 모두 동일한 규칙으로 대응합니다.
@Composable
private fun LeadingPlacementLayout(
    input: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
) {
    Layout(
        modifier = modifier,
        content = {
            label?.invoke()
            input()
            footer?.invoke()
        },
    ) { measurables, constraints ->
        val horizontalGapPx = LabelHorizontalGap.roundToPx()
        val verticalGapPx = ContentVerticalGap.roundToPx()
        val labelBandMaxPx = LabelMaxHeight.roundToPx()

        // 긴 라벨이 입력 영역을 잠식하지 않도록 라벨 최대 폭을 제한한다. (초과 시 라벨은 ellipsis 처리)
        val labelMaxWidth = if (constraints.maxWidth == Constraints.Infinity) {
            Constraints.Infinity
        } else {
            (constraints.maxWidth * LEADING_LABEL_MAX_WIDTH_RATIO).roundToInt()
        }

        var measurableIndex = 0
        val labelPlaceable = if (label != null) {
            measurables[measurableIndex++].measure(
                constraints.copy(minWidth = 0, maxWidth = labelMaxWidth, minHeight = 0)
            )
        } else {
            null
        }

        val labelWidth = labelPlaceable?.width ?: 0
        val labelGap = if (labelPlaceable != null) horizontalGapPx else 0
        val inputX = labelWidth + labelGap
        // 가로 폭이 unbounded(Constraints.Infinity)면 라벨 폭을 뺀 산술이 거대한 양수가 되어
        // 하위 측정에 위험하므로 그대로 free 측정하고, bounded면 남은 폭을 채우도록 고정한다.
        val inputConstraints = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.copy(minWidth = 0, minHeight = 0)
        } else {
            val inputWidth = (constraints.maxWidth - inputX).coerceAtLeast(0)
            constraints.copy(minWidth = inputWidth, maxWidth = inputWidth, minHeight = 0)
        }

        val inputPlaceable = measurables[measurableIndex++].measure(inputConstraints)
        val footerPlaceable = if (footer != null) {
            measurables[measurableIndex].measure(inputConstraints)
        } else {
            null
        }

        // 라벨 중앙정렬 기준 영역: Input 상단 기준 min(inputHeight, LabelMaxHeight)
        val labelBand = minOf(inputPlaceable.height, labelBandMaxPx)
        val labelY = labelPlaceable
            ?.let { ((labelBand - it.height) / 2).coerceAtLeast(0) }
            ?: 0

        val footerY = inputPlaceable.height + if (footerPlaceable != null) verticalGapPx else 0
        val contentBottom = footerY + (footerPlaceable?.height ?: 0)
        val labelBottom = labelY + (labelPlaceable?.height ?: 0)
        val layoutHeight = maxOf(contentBottom, labelBottom)

        // unbounded면 maxWidth(=Infinity)를 그대로 반환할 수 없으므로 실측 컨텐츠 폭을 사용한다.
        val layoutWidth = if (constraints.maxWidth == Constraints.Infinity) {
            inputX + maxOf(inputPlaceable.width, footerPlaceable?.width ?: 0)
        } else {
            constraints.maxWidth
        }

        layout(constraints.constrainWidth(layoutWidth), constraints.constrainHeight(layoutHeight)) {
            labelPlaceable?.place(x = 0, y = labelY)
            inputPlaceable.place(x = inputX, y = 0)
            footerPlaceable?.place(x = inputX, y = footerY)
        }
    }
}
