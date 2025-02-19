package com.wanted.android.wanted.design.actions.actionarea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45079&m=dev
 */
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    sticky: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    type: ActionAreaType,
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    onClickPositive: () -> Unit,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
    caption: String? = null,
    variant: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = type,
        safeArea = safeArea,
        sticky = sticky,
        gradationColor = gradationColor,
        positive = {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = positive,
                onClick = onClickPositive
            )
        },
        negative = onClickNegative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = negative.orEmpty(),
                    onClick = onClickNegative
                )
            }
        },
        neutral = onClickNeutral?.let {
            {
                WantedButton(
                    modifier = if (type == ActionAreaType.Strong) {
                        Modifier
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    text = neutral.orEmpty(),
                    onClick = onClickNeutral
                )
            }
        },
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        variant = variant
    )
}


@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    sticky: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    type: ActionAreaType = ActionAreaType.Strong,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null,
    caption: String? = null,
    variant: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = type,
        safeArea = safeArea,
        sticky = sticky,
        gradationColor = gradationColor,
        positive = positive,
        negative = negative,
        neutral = neutral,
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        variant = variant
    )
}


@Deprecated("", level = DeprecationLevel.ERROR)
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    sticky: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    actionAreaDefault: WantedActionAreaDefault = WantedActionAreaDefaults.getDefault(),
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    onClickPositive: () -> Unit,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
    caption: String? = null,
    variant: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = actionAreaDefault.type,
        safeArea = safeArea,
        sticky = sticky,
        gradationColor = gradationColor,
        positive = {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = positive,
                buttonDefault = actionAreaDefault.positiveButtonDefault,
                onClick = onClickPositive
            )
        },
        negative = onClickNegative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = negative.orEmpty(),
                    buttonDefault = actionAreaDefault.negativeButtonDefault,
                    onClick = onClickNegative
                )
            }
        },
        neutral = onClickNeutral?.let {
            {
                WantedButton(
                    modifier = if (actionAreaDefault.type == ActionAreaType.Strong) {
                        Modifier
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    text = neutral.orEmpty(),
                    buttonDefault = actionAreaDefault.neutralButtonDefault,
                    onClick = onClickNeutral
                )
            }
        },
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        variant = variant
    )
}


@Composable
private fun WantedActionAreaLayout(
    modifier: Modifier = Modifier,
    safeArea: Boolean,
    sticky: Boolean,
    gradationColor: Color,
    type: ActionAreaType,
    variant: @Composable (() -> Unit)?,
    caption: @Composable (() -> Unit)?,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)?,
    neutral: @Composable (() -> Unit)?
) {
    Column(
        modifier = modifier,
    ) {

        variant?.let {
            HorizontalDivider(color = colorResource(id = R.color.line_normal_neutral))

            Box(
                modifier = if (safeArea) {
                    Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 4.dp)
                        .fillMaxWidth()
                } else {
                    Modifier
                        .padding(top = 20.dp, bottom = 4.dp)
                        .fillMaxWidth()
                }
            ) {
                variant()
            }
        }

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (box, gradation) = createRefs()
            val contentModifier = if (safeArea) {
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
                    .padding(top = if (sticky && variant == null) 0.dp else 20.dp)
                    .fillMaxWidth()
            } else {
                Modifier.padding(top = if (variant == null) 0.dp else 20.dp)
            }.constrainAs(box) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            if (sticky && variant == null) {
                WantedActionAreaGradation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .constrainAs(gradation) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(box.top)
                        },
                    color = gradationColor
                )
            }

            when (type) {
                ActionAreaType.Strong -> {
                    WantedActionStrongAreaLayout(
                        modifier = modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = negative,
                        neutral = neutral
                    )
                }

                ActionAreaType.Neutral -> {
                    WantedActionNeutralAreaLayout(
                        modifier = modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = negative,
                        neutral = neutral
                    )
                }

                ActionAreaType.Compact -> {
                    WantedActionCompactAreaLayout(
                        modifier = modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = negative,
                        neutral = neutral
                    )
                }

                ActionAreaType.Cancel -> {
                    WantedActionStrongAreaLayout(
                        modifier = modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = null,
                        neutral = null
                    )
                }
            }
        }
    }
}

@Composable
fun WantedActionAreaGradation(
    modifier: Modifier = Modifier,
    color: Color
) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color.copy(0.0f),
                                color.copy(0.14f),
                                color.copy(0.27f),
                                color.copy(0.38f),
                                color.copy(0.48f),
                                color.copy(0.57f),
                                color.copy(0.65f),
                                color.copy(0.71f),
                                color.copy(0.77f),
                                color.copy(0.82f),
                                color.copy(0.86f),
                                color.copy(0.90f),
                                color.copy(0.93f),
                                color.copy(0.96f),
                                color.copy(0.98f),
                                color,
                            )
                        )
                    )
            )
        }
    ) { measurables, constraints ->
        val textPlaceable = measurables[0].measure(constraints)

        // Calculate the expanded dimensions
        val expandedHeight = textPlaceable.height

        layout(textPlaceable.width, expandedHeight) {
            textPlaceable.placeRelative(
                x = 0,
                y = -textPlaceable.height
            )
        }
    }
}

@Composable
private fun WantedActionStrongAreaLayout(
    modifier: Modifier = Modifier,
    caption: @Composable (() -> Unit)? = null,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        caption?.let {
            CaptionLayout(
                modifier = Modifier.padding(bottom = 8.dp),
                caption = caption
            )
        }

        positive()

        negative?.invoke()

        neutral?.invoke()
    }
}

@Composable
private fun WantedActionNeutralAreaLayout(
    modifier: Modifier = Modifier,
    caption: @Composable (() -> Unit)? = null,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        caption?.let {
            CaptionLayout(caption = caption)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {

            neutral?.let {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .wrapContentHeight()
                ) {
                    neutral()
                }
            }


            negative?.let {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    negative()
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                positive()
            }
        }
    }
}

@Composable
private fun WantedActionCompactAreaLayout(
    modifier: Modifier = Modifier,
    caption: @Composable (() -> Unit)? = null,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        caption?.let {
            CaptionLayout(caption = caption)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {

            neutral?.let {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .wrapContentHeight()
                ) {
                    neutral?.invoke()
                }
            }


            negative?.let {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .wrapContentHeight()
                ) {
                    negative()
                }
            }


            Box(
                modifier = Modifier
                    .width(84.dp)
                    .wrapContentHeight()
            ) {
                positive()
            }
        }
    }
}

@Composable
private fun CaptionLayout(
    modifier: Modifier = Modifier,
    caption: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ProvideTextStyle(
            value = WantedTextStyle(
                colorRes = R.color.label_alternative,
                style = DesignSystemTheme.typography.label2Regular
            )
        ) {
            caption()
        }
    }
}


@DevicePreviews
@Composable
private fun WantedActionAreaPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedActionArea(
                    type = ActionAreaType.Strong,
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {},
                    variant = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .background(Color.Gray)
                        ) {
                            Text(text = "variant")
                        }
                    }
                )

                WantedActionArea(
                    type = ActionAreaType.Neutral,
                    caption = "캡션",
                    positive = "메인 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNeutral = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Compact,
                    caption = "캡션",
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Cancel,
                    caption = "캡션",
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Cancel,
                    sticky = true,
                    caption = "캡션",
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )
            }
        }
    }
}