package com.wanted.android.montage.sample.actions.iconbutton

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoViewState
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.IconButtonVariant
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonBadgePosition
import com.wanted.android.wanted.design.actions.button.iconbutton.IconButtonInteractionEffect
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackground
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackgroundSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormal
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonOutlined
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSolid
import com.wanted.android.wanted.design.feedback.pushbadge.WantedPushBadge
import com.wanted.android.wanted.design.input.select.WantedSelect
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedIconButtonDemoScreen(
    modifier: Modifier = Modifier,
    viewModel: DSWantedIconButtonDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedIconButtonDemoSideEffect.CopyCode -> {
                clipboardManager.setText(AnnotatedString(sideEffect.code))
            }
        }
    }

    DSWantedIconButtonDemoScreenContent(
        modifier = modifier,
        viewState = viewState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedIconButtonDemoViewEvent.OnClickBack -> onClickBack()
            DSWantedIconButtonDemoViewEvent.OnClickShowCode -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(true))
            }

            DSWantedIconButtonDemoViewEvent.OnClickCopyCode -> {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.CopyCode)
            }

            // 옵션 변경은 전부 1:1 매핑이라 toOptionEvent 로 위임한다.
            else -> viewEvent.toOptionEvent()?.let { viewModel.setEvent(it) }
        }
    }

    if (viewState.isShowCode) {
        WantedPopup(
            positive = "코드 복사",
            onClickPositive = {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.CopyCode)
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(false))
            },
            onDismissRequest = {
                viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(false))
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

/**
 * 옵션 변경 ViewEvent 를 대응하는 Event 로 변환합니다.
 *
 * 옵션 변경이 아닌 ViewEvent(내비게이션·코드 보기 등)는 null 이라 아무 일도 일어나지 않습니다 —
 * 매핑을 빠뜨려도 "동작 안 함"으로 드러나며 엉뚱한 이벤트로 흘러가지 않습니다.
 */
