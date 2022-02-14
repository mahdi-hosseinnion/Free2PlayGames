package com.ssmmhh.free2playgames.feature_game.domain.use_cases

import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.domain.repository.GameRepository
import javax.inject.Inject

class GetGamesUseCase
@Inject
constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(): Result<List<Game>> {
        return gameRepository.getGames()
    }
}