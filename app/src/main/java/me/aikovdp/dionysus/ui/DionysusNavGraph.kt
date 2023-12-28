package me.aikovdp.dionysus.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.ui.screens.diary.DiaryFab
import me.aikovdp.dionysus.ui.screens.diary.DiaryScreen
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistFab
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: DionysusScreen.Watchlist.name
    val screenTitle = DionysusScreen.valueOf(selectedDestination).title

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(screenTitle)) })
        },
        floatingActionButton = {
            AnimatedContent(
                targetState = selectedDestination,
                transitionSpec = { scaleIn() togetherWith scaleOut() },
                label = "FAB Enter / Exit"
            ) { dest ->
                when (dest) {
                    DionysusScreen.Watchlist.name -> WatchlistFab(onClick = { /*TODO*/ })
                    DionysusScreen.Diary.name -> DiaryFab(onClick = { /*TODO*/ })
                }

            }

        },
        bottomBar = {
            NavigationBar {
                listOf(DionysusScreen.Watchlist, DionysusScreen.Diary).forEach { item ->
                    val selected = selectedDestination == item.name
                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigate(item.name) },
                        icon = {
                            Icon(
                                if (selected) item.selectedIcon else item.icon,
                                stringResource(item.title)
                            )
                        },
                        label = { Text(stringResource(item.title)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = DionysusScreen.Watchlist.name) {

            composable(DionysusScreen.Watchlist.name) {
                WatchlistScreen(Modifier.padding(paddingValues))
            }
            composable(DionysusScreen.Diary.name) {
                DiaryScreen(Modifier.padding(paddingValues))
            }
        }

    }
}

enum class DionysusScreen(
    @StringRes val title: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
) {
    Watchlist(R.string.watchlist, Icons.Outlined.BookmarkBorder, Icons.Default.Bookmark),
    Diary(R.string.diary, Icons.Outlined.Book, Icons.Default.Book)
}
