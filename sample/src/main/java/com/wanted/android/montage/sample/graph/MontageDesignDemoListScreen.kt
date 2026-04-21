package com.wanted.android.montage.sample.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import com.wanted.android.wanted.design.input.search.WantedSearchField
import com.wanted.android.wanted.design.input.search.WantedSearchFieldDefaults
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBar

@Composable
fun MontageDesignDemoListScreen(
    items: List<MontageDesignDemoNavContract>,
    onSelect: (MontageDesignDemoNavContract) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val filteredItems = remember(items, query) {
        if (query.isBlank()) {
            items
        } else {
            items.filter {
                it::class.simpleName.orEmpty().contains(query, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            WantedTopAppBar(
                title = {
                    Text(text = "Montage Design Demo")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            WantedSearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                text = query,
                placeholder = "검색어를 입력해주세요",
                size = WantedSearchFieldDefaults.Size.Medium(),
                onValueChange = { query = it }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = filteredItems,
                    key = { item -> item::class.simpleName ?: item.hashCode() }
                ) { item ->
                    WantedListCell(
                        modifier = Modifier.fillMaxWidth(),
                        text = item::class.simpleName.orEmpty(),
                        verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                        fillWidth = true,
                        divider = true,
                        ellipsis = true,
                        chevrons = true,
                        onClick = { onSelect(item) }
                    )
                }
            }
        }
    }
}