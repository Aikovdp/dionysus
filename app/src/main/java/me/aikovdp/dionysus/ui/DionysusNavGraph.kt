package me.aikovdp.dionysus.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.aikovdp.dionysus.ui.screens.diary.DiaryScreen
import me.aikovdp.dionysus.ui.screens.movie.MovieDetailScreen
import me.aikovdp.dionysus.ui.screens.watchlist.WatchlistScreen

@OptIn(
    ExperimentalMaterial3AdaptiveNavigationSuiteApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)
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

    val adaptiveInfo = currentWindowAdaptiveInfo()
    // Custom configuration that shows a navigation drawer in large screens.
    val customNavSuiteType =
        if (adaptiveInfo.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }

    NavigationSuiteScaffold(
        layoutType = customNavSuiteType,
        navigationSuiteItems = navigationSuiteItems(selectedDestination, navActions::navigateTo)
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(DionysusDestinations.WATCHLIST_ROUTE) {
                WatchlistScreen(
                    navigateToMovieDetails = navActions::navigateToMovieDetail
                )
            }
            composable(DionysusDestinations.DIARY_ROUTE) {
                DiaryScreen(
                    navigateToMovieDetails = navActions::navigateToMovieDetail
                )
            }
            composable(DionysusDestinations.MOVIE_DETAIL_ROUTE) {
                MovieDetailScreen(
                    navigateUp = navController::navigateUp
                )
            }
        }
    }
}
