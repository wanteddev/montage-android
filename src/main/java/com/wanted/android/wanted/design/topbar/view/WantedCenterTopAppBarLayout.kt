package com.wanted.android.wanted.design.topbar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun WantedCenterTopAppBarLayout(
    modifier: Modifier,
    title: (@Composable () -> Unit)? = null,
    titleVerticalArrangement: Arrangement.Vertical = Arrangement.Center,
    titleHorizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    titleBottomPadding: Int = 0,
    hideTitleSemantics: Boolean = false,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    val height = remember { mutableFloatStateOf(56f) }
    val localDensity = LocalDensity.current

    Layout(
        content = {
            Box(
                Modifier
                    .layoutId("navigationIcon")
                    .padding(start = 8.dp)
            ) {
                Row {
                    navigationIcon?.let {
                        Box(
                            modifier = Modifier
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            navigationIcon()
                        }

                        Spacer(modifier = Modifier.size(12.dp))
                    } ?: kotlin.run {
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }

            Box(
                Modifier
                    .layoutId("title")
                    .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier)
                    .onGloballyPositioned { coordinates ->
                        height.floatValue = with(localDensity) {
                            coordinates.size.height.toFloat() + 28.dp.toPx()
                        }
                    }
            ) {
                title?.let {
                    ProvideTextStyle(
                        value = WantedTextStyle(
                            colorRes = R.color.label_strong,
                            style = DesignSystemTheme.typography.heading2Bold
                        )
                    ) {
                        title()
                    }
                }
            }

            Box(
                Modifier
                    .layoutId("actionIcons")
                    .padding(end = 8.dp)
            ) {
                actions?.let {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        actions()
                    }
                }
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val navigationIconPlaceable =
            measurables.fastFirst { it.layoutId == "navigationIcon" }
                .measure(constraints.copy(minWidth = 0))
        val actionIconsPlaceable =
            measurables.fastFirst { it.layoutId == "actionIcons" }
                .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.maxWidth
        } else {
            (constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width)
                .coerceAtLeast(0)
        }
        val titlePlaceable =
            measurables.fastFirst { it.layoutId == "title" }
                .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        // Locate the title's baseline.
        val titleBaseline =
            if (titlePlaceable[LastBaseline] != AlignmentLine.Unspecified) {
                titlePlaceable[LastBaseline]
            } else {
                0
            }

        val layoutHeight = height.floatValue.roundToInt()

        layout(constraints.maxWidth, layoutHeight) {
            // Navigation icon
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            // Title
            titlePlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> {
                        var baseX = (constraints.maxWidth - titlePlaceable.width) / 2
                        if (baseX < navigationIconPlaceable.width) {
                            // May happen if the navigation is wider than the actions and the
                            // title is long. In this case, prioritize showing more of the title by
                            // offsetting it to the right.
                            baseX += (navigationIconPlaceable.width - baseX)
                        } else if (baseX + titlePlaceable.width >
                            constraints.maxWidth - actionIconsPlaceable.width
                        ) {
                            // May happen if the actions are wider than the navigation and the title
                            // is long. In this case, offset to the left.
                            baseX += ((constraints.maxWidth - actionIconsPlaceable.width) -
                                (baseX + titlePlaceable.width))
                        }
                        baseX
                    }

                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width - actionIconsPlaceable.width
                    // Arrangement.Start.
                    // An TopAppBarTitleInset will make sure the title is offset in case the
                    // navigation icon is missing.
                    else -> max(0
                        /**TopAppBarTitleInset.roundToPx()**/
                        , navigationIconPlaceable.width)
                },
                y = when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    // Apply bottom padding from the title's baseline only when the Arrangement is
                    // "Bottom".
                    Arrangement.Bottom ->
                        if (titleBottomPadding == 0) layoutHeight - titlePlaceable.height
                        else layoutHeight - titlePlaceable.height - max(
                            0,
                            titleBottomPadding - titlePlaceable.height + titleBaseline
                        )
                    // Arrangement.Top
                    else -> 0
                }
            )

            // Action icons
            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )
        }
    }
}
