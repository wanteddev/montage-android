package com.wanted.android.wanted.design.contents.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun WantedAvatarContent(
    modifier: Modifier = Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    isDrawableRes: Boolean = false,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit
) {
    model?.let {
        if (isDrawableRes && model is Int) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = model),
                contentDescription = "",
                tint = Color.Unspecified
            )
        } else {
            GlideImage(
                modifier = modifier,
                model = model,
                contentDescription = "",
                alignment = alignment,
                contentScale = contentScale,
                loading = placeHolder?.let { placeholder(it) },
                failure = placeHolder?.let { placeholder(it) },
            )
        }
    } ?: run {
        placeHolder?.let {
            Icon(
                modifier = modifier,
                painter = painterResource(id = placeHolder),
                contentDescription = "",
                tint = Color.Unspecified
            )
        } ?: run {
            Box(
                modifier = modifier
                    .background(DesignSystemTheme.colors.staticWhite)
            )
        }
    }
}