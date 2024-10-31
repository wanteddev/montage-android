package com.wanted.android.wanted.design.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.GlideImage
import com.wanted.android.designsystem.R


@Composable
internal fun WantedAvatarContent(
    modifier: Modifier = Modifier,
    model: Any?,
    @DrawableRes placeHolder: Int? = null,
    isDrawableRes: Boolean = false,
) {
    model?.let {
        if (isDrawableRes && model is Int) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = model),
                contentDescription = ""
            )
        } else {
            GlideImage(
                modifier = modifier,
                model = model,
                contentDescription = ""
            ) {
                it.placeholder(placeHolder ?: 0)
                    .error(placeHolder ?: 0)
            }
        }
    } ?: run {
        placeHolder?.let {
            Icon(
                modifier = modifier,
                painter = painterResource(id = placeHolder), contentDescription = "")
        } ?: run {
            Box(
                modifier = modifier
                    .background(colorResource(id = R.color.fill_normal))
            )
        }
    }
}