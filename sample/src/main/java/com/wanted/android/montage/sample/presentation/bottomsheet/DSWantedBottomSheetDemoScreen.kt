package com.wanted.android.montage.sample.presentation.bottomsheet

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoSideEffect
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewEvent
import com.wanted.android.montage.sample.presentation.bottomsheet.DSWantedBottomSheetDemoScreenContract.DSWantedBottomSheetDemoViewState
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogCloseTopAppBar
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBarContract.Variant
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedBottomSheetDefaults
import com.wanted.android.wanted.design.presentation.modal.bottomsheet.WantedModalBottomSheet
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedBottomSheetDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedBottomSheetDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedBottomSheetDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedBottomSheetDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedBottomSheetDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedBottomSheetDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(true))
            }

            DSWantedBottomSheetDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.CopyCode)
            }

            is DSWantedBottomSheetDemoViewEvent.OnModalTypeChanged -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetModalType(viewEvent.type))
            }

            is DSWantedBottomSheetDemoViewEvent.OnDismissOnClickOutsideChanged -> {
                viewModel.setEvent(
                    DSWantedBottomSheetDemoEvent.SetDismissOnClickOutside(viewEvent.dismiss)
                )
            }

            is DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged -> {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetShowSheet(viewEvent.show))
            }

            else -> handleOptionViewEvent(viewEvent = viewEvent, viewModel = viewModel)
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedBottomSheetDemoEvent.ShowCode(false))
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
private fun DSWantedBottomSheetDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedBottomSheetDemoViewState,
    onViewEvent: (DSWantedBottomSheetDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "WantedModalBottomSheet") {
                onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickBack)
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
                            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickShowCode)
                        }
                    )
                },
                sub = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "코드 복사",
                        onClick = {
                            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnClickCopyCode)
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        DSWantedBottomSheetDemoScreenLayout(
            modifier = Modifier.padding(innerPadding),
            preview = {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "바텀시트 열기",
                    onClick = {
                        onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(true))
                    }
                )
            },
            type = {
                WantedSelect(
                    value = "type : ${getTypeName(viewState.modalType)}",
                    selectedValue = getTypeName(viewState.modalType),
                    selectValueList = listOf(
                        "Flexible",
                        "FixedWrapContent",
                        "Fixed",
                        "FixedFullScreen",
                        "FixedRatio"
                    ),
                    onSelect = { typeName ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnModalTypeChanged(
                                getTypeFromName(typeName)
                            )
                        )
                    }
                )
            },
            dismissOnClickOutside = {
                DSWantedOptionSwitchCell(
                    text = "dismissOnClickOutside : ${viewState.dismissOnClickOutside}",
                    checkState = viewState.dismissOnClickOutside,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnDismissOnClickOutsideChanged(checked)
                        )
                    }
                )
            },
            navigationVariant = {
                // Bottom Sheet 는 Emphasized(기본) 와 Floating 만 허용한다. Normal(중앙)은 Full 전용이다.
                WantedSelect(
                    value = "navigation variant : ${variantName(viewState.navigationVariant)}",
                    selectedValue = variantName(viewState.navigationVariant),
                    selectValueList = NAVIGATION_VARIANTS.map { variantName(it) },
                    onSelect = { selected ->
                        val variant = NAVIGATION_VARIANTS
                            .firstOrNull { variantName(it) == selected }
                            ?: Variant.Emphasized
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnNavigationVariantChanged(variant)
                        )
                    }
                )
            },
            closeButtonBackground = {
                DSWantedOptionSwitchCell(
                    text = "closeButtonBackground : ${viewState.closeButtonBackground}",
                    checkState = viewState.closeButtonBackground,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnCloseButtonBackgroundChanged(checked)
                        )
                    }
                )
            },
            contentPadding = {
                DSWantedOptionSwitchCell(
                    text = "content padding : ${viewState.useContentPadding}",
                    checkState = viewState.useContentPadding,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnContentPaddingChanged(checked)
                        )
                    }
                )
            },
            actionArea = {
                DSWantedOptionSwitchCell(
                    text = "actionArea : ${viewState.useActionArea}",
                    checkState = viewState.useActionArea,
                    onCheckChanged = { checked ->
                        onViewEvent(
                            DSWantedBottomSheetDemoViewEvent.OnUseActionAreaChanged(checked)
                        )
                    }
                )
            }
        )
    }

    WantedModalBottomSheet(
        isShow = viewState.isShowSheet,
        onDismissRequest = {
            onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false))
        },
        type = viewState.modalType,
        sheetDefault = sheetDefault(
            type = viewState.modalType,
            useContentPadding = viewState.useContentPadding
        ),
        dismissOnClickOutside = viewState.dismissOnClickOutside,
        topBar = {
            WantedDialogCloseTopAppBar(
                variant = navigationVariant(
                    variant = viewState.navigationVariant,
                    iconBackground = viewState.closeButtonBackground
                ),
                title = "Bottom Sheet",
                onClickClose = {
                    onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false))
                }
            )
        },
        bottomBar = if (viewState.useActionArea) {
            {
                // 여백은 Bottom Sheet 가 actionPadding 으로 넣으므로 Action Area 의 safeArea 여백은 끈다.
                WantedActionArea(
                    safeArea = false,
                    divider = false,
                    main = {
                        WantedButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "확인",
                            onClick = {
                                onViewEvent(
                                    DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false)
                                )
                            }
                        )
                    }
                )
            }
        } else {
            null
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "바텀시트 콘텐츠")
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "닫기",
                    onClick = {
                        onViewEvent(DSWantedBottomSheetDemoViewEvent.OnShowSheetChanged(false))
                    }
                )
            }
        }
    )
}

