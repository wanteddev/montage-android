package com.wanted.android.wanted.design.actions.actionarea

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedActionArea
 *
 * 하단 액션 버튼 영역을 생성합니다.
 *
 * 버튼은 main, alternative, sub 텍스트로 생성하며, 각 버튼에 클릭 콜백을 전달할 수 있습니다.
 * 또한, Variant 속성을 활용하여 상단 영역에 부가적인 요소를 렌더링할 수 있습니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedActionArea(
 *     type = ActionAreaType.Strong,
 *     main = "확인",
 *     onClickMain = { /* 처리 */ },
 *     alternative = "취소",
 *     onClickAlternative = { /* 처리 */ },
 *     sub = "건너뛰기",
 *     onClickSub = { /* 처리 */ }
 * )
 * ```
 *
 * @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
 * @param main String: 메인 액션 버튼의 텍스트입니다.
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param isEnableMain Boolean: 메인 액션 버튼의 활성화 여부입니다.
 * @param onClickMain () -> Unit: 메인 액션 버튼 클릭 콜백입니다.
 * @param alternative String?: 대체 액션 버튼의 텍스트입니다.
 * @param isEnableAlternative Boolean: 대체 액션 버튼의 활성화 여부입니다.
 * @param sub String?: 보조 액션 버튼의 텍스트입니다.
 * @param isEnableSub Boolean: 보조 액션 버튼의 활성화 여부입니다.
 * @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
 * @param captionIcon Int?: 캡션 텍스트 앞에 표시할 아이콘 리소스입니다. 기본값은 아이콘 없음이며, 권장 아이콘은 [WantedActionAreaDefaults.CAPTION_ICON]입니다.
 * @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
 * @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
 * @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
 * @param divider Boolean: 구분선 표시 여부를 지정합니다. extra가 있을 때만 표시됩니다.
 * @param backgroundColor Color: Extra·버튼 영역의 배경색이자 sticky 그라데이션의 색상입니다. 그라데이션이 꺼져 있고 extra 도 없으면 배경을 칠하지 않습니다.
 * @param onClickAlternative (() -> Unit)?: 대체 액션 버튼 클릭 콜백입니다.
 * @param onClickSub (() -> Unit)?: 보조 액션 버튼 클릭 콜백입니다.
 * @param extra (@Composable () -> Unit)?: 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    type: ActionAreaType,
    main: String,
    modifier: Modifier = Modifier,
    isEnableMain: Boolean = true,
    onClickMain: () -> Unit,
    alternative: String? = null,
    isEnableAlternative: Boolean = true,
    sub: String? = null,
    isEnableSub: Boolean = true,
    caption: String? = null,
    @DrawableRes captionIcon: Int? = null,
    scrollableState: ScrollableState? = null,
    background: Boolean = false,
    safeArea: Boolean = true,
    divider: Boolean = true,
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    onClickAlternative: (() -> Unit)? = null,
    onClickSub: (() -> Unit)? = null,
    extra: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = type,
        safeArea = safeArea,
        background = background,
        backgroundColor = backgroundColor,
        scrollableState = scrollableState,
        main = {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                variant = WantedActionAreaDefaults.getMainButtonVariant(),
                type = WantedActionAreaDefaults.getMainButtonType(type),
                size = WantedActionAreaDefaults.getMainButtonSize(),
                text = main,
                enabled = isEnableMain,
                onClick = onClickMain
            )
        },
        alternative = onClickAlternative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    variant = WantedActionAreaDefaults.getAlternativeButtonVariant(),
                    type = WantedActionAreaDefaults.getAlternativeButtonType(),
                    size = WantedActionAreaDefaults.getAlternativeButtonSize(),
                    text = alternative.orEmpty(),
                    enabled = isEnableAlternative,
                    onClick = onClickAlternative
                )
            }
        },
        sub = onClickSub?.let {
            {
                WantedButton(
                    modifier = Modifier.wrapContentSize(),
                    variant = WantedActionAreaDefaults.getSubButtonVariant(type),
                    size = WantedActionAreaDefaults.getSubButtonSize(type),
                    type = WantedActionAreaDefaults.getSubButtonType(type),
                    text = sub.orEmpty(),
                    enabled = isEnableSub,
                    onClick = onClickSub
                )
            }
        },
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        captionIcon = captionIcon,
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
 *     main = {
 *         CustomMainButton(onClick = { ... })
 *     },
 *     alternative = {
 *         CustomSecondaryButton(onClick = { ... })
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param type ActionAreaType: 액션 영역의 타입을 설정합니다.
 * @param safeArea Boolean: SafeArea를 적용할지 여부를 지정합니다.
 * @param background Boolean: 배경 그라데이션 표시 여부를 지정합니다.
 * @param backgroundColor Color: Extra·버튼 영역의 배경색이자 sticky 그라데이션의 색상입니다. 그라데이션이 꺼져 있고 extra 도 없으면 배경을 칠하지 않습니다.
 * @param caption String?: 액션 영역 상단에 표시할 캡션입니다.
 * @param captionIcon Int?: 캡션 텍스트 앞에 표시할 아이콘 리소스입니다. 기본값은 아이콘 없음이며, 권장 아이콘은 [WantedActionAreaDefaults.CAPTION_ICON]입니다.
 * @param scrollableState ScrollableState?: 스크롤이 가능한 경우 상태를 전달합니다.
 * @param divider Boolean: 구분선 표시 여부를 지정합니다. extra가 있을 때만 표시됩니다.
 * @param main (@Composable () -> Unit): 메인 액션 버튼 Slot입니다.
 * @param alternative (@Composable (() -> Unit)?): 대체 액션 버튼 Slot입니다.
 * @param sub (@Composable (() -> Unit)?): 보조 액션 버튼 Slot입니다.
 * @param extra (@Composable (() -> Unit)?): 추가적으로 표시할 컴포넌트입니다.
 */
@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    type: ActionAreaType = ActionAreaType.Strong,
    safeArea: Boolean = true,
    background: Boolean = false,
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    caption: String? = null,
    @DrawableRes captionIcon: Int? = null,
    scrollableState: ScrollableState? = null,
    divider: Boolean = true,
    main: @Composable () -> Unit,
    alternative: @Composable (() -> Unit)? = null,
    sub: @Composable (() -> Unit)? = null,
    extra: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = type,
        safeArea = safeArea,
        background = background,
        backgroundColor = backgroundColor,
        scrollableState = scrollableState,
        main = main,
        alternative = alternative,
        sub = sub,
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        captionIcon = captionIcon,
        divider = divider,
        extra = extra
    )
}

@Deprecated("Slot 방식의 WantedActionArea를 사용하시기 바랍니다.", level = DeprecationLevel.ERROR)
@Composable
fun WantedActionArea(
    main: String,
    onClickMain: () -> Unit,
    modifier: Modifier = Modifier,
    alternative: String? = null,
    onClickAlternative: (() -> Unit)? = null,
    sub: String? = null,
    onClickSub: (() -> Unit)? = null,
    actionAreaDefault: WantedActionAreaDefault = WantedActionAreaDefaults.getDefault(),
    safeArea: Boolean = true,
    divider: Boolean = true,
    background: Boolean = false,
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    scrollableState: ScrollableState? = null,
    caption: String? = null,
    extra: @Composable (() -> Unit)? = null
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = actionAreaDefault.type,
        safeArea = safeArea,
        background = background,
        backgroundColor = backgroundColor,
        scrollableState = scrollableState,
        main = {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = main,
                buttonDefault = actionAreaDefault.mainButtonDefault,
                onClick = onClickMain
            )
        },
        alternative = onClickAlternative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = alternative.orEmpty(),
                    buttonDefault = actionAreaDefault.alternativeButtonDefault,
                    onClick = onClickAlternative
                )
            }
        },
        sub = onClickSub?.let {
            {
                WantedButton(
                    modifier = if (actionAreaDefault.type == ActionAreaType.Strong) {
                        Modifier
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    text = sub.orEmpty(),
                    buttonDefault = actionAreaDefault.subButtonDefault,
                    onClick = onClickSub
                )
            }
        },
        caption = caption?.let {
            {
                Text(text = caption)
            }
        },
        captionIcon = null,
        divider = divider,
        extra = extra
    )
}


@Composable
private fun WantedActionAreaLayout(
    modifier: Modifier = Modifier,
    safeArea: Boolean,
    background: Boolean,
    backgroundColor: Color,
    type: ActionAreaType,
    divider: Boolean,
    scrollableState: ScrollableState? = null,
    extra: @Composable (() -> Unit)?,
    caption: @Composable (() -> Unit)?,
    @DrawableRes captionIcon: Int?,
    main: @Composable () -> Unit,
    alternative: @Composable (() -> Unit)?,
    sub: @Composable (() -> Unit)?
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
        modifier = modifier.actionAreaBackground(
            background = background,
            hasExtra = extra != null,
            backgroundColor = backgroundColor
        ),
    ) {
        extra?.let {
            if (divider) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = DesignSystemTheme.colors.lineNeutralTertiary
                )
            }

            Box(
                modifier = if (safeArea) {
                    Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                } else {
                    Modifier
                        .padding(top = 20.dp)
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
                    color = backgroundColor
                )
            }

            when (type) {
                ActionAreaType.Strong -> {
                    WantedActionStrongAreaLayout(
                        modifier = Modifier.then(contentModifier),
                        caption = caption,
                        captionIcon = captionIcon,
                        main = main,
                        alternative = alternative,
                        sub = sub
                    )
                }

                ActionAreaType.Neutral -> {
                    WantedActionNeutralAreaLayout(
                        modifier = Modifier.then(contentModifier),
                        caption = caption,
                        captionIcon = captionIcon,
                        main = main,
                        alternative = alternative,
                        sub = sub
                    )
                }

                ActionAreaType.Cancel -> {
                    WantedActionStrongAreaLayout(
                        modifier = Modifier.then(contentModifier),
                        caption = caption,
                        captionIcon = captionIcon,
                        main = main,
                        alternative = null,
                        sub = null
                    )
                }
            }
        }
    }
}

/**
 * Modifier.actionAreaBackground
 *
 * Extra 영역과 버튼 영역에 배경색을 칠합니다. 그라디언트가 꺼져 있고 Extra 도 없을 때만 배경을 걷어
 * 페이지 배경이 그대로 비치게 둡니다(iOS 와 동일).
 *
 * 배경을 칠하지 않으면 다크모드에서 Extra 영역이 페이지 배경색으로 보이고,
 * sticky 일 때 그라디언트 끝에 경계선이 생깁니다.
 *
 * @param background Boolean: 배경 그라데이션 표시 여부입니다.
 * @param hasExtra Boolean: Extra 슬롯이 있는지 여부입니다.
 * @param backgroundColor Color: 칠할 배경색입니다.
 * @return Modifier: 배경이 적용된 Modifier 입니다.
 */
private fun Modifier.actionAreaBackground(
    background: Boolean,
    hasExtra: Boolean,
    backgroundColor: Color
): Modifier = if (background || hasExtra) {
    this.background(color = backgroundColor)
} else {
    this
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
    @DrawableRes captionIcon: Int? = null,
    main: @Composable () -> Unit,
    alternative: @Composable (() -> Unit)? = null,
    sub: @Composable (() -> Unit)? = null
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
                caption = caption,
                captionIcon = captionIcon
            )
        }

        main()

        alternative?.invoke()

        // 보조 액션(텍스트 버튼)은 위아래 8 여백을 갖는다. 여백이 없으면 대체 액션과의 간격이
        // 좁아 보이고 영역 높이가 16 짧아진다.
        sub?.let {
            Box(modifier = Modifier.padding(vertical = SUB_ACTION_VERTICAL_PADDING)) {
                sub()
            }
        }
    }
}

@Composable
private fun WantedActionNeutralAreaLayout(
    modifier: Modifier = Modifier,
    caption: @Composable (() -> Unit)? = null,
    @DrawableRes captionIcon: Int? = null,
    main: @Composable () -> Unit,
    alternative: @Composable (() -> Unit)? = null,
    sub: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        caption?.let {
            CaptionLayout(
                caption = caption,
                captionIcon = captionIcon
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 보조 액션은 라벨 길이에 맞춰 줄어든다. 최소 폭을 주면 좌우에 빈 공간이 생겨
            // 대체 액션과의 간격이 12보다 넓어 보인다.
            sub?.let {
                Box(modifier = Modifier.wrapContentSize()) {
                    sub()
                }
            }


            alternative?.let {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    alternative()
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                main()
            }
        }
    }
}

@Composable
private fun WantedActionCompactAreaLayout(
    modifier: Modifier = Modifier,
    caption: @Composable (() -> Unit)? = null,
    @DrawableRes captionIcon: Int? = null,
    main: @Composable () -> Unit,
    alternative: @Composable (() -> Unit)? = null,
    sub: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        caption?.let {
            CaptionLayout(
                caption = caption,
                captionIcon = captionIcon
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {

            sub?.let {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .wrapContentHeight()
                ) {
                    sub?.invoke()
                }
            }


            alternative?.let {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .wrapContentHeight()
                ) {
                    alternative()
                }
            }


            Box(
                modifier = Modifier
                    .width(84.dp)
                    .wrapContentHeight()
            ) {
                main()
            }
        }
    }
}

@Composable
private fun CaptionLayout(
    modifier: Modifier = Modifier,
    caption: @Composable () -> Unit,
    @DrawableRes captionIcon: Int? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(CAPTION_ICON_SPACING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            captionIcon?.let {
                Icon(
                    modifier = Modifier.size(CAPTION_ICON_SIZE),
                    painter = painterResource(id = captionIcon),
                    tint = DesignSystemTheme.colors.foregroundNeutralTertiary,
                    contentDescription = null
                )
            }

            ProvideTextStyle(
                value = DesignSystemTheme.typography.label2Medium.copy(
                    color = DesignSystemTheme.colors.foregroundNeutralTertiary
                )
            ) {
                caption()
            }
        }
    }
}

private val SUB_ACTION_VERTICAL_PADDING = 8.dp
private val CAPTION_ICON_SIZE = 16.dp
private val CAPTION_ICON_SPACING = 4.dp


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
                    main = "메인 액션",
                    alternative = "대체 액션",
                    sub = "보조 액션",
                    onClickMain = {},
                    onClickAlternative = {},
                    onClickSub = {},
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
                    main = "메인 액션",
                    sub = "보조 액션",
                    onClickMain = {},
                    onClickSub = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Strong,
                    caption = "캡션",
                    captionIcon = WantedActionAreaDefaults.CAPTION_ICON,
                    main = "메인 액션",
                    alternative = "대체 액션",
                    onClickMain = {},
                    onClickAlternative = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Cancel,
                    caption = "캡션",
                    main = "메인 액션",
                    alternative = "대체 액션",
                    sub = "보조 액션",
                    onClickMain = {},
                    onClickAlternative = {},
                    onClickSub = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Cancel,
                    background = true,
                    caption = "캡션",
                    main = "메인 액션",
                    alternative = "대체 액션",
                    sub = "보조 액션",
                    onClickMain = {},
                    onClickAlternative = {},
                    onClickSub = {}
                )
            }
        }
    }
}