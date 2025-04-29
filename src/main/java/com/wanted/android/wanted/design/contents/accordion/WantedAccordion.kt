package com.wanted.android.wanted.design.contents.accordion

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.contents.accordion.WantedAccordionContract.VerticalPadding
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_8
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 아코디언 형태로 펼치기 및 접기가 가능한 컴포넌트입니다.
 *
 * 사용자는 제목(Title)을 눌러 추가적인 설명(Description) 및 콘텐츠(Content)를 확장하거나 축소할 수 있습니다.
 * 주로 리스트, FAQ, 설정 화면 등 다양한 UI 구성에서 유용하게 사용할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAccordion(
 *     modifier = Modifier,
 *     title = "제목",
 *     description = "상세 설명",
 *     isExpanded = false,
 *     onChangeExpanded = { expanded -> },
 *     content = {
 *         Text("추가 콘텐츠")
 *     }
 * )
 * ```
 *
 * @param modifier Modifier: 아코디언의 레이아웃 및 스타일을 조정할 때 사용합니다.
 * @param title String: 아코디언 헤더에 표시될 텍스트입니다.
 * @param titleMaxLine Int: 제목 텍스트의 최대 줄 수를 지정합니다. 기본값은 Int.MAX_VALUE입니다.
 * @param description String?: 제목 하단에 표시될 부가 설명 텍스트입니다. 선택 사항입니다.
 * @param titleStyle TextStyle: 제목에 적용할 텍스트 스타일입니다.
 * @param descriptionStyle TextStyle: 설명에 적용할 텍스트 스타일입니다.
 * @param isExpanded Boolean: 현재 아코디언이 확장(expanded) 상태인지 여부를 지정합니다.
 * @param fillWidth Boolean: 내용이 가로 전체를 채울지 여부를 결정합니다.
 * @param divider Boolean: 하단에 Divider를 표시할지 여부를 설정합니다.
 * @param verticalPadding VerticalPadding: 헤더 영역의 수직 패딩을 설정합니다.
 * @param content @Composable (() -> Unit)?: 확장 시 표시할 추가적인 Composable 콘텐츠입니다. 선택 사항입니다.
 * @param onChangeExpanded (Boolean) -> Unit: 확장/축소 상태가 변경될 때 호출되는 콜백입니다.
 * @param leadingIcon @Composable (() -> Unit)?: 제목 왼쪽에 표시할 아이콘입니다. 선택 사항입니다.
 * @param trail @Composable () -> Unit: 제목 오른쪽에 표시할 트레일 아이콘입니다. 기본값은 확장/축소를 나타내는 화살표입니다.
 *
 * @return Unit
 *
 * @see WantedAccordionHeader
 * @see WantedAccordionContract.VerticalPadding
 * @see WantedAccordionTrailArrowIcon
 */
@Composable
fun WantedAccordion(
    modifier: Modifier = Modifier,
    title: String,
    titleMaxLine: Int = Int.MAX_VALUE,
    description: String? = null,
    titleStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_normal,
        style = DesignSystemTheme.typography.body2Bold
    ),
    descriptionStyle: TextStyle = WantedTextStyle(
        colorRes = R.color.label_neutral,
        style = DesignSystemTheme.typography.label1Regular
    ),
    isExpanded: Boolean = false,
    fillWidth: Boolean = false,
    divider: Boolean = true,
    verticalPadding: VerticalPadding = VerticalPadding.Padding12,
    content: @Composable (() -> Unit)? = null,
    onChangeExpanded: (Boolean) -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trail: @Composable () -> Unit = {
        WantedAccordionTrailArrowIcon(
            isExpanded = isExpanded,
            tint = titleStyle.color
        )
    }
) {
    AccordionLayout(
        modifier = modifier.animateContentSize(),
        isExpanded = isExpanded,
        divider = divider,
        verticalPadding = verticalPadding,
        header = {
            WantedAccordionHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalPadding = verticalPadding,
                title = title,
                maxLine = titleMaxLine,
                style = titleStyle,
                fillWidth = fillWidth,
                leadingIcon = leadingIcon,
                trailIcon = trail,
                onClick = {
                    onChangeExpanded(!isExpanded)
                }
            )
        },
        description = description?.let {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (fillWidth) 20.dp else 0.dp),
                    text = description,
                    style = descriptionStyle
                )
            }
        },
        content = content?.let {
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (fillWidth) 20.dp else 0.dp)
                ) {
                    content()
                }
            }
        }
    )
}

@Composable
private fun AccordionLayout(
    modifier: Modifier = Modifier,
    divider: Boolean,
    isExpanded: Boolean,
    verticalPadding: VerticalPadding,
    header: @Composable () -> Unit,
    description: @Composable (() -> Unit)?,
    content: @Composable (() -> Unit)?,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            header()

            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = when (verticalPadding) {
                                VerticalPadding.Padding16 -> 0.dp
                                VerticalPadding.Padding12 -> 4.dp
                                VerticalPadding.Padding8 -> 8.dp
                            }
                        )
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    description?.invoke()

                    content?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            content()
                        }
                    }
                }
            }
        }

        if (divider) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = colorResource(R.color.line_normal_alternative)
            )
        }
    }
}

@DevicePreviews
@Composable
private fun WantedAccordionPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAccordion(
                    modifier = Modifier,
                    isExpanded = true,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    content = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(colorResource(R.color.accent_background_violet).copy(OPACITY_8))
                        )
                    },
                    onChangeExpanded = {}
                )

                WantedAccordion(
                    modifier = Modifier,
                    isExpanded = true,
                    fillWidth = true,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    content = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(colorResource(R.color.accent_background_violet).copy(OPACITY_8))
                        )
                    },
                    onChangeExpanded = {}
                )

                WantedAccordion(
                    modifier = Modifier,
                    title = "제목",
                    description = "제목에 대한 상세 내용을 입력해주세요.\n긴 컨텐츠라면 접은 상태를 기본 값으로 사용하세요.",
                    fillWidth = true,
                    content = {},
                    onChangeExpanded = {}
                )
            }
        }
    }
}