package com.ssmmhh.free2playgames.feature_game.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssmmhh.free2playgames.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}