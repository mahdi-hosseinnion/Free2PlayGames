package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.featureGame.data.util.getDataIfSucceeded
import com.ssmmhh.free2playgames.featureGame.domain.useCases.GetGamesUseCase
import com.ssmmhh.free2playgames.featureGame.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        getGames()
    }

    private fun getGames() {
        _gameListUiState.value = GameListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _gameListUiState.value =
                GameListUiState.HasGames(getGamesUseCase.invoke().getDataIfSucceeded()!!)
        }
    }
}
