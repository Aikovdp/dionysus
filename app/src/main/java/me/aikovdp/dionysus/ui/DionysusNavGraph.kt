package me.aikovdp.dionysus.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.ui.screens.diary.DiaryFab
import me.aikovdp.dionysus.ui.screens.diary.DiaryScreen
import me.aikovdp.dionysus.ui.screens.movie.MovieDetailScreen
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistFab
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = DionysusDestinations.WATCHLIST_ROUTE
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: startDestination
    val screenTitle = TOP_LEVEL_DESTINATIONS.find { it.route == selectedDestination }?.iconTextId ?: R.string.movie
    val navActions = remember(navController) {
        DionysusNavigationActions(navController)
    }

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
                    DionysusDestinations.WATCHLIST_ROUTE -> WatchlistFab(onClick = { /*TODO*/ })
                    DionysusDestinations.DIARY_ROUTE -> DiaryFab(onClick = { /*TODO*/ })
                }

            }

        },
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_DESTINATIONS.forEach { item ->
                    val selected = selectedDestination == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = { navActions.navigateTo(item) },
                        icon = {
                            Icon(
                                if (selected) item.selectedIcon else item.unselectedIcon,
                                stringResource(item.iconTextId)
                            )
                        },
                        label = { Text(stringResource(item.iconTextId)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = startDestination) {

            composable(DionysusDestinations.WATCHLIST_ROUTE) {
                WatchlistScreen(
                    navigateToMovieDetails = { navActions.navigateToMovieDetail(it) },
                    modifier = Modifier.padding(paddingValues),
                )
            }
            composable(DionysusDestinations.DIARY_ROUTE) {
                DiaryScreen(Modifier.padding(paddingValues))
            }
            composable(DionysusDestinations.MOVIE_DETAIL_ROUTE) {
                MovieDetailScreen(Modifier.padding(paddingValues))
            }
        }

    }
}
