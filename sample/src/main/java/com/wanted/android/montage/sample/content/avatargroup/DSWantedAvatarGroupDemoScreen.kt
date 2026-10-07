package com.wanted.android.montage.sample.content.avatargroup

import androidx.compose.foundation.layout.Arrangement
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
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoSideEffect
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewEvent
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.DSWantedAvatarGroupDemoViewState
import com.wanted.android.montage.sample.content.avatargroup.DSWantedAvatarGroupDemoScreenContract.TrailingContentType
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.MAX_GROUP_VISIBLE_COUNT
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupDefaults.WantedAvatarGroupSize
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroup
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupTrailingText
import com.wanted.android.wanted.design.contents.avatar.avatargroup.WantedAvatarGroupTrailingTextButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * avatarCount 옵션으로 선택할 수 있는 아바타 수입니다. 표시 상한(5명) 초과 동작을 확인하기 위해 8까지 제공한다.
 */
private val AVATAR_COUNT_OPTIONS = listOf(1, 3, 5, 8)

/**
 * 그룹 접근성 라벨의 접두 문구입니다. 그룹은 Person 형태만 제공하므로 고정입니다.
 */
private const val AVATAR_GROUP_CONTENT_DESCRIPTION_PREFIX = "프로필 이미지"

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

            is DSWantedAvatarGroupDemoViewEvent.OnAvatarCountChanged -> {
                viewModel.setEvent(DSWantedAvatarGroupDemoEvent.SetAvatarCount(viewEvent.count))
            }

            is DSWantedAvatarGroupDemoViewEvent.OnTrailingContentTypeChanged -> {
                viewModel.setEvent(
                    DSWantedAvatarGroupDemoEvent.SetTrailingContentType(viewEvent.trailingContentType)
                )
            }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
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
                main = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 보기",
                        onClick = {
                            onViewEvent(DSWantedAvatarGroupDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
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
                val overflowCount = (viewState.avatarCount - MAX_GROUP_VISIBLE_COUNT)
                    .coerceAtLeast(0)

                WantedAvatarGroup(
                    modelList = List(viewState.avatarCount) {
                        R.drawable.icon_normal_person_fill
                    },
                    size = viewState.size,
                    isDrawableRes = true,
                    contentDescription = AVATAR_GROUP_CONTENT_DESCRIPTION_PREFIX,
                    trailingContent = when (viewState.trailingContentType) {
                        TrailingContentType.None -> null
                        TrailingContentType.Text -> {
                            { WantedAvatarGroupTrailingText(text = "외 ${overflowCount}명") }
                        }

                        TrailingContentType.TextButton -> {
                            {
                                WantedAvatarGroupTrailingTextButton(
                                    text = "외 ${overflowCount}명",
                                    onClick = {}
                                )
                            }
                        }
                    }
                )
            },
            size = {
                WantedSelect(
                    value = "size : ${viewState.size.name}",
                    selectedValue = viewState.size.name,
                    selectValueList = WantedAvatarGroupSize.entries.map { it.name },
                    onSelect = { sizeName ->
                        onViewEvent(
                            DSWantedAvatarGroupDemoViewEvent.OnSizeChanged(
                                WantedAvatarGroupSize.valueOf(sizeName)
                            )
                        )
                    }
                )
            },
            avatarCount = {
                WantedSelect(
                    value = "avatarCount : ${viewState.avatarCount}",
                    selectedValue = viewState.avatarCount.toString(),
                    selectValueList = AVATAR_COUNT_OPTIONS.map { it.toString() },
                    onSelect = { count ->
                        onViewEvent(
                            DSWantedAvatarGroupDemoViewEvent.OnAvatarCountChanged(count.toInt())
                        )
                    }
                )
            },
            trailingContentType = {
                WantedSelect(
                    value = "trailingContent : ${viewState.trailingContentType.name}",
                    selectedValue = viewState.trailingContentType.name,
                    selectValueList = TrailingContentType.entries.map { it.name },
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedAvatarGroupDemoViewEvent.OnTrailingContentTypeChanged(
                                TrailingContentType.valueOf(typeName)
                            )
                        )
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
    avatarCount: @Composable () -> Unit,
    trailingContentType: @Composable () -> Unit,
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
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        DSWantedPreviewContainer {
            preview()
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Option",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_strong,
                style = DesignSystemTheme.typography.heading2Bold
            )
        )
        size()
        avatarCount()
        trailingContentType()
        Spacer(modifier = Modifier.height(20.dp))
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
