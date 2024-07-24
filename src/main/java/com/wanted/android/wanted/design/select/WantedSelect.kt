package com.wanted.android.wanted.design.select

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedCommonIcon
import com.wanted.android.wanted.design.base.WantedComponentTitle
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    error: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    onDelete: (String) -> Unit,
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectLayout(
        modifier = modifier,
        title = title?.let {
            {
                WantedComponentTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    isRequiredBadge = isRequiredBadge
                )
            }
        },
        select = {
            SelectLayout(
                modifier = Modifier
                    .border(
                        shape = RoundedCornerShape(12.dp),
                        color = when {
                            error || focused -> {
                                colorResource(id = R.color.background_normal_normal)
                                    .copy(alpha = OPACITY_43)
                            }

                            else -> colorResource(R.color.transparent)
                        },
                        width = if (focused) 2.dp else 1.dp
                    )
                    .border(
                        shape = RoundedCornerShape(12.dp),
                        color = colorResource(
                            id = when {
                                !enabled -> R.color.line_normal_alternative
                                error -> R.color.status_negative
                                focused -> R.color.primary_normal
                                else -> R.color.line_normal_neutral
                            }
                        ),
                        width = if (focused) 2.dp else 1.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        colorResource(
                            id = if (enabled) {
                                R.color.background_normal_normal
                            } else {
                                R.color.line_normal_alternative
                            }
                        )
                    )
                    .clickOnceForDesignSystem(enabled) {
                        onClick()
                    }
                    .padding(12.dp),
                leadingIcon = leadingIcon,
                text = {
                    if (value.isEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = placeHolder,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = WantedTextStyle(
                                colorRes = if (enabled) {
                                    R.color.label_alternative
                                } else {
                                    R.color.label_disable
                                },
                                style = DesignSystemTheme.typography.body1Regular
                            )
                        )
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = value,
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
                },
                rightButton = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(
                            id = if (focused) {
                                R.drawable.ic_normal_chevron_up_svg
                            } else {
                                R.drawable.ic_normal_chevron_down_svg
                            }
                        ),
                        tint = colorResource(
                            id = if (enabled) {
                                R.color.label_alternative
                            } else {
                                R.color.label_disable
                            }
                        ),
                        contentDescription = ""
                    )
                },
                trailingIcon = if (enabled && value.isNotEmpty()) {
                    {
                        WantedCommonIcon(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .clickOnceForDesignSystem { onDelete(value) },
                            resourceId = R.drawable.ic_normal_circle_close_svg,
                            tint = colorResource(id = R.color.label_alternative)
                        )
                    }
                } else {
                    null
                }
            )
        }
    )
}

@Composable
private fun WantedSelectLayout(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    select: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {

        title?.invoke()

        select()
    }
}

@Composable
private fun SelectLayout(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    rightButton: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leadingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                leadingIcon()
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(weight = 1f, fill = false),
            contentAlignment = Alignment.CenterStart
        ) {
            text()
        }

        trailingIcon?.let {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                trailingIcon()
            }
        }

        Box(
            modifier = Modifier
                .size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            rightButton()
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedSelectPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    placeHolder = "선택해 주세요",
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    focused = true,
                    placeHolder = "선택해 주세요",
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    error = true,
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    focused = true,
                    error = true,
                    value = "선택값",
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    value = "선택값",
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    enabled = false,
                    value = "선택값",
                    onDelete = { },
                    onClick = {}
                )
            }
        }
    }
}