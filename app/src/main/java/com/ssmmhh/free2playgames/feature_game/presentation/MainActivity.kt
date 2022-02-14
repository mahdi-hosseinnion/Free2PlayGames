package com.ssmmhh.free2playgames.feature_game.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.feature_game.data.util.getDataIfSucceeded
import com.ssmmhh.free2playgames.feature_game.domain.use_cases.GetGamesUseCase
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

        runBlocking {
            val result = useCase.invoke()
            result.getDataIfSucceeded()?.let { data ->
                val textView: TextView = findViewById(R.id.txt_hello_world)
                val text = data.mapIndexed { index, game ->
                    "\n#${index.plus(1)} id: ${game.id} ${game.title}"
                }
                textView.text = text.toString()

            }

        }
    }
}