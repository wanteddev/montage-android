package com.wanted.android.montage.sample.content.avatar

import androidx.compose.ui.unit.Dp
import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoEvent
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoSideEffect
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoViewState
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DSWantedAvatarDemoViewModel @Inject constructor() :
    WantedStateViewModel<DSWantedAvatarDemoEvent, DSWantedAvatarDemoViewState, DSWantedAvatarDemoSideEffect>() {

    override fun setInitialState() = DSWantedAvatarDemoViewState()

    override fun handleEvents(event: DSWantedAvatarDemoEvent) {
        when (event) {
            is DSWantedAvatarDemoEvent.ShowCode -> showCode(event.isShowCode)
            is DSWantedAvatarDemoEvent.CopyCode -> copyCode()
            is DSWantedAvatarDemoEvent.SetType -> setType(event.type)
            is DSWantedAvatarDemoEvent.SetSize -> setSize(event.size)
            is DSWantedAvatarDemoEvent.SetPushBadge -> setPushBadge(event.pushBadge)
            is DSWantedAvatarDemoEvent.SetIsGroup -> setIsGroup(event.isGroup)
            is DSWantedAvatarDemoEvent.SetIsIcon -> setIsIcon(event.isIcon)
            is DSWantedAvatarDemoEvent.ShowAll -> showAll(event.isShowAll)
            is DSWantedAvatarDemoEvent.SetCustomSize -> setCustomSize(event.size)
            is DSWantedAvatarDemoEvent.SetCustomCornerRadius -> setCustomCornerRadius(event.cornerRadius)
            is DSWantedAvatarDemoEvent.SetCustomBadgeSize -> setCustomBadgeSize(event.badgeSize)
            is DSWantedAvatarDemoEvent.SetCustomBadgeSizeDefault -> setCustomBadgeSizeDefault(event.isDefault)
        }
    }

    private fun showCode(isShowCode: Boolean) {
        setState {
            copy(
                code = getCode(),
                isShowCode = isShowCode
            )
        }
    }

    private fun copyCode() {
        setEffect { DSWantedAvatarDemoSideEffect.CopyCode(getCode()) }
    }

    private fun showAll(isShowAll: Boolean) {
        setState { copy(isShowAll = isShowAll) }
    }

    private fun setType(type: WantedAvatarType) {
        setState { copy(selectedType = type) }
    }

    private fun setSize(size: WantedAvatarSize) {
        setState { copy(selectedSize = size) }
    }

    private fun setCustomSize(size: Dp) {
        setState {
            val nextState = copy(customSize = size)
            nextState.copy(
                selectedSize = if (nextState.isCustomBadgeSizeDefault) {
                    WantedAvatarSize.Custom(
                        size = size,
                        cornerRadius = nextState.customCornerRadius
                    )
                } else {
                    WantedAvatarSize.Custom(
                        size = size,
                        cornerRadius = nextState.customCornerRadius,
                        badgeSize = nextState.customBadgeSize
                    )
                }
            )
        }
    }

    private fun setCustomCornerRadius(cornerRadius: Dp) {
        setState {
            val nextState = copy(customCornerRadius = cornerRadius)
            nextState.copy(
                selectedSize = if (nextState.isCustomBadgeSizeDefault) {
                    WantedAvatarSize.Custom(
                        size = nextState.customSize,
                        cornerRadius = cornerRadius
                    )
                } else {
                    WantedAvatarSize.Custom(
                        size = nextState.customSize,
                        cornerRadius = cornerRadius,
                        badgeSize = nextState.customBadgeSize
                    )
                }
            )
        }
    }

    private fun setCustomBadgeSize(badgeSize: PushBadgeSize) {
        setState {
            val nextState = copy(customBadgeSize = badgeSize)
            nextState.copy(
                selectedSize = WantedAvatarSize.Custom(
                    size = nextState.customSize,
                    cornerRadius = nextState.customCornerRadius,
                    badgeSize = badgeSize
                )
            )
        }
    }

    private fun setCustomBadgeSizeDefault(isDefault: Boolean) {
        setState {
            val nextState = copy(isCustomBadgeSizeDefault = isDefault)
            nextState.copy(
                selectedSize = if (isDefault) {
                    WantedAvatarSize.Custom(
                        size = nextState.customSize,
                        cornerRadius = nextState.customCornerRadius
                    )
                } else {
                    WantedAvatarSize.Custom(
                        size = nextState.customSize,
                        cornerRadius = nextState.customCornerRadius,
                        badgeSize = nextState.customBadgeSize
                    )
                }
            )
        }
    }

    private fun setPushBadge(pushBadge: Boolean) {
        setState { copy(pushBadge = pushBadge) }
    }

    private fun setIsGroup(isGroup: Boolean) {
        setState { copy(isGroup = isGroup) }
    }

    private fun setIsIcon(isIcon: Boolean) {
        setState { copy(isIcon = isIcon) }
    }

    private fun getCode(): String {
        val viewStateValue = viewState.value
        val sizeName = when (val selectedSize = viewStateValue.selectedSize) {
            is WantedAvatarSize.XSmall -> "XSmall"
            is WantedAvatarSize.Small -> "Small"
            is WantedAvatarSize.Medium -> "Medium"
            is WantedAvatarSize.Large -> "Large"
            is WantedAvatarSize.XLarge -> "XLarge"
            is WantedAvatarSize.Custom -> {
                if (viewStateValue.isCustomBadgeSizeDefault) {
                    "Custom(${selectedSize.size}.dp, ${selectedSize.cornerRadius}.dp)"
                } else {
                    "Custom(${selectedSize.size}.dp, ${selectedSize.cornerRadius}.dp, PushBadgeSize.${selectedSize.badgeSize.name})"
                }
            }
        }

        return if (viewStateValue.isGroup) {
            """
                WantedAvatarGroup(
                    modelList = listOf( /* model list */ ),
                    size = WantedAvatarSize.$sizeName,
                    type = WantedAvatarType.${viewStateValue.selectedType.name},
                    isIcon = ${viewStateValue.isIcon}
                )
            """.trimIndent()
        } else {
            """
                WantedAvatar(
                    type = WantedAvatarType.${viewStateValue.selectedType.name},
                    size = WantedAvatarSize.$sizeName,
                    pushBadge = ${viewStateValue.pushBadge},
                    isGroup = ${viewStateValue.isGroup},
                    isIcon = ${viewStateValue.isIcon},
                    onClick = { /* onClick event */ }
                )
            """.trimIndent()
        }
    }
}
