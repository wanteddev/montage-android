package com.wanted.android.wanted.design.navigations.topbar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlin.math.roundToInt

@Composable
internal fun WantedCenterTopAppBarLayout(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    val height = remember { mutableFloatStateOf(56f) }
    val localDensity = LocalDensity.current

    Layout(
        modifier = modifier,
        content = {
            Box(
                Modifier
                    .layoutId("navigationIcon")
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                navigationIcon?.let {
                    Row {
                        navigationIcon()
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }

            Box(
                Modifier
                    .layoutId("title")
                    .onGloballyPositioned { coordinates ->
                        height.floatValue = with(localDensity) {
                            coordinates.size.height.toFloat() + 28.dp.toPx()
                        }
                    }
            ) {
                title?.let {
                    ProvideTextStyle(
                        value = DesignSystemTheme.typography.headline2Bold.copy(
                            color = DesignSystemTheme.colors.labelStrong
                        )
                    ) {
                        title()
                    }
                }
            }

            Box(
                Modifier
                    .layoutId("actionIcons")
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                actions?.let {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                    ) {
                        actions()
                    }
                }
            }
        }
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

        val layoutHeight = height.floatValue.roundToInt()

        layout(constraints.maxWidth, layoutHeight) {
            // Navigation icon
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

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

            // Title
            titlePlaceable.placeRelative(
                x = baseX,
                y = (layoutHeight - titlePlaceable.height) / 2
            )

            // Action icons
            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )
        }
    }
}
