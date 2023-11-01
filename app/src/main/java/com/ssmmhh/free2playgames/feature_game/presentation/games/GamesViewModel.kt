package com.ssmmhh.free2playgames.feature_game.presentation.games

import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.feature_game.data.util.getDataIfSucceeded
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGamesUseCase
import com.ssmmhh.free2playgames.feature_game.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

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