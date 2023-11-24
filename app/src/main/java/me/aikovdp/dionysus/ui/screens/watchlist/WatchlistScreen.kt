package me.aikovdp.dionysus.ui.screens.watchlist

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.aikovdp.dionysus.R

@Composable
fun WatchlistScreen(modifier: Modifier = Modifier) {
    Text("Watchlist", modifier)
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
