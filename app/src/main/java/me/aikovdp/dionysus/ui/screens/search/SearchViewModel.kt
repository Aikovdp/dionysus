package me.aikovdp.dionysus.ui.screens.search

import androidx.lifecycle.SavedStateHandle
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
import me.aikovdp.dionysus.data.Movie
import me.aikovdp.dionysus.data.MovieRepository
import me.aikovdp.dionysus.util.Async
import me.aikovdp.dionysus.util.WhileUiSubscribed
import javax.inject.Inject

data class SearchUiState(
    val searchQuery: String = "",
    val items: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _savedSearchQuery = savedStateHandle.getStateFlow(MOVIE_SEARCH_SAVED_STATE_KEY, "")
    private val _searchResultsAsync = _savedSearchQuery.map { movieRepository.searchMovies(it) }
            .map { Async.Success(it) }
            .catch<Async<List<Movie>>> { emit(Async.Error(R.string.loading_search_results_error)) }

    val uiState: StateFlow<SearchUiState> = combine(
        _isLoading,
        _userMessage,
        _savedSearchQuery,
        _searchResultsAsync
    ) { isLoading, userMessage, searchQuery, searchResultsAsync ->
        when (searchResultsAsync) {
            Async.Loading -> {
                SearchUiState(searchQuery = searchQuery, isLoading = true)
            }

            is Async.Error -> {
                SearchUiState(
                    searchQuery = searchQuery,
                    userMessage = searchResultsAsync.errorMessage
                )
            }

            is Async.Success -> {
                SearchUiState(
                    searchQuery = searchQuery,
                    items = searchResultsAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = SearchUiState(isLoading = true)
    )

    fun search(query: String) {
        savedStateHandle[MOVIE_SEARCH_SAVED_STATE_KEY] = query
    }
}

const val MOVIE_SEARCH_SAVED_STATE_KEY = "MOVIE_SEARCH_SAVED_STATE_KEY"
