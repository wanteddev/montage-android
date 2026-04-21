package com.wanted.android.montage.sample.content.avatargroup

import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoSideEffect
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewState
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DSWantedAvatarGroupDemoViewModel @Inject constructor(

) : WantedStateViewModel<DSWantedAvatarGroupDemoEvent, DSWantedAvatarGroupDemoViewState, DSWantedAvatarGroupDemoSideEffect>() {
    override fun setInitialState() = DSWantedAvatarGroupDemoViewState()

    override fun handleEvents(event: DSWantedAvatarGroupDemoEvent) {
        when (event) {
            is DSWantedAvatarGroupDemoEvent.InitState -> setState { event.viewState }
            is DSWantedAvatarGroupDemoEvent.ShowCode -> {
                setState { copy(isShowCode = event.isShowCode, code = getCode()) }
            }

            DSWantedAvatarGroupDemoEvent.CopyCode -> copyCode()
            is DSWantedAvatarGroupDemoEvent.SetSize -> setState { copy(size = event.size) }
            is DSWantedAvatarGroupDemoEvent.SetType -> setState { copy(type = event.type) }
            is DSWantedAvatarGroupDemoEvent.SetShowTrailing -> setState { copy(showTrailing = event.show) }
        }
    }

    private fun copyCode() {
        setEffect { DSWantedAvatarGroupDemoSideEffect.CopyCode(getCode()) }
    }

    private fun getCode(): String {
        val state = viewState.value
        val sizeString = when (state.size) {
            WantedAvatarSize.XSmall -> "WantedAvatarSize.XSmall"
            WantedAvatarSize.Small -> "WantedAvatarSize.Small"
            WantedAvatarSize.Medium -> "WantedAvatarSize.Medium"
            WantedAvatarSize.Large -> "WantedAvatarSize.Large"
            WantedAvatarSize.XLarge -> "WantedAvatarSize.XLarge"
            is WantedAvatarSize.Custom -> {
                "WantedAvatarSize.Custom(size = ${state.size.size.value}.dp, cornerRadius = ${state.size.cornerRadius.value}.dp)"
            }
        }
        val typeString = when (state.type) {
            WantedAvatarType.Person -> "WantedAvatarType.Person"
            WantedAvatarType.Company -> "WantedAvatarType.Company"
            WantedAvatarType.Academic -> "WantedAvatarType.Academic"
        }
        val trailingContentLine = if (state.showTrailing) {
            "trailingContent = { maxHeight -> Text(\"+3\") },"
        } else {
            "trailingContent = null,"
        }

        return """
            WantedAvatarGroup(
                modelList = listOf(
                    R.drawable.icon_avatar_placeholder_person,
                    R.drawable.icon_avatar_placeholder_person,
                    R.drawable.icon_avatar_placeholder_person
                ),
                size = $sizeString,
                type = $typeString,
                isDrawableRes = true,
                $trailingContentLine
            )
        """.trimIndent()
    }
}