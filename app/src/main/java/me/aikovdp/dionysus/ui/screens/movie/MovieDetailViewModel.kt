package me.aikovdp.dionysus.ui.screens.movie

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
import me.aikovdp.dionysus.ui.DionysusDestinationArgs
import me.aikovdp.dionysus.util.Async
import me.aikovdp.dionysus.util.WhileUiSubscribed
import javax.inject.Inject

data class MovieDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: String = savedStateHandle[DionysusDestinationArgs.MOVIE_ID_ARG]!!

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _movieAsync = movieRepository.getMovieStream(movieId.toInt())
        .map { handleMovie(it) }
        .catch { emit(Async.Error(R.string.loading_movie_error)) }

    val uiState: StateFlow<MovieDetailUiState> = combine(
        _userMessage, _isLoading, _movieAsync
    ) { userMessage, isLoading, movieAsync ->
        when (movieAsync) {
            Async.Loading -> {
                MovieDetailUiState(isLoading = true)
            }

            is Async.Error -> {
                MovieDetailUiState(
                    userMessage = movieAsync.errorMessage
                )
            }

            is Async.Success -> {
                MovieDetailUiState(
                    movie = movieAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = MovieDetailUiState(isLoading = true)
        )

    private fun handleMovie(movie: Movie?): Async<Movie?> {
        if (movie == null) {
            return Async.Error(R.string.movie_not_found)
        }
        return Async.Success(movie)
    }
}
