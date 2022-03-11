package com.ssmmhh.free2playgames.feature_game.presentation.common

import androidx.lifecycle.ViewModel
import com.ssmmhh.free2playgames.common.Queue
import com.ssmmhh.free2playgames.common.StateMessage
import com.ssmmhh.free2playgames.common.doesNotAlreadyExistInQueue
import com.ssmmhh.free2playgames.common.uiComponentTypeIsNotNone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    private val _stateMessageQueue = MutableStateFlow(Queue<StateMessage>())
    val stateMessageQueue: StateFlow<Queue<StateMessage>> = _stateMessageQueue

    fun appendToMessageQueue(stateMessage: StateMessage) {
        _stateMessageQueue.value.let {
            if (stateMessage.doesNotAlreadyExistInQueue(it)
                &&
                stateMessage.uiComponentTypeIsNotNone()
            ) {
                //Create new queue b/c stateFlow won't emit the same value twice
                //more-> (https://stackoverflow.com/a/66742924/10362460)
                _stateMessageQueue.value = Queue<StateMessage>().apply {
                    addAll(_stateMessageQueue.value)
                    add(stateMessage)
                }
            }
        }
    }

    fun removeHeadFromQueue() {
        //Create new queue b/c stateFlow won't emit the same value twice
        _stateMessageQueue.value = Queue<StateMessage>().apply {
            addAll(_stateMessageQueue.value)
            remove()
        }
    }
}