package com.wanted.android.wanted.design.contents.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.contents.contentbadge.WantedContentBadge
import com.wanted.android.wanted.design.loading.skeleton.WantedSkeletonRectangle
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedCard
 * 기본 카드 컴포넌트로, 로딩 상태(isLoading)에 따라 Skeleton 또는 실제 콘텐츠를 렌더링합니다.
 *
 * 썸네일, 오버레이 캡션, 타이틀, 캡션, 서브 캡션, 추가 캡션, 상/하단 커스텀 콘텐츠를 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedCard(
 *     title = "제목",
 *     caption = "캡션",
 *     overlayCaption = "Overlay",
 *     overlayToggleIcon = {
 *         Icon(painter = painterResource(id = R.drawable.icon), contentDescription = null)
 *     },
 *     onClick = { /* 클릭 이벤트 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 레이아웃 및 스타일을 지정하는 Modifier입니다.
 * @param thumbnail (@Composable () -> Unit): 썸네일 이미지 영역입니다.
 * @param overlayCaption String: 썸네일 위에 오버레이로 표시할 텍스트입니다.
 * @param title String: 카드 타이틀 텍스트입니다.
 * @param caption String: 보조 캡션 텍스트입니다.
 * @param subCaption String: 추가 보조 캡션 텍스트입니다.
 * @param extraCaption String: 하단 추가 설명 텍스트입니다.
 * @param isLoading Boolean: 로딩 상태 여부입니다. true이면 skeleton UI가 렌더링됩니다.
 * @param cardDefault WantedCardDefault: skeleton 모드에서 사용할 설정값입니다.
 * @param overlayToggleIcon (@Composable () -> Unit)?: 썸네일 오버레이에 포함될 토글 아이콘입니다.
 * @param topContent (@Composable () -> Unit)?: 카드 상단 타이틀 위에 추가 표시할 컴포넌트입니다.
 * @param bottomContent (@Composable () -> Unit)?: 카드 하단에 추가 표시할 컴포넌트입니다.
 * @param onClick () -> Unit: 카드 전체 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedCard(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit = {},
    overlayCaption: String = "",
    title: String = "",
    caption: String = "",
    subCaption: String = "",
    extraCaption: String = "",
    isLoading: Boolean = false,
    cardDefault: WantedCardDefault = WantedCardDefaults.getDefault(),
    overlayToggleIcon: @Composable (() -> Unit)? = null,
    topContent: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    if (isLoading) {
        WantedCardSkeleton(
            modifier = modifier,
            topContent = cardDefault.topContentSkeleton,
            caption = cardDefault.captionSkeleton,
            extraCaption = cardDefault.extraCaptionSkeleton,
            bottomContent = cardDefault.bottomContentSkeleton,
            ratio = cardDefault.ratio
        )
    } else {
        WantedTouchArea(
            content = {
                WantedCardLayout(
                    modifier = modifier,
                    thumbnail = thumbnail,
                    thumbnailOverlay = if (overlayCaption.isNotEmpty() || overlayToggleIcon != null) {
                        {
                            WantedThumbnailOverly(
                                modifier = Modifier,
                                title = overlayCaption,
                                toggleIcon = overlayToggleIcon?.let {
                                    {
                                        overlayToggleIcon()
                                    }
                                }
                            )
                        }
                    } else null,
                    description = {
                        WantedCardDescription(
                            modifier = Modifier,
                            title = title,
                            caption = caption,
                            subCaption = subCaption,
                            extraCaption = extraCaption,
                            bottomContent = bottomContent,
                            topContent = topContent
                        )
                    }
                )
            },
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            enabledInnerTouch = true,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            ),
            onClick = onClick
        )
    }
}

@Composable
private fun WantedCardSkeleton(
    modifier: Modifier = Modifier,
    topContent: Boolean = false,
    caption: Boolean = true,
    extraCaption: Boolean = true,
    bottomContent: Boolean = false,
    ratio: Float = 4 / 3f
) {
    WantedCardLayout(
        modifier = modifier,
        thumbnail = {
            WantedSkeletonRectangle(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio)
            )
        },
        thumbnailOverlay = null,
        description = {
            WantedCardDescriptionSkeleton(
                modifier = Modifier,
                caption = caption,
                extraCaption = extraCaption,
                bottomContent = bottomContent,
                topContent = topContent
            )
        }
    )
}

@Composable
private fun WantedCardLayout(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit = { },
    thumbnailOverlay: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
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
            thumbnailOverlay?.invoke()
        }

        description?.invoke()
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
                WantedCard(
                    modifier = Modifier.width(152.dp),
                    thumbnail = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4 / 3f)
                        )
                    },
                    title = "제목",
                    caption = "캡션",
                    overlayCaption = "overlayCaption"
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    overlayCaption = "overlayCaption",
                    overlayToggleIcon = {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4 / 3f),
                            model = "",
                            contentDescription = ""
                        )
                    }
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    bottomContent = {
                        WantedContentBadge(text = "텍스트")
                    },
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
                WantedCard(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    overlayCaption = "overlayCaption"
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    overlayCaption = "overlayCaption",
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true
                    ),
                    overlayToggleIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_normal_bookmark_fill),
                            tint = DesignSystemTheme.colors.primaryNormal,
                            contentDescription = ""
                        )
                    }
                )

                WantedCard(
                    modifier = Modifier.width(152.dp),
                    isLoading = true,
                    title = "제목",
                    caption = "캡션",
                    subCaption = "추가 캡션",
                    cardDefault = WantedCardDefault(
                        topContentSkeleton = true,
                        bottomContentSkeleton = true
                    ),
                    topContent = {
                        WantedContentBadge(text = "텍스트")
                    }
                )
            }
        }
    }
}