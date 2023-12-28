package me.aikovdp.dionysus.ui.screens.diary

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.aikovdp.dionysus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(stringResource(R.string.diary)) }) }
    ) { paddingValues ->
        Text("Diary", modifier.padding(paddingValues))
    }
}

@Composable
fun DiaryFab(onClick: () -> Unit) {
    LargeFloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Create,
            contentDescription = stringResource(R.string.add_diary_entry),
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
        )
    }
}
