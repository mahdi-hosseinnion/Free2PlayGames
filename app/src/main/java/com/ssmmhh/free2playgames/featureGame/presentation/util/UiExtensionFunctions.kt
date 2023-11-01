package com.ssmmhh.free2playgames.featureGame.presentation.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.BaseRequestOptions

fun View.setVisibilityToVisible() {
    this.visibility = View.VISIBLE
}

fun View.setVisibilityToInVisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisibilityToGone() {
    this.visibility = View.GONE
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, duration).show()
}

fun Fragment.toast(@StringRes text: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, duration).show()
}

/**
 * Add circular progress animation for glide placeHolder
 * note: [androidx.swiperefreshlayout] library required
 * source: https://stackoverflow.com/a/35306315/10362460
 */
fun <T : BaseRequestOptions<T>?> BaseRequestOptions<T>.addCircularProgressAnimationForPlaceHolder(
    context: Context,
    strokeWidth: Float = 5f,
    centerRadius: Float = 30f
): T {
    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        this.strokeWidth = strokeWidth
        this.centerRadius = centerRadius
        start()
    }
    return placeholder(circularProgressDrawable)
}
