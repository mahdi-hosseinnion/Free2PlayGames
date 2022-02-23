package com.ssmmhh.free2playgames.feature_game.presentation.util

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


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