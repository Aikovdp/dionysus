package me.aikovdp.dionysus.ui.screens.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.aikovdp.dionysus.R
import me.aikovdp.dionysus.data.WatchlistEntry
import me.aikovdp.dionysus.data.WatchlistRepository
import me.aikovdp.dionysus.util.Async
import me.aikovdp.dionysus.util.WhileUiSubscribed
import javax.inject.Inject

data class WatchlistUiState(
    val items: List<WatchlistEntry> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    watchlistRepository: WatchlistRepository
) : ViewModel() {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _watchlistEntriesAsync =
        watchlistRepository.getEntriesStream()
            .map { Async.Success(it) }
            .catch<Async<List<WatchlistEntry>>> {
                emit(Async.Error(R.string.loading_watchlist_error))
                Log.e("WatchlistViewModel", "Issue getting watchlist state", it)
            }

    val uiState: StateFlow<WatchlistUiState> = combine(
        _isLoading,
        _userMessage,
        _watchlistEntriesAsync
    ) { isLoading, userMessage, watchlistEntriesAsync ->
        when (watchlistEntriesAsync) {
            Async.Loading -> {
                WatchlistUiState(isLoading = true)
            }

            is Async.Error -> {
                WatchlistUiState(userMessage = watchlistEntriesAsync.errorMessage)
            }

            is Async.Success -> {
                WatchlistUiState(
                    items = watchlistEntriesAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = WatchlistUiState(isLoading = true)
    )
}
