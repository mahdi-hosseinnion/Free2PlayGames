package com.ssmmhh.free2playgames.feature_game.presentation.util

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View

class TopSpacingItemDecoration(private val padding: Int = 16) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = padding
    }
}