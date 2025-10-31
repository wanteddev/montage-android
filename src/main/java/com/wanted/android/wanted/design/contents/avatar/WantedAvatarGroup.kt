package com.wanted.android.wanted.design.contents.avatar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 여러 개의 아바타를 그룹 형태로 겹쳐 보여주는 컴포저블입니다.
 *
 * 좌우로 겹쳐진 형태의 아바타와 우측에 추가 텍스트나 콘텐츠를 표시할 수 있습니다.
 * Drawable 리소스 또는 URL 기반 이미지 모두를 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAvatarGroup(
 *     modelList = listOf(R.drawable.ic_avatar_placeholder_person),
 *     modifier = Modifier,
 *     placeHolder = R.drawable.ic_avatar_placeholder_person,
 *     size = WantedAvatarSize.Medium,
 *     type = WantedAvatarType.Person,
 *     isDrawableRes = true,
 *     isIcon = false
 * )
 * ```
 *
 * @param modelList List<Any>: 표시할 아바타 모델 리스트입니다 (URL 또는 Drawable ID).
 * @param size WantedAvatarSize: 아바타 크기 및 코너 반경입니다.
 * @param type WantedAvatarType: 아바타의 유형(Person, Company, Academic)을 지정합니다.
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param placeHolder Int?: 이미지 로딩 실패 시 사용할 Drawable 리소스 ID입니다.
 * @param isIcon Boolean: 아바타 내부에 Inner 보더를 적용할지 여부입니다.
 * @param isDrawableRes Boolean: modelList 항목이 Drawable 리소스인지 여부입니다.
 * @param trailingContent (@Composable (Dp) -> Unit)?: 아바타 그룹 오른쪽에 추가적으로 표시할 콘텐츠입니다.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WantedAvatarGroup(
    modelList: List<Any>,
    size: WantedAvatarSize,
    type: WantedAvatarType,
    modifier: Modifier = Modifier,
    @DrawableRes placeHolder: Int? = null,
    isIcon: Boolean = false,
    isDrawableRes: Boolean = false,
    trailingContent: @Composable ((Dp) -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((8).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f, fill = false),
            horizontalArrangement = Arrangement.spacedBy((-6).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            modelList.forEachIndexed { index, any ->
                Box(modifier = Modifier.zIndex(modelList.size - index.toFloat())) {
                    WantedAvatar(
                        modifier = Modifier,
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

        trailingContent?.let {
            BoxWithConstraints(modifier = Modifier.height(24.dp)) {
                trailingContent(maxHeight)
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
                    isIcon = false,
                    trailingContent = {
                        Text(text = "외 1명")
                    }
                )
            }
        }
    }
}