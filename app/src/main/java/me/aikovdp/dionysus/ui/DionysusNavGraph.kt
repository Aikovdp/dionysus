package me.aikovdp.dionysus.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.aikovdp.dionysus.ui.screens.diary.DiaryScreen
import me.aikovdp.dionysus.ui.screens.movie.MovieDetailScreen
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistScreen

@Composable
@Preview
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = DionysusDestinations.WATCHLIST_ROUTE
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: startDestination
    val navActions = remember(navController) {
        DionysusNavigationActions(navController)
    }

    Scaffold(
        bottomBar = {
            DionysusNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navActions::navigateTo
            )
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
                DiaryScreen(
                    navigateToMovieDetails = { navActions.navigateToMovieDetail(it) },
                    modifier = Modifier.padding(paddingValues),
                )
            }
            composable(DionysusDestinations.MOVIE_DETAIL_ROUTE) {
                MovieDetailScreen(Modifier.padding(paddingValues))
            }
        }
    }
}
