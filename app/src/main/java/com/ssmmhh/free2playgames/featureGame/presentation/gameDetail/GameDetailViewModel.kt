package com.ssmmhh.free2playgames.featureGame.presentation.gameDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.common.MessageType
import com.ssmmhh.free2playgames.common.Response
import com.ssmmhh.free2playgames.common.StateMessage
import com.ssmmhh.free2playgames.common.UIComponentType
import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.data.util.takeStringRes
import com.ssmmhh.free2playgames.featureGame.domain.useCases.GetGameDetailByIdUseCase
import com.ssmmhh.free2playgames.featureGame.presentation.common.BaseViewModel
import com.ssmmhh.free2playgames.featureGame.presentation.gameDetail.viewstate.GameDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
        // TODO("show unable to load get game url, when dialog error system created")
        return null
    }

    fun getGameProfileUrl(): String? {
        gameDetailViewState.value.gameDetail?.let {
            return it.freeToGameProfileUrl
        }
        // TODO("show unable to load get game profile url, when dialog error system created")
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
        // set is loading to true
        _gameDetailViewState.value = _gameDetailViewState.value.copy(isLoading = true)
        // retrieve data from api
        viewModelScope.launch {
            val result = getGameDetailByIdUseCase.invoke(gameId)
            _gameDetailViewState.value = _gameDetailViewState.value.let { vs ->
                when (result) {
                    is Result.Success -> vs.copy(
                        gameDetail = result.data,
                        isLoading = false
                    )
                    is Result.Error -> {
                        appendToMessageQueue(gameDetailError(result.exception))
                        vs.copy(isLoading = false)
                    }
                    is Result.Loading -> vs.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    private fun gameDetailError(exception: Exception): StateMessage = StateMessage(
        Response(
            message = exception.takeStringRes(R.string.unknown_error),
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
                    message = R.string.unable_to_retrieve_id_for_this_game,
                    uiComponentType = UIComponentType.Dialog,
                    messageType = MessageType.Error
                )
            )
        )
        _gameDetailViewState.value = _gameDetailViewState.value.copy(isLoading = false)
    }
}
