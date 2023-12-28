package me.aikovdp.dionysus.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import me.aikovdp.dionysus.ui.DionysusDestinationArgs.MOVIE_ID_ARG
import me.aikovdp.dionysus.ui.DionysusScreens.DIARY_SCREEN
import me.aikovdp.dionysus.ui.DionysusScreens.MOVIE_DETAIL_SCREEN
import me.aikovdp.dionysus.ui.DionysusScreens.WATCHLIST_SCREEN

/**
 * Screens used in [DionysusDestinations]
 */
private object DionysusScreens {
    const val WATCHLIST_SCREEN = "watchlist"
    const val DIARY_SCREEN = "diary"
    const val MOVIE_DETAIL_SCREEN = "movie"
}

/**
 * Arguments used in [DionysusDestinations] routes
 */
object DionysusDestinationArgs {
    const val MOVIE_ID_ARG = "movieId"
}

/**
 * Destinations used in the [MainActivity]
 */
object DionysusDestinations {
    const val WATCHLIST_ROUTE = WATCHLIST_SCREEN
    const val DIARY_ROUTE = DIARY_SCREEN
    const val MOVIE_DETAIL_ROUTE = "$MOVIE_DETAIL_SCREEN/{$MOVIE_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class DionysusNavigationActions(private val navController: NavHostController) {
    fun navigateToWatchlist() {
        navController.navigate(DionysusDestinations.WATCHLIST_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToDiary() {
        navController.navigate(DionysusDestinations.DIARY_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToMovieDetail(movieId: String) {
        navController.navigate("$MOVIE_DETAIL_SCREEN/$movieId")
    }
}
