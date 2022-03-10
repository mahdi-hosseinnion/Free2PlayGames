package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.common.*
import com.ssmmhh.free2playgames.feature_game.data.util.Result
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

    private val TAG = "GamesViewModel"

    private val _gameListViewState = MutableStateFlow(GameListViewState(isLoading = true))
    val gameListViewState: StateFlow<GameListViewState> = _gameListViewState

    private val _stateMessageQueue = MutableStateFlow(Queue<StateMessage>(emptyList()))
    val stateMessageQueue: StateFlow<Queue<StateMessage>> = _stateMessageQueue

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
                        appendToMessageQueue(
                            StateMessage(
                                Response(
                                    message = result.exception.message
                                        ?: "An unknown error occurred!",
                                    uiComponentType = UIComponentType.TryAgainDialogForError(
                                        tryAgain = { getGames() }
                                    ),
                                    messageType = MessageType.Error
                                )
                            )
                        )
                        vs.copy(isLoading = false)
                    }

                    is Result.Loading -> vs.copy(isLoading = true)
                }
            }
        }
    }

    fun appendToMessageQueue(stateMessage: StateMessage) {
        _stateMessageQueue.value.let {
            if (stateMessage.doesNotAlreadyExistInQueue(it) &&
                stateMessage.uiComponentTypeIsNotNone()
            ) {
                //TODO fix this problem(https://stackoverflow.com/a/66742924/10362460)
                _stateMessageQueue.value = Queue(
                    _stateMessageQueue.value.items.apply { add(stateMessage) }
                )
            }
        }
    }

    fun removeHeadFromQueue() {
        _stateMessageQueue.value = _stateMessageQueue.value.apply {
            try {
                remove()
            } catch (e: Queue.QueueException) {
                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from messageQueue")
            }
        }

    }
}