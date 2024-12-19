package com.wanted.android.wanted.design.avatar

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.badge.WantedPushBadge
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5

/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14852-40148&m=dev
 * 설명 : https://www.figma.com/design/MK6KmtXBxX7ZkoQXfD9MFH/%EA%B0%9C%EC%84%A0%3A-Components?t=dVJqzo6d9uRelotZ-0
 */
@Composable
fun WantedAvatar(
    modifier: Modifier,
    model: Any? = null,
    @DrawableRes placeHolder: Int? = null,
    size: WantedAvatarSize,
    type: WantedAvatarType,
    isIcon: Boolean = false,
    isDrawableRes: Boolean = false,
    isGroup: Boolean = false,
    boarderColor: Color = colorResource(id = R.color.background_normal_normal),
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    when (type) {
        WantedAvatarType.Person -> {
            WantedAvatarPerson(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.ic_avatar_placeholder_person,
                size = size.size,
                isDrawableRes = isDrawableRes,
                isIcon = isIcon,
                isGroup = isGroup,
                boarderColor = boarderColor,
                pushBadge = pushBadge,
                onClick = onClick
            )
        }

        WantedAvatarType.Company -> {
            WantedAvatar(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.ic_avatar_placeholder_company,
                size = size,
                isDrawableRes = isDrawableRes,
                isIcon = isIcon,
                isGroup = isGroup,
                boarderColor = boarderColor,
                pushBadge = pushBadge,
                onClick = onClick
            )
        }

        WantedAvatarType.Academic -> {
            WantedAvatar(
                modifier = modifier,
                model = model,
                placeHolder = placeHolder ?: R.drawable.ic_avatar_placeholder_academic,
                size = size,
                isDrawableRes = isDrawableRes,
                isIcon = isIcon,
                isGroup = isGroup,
                boarderColor = boarderColor,
                pushBadge = pushBadge,
                onClick = onClick
            )
        }

    }
}

@Composable
fun WantedAvatarPerson(
    modifier: Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    size: Dp,
    isDrawableRes: Boolean = false,
    isGroup: Boolean = false,
    isIcon: Boolean = false,
    boarderColor: Color = colorResource(id = R.color.background_normal_normal),
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedAvatarLayout(
        modifier = modifier
            .getBoarderModifier(
                size = size,
                isCircleShape = true,
                boarderType = if (isGroup) BoarderType.OutLine else BoarderType.None,
                boarderWidth = 2.dp,
                boarderColor = boarderColor
            )
            .size(size),
        content = {
            WantedAvatarContent(
                modifier = Modifier
                    .size(size)
                    .getBoarderModifier(
                        size = size,
                        isCircleShape = true,
                        boarderType = if (isIcon) BoarderType.InnerLine else BoarderType.None
                    ),
                model = model,
                placeHolder = placeHolder,
                isDrawableRes = isDrawableRes
            )
        },
        pushBadge = pushBadge,
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
    isIcon: Boolean = false,
    isGroup: Boolean = false,
    boarderColor: Color = colorResource(id = R.color.background_normal_normal),
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    WantedAvatarLayout(
        modifier = modifier
            .size(size.size)
            .getBoarderModifier(
                size = size.size,
                isCircleShape = false,
                cornerRadius = size.cornerRadius,
                boarderType = if (isGroup) BoarderType.OutLine else BoarderType.None,
                boarderWidth = 2.dp,
                boarderColor = boarderColor
            ),
        content = {
            WantedAvatarContent(
                modifier = Modifier
                    .size(size.size)
                    .getBoarderModifier(
                        size = size.size,
                        isCircleShape = false,
                        cornerRadius = size.cornerRadius,
                        boarderType = if (isIcon) BoarderType.InnerLine else BoarderType.None
                    ),
                model = model,
                placeHolder = placeHolder,
                isDrawableRes = isDrawableRes
            )
        },
        pushBadge = pushBadge,
        onClick = onClick
    )
}


@Composable
fun WantedAvatarLayout(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    pushBadge: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    onClick?.let {
        WantedTouchArea(
            modifier = Modifier,
            horizontalPadding = 8.dp,
            verticalPadding = 8.dp,
            shape = CircleShape,
            content = {
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
                            .offset(10.dp, (-10).dp)
                    ) {
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
                        .offset(10.dp, (-10).dp)
                ) {
                    pushBadge()
                }
            }
        }
    }
}

@Composable
inline fun Modifier.getBoarderModifier(
    size: Dp,
    isCircleShape: Boolean,
    boarderType: BoarderType,
    cornerRadius: Dp = 0.dp,
    boarderWidth: Dp = 1.dp,
    boarderColor: Color = colorResource(id = R.color.label_normal).copy(OPACITY_5)
) = this.then(
    when (boarderType) {
        BoarderType.None -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.static_white))
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = colorResource(id = R.color.static_white))
            }
        }

        BoarderType.OutLine -> {
            val localDensity = LocalDensity.current
            Modifier
                .drawBehind {
                    if (isCircleShape) {
                        drawCircle(
                            color = boarderColor,
                            radius = with(localDensity) { (size + boarderWidth * 2).toPx() } / 2,
                            center = center,
                            style = Fill
                        )
                    } else {
                        drawRoundRect(
                            color = boarderColor,
                            topLeft = Offset(
                                -with(localDensity) { boarderWidth.toPx() },
                                -with(localDensity) { boarderWidth.toPx() }),
                            size = Size(
                                with(localDensity) { (size + boarderWidth * 2).toPx() },
                                with(localDensity) { (size + boarderWidth * 2).toPx() }),
                            cornerRadius = CornerRadius(with(localDensity) { (cornerRadius + boarderWidth).toPx() }),
                            style = Fill
                        )
                    }
                }
                .background(
                    color = colorResource(id = R.color.static_white),
                    shape = if (isCircleShape) {
                        CircleShape
                    } else {
                        RoundedCornerShape(cornerRadius)
                    }
                )
        }

        BoarderType.InnerLine -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.static_white))
                    .border(
                        width = boarderWidth,
                        color = boarderColor,
                        shape = CircleShape
                    )
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = colorResource(id = R.color.static_white))
                    .border(
                        width = boarderWidth,
                        color = boarderColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            }
        }
    }
)


enum class BoarderType {
    None,
    InnerLine,
    OutLine
}

enum class WantedAvatarSize(
    val size: Dp,
    val cornerRadius: Dp
) {
    XSmall(24.dp, 6.dp),
    Small(32.dp, 6.dp),
    Medium(40.dp, 8.dp),
    Large(48.dp, 10.dp),
    XLarge(56.dp, 12.dp)
}

enum class WantedAvatarType {
    Person,
    Company,
    Academic
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
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
                    placeHolder = R.drawable.ic_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    pushBadge = {
                        WantedPushBadge()
                    },
                    onClick = {}
                )
                WantedAvatar(
                    modifier = Modifier,
                    model = R.drawable.ic_avatar_placeholder_person,
                    placeHolder = R.drawable.ic_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    model = R.drawable.ic_avatar_placeholder_person,
                    placeHolder = R.drawable.ic_avatar_placeholder_person,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    isIcon = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    pushBadge = {
                        WantedPushBadge()
                    },
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    isIcon = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    isIcon = true,
                    onClick = {}
                )

                WantedAvatar(
                    modifier = Modifier,
                    size = WantedAvatarSize.Medium,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    isIcon = true,
                    pushBadge = {
                        WantedPushBadge()
                    },
                    onClick = {}
                )
            }
        }
    }
}