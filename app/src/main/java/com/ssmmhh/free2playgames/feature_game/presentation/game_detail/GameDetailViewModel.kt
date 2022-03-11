package com.ssmmhh.free2playgames.feature_game.presentation.game_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.common.MessageType
import com.ssmmhh.free2playgames.common.Response
import com.ssmmhh.free2playgames.common.StateMessage
import com.ssmmhh.free2playgames.common.UIComponentType
import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGameDetailByIdUseCase
import com.ssmmhh.free2playgames.feature_game.presentation.common.BaseViewModel
import com.ssmmhh.free2playgames.feature_game.presentation.game_detail.viewstate.GameDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel
@Inject
constructor(
    private val getGameDetailByIdUseCase: GetGameDetailByIdUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {

    private val _gameDetailViewState = MutableStateFlow(GameDetailViewState(isLoading = true))
    val gameDetailViewState: StateFlow<GameDetailViewState> = _gameDetailViewState

    private val _gameName = MutableStateFlow<String?>(state.get("gameTitle"))
    val gameName: StateFlow<String?> = _gameName

    private val _isGameDescriptionTextViewCollapsed = MutableStateFlow(true)
    val isGameDescriptionTextViewCollapsed: StateFlow<Boolean> = _isGameDescriptionTextViewCollapsed

    private var _imageSliderPosition: Int = 0

    init {
        getGameDetail()
    }

    fun refreshGameDetail() {
        getGameDetail()
    }

    fun getGamePlayNowUrl(): String? {
        gameDetailViewState.value.gameDetail?.let {
            return it.gameUrl
        }
        //TODO("show unable to load get game url, when dialog error system created")
        return null
    }

    fun getGameProfileUrl(): String? {
        gameDetailViewState.value.gameDetail?.let {
            return it.freeToGameProfileUrl
        }
        //TODO("show unable to load get game profile url, when dialog error system created")
        return null
    }

    fun reverseIsGameDescriptionTextViewCollapsed() {
        _isGameDescriptionTextViewCollapsed.value = !(_isGameDescriptionTextViewCollapsed.value)
    }

    fun onImageSliderPositionChanged(newPosition: Int) {
        _imageSliderPosition = newPosition
    }

    fun getImageSliderPosition() = _imageSliderPosition

    private fun getGameDetail() {
        gameId?.let {
            getGameDetailById(gameId = it)
        } ?: showUnableToRetrieveIdError()
    }

    private val gameId: Int? get() = state.get("gameId")

    private fun getGameDetailById(gameId: Int) {
        //set is loading to true
        _gameDetailViewState.value = _gameDetailViewState.value.copy(isLoading = true)
        //retrieve data from api
        viewModelScope.launch {
            val result = getGameDetailByIdUseCase.invoke(gameId)
            _gameDetailViewState.value = _gameDetailViewState.value.let { vs ->
                when (result) {
                    is Result.Success -> vs.copy(
                        gameDetail = result.data,
                        isLoading = false
                    )
                    is Result.Error -> {
                        appendToMessageQueue(gameDetailError(message = result.exception.message))
                        vs.copy(isLoading = false)
                    }
                    is Result.Loading -> vs.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun gameDetailError(message: String?): StateMessage = StateMessage(
        Response(
            message = message ?: "An unknown error occurred!",
            uiComponentType = UIComponentType.TryAgainDialogForError(
                tryAgain = {
                    getGameDetail()
                }
            ),
            messageType = MessageType.Error
        )
    )

    private fun showUnableToRetrieveIdError() {
        appendToMessageQueue(
            StateMessage(
                Response(
                    message = "Unable to retrieve id for this game! \nnavigate back and try again!",
                    uiComponentType = UIComponentType.Dialog,
                    messageType = MessageType.Error
                )
            )
        )
        _gameDetailViewState.value = _gameDetailViewState.value.copy(isLoading = false)

    }


}