package com.example.opsc7311.ui.screens.list.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opsc7311.viewmodels.SharedViewModel


@Composable
fun FilterBar(
    sharedViewModel: SharedViewModel,
    filterBarViewModel: FilterBarViewModel
) {

    val filterBarUiState by filterBarViewModel.uiState.collectAsState()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        FilterBarDateItem(
            text = filterBarUiState.startDate,
            expanded = filterBarUiState.startDateFilterExpanded
        ) {
            filterBarViewModel.setStartDateFilterExpanded(!filterBarUiState.startDateFilterExpanded)
        }
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        FilterBarDateItem(
            text = filterBarUiState.endDate,
            expanded = filterBarUiState.endDateFilterExpanded
        ) {
            filterBarViewModel.setEndDateFilterExpanded(!filterBarUiState.endDateFilterExpanded)

        }
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        FilterBarItem(
            text = "Reset"
        ) {

        }
    }



}

@Preview
@Composable
fun PreviewTripleAppBar()
{
    FilterBar(
        sharedViewModel = viewModel(),
        filterBarViewModel = viewModel()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedSearchBarSample() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        // Talkback focus order sorts based on x and y position before considering z-index. The
        // extra Box with semantics and fillMaxWidth is a workaround to get the search bar to focus
        // before the content.
        Box(
            Modifier
                .semantics {
                    isContainer = true
                }
                .zIndex(1f)
                .fillMaxWidth()) {
            DockedSearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Hinted search text") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            modifier = Modifier.clickable {
                                text = resultText
                                active = false
                            }
                        )
                    }
                }
            }
        }
    }
}
