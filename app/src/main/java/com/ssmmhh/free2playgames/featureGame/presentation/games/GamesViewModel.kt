package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.domain.model.NetworkError
import com.ssmmhh.free2playgames.featureGame.domain.useCases.GetGamesUseCase
import com.ssmmhh.free2playgames.featureGame.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GamesViewModel
@Inject
constructor(
    private val getGamesUseCase: GetGamesUseCase
) : BaseViewModel() {

    private val _gameListUiState = MutableStateFlow<GameListUiState>(GameListUiState.Loading)
    val gameListUiState: StateFlow<GameListUiState> = _gameListUiState

    init {
        fetchGames()
    }

    private fun fetchGames() {
        _gameListUiState.update { GameListUiState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            val requestResult = getGamesUseCase.invoke()
            _gameListUiState.update {
                if (requestResult is Result.Success) {
                    GameListUiState.HasGames(requestResult.data)
                } else {
                    GameListUiState.Failed(error = NetworkError.UNKNOWN, retry = { fetchGames() })
                }
            }
        }
    }
}
