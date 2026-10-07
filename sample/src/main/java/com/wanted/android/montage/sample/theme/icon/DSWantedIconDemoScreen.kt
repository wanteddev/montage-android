package com.wanted.android.montage.sample.theme.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.montage.sample.util.ObserveAsEvent
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoEvent
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoSideEffect
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoViewEvent
import com.wanted.android.montage.sample.theme.icon.DSWantedIconDemoScreenContract.DSWantedIconDemoViewState
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackBar
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import kotlinx.coroutines.launch

private const val GRID_COLUMN_COUNT = 5
private const val COPY_ACTION_LABEL = "복사"

@Composable
fun DSWantedIconDemoScreen(
    viewModel: DSWantedIconDemoViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is DSWantedIconDemoSideEffect.ShowIconNameSnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(
                        visuals = WantedSnackbarVisuals(
                            message = sideEffect.iconName,
                            actionLabel = COPY_ACTION_LABEL
                        )
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        clipboardManager.setText(AnnotatedString(sideEffect.iconName))
                    }
                }
            }
        }
    }

    DSWantedIconDemoScreenContent(
        viewState = viewState,
        snackbarHostState = snackbarHostState
    ) { viewEvent ->
        when (viewEvent) {
            DSWantedIconDemoViewEvent.OnClickBack -> onClickBack()
            is DSWantedIconDemoViewEvent.OnQueryChange -> {
                viewModel.setEvent(DSWantedIconDemoEvent.SetQuery(viewEvent.query))
            }

            is DSWantedIconDemoViewEvent.OnSelectBackground -> {
                viewModel.setEvent(DSWantedIconDemoEvent.SetBackground(viewEvent.background))
            }

            is DSWantedIconDemoViewEvent.OnClickIcon -> {
                viewModel.setEvent(DSWantedIconDemoEvent.ClickIcon(viewEvent.iconName))
            }
        }
    }
}

@Composable
private fun DSWantedIconDemoScreenContent(
    modifier: Modifier = Modifier,
    viewState: DSWantedIconDemoViewState,
    snackbarHostState: SnackbarHostState,
    onViewEvent: (DSWantedIconDemoViewEvent) -> Unit
) {
    val resources = LocalResources.current
    val iconNames = remember(viewState.icons) {
        viewState.icons.associate { item -> item.resId to resources.getResourceEntryName(item.resId) }
    }
    val indexedIcons = remember(viewState.icons) {
        viewState.icons.map { item ->
            item to buildIconSearchIndex(
                resourceName = iconNames[item.resId].orEmpty(),
                keywords = item.keywords
            )
        }
    }
    val filteredIcons = remember(indexedIcons, viewState.query) {
        if (viewState.query.isBlank()) {
            indexedIcons.map { (item, _) -> item }
        } else {
            indexedIcons.mapNotNull { (item, index) ->
                item.takeIf { matchesIconQuery(query = viewState.query, index = index) }
            }
        }
    }
    val newIcons = remember(filteredIcons) { filteredIcons.filter { it.isNew } }
    val otherIcons = remember(filteredIcons) { filteredIcons.filterNot { it.isNew } }
    val previewBackground = iconDemoBackgroundColor(viewState.background)

    Scaffold(
        modifier = modifier,
        topBar = {
            WantedBackTopAppBar(title = "Icon") {
                onViewEvent(DSWantedIconDemoViewEvent.OnClickBack)
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
                placeholder = "아이콘 검색 (영문·한글·초성)",
                size = WantedSearchFieldDefaults.Size.Large,
                onValueChange = { onViewEvent(DSWantedIconDemoViewEvent.OnQueryChange(it)) }
            )
            IconDemoBackgroundPicker(
                selected = viewState.background,
                onSelect = { onViewEvent(DSWantedIconDemoViewEvent.OnSelectBackground(it)) }
            )
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(GRID_COLUMN_COUNT)
            ) {
                iconSection(
                    title = "신규",
                    icons = newIcons,
                    iconNames = iconNames,
                    previewBackground = previewBackground,
                    keyPrefix = "new",
                    onClickIcon = { onViewEvent(DSWantedIconDemoViewEvent.OnClickIcon(it)) }
                )
                iconSection(
                    title = "전체",
                    icons = otherIcons,
                    iconNames = iconNames,
                    previewBackground = previewBackground,
                    keyPrefix = "all",
                    onClickIcon = { onViewEvent(DSWantedIconDemoViewEvent.OnClickIcon(it)) }
                )
            }
        }
    }
}

private fun LazyGridScope.iconSection(
    title: String,
    icons: List<DSWantedIconItem>,
    iconNames: Map<Int, String>,
    previewBackground: Color,
    keyPrefix: String,
    onClickIcon: (String) -> Unit
) {
    if (icons.isEmpty()) return

    item(
        key = "header_$keyPrefix",
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            text = title,
            style = DesignSystemTheme.typography.body1Bold,
            color = DesignSystemTheme.colors.foregroundNeutralStrong
        )
    }
    items(
        items = icons,
        key = { item -> "${keyPrefix}_${item.resId}" }
    ) { item ->
        val iconName = iconNames[item.resId].orEmpty()
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(6.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(previewBackground)
                .clickable { onClickIcon(iconName) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(item.resId),
                contentDescription = iconName
            )
        }
    }
}

@Composable
private fun IconDemoBackgroundPicker(
    selected: IconDemoBackground,
    onSelect: (IconDemoBackground) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconDemoBackground.entries.forEach { background ->
            val isSelected = background == selected
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(iconDemoBackgroundColor(background))
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) {
                                DesignSystemTheme.colors.surfaceBrandPrimary
                            } else {
                                DesignSystemTheme.colors.foregroundNeutralQuaternary
                            },
                            shape = CircleShape
                        )
                        .clickable { onSelect(background) }
                )
                Text(
                    text = background.label,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = if (isSelected) {
                        DesignSystemTheme.colors.foregroundNeutralStrong
                    } else {
                        DesignSystemTheme.colors.foregroundNeutralSecondary
                    }
                )
            }
        }
    }
}

@Composable
private fun iconDemoBackgroundColor(background: IconDemoBackground): Color = when (background) {
    IconDemoBackground.Default -> DesignSystemTheme.colors.backgroundNeutralPrimary
    IconDemoBackground.Alternative -> DesignSystemTheme.colors.backgroundNeutralSecondary
    IconDemoBackground.White -> DesignSystemTheme.colors.staticWhite
    IconDemoBackground.Dark -> DesignSystemTheme.colors.surfaceNeutralInverse
    IconDemoBackground.Black -> DesignSystemTheme.colors.staticBlack
}

@DevicePreviews
@Composable
private fun DSWantedIconDemoScreenPreview() {
    DesignSystemTheme {
        DSWantedIconDemoScreenContent(
            viewState = DSWantedIconDemoViewState(),
            snackbarHostState = remember { SnackbarHostState() },
            onViewEvent = { }
        )
    }
}
