package com.ssmmhh.free2playgames.feature_game.presentation.game_detail

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.*
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.feature_game.presentation.util.addCircularProgressAnimationForPlaceHolder


class GameDetailImagesViewPagerAdapter(
    private var urlOfImages: List<String> = emptyList()
) : PagerAdapter() {
    fun submitNewData(newUrlOfImages: List<String>) {
        this.urlOfImages = newUrlOfImages
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context).apply {
            scaleType = ImageView.ScaleType.FIT_XY
            contentDescription = container.context.getString(R.string.game_thumbnail)
        }
        Glide.with(container.context)
            .load(urlOfImages[position])
            .addCircularProgressAnimationForPlaceHolder(container.context)
            .error(R.drawable.no_pictures)
            .transition(withCrossFade())
            .into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun isViewFromObject(view: View, any: Any): Boolean = (view === any)

    override fun getCount(): Int = urlOfImages.size

}