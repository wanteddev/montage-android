package com.wanted.android.wanted.design.feedback.fallback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 이미지, 제목, 설명, 버튼 등을 조합하여 비어 있는 상태를 안내하는 컴포넌트입니다.
 *
 * 주로 데이터가 없거나 결과가 없을 때 사용자에게 피드백을 제공하는 용도로 사용됩니다.
 * 이미지, 제목, 설명, 버튼을 선택적으로 구성할 수 있으며, 버튼 클릭 시 콜백을 전달할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFallbackView(
 *     heading = "데이터가 없습니다.",
 *     description = "새로운 데이터를 추가해보세요.",
 *     positive = "추가하기",
 *     onClickPositive = { /* 버튼 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param buttonVariant WantedFallbackButtonVariant: 버튼 배치 방식을 지정합니다 (Single, Horizontal, Vertical).
 * @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
 * @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
 * @param positive String?: 긍정 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param negative String?: 부정 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param positiveColor ButtonType: 긍정 버튼의 색상 타입입니다. 기본값은 ASSISTIVE입니다.
 * @param negativeColor ButtonType: 부정 버튼의 색상 타입입니다. 기본값은 ASSISTIVE입니다.
 * @param image (@Composable () -> Unit)?: 중앙에 표시될 이미지 컴포넌트입니다.
 * @param onClickPositive () -> Unit: 긍정 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClickNegative () -> Unit: 부정 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedFallbackView(
    modifier: Modifier = Modifier,
    buttonVariant: WantedFallbackButtonVariant = WantedFallbackButtonVariant.Single,
    heading: String? = null,
    description: String? = null,
    positive: String? = null,
    negative: String? = null,
    positiveColor: ButtonType = ButtonType.ASSISTIVE,
    negativeColor: ButtonType = ButtonType.ASSISTIVE,
    image: @Composable (() -> Unit)? = null,
    onClickPositive: () -> Unit = {},
    onClickNegative: () -> Unit = {}
) {
    WantedFallbackLayout(
        modifier = modifier.fillMaxWidth(),
        image = image,
        heading = heading?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        description = description?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        button = {
            when (buttonVariant) {
                WantedFallbackButtonVariant.Single -> {
                    WantedFallbackSingleButton(
                        positive = positive,
                        positiveColor = positiveColor,
                        onClickPositive = onClickPositive
                    )
                }

                WantedFallbackButtonVariant.Horizontal -> {
                    WantedFallbackHorizontalButtons(
                        positive = positive,
                        negative = negative,
                        positiveColor = positiveColor,
                        negativeColor = negativeColor,
                        onClickPositive = onClickPositive,
                        onClickNegative = onClickNegative
                    )
                }

                WantedFallbackButtonVariant.Vertical -> {
                    WantedFallbackVerticalButtons(
                        positive = positive,
                        negative = negative,
                        positiveColor = positiveColor,
                        negativeColor = negativeColor,
                        onClickPositive = onClickPositive,
                        onClickNegative = onClickNegative
                    )
                }
            }
        }
    )
}

@Composable
private fun WantedFallbackSingleButton(
    positive: String?,
    positiveColor: ButtonType,
    modifier: Modifier = Modifier,
    onClickPositive: () -> Unit
) {
    positive?.let {
        Box(modifier = modifier) {
            WantedButton(
                text = it,
                variant = ButtonVariant.OUTLINED,
                type = positiveColor,
                size = ButtonSize.SMALL,
                onClick = onClickPositive
            )
        }
    }
}

@Composable
private fun WantedFallbackHorizontalButtons(
    positive: String?,
    negative: String?,
    positiveColor: ButtonType,
    negativeColor: ButtonType,
    modifier: Modifier = Modifier,
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    if (positive != null || negative != null) {
        Row(
            modifier = modifier
                .width(intrinsicSize = IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            negative?.let {
                WantedButton(
                    modifier = Modifier.weight(1f),
                    text = it,
                    variant = ButtonVariant.OUTLINED,
                    type = negativeColor,
                    size = ButtonSize.SMALL,
                    onClick = onClickNegative
                )
            }

            positive?.let {
                WantedButton(
                    modifier = Modifier.weight(1f),
                    text = it,
                    variant = ButtonVariant.OUTLINED,
                    type = positiveColor,
                    size = ButtonSize.SMALL,
                    onClick = onClickPositive
                )
            }
        }
    }
}

@Composable
private fun WantedFallbackVerticalButtons(
    positive: String?,
    negative: String?,
    positiveColor: ButtonType,
    negativeColor: ButtonType,
    modifier: Modifier = Modifier,
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    if (positive != null || negative != null) {
        Column(
            modifier = modifier
                .width(intrinsicSize = IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            positive?.let {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    variant = ButtonVariant.OUTLINED,
                    type = positiveColor,
                    size = ButtonSize.SMALL,
                    onClick = onClickPositive
                )
            }

            negative?.let {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    variant = ButtonVariant.OUTLINED,
                    type = negativeColor,
                    size = ButtonSize.SMALL,
                    onClick = onClickNegative
                )
            }
        }
    }
}

@Composable
private fun WantedFallbackLayout(
    modifier: Modifier,
    image: @Composable (() -> Unit)? = null,
    heading: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    button: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.padding(bottom = image?.let { 20.dp } ?: 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        image?.let {
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                image()
            }
        }

        WantedFallbackLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = image?.let { 0.dp } ?: 4.dp)
                .wrapContentHeight(),
            heading = heading,
            description = description,
            button = button
        )
    }
}

@Composable
private fun WantedFallbackLayout(
    modifier: Modifier,
    heading: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    button: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (heading != null || description != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                heading?.let {
                    ProvideTextStyle(
                        value = DesignSystemTheme.typography.heading2Bold.copy(
                            color = DesignSystemTheme.colors.labelNormal
                        )
                    ) {
                        heading()
                    }
                }

                description?.let {
                    ProvideTextStyle(
                        value = DesignSystemTheme.typography.body1ReadingRegular.copy(
                            color = DesignSystemTheme.colors.labelAlternative
                        )
                    ) {
                        description()
                    }
                }
            }
        }

        button?.let {
            button()
        }
    }
}

enum class WantedFallbackButtonVariant {
    Single,
    Horizontal,
    Vertical
}

@DevicePreviews
@Composable
private fun WantedFallbackViewPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedFallbackView(
                    modifier = Modifier,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    positive = "텍스트",
                    onClickPositive = {}

                )

                WantedFallbackView(
                    modifier = Modifier,
                    image = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(DesignSystemTheme.colors.labelDisable)
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.icon_normal_camera_fill),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    },
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    positive = "텍스트",
                    onClickPositive = {}

                )
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedFallbackViewHorizontalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                WantedFallbackView(
                    modifier = Modifier,
                    buttonVariant = WantedFallbackButtonVariant.Horizontal,
                    image = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(DesignSystemTheme.colors.labelDisable)
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.icon_normal_camera_fill),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    },
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    positive = "텍스트",
                    negative = "대체 액션",
                    onClickPositive = {}

                )
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedFallbackViewVerticalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedFallbackView(
                    modifier = Modifier,
                    buttonVariant = WantedFallbackButtonVariant.Vertical,
                    image = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(DesignSystemTheme.colors.labelDisable)
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.icon_normal_camera_fill),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    },
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    positive = "텍스트",
                    negative = "대체 액션",
                    onClickPositive = {}

                )
            }
        }
    }
}