package com.wanted.android.montage.sample.theme.typography

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.DSWantedOptionSwitchCell
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoEvent
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoSideEffect
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoViewEvent
import com.wanted.android.montage.sample.theme.typography.DSWantedTypographyDemoScreenContract.DSWantedTypographyDemoViewState
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackBar
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val COPY_ACTION_LABEL = "복사"

@Composable
internal fun DSWantedTypographyDemoScreen(
    viewModel: DSWantedTypographyDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedTypographyDemoSideEffect.ShowCopySnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(
                        visuals = WantedSnackbarVisuals(
                            message = sideEffect.code,
                            actionLabel = COPY_ACTION_LABEL
                        )
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        clipboardManager.setText(AnnotatedString(sideEffect.code))
                    }
                }
            }
        }
    }

    DSWantedTypographyDemoScreenContent(
        viewState = viewState,
        snackbarHostState = snackbarHostState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedTypographyDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedTypographyDemoViewEvent.OnSelectWeight -> {
                viewModel.setEvent(DSWantedTypographyDemoEvent.SetWeight(viewEvent.weight))
            }

            is DSWantedTypographyDemoViewEvent.OnSelectSample -> {
                viewModel.setEvent(DSWantedTypographyDemoEvent.SetSample(viewEvent.sample))
            }

            is DSWantedTypographyDemoViewEvent.OnChangeShowLineBox -> {
                viewModel.setEvent(DSWantedTypographyDemoEvent.SetShowLineBox(viewEvent.enabled))
            }

            is DSWantedTypographyDemoViewEvent.OnClickItem -> {
                viewModel.setEvent(DSWantedTypographyDemoEvent.ClickItem(viewEvent.name))
            }
        }
    }
}

@Composable
private fun DSWantedTypographyDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedTypographyDemoViewState,
    snackbarHostState: SnackbarHostState,
    onViewEvent: (DSWantedTypographyDemoViewEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "Typography") {
                onViewEvent(DSWantedTypographyDemoViewEvent.OnClickBack)
            }
        },
        snackbarHost = {
            WantedSnackBar(snackbarHostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OptionChipRow(
                options = TypographyWeight.entries,
                selected = viewState.weight,
                label = { it.label },
                onSelect = { onViewEvent(DSWantedTypographyDemoViewEvent.OnSelectWeight(it)) }
            )
            OptionChipRow(
                options = TypographySample.entries,
                selected = viewState.sample,
                label = { it.label },
                onSelect = { onViewEvent(DSWantedTypographyDemoViewEvent.OnSelectSample(it)) }
            )
            DSWantedOptionSwitchCell(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "행간 영역 표시 : ${viewState.showLineBox}",
                checkState = viewState.showLineBox,
                onCheckChanged = { onViewEvent(DSWantedTypographyDemoViewEvent.OnChangeShowLineBox(it)) }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = viewState.filteredItems,
                    key = { item -> item.name }
                ) { item ->
                    TypographyItem(
                        name = item.name,
                        style = item.style(DesignSystemTheme.typography),
                        sample = viewState.sample.text,
                        showLineBox = viewState.showLineBox,
                        onClick = { onViewEvent(DSWantedTypographyDemoViewEvent.OnClickItem(item.name)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun <T> OptionChipRow(
    options: List<T>,
    selected: T,
    label: (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            WantedChip(
                text = label(option),
                size = ChipSize.Small,
                variant = ChipVariant.Outlined,
                isActive = option == selected,
                onClick = { onSelect(option) }
            )
        }
    }
}

@Composable
private fun TypographyItem(
    name: String,
    style: TextStyle,
    sample: String,
    showLineBox: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            style = DesignSystemTheme.typography.label1Bold,
            color = DesignSystemTheme.colors.foregroundNeutralStrong
        )
        Text(
            text = style.specText(),
            style = DesignSystemTheme.typography.caption1Regular,
            color = DesignSystemTheme.colors.foregroundNeutralTertiary
        )
        Text(
            modifier = if (showLineBox) {
                Modifier.background(DesignSystemTheme.colors.surfaceBrandPrimary.copy(alpha = LINE_BOX_ALPHA))
            } else {
                Modifier
            },
            text = sample,
            style = style,
            color = DesignSystemTheme.colors.foregroundNeutralPrimary
        )
    }
}

private const val LINE_BOX_ALPHA = 0.08f

// Figma 표기와 대조하기 쉽게 크기·행간은 sp, 행간 배율은 소수 셋째 자리, 자간은 %(= em × 100)로 보여준다.
// 기본 Locale 에 따라 소수점 표기가 바뀌지 않도록 String.format 대신 반올림 연산으로 만든다.
private fun TextStyle.specText(): String {
    val size = fontSize.value
    val lineHeight = lineHeight.value
    val ratio = (lineHeight / size * RATIO_SCALE).roundToInt() / RATIO_SCALE
    val tracking = (letterSpacing.value * PERCENT * TRACKING_SCALE).roundToInt() / TRACKING_SCALE
    val weight = fontWeight?.weight ?: 0
    return "${size.roundToInt()}sp / 행간 ${lineHeight.roundToInt()}sp ($ratio) / 자간 $tracking% / W$weight"
}

private const val RATIO_SCALE = 1000.0
private const val PERCENT = 100
private const val TRACKING_SCALE = 100.0

@DevicePreviews
@Composable
private fun DSWantedTypographyDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedTypographyDemoScreenContent(
            viewState = DSWantedTypographyDemoViewState(),
            snackbarHostState = remember { SnackbarHostState() },
            onViewEvent = { }
        )
    }
}
