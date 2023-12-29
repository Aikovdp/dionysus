package me.aikovdp.dionysus.ui.screens.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.aikovdp.dionysus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { uiState.movie?.title?.let { Text(it) } }) },
        modifier = modifier
    ) { paddingValues ->
        Column {
            Text(
                text = uiState.movie?.title ?: if (uiState.isLoading) "Loading" else stringResource(uiState.userMessage ?: R.string.movie_not_found),
                modifier = Modifier.padding(paddingValues)
            )
            AsyncImage(model = uiState.movie?.posterUrl, contentDescription = uiState.movie?.title)
        }

    }

}

@Composable
private fun AddToDiaryFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Create,
                contentDescription = stringResource(R.string.add_to_diary)
            )
        },
        text = { Text(stringResource(R.string.add_to_diary)) }
    )
}

@Composable
private fun AddToWatchlistFab(onClick: () -> Unit, isInWatchlist: Boolean) {
    SmallFloatingActionButton(onClick = onClick) {
        if (isInWatchlist)
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(R.string.remove_from_watchlist)
            )
        else
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = stringResource(R.string.add_to_watchlist)
            )
    }
}

@Composable
fun MovieFabs(
    onDiaryFabClick: () -> Unit,
    onWatchlistFabClick: () -> Unit,
    isInWatchlist: Boolean
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AddToWatchlistFab(onClick = onWatchlistFabClick, isInWatchlist)
        AddToDiaryFab(onClick = onDiaryFabClick)
    }
}

@Preview
@Composable
fun MovieFabsPreview() {
    var isInWatchlist by remember { mutableStateOf(false) }
    MovieFabs(
        onDiaryFabClick = { },
        onWatchlistFabClick = { isInWatchlist = !isInWatchlist },
        isInWatchlist = isInWatchlist
    )
}
