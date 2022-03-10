package com.ssmmhh.free2playgames.common

data class StateMessage(val response: Response)

data class Response(
    val message: String,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType {

    object Toast : UIComponentType()

    object Dialog : UIComponentType()

    class AreYouSureDialog(
        val proceed: () -> Unit,
        val cancel: () -> Unit,
    ) : UIComponentType()

    class TryAgainDialogForError(
        val tryAgain: () -> Unit,
        val cancel: () -> Unit,
    ) : UIComponentType()

    object None : UIComponentType()
}

sealed class MessageType {

    object Success : MessageType()

    object Error : MessageType()

    object Info : MessageType()

    object None : MessageType()
}


fun StateMessage.doesNotAlreadyExistInQueue(queue: Queue<StateMessage>): Boolean =
    !queue.items.contains(this)

fun StateMessage.uiComponentTypeIsNotNone(): Boolean =
    (response.uiComponentType !is UIComponentType.None)


