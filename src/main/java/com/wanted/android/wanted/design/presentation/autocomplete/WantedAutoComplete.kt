package com.wanted.android.wanted.design.presentation.autocomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R

@Composable
fun ExposedDropdownMenuBoxScope.WantedAutoComplete(
    modifier: Modifier = Modifier,
    containerColor: Color = colorResource(R.color.background_normal_normal),
    expended: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    autoCompleteContent: @Composable ColumnScope.() -> Unit,
    topDirectInput: @Composable (() -> Unit)? = null,
    bottomDirectInput: @Composable (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    ExposedDropdownMenu(
        modifier = modifier,
        scrollState = scrollState,
        containerColor = containerColor,
        shape = RoundedCornerShape(16.dp),
        expanded = expended,
        onDismissRequest = {
            onExpandedChange(expended)
        }
    ) {
        topDirectInput?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(0, scrollState.value)
                    }
                    .zIndex(1000f)
                    .background(containerColor)

            ) {
                it()
            }
            autoCompleteContent()

            bottomDirectInput?.let {
                it()
            }
        }

    }
}
