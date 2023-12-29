package me.aikovdp.dionysus.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DionysusSearchBar(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchActive by remember { mutableStateOf(false) }

    SearchBar(
        query = uiState.searchQuery,
        onQueryChange = viewModel::search,
        onSearch = viewModel::search,
        active = searchActive,
        onActiveChange = { searchActive = !searchActive },
        placeholder = { Text("Search movies") },
        leadingIcon = { Icon(Icons.Default.Search, "Search") },
        modifier = modifier
    ) {
        LazyColumn {
            items(uiState.items) {
                ListItem(
                    headlineContent = { Text(it.title) },
                    modifier = Modifier.clickable { navigateToMovieDetails(it.id) }
                )
            }
        }
    }
}

@Composable
fun SearchBarScaffold(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(paddingValues: PaddingValues) -> Unit)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }
    ) {
        DionysusSearchBar(
            navigateToMovieDetails = navigateToMovieDetails,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f }
        )
        content(PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp))
    }
}
