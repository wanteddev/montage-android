package com.wanted.android.wanted.design.presentation.autocomplete

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun ExposedDropdownMenuBoxScope.WantedAutoComplete(
    modifier: Modifier = Modifier,
    containerColor: Color = colorResource(R.color.background_normal_normal),
    expended: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    sectionTitleHorizontalPadding: Dp = 20.dp,
    sectionCount: Int,
    sectionTitle: ((section: Int) -> String)? = null,
    sectionItemCount: (section: Int) -> Int,
    sectionItem: @Composable (section: Int, index: Int) -> Unit,
    topDirectInput: @Composable (() -> Unit)? = null,
    bottomDirectInput: @Composable (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    val topDirectInputSize = remember { mutableIntStateOf(0) }
    val currentSection = remember { mutableIntStateOf(-1) }
    val sectionOffsets = remember { mutableStateMapOf<Int, Int>() }
    val density = LocalDensity.current
    val title = remember { mutableStateOf("") }

    LaunchedEffect(currentSection.intValue) {
        title.value = if (currentSection.intValue == -1) {
            ""
        } else {
            sectionTitle?.invoke(currentSection.intValue) ?: ""
        }
    }

    LaunchedEffect(scrollState.value) {
        currentSection.intValue = sectionOffsets.filter {
            it.value <= (scrollState.value + with(density) {
                TITLE_VERTICAL_PADDING.dp.toPx()
                +if (title.value.isEmpty()) {
                    0.dp.toPx()
                } else {
                    TITLE_VERTICAL_PADDING.dp.toPx() + TITLE_VERTICAL_PADDING.dp.toPx()
                }
            })
        }.maxOfOrNull { it.key } ?: -1
    }

    ExposedDropdownMenu(
        modifier = modifier.padding(horizontal = 8.dp),
        scrollState = scrollState,
        containerColor = containerColor,
        shape = RoundedCornerShape(16.dp),
        expanded = expended,
        shadowElevation = 1.dp,
        onDismissRequest = {
            onExpandedChange(expended)
        }
    ) {
        topDirectInput?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        topDirectInputSize.intValue = it.size.height
                    }
                    .zIndex(1000f)
                    .background(containerColor)

            ) {
                it()
                Spacer(modifier = Modifier.size(4.dp))
            }
        }

        if (title.value.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(0, scrollState.value - topDirectInputSize.intValue)
                    }
                    .zIndex(1001f)
                    .background(containerColor)
                    .padding(horizontal = sectionTitleHorizontalPadding)
                    .padding(horizontal = 1.dp)
                    .padding(vertical = 4.dp),
                text = title.value,
                style = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.caption1Bold
                )
            )

            Spacer(modifier = Modifier.size(4.dp))

        }

        repeat(sectionCount) { section ->
            if (section != 0 || title.value.isEmpty()) {
                sectionTitle?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                Log.d(
                                    "_SMY",
                                    "onGloballyPositioned: ${it.positionInParent().y.toInt()}, ${it.positionInRoot().y.toInt()}, ${scrollState.value} "
                                )
                                sectionOffsets[section] = it.positionInParent().y.toInt()
                            }
                            .padding(horizontal = sectionTitleHorizontalPadding)
                            .padding(horizontal = 1.dp)
                            .padding(vertical = 4.dp),
                        text = sectionTitle(section),
                        style = WantedTextStyle(
                            colorRes = R.color.label_alternative,
                            style = DesignSystemTheme.typography.caption1Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))
            }

            val itemCount = sectionItemCount(section)
            repeat(itemCount) { index ->
                sectionItem(section, index)

                if (itemCount != index - 1) {
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }

        bottomDirectInput?.let {
            it()
        }
    }
}

private const val TITLE_VERTICAL_PADDING = 4
