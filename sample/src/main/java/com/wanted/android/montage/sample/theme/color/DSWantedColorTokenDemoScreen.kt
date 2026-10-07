package com.wanted.android.montage.sample.theme.color

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.R
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoEvent
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoSideEffect
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoViewEvent
import com.wanted.android.montage.sample.theme.color.DSWantedColorTokenDemoScreenContract.DSWantedColorTokenDemoViewState
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackBar
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlinx.coroutines.launch

private const val COPY_ACTION_LABEL = "복사"

@Composable
internal fun DSWantedColorTokenDemoScreen(
    viewModel: DSWantedColorTokenDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedColorTokenDemoSideEffect.ShowCopySnackBar -> {
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

    DSWantedColorTokenDemoScreenContent(
        viewState = viewState,
        snackbarHostState = snackbarHostState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedColorTokenDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedColorTokenDemoViewEvent.OnQueryChange -> {
                viewModel.setEvent(DSWantedColorTokenDemoEvent.SetQuery(viewEvent.query))
            }

            is DSWantedColorTokenDemoViewEvent.OnSelectGroup -> {
                viewModel.setEvent(DSWantedColorTokenDemoEvent.SetGroup(viewEvent.group))
            }

            is DSWantedColorTokenDemoViewEvent.OnClickItem -> {
                viewModel.setEvent(DSWantedColorTokenDemoEvent.ClickItem(viewEvent.code))
            }
        }
    }
}

@Composable
private fun DSWantedColorTokenDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedColorTokenDemoViewState,
    snackbarHostState: SnackbarHostState,
    onViewEvent: (DSWantedColorTokenDemoViewEvent) -> Unit
) {
    // 색상은 colorResource 로 기기의 다크 모드 설정을 따르므로, 라이트·다크 값을 한 화면에 함께 보이도록
    // uiMode 만 바꾼 Context 에서 각각 읽는다.
    val lightContext = rememberUiModeContext(isNight = false)
    val darkContext = rememberUiModeContext(isNight = true)

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "Color Token") {
                onViewEvent(DSWantedColorTokenDemoViewEvent.OnClickBack)
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
            WantedSearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                text = viewState.query,
                placeholder = "토큰 검색 (예: neutral/primary, lineBrand)",
                size = WantedSearchFieldDefaults.Size.Large,
                onValueChange = { onViewEvent(DSWantedColorTokenDemoViewEvent.OnQueryChange(it)) }
            )
            GroupChipRow(
                selected = viewState.group,
                onSelect = { onViewEvent(DSWantedColorTokenDemoViewEvent.OnSelectGroup(it)) }
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                text = "${viewState.filteredItems.size}개 · 왼쪽 칩 라이트 / 오른쪽 칩 다크 · 탭하면 코드 표시 → 복사",
                style = DesignSystemTheme.typography.caption1Regular,
                color = DesignSystemTheme.colors.foregroundNeutralTertiary
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = viewState.filteredItems,
                    key = { item -> item.name }
                ) { item ->
                    ColorTokenItem(
                        item = item,
                        lightColor = lightContext.colorOf(item.colorRes),
                        darkColor = darkContext.colorOf(item.colorRes),
                        lightBackground = lightContext.colorOf(R.color.background_neutral_primary),
                        darkBackground = darkContext.colorOf(R.color.background_neutral_primary),
                        lightBorder = lightContext.colorOf(R.color.line_neutral_primary),
                        darkBorder = darkContext.colorOf(R.color.line_neutral_primary),
                        onClick = { onViewEvent(DSWantedColorTokenDemoViewEvent.OnClickItem(item.code)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun rememberUiModeContext(isNight: Boolean): Context {
    val context = LocalContext.current
    return remember(context, isNight) {
        val configuration = Configuration(context.resources.configuration).apply {
            val nightMode = if (isNight) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
            uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or nightMode
        }
        context.createConfigurationContext(configuration)
    }
}

private fun Context.colorOf(colorRes: Int): Int = getColor(colorRes)

@Composable
private fun GroupChipRow(
    selected: ColorTokenGroup,
    onSelect: (ColorTokenGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ColorTokenGroup.entries.forEach { group ->
            WantedChip(
                text = group.label,
                size = ChipSize.Small,
                variant = ChipVariant.Outlined,
                isActive = group == selected,
                onClick = { onSelect(group) }
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun ColorTokenItem(
    item: DSWantedColorTokenItem,
    lightColor: Int,
    darkColor: Int,
    lightBackground: Int,
    darkBackground: Int,
    lightBorder: Int,
    darkBorder: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            ColorSwatch(color = lightColor, background = lightBackground, border = lightBorder)
            ColorSwatch(color = darkColor, background = darkBackground, border = darkBorder)
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = item.tokenPath,
                style = DesignSystemTheme.typography.label1Bold,
                color = DesignSystemTheme.colors.foregroundNeutralStrong
            )
            Text(
                text = item.name,
                style = DesignSystemTheme.typography.caption1Regular,
                color = DesignSystemTheme.colors.foregroundNeutralSecondary
            )
            Text(
                text = "Light ${lightColor.toHexText()}  ·  Dark ${darkColor.toHexText()}",
                style = DesignSystemTheme.typography.caption2Regular,
                color = DesignSystemTheme.colors.foregroundNeutralTertiary
            )
        }
    }
}

// 반투명 토큰도 실제 보이는 대로 확인할 수 있게, 해당 모드의 배경색 위에 칠한다.
@Composable
private fun ColorSwatch(color: Int, background: Int, border: Int) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .size(SWATCH_SIZE.dp)
            .clip(shape)
            .background(Color(background))
            .border(width = 1.dp, color = Color(border), shape = shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(color))
        )
    }
}

private const val SWATCH_SIZE = 40
private const val HEX_LENGTH = 8
private const val ALPHA_SHIFT = 24
private const val ALPHA_MASK = 0xFF
private const val PERCENT = 100

// ARGB hex 와 알파 % (Figma 표기와 대조용). 기본 Locale 영향을 받지 않도록 String.format 을 쓰지 않는다.
internal fun Int.toHexText(): String {
    val hex = Integer.toHexString(this).uppercase().padStart(HEX_LENGTH, '0')
    val alphaPercent = (((this ushr ALPHA_SHIFT) and ALPHA_MASK) * PERCENT + ALPHA_MASK / 2) / ALPHA_MASK
    return "#$hex ($alphaPercent%)"
}

@DevicePreviews
@Composable
private fun DSWantedColorTokenDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedColorTokenDemoScreenContent(
            viewState = DSWantedColorTokenDemoViewState(),
            snackbarHostState = remember { SnackbarHostState() },
            onViewEvent = { }
        )
    }
}
