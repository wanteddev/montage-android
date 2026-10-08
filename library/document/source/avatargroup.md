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

/**
* object WantedAvatarGroupDefaults
*
* Avatar Group 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class WantedAvatarGroupSize
*
* Avatar Group 이 지원하는 크기입니다.
*
* 단독 [WantedAvatarSize] 는 5종 + Custom 이지만, Avatar Group 은 Figma·iOS 와 동일하게
* XSmall·Small 2종만 제공합니다. 나머지 크기는 그룹 스펙(겹침 폭·trailing 간격·trailing 높이)이
* 정의되어 있지 않습니다.
*
* @property avatarSize 그룹을 구성하는 개별 아바타의 크기입니다.
* @property avatarOverlap 아바타끼리 겹치는 폭입니다. 음수 간격으로 적용됩니다.
* @property trailingSpacing 아바타 묶음과 trailingContent 사이 간격입니다.
* @property trailingHeight trailingContent 슬롯의 높이입니다. 슬롯은 이 높이로 고정되며(Figma sizing=FILL), 더 큰 콘텐츠는 이 높이에 맞춰 압축됩니다.
*/

/**
* WantedAvatarGroupTrailingText
*
* [WantedAvatarGroup]의 trailingContent 슬롯에 사용하는 텍스트 프리셋입니다.
*
* 색상 Foreground/Neutral/Secondary, 타이포 Label 1/Normal - Medium 토큰이 고정 적용되며,
* 표시 상한(5명)을 초과한 인원을 안내하는 용도로 주로 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedAvatarGroup(
*     modelList = modelList,
*     size = WantedAvatarGroupSize.XSmall,
*     trailingContent = { WantedAvatarGroupTrailingText(text = "외 3명") }
* )
* ```
*
* @param text String: 표시할 문구입니다.
* @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
*
* @see WantedAvatarGroup
* @see WantedAvatarGroupTrailingTextButton
*/

/**
* WantedAvatarGroupTrailingTextButton
*
* [WantedAvatarGroup]의 trailingContent 슬롯에 사용하는 텍스트 버튼 프리셋입니다.
*
* [WantedTextButton] 위에 trailing 스펙의 색·타이포 토큰을 적용한 형태로,
* [WantedAvatarGroupTrailingText]와 보이는 모습은 같고 클릭 영역만 추가됩니다.
* 밑줄 등 별도의 시각적 구분은 두지 않습니다.
*
* 사용 예시:
* ```kotlin
* WantedAvatarGroup(
*     modelList = modelList,
*     size = WantedAvatarGroupSize.XSmall,
*     trailingContent = {
*         WantedAvatarGroupTrailingTextButton(text = "외 3명", onClick = { /* 클릭 동작 */ })
*     }
* )
* ```
*
* @param text String: 표시할 문구입니다.
* @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
* @param enabled Boolean: 클릭 가능 여부입니다.
* @param onClick (() -> Unit): 클릭 시 호출될 콜백 함수입니다.
*
* @see WantedAvatarGroup
* @see WantedAvatarGroupTrailingText
*/