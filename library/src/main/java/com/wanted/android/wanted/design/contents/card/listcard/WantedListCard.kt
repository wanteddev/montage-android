package com.wanted.android.wanted.design.contents.card.listcard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.contents.card.WantedCardDefault
import com.wanted.android.wanted.design.contents.card.WantedCardDefaults
import com.wanted.android.wanted.design.contents.card.WantedCardDescription
import com.wanted.android.wanted.design.contents.card.WantedCardDescriptionSkeleton
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.input.input.control.CheckBoxSize
import com.wanted.android.wanted.design.input.input.control.WantedCheckBox
import com.wanted.android.wanted.design.loading.skeleton.WantedSkeletonRectangle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_8

/**
 * WantedListCard
 * 썸네일과 설명 영역을 좌우로 배치한 가로형 카드 컴포넌트입니다.
 *
 * 로딩 상태(isLoading)에 따라 Skeleton 또는 실제 콘텐츠를 렌더링합니다.
 * 상단/하단 커스텀 콘텐츠와 좌우 콘텐츠 Slot을 제공합니다.
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
 *     leadingContent = { WantedCheckBox(...) },
 *     trailingContent = { Icon(...) },
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 컴포넌트의 레이아웃과 스타일을 조정합니다.
 * @param title String: 카드의 주요 제목입니다.
 * @param caption String: 제목 아래에 표시될 보조 설명입니다.
 * @param extraCaption String: 추가적인 설명 텍스트입니다.
 * @param isLoading Boolean: true일 경우 스켈레톤 UI로 렌더링됩니다.
 * @param cardDefault WantedCardDefault: 스켈레톤 모드 시 항목별 표시 여부를 지정하는 설정 객체입니다.
 * @param thumbnail (@Composable () -> Unit)?: 썸네일 이미지 영역입니다. null이면 기본 배경으로 표시됩니다.
 * @param topContent (@Composable () -> Unit)?: 설명 위에 표시될 추가 콘텐츠입니다.
 * @param bottomContent (@Composable () -> Unit)?: 설명 아래에 표시될 추가 콘텐츠입니다.
 * @param leadingContent (@Composable () -> Unit)?: 썸네일 왼쪽에 표시될 콘텐츠입니다 (예: 체크박스).
 * @param trailingContent (@Composable () -> Unit)?: 설명 오른쪽에 표시될 콘텐츠입니다 (예: 아이콘 버튼).
 * @param onClick () -> Unit: 카드 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCard(
    modifier: Modifier = Modifier,
    title: String = "",
    caption: String = "",
    extraCaption: String = "",
    isLoading: Boolean = false,
    cardDefault: WantedCardDefault = WantedCardDefaults.getDefault(),
    thumbnail: (@Composable () -> Unit)? = null,
    topContent: (@Composable () -> Unit)? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    if (isLoading) {
        WantedCardHorizontalSkeleton(
            modifier = modifier,
            thumbnail = thumbnail,
            topContent = cardDefault.topContentSkeleton,
            caption = cardDefault.captionSkeleton,
            extraCaption = cardDefault.extraCaptionSkeleton,
            bottomContent = cardDefault.bottomContentSkeleton,
            leadingContent = leadingContent,
            trailingContent = trailingContent
        )
    } else {
        WantedTouchArea(
            content = {
                WantedCardHorizontalLayout(
                    modifier = modifier,
                    thumbnail = thumbnail ?: {
                        Box(
                            modifier = Modifier
                                .height(64.dp)
                                .aspectRatio(cardDefault.ratio)
                                .background(
                                    color = DesignSystemTheme.colors.fillNormal
                                        .copy(OPACITY_8)
                                ),
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
                    leadingContent = leadingContent,
                    trailingContent = trailingContent
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
    thumbnail: (@Composable () -> Unit)?,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false,
    ratio: Float = 4 / 3f,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    WantedCardHorizontalLayout(
        modifier = modifier,
        thumbnail = thumbnail ?: run {
            {
                WantedSkeletonRectangle(
                    Modifier
                        .height(64.dp)
                        .aspectRatio(ratio)
                )
            }
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
        leadingContent = if (leadingContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
        trailingContent = if (trailingContent != null) {
            {
                Spacer(modifier = Modifier.size(24.dp))
            }
        } else null,
    )
}

@Composable
private fun WantedCardHorizontalLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit = { },
    description: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leadingContent?.invoke()

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = DesignSystemTheme.colors.fillAlternative)
                .border(
                    width = 1.dp,
                    color = DesignSystemTheme.colors.lineSolidAlternative,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.TopStart
        ) {
            thumbnail()
        }

        description?.let {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                description()
            }
        }

        trailingContent?.invoke()
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
                    leadingContent = {
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
                    trailingContent = {
                        WantedTouchArea(
                            modifier = Modifier.size(24.dp),
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_normal_bookmark),
                                    contentDescription = "",
                                    tint = DesignSystemTheme.colors.staticWhite
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
                    leadingContent = {}
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
                    trailingContent = {}
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
                    leadingContent = {},
                    trailingContent = {}
                )

            }
        }
    }
}

