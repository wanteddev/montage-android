package com.wanted.android.wanted.design.select

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedComponentTitle
import com.wanted.android.wanted.design.base.WantedDropShadow
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.select.view.WantedMultiSelectContents
import com.wanted.android.wanted.design.select.view.WantedMultiSelectPlaceHolder
import com.wanted.android.wanted.design.select.view.WantedSelectContentLayout
import com.wanted.android.wanted.design.select.view.WantedSelectLayout
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.wantedRippleEffect

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-44983&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?node-id=1934-43909&t=33KjAy2RlyzyhLH6-4
 */

@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    valueList: List<WantedSelectData>,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    errorList: List<WantedSelectData> = emptyList(),
    focused: Boolean = false,
    enabled: Boolean = true,
    isChip: Boolean = false,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onDelete: (WantedSelectData) -> Unit = {},
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectImpl(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        error = errorList.isNotEmpty(),
        focused = focused,
        enabled = enabled,
        background = background,
        onClick = onClick,
        leadingIcon = leadingIcon,
        contents = {
            WantedMultiSelectContents(
                modifier = Modifier.fillMaxWidth(),
                valueList = valueList,
                placeHolder = placeHolder,
                errorList = errorList,
                enabled = enabled,
                isChip = isChip,
                onDelete = onDelete
            )
        }
    )
}

@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    value: WantedSelectData,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    error: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelect(
        modifier = modifier,
        title = title,
        description = description,
        value = value.text,
        placeHolder = placeHolder,
        isRequiredBadge = isRequiredBadge,
        error = error,
        focused = focused,
        enabled = enabled,
        background = background,
        onClick = onClick,
        leadingIcon = leadingIcon
    )
}

@Composable
fun WantedSelect(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    value: String,
    placeHolder: String = "",
    isRequiredBadge: Boolean = false,
    error: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null
) {
    WantedSelectImpl(
        modifier = modifier,
        title = title,
        description = description,
        isRequiredBadge = isRequiredBadge,
        error = error,
        focused = focused,
        enabled = enabled,
        background = background,
        onClick = onClick,
        leadingIcon = leadingIcon,
        contents = {
            if (value.isEmpty()) {
                WantedMultiSelectPlaceHolder(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = placeHolder,
                    enabled = enabled
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    text = value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WantedTextStyle(
                        colorRes = if (enabled) {
                            R.color.label_normal
                        } else {
                            R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.body1Regular
                    )
                )
            }
        }
    )
}

@Composable
private fun WantedSelectImpl(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    isRequiredBadge: Boolean = false,
    error: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
    background: Color,
    onClick: () -> Unit = {},
    contents: @Composable () -> Unit,
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
            ConstraintLayout {
                val (shadow, select) = createRefs()
                WantedDropShadow(
                    Modifier
                        .constrainAs(shadow) {
                            top.linkTo(select.top)
                            bottom.linkTo(select.bottom)
                            start.linkTo(select.start)
                            end.linkTo(select.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    background = background,
                    shape = RoundedCornerShape(12.dp)
                )

                WantedSelectContentLayout(
                    modifier = Modifier
                        .constrainAs(select) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
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
                            color = when {
                                !enabled -> colorResource(R.color.line_normal_alternative)
                                error -> colorResource(R.color.status_negative).copy(OPACITY_43)
                                focused -> colorResource(R.color.primary_normal).copy(OPACITY_43)
                                else -> colorResource(R.color.line_normal_neutral)
                            },
                            width = if (focused) 2.dp else 1.dp
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (enabled) {
                                background
                            } else {
                                colorResource(R.color.line_normal_alternative)
                            }
                        )
                        .clickOnceForDesignSystem(
                            enabled = enabled,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = getSelectRippleEffect(
                                enabled = enabled,
                                focused = focused,
                                error = error
                            )
                        ) {
                            onClick()
                        }
                        .padding(12.dp),
                    leadingIcon = leadingIcon,
                    contents = {
                        contents()
                    },
                    rightButton = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(
                                id = if (focused) {
                                    R.drawable.ic_normal_chevron_up_thick_small_svg
                                } else {
                                    R.drawable.ic_normal_chevron_down_thick_small_svg
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
                    trailingIcon = if (error && !focused && enabled) {
                        {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(1.dp),
                                painter = painterResource(id = R.drawable.ic_normal_circle_exclamation_fill_svg),
                                tint = colorResource(id = R.color.status_negative),
                                contentDescription = ""
                            )
                        }
                    } else {
                        null
                    }
                )

            }

        },
        description = description?.let {
            {
                Text(
                    text = description,
                    style = WantedTextStyle(
                        colorRes = when {
                            enabled && error -> R.color.status_negative
                            else -> R.color.label_alternative
                        },
                        style = DesignSystemTheme.typography.caption1Regular
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    )
}

@Composable
private fun getSelectRippleEffect(
    error: Boolean = false,
    focused: Boolean = false,
    enabled: Boolean = true,
) = when {
    !enabled -> null
    error -> wantedRippleEffect(colorResource(id = R.color.status_negative))
    focused -> wantedRippleEffect(colorResource(id = R.color.primary_normal))
    else -> wantedRippleEffect(colorResource(id = R.color.label_normal_opacity12))
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
                    value = "asdf",
                    enabled = false,
                    description = "메시지에 마침표를 찍어요.",
                    placeHolder = "선택해 주세요",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    focused = true,
                    placeHolder = "선택해 주세요",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    value = "선택값",
                    description = "메시지에 마침표를 찍어요.",
                    error = true,
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    focused = true,
                    error = true,
                    value = "선택값",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    enabled = false,
                    value = "선택값",
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    valueList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    title = "주제",
                    isRequiredBadge = true,
                    valueList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    errorList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    isChip = true,
                    valueList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    errorList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    isChip = true,
                    focused = true,
                    valueList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    errorList = listOf(
                        WantedSelectData(text = "선택값1")
                    ),
                    onDelete = { },
                    onClick = {}
                )

                WantedSelect(
                    modifier = Modifier.fillMaxWidth(),
                    isChip = true,
                    enabled = false,
                    valueList = listOf(
                        WantedSelectData(text = "선택값1"),
                        WantedSelectData(text = "선택값2")
                    ),
                    errorList = listOf(
                        WantedSelectData(text = "선택값2")
                    ),
                    onDelete = { },
                    onClick = {}
                )
            }
        }
    }
}