package com.wanted.android.montage.sample.content.avatargroup

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoSideEffect
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewState
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.TrailingContentType
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedAvatarGroupDemoViewModel @Inject constructor() :
    WantedStateViewModel<
        DSWantedAvatarGroupDemoEvent,
        DSWantedAvatarGroupDemoViewState,
        DSWantedAvatarGroupDemoSideEffect
        >() {
    override fun setInitialState() = DSWantedAvatarGroupDemoViewState()

    override fun handleEvents(event: DSWantedAvatarGroupDemoEvent) {
        when (event) {
            is DSWantedAvatarGroupDemoEvent.InitState -> setState { event.viewState }
            is DSWantedAvatarGroupDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedAvatarGroupDemoEvent.CopyCode -> copyCode()
            is DSWantedAvatarGroupDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedAvatarGroupDemoEvent.SetAvatarCount -> setAvatarCount(event.count)
            is DSWantedAvatarGroupDemoEvent.SetTrailingContentType -> {
                setTrailingContentType(event.trailingContentType)
            }
        }
    }

    private fun setAvatarCount(count: Int) {
        setState { copy(avatarCount = count) }
    }

    private fun setTrailingContentType(trailingContentType: TrailingContentType) {
        setState { copy(trailingContentType = trailingContentType) }
    }

    private fun copyCode() {
        setEffect { DSWantedAvatarGroupDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = "WantedAvatarGroupSize.${state.size.name}"
        val overflowCount = (state.avatarCount - WantedAvatarDefaults.MAX_GROUP_VISIBLE_COUNT)
            .coerceAtLeast(0)
        val trailingContentLine = when (state.trailingContentType) {
            TrailingContentType.None -> "trailingContent = null,"
            TrailingContentType.Text -> {
                "trailingContent = { WantedAvatarGroupTrailingText(text = \"외 ${overflowCount}명\") },"
            }

            TrailingContentType.TextButton -> {
                "trailingContent = { WantedAvatarGroupTrailingTextButton(text = \"외 ${overflowCount}명\", onClick = {}) },"
            }
        }

        return """
            WantedAvatarGroup(
                modelList = List(${state.avatarCount}) { R.drawable.icon_normal_person_fill },
                size = $sizeString,
                isDrawableRes = true,
                contentDescription = "프로필 이미지",
                $trailingContentLine
            )
        """.trimIndent()
    }
}