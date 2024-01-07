package me.aikovdp.dionysus.ui.screens.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.data.MovieDetails
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val movie = uiState.movie ?: return
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DiaryDatePicker(
            onConfirm = { viewModel.addToWatchlist(it) },
            close = { openDialog = false }
        )
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(movie.title) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
            )
        },
        floatingActionButton = {
            MovieFabs(
                onDiaryFabClick = { openDialog = true },
                onWatchlistFabClick = { viewModel.toggleInWatchlist() },
                isInWatchlist = uiState.isInWatchlist
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp + 56.dp + 24.dp + 40.dp + 16.dp
            ),
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item {
                Card {
                    AsyncImage(
                        model = movie.backdropUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                MainInformation(movie)
            }

            item {
                MovieDescription(movie)
            }
        }
    }
}

@Composable
private fun MovieDescription(movie: MovieDetails) {
    SurfaceContainer {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = movie.tagline,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun MainInformation(movie: MovieDetails) {
    SurfaceContainer {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineLarge
            )
            val formattedReleaseDate = movie.releaseDate
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
            Text(
                text = "$formattedReleaseDate  •  ${movie.runtime}",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun SurfaceContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 3.dp,
        modifier = modifier
            .fillMaxWidth()
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDatePicker(onConfirm: (Long?) -> Unit, close: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = close,
        confirmButton = {
            TextButton(
                onClick = {
                    close()
                    onConfirm(datePickerState.selectedDateMillis)
                },
                enabled = confirmEnabled
            ) {
                Text(stringResource(R.string.add_to_diary))
            }
        },
        dismissButton = {
            TextButton(onClick = close) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
