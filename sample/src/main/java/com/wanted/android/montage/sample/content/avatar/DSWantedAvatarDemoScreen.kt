package com.wanted.android.montage.sample.content.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoEvent
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoModel
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoSideEffect
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoViewEvent
import com.wanted.android.montage.sample.content.avatar.DSWantedAvatarDemoScreenContract.DSWantedAvatarDemoViewState
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.avatar.WantedAvatar
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroup
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupDefaults.WantedAvatarGroupSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.input.slider.WantedSlider
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAvatarDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedAvatarDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedAvatarDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedAvatarDemoScreenImpl(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            is DSWantedAvatarDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedAvatarDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowCode(true))
            }

            is DSWantedAvatarDemoViewEvent.OnSelectType -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedAvatarDemoViewEvent.OnSelectSize -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedAvatarDemoViewEvent.OnChangePushBadge -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.SetPushBadge(viewEvent.pushBadge))
            }

            is DSWantedAvatarDemoViewEvent.OnChangeIsGroup -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.SetIsGroup(viewEvent.isGroup))
            }

            is DSWantedAvatarDemoViewEvent.OnClickShowAll -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowAll(true))
            }

            is DSWantedAvatarDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedAvatarDemoEvent.CopyCode)
            }

            is DSWantedAvatarDemoViewEvent.OnSelectModel,
            is DSWantedAvatarDemoViewEvent.OnChangeColoredBackground,
            is DSWantedAvatarDemoViewEvent.OnChangeCustomSize,
            is DSWantedAvatarDemoViewEvent.OnChangeCustomCornerRadius,
            is DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSize,
            is DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSizeDefault -> {
                handleOptionViewEvent(viewEvent, viewModel)
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedAvatarDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowCode(false))
            },
            content = {
                Text(text = viewState.code)
            }
        )
    }

    if (viewState.isShowAll) {
        WantedPopup(
            positive = "확인",
            onClickPositive = {
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowAll(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedAvatarDemoEvent.ShowAll(false))
            },
            content = {
                DSWantedAllAvatar(viewState) { type, size ->
                    viewModel.setEvent(DSWantedAvatarDemoEvent.SetType(type))
                    viewModel.setEvent(DSWantedAvatarDemoEvent.SetSize(size))
                    viewModel.setEvent(DSWantedAvatarDemoEvent.ShowAll(false))
                    viewModel.setEvent(DSWantedAvatarDemoEvent.CopyCode)
                }
            }
        )
    }
}

private fun handleOptionViewEvent(
    viewEvent: DSWantedAvatarDemoViewEvent,
    viewModel: DSWantedAvatarDemoViewModel
) {
    when (viewEvent) {
        is DSWantedAvatarDemoViewEvent.OnSelectModel -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetModel(viewEvent.model))
        }

        is DSWantedAvatarDemoViewEvent.OnChangeColoredBackground -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetColoredBackground(viewEvent.isColoredBackground))
        }

        is DSWantedAvatarDemoViewEvent.OnChangeCustomSize -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetCustomSize(viewEvent.size))
        }

        is DSWantedAvatarDemoViewEvent.OnChangeCustomCornerRadius -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetCustomCornerRadius(viewEvent.cornerRadius))
        }

        is DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSize -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetCustomBadgeSize(viewEvent.badgeSize))
        }

        is DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSizeDefault -> {
            viewModel.setEvent(DSWantedAvatarDemoEvent.SetCustomBadgeSizeDefault(viewEvent.isDefault))
        }

        else -> Unit
    }
}

