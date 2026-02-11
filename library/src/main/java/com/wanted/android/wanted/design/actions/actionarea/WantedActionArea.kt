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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedActionArea
 *
 * 하단 액션 버튼 영역을 생성합니다.
 *
 * 버튼은 positive, negative, neutral 텍스트로 생성하며, 각 버튼에 클릭 콜백을 전달할 수 있습니다.
 * 또한, Variant 속성을 활용하여 상단 영역에 부가적인 요소를 렌더링할 수 있습니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedActionArea(
 *     type = ActionAreaType.Strong,
 *     positive = "확인",
 *     onClickPositive = { /* 처리 */ },
 *     negative = "취소",
 *     onClickNegative = { /* 처리 */ },
 *     neutral = "건너뛰기",
 *     onClickNeutral = { /* 처리 */ }
 * )
 * ```
 *
 * @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
 * @param positive String: 메인(긍정) 액션 버튼의 텍스트입니다.
 * @param isEnablePositive Boolean: 메인 액션 버튼의 활성화 여부입니다.
 * @param onClickPositive () -> Unit: 메인 액션 버튼 클릭 콜백입니다.
 * @param negative String?: 서브(부정) 액션 버튼의 텍스트입니다.
 * @param isEnableNegative Boolean: 서브 액션 버튼의 활성화 여부입니다.
 * @param neutral String?: 추가(중립) 액션 버튼의 텍스트입니다.
 * @param isEnableNeutral Boolean: 추가 액션 버튼의 활성화 여부입니다.
 * @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
 * @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
 * @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
 * @param divider Boolean: 구분선 표시 여부를 지정합니다.
 * @param gradationColor Color: 배경 그라데이션 색상을 설정합니다.
 * @param onClickNegative (() -> Unit)?: 서브 액션 버튼 클릭 콜백입니다.
 * @param onClickNeutral (() -> Unit)?: 추가 액션 버튼 클릭 콜백입니다.
 * @param extra (@Composable () -> Unit)?: 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    type: ActionAreaType,
    positive: String,
    isEnablePositive: Boolean = true,
    onClickPositive: () -> Unit,
    negative: String? = null,
    isEnableNegative: Boolean = true,
    neutral: String? = null,
    isEnableNeutral: Boolean = true,
    caption: String? = null,
    scrollableState: ScrollableState? = null,
    modifier: Modifier = Modifier,
    background: Boolean = false,
    safeArea: Boolean = true,
    divider: Boolean = false,
    gradationColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
    extra: @Composable (() -> Unit)? = null
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
                enabled = isEnablePositive,
                onClick = onClickPositive
            )
        },
        negative = onClickNegative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.PRIMARY,
                    text = negative.orEmpty(),
                    enabled = isEnableNegative,
                    onClick = onClickNegative
                )
            }
        },
        neutral = onClickNeutral?.let {
            {
                WantedButton(
                    modifier = Modifier.wrapContentSize(),
                    variant = if (type == ActionAreaType.Strong) {
                        ButtonVariant.TEXT
                    } else {
                        ButtonVariant.OUTLINED
                    },
                    size = if (type == ActionAreaType.Strong) {
                        ButtonSize.SMALL
                    } else {
                        ButtonSize.LARGE
                    },
                    type = ButtonType.ASSISTIVE,
                    text = neutral.orEmpty(),
                    enabled = isEnableNeutral,
                    onClick = onClickNeutral
                )
            }
        },
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        divider = divider,
        extra = extra
    )
}


/**
 * WantedActionArea
 *
 * Slot을 활용하여 커스텀 버튼을 직접 전달할 수 있습니다.
 * 버튼의 스타일 및 배치를 완전히 자유롭게 제어할 수 있습니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedActionArea(
 *     type = ActionAreaType.Strong,
 *     positive = {
 *         CustomMainButton(onClick = { ... })
 *     },
 *     negative = {
 *         CustomSecondaryButton(onClick = { ... })
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
 * @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
 * @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
 * @param gradationColor Color: 배경 그라데이션 색상을 설정합니다.
 * @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
 * @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
 * @param divider Boolean: 구분선 표시 여부를 지정합니다.
 * @param positive (@Composable () -> Unit): 메인(긍정) 액션 버튼 Slot입니다.
 * @param negative (@Composable (() -> Unit)?): 서브(부정) 액션 버튼 Slot입니다.
 * @param neutral (@Composable (() -> Unit)?): 추가(중립) 액션 버튼 Slot입니다.
 * @param extra (@Composable (() -> Unit)?): 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    type: ActionAreaType = ActionAreaType.Strong,
    safeArea: Boolean = true,
    background: Boolean = false,
    gradationColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    caption: String? = null,
    scrollableState: ScrollableState? = null,
    divider: Boolean = false,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null,
    extra: @Composable (() -> Unit)? = null
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
        divider = divider,
        extra = extra
    )
}

@Deprecated("Slot 방식의 WantedActionArea를 사용하시기 바랍니다.", level = DeprecationLevel.ERROR)
@Composable
fun WantedActionArea(
    positive: String,
    onClickPositive: () -> Unit,
    negative: String? = null,
    onClickNegative: (() -> Unit)? = null,
    neutral: String? = null,
    onClickNeutral: (() -> Unit)? = null,
    actionAreaDefault: WantedActionAreaDefault = WantedActionAreaDefaults.getDefault(),
    safeArea: Boolean = true,
    divider: Boolean = false,
    background: Boolean = false,
    gradationColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    scrollableState: ScrollableState? = null,
    caption: String? = null,
    extra: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
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
        divider = divider,
        extra = extra
    )
}


@Composable
private fun WantedActionAreaLayout(
    modifier: Modifier = Modifier,
    safeArea: Boolean,
    background: Boolean,
    gradationColor: Color,
    type: ActionAreaType,
    divider: Boolean,
    scrollableState: ScrollableState? = null,
    extra: @Composable (() -> Unit)?,
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
        extra?.let {
            if (divider) {
                HorizontalDivider(color = DesignSystemTheme.colors.lineNormalNeutral)
            }

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
                extra()
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
                    .padding(top = if (background && extra == null) 0.dp else 20.dp)
                    .fillMaxWidth()
            } else {
                Modifier.padding(top = if (extra == null) 0.dp else 20.dp)
            }.constrainAs(box) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            if (background && extra == null && isShowGradient.value) {
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
                        modifier = Modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = negative,
                        neutral = neutral
                    )
                }

                ActionAreaType.Neutral -> {
                    WantedActionNeutralAreaLayout(
                        modifier = Modifier.then(contentModifier),
                        caption = caption,
                        positive = positive,
                        negative = negative,
                        neutral = neutral
                    )
                }

                ActionAreaType.Cancel -> {
                    WantedActionStrongAreaLayout(
                        modifier = Modifier.then(contentModifier),
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
            value = DesignSystemTheme.typography.label2Regular.copy(
                color = DesignSystemTheme.colors.labelAlternative
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
                    extra = {
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