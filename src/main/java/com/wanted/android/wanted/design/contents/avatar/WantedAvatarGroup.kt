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
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarContract.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarContract.WantedAvatarType
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 여러 개의 아바타를 그룹 형태로 보여주는 컴포넌트입니다.
 *
 * 모델 리스트를 받아 좌우로 겹쳐진 형태로 아바타들을 표시하며, 필요 시 추가 텍스트나 컴포넌트를 우측에 표시할 수 있습니다.
 * 아바타는 Drawable 리소스 또는 URL 기반의 이미지를 지원합니다.
 *
 * ### 사용 예시
 * ```kotlin
 * WantedAvatarGroup(
 *     modifier = Modifier,
 *     modelList = listOf(
 *         R.drawable.ic_avatar_placeholder_person,
 *         R.drawable.ic_avatar_placeholder_person,
 *         R.drawable.ic_avatar_placeholder_person
 *     ),
 *     placeHolder = R.drawable.ic_avatar_placeholder_person,
 *     size = WantedAvatarSize.XLarge,
 *     type = WantedAvatarType.Person,
 *     isDrawableRes = true,
 *     isIcon = false
 * )
 * ```
 *
 * @param modifier 외부에서 전달받는 Modifier로 레이아웃 커스터마이징에 사용됩니다.
 * @param modelList 표시할 아바타 모델 리스트입니다. (URL 또는 Drawable ID)
 * @param placeHolder 이미지 로딩 실패 시 표시할 Drawable 리소스 ID입니다.
 * @param size 아바타 크기 및 모서리 반경을 정의합니다.
 * @param type 아바타의 유형(Person, Company, Academic)을 지정합니다.
 * @param isIcon 아바타를 아이콘 스타일로 표시할지 여부를 설정합니다.
 * @param isDrawableRes 모델이 Drawable 리소스인지 여부를 나타냅니다.
 * @param trailingContent 아바타 그룹 우측에 추가적으로 표시할 컴포저블 콘텐츠입니다.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WantedAvatarGroup(
    modifier: Modifier = Modifier,
    modelList: List<Any>,
    @DrawableRes placeHolder: Int? = null,
    size: WantedAvatarSize,
    type: WantedAvatarType,
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