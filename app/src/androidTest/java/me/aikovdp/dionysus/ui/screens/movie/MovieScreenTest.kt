package me.aikovdp.dionysus.ui.screens.movie

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.aikovdp.dionysus.HiltTestActivity
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.data.DiaryRepository
import me.aikovdp.dionysus.data.MovieRepository
import me.aikovdp.dionysus.data.WatchlistRepository
import me.aikovdp.dionysus.ui.DionysusDestinationArgs.MOVIE_ID_ARG
import me.aikovdp.dionysus.ui.theme.DionysusTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MovieScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var watchlistRepository: WatchlistRepository

    @Inject
    lateinit var diaryRepository: DiaryRepository


    @Before
    fun setup() {
        hiltRule.inject()
        val laLaLandId = 313369

        // GIVEN - On the "Add Task" screen.
        composeTestRule.setContent {
            DionysusTheme {
                MovieDetailScreen(
                    navigateUp = {},
                    modifier = Modifier.fillMaxHeight(),
                    viewModel = MovieDetailViewModel(
                        movieRepository,
                        watchlistRepository,
                        diaryRepository,
                        SavedStateHandle(mapOf(MOVIE_ID_ARG to laLaLandId.toString()))
                    )
                )
            }
        }
    }

    @Test
    fun addToWatchlistButton_togglesCorrectly() = runTest {
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag(activity.getString(R.string.watchlist_toggle_fab))
                .isDisplayed()
        }
        // Add the entry
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.add_to_watchlist))
            .performClick()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.remove_from_watchlist))
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.remove_from_watchlist))
            .performClick()

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.add_to_watchlist))
            .assertIsDisplayed()
    }
}
