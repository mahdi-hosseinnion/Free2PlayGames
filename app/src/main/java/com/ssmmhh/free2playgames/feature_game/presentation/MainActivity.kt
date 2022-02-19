package com.ssmmhh.free2playgames.feature_game.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.feature_game.data.util.getDataIfSucceeded
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGamesUseCase
import com.ssmmhh.free2playgames.feature_game.presentation.games.GameListRecyclerViewAdapter
import com.ssmmhh.free2playgames.feature_game.presentation.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}