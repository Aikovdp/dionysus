package me.aikovdp.dionysus.ui.screens.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.data.Movie
import me.aikovdp.dionysus.data.WatchlistEntry
import me.aikovdp.dionysus.ui.theme.Typography
import java.time.Instant

@Composable
fun WatchlistScreen(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    WatchlistGrid(items = uiState.items, navigateToMovieDetails = navigateToMovieDetails, modifier = modifier)
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

@Composable
fun WatchlistGrid(
    items: List<WatchlistEntry>,
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) {
            WatchlistGridEntry(it, navigateToMovieDetails)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WatchlistGridEntry(
    entry: WatchlistEntry,
    navigateToMovieDetails: (Int) -> Unit,
) {
    ElevatedCard(
        onClick = { navigateToMovieDetails(entry.movie.id) },
        modifier = Modifier
    ) {
        AsyncImage(
            model = entry.movie.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = entry.movie.title,
            style = Typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistGridPreview() {
    val list = listOf(
        WatchlistEntry(
            1,
            Movie(
                1,
                "La La Land",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg"
            ),
            Instant.now()
        ),
        WatchlistEntry(
            2,
            Movie(
                2,
                "Parasite",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
            ),
            Instant.now()
        )
    )

    WatchlistGrid(list, {})
}
