package com.ssmmhh.free2playgames.feature_game.presentation.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGamesUseCase
import com.ssmmhh.free2playgames.feature_game.presentation.games.viewstate.GameListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel
@Inject
constructor(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _gameListViewState = MutableStateFlow(GameListViewState(isLoading = true))
    val gameListViewState: StateFlow<GameListViewState> = _gameListViewState

    init {
        getGames()
    }

    fun refreshGames() = getGames()

    private fun getGames() {
        //set is loading to true
        _gameListViewState.value = GameListViewState(isLoading = true)
        //retrieve data from api
        viewModelScope.launch {
            val result = getGamesUseCase.invoke()
            _gameListViewState.value = when (result) {
                is Result.Success -> GameListViewState(
                    games = result.data
                )
                is Result.Error -> GameListViewState(
                    errorMessage = result.exception.message ?: "An unexpected error occurred!"
                )
                is Result.Loading -> GameListViewState(
                    isLoading = true
                )
            }
        }
    }

}