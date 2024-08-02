package com.wanted.android.wanted.design.select.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.select.WantedSelectData
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
internal fun WantedMultiSelectContents(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    placeHolder: String = "",
    errorList: List<WantedSelectData>,
    isChip: Boolean,
    enabled: Boolean,
    onDelete: (WantedSelectData) -> Unit
) {
    when {
        valueList.isEmpty() -> {
            WantedMultiSelectPlaceHolder(
                modifier = modifier,
                placeHolder = placeHolder,
                enabled = enabled,
            )
        }

        isChip -> {
            WantedMultiSelectChipList(
                modifier = modifier,
                valueList = valueList,
                errorList = errorList,
                enabled = enabled,
                onDelete = onDelete
            )
        }

        else -> {
            WantedMultiSelectText(
                modifier = modifier,
                valueList = valueList,
                enabled = enabled
            )
        }
    }
}


@Composable
private fun WantedMultiSelectChipList(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    errorList: List<WantedSelectData>,
    enabled: Boolean,
    onDelete: (WantedSelectData) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        valueList.forEach {
            WantedSelectChip(
                text = it.text,
                onClick = { onDelete(it) },
                enable = enabled,
                error = errorList.contains(it),
                leadingIcon = if (it.iconUrl.isNotEmpty()) {
                    {
                        GlideImage(
                            modifier = Modifier.size(11.dp),
                            model = it.iconUrl,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    }
                } else if (it.iconRes != 0) {
                    {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_normal_close_svg),
                            tint = if (it.tint != 0) {
                                colorResource(id = it.tint)
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
    }
}

@Composable
private fun WantedMultiSelectText(
    modifier: Modifier = Modifier,
    valueList: List<WantedSelectData>,
    enabled: Boolean
) {
    Text(
        modifier = modifier,
        text = valueList.joinToString(separator = ", ") { value -> value.text },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = WantedTextStyle(
            colorRes = if (enabled) {
                R.color.label_normal
            } else {
                R.color.label_disable
            },
            style = DesignSystemTheme.typography.body1Regular
        )
    )
}