@Composable
private fun DSWantedAvatarDemoScreenImpl(
    modifier: Modifier = Modifier,
    viewState: DSWantedAvatarDemoViewState,
    onViewEvent: (DSWantedAvatarDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedAvatar") {
                onViewEvent(DSWantedAvatarDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedAvatarDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "모든 옵션 보기",
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            onViewEvent(DSWantedAvatarDemoViewEvent.OnClickShowAll)
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        DSWantedAvatarDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            isColoredBackground = viewState.isColoredBackground,
            preview = {
                if (viewState.isGroup) {
                    DSWantedAvatarGroupPreview(size = viewState.selectedSize, model = viewState.selectedModel)
                } else {
                    WantedAvatar(
                        type = viewState.selectedType,
                        size = viewState.selectedSize,
                        model = viewState.selectedModel.toAvatarModel(),
                        isDrawableRes = viewState.selectedModel == DSWantedAvatarDemoModel.Image,
                        pushBadge = viewState.pushBadge,
                        isGroup = viewState.isGroup,
                        onClick = {
                            onViewEvent(DSWantedAvatarDemoViewEvent.OnClickCopyCode)
                        }
                    )
                }
            },
            type = {
                WantedSelect(
                    value = "Type : ${viewState.selectedType.name}",
                    selectedValue = viewState.selectedType.name,
                    selectValueList = viewState.typeList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedAvatarDemoViewEvent.OnSelectType(
                                WantedAvatarType.valueOf(
                                    it
                                )
                            )
                        )
                    },
                )
            },
            model = {
                WantedSelect(
                    value = "Model : ${viewState.selectedModel.name}",
                    selectedValue = viewState.selectedModel.name,
                    selectValueList = viewState.modelList.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedAvatarDemoViewEvent.OnSelectModel(DSWantedAvatarDemoModel.valueOf(it))
                        )
                    },
                )
            },
            coloredBackground = {
                DSWantedOptionSwitchCell(
                    text = "coloredBackground : ${viewState.isColoredBackground}",
                    checkState = viewState.isColoredBackground,
                    onCheckChanged = {
                        onViewEvent(DSWantedAvatarDemoViewEvent.OnChangeColoredBackground(it))
                    }
                )
            },
            size = {
                val currentSizeName = viewState.selectedSize::class.simpleName ?: ""
                WantedSelect(
                    value = "Size : $currentSizeName",
                    selectedValue = currentSizeName,
                    selectValueList = viewState.sizeList.map { it::class.simpleName ?: "" },
                    onSelect = { selectedName ->
                        val selectedSize =
                            viewState.sizeList.first { it::class.simpleName == selectedName }
                        onViewEvent(DSWantedAvatarDemoViewEvent.OnSelectSize(selectedSize))
                    },
                )
            },
            pushBadge = {
                DSWantedOptionSwitchCell(
                    text = "pushBadge : ${viewState.pushBadge}",
                    checkState = viewState.pushBadge,
                    onCheckChanged = {
                        onViewEvent(DSWantedAvatarDemoViewEvent.OnChangePushBadge(it))
                    }
                )
            },
            isGroup = {
                DSWantedOptionSwitchCell(
                    text = "isGroup : ${viewState.isGroup}",
                    checkState = viewState.isGroup,
                    onCheckChanged = {
                        onViewEvent(DSWantedAvatarDemoViewEvent.OnChangeIsGroup(it))
                    }
                )
            },
            customOptions = {
                if (viewState.selectedSize is WantedAvatarSize.Custom) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        WantedSlider(
                            value = viewState.customSize.value,
                            valueRange = 10f..100f,
                            header = "Custom Size: ${viewState.customSize.value.toInt()}dp",
                            onValueChange = {
                                onViewEvent(DSWantedAvatarDemoViewEvent.OnChangeCustomSize(it.dp))
                            }
                        )
                        WantedSlider(
                            value = viewState.customCornerRadius.value,
                            valueRange = 0f..50f,
                            header = "Custom Corner Radius: ${viewState.customCornerRadius.value.toInt()}dp",
                            onValueChange = {
                                onViewEvent(
                                    DSWantedAvatarDemoViewEvent.OnChangeCustomCornerRadius(
                                        it.dp
                                    )
                                )
                            }
                        )
                        DSWantedOptionSwitchCell(
                            text = "Default Badge Size : ${viewState.isCustomBadgeSizeDefault}",
                            checkState = viewState.isCustomBadgeSizeDefault,
                            onCheckChanged = {
                                onViewEvent(DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSizeDefault(it))
                            }
                        )
                        if (viewState.isCustomBadgeSizeDefault.not()) {
                            WantedSelect(
                                value = "Custom Badge Size : ${viewState.customBadgeSize.name}",
                                selectedValue = viewState.customBadgeSize.name,
                                selectValueList = PushBadgeSize.entries.map { it.name },
                                onSelect = {
                                    onViewEvent(
                                        DSWantedAvatarDemoViewEvent.OnChangeCustomBadgeSize(
                                            PushBadgeSize.valueOf(it)
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun DSWantedAllAvatar(
    viewState: DSWantedAvatarDemoViewState,
    onClick: (WantedAvatarType, WantedAvatarSize) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewState.typeList.forEach { type ->
            item {
                Text(
                    text = type.name,
                    style = DesignSystemTheme.typography.title3Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            viewState.sizeList.forEach { size ->
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (viewState.isGroup) {
                            DSWantedAvatarGroupPreview(size = size, model = viewState.selectedModel)
                        } else {
                            WantedAvatar(
                                type = type,
                                size = size,
                                model = viewState.selectedModel.toAvatarModel(),
                                isDrawableRes = viewState.selectedModel == DSWantedAvatarDemoModel.Image,
                                pushBadge = viewState.pushBadge,
                                isGroup = viewState.isGroup,
                                onClick = {
                                    onClick(type, size)
                                }
                            )
                        }
                        Text(
                            text = size::class.simpleName ?: "",
                            style = DesignSystemTheme.typography.body2Regular
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DSWantedAvatarDemoScreenLayout(
    modifier: Modifier,
    isColoredBackground: Boolean,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    model: @Composable () -> Unit,
    coloredBackground: @Composable () -> Unit,
    size: @Composable () -> Unit,
    pushBadge: @Composable () -> Unit,
    isGroup: @Composable () -> Unit,
    customOptions: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.line_neutral_primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .then(
                    // placeholder 의 아이콘 자리가 배경까지 뚫리면 이 색이 비쳐 보인다
                    if (isColoredBackground) {
                        Modifier.background(
                            color = DesignSystemTheme.colors.surfaceBrandPrimary,
                            shape = RoundedCornerShape(8.dp)
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            preview()
        }

        Spacer(Modifier.size(10.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Option",
                style = WantedTextStyle(
                    colorRes = com.wanted.android.montage.sample.R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            type()
            model()
            coloredBackground()
            size()
            pushBadge()
            isGroup()
            customOptions()
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedAvatarDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedAvatarDemoScreenImpl(
            viewState = DSWantedAvatarDemoViewState(),
            onViewEvent = { }
        )
    }
}

// Avatar 데모의 그룹 미리보기.
// Avatar Group 은 XSmall·Small 2종만 지원하므로, 나머지 사이즈에서는 그룹을 그리지 않고 지원 범위를 문구로 안내한다.
// modelList 는 null 을 받지 않으므로 None 은 로딩 실패 주소로 채워 컴포넌트 기본 placeholder 를 띄운다.
// placeholder 모양의 Drawable 을 이미지로 넣으면 아바타의 흰 바탕 위에 그대로 그려져 다크에서도 라이트 색으로 보인다.
@Composable
private fun DSWantedAvatarGroupPreview(size: WantedAvatarSize, model: DSWantedAvatarDemoModel) {
    val groupSize = when (size) {
        WantedAvatarSize.XSmall -> WantedAvatarGroupSize.XSmall
        WantedAvatarSize.Small -> WantedAvatarGroupSize.Small
        else -> null
    }

    if (groupSize == null) {
        Text(
            text = "Avatar Group 은 XSmall · Small 만 지원합니다.",
            style = DesignSystemTheme.typography.caption1Regular,
            color = DesignSystemTheme.colors.foregroundNeutralTertiary
        )
    } else {
        WantedAvatarGroup(
            modelList = List(GROUP_PREVIEW_AVATAR_COUNT) {
                model.toAvatarModel() ?: LOAD_FAIL_MODEL_URL
            },
            size = groupSize,
            isDrawableRes = model == DSWantedAvatarDemoModel.Image
        )
    }
}

private fun DSWantedAvatarDemoModel.toAvatarModel(): Any? = when (this) {
    DSWantedAvatarDemoModel.None -> null
    DSWantedAvatarDemoModel.LoadFail -> LOAD_FAIL_MODEL_URL
    DSWantedAvatarDemoModel.Image -> R.drawable.icon_wanted
}

/** 그룹 미리보기에 표시할 아바타 수입니다. */
private const val GROUP_PREVIEW_AVATAR_COUNT = 3
