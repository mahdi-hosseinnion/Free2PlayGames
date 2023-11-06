package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.domain.model.DEFAULT
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions
import com.ssmmhh.free2playgames.featureGame.domain.model.NetworkError
import com.ssmmhh.free2playgames.featureGame.domain.useCases.GetGamesUseCase
import com.ssmmhh.free2playgames.featureGame.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Error
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GamesViewModel
@Inject
constructor(
    private val getGamesUseCase: GetGamesUseCase
) : BaseViewModel() {

    private val selectedGameFilter = MutableStateFlow<GameSortOptions>(GameSortOptions.DEFAULT)

    private val viewModelState = MutableStateFlow<HomeViewModelState>(
        HomeViewModelState(
            gameList = null,
            sortOption = GameSortOptions.DEFAULT,
            isLoading = true,
            error = null
        )
    )

    val gameListUiState: StateFlow<GameListUiState> = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelState.update { it.copy(isLoading = true, gameList = null) }
        viewModelScope.launch(Dispatchers.IO) {
            val requestResult = getGamesUseCase(
                sort = viewModelState.value.sortOption
            )
            viewModelState.update {
                if (requestResult is Result.Success) {
                    it.copy(isLoading = false, gameList = requestResult.data)
                } else {
                    val error = if (requestResult is Result.Error) NetworkError.UNKNOWN else null
                    it.copy(isLoading = false, gameList = null, error = error)
                }
            }
        }
    }

    fun changeSortOptionTo(sortOptions: GameSortOptions) {
        viewModelState.update { it.copy(sortOption = sortOptions) }
        fetchGames()
    }
}

data class HomeViewModelState(
    val gameList: List<Game>?,
    val isLoading: Boolean,
    val sortOption: GameSortOptions,
    val error: NetworkError?
) {
    fun toUiState(): GameListUiState =
        when {
            gameList != null && !isLoading -> GameListUiState.HasGames(
                gameList = gameList,
                sortOption = sortOption
            )

            isLoading -> GameListUiState.Loading
            else -> GameListUiState.Failed(error = error)
        }
}
