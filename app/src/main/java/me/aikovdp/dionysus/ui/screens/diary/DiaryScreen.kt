package me.aikovdp.dionysus.ui.screens.diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.data.DiaryEntry
import me.aikovdp.dionysus.ui.search.SearchBarScaffold
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DiaryScreen(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiaryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchBarScaffold(
        navigateToMovieDetails = navigateToMovieDetails,
        modifier = modifier
            .testTag(stringResource(R.string.test_tag_diary_content))
    ) { paddingValues ->
        Text("Diary", modifier.padding(paddingValues))
        DiaryContent(
            diaryEntries = uiState.items,
            navigateToMovieDetails = navigateToMovieDetails,
            contentPadding = paddingValues
        )
    }
}

@Composable
fun DiaryContent(
    diaryEntries: List<DiaryEntry>,
    navigateToMovieDetails: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding
    ) {
        items(diaryEntries) {
            DiaryListEntry(
                diaryEntry = it,
                navigateToMovieDetails = navigateToMovieDetails
            )
        }
    }
}

@Composable
fun DiaryListEntry(
    diaryEntry: DiaryEntry,
    navigateToMovieDetails: (Int) -> Unit,
) {
    val formattedDate = diaryEntry.added.format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    )
    ListItem(
        headlineContent = { Text(diaryEntry.movie.title) },
        leadingContent = {
            Box(
                modifier = Modifier
                    .widthIn(max = 56.dp.times(0.675F))
            ) {
                AsyncImage(
                    model = diaryEntry.movie.posterUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .clip(MaterialTheme.shapes.extraSmall)
                        .aspectRatio(0.675F)
                )
            }
        },
        supportingContent = { Text(formattedDate) },
        modifier = Modifier.clickable { navigateToMovieDetails(diaryEntry.movie.id) }
    )
}