private fun handleOptionViewEvent(
    viewEvent: DSWantedBottomSheetDemoViewEvent,
    viewModel: DSWantedBottomSheetDemoViewModel
) {
    when (viewEvent) {
        is DSWantedBottomSheetDemoViewEvent.OnNavigationVariantChanged -> {
            viewModel.setEvent(
                DSWantedBottomSheetDemoEvent.SetNavigationVariant(viewEvent.variant)
            )
        }

        is DSWantedBottomSheetDemoViewEvent.OnCloseButtonBackgroundChanged -> {
            viewModel.setEvent(
                DSWantedBottomSheetDemoEvent.SetCloseButtonBackground(viewEvent.use)
            )
        }

        is DSWantedBottomSheetDemoViewEvent.OnContentPaddingChanged -> {
            viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetContentPadding(viewEvent.use))
        }

        is DSWantedBottomSheetDemoViewEvent.OnUseActionAreaChanged -> {
            viewModel.setEvent(DSWantedBottomSheetDemoEvent.SetUseActionArea(viewEvent.use))
        }

        else -> Unit
    }
}

// Content 여백은 컴포넌트 기본값(스펙)이 켜져 있다. 끄면 호출부가 직접 여백을 주는 기존 화면 형태가 된다.
private fun sheetDefault(type: ModalType, useContentPadding: Boolean) =
    when {
        !useContentPadding -> WantedBottomSheetDefaults.getWithoutContentPadding(type)
        type is ModalType.FixedFullScreen -> WantedBottomSheetDefaults.getFullDefault()
        else -> WantedBottomSheetDefaults.getDefault()
    }

// 아이콘 배경은 Floating 만 갖는 속성이라 Floating 일 때만 값을 싣는다.
private fun navigationVariant(variant: Variant, iconBackground: Boolean): Variant =
    if (variant is Variant.Floating) Variant.Floating(iconBackground) else variant

// sealed class 라 enum 의 name 이 없다. 선택 UI 표기는 클래스 이름으로 맞춘다.
private fun variantName(variant: Variant): String = variant::class.simpleName.orEmpty()

// Bottom Sheet 가 허용하는 Navigation variant. Normal(중앙 정렬)은 Full 전용이라 제외한다.
private val NAVIGATION_VARIANTS = listOf(Variant.Emphasized, Variant.Floating())

@Composable
private fun DSWantedBottomSheetDemoScreenLayout(
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
    type: @Composable () -> Unit,
    dismissOnClickOutside: @Composable () -> Unit,
    navigationVariant: @Composable () -> Unit,
    closeButtonBackground: @Composable () -> Unit,
    contentPadding: @Composable () -> Unit,
    actionArea: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
        type()
        dismissOnClickOutside()
        navigationVariant()
        closeButtonBackground()
        contentPadding()
        actionArea()
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getTypeName(type: ModalType): String {
    return when (type) {
        ModalType.Flexible -> "Flexible"
        is ModalType.FixedWrapContent -> "FixedWrapContent"
        is ModalType.Fixed -> "Fixed"
        is ModalType.FixedFullScreen -> "FixedFullScreen"
        is ModalType.FixedRatio -> "FixedRatio"
    }
}

private fun getTypeFromName(name: String): ModalType {
    return when (name) {
        "Flexible" -> ModalType.Flexible
        "FixedWrapContent" -> ModalType.FixedWrapContent()
        "Fixed" -> ModalType.Fixed(height = 400.dp)
        "FixedFullScreen" -> ModalType.FixedFullScreen()
        "FixedRatio" -> ModalType.FixedRatio(ratio = 0.6f)
        else -> ModalType.Flexible
    }
}

@DevicePreviews
@Composable
private fun DSWantedBottomSheetDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedBottomSheetDemoScreen(
            onClickBack = {}
        )
    }
}