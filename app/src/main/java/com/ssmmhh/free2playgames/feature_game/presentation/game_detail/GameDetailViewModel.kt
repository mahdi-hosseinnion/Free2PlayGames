package com.ssmmhh.free2playgames.feature_game.presentation.game_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGameDetailByIdUseCase
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
) : ViewModel() {

    private val _gameDetailViewState = MutableStateFlow(GameDetailViewState(isLoading = true))
    val gameDetailViewState: StateFlow<GameDetailViewState> = _gameDetailViewState

    private val _gameName = MutableStateFlow<String?>(state.get("gameTitle"))
    val gameName: StateFlow<String?> = _gameName

    private val _isGameDescriptionTextViewCollapsed = MutableStateFlow(true)
    val isGameDescriptionTextViewCollapsed: StateFlow<Boolean> = _isGameDescriptionTextViewCollapsed

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

    private fun getGameDetail() {
        getGameIdFromState()?.let {
            getGameDetailById(gameId = it)
        } ?: showUnableToRetrieveIdError()
    }

    private fun getGameIdFromState(): Int? = state.get("gameId")

    private fun getGameDetailById(gameId: Int) {
        //set is loading to true
        _gameDetailViewState.value = GameDetailViewState(isLoading = true)
        //retrieve data from api
        viewModelScope.launch {
            val result = getGameDetailByIdUseCase.invoke(gameId)
            _gameDetailViewState.value = when (result) {
                is Result.Success -> GameDetailViewState(
                    gameDetail = result.data
                )
                is Result.Error -> GameDetailViewState(
                    errorMessage = result.exception.message ?: "An unexpected error occurred!"
                )
                is Result.Loading -> GameDetailViewState(
                    isLoading = true
                )
            }
        }
    }

    private fun showUnableToRetrieveIdError() {
        _gameDetailViewState.value = GameDetailViewState(
            errorMessage = "Unable to retrieve id for this game! \n navigate back and try again!"
        )
    }


}