package com.wanted.android.wanted.design.beta

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_5

@Composable
fun WantedPersonAvatar(
    modifier: Modifier = Modifier,
    profileImageUrl: String?,
    size: Dp,
    borderColor: Color = DesignSystemTheme.colors.labelNormal.copy(alpha = OPACITY_5),
    alpha: Float = 1f,
) {
    val baseModifier = Modifier
        .size(size)
        .clip(CircleShape)
        .background(color = DesignSystemTheme.colors.backgroundNormalNormal)
        .border(
            width = 1.dp,
            color = borderColor,
            shape = CircleShape
        )

    Box(
        modifier = modifier.then(baseModifier)
    ) {
        GlideImage(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .alpha(alpha),
            model = profileImageUrl,
            contentDescription = "Profile Image",
            loading = placeholder(R.drawable.profile_default),
            failure = placeholder(R.drawable.profile_default),
            contentScale = ContentScale.Crop,
        )
    }
}