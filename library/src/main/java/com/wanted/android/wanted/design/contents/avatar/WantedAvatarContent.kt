package com.wanted.android.wanted.design.contents.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.android.wanted.design.theme.DesignSystemTheme

// 기본 placeholder 글리프가 아바타 한 변에서 차지하는 비율.
// 시안의 글리프 가시 영역은 타입마다 다르지만(Person 49.44%×51.52% / Company 53.33%×55.55% /
// Academy 59.77%×51.67%), 각 아이콘 리소스가 24dp 뷰포트 안에서 갖는 자체 여백
// (74.17%×77.28% / 79.99%×83.33% / 89.65%×77.50%)으로 나누면 여섯 값이 모두 0.6667 로 수렴한다.
// 사이즈(XSmall~XLarge)에 따라서도 변하지 않으므로 타입·사이즈 분기 없이 이 값 하나를 쓴다.
private const val DEFAULT_PLACEHOLDER_ICON_SIZE_RATIO = 2f / 3f

@Composable
internal fun WantedAvatarContent(
    modifier: Modifier = Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    @DrawableRes defaultPlaceHolder: Int? = null,
    isDrawableRes: Boolean = false,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    model?.let {
        if (isDrawableRes && model is Int) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = model),
                contentDescription = contentDescription,
                tint = Color.Unspecified
            )
        } else {
            GlideImage(
                modifier = modifier,
                model = model,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                // Glide가 placeholder 컴포저블을 `Box(modifier = 여기 넘긴 modifier)` 안에서 그리므로
                // 아바타 크기·shape clip은 이미 적용돼 있다. 내부는 fillMaxSize로 그 박스를 채운다.
                loading = placeholder {
                    WantedAvatarPlaceholder(
                        placeHolder = placeHolder,
                        defaultPlaceHolder = defaultPlaceHolder
                    )
                },
                failure = placeholder {
                    WantedAvatarPlaceholder(
                        placeHolder = placeHolder,
                        defaultPlaceHolder = defaultPlaceHolder
                    )
                },
            )
        }
    } ?: run {
        WantedAvatarPlaceholder(
            modifier = modifier,
            placeHolder = placeHolder,
            defaultPlaceHolder = defaultPlaceHolder,
            contentDescription = contentDescription
        )
    }
}

// 이미지가 없거나 로딩에 실패했을 때 노출되는 영역.
// 호출부가 placeHolder 를 넘겼으면 그 리소스를 아바타 전체 크기에 원본 색 그대로 그린다 —
// 어떤 그림을 어떻게 채울지는 호출부가 정한 것이므로 손대지 않는다.
// placeHolder 가 null 일 때만 디자인 시스템 기본 상태로 3레이어를 그린다:
// ① backgroundNeutralPrimary 배경 ② surfaceNeutralStrong 면(아이콘 실루엣만큼 뚫림) ③ staticWhite 28% 아이콘.
// 면과 아이콘이 겹치면 아이콘 자리가 면 색에 더해져 목표 색보다 밝아지므로 면에서 아이콘을 반드시 뺀다.
// 아이콘 리소스를 아바타 전체 크기로 그리면 글리프가 아바타를 꽉 채우므로 반드시 축소한다.
@Composable
private fun WantedAvatarPlaceholder(
    modifier: Modifier = Modifier,
    @DrawableRes placeHolder: Int?,
    @DrawableRes defaultPlaceHolder: Int?,
    contentDescription: String? = null
) {
    placeHolder?.let {
        Icon(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(id = placeHolder),
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )
    } ?: run {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colors.backgroundNeutralPrimary),
            contentAlignment = Alignment.Center
        ) {
            // Offscreen 이 없으면 DstOut 이 면뿐 아니라 아래 배경까지 뚫는다. background 는 레이어 안쪽에서 그려야 한다.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .background(DesignSystemTheme.colors.surfaceNeutralStrong),
                contentAlignment = Alignment.Center
            ) {
                defaultPlaceHolder?.let {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(DEFAULT_PLACEHOLDER_ICON_SIZE_RATIO)
                            .knockOut(),
                        painter = painterResource(id = defaultPlaceHolder),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
            defaultPlaceHolder?.let {
                Icon(
                    modifier = Modifier.fillMaxSize(DEFAULT_PLACEHOLDER_ICON_SIZE_RATIO),
                    painter = painterResource(id = defaultPlaceHolder),
                    contentDescription = contentDescription,
                    tint = DesignSystemTheme.colorsOpacity.staticWhiteOpacity28
                )
            }
        }
    }
}

// 콘텐츠의 알파만큼 이미 그려진 픽셀을 지운다(destination-out). 색은 무관하고 알파만 쓴다.
private fun Modifier.knockOut(): Modifier = drawWithContent {
    val paint = Paint().apply { blendMode = BlendMode.DstOut }
    drawIntoCanvas { canvas ->
        canvas.saveLayer(size.toRect(), paint)
        drawContent()
        canvas.restore()
    }
}
