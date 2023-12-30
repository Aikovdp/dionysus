package me.aikovdp.dionysus.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import me.aikovdp.dionysus.MainActivity
import me.aikovdp.dionysus.R
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
    fun navigateToMovieDetail(movieId: Int) {
        navController.navigate("$MOVIE_DETAIL_SCREEN/$movieId")
    }

    fun navigateTo(destination: DionysusTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id)
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
        }
    }
}

data class DionysusTopLevelDestination (
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    DionysusTopLevelDestination(
        route = DionysusDestinations.WATCHLIST_ROUTE,
        selectedIcon = Icons.Default.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        iconTextId = R.string.watchlist
    ),
    DionysusTopLevelDestination(
        route = DionysusDestinations.DIARY_ROUTE,
        selectedIcon = Icons.Default.Book,
        unselectedIcon = Icons.Outlined.Book,
        iconTextId = R.string.diary
    ),
)