private fun DSWantedIconButtonDemoViewEvent.toOptionEvent(): DSWantedIconButtonDemoEvent? = when (this) {
    is DSWantedIconButtonDemoViewEvent.OnContainerSizeChanged ->
        DSWantedIconButtonDemoEvent.SetContainerSize(size)

    is DSWantedIconButtonDemoViewEvent.OnNormalSizeChanged ->
        DSWantedIconButtonDemoEvent.SetNormalSize(size)

    is DSWantedIconButtonDemoViewEvent.OnUseCustomSizeChanged ->
        DSWantedIconButtonDemoEvent.SetUseCustomSize(useCustomSize)

    is DSWantedIconButtonDemoViewEvent.OnCustomBoxSizeChanged ->
        DSWantedIconButtonDemoEvent.SetCustomBoxSize(boxSizeDp)

    is DSWantedIconButtonDemoViewEvent.OnEnabledChanged ->
        DSWantedIconButtonDemoEvent.SetEnabled(enabled)

    is DSWantedIconButtonDemoViewEvent.OnVariantChanged ->
        DSWantedIconButtonDemoEvent.SetVariant(variant)

    is DSWantedIconButtonDemoViewEvent.OnInteractionEffectChanged ->
        DSWantedIconButtonDemoEvent.SetInteractionEffect(interactionEffect)

    is DSWantedIconButtonDemoViewEvent.OnInteractionOverflowChanged ->
        DSWantedIconButtonDemoEvent.SetInteractionOverflow(interactionOverflow)

    is DSWantedIconButtonDemoViewEvent.OnUseNormalInteractionChanged ->
        DSWantedIconButtonDemoEvent.SetUseNormalInteraction(useNormalInteraction)

    is DSWantedIconButtonDemoViewEvent.OnShowBadgeChanged ->
        DSWantedIconButtonDemoEvent.SetShowBadge(showBadge)

    is DSWantedIconButtonDemoViewEvent.OnBadgePositionChanged ->
        DSWantedIconButtonDemoEvent.SetBadgePosition(badgePosition)

    else -> null
}
@Composable
private fun DSWantedIconButtonDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedIconButtonDemoViewState,
    onViewEvent: (DSWantedIconButtonDemoViewEvent) -> Unit
) {
    Scaffold(
        topBar = {
            WantedBackTopAppBar(
                title = "Icon Button",
                onClickBack = { onViewEvent(DSWantedIconButtonDemoViewEvent.OnClickBack) }
            )
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                type = com.wanted.android.wanted.design.actions.actionarea.ActionAreaType.Strong,
                main = "Code",
                onClickMain = { onViewEvent(DSWantedIconButtonDemoViewEvent.OnClickShowCode) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Preview",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
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
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                DSWantedIconButtonDemoPreview(viewState = viewState)
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Enabled / Disabled 비교",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            DSWantedIconButtonEnabledDisabledComparison()

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Interaction Effect 비교 (Normal variant)",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            DSWantedIconButtonInteractionEffectComparison()

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Option",
                style = WantedTextStyle(
                    colorRes = R.color.foreground_neutral_strong,
                    style = DesignSystemTheme.typography.heading2Bold
                )
            )

            WantedSelect(
                value = "Variant : ${viewState.variant}",
                selectedValue = viewState.variant.name,
                selectValueList = IconButtonVariant.entries.map { it.name },
                onSelect = {
                    onViewEvent(DSWantedIconButtonDemoViewEvent.OnVariantChanged(IconButtonVariant.valueOf(it)))
                }
            )

            if (!viewState.useCustomSize) {
                when (viewState.variant) {
                    IconButtonVariant.Outlined, IconButtonVariant.Solid -> {
                        WantedSelect(
                            value = "Box Size : ${viewState.containerSize}",
                            selectedValue = viewState.containerSize.toString(),
                            selectValueList = WantedIconButtonSize.presets.map { it.toString() },
                            onSelect = { selected ->
                                WantedIconButtonSize.presets.firstOrNull { it.toString() == selected }
                                    ?.let { onViewEvent(DSWantedIconButtonDemoViewEvent.OnContainerSizeChanged(it)) }
                            }
                        )
                    }

                    IconButtonVariant.Normal -> {
                        WantedSelect(
                            value = "Box Size : ${viewState.normalSize}",
                            selectedValue = viewState.normalSize.toString(),
                            selectValueList = WantedIconButtonNormalSize.presets.map { it.toString() },
                            onSelect = { selected ->
                                WantedIconButtonNormalSize.presets.firstOrNull { it.toString() == selected }
                                    ?.let { onViewEvent(DSWantedIconButtonDemoViewEvent.OnNormalSizeChanged(it)) }
                            }
                        )
                    }

                    // Background: 단일 Default preset — 박스 커스텀은 아래 Custom Size 로 지정
                    IconButtonVariant.Background -> Unit
                }
            }

            DSWantedOptionSwitchCell(
                text = "Custom Size : ${viewState.useCustomSize}",
                checkState = viewState.useCustomSize,
                onCheckChanged = {
                    onViewEvent(DSWantedIconButtonDemoViewEvent.OnUseCustomSizeChanged(it))
                }
            )

            if (viewState.useCustomSize) {
                WantedSelect(
                    value = "Custom Size (dp) : ${viewState.customBoxSize}",
                    selectedValue = viewState.customBoxSize.toString(),
                    selectValueList = DSWantedIconButtonDemoScreenContract.CUSTOM_BOX_SIZE_OPTIONS
                        .map { it.toString() },
                    onSelect = { selected ->
                        selected.toIntOrNull()
                            ?.let { onViewEvent(DSWantedIconButtonDemoViewEvent.OnCustomBoxSizeChanged(it)) }
                    }
                )
            }

            DSWantedOptionSwitchCell(
                text = "Enabled : ${viewState.enabled}",
                checkState = viewState.enabled,
                onCheckChanged = {
                    onViewEvent(DSWantedIconButtonDemoViewEvent.OnEnabledChanged(it))
                }
            )

            // Dim 은 Normal variant 전용이라 나머지 variant 는 Highlight/None 만 노출한다.
            val interactionEffectOptions = if (viewState.variant == IconButtonVariant.Normal) {
                IconButtonInteractionEffect.entries
            } else {
                listOf(IconButtonInteractionEffect.Highlight, IconButtonInteractionEffect.None)
            }

            WantedSelect(
                value = "Interaction Effect : ${viewState.interactionEffect}",
                selectedValue = viewState.interactionEffect.name,
                selectValueList = interactionEffectOptions.map { it.name },
                onSelect = {
                    onViewEvent(
                        DSWantedIconButtonDemoViewEvent.OnInteractionEffectChanged(
                            IconButtonInteractionEffect.valueOf(it)
                        )
                    )
                }
            )

            if (viewState.variant == IconButtonVariant.Normal) {
                DSWantedOptionSwitchCell(
                    text = "Interaction Overflow (Normal only, size = 아이콘 크기) : ${viewState.interactionOverflow}",
                    checkState = viewState.interactionOverflow,
                    onCheckChanged = {
                        onViewEvent(DSWantedIconButtonDemoViewEvent.OnInteractionOverflowChanged(it))
                    }
                )
            }

            if (viewState.variant == IconButtonVariant.Background) {
                DSWantedOptionSwitchCell(
                    text = "Use Normal Interaction (Background only) : ${viewState.useNormalInteraction}",
                    checkState = viewState.useNormalInteraction,
                    onCheckChanged = {
                        onViewEvent(DSWantedIconButtonDemoViewEvent.OnUseNormalInteractionChanged(it))
                    }
                )
            }

            DSWantedOptionSwitchCell(
                text = "Show Badge : ${viewState.showBadge}",
                checkState = viewState.showBadge,
                onCheckChanged = {
                    onViewEvent(DSWantedIconButtonDemoViewEvent.OnShowBadgeChanged(it))
                }
            )

            if (viewState.showBadge) {
                WantedSelect(
                    value = "Badge Position : ${viewState.badgePosition}",
                    selectedValue = viewState.badgePosition.name,
                    selectValueList = IconButtonBadgePosition.entries.map { it.name },
                    onSelect = {
                        onViewEvent(
                            DSWantedIconButtonDemoViewEvent.OnBadgePositionChanged(
                                IconButtonBadgePosition.valueOf(it)
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun DSWantedIconButtonDemoPreview(
    viewState: DSWantedIconButtonDemoViewState
) {
    val badge: (@Composable () -> Unit)? = if (viewState.showBadge) {
        { WantedPushBadge() }
    } else {
        null
    }
    // Normal 외 variant 는 아직 disableInteraction 만 지원한다.
    val disableInteraction = viewState.interactionEffect == IconButtonInteractionEffect.None

    when (viewState.variant) {
        IconButtonVariant.Normal -> WantedIconButtonNormal(
            icon = R.drawable.icon_wanted,
            size = if (viewState.useCustomSize) {
                WantedIconButtonNormalSize.Custom(viewState.customBoxSize.dp)
            } else {
                viewState.normalSize
            },
            enabled = viewState.enabled,
            pushBadge = badge,
            badgePosition = viewState.badgePosition,
            interactionEffect = viewState.interactionEffect,
            interactionOverflow = viewState.interactionOverflow,
            onClick = { }
        )

        IconButtonVariant.Outlined -> WantedIconButtonOutlined(
            icon = R.drawable.icon_wanted,
            size = if (viewState.useCustomSize) {
                WantedIconButtonSize.Custom(viewState.customBoxSize.dp)
            } else {
                viewState.containerSize
            },
            modifier = Modifier,
            enabled = viewState.enabled,
            pushBadge = badge,
            badgePosition = viewState.badgePosition,
            disableInteraction = disableInteraction,
            onClick = { }
        )

        IconButtonVariant.Solid -> WantedIconButtonSolid(
            icon = R.drawable.icon_wanted,
            size = if (viewState.useCustomSize) {
                WantedIconButtonSize.Custom(viewState.customBoxSize.dp)
            } else {
                viewState.containerSize
            },
            modifier = Modifier,
            enabled = viewState.enabled,
            pushBadge = badge,
            badgePosition = viewState.badgePosition,
            disableInteraction = disableInteraction,
            onClick = { }
        )

        IconButtonVariant.Background -> WantedIconButtonBackground(
            icon = R.drawable.icon_wanted,
            size = if (viewState.useCustomSize) {
                WantedIconButtonBackgroundSize.Custom(viewState.customBoxSize.dp)
            } else {
                WantedIconButtonBackgroundSize.Default
            },
            enabled = viewState.enabled,
            pushBadge = badge,
            badgePosition = viewState.badgePosition,
            disableInteraction = disableInteraction,
            useNormalInteraction = viewState.useNormalInteraction,
            onClick = { }
        )
    }
}

@Composable
private fun DSWantedIconButtonEnabledDisabledComparison() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.line_neutral_primary),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Normal variant
        Text(
            text = "Normal",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_tertiary,
                style = DesignSystemTheme.typography.body1Regular
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WantedIconButtonNormal(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonNormalSize.Large,
                enabled = true,
                onClick = {}
            )
            WantedIconButtonNormal(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonNormalSize.Large,
                enabled = false,
                onClick = {}
            )
        }

        // Outlined variant
        Text(
            text = "Outlined",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_tertiary,
                style = DesignSystemTheme.typography.body1Regular
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WantedIconButtonOutlined(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonSize.Medium,
                modifier = Modifier,
                enabled = true,
                onClick = {}
            )
            WantedIconButtonOutlined(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonSize.Medium,
                modifier = Modifier,
                enabled = false,
                onClick = {}
            )
        }

        // Solid variant
        Text(
            text = "Solid",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_tertiary,
                style = DesignSystemTheme.typography.body1Regular
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WantedIconButtonSolid(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonSize.Medium,
                modifier = Modifier,
                enabled = true,
                onClick = {}
            )
            WantedIconButtonSolid(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonSize.Medium,
                modifier = Modifier,
                enabled = false,
                onClick = {}
            )
        }

        // Background variant
        Text(
            text = "Background",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_tertiary,
                style = DesignSystemTheme.typography.body1Regular
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WantedIconButtonBackground(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonBackgroundSize.Default,
                enabled = true,
                onClick = {}
            )
            WantedIconButtonBackground(
                icon = R.drawable.icon_wanted,
                size = WantedIconButtonBackgroundSize.Default,
                enabled = false,
                onClick = {}
            )
        }
    }
}

/**
 * IconButton 의 인터랙션 피드백 3종을 나란히 보여준다.
 *
 * 눌러야 차이가 드러나므로(Dim 은 눌림 중에만 아이콘 투명도가 내려간다) 값별로 직접 눌러 비교한다.
 */
@Composable
private fun DSWantedIconButtonInteractionEffectComparison() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.line_neutral_primary),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "각 버튼을 눌러 피드백을 비교하세요.",
            style = WantedTextStyle(
                colorRes = R.color.foreground_neutral_tertiary,
                style = DesignSystemTheme.typography.body1Regular
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonInteractionEffect.entries.forEach { effect ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WantedIconButtonNormal(
                        icon = R.drawable.icon_wanted,
                        size = WantedIconButtonNormalSize.Xlarge,
                        interactionEffect = effect,
                        onClick = {}
                    )
                    Text(
                        text = effect.name,
                        style = WantedTextStyle(
                            colorRes = R.color.foreground_neutral_tertiary,
                            style = DesignSystemTheme.typography.body1Regular
                        )
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun DSWantedIconButtonDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedIconButtonDemoScreenContent(
            viewState = DSWantedIconButtonDemoViewState(),
            onViewEvent = { }
        )
    }
}
