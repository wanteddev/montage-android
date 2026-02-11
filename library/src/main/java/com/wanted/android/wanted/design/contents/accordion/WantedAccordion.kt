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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.accordion.WantedAccordionDefaults.VerticalPadding
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_8

/**
 * 아코디언 형태로 확장/축소가 가능한 컴포넌트입니다.
 *
 * 사용자는 제목 영역을 클릭하여 추가 설명과 콘텐츠를 확장하거나 축소할 수 있습니다.
 * FAQ, 설정 메뉴, 리스트 등 다양하게 활용할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAccordion(
 *     title = "제목",
 *     modifier = Modifier,
 *     description = "설명",
 *     isExpanded = false,
 *     onChangeExpanded = { expanded -> },
 *     content = {
 *         Text("확장 콘텐츠")
 *     }
 * )
 * ```
 *
 * @param title String: 아코디언 헤더에 표시될 제목 텍스트입니다.
 * @param modifier Modifier: 레이아웃 외형 및 동작을 위한 Modifier입니다.
 * @param titleMaxLine Int: 제목 텍스트의 최대 줄 수입니다. 기본값은 Int.MAX_VALUE입니다.
 * @param description String?: 제목 하단에 표시될 부가 설명입니다. 선택 사항입니다.
 * @param titleStyle TextStyle: 제목에 적용할 텍스트 스타일입니다.
 * @param descriptionStyle TextStyle: 설명 텍스트에 적용할 스타일입니다.
 * @param isExpanded Boolean: 현재 아코디언이 확장 상태인지 여부를 나타냅니다.
 * @param fillWidth Boolean: 콘텐츠의 가로 너비를 전체로 채울지 여부입니다.
 * @param divider Boolean: 하단 Divider 표시 여부입니다.
 * @param verticalPadding VerticalPadding: 헤더 영역의 수직 패딩 값입니다.
 * @param leadingIcon (@Composable () -> Unit)?: 제목 좌측에 위치할 아이콘입니다. 선택 사항입니다.
 * @param trail (@Composable () -> Unit): 제목 우측에 위치할 아이콘입니다. 기본값은 확장/축소 화살표입니다.
 * @param content (@Composable () -> Unit)?: 확장 상태일 때 표시될 추가 콘텐츠입니다. 선택 사항입니다.
 * @param onChangeExpanded (Boolean) -> Unit: 확장 상태 변경 시 호출되는 콜백입니다.
 *
 * @see WantedAccordionHeader
 * @see WantedAccordionDefaults.VerticalPadding
 * @see WantedAccordionTrailArrowIcon
 */
@Composable
fun WantedAccordion(
    title: String,
    modifier: Modifier = Modifier,
    titleMaxLine: Int = Int.MAX_VALUE,
    description: String? = null,
    titleStyle: TextStyle = DesignSystemTheme.typography.body2Bold.copy(
        color = DesignSystemTheme.colors.labelNormal
    ),
    descriptionStyle: TextStyle = DesignSystemTheme.typography.label1Regular.copy(
        color = DesignSystemTheme.colors.labelNeutral
    ),
    isExpanded: Boolean = false,
    fillWidth: Boolean = false,
    divider: Boolean = true,
    verticalPadding: VerticalPadding = VerticalPadding.Padding12,
    leadingIcon: (@Composable () -> Unit)? = null,
    trail: @Composable () -> Unit = {
        WantedAccordionTrailArrowIcon(
            isExpanded = isExpanded,
            tint = titleStyle.color
        )
    },
    content: @Composable (() -> Unit)? = null,
    onChangeExpanded: (Boolean) -> Unit
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
                color = DesignSystemTheme.colors.lineNormalAlternative
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
                                .background(
                                    color = DesignSystemTheme.colors.accentBackgroundViolet
                                        .copy(OPACITY_8)
                                )
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
                                .background(
                                    color = DesignSystemTheme.colors.accentBackgroundViolet
                                        .copy(OPACITY_8)
                                )
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