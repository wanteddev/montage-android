package com.wanted.android.wanted.design.contents.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.BorderType
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.base.getBorderModifier
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedAvatar
 * 사용자, 회사, 학력 등 다양한 유형의 아바타를 표시하는 컴포넌트입니다.
 *
 * 이미지, 아이콘, 그룹 아바타, 알림 뱃지 등 다양한 스타일을 지원합니다.
 * 클릭 이벤트 및 플레이스 홀더 이미지를 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAvatar(
 *     modifier = Modifier,
 *     model = R.drawable.ic_avatar_placeholder_person,
 *     placeHolder = R.drawable.ic_avatar_placeholder_person,
 *     size = WantedAvatarSize.Medium,
 *     type = WantedAvatarType.Person,
 *     isDrawableRes = true,
 *     pushBadge = true,
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * @param type WantedAvatarType: 아바타의 유형(Person, Company, Academic)을 지정합니다.
 * @param modifier Modifier: 아바타의 크기, 외형, 배치를 조정하는 Modifier입니다.
 * @param size WantedAvatarSize: 아바타의 크기와 코너 반경을 결정합니다. 기본값은 Small입니다.
 * @param model Any?: 표시할 이미지 모델입니다 (URL 또는 Drawable ID).
 * @param placeHolder Int?: 로딩 실패 시 표시할 기본 이미지 리소스 ID입니다.
 * @param isDrawableRes Boolean: model이 Drawable 리소스 ID일 경우 true로 설정합니다.
 * @param isGroup Boolean: 그룹 아바타 스타일을 적용할지 여부를 설정합니다.
 * @param pushBadge Boolean: 아바타에 푸시 알림 뱃지를 표시할지 여부를 설정합니다.
 * @param borderColor Color: 아바타 외곽선의 색상입니다. 기본값은 배경색입니다.
 * @param alignment Alignment: 이미지의 정렬 방식입니다.
 * @param contentScale ContentScale: 이미지의 크기 조정 방식입니다.
 * @param onClick (() -> Unit)?: 아바타 클릭 시 호출될 콜백 함수입니다.
 *
 * @see WantedAvatarType
 * @see WantedAvatarSize
 */
@Composable
fun WantedAvatar(
    type: WantedAvatarType,
    modifier: Modifier = Modifier,
    size: WantedAvatarSize = WantedAvatarSize.Small,
    model: Any? = null,
    @DrawableRes placeHolder: Int? = null,
    isDrawableRes: Boolean = false,
    isGroup: Boolean = false,
    pushBadge: Boolean = false,
    borderColor: Color = DesignSystemTheme.colors.backgroundNormalNormal,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    onClick: (() -> Unit)? = null
) {
    when (type) {
        WantedAvatarType.Person -> {
            WantedAvatarPerson(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.icon_avatar_placeholder_person,
                size = size,
                isDrawableRes = isDrawableRes,
                isGroup = isGroup,
                borderColor = borderColor,
                pushBadge = pushBadge,
                alignment = alignment,
                contentScale = contentScale,
                onClick = onClick
            )
        }

        WantedAvatarType.Company -> {
            WantedAvatar(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.icon_avatar_placeholder_company,
                size = size,
                isDrawableRes = isDrawableRes,
                isGroup = isGroup,
                borderColor = borderColor,
                pushBadge = pushBadge,
                alignment = alignment,
                contentScale = contentScale,
                onClick = onClick
            )
        }

        WantedAvatarType.Academic -> {
            WantedAvatar(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.icon_avatar_placeholder_academic,
                size = size,
                isDrawableRes = isDrawableRes,
                isGroup = isGroup,
                borderColor = borderColor,
                pushBadge = pushBadge,
                alignment = alignment,
                contentScale = contentScale,
                onClick = onClick
            )
        }

    }
}

@Composable
internal fun WantedAvatarPerson(
    modifier: Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    size: WantedAvatarSize,
    isDrawableRes: Boolean = false,
    isGroup: Boolean = false,
    borderColor: Color = DesignSystemTheme.colors.lineNormalAlternative,
    pushBadge: Boolean = false,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    onClick: (() -> Unit)? = null
) {
    WantedAvatarLayout(
        modifier = modifier
            .getBorderModifier(
                size = size.size,
                isCircleShape = true,
                borderType = if (isGroup) BorderType.OutLine else BorderType.None,
                borderWidth = 2.dp,
                borderColor = borderColor
            )
            .size(size.size),
        content = {
            WantedAvatarContent(
                modifier = Modifier
                    .size(size.size)
                    .getBorderModifier(
                        size = size.size,
                        isCircleShape = true,
                        borderType = BorderType.InnerLine
                    ),
                model = model,
                placeHolder = placeHolder,
                isDrawableRes = isDrawableRes,
                alignment = alignment,
                contentScale = contentScale
            )
        },
        pushBadge = if (pushBadge) {
            {
                WantedPushBadge(size = size.badgeSize)
            }
        } else null,
        onClick = onClick
    )
}

@Composable
private fun WantedAvatar(
    modifier: Modifier = Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    size: WantedAvatarSize,
    isDrawableRes: Boolean = false,
    isGroup: Boolean = false,
    borderColor: Color = DesignSystemTheme.colors.lineNormalAlternative,
    pushBadge: Boolean,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    onClick: (() -> Unit)? = null
) {
    WantedAvatarLayout(
        modifier = modifier
            .size(size.size)
            .getBorderModifier(
                size = size.size,
                isCircleShape = false,
                cornerRadius = size.cornerRadius,
                borderType = if (isGroup) BorderType.OutLine else BorderType.None,
                borderWidth = 2.dp,
                borderColor = borderColor
            ),
        interactionShape = RoundedCornerShape(size.size / 4),
        content = {
            WantedAvatarContent(
                modifier = Modifier
                    .size(size.size)
                    .getBorderModifier(
                        size = size.size,
                        isCircleShape = false,
                        cornerRadius = size.cornerRadius,
                        borderType = BorderType.InnerLine
                    ),
                model = model,
                placeHolder = placeHolder,
                isDrawableRes = isDrawableRes,
                alignment = alignment,
                contentScale = contentScale
            )
        },
        pushBadge = if (pushBadge) {
            {
                WantedPushBadge(size = size.badgeSize)
            }
        } else null,
        onClick = onClick
    )
}


@Composable
private fun WantedAvatarLayout(
    modifier: Modifier = Modifier,
    interactionShape: Shape = CircleShape,
    content: @Composable BoxScope.() -> Unit,
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    onClick?.let {
        WantedTouchArea(
            modifier = Modifier,
            horizontalPadding = 8.dp,
            verticalPadding = 8.dp,
            shape = interactionShape,
            content = {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }

                pushBadge?.let {
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        pushBadge()
                    }
                }

            },
            onClick = { onClick() }
        )
    } ?: run {
        Box {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                content()
            }
            pushBadge?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    pushBadge()
                }
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedAvatarPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAvatar(
                    modifier = Modifier,
                    model = null,
                    placeHolder = R.drawable.icon_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    pushBadge = true,
                    onClick = {}
                )
                WantedAvatar(
                    modifier = Modifier,
                    model = R.drawable.icon_avatar_placeholder_person,
                    placeHolder = R.drawable.icon_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    onClick = {}
                )


                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    pushBadge = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    pushBadge = true,
                    onClick = {}
                )
            }
        }
    }
}