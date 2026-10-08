package com.wanted.android.wanted.design.contents.avatar.avatargroup

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.contents.avatar.WantedAvatar
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupDefaults.WantedAvatarGroupSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedAvatarGroup
 *
 * 여러 개의 아바타를 그룹 형태로 겹쳐 보여주는 컴포넌트입니다.
 *
 * 좌우로 겹쳐진 형태의 아바타와 우측에 추가 텍스트나 콘텐츠를 표시할 수 있습니다.
 * Drawable 리소스 또는 URL 기반 이미지 모두를 지원합니다.
 *
 * 아바타는 [WantedAvatarDefaults.MAX_GROUP_VISIBLE_COUNT]명까지만 표시하며,
 * 초과 인원은 호출부가 [trailingContent]로 처리합니다(예: "외 N명").
 *
 * 그룹은 Person 형태만 제공합니다. Company·Academy가 필요하면 단독 [WantedAvatar]를 사용합니다.
 *
 * 크기는 Figma·iOS 와 동일하게 [WantedAvatarGroupSize] 의 XSmall·Small 2종만 지원합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedAvatarGroup(
 *     modelList = listOf(R.drawable.ic_avatar_placeholder_person),
 *     modifier = Modifier,
 *     placeHolder = R.drawable.ic_avatar_placeholder_person,
 *     size = WantedAvatarGroupSize.XSmall,
 *     isDrawableRes = true,
 *     contentDescription = "프로필 이미지",
 *     trailingContent = { WantedAvatarGroupTrailingText(text = "외 3명") }
 * )
 * ```
 *
 * @param modelList List<Any>: 표시할 아바타 모델 리스트입니다 (URL 또는 Drawable ID).
 * @param size WantedAvatarGroupSize: 그룹 크기입니다. XSmall·Small 중 선택합니다.
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param placeHolder Int?: 이미지 로딩 실패 시 사용할 Drawable 리소스 ID입니다.
 * @param isDrawableRes Boolean: modelList 항목이 Drawable 리소스인지 여부입니다.
 * @param contentDescription String?: 그룹 접근성 라벨의 접두 문구입니다. 지정하면 뒤에 표시 인원 수가 붙어 "프로필 이미지 3" 형태로 낭독되며, null이면 그룹 라벨을 부여하지 않습니다.
 * @param trailingContent (@Composable (Dp) -> Unit)?: 아바타 그룹 오른쪽에 추가적으로 표시할 콘텐츠입니다. 전달되는 Dp는 슬롯 높이(XSmall 24dp / Small 32dp)이며, 슬롯이 이 높이로 고정되므로 더 큰 콘텐츠는 이 높이에 맞춰 압축됩니다. 디자인 토큰이 적용된 [WantedAvatarGroupTrailingText]·[WantedAvatarGroupTrailingTextButton] 사용을 권장합니다.
 *
 * @see WantedAvatarGroupTrailingText
 * @see WantedAvatarGroupTrailingTextButton
 */
@Composable
fun WantedAvatarGroup(
    modelList: List<Any>,
    size: WantedAvatarGroupSize,
    modifier: Modifier = Modifier,
    @DrawableRes placeHolder: Int? = null,
    isDrawableRes: Boolean = false,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    trailingContent: @Composable ((Dp) -> Unit)? = null
) {
    val visibleModelList = modelList.take(WantedAvatarDefaults.MAX_GROUP_VISIBLE_COUNT)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(size.trailingSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f, fill = false)
                .then(
                    contentDescription?.let { prefix ->
                        Modifier.semantics(mergeDescendants = true) {
                            this.contentDescription = "$prefix ${visibleModelList.size}"
                        }
                    } ?: Modifier
                ),
            horizontalArrangement = Arrangement.spacedBy(size.avatarOverlap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            visibleModelList.forEach { any ->
                Box(modifier = Modifier) {
                    WantedAvatar(
                        modifier = Modifier,
                        model = any,
                        placeHolder = placeHolder,
                        size = size.avatarSize,
                        type = WantedAvatarType.Person,
                        isGroup = true,
                        alignment = alignment,
                        contentScale = contentScale,
                        isDrawableRes = isDrawableRes
                    )
                }
            }
        }

        trailingContent?.let {
            // Figma 의 Trailing Content sizing 은 FILL 이라 슬롯 높이로 고정한다.
            // 28dp 인 Text Button 도 XSmall(24dp)에서는 이 높이에 맞춰 압축된다.
            Box(
                modifier = Modifier.height(size.trailingHeight),
                contentAlignment = Alignment.Center
            ) {
                trailingContent(size.trailingHeight)
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
                    modelList = List(3) { R.drawable.icon_normal_person_fill },
                    placeHolder = R.drawable.icon_normal_person_fill,
                    size = WantedAvatarGroupSize.XSmall,
                    isDrawableRes = true,
                    contentDescription = "프로필 이미지"
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = List(3) { R.drawable.icon_normal_person_fill },
                    placeHolder = R.drawable.icon_normal_person_fill,
                    size = WantedAvatarGroupSize.Small,
                    isDrawableRes = true,
                    contentDescription = "프로필 이미지"
                )

                // 표시 상한(5명) 초과 — 5명만 렌더되고 나머지는 trailingContent가 안내한다
                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = List(8) { R.drawable.icon_normal_person_fill },
                    placeHolder = R.drawable.icon_normal_person_fill,
                    size = WantedAvatarGroupSize.XSmall,
                    isDrawableRes = true,
                    contentDescription = "프로필 이미지",
                    trailingContent = {
                        WantedAvatarGroupTrailingText(text = "외 3명")
                    }
                )

                WantedAvatarGroup(
                    modifier = Modifier,
                    modelList = List(8) { R.drawable.icon_normal_person_fill },
                    placeHolder = R.drawable.icon_normal_person_fill,
                    size = WantedAvatarGroupSize.XSmall,
                    isDrawableRes = true,
                    contentDescription = "프로필 이미지",
                    trailingContent = {
                        WantedAvatarGroupTrailingTextButton(text = "외 3명", onClick = {})
                    }
                )
            }
        }
    }
}