package com.ssmmhh.free2playgames.feature_game.presentation.games

import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.common.*
import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.data.util.takeStringRes
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGamesUseCase
import com.ssmmhh.free2playgames.feature_game.presentation.common.BaseViewModel
import com.ssmmhh.free2playgames.feature_game.presentation.games.viewstate.GameListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GamesViewModel
@Inject
constructor(
    private val getGamesUseCase: GetGamesUseCase
) : BaseViewModel() {

    private val _gameListViewState = MutableStateFlow(GameListViewState(isLoading = true))
    val gameListViewState: StateFlow<GameListViewState> = _gameListViewState

    init {
        getGames()
    }

    fun refreshGames() = getGames()

    private fun getGames() {
        //set is loading to true
        _gameListViewState.value = _gameListViewState.value.copy(isLoading = true)
        //retrieve data from api
        viewModelScope.launch {
            val result = getGamesUseCase.invoke()
            _gameListViewState.value = _gameListViewState.value.let { vs ->
                when (result) {
                    is Result.Success -> GameListViewState(
                        isLoading = false,
                        games = result.data
                    )

                    is Result.Error -> {
                        appendToMessageQueue(gameListError(exception = result.exception))
                        vs.copy(isLoading = false)
                    }

                    is Result.Loading -> vs.copy(isLoading = true)
                }
            }
        }
    }

    private fun gameListError(exception: Exception): StateMessage = StateMessage(
        Response(
            message = exception.takeStringRes(R.string.unknown_error),
            uiComponentType = UIComponentType.TryAgainDialogForError(
                tryAgain = { getGames() }
            ),
            messageType = MessageType.Error
        )
    )
}