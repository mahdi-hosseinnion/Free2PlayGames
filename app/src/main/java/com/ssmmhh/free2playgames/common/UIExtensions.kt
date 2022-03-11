package com.ssmmhh.free2playgames.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.ssmmhh.free2playgames.R

private const val TAG = "AppDebug:UIExtensions"
private const val DIALOG_CORNER_RADIUS: Float = 8F

fun processQueue(
    context: Context,
    queue: Queue<StateMessage>,
    removeMessageFromQueue: () -> Unit
) {
    queue.peek()?.let { stateMessage ->
        context.onResponseReceived(
            response = stateMessage.response,
            removeMessageFromQueue = removeMessageFromQueue
        )
    }
}


private fun Context.onResponseReceived(
    response: Response,
    removeMessageFromQueue: () -> Unit
) {
    when (response.uiComponentType) {

        is UIComponentType.AreYouSureDialog ->
            areYouSureDialog(
                message = response.message,
                proceed = response.uiComponentType.proceed,
                cancel = response.uiComponentType.cancel,
                removeMessageFromQueue = removeMessageFromQueue
            )
        is UIComponentType.TryAgainDialogForError ->
            tryAgainDialogForError(
                message = response.message,
                tryAgain = response.uiComponentType.tryAgain,
                cancel = response.uiComponentType.cancel,
                removeMessageFromQueue = removeMessageFromQueue
            )

        is UIComponentType.Toast ->
            displayToast(
                message = response.message,
                removeMessageFromQueue = removeMessageFromQueue
            )

        is UIComponentType.Dialog ->
            displayDialog(
                response = response,
                removeMessageFromQueue = removeMessageFromQueue
            )


        is UIComponentType.None -> {
            // This would be a good place to send to your Error Reporting
            // software of choice (ex: Firebase crash reporting)
            Log.i(TAG, "onResponseReceived: ${response.message}")
            removeMessageFromQueue()
        }
    }
}


private fun Context.displayDialog(
    response: Response,
    removeMessageFromQueue: () -> Unit
) = when (response.messageType) {

    is MessageType.Error -> {
        displayErrorDialog(
            message = response.message,
            removeMessageFromQueue = removeMessageFromQueue
        )
    }

    is MessageType.Success -> {
        displaySuccessDialog(
            message = response.message,
            removeMessageFromQueue = removeMessageFromQueue
        )
    }

    is MessageType.Info -> {
        displayInfoDialog(
            message = response.message,
            removeMessageFromQueue = removeMessageFromQueue
        )
    }

    is MessageType.None -> {
        // do nothing
        removeMessageFromQueue()
    }

}


private fun Context.displaySuccessDialog(
    message: String,
    removeMessageFromQueue: () -> Unit
) {
    MaterialDialog(this)
        .show {
            title(R.string.text_success)
            message(text = message)
            positiveButton(R.string.text_ok) {
                removeMessageFromQueue()
                dismiss()
            }
            onDismiss {
                removeMessageFromQueue()
            }
            onCancel {
                removeMessageFromQueue()
            }
            cancelable(true)
            cornerRadius(DIALOG_CORNER_RADIUS)
        }
}

private fun Context.displayErrorDialog(
    message: String,
    removeMessageFromQueue: () -> Unit
) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(text = message)
            positiveButton(R.string.text_ok) {
                removeMessageFromQueue()
                dismiss()
            }
            cancelable(false)
            cornerRadius(DIALOG_CORNER_RADIUS)
        }
}

private fun Context.displayInfoDialog(
    message: String,
    removeMessageFromQueue: () -> Unit
) {
    MaterialDialog(this)
        .show {
            title(R.string.text_info)
            message(text = message)
            positiveButton(R.string.text_ok) {
                removeMessageFromQueue()
                dismiss()
            }
            onDismiss {
            }
            cancelable(false)
            cornerRadius(DIALOG_CORNER_RADIUS)
        }
}

private fun Context.areYouSureDialog(
    message: String,
    proceed: () -> Unit,
    cancel: () -> Unit,
    removeMessageFromQueue: () -> Unit
) {
    MaterialDialog(this)
        .show {
            title(R.string.are_you_sure)
            message(text = message)
            negativeButton(R.string.text_cancel) {
                cancel()
                removeMessageFromQueue()
                dismiss()
            }
            positiveButton(R.string.text_yes) {
                proceed()
                removeMessageFromQueue()
                dismiss()
            }
            onDismiss {
            }
            cancelable(false)
            cornerRadius(DIALOG_CORNER_RADIUS)
        }
}

private fun Context.tryAgainDialogForError(
    message: String,
    tryAgain: () -> Unit,
    cancel: () -> Unit,
    removeMessageFromQueue: () -> Unit
) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(text = message)
            negativeButton(R.string.text_cancel) {
                cancel()
                removeMessageFromQueue()
                dismiss()
            }
            positiveButton(R.string.try_again) {
                tryAgain()
                removeMessageFromQueue()
                dismiss()
            }
            onDismiss {
            }
            cancelable(false)
            cornerRadius(DIALOG_CORNER_RADIUS)
        }
}

fun Context.displayToast(
    @StringRes message: Int,
    removeMessageFromQueue: () -> Unit
) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    removeMessageFromQueue()
}

fun Context.displayToast(
    message: String,
    removeMessageFromQueue: () -> Unit
) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    removeMessageFromQueue()
}


