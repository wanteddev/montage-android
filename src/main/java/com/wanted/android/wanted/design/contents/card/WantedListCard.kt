package com.wanted.android.wanted.design.contents.card

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.control.WantedCheckBox
import com.wanted.android.wanted.design.loading.skeleton.WantedSkeletonRectangle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 가로형 카드 컴포저블입니다.
 *
 * 썸네일과 설명 영역을 좌우로 배치한 형태의 카드입니다.
 * 로딩 상태에서는 Skeleton UI가 나타나며, 상단/하단 커스텀 콘텐츠와 좌우 콘텐츠 Slot을 제공합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedListCard(
 *     modifier = Modifier.fillMaxWidth(),
 *     title = "제목",
 *     caption = "캡션",
 *     extraCaption = "추가 설명",
 *     topContent = { WantedContentBadge(text = "상단") },
 *     bottomContent = { WantedContentBadge(text = "하단") },
 *     leftContent = { WantedCheckBox(...) },
 *     rightContent = { Icon(...) },
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트의 레이아웃과 스타일을 조정합니다.
 * @param thumbnail Any?: 썸네일 이미지 리소스 또는 URL입니다. null이면 표시되지 않습니다.
 * @param title String: 카드의 주요 제목입니다.
 * @param caption String: 제목 아래에 표시될 보조 설명입니다.
 * @param extraCaption String: 추가적인 설명 텍스트입니다.
 * @param isLoading Boolean: true일 경우 스켈레톤 UI로 렌더링됩니다.
 * @param cardDefault WantedCardDefault: 스켈레톤 모드 시 항목별 표시 여부를 지정하는 설정 객체입니다.
 * @param topContent @Composable (() -> Unit)?: 설명 위에 표시될 추가 콘텐츠입니다.
 * @param bottomContent @Composable (() -> Unit)?: 설명 아래에 표시될 추가 콘텐츠입니다.
 * @param leftContent @Composable (() -> Unit)?: 썸네일 왼쪽에 표시될 콘텐츠입니다. (예: 체크박스)
 * @param rightContent @Composable (() -> Unit)?: 설명 오른쪽에 표시될 콘텐츠입니다. (예: 아이콘 버튼)
 * @param onClick () -> Unit: 카드 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCard(
    modifier: Modifier = Modifier,
    thumbnail: Any? = null,
    title: String = "",
    caption: String = "",
    extraCaption: String = "",
    isLoading: Boolean = false,
    cardDefault: WantedCardDefault = WantedCardDefaults.getDefault(),
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    if (isLoading) {
        WantedCardHorizontalSkeleton(
            modifier = modifier,
            topContent = cardDefault.topContentSkeleton,
            caption = cardDefault.captionSkeleton,
            extraCaption = cardDefault.extraCaptionSkeleton,
            bottomContent = cardDefault.bottomContentSkeleton,
            leftContent = leftContent,
            rightContent = rightContent
        )
    } else {
        WantedTouchArea(
            content = {
                WantedCardHorizontalLayout(
                    modifier = modifier,
                    thumbnail = { width: Dp, height: Dp ->
                        GlideImage(
                            modifier = Modifier.size(width, height),
                            model = thumbnail,
                            contentDescription = ""
                        )
                    },
                    description = {
                        WantedCardDescription(
                            modifier = Modifier,
                            maxLines = 1,
                            title = title,
                            caption = caption,
                            subCaption = extraCaption,
                            bottomContent = bottomContent,
                            topContent = topContent
                        )
                    },
                    leftContent = leftContent,
                    rightContent = rightContent
                )
            },
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            enabledInnerTouch = true,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 12.dp,
                bottomStart = 20.dp,
                bottomEnd = 12.dp
            ),
            onClick = onClick
        )
    }
}


@Composable
private fun WantedCardHorizontalSkeleton(
    modifier: Modifier = Modifier,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    WantedCardHorizontalLayout(
        modifier = modifier,
        thumbnail = { width: Dp, height: Dp ->
            WantedSkeletonRectangle(Modifier.size(width, height))
        },
        description = {
            WantedCardDescriptionSkeleton(
                modifier = Modifier,
                caption = caption,
                extraCaption = extraCaption,
                bottomContent = bottomContent,
                topContent = topContent
            )
        },
        leftContent = if (leftContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
        rightContent = if (rightContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun WantedCardHorizontalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable (width: Dp, height: Dp) -> Unit = { _, _ -> },
    description: @Composable (() -> Unit)? = null,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftContent?.invoke()
        BoxWithConstraints(
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(4 / 3f)
                .clip(RoundedCornerShape(12.dp))
                .background(color = colorResource(id = R.color.fill_alternative))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.line_solid_alternative),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.TopStart
        ) {
            thumbnail(maxWidth, maxHeight)
        }

        description?.let {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                description()
            }
        }

        rightContent?.invoke()
    }
}


@DevicePreviews
@Composable
private fun WantedCardPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션"
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {
                        WantedCheckBox(
                            modifier = Modifier,
                            size = CheckBoxSize.Normal,
                            onCheckedChange = {}
                        )
                    }
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    rightContent = {
                        WantedTouchArea(
                            modifier = Modifier.size(24.dp),
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.button_bookmark_line_svg),
                                    contentDescription = ""
                                )
                            },
                            onClick = {}
                        )
                    }

                )
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedCardSkeletonPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        bottomContentSkeleton = true
                    ),
                    extraCaption = "추가 캡션"
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {}
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    rightContent = {}
                )

                WantedListCard(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .fillMaxWidth(),
                    title = "제목",
                    caption = "캡션",
                    extraCaption = "추가 캡션",
                    isLoading = true,
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    },
                    leftContent = {},
                    rightContent = {}
                )

            }
        }
    }
}

