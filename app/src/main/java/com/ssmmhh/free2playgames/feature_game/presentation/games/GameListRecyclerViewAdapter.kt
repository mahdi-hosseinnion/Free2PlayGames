package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.databinding.ListItemGamesBinding
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.presentation.util.addCircularProgressAnimationForPlaceHolder

class GameListRecyclerViewAdapter(
    private val onClickOnItem: (item: Game, position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {

        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
            oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return GameListViewHolder(
            binding = ListItemGamesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickOnItem = onClickOnItem
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Game>) {
        differ.submitList(list)
    }

    class GameListViewHolder
    constructor(
        private val binding: ListItemGamesBinding,
        private val onClickOnItem: (item: Game, position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game) = with(binding) {
            //set on click for navigating to next fragment
            root.setOnClickListener {
                onClickOnItem(item, adapterPosition)
            }
            //setup text values
            txtGameTitle.text = item.title
            txtGameShortDescription.text = item.shortDescription
            txtGameGenre.text = item.genre
            txtGamePlatform.text = item.platform
            //set thumbnail image
            Glide.with(binding.root)
                .load(item.thumbnail)
                .addCircularProgressAnimationForPlaceHolder(binding.root.context)
                .error(R.drawable.no_pictures)
                .transition(withCrossFade())
                .into(imgGameThumbnail)

        }
    }
}
