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

    @Inject
    lateinit var useCase: GetGamesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerAdapter = GameListRecyclerViewAdapter()
        findViewById<RecyclerView>(R.id.recycler_game_list).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(TopSpacingItemDecoration())
            adapter = recyclerAdapter
        }
        runBlocking {
            val result = useCase.invoke()
            result.getDataIfSucceeded()?.let { data ->
                recyclerAdapter.submitList(data)
            }

        }
    }
}