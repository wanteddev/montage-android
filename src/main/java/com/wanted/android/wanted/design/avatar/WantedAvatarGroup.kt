package com.wanted.android.wanted.design.avatar

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=14854-45474&m=dev
 */
@Composable
fun WantedAvatarGroup(
    modifier: Modifier = Modifier,
    modelList: List<Any>,
    @DrawableRes placeHolder: Int? = null,
    size: WantedAvatarSize,
    type: WantedAvatarType,
    isIcon: Boolean = false,
    isDrawableRes: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-6).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        modelList.forEachIndexed { index, any ->
            WantedAvatar(
                modifier = Modifier.zIndex(modelList.size - index.toFloat()),
                model = any,
                placeHolder = placeHolder,
                size = size,
                type = type,
                isGroup = true,
                isIcon = isIcon,
                isDrawableRes = isDrawableRes
            )
        }
    }
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
                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_person,
                        R.drawable.ic_avatar_placeholder_person,
                        R.drawable.ic_avatar_placeholder_person
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    isIcon = false
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_person,
                        R.drawable.ic_avatar_placeholder_person,
                        R.drawable.ic_avatar_placeholder_person
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_person,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Person,
                    isDrawableRes = true,
                    isIcon = true
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_company,
                        R.drawable.ic_avatar_placeholder_company,
                        R.drawable.ic_avatar_placeholder_company
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_company,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    isIcon = false
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_company,
                        R.drawable.ic_avatar_placeholder_company,
                        R.drawable.ic_avatar_placeholder_company
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_company,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Company,
                    isDrawableRes = true,
                    isIcon = true
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_academic,
                        R.drawable.ic_avatar_placeholder_academic,
                        R.drawable.ic_avatar_placeholder_academic
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_academic,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    isIcon = false
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = listOf(
                        R.drawable.ic_avatar_placeholder_academic,
                        R.drawable.ic_avatar_placeholder_academic,
                        R.drawable.ic_avatar_placeholder_academic
                    ),
                    placeHolder = R.drawable.ic_avatar_placeholder_academic,
                    size = WantedAvatarSize.XLarge,
                    type = WantedAvatarType.Academic,
                    isDrawableRes = true,
                    isIcon = true
                )
            }
        }
    }
}