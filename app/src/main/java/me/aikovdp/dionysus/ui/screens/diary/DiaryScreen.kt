package me.aikovdp.dionysus.ui.screens.diary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.aikovdp.dionysus.ui.screens.search.SearchBarScaffold

@Composable
fun DiaryScreen(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBarScaffold(
        navigateToMovieDetails = navigateToMovieDetails,
        modifier = modifier
    ) { paddingValues ->
        Text("Diary", modifier.padding(paddingValues))
    }
}
