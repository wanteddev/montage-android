package com.wanted.android.montage.sample.content.avatar

import androidx.compose.ui.unit.Dp
import com.wanted.android.montage.sample.base.WantedStateViewModel
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoEvent
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoModel
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
            is DSWantedAvatarDemoEvent.ShowAll -> showAll(event.isShowAll)
            is DSWantedAvatarDemoEvent.SetModel -> setModel(event.model)
            is DSWantedAvatarDemoEvent.SetColoredBackground -> setColoredBackground(event.isColoredBackground)
            is DSWantedAvatarDemoEvent.SetCustomSize,
            is DSWantedAvatarDemoEvent.SetCustomCornerRadius,
            is DSWantedAvatarDemoEvent.SetCustomBadgeSize,
            is DSWantedAvatarDemoEvent.SetCustomBadgeSizeDefault -> handleCustomSizeEvent(event)
        }
    }

    private fun handleCustomSizeEvent(event: DSWantedAvatarDemoEvent) {
        when (event) {
            is DSWantedAvatarDemoEvent.SetCustomSize -> setCustomSize(event.size)
            is DSWantedAvatarDemoEvent.SetCustomCornerRadius -> setCustomCornerRadius(event.cornerRadius)
            is DSWantedAvatarDemoEvent.SetCustomBadgeSize -> setCustomBadgeSize(event.badgeSize)
            is DSWantedAvatarDemoEvent.SetCustomBadgeSizeDefault -> setCustomBadgeSizeDefault(event.isDefault)
            else -> Unit
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

    private fun setModel(model: DSWantedAvatarDemoModel) {
        setState { copy(selectedModel = model) }
    }

    private fun setColoredBackground(isColoredBackground: Boolean) {
        setState { copy(isColoredBackground = isColoredBackground) }
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
                    "Custom(${selectedSize.size}, ${selectedSize.cornerRadius})"
                } else {
                    "Custom(${selectedSize.size}, ${selectedSize.cornerRadius}, PushBadgeSize.${selectedSize.badgeSize.name})"
                }
            }
        }

        val model = viewStateValue.selectedModel

        val groupSizeName = viewStateValue.selectedSize.toGroupSizeName()

        return if (viewStateValue.isGroup && groupSizeName == null) {
            "// Avatar Group 은 XSmall · Small 만 지원합니다."
        } else if (viewStateValue.isGroup) {
            """
                WantedAvatarGroup(
                    modelList = ${model.toGroupModelListCode()},
                    size = WantedAvatarGroupSize.$groupSizeName,${model.toGroupDrawableResCode()}
                )
            """.trimIndent()
        } else {
            """
                WantedAvatar(
                    type = WantedAvatarType.${viewStateValue.selectedType.name},${model.toModelCode()}
                    size = WantedAvatarSize.$sizeName,
                    pushBadge = ${viewStateValue.pushBadge},
                    isGroup = ${viewStateValue.isGroup},
                    onClick = { /* onClick event */ }
                )
            """.trimIndent()
        }
    }
}

// WantedAvatarGroup 은 WantedAvatarGroupSize(XSmall·Small)만 받는다. 그 밖의 사이즈는 null.
private fun WantedAvatarSize.toGroupSizeName(): String? = when (this) {
    WantedAvatarSize.XSmall -> "XSmall"
    WantedAvatarSize.Small -> "Small"
    else -> null
}

private fun DSWantedAvatarDemoModel.toModelCode(): String = when (this) {
    DSWantedAvatarDemoModel.None -> ""
    DSWantedAvatarDemoModel.LoadFail -> "${CODE_PARAM_LINE}model = \"$LOAD_FAIL_MODEL_URL\","
    DSWantedAvatarDemoModel.Image ->
        "${CODE_PARAM_LINE}model = R.drawable.icon_wanted,${CODE_PARAM_LINE}isDrawableRes = true,"
}

private fun DSWantedAvatarDemoModel.toGroupModelListCode(): String = when (this) {
    DSWantedAvatarDemoModel.None -> "listOf( /* model list */ )"
    DSWantedAvatarDemoModel.LoadFail -> "listOf(\"$LOAD_FAIL_MODEL_URL\")"
    DSWantedAvatarDemoModel.Image -> "listOf(R.drawable.icon_wanted)"
}

private fun DSWantedAvatarDemoModel.toGroupDrawableResCode(): String = when (this) {
    DSWantedAvatarDemoModel.Image -> "${CODE_PARAM_LINE}isDrawableRes = true,"
    DSWantedAvatarDemoModel.None,
    DSWantedAvatarDemoModel.LoadFail -> ""
}

// 코드 보기에 끼워 넣는 인자 줄의 시작. trimIndent 전 템플릿의 인자 줄 들여쓰기(20칸)와 같아야 결과 코드가 밀리지 않는다.
private const val CODE_PARAM_LINE = "\n                    "

/** 로딩 실패 placeholder 확인용 주소입니다. `.invalid` 는 예약 TLD 라 DNS 조회가 즉시 실패합니다. */
internal const val LOAD_FAIL_MODEL_URL = "https://avatar.invalid/placeholder.png"
