package me.aikovdp.dionysus.ui.screens.diary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.ui.search.SearchBarScaffold

@Composable
fun DiaryScreen(
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBarScaffold(
        navigateToMovieDetails = navigateToMovieDetails,
        modifier = modifier
            .testTag(stringResource(R.string.test_tag_diary_content))
    ) { paddingValues ->
        Text("Diary", modifier.padding(paddingValues))
    }
}
