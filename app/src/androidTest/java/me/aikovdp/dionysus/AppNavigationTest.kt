package me.aikovdp.dionysus

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.aikovdp.dionysus.data.WatchlistRepository
import me.aikovdp.dionysus.ui.MainNavigation
import me.aikovdp.dionysus.ui.theme.DionysusTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class AppNavigationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var watchlistRepository: WatchlistRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun bottomBarNavigationFromWatchlistToDiary() = runTest {
        setContent()

        // Open diary screen
        composeTestRule.onNodeWithText(activity.getString(R.string.diary))
            .performClick()
        // Check that the diary screen was opened
        composeTestRule.onNodeWithTag(activity.getString(R.string.test_tag_diary_content))
            .assertIsDisplayed()

        // Open watchlist screen
        composeTestRule.onNodeWithText(activity.getString(R.string.watchlist))
            .performClick()
        // Check that the watchlist screen was opened
        composeTestRule.onNodeWithTag(activity.getString(R.string.test_tag_watchlist_content))
            .assertIsDisplayed()
    }

    @Test
    fun movieDetailScreenFromWatchlist_upButton_returnsToWatchlist() = runTest {
        val laLaLandId = 313369
        if (!watchlistRepository.containsMovieStream(laLaLandId).first()) {
            watchlistRepository.createEntry(313369)
        }

        setContent()
        // Navigate to the movie detail screen
        composeTestRule.onNodeWithText("La La Land").performClick()

        // Click the back button
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.back))
            .performClick()
        // Check that the watchlist screen was opened again
        composeTestRule.onNodeWithTag(activity.getString(R.string.test_tag_watchlist_content))
            .assertIsDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            DionysusTheme {
                MainNavigation()
            }
        }
    }
}
