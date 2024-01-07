package me.aikovdp.dionysus.ui.screens.diary

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
import me.aikovdp.dionysus.data.DiaryEntry
import me.aikovdp.dionysus.data.DiaryRepository
import me.aikovdp.dionysus.util.Async
import me.aikovdp.dionysus.util.WhileUiSubscribed
import javax.inject.Inject

data class DiaryUiState(
    val items: List<DiaryEntry> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class DiaryViewModel @Inject constructor(
    diaryRepository: DiaryRepository
) : ViewModel() {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _diaryEntriesAsync =
        diaryRepository.getEntriesStream()
            .map { Async.Success(it) }
            .catch<Async<List<DiaryEntry>>> {
                emit(Async.Error(R.string.loading_diary_error))
                Log.e("DiaryViewModel", "Issue getting diary state", it)
            }

    val uiState: StateFlow<DiaryUiState> = combine(
        _isLoading,
        _userMessage,
        _diaryEntriesAsync
    ) { isLoading, userMessage, diaryEntriesAsync ->
        when (diaryEntriesAsync) {
            Async.Loading -> {
                DiaryUiState(isLoading = true)
            }

            is Async.Error -> {
                DiaryUiState(userMessage = diaryEntriesAsync.errorMessage)
            }

            is Async.Success -> {
                DiaryUiState(
                    items = diaryEntriesAsync.data.sortedByDescending { it.added },
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = DiaryUiState(isLoading = true)
    )
}
