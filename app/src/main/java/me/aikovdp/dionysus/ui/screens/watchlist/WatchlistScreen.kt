package me.aikovdp.dionysus.ui.screens.watchlist

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.aikovdp.dionysus.R

@Composable
fun WatchlistScreen(
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = modifier) {
        items(uiState.items) {
            Text(text = it.movie.title)
        }
    }
}

@Composable
fun WatchlistFab(onClick: () -> Unit) {
    LargeFloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.BookmarkAdd,
            contentDescription = stringResource(R.string.add_watchlist_entry),
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
        )
    }
}
