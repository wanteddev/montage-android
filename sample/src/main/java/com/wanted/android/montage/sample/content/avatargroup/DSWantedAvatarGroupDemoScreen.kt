package com.wanted.android.montage.sample.content.avatargroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoSideEffect
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarType
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroup
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAvatarGroupDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedAvatarGroupDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedAvatarGroupDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedAvatarGroupDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedAvatarGroupDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedAvatarGroupDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.ShowCode(true))
            }

            DSWantedAvatarGroupDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.CopyCode)
            }

            is DSWantedAvatarGroupDemoViewEvent.OnSizeChanged -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.SetSize(viewEvent.size))
            }

            is DSWantedAvatarGroupDemoViewEvent.OnTypeChanged -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.SetType(viewEvent.type))
            }

            is DSWantedAvatarGroupDemoViewEvent.OnShowTrailingChanged -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.SetShowTrailing(viewEvent.show))
            }
        }
    }

    if (viewState.isShowCode) {
        WantedModal(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.ShowCode(false))
            },
            content = {
                Text(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    text = viewState.code
                )
            }
        )
    }
}

@Composable
private fun DSWantedAvatarGroupDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedAvatarGroupDemoViewState,
    onViewEvent: (DSWantedAvatarGroupDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedAvatarGroup") {
                onViewEvent(DSWantedAvatarGroupDemoViewEvent.OnClickBack)
            }
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.navigationBarsPadding(),
                background = true,
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedAvatarGroupDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                neutral = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedAvatarGroupDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedAvatarGroupDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedAvatarGroup(
                    modelList = listOf(
                        com.wanted.android.montage.sample.R.drawable.icon_avatar_placeholder_person,
                        com.wanted.android.montage.sample.R.drawable.icon_avatar_placeholder_person,
                        com.wanted.android.montage.sample.R.drawable.icon_avatar_placeholder_person
                    ),
                    size = viewState.size,
                    type = viewState.type,
                    isDrawableRes = true,
                    trailingContent = if (viewState.showTrailing) {
                        { maxHeight ->
                            Box(modifier = Modifier.height(maxHeight)) {
                                Text(
                                    text = "+3",
                                    style = DesignSystemTheme.typography.body2Bold
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${getSizeName(viewState.size)}",
                    selectedValue = getSizeName(viewState.size),
                    selectValueList = listOf("XSmall", "Small", "Medium", "Large", "XLarge"),
                    onSelect = { sizeName ->
                        onViewEvent(
                            DSWantedAvatarGroupDemoViewEvent.OnSizeChanged(getSizeFromName(sizeName))
                        )
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${viewState.type.name}",
                    selectedValue = viewState.type.name,
                    selectValueList = listOf("Person", "Company", "Academic"),
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedAvatarGroupDemoViewEvent.OnTypeChanged(getTypeFromName(typeName))
                        )
                    }
                )
            },
            showTrailing = {
                DSWantedOptionSwitchCell(
                    text = "trailingContent : ${viewState.showTrailing}",
                    checkState = viewState.showTrailing,
                    onCheckChanged = { checked ->
                        onViewEvent(DSWantedAvatarGroupDemoViewEvent.OnShowTrailingChanged(checked))
                    }
                )
            }
        )
    }
}

@Composable
private fun DSWantedAvatarGroupDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    size: @Composable () -> Unit,
    type: @Composable () -> Unit,
    showTrailing: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Preview",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        preview()

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.label_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        size()
        type()
        showTrailing()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getSizeName(size: WantedAvatarSize): String {
    return when (size) {
        WantedAvatarSize.XSmall -> "XSmall"
        WantedAvatarSize.Small -> "Small"
        WantedAvatarSize.Medium -> "Medium"
        WantedAvatarSize.Large -> "Large"
        WantedAvatarSize.XLarge -> "XLarge"
        is WantedAvatarSize.Custom -> "Custom"
    }
}

private fun getSizeFromName(name: String): WantedAvatarSize {
    return when (name) {
        "XSmall" -> WantedAvatarSize.XSmall
        "Small" -> WantedAvatarSize.Small
        "Medium" -> WantedAvatarSize.Medium
        "Large" -> WantedAvatarSize.Large
        "XLarge" -> WantedAvatarSize.XLarge
        else -> WantedAvatarSize.Medium
    }
}

private fun getTypeFromName(name: String): WantedAvatarType {
    return when (name) {
        "Person" -> WantedAvatarType.Person
        "Company" -> WantedAvatarType.Company
        "Academic" -> WantedAvatarType.Academic
        else -> WantedAvatarType.Person
    }
}

@DevicePreviews
@Composable
private fun DSWantedAvatarGroupDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedAvatarGroupDemoScreen(
            onClickBack = {}
        )
    }
}