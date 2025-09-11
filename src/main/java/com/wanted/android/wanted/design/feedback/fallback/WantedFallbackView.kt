package com.wanted.android.wanted.design.feedback.fallback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 이미지, 제목, 설명, 버튼 등을 조합하여 비어 있는 상태를 안내하는 컴포저블입니다.
 *
 * 주로 데이터가 없거나 결과가 없을 때 사용자에게 피드백을 제공하는 용도로 사용됩니다.
 * 이미지, 제목, 설명, 버튼을 선택적으로 구성할 수 있으며, 버튼 클릭 시 콜백을 전달할 수 있습니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedFallbackView(
 *     heading = "데이터가 없습니다.",
 *     description = "새로운 데이터를 추가해보세요.",
 *     button = "추가하기",
 *     onClick = { /* 버튼 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
 * @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
 * @param button String?: 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param image (() -> Unit)?: 중앙에 표시될 이미지 컴포저블입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedFallbackView(
    modifier: Modifier = Modifier,
    heading: String? = null,
    description: String? = null,
    button: String? = null,
    image: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
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
        button = button?.let {
            {
                WantedButton(
                    text = it,
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.ASSISTIVE,
                    size = ButtonSize.LARGE,
                    onClick = onClick
                )
            }
        }
    )
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
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        heading?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_normal,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            ) {
                heading()
            }
        }

        description?.let {
            ProvideTextStyle(
                value = WantedTextStyle(
                    colorRes = R.color.label_alternative,
                    style = DesignSystemTheme.typography.body1ReadingRegular
                )
            ) {
                description()
            }
        }

        button?.let {
            Box(modifier = Modifier.padding(top = 12.dp)) {
                button()
            }
        }
    }
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
                    button = "텍스트",
                    onClick = {}

                )

                WantedFallbackView(
                    modifier = Modifier,
                    image = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(colorResource(id = R.color.label_disable))
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.icon_normal_camera_fill),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    },
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                        "설명은 최대 두 줄로 작성해요.",
                    button = "텍스트",
                    onClick = {}

                )
            }
        }
    }
}