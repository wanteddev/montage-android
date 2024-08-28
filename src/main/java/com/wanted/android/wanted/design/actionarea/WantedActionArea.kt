package com.wanted.android.wanted.design.actionarea

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.button.WantedButton
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
    type: ActionAreaType = ActionAreaType.Strong,
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    onClickPositive: () -> Unit,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
    caption: String? = null
) {
    WantedActionArea(
        modifier = modifier,
        safeArea = safeArea,
        sticky = sticky,
        actionAreaDefault = WantedActionAreaDefaults.getDefault(type = type),
        positive = positive,
        negative = negative,
        neutral = neutral,
        onClickPositive = onClickPositive,
        onClickNegative = onClickNegative,
        onClickNeutral = onClickNeutral,
        caption = caption
    )
}

@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    sticky: Boolean = false,
    actionAreaDefault: WantedActionAreaDefault = WantedActionAreaDefaults.getDefault(),
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    onClickPositive: () -> Unit,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
    caption: String? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = actionAreaDefault.type,
        safeArea = safeArea,
        sticky = sticky,
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
        }
    )
}


@Composable
private fun WantedActionAreaLayout(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    sticky: Boolean = false,
    type: ActionAreaType = ActionAreaType.Strong,
    caption: @Composable (() -> Unit)? = null,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {

    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, gradation) = createRefs()
        val safeAreaModifier = if (safeArea) {
            Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .padding(top = if (sticky) 0.dp else 20.dp)
                .fillMaxWidth()
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

        } else {
            Modifier
        }

        if (sticky) {
            WantedActionSticky(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .constrainAs(gradation) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(box.top)
                    }
            )
        }

        when (type) {
            ActionAreaType.Strong -> {
                WantedActionStrongAreaLayout(
                    modifier = modifier.then(safeAreaModifier),
                    caption = caption,
                    positive = positive,
                    negative = negative,
                    neutral = neutral
                )
            }

            ActionAreaType.Neutral -> {
                WantedActionNeutralAreaLayout(
                    modifier = modifier.then(safeAreaModifier),
                    caption = caption,
                    positive = positive,
                    negative = negative,
                    neutral = neutral
                )
            }

            ActionAreaType.Compact -> {
                WantedActionCompactAreaLayout(
                    modifier = modifier.then(safeAreaModifier),
                    caption = caption,
                    positive = positive,
                    negative = negative,
                    neutral = neutral
                )
            }

            ActionAreaType.Cancel -> {
                WantedActionStrongAreaLayout(
                    modifier = modifier.then(safeAreaModifier),
                    caption = caption,
                    positive = positive,
                    negative = null,
                    neutral = null
                )
            }
        }
    }
}

@Composable
fun WantedActionSticky(modifier: Modifier = Modifier) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.transparent),
                                colorResource(id = R.color.background_normal_normal)
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

            Box(
                modifier = Modifier
                    .weight(84f)
                    .wrapContentHeight()
            ) {
                neutral?.invoke()
            }

            Box(
                modifier = Modifier
                    .weight(106f)
                    .wrapContentHeight()
            ) {
                negative?.invoke()
            }

            Box(
                modifier = Modifier
                    .weight(106f)
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

            Spacer(modifier = Modifier.weight(32f))

            Box(
                modifier = Modifier
                    .weight(84f)
                    .wrapContentHeight()
            ) {
                neutral?.invoke()
            }

            Box(
                modifier = Modifier
                    .weight(84f)
                    .wrapContentHeight()
            ) {
                negative?.invoke()
            }


            Box(
                modifier = Modifier
                    .weight(84f)
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


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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
                    onClickNeutral = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Neutral,
                    caption = "캡션",
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
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