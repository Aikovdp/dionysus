package me.aikovdp.dionysus.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchActive by remember { mutableStateOf(false)}
    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::search,
            onSearch = viewModel::search,
            active = searchActive,
            onActiveChange = { searchActive = !searchActive },
            placeholder = { Text("Search movies")},
            leadingIcon = { Icon(Icons.Default.Search, "Search")},
            modifier = Modifier.align(Alignment.TopCenter)
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
}
