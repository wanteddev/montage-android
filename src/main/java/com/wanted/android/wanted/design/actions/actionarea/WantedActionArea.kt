package com.wanted.android.wanted.design.actions.actionarea

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 화면 하단에 액션 버튼 영역을 생성하는 Compose 함수입니다.
 * SafeArea 처리, 배경 그라데이션, 스크롤 상태 반영 등을 지원합니다.
 *
 * @param modifier Modifier를 설정합니다.
 * @param safeArea SafeArea를 적용할지 여부를 지정합니다.
 * @param background 배경 그라데이션 표시 여부를 지정합니다.
 * @param gradationColor 배경 그라데이션 색상을 설정합니다.
 * @param type 액션 영역의 타입을 설정합니다.
 * @param positive 메인(긍정) 액션 버튼 텍스트입니다.
 * @param negative 서브(부정) 액션 버튼 텍스트입니다.
 * @param neutral 추가(중립) 액션 버튼 텍스트입니다.
 * @param scrollableState 스크롤 가능한 경우 상태를 전달합니다.
 * @param onClickPositive 메인 액션 버튼 클릭 콜백입니다.
 * @param onClickNegative 서브 액션 버튼 클릭 콜백입니다.
 * @param onClickNeutral 추가 액션 버튼 클릭 콜백입니다.
 * @param caption 액션 영역 상단에 표시할 캡션입니다.
 * @param variant 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    background: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    type: ActionAreaType,
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    scrollableState: ScrollableState? = null,
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
        background = background,
        gradationColor = gradationColor,
        scrollableState = scrollableState,
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
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.SECONDARY,
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
                        Modifier.wrapContentSize()
                    },
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.ASSISTIVE,
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


/**
 * 직접 Composable Slot으로 액션 버튼을 구성하는 Compose 함수입니다.
 * 다양한 커스터마이징이 필요한 경우 사용합니다.
 *
 * @param modifier Modifier를 설정합니다.
 * @param safeArea SafeArea를 적용할지 여부를 지정합니다.
 * @param background 배경 그라데이션 표시 여부를 지정합니다.
 * @param gradationColor 배경 그라데이션 색상을 설정합니다.
 * @param type 액션 영역의 타입을 설정합니다.
 * @param scrollableState 스크롤 가능한 경우 상태를 전달합니다.
 * @param positive 메인(긍정) 액션 버튼 Slot입니다.
 * @param negative 서브(부정) 액션 버튼 Slot입니다.
 * @param neutral 추가(중립) 액션 버튼 Slot입니다.
 * @param caption 액션 영역 상단에 표시할 캡션입니다.
 * @param variant 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true, // dialog 에서는 false, 일반 screen  에서는 true
    background: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    type: ActionAreaType = ActionAreaType.Strong,
    scrollableState: ScrollableState? = null,
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
        background = background,
        gradationColor = gradationColor,
        scrollableState = scrollableState,
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

/**
 * Warning: Deprecated API - 더 이상 사용되지 않습니다.
 * 대신 Slot 방식을 사용하는 WantedActionArea를 사용하세요.
 *
 * @param modifier Modifier를 설정합니다.
 * @param safeArea SafeArea를 적용할지 여부를 지정합니다.
 * @param background 배경 그라데이션 표시 여부를 지정합니다.
 * @param gradationColor 배경 그라데이션 색상을 설정합니다.
 * @param actionAreaDefault 버튼 스타일 기본값을 설정합니다.
 * @param positive 메인 액션 버튼 텍스트입니다.
 * @param negative 서브 액션 버튼 텍스트입니다.
 * @param neutral 추가 액션 버튼 텍스트입니다.
 * @param scrollableState 스크롤 가능한 경우 상태를 전달합니다.
 * @param onClickPositive 메인 액션 버튼 클릭 콜백입니다.
 * @param onClickNegative 서브 액션 버튼 클릭 콜백입니다.
 * @param onClickNeutral 추가 액션 버튼 클릭 콜백입니다.
 * @param caption 액션 영역 상단에 표시할 캡션입니다.
 * @param variant 추가적으로 표시할 컴포넌트입니다.
 */
@Deprecated("Slot 방식을 사용하는 WantedActionArea를 사용하세요", level = DeprecationLevel.ERROR)
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    background: Boolean = false,
    gradationColor: Color = colorResource(id = R.color.background_normal_normal),
    actionAreaDefault: WantedActionAreaDefault = WantedActionAreaDefaults.getDefault(),
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    scrollableState: ScrollableState? = null,
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
        background = background,
        gradationColor = gradationColor,
        scrollableState = scrollableState,
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
    background: Boolean,
    gradationColor: Color,
    type: ActionAreaType,
    scrollableState: ScrollableState? = null,
    variant: @Composable (() -> Unit)?,
    caption: @Composable (() -> Unit)?,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)?,
    neutral: @Composable (() -> Unit)?
) {
    val isShowGradient = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = scrollableState?.canScrollForward) {
        scrollableState?.canScrollForward?.let {
            isShowGradient.value = scrollableState.canScrollForward == true
        } ?: run {
            isShowGradient.value = true
        }
    }

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
                    .padding(top = if (background && variant == null) 0.dp else 20.dp)
                    .fillMaxWidth()
            } else {
                Modifier.padding(top = if (variant == null) 0.dp else 20.dp)
            }.constrainAs(box) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            if (background && variant == null && isShowGradient.value) {
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
                        .defaultMinSize(84.dp)
                        .wrapContentSize()
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
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
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
                    background = true,
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