package com.wanted.android.wanted.design.accordion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.accordion.WantedAccordionContract.HeaderVerticalPadding
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedAccordionHeader(
    modifier: Modifier = Modifier,
    headerVerticalPadding: HeaderVerticalPadding,
    title: String,
    style: TextStyle,
    fillWidth: Boolean,
    trail: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val localDensity = LocalDensity.current
    var padding by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = if (fillWidth) 20.dp else 0.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        WantedTouchArea(
            modifier = Modifier.fillMaxWidth(),
            horizontalPadding = if (fillWidth) 20.dp else 12.dp,
            shape = RoundedCornerShape(0.dp),
            content = {
                Text(
                    modifier = Modifier
                        .padding(end = padding)
                        .padding(vertical = headerVerticalPadding.value)
                        .fillMaxSize(),
                    text = title,
                    style = style
                )
            },
            onClick = onClick
        )

        /**
         * _SMY
         *  - Row 에 넣으면 touch event 가 씹힌다.
         *  - 원인 : WantedTouchArea
         */
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .wrapContentSize()
                .onGloballyPositioned { coordinates ->
                    padding = with(localDensity) { coordinates.size.width.toDp() + 8.dp }
                }
        ) {
            trail()
        }
    }
}

@DevicePreviews
@Composable
private fun AccordionHeaderPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedAccordionHeader(
                    modifier = Modifier
                        .fillMaxWidth(),
                    headerVerticalPadding = HeaderVerticalPadding.Padding12,
                    title = "제목",
                    style = WantedTextStyle(
                        colorRes = R.color.label_normal,
                        style = DesignSystemTheme.typography.body2Bold
                    ),
                    fillWidth = false,
                    trail = {
                        WantedAccordionTrailArrowIcon(
                            isExpanded = true,
                            tint = colorResource(R.color.label_normal),
                            onClick = { }
                        )
                    },
                    onClick = {
                    }
                )
            }

        }
    }
}