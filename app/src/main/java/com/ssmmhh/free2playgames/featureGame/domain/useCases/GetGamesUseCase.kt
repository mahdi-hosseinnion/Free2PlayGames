package com.ssmmhh.free2playgames.featureGame.domain.useCases

import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.repository.GameRepository
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
